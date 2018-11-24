/*
 * Copyright (c) dabing.io
 * All rights reserved. 
 * MenuReadRecordEntityService.java
 */

package com.ford.wechat.service.pc.message;

import com.ford.wechat.entity.pc.menu.MenuReadRecordEntity;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.Service;

import java.util.Date;
import java.util.List;

/**
 * 描述：MenuReadRecordEntityService 服务接口层代码类
 *
 * @author ziv
 * @since 1.0
 */
public interface MenuReadRecordEntityService extends Service {

    Page<Object[]> pagingBy(GridPage page, String datePattern, Date startDate, Date endDate);

    void save(List<MenuReadRecordEntity> object);
}
