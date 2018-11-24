package com.ford.wechat.batch.model.verify;/**
 * Created by jovi on 26/03/2017.
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
 * All rights reserved. 2017-03-26 15:16
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Data
public class DealerVerify {
    /**
     * vin码
     */
    private String vin;
    /**
     * openid
     */
    private String openId;
    /**
     * 经销商代码
     */
    private String dealerCode;
    /**
     * 经销商名称
     */
    private String dealerName;
    /**
     * 认证时间
     */
    private String dcrtDate;
    /**
     * 大区
     */
    private String bigArea;
}
