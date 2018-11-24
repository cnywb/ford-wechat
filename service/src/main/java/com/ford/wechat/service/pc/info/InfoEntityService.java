/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * InfoEntity.java 2017-05-16 19:31:48
 */
package com.ford.wechat.service.pc.info;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import com.ford.wechat.entity.pc.info.InfoEntity;
import java.util.List;
/**
 * Created by ziv.hung on 2017-05-16 19:31:48 .
 * @since 1.0
 */
public interface InfoEntityService  {
    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page     分页对象，里面有关键字进行模糊匹配
     * @return
     */
    Page<InfoEntity> findByGridPage(GridPage page);

    void save(InfoEntity object);

    /**
    * 逻辑删除 deleted true
    */
    void deleteByLogic(List<InfoEntity> objectList);

    /**
    * 逻辑删除 deleted true
    */
    void deleteByLogic(InfoEntity object);

    /**
    * 逻辑删除 deleted true
    */
    void deleteByLogic(Long id);

    /**
    * 物理删除
    */
    void delete(InfoEntity object);

    /**
    * 物理删除
    */
    void delete(Long id);

    void update(InfoEntity object);

    InfoEntity get(Long id);
}