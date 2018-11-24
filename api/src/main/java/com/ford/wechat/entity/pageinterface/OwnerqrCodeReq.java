package com.ford.wechat.entity.pageinterface;

/**
 * Created by huangwen on 17/26/6.
 */
public class OwnerqrCodeReq extends BasePageInterfaceReq {

	/**
	 * url:活动url
	 */
	private static final long serialVersionUID = 6273618324650164960L;
	private String name;
	private String mobile;
	private String vin;
	private String url;
	
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	

	
	
}


