/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * Appointment.java 2017-09-18 13:57:36
 */
package com.ford.wechat.pc.controller.wireframe.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：对象创建或修改请求返回
 *
 * Created by ${USER} on 2017-09-18 13:57:36.
 * @since 1.0
*/
@Data
@NoArgsConstructor
public class AppointmentHandleReq {

    private Long id;
    private String vin;
    private String orderNo;
    private String carType;
    private String miles;
    private String dealerName;
    private String appointDate;
    private String appointHour;
    private String serviceAdviser;
    private String serviceType;
    private String comment;
    private String contactName;
    private String contactPhone;
    private String appointStatus;
    private  java.util.Date firstInsertDate;
    private  java.util.Date modifyDate;
    private Integer deleted;
    private String deleteTime;
    /*操作类型,删除还是新增*/
    private String operateType;

    public static final String CRATE = "create";

    public static final String MODIFY = "modify";

}