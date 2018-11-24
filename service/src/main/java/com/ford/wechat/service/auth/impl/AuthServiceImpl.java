package com.ford.wechat.service.auth.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.ford.wechat.Constant;
import com.ford.wechat.entity.user.WechatUser;
import com.ford.wechat.service.auth.AuthService;
import com.ford.wechat.service.token.AccessTokenService;
import io.dabing.common.util.HttpClientUtil;
import io.dabing.common.util.PropUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by wanglijun on 16/11/13.
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AccessTokenService accessTokenService;

    @Value("${weixin.authen.url}")
    private String weixinAuthUrl;

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    @Override
    public WechatUser getUser(String openid, String accessToken) {
        StringBuffer sb = new StringBuffer();
        sb.append("https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken);
        sb.append("&openid=" + openid + "&lang=zh_CN");
        try {
            String json = HttpClientUtil.get(sb.toString(), 120000, 120000, "UTF-8");
            if (!StringUtils.isEmpty(json) && json.indexOf("openid") >= 0) {
                Map<String, String> map = (Map<String, String>) JSON.parse(json);
                WechatUser user = new WechatUser();
                user.setOpenid(map.get("openid"));
                user.setNickname(map.get("nickname"));
                user.setImgurl(map.get("headimgurl"));
                user.setProvince(map.get("province"));
                user.setCity(map.get("city"));
                user.setSex(map.get("sex"));
                return user;
            }

        } catch (Exception ex) {

        }
        return null;
    }

    @Override
    public Map<String, String> getOpenIdByCode(String code) {
        logger.info ("code:{}",code);
        String url = this.weixinAuthUrl;
        url = url.replace("%s", code);
        logger.info(url);
        try {
            String json = HttpClientUtil.get(url, 120000, 120000, "UTF-8");
            logger.info ("json:{}",json);
            if (!StringUtils.isEmpty(json) && json.indexOf("openid") >= 0) {
                Map<String, String> map = (Map<String, String>) JSON.parse(json);
                return map;
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage(), ex);
        }
        return null;
    }


    @Override
    public Map<String, String> getOpenId(String code) {
        logger.info ("code:{}",code);
        String url = PropUtils.getPropertyValue(Constant.WECHAT_FILE, "auth_access_token_url").replace("%s", code);
        logger.info(url);
        try {
            String json = HttpClientUtil.get(url, 120000, 120000, "UTF-8");
            logger.info ("json:{}",json);
            if (!StringUtils.isEmpty(json) && json.indexOf("openid") >= 0) {
                Map<String, String> map = (Map<String, String>) JSON.parse(json);
                return map;
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage(), ex);
        }
        return null;
    }


    public static void main(String[] args) {
        String aa = "1%s!";
        System.out.println(aa.replace("%s", "aaa"));
    }
}
