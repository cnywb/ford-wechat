package com.ford.wechat.service.survey.response;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SurveyDetail {

    /** 微信用户地址(nan为空) */
    private String weixin_addr;

    /** 答卷序号 */
    private int seq;

    /** 来源 */
    private String source;

    /** 答题时长 */
    private String time_used;

    /** 答题ip */
    private String ip;

    /** 微信用户性别(nan为空) */
    private String weixin_sex;

    /** 开始时间 */
    private String start;

    /** 结束时间 */
    private String finish;

    /** 微信用户昵称(nan为空) */
    private String weixin_nickname;

    /** 答题状态 */
    private String rspd_status;

}
