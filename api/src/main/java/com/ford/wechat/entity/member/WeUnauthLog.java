package com.ford.wechat.entity.member;

import com.ford.wechat.entity.VersionEntity;
import lombok.Data;

import javax.persistence.*;


/**
 * Created by zhaoliang on 2017/5/22.
 */
@Data
@Entity
@Table(name = "WE_UNAUTH_LOG")
public class WeUnauthLog extends VersionEntity {

    /**主键*/
    @javax.persistence.Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "SEQ_WE_UNAUTH_LOG")
    @SequenceGenerator(name = "SEQ_WE_UNAUTH_LOG",allocationSize=1,sequenceName = "SEQ_WE_UNAUTH_LOG")
    private Long id;

    /**原用户主键*/
    @Column(name = "USER_ID")
    private Integer userId;

    /**原微信唯一标识*/
    @Column(name = "OLD_OPEN_ID")
    private String oldOpenId;

    /**现微信唯一标识*/
    @Column(name = "NEW_OPEN_ID")
    private String newOpenId;

    /**原手机号*/
    @Column(name = "OLD_MOBILE")
    private String oldMobile;

    /**先手机号*/
    @Column(name = "NEW_MOBILE")
    private String newMobile;

    /** vin码*/
    @Column(name = "VIN")
    private String vin;

    /** 0 解绑失败 1 解绑成功 2 修改手机成功 3 修改手机失败*/
    @Column(name = "STATUS")
    private Integer status;

    /**原姓名*/
    @Column(name = "OLD_NAME")
    private String oldName;

    /**先姓名*/
    @Column(name = "NEW_NAME")
    private String newName;

    /**行驶证编号*/
    @Column(name = "LICENSE_IMG_ID")
    private Integer licenseImgId;

    /**行驶证地址*/
    @Column(name = "LICENSE_URL")
    private String licenseUrl;

}
