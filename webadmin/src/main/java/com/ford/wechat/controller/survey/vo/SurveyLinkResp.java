package com.ford.wechat.controller.survey.vo;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SurveyLinkResp {

    /** 项目短id */
    private String short_id;

    private String link;

}
