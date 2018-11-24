package com.ford.test;

import com.alibaba.fastjson.JSON;
import com.ford.wechat.entity.auth.AuthToDms;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import io.dabing.common.exception.BusinessException;
import okhttp3.*;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoliang on 2017/5/22.
 */
public class WeAuthToDmsTest {

/*    @Autowired
    private WeAuthToDmsService weAuthToDmsService;

    static Logger logger = LoggerFactory.getLogger(WeAuthToDmsTest.class);

    @Test
    public void httpPost(){

        List<AuthToDms> weAuthToDmsList =  weAuthToDmsService.findAll();

        List<Map<String, String>> mapContentList = Lists.newLinkedList();

        for (AuthToDms weAuthToDms : weAuthToDmsList) {
            Map<String, String> reqMap = new HashMap<String, String>();
            reqMap.put("USER_ID", weAuthToDms.getUserId());
            reqMap.put("NAME", weAuthToDms.getName());
            reqMap.put("MOBILE", weAuthToDms.getMobile());
            reqMap.put("VIN", weAuthToDms.getVin());
            reqMap.put("OPEN_ID", weAuthToDms.getOpenId());
            reqMap.put("CHANNEL_CODE", weAuthToDms.getChannelCode());
            reqMap.put("CHANNEL_NAME", weAuthToDms.getChannelName());
            reqMap.put("DEALER_SERVICE_CODE", weAuthToDms.getDealerServiceCode());//需要查询数据库获得得到经销商代码
            reqMap.put("DEALER_NAME", weAuthToDms.getDealerName());
            reqMap.put("DATE_NO", weAuthToDms.getDateNo());
            reqMap.put("BIG_AREA", weAuthToDms.getBigArea());
            reqMap.put("SMALL_AREA", weAuthToDms.getSmallArea());
            reqMap.put("BATCH_NO", weAuthToDms.getBatchNo());
            mapContentList.add(reqMap);
        }
            Gson gosn = new Gson();
            String bodyString = null;
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, gosn.toJson(mapContentList));

                Request request = new Request.Builder()
                        .url("http://192.168.1.111/post")
                        .post(body)
                        .addHeader("content-type", "application/json")
                        .build();
            try{
                Response response = client.newCall(request).execute();
                bodyString = request.body().toString();
                System.out.println(response);
                System.out.println(bodyString);
            }catch(IOException e){
                e.printStackTrace();
                throw new BusinessException("dms 接口请求异常");
            }
            Map dmsResp = JSON.parseObject(bodyString,Map.class);

        if (!"0".equals(dmsResp.get("status_code"))) {
            logger.info("DMS回传接口返回数据异常！", JSON.toJSON(dmsResp));
            throw new BusinessException("DMS回传接口返回数据异常！FC不做任何处理！");
        }
        List<Map<String, String>> contentMapList = (List<Map<String, String>>) dmsResp.get("content");
        for (AuthToDms orderDms : weAuthToDmsList) {
            orderDms.setSendDmsStatus(0);
            orderDms.setSendDmsDate(new Date());
            orderDms.setRemark("发送失败!");
            if (contentMapList == null || contentMapList.size() == 0) {
                orderDms.setSendDmsStatus(AuthToDms.SEND_DMS_FAIL);
                orderDms.setRemark("发送成功");
            } else {
                        orderDms.setRemark("发送失败");
                        break;
                }
            }

       weAuthToDmsService.update(weAuthToDmsList);

    }*/
}
