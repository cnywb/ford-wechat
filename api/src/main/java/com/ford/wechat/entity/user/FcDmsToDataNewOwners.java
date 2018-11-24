package com.ford.wechat.entity.user;/**
 * Created by jovi on 17/07/2017.
 */

import lombok.Data;

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
 * All rights reserved. 2017-07-17 16:34
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Data
@Entity
@Table(name = "FC_DMS_TO_DATA_NEWOWNERS")
public class FcDmsToDataNewOwners {
    /**
     * 车主编号
     */
    @Id
    @Column(name = "OWNER_ID")
    private String ownerId;
    /**
     * vin码
     */
    @Column(name = "VIN")
    private String vin;
    /**
     * 经销商服务代码
     */
    @Column(name = "DEALER_SERVICE_CODE")
    private String dealerServiceCode;
}
