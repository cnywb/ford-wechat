/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MaintenancePackageRepositoryImpl.java
 */
package com.ford.wechat.repository.pc.combo.impl;

import com.ford.wechat.entity.pc.combo.MaintenancePackage;
import com.ford.wechat.repository.pc.combo.MaintenancePackageRepository;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述：MaintenancePackageRepositoryImpl 仓储服务接口实现层代码类
 *
 * @author ziv
 * @since 1.0
 */
@Repository
public class MaintenancePackageRepositoryImpl extends DefaultJpaRepository<MaintenancePackage, Long> implements MaintenancePackageRepository {

    @Override
    public List findByPackageType( String packageType) {
        StringQuery query = StringQuery.newQuery()
                .query("select distinct a.model from MaintenancePackage a where a.packageType = :packageType")
                .param("packageType", packageType).build();
        return find(query);
    }

    @Override
    public List<MaintenancePackage> findByModelAndPackageType(String model, String packageType) {
        StringQuery query = StringQuery.newQuery()
                .query("from MaintenancePackage a where 1=1 ")
                .predicateHasText(model)
                .query(" and a.model = :model ")
                .param("model",model)
                .predicateHasText(packageType)
                .query(" and a.packageType = :packageType")
                .param("packageType", packageType).build();
        return find(query);
    }
}
