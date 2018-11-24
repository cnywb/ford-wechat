/*
 * Copyright (c) dabing.io
 * All rights reserved. 
 * MaintenancePackageOrderService.java
 */

package com.ford.wechat.service.pc.combo;

import com.ford.wechat.entity.pc.combo.MaintenancePackageOrder;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.Service;

import java.util.List;

/**
 * 描述：MaintenancePackageOrderService 服务接口层代码类
 *
 * @author ziv
 * @since 1.0
 */
public interface MaintenancePackageOrderService extends Service {

    List<MaintenancePackageOrder> findByVinAndProductType(String vin, String productType);
}
