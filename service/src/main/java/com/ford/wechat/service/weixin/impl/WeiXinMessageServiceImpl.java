package com.ford.wechat.service.weixin.impl;

import com.alibaba.fastjson.JSON;
import com.ford.wechat.service.weixin.ParllayService;
import com.ford.wechat.service.weixin.WeiXinMessage;
import com.ford.wechat.service.weixin.WeiXinMessageService;
import com.google.common.collect.Maps;
import io.dabing.common.util.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by yangkui on 15/11/4.
 * 微信接口调用服务类
 */
@Service
public class WeiXinMessageServiceImpl implements WeiXinMessageService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String WEIXIN_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
    private static final String WEIXIN_SEND_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
    private static final String WEIXIN_USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info";

    /**
     * 微信公众平台提供的appId,两个公众号和微信支付对应的appId都不一致
     */
    @Value("${weixin.config.appId}")
    private String appId;

    /**
     * 微信公众平台提供的appSecret
     */
    @Value("${weixin.config.appSecret}")
    private String appSecret;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ParllayService parllayService;

    /**
     * 根据微信配置的类型获取对应的微信访问接口，默认会缓存100分钟。
     *
     * @return 调用微信所有接口之前的必备参数accessToken
     */
    private String getAccessToken() {
        String accessToken = (String) redisTemplate.opsForValue().get("WeiXin:accessToken");
        if (!StringUtils.isEmpty(accessToken)) {
            return accessToken;
        }

        String url = WEIXIN_ACCESS_TOKEN + "&grant_type=client_credential&appid=" + appId + "&secret=" + appSecret;
        try {
            logger.info("请求获取微信accesstoken url:{}", url);
            String retval = HttpClientUtil.get(url, 30000, 30000, "UTF-8");
            logger.info("请求获取微信accesstoken返回值为：{}", retval);
            Map<String, String> json = JSON.parseObject(retval, HashMap.class);
            String errcode = json.get("errcode");
            if (errcode != null) {
                logger.info("请求获取微信accesstoken异常,errorCode:{},errmsg:{}", errcode, json.get("errmsg"));
                return null;
            }
            accessToken = json.get("access_token");
            //默认缓存100分钟
            redisTemplate.opsForValue().set("WeiXin:accessToken", accessToken, 100, TimeUnit.MINUTES);
            return accessToken;
        } catch (Exception e) {
            logger.error("请求获取微信accesstoken异常", e);
        }

        return null;
    }


    /**
     * 发送微信消息
     *
     * @param message 消息对象
     */
    public void doSendMessage(WeiXinMessage message) {

        if (StringUtils.isEmpty(message.getTouser())) {
            logger.info(" openId 为空，不发送微信消息 touser:{}", message.getTouser());
            message.setErrCode("E00001");
            message.setErrMsg("openID 为空！");
            return;
        }
        String json = JSON.toJSONString(message);
        try {
            logger.info("微信模板消息发送报文 touser:{},requestJson:{}", message.getTouser(), json);
            String retval = HttpClientUtil.postJSON(WEIXIN_SEND_MESSAGE + this.parllayService.getAccessToken(), json);
            logger.info("微信模板消息返回报文 touser:{},retval:{}", message.getTouser(), retval);
            Map<String, Object> map = JSON.parseObject(retval, HashMap.class);
            String errmsg = (String) map.get("errmsg");
            if (!"ok".equals(errmsg)) {
                message.setErrCode(map.get("errcode").toString());
                message.setErrMsg(map.get("errmsg").toString());
            }
        } catch (Exception e) {
            logger.error("发送微信消息异常!", e);
            message.setErrCode("E00002");
            message.setErrMsg(e.getMessage());
        }
    }

    /**
     * 根据accessToken以及openId 获取用户个人信息`
     *
     * @param openId 普通用户的标识，对当前开发者帐号唯一
     * @return 返回key参见：com.ford.wechat.service.weixin.impl.WeiXinEnum
     */
    public Map<String, Object> gainUserInfo(String openId) {

        Map<String, Object> map = Maps.newHashMap();
        try {
            String accessToken = this.parllayService.getAccessToken();
            String url = WEIXIN_USER_INFO_URL + "?access_token=" + accessToken + "&openid=" + openId + "&lang=zh_CN";
            logger.info("get userInfo url:{}", url);
            String retval = HttpClientUtil.get(url, 30000, 30000, "UTF-8");
            logger.info("get userInfo retval:{}", retval);
            return JSON.parseObject(retval, HashMap.class);
        } catch (Exception e) {
            logger.error("获取userInfo异常, url:{}", WEIXIN_USER_INFO_URL, e);
            map.put(WeiXinEnum.ERR_CODE, "PC500");
            map.put(WeiXinEnum.ERR_MSG, "调用微信服务异常");
        }
        return map;
    }
}
