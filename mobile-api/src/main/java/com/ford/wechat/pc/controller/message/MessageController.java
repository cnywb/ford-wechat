/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageController.java
 */
package com.ford.wechat.pc.controller.message;

import com.ford.wechat.entity.pc.message.MessageRecordEntity;
import com.ford.wechat.pc.common.response.Response;
import com.ford.wechat.pc.controller.message.vo.MessageDetailReq;
import com.ford.wechat.pc.controller.message.vo.MessageDetailResp;
import com.ford.wechat.pc.controller.message.vo.MessageListReq;
import com.ford.wechat.pc.controller.message.vo.MessageListResp;
import com.ford.wechat.service.pc.message.MessageRecordEntityService;
import io.dabing.common.Assert;
import io.dabing.common.date.DateUtil;
import io.dabing.core.repository.domain.Page;
import io.dabing.redis.client.BinaryRedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 描述:MessageController CRUD 逻辑控制层
 *
 * @author ziv
 * @since 1.0
 */
@Controller
@RequestMapping("/api/public/msg")
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageRecordEntityService recordEntityService;

    /**
     * 查询有效的公告消息列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public Response<Page<MessageListResp>> list(@RequestBody MessageListReq req) {
        Assert.notNull(req.getOpenId(), "请使用微信登陆");
        Page<MessageRecordEntity> pages = recordEntityService.pagingBy(req.getPage(),req.getOpenId(),null,null,null,null);
        Page<MessageListResp> retval = pages.map(new Converter<MessageRecordEntity, MessageListResp>() {
            @Override
            public MessageListResp convert(MessageRecordEntity record) {
                MessageListResp resp = new MessageListResp();
                resp.setId(record.getId());
                resp.setTitle(record.getTitle());
                resp.setPublishTime(DateUtil.formatDate(record.getPublishTime(), DateUtil.FORMAT_DATETIME_YYYY_MM_DD_HH_MM));
                String content = record.getContent().getContent();
                if (content.length() > 50) {
                    content = content.substring(0, 50) + "...";
                }
                resp.setContent(content);
                return resp;
            }
        });

        return  new Response<>(retval);
    }

    /**
     * 根据有效的公告消息ID获取明细信息
     */
    @ResponseBody
    @RequestMapping("/detail")
    public Response<MessageDetailResp> detail(@RequestBody MessageDetailReq req) {
        Assert.notNull(req.getOpenId(), "请使用微信");
        MessageDetailResp resp = new MessageDetailResp();
        MessageRecordEntity recordEntity = recordEntityService.get(req.getId());
        if (recordEntity == null) {
            logger.error("公告消息为空，公告ID{}", req.getId());
            return new Response<>(resp);
        }
        resp.setId(recordEntity.getId());
        resp.setTitle(recordEntity.getTitle());
        resp.setPublishTime(DateUtil.formatDate(recordEntity.getPublishTime(), DateUtil.FORMAT_DATETIME_YYYY_MM_DD_HH_MM));
        String content = recordEntity.getContent().getContent();
        resp.setContent(content);
        return new Response<>(resp);
    }
}