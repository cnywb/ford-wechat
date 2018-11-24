/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * AppointmentServiceImpl.java 2017-09-18 13:57:36
 */
package com.ford.wechat.service.wireframe.impl;

import com.ford.wechat.entity.wireframe.dmsvo.WireframeDmsRequest;
import com.ford.wechat.entity.wireframe.dmsvo.WireframeDmsResponse;
import com.ford.wechat.service.wireframe.DmsRequestService;
import io.dabing.common.grid.GridPage;
import io.dabing.common.util.JSONUtil;
import io.dabing.core.repository.domain.Page;
import io.dabing.redis.client.RedisClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ford.wechat.repository.pc.wireframe.AppointmentRepository;
import com.ford.wechat.entity.wireframe.Appointment;
import com.ford.wechat.service.wireframe.AppointmentService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lc on 2017-09-18 13:57:36 .
 *
 * @since 1.0
 */
@Service
public class AppointmentServiceImpl implements AppointmentService {
    private static final String REDIS_KEY = "WIRE_FRAME_APPOINTMENT";
    @Autowired
    private AppointmentRepository repository;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private DmsRequestService dmsRequestService;

    @Override
    public Page<Appointment> findByGridPage(GridPage page) {
        return repository.findByGridPage(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public String save(Appointment appointment) {
        String opType = "create";
        ArrayList arrayList = new ArrayList();
         /*TODO DMS 参数 */
         /*DMS发送请求*/
        WireframeDmsResponse wireframeDmsResponse = getWireframeDmsResponse(appointment, opType, arrayList);

        /*TODO dealer_service_code */
        /*保存至数据库*/
        appointment.setOrderNo(generateOrderNo(appointment));
        appointment.setDealerServiceCode("");
        repository.save(appointment);
        return JSONUtil.objectToJson(wireframeDmsResponse);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public String update(Appointment appointment) {
        List<Appointment> byOrderNo = this.repository.findByOrderNo(appointment);
        ArrayList contents =  transferDeletedList(byOrderNo);
        String opType = "modify";
        /*TODO DMS 参数*/
        WireframeDmsResponse wireframeDmsResponse = getWireframeDmsResponse(appointment, opType, contents);

        repository.update(appointment);
        return  JSONUtil.objectToJson(wireframeDmsResponse);
    }

    private ArrayList transferDeletedList(List<Appointment> byOrderNo) {
        ArrayList<Map<String, String>> maps = new ArrayList<>();
        for (Appointment appointment:byOrderNo) {
            HashMap<String, String> map = new HashMap<>();
            map.put("order_no",appointment.getOrderNo());
            map.put("vin",appointment.getVin());
            map.put("date",appointment.getAppointDate());
            map.put("time",appointment.getAppointHour());
            map.put("dealer_service_code",appointment.getDealerServiceCode());
            map.put("status","C");
            maps.add(map);
        }
        return  maps;
    }

    private WireframeDmsResponse getWireframeDmsResponse(Appointment appointment, String opType, ArrayList arrayList) {
        WireframeDmsRequest request = new WireframeDmsRequest();
        request.setUser_name(null);
        request.setPassword(null);
        request.setBusiness_type(WireframeDmsRequest.WBO2);
        request.setSource(WireframeDmsRequest.MX);
        transferOrderContent(appointment,arrayList,opType);
        return dmsRequestService.orderRequest(request);
    }

    private ArrayList<HashMap> transferOrderContent(Appointment appointment, List contents, String operateType) {
        if (contents.isEmpty()) {
            contents = new ArrayList<HashMap>();
        }
        String appointDate = appointment.getAppointDate();
        String dealerName = appointment.getDealerName();
        if (operateType.equals("create")) {

        }

        if (operateType.equals("modify")) {

        }

        return null;
    }

    public String generateOrderNo(Appointment appointment) {
        StringBuffer orderNo = new StringBuffer();
        orderNo.append(new String("WX"));
        String appointDate = appointment.getAppointDate();
        appointDate = appointDate.replaceAll(".", "-");
        orderNo.append(appointDate);
        String redisValue = redisClient.get(REDIS_KEY);
        if (StringUtils.isEmpty(redisValue)) {
            synchronized (this) {
                if (StringUtils.isEmpty(redisValue)){
                    redisClient.set(REDIS_KEY, "0");
                }
            }
        }
        Long incr = redisClient.incr(REDIS_KEY);
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("0000");
        String num = format.format(incr);
        orderNo.append(num);
        return orderNo.toString();
    }
}