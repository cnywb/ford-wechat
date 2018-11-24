package com.ford.wechat.controller.dealer.vo;/**
 * Created by jovi on 17/02/2017.
 */

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-02-17 14:49
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public class ScanDealerHandleReq {
    /**
     * 主键
     */
    private Long id;
    /**
     * 销售代码
     */
    private String code;
    /**
     * 服务代码
     */
    private String sstCode;
    /**
     * 经销商名称
     */
    private String name;
    /**
     * 小区
     */
    private String smallArea;
    /**
     * 大区
     */
    private String bigArea;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 地址
     */
    private String address;
    /**
     * 邮编
     */
    private String zipcode;
    /**
     * 类型 经销商 意见领袖 活动
     */
    private Integer type;

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

    public String getSstCode() {
        return sstCode;
    }

    public void setSstCode(String sstCode) {
        this.sstCode = sstCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
