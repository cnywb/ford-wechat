package com.ford.wechat.controller.auth.vo;

import com.ford.wechat.controller.vo.PageReq;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by zhaoliang on 2017/5/26.
 */
public class AuthToDmsGetReq extends PageReq {

    /**
     * 开始时间
     * @return
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createStartDate;

    /**
     * 结束时间
     * @return
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createEndDate;

    private String batchNo;
    private String vin;
    private String openId;
    private Integer channelType;
    private Integer sendDmsStatus;
    private Integer verify;

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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getChannelType() {
        return channelType;
    }

    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    public Integer getSendDmsStatus() {
        return sendDmsStatus;
    }

    public void setSendDmsStatus(Integer sendDmsStatus) {
        this.sendDmsStatus = sendDmsStatus;
    }

    public Integer getVerify() {
        return verify;
    }

    public void setVerify(Integer verify) {
        this.verify = verify;
    }

    public Date getCreateStartDate() {
        return createStartDate;
    }

    public void setCreateStartDate(Date createStartDate) {
        this.createStartDate = createStartDate;
    }

    public Date getCreateEndDate() {
        return createEndDate;
    }

    public void setCreateEndDate(Date createEndDate) {
        this.createEndDate = createEndDate;
    }
}
