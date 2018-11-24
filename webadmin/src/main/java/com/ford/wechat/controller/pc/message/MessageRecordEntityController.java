/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * MessageRecordEntity.java 2017-05-05 15:37:27
 */
package com.ford.wechat.controller.pc.message;

import com.ford.wechat.controller.pc.message.vo.MessageRecordGetReq;
import com.ford.wechat.controller.pc.message.vo.MessageRecordGetResp;
import com.ford.wechat.controller.pc.message.vo.MessageRecordPageReq;
import com.ford.wechat.controller.pc.message.vo.MessageRecordPageResp;
import com.ford.wechat.entity.pc.message.MessageContentEntity;
import com.ford.wechat.entity.pc.message.MessageRecordEntity;
import com.ford.wechat.service.pc.message.MessageRecordEntityService;
import io.dabing.common.date.DateUtil;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;

/**
 * Created by yangkui on 2017-05-05 15:37:27.
 *
 * @since 1.0
 */
@Controller
public class MessageRecordEntityController {
    @Autowired
    private MessageRecordEntityService service;

    //按关键字分页查询对象
    @ApiService(transCode = "messageRecordPage")
    public Page<MessageRecordPageResp> messageRecordPage(MessageRecordPageReq req) {
        Page<MessageRecordEntity> pages = service.pagingAdminBy(req.getPage(), req.getVin(), req.getMsgClass(), req.getMsgType(), req.getPushChannel());

        Page<MessageRecordPageResp> resp = pages.map (new Converter<MessageRecordEntity, MessageRecordPageResp>() {
            public MessageRecordPageResp convert(MessageRecordEntity source) {
                MessageRecordPageResp a = new MessageRecordPageResp();
                BeanUtils.copyProperties(source, a);
                a.setCreatedDate(DateUtil.formatDate(source.getCreatedDate(), DateUtil.FORMAT_DATETIME_DEFAULT));
                a.setPublishTime(DateUtil.formatDate(source.getPublishTime(), DateUtil.FORMAT_DATETIME_DEFAULT));
                a.setInvalidTime(DateUtil.formatDate(source.getInvalidTime(), DateUtil.FORMAT_DATE_DEFAULT));
                return a;
            }
        });

        return resp;
    }

    //查看明细
    @ApiService(transCode = "messageRecordGet")
    public MessageRecordGetResp messageRecordGet(MessageRecordGetReq req) {
        MessageRecordEntity entity = service.get(req.getId());
        MessageContentEntity contentEntity = entity.getContent();
        String content = contentEntity.getContent();
        MessageRecordGetResp resp = new MessageRecordGetResp();
        BeanUtils.copyProperties(entity, resp);
        resp.setPublishTime(DateUtil.formatDate(entity.getPublishTime(), DateUtil.FORMAT_DATETIME_DEFAULT));
        resp.setInvalidTime(DateUtil.formatDate(entity.getInvalidTime(), DateUtil.FORMAT_DATE_DEFAULT));
        resp.setContent(content);
        return resp;
    }
}