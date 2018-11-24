/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageListReq.java
 */

package com.ford.wechat.pc.controller.message.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述: 列表查询请求对象
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class MessageDetailReq {

    /**
     * id 阅读的公告ID
     */
    private Long id;
    /**
     * openId
     */
    private String openId;
    /**
     * vin码
     */
    private String vin;
    /**
     * 手机号
     */
    private String mobile;
}