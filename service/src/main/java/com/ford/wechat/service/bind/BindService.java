package com.ford.wechat.service.bind;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Neel on 2017/5/16.
 */
public interface BindService {

    /**
     * 发送短信验证码
     * @param openId
     * @param mobile
     */
    void sendBindCode(String openId, String mobile);

    /**
     * 检查验证码是否正确
     * @param openId
     * @param mobile
     * @param code
     * @return
     */
    boolean checkBindCode(String openId, String mobile, String code);

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    int doOwnerAuthenForConsole(String channelCode, Integer channelType, String openId, /*String name,*/ String mobile, String vin, Long licenseImgId);

    int doOwnerAuthenForWechat(String channelCode, Integer channelType, String openId, /*String name,*/ String mobile, String vin, String ip, Long licenseImgId);

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    int doOwnerAutoAuthenForWechat(String channelCode, Integer channelType, String openId, String mobile, String source, String vin, String ip);
}
