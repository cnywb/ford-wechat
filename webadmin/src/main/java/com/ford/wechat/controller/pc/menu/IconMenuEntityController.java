/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * IconMenuEntity.java 2017-05-05 15:37:27
 */
package com.ford.wechat.controller.pc.menu;

import com.ford.wechat.controller.pc.menu.vo.*;
import com.ford.wechat.entity.im.ImCodeList;
import com.ford.wechat.entity.pc.menu.IconMenuEntity;
import com.ford.wechat.service.im.ImCodeListService;
import com.ford.wechat.service.pc.menu.IconMenuEntityService;
import io.dabing.common.date.DateUtil;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import io.dabing.redis.client.BinaryRedisClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 *  Created by yangkui on 2017-05-05 15:37:27.
 *
 * @since 1.0
 */
@Controller
public class IconMenuEntityController {
    @Autowired
    private IconMenuEntityService service;

    @Autowired
    private ImCodeListService imCodeListService;

    //按关键字分页查询对象
    @ApiService(transCode = "iconMenuEntityPage")
    public Page<IconMenuEntityPageResp> findPage(IconMenuEntityPageReq req) {
        Page<IconMenuEntity> pages = service.findByGridPage(req.getPage());
        Page<IconMenuEntityPageResp> resp = pages.map(new Converter<IconMenuEntity, IconMenuEntityPageResp>() {
            public IconMenuEntityPageResp convert(IconMenuEntity source) {
            IconMenuEntityPageResp a = new IconMenuEntityPageResp();
            BeanUtils.copyProperties(source, a);
            a.setRecommendEndTime(DateUtil.formatDefaultDate(source.getRecommendEndTime()));
            return a;
            }
        });
        return resp;
    }
    //查询所有菜单
    @ApiService(transCode = "findAllIconMenus")
    public List<IconMenuFindAllResp> findAllIconMenus(String none){
        List<IconMenuFindAllResp> retval = new ArrayList<>();
        List<IconMenuEntity> result = service.findAllNoCache();
        for(IconMenuEntity entity :result){
            IconMenuFindAllResp resp = new IconMenuFindAllResp();
            BeanUtils.copyProperties(entity,resp);
            //获取分类相关字段
            ImCodeList type =  imCodeListService.getByTypeCodeAndCode("iconMenuType",entity.getTypeCode());
            resp.setTypeId(type.getId());
            resp.setTypeName(type.getNameCn());
            resp.setTypeOrder(type.getSortNo());
            resp.setRecommendEndTime(DateUtil.formatDefaultDate(entity.getRecommendEndTime()));
            retval.add(resp);
        }
        //将所有分类数据一并返回，供页面渲染使用
        List<ImCodeList> types = imCodeListService.findByTypeCode("iconMenuType");
        for(ImCodeList im:types){
            IconMenuFindAllResp resp = new IconMenuFindAllResp();
            resp.setTypeId(im.getId());
            resp.setTypeName(im.getNameCn());
            resp.setTypeOrder(im.getSortNo());
            retval.add(resp);
        }

        return retval;
    }

    //单一创建对象
    @ApiService(transCode = "iconMenuEntityHandle")
    public void create(IconMenuEntityHandleReq req) {
        IconMenuEntity object = new IconMenuEntity();
        if (req.getId() != null) {
            object = service.get(req.getId());
        }
        ImCodeList type =  imCodeListService.getByTypeCodeAndCode("iconMenuType",req.getTypeCode());
        object.setType(type);
        object.setTypeName(type.getNameCn());
        BeanUtils.copyProperties(req, object);
        if (req.getId() != null) {
            this.service.update(object);
        } else {
            this.service.save(object);
        }
    }

    @Autowired
    private BinaryRedisClient binaryRedisClient;

    /**
     * 一键缓存redis
     *
     * @param req 请求入参对象
     */
    @ApiService(transCode = "iconMenuCache")
    public void iconMunuCache(IconMenuCacheReq req) {
        List<IconMenuEntity> iconMenuList = service.findRecommendMenus();
        binaryRedisClient.set(("FC:PC:MENU:ALL"), iconMenuList);
    }


    //批量删除对象
    @ApiService(transCode = "iconMenuEntityDelete")
    public void delete(IconMenuEntityDeleteReq req) {
        for (Long id : req.getIds()) {
            service.delete(id);
        }
    }
}