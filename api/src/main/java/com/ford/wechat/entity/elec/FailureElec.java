package com.ford.wechat.entity.elec;

/**
 * Created by wanglijun on 16/11/21.
 */
public class FailureElec {
    private String dealName;//券名
    private String amount;//金额
    private String usedAmount;//已用金额
    private String unusedAmount;//未用金额
    private String status;//状态

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(String usedAmount) {
        this.usedAmount = usedAmount;
    }

    public String getUnusedAmount() {
        return unusedAmount;
    }

    public void setUnusedAmount(String unusedAmount) {
        this.unusedAmount = unusedAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
