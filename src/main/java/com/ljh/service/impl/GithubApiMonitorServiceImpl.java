package com.ljh.service.impl;

import com.ljh.constant.GithubApiConstant;
import com.ljh.handler.GithubApiStatusWebSocketHandler;
import com.ljh.service.GithubApiMonitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Service
@Slf4j
@EnableScheduling
public class GithubApiMonitorServiceImpl implements GithubApiMonitorService {

    private boolean githubApiStatus = true;
    private final RestTemplate restTemplate = new RestTemplate();
    private final GithubApiStatusWebSocketHandler githubApiStatusWebSocketHandler;
    private final ThreadPoolTaskScheduler taskScheduler;

    public GithubApiMonitorServiceImpl(GithubApiStatusWebSocketHandler webSocketHandler, ThreadPoolTaskScheduler taskScheduler) {
        this.githubApiStatusWebSocketHandler = webSocketHandler;
        this.taskScheduler = taskScheduler;
    }

    /**
     * 启动定时检查 GitHub API 健康状态
     * 每隔 10 分钟执行一次
     */
    @PostConstruct
    public void startHealthCheckScheduler() {
        taskScheduler.scheduleAtFixedRate(this::checkGithubApiHealth, 10 * 60 * 1000);
    }

    /**
     * 定时检查 GitHub API 的健康状态
     */
    public void checkGithubApiHealth() {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(GithubApiConstant.GITHUB_API_USER_URL_TEST, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                githubApiStatus = true;
            } else {
                githubApiStatus = false;
            }
        } catch (Exception e) {
            githubApiStatus = false;
        }

        // 发送健康状态到 WebSocket 客户端
        String status = githubApiStatus ? GithubApiConstant.GITHUB_API_USER_SUCCESS : GithubApiConstant.GITHUB_API_USER_ERROR;
        githubApiStatusWebSocketHandler.sendMessage(status, "1");  // 假设用户ID是 1
    }

    @Override
    public boolean isGithubApiHealthy() {
        return githubApiStatus;
    }
}
