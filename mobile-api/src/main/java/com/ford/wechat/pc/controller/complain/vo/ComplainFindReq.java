/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * ComplainFindReq.java
 */

package com.ford.wechat.pc.controller.complain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述: 投诉处理结果查询
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class ComplainFindReq {
    /**
     * 微信唯一标识
     */
    private String openId;
}