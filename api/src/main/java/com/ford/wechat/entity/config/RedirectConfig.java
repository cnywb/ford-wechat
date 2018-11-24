package com.ford.wechat.entity.config;

import com.ford.wechat.entity.AuditEntity;

import javax.persistence.*;

/**
 * Created by wanglijun on 16/11/1.
 */
@Entity
@Table(name="WE_REDIRECT_CONFIG")
public class RedirectConfig extends AuditEntity{

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "SEQ_WE_REDIRECT_CONFIG_ID")
    @SequenceGenerator(name = "SEQ_WE_REDIRECT_CONFIG_ID",allocationSize=1,sequenceName = "SEQ_WE_REDIRECT_CONFIG_ID")
    private Long id;

    /**微信入口状态参数*/
    @Column(name="RED_STATE")
    private String state;

    /**授权跳转的URL，带有openid参数*/
    @Column(name="RED_URL")
    private String url;

    /**备注说明*/
    @Column(name="REMARK")
    private String remark;


    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
