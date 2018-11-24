/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MaintenancePackageRepository.java
 */
package com.ford.wechat.repository.pc.combo;

import com.ford.wechat.entity.pc.combo.MaintenancePackage;
import io.dabing.core.repository.JpaRepository;

import java.util.List;


/**
 * 描述：MaintenancePackageRepository 仓储服务接口层代码类
 *
 * @author ziv
 * @since 1.0
 */
public interface MaintenancePackageRepository extends JpaRepository<MaintenancePackage, Long> {
    List findByPackageType( String packageType);
    List<MaintenancePackage> findByModelAndPackageType(String model, String packageType);
}
