package com.ford.wechat.web.auth;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 提供微信授权参数接口
 * Created by wanglijun on 16/11/2.
 */
@Controller
public class Yihu {

    @RequestMapping("/yihu")
    public ModelAndView index(@Param(value = "state") String state, HttpServletRequest request) {
        if (state.equals("180"))
            return new ModelAndView("templates/autoparts/forisi");
        else if (state.equals("181"))
            return new ModelAndView("templates/autoparts/jdfocus");
        else if (state.equals("182"))
            return new ModelAndView("templates/autoparts/maverick");
        else if (state.equals("183"))
            return new ModelAndView("templates/autoparts/newcarnival");
        else if (state.equals("184"))
            return new ModelAndView("templates/autoparts/newfocus");
        else if (state.equals("185"))
            return new ModelAndView("templates/autoparts/newmondeo");
        else if (state.equals("186"))
            return new ModelAndView("templates/autoparts/wingbo");
        return null;
    }
}
