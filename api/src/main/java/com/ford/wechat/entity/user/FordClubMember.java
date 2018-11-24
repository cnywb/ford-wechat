package com.ford.wechat.entity.user;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by wanglijun on 16/12/11.
 */
@Entity
@Data
@Table(name = "FORD_CLUB_MEMBER")
public class FordClubMember {

    /**
     * 俱乐部会员编号
     */
    @Id
    @Column(name = "VCARD_ID", nullable = false)
    private String id;
    /**
     * vin码
     */
    @Column(name = "VVIN")
    private String vvin;

    /**
     * 手机
     */
    @Column(name = "VMOBILE")
    private String mobile;

    /**
     * 客户名称
     */
    @Column(name = "VNAME")
    private String name;

    @Column(name = "USER_ID")
    private Long userId;
    /**
     * 认证时间
     */
    @Column(name = "DCRT_DATE")
    private Date dcrtDate;

    /** Neel */

    /**
     * 客户编号
     */
    @Column(name = "VCUSTOMER_ID", length = 20)
    private String vcustomerId;


    /**
     * 俱乐部卡号
     */
    @Column(name = "VCARD_NO", length = 60)
    private String vcardNo;


    /**
     * 车辆编号
     */
    @Column(name = "VCAR_ID", length = 60)
    private String vcarId;

    /**
     * 会员卡状态
     */
    @Column(name = "VCARD_STATUS")
    private String vcardStatus;

    /**
     * 批处理标识 0或者空 未处理 1 已处理
     */
    @Column(name = "JOB_STATUS")
    private Integer jobStatus = 0;

    /**
     * 会员号
     */
    @Column(name = "MEMBER_NO")
    private String memberNo;

    /**
     * 微信唯一标识
     */
    @Column(name = "OPEN_ID")
    private String openId;

    /**
     * 认证方式
     *
     */
    @Column(name = "AUTH_WAY")
    private String authWay;

    /**
     * 认证来源
     */
    @Column(name = "SOURCE")
    private String source;

    /**
     * 渠道代码
     */
    @Column(name = "CHANNEL_CODE")
    private String channelCode;

    /**
     * 渠道类型
     */
    @Column(name = "CHANNEL_TYPE")
    private Integer channelType;

    /**
     * 行驶证照片地址
     */
    @Column(name = "LICENSE_URL")
    private String licenseUrl;
    /**
     * 行驶证扫码结果
     */
    @Column(name = "LICENSE_JSON")
    private String licenseJson;

    /**
     * 行驶证上传表主键ID
     */
    @Column(name = "LICENSE_IMG_ID")
    private Long licenseImgId;

    @Column(name = "VADDRESS")
    private String address;

}
