/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * Appointment.java 2017-09-18 13:57:36
 */
package com.ford.wechat.pc.controller.wireframe;

import com.ford.wechat.entity.wireframe.Appointment;
import com.ford.wechat.entity.wireframe.dmsvo.WireframeDmsRequest;
import com.ford.wechat.entity.wireframe.dmsvo.WireframeDmsResponse;
import com.ford.wechat.pc.controller.wireframe.vo.AppointmentHandleReq;
import com.ford.wechat.pc.controller.wireframe.vo.AppointmentPageReq;
import com.ford.wechat.pc.controller.wireframe.vo.AppointmentPageResp;
import com.ford.wechat.service.wireframe.AppointmentService;
import com.ford.wechat.service.wireframe.DmsRequestService;
import io.dabing.common.date.DateUtil;
import io.dabing.common.util.JSONUtil;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import io.dabing.redis.client.BinaryRedisClient;
import io.dabing.redis.client.RedisClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *  Created by lc on 2017-09-18 13:57:36.
 *
 * @since 1.0
 */
@Controller
public class AppointmentController {
    @Autowired
    private AppointmentService service;

    @Autowired
    private DmsRequestService dmsRequestService;





    //按关键字分页查询对象
    @ApiService(transCode = "appointmentPage")
    public Page<AppointmentPageResp> findPage(AppointmentPageReq req) {
        Page<Appointment> pages = service.findByGridPage(req.getPage());
        Page<AppointmentPageResp> resp = pages.map(new Converter<Appointment, AppointmentPageResp>() {
            public AppointmentPageResp convert(Appointment source) {
            AppointmentPageResp a = new AppointmentPageResp();
            BeanUtils.copyProperties(source, a);
                //TODO
            return a;
            }
        });
        return resp;
    }

    /*请求时间段*/
    @RequestMapping(value = "getAviableTime")
    @ResponseBody
    public String getAviableTime(AppointmentHandleReq req){
        WireframeDmsRequest request = new WireframeDmsRequest();
        /*TODO */
        request.setUser_name(null);
        request.setPassword(null);
        /**/
        request.setBusiness_type(WireframeDmsRequest.WBO1);
        request.setSource(WireframeDmsRequest.MX);
        ArrayList<HashMap> content = transferRequestContent(req);
        request.setContent(content);
        WireframeDmsResponse wireframeDmsResponse = dmsRequestService.sourceNumSyn(request);
        /*TODO 处理返回参数*/
        return  JSONUtil.objectToJson(wireframeDmsResponse);
    }

    public ArrayList<HashMap> transferRequestContent(AppointmentHandleReq req){
        String appointDate = req.getAppointDate();
        String dealerName = req.getDealerName();
        HashMap<String, String> content = new HashMap<>();
        content.put("date",appointDate);
        /*TODO dealerName --->service_code */
        content.put("dealer_service_code",dealerName);
        ArrayList<HashMap> contents = new ArrayList<>();
        contents.add(content);
        return contents;
    }


    @RequestMapping(value = "createAppoint")
    public String createAppoint(AppointmentHandleReq req){
        Appointment appointment = new Appointment();
        BeanUtils.copyProperties(req,appointment);
        /*放在同一个事务中*/
        String saveMessage = service.save(appointment);
        return saveMessage;
    }
    @RequestMapping(value = "updateAppoint")
    public String updateAppoint(AppointmentHandleReq req){
        Appointment appointment = new Appointment();
        BeanUtils.copyProperties(req,appointment);
         /*放在同一个事务中*/
        service.update(appointment);
        return  null;
    }


}