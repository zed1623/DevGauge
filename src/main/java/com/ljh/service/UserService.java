package com.ljh.service;

import java.util.Map;

public interface UserService {

    /**
     * 获取用户登录信息，并把信息保存到数据库里面
     *
     * @param userAttributes
     */
    void saveUser(Map<String, Object> userAttributes);
}
