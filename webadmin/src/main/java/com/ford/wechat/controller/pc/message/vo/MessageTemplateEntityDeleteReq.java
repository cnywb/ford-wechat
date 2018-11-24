/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageTemplateEntityDeleteReq.java
 */
package com.ford.wechat.controller.pc.message.vo;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：MessageTemplateEntityDeleteReq 逻辑删除请求对象
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class MessageTemplateEntityDeleteReq {

    private List<Long> ids;

}
