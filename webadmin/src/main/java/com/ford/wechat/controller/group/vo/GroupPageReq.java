package com.ford.wechat.controller.group.vo;

import com.ford.wechat.controller.vo.PageReq;

import java.util.Date;

/**
 * Created by wanglijun on 16/11/2.
 */
public class GroupPageReq extends PageReq{
    /**
     * openid
     */
    private String openId;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 用户手机
     */
    private String mobile;
    /**
     * 意向车型名称
     */
    private String favourCarName;
    /**
     * 经销商
     */
    private String dealer;
    /**
     * app名称
     */
    private String appLinkName;
    /**
     * 购车开始时间
     */
    private Date buyStartDate;
    /**
     * 购车结束时间
     */
    private Date buyEndDate;

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

    public String getFavourCarName() {
        return favourCarName;
    }

    public void setFavourCarName(String favourCarName) {
        this.favourCarName = favourCarName;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getAppLinkName() {
        return appLinkName;
    }

    public void setAppLinkName(String appLinkName) {
        this.appLinkName = appLinkName;
    }

    public Date getBuyStartDate() {
        return buyStartDate;
    }

    public void setBuyStartDate(Date buyStartDate) {
        this.buyStartDate = buyStartDate;
    }

    public Date getBuyEndDate() {
        return buyEndDate;
    }

    public void setBuyEndDate(Date buyEndDate) {
        this.buyEndDate = buyEndDate;
    }
}
