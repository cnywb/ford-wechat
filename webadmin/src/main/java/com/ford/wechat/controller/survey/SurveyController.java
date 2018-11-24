package com.ford.wechat.controller.survey;

import com.ford.wechat.controller.survey.vo.*;
import com.ford.wechat.entity.hub.Redirection;
import com.ford.wechat.service.hub.RedirectionService;
import com.ford.wechat.service.survey.SurveyService;
import com.ford.wechat.service.survey.response.Survey;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * Created by zhaoliang on 2017/5/24.
 */
@Controller
public class SurveyController {


    @Autowired
    private SurveyService surveyService;

    @Autowired
    private RedirectionService redirectionService;

    /**
     * 按关键字分页查询对象 调用redirectConfigPage
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "surveyPage")
    public Page<SurveyResp> page(SurveyReq req) {

        Page<Survey> pages = this.surveyService.pagingBy(req.getStatus(), req.getPage());

        Page<SurveyResp> respS = pages.map (new Converter<Survey, SurveyResp>() {
            public SurveyResp convert(Survey source) {
                SurveyResp resp = new SurveyResp();
                if (source != null) {
                    BeanUtils.copyProperties(source, resp);
                }
                return resp;
            }
        });
        return respS;
    }
    @ApiService(transCode = "surveyLink")
    public SurveyLinkResp link(SurveyLinkReq req) {

        String link = this.surveyService.getHubOAuthUri(req.getShortId());

        SurveyLinkResp resp = new SurveyLinkResp();
        resp.setShort_id(req.getShortId());
        resp.setLink(link);

        return resp;
    }



}
