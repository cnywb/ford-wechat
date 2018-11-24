package com.ford.wechat.controller.coupon.vo;

import lombok.Data;

/**
 * Created by zhaoliang on 2017/8/31.
 */
@Data
public class CouponDmsPageResp {

    protected Long id;


    /**批处理批次号*/
    private String batchNo;

    /**
     * 关联购买订单
     */
   // protected Coupon order;

    /**
     * 唯一标示
     */
    protected String seqNo;
    /**
     * 电子账户名称
     */
    protected String name;
    /**
     * VIN码
     */
    protected String vin;
    /**
     * 活动编号
     */
    protected String campaignCode;
    /**
     * 总金额
     */
    protected String amount;
    /**
     * 已使用金额 默认0
     */
    protected String useAmount;
    /**
     * 开始生效日期
     */
    protected String validBeginDate;
    /**
     * 结束生效日期
     */
    protected String validEndDate;
    /**
     * 目标（责任）经销商服务代码
     */
    protected String targetDealer;
    /**
     * 使用经销商服务代码
     */
    protected String useDealer;
    /**
     * 门槛金额
     */
    protected String lowestAmount;
    /**
     * 抵扣限额
     */
    protected String limitDeduct;

    /**
     * 是否发送DMS
     */
    protected String sendDmsStatus;

    /**
     * 发送DMS的次数，超过3次就不做查询发送
     */
    protected Integer sendCount;

    /**
     * 发送DMS的响应结果。
     */
    protected String dmsResult;

    /**
     * 是否取消
     */
    protected String cancel ;

    /**
     * 发送历史记录
     */
    protected String sendHistory;

    /**
     * 短信是否已发送
     */
    protected String sendSms;

    /**
     * 认证车主手机号
     */
    protected String customerMobile;

    /**
     * 活动类型
     */
    protected String periodType;

    /**
     * 份数
     */
    protected Integer copies;

    /**
     * 标签区分 微商城跟618活动
     */
    protected String tag;
}
