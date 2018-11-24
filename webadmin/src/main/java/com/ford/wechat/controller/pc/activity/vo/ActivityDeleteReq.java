/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * ActivityDeleteReq.java
 */

package com.ford.wechat.controller.pc.activity.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 描述: 删除活动
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class ActivityDeleteReq {

    private List<Long> ids;
}