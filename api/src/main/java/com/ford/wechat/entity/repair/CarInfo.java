package com.ford.wechat.entity.repair;

/**
 * Created by wanglijun on 16/11/21.
 */
public class CarInfo {
    private String vbillId;//工单编号
    private String vrepairDate;//修理日期
    private String vsstId;//维修站号
    private String vsstName;//维修站名称
    private String cdBrand;//品牌(默认"福特")
    private String cdname;//车型大类
    private String vvin;//vin码

    public String getVbillId() {
        return vbillId;
    }

    public void setVbillId(String vbillId) {
        this.vbillId = vbillId;
    }

    public String getVrepairDate() {
        return vrepairDate;
    }

    public void setVrepairDate(String vrepairDate) {
        this.vrepairDate = vrepairDate;
    }

    public String getVsstId() {
        return vsstId;
    }

    public void setVsstId(String vsstId) {
        this.vsstId = vsstId;
    }

    public String getVsstName() {
        return vsstName;
    }

    public void setVsstName(String vsstName) {
        this.vsstName = vsstName;
    }

    public String getCdBrand() {
        return cdBrand;
    }

    public void setCdBrand(String cdBrand) {
        this.cdBrand = cdBrand;
    }

    public String getCdname() {
        return cdname;
    }

    public void setCdname(String cdname) {
        this.cdname = cdname;
    }

    public String getVvin() {
        return vvin;
    }

    public void setVvin(String vvin) {
        this.vvin = vvin;
    }

    public String getVengineNmb() {
        return vengineNmb;
    }

    public void setVengineNmb(String vengineNmb) {
        this.vengineNmb = vengineNmb;
    }

    public String getNlaborFee() {
        return nlaborFee;
    }

    public void setNlaborFee(String nlaborFee) {
        this.nlaborFee = nlaborFee;
    }

    public String getNrepairMaterialFee() {
        return nrepairMaterialFee;
    }

    public void setNrepairMaterialFee(String nrepairMaterialFee) {
        this.nrepairMaterialFee = nrepairMaterialFee;
    }

    public String getNsaleMaterialFee() {
        return nsaleMaterialFee;
    }

    public void setNsaleMaterialFee(String nsaleMaterialFee) {
        this.nsaleMaterialFee = nsaleMaterialFee;
    }

    public String getNaddItemFee() {
        return naddItemFee;
    }

    public void setNaddItemFee(String naddItemFee) {
        this.naddItemFee = naddItemFee;
    }

    public String getNrepairFee() {
        return nrepairFee;
    }

    public void setNrepairFee(String nrepairFee) {
        this.nrepairFee = nrepairFee;
    }

    private String vengineNmb;//发动机号
    private String nlaborFee;//工时费
    private String nrepairMaterialFee;//维修材料费
    private String nsaleMaterialFee;//销售材料费
    private String naddItemFee;//其他(附加费用)
    private String nrepairFee;//维修金额
}
