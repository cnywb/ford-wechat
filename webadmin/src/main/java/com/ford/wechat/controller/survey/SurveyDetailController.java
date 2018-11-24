package com.ford.wechat.controller.survey;

import com.ford.wechat.controller.survey.vo.SurveyDetailResp;
import com.ford.wechat.controller.survey.vo.SurveyDetailReq;
import com.ford.wechat.service.survey.SurveyService;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * Created by zhaoliang on 2017/5/24.
 */
@Controller
public class SurveyDetailController {

    @Autowired
    private SurveyService surveyService;

    /**
     * 按关键字分页查询对象 调用redirectConfigPage
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "surveyResultPage")
    public Page<SurveyDetailResp> page(SurveyDetailReq req) {

        Page<Map> pages = this.surveyService.pagingDetailBy(req.getShortId(), req.getPage());

        Page<SurveyDetailResp> respS = pages.map (new Converter<Map, SurveyDetailResp>() {
            public SurveyDetailResp convert(Map source) {
                SurveyDetailResp resp = new SurveyDetailResp();
                if (source != null) {
                    resp.setFinish(source.get("finish") == null ? null : source.get("finish").toString());
                    resp.setIp(source.get("ip") == null ? null : source.get("ip").toString());
                    resp.setRspdStatus(source.get("rspd_status") == null ? null : source.get("rspd_status").toString());
                    resp.setSeq(source.get("seq") == null ? 0 : Integer.parseInt(source.get("seq").toString()));
                    resp.setSource(source.get("source") == null ? null : source.get("source").toString());
                    resp.setStart(source.get("start") == null ? null : source.get("start").toString());
                    resp.setTimeUsed(source.get("time_used") == null ? null : source.get("time_used").toString());
                    resp.setWeixinAddr(source.get("weixin_addr") == null ? null : source.get("weixin_addr").toString());
                    resp.setWeixinNickname(source.get("weixin_nickname") == null ? null : source.get("weixin_nickname").toString());
                    resp.setWeixinSex(source.get("weixin_sex") == null ? null : source.get("weixin_sex").toString());
                }
                return resp;
            }
        });

        return respS;
    }

}
