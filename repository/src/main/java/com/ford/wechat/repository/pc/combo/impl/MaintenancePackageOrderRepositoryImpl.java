/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MaintenancePackageOrderRepositoryImpl.java
 */
package com.ford.wechat.repository.pc.combo.impl;

import com.ford.wechat.entity.pc.combo.MaintenancePackageOrder;
import com.ford.wechat.repository.pc.combo.MaintenancePackageOrderRepository;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述：MaintenancePackageOrderRepositoryImpl 仓储服务接口实现层代码类
 *
 * @author ziv
 * @since 1.0
 */
@Repository
public class MaintenancePackageOrderRepositoryImpl extends DefaultJpaRepository<MaintenancePackageOrder, Long> implements MaintenancePackageOrderRepository {

    public List<MaintenancePackageOrder> findByVinAndProductType(String vin, String productType) {
        StringQuery query = StringQuery.newQuery()
                .query("from MaintenancePackageOrder a where a.vin=:vin and a.productType=:productType")
                .param("vin", vin)
                .param("productType", productType).build();
        return find(query);
    }
}
