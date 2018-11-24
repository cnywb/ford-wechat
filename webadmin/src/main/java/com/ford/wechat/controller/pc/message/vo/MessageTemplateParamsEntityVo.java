/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageTemplateParamsEntityVo.java
 */

package com.ford.wechat.controller.pc.message.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述: 消息模板参数对象
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class MessageTemplateParamsEntityVo {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 参数key值，用于模板内容当中的占位符,如：name、amount
     */
    private String paramKey;

    /**
     * key描述，用于描述这个key的含义
     */
    private String keyDesc;
}