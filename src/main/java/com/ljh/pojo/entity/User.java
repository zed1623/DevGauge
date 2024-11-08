package com.ljh.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户实体类，存储 GitHub 用户的基本信息和可选信息。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    /**
     * 用户唯一标识符，使用 GitHub 提供的用户ID
     */
    private Long id;

    /**
     * GitHub 用户名
     */
    private String login;

    /**
     * 用户头像链接
     */
    private String avatarUrl;

    /**
     * 用户在 GitHub 上的个人主页链接
     */
    private String htmlUrl;

    /**
     * 账户创建时间，用于跟踪用户注册日期
     */
    private LocalDateTime createdAt;

    /**
     * 用户信息的最后更新时间
     */
    private LocalDateTime updatedAt;

    /**
     * 用户的真实姓名
     */
    private String name;

    /**
     * 用户所在公司
     */
    private String company;

    /**
     * 用户的地理位置
     */
    private String location;

    /**
     * 用户的电子邮件地址
     */
    private String email;

    /**
     * 用户的个人简介
     */
    private String bio;
}
