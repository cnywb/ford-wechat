package com.ford.wechat.controller.batch.vo;

import com.ford.wechat.controller.vo.PageReq;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by zhaoliang on 2017/5/27.
 */
public class BatchTaskGetReq extends PageReq {
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
    private Integer runTimes;
    private Integer status;

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

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getRunTimes() {
        return runTimes;
    }

    public void setRunTimes(Integer runTimes) {
        this.runTimes = runTimes;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
