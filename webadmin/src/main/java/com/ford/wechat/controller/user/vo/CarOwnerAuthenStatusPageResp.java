package com.ford.wechat.controller.user.vo;

import java.util.Date;

/**
 * Created by zhaoliang on 2017/5/24.
 */
public class CarOwnerAuthenStatusPageResp {

    private Long id;

    private String userVin;

    private String openId;

    private String userName;

    private String userMobile;

    private Long createDate;

    private Long authState;

    private long times;

    private Long authResult;

    private String authWay;

    private Date authDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserVin() {
        return userVin;
    }

    public void setUserVin(String userVin) {
        this.userVin = userVin;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public Long getAuthState() {
        return authState;
    }

    public void setAuthState(Long authState) {
        this.authState = authState;
    }

    public long getTimes() {
        return times;
    }

    public void setTimes(long times) {
        this.times = times;
    }

    public Long getAuthResult() {
        return authResult;
    }

    public void setAuthResult(Long authResult) {
        this.authResult = authResult;
    }

    public String getAuthWay() {
        return authWay;
    }

    public void setAuthWay(String authWay) {
        this.authWay = authWay;
    }

    public Date getAuthDate() {
        return authDate;
    }

    public void setAuthDate(Date authDate) {
        this.authDate = authDate;
    }
}
