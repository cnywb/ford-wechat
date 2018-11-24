package com.ford.wechat.service.batch.impl;

import com.ford.wechat.entity.batch.BatchTask;
import com.ford.wechat.entity.batch.BatchType;
import com.ford.wechat.respository.batch.BatchTaskRepository;
import com.ford.wechat.service.batch.BatchTaskService;

import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Date;
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
 * All rights reserved. 2017-03-23 20:55
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Service
public class BatchTaskServiceImpl extends AbstractService implements BatchTaskService {
    @Autowired
    private BatchTaskRepository repository;

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<BatchTask> pagingBy(GridPage page) {
        return repository.pagingBy(page);
    }

    /**
     * 保存
     *
     * @param object
     */
    public void save(BatchTask object) {
        repository.save(object);
    }

    /**
     * 批量删除
     *
     * @param objectList
     */
    public void delete(List<BatchTask> objectList) {
        repository.delete(objectList);
    }

    /**
     * 删除
     *
     * @param object
     */
    public void delete(BatchTask object) {
        object.setDeleted(Boolean.TRUE);
        this.update(object);
    }

    /**
     * 根据主键删除
     *
     * @param id
     */
    public void delete(Long id) {
        BatchTask entity = this.repository.get(id);
        entity.setDeleted(Boolean.TRUE);
        this.update(entity);
    }

    /**
     * 更新
     *
     * @param object
     */
    public void update(BatchTask object) {
        repository.update(object);
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    public BatchTask get(Long id) {
        return repository.get(id);
    }

    /**
     * 根据类型返回结果
     *
     * @param batchType 批处理类型
     * @return 分页结果数据对象集合
     */
    @Override
    public List<BatchTask> getByType(BatchType batchType) {
        return repository.getByType(batchType);
    }

    @Override
    public Page<BatchTask> pagingBy(String batchNo, Integer runTimes, Integer status, Date createStartDate, Date createEndDate, GridPage page) {
        return repository.pagingBy(batchNo, runTimes, status, createStartDate, createEndDate, page);
    }
}
