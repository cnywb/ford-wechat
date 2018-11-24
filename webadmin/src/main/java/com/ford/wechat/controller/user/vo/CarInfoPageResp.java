package com.ford.wechat.controller.user.vo;

import com.ford.wechat.controller.vo.PageResp;

import java.util.Date;

/**
 * Created by wanglijun on 16/11/2.
 */
public class CarInfoPageResp extends PageResp{
    /**主键*/
    private Long id;

    /**微信openId*/
    private String openId;

    /**购车日期*/
    private Date buyDate;

    /**购车经销商-NAME*/
    private String buyDealerName;

    /**购车经销商-CODE*/
    private String buyDealerCode;

    /**维修经销商-NAME*/
    private String repairDealerName;

    /**维修经销商-CODE*/
    private String repairDealerCode;

    /**车型*/
    private String model;

    /**车款*/
    private String style;

    /**颜色*/
    private String color;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public String getBuyDealerName() {
        return buyDealerName;
    }

    public void setBuyDealerName(String buyDealerName) {
        this.buyDealerName = buyDealerName;
    }

    public String getBuyDealerCode() {
        return buyDealerCode;
    }

    public void setBuyDealerCode(String buyDealerCode) {
        this.buyDealerCode = buyDealerCode;
    }

    public String getRepairDealerName() {
        return repairDealerName;
    }

    public void setRepairDealerName(String repairDealerName) {
        this.repairDealerName = repairDealerName;
    }

    public String getRepairDealerCode() {
        return repairDealerCode;
    }

    public void setRepairDealerCode(String repairDealerCode) {
        this.repairDealerCode = repairDealerCode;
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
}
