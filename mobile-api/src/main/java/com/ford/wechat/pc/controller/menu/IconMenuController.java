/*
 * Copyright (c)  2016, dabing.io
 * All rights reserved.
 * IconMenuController.java 2017-05-07 上午11:43
 */

package com.ford.wechat.pc.controller.menu;

import com.alibaba.fastjson.JSON;
import com.ford.wechat.entity.im.ImCodeList;
import com.ford.wechat.entity.pc.menu.IconMenuEntity;
import com.ford.wechat.pc.common.response.Response;
import com.ford.wechat.pc.controller.menu.vo.FindAllResp;
import com.ford.wechat.pc.controller.menu.vo.FindIndexMenuReq;
import com.ford.wechat.pc.controller.menu.vo.FindIndexMenuResp;
import com.ford.wechat.service.im.ImCodeListService;
import com.ford.wechat.service.pc.menu.IconMenuEntityService;
import io.dabing.common.date.DateUtil;
import io.dabing.redis.client.RedisClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 描述:图标菜单控制器
 *
 * @author yangkui create on 2017-05-07.
 * @since 1.0
 */
@Controller
@RequestMapping("/api/public/menu")
public class IconMenuController {

    @Autowired
    private IconMenuEntityService iconMenuEntityService;

    @Autowired
    private ImCodeListService imCodeListService;

    @RequestMapping("/findAll")
    @ResponseBody
    public Response<List<FindAllResp>> findAll() {
        List<FindAllResp> retval = new ArrayList<>();
        List<IconMenuEntity> result = iconMenuEntityService.findAllNoCache();
        for (IconMenuEntity entity : result) {
            FindAllResp resp = new FindAllResp();
            BeanUtils.copyProperties(entity, resp);
            //获取分类相关字段
            ImCodeList type = imCodeListService.getByTypeCodeAndCode("iconMenuType", entity.getTypeCode());
            resp.setTypeId(type.getId());
            resp.setTypeName(type.getNameCn());
            resp.setTypeOrder(type.getSortNo());
            resp.setRecommendEndTime(DateUtil.formatDefaultDate(entity.getRecommendEndTime()));
            retval.add(resp);
        }
        //将所有分类数据一并返回，供页面渲染使用.用同一个对象来承载分类数据。页面根据属性值判断是何种类型的数据。
        List<ImCodeList> types = imCodeListService.findByTypeCode("iconMenuType");
        for (ImCodeList im : types) {
            FindAllResp resp = new FindAllResp();
            resp.setTypeId(im.getId());
            resp.setTypeName(im.getNameCn());
            resp.setTypeOrder(im.getSortNo());
            retval.add(resp);
        }

        return new Response<>(retval);
    }

    @Autowired
    private RedisClient redisClient;

    @RequestMapping("/findIndexMenu")
    @ResponseBody
    public Response<List<FindIndexMenuResp>> findIndexMenu(@RequestBody FindIndexMenuReq req) {
        //查询系统设置

        String periodList = redisClient.get("FC:PC:MENU:ALL");
        List<IconMenuEntity> result = JSON.parseArray(periodList, IconMenuEntity.class);
        List<FindIndexMenuResp> response = new ArrayList<>();
        for (IconMenuEntity entity : result) {
            //推荐时间晚于当前时间的才显示，否则不显示
            if (entity.getRecommendEndTime() != null && entity.getRecommendEndTime().before(new Date())) {
                continue;
            }
            FindIndexMenuResp resp = new FindIndexMenuResp();
            BeanUtils.copyProperties(entity, resp);
            response.add(resp);
        }
        return new Response<>(response);
    }
}