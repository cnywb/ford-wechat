package com.ford.wechat.entity.dict;/**
 * Created by jovi on 14/08/2017.
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
 * All rights reserved. 2017-08-14 21:31
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Data
@Entity
@Table(name = "FC_DMS_DICTIONARY")
public class FcDmsDictionary {
    /**
     * 代码
     */
    @Id
    @Column(name = "CODE")
    private String code;
    /**
     * 类型名称
     */
    @Column(name = "CODE_NAME")
    private String codeName;
    /**
     * 类型
     */
    @Column(name = "TYPE")
    private String type;
}
