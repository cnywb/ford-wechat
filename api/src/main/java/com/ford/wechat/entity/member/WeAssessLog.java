package com.ford.wechat.entity.member;

import com.ford.wechat.entity.VersionEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhaoliang on 2017/5/22.
 */
@Data
@Entity
@Table(name = "WE_ASSESS_LOG")
public class WeAssessLog extends VersionEntity {

    /**主键*/
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "SEQ_WE_ASSESS_LOG")
    @SequenceGenerator(name = "SEQ_WE_ASSESS_LOG",allocationSize=1,sequenceName = "SEQ_WE_ASSESS_LOG")
    private Long id;

    /**审核时间*/
    @Column(name = "AUTH_DATE")
    private Date authDate;

    /**审核结果*/
    @Column(name = "AUTH_RESULT")
    private Integer authResult;

   /**审核方式*/
    @Column(name = "AUTH_WAY")
    private String authWay;

    /**审核次数*/
    @Column(name = "AUTH_COUNT")
    private Integer authCount;

    /**审核VIN码*/
    @Column(name = "VIN")
    private String vin;

    /**微信唯一标识*/
    @Column(name = "OPEN_ID")
    private String openId;

    /**认证手机*/
    @Column(name = "USER_MOBILE")
    private String userMobile;

    /**认证姓名*/
    @Column(name = "USER_NAME")
    private String userName;

    /**备注*/
    @Column(name = "REMARK")
    private String remark;


}
