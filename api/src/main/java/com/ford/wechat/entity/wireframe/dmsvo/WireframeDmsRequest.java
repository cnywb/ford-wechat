package com.ford.wechat.entity.wireframe.dmsvo;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lc on 2017/9/18.
 * 请求DMS实体类
 */
@Data
public class WireframeDmsRequest {
    public static final String MX="MX";
    public static final String FP="FP";


    public static final String WBO1="WBO1";
    public static final String WBO2="WBO2";


    /*用户名：由平台指定给到调用方，用于身份验证*/
    private String user_name;

    /*密码：由平台指定给到调用方，用于身份验证，不定期修改。*/
    private String password;

    /*来源：MX/FP*/
    private String source;

    /*业务类型：由平台指定给到调用方，代表不同的业务操作。*/
    private String business_type;

    /*业务数据内容：格式由具体的business_type决定，格式为JSON格式的字符串。*/
    private ArrayList<HashMap> content;


}
