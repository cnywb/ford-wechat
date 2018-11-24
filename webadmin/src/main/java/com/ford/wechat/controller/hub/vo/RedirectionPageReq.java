package com.ford.wechat.controller.hub.vo;

import com.ford.wechat.controller.vo.PageReq;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Neel on 16/11/2.
 */
@Data
@NoArgsConstructor
public class RedirectionPageReq extends PageReq{
    /**
     * 微信入口状态参数
     */
    private String state;
    /**
     * 授权跳转的url
     */
    private String url;

}
