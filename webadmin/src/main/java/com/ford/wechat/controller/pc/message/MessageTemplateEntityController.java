/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageTemplateEntityController.java
 */
package com.ford.wechat.controller.pc.message;

import com.ford.wechat.controller.pc.message.vo.*;
import com.ford.wechat.entity.pc.message.tempate.MessageTemplateEntity;
import com.ford.wechat.entity.pc.message.tempate.MessageTemplateParamsEntity;
import com.ford.wechat.service.pc.message.MessageTemplateEntityService;
import com.google.common.collect.Lists;
import io.dabing.common.Assert;
import io.dabing.common.date.DateUtil;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;

import java.util.List;


/**
 * 描述:MessageTemplateEntityController CRUD 逻辑控制层
 *
 * @author ziv
 * @since 1.0
 */
@Controller
public class MessageTemplateEntityController {

    @Autowired
    private MessageTemplateEntityService service;

    /**
     * 按关键字分页查询对象
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "messageTemplatePage")
    public Page<MessageTemplateEntityPageResp> messageTemplatePage(MessageTemplateEntityPageReq req) {
        Page<MessageTemplateEntity> pages = service.pagingBy(req.getPage(), req.getCode(), req.getName(), req.getTemplateType(), req.getUseChannel());


        Page<MessageTemplateEntityPageResp> retval = pages.map (new Converter<MessageTemplateEntity, MessageTemplateEntityPageResp>() {
            public MessageTemplateEntityPageResp convert(MessageTemplateEntity source) {
                MessageTemplateEntityPageResp resp = new MessageTemplateEntityPageResp();
                if (source != null) {
                    BeanUtils.copyProperties(source, resp);
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
    @ApiService(transCode = "messageTemplateHandle")
    public void messageTemplateHandle(MessageTemplateEntityHandleReq req) {
        MessageTemplateEntity templateEntity = service.findByCodeAndIdNot(req.getCode(), req.getId());
        Assert.isNull(templateEntity, "模板代码已存在！");
        Assert.notNull(req.getParams(), "未提交模板参数！");
        MessageTemplateEntity entity = new MessageTemplateEntity();
        if (req.getId() != null) {
            entity = service.get(req.getId());
        }
        BeanUtils.copyProperties(req, entity);
        List<MessageTemplateParamsEntity> templateParamsEntityList = Lists.newArrayList();
        for (MessageTemplateParamsEntityVo voParam : req.getParams()) {
            MessageTemplateParamsEntity messageTemplateParamsEntity = new MessageTemplateParamsEntity();
            messageTemplateParamsEntity.setParamKey(voParam.getParamKey());
            messageTemplateParamsEntity.setKeyDesc(voParam.getKeyDesc());
            templateParamsEntityList.add(messageTemplateParamsEntity);
        }
        if (req.getId() != null) {
            service.update(entity, templateParamsEntityList);
        } else {
            service.save(entity, templateParamsEntityList);
        }
    }

    /**
     * 创建/修改对象处理
     *
     * @param req 请求入参对象
     */
    @ApiService(transCode = "messageTemplateParamGet")
    public List<MessageTemplateParamsEntityVo> messageTemplateParamGet(MessageTemplateParamGetReq req) {
        List<MessageTemplateParamsEntityVo> voList = Lists.newArrayList();
        Assert.notNull(req.getTemplateId(), "未选择模板！");
        List<MessageTemplateParamsEntity> dbList = service.findByTemplateId(req.getTemplateId());
        for (MessageTemplateParamsEntity entity : dbList) {
            MessageTemplateParamsEntityVo vo = new MessageTemplateParamsEntityVo();
            BeanUtils.copyProperties(entity, vo);
            voList.add(vo);
        }
        return voList;
    }

    /**
     * 物理删除对象处理,批量,单一删除均支持
     *
     * @param req 请求入参对象
     */
    @ApiService(transCode = "messageTemplateDelete")
    public void messageTemplateDelete(MessageTemplateEntityDeleteReq req) {
        for (Long id : req.getIds()) {
            service.delete(id);
        }
    }
}