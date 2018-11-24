package com.ford.wechat.controller.user.vo;

/**
 * Created by zhaoliang on 2017/7/6.
 */
public class CarOwnerAuthenStatusRemoveReq {
    private Long id;
    private String openId;
    private String userVin;


    public String getUserVin() {
        return userVin;
    }

    public void setUserVin(String userVin) {
        this.userVin = userVin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
