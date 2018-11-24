package com.ford.wechat.auth.controller.user;

import com.ford.wechat.auth.common.response.Response;
import com.ford.wechat.auth.common.utils.SessionUtils;
import com.ford.wechat.auth.controller.user.vo.CodeReq;
import com.ford.wechat.service.bind.BindService;
import com.ford.wechat.service.mdao.SmsService;
import io.dabing.common.exception.BusinessException;
import io.dabing.common.util.ValidateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Neel on 2017/5/9.
 */

@RestController
@RequestMapping(value="/i/code")
public class CodeController {

    @Autowired
    private BindService bindService;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public Response<Void> send(String mobile) {
        String openId = SessionUtils.getOpenId();
//        String mobile = req.getMobile();
        if (!ValidateUtils.isMobileNo(mobile)) throw new BusinessException("300", "手机号不合法");

        if (StringUtils.isBlank(mobile)) {
            throw new BusinessException("手机号不存在");
        }

        bindService.sendBindCode(openId, mobile);

        return new Response<>();
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public Response<Void> check(String mobile, String code) {
        String openId = SessionUtils.getOpenId();

        if (StringUtils.isBlank(mobile)) {
            throw new BusinessException("手机号不存在");
        }
        if (StringUtils.isBlank(code)) {
            throw new BusinessException("验证码不能为空");
        }
        bindService.checkBindCode(openId, mobile, code);

        return new Response<>();
    }

}
