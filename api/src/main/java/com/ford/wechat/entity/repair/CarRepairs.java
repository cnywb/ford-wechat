package com.ford.wechat.entity.repair;

/**
 * Created by wanglijun on 16/11/21.
 */
public class CarRepairs {
    private String vsstName;//经销商名称
    private String nbalanceFee;//费用统计
    private String vrepairDate;//日期

    public String getVsstName() {
        return vsstName;
    }

    public void setVsstName(String vsstName) {
        this.vsstName = vsstName;
    }

    public String getNbalanceFee() {
        return nbalanceFee;
    }

    public void setNbalanceFee(String nbalanceFee) {
        this.nbalanceFee = nbalanceFee;
    }

    public String getVrepairDate() {
        return vrepairDate;
    }

    public void setVrepairDate(String vrepairDate) {
        this.vrepairDate = vrepairDate;
    }

    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    private String repairId;//工单Id
}
