/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * QAInfoEntity.java 2017-05-16 19:35:16
 */
package com.ford.wechat.service.pc.info;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import com.ford.wechat.entity.pc.info.QAInfoEntity;
import java.util.List;
/**
 * Created by ziv.hung on 2017-05-16 19:35:16 .
 * @since 1.0
 */
public interface QAInfoEntityService  {
    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page     分页对象，里面有关键字进行模糊匹配
     * @return
     */
    Page<QAInfoEntity> findByGridPage(GridPage page);

    void save(QAInfoEntity object);

    /**
    * 逻辑删除 deleted true
    */
    void deleteByLogic(List<QAInfoEntity> objectList);

    /**
    * 逻辑删除 deleted true
    */
    void deleteByLogic(QAInfoEntity object);

    /**
    * 逻辑删除 deleted true
    */
    void deleteByLogic(Long id);

    /**
    * 物理删除
    */
    void delete(QAInfoEntity object);

    /**
    * 物理删除
    */
    void delete(Long id);

    void update(QAInfoEntity object);

    QAInfoEntity get(Long id);
}