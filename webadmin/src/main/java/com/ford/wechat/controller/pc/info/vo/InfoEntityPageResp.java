/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * InfoEntity.java 2017-05-16 19:31:48
 */
package com.ford.wechat.controller.pc.info.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：分页查询请求返回
 *
 * Created by ziv.hung on 2017-05-16 19:31:48.
 * @since 1.0
*/
@Data
@NoArgsConstructor
public class InfoEntityPageResp {


    private Long id;

    private String infoType;

    private String title;

    private String iconUrl;

    private String digest;

    private String tags;

    private String content;

    private String sourceHref;

    private boolean indexed;

    private String esId;
}