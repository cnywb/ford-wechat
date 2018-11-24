package com.ford.wechat.service.weixin.impl;

import com.ford.wechat.service.weixin.AuthMessageService;
import com.ford.wechat.service.weixin.MessageData;
import com.ford.wechat.service.weixin.WeiXinMessage;
import com.ford.wechat.service.weixin.WeiXinMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Neel on 2017/6/19.
 */
@Slf4j
@Service
public class AuthMessageServiceImpl implements AuthMessageService {

    @Autowired
    private WeiXinMessageService weiXinMessageService;

    @Value("${weixin.message.authen.id}")
    private String authTemplateId;

    @Value("${weixin.message.authen.url}")
    private String authTemplateUrl;

    @Value("${weixin.message.mobile.id}")
    private String mobileTemplateId;

    @Value("${weixin.message.mobile.url}")
    private String mobileTemplateUrl;

    @Value("${weixin.message.unbind.id}")
    private String unbindTemplateId;

    @Value("${weixin.message.unbind.url}")
    private String unbindTemplateUrl;

    @Override
    public void sendAuthSuccessMessage(String openId, String vin, String time) {
        Map<String, MessageData> data = new HashMap<>();
        data.put("first", new MessageData("尊敬的车主您好！您车架号为" + vin + "的车辆已经完成长安福特微客服车主认证，我们即将为您开启全新服务体验，祝您生活愉快！"));
        data.put("keyword1", new MessageData("通过"));
        data.put("keyword2", new MessageData(time));
        data.put("remark", new MessageData("我们会积极保护隐私，不将您的个人信息泄露给第三方。"));

        WeiXinMessage message = new WeiXinMessage();
        message.setData(data);
        message.setTemplate_id(authTemplateId);
        message.setTouser(openId);
        message.setUrl(authTemplateUrl + "&openId=" + openId);

        this.weiXinMessageService.doSendMessage(message);
    }

    @Override
    public void sendAuthFailedMessage(String openId, String vin, String time) {
        Map<String, MessageData> data = new HashMap<>();
        data.put("first", new MessageData("尊敬的车主您好！您已经成功完成微信平台车主认证身份验证，长安福特为您开启全新服务体验，祝您生活愉快！"));
        data.put("keyword1", new MessageData("未通过"));
        data.put("keyword2", new MessageData(time));
        data.put("remark", new MessageData(""));

        WeiXinMessage message = new WeiXinMessage();
        message.setData(data);
        message.setTemplate_id(authTemplateId);
        message.setTouser(openId);
        message.setUrl(authTemplateUrl + "&openId=" + openId);

        this.weiXinMessageService.doSendMessage(message);
    }

    @Override
    public void sendUpdateMobileMessage(String openId, String oldMobile, String newMobile, String time) {
        Map<String, MessageData> data = new HashMap<>();
        data.put("first", new MessageData("您好，与您认证关联的手机号已经成功变更"));
        data.put("keyword1", new MessageData(oldMobile));
        data.put("keyword2", new MessageData(newMobile));
        data.put("keyword2", new MessageData(time));
        data.put("remark", new MessageData("感谢您一路对长安福特为客服的支持，祝您生活愉快！"));

        WeiXinMessage message = new WeiXinMessage();
        message.setData(data);
        message.setTemplate_id(mobileTemplateId);
        message.setTouser(openId);
        message.setUrl(mobileTemplateUrl + "&openId=" + openId);

        this.weiXinMessageService.doSendMessage(message);
    }

    @Override
    public void sendUnbindSuccessMessage(String openId, String vin, String time) {
        Map<String, MessageData> data = new HashMap<>();
        data.put("first", new MessageData("您好，您车架号为" + vin + "的车辆解绑申请已通过；"));
        data.put("keyword1", new MessageData(vin));
        data.put("keyword2", new MessageData(time));
        data.put("remark", new MessageData("您的车辆解绑申请已通过，感谢您使用长安福特微客服，如有任何疑问可在微客服中反馈。祝您生活愉快！"));

        WeiXinMessage message = new WeiXinMessage();
        message.setData(data);
        message.setTemplate_id(unbindTemplateId);
        message.setTouser(openId);
        message.setUrl(unbindTemplateUrl + "&openId=" + openId);

        this.weiXinMessageService.doSendMessage(message);
    }
    @Override
    public void sendUnbindFailedMessage(String openId, String vin, String time, String reason) {
        Map<String, MessageData> data = new HashMap<>();
        data.put("first", new MessageData("您好，您车架号为" + vin + "的车辆解绑申请未通过；"));
        data.put("keyword1", new MessageData(vin));
        data.put("keyword2", new MessageData(time));
        data.put("remark", new MessageData("您的车辆解绑申请未通过，原因是" + reason + "，如有任何疑问可在微客服中反馈。祝您生活愉快！"));

        WeiXinMessage message = new WeiXinMessage();
        message.setData(data);
        message.setTemplate_id(unbindTemplateId);
        message.setTouser(openId);
        message.setUrl(unbindTemplateUrl + "&openId=" + openId);

        this.weiXinMessageService.doSendMessage(message);
    }

    public void sendMessage(String openId,String templateId, String targetUrl, Map<String, MessageData> data) {
        WeiXinMessage message = new WeiXinMessage();
        message.setData(data);
        message.setTemplate_id(templateId);
        message.setTouser(openId);
        message.setUrl(targetUrl);
        this.weiXinMessageService.doSendMessage(message);
    }
}
