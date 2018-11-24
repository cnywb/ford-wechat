/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageContentEntityDeleteReq.java
 */
package com.ford.wechat.controller.pc.message.vo;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：MessageContentEntityDeleteReq 逻辑删除请求对象
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class MessageContentEntityDeleteReq {

    private List<Long> ids;

}
