package com.ford.wechat.entity.pageinterface;

import io.dabing.common.util.JSONUtil;

/**
 * Created by huangwen on 17/26/6.
 */
public class OwnerQrCodeVO {

/**
	*model 车型
	*name 姓名
	*mobile 手机号
	*vin 车架号
	*buyCarTime	购车时间
	*openId 微信号
	*url 活动路径
 */
	private String model;
	private String name;
	private String mobile;
	private String vin;
	private String buyCarTime;
	private String openId;
	private String url;
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getBuyCarTime() {
		return buyCarTime;
	}
	public void setBuyCarTime(String buyCarTime) {
		this.buyCarTime = buyCarTime;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return JSONUtil.objectToJson(this);
	}
}


