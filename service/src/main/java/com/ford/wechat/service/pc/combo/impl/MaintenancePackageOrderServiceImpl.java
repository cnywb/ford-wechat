/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MaintenancePackageOrderServiceImpl.java
 */
package com.ford.wechat.service.pc.combo.impl;

import com.ford.wechat.entity.pc.combo.MaintenancePackageOrder;
import com.ford.wechat.repository.pc.combo.MaintenancePackageOrderRepository;
import com.ford.wechat.service.pc.combo.MaintenancePackageOrderService;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述： MaintenancePackageOrderServiceImpl 服务接口实现层代码类
 *
 * @author ziv
 * @since 1.0
 */
@Service
public class MaintenancePackageOrderServiceImpl extends AbstractService implements MaintenancePackageOrderService {

    @Autowired
    private MaintenancePackageOrderRepository repository;

    @Override
    public List<MaintenancePackageOrder> findByVinAndProductType(String vin, String productType) {
        return repository.findByVinAndProductType(vin, productType);
    }
}
