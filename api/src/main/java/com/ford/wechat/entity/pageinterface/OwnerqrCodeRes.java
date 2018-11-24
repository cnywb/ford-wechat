package com.ford.wechat.entity.pageinterface;

import io.dabing.common.util.JSONUtil;

/**
 * Created by huangwen on 17/26/6.
 */
public class OwnerqrCodeRes extends BasePageInterfaceRes {

	/**
	 *  ownerInfoUrl 二维码内容包含：
		url  活动链接地址
		model  车型 
		name  客户姓名
		mobile   手机号
		referrerVin  车架号
		buyCarTime  购买时间	         
	 */
	private static final long serialVersionUID = -8767538630254069533L;
	private String ownerInfoUrl;
	
	


	public String getOwnerInfoUrl() {
		return ownerInfoUrl;
	}




	public void setOwnerInfoUrl(String ownerInfoUrl) {
		this.ownerInfoUrl = ownerInfoUrl;
	}




	@Override
	public String toString() {
		return JSONUtil.objectToJson(this);
	}
}


