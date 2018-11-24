/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * InfoEntityVo.java
 */

package com.ford.wechat.pc.controller.info.search.vo;

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
public class EsSearchQAInfoPageResp {

    private Long id;
    private String question;
    private String answer;
    /**
     * 要高亮显示的问题
     */
    private String highLightQuestion;
    /**
     * 要高亮显示的答案
     */
    private String highLightAnswer;

    private String createDate;
}