/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * ActivityEntityController.java
 */
package com.ford.wechat.controller.pc.activity;

import com.ford.wechat.controller.pc.activity.vo.ActivityDeleteReq;
import com.ford.wechat.controller.pc.activity.vo.ActivityHandleReq;
import com.ford.wechat.controller.pc.activity.vo.ActivityPageReq;
import com.ford.wechat.controller.pc.activity.vo.ActivityPageResp;
import com.ford.wechat.controller.pc.menu.vo.IconMenuCacheReq;
import com.ford.wechat.entity.pc.activity.ActivityEntity;
import com.ford.wechat.service.pc.activity.ActivityEntityService;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import io.dabing.redis.client.BinaryRedisClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;

import java.util.List;


/**
 * 描述:ActivityEntityController CRUD 逻辑控制层
 *
 * @author ziv
 * @since 1.0
 */
@Controller
public class ActivityController {

    @Autowired
    private ActivityEntityService service;

    /**
     * 按关键字分页查询对象
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "activityPage")
    public Page<ActivityPageResp> activityPage(ActivityPageReq req) {
        Page<ActivityEntity> pages = service.pagingBy(req.getPage());
        Page<ActivityPageResp> retval = pages.map(new Converter<ActivityEntity, ActivityPageResp>() {
            public ActivityPageResp convert(ActivityEntity source) {
                ActivityPageResp a = new ActivityPageResp();
                BeanUtils.copyProperties(source, a);
                return a;
            }
        });
        return retval;
    }

    /**
     * 创建/修改对象处理
     *
     * @param req 请求入参对象
     */
    @ApiService(transCode = "activityHandle")
    public void activityHandle(ActivityHandleReq req) {
        ActivityEntity entity = new ActivityEntity();
        if (req.getId() != null) {
            entity = service.get(req.getId());
        }
        BeanUtils.copyProperties(req, entity);
        if (req.getId() != null) {
            service.update(entity);
        } else {
            service.save(entity);
        }
    }

    /**
     * 获取活动信息
     *
     * @param req 请求入参对象
     */
    @ApiService(transCode = "activityGet")
    public ActivityPageResp activityGet(ActivityHandleReq req) {
        ActivityEntity entity = service.get(req.getId());
        ActivityPageResp resp = new ActivityPageResp();
        BeanUtils.copyProperties(entity, resp);
        return resp;
    }


    @Autowired
    private BinaryRedisClient binaryRedisClient;

    /**
     * 一键缓存redis
     *
     * @param req 请求入参对象
     */
    @ApiService(transCode = "activityCache")
    public void activityCache(ActivityHandleReq req) {
        List<ActivityEntity> activityEntityList = service.findAll();
        binaryRedisClient.set(("FC:PC:ACTIVITY:ALL"), activityEntityList);
    }

    /**
     * 物理删除对象处理,批量,单一删除均支持
     *
     * @param req 请求入参对象
     */
    @ApiService(transCode = "activityDelete")
    public void activityDelete(ActivityDeleteReq req) {
        for (Long id : req.getIds()) {
            service.delete(id);
        }
    }
}