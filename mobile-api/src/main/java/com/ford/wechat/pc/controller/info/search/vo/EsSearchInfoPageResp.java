/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * InfoEntityVo.java
 */

package com.ford.wechat.pc.controller.info.search.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 描述:
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class EsSearchInfoPageResp {

    private Long id;
    private String infoType;
    private String title;
    private String iconUrl;
    private String digest;
    private String sourceHref;
    private String createDate;
    /**
     * 要高亮显示的标题
     */
    private String highLightTitle;
    /**
     * 要高亮显示的摘要
     */
    private String highLightDigest;
}