package com.ford.wechat.auth.controller.bind;/**
 * Created by Neel on 05/05/2017.
 */

import com.ford.wechat.auth.common.response.Response;
import com.ford.wechat.auth.common.utils.SessionUtils;
import com.ford.wechat.auth.controller.bind.vo.MemberResp;
import com.ford.wechat.auth.controller.bind.vo.VinResp;
import com.ford.wechat.entity.user.FordClubMember;
import com.ford.wechat.entity.user.JoUser;
import com.ford.wechat.service.bind.BindService;
import com.ford.wechat.service.bind.VehicleService;
import com.ford.wechat.service.user.FordClubMemberService;
import com.ford.wechat.service.user.JoUserService;
import io.dabing.common.exception.BusinessException;
import io.dabing.common.util.ValidateUtils;
import io.dabing.common.util.VinUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

@RestController
@RequestMapping(value="/i/bind/vin")
public class VinController {

    @Autowired
    private JoUserService joUserService;
    @Autowired
    private FordClubMemberService fordClubMemberService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Response<List<VinResp>> list() {

        String openId = SessionUtils.getOpenId();

        List<VinResp> body = new ArrayList<>();

        //查询openid是否存在
        JoUser user = joUserService.getJoUserByWechatId(openId);
        if(user == null){
           return new Response<>(body);
        }
        //查询对应vin码
        List<FordClubMember> list = this.fordClubMemberService.findByUserId(user.getId());

        for (FordClubMember item : list) {
            body.add(new VinResp(item.getVvin(), VinUtils.vin2brand(item.getVvin()), VinUtils.vin2type(item.getVvin()), VinUtils.vin2model(item.getVvin())));
        }
        return new Response<>(body);
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Response<List<MemberResp>> get() {

        String openId = SessionUtils.getOpenId();

        //查询对应vin码
        JoUser user = joUserService.getJoUserByWechatId(openId);

        if (user == null) {
            throw new BusinessException("500001", "未认证");
        }

        List<FordClubMember> carList = this.fordClubMemberService.findByUserId(user.getId());

        if (carList.isEmpty()) {
            throw new BusinessException("500001", "未认证");
        }

        List<MemberResp> list = new ArrayList<>();
        for(FordClubMember item : carList) {
            MemberResp body = new MemberResp();
            BeanUtils.copyProperties(item, body);
            body.setVin(item.getVvin());
            list.add(body);
        }

        return new Response<>(list);
    }



}
