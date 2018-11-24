package com.ford.wechat.hub.controller.oauth;

import com.ford.wechat.entity.hub.Redirection;
import com.ford.wechat.service.auth.AuthService;
import com.ford.wechat.service.hub.RedirectionService;
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
public class AuthorizeController {

    /***
     * 编码
     */
    private static final String UTF8 = "UTF-8";

    @Value("${parllay.oauth.url}")
    private String parllayAuthUrl;

    @Value("${hub.oauth.url}")
    private String authorizeUrl;

    @Autowired
    private AuthService authService;

    @Autowired
    private RedirectionService redirectionService;

    @RequestMapping("/{state}/authorize")
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

        String thisUrl = authorizeUrl;
        String url = thisUrl.replaceAll(":state", state);

        sb.append(URLEncoder.encode(url, UTF8));

        String pallayUrl = sb.toString();
        log.info ("pallayUrl为:{}", pallayUrl);

        return new ModelAndView("redirect:" + pallayUrl);
    }


    private String fillRedirectUrl (String state, String openid) {
        Redirection config = this.redirectionService.getItem(state);

        if (config == null) return "templates/parameter-missing";

        String redirectUrl = config.getUrl();

        redirectUrl = redirectUrl.replaceAll(":openId", openid);
        redirectUrl = redirectUrl.replaceAll(":openid", openid);

        redirectUrl = redirectUrl.replaceAll(":shortId", state);
        redirectUrl = redirectUrl.replaceAll(":state", state);

        return "redirect:" + redirectUrl;
    }



}
