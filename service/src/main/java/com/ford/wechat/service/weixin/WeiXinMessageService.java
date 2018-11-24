package com.ford.wechat.service.weixin;

import java.util.Map;

/**
 * Created by yangkui on 15/11/4.
 * 所有与微信相关的公共服务
 */
public interface WeiXinMessageService {

    /**
     * 发送微信模板消息
     *
     * @param message 消息体和消息发送结果，具体有没有发送成功，这里无法获取，需要微信的回调接口才可以知道。
     * @return
     */
    void doSendMessage(WeiXinMessage message);

    /**
     * 根据授权的code 获取accessToken以及openId等信息
     *
     * @param openId 普通用户的标识，对当前开发者帐号唯一
     * @return 返回key参见：com.ford.wechat.service.weixin.impl.WeiXinEnum
     */
    Map<String, Object> gainUserInfo(String openId);
}
