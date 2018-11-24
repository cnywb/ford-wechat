package com.ford.wechat.entity.dealer;

import com.ford.wechat.entity.VersionEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2017
 * All rights reserved. 2017-05-04 20:20
 * </p>
 *
 * @author zhaoliang
 * @version 1.0
 */
@Data
@Entity
@Table(name="WE_SCAN_CHANNEL")
public class WeChannel extends VersionEntity {

    public final static int DEALER = 0;//经销商扫码
    public final static int KOL = 1;//意见领袖
    public final static int OWNERBOOK = 2;//车主手册
    public final static int ACTIVITY = 3;//活动

    /**主键*/
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "SEQ_WE_CHANNEL")
    @SequenceGenerator(name = "SEQ_WE_CHANNEL",allocationSize=1,sequenceName = "SEQ_WE_CHANNEL")
    private Long id;

    /**
     * 渠道代码
     */
    @Column(name = "CODE")
    private String code;

    /**
     * 渠道名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 经销商
     */
    @Column(name = "DEALER_CODE")
    private String dealerCode;

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
     * 省
     */
    @Column(name = "PROVINCE")
    private String province;

    /**
     * 市
     */
    @Column(name = "CITY")
    private String city;

    /**
     * 地址
     */
    @Column(name = "ADDRESS")
    private String address;

    /**
     * 邮政编码
     */
    @Column(name = "ZIPCODE")
    private String zipCode;

    /**
     * 渠道类型
     */
    @Column(name = "TYPE")
    private int type;

    /**
     * 经销商代码
     */
    @Column(name = "DEALER_SERVICE_CODE")
    private String dealerServiceCode;

    /**
     * 备注
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * URL
     */
    @Column(name = "URL")
    private String url;

}
