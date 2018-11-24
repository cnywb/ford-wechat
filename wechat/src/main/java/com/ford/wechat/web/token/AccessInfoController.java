package com.ford.wechat.web.token;

import com.ford.wechat.Constant;
import com.ford.wechat.entity.token.CardInfo;
import com.ford.wechat.entity.token.JsApiInfo;
import com.ford.wechat.service.token.AccessTokenService;
import com.ford.wechat.web.auth.AuthController;
import io.dabing.common.util.PropUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 提供微信授权参数接口
 * Created by wanglijun on 16/11/2.
 */
@Controller
public class AccessInfoController {
    /***
     * 日志
     */
    private static final Logger logger= LoggerFactory.getLogger (AuthController.class);

    /**获取accesstoken:AccessToken*/
    private final String ACCESS_TOKEN_STATE="2";

    /**获取jsapi相关信息*/
    private final String JS_API_STATE="3";

    /**跨域获取jsapi相关信息*/
    private final String  CROSS_DOMAIN_JS_API_STATE="4";

    /**跨域卡券调用接口*/
    private final String CROSS_DOMAIN_JS_TICKET_STATE="7";

    /**jsticket调用接口*/
    private final String JS_TICKET_STATE="8";

    @Autowired
    private AccessTokenService accessTokenService;





    @RequestMapping("/getinfo")
    @ResponseBody
    public String index(@Param(value = "state") String state, HttpServletRequest request) {

        if(ACCESS_TOKEN_STATE.equals (state)){
            return accessTokenService.getAccessToken();
        }else if(JS_API_STATE.equals (state)){
            String url = request.getParameter("url");
            JsApiInfo info = accessTokenService.getJsticket(url);
            return setJsInfo(info, StringUtils.EMPTY);
        }else if(CROSS_DOMAIN_JS_API_STATE.equals (state)){
            //跨域获取jsapi相关信息
            String url = request.getParameter("url");
            String callback = request.getParameter("callback");
            JsApiInfo info = accessTokenService.getJsticket(url);
            return setJsInfo(info, callback);
        }else if(CROSS_DOMAIN_JS_TICKET_STATE.equals (state)){
            //跨域卡券调用接口
            String cardid = request.getParameter("card_id");
            String callback = request.getParameter("callback");
            CardInfo card = accessTokenService.getCardTicket(cardid);
            if (null != card) {
                StringBuffer sb = new StringBuffer();
                sb.append(callback + "({\"ticket\":\"" + card.getTicket() + "\",");
                sb.append("\"timestamp\":" + card.getTimestamp() + "\",");
                sb.append("\"signature\":" + card.getSignature() + "\"})");
                return sb.toString();
            }
        }else if(JS_TICKET_STATE.equals (state)){
            //jsticket调用接口
            String ticket = accessTokenService.getJsApiTicket();
            return "{\"jsticket\":\"" + ticket + "\"}";
        }
        return StringUtils.EMPTY;
    }

    String setJsInfo(JsApiInfo info, String callback) {
        if (null != info) {
            StringBuffer sb = new StringBuffer();
            if (StringUtils.isNotEmpty(callback))
                sb.append(callback + "(");
            sb.append("{\"appId\":\"" + info.getAppId() + "\"");
            sb.append(",\"timestamp\":\"" + info.getTimestamp() + "\"");
            sb.append(",\"nonceStr\":\"" + info.getNonceStr() + "\"");
            sb.append(",\"signature\":\"" + info.getSignature() + "\"}");
            if (StringUtils.isNotEmpty(callback))
                sb.append(")");
            return sb.toString();
        }
        return StringUtils.EMPTY;
    }
}
