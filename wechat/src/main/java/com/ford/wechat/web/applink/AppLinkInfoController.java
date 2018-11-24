package com.ford.wechat.web.applink;

import com.ford.wechat.service.applink.AppLinkInfoService;
import com.ford.wechat.entity.applink.AppLinkInfo;
import com.ford.wechat.web.applink.vo.AppLinkInfoHandleReq;
import com.ford.wechat.web.applink.vo.AppLinkInfoPageReq;
import com.ford.wechat.web.applink.vo.AppLinkInfoPageResp;
import com.ford.wechat.web.question.QuestionController;
import io.dabing.core.web.annotation.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;


/**
 * Created by wanglijun on 2016-10-22.
 * 描述:TODO
 *
 * @since 1.0
 */
@Controller
@RequestMapping("/fordwechat")
public class AppLinkInfoController {

    @Autowired
    AppLinkInfoService service;

    /***
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(AppLinkInfoController.class);

    /**
     * 按关键字分页查询对象 调用appLinkInfoPage
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "appLinkInfoList")
    public List<AppLinkInfoPageResp> appLinkInfoPage(AppLinkInfoPageReq req) {

        List<AppLinkInfoPageResp> resps= new ArrayList<> ();

        AppLinkInfoPageResp resp=new AppLinkInfoPageResp ();

        resp.setAppLinkName ("111111");

        resps.add (resp);

        return resps;
    }

    /**
     * 创建/修改对象处理 调用appLinkInfoHandle
     *
     * @param req 请求入参对象
     * @return
     */
    @ApiService(transCode = "appLinkInfoHandle")
    public String appLinkInfoHandle(AppLinkInfoHandleReq req) {
        AppLinkInfo entity = new AppLinkInfo ();
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

    @RequestMapping("/applink_first.do")
    public ModelAndView test() {
        return new ModelAndView("templates/applink/a");
    }

    @ResponseBody
    @RequestMapping("/applink.do")
    public List<String> getList(HttpServletRequest request) {
        List<String> list = new ArrayList<String>();
        String platform = getPlatform(request);
        List<AppLinkInfo> applinkinfos = service.doFindList(platform);
        for (AppLinkInfo app : applinkinfos) {
            StringBuffer sb = new StringBuffer();
            sb.append("<a href='" + app.getAppLinkUrl())
                    .append("'><img src='" + app.getAppLinkImg())
                    .append("'><p class='app_name'>" + app.getAppLinkName())
                    .append("</p><p class='app_company'>" + app.getDownloadName())
                    .append("</p>")
                    .append("<p class='app_install'>免费安装</p></a>");
            list.add(sb.toString());
        }

        return list;
    }

    private String getPlatform(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent");
        String platForm = "Android";
        String[] ios = {"iPhone", "iPad", "iPod", "iOS"};
        for (String str : ios) {
            if (userAgent.contains(str)) {
                platForm = "iOS";
                break;
            }
        }
        return platForm;
    }

}