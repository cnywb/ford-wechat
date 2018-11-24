/*
 * Copyright (c)  2016, dabing.io
 * All rights reserved.
 * FindAllReq.java 2017-05-07 上午11:45
 */

package com.ford.wechat.pc.controller.menu.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述:查找首页图标
 *
 * @author yangkui create on 2017-05-07.
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class FindAllReq {

    private String openId;
    private String vin;
}