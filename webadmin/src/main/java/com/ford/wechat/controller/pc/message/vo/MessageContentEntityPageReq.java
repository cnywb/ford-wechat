/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageContentEntityPageReq.java
 */
package com.ford.wechat.controller.pc.message.vo;

import io.dabing.common.grid.GridPage;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：MessageContentEntityPageReq 数据分页查询请求对象
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class MessageContentEntityPageReq {

    private GridPage page;
    private String vin;
    private String mobile;
    private String msClass;
    private String msgType;
    private String pushChannel;
}
