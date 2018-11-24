/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * UserInfoReq.java
 */

package com.ford.wechat.pc.controller.information.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述: 车主数据更新请求对象
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class CarInfoQueryResp {

    /**
     * 购车日期
     */
    private String buyDate;

    /**
     * 购车经销商-NAME
     */
    private String buyDealerName;

    /**
     * 维修经销商-NAME
     */
    private String repairDealerName;

    /**
     * 车型
     */
    private String model;

    /**
     * 车款
     */
    private String style;

    /**
     * 颜色
     */
    private String color;

    /**驾照号码*/
    private String license;
}