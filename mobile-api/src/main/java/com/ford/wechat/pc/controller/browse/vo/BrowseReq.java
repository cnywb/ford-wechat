/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * BrowseReq.java
 */

package com.ford.wechat.pc.controller.browse.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述: 菜单点击，公告预览记录请求对象
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class BrowseReq {
    private Long id;
    private String name;
    private Long sort;
    private String typeName;
}