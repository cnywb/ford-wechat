package com.ford.wechat.controller.group.vo;

import com.ford.wechat.controller.vo.PageResp;

import java.util.Date;

/**
 * Created by wanglijun on 16/11/2.
 */
public class GroupPageResp  extends PageResp{
    /**主键*/
    private Long id;

    /**微信openId*/
    private String openId;
    /**用户姓名*/
    private String name;
    /**用户手机*/
    private String mobile;
    /**意向车型编号也即粉丝分组编号*/
    private String favourCarCode;

    /**意向车型名称也即粉丝分组名称*/
    private String favourCarName;
    /***
     * 购车时间
     */
    private Date buyDate;
    /**省*/
    private String province;
    /**经销商*/
    private String city;

    /**经销商*/
    private String dealer;

    /**经销商编号*/
    private String dealerNo;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFavourCarCode() {
        return favourCarCode;
    }

    public void setFavourCarCode(String favourCarCode) {
        this.favourCarCode = favourCarCode;
    }

    public String getFavourCarName() {
        return favourCarName;
    }

    public void setFavourCarName(String favourCarName) {
        this.favourCarName = favourCarName;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
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

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getDealerNo() {
        return dealerNo;
    }

    public void setDealerNo(String dealerNo) {
        this.dealerNo = dealerNo;
    }
}
