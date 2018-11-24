package com.ford.wechat.entity.dealer;/**
 * Created by jovi on 01/04/2017.
 */

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-04-01 14:02
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "CLUB_DEALER")
public class ClubDealer {
    /**
     * 物理主键
     */
    @Id
    @Column(name = "DLID")
    private Long id;

    /**
     * 经销商服务代码
     */
    @Column(name = "SST_CODE")
    private String sstCode;

    /**
     * 经销商销售代码
     */
    @Column(name = "DLSALECODE")
    private String dlsaleCode;

    /**
     * 经销商名称
     */
    @Column(name = "SST_NAME")
    private String sstName;

    /**
     * 大区
     */
    @Column(name = "DLANAME")
    private String dlaName;
}
