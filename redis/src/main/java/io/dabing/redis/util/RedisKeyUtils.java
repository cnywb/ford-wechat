package io.dabing.redis.util;

import java.text.MessageFormat;

/**
 * Created by wanglijun on 16/7/29.
 */
public class RedisKeyUtils {

    /***
     * 构建Key,根据key,params
     * @param key
     * @param params
     * @return
     */
    public static String  build(String key,Object... params){

       return  MessageFormat.format (key,params);
    }

    /****
     *
     * @param key
     * @param param
     * @return
     */
    public static String  build(String key,String param){
        return MessageFormat.format (key,new Object []{param});
    }

    /***
     *
     * @param key
     * @param param1
     * @param param2
     * @param param3
     * @return String
     */
    public static String  build(String key,String param1,String param2,String param3){
        return MessageFormat.format (key,new Object []{param1,param2,param3});
    }

    /***
     *
     * @param key
     * @param param1
     * @param param2
     * @return String
     */
    public static String  build(String key,String param1,String param2){
        return MessageFormat.format (key,new Object []{param1,param2});
    }
}