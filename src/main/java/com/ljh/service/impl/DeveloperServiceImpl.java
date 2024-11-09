package com.ljh.service.impl;

import com.ljh.constant.GithubApiConstant;
import com.ljh.manager.ApiCallCountManager;
import com.ljh.mapper.DeveloperMapper;
import com.ljh.pojo.entity.Developer;
import com.ljh.service.DeveloperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Service
public class DeveloperServiceImpl implements DeveloperService {

    @Autowired
    private DeveloperMapper developerMapper;

    // 使用 AtomicInteger 计数接口调用次数
    private AtomicInteger apiCallCount = new AtomicInteger(0);

    // 用于将 Map 转换为 JSON 字符串
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 根据GitHub用户名获取用户信息
     *
     * @param username
     * @return
     */
    @Override
    public Developer getUserInfo(String username) {

        // 增加接口调用次数
        ApiCallCountManager.incrementCount("getUserInfo");

        RestTemplate restTemplate = new RestTemplate();
        String url = GithubApiConstant.GITHUB_API_USER_URL + username;

        // 设置请求头，加入 token 进行认证
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + GithubApiConstant.GITHUB_TOKEN);  // 使用 Bearer Token

        // 使用 HttpEntity 将头信息和请求一起发送
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 发送请求并获取 GitHub 用户信息
        ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        Map<String, Object> userInfo = responseEntity.getBody();

        if (userInfo == null) {
            return null; // 处理未找到用户的情况
        }

        // 获取用户的仓库信息
        String reposUrl = (String) userInfo.get("repos_url");
        ResponseEntity<List> reposResponseEntity = restTemplate.exchange(reposUrl, HttpMethod.GET, entity, List.class);

        List<Map<String, Object>> repos = reposResponseEntity.getBody();
        Map<String, Double> languageUsage = new HashMap<>();
        Map<String, Double> repoScores = new HashMap<>(); // 用于存储每个仓库的分数
        double totalCommits = 0;

        // 初始化每个仓库的分数为 1，并获取提交数量
        for (Map<String, Object> repo : repos) {
            String repoName = (String) repo.get("name");
            repoScores.put(repoName, 1.0);
            String commitsUrl = (String) repo.get("commits_url");
            commitsUrl = commitsUrl.replace("{/sha}", "");  // 去掉 URL 中的 {sha} 部分

            // 获取该仓库的提交数量
            ResponseEntity<List> commitsResponse = restTemplate.exchange(commitsUrl, HttpMethod.GET, entity, List.class);
            if (commitsResponse.getBody() != null) {
                totalCommits += commitsResponse.getBody().size();  // 累加提交数量
            }
        }

        // 遍历所有仓库，获取每个仓库的语言使用情况
        for (Map<String, Object> repo : repos) {
            String repoUrl = (String) repo.get("languages_url");
            ResponseEntity<Map> languagesResponseEntity = restTemplate.exchange(repoUrl, HttpMethod.GET, entity, Map.class);
            Map<String, Integer> languages = languagesResponseEntity.getBody();

            // 计算每种语言的字节数占比
            if (languages != null) {
                for (Map.Entry<String, Integer> entry : languages.entrySet()) {
                    languageUsage.merge(entry.getKey(), (double) entry.getValue(), Double::sum);
                }
            }
        }

        // 计算总字节数
        double totalBytes = languageUsage.values().stream().mapToDouble(Double::doubleValue).sum();
        Map<String, Double> languagePercentage = new HashMap<>();

        // 归一化，确保总占比为 100%
        if (totalBytes > 0) {
            for (Map.Entry<String, Double> entry : languageUsage.entrySet()) {
                // 计算百分比
                double percentage = (entry.getValue() / totalBytes) * 100;
                languagePercentage.put(entry.getKey(), percentage);
            }
        }

        // 计算 TalentRank
        double talentRank = calculateTalentRank(repos.size(), languagePercentage, repoScores, totalCommits);

        // 将 languagePercentage 转换为 JSON 字符串
        String languagePercentageJson = null;
        try {
            languagePercentageJson = objectMapper.writeValueAsString(languagePercentage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // 将数据映射到 Developer 对象
        Developer developer = Developer.builder()
                .id(((Number) userInfo.get("id")).longValue())  // 转换为 long 类型
                .login((String) userInfo.get("login"))
                .name((String) userInfo.get("name"))
                .email((String) userInfo.get("email"))
                .avatarUrl((String) userInfo.get("avatar_url"))
                .profileUrl((String) userInfo.get("html_url"))
                .bio((String) userInfo.get("bio"))
                .createdAt(parseDateTime((String) userInfo.get("created_at")))  // 转换时间字段
                .updatedAt(parseDateTime((String) userInfo.get("updated_at")))  // 转换时间字段
                .languageUsage(languagePercentageJson)  // 将 JSON 字符串存储到 languageUsage
                .talentRank(talentRank)  // 保存 talentRank
                .build();

        // 检查开发者是否已存在，如果存在则更新，否则保存新数据
        Developer existingDeveloper = developerMapper.findById(developer.getId());
        if (existingDeveloper != null) {
            developerMapper.update(developer);
        } else {
            developerMapper.save(developer);
        }

        Developer developer1 = developerMapper.findById(developer.getId());
        return developer1;
    }

    /**
     * TalentRank计算，综合考虑仓库数量、语言占比和提交数
     */
    private double calculateTalentRank(int repoCount, Map<String, Double> languagePercentage, Map<String, Double> repoScores, double totalCommits) {
        // 计算仓库数量得分（最多 30 分）
        double repoScore = Math.min(repoCount, 10) * 3.0;  // 仓库得分，最大为 30 分

        // 计算语言占比得分（最多 30 分）
        double languageScore = languagePercentage.values().stream().mapToDouble(v -> v).sum() * 0.3;

        // 计算提交数得分（最多 40 分）
        double commitsScore = Math.min(totalCommits / 1000, 1) * 40.0;  // 这里以每 100 次提交为 1 分，最大 40 分

        // 综合得分
        double talentRank = repoScore + languageScore + commitsScore;
        return Math.min(talentRank, 100);  // 确保 talentRank 最大值为 100
    }


    /**
     * 将字符串时间转换为 LocalDateTime
     * @param dateTimeStr
     * @return
     */
    private LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null) return null;
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_DATE_TIME);
    }

    /**
     * 每分钟执行一次，统计接口调用次数
     */
    @Scheduled(cron = "0 * * * * ?") // 每分钟的第一个秒钟执行
    public void reportApiCallCount() {
        int count = apiCallCount.get();
        log.info("过去一分钟内getUserInfo接口调用次数: " + count);
        // 这里可以做更多的统计操作，比如将次数存储到数据库中
        apiCallCount.set(0);  // 重置接口调用次数
    }
}