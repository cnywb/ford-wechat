/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * Appointment.java 2017-09-18 13:57:36
 */
package com.ford.wechat.pc.controller.wireframe.vo;
import io.dabing.common.grid.GridPage;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：分页查询请求
 *
 * Created by ${USER} on 2017-09-18 13:57:36.
 * @since 1.0
*/
@Data
@NoArgsConstructor
public class AppointmentPageReq {
    private GridPage page;
}