package com.ford.wechat.service.factory;

import com.ford.wechat.entity.factory.WeFactoryForm;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;

import java.util.List;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-05-27 14:11
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public interface WeFactoryFormService {



    /**
     * 保存
     *
     * @param object
     */
    void save(WeFactoryForm object);

    /**
     * 批量删除
     *
     * @param objectList
     */
    void delete(List<WeFactoryForm> objectList);

    /**
     * 删除
     *
     * @param object
     */
    void delete(WeFactoryForm object);

    /**
     * 根据主键删除
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 更新
     *
     * @param object
     */
    void update(WeFactoryForm object);

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    WeFactoryForm get(Long id);

    /**
     * 根据vin码查询
     *
     * @param vin
     * @return
     */
    WeFactoryForm findByVin(String vin);
}
