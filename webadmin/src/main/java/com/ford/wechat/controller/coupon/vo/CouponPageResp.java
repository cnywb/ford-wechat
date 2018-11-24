package com.ford.wechat.controller.coupon.vo;

import lombok.Data;
import java.util.Date;

/**
 * Created by zhaoliang on 2017/8/27.
 */
@Data
public class CouponPageResp {

    private Long id;

    /**
     * 时间批次
     */
    private String dateNo ;

    /**
     * 活动代码
     */
    private String projectCode;

    /**
     * 活动名称
     */
    private String projectName;

    /**
     * 代金券编号
     */
    private String couponNo;

    /**
     * 金额
     */
    private Integer amount;

    /**
     * 门槛金额
     */
    private Integer lowestAmount;

    /**
     * 目标（责任）经销商服务代码
     */
    private String dealerCode;

    /**
     * 绑定VIN
     */
    private String vin;

    /**
     * 绑定OPENID
     */
    private String openId;

    /**
     * 会员编号
     */
    private String memberNo;

    /**
     * 会员手机号
     */
    private String mobile;

    /**
     * 状态  0 未领用  1 已领用
     */
    private int status;

    /**
     * 批处理状态  0 未处理  1 已处理
     */
    private int batchStatus;

    /**
     * 代金券开始日期 yyyy-MM-dd
     */
    private String startTime;

    /**
     * 代金券失效日期 yyyy-MM-dd
     */
    private String expiredTime;

    /**
     * 祝福语
     */
    private String wishing;
}
