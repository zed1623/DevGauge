package com.ljh.service;

import com.ljh.pojo.entity.Developer;

public interface DeveloperService {
    /**
     * 根据GitHub用户名获取用户信息
     * @param username
     * @return
     */
    Developer getUserInfo(String username);
}
