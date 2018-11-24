package com.ford.wechat.web.pageinterface;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ford.wechat.entity.pageinterface.IsVerifyOwnerReq;
import com.ford.wechat.entity.pageinterface.IsVerifyOwnerRes;
import com.ford.wechat.entity.pageinterface.OwnerqrCodeReq;
import com.ford.wechat.entity.pageinterface.OwnerqrCodeRes;
import com.ford.wechat.service.pageinterface.PageInterfaceService;

import io.dabing.common.util.JSONUtil;



/**
 * Created by huangwen on 2017-06-26.
 * 描述:TODO
 *
 * @since 1.0
 */
@Controller@Scope("prototype")
@RequestMapping("/fordwechat/data/*")
public class OwnerInfoController {

  
    /***
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(OwnerInfoController.class);
    @Autowired
    private PageInterfaceService pageService;
    
 
	//跨域调用   根据openId验证是否已验证车主
	@RequestMapping("isverifyowner.do")
	public void isverifyowner( PrintWriter out,HttpServletRequest request,HttpServletResponse response,String jsonpcallback,String jsonStr){
		response.setContentType("text/javascript");//如果不这样设置 chrome浏览器无法调用	
		log.info("/fordwechat/data/isverifyowner.do-in;jsonpcallback:"+jsonpcallback+";jsonStr:"+jsonStr);		
		IsVerifyOwnerReq req=JSONUtil.toObject(jsonStr, IsVerifyOwnerReq.class);
		IsVerifyOwnerRes res = pageService.vaildIsVerifyOwner(req);
		String result=res.toString();
	    String return_str =  jsonpcallback+"("+result+")";
		out.print(return_str);	
		log.info("/fordwechat/data/isverifyowner.do-out;return_str:"+return_str);		
    }
	
    
	//跨域调用   获取车主二维码
	@RequestMapping("ownerqrcode.do")
	public void ownerqrcode( PrintWriter out,HttpServletRequest request,HttpServletResponse response,String jsonpcallback,String jsonStr){
		response.setContentType("text/javascript");//如果不这样设置 chrome浏览器无法调用	
		log.info("/fordwechat/data/ownerqrcode.do-in;jsonpcallback:"+jsonpcallback+";jsonStr:"+jsonStr);		
		OwnerqrCodeReq req=JSONUtil.toObject(jsonStr, OwnerqrCodeReq.class);
		OwnerqrCodeRes res = pageService.saveOwnerqrCodeUrl(req);
		String result=res.toString();
	    String return_str =  jsonpcallback+"("+result+")";
		out.print(return_str);
		log.info("/fordwechat/data/ownerqrcode.do-out;return_str:"+return_str);		
		
    }

}