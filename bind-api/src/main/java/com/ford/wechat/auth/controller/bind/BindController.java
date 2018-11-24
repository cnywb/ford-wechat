package com.ford.wechat.auth.controller.bind;/**
 * Created by Neel on 05/05/2017.
 */

import com.ford.wechat.auth.common.response.Response;
import com.ford.wechat.auth.common.utils.SessionUtils;
import com.ford.wechat.auth.controller.bind.vo.VinResp;
import com.ford.wechat.auth.controller.user.vo.UserVo;
import com.ford.wechat.entity.user.FordClubMember;
import com.ford.wechat.entity.user.JoUser;
import com.ford.wechat.service.bind.BindService;
import com.ford.wechat.service.user.CarOwnerAuthenStatusService;
import com.ford.wechat.service.user.FordClubMemberService;
import com.ford.wechat.service.user.JoUserService;
import io.dabing.common.exception.BusinessException;
import io.dabing.common.util.ValidateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-05-05 13:14
 * </p>
 *
 * @author Neel
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping(value="/i/bind/")
public class BindController {

    @Autowired
    private BindService bindService;

    @Autowired
    private CarOwnerAuthenStatusService carOwnerAuthenStatusService;

    @Autowired
    private FordClubMemberService fordClubMemberService;

    @Autowired
    private JoUserService joUserService;


    /**
     * 提交认证请求
     * @param mobile
     * @param vin
     * @param imageId
     * @param times 提交次数
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Response<String> save(HttpServletRequest request, @RequestParam(required = true) String mobile, @RequestParam(required = true) String vin, @RequestParam(required = true) Long imageId, @RequestParam(required = true) int times) {
        log.info("/i/bind/save  params { mobile: {}  vin: {}  imageId: {}  times: {} }", mobile, vin, imageId, times);
        String openId = SessionUtils.getOpenId();

        String ip = request.getRemoteAddr();

        UserVo user = (UserVo) SessionUtils.get(SessionUtils.USER_DETAIL);
        log.info("/i/bind/save  openId: {}", openId);

        if (StringUtils.isNotEmpty(vin)) {
            vin = vin.toUpperCase();
        }

        /**
         * TODO
         * 行驶证识别超过3次，强制需要上传行驶证
         * 非第一次绑定需要上传行驶证
         */
        boolean manual = times >= 3;
        if (manual && imageId == null) {
            log.error("行驶证识别失败次数超过3次，必须上传行驶证");
            throw new BusinessException("必须上传行驶证");
        }
        if(StringUtils.isBlank(vin) || vin.length() != 17){//vin为空或者长度不为17
            log.error("VIN不能未空，或者格式不正确");
            throw new BusinessException("您输入的VIN码有误，请重新输入");
        }

        //查询openid是否存在
        if (StringUtils.isNotBlank(mobile)) {
            if (!ValidateUtils.isMobileNo(mobile)) {
                log.error("手机号格式不正确");
                throw new BusinessException("您输入的手机号码有误，请重新输入");
            }
        } else {
            JoUser joUser = joUserService.getJoUserByWechatId(openId);
            if (joUser != null) {
                log.info("非第一次认证，手机号从JoUser获取");
                mobile = joUser.getMobilePhone();
            }
        }


        log.info("准备调用Service中认证方法");
        int result = this.bindService.doOwnerAuthenForWechat(user.getChannelCode(), user.getChannelType(),  openId, mobile, vin, ip, imageId);

        if (result == 1) {
            log.info("绑定成功: OK");
            return new Response<>("OK","绑定成功");//信息绑定成功，您已成为微客服认证车主
        }
        if (result == 2) {
            log.info("认证审核中: WAITING - OPENID: {}", openId);
            return new Response<>("WAITING","认证审核中");
        }
        if (result == 3) {
            log.info("需要上传行驶证: NEED_LICENSE - OPENID: {}", openId);
            return new Response<>("NEED_LICENSE","需要上传行驶证");
        }
        if (result == -1) {
            log.info("超过认证次数限制: WAITING - OPENID: {}", openId);
            return new Response<>("MAX","超过认证次数限制");
        }
        if (result == 0) {
            log.error("认证失败: ERROR - OPENID: {}", openId);
            return new Response<>("ERROR","认证失败");
        }

        return new Response<>();
    }


    /**
     * 提交认证申请
     * @param mobile
     * @return
     */
    @RequestMapping(value = "apply", method = RequestMethod.POST)
    public Response<String> apply(HttpServletRequest request, @RequestParam(required = true) String mobile) {
        String openId = SessionUtils.getOpenId();
        UserVo user = (UserVo)SessionUtils.get(SessionUtils.USER_DETAIL);
        String channelCode = user.getChannelCode();
        Integer channelType = user.getChannelType();

        if(StringUtils.isBlank(mobile) || !ValidateUtils.isMobileNo(mobile)) {//手机号为空，或者错误
            throw new BusinessException("手机号不正确");
        }


        this.carOwnerAuthenStatusService.save(channelCode, channelType, openId, mobile, null);

        return new Response<>();
    }

}
