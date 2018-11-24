package com.ford.wechat.controller.user.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhaoliang on 2017/5/25.
 */
@Data
public class CarOwnerAuthenStatusHandleReq {
    private Long id;

    private String userVin;

    private String openId;

    private String userName;

    private String userMobile;

    private Long createDate;

    private Long authState;

    private long times;

    private Long authResult;

    private String authWay;

    private Date authDate;

    private Long imgUrlId;

    private Long notPass;

}
