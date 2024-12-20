package com.ljh.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeveloperPageQueryDTO implements Serializable {

    // 页码
    private int page;

    // 每页记录数
    private int pageSize;

}
