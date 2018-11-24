package com.ford.wechat.controller.auth;

import com.ford.wechat.controller.auth.vo.AuthToDmsGetReq;
import com.ford.wechat.controller.auth.vo.AuthToDmsPageResp;
import com.ford.wechat.entity.auth.AuthToDms;
import com.ford.wechat.service.auth.AuthToDmsService;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;

/**
 * Created by zhaoliang on 2017/5/26.
 */
@Controller
public class AuthToDmsController {
    @Autowired
    private AuthToDmsService service;

    /**
     * 按关键字分页查询对象 调用redirectConfigPage
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "authToDmsPage")
    public Page<AuthToDmsPageResp> page(AuthToDmsGetReq req) {
        Page<AuthToDms> pages = service.pagingBy(req.getBatchNo(),req.getVin(),req.getOpenId(),req.getChannelType(),req.getSendDmsStatus(),req.getVerify(),req.getCreateStartDate(),req.getCreateEndDate(),req.getPage());
                //service.pagingBy (req.getPage ());
        Page<AuthToDmsPageResp> respS = pages.map (new Converter<AuthToDms, AuthToDmsPageResp>() {
            public AuthToDmsPageResp convert(AuthToDms source) {
                AuthToDmsPageResp resp = new AuthToDmsPageResp();
                if (source != null) {
                    BeanUtils.copyProperties (source, resp);
                }
                return resp;
            }
        });
        return respS;
    }



}
