package com.ljh.config;

import io.github.briqt.spark4j.SparkClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@ConfigurationProperties(prefix = "xunfei.client")
@Data
public class XingHuoConfig {
    // AppID，即开放平台分配给开发者的AppID
    private String appid;
    // AppSecret，即开放平台分配给开发者的AppSecret
    private String apiSecret;
    // 用于API调用的密钥，由开发者填写
    private String apiKey;


    @Bean
    //定义一个SparkClient函数，返回一个SparkClient对象
    public SparkClient sparkClient() {
        //创建一个SparkClient对象
        SparkClient sparkClient = new SparkClient();
        //将apiKey赋值给SparkClient的apiKey属性
        sparkClient.apiKey = apiKey;
        //将apiSecret赋值给SparkClient的apiSecret属性
        sparkClient.apiSecret = apiSecret;
        //将appid赋值给SparkClient的appid属性
        sparkClient.appid = appid;
        //返回SparkClient对象
        return sparkClient;
    }
}
