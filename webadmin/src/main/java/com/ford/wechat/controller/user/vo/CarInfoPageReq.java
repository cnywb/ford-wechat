package com.ford.wechat.controller.user.vo;

import com.ford.wechat.controller.vo.PageReq;

import java.util.Date;

/**
 * Created by wanglijun on 16/11/2.
 */
public class CarInfoPageReq  extends PageReq{
    /**
     * 微信openid
     */
    private String openId;
    /**
     * 购车经销商
     */
    private String buyDealerName;
    /**
     * 维修经销商
     */
    private String repairDealerName;
    /**
     * 车型
     */
    private String model;
    /**
     * 车款
     */
    private String style;
    /**
     * 颜色
     */
    private String color;
    /**
     * 购车时间
     */
    private Date buyStartDate;
    /**
     * 购车时间
     */
    private Date buyEndDate;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getBuyDealerName() {
        return buyDealerName;
    }

    public void setBuyDealerName(String buyDealerName) {
        this.buyDealerName = buyDealerName;
    }

    public String getRepairDealerName() {
        return repairDealerName;
    }

    public void setRepairDealerName(String repairDealerName) {
        this.repairDealerName = repairDealerName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date getBuyStartDate() {
        return buyStartDate;
    }

    public void setBuyStartDate(Date buyStartDate) {
        this.buyStartDate = buyStartDate;
    }

    public Date getBuyEndDate() {
        return buyEndDate;
    }

    public void setBuyEndDate(Date buyEndDate) {
        this.buyEndDate = buyEndDate;
    }
}
