package com.ford.wechat.entity.user;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wanglijun on 16/11/25.
 */
@Data
@Entity
@Table(name = "car_owner_authen_status")
public class CarOwnerAuthenStatus implements java.io.Serializable {

    /**
     * 审核方式-自动
     */
    public static String AUTH_WAY_AUTO = "自动";
    /**
     * 审核方式-手动
     */
    public static String AUTH_WAY_MANUAL = "手动";
    /**
     * vin码来源-DMS
     */
    public static String VIN_SOURCE_DMS = "DMS";
    /**
     * vin码来源-主机厂
     */
    public static String VIN_SOURCE_FACTORY = "主机厂";
    /**
     * vin码来源-重复认证
     */
    public static String VIN_SOURCE_AUTH = "重复认证";
    /**
     * 审核结果-未通过
     */
    public static Long AUTH_RESULT_FAIL = 2L;
    /**
     * 审核结果-通过
     */
    public static Long AUTH_RESULT_PAAS = 1L;
    /**
     * 审核状态-未审核
     */
    public static Long AUTH_STATE_NOT_AUDITED = 0L;
    /**
     * 审核状态-已审核
     */
    public static Long AUTH_STATE_AUDITED = 1L;


    private static final long serialVersionUID = 823471781509072829L;



    /**主键*/
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "SEQ_CAR_OWNER_AUTHEN_STATUS")
    @SequenceGenerator(name = "SEQ_CAR_OWNER_AUTHEN_STATUS",allocationSize=1, sequenceName = "SEQ_CAR_OWNER_AUTHEN_STATUS")
    private Long id;


    @Column(name = "user_vin", unique = true, nullable = false, length = 50)
    private String userVin;
    @Column(name = "openid")
    private String openId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_mobile")
    private String userMobile;
    @Column(name = "create_date")
    private Long createDate;

    /** 0.未审核   1. 已审核 */
    @Column(name = "auth_state")
    private Long authState = 0L;

    /** Neel */
    /** 当前审核次数 */
    @Column(name = "TIMES")
    private long times = 0L;

    /** 审核结果 2.未通过   1.通过 */
    @Column(name = "AUTH_RESULT")
    private Long authResult;

    /** 审核方式  自动  人工*/
    @Column(name = "AUTH_WAY")
    private String authWay;

    /** 匹配方式  DMS 主机厂 重复认证 */
    @Column(name = "VIN_SOURCE")
    private String vinSource;

    /** 审核时间 */
    @Column(name = "AUTH_DATE")
    private Date authDate;

    /** 日期批次 */
    @Column(name = "DATE_NO")
    private String dateNo;

    /** 批次号 */
    @Column(name = "BATCH_NO")
    private String batchNo;

    /** 备注 */
    @Column(name = "REMARK")
    private String remark;

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

}
