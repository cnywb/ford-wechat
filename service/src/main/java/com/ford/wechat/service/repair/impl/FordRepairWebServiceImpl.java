package com.ford.wechat.service.repair.impl;

import com.ford.wechat.entity.repair.FordRepairWeb;
import com.ford.wechat.respository.repair.FordRepairWebRepository;
import com.ford.wechat.service.repair.FordRepairWebService;
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
 * All rights reserved. 2017-04-01 14:28
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Service
public class FordRepairWebServiceImpl extends AbstractService implements FordRepairWebService {
    @Autowired
    private FordRepairWebRepository repository;

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<FordRepairWeb> pagingBy(GridPage page) {
        return repository.pagingBy(page);
    }

    /**
     * 保存
     *
     * @param object
     */
    public void save(FordRepairWeb object) {
        repository.save(object);
    }

    /**
     * 批量删除
     *
     * @param objectList
     */
    public void delete(List<FordRepairWeb> objectList) {
        repository.delete(objectList);
    }

    /**
     * 删除
     *
     * @param object
     */
    public void delete(FordRepairWeb object) {
        repository.delete(object);
    }

    /**
     * 根据主键删除
     *
     * @param id
     */
    public void delete(Long id) {
        FordRepairWeb entity = this.repository.get(id);
        repository.delete(entity);
    }

    /**
     * 更新
     *
     * @param object
     */
    public void update(FordRepairWeb object) {
        repository.update(object);
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    public FordRepairWeb get(Long id) {
        return repository.get(id);
    }

    /**
     * 根据vin码查询
     *
     * @param vvin vin码
     * @return
     */
    @Override
    public List<FordRepairWeb> findByVin(String vvin) {
        return repository.findByVin(vvin);
    }
}
