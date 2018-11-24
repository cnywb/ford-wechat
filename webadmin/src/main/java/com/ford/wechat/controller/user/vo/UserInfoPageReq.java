package com.ford.wechat.controller.user.vo;

import com.ford.wechat.controller.vo.PageReq;

/**
 * Created by wanglijun on 16/11/2.
 */
public class UserInfoPageReq extends PageReq{
    /**
     * 微信openid
     */
    private String openId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 驾照号码
     */
    private String license;
    /**
     * 联系电话
     */
    private String mobile;
    /**
     * 联系email
     */
    private String email;

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

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
