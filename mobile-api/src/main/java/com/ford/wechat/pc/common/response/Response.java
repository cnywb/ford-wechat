package com.ford.wechat.pc.common.response;

import org.springframework.http.HttpStatus;

/**
 * Created by Neel on 2016/12/10.
 */
public class Response<T> {

    private int status;

    private String message;

    private T body;


    /**
     * 该构造方法默认status 为200
     */
    public Response() {
        this(HttpStatus.OK.value(), null);
    }

    /**
     * 该构造方法默认status 为200
     * @param body
     */
    public Response(T body) {
        this(HttpStatus.OK.value(), body);
    }

    public Response(int status, T body) {
        this(status, null, body);
    }

    public Response(int status, String message, T body) {
        this.status = status;
        this.body = body;
        this.message = message;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}