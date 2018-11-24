/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MaintenancePackageOrderListResp.java
 */

package com.ford.wechat.pc.controller.combo.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述: 响应对象
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class MaintenancePackageOrderListResp {
    private String product;
    private String productType;
    private String vin;
    private String contractNumber;
    private String termMonths;
    private String termOdometer;
    private String vehicleRetail;
    private String contractRegistrationDate;
    private String contractStartDate;
    private String contractEndDate;
    private String vehicleLine;
    private String engineType;
    private String dealerSupplierCode;
    private String dealerName;
    private String fileName;
}