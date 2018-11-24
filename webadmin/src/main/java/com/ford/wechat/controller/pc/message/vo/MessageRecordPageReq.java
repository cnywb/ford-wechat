/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageRecordPageReq.java
 */
package com.ford.wechat.controller.pc.message.vo;

import io.dabing.common.grid.GridPage;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：MessageRecordPageReq 数据分页查询请求对象
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class MessageRecordPageReq {

    private GridPage page;
    private String vin;
    private String mobile;
    private String msgClass;
    private String msgType;
    private String pushChannel;

}
