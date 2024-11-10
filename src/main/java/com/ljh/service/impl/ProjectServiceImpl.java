package com.ljh.service.impl;

import com.ljh.constant.GithubApiConstant;
import com.ljh.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ProjectServiceImpl implements ProjectService {

    /**
     * 获取GitHub上热门的仓库（超过10万stars，并且限制返回10个结果）
     *
     * @return 返回热门仓库的信息
     */
    @Override
    public List<Map<String, Object>> getHotRepositories() {
        // 使用 RestTemplate 调用 GitHub API
        RestTemplate restTemplate = new RestTemplate();

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + GithubApiConstant.GITHUB_TOKEN);

        // 构建请求体，查询超过100万stars的项目，并限制返回10个
        String url = GithubApiConstant.GITHUB_API_PROJECT_URL + "?q=stars:>100000&sort=stars&order=desc&per_page=10";
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 发出 GET 请求，获取响应
        ResponseEntity<Map> response = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, entity, Map.class);

        // 解析返回的数据
        List<Map<String, Object>> repositories = new ArrayList<>();
        List<Map<String, Object>> items = (List<Map<String, Object>>) response.getBody().get("items");

        // 提取所需信息
        if (items != null) {
            for (Map<String, Object> repo : items) {
                Map<String, Object> repositoryInfo = new HashMap<>();

                // 提取所需字段
                Map<String, Object> owner = (Map<String, Object>) repo.get("owner");
                repositoryInfo.put("owner_name", owner.get("login"));  // 仓库拥有者的名称
                repositoryInfo.put("avatar_url", owner.get("avatar_url"));  // 仓库作者的头像
                repositoryInfo.put("name", repo.get("name"));  // 仓库名称
                repositoryInfo.put("description", repo.get("description"));  // 仓库描述
                repositoryInfo.put("stargazers_count", repo.get("stargazers_count"));  // 仓库的 stars 数量
                repositoryInfo.put("html_url", repo.get("html_url"));  // 仓库的 GitHub 页面链接

                repositories.add(repositoryInfo);
            }
        }
        return repositories;
    }
}
