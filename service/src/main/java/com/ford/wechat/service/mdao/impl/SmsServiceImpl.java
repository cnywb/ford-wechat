package com.ford.wechat.service.mdao.impl;

import com.alibaba.fastjson.JSON;
import com.ford.wechat.service.mdao.SmsService;
import io.dabing.common.util.HttpClientUtil;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Neel on 2017/5/15.
 */
@Service
public class SmsServiceImpl implements SmsService {

    private static Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

    //    private static final String SMS_URL = "https://emap.mdao.com:8443/webservice/http/service";
//    private static final String SMS_URL = "http://120.26.107.97:8180/webservice/http/service";
    //    public static final String SMS_URL = "http://localhost:4444/webservice/http/service";
//    private static final String SMS_CID = "9000561";
//    private static final String SMS_UID = "admin";
//    private static final String SMS_PWD = "20160630";

    @Value("${sms.url}")
    private String smsUrl;
    @Value("${sms.cid}")
    private String smsCid;
    @Value("${sms.uid}")
    private String smsUid;
    @Value("${sms.pwd}")
    private String smsPwd;

    public String send(String mobile, String content) {
        SMSMessage s = new SMSMessage();
        s.setMobiles(new String[]{mobile});
        s.setContent(content);
        return send(s);
    }

    /**
     * 短信发送
     *
     * @param sms 只需要填写 手机号和消息即可
     * @return 发送失败抛出异常 ,成功则返回成功
     */
    /*public String send(SMSMessage sms) {
        *//**
         * 先设置Timestamp 取当前时间，
         * SMSMessage 内部会自动生成 Timestamp 要求字符串
         * 一定要在设置密码前面，密码加密用到 Timestamp
         * 密码md5加密也是SMSMessage 内部实现
         *
         *//*
        sms.setTimestamp(new Date());
        sms.setCid(this.smsCid);
        sms.setUid(this.smsUid);
        sms.setPwd(this.smsPwd);


        try {
            OkHttpClient client = new OkHttpClient();

            RequestBody body = new FormBody.Builder()
                    .add("op", "sendSm")
                    .add("message", JSON.toJSONString(sms))
                    .build();

            Request request = new Request.Builder()
                    .url(this.smsUrl)
//                    .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8")
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            String retval = response.body().string();
            if (retval != null && !"".equals(retval)) {
                logger.info("短信发送返回消息:{},mobile:{}", retval, sms.getMobiles());
                ResultMessage resultMessage = JSON.parseObject(retval, ResultMessage.class);
                int stateCode = resultMessage.getStateCode();
                if (stateCode == 0) {
                    return null;
                }
                logger.info("短信发送失败,原因:{},mobile:{}", resultMessage.getMessage(), sms.getMobiles());
                return "短信发送失败!原因:" + resultMessage.getMessage();
            } else {
                return "短信发送失败!原因:接口返回报文为空";
            }
        } catch (IOException e) {
            logger.error("短信发送异常:{},mobile:{}", e.getMessage(), sms.getMobiles(), e);
            return "短信发送异常!原因:" + e.getMessage();
        }
    }*/
    public String send(SMSMessage sms) {
        /**
         * 先设置Timestamp 取当前时间，
         * SMSMessage 内部会自动生成 Timestamp 要求字符串
         * 一定要在设置密码前面，密码加密用到 Timestamp
         * 密码md5加密也是SMSMessage 内部实现
         *
         */
        sms.setTimestamp(new Date());
        sms.setCid(this.smsCid);
        sms.setUid(this.smsUid);
        sms.setPwd(this.smsPwd);
        Map<String, String> map = new HashMap<>();
        map.put("op", "sendSm");
        map.put("message", JSON.toJSONString(sms));


        try {
            String retval = HttpClientUtil.post(this.smsUrl, map, 10000, 10000, "UTF-8");
            if (retval != null && !"".equals(retval)) {
                logger.info("短信发送返回消息:{},mobile:{}", retval, sms.getMobiles());
                ResultMessage resultMessage = JSON.parseObject(retval, ResultMessage.class);
                int stateCode = resultMessage.getStateCode();
                if (stateCode == 0) {
                    return null;
                }
                logger.info("短信发送失败,原因:{}, mobile:{}, code:{}", resultMessage.getMessage(), sms.getMobiles(), stateCode);
                return "短信发送失败!原因:" + resultMessage.getMessage() + " Code: " + stateCode;
            } else {
                return "短信发送失败!原因:接口返回报文为空";
            }
        } catch (IOException e) {
            logger.error("短信发送异常:{},mobile:{}", e.getMessage(), sms.getMobiles(), e);
            return "短信发送异常!原因:" + e.getMessage();
        } catch (Exception e) {
            logger.error("短信发送异常:{},mobile:{}", e.getMessage(), sms.getMobiles(), e);
            return "短信发送异常!原因:" + e.getMessage();
        }
    }
}
