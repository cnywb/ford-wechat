package com.ford.wechat.controller.channel.vo;

import lombok.Data;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2017
 * All rights reserved. 2017-05-04 13:55
 * </p>
 *
 * @author zhaoliang
 * @version 1.0
 */
@Data
public class WeChannelPageResp {

    /**
     * 主键
     */
    private Long id;
    /**
     * 服务商
     */
    private String dealerCode;
    /**
     * 服务代码
     */
    private String code;
    /**
     * 渠道名称
     */
    private String name;
    /**
     * 小区
     */
    private String smallArea;
    /**
     * 大区
     */
    private String bigArea;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 地址
     */
    private String address;
    /**
     * 邮编
     */
    private String zipCode;
    /**
     * 类型 经销商 意见领袖 活动
     */
    private Integer type;

    /**
     * 经销商代码
     */
    private String dealerServiceCode;

    /**
     * 备注
     */
    private String remark;

    private String url;

}
