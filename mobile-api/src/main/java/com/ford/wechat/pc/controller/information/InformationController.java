/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * InformationController.java
 */

package com.ford.wechat.pc.controller.information;

import com.ford.wechat.entity.user.CarInfo;
import com.ford.wechat.entity.user.UserInfo;
import com.ford.wechat.pc.common.UserUtil;
import com.ford.wechat.pc.common.response.Response;
import com.ford.wechat.pc.controller.information.vo.CarInfoHandleReq;
import com.ford.wechat.pc.controller.information.vo.CarInfoQueryResp;
import com.ford.wechat.pc.controller.information.vo.UserInfoHandleReq;
import com.ford.wechat.pc.controller.information.vo.UserInfoQueryResp;
import com.ford.wechat.service.user.CarInfoService;
import com.ford.wechat.service.user.UserInfoService;
import io.dabing.common.date.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 描述: 车主信息以及车辆信息管理。
 *
 * @author ziv
 * @since 1.0
 */
@Controller
@RequestMapping("/api/private/info")
public class InformationController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private CarInfoService carInfoService;

    /**
     * 车主信息更新数据
     */
    @RequestMapping("/user")
    @ResponseBody
    public Response<Void> user(@RequestBody UserInfoHandleReq req) {
        String openId = UserUtil.get();
        UserInfo userInfo = userInfoService.findByOpenId(openId);
        if (userInfo == null) {
            userInfo = new UserInfo();
        }
        BeanUtils.copyProperties(req, userInfo);
        if (userInfo.getOpenId() != null) {
            userInfoService.update(userInfo);
        } else {
            userInfo.setOpenId(openId);
            userInfoService.save(userInfo);
        }
        return new Response<>();
    }

    /**
     * 车主信息数据查询
     */
    @RequestMapping("/user/query")
    @ResponseBody
    public Response<UserInfoQueryResp> userQuery() {
        String openId = UserUtil.get();
        UserInfo userInfo = userInfoService.findByOpenId(openId);
        if (userInfo == null) {
            userInfo = new UserInfo();
        }
        UserInfoQueryResp resp = new UserInfoQueryResp();
        BeanUtils.copyProperties(userInfo, resp);
        return new Response<>(resp);
    }

    /**
     * 车辆信息更新数据
     */
    @RequestMapping("/car")
    @ResponseBody
    public Response<Void> car(@RequestBody CarInfoHandleReq req) {
        String openId = UserUtil.get();
        CarInfo carInfo = carInfoService.findByOpenId(openId);
        if (carInfo == null) {
            carInfo = new CarInfo();
        }
        BeanUtils.copyProperties(req, carInfo);
        if (carInfo.getOpenId() != null) {
            carInfoService.update(carInfo);
        } else {
            carInfo.setOpenId(openId);
            carInfoService.save(carInfo);
        }
        return new Response<>();
    }

    /**
     * 车辆信息数据查询
     */
    @RequestMapping("/car/query")
    @ResponseBody
    public Response<CarInfoQueryResp> carQuery() {
        String openId = UserUtil.get();
        CarInfo carInfo = carInfoService.findByOpenId(openId);
        if (carInfo == null) {
            carInfo = new CarInfo();
        }
        CarInfoQueryResp resp = new CarInfoQueryResp();
        BeanUtils.copyProperties(carInfo, resp);
        if (carInfo.getBuyDate() != null) {
        }
        return new Response<>(resp);
    }
}