package com.ford.wechat.entity.user;/**
 * Created by jovi on 08/05/2017.
 */

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
 * All rights reserved. 2017-05-08 20:47
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Data
@Entity
@Table(name = "JB_USER_EXT")
public class JbUserExt {

    @Id
    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "REALNAME")
    private String realName;

    @Column(name = "GENDER")
    private Integer gender;

    @Column(name = "AVATAR")
    private String avatar;

    @Column(name = "BIRTHDAY")
    private Date birthday;

    @Column(name = "INTRO")
    private String info;

    @Column(name = "COMEFROM")
    private String comeFrom;

    @Column(name = "QQ")
    private String qq;

    @Column(name = "MSN")
    private String msn;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "MOBILE")
    private String mobile;
}
