package com.ford.wechat.controller.message.vo;

import com.ford.wechat.controller.vo.PageReq;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

/**
 * Created by zhaoliang on 2017/8/27.
 */
public class MessageSendGetReq extends PageReq {

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

    /**
     * 发送结果
     */
    private String sendResult;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 项目代码
     */
    private String projectCode;
    /**
     * 微信唯一标识
     */
    private String openId;
    /**
     * VIN码
     */
    private String vin;

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

    public String getSendResult() {
        return sendResult;
    }

    public void setSendResult(String sendResult) {
        this.sendResult = sendResult;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
}
