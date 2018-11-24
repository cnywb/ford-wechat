package com.ford.wechat.web.members.vo;

/**
 * Created by wanglijun on 16/11/6.
 */
public class CarOwnerHandleReq {
    private String name;
    private String mobile;
    private String vincode;
    private String openid;
    private String dealercode;
    private int subcount;

    public int getSubcount() {
        return subcount;
    }

    public void setSubcount(int subcount) {
        this.subcount = subcount;
    }

    public String getDealercode() {
        return dealercode;
    }

    public void setDealercode(String dealercode) {
        this.dealercode = dealercode;
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

    public String getVincode() {
        return vincode;
    }

    public void setVincode(String vincode) {
        this.vincode = vincode;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
