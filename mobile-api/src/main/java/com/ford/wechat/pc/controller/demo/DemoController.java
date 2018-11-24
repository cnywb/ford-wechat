package com.ford.wechat.pc.controller.demo;

import com.ford.wechat.pc.controller.demo.vo.DemoReq;
import com.sun.org.apache.regexp.internal.RE;
import io.dabing.common.response.Response;
import io.dabing.core.web.annotation.ApiService;
import com.ford.wechat.pc.controller.demo.vo.DemoResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neel on 2016/12/7.
 */
@Controller
public class DemoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/api/private/list")
    @ResponseBody
    public Response<Void> api(@RequestBody String api){
        logger.info("需要登录api");
        return new Response<>(null);
    }

    @RequestMapping("/api/public/list")
    @ResponseBody
    public Response<Void> publicAccess (@RequestBody String api){
        logger.info("无需登录访问：publicAccess");
        return new Response<>(null);
    }
}
