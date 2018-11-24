/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * ActivityHandleReq.java
 */
package com.ford.wechat.controller.pc.activity.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：修改新增活动
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class ActivityHandleReq {

    private Long id;
    private String name;
    private String imageUrl;
    private String href;
    private Integer sortNo;
}
