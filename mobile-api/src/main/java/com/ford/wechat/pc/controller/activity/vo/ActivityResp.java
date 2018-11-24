/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * ActivityResp.java
 */

package com.ford.wechat.pc.controller.activity.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述: 活动数据返回
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class ActivityResp {
    private Long id;
    private String name;
    private String imageUrl;
    private String href;
}