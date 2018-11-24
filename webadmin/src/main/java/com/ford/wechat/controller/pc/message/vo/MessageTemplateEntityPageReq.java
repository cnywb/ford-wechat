/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageTemplateEntityPageReq.java
 */
package com.ford.wechat.controller.pc.message.vo;

import io.dabing.common.grid.GridPage;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：MessageTemplateEntityPageReq 数据分页查询请求对象
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class MessageTemplateEntityPageReq {

    private GridPage page;

    private String code;
    private String name;
    private String templateType;
    private String useChannel;
}
