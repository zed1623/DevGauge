package com.ljh.controller;

import com.ljh.manager.ApiCallCountManager;
import com.ljh.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/apiCallCount")
@Slf4j
@Api(tags = "接口调用次数相关接口")
public class ApiCallCountController {

    /**
     * 获取某个接口的调用次数
     *
     * @param apiName
     * @return
     */
    @ApiOperation(value = "获取某个接口的调用次数")
    @GetMapping("/getApiCallCount/{apiName}")
    public Result<Integer> getApiCallCount(@PathVariable String apiName) {
        int count = ApiCallCountManager.getApiCallCount(apiName);  // 调用管理类获取接口调用次数
        return Result.success(count);
    }

    /**
     * 获取所有接口的调用次数
     *
     * @return
     */
    @ApiOperation(value = "获取所有接口的调用次数")
    @GetMapping("/getAllApiCallCounts")
    public Result<Map<String, Integer>> getAllApiCallCounts() {
        Map<String, Integer> result = ApiCallCountManager.getAllApiCallCounts().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().get()));
        return Result.success(result);
    }
}
