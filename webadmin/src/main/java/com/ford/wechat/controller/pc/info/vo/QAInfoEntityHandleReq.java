/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * QAInfoEntity.java 2017-05-16 19:35:16
 */
package com.ford.wechat.controller.pc.info.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：对象创建或修改请求返回
 *
 * Created by ziv.hung on 2017-05-16 19:35:16.
 * @since 1.0
*/
@Data
@NoArgsConstructor
public class QAInfoEntityHandleReq {

    private Long id;
    private String question;
    private String answer;
    private String tags;
    private boolean indexed;

}