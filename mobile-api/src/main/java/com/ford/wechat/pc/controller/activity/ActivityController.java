/*
 * Copyright (c)  2016, dabing.io
 * All rights reserved.
 * IconMenuController.java 2017-05-07 上午11:43
 */

package com.ford.wechat.pc.controller.activity;

import com.alibaba.fastjson.JSON;
import com.ford.wechat.entity.pc.activity.ActivityEntity;
import com.ford.wechat.pc.common.response.Response;
import com.ford.wechat.pc.controller.activity.vo.ActivityResp;
import io.dabing.redis.client.RedisClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:图标菜单控制器
 *
 * @author yangkui create on 2017-05-07.
 * @since 1.0
 */
@Controller
@RequestMapping("/api/public/activity")
public class ActivityController {

    @Autowired
    private RedisClient redisClient;

    @RequestMapping("/findAll")
    @ResponseBody
    public Response<List<ActivityResp>> findAll() {
        //查询系统设置

        String activityList = redisClient.get("FC:PC:ACTIVITY:ALL");
        List<ActivityEntity> result = JSON.parseArray(activityList, ActivityEntity.class);
        List<ActivityResp> response = new ArrayList<>();
        for (ActivityEntity entity : result) {
            ActivityResp resp = new ActivityResp();
            BeanUtils.copyProperties(entity, resp);
            response.add(resp);
        }
        return new Response<>(response);
    }
}