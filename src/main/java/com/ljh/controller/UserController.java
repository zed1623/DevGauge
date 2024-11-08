package com.ljh.controller;

import com.ljh.result.Result;
import com.ljh.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@Api(tags = "用户登录相关接口")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户登录信息，并把信息保存到数据库里面
     * @param authentication
     * @return
     */
    @GetMapping("/getUserInfo")
    public Result<Map<String, Object>> getUserData(OAuth2AuthenticationToken authentication) {
        log.info("获取用户登录信息：" + authentication.getPrincipal());
        OAuth2User user = authentication.getPrincipal();
        // 获取 GitHub 用户信息
        Map<String, Object> userAttributes = user.getAttributes();
        // 调用服务层保存用户信息
        userService.saveUser(userAttributes);
        // 返回 GitHub 用户信息
        return Result.success(userAttributes);
    }
}
