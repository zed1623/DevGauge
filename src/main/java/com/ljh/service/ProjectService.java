package com.ljh.service;

import com.ljh.pojo.entity.Developer;

import java.util.List;
import java.util.Map;

public interface ProjectService {

    /**
     * 获取 GitHub 上的热门仓库（stars > 100万，最多返回10个）
     * @return
     */
    List<Map<String, Object>> getHotRepositories();
}
