package com.ljh.controller;

import com.ljh.manager.ApiCallCountManager;
import com.ljh.pojo.entity.Developer;
import com.ljh.pojo.entity.Project;
import com.ljh.result.Result;
import com.ljh.service.DeveloperService;
import com.ljh.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/project")
@Slf4j
@Api(tags = "项目相关接口")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ApiCallCountManager apiCallCountManager;

    /**
     * 获取 GitHub 上的热门仓库（stars > 10万，最多返回10个）
     *
     * @return 返回热门仓库的信息
     */
    @GetMapping("/hotRepositories")
    public Result<List<Map<String, Object>>> getHotRepositories() {
        apiCallCountManager.incrementCount("getHotRepositories");
        List<Map<String, Object>> hotRepos = projectService.getHotRepositories();
        return Result.success(hotRepos);
    }

    /**
     * 根据仓库链接分析项目
     * @param repoUrl
     * @return
     */
    @PostMapping("/analyzeRepository")
    public Result<Project> analyzeRepository(@RequestParam String repoUrl) {
        apiCallCountManager.incrementCount("analyzeRepository");
        Project project = projectService.analyzeRepository(repoUrl);
        return Result.success(project);
    }

}
