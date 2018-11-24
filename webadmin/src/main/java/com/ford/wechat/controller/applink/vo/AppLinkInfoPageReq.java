package com.ford.wechat.controller.applink.vo;

import com.ford.wechat.controller.vo.PageReq;

/**
 * Created by wanglijun on 16/11/2.
 */
public class AppLinkInfoPageReq extends PageReq{
    /**
     * 平台
     */
    private String plantForm;
    /**
     * app名称
     */
    private String appLinkName;
    /**
     * 下载公司名称
     */
    private String downloadName;

    public String getPlantForm() {
        return plantForm;
    }

    public void setPlantForm(String plantForm) {
        this.plantForm = plantForm;
    }

    public String getAppLinkName() {
        return appLinkName;
    }

    public void setAppLinkName(String appLinkName) {
        this.appLinkName = appLinkName;
    }

    public String getDownloadName() {
        return downloadName;
    }

    public void setDownloadName(String downloadName) {
        this.downloadName = downloadName;
    }
}
