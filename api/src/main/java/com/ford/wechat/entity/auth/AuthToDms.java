package com.ford.wechat.entity.auth;/**
 * Created by jovi on 19/05/2017.
 */

import com.ford.wechat.entity.AuditEntity;
import com.ford.wechat.entity.VersionEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-05-19 15:33
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Data
@Entity
@Table(name="WE_AUTH_TO_DMS")
public class AuthToDms extends AuditEntity {
    /**
     * 是否经销商扫码-是
     */
    public static final int DEALER_SCAN_IS_DEALER = 1;
    /**
     * 是否经销商扫码-否
     */
    public static final int DEALER_SCAN_NOT_DEALER = 0;
    /**
     * 是否关注-是
     */
    public static final int FOLLOW_FOLLOW = 1;
    /**
     * 是否关注-否
     */
    public static final int FOLLOW_NOT_FOLLOW = 0;
    /**
     * 认证状态-认证
     */
    public static final int VERIFY_AUTH = 1;
    /**
     * 认证状态-解绑
     */
    public static final int VERIFY_UNAUTH = 2;
    /**
     * 发送dms状态-不发送
     */
    public static final int SEND_DMS_NOT=0;
    /**
     * 发送dms状态-待发送
     */
    public static final int SEND_DMS_PENDING=1;

    /**
     * 发送dms状态-成功
     */
    public static final int SEND_DMS_SUCCESS=3;

    /**
     * 发送dms状态-失败
     */
    public static final int SEND_DMS_FAIL=2;

    /**
     * 发送dms结果-发送成功
     */
    public static final String SEND_DMS_RESULT_SUCCESS="发送成功";

    /**
     * 主键
     */
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WE_AUTH_TO_DMS_ID")
    @SequenceGenerator(name = "SEQ_WE_AUTH_TO_DMS_ID", allocationSize = 1, sequenceName = "SEQ_WE_AUTH_TO_DMS_ID")
    private Long id;
    /**
     * 批次号
     */
    @Column(name = "BATCH_NO")
    private String batchNo;
    /**
     * 用户ID
     */
    @Column(name = "USER_ID")
    private Long userId;
    /**
     * 车主姓名
     */
    @Column(name = "NAME")
    private String name;
    /**
     * 车主电话
     */
    @Column(name = "MOBILE")
    private String mobile;
    /**
     * VIN码
     */
    @Column(name = "VIN")
    private String vin;
    /**
     * 微信唯一标识
     */
    @Column(name = "OPEN_ID")
    private String openId;
    /**
     * 认证时间
     */
    @Column(name = "DCRT_DATE")
    private Date dcrtDate;
    /**
     * 渠道类型
     */
    @Column(name = "CHANNEL_TYPE")
    private Integer channelType;
    /**
     * 渠道代码
     */
    @Column(name = "CHANNEL_CODE")
    private String channelCode;
    /**
     * 渠道名称
     */
    @Column(name = "CHANNEL_NAME")
    private String channelName;
    /**
     * 经销商代码
     */
    @Column(name = "DEALER_SERVICE_CODE")
    private String dealerServiceCode;
    /**
     * 经销商名称
     */
    @Column(name = "DEALER_NAME")
    private String dealerName;
    /**
     * 日期批次
     */
    @Column(name = "DATE_NO")
    private String dateNo;
    /**
     * 大区
     */
    @Column(name = "BIG_AREA")
    private String bigArea;

    /**
     * 小区
     */
    @Column(name = "SMALL_AREA")
    private String smallArea;

    /**
     * 认证状态
     */
    @Column(name = "VERIFY")
    private Integer verify;

    /**
     * 关注状态
     */
    @Column(name = "FOLLOW")
    private Integer follow;

    /**
     * 有效性
     */
    @Column(name = "VALID")
    private Integer valid;

    /**
     * 是否经销商扫码
     */
    @Column(name = "DEALER_SCAN")
    private Integer dealerScan;

    /**
     * 备用字段
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 发送dms时间
     */
    @Column(name = "SEND_DMS_DATE")
    private Date sendDmsDate;

    /**
     * 发送dms状态
     */
    @Column(name = "SEND_DMS_STATUS")
    private Integer sendDmsStatus;

    /**
     * 解绑时间
     */
    @Column(name = "UNAUTH_DATE")
    private Date unauthDate;

    /**
     * 发送dms结果
     */
    @Column(name = "SEND_DMS_RESULT")
    private String sendDmsResult;

    /**
     * 最终时间（如果为认证，则为认证时间;如果为解绑，则为解绑时间）
     */
    @Column(name = "FINAL_DATE")
    private Date finalDate;

}
