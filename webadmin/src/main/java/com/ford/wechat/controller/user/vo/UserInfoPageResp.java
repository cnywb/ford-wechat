package com.ford.wechat.controller.user.vo;

import com.ford.wechat.controller.vo
        .PageResp;

import java.util.Date;

/**
 * Created by wanglijun on 16/11/2.
 */
public class UserInfoPageResp extends PageResp{
    /**主键*/
    private Long id;

    /**微信openId*/
    private String openId;

    /**用户名*/
    private String userName;

    /**性别*/
    private String gender;

    /**用户生日*/
    private Date birthday;
    /**驾照号码*/
    private String license;

    /**联系电话*/
    private String mobile;

    /**联系地址*/
    private String address;

    /**联系email*/
    private String email;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
