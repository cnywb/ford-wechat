/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MenuReadRecordEntityRepository.java
 */
package com.ford.wechat.repository.pc.message;

import com.ford.wechat.entity.pc.menu.MenuReadRecordEntity;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;

import java.util.Date;
import java.util.List;


/**
 * 描述：MenuReadRecordEntityRepository 仓储服务接口层代码类
 *
 * @author ziv
 * @since 1.0
 */
public interface MenuReadRecordEntityRepository extends JpaRepository<MenuReadRecordEntity, Long> {

    Page<Object[]> pagingBy(GridPage page, String datePattern, Date startDate, Date endDate);

    List<Object[]> findForExcel(String datePattern, Date startDate, Date endDate);
}
