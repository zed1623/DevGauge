package com.ljh.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 项目实体类，存储 GitHub 上的开源项目的基本信息，
 * 用于评估开发者的贡献度和项目的重要性。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project implements Serializable {

    /**
     * 项目唯一标识，数据库或系统内唯一标识项目的 ID
     */
    private Long id;

    /**
     * 项目名称，GitHub 仓库的名称
     */
    private String name;

    /**
     * 项目成员数量，指该项目中实际参与的用户数量
     */
    private Integer projectUser;

    /**
     * 项目总提交数，用于衡量项目的活跃度
     */
    private int totalCommits;

    /**
     * 项目的总代码行数，代表项目的代码规模
     */
    private int projectCode;

    /**
     * 项目介绍，包含项目的功能、目标或背景描述
     */
    private String description;

    /**
     * 项目所有者的 GitHub 用户名
     */
    private String ownerLogin;

    /**
     * 仓库的 GitHub 链接，用于直接访问项目仓库
     */
    private String repoUrl;

    /**
     * 项目 GitHub 星标数量，代表项目的受欢迎程度
     */
    private int stars;

    /**
     * 项目被 Fork 的数量，反映项目的复用程度
     */
    private int forks;

    /**
     * 项目中开放的 issue 数量，表明项目的问题和需求处理情况
     */
    private int issues;

    /**
     * 项目的创建时间（ISO 8601 格式），代表项目的开始时间
     */
    private LocalDateTime createdAt;

    /**
     * 项目最后更新时间（ISO 8601 格式），记录项目最近的活跃情况
     */
    private LocalDateTime updatedAt;

    /**
     * 项目的重要性评分，基于星标、fork 等权重进行综合计算
     */
    private double importanceScore;

    /**
     * 关联的其他项目名称列表，表示该项目与其他项目的关系（例如被 Fork 或引用的项目）
     */
    private String[] linkedProjects;

    /**
     * 计算项目的实时重要性评分（根据星标、fork 等权重）
     *
     * @return 计算后的重要性评分
     */
    public double calculateImportanceScore() {
        // 假设一个简单的计算公式，例如星标权重为 0.6，Fork 权重为 0.3，Issue 权重为 0.1
        double starWeight = 0.6;
        double forkWeight = 0.3;
        double issueWeight = 0.1;
        this.importanceScore = stars * starWeight + forks * forkWeight - issues * issueWeight;
        return this.importanceScore;
    }
}
