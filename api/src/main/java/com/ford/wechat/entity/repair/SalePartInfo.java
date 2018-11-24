package com.ford.wechat.entity.repair;

/**
 * Created by wanglijun on 16/11/21.
 */
public class SalePartInfo {
    private String vpartName = "";//配件名称
    private String vpartCode = "";//配件代码
    private String npartNum = "";//配件数量
    private String npartSaleFee = "";//配件销售金额

    public String getVpartName() {
        return vpartName;
    }

    public void setVpartName(String vpartName) {
        this.vpartName = vpartName;
    }

    public String getVpartCode() {
        return vpartCode;
    }

    public void setVpartCode(String vpartCode) {
        this.vpartCode = vpartCode;
    }

    public String getNpartNum() {
        return npartNum;
    }

    public void setNpartNum(String npartNum) {
        this.npartNum = npartNum;
    }

    public String getNpartSaleFee() {
        return npartSaleFee;
    }

    public void setNpartSaleFee(String npartSaleFee) {
        this.npartSaleFee = npartSaleFee;
    }
}
