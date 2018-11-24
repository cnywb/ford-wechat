/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * Complain.java
 */

package com.ford.wechat.entity.pc.complain;

import com.ford.wechat.entity.AuditEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 描述: 投诉数据对象
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "PC_COMPLAIN")
public class ComplainEntity extends AuditEntity {

    public static final String SEND_MAIL_WAIT = "待发送";
    public static final String SEND_MAIL_SENDING = "发送中";
    public static final String SEND_MAIL_SENDED = "已发送";

    /**
     * 物理主键
     */
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PC_COMPLAIN")
    @SequenceGenerator(name = "SEQ_PC_COMPLAIN", allocationSize = 1, sequenceName = "SEQ_PC_COMPLAIN")
    private Long id;
    /**
     * 微信唯一标识
     */
    @Column(name = "OPEN_ID")
    private String openId;
    /**
     * 客户名称
     */
    @Column(name = "CUSTOMER_NAME")
    private String customerName;
    /**
     * 客户手机号
     */
    @Column(name = "CUSTOMER_MOBILE")
    private String customerMobile;
    /**
     * 客户VIN码
     */
    @Column(name = "CUSTOMER_VIN")
    private String customerVin;
    /**
     * 车牌号
     */
    @Column(name = "LICENSE")
    private String license;
    /**
     * 联系地址
     */
    @Column(name = "ADDRESS")
    private String address;
    /**
     * 车型
     */
    @Column(name = "CAR_MODEL")
    private String carModel;
    /**
     * 行驶里程
     */
    @Column(name = "EXERCISE_MILEAGE")
    private Long exerciseMileage;
    /**
     * 投诉对象
     */
    @Column(name = "COMPLAIN_OBJECT")
    private String complainObject;
    /**
     * 投诉原因
     */
    @Column(name = "COMPLAIN_REASON")
    private String complainReason;
    /**
     * 涉及经销商
     */
    @Column(name = "INVOLVE_DEALER")
    private String involveDealer;
    /**
     * 事件描述
     */
    @Column(name = "INCIDENT_DESC")
    private String incidentDesc;
    /**
     * 发送状态
     */
    @Column(name = "SEND_MAIL")
    private String sendMail;
    /**
     * 发送时间
     */
    @Column(name = "SEND_DATE")
    private Date sendDate;
}