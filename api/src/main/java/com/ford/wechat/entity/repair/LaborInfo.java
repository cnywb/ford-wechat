package com.ford.wechat.entity.repair;

/**
 * Created by wanglijun on 16/11/21.
 */
public class LaborInfo {
    private String vitemName = "";//维修项目名称
    private String vitemCode = "";//维修项目代码
    private String nitemCost = "";//工时单价
    private String nlaborHour = "";//工时数量
    private String ndiscountRate = "";//折扣系数
    private String nlaborFee = "";//工时收费

    public String getVitemName() {
        return vitemName;
    }

    public void setVitemName(String vitemName) {
        this.vitemName = vitemName;
    }

    public String getVitemCode() {
        return vitemCode;
    }

    public void setVitemCode(String vitemCode) {
        this.vitemCode = vitemCode;
    }

    public String getNitemCost() {
        return nitemCost;
    }

    public void setNitemCost(String nitemCost) {
        this.nitemCost = nitemCost;
    }

    public String getNlaborHour() {
        return nlaborHour;
    }

    public void setNlaborHour(String nlaborHour) {
        this.nlaborHour = nlaborHour;
    }

    public String getNdiscountRate() {
        return ndiscountRate;
    }

    public void setNdiscountRate(String ndiscountRate) {
        this.ndiscountRate = ndiscountRate;
    }

    public String getNlaborFee() {
        return nlaborFee;
    }

    public void setNlaborFee(String nlaborFee) {
        this.nlaborFee = nlaborFee;
    }
}
