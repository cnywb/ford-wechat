package com.ford.wechat.entity.repair;

import java.util.List;

/**
 * Created by wanglijun on 16/11/21.
 */
public class HistoryResponseBody {
    private String responseCode;
    private List<CarInfoList> carInfoList;//车辆维修信息

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public List<CarInfoList> getCarInfoList() {
        return carInfoList;
    }

    public void setCarInfoList(List<CarInfoList> carInfoList) {
        this.carInfoList = carInfoList;
    }
}
