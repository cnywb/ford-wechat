package com.ford.wechat.service.batch;

import com.ford.wechat.entity.batch.BatchTask;
import com.ford.wechat.entity.batch.BatchType;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;

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
public interface BatchTaskService {

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    Page<BatchTask> pagingBy(GridPage page);

    /**
     * 保存
     *
     * @param object
     */
    void save(BatchTask object);

    /**
     * 批量删除
     *
     * @param objectList
     */
    void delete(List<BatchTask> objectList);

    /**
     * 删除
     *
     * @param object
     */
    void delete(BatchTask object);

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
    void update(BatchTask object);

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    BatchTask get(Long id);

    /**
     * 根据类型返回结果
     *
     * @param batchType 批处理类型
     * @return 分页结果数据对象集合
     */
    List<BatchTask> getByType(BatchType batchType);

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<BatchTask> pagingBy(String batchNo, Integer runTimes, Integer status, Date createStartDate, Date createEndDate, GridPage page);

}
