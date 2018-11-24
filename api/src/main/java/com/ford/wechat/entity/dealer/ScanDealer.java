package com.ford.wechat.entity.dealer;/**
 * Created by jovi on 7/20/16.
 */

import com.ford.wechat.entity.BaseEntity;

import javax.persistence.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2016-07-20 11:36
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Entity
@Table(name="P_CLUB_DEALER")
public class ScanDealer extends BaseEntity {
    @javax.persistence.Id
    @Column(name = "DLID", nullable = false)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "P_CLUB_DEALER_ID")
    @SequenceGenerator(name = "P_CLUB_DEALER_ID",allocationSize=1,sequenceName = "P_CLUB_DEALER_ID")
    private Long id;
    /**
     * 销售代码
     */
    @Column(name = "CODE")
    private String code;
    /**
     * 服务代码
     */
    @Column(name = "SST_CODE")
    private String sstCode;
    /**
     * 经销商名称
     */
    @Column(name = "SST_NAME")
    private String name;
    /**
     * 小区
     */
    @Column(name = "SMALL_AREA")
    private String smallArea;
    /**
     * 大区
     */
    @Column(name = "BIG_AREA")
    private String bigArea;
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
     * 邮编
     */
    @Column(name = "ZIPCODE")
    private String zipcode;
    /**
     * 类型 经销商 意见领袖 活动
     */
    @Column(name = "TYPE")
    private Integer type;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSstCode() {
        return sstCode;
    }

    public void setSstCode(String sstCode) {
        this.sstCode = sstCode;
    }

    public String getSmallArea() {
        return smallArea;
    }

    public void setSmallArea(String smallArea) {
        this.smallArea = smallArea;
    }

    public String getBigArea() {
        return bigArea;
    }

    public void setBigArea(String bigArea) {
        this.bigArea = bigArea;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
