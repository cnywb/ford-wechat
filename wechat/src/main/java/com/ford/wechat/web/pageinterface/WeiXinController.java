package com.ford.wechat.web.pageinterface;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ford.wechat.service.token.AccessTokenService;
import com.ford.wechat.web.members.vo.ShareInfoReq;
import com.ford.wechat.web.members.vo.ShareInfoRes;

import io.dabing.common.util.HttpClientUtil;
import io.dabing.common.util.JSONUtil;

/**
 * Created by huangwen on 2017/07/18.
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/fordwechat/wx/*")
public class WeiXinController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AccessTokenService accessTokenService;

	public static final String WX_URL = "http://api.parllay.cn/v1.1/social/wxconfig/7UOHYCCD9T/metadata/get";

	@RequestMapping("getShareInfo.do")
	public void getShareInfo(PrintWriter out, HttpServletRequest request, HttpServletResponse response,
			String jsonpcallback, String jsonStr) {
		logger.info("/fordwechat/wx/getShareInfo.do in:jsonStr:" + jsonStr);
		response.setContentType("text/javascript");// 如果不这样设置 chrome浏览器无法调用
		ShareInfoReq req = JSONUtil.toObject(jsonStr, ShareInfoReq.class);
		ShareInfoRes res = getShareInfoDetail(req);
		String result = res.toString();

		String return_str = jsonpcallback + "(" + result + ")";

		logger.info("/fordwechat/wx/getShareInfo.do;return_str:" + return_str);
		out.print(return_str);
		logger.info("/fordwechat/wx/getShareInfo.do out;");
	}

	public ShareInfoRes getShareInfoDetail(ShareInfoReq req) {
		ShareInfoRes res = new ShareInfoRes();
		try {
			if (StringUtils.isBlank(req.getUrl())) {
				res.setStatus(-11);
				res.setMessage("操作失败,url不能为空！");
				return res;
			}
			// String accessToken = accessTokenService.getAccessToken();
			String accessToken = accessTokenService.getAccessTokenWeixinByAppId();
			if (null == accessToken) {
				res.setStatus(0);// 状态 1.成功，0 失败
				res.setMessage("操做 失败！accessToke获取失败");
				return res;
			}
			logger.info("/fordwechat/wx/getShareInfo.do in1:accessToken:" + accessToken);
			String responseBody = HttpClientUtil.get(WX_URL + "?url=" + req.getUrl() + "&callback=?" + "&access_token=" + accessToken,
					30000, 30000, "UTF-8");
			logger.info(" responseBody HttpClientUtil.get  result  = " + responseBody);
			
			if (null != responseBody) {
//				responseBody = responseBody.substring(5, responseBody.length());
				responseBody = responseBody.substring(2, responseBody.length());
				responseBody = responseBody.substring(0, responseBody.length() - 1);
			} else {
				res.setAccessToken(accessToken);
				res.setStatus(0);// 状态 1.成功，0 失败
				res.setMessage("操做 失败！");
				return res;
			}
			logger.info("/fordwechat/wx/getShareInfo.do in2:responseBody:" + responseBody);
			res = JSONUtil.toObject(responseBody, ShareInfoRes.class);
			res.setAccessToken(accessToken);
			res.setStatus(1);// 状态 1.成功，0 失败
			res.setMessage("操做成功！");
			return res;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			res.setStatus(-99);
			res.setMessage("操作失败,异常");
			return res;
		}
	}

	@RequestMapping("getShareInfoForSignature.do")
	public void getShareInfoForSignature(PrintWriter out, HttpServletRequest request, HttpServletResponse response,
			String jsonpcallback, String jsonStr) {
		logger.info("/fordwechat/wx/getShareInfoForSignature.do in:jsonStr:" + jsonStr);
		response.setContentType("text/javascript");// 如果不这样设置 chrome浏览器无法调用

		ShareInfoReq req = JSONUtil.toObject(jsonStr, ShareInfoReq.class);
		ShareInfoRes res = getShareInfoDetailForSignature(req);
		String result = res.toString();
		String return_str = jsonpcallback + "(" + result + ")";
		logger.info("/fordwechat/wx/getShareInfoForSignature.do;return_str:" + return_str);
		out.print(return_str);
		logger.info("/fordwechat/wx/getShareInfoForSignature.do out;");
	}

	public ShareInfoRes getShareInfoDetailForSignature(ShareInfoReq req) {
		ShareInfoRes res = new ShareInfoRes();
		try {
			if (StringUtils.isBlank(req.getUrl())) {
				res.setStatus(-11);
				res.setMessage("操作失败,url不能为空！");
				return res;
			}
			// String accessToken = accessTokenService.getAccessToken();
			String accessToken = accessTokenService.getAccessTokenWeixinByAppId();
			if (null == accessToken) {
				res.setStatus(0);// 状态 1.成功，0 失败
				res.setMessage("操做 失败！accessToke获取失败");
				return res;
			}
			logger.info("/fordwechat/wx/getShareInfoForSignature.do in1:accessToken:" + accessToken);
			String responseBody = HttpClientUtil.get(WX_URL + "?url=" + req.getUrl() + "&callback=?" + "&access_token=" + accessToken,
					30000, 30000, "UTF-8");
			if (null != responseBody) {
				responseBody = responseBody.substring(2, responseBody.length());
				responseBody = responseBody.substring(0, responseBody.length() - 1);
			} else {
				res.setAccessToken(accessToken);
				res.setStatus(0);// 状态 1.成功，0 失败
				res.setMessage("操做 失败！");
				return res;
			}
			logger.info("/fordwechat/wx/getShareInfoForSignature.do in2:responseBody:" + responseBody);
			// res = JSONUtil.toObject(responseBody, ShareInfoRes.class);
			res.setResponseBody(responseBody);

			res.setAccessToken(accessToken);
			res.setStatus(1);// 状态 1.成功，0 失败
			res.setMessage("操做成功！");
			return res;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			res.setStatus(-99);
			res.setMessage("操作失败,异常");
			return res;
		}
	}

	// public static void main(String[] args) {
	// //null({\"signature\":\"1d832dce2e0a799ddf68419ffdd9b2a2a2ac28ff\",\"appid\":\"wxbc330df0e013e079\",\"jsapi_ticket\":\"U3NCfECtmlRRNaClTyy4tx03e1qejQXbDmZ_WQvwvrIK_NgirVSVayL4s9ExVHRtSsKxh0u3FTHR8lfO3kfFeg\",\"url\":\"http://test.weidongs.com/k/43/\",\"noncestr\":\"d384e988-1be1-4ed5-911a-4b0802daf6b8\",\"timestamp\":\"1500520931\"})
	// String responseBody =
	// "null({\"signature\":\"7ae76821fae241ac453be9bd23253cb01efa706f\",\"appid\":\"wxbc330df0e013e079\",\"jsapi_ticket\":\"U3NCfECtmlRRNaClTyy4tx03e1qejQXbDmZ_WQvwvrIgyQPoTu7d53YbSa-A9QH8Nzs9Z6NBStDWsoxW1HyMpw\",\"url\":\"http://www.baidu.com\",\"noncestr\":\"f06fe400-da1b-4c16-951e-dc907077973d\",\"timestamp\":\"1500534039\"})";
	// if(responseBody!=null){
	// responseBody = responseBody.substring(5,responseBody.length());
	// responseBody = responseBody.substring(0,responseBody.length()-1);
	// }
	//
	// System.out.println(responseBody);
	// ShareInfo info = JSONUtil.toObject(responseBody, ShareInfo.class);
	// String result=info.toString();
	// System.out.println(result);
	// }
}
