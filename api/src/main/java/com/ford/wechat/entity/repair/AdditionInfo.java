package com.ford.wechat.entity.repair;

/**
 * Created by wanglijun on 16/11/21.
 */
public class AdditionInfo {
    private String vitemName;//附加项目名称
    private String vitemType;//收费类型代码
    private String vitemCode;//附加项目代码
    private String nadditionFee;//附加项目收费

    public String getVitemName() {
        return vitemName;
    }

    public void setVitemName(String vitemName) {
        this.vitemName = vitemName;
    }

    public String getVitemType() {
        return vitemType;
    }

    public void setVitemType(String vitemType) {
        this.vitemType = vitemType;
    }

    public String getVitemCode() {
        return vitemCode;
    }

    public void setVitemCode(String vitemCode) {
        this.vitemCode = vitemCode;
    }

    public String getNadditionFee() {
        return nadditionFee;
    }

    public void setNadditionFee(String nadditionFee) {
        this.nadditionFee = nadditionFee;
    }
}
