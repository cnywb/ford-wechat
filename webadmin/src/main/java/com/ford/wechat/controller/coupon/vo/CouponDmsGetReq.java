package com.ford.wechat.controller.coupon.vo;

import com.ford.wechat.controller.vo.PageReq;

/**
 * Created by zhaoliang on 2017/8/31.
 */
public class CouponDmsGetReq extends PageReq {

    /**
     * 开始时间
     */
    private String createStartDate;

    /**
     * 结束时间
     */
    private String createEndDate;

    private String targetDealer;
    private String batchNo;
    private String vin;
    private String customerMobile;
    private String sendSms;
    private String sendDmsStatus;

    public String getSendDmsStatus() {
        return sendDmsStatus;
    }

    public void setSendDmsStatus(String sendDmsStatus) {
        this.sendDmsStatus = sendDmsStatus;
    }

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

    public String getTargetDealer() {
        return targetDealer;
    }

    public void setTargetDealer(String targetDealer) {
        this.targetDealer = targetDealer;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getSendSms() {
        return sendSms;
    }

    public void setSendSms(String sendSms) {
        this.sendSms = sendSms;
    }
}
