/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * ComplainReq.java
 */

package com.ford.wechat.pc.controller.complain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述: 投诉请求对象
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class ComplainReq {

    private Long id;
    /**
     * 微信唯一标识
     */
    private String openId;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 客户手机号
     */
    private String customerMobile;
    /**
     * 客户VIN码
     */
    private String customerVin;
    /**
     * 车牌号
     */
    private String license;
    /**
     * 联系地址
     */
    private String address;
    /**
     * 行驶里程
     */
    private Long exerciseMileage;
    /**
     * 车型
     */
    private String carModel;
    /**
     * 投诉对象
     */
    private String complainObject;
    /**
     * 投诉原因
     */
    private String complainReason;
    /**
     * 涉及经销商
     */
    private String involveDealer;
    /**
     * 事件描述
     */
    private String incidentDesc;
}