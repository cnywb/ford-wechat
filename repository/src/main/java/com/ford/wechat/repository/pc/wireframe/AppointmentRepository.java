/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * Appointment.java 2017-09-18 13:57:35
 */
package com.ford.wechat.repository.pc.wireframe;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;
import com.ford.wechat.entity.wireframe.Appointment;

import java.util.List;

/**
 * Created by ${USER} on 2017-09-18 13:57:35.
 * @since 1.0
 */
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
  /**
   * 根据GridPage对象按分页查找服务
   * @param page 分页对象，里面有关键字进行模糊匹配
   * @return
   */
   Page<Appointment> findByGridPage(GridPage page);

    List<Appointment> findByOrderNo(Appointment appointment);
}

