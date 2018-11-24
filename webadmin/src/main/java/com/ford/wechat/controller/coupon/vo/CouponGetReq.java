package com.ford.wechat.controller.coupon.vo;

import com.ford.wechat.controller.vo.PageReq;

/**
 * Created by zhaoliang on 2017/8/27.
 */
public class CouponGetReq extends PageReq {

    /**
     * 开始时间
     */
    private String createStartDate;

    /**
     * 结束时间
     */
    private String createEndDate;

    /**
     * 活动代码
     */
    private String projectCode;
    /**
     * 微信唯一标识
     */
    private String openId;
    /**
     * vin
     */
    private String vin;
    /**
     * 批处理状态
     */
    private Integer batchStatus;
    /**
     * 状态
     */
    private Integer status;

    public String getCreateStartDate() {
        return createStartDate;
    }

    public void setCreateStartDate(String createStartDate) {
        this.createStartDate = createStartDate;
    }

    public String getCreateEndDate() {
        return createEndDate;
    }

    public void setCreateEndDate(String createEndDate) {
        this.createEndDate = createEndDate;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Integer getBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(Integer batchStatus) {
        this.batchStatus = batchStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
