package com.ljh.service;

import com.ljh.pojo.entity.Developer;

import java.util.List;
import java.util.Map;

public interface DeveloperService {
    /**
     * 根据GitHub用户名获取用户信息
     *
     * @param username
     * @return
     */
    Developer getUserInfo(String username);

    /**
     * 获取当前小时的接口调用统计
     *
     * @return
     */
    Map<String, Integer> getHourlyApiCallStats();

    /**
     * 获取 GitHub 上的前 10 个热门用户（按 followers 数量排序）
     *
     * @return
     */
    List<Map<String, Object>> getTopGitHubUsers();
}
