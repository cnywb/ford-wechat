/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * QAInfoEntity.java 2017-05-16 19:35:16
 */
package com.ford.wechat.controller.pc;
import io.dabing.common.grid.GridPage;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：分页查询请求
 *
 * Created by ziv.hung on 2017-05-16 19:35:16.
 * @since 1.0
*/
@Data
@NoArgsConstructor
public class EsSearchInfoDeleteReq {
    private String esId;
    private String type;
}