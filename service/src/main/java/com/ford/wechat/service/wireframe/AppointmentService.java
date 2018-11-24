/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * Appointment.java 2017-09-18 13:57:36
 */
package com.ford.wechat.service.wireframe;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import com.ford.wechat.entity.wireframe.Appointment;
import java.util.List;
/**
 * Created by lc on 2017-09-18 13:57:36 .
 * @since 1.0
 */
public interface AppointmentService  {

    Page<Appointment> findByGridPage(GridPage page);

    String save(Appointment object);

    String update(Appointment object);
}