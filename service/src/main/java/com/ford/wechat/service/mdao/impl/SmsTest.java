package com.ford.wechat.service.mdao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SmsTest {
    public static void main(String[] args) {
        SMSMessage sm = new SMSMessage();
        /**
         * 先设置Timestamp 取当前时间，
         * SMSMessage 内部会自动生成 Timestamp 要求字符串
         * 一定要在设置密码前面，密码加密用到 Timestamp
         * 密码md5加密也是SMSMessage 内部实现
         * 
         */
        sm.setTimestamp(new Date());
        sm.setContent("测试QMSG3接口");
        List<String> msd =new ArrayList<String>();
        msd.add("13093802740");
        sm.setMobiles(msd.toArray(new String[msd.size()]));
//        sm.setAtTime(new Date());
//        sm.setDailyBeginTime("00:00:00");
//        sm.setDailyEndTime("23:59:59");
        
        long startTime = System.currentTimeMillis();
//        System.out.println(MdaoSms.sendSms(sm));
//        MdaoSms.sendSms("13093802740","短信发送测试!");
        long endTime = System.currentTimeMillis();
        System.out.println("毫时："+(endTime-startTime));
    }
}
