package com.ford.wechat.web.applink.vo;

import com.ford.wechat.web.vo.PageResp;

import java.util.Date;

/**
 * Created by wanglijun on 16/10/22.
 */
public class AppLinkInfoPageResp  extends PageResp{
    /**平台*/
    private String plantForm;

    /***/
    private String idCat;

    private String idApp;

    private String appLinkName;

    private String downloadName;

    private String appLinkUrl;

    private String appLinkImg;

    private Date createDate;


    public String getAppLinkImg() {
        return appLinkImg;
    }

    public void setAppLinkImg(String appLinkImg) {
        this.appLinkImg = appLinkImg;
    }

    public String getAppLinkName() {
        return appLinkName;
    }

    public void setAppLinkName(String appLinkName) {
        this.appLinkName = appLinkName;
    }

    public String getAppLinkUrl() {
        return appLinkUrl;
    }

    public void setAppLinkUrl(String appLinkUrl) {
        this.appLinkUrl = appLinkUrl;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDownloadName() {
        return downloadName;
    }

    public void setDownloadName(String downloadName) {
        this.downloadName = downloadName;
    }

    public String getIdApp() {
        return idApp;
    }

    public void setIdApp(String idApp) {
        this.idApp = idApp;
    }

    public String getIdCat() {
        return idCat;
    }

    public void setIdCat(String idCat) {
        this.idCat = idCat;
    }

    public String getPlantForm() {
        return plantForm;
    }

    public void setPlantForm(String plantForm) {
        this.plantForm = plantForm;
    }
}
