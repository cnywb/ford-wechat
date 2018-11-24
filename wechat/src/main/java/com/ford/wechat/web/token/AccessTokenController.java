package com.ford.wechat.web.token;

import com.ford.wechat.service.token.AccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


/**
 * Created by wanglijun on 2016-10-22.
 * 描述:TODO
 *
 * @since 1.0
 */
@Controller
public class AccessTokenController {

    @Autowired
    AccessTokenService service;


}