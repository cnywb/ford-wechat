package com.ford.wechat.service.factory.impl;

import com.ford.wechat.entity.factory.WeFactoryForm;
import com.ford.wechat.respository.factory.WeFactoryFormRepository;
import com.ford.wechat.service.factory.WeFactoryFormService;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 * All rights reserved. 2017-05-27 14:12
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Service
public class WeFactoryFormServiceImpl extends AbstractService implements WeFactoryFormService {
    @Autowired
    private WeFactoryFormRepository repository;


    /**
     * 保存
     *
     * @param object
     */
    public void save(WeFactoryForm object) {
        repository.save(object);
    }

    /**
     * 批量删除
     *
     * @param objectList
     */
    public void delete(List<WeFactoryForm> objectList) {
        repository.delete(objectList);
    }

    /**
     * 删除
     *
     * @param object
     */
    public void delete(WeFactoryForm object) {
        object.setDeleted(Boolean.TRUE);
        this.update(object);
    }

    /**
     * 根据主键删除
     *
     * @param id
     */
    public void delete(Long id) {
        WeFactoryForm entity = this.repository.get(id);
        entity.setDeleted(Boolean.TRUE);
        this.update(entity);
    }

    /**
     * 更新
     *
     * @param object
     */
    public void update(WeFactoryForm object) {
        repository.update(object);
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    public WeFactoryForm get(Long id) {
        return repository.get(id);
    }

    /**
     * 根据vin码查询
     *
     * @param vin
     * @return
     */
    @Override
    public WeFactoryForm findByVin(String vin) {
        return repository.findByVin(vin);
    }
}
