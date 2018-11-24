package com.ford.wechat.service.weixin.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.ford.wechat.Constant;
import com.ford.wechat.service.weixin.ParllayService;
import io.dabing.common.util.HttpClientUtil;
import io.dabing.common.util.PropUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Neel on 2017/7/6.
 */
@Slf4j
@Service
public class ParllayServiceImpl implements ParllayService {


    public static final String KEY_WEIXIN_ACCESS_TOKEN = "FORD:WEIXIN:ACCESS:TOKEN";

    @Value("${parllay.access-token.url}")
    private String url;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public String getAccessToken() {
        String accessToken = (String) redisTemplate.opsForValue().get(KEY_WEIXIN_ACCESS_TOKEN);
        if (!org.springframework.util.StringUtils.isEmpty(accessToken)) {
            return accessToken;
        }
        try {
            String json = HttpClientUtil.get(this.url, 6000, 6000, "UTF-8");
            if (!StringUtils.isEmpty(json) && json.indexOf("access_token") >= 0) {
                Map<String, String> map = (Map<String, String>) JSON.parse(json);

                accessToken = map.get("access_token");
                log.info("烽火台获取access_token成功: {}", accessToken);
                //默认缓存100分钟
                redisTemplate.opsForValue().set(KEY_WEIXIN_ACCESS_TOKEN, accessToken, 30, TimeUnit.MINUTES);
                return accessToken;
            }
            log.error("烽火台获取access_token失败 {}", json);
        } catch (Exception ex) {
            log.error("烽火台获取access_token异常");
            ex.printStackTrace();
        }
        return null;
    }
}
