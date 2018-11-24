package com.ford.wechat.service.weixin;

import java.util.Map;

/**
 * Created by yangkui on 15/11/4.
 * 微信模板消息对象
 */
public class WeiXinMessage {

    /**
     * 接收方的openId（不能为空）
     */
    private String touser;
    /**
     * 消息模板id,id值从微信公众号平台上获取（不能为空）
     */
    private String template_id;
    /**
     * 消息内容详情点击的url,可以为空
     */
    private String url;

    /**
     *
     * 具体的消息模板内容（不能为空）
     * 每个key与模板当中定义的key一致
     */
    private Map<String,MessageData> data;

    /**
     * 返回值
     * 如果发送失败，则错误代码不为空
     */
    private String errCode;

    /**
     * 返回值
     *  如果发送失败，错误消息内容
     */
    private String errMsg;


    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, MessageData> getData() {
        return data;
    }

    public void setData(Map<String, MessageData> data) {
        this.data = data;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}

