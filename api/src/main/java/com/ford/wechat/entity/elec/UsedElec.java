package com.ford.wechat.entity.elec;

/**
 * Created by wanglijun on 16/11/21.
 */
public class UsedElec {
    private String accountId;//优惠券编号
    private String usedAmount;//使用金额
    private String roNo;//工单号
    private String usedDate;//使用时间(格式2014-06-12)

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(String usedAmount) {
        this.usedAmount = usedAmount;
    }

    public String getRoNo() {
        return roNo;
    }

    public void setRoNo(String roNo) {
        this.roNo = roNo;
    }

    public String getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(String usedDate) {
        this.usedDate = usedDate;
    }
}
