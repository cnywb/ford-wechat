package com.ford.wechat.controller.survey.vo;


import com.ford.wechat.controller.vo.PageReq;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SurveyLinkReq extends PageReq {

    /**  */
    private String shortId;

    private String title;

}
