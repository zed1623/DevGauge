package com.ljh.service.impl;

import com.ljh.manager.ApiCallCountManager;
import com.ljh.mapper.UserMapper;
import com.ljh.pojo.entity.User;
import com.ljh.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    // 使用 AtomicInteger 计数接口调用次数
    private AtomicInteger apiCallCount = new AtomicInteger(0);


    /**
     * 获取用户登录信息，并把信息保存到数据库里面
     *
     * @param userAttributes
     */
    @Override
    public User saveUser(Map<String, Object> userAttributes) {
        // 增加接口调用次数
        ApiCallCountManager.incrementCount("loginUser");
        // 提取需要的字段
        Long id = Long.valueOf(userAttributes.get("id").toString()); // 将用户 ID 转换为 Long
        String login = (String) userAttributes.get("login");
        String avatarUrl = (String) userAttributes.get("avatar_url");
        String htmlUrl = (String) userAttributes.get("html_url");
        String name = (String) userAttributes.get("name");
        String company = (String) userAttributes.get("company");
        String location = (String) userAttributes.get("location");
        String email = (String) userAttributes.get("email");
        String bio = (String) userAttributes.get("bio");

        // 获取时间戳并转换为 LocalDateTime
        LocalDateTime createdAt = LocalDateTime.parse((String) userAttributes.get("created_at"), DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime updatedAt = LocalDateTime.parse((String) userAttributes.get("updated_at"), DateTimeFormatter.ISO_DATE_TIME);

        // 创建用户对象
        User newUser = new User();
        newUser.setId(id);
        newUser.setLogin(login);
        newUser.setAvatarUrl(avatarUrl);
        newUser.setHtmlUrl(htmlUrl);
        newUser.setName(name);
        newUser.setCompany(company);
        newUser.setLocation(location);
        newUser.setEmail(email);
        newUser.setBio(bio);
        newUser.setCreatedAt(createdAt);
        newUser.setUpdatedAt(updatedAt);

        // 检查用户是否已存在
        User existingUser = userMapper.findById(id);
        if (existingUser != null) {
            // 如果用户存在，更新用户信息
            userMapper.updateUser(newUser);
        } else {
            // 如果用户不存在，保存新用户到数据库
            userMapper.save(newUser);
        }

        return newUser;
    }

    /**
     * 每分钟执行一次，统计接口调用次数
     */
    @Scheduled(cron = "0 * * * * ?") // 每分钟的第一个秒钟执行
    public void reportApiCallCount() {
        int count = apiCallCount.get();
        log.info("过去一分钟内loginUser接口调用次数: " + count);
        // 这里可以做更多的统计操作，比如将次数存储到数据库中
        apiCallCount.set(0);  // 重置接口调用次数
    }
}
