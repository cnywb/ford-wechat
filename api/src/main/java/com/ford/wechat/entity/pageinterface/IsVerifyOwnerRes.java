package com.ford.wechat.entity.pageinterface;

import io.dabing.common.util.JSONUtil;

/**
 * Created by huangwen on 17/26/6.
 */
public class IsVerifyOwnerRes extends BasePageInterfaceRes {

	private static final long serialVersionUID = 4098695363977801823L;

	private Integer isverifyOwner;
	private IsVerifyOwnerVO data;
	private String qrcode;
	public Integer getIsverifyOwner() {
		return isverifyOwner;
	}
	public void setIsverifyOwner(Integer isverifyOwner) {
		this.isverifyOwner = isverifyOwner;
	}
	public IsVerifyOwnerVO getData() {
		return data;
	}
	public void setData(IsVerifyOwnerVO data) {
		this.data = data;
	}
	
	public String getQrcode() {
		return qrcode;
	}
	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	@Override
	public String toString() {
		return JSONUtil.objectToJson(this);
	}
}


