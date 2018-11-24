package com.ford.wechat.entity.user;/**
 * Created by jovi on 01/04/2017.
 */

import com.ford.wechat.entity.VersionEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-04-01 13:46
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "FORD_CAR")
public class FordCar  {
    /**
     * 物理主键
     */
    @Id
    @Column(name = "VCAR_ID")
    private Long id;
    /**
     * vin码
     */
    @Column(name = "VVIN")
    private String vvin;

    /**
     * 经销商ID
     */
    @Column(name = "VDEALER_ID")
    private String vdealerId;

    /**
     * 经销商名称
     */
    @Column(name = "VDEALER_NAME")
    private String vdealerName;
    /**
     * 销售日期
     */
    @Column(name = "VPURCHASED_DATE")
    private String vpurchasedDate;

    @Column(name = "VMODEL")
    private String vmodel;

}
