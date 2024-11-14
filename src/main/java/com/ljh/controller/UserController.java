package com.ljh.controller;

import cn.hutool.http.HttpRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljh.pojo.entity.User;
import com.ljh.result.Result;
import com.ljh.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@Api(tags = "用户登录相关接口")
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.github.redirect-uri}")
    private String redirectUri;

    private final RestTemplate restTemplate;

    public UserController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 获取用户登录信息，并把信息保存到数据库里面
     *
     * @param code
     * @return
     */
    @ApiOperation(value = "调用GitHub的登录接口(new)")
    @GetMapping("/callback")
    public Result<User> getUserData(String code) throws JsonProcessingException {
        log.info("Received code: {}", code);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("client_id", clientId);
        paramMap.put("client_secret", clientSecret);
        paramMap.put("code", code);
        paramMap.put("redirect_uri", redirectUri);
        paramMap.put("accept", "json");

        // 获取 GitHub Access Token
        String result = HttpRequest.post("https://github.com/login/oauth/access_token")
                .form(paramMap)
                .header("Accept", "application/json")
                .execute().body();

        if (result.contains("error")) {
            log.error("Error fetching access token: {}", result);
            return Result.error("Failed to get access token");
        }

        // 解析 token
        Map<String, Object> response = new ObjectMapper().readValue(result, Map.class);
        String token = (String) response.get("access_token");
        log.info("Access Token: {}", token);

        // 使用 token 获取用户信息
        String userInfo = HttpRequest.get("https://api.github.com/user")
                .header("Authorization", "token " + token)
                .header("X-GitHub-Api-Version", "2022-11-28")
                .execute().body();

        // 将 userInfo 转换为 Map<String, Object> 类型的 userAttributes
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> userAttributes = objectMapper.readValue(userInfo, Map.class);

        // 获取私有仓库信息
        String privateReposInfo = HttpRequest.get("https://api.github.com/user/repos?visibility=private")
                .header("Authorization", "token " + token)
                .header("X-GitHub-Api-Version", "2022-11-28")
                .execute().body();

        // 打印私有仓库信息
        log.info("Private Repositories(私有仓库): {}", privateReposInfo);

        // 调用服务层保存用户信息
        User user = userService.saveUser(userAttributes);

        log.info("User Info: {}", userInfo);
        return Result.success(user);
    }
}
