package com.ford.wechat.auth.controller.user.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Neel on 2017/5/9.
 */

@Data
@NoArgsConstructor
public class LoginReq {

    private String openId;

    private String state;

    private String channelCode;

}
