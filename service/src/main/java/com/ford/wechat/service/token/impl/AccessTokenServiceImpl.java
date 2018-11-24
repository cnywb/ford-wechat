/*
 * Copyright (c)  2016, newtouch.com
 * All rights reserved.
 * AccessTokenServiceImpl.java 2016-10-22 下午4:47
 */
package com.ford.wechat.service.token.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.ford.wechat.Constant;
import com.ford.wechat.entity.token.AccessToken;
import com.ford.wechat.entity.token.CardInfo;
import com.ford.wechat.entity.token.JsApiInfo;
import com.ford.wechat.respository.token.AccessTokenRepository;
import com.ford.wechat.service.token.AccessTokenService;
import io.dabing.common.grid.GridPage;
import io.dabing.common.util.HttpClientUtil;
import io.dabing.common.util.JsApiSign;
import io.dabing.common.util.PropUtils;
import io.dabing.common.util.WxCardSign;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import io.dabing.redis.client.BinaryRedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 描述：TODO
 *
 * @author wanglijun create on 2016-10-22
 * @since 1.0
 */
@Service
public class AccessTokenServiceImpl extends AbstractService implements AccessTokenService {
    @Autowired
    AccessTokenRepository repository;


    @Autowired
    private BinaryRedisClient binaryRedisClient;

    private static final Logger logger = LoggerFactory.getLogger(AccessTokenServiceImpl.class);

    /***
     * 零件名称查询KEY
     */
    private static final String WE_ACCESS_TOKEN = "WE:ACCESS_TOKEN";

    private static final String WE_JS_API = "WE:JS:API";

    private static final String WE_JS_TICKET = "WE:JS:TICKET";


    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<AccessToken> pagingBy(GridPage page) {
        return repository.pagingBy (page);
    }

    public void save(AccessToken object) {
        repository.save (object);
    }

    public void delete(List<AccessToken> objectList) {
        repository.delete (objectList);
    }

    public void delete(AccessToken object) {
        repository.delete (object);
    }

    public void delete(Long id) {
        repository.delete (id);
    }

    public void update(AccessToken object) {
        repository.update (object);
    }

    public AccessToken get(Long id) {
        return repository.get (id);
    }

    @Override
    public String getAccessToken() {
        //return getWechatToken();
        return getPallayToken();
    }

    String getPallayToken() {
        String url = PropUtils.getPropertyValue(Constant.WECHAT_FILE, "accesstoken_url");
        try {
            String json = HttpClientUtil.get(url, 6000, 6000, "UTF-8");
            if (!StringUtils.isEmpty(json) && json.indexOf("access_token") >= 0) {
                Map<String, String> map = (Map<String, String>) JSON.parse(json);
                return map.get("access_token");
            }
        } catch (Exception ex) {

        }
        return null;
    }

    String getWechatToken() {
        String token = (String) this.binaryRedisClient.getObject(WE_ACCESS_TOKEN);
        if (StringUtils.isEmpty(token)) {
            String url = PropUtils.getPropertyValue(Constant.WECHAT_FILE, "accesstoken_url");
            try {
                String json = HttpClientUtil.get(url, 6000, 6000, "UTF-8");
                if (!StringUtils.isEmpty(json) && json.indexOf("access_token") >= 0) {
                    Map<String, String> map = (Map<String, String>) JSON.parse(json);
                    token = map.get("access_token");
                    this.binaryRedisClient.setexObject(WE_ACCESS_TOKEN, token, 7000);
                }
            } catch (Exception e) {
            }
        }
        return token;
    }

    @Override
    public JsApiInfo getJsticket(String url) {

        String ticket = getJsApiTicket();
        Map<String, String> map = JsApiSign.sign(ticket, url);
        if (null != map) {
            JsApiInfo info = new JsApiInfo();
            info.setAppId(PropUtils.getPropertyValue(Constant.WECHAT_FILE, "appID"));
            info.setNonceStr(map.get("nonceStr"));
            info.setTimestamp(map.get("timestamp"));
            info.setSignature(map.get("signature"));

            return info;
        }
        return null;
    }

    public String getJsApiTicket() {
        String ticket = (String) this.binaryRedisClient.getObject(WE_JS_TICKET);
        if (StringUtils.isEmpty(ticket)) {
            String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + getAccessToken() + "&type=jsapi";//PropUtils.getPropertyValue(Constant.WECHAT_FILE, "jsticket_url");
            try {
                String json = HttpClientUtil.get(url, 6000, 6000, "UTF-8");
                if (!StringUtils.isEmpty(json) && json.indexOf("ticket") >= 0) {
                    Map<String, String> map = (Map<String, String>) JSON.parse(json);
                    ticket = map.get("ticket");
                    this.binaryRedisClient.setexObject(WE_JS_TICKET, ticket, 7000);
                }
            } catch (Exception ex) {

            }
        }
        return ticket;
    }

    @Override
    public CardInfo getCardTicket(String card_id) {
        WxCardSign sign = new WxCardSign();
        String timestamp = JsApiSign.create_timestamp();
        String card = getCard();
        sign.AddData(timestamp);
        sign.AddData(card);
        sign.AddData(card_id);
        if (!StringUtils.isEmpty(card)) {
            CardInfo info = new CardInfo();
            info.setSignature(sign.GetSignature());
            info.setTimestamp(timestamp);
            info.setTicket(card);
        }
        return null;
    }

    String getCard() {
        String cardticket = (String) this.binaryRedisClient.getObject(WE_JS_API);
        if (StringUtils.isEmpty(cardticket)) {
            StringBuffer sb = new StringBuffer();
            sb.append("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + getAccessToken());
            sb.append("&type=wx_card");
            try {
                String json = HttpClientUtil.get(sb.toString(), 6000, 6000, "UTF-8");
                if (!StringUtils.isEmpty(json) && json.indexOf("ticket") >= 0) {
                    Map<String, String> map = (Map<String, String>) JSON.parse(json);
                    cardticket = map.get("ticket");
                    this.binaryRedisClient.setexObject(WE_JS_API, cardticket, 7000);
                }
            } catch (Exception ex) {

            }

        }
        return cardticket;
    }

	@Override
	public String getAccessTokenWeixinByAppId() {
  		String appID = "wxbc330df0e013e079";//PropUtils.getPropertyValue(Constant.WECHAT_FILE, "appID");
  		String secret= "1d5a471f4b31f030ff4f02c95baa7e94";//PropUtils.getPropertyValue(Constant.WECHAT_FILE, "appsecret");
  	    logger.info("请求accesstoken getAccessTokenWeixinPSappID:{}", appID);
  	    logger.info("请求accesstoken getAccessTokenWeixinPS secret:{}", secret);
  		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" + "&grant_type=client_credential&appid=" +appID + "&secret=" +secret;
  	        try {
  	            logger.info("请求accesstoken url:{}", url);
  	            String json = HttpClientUtil.get(url, 30000, 30000, "UTF-8");
  	            logger.info("获取accesstoken返回值为：{}", json);
  	            Map<String, String> map = (Map<String, String>) JSON.parse(json);
  	            String  errcode = map.get("errcode");
  	            if (errcode != null) {
  	                logger.info("获取accesstoken异常,errorCode:{},errmsg:{}", errcode, map.get("errmsg"));
  	                return null;
  	            }
  	            return map.get("access_token");
  	        } catch (Exception e) {
  	            logger.error("获取accesstoken异常", e);
  	        }
  	        return null;
	}

}
