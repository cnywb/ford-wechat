package com.ford.wechat.respository.factory;

import com.ford.wechat.entity.factory.WeFactoryForm;
import io.dabing.core.repository.JpaRepository;

import java.util.List;

/**
 * Created by Neel on 2017/5/24.
 */
public interface WeFactoryFormRepository extends JpaRepository<WeFactoryForm, Long> {
    /**
     * 根据vin码查询
     * @param vin
     * @return
     */
    WeFactoryForm findByVin(String vin);
}
