package com.ford.wechat.controller.survey.vo;

import com.ford.wechat.controller.vo.HandleReq;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by wanglijun on 16/11/2.
 */
@Data
@NoArgsConstructor
public class SurveyConfigHandleReq extends HandleReq{

    private Long id;

    /** 短项目ID*/
    private String shortId;

    /** 是否需要回调 1. 需要  0. 不需要*/
    private Boolean needCallback = Boolean.FALSE;

    /** 答题完跳转链接 */
    private String redirectUrl;

    /** 回调地址 */
    private String callbackUrl;

    private String remark;
}
