package com.ford.wechat.controller.coupon.vo;

import com.ford.wechat.controller.vo.PageReq;


/**
 * Created by zhaoliang on 2017/8/27.
 */
public class EventStatisticsGetReq extends PageReq {

    /**
     * 开始时间
     */
    private String createStartDate;

    /**
     * 结束时间
     */
    private String createEndDate;

    /**
     * 项目代码
     */
    private String projectCode;


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
}
