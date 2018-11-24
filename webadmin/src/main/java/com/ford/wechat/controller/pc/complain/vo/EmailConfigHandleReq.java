/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * EmailConfigHandleReq.java
 */

package com.ford.wechat.controller.pc.complain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述: 邮箱管理
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class EmailConfigHandleReq {

    private String sendEmail;
    private String ccEmail;

}