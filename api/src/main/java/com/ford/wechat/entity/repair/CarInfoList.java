package com.ford.wechat.entity.repair;

import java.util.List;

/**
 * Created by wanglijun on 16/11/21.
 */
public class CarInfoList {
    private String carType;//车辆信息
    private String vin;//vin码
    private String carColor;//颜色
    private List<CarRepairs> carRepairs;//维修工单记录

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public List<CarRepairs> getCarRepairs() {
        return carRepairs;
    }

    public void setCarRepairs(List<CarRepairs> carRepairs) {
        this.carRepairs = carRepairs;
    }
}
