/*
 * Copyright (c)  2017
 * All rights reserved.
 * FindIndexMenuReq.java 2017-05-17 上午11:11
 */

package com.ford.wechat.pc.controller.menu.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述:查询首页菜单图标
 *
 * @author yangkui create on 2017-05-17 上午11:11.
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class FindIndexMenuReq {
    private String openId;
    private String vin;
}