package com.ford.wechat.entity.user;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanglijun on 16/11/25.
 */
@Data
public class CarOwnerAuthenStatusResult implements Serializable {


    /***/
    private static Map<String,String> RESULT = new HashMap();

    /**
     * 手机号格式不正确
     */
    public static final String MOBILE_NOT_VALID = "MOBILE_NOT_VALID";
    /**
     * 手机号格式已认证
     */
    public static final String MOBILE_ALREADY_EXIST = "MOBILE_ALREADY_EXIST";

    /**
     * VIN码格式不正确
     */
    public static final String VIN_NOT_VALID = "VIN_NOT_VALID";
    /**
     * VIN码已经被认证
     */
    public static final String VIN_ALREADY_AUTH = "VIN_ALREADY_AUTH";
    /**
     * 手机号错误
     */
    public static final String MOBILE_NOT_CORRECT = "MOBILE_NOT_CORRECT";
    /**
     * 认证成功
     */
    public static final String AUTH_SUCCESS = "AUTH_SUCCESS";
    /**
     * 认证失败
     */
    public static final String AUTH_FAIL = "AUTH_FAIL";
    /**
     * VIN码不存在
     */
    public static final String VIN_NOT_EXIST = "VIN_NOT_EXIST";

    static {
        RESULT.put(CarOwnerAuthenStatusResult.MOBILE_NOT_VALID, "手机号格式不正确");
        RESULT.put(CarOwnerAuthenStatusResult.VIN_NOT_VALID, "VIN码格式不正确");
        RESULT.put(CarOwnerAuthenStatusResult.VIN_ALREADY_AUTH, "VIN码已经被认证");
        RESULT.put(CarOwnerAuthenStatusResult.MOBILE_NOT_CORRECT, "手机号不正确");
        RESULT.put(CarOwnerAuthenStatusResult.MOBILE_ALREADY_EXIST, "手机号已认证");
        RESULT.put(CarOwnerAuthenStatusResult.VIN_NOT_EXIST, "VIN码不存在");
        RESULT.put(CarOwnerAuthenStatusResult.AUTH_SUCCESS, "认证成功");
        RESULT.put(CarOwnerAuthenStatusResult.AUTH_FAIL, "认证失败，原因:{0}");
    }

    /***
     * 获取弹回的原因
     * @param key
     * @param params
     * @return
     */
    public static  String  getReason(String key,String ... params){

        if(!RESULT.containsKey (key)){
            return StringUtils.EMPTY;
        }
        String text=RESULT.get (key);
        //是否处理参数化
        if(params!=null&&params.length>0){
            text= MessageFormat.format (text,params);
        }
        return text;
    }
}
