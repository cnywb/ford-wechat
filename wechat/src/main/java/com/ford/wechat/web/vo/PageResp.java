package com.ford.wechat.web.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by wanglijun on 16/7/9.
 */
public class PageResp {
    /***ID*/
    private Long id;

    /**创建日期*/
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    protected Date createdDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
