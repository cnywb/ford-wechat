/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MaintenancePackageOrderRepository.java
 */
package com.ford.wechat.repository.pc.combo;

import com.ford.wechat.entity.pc.combo.MaintenancePackageOrder;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;

import java.util.List;


/**
 * 描述：MaintenancePackageOrderRepository 仓储服务接口层代码类
 *
 * @author ziv
 * @since 1.0
 */
public interface MaintenancePackageOrderRepository extends JpaRepository<MaintenancePackageOrder, Long> {

    List<MaintenancePackageOrder> findByVinAndProductType(String vin,String productType);

}
