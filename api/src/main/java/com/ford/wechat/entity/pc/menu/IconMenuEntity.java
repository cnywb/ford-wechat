/*
 * Copyright (c)  2016, dabing.io
 * All rights reserved.
 * IconMenu.java 2017-04-25 上午10:44
 */

package com.ford.wechat.entity.pc.menu;

import com.ford.wechat.entity.AuditEntity;
import com.ford.wechat.entity.im.ImCodeList;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 描述:图标菜单定义表
 *
 * @author yangkui create on 2017-04-25.
 * @since 1.0
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="PC_ICON_MENU")
public class IconMenuEntity extends AuditEntity {

    /**
     * 物理主键
     */
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "SEQ_PC_ICON_MENU_ID")
    @SequenceGenerator(name = "SEQ_PC_ICON_MENU_ID",allocationSize=1,sequenceName = "SEQ_PC_ICON_MENU_ID")
    private Long id;

    /**
     * 图标名称，用于页面显示的中文名称
     */
    @Column(name="NAME")
    private String name;

    /**
     * 图标分类，通过数据字典实现
     * 分类的排序在数据字典的属性上
     */
    @ManyToOne
    @JoinColumn(name = "TYPE_ID")
    private ImCodeList type;

    /**
     * 分类名称，冗余字段，用于页面上显示使用。当更改了分类后需要同时修改该属性
     */
    @Column(name="TYPE_NAME")
    private String typeName;
    /**
     * 分类代码，冗余字段，用于查询使用
     */
    @Column(name="TYPE_CODE")
    private String typeCode;

    /**
     * 图标图片地址，可以是相对路径，也可以是绝对路径
     */
    @Column(name="IMAGE_URL")
    private String imageUrl;

    /**
     * 图标在首页上显示的位置，从大到小依次排序。
     * 这是系统默认排序，每个用户自己的排序根据另外一个关系获取
     */
    @Column(name = "ORDER_")
    private int order;

    /**
     * 是否推荐，推荐的图标优先显示到首页。
     * 推荐的图标用户无法进行显示顺序调整。
     */
    @Column(name="RECOMMEND")
    private boolean recommend;

    /**
     * 是否需要认证才能访问
     */
    @Column(name="REQUIRED_AUTH")
    private boolean requiredAuth;

    /**
     * 推荐的结束时间，到点后不再放到首页上，而是按顺序显示
     *
     */
    @Column(name="RECOMMEND_END_TIME")
    private Date recommendEndTime;


    /**
     * 标红提示，用于醒目的样式
     */
    @Column(name="RED_TIP")
    private boolean redTip;

    /**
     * 跳转链接地址，可以是基于路由的地址，也可以是http协议的绝对路径地址，也可以是tel开头
     */
    @Column(name = "HREF")
    private String href;

}