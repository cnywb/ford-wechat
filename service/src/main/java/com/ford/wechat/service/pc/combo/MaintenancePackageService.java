/*
 * Copyright (c) dabing.io
 * All rights reserved. 
 * MaintenancePackageService.java
 */

package com.ford.wechat.service.pc.combo;

import com.ford.wechat.entity.pc.combo.MaintenancePackage;
import io.dabing.core.service.Service;

import java.util.List;

/**
 * 描述：MaintenancePackageService 服务接口层代码类
 *
 * @author ziv
 * @since 1.0
 */
public interface MaintenancePackageService extends Service {
    List<MaintenancePackage> findByModelAndPackageType(String model, String packageType);
    List findByPackageType( String packageType);
}
