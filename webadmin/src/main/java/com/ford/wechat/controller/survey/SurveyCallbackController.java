package com.ford.wechat.controller.survey;

import com.ford.wechat.controller.survey.vo.SurveyCallbackPageReq;
import com.ford.wechat.controller.survey.vo.SurveyCallbackPageResp;
import com.ford.wechat.entity.survey.SurveyCallback;
import com.ford.wechat.service.survey.SurveyCallbackService;
import com.ford.wechat.service.survey.SurveyService;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;


/**
 * Created by Neel on 2017-11-02.
 * 描述:TODO
 *
 * @since 1.0
 */
@Controller
public class SurveyCallbackController {

    @Autowired
    SurveyCallbackService service;


    @Autowired
    private SurveyService surveyService;

    /**
     * 按关键字分页查询对象 调用surveyConfigPage
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "surveyCallbackPage")
    public Page<SurveyCallbackPageResp> page(SurveyCallbackPageReq req) {
        Page<SurveyCallback> pages = service.pagingBy (req.getShortId(), req.getOpenId(), req.getPage ());
        Page<SurveyCallbackPageResp> respS = pages.map (new Converter<SurveyCallback, SurveyCallbackPageResp> () {
            public SurveyCallbackPageResp convert(SurveyCallback source) {
                SurveyCallbackPageResp resp = new SurveyCallbackPageResp();
                if (source != null) {
                    BeanUtils.copyProperties (source, resp);
                }
                return resp;
            }
        });
        return respS;
    }
}