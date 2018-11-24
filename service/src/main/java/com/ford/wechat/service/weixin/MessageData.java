package com.ford.wechat.service.weixin;

/**
 * Created by yangkui on 15/11/4.
 * 消息格式，可以设置颜色
 */
public class MessageData {
    private String value;
    private String color;

    public MessageData(String value) {
        this.value = value;
    }

    public MessageData(String value, String color) {
        this.value = value;
        this.color = color;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
