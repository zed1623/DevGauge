package com.ljh.service.impl;

import com.ljh.constant.GithubApiConstant;
import com.ljh.mapper.ProjectMapper;
import com.ljh.pojo.entity.Project;
import com.ljh.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    /**
     * 获取GitHub上热门的仓库（超过10万stars，并且限制返回10个结果）
     *
     * @return 返回热门仓库的信息
     */
    @Override
    public List<Map<String, Object>> getHotRepositories() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + GithubApiConstant.GITHUB_TOKEN);

        String url = GithubApiConstant.GITHUB_API_PROJECT_URL + "?q=stars:>100000&sort=stars&order=desc&per_page=10";
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        List<Map<String, Object>> repositories = new ArrayList<>();
        List<Map<String, Object>> items = (List<Map<String, Object>>) response.getBody().get("items");

        if (items != null) {
            for (Map<String, Object> repo : items) {
                Map<String, Object> repositoryInfo = new HashMap<>();
                Map<String, Object> owner = (Map<String, Object>) repo.get("owner");
                repositoryInfo.put("owner_name", owner.get("login"));
                repositoryInfo.put("avatar_url", owner.get("avatar_url"));
                repositoryInfo.put("name", repo.get("name"));
                repositoryInfo.put("description", repo.get("description"));
                repositoryInfo.put("stargazers_count", repo.get("stargazers_count"));
                repositoryInfo.put("html_url", repo.get("html_url"));
                repositories.add(repositoryInfo);
            }
        }
        return repositories;
    }

    /**
     * 根据仓库链接分析项目
     *
     * @param repoUrl 仓库链接
     * @return 分析后的项目详情
     */
    @Override
    public Project analyzeRepository(String repoUrl) {
        String[] repoInfo = extractRepoInfo(repoUrl);
        String owner = repoInfo[0];
        String repo = repoInfo[1];

        String url = UriComponentsBuilder.fromHttpUrl(GithubApiConstant.GITHUB_API_BASE_URL)
                .pathSegment(owner, repo)
                .build()
                .toString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + GithubApiConstant.GITHUB_TOKEN);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        Map<String, Object> response = responseEntity.getBody();

        Project projectDetails = parseRepositoryDetails(response);

        // 获取项目成员数量
        int projectUserCount = getProjectUserCount(owner, repo, headers);
        projectDetails.setProjectUser(projectUserCount);

        // 获取项目总提交数
        int totalCommits = getTotalCommits(owner, repo, headers);
        projectDetails.setTotalCommits(totalCommits);

        // 获取项目代码行数
        int projectCodeLines = getProjectCodeLines(owner, repo, headers);
        projectDetails.setProjectCode(projectCodeLines);

        // 判断项目是否已经存在于数据库中
        Project existingProject = projectMapper.findById(projectDetails.getId());
        if (existingProject != null) {
            projectMapper.update(projectDetails);
        } else {
            projectMapper.insert(projectDetails);
        }
        return projectDetails;
    }

    private String[] extractRepoInfo(String repoUrl) {
        String[] parts = repoUrl.split("/");
        String owner = parts[parts.length - 2];
        String repo = parts[parts.length - 1];
        return new String[]{owner, repo};
    }

    private Project parseRepositoryDetails(Map<String, Object> response) {
        Project projectDetails = new Project();
        projectDetails.setId(Long.parseLong(response.get("id").toString()));
        projectDetails.setName((String) response.get("name"));
        Map<String, Object> ownerMap = (Map<String, Object>) response.get("owner");
        projectDetails.setOwnerLogin((String) ownerMap.get("login"));
        projectDetails.setRepoUrl((String) response.get("html_url"));
        projectDetails.setStars((Integer) response.get("stargazers_count"));
        projectDetails.setForks((Integer) response.get("forks_count"));
        projectDetails.setIssues((Integer) response.get("open_issues_count"));
        projectDetails.setDescription((String) response.get("description"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime createdAt = LocalDateTime.parse((String) response.get("created_at"), formatter);
        LocalDateTime updatedAt = LocalDateTime.parse((String) response.get("updated_at"), formatter);

        projectDetails.setCreatedAt(createdAt);
        projectDetails.setUpdatedAt(updatedAt);
        projectDetails.setImportanceScore(calculateImportanceScore(projectDetails));
        return projectDetails;
    }

    private int getProjectUserCount(String owner, String repo, HttpHeaders headers) {
        String url = UriComponentsBuilder.fromHttpUrl(GithubApiConstant.GITHUB_API_BASE_URL)
                .pathSegment(owner, repo, "contributors")
                .build()
                .toString();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);
        List contributors = responseEntity.getBody();
        return contributors != null ? contributors.size() : 0;
    }

    private int getTotalCommits(String owner, String repo, HttpHeaders headers) {
        String url = UriComponentsBuilder.fromHttpUrl(GithubApiConstant.GITHUB_API_BASE_URL)
                .pathSegment(owner, repo, "commits")
                .queryParam("per_page", 1)
                .build()
                .toString();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);
        List commits = responseEntity.getBody();
        String linkHeader = responseEntity.getHeaders().getFirst("Link");
        if (linkHeader != null && linkHeader.contains("last")) {
            String lastPageUrl = linkHeader.split(",")[1].split(";")[0].replace("<", "").replace(">", "").trim();
            String[] parts = lastPageUrl.split("&page=");
            return Integer.parseInt(parts[1]);
        }
        return commits != null ? commits.size() : 0;
    }

    private int getProjectCodeLines(String owner, String repo, HttpHeaders headers) {
        String url = UriComponentsBuilder.fromHttpUrl(GithubApiConstant.GITHUB_API_BASE_URL)
                .pathSegment(owner, repo, "languages")
                .build()
                .toString();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        Map<String, Integer> languages = responseEntity.getBody();
        return languages != null ? languages.values().stream().mapToInt(Integer::intValue).sum() : 0;
    }

    private double calculateImportanceScore(Project projectDetails) {
        return projectDetails.getStars() * 0.7 + projectDetails.getForks() * 0.3;
    }
}
