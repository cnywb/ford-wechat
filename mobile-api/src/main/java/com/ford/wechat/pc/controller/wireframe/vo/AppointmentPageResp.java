/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * Appointment.java 2017-09-18 13:57:36
 */
package com.ford.wechat.pc.controller.wireframe.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：分页查询请求返回
 *
 * Created by ${USER} on 2017-09-18 13:57:36.
 * @since 1.0
*/
@Data
@NoArgsConstructor
public class AppointmentPageResp {


    private Long id;

    private String vin;

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
}