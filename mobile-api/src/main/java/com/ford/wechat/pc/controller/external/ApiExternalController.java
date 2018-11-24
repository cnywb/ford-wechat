/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * ApiExternalController.java
 */

package com.ford.wechat.pc.controller.external;

import com.ford.wechat.entity.pc.message.MessageContentEntity;
import com.ford.wechat.entity.pc.message.MessageEnum;
import com.ford.wechat.entity.pc.message.MessageRecordEntity;
import com.ford.wechat.entity.pc.message.tempate.MessageTemplateEntity;
import com.ford.wechat.entity.pc.message.tempate.MessageTemplateParamsEntity;
import com.ford.wechat.pc.common.response.Response;
import com.ford.wechat.pc.controller.external.vo.MessageHandleReq;
import com.ford.wechat.pc.controller.external.vo.MessageHandleResp;
import com.ford.wechat.service.pc.message.MessageContentEntityService;
import com.ford.wechat.service.pc.message.MessageTemplateEntityService;
import com.ford.wechat.service.weixin.AuthMessageService;
import com.ford.wechat.service.weixin.MessageData;
import com.google.common.collect.Maps;
import io.dabing.common.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 描述:
 *
 * @author Yvan
 * @since 1.0
 */

@Controller
@RequestMapping("/api/external")
public class ApiExternalController {

    static final Logger logger = LoggerFactory.getLogger(ApiExternalController.class);

    @Autowired
    private MessageContentEntityService contentEntityService;
    @Autowired
    private MessageTemplateEntityService templateEntityService;
    @Autowired
    private AuthMessageService authMessageService;

    /**
     * 消息类型
     */
    private static Map<String, String> msgClassMap = Maps.newLinkedHashMap();
    private static final String MSG_CLASS_1 = "1";

    static {
        msgClassMap.put(MSG_CLASS_1, MessageEnum.TEMPLATE_TYPE_GR);
        msgClassMap.put("2", MessageEnum.TEMPLATE_TYPE_GG);
    }


    @ResponseBody
    @RequestMapping(value = "/msg/publish", method = RequestMethod.POST)
    public Response<MessageHandleResp> publish(@RequestBody MessageHandleReq req) {
        MessageHandleResp resp = new MessageHandleResp();

        if (StringUtils.isEmpty(req.getMsgTemplateCode())
                || StringUtils.isEmpty(req.getChannel())
                || StringUtils.isEmpty(req.getTitle())
                || StringUtils.isEmpty(req.getMsgClass())
                || StringUtils.isEmpty(req.getPublishTime())
                || StringUtils.isEmpty(req.getInvalidTime())
                || StringUtils.isEmpty(req.getContent())) {
            resp.setMessage("参数不全");
            resp.setCode(0);
            return new Response<>(resp);
        }
        MessageTemplateEntity templateEntity = templateEntityService.findByTemplateCode(req.getMsgTemplateCode());
        if (templateEntity == null) {
            resp.setMessage("消息模板不存在");
            resp.setCode(0);
            return new Response<>(resp);
        }

        List<MessageTemplateParamsEntity> paramsEntityList = templateEntityService.findByTemplateId(templateEntity.getId());

        StringBuffer sb = new StringBuffer();
        boolean flag = false;
        for (MessageTemplateParamsEntity paramsEntity : paramsEntityList) {
            Map<String, String> paramMap = req.getMsgTemplateParamMap();
            String paramsContent = paramMap.get(paramsEntity.getParamKey());
            if (StringUtils.isEmpty(paramsContent)) {
                flag = true;
                sb.append(paramsEntity.getParamKey() + ",");
            }
        }

        if (flag) {
            String message = (sb.toString() + ",").replace(",,", "") + " 数据为空";
            resp.setMessage("消息模板参数：" + message);
            resp.setCode(0);
            return new Response<>(resp);
        }

        if (msgClassMap.get(req.getMsgClass()) == null) {
            resp.setMessage("消息种类不存在");
            resp.setCode(0);
            return new Response<>(resp);
        }
        if (MSG_CLASS_1.equals(req.getMsgClass())) {
            if (StringUtils.isEmpty(req.getOpenId()) || StringUtils.isEmpty(req.getVin())) {
                resp.setMessage("个人消息：vin,openId必填");
                resp.setCode(0);
                return new Response<>(resp);
            }
            boolean vinValid = checkVin(req.getVin());
            if (!vinValid) {
                resp.setMessage("个人消息：vin码格式不正确");
                resp.setCode(0);
                return new Response<>(resp);
            }
        }

        if (!StringUtils.isEmpty(req.getLink())) {
            if (req.getLink().indexOf("http://") != 0 && req.getLink().indexOf("https://") != 0) {
                resp.setMessage("消息内容：非有效链接");
                resp.setCode(0);
                return new Response<>(resp);
            }
        }

        try {
            String useChannel = templateEntity.getUseChannel();
            if (useChannel.contains(MessageEnum.PUSH_CHANNEL_WXPT)) {//微信平台 发送微信不存储消息。
                sendWeiXinMsg(req.getMsgTemplateParamMap(), req.getOpenId(), req.getMsgTemplateCode(), req.getLink(), paramsEntityList);
            } else if (useChannel.contains(MessageEnum.PUSH_CHANNEL_GRZX)) {//个人中心 发送微信不存储消息。
                MessageContentEntity entity = new MessageContentEntity();
                entity.setMessageTemplate(templateEntity);
                entity.setContent(req.getContent());
                entity.setPushChannel(MessageEnum.PUSH_CHANNEL_GRZX);
                entity.setOpenId(req.getOpenId());
                entity.setVin(req.getVin());
                entity.setMsgClass(msgClassMap.get(req.getMsgClass()));
                BeanUtils.copyProperties(req, entity);
                MessageRecordEntity recordEntity = new MessageRecordEntity();
                BeanUtils.copyProperties(entity, recordEntity);
                recordEntity.setPushChannel(MessageEnum.PUSH_CHANNEL_GRZX);
                recordEntity.setSourceChannel(req.getChannel());
                recordEntity.setMsgSource(MessageEnum.MSG_SOURCE_INTERFACE);
                recordEntity.setMsgClass(msgClassMap.get(req.getMsgClass()));

                contentEntityService.save(entity, recordEntity);
                resp.setUniqueId(DateUtils.format(new Date(), DateUtils.FORMAT_DATE_TIME_YYYYMMDDHHMMSS) + "-" + entity.getId() + "-" + recordEntity.getId());
            } else if (useChannel.contains(MessageEnum.PUSH_CHANNEL_DXPT)) {//短信发送
            }
        } catch (Exception e) {
            logger.error("数据处理异常！", e);
            resp.setCode(0);
            resp.setMessage("消息发布失败！");
        }
        return new Response<>(resp);
    }

    private void sendWeiXinMsg(Map<String, String> paramMap, String openId, String templateCode, String redirectUrl, List<MessageTemplateParamsEntity> paramsEntityList) {
        Map<String, MessageData> data = Maps.newLinkedHashMap();
        for (MessageTemplateParamsEntity paramsEntity : paramsEntityList) {
            data.put(paramsEntity.getParamKey(), new MessageData(paramMap.get(paramsEntity.getParamKey())));
        }
        this.authMessageService.sendMessage(openId, templateCode, redirectUrl, data);
    }


    private static boolean checkVin(String vin) {
        if (vin.length() != 17) {
            return false;
        }
        String prefix = vin.substring(0, 3).toUpperCase();
        if (!"LVS".equals(prefix) && !"LVR".equals(prefix)) {
            return false;
        }

        String endstr = vin.substring(11, 17);
        String regEnd = "^[0-9]*[1-9][0-9]*$";
        if (!Pattern.compile(regEnd).matcher(endstr).find()) {
            return false;
        }
        String reg11 = "^[A-Z]+$";
        String eleven = vin.substring(10, 11);
        if (!Pattern.compile(reg11).matcher(eleven).find()) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(checkVin("LVSHCFAE9BF7549451"));
        System.out.println(checkVin("LVNHCFAE9BF754945"));

        System.out.println(checkVin("LVRHCFAE9BF754N45"));
        System.out.println(checkVin("LVSHCFAE9Ba754945"));

        System.out.println(checkVin("LVRHCFAE9BF754945"));
        System.out.println(checkVin("LVSHCFAE9BF754945"));
        System.out.println("http://1111".indexOf("http://"));
    }
}