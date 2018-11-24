package com.ford.wechat.test;

import java.util.UUID;

/**
 * Created by Neel on 2017/9/19.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        System.out.println(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
    }
}
