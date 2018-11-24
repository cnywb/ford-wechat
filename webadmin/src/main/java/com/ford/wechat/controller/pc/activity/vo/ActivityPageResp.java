/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * ActivityPageResp.java
 */
package com.ford.wechat.controller.pc.activity.vo;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：ActivityPageResp 数据分页查询响应对象
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class ActivityPageResp {

    private Long id;
    private String name;
    private String imageUrl;
    private String href;
    private Integer sortNo;
}
