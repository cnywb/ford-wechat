package com.ford.wechat;/**
 * Created by jovi on 09/06/2017.
 */

import com.alibaba.fastjson.JSON;
import io.dabing.common.util.HttpClientUtil;
import org.junit.Test;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-06-09 10:42
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public class SubscribeTest {

    private static String WX_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={0}&openid={1}&lang=zh_CN";

    @Test
    public Boolean subscribe(){

        String accessToken = getAccessToken();
        String openId = "oULu_jqoNaMjwDEBtRhOkKTds43U";
        String url = MessageFormat.format(WX_URL,accessToken,openId);
        System.out.println(url);
        Subscribe subscribe=null;
        try {
            String result = HttpClientUtil.get(url, 30000, 30000, "UTF-8");
            System.out.println(result);
            subscribe = JSON.parseObject (result, Subscribe.class);
        }catch (IOException e){

        }

        if(subscribe!=null&&"1".equals (subscribe.getFlag ())){

            return true;
        }
        return false;
    }

    private String getAccessToken(){
        String infoUrl="http://www.changanfordwechat.com/getinfo?state=2";
        try {
            String accessToken = HttpClientUtil.get (infoUrl, 30000, 30000, "UTF-8");
            return accessToken;
        } catch (IOException e) {

        }
        return null;
    }


//    public Boolean isSubscribe(String openId){
//
//        Boolean  subscribe=getUserInfoState("5",openId);
//        return subscribe;
//    }
//
//    private Boolean getUserInfoState(String state, String openId){
//
//        String infoUrl="http://www.changanfordwechat.com/getinfo?state="+state+"&openid="+openId;
//        Subscribe subscribe=null;
//        try {
//            String result = HttpClientUtil.get (infoUrl, 30000, 30000, "UTF-8");
//            if(org.apache.commons.lang.StringUtils.isNotEmpty (result)) {
//                subscribe = JSON.parseObject (result, Subscribe.class);
//            }
//        } catch (IOException e) {
//
//        }
//
//        if(subscribe!=null&&"1".equals (subscribe.getFlag ())){
//
//            return true;
//        }
//        return false;
//    }
}
