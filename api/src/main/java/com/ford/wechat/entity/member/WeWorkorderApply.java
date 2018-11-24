package com.ford.wechat.entity.member;

import com.ford.wechat.entity.VersionEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhaoliang on 2017/5/22.
 */
@Data
@Entity
@Table(name = "WE_WORKORDER_APPLY")
public class WeWorkorderApply extends VersionEntity {
    /**
     * 处理结果-待处理
     */
    public static final Integer COMPLETED_RESULT_PENDING = 0;
    /**
     * 处理结果-正在处理
     */
    public static final Integer COMPLETED_RESULT_PROCESSING = 1;
    /**
     * 处理结果-处理成功
     */
    public static final Integer COMPLETED_RESULT_SUCCESS = 2;
    /**
     * 处理结果-处理失败
     */
    public static final Integer COMPLETED_RESULT_FAIL = 3;


    /**
     * 审核结果-未通过
     */
    public static final Integer ASSESS_RESULT_FAIL = 2;
    /**
     * 审核结果-通过
     */
    public static final Integer ASSESS_RESULT_PASS = 1;

    /**
     * 审核结果-关闭
     */
    public static final Integer ASSESS_RESULT_CLOSE = 3;

    /**
     * 审核状态-待审核
     */
    public static final Integer ASSESS_STATUS_NOT_AUDITED = 0;
    /**
     * 审核状态-审核完成
     */
    public static final Integer ASSESS_STATUS_AUDITED = 2;


    /**
     * 审核类型-解绑
     */
    public static final Integer APPLY_TYPE_UNBIND = 0;
    /**
     * 审核类型-手机号更改
     */
    public static final Integer APPLY_TYPE_MOBILE = 1;


    /**主键*/
    @javax.persistence.Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "SEQ_WE_WORKORDER_APPLY")
    @SequenceGenerator(name = "SEQ_WE_WORKORDER_APPLY",allocationSize=1,sequenceName = "SEQ_WE_WORKORDER_APPLY")
    private Long id;

    /**申请人姓名*/
    @Column(name = "NAME")
    private String name;


    /**申请人VIN码*/
    @Column(name = "VIN")
    private String vin;

    /**申请人手机号*/
    @Column(name = "MOBILE")
    private String mobile;

    /**申请人地址*/
    @Column(name = "ADDRESS")
    private String address;

    /**微信唯一标识*/
    @Column(name = "OPEN_ID")
    private String openId;

    /** 问题标题*/
    @Column(name = "TITLE")
    private String title;

    /** 原因*/
    @Column(name = "REASON")
    private String reason;

    /**涉事经销商*/
    @Column(name = "DEALER")
    private String dealer;

    /**申请来源*/
    @Column(name = "SOURCE")
    private String source;

    /**审核状态 0.待审核  1.审核中  2.审核完成*/
    @Column(name = "ASSESS_STATUS")
    private Integer assessStatus;


    /**审核结果 2.未通过  1.通过  0.显示空 3.关闭*/
    @Column(name = "ASSESS_RESULT")
    private Integer assessResult;


    /**申请时间*/
    @Column(name = "APPLY_DATE")
    private Date applyDate;

    /**处理时间*/
    @Column(name = "COMPLETED_DATE")
    private Date completedDate;

    /**跑批标识*/
    @Column(name = "JOB_STATUS")
    private Integer jobStatus = 0;


    /**行驶证主键编号*/
    @Column(name = "LICENSE_IMG_ID")
    private Long licenseImgId;

    /**行驶证图片地址*/
    @Column(name = "LICENSE_URL")
    private String licenseUrl;

    /**原认证姓名*/
    @Column(name = "OLD_NAME")
    private String oldName;

    /**原认证手机*/
    @Column(name = "OLD_MOBILE")
    private String oldMobile;

    /**原微信唯一标识*/
    @Column(name = "OLD_OPEN_ID")
    private String oldOpenId;

    /**原渠道代码*/
    @Column(name = "OLD_CHANNEL_CODE")
    private String oldChannelCode;

    /**认证时间*/
    @Column(name = "DCRT_DATE")
    private Date dcrtDate;

    /**审核类型 0. 解绑  1.更改手机  2. 无法认证  3. 其他 2 3 暂时不用*/
    @Column(name = "APPLY_TYPE")
    private Integer applyType;

    /**问题描述*/
    @Column(name = "MEMO")
    private String memo;

    /**用户编号*/
    @Column(name = "USER_ID")
    private Long userId;

    /**审核原因*/
    @Column(name = "ASSESS_MEMO")
    private String assessMemo;

}
