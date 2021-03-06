package com.ford.wechat.controller.survey;

import com.ford.wechat.controller.survey.vo.SurveyConfigDeleteReq;
import com.ford.wechat.controller.survey.vo.SurveyConfigHandleReq;
import com.ford.wechat.controller.survey.vo.SurveyConfigPageReq;
import com.ford.wechat.controller.survey.vo.SurveyConfigPageResp;
import com.ford.wechat.entity.survey.SurveyConfig;
import com.ford.wechat.service.survey.SurveyConfigService;
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
public class SurveyConfigController {

    @Autowired
    SurveyConfigService service;


    @Autowired
    private SurveyService surveyService;

    /**
     * 按关键字分页查询对象 调用surveyConfigPage
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "surveyConfigPage")
    public Page<SurveyConfigPageResp> surveyConfigPage(SurveyConfigPageReq req) {
        Page<SurveyConfig> pages = service.pagingBy (req.getShortId(), req.getRedirectUrl(), req.getPage ());
        Page<SurveyConfigPageResp> respS = pages.map (new Converter<SurveyConfig, SurveyConfigPageResp> () {
            public SurveyConfigPageResp convert(SurveyConfig source) {
                SurveyConfigPageResp resp = new SurveyConfigPageResp();
                if (source != null) {
                    BeanUtils.copyProperties (source, resp);
                    resp.setLink(surveyService.getHubOAuthUri(source.getShortId()));
                }
                return resp;
            }
        });
        return respS;
    }

    /**
     * 创建/修改对象处理 调用surveyConfigHandle
     *
     * @param req 请求入参对象
     * @return
     */
    @ApiService(transCode = "surveyConfigHandle")
    public String surveyConfigHandle(SurveyConfigHandleReq req) {
        SurveyConfig entity = new SurveyConfig();
        if (req.getId () != null) {
            entity = service.get (req.getId ());
        }
        BeanUtils.copyProperties (req, entity);
        if (req.getId () != null) {
            service.update (entity);
        } else {
            service.save (entity);
        }
        return "";
    }

    /**
     * 删除对象处理,批量,单一删除均支持 调用surveyConfigRemove
     *
     * @param req 请求入参对象
     * @return
     */
    @ApiService(transCode = "surveyConfigDelete")
    public void surveyConfigDetele(SurveyConfigDeleteReq req) {
        for (Long id:req.getIds()) {
            service.delete (id);
        }
    }
}