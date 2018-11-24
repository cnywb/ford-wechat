package com.ford.wechat.entity.repair;/**
 * Created by jovi on 01/04/2017.
 */

import lombok.Data;
import lombok.EqualsAndHashCode;

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
 * All rights reserved. 2017-04-01 13:56
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "FORD_REPAIR_WEB")
public class FordRepairWeb {
    /**
     * 物理主键
     */
    @Id
    @Column(name = "REPAIR_ID")
    private Long id;

    /**
     * vin码
     */
    @Column(name = "VVIN")
    private String vvin;

    /**
     * 经销商代码
     */
    @Column(name = "VSST_ID")
    private String vsstId;

    /**
     * 经销商名称
     */
    @Column(name = "VSST_NAME")
    private String vsstName;

    /**
     * 创建时间
     */
    @Column(name = "DMS_CREATE_DATE")
    private Date createDate;
}
