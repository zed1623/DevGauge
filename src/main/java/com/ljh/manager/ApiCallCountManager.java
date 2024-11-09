package com.ljh.manager;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentHashMap;

public class ApiCallCountManager {
    private static final ConcurrentHashMap<String, AtomicInteger> apiCallCountMap = new ConcurrentHashMap<>();

    // 增加某个接口的调用次数
    public static void incrementCount(String apiName) {
        apiCallCountMap.computeIfAbsent(apiName, key -> new AtomicInteger(0)).incrementAndGet();
    }

    // 获取某个接口的调用次数
    public static int getApiCallCount(String apiName) {
        return apiCallCountMap.getOrDefault(apiName, new AtomicInteger(0)).get();
    }

    // 重置某个接口的调用次数
    public static void resetApiCallCount(String apiName) {
        AtomicInteger count = apiCallCountMap.get(apiName);
        if (count != null) {
            count.set(0);
        }
    }

    // 获取所有接口的调用次数
    public static ConcurrentHashMap<String, AtomicInteger> getAllApiCallCounts() {
        return apiCallCountMap;
    }
}
