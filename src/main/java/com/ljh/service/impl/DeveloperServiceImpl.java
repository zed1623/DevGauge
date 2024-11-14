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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Service
public class DeveloperServiceImpl implements DeveloperService {

    @Autowired
    private DeveloperMapper developerMapper;

    // 使用 ConcurrentHashMap 存储每小时的接口调用次数
    private ConcurrentHashMap<String, AtomicInteger> apiCallCountPerHour = new ConcurrentHashMap<>();
    // 使用线程池
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private final ObjectMapper objectMapper = new ObjectMapper();

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

        incrementApiCallCount();

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

        // 使用线程池处理并行请求
        List<Future<Map<String, Object>>> futures = new ArrayList<>();
        futures.add(executorService.submit(() -> fetchReposData(reposUrl, entity, restTemplate)));  // 获取仓库数据
        futures.add(executorService.submit(() -> fetchUserDetails(userInfo, entity, restTemplate)));  // 获取用户详细信息

        // 等待所有任务完成
        Map<String, Object> reposData = null;
        Map<String, Object> userDetails = null;

        try {
            for (Future<Map<String, Object>> future : futures) {
                Map<String, Object> data = future.get();
                if (data.containsKey("repos")) {
                    reposData = data;
                } else if (data.containsKey("followers")) {
                    userDetails = data;
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (reposData == null || userDetails == null) {
            return null;
        }

        // 处理仓库数据
        List<Map<String, Object>> repos = (List<Map<String, Object>>) reposData.get("repos");
        Map<String, Double> languageUsage = new HashMap<>();
        Map<String, Double> repoScores = new HashMap<>();
        double totalCommits = 0;

        for (Map<String, Object> repo : repos) {
            String repoName = (String) repo.get("name");
            repoScores.put(repoName, 1.0);
            String commitsUrl = (String) repo.get("commits_url");
            commitsUrl = commitsUrl.replace("{/sha}", "");

            // 获取该仓库的提交数量
            ResponseEntity<List> commitsResponse = restTemplate.exchange(commitsUrl, HttpMethod.GET, entity, List.class);
            if (commitsResponse.getBody() != null) {
                totalCommits += commitsResponse.getBody().size();
            }
        }

        // 获取语言使用情况
        for (Map<String, Object> repo : repos) {
            String repoUrl = (String) repo.get("languages_url");
            ResponseEntity<Map> languagesResponseEntity = restTemplate.exchange(repoUrl, HttpMethod.GET, entity, Map.class);
            Map<String, Integer> languages = languagesResponseEntity.getBody();

            if (languages != null) {
                for (Map.Entry<String, Integer> entry : languages.entrySet()) {
                    languageUsage.merge(entry.getKey(), (double) entry.getValue(), Double::sum);
                }
            }
        }

        double talentRank = calculateTalentRank(repos.size(), languageUsage, repoScores, totalCommits);

        // 将 languagePercentage 转换为 JSON 字符串
        String languagePercentageJson = null;
        try {
            languagePercentageJson = objectMapper.writeValueAsString(languageUsage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // 将数据映射到 Developer 对象
        Developer developer = Developer.builder()
                .id(((Number) userInfo.get("id")).longValue())
                .login((String) userInfo.get("login"))
                .name((String) userInfo.get("name"))
                .email((String) userInfo.get("email"))
                .avatarUrl((String) userInfo.get("avatar_url"))
                .profileUrl((String) userInfo.get("html_url"))
                .bio((String) userInfo.get("bio"))
                .createdAt(parseDateTime((String) userInfo.get("created_at")))
                .updatedAt(parseDateTime((String) userInfo.get("updated_at")))
                .languageUsage(languagePercentageJson)
                .talentRank(talentRank)
                .build();

        // 检查开发者是否已存在，如果存在则更新，否则保存新数据
        Developer existingDeveloper = developerMapper.findById(developer.getId());
        if (existingDeveloper != null) {
            developerMapper.update(developer);
        } else {
            developerMapper.save(developer);
        }

        return developer;
    }

    /**
     * 获取用户仓库数据
     *
     * @param reposUrl
     * @param entity
     * @param restTemplate
     * @return
     */
    private Map<String, Object> fetchReposData(String reposUrl, HttpEntity<String> entity, RestTemplate restTemplate) {
        ResponseEntity<List> reposResponseEntity = restTemplate.exchange(reposUrl, HttpMethod.GET, entity, List.class);
        Map<String, Object> result = new HashMap<>();
        result.put("repos", reposResponseEntity.getBody());
        return result;
    }

    /**
     * 获取用户的详细信息（如粉丝数）
     *
     * @param userInfo
     * @param entity
     * @param restTemplate
     * @return
     */
    private Map<String, Object> fetchUserDetails(Map<String, Object> userInfo, HttpEntity<String> entity, RestTemplate restTemplate) {
        Map<String, Object> userDetailsInfo = new HashMap<>();
        String userUrl = (String) userInfo.get("url");

        ResponseEntity<Map> userDetailsResponse = restTemplate.exchange(userUrl, HttpMethod.GET, entity, Map.class);
        Map<String, Object> userDetails = userDetailsResponse.getBody();

        if (userDetails != null) {
            userDetailsInfo.put("followers", userDetails.get("followers"));
        }

        return userDetailsInfo;
    }

    /**
     * 获取 GitHub 上热门的前 10 个用户（按 followers 数量排序）
     *
     * @return 返回热门的前 10 个用户的信息
     */
    @Override
    public List<Map<String, Object>> getTopGitHubUsers() {
        // 使用 RestTemplate 调用 GitHub API
        RestTemplate restTemplate = new RestTemplate();

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + GithubApiConstant.GITHUB_TOKEN);  // 可选：使用 token 增加请求限额

        // 构建请求体，查询热门用户
        String url = GithubApiConstant.GITHUB_API_URL + "/search/users?q=type:user&sort=followers&order=desc&per_page=10";
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 发出 GET 请求，获取响应
        ResponseEntity<Map> response = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, entity, Map.class);

        // 解析返回的数据
        List<Map<String, Object>> users = new ArrayList<>();
        Map<String, Object> responseBody = response.getBody();
        if (responseBody != null) {
            List<Map<String, Object>> items = (List<Map<String, Object>>) responseBody.get("items");

            // 提取所需字段
            if (items != null) {
                for (Map<String, Object> user : items) {
                    Map<String, Object> userInfo = new HashMap<>();

                    // 提取所需字段
                    userInfo.put("login", user.get("login"));  // 用户名
                    userInfo.put("avatar_url", user.get("avatar_url"));  // 用户头像
                    userInfo.put("html_url", user.get("html_url"));  // GitHub 个人主页链接

                    // 获取每个用户的粉丝数（通过 /users/{username} 请求）
                    String userUrl = (String) user.get("url");  // 获取用户详情URL
                    ResponseEntity<Map> userDetailsResponse = restTemplate.exchange(userUrl, org.springframework.http.HttpMethod.GET, entity, Map.class);
                    Map<String, Object> userDetails = userDetailsResponse.getBody();

                    if (userDetails != null) {
                        userInfo.put("followers", userDetails.get("followers"));  // 粉丝数
                    }

                    users.add(userInfo);
                }
            }
        }

        return users;
    }

    /**
     * TalentRank计算，综合考虑仓库数量、语言占比和提交数
     *
     * @param repoCount
     * @param languagePercentage
     * @param repoScores
     * @param totalCommits
     * @return
     */
    private double calculateTalentRank(int repoCount, Map<String, Double> languagePercentage, Map<String, Double> repoScores, double totalCommits) {
        // 计算仓库数量得分（最多 30 分）
        double repoScore = Math.min(repoCount, 10) * 3.0;  // 仓库得分，最大为 30 分

        // 计算语言占比得分（最多 30 分）
        double languageScore = languagePercentage.values().stream().mapToDouble(v -> v).sum() * 0.3;

        // 计算提交数得分（最多 40 分）
        double commitsScore = Math.min(totalCommits / 10000000, 1) * 40.0;

        // 综合得分
        double talentRank = repoScore + languageScore + commitsScore;
        return Math.min(talentRank, 100);  // 确保 talentRank 最大值为 100
    }

    /**
     * 获取当前小时的接口调用统计
     *
     * @return
     */
    public Map<String, Integer> getHourlyApiCallStats() {
        Map<String, Integer> result = new HashMap<>();
        // 获取当前日期
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 遍历每小时的接口调用次数
        for (Map.Entry<String, AtomicInteger> entry : apiCallCountPerHour.entrySet()) {
            String hourKey = entry.getKey();
            if (hourKey.startsWith(currentDate)) {
                // 提取小时部分
                String hour = hourKey.substring(11, 13);
                result.put(hour + "时", entry.getValue().get());  // 存储接口调用次数，key 为小时（13时，14时等）
            }
        }
        return result;
    }

    /**
     * 将字符串时间转换为 LocalDateTime
     *
     * @param dateTimeStr
     * @return
     */
    private LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null) return null;
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_DATE_TIME);
    }

    /**
     * 增加接口调用次数
     */
    public void incrementApiCallCount() {
        // 获取当前小时（格式：yyyy-MM-dd-HH）
        String currentHourKey = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH"));

        // 如果该小时的统计不存在，创建新的计数器
        apiCallCountPerHour.putIfAbsent(currentHourKey, new AtomicInteger(0));

        // 增加该小时的接口调用次数
        apiCallCountPerHour.get(currentHourKey).incrementAndGet();
    }

    /**
     * 每小时的第一分钟执行
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void reportHourlyApiCallCount() {
        // 获取当前小时的接口调用次数
        String currentHourKey = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH"));
        AtomicInteger count = apiCallCountPerHour.get(currentHourKey);

        if (count != null) {
            log.info("当前小时接口调用次数 (" + currentHourKey + "): " + count.get());
            // 统计完成后重置该小时的接口调用次数
            count.set(0);
        }
    }


    /**
     * 每天定时执行，统计每天的接口调用次数
     */
    @Scheduled(cron = "0 0 0 * * ?") // 每天的零点执行
    public void resetDailyStats() {
        apiCallCountPerHour.clear();  // 清空所有小时的计数，准备开始新的统计
        log.info("已清空当天的接口调用次数统计");
    }
}