package com.ford.wechat.auth.controller.open;

import com.ford.wechat.auth.common.response.Response;
import com.ford.wechat.auth.controller.open.vo.MemberReq;
import com.ford.wechat.auth.controller.open.vo.MemberVinResp;
import com.ford.wechat.entity.user.FordClubMember;
import com.ford.wechat.entity.user.JoUser;
import com.ford.wechat.service.user.FordClubMemberService;
import com.ford.wechat.service.user.JoUserService;
import io.dabing.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neel on 2017/9/19.
 */
@Slf4j
@RestController
@RequestMapping(value="/i/member/vin")
public class MemberController {

    @Autowired
    private JoUserService joUserService;
    @Autowired
    private FordClubMemberService fordClubMemberService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Response<List<MemberVinResp>> list(MemberReq req) {

        if (!"AEB98CBE5F5248688EF0A7750F4000D2".equals(req.getSecret())) {
            throw new BusinessException("签名错误");
        }

        List<MemberVinResp> body = new ArrayList<>();

        //查询openid是否存在
        JoUser user = joUserService.getJoUserByWechatId(req.getOpenId());
        if(user == null){
            return new Response<>(body);
        }
        //查询对应vin码
        List<FordClubMember> list = this.fordClubMemberService.findByUserId(user.getId());

        for(FordClubMember item : list) {
            MemberVinResp resp = new MemberVinResp();
            BeanUtils.copyProperties(item, resp);
            resp.setVin(item.getVvin());

            body.add(resp);
        }
        return new Response<>(body);
    }

}
