/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * Appointment.java 2017-09-18 13:57:36
 */
package com.ford.wechat.pc.controller.wireframe.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 描述：批量删除请求
 *
 * Created by ${USER} on 2017-09-18 13:57:36.
 * @since 1.0
*/
@Data
@NoArgsConstructor
public class AppointmentDeleteReq {
    private List<Long> ids;
}