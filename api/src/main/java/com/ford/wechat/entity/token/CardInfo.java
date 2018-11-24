package com.ford.wechat.entity.token;

/**
 * Created by wanglijun on 16/11/13.
 */
public class CardInfo {
    private String ticket;
    private String timestamp;
    private String signature;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
