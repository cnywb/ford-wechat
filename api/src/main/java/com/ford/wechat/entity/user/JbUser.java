package com.ford.wechat.entity.user;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Neel
 */
@Data
@Entity
@Table(name = "jb_user")
public class JbUser {

    @Id
    @Column(name = "USER_ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "REGISTER_TIME", nullable = false)
    private Date registerTime = new Date(0);

    @Column(name = "REGISTER_IP", nullable = false)
    private String registerIp;

    @Column(name = "LAST_LOGIN_TIME")
    private Date lastLoginTime = new Date(0);

    @Column(name = "LAST_LOGIN_IP")
    private String lastLoginIp;

    @Column(name = "LOGIN_COUNT", nullable = false)
    private Integer loginCount = 0;

    @Column(name = "UPLOAD_TOTAL")
    private long uploadTotal = 0L;

    @Column(name = "UPLOAD_TODAY")
    private Integer uploadToday = 0;

    @Column(name = "UPLOAD_SIZE")
    private Integer uploadSize = 0;

    @Column(name = "UPLOAD_DATE")
    private Date uploadDate;

    @Column(name = "IS_ADMIN")
    private Boolean admin;

    @Column(name = "IS_DISABLED")
    private Boolean disabled;

    @Column(name = "POINT")
    private Long point = 0L;

    @Column(name = "PRESTIGE")
    private Long prestige = 0L;

    @Column(name = "INTRODUCTION")
    private String introduction;

    @Column(name = "SIGNED")
    private String signed;

    @Column(name = "AVATAR")
    private String avatar;

    @Column(name = "AVATAR_TYPE")
    private short avatarType;

    @Column(name = "TOPIC_COUNT")
    private Integer topicCount = 0;

    @Column(name = "REPLY_COUNT")
    private Integer replyCount = 0;

    @Column(name = "PRIME_COUNT")
    private Integer primeCount = 0;

    @Column(name = "POST_TODAY")
    private Integer postToday = 0;

    @Column(name = "LAST_POST_TIME")
    private Date lastPostTime;

    @Column(name = "PROHIBIT_POST")
    private short prohibitPost;

    @Column(name = "PROHIBIT_TIME")
    private Date prohibitTime;

    @Column(name = "GRADE_TODAY")
    private Integer gradeToday;

    @Column(name = "MAGIC_PACKET_SIZE")
    private Integer magicPacketSize = 0;

    @Column(name = "GROUP_ID")
    private Long groupId;

}
