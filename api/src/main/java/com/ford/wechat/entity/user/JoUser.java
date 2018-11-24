package com.ford.wechat.entity.user;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wanglijun on 16/11/20.
 */
@Data
@Entity
@Table(name = "JO_USER")
public class JoUser {

    @Id
    @GeneratedValue(generator="SEQUENCE",strategy=GenerationType.SEQUENCE)
    @SequenceGenerator(sequenceName="S_JO_USER", allocationSize = 1, name ="SEQUENCE")
    @Column(name = "USER_ID", unique = true, nullable = false)
    private Long id;


    @Column(name = "WECHAT_USERNAME")
    private String wechatUserName;

    @Column(name = "DEALER_CODE")
    private String dealerCode;

    @Column(name="USERNAME",nullable=false, unique = true)
    private String username ;


    @Column(name="EMAIL",nullable=false,unique = true)
    private String email;

    @Column(name="PASSWORD")
    private String password;

    @Column(name="REGISTER_TIME",nullable=false)
    private Date registerTime=new Date();

    @Column(name="REGISTER_IP",nullable=false)
    private String registerIp;

    @Column(name="LAST_LOGIN_TIME")
    private Date lastLoginTime=new Date();

    @Column(name="LAST_LOGIN_IP")
    private String lastLoginIp;

    @Column(name="LOGIN_COUNT",nullable=false)
    private Integer loginCount=0;

    @Column(name="RESET_KEY")
    private String resetKey;

    @Column(name="RESET_PWD")
    private String resetPwd;

    @Column(name="ERROR_TIME")
    private Date errorTime;

    @Column(name="ERROR_COUNT")
    private Integer errorCount=0;

    @Column(name="ERROR_IP")
    private String errorIp;

    @Column(name="ACTIVATION",nullable=false)
    private Boolean activation;

    @Column(name="ACTIVATION_CODE")
    private String activationCode;

    @Column(name="MOBILE_PHONE")
    private String mobilePhone;

    @Column(name="MOBILE_PHONE_AUTH")
    private Integer mobilePhoneAuth;


    /**
     * Neel
     */
    @Column(name = "MEMBER_NO")
    private String memberNo;

}
