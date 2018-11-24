package com.ford.wechat.controller.members.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhaoliang on 2017/5/25.
 */
@Data
public class WeUnauthApplyPageResp {
    private Long id;

    /**申请人姓名*/
    private String name;

    /**申请人VIN码*/
    private String vin;

    /**申请人手机号*/
    private String mobile;

    /**申请人地址*/
    private String address;

    /**微信唯一标识*/
    private String openId;

    /** 原因*/
    private String reason;

    /**涉事经销商*/
    private String dealer;

    /**申请来源*/
    private String source;

    /**审核状态 0.待审核  1.审核中  2.审核完成*/
    private Integer assessStatus;

    /**审核结果 0.未通过  1.通过*/
    private Integer assessResult;

    /**申请时间*/
    private Date applyDate;

    /**处理时间*/
    private Date completedDate;

    /**跑批标识*/
    private Integer jobStatus;

    /**行驶证主键编号*/
    private Long licenseImgId;

    /**行驶证图片地址*/
    private String licenseUrl;

    /**原认证姓名*/
    private String oldName;

    /**原认证手机*/
    private String oldMobile;

    /**原微信唯一标识*/
    private String oldOpenId;

    /**原渠道代码*/
    private String oldChannelCode;

    /**认证时间*/
    private Date dcrtDate;

    /**申请类型 0. 解绑  1.更改手机*/
    private Integer applyType = 0;

    /**问题描述*/
    private String memo;
    /**问题标题*/
    private String title;
    /**审核理由*/
    private String assessMemo;
}
