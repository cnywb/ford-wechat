package com.ford.wechat.controller.members.vo;

import com.ford.wechat.controller.vo.PageReq;

/**
 * Created by zhaoliang on 2017/5/25.
 */
public class WeUnauthApplyGetReq extends PageReq {

    private String vin;
    private String openId;
    private String mobile;
    private Integer assessStatus;

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getAssessStatus() {
        return assessStatus;
    }

    public void setAssessStatus(Integer assessStatus) {
        this.assessStatus = assessStatus;
    }
}
