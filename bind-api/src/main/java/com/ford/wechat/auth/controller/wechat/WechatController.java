package com.ford.wechat.auth.controller.wechat;

import com.ford.wechat.auth.common.response.Response;
import com.ford.wechat.auth.common.utils.SessionUtils;
import com.ford.wechat.auth.controller.wechat.vo.WechatResp;
import com.google.gson.Gson;
import io.dabing.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by Neel on 2017/5/17.
 */
@RestController
@RequestMapping(value="/i/wechat")
@Slf4j
public class WechatController {

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public Response<WechatResp> info(String url) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://wechat.xiqing.info/wechat/getInfo?state=4&url=" + url)
                .get().build();

        try {
            okhttp3.Response resp = client.newCall(request).execute();
            Gson gson = new Gson();

            WechatResp body = gson.fromJson(resp.body().string(), WechatResp.class);

            return new Response<>(body);
        } catch (IOException e) {
            log.info("微信JSSDK配置获取失败");
            throw new BusinessException("配置获取失败");
        }
    }
}
