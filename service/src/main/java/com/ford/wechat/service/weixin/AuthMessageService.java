package com.ford.wechat.service.weixin;

import java.util.Map;

/**
 * 认证相关微信通知
 * Created by Neel on 2017/6/19.
 */
public interface AuthMessageService {

    /**
     * 认证成功消息
     */
    void sendAuthSuccessMessage(String openId, String vin, String time);

    /**
     * 认证失败消息
     */
    void sendAuthFailedMessage(String openId, String vin, String time);

    /**
     * 更新手机号消息
     */
    void sendUpdateMobileMessage(String openId, String oldMobile, String newMobile, String time);

    /**
     * 解绑成功消息
     */
    void sendUnbindSuccessMessage(String openId, String vin, String time);

    void sendUnbindFailedMessage(String openId, String vin, String time, String reason);


    /**
     * 统一发送 微信消息通知接口
     *
     * @param openId     收消息openid
     * @param templateId 发送使用模板Id
     * @param targetUrl  消息提醒点击跳转目标地址
     * @param data       发送的消息数据
     */
    void sendMessage(String openId, String templateId, String targetUrl, Map<String, MessageData> data);
}
