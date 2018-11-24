package com.ford.wechat.hub.controller.survey;

import com.ford.wechat.entity.hub.Redirection;
import com.ford.wechat.service.auth.AuthService;
import com.ford.wechat.service.hub.RedirectionService;
import com.ford.wechat.service.survey.SurveyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by wanglijun on 16/10/22.
 */
@Slf4j
@Controller
@RequestMapping("/hub/oauth")
public class WenJuanAuthorizeController {

    /***
     * 编码
     */
    private static final String UTF8 = "UTF-8";

    @Value("${parllay.oauth.url}")
    private String parllayAuthUrl;

    @Value("${hub.survey.oauth.url}")
    private String surveyAuthorizeUrl;


    @Autowired
    private AuthService authService;

    @Autowired
    private RedirectionService redirectionService;

    @Autowired
    private SurveyService surveyService;


    //http://local.point.xiqing.info:8800/hub/oauth/wenjuan/mMzAru/authorize
    @RequestMapping("/wenjuan/{state}/authorize")
    public ModelAndView authorize(@PathVariable String state, String code, String openid, String openId) throws Exception {
        log.info ("获取openid: {}  openId: {}  code: {}  state: {}", openid, openId, code, state);
        openid = StringUtils.isEmpty(openid) ? openId : openid;

        if (StringUtils.isNotEmpty(openid)) {//openId 存在  重定向回原应用
            log.info ("获取openid为:{}", openid);
            return new ModelAndView("redirect:" + this.fillRedirectUrl(state, openid));
        }

        if (StringUtils.isNotEmpty(code)) { //烽火台返回code
            Map<String, String> map = authService.getOpenIdByCode(code);
            log.info ("获取参数为:{}",map);
            if (!CollectionUtils.isEmpty (map) && StringUtils.isNotEmpty(map.get("openid"))) {
                openid = map.get("openid");
                return new ModelAndView(this.fillRedirectUrl(state, openid));
            } else {
                return new ModelAndView("templates/openidisnull");
            }
        }

        //跳转到烽火台获取微信code
        StringBuffer sb = new StringBuffer();
        sb.append(parllayAuthUrl);

        String thisUrl = surveyAuthorizeUrl;
        String url = thisUrl.replaceAll(":state", state);

        sb.append(URLEncoder.encode(url, UTF8));

        String pallayUrl = sb.toString();
        log.info ("pallayUrl为:{}", pallayUrl);

        return new ModelAndView("redirect:" + pallayUrl);
    }


    private String fillRedirectUrl (String state, String openid) {

        String redirectUrl = this.surveyService.getSurveyUri(state, openid);

        return "redirect:" + redirectUrl;
    }



}
