package com.ford.wechat.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 车辆信息（查）
 * Created by wanglijun on 16/11/1.
 */
@Entity
@Table(name="WE_USER_CAR_INFO")
public class CarInfo {


    /**
     * 微信openId
     */
    @Id
    @Column(name = "OPEN_ID")
    private String openId;

    /**
     * 购车日期
     */
    @Column(name = "CAR_BUYING_DATE")
    private String buyDate;

    /**
     * 购车经销商-NAME
     */
    @Column(name = "CAR_BUYING_DEALER")
    private String buyDealerName;

    /**
     * 购车经销商-CODE
     */
    @Column(name = "CAR_BUYING_DEALER_CODE")
    private String buyDealerCode;

    /**
     * 维修经销商-NAME
     */
    @Column(name = "CAR_REPAIR_DEALER")
    private String repairDealerName;

    /**
     * 维修经销商-CODE
     */
    @Column(name = "CAR_REPAIR_DEALER_CODE")
    private String repairDealerCode;

    /**驾照号码*/
    @Column(name="CAR_LICENSE")
    private String license;

    /**
     * 车型
     */
    @Column(name = "CAR_MODEL")
    private String model;

    /**
     * 车款
     */
    @Column(name = "CAR_STYLE")
    private String style;

    /**
     * 颜色
     */
    @Column(name = "CAR_COLOR")
    private String color;


    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public String getBuyDealerCode() {
        return buyDealerCode;
    }

    public void setBuyDealerCode(String buyDealerCode) {
        this.buyDealerCode = buyDealerCode;
    }

    public String getBuyDealerName() {
        return buyDealerName;
    }

    public void setBuyDealerName(String buyDealerName) {
        this.buyDealerName = buyDealerName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getRepairDealerCode() {
        return repairDealerCode;
    }

    public void setRepairDealerCode(String repairDealerCode) {
        this.repairDealerCode = repairDealerCode;
    }

    public String getRepairDealerName() {
        return repairDealerName;
    }

    public void setRepairDealerName(String repairDealerName) {
        this.repairDealerName = repairDealerName;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

}
