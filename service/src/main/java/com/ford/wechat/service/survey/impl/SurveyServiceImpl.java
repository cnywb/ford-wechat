package com.ford.wechat.service.survey.impl;

import com.alibaba.fastjson.JSON;
import com.ford.wechat.entity.survey.SurveyConfig;
import com.ford.wechat.service.hub.RedirectionService;
import com.ford.wechat.service.survey.SurveyConfigService;
import com.ford.wechat.service.survey.SurveyService;
import com.ford.wechat.service.survey.response.Survey;
import com.ford.wechat.service.survey.response.SurveyPageResponse;
import com.ford.wechat.service.survey.response.SurveyResultPageResponse;
import io.dabing.common.exception.BusinessException;
import io.dabing.common.grid.GridPage;
import io.dabing.common.util.MD5Utils;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;


@Slf4j
@Service
public class SurveyServiceImpl implements SurveyService, io.dabing.core.service.Service {

    @Value("${wenjuan.client.url}")
    private String clientUrl;

    @Value("${wenjuan.api.url}")
    private String url;

    @Value("${wenjuan.api.user}")
    private String userId;

    @Value("${wenjuan.api.appKey}")
    private String appKey;

    @Value("${wenjuan.api.appSecret}")
    private String appSecret;

    private final static String DATA_TYPE = "json";



    @Value("${hub.survey.oauth.url}")
    private String surveyOAuthUrl;

    @Value("${hub.survey.client.url}")
    private String surveyUrl;

    @Value("${hub.survey.callback.url}")
    private String callbackUrl;

    @Value("${hub.oauth.url}")
    private String oauthUrl;

    @Autowired
    private RedirectionService redirectionService;


    @Autowired
    private SurveyConfigService surveyConfigService;



    public Page<Map> pagingDetailBy(String shortId, GridPage gridPage) {
        String detailListUrl = this.url + "get_rspd_detail_list/?wj_appkey=%s&wj_page=%d&wj_pagesize=%d&wj_short_id=%s&wj_timestamp=%s&wj_user=%s&wj_signature=%s";


        OkHttpClient client = new OkHttpClient();

        String timestamp = String.valueOf(new Date().getTime());
        timestamp = timestamp.substring(0, timestamp.length() - 3);

        String signature = MD5Utils.digest(appKey + String.valueOf(gridPage.getPageNumber()) + String.valueOf(gridPage.getPageSize()) + shortId + timestamp + userId + appSecret);


        String url = String.format(detailListUrl, appKey, gridPage.getPageNumber(), gridPage.getPageSize(), shortId, timestamp, userId, signature);

        log.debug(url);

        Request request = new Request.Builder().url(url).get().build();

        try {
            Response response = client.newCall(request).execute();
            String body = response.body().string();
            log.debug(body);
            SurveyResultPageResponse result = JSON.parseObject(body, SurveyResultPageResponse.class);

            Page<Map> page = new Page<>(result.getRspd_list(), PageRequest.newPage(result.getCurrent_page(), gridPage.getPageSize()), result.getTotal_count());

            log.debug("=====");
            return page;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("查询失败");
        }
    }


    public Page<Survey> pagingBy(Integer status, GridPage gridPage) {
        String listUrl = this.url + "get_proj_list/?wj_appkey=%s&wj_page=%d&wj_pagesize=%d&wj_timestamp=%s&wj_signature=%s";


        OkHttpClient client = new OkHttpClient();

        String timestamp = String.valueOf(new Date().getTime());
        timestamp = timestamp.substring(0, timestamp.length() - 3);

        String signature = MD5Utils.digest(appKey + String.valueOf(gridPage.getPageNumber()) + String.valueOf(gridPage.getPageSize()) + timestamp + appSecret);

        String url = String.format(listUrl, appKey, gridPage.getPageNumber(), gridPage.getPageSize(), timestamp, signature);

        log.debug(url);

        Request request = new Request.Builder().url(url).get().build();

        try {
            Response response = client.newCall(request).execute();
            String body = response.body().string();
            log.debug(body);
            SurveyPageResponse result = JSON.parseObject(body, SurveyPageResponse.class);

            Page<Survey> page = new Page<>(result.getProject_list(), PageRequest.newPage(result.getCurrent_page(), gridPage.getPageSize()), result.getTotal_count());

            log.debug("=====");
            return page;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("查询失败");
        }
    }



    @Override
    public Map getDetailByShortIdAndSource(String shortId, String source) {
        String detailUrl = this.url + "get_rspd_detail/?wj_appkey=%s&wj_datatype=%s&wj_respondent=%s&wj_short_id=%s&wj_timestamp=%s&wj_user=%s&wj_signature=%s";

        OkHttpClient client = new OkHttpClient();

        String timestamp = String.valueOf(new Date().getTime());
        timestamp = timestamp.substring(0, timestamp.length() - 3);

        String signature = MD5Utils.digest(appKey + DATA_TYPE + source + shortId + timestamp + userId + appSecret);

        String url = String.format(detailUrl, appKey, DATA_TYPE, source, shortId, timestamp, userId, signature);

        log.debug(url);

        Request request = new Request.Builder().url(url).get().build();

        try {
            Response response = client.newCall(request).execute();
            String body = response.body().string();
            log.debug(body);
            Map result = JSON.parseObject(body, Map.class);

            log.debug("=====");
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("查询失败");
        }
    }


    /**
     * 获取问卷hub回调地址
     * @return
     */
    @Override
    public String getHubCallbackUri() {

        return this.callbackUrl;
    }

    @Override
    public String getHubOAuthUri(String state) {
        return this.surveyOAuthUrl.replaceAll(":state", state);
    }


    @Override
    public String getSurveyUri(String shortId, String source) {

        SurveyConfig config = this.surveyConfigService.getItem(shortId);
        if (config == null){
            return null;
        }

        String callbackUri = "";
        if (config.getNeedCallback() != null && config.getNeedCallback().booleanValue()) {
            callbackUri = this.getHubCallbackUri();
        }
        String redirectUri = config.getRedirectUrl();

        String str = appKey + callbackUri + redirectUri + source + appSecret;
        log.info("加密前的字符串 str: {}", str);

        String signature = MD5Utils.digest(str);
        log.info("加密后的字符串 signature: {}", signature);

        String url = this.clientUrl + "s/"+ shortId + "/?wj_appkey=" + this.appKey + "&wj_respondent=" + source + (StringUtils.isEmpty(callbackUri) ? "" : "&wj_callback=" + callbackUri) + (StringUtils.isEmpty(redirectUri) ? "" : "&wj_redirect_uri=" + redirectUri) + "&wj_signature=" + signature;

        log.info("生成链接 url: {}", url);

        return url;
    }


}
