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
 * All rights reserved. 2017-05-08 20:37
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Data
@Entity
@Table(name = "JC_USER_EXT")
public class JcUserExt {

    @Id
    @Column(name = "USER_ID", nullable = false)
    private Long userId;


    @Column(name = "REALNAME")
    private String realName;

    @Column(name = "GENDER")
    private Integer gender;

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

    @Column(name = "USER_IMG")
    private String userImg;

    @Column(name = "USER_SIGNATURE")
    private String userSignature;

    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "CITY")
    private String city;

    @Column(name = "COUNTY")
    private String county;

    @Column(name = "CAR_BUYING_DATE")
    private Date carBuyingDate;

    @Column(name = "CAR_BUYING_DEALER")
    private String carBuyingDealer;

    @Column(name = "CAR_REPAIR_DEALER")
    private String carRepairDealer;

    @Column(name = "CAR_MODEL")
    private String carModel;

    @Column(name = "CAR_STYLE")
    private String carStyle;

    @Column(name = "CAR_COLOR")
    private String carColor;

    @Column(name = "BUYING_DEALER_PROVINCE")
    private String buyingDealerProvince;

    @Column(name = "BUYING_DEALER_CITY")
    private String buyingDealerCity;

    @Column(name = "DRIVING_LICENSE")
    private String drivingLicense;

}
