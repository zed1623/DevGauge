package com.ljh.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 开发者实体类，存储开发者的基本信息，包括个人资料、所属国家、领域等信息。
 * 包含与开发者相关的详细信息，以便进行全面的项目分析和展示。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Developer implements Serializable {

    /**
     * 开发者唯一标识
     */
    private long id;

    /**
     * 开发者所属的项目 URL
     */
    private String projectUrl;

    /**
     * 活动时区分布，记录开发者在不同时间段的活跃频率
     */
    private String hourFrequency;

    /**
     * 代码片段或主要代码内容标识
     */
    private String code;

    /**
     * GitHub 用户名，唯一标识开发者的 GitHub 账户
     */
    private String login;

    /**
     * 开发者的全名
     */
    private String name;

    /**
     * 开发者邮箱，用于联系开发者（公开的邮箱）
     */
    private String email;

    /**
     * 提交代码的次数，代表开发者的贡献活跃度
     */
    private int number;

    /**
     * 总的代码添加量，显示开发者的代码贡献（新增代码行数）
     */
    private long totalAdditions;

    /**
     * 总的代码删除量，显示开发者的代码调整能力（删除代码行数）
     */
    private long totalDeletions;

    /**
     * 开发者头像 URL
     */
    private String avatarUrl;

    /**
     * 开发者博客链接，如果开发者提供了个人博客
     */
    private String blogUrl;

    /**
     * GitHub 个人主页链接，便于查看开发者的 GitHub 主页
     */
    private String profileUrl;

    /**
     * 开发者个人介绍或自我描述
     */
    private String bio;

    /**
     * 开发者所属国家的推测结果（如果可以猜测）
     */
    private String nation;

    /**
     * 国家推测的置信度，用于表明国家推测的准确性
     */
    private float nationConfidence;

    /**
     * 开发者所属的技术领域，比如前端、后端、机器学习等
     */
    private String field;

    /**
     * 技术能力评价分数，基于 TalentRank 算法计算
     */
    private float talentRank;

    /**
     * 开发者账号的创建时间（ISO 8601 格式）
     */
    private String createdAt;

    /**
     * 最后更新时间（ISO 8601 格式）
     */
    private String updatedAt;

    /**
     * 项目贡献度，计算开发者在项目中的重要性
     */
    private float projectContributionScore;

    /**
     * 关注者数量，表示开发者的影响力
     */
    private int followersCount;

    /**
     * 关注的用户数量，展示开发者的网络范围
     */
    private int followingCount;

    /**
     * 开发者的活跃仓库数量，表明开发者的参与度
     */
    private int activeRepoCount;
}
