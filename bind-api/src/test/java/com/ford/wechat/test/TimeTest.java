package com.ford.wechat.test;

import com.ford.wechat.auth.common.utils.DateUtils;

import java.util.Date;

/**
 * Created by Neel on 2017/5/30.
 */
public class TimeTest {

    public final static void main(String[] args) throws Exception {
        System.out.println(DateUtils.formatTime(new Date(1496130140981l)));
    }
}
