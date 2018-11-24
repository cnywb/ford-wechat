package com.ford.wechat.entity.repair;

import java.util.List;

/**
 * Created by wanglijun on 16/11/21.
 */
public class HistoryDetailResponseBody {
    private String responseCode;
    private CarInfo carInfo;//基本信息
    private List<RepairPartInfo> repairPartInfo;//维修材料
    private List<LaborInfo> laborInfo;//维修工时
    private List<SalePartInfo> salePartInfo;//销售配件
    private List<AdditionInfo> additionInfo;//维修附加项

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public CarInfo getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(CarInfo carInfo) {
        this.carInfo = carInfo;
    }

    public List<RepairPartInfo> getRepairPartInfo() {
        return repairPartInfo;
    }

    public void setRepairPartInfo(List<RepairPartInfo> repairPartInfo) {
        this.repairPartInfo = repairPartInfo;
    }

    public List<LaborInfo> getLaborInfo() {
        return laborInfo;
    }

    public void setLaborInfo(List<LaborInfo> laborInfo) {
        this.laborInfo = laborInfo;
    }

    public List<SalePartInfo> getSalePartInfo() {
        return salePartInfo;
    }

    public void setSalePartInfo(List<SalePartInfo> salePartInfo) {
        this.salePartInfo = salePartInfo;
    }

    public List<AdditionInfo> getAdditionInfo() {
        return additionInfo;
    }

    public void setAdditionInfo(List<AdditionInfo> additionInfo) {
        this.additionInfo = additionInfo;
    }


}
