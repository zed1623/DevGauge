package com.ljh.controller;

import com.ljh.constant.GithubApiConstant;
import com.ljh.handler.GithubApiStatusWebSocketHandler;
import com.ljh.result.Result;
import com.ljh.service.GithubApiMonitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@Api(tags = "GitHub API 健康检查相关接口")
@RequestMapping("/api/githubApi")
public class GithubApiHealthCheckController {

    @Autowired
    private GithubApiMonitorService githubApiMonitorService;

    @Autowired
    private GithubApiStatusWebSocketHandler githubApiStatusWebSocketHandler;
    /**
     * 启动健康状态推送
     */
    @GetMapping("/startHealthCheck")
    @ApiOperation(value = "启动GitHub API健康状态推送")
    public Result<String> startHealthCheck() {
        log.info("启动健康状态推送");
        boolean isHealthy = githubApiMonitorService.isGithubApiHealthy();
        String status = isHealthy ? GithubApiConstant.GITHUB_API_USER_SUCCESS: GithubApiConstant.GITHUB_API_USER_ERROR;
        // 调用 githubApiStatusWebSocketHandler  sendMessage 推送状态
        githubApiStatusWebSocketHandler.sendMessage(status,"1");
        return Result.success(status);
    }
}
