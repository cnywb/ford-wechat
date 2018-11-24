/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageHandleResp.java
 */

package com.ford.wechat.pc.controller.external.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述: 外部公告发起相应对象
 *
 * @author Yvan
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class MessageHandleResp implements Serializable {
    // code 1：发布消息成功，0，失败（message为失败原因）
    private int code = 1;
    // message为失败原因
    private String message = "发布消息成功";
    // 消息发布后的唯一键
    private String uniqueId;
}