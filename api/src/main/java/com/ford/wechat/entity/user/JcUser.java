package com.ford.wechat.entity.user;/**
 * Created by jovi on 08/05/2017.
 */

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-05-08 20:49
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Data
@Entity
@Table(name = "JC_USER")
public class JcUser {

    @Id
    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "GROUP_ID")
    private Long groupId;

    @Column(name = "DEPART_ID")
    private Long departId;

    @Column(name = "USERNAME")
    private String userName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "REGISTER_TIME")
    private Date registerTime;

    @Column(name = "REGISTER_IP")
    private String registerIp;

    @Column(name = "LAST_LOGIN_TIME")
    private Date lastLoginTime;

    @Column(name = "LAST_LOGIN_IP")
    private String lastLoginIp;

    @Column(name = "LOGIN_COUNT")
    private Long loginCount;

    @Column(name = "RANK")
    private Long rank;

    @Column(name = "UPLOAD_TOTAL")
    private Long uploadTotal;

    @Column(name = "UPLOAD_SIZE")
    private Long uploadSize;

    @Column(name = "UPLOAD_DATE")
    private Date updateDate;

    @Column(name = "IS_ADMIN")
    private Integer isAdmin;

    @Column(name = "IS_VIEWONLY_ADMIN")
    private Integer isViewonlyAdmin;

    @Column(name = "IS_SELF_ADMIN")
    private Integer isSelfAdmin;

    @Column(name = "IS_DISABLED")
    private Integer isDisabled;

    @Column(name = "FILE_TOTAL")
    private Long fileTotal;

    @Column(name = "GRAIN")
    private Long grain;

    @Column(name = "REGIST_TYPE")
    private Long registType;

    /** Neel */

  /*  @Column(name="IS_ADMIN",nullable=false)
    private Boolean admin;

    @Column(name="IS_VIEWONLY_ADMIN",nullable=false)
    private Boolean viewonlyAdmin;


    @Column(name="IS_SELF_ADMIN",nullable=false)
    private Boolean selfAdmin;

    @Column(name="IS_DISABLED",nullable=false)
    private Boolean disabled;*/

}
