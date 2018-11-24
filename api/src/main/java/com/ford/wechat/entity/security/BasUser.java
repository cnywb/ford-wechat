package com.ford.wechat.entity.security;


import com.ford.wechat.entity.AuditEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 管理端用户
 *
 * @author li.yu
 * @since 1.0
 */
@Entity
@Table(name = "WE_USER")
public class BasUser extends AuditEntity {

    /** 物理主键 */
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "SEQ_WE_USER_ID")
    @SequenceGenerator(name = "SEQ_WE_USER_ID",allocationSize=1,sequenceName = "SEQ_WE_USER_ID")
    protected Long id;
    /** 后台登录用户名 */
    @Column(name = "LOGIN_NAME")
    private String loginName;
    /** 后台登录密码 */
    @Column(name = "PASSWORD")
    private String password;
    /** 姓名 */
    @Column(name = "NAME")
    private String name;
    /** 联系电话 */
    @Column(name = "MOBILE")
    private String mobile;
    /** 邮件 */
    @Column(name = "EMAIL")
    private String email;
    /** 机构代码 */
    @Column(name = "ORG_CODE")
    private String orgCode;
    /** 机构ID */
    @Column(name = "ORG_ID")
    private Long orgId;
    /** 机构名称 */
    @Column(name = "ORG_NAME")
    private String orgName;

    /**密码有效期*/
    @Column(name="CREDENTIAL_EXPIRED_DATE")
    private Date credentialExpiredDate;


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Date getCredentialExpiredDate() {
        return credentialExpiredDate;
    }

    public void setCredentialExpiredDate(Date credentialExpiredDate) {
        this.credentialExpiredDate = credentialExpiredDate;
    }
}
