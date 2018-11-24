package com.ford.wechat.web.members.vo;

/**
 * Created by wanglijun on 16/11/27.
 */
public class CarInfoVo {
    private String year;
    private String month;
    private String day;
    private String carBuyingDealer;
    private String carRepairDealer;
    private String carModel;
    private String carStyle;
    private String carColor;
    private String openid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getCarBuyingDealer() {
        return carBuyingDealer;
    }

    public void setCarBuyingDealer(String carBuyingDealer) {
        this.carBuyingDealer = carBuyingDealer;
    }

    public String getCarRepairDealer() {
        return carRepairDealer;
    }

    public void setCarRepairDealer(String carRepairDealer) {
        this.carRepairDealer = carRepairDealer;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarStyle() {
        return carStyle;
    }

    public void setCarStyle(String carStyle) {
        this.carStyle = carStyle;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }
}
