package com.ford.wechat.controller.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.dabing.common.date.DateUtil;

import java.util.Date;

/**
 * Created by wanglijun on 16/7/9.
 */
public class PageResp {
    /***ID*/
    private Long id;

    /**创建日期*/
    @JSONField(format = DateUtil.FORMAT_DATETIME_DEFAULT)
    protected Date createdDate;


    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
