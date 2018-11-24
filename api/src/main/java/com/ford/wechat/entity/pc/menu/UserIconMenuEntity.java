/*
 * Copyright (c)  2016, dabing.io
 * All rights reserved.
 * UserIconMenuEntity.java 2017-04-27 上午11:04
 */

package com.ford.wechat.entity.pc.menu;

import com.ford.wechat.entity.AuditEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 描述:用户的图标菜单排序关系维护
 *
 * @author yangkui create on 2017-04-27.
 * @since 1.0
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="PC_USER_ICON_MENU")
public class UserIconMenuEntity extends AuditEntity {

    /**
     * 物理主键
     */
    @javax.persistence.Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "SEQ_PC_USER_ICON_MENU_ID")
    @SequenceGenerator(name = "SEQ_PC_USER_ICON_MENU_ID",allocationSize=1,sequenceName = "SEQ_PC_USER_ICON_MENU_ID")
    private Long id;

    /**
     * 用户的微信OPENID
     */
    @Column(name = "OPENID")
    private String openId;

    /**
     * 客户的VIN码
     */
    @Column(name = "VIN")
    private String vin;

    /**
     * 对应的图标菜单
     */
    @ManyToOne
    @JoinColumn(name = "ICON_MENU_ID")
    private IconMenuEntity iconMenu;

    /**
     * 图标在首页上显示的位置，从大到小依次排序。
     */
    @Column(name = "ORDER_")
    private int order;

}