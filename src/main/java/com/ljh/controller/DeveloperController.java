package com.ljh.controller;

import com.ljh.pojo.entity.Developer;
import com.ljh.result.Result;
import com.ljh.service.DeveloperService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/developer")
@Slf4j
@Api(tags = "开发者相关接口")
public class DeveloperController {

    @Autowired
    private DeveloperService developerService;

    /**
     * 根据GitHub用户名获取用户信息
     *
     * @param username
     * @return
     */
    @ApiOperation(value = "根据GitHub用户名获取用户信息")
    @PostMapping("/getUserInfo")
    public Result<Developer> getUserInfo(String username) {
        log.info("根据GitHub用户名获取用户信息:" + username);
        try {
            Developer developer = developerService.getUserInfo(username);
            // 调用开发者服务层的获取用户信息方法
            return Result.success(developer);
        } catch (Exception e) {
            log.error("获取用户信息失败：", e);
            return Result.error("获取用户信息失败");
        }
    }

    /**
     * 获取 GitHub 上的前 10 个热门用户（按 followers 数量排序）
     *
     * @return 返回热门的前 10 个用户的信息
     */
    @GetMapping("/topUsers")
    public Result<List<Map<String, Object>>> getTopGitHubUsers() {
        List<Map<String, Object>> topUsers = developerService.getTopGitHubUsers();
        return Result.success(topUsers);
    }

    /**
     * 获取当前小时的接口调用统计
     *
     * @return 返回每天每小时的接口调用次数
     */
    @GetMapping("/hourlyStats")
    public Result<Map<String, Integer>> getHourlyApiCallStats() {
        Map<String, Integer> stats = developerService.getHourlyApiCallStats();
        return Result.success(stats);
    }

}
