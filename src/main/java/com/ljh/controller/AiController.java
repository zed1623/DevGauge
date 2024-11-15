package com.ljh.controller;

import com.ljh.manager.AiManager;
import com.ljh.manager.ApiCallCountManager;
import com.ljh.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Api(tags = "Ai相关接口")
@RequestMapping("/api/ai")
public class AiController {

    @Autowired
    private AiManager aiManager;

    @Autowired
    private ApiCallCountManager apiCallCountManager;

    /**
     * 通过AI模型进行聊天
     * @param systemMessages 系统消息
     * @param userMessages 用户消息
     * @param stream 是否开启流式响应
     * @param temperature 模型的温度设置，决定生成内容的随机性
     * @return 结果
     */
    @PostMapping("/chatAi")
    @ApiOperation(value = "AI聊天接口")
    public Result<String> chatAi(
            @RequestParam String systemMessages,
            @RequestParam String userMessages,
            @RequestParam(defaultValue = "false") Boolean stream,
            @RequestParam(defaultValue = "0.7") Float temperature) {
        // 增加接口调用次数
        apiCallCountManager.incrementCount("chatAi");

        try {
            // 调用AiManager执行请求
            String response = aiManager.doRequest(systemMessages, userMessages, stream, temperature);
            System.out.println("ai回答: " + response);
            return Result.success(response);  // 返回成功的结果
        } catch (Exception e) {
            log.error("AI生成回答失败", e);
            return Result.error("AI生成回答错误");  // 返回错误的结果
        }
    }
}
