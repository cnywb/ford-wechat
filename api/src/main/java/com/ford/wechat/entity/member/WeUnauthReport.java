package com.ford.wechat.entity.member;/**
 * Created by jovi on 29/05/2017.
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
 * All rights reserved. 2017-05-29 20:20
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Data
public class WeUnauthReport {
    /**
     * VIN码
     */
    private String vin;

    /**
     * 微信唯一标识
     */
    private String openId;

    /**
     * 解绑时间
     */
    private String unauthDate;

    /**
     * 经销商服务代码
     */
    private String dealerServiceCode;

    /**
     * 经销商名称
     */
    private String dealerName;

    /**
     * 大区
     */
    private String bigArea;
}
