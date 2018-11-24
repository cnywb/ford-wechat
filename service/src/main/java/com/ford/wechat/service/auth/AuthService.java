package com.ford.wechat.service.auth;

import com.ford.wechat.entity.user.WechatUser;

import java.util.Map;

/**
 * Created by wanglijun on 16/11/13.
 */
public interface AuthService {

    /**
     * @param code
     * @return
     */
    Map<String, String> getOpenId(String code);

    Map<String, String> getOpenIdByCode(String code);

    /**
     * @param openid
     * @return
     */
    WechatUser getUser(String openid, String accessToken);

}
