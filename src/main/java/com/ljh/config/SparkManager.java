package com.ljh.config;

import io.github.briqt.spark4j.SparkClient;
import io.github.briqt.spark4j.constant.SparkApiVersion;
import io.github.briqt.spark4j.model.SparkMessage;
import io.github.briqt.spark4j.model.SparkSyncChatResponse;
import io.github.briqt.spark4j.model.request.SparkRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SparkManager {
    @Resource
    private SparkClient sparkClient;




    /**
     * AI生成问题的预设条件
     */
    public static final String PRECONDITION = "你是一名数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容：\n" +
            "分析需求：\n" +
            "分析指定的 GitHub 项目或开发者，以确定项目的重要性或开发者的技术能力。\n" +
            "原始数据：\n" +
            "项目数据：\n" +
            "{项目名称, 项目成员数量, 总提交数, 代码总行数, 项目介绍, 项目所有者用户名, 仓库链接, 星标数量, Fork 数量, 开放 issue 数量, 创建时间, 最后更新时间, 重要性评分, 关联项目}\n" +
            "或\n" +
            "开发者数据：\n" +
            "{GitHub 用户名, 全名, 邮箱, 头像链接, 博客链接, GitHub 个人主页链接, 个人介绍, 国家, 国家置信度, 技术领域, 技术能力评价分数, 账号创建时间, 最后更新时间}\n" +
            "请根据这些信息，按照以下格式生成内容（除此之外不要输出任何多余的开头、结尾或注释）：\n" +
            "【【【【【\n" +
            "{详细的数据分析结论，越详细越好，分析项目的重要性或开发者的技术能力，不要生成多余的注释}\n" +
            "最终格式是：【【【【【【【【【【分析结论";



    /**
     * 向星火AI发送请求
     *
     * @param content
     * @return
     */
    public String sendMesToAIUseXingHuo(final String content) {
        // 消息列表，可以在此列表添加历史对话记录
        List<SparkMessage> messages = new ArrayList<>();
        messages.add(SparkMessage.systemContent(PRECONDITION));
        messages.add(SparkMessage.userContent(content));
        // 构造请求
        SparkRequest sparkRequest = SparkRequest.builder()
                // 消息列表
                .messages(messages)
                // 模型回答的tokens的最大长度，非必传，默认为2048
                .maxTokens(2048)
                // 结果随机性，取值越高随机性越强，即相同的问题得到的不同答案的可能性越高，非必传，取值为[0,1]，默认为0.5
                .temperature(0.2)
                // 指定请求版本
                .apiVersion(SparkApiVersion.V3_5)
                .build();
        // 同步调用
        SparkSyncChatResponse chatResponse = sparkClient.chatSync(sparkRequest);
        String responseContent = chatResponse.getContent();
        log.info("星火AI返回的结果{}", responseContent);
        return responseContent;
    }
}