/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * CFOrderDms.java
 */

package com.ford.wechat.entity.coupon;

import com.ford.wechat.entity.AuditEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * 描述: 下发DMS
 *
 * @author Neel
 * @since 1.0
 */
@Data
@Entity
@Table(name = "WE_COUPON_SEND_DMS")
public class CouponDms extends AuditEntity {

    public static final String SEND_DMS_WAITING = "待发送";
    public static final String SEND_DMS_SENDING = "发送中";
    public static final String SEND_DMS_SENDED = "已发送";

    public static final String SEND_SMS_WAITING = "待发送";
    public static final String SEND_SMS_SENDED = "已发送";
    public static final String SEND_SMS_FAIL = "发送失败";


    public static final String TAG_AUTH_COUPON = "AUTH_COUPON";


    public static final String PERIOD_TYPE_COUPON = "COUPON";

    /**
     * 物理主键
     */
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WE_COUPON_SEND_DMS")
    @SequenceGenerator(name = "SEQ_WE_COUPON_SEND_DMS", allocationSize = 1, sequenceName = "SEQ_WE_COUPON_SEND_DMS")
    protected Long id;


    /**批处理批次号*/
    @Column(name="BATCH_NO")
    private String batchNo;

    /**
     * 关联购买订单
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    protected Coupon order;

    /**
     * 唯一标示
     */
    @Column(name = "SEQ_NO")
    protected String seqNo;
    /**
     * 电子账户名称
     */
    @Column(name = "NAME")
    protected String name;
    /**
     * VIN码
     */
    @Column(name = "VIN")
    protected String vin;
    /**
     * 活动编号
     */
    @Column(name = "CAMPAIGN_CODE")
    protected String campaignCode;
    /**
     * 总金额
     */
    @Column(name = "AMOUNT")
    protected String amount;
    /**
     * 已使用金额 默认0
     */
    @Column(name = "USE_AMOUNT")
    protected String useAmount;
    /**
     * 开始生效日期
     */
    @Column(name = "VALID_BEGIN_DATE")
    protected String validBeginDate;
    /**
     * 结束生效日期
     */
    @Column(name = "VALID_END_DATE")
    protected String validEndDate;
    /**
     * 目标（责任）经销商服务代码
     */
    @Column(name = "TARGET_DEALER")
    protected String targetDealer;
    /**
     * 使用经销商服务代码
     */
    @Column(name = "USE_DEALER")
    protected String useDealer = null;
    /**
     * 门槛金额
     */
    @Column(name = "LOWEST_AMOUNT")
    protected String lowestAmount;
    /**
     * 抵扣限额
     */
    @Column(name = "LIMIT_DEDUCT")
    protected String limitDeduct;

    /**
     * 是否发送DMS
     */
    @Column(name = "SEND_DMS_STATUS")
    protected String sendDmsStatus = this.SEND_DMS_WAITING;

    /**
     * 发送DMS的次数，超过3次就不做查询发送
     */
    @Column(name = "SEND_COUNT")
    protected Integer sendCount;

    /**
     * 发送DMS的响应结果。
     */
    @Column(name = "DMS_RESULT")
    protected String dmsResult;

    /**
     * 是否取消
     */
    @Column(name = "CANCEL")
    protected String cancel = "0";

    /**
     * 发送历史记录
     */
    @Column(name = "SEND_HISTORY")
    protected String sendHistory;

    /**
     * 短信是否已发送
     */
    @Column(name = "SEND_SMS")
    protected String sendSms = this.SEND_SMS_WAITING;

    /**
     * 认证车主手机号
     */
    @Column(name = "CUSTOMER_MOBILE")
    protected String customerMobile;

    /**
     * 活动类型
     */
    @Column(name = "PERIOD_TYPE")
    protected String periodType;

    /**
     * 份数
     */
    @Column(name = "COPIES")
    protected Integer copies;

    /**
     * 标签区分 微商城跟618活动
     */
    @Column(name = "TAG")
    protected String tag;
}