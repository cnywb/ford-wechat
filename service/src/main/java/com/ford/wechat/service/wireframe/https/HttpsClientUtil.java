package com.ford.wechat.service.wireframe.https;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import io.dabing.common.util.HttpClientUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lc on 2017/9/18.
 */
public class HttpsClientUtil {

    private static final String CHARSET="UTF-8";

    /***
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(HttpsClientUtil.class);

    public static String doPost(String url,String content,String contentType){
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try{
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            //设置参数
            StringEntity myEntity = new StringEntity(content, CHARSET);
            httpPost.addHeader("Content-Type", contentType);
            httpPost.setEntity(myEntity);
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,CHARSET);
                }
            }
        }catch(Exception ex){
            writeLog (url,content);
        }
        return result;
    }

    private  static void  writeLog(String  url,String requestContent)  {
        logger.error("current request url error,url:{},requestContent:{}",url,requestContent);
    }
}
