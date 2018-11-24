package com.ford.wechat.controller.user.vo;

import com.ford.wechat.controller.vo.PageReq;

/**
 * Created by zhaoliang on 2017/5/24.
 */
public class CarOwnerAuthenStatusGetReq extends PageReq {
    private String userVin;
    private String openId;
    private String userMobile;
    private Long authState;

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

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public Long getAuthState() {
        return authState;
    }

    public void setAuthState(Long authState) {
        this.authState = authState;
    }
}
