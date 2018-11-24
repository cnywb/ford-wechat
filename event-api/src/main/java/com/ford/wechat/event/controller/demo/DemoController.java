package com.ford.wechat.event.controller.demo;

import com.ford.wechat.event.common.response.Response;
import com.ford.wechat.event.controller.demo.vo.DemoReq;
import com.ford.wechat.event.controller.demo.vo.DemoResp;
import io.dabing.common.exception.BusinessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neel on 2016/12/7.
 */


@RestController
@RequestMapping(value="/e/open/demo")
public class DemoController {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Response<List<DemoResp>> list(DemoReq req) {
        List<DemoResp> body = new ArrayList<>();
        DemoResp resp = new DemoResp(1l, "jingxuan", "精选", 1);
        body.add(resp);
        resp = new DemoResp(2l, "hengshan", "衡山", 1);
        body.add(resp);
        resp = new DemoResp(3l, "huashan", "华山", 1);
        body.add(resp);
        resp = new DemoResp(4l, "huangshan", "黄山", 1);
        body.add(resp);
        resp = new DemoResp(5l, "taishan", "泰山", 1);
        body.add(resp);
        resp = new DemoResp(6l, "alishan", "阿里山", 1);
        body.add(resp);
        resp = new DemoResp(7l, "alishan2", "阿里山2", 1);
        body.add(resp);
        return new Response<>(body);
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public Response<List<DemoResp>> error(DemoReq req) {
        throw new BusinessException("error", "错啦");
    }
}
