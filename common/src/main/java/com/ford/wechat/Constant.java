package com.ford.wechat;

import io.dabing.common.util.DateUtils;
import org.apache.commons.collections.bag.SynchronizedBag;
import org.apache.commons.lang.ArrayUtils;

import java.util.*;

/**
 * Created by wanglijun on 16/11/13.
 */
public final class Constant {

    public static final String WECHAT_FILE = "properties/wechat.properties";//Constant.class.getResource("/").getPath() +

    public static final String DEALER_CODE = "dealercode";

    public static void main(String[] args) {
        System.out.println("31and1001".split("and")[1]);
        System.out.println("发大发dsd放到沙发".substring(1));
        System.out.println(Long.parseLong(DateUtils.format(new Date(), DateUtils.FORMAT_DATE_TIME_YYYYMMDDHHMMSS)));
        System.out.println(Constant.class.getResource("/").getPath());
        List<Long> list = new ArrayList<Long>();
        list.add(1L);
        list.add(6L);
        list.add(3L);
        Collections.sort(list);
        System.out.println(list.toString());
        Calendar cal = Calendar.getInstance();//使用日历类
        long year = cal.get(Calendar.YEAR);//得到年
        long month = cal.get(Calendar.MONTH) + 1;//得到月，因为从0开始的，所以要加1
        System.out.println(year);
        System.out.println(month);
        long dif = (year - 2015) * 12 + (month - 12);

        System.out.println(dif);


    }

}
