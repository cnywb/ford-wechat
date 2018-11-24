package com.ford.wechat.entity.elec;

import java.util.List;

/**
 * Created by wanglijun on 16/11/21.
 */
public class ElecListResponseBody {
    private String responseCode;
    private List<AvailableElec> availableElec;//可用优惠券
    private List<UsedElec> usedElec;//使用记录
    private List<FailureElec> failureElec;//失效优惠券

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public List<AvailableElec> getAvailableElec() {
        return availableElec;
    }

    public void setAvailableElec(List<AvailableElec> availableElec) {
        this.availableElec = availableElec;
    }

    public List<UsedElec> getUsedElec() {
        return usedElec;
    }

    public void setUsedElec(List<UsedElec> usedElec) {
        this.usedElec = usedElec;
    }

    public List<FailureElec> getFailureElec() {
        return failureElec;
    }

    public void setFailureElec(List<FailureElec> failureElec) {
        this.failureElec = failureElec;
    }
}
