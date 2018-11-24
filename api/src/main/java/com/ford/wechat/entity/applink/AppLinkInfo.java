package com.ford.wechat.entity.applink;

import com.ford.wechat.entity.AuditEntity;

import javax.persistence.*;

/**
 * Created by wanglijun on 16/10/22.
 */
@Entity
@Table(name="WE_APP_LINK_INFO")
public class AppLinkInfo  extends AuditEntity{

    /**主键*/
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "SEQ_WE_APP_LINK_INFO_ID")
    @SequenceGenerator(name = "SEQ_WE_APP_LINK_INFO_ID",allocationSize=1,sequenceName = "SEQ_WE_APP_LINK_INFO_ID")
    private Long id;

    /**平台:平台信息iOS，Android*/
    @Column(name="PLANT_FORM")
    private String plantForm;

    /**Must Have的标识用于查询list*/
    @Column(name="ID_CAT")
    private String idCat;

    /**app的ID，便于查询详情*/
    @Column(name="ID_APP")
    private String idApp;
    /**app名称*/
    @Column(name="APP_LINK_NAME")
    private String appLinkName;
    /**下载公司名称*/
    @Column(name="DOWNLOAD_NAME")
    private String downloadName;

    /**APP 下载链接*/
    @Column(name="APP_LINK_URL")
    private String appLinkUrl;

    /**APP图标*/
    @Column(name="APP_LINK_IMG")
    private String appLinkImg;


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



    public String getDownloadName() {
        return downloadName;
    }

    public void setDownloadName(String downloadName) {
        this.downloadName = downloadName;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
