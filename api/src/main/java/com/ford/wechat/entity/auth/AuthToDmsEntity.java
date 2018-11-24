package com.ford.wechat.entity.auth;/**
 * Created by jovi on 23/05/2017.
 */

import lombok.Data;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-05-23 19:05
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Data
public class AuthToDmsEntity {
    /**
     * 日期批次
     */
    private String dateNo;
    /**
     * 状态
     */
    private String status;
    /**
     * 批次号
     */
    private String batchNo;
    /**
     * VIN码
     */
    private String vin;
    /**
     * 微信唯一标识
     */
    private String openId;
    /**
     * 手机号
     */
    private String verifyMobile;
    /**
     * 认证状态
     */
    private String verify;
    /**
     * 关注状态
     */
    private String follow;
    /**
     * 经销商服务代码
     */
    private String dealerServiceCode;
    /**
     * 认证来源
     */
    private String verifyChannel;
    /**
     * 认证时间
     */
    private String verifyDate;
    /**
     * 是否经销商扫码
     */
    private String dealerScan;
    /**
     * 保留字段
     */
    private String remark2;
    /**
     * 发送时间
     */
    private String sendDate;
    /**
     * 日期
     */
    private String updateDate;
}
