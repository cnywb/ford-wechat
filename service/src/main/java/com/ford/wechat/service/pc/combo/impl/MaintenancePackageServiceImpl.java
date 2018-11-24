/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MaintenancePackageServiceImpl.java
 */
package com.ford.wechat.service.pc.combo.impl;

import com.ford.wechat.entity.pc.combo.MaintenancePackage;
import com.ford.wechat.repository.pc.combo.MaintenancePackageRepository;
import com.ford.wechat.service.pc.combo.MaintenancePackageService;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述： MaintenancePackageServiceImpl 服务接口实现层代码类
 *
 * @author ziv
 * @since 1.0
 */
@Service
public class MaintenancePackageServiceImpl extends AbstractService implements MaintenancePackageService {

    @Autowired
    private MaintenancePackageRepository repository;
    @Override
    public List<MaintenancePackage> findByModelAndPackageType(String model, String packageType) {
        return repository.findByModelAndPackageType(model, packageType);
    }
    @Override
    public List findByPackageType( String packageType) {
        return repository.findByPackageType(packageType);
    }
}
