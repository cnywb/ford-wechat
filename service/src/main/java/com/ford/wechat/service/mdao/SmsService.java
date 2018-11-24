package com.ford.wechat.service.mdao;

import com.ford.wechat.service.mdao.impl.SMSMessage;

/**
 * Created by Neel on 2017/5/15.
 */
public interface SmsService {

    String send(String mobile, String content);

    /**
     * 短信发送
     *
     * @param sms 只需要填写 手机号和消息即可
     * @return 发送失败抛出异常 ,成功则返回成功
     */
    String send(SMSMessage sms);
}
