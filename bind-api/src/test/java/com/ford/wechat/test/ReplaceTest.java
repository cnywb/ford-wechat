package com.ford.wechat.test;

public class ReplaceTest {

    public static void main(String[] args) throws Exception {
        String redirectUrl = "http://www.imooc.com/article/1392/?openId=%s&status=%s";

        redirectUrl = redirectUrl.replaceAll("openId=%s", "openId=xxxxx");
        redirectUrl = redirectUrl.replaceAll("status=%s", "status=1");

        System.out.println(redirectUrl);

    }
}
