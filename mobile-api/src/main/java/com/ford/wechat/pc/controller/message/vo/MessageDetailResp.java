/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageListResp.java
 */

package com.ford.wechat.pc.controller.message.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述: 内容列表响应对象
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class MessageDetailResp {

    private Long id;
    private String title;
    private String publishTime;
    private String content;
}