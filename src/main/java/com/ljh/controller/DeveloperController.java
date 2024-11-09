package com.ljh.controller;

import com.ljh.pojo.entity.Developer;
import com.ljh.result.Result;
import com.ljh.service.DeveloperService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/developer")
@Slf4j
@Api(tags = "开发者相关接口")
public class DeveloperController {

    @Autowired
    private DeveloperService developerService;

    /**
     * 根据GitHub用户名获取用户信息
     * @param username
     * @return
     */
    @ApiOperation(value = "根据GitHub用户名获取用户信息")
    @PostMapping("/getUserInfo")
    public Result<Developer> getUserInfo(String username) {
        log.info("根据GitHub用户名获取用户信息:" + username);
        try {
            Developer developer = developerService.getUserInfo(username);
            // 调用开发者服务层的获取用户信息方法
            return Result.success(developer);
        } catch (Exception e) {
            log.error("获取用户信息失败：", e);
            return Result.error("获取用户信息失败");
        }
    }

}
