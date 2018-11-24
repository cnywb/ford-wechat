package com.ford.wechat.pc.controller.user;


import com.ford.wechat.entity.user.FordClubMember;
import com.ford.wechat.entity.user.JoUser;
import com.ford.wechat.pc.common.UserUtil;
import com.ford.wechat.pc.common.response.Response;
import com.ford.wechat.pc.controller.user.vo.LoginReq;
import com.ford.wechat.pc.controller.user.vo.LoginResp;
import com.ford.wechat.pc.interceptor.AuthenticationInterceptor;
import com.ford.wechat.service.user.FordClubMemberService;
import com.ford.wechat.service.user.JoUserService;
import com.ford.wechat.service.weixin.WeiXinMessageService;
import com.ford.wechat.service.weixin.impl.WeiXinEnum;
import com.google.common.collect.Lists;
import io.dabing.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Neel on 2017/5/9.
 * 模拟用户登录，将openId存入session当中，后续所有接口都可以从session当中获取
 */

@RestController
@RequestMapping(value = "/api/public")
@Slf4j
public class UserController {

    @Autowired
    private FordClubMemberService fordClubMemberService;


    @Autowired
    private JoUserService joUserService;

    @Autowired
    private WeiXinMessageService weiXinMessageService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response<LoginResp> login(@RequestBody LoginReq req, HttpServletRequest request) {
        if (StringUtils.isBlank(req.getOpenId())) {
            throw new BusinessException("OPENID 不存在");
        }
        request.getSession().setAttribute(AuthenticationInterceptor.SESSION_TOKEN, req.getOpenId());

        LoginResp resp = new LoginResp();
        //额外添加一个微信头像字段 以及获取微信头像服务。
        Map<String, Object> userInfoMap = weiXinMessageService.gainUserInfo(req.getOpenId());
        if (userInfoMap.get(WeiXinEnum.ERR_CODE) == null || userInfoMap.get(WeiXinEnum.ERR_CODE) == "") {
            resp.setHeadImg(userInfoMap.get(WeiXinEnum.USER_HEAD_IMG_URL) + "");
        }

        JoUser joUser = joUserService.getJoUserByWechatId(req.getOpenId());
        List<FordClubMember> memberList = fordClubMemberService.findVinByOpenId(req.getOpenId());
        if (joUser == null || memberList == null || memberList.size() == 0) {
            log.info("openId:{},未认证用户登录!", req.getOpenId());
            resp.setAuth(false);
            return new Response<>(resp);
        }
        FordClubMember member = memberList.get(0);
        resp.setVin(member.getVvin());
        resp.setName(member.getName());
        resp.setMobile(member.getMobile());
        //如果member对应的vin码不为空，则认为已经认证
        if (StringUtils.isNotBlank(member.getVvin())) {
            resp.setAuth(true);
        }
        resp.setUserNum(joUser.getMemberNo());
        log.info("openId:{},vin:{},mobile:{},已认证用户登录!", req.getOpenId(), member.getVvin(), member.getMobile());
        resp.setVinList(vinList(memberList));
        return new Response<>(resp);
    }

    private List<String> vinList(List<FordClubMember> memberList) {
        List<String> vinList = Lists.newLinkedList();
        for (FordClubMember member : memberList) {
            vinList.add(member.getVvin());
        }
        return vinList;
    }

}
