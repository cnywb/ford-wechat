package com.ford.wechat.controller.applink.vo;

/**
 * Created by wanglijun on 16/11/2.
 */
public class AppLinkInfoPageResp {

    /**主键*/
    private Long id;

    /**平台:平台信息iOS，Android*/
    private String plantForm;

    /**Must Have的标识用于查询list*/
    private String idCat;

    /**app的ID，便于查询详情*/
    private String idApp;

    /**app名称*/
    private String appLinkName;

    /**下载公司名称*/
    private String downloadName;

    /**APP 下载链接*/
    private String appLinkUrl;

    /**APP图标*/
    private String appLinkImg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlantForm() {
        return plantForm;
    }

    public void setPlantForm(String plantForm) {
        this.plantForm = plantForm;
    }

    public String getIdCat() {
        return idCat;
    }

    public void setIdCat(String idCat) {
        this.idCat = idCat;
    }

    public String getIdApp() {
        return idApp;
    }

    public void setIdApp(String idApp) {
        this.idApp = idApp;
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

    public String getAppLinkUrl() {
        return appLinkUrl;
    }

    public void setAppLinkUrl(String appLinkUrl) {
        this.appLinkUrl = appLinkUrl;
    }

    public String getAppLinkImg() {
        return appLinkImg;
    }

    public void setAppLinkImg(String appLinkImg) {
        this.appLinkImg = appLinkImg;
    }
}
