/**
 * Copyright (c) dabing.io
 * All rights reserved.
 * MaintenancePackageOrder.java
 */
package com.ford.wechat.entity.pc.combo;

import com.ford.wechat.entity.pc.message.MessageEnum;
import com.ford.wechat.entity.pc.message.tempate.MessageTemplateEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 描述:MaintenancePackageOrder 原厂保养套餐订单模型
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "MAINTENANCE_PACKAGE_ORDER")
public class MaintenancePackageOrder {
    /**
     * 物理主键
     */
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MAIN_PACK_ORDER_ID")
    @SequenceGenerator(name = "SEQ_MAIN_PACK_ORDER_ID", allocationSize = 1, sequenceName = "SEQ_MAIN_PACK_ORDER_ID")
    private Long id;

    /**
     * 用户的微信OPENID（如果是公告消息，可以为空）
     */
    @Column(name = "PRODUCT")
    private String product;
    @Column(name = "PRODUCT_TYPE")
    private String productType;
    @Column(name = "VIN")
    private String vin;
    @Column(name = "CONTRACT_NUMBER")
    private String contractNumber;
    @Column(name = "TERM_MONTHS")
    private String termMonths;
    @Column(name = "TERM_ODOMETER")
    private String termOdometer;
    @Column(name = "VEHICLE_RETAIL")
    private String vehicleRetail;
    @Column(name = "CONTRACT_REGISTRATION_DATE")
    private String contractRegistrationDate;
    @Column(name = "CONTRACT_START_DATE")
    private String contractStartDate;
    @Column(name = "CONTRACT_END_DATE")
    private String contractEndDate;
    @Column(name = "VEHICLE_LINE")
    private String vehicleLine;
    @Column(name = "ENGINE_TYPE")
    private String engineType;
    @Column(name = "DEALER_SUPPLIER_CODE")
    private String dealerSupplierCode;
    @Column(name = "DEALER_NAME")
    private String dealerName;
    @Column(name = "FILE_NAME")
    private String fileName;

}
