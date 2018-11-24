package com.ford.wechat.controller.survey.vo;

import com.ford.wechat.controller.vo.PageResp;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by wanglijun on 16/11/2.
 */
@Data
@NoArgsConstructor
public class SurveyCallbackPageResp extends PageResp {


    private Long id;

    /** 短项目ID*/
    private String shortId;

    /** 微信openId*/
    private String openId;

    /**答题状态：有效答卷； 无效答卷*/
    private String status;

    /**回调时间戳*/
    private String timestamp;

    /**签名*/
    private String signature;

}
