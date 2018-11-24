package com.ford.wechat.auth.controller.user.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Neel on 2017/5/9.
 */

@Data
@NoArgsConstructor
public class LoginResp {

    private int cars;

    private boolean binding;

    private boolean unbinding;

    private String channelUrl;

    private Integer channelType;

    private String dealer;

}
