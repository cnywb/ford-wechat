package com.ford.wechat.service.user.impl;

import com.ford.wechat.entity.user.FordCar;
import com.ford.wechat.respository.user.FordCarRepository;
import com.ford.wechat.service.user.FordCarService;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
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
 * All rights reserved. 2017-04-01 14:20
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Service
public class FordCarServiceImpl extends AbstractService implements FordCarService {
    @Autowired
    private FordCarRepository repository;

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<FordCar> pagingBy(GridPage page) {
        return repository.pagingBy(page);
    }

    /**
     * 保存
     *
     * @param object
     */
    public void save(FordCar object) {
        repository.save(object);
    }

    /**
     * 批量删除
     *
     * @param objectList
     */
    public void delete(List<FordCar> objectList) {
        repository.delete(objectList);
    }

    /**
     * 删除
     *
     * @param object
     */
    public void delete(FordCar object) {
        repository.delete(object);
    }

    /**
     * 根据主键删除
     *
     * @param id
     */
    public void delete(Long id) {
        FordCar entity = this.repository.get(id);
        repository.delete(entity);
    }

    /**
     * 更新
     *
     * @param object
     */
    public void update(FordCar object) {
        repository.update(object);
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    public FordCar get(Long id) {
        return repository.get(id);
    }

    /**
     * 查找vin码对应的维修或销售经销商
     *
     * @param vin
     * @param i
     * @return
     */
    @Override
    public FordCar findByVin(String vin, int i) {
        return repository.findByVin(vin, i);
    }
}
