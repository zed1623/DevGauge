package com.ljh.service;

import com.ljh.pojo.entity.User;

import java.util.Map;

public interface UserService {

    /**
     * 获取用户登录信息，并把信息保存到数据库里面
     *
     * @param userAttributes
     */
    User saveUser(Map<String, Object> userAttributes);
}
