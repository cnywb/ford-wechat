/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * EmailConfigController.java
 */
package com.ford.wechat.controller.pc.complain;

import com.ford.wechat.controller.pc.complain.vo.EmailConfigGetResp;
import com.ford.wechat.controller.pc.complain.vo.EmailConfigHandleReq;
import com.ford.wechat.entity.pc.complain.EmailConfigEntity;
import com.ford.wechat.service.pc.complain.EmailConfigEntityService;
import io.dabing.common.date.DateUtil;
import io.dabing.core.web.annotation.ApiService;
import io.dabing.redis.client.BinaryRedisClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Date;


/**
 * 描述:EmailConfigController CRUD 逻辑控制层
 *
 * @author ziv
 * @since 1.0
 */
@Controller
public class EmailConfigController {

    @Autowired
    private EmailConfigEntityService service;

    private static final String EMAIL_CODE = "this is send email code";

    /**
     * 创建/修改对象处理
     *
     * @param req 请求入参对象
     */
    @ApiService(transCode = "emailConfigHandle")
    public void emailConfigHandle(EmailConfigHandleReq req) {
        EmailConfigEntity entity = service.getByCode(EMAIL_CODE);
        Date date = new Date();
        if (entity != null) {
            String sendEmail = entity.getSendEmail();
            String ccEmail = entity.getCcEmail();
            if (!sendEmail.equals(req.getSendEmail()) || !ccEmail.equals(req.getCcEmail())) {
                entity.setHistoryDate(DateUtil.formatDate(entity.getUpdatedDate(), DateUtil.FORMAT_DATETIME_DEFAULT));
                entity.setHistoryEmail(sendEmail);
                entity.setHistoryCCEmail(ccEmail);
                entity.setSendEmail(req.getSendEmail());
                entity.setCcEmail(req.getCcEmail());
                service.update(entity);
                this.putRedis(req.getSendEmail(), req.getCcEmail());
            }
        } else {
            entity = new EmailConfigEntity();
            entity.setCode(EMAIL_CODE);
            entity.setSendEmail(req.getSendEmail());
            entity.setCcEmail(req.getSendEmail());
            entity.setCreatedDate(date);
            entity.setUpdatedDate(date);
            service.save(entity);
            this.putRedis(req.getSendEmail(), req.getCcEmail());
        }
    }

    @Autowired
    private BinaryRedisClient binaryRedisClient;

    private void putRedis(String toMail, String ccMail) {
        binaryRedisClient.set("FC:PC:COMPLAIN:TOMAIL", toMail);
        binaryRedisClient.set("FC:PC:COMPLAIN:CCMAIL", ccMail);
    }

    /**
     * 创建/修改对象处理
     *
     * @param req 请求入参对象
     */
    @ApiService(transCode = "emailConfigGet")
    public EmailConfigGetResp emailConfigGet(EmailConfigHandleReq req) {
        EmailConfigEntity entity = service.getByCode(EMAIL_CODE);
        EmailConfigGetResp resp = new EmailConfigGetResp();
        if (entity != null) {
            BeanUtils.copyProperties(entity, resp);
            resp.setLastDate(DateUtil.formatDate(entity.getUpdatedDate(), DateUtil.FORMAT_DATETIME_DEFAULT));
        }
        return resp;
    }

}