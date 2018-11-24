/*
 * Copyright (c)  2016, dabing.io
 * All rights reserved.
 * MenuReadRecordEntity.java 2017-04-25 上午10:44
 */

package com.ford.wechat.entity.pc.menu;

import com.ford.wechat.entity.AuditEntity;
import com.ford.wechat.entity.im.ImCodeList;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 描述:菜单点击记录表
 *
 * @author yangkui create on 2017-04-25.
 * @since 1.0
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "PC_MENU_READ_RECORD")
public class MenuReadRecordEntity extends AuditEntity {

    /**
     * 物理主键
     */
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PC_MENU_READ_RECORD_ID")
    @SequenceGenerator(name = "SEQ_PC_MENU_READ_RECORD_ID", allocationSize = 1, sequenceName = "SEQ_PC_MENU_READ_RECORD_ID")
    private Long id;

    /**
     * 菜单ID
     */
    @Column(name = "MENU_ID")
    private Long menuId;

    /**
     * 菜单名称
     */
    @Column(name = "MENU_NAME")
    private String menuName;

    /**
     * 菜单排序值
     */
    @Column(name = "MENU_SORT")
    private Long menuSort;

    /**
     * 点击时间
     */
    @Column(name = "READ_TIME")
    private Date readTime;

    /**
     * 分类名称，冗余字段，用于页面上显示使用。当更改了分类后需要同时修改该属性
     */
    @Column(name="TYPE_NAME")
    private String typeName;
}