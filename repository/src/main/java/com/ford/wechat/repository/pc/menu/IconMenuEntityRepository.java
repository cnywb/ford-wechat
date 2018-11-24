/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * IconMenuEntity.java 2017-05-05 15:37:27
 */
package com.ford.wechat.repository.pc.menu;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;
import com.ford.wechat.entity.pc.menu.IconMenuEntity;

import java.util.List;

/**
 * Created by yangkui on 2017-05-05 15:37:27.
 * @since 1.0
 */
public interface IconMenuEntityRepository extends JpaRepository<IconMenuEntity, Long> {
  /**
   * 根据GridPage对象按分页查找服务
   * @param page 分页对象，里面有关键字进行模糊匹配
   * @return
   */
   Page<IconMenuEntity> findByGridPage(GridPage page);

    List<IconMenuEntity> findRecommendMenus();

    List<IconMenuEntity> findAll();
}



