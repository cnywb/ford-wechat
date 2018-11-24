/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * InfoEntityVo.java
 */

package com.ford.wechat.controller.pc;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述:
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class EsSearchInfoPageResp {

    private String esId;

    private String type;

    private String content;

    /**
     * 要高亮显示的标题
     */
    private String highLightTitle;
    /**
     * 要高亮显示的摘要
     */
    private String highLightDigest;

}