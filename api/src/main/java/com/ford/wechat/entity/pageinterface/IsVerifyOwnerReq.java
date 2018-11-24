package com.ford.wechat.entity.pageinterface;

/**
 * Created by huangwen on 17/26/6.
 */
public class IsVerifyOwnerReq extends BasePageInterfaceReq {

	private static final long serialVersionUID = 4098695363977801823L;

	private String openid;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	
}


