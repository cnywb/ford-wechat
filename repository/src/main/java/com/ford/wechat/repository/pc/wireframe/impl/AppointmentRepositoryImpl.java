/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * Appointment.java 2017-09-18 13:57:35
 */
package com.ford.wechat.repository.pc.wireframe.impl;

import com.ford.wechat.repository.pc.wireframe.AppointmentRepository;
import org.springframework.stereotype.Repository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import com.ford.wechat.entity.wireframe.Appointment;

import java.util.List;

/**
 * Created by ${USER} on 2017-09-18 13:57:35 .
 *
 * @since 1.0
 */
@Repository
public class AppointmentRepositoryImpl extends DefaultJpaRepository<Appointment, Long> implements AppointmentRepository {
    /**
     * 根据GridPage对象按分页查找服务
     * @param page 分页对象，里面有关键字进行模糊匹配
     * @return
     */
    public Page<Appointment> findByGridPage(GridPage page) {
        StringQuery query = StringQuery.newQuery()
                .query("from "+Appointment.class.getName()+" a where (1=1) ")
                .predicateHasText(page.getKeyWord())
                    .query(" and a.name like :name")
                    .likeParam("name", page.getKeyWord())
                .query(" order by a.id desc").build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }

    @Override
    public List<Appointment> findByOrderNo(Appointment appointment) {
        StringQuery query = StringQuery.newQuery()
                .query("from "+Appointment.class.getName()+" a where (1=1) ")
                .predicateHasText(appointment.getOrderNo())
                .query(" and a.orderNo = :orderNo")
                .likeParam("orderNo", appointment.getOrderNo())
                .query(" order by a.id desc").build();
        return find(query);
    }
}