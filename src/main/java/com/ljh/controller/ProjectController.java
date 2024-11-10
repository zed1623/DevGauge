package com.ljh.controller;

import com.ljh.pojo.entity.Developer;
import com.ljh.result.Result;
import com.ljh.service.DeveloperService;
import com.ljh.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/project")
@Slf4j
@Api(tags = "项目相关接口")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    /**
     * 获取 GitHub 上的热门仓库（stars > 10万，最多返回10个）
     *
     * @return 返回热门仓库的信息
     */
    @GetMapping("/hotRepositories")
    public Result<List<Map<String, Object>>> getHotRepositories() {
        List<Map<String, Object>> hotRepos = projectService.getHotRepositories();
        return Result.success(hotRepos);
    }


}
