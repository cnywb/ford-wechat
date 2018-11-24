/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageContentEntityController.java
 */
package com.ford.wechat.controller.pc.message;

import com.ford.wechat.controller.pc.message.vo.*;
import com.ford.wechat.entity.pc.message.MessageContentEntity;
import com.ford.wechat.entity.pc.message.MessageRecordEntity;
import com.ford.wechat.entity.pc.message.tempate.MessageTemplateEntity;
import com.ford.wechat.entity.pc.message.tempate.MessageTemplateParamsEntity;
import com.ford.wechat.service.pc.message.MessageContentEntityService;
import com.ford.wechat.service.pc.message.MessageTemplateEntityService;
import com.ford.wechat.service.pc.message.MessageTemplateParamsEntityService;
import com.google.common.collect.Lists;
import io.dabing.common.date.DateUtil;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;


/**
 * 描述:MessageContentEntityController CRUD 逻辑控制层
 *
 * @author ziv
 * @since 1.0
 */
@Controller
public class MessageContentEntityController {

    @Autowired
    private MessageContentEntityService service;


    @Autowired
    private MessageTemplateEntityService templateService;

    /**
     * 按关键字分页查询对象
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "messageContentPage")
    public Page<MessageContentEntityPageResp> messageContentPage(MessageContentEntityPageReq req) {
        Page<MessageContentEntity> pages = service.pagingBy(req.getPage(), req.getVin(), req.getMobile(), req.getMsClass(), req.getMsgType(), req.getPushChannel());

        Page<MessageContentEntityPageResp> retval = pages.map (new Converter<MessageContentEntity, MessageContentEntityPageResp>() {
            public MessageContentEntityPageResp convert(MessageContentEntity source) {
                MessageContentEntityPageResp resp = new MessageContentEntityPageResp();
                if (source != null) {
                    BeanUtils.copyProperties(source, resp);
                    resp.setPublishTime(DateUtil.formatDate(source.getPublishTime(), DateUtil.FORMAT_DATETIME_YYYY_MM_DD_HH_MM));
                    resp.setInvalidTime(DateUtil.formatDate(source.getInvalidTime(), DateUtil.FORMAT_DATETIME_YYYY_MM_DD_HH_MM));
                }
                return resp;
            }
        });

        return retval;
    }

    /**
     * 创建/修改对象处理
     *
     * @param req 请求入参对象
     */
    @ApiService(transCode = "messageContentHandle")
    public void messageContentHandle(MessageContentEntityHandleReq req) {
        MessageContentEntity entity = new MessageContentEntity();
//        MessageTemplateEntity templateEntity = templateService.get(req.getTemplateId());
        /*String content = templateEntity.getContent();
        List<Map<String, String>> tmpParamData = req.getTmpParamData();
        for (Map<String, String> mapData : tmpParamData) {
            for (String key : mapData.keySet()) {
                content = content.replace("${" + key + "}", mapData.get(key));
            }
        }*/
        BeanUtils.copyProperties(req,entity);
        String[] useChannels = req.getPushChannel().split(",");
        List<MessageRecordEntity> recordEntityList = Lists.newArrayList();
        for (String pushChannel : useChannels) {
            MessageRecordEntity recordEntity = new MessageRecordEntity();
            BeanUtils.copyProperties(entity, recordEntity);
            recordEntity.setPushChannel(pushChannel);
            recordEntity.setContent(entity);
            recordEntity.setMsgType(req.getMsgType());
            recordEntityList.add(recordEntity);
        }
        service.save(entity, recordEntityList);
    }

    //查看明细
    @ApiService(transCode = "messageContentGet")
    public MessageContentEntityGetResp messageContentGet(MessageContentEntityGetReq req) {
        MessageContentEntity entity = service.get(req.getId());
        MessageContentEntityGetResp resp = new MessageContentEntityGetResp();
        BeanUtils.copyProperties(entity, resp);
        resp.setInvalidTime(DateUtil.formatDate(entity.getInvalidTime(), DateUtil.FORMAT_DATETIME_YYYY_MM_DD_HH_MM));
        resp.setPublishTime(DateUtil.formatDate(entity.getPublishTime(), DateUtil.FORMAT_DATETIME_YYYY_MM_DD_HH_MM));
        return resp;
    }

    /**
     * 物理删除对象处理,批量,单一删除均支持
     *
     * @param req 请求入参对象
     */
    @ApiService(transCode = "messageContentDelete")
    public void messageContentDelete(MessageContentEntityDeleteReq req) {
        for (Long id : req.getIds()) {
            service.delete(id);
        }
    }
}