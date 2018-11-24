package com.ford.wechat.controller.survey.vo;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SurveyDetailResp {

    /** 微信用户地址(nan为空) */
    private String weixinAddr;

    /** 答卷序号 */
    private int seq;

    /** 来源 */
    private String source;

    /** 答题时长 */
    private String timeUsed;

    /** 答题ip */
    private String ip;

    /** 微信用户性别(nan为空) */
    private String weixinSex;

    /** 开始时间 */
    private String start;

    /** 结束时间 */
    private String finish;

    /** 微信用户昵称(nan为空) */
    private String weixinNickname;

    /** 答题状态 */
    private String rspdStatus;

}
