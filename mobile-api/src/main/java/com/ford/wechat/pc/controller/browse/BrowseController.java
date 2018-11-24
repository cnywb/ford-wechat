/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageController.java
 */
package com.ford.wechat.pc.controller.browse;

import com.ford.wechat.entity.pc.PCRedisKeys;
import com.ford.wechat.entity.pc.menu.MenuReadRecordEntity;
import com.ford.wechat.entity.pc.message.MessageEnum;
import com.ford.wechat.entity.pc.message.MessageReadRecordEntity;
import com.ford.wechat.pc.common.response.Response;
import com.ford.wechat.pc.controller.browse.vo.BrowseReq;
import io.dabing.common.date.DateUtil;
import io.dabing.common.util.JSONUtil;
import io.dabing.redis.util.RedisKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;


/**
 * 描述:MessageController CRUD 逻辑控制层
 *
 * @author ziv
 * @since 1.0
 */
@Controller
@RequestMapping("/api/public/browse")
public class BrowseController {

    @Autowired
    private RedisTemplate redisTemplate;

    @ResponseBody
    @RequestMapping("/menu")
    public Response<Void> menu(@RequestBody BrowseReq req) {
        Date readDate = new Date();
        String readDateStr = DateUtil.formatDate(readDate, DateUtil.FORMAT_DATE_YYYYMMDD);
        String msgReadKey = RedisKeyUtils.build(PCRedisKeys.MENU_READ_RECORD, readDateStr);
        MenuReadRecordEntity menuReadRecordEntity = new MenuReadRecordEntity();
        menuReadRecordEntity.setMenuId(req.getId());
        menuReadRecordEntity.setMenuName(req.getName());
        menuReadRecordEntity.setMenuSort(req.getSort());
        menuReadRecordEntity.setReadTime(readDate);
        menuReadRecordEntity.setTypeName(req.getTypeName());
        this.redisTemplate.opsForZSet().add(msgReadKey, JSONUtil.toJson(menuReadRecordEntity), 1d);
        return new Response<>();
    }

    @ResponseBody
    @RequestMapping("/announcement")
    public Response<Void> announcement(@RequestBody BrowseReq req) {
        Date readDate = new Date();
        String readDateStr = DateUtil.formatDate(readDate, DateUtil.FORMAT_DATE_YYYYMMDD);
        String msgReadKey = RedisKeyUtils.build(PCRedisKeys.MSG_READ_RECORD, readDateStr);
        MessageReadRecordEntity readRecordEntity = new MessageReadRecordEntity();
        readRecordEntity.setMsgId(req.getId());
        readRecordEntity.setMsgTitle(req.getName());
        readRecordEntity.setReadTime(readDate);
        readRecordEntity.setSourceChannel(MessageEnum.SOURCE_CHANNEL_GRZX);
        this.redisTemplate.opsForZSet().add(msgReadKey, JSONUtil.toJson(readRecordEntity), 1d);
        return new Response<>();
    }


}