/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * QAInfoEntity.java 2017-05-16 19:35:15
 */
package com.ford.wechat.repository.pc.info;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;
import com.ford.wechat.entity.pc.info.QAInfoEntity;

/**
 * Created by ziv.hung on 2017-05-16 19:35:15.
 * @since 1.0
 */
public interface QAInfoEntityRepository extends JpaRepository<QAInfoEntity, Long> {
  /**
   * 根据GridPage对象按分页查找服务
   * @param page 分页对象，里面有关键字进行模糊匹配
   * @return
   */
   Page<QAInfoEntity> findByGridPage(GridPage page);
}

