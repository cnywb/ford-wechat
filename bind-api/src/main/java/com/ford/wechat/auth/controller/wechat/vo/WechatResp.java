package com.ford.wechat.auth.controller.wechat.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Neel on 2017/5/9.
 */

@Data
@NoArgsConstructor
public class WechatResp {

    private String appId;
    private String nonceStr;
    private String signature;
    private String timestamp;

}
