/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * IconMenuEntity.java 2017-05-05 15:37:27
 */
package com.ford.wechat.service.pc.menu;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import com.ford.wechat.entity.pc.menu.IconMenuEntity;
import org.springframework.cache.annotation.Cacheable;

import javax.swing.*;
import java.util.List;
/**
 * Created by yangkui on 2017-05-05 15:37:27 .
 * @since 1.0
 */
public interface IconMenuEntityService  {
    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page     分页对象，里面有关键字进行模糊匹配
     * @return
     */
    Page<IconMenuEntity> findByGridPage(GridPage page);

    void save(IconMenuEntity object);

    /**
     * 带缓存的查询
     * @return
     */
    List<IconMenuEntity> findAll();

    /**
     * 带缓存查询所有推荐的菜单
     * @return
     */
    List<IconMenuEntity> findRecommendMenus();

    /**
     * 查询无缓存的数据
     * @return
     */
    List<IconMenuEntity> findAllNoCache();


    /**
    * 逻辑删除 deleted true
    */
    void deleteByLogic(List<IconMenuEntity> objectList);

    /**
    * 逻辑删除 deleted true
    */
    void deleteByLogic(IconMenuEntity object);

    /**
    * 逻辑删除 deleted true
    */
    void deleteByLogic(Long id);

    /**
    * 物理删除
    */
    void delete(IconMenuEntity object);

    /**
    * 物理删除
    */
    void delete(Long id);

    void update(IconMenuEntity object);

    IconMenuEntity get(Long id);
}