package com.ford.wechat.web.auth;

import com.ford.wechat.Constant;
import com.ford.wechat.entity.config.RedirectConfig;
import com.ford.wechat.entity.user.WechatUser;
import com.ford.wechat.service.auth.AuthService;
import com.ford.wechat.service.config.RedirectConfigService;
import com.ford.wechat.service.user.JoUserService;
import io.dabing.common.util.PropUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by wanglijun on 16/10/22.
 */
@Controller
@RequestMapping("/fordwechat")
public class AuthController {

    /***
     * 日志
     */
    private static final Logger logger= LoggerFactory.getLogger (AuthController.class);

    private static final String SRC = "src";
    private static final String SRC_CO = "src_co";
    private static final String CALLBACK = "callback";

    private static final String CALLBACK_CO = "callback_co";

    private static final String RETURN_URL = "returnUrl";
    /***
     * 编码
     */
    private static final String UTF8 = "UTF-8";

    @Autowired
    private AuthService authService;

    @Autowired
    private RedirectConfigService redirectConfigService;

    @Autowired
    private JoUserService joUserService;

    @RequestMapping("/control")
    public ModelAndView index(@Param(value = "state") String state, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            logger.info ("state:{}", state);
            setCookieParam (request, response);//
            return doAll (state, request, response);
        }catch(Exception e){
            //如果发生异常则跳转到
            logger.error (e.getMessage (),e);
            return new ModelAndView("templates/openidisnull");
        }
    }


    /**
     * 供应商防止因为微信跳转获取不到参数，特存放在cookie中
     *
     * @param request
     * @param response
     */
    private void setCookieParam(HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isNotEmpty(request.getParameter(SRC)))
            setCookie(SRC_CO, request.getParameter(SRC), response);
        if (StringUtils.isNotEmpty(request.getParameter(CALLBACK)))
            setCookie(CALLBACK_CO, request.getParameter(CALLBACK), response);
        if (StringUtils.isNotEmpty(request.getParameter(RETURN_URL)))
            setCookie(RETURN_URL, request.getParameter(RETURN_URL), response);
    }

    private ModelAndView doAll(String state, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String openid = getCookieValueByName("openid", request);
        String accessToken = getCookieValueByName("access_token", request);
        if (StringUtils.isEmpty(openid)) {
            String code = request.getParameter("code");
            if (StringUtils.isEmpty(code)) {
                response.sendRedirect(getPallayUrl(state, request));
                return null;
            } else {
                Map<String, String> map = authService.getOpenId (code);

                logger.info ("获取参数为:{}",map);
                if (!CollectionUtils.isEmpty (map)&& StringUtils.isNotEmpty(map.get("openid"))) {

                    openid = map.get("openid");
                    accessToken = map.get("access_token");
                    setCookie("openid", openid, response);
                    setCookie("access_token", accessToken, response);
                } else {
                    return new ModelAndView("templates/openidisnull");
                }
            }
        }
        return doRedirect(state, request, response, openid, accessToken);

    }

    //正式服务器调用wundermanlive
    private String getPallayUrl(String state, HttpServletRequest request) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(PropUtils.getPropertyValue(Constant.WECHAT_FILE, "auth_url"));
        String dealerCode = request.getParameter("dealercode");
        if (!StringUtils.isEmpty(dealerCode))
            state = state + "and" + dealerCode;
        String productUrl=PropUtils.getPropertyValue(Constant.WECHAT_FILE, "product_url");
        logger.info ("productUrl:{}",productUrl);
        sb.append(URLEncoder.encode(productUrl + "state=" + state, UTF8));
        return sb.toString();
    }

    //测试服务器调用
    private String getWechatUrl(String state, HttpServletRequest request) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx51526acf7f7e9e03&redirect_uri=");
        String dealerCode = request.getParameter("dealercode");
        if (!StringUtils.isEmpty(dealerCode))
            state = state + "and" + dealerCode;
        sb.append(URLEncoder.encode(PropUtils.getPropertyValue(Constant.WECHAT_FILE, "product_url"), UTF8));
        sb.append("&response_type=code&scope=snsapi_userinfo&state=" + state + "#wechat_redirect");
        return sb.toString();
    }

    private ModelAndView doRedirect(String state, HttpServletRequest request, HttpServletResponse response, String openid, String accessToken) {
        ModelAndView view = new ModelAndView("templates/prompt2");
        try {
            appLink(state, view);
            if (carOwner(state, request, response, openid, view))
                return null;
            questionAnswer(state, view);//
            saleService(state, view);
            presaleAotuGroup(state, view);
            if (redirectWithOpenid(state, response, openid))
                return null;
            if (redirectWithUser(state, request, response, openid, accessToken))
                return null;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return view;
    }

    private void appLink(String state, ModelAndView view) {
        //applink
        if (state.equals("400"))
            view.setViewName("templates/applink/a");
    }

    private boolean carOwner(String state, HttpServletRequest request, HttpServletResponse response, String openid, ModelAndView view) throws Exception {
        //认证车主初始跳转
        if (state.equals("31")) {
            String dealerCode = request.getParameter("dealercode");//dealerCode=%s&openId=%s
            String dcode = request.getParameter("dcode");
            if (!StringUtils.isEmpty(dealerCode)) {
                logger.info("dealerCode is not empty dealerCode:{}",dealerCode);
                setCookie(Constant.DEALER_CODE, dealerCode, response);
                if (StringUtils.isEmpty(dcode)) {
                    logger.info("dcode is empty dcode:{}",dcode);
                    StringBuffer sb = new StringBuffer();
                    sb.append(PropUtils.getPropertyValue(Constant.WECHAT_FILE, "scores_scan_url"));
                    sb.append("?channelCode=" + dealerCode);
                    sb.append("&openId=" + openid);
                    sb.append("&state=" + 31);
                    response.sendRedirect(sb.toString());
                    return true;
                }
            }
            logger.info("http://point.xiqing.info/bind/static/init.html?openId="+openid+"&state=31");
            //view.setViewName("http://point.xiqing.info/bind/static/init.html?openId="+openid+"&state=31");
            response.sendRedirect("http://point.xiqing.info/bind/static/init.html?openId="+openid+"&state=31");
            return true;
        }
        //认证初始页面
        else if (state.indexOf("31and") >= 0) {
            logger.info("31and");
            setCookie(Constant.DEALER_CODE, state.split("and")[1], response);
            StringBuffer sb = new StringBuffer();
            sb.append(PropUtils.getPropertyValue(Constant.WECHAT_FILE, "scores_scan_url"));
            sb.append("?channelCode=" + state.split("and")[1]);
            sb.append("&openId=" + openid);
            sb.append("&state=" + 31);
            response.sendRedirect(sb.toString());
            return true;
        }
        //绑定初始页面
        else if (state.equals("32"))
            view.setViewName("templates/chezhubangdin");
        //绑定老友惠页面
        else if (state.equals("16278")){
            String dealercode = request.getParameter("dealercode");//dealerCode=%s&openId=%s
            String dcode = request.getParameter("dcode");
            if (!StringUtils.isEmpty(dealercode)) {
                setCookie(Constant.DEALER_CODE, dealercode, response);
                if (StringUtils.isEmpty(dcode)) {
                    StringBuffer sb = new StringBuffer();
                    sb.append(PropUtils.getPropertyValue(Constant.WECHAT_FILE, "scores_scan_url"));
                    sb.append("?dealerCode=" + dealercode);
                    sb.append("&openId=" + openid);
                    response.sendRedirect(sb.toString());
                    return true;
                }
            }
            view.setViewName("templates/lyhchezhurenzh");
        }
        //绑定老友惠页面
        else if (state.equals("16378")){
            String dealercode = request.getParameter("dealercode");//dealerCode=%s&openId=%s
            String dcode = request.getParameter("dcode");
            if (!StringUtils.isEmpty(dealercode)) {
                setCookie(Constant.DEALER_CODE, dealercode, response);
                if (StringUtils.isEmpty(dcode)) {
                    StringBuffer sb = new StringBuffer();
                    sb.append(PropUtils.getPropertyValue(Constant.WECHAT_FILE, "scores_scan_url"));
                    sb.append("?dealerCode=" + dealercode);
                    sb.append("&openId=" + openid);
                    response.sendRedirect(sb.toString());
                    return true;
                }
            }
            view.setViewName("templates/mallchezhurenzh");
        }
        //车主工单列表查询
        else if (state.equals("35")) {
            view.setViewName("templates/repairlist");
            if (joUserService.getCountByWechatId(openid) == 0)
                view.setViewName("templates/prompt");
        }
        //电子账单查询
        else if (state.equals("37")) {
            view.setViewName("templates/zdcx");
            if (joUserService.getCountByWechatId(openid) == 0)
                view.setViewName("templates/prompt");
        }
        //车主手动认证操作手册
        else if (state.equals("38"))
            view.setViewName("templates/manual");
            //车主个人信息完善初始页面
        else if (state.equals("300"))
            view.setViewName("templates/czxxws");
            //车主车辆信息完善初始页面
        else if (state.equals("302"))
            view.setViewName("templates/czclws");
            //车主信息选择完善跳转页面
        else if (state.equals("304"))
            view.setViewName("templates/userinfoselect");
        return false;
    }

    //互动问答
    private void questionAnswer(String state, ModelAndView view) {
        //表示初次get提交互动问答列表查询页面
        if (state.equals("2"))
            view.setViewName("templates/qa");
    }

    //售后服务
    private void saleService(String state, ModelAndView view) {
        //保养计算器
        if (state.equals("21"))
            view.setViewName("templates/baoy_calculator");
            //保养套餐
        else if (state.equals("22"))
            view.setViewName("templates/maintain");
            //24小时客服热线
        else if (state.equals("26"))
            view.setViewName("templates/24hoursjy");
    }

    //售前自动分组
    private void presaleAotuGroup(String state, ModelAndView view) {
        //初始信息收集页面
        if (state.equals("500"))
            view.setViewName("templates/autogroup/buyCar");
            //中转页面
        else if (state.equals("502"))
            view.setViewName("templates/autogroup/fenzu");
    }

    //获取openid跳转只带openid
    private boolean redirectWithOpenid(String state, HttpServletResponse response, String openid) throws Exception {
        RedirectConfig config = redirectConfigService.getItem(state);
        if (null != config) {
            response.sendRedirect(config.getUrl().replace("%s", openid));
            return true;
        }
        return false;
    }

    //获取wechatuser跳转
    private boolean redirectWithUser(String state, HttpServletRequest request, HttpServletResponse response, String openid, String accessToken) throws Exception {
        if (checkState(state) || state.indexOf("1301") >= 0) {
            WechatUser user = authService.getUser(openid, accessToken);
            if (null != user) {
                return redirectToUrl(state, request, response, user);
            } else {
                return state114(state, request, response, openid);
            }
        }
        return false;
    }

    private boolean checkState(String state) {
        String[] strs = {"111", "113", "114", "1000"};
        for (String str : strs) {
            if (str.equals(state))
                return true;
        }
        return false;
    }

    private boolean state114(String state, HttpServletRequest request, HttpServletResponse response, String openid) throws Exception {
        String callback = request.getParameter(CALLBACK);
        if (StringUtils.isEmpty(callback))
            callback = getCookieValueByName(CALLBACK_CO, request);
        StringBuffer sb = new StringBuffer();
        if (state.equals("114")) {
            String url = "openid=" + openid + "&nickname=&sex=&headimgurl=&province=&city=";
            sb.append(URLDecoder.decode(callback, UTF8));
            sb.append("&parameter=" + URLEncoder.encode(url, UTF8));
            response.sendRedirect(sb.toString());
            return true;
        }
        return false;
    }

    private boolean redirectToUrl(String state, HttpServletRequest request, HttpServletResponse response, WechatUser user) throws Exception {
        String src = request.getParameter(SRC);
        if (StringUtils.isEmpty(src))
            src = getCookieValueByName(SRC_CO, request);
        String callback = request.getParameter(CALLBACK);
        if (StringUtils.isEmpty(callback))
            callback = getCookieValueByName(CALLBACK_CO, request);
        String returnurl = request.getParameter(RETURN_URL);
        if (StringUtils.isEmpty(returnurl))
            returnurl = getCookieValueByName(RETURN_URL, request);
        StringBuffer sb = new StringBuffer();
        if (state.equals("111") || state.equals("113")) {
            sb.append("http://fe.huangu001.cn/index.php?g=Wap&m=Fordtestdrive&a=callbackurl&token=jvvktf1390570573");
            if (state.equals("111"))
                sb.append("&r=reindex12");
            else
                sb.append("&r=reindex13");
            sb.append("&src=" + src);
            sb.append("&openid=" + user.getOpenid());
            sb.append("&nickname=" + URLEncoder.encode(user.getNickname(), UTF8));
            sb.append("&sex=" + user.getSex());
            sb.append("&headimgurl=" + user.getImgurl());
            sb.append("&province=" + URLEncoder.encode(user.getProvince(), UTF8));
            sb.append("&city=" + URLEncoder.encode(user.getCity(), UTF8));
        } else if (state.equals("114")) {
            String url = "openid=" + user.getOpenid() + "&nickname=" + user.getNickname() + "&sex=" + user.getSex() +
                    "&headimgurl=" + user.getImgurl() + "&province=" + user.getProvince() + "&city=" + user.getCity();
            sb.append(URLDecoder.decode(callback, UTF8));
            sb.append("&parameter=" + URLEncoder.encode(url, UTF8));
        } else if (state.equals("1000")) {
            sb.append(returnurl);
            sb.append("?openid=" + user.getOpenid());
            sb.append("&nickname=" + URLEncoder.encode(user.getNickname(), UTF8));
            sb.append("&headimgurl=" + user.getImgurl());
        } else if (state.indexOf("1301") >= 0) {
            sb.append("http://www.changanfordclub.com/infocollection/potentialcustomer/taurus/index.jspx?");
            sb.append("nickName=" + URLEncoder.encode(user.getNickname(), UTF8));
            sb.append("&userPhoto=" + user.getImgurl());
            sb.append("&channel=" + state.substring(4, 6));
        }

        if (StringUtils.isNotEmpty(sb.toString())) {
            response.sendRedirect(sb.toString());
            return true;
        }
        return false;
    }


    private String getCookieValueByName(String name, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();//
        String co = null;
        if (null != cookies) {
            for (Cookie c : cookies) {
                if (null != c && c.getName().equals(name)) {
                    co = c.getValue();
                }
            }
        }
        return co;
    }

    private void setCookie(String name, String value, HttpServletResponse response) {
        if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(value)) {
            Cookie co = new Cookie(name, value);
            co.setPath("/");
            co.setMaxAge(-1);
            response.addCookie(co);
        }
    }
}
