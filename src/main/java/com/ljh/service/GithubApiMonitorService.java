package com.ljh.service;

public interface GithubApiMonitorService {
    /**
     * 检查 GitHub API 的健康状态
     * @return
     */
    boolean isGithubApiHealthy();
}
