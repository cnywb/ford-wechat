package com.ford.wechat.pc.controller.user;


import com.ford.wechat.pc.common.UserUtil;
import com.ford.wechat.pc.common.response.Response;
import com.ford.wechat.service.weixin.WeiXinMessageService;
import com.ford.wechat.service.weixin.impl.WeiXinEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 *
 */

@RestController
@RequestMapping(value = "/api/public")
@Slf4j
public class WeixinInfoController {

    @Autowired
    private WeiXinMessageService weiXinMessageService;

    @RequestMapping(value = "/weixin/headImg", method = RequestMethod.POST)
    public Response<Object> login() {
        Map<String, Object> userInfoMap = weiXinMessageService.gainUserInfo(UserUtil.get());
        Object headImg = "";
        if (userInfoMap.get(WeiXinEnum.ERR_CODE) == null || userInfoMap.get(WeiXinEnum.ERR_CODE) == "") {
            headImg = userInfoMap.get(WeiXinEnum.USER_HEAD_IMG_URL);
        }
        return new Response<>(headImg);
    }
}
