package com.ford.wechat.auth.controller.user;

import com.ford.wechat.auth.common.response.Response;
import com.ford.wechat.auth.common.utils.DateUtils;
import com.ford.wechat.auth.common.utils.SessionUtils;
import com.ford.wechat.auth.controller.user.vo.LoginReq;
import com.ford.wechat.auth.controller.user.vo.LoginResp;
import com.ford.wechat.auth.controller.user.vo.UserVo;
import com.ford.wechat.entity.coupon.Event;
import com.ford.wechat.entity.coupon.EventDetail;
import com.ford.wechat.entity.dealer.WeChannel;
import com.ford.wechat.entity.member.WeWorkorderApply;
import com.ford.wechat.entity.user.CarOwnerAuthenStatus;
import com.ford.wechat.entity.user.FordClubMember;
import com.ford.wechat.entity.user.JoUser;
import com.ford.wechat.service.coupon.EventDetailService;
import com.ford.wechat.service.coupon.EventService;
import com.ford.wechat.service.dealer.WeChannelService;
import com.ford.wechat.service.members.WeWorkorderApplyService;
import com.ford.wechat.service.user.CarOwnerAuthenStatusService;
import com.ford.wechat.service.user.FordClubMemberService;
import com.ford.wechat.service.user.JoUserService;
import io.dabing.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by Neel on 2017/5/9.
 */
@Slf4j
@RestController
@RequestMapping(value="/i")
public class UserController {

    @Autowired
    private JoUserService joUserService;

    @Autowired
    private FordClubMemberService fordClubMemberService;

    @Autowired
    private WeWorkorderApplyService weWorkorderApplyService;

    @Autowired
    private CarOwnerAuthenStatusService carOwnerAuthenStatusService;

    @Autowired
    private WeChannelService weChannelService;

    @Autowired
    private EventDetailService eventDetailService;

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response<LoginResp> login(@RequestBody LoginReq req) {
        log.info("/i/login");

        if (StringUtils.isBlank(req.getOpenId())) {
            throw new BusinessException("OPENID 不存在");
        }
        String openId = req.getOpenId();
        String channelCode = req.getChannelCode();
        String state = req.getState();
        if (StringUtils.isNotEmpty(channelCode)) {
            channelCode = channelCode.trim();
        }

        SessionUtils.set(req.getOpenId());

        UserVo user = new UserVo();
        LoginResp body = new LoginResp();

        List<WeWorkorderApply> unauthApplies = this.weWorkorderApplyService.findUnAuthList(openId);
        List<CarOwnerAuthenStatus> carOwnerAuthenStatuses = this.carOwnerAuthenStatusService.findProcessingBy(openId);

        log.info("openId为{} 是否认证中: {}  是否解绑中: {}", openId, !carOwnerAuthenStatuses.isEmpty(), !unauthApplies.isEmpty());

        body.setUnbinding(!unauthApplies.isEmpty());
        body.setBinding(!carOwnerAuthenStatuses.isEmpty());

        log.info("根据openId获取JoUser");
        //查询openid是否存在
        JoUser joUser = joUserService.getJoUserByWechatId(openId);
        log.info("获取JoUser: {}", joUser != null);
        if(joUser != null){
            log.info("获取JoUser成功");
            user.setUserId(joUser.getId());
            //查询对应vin码
            List<FordClubMember> carList = this.fordClubMemberService.findByUserId(joUser.getId());
            body.setCars(carList.size());
        }

        BeanUtils.copyProperties(body, user);
        user.setChannelCode(channelCode);
        user.setState(state);
        boolean flag = false;
        if (StringUtils.isNotEmpty(channelCode)) {
            log.info("根据ChannelCode获取WeChannel");
            //获取做完车主认证之后是否要重定向到原页面
            WeChannel channel = weChannelService.getByCode(channelCode);
            user.setChannelType(channel == null ? null : channel.getType());
            body.setChannelType(channel == null ? null : channel.getType());
            if (channel != null) {
                if (channel.getType() == WeChannel.DEALER) {
                    user.setDealer(channel.getName());
                    body.setDealer(channel.getName());
                }
            }
            if (channel != null && StringUtils.isNotEmpty(channel.getUrl())) {
                flag = true;
                log.info("替换channelUrl参数: {}", channel.getUrl());
                String url = channel.getUrl();
                url = url.replaceAll(":openId", openId);
                url = url.replaceAll(":channelCode", channelCode);
                url = url.replaceAll(":dealerCode", channelCode);
                url = url.replaceAll(":state", state);
                user.setChannelUrl(url);
                body.setChannelUrl(url);
                log.info("替换channelUrl参数结果: {}", url);
            }
        }

        if (!flag) {
            //查询是否存在认证激励活动
            Event event = this.eventService.findByDate(DateUtils.formatDate(new Date()));
            if (event != null) {
                log.info("替换channelUrl参数为认证激励活动URL: {}", event.getEventUrl());
                String url = event.getEventUrl();
                url = url.replaceAll(":openId", openId);
                url = url.replaceAll(":channelCode", channelCode);
                url = url.replaceAll(":dealerCode", channelCode);
                url = url.replaceAll(":state", state);
                user.setChannelUrl(url);
                body.setChannelUrl(url);
                log.info("替换channelUrl参数为认证激励活动结果: {}", url);
            }
        }

        SessionUtils.put(SessionUtils.USER_DETAIL, user);

        return new Response<>(body);
    }

}
