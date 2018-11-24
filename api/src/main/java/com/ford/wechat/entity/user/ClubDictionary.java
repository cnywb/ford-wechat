package com.ford.wechat.entity.user;/**
 * Created by jovi on 01/04/2017.
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-06-26
 * </p>
 *
 * @author huangwen
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "CLUB_DICTIONARY")
public class ClubDictionary  {
    /**
     * 物理主键
     */
    @Id
    @Column(name = "CDID")
    private Long id;
    /**
     * 字典类型
     */
    @Column(name = "CDTYPE")
    private String cdtype;


    /**
     * code
     */
    @Column(name = "CDCODE")
    private String cdcode;
    /**
     * 
     */
	@Column(name = "CDNAME")
	private String cdname;
	
	
}
