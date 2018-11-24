/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageTemplateParamGetReq.java
 */

package com.ford.wechat.controller.pc.message.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述: 模板参数获取请求对象
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class MessageTemplateParamGetReq {
    private Long templateId;
}