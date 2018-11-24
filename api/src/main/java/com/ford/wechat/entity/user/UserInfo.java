package com.ford.wechat.entity.user;

import com.ford.wechat.entity.VersionEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 微信用户（查）
 * Created by wanglijun on 16/11/1.
 */
@Entity
@Table(name= "WE_USER_INFO")
public class UserInfo {

    /**微信openId*/
    @Id
    @Column(name="OPEN_ID")
    private String openId;

    /**用户名*/
    @Column(name="USER_NAME")
    private String userName;

    /**性别*/
    @Column(name="USER_SEX")
    private String gender;

    /**用户生日*/
    @Column(name="USER_BOTHDAY")
    private String birthday;
    /**驾照号码*/
    @Column(name="USER_LICENSE")
    private String license;

    /**联系电话*/
    @Column(name="USER_MOBILE")
    private String mobile;

    /**联系地址*/
    @Column(name="USER_ADDRESS")
    private String address;

    /**联系email*/
    @Column(name="USER_EMAIL")
    private String email;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
}
