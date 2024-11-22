package com.ljh.service;

import com.ljh.pojo.entity.Developer;
import com.ljh.pojo.entity.Project;

import java.util.List;
import java.util.Map;

public interface ProjectService {

    /**
     * 获取 GitHub 上的热门仓库（stars > 100万，最多返回10个）
     * @return
     */
    List<Map<String, Object>> getHotRepositories();

    /**
     * 根据仓库链接分析项目
     * @param repoUrl
     * @return
     */
    Project analyzeRepository(String repoUrl);
}
