/*
 * Copyright (c)  2016, dabing.io
 * All rights reserved.
 * InfoType.java 2017-04-27 上午11:12
 */

package com.ford.wechat.entity.pc.info;

import com.ford.wechat.entity.AuditEntity;
import com.ford.wechat.entity.im.ImCodeList;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 描述:资讯信息，这里只存放原始数据，页面关键字搜索和详情显示通过ES服务来实现
 *
 * @author yangkui create on 2017-04-27.
 * @since 1.0
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "PC_INFO")
public class InfoEntity extends AuditEntity {

    /**
     * 物理主键
     */
    @javax.persistence.Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PC_INFO_ID")
    @SequenceGenerator(name = "SEQ_PC_INFO_ID", allocationSize = 1, sequenceName = "SEQ_PC_INFO_ID")
    private Long id;

    /**
     * 资讯信息所属的分类字典
     */
    @Column(name = "INFO_TYPE")
    private String infoType;

    /**
     * 资讯标题
     */
    @Column(name = "TITLE")
    private String title;
    /**
     * 标题图片地址
     */
    @Column(name = "ICON_URL")
    private String iconUrl;

    /**
     * 资讯摘要
     */
    @Column(name = "DIGEST")
    private String digest;

    /**
     * 资讯相关的tag标签，通过标签进行精确匹配
     */
    @Column(name = "TAGS")
    private String tags;

    /**
     * 资讯内容
     */
    @Column(name = "CONTENT")
    private String content;

    /**
     * 原文链接，资讯的详细内容链接
     */
    @Column(name = "SOURCE_HREF")
    private String sourceHref;

    /**
     * 是否已经索引过，数据是否已经在ES服务当中
     * 新创建和修改都需要重新建立索引
     */
    @Column(name = "INDEXED")
    private boolean indexed = false;

    /**
     * ES索引ID
     */
    @Column(name = "ES_ID")
    private String esId;
}