package com.ford.wechat.respository.batch.impl;

import com.ford.wechat.date.DateUtils;
import com.ford.wechat.entity.batch.BatchTask;
import com.ford.wechat.entity.batch.BatchType;
import com.ford.wechat.respository.batch.BatchTaskRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

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
 * All rights reserved. 2017-03-23 20:46
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Repository
public class BatchTaskRepositoryImpl extends DefaultJpaRepository<BatchTask, Long> implements BatchTaskRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<BatchTask> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery()
                .query("from BatchTask where 1 = 1 ")
                .predicateNotNull(page.getKeyWord())
                .query(" and name like :name")
                .likeParam("name", page.getKeyWord())
                .predicate(Boolean.TRUE).build();
//                .query(" order by firstInsert desc").build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }

    /**
     * 根据类型返回结果
     *
     * @param batchType 批处理类型
     * @return 分页结果数据对象集合
     */
    @Override
    public List<BatchTask> getByType(BatchType batchType) {
        StringQuery query = StringQuery.newQuery()
                .query("from BatchTask where 1 = 1 ")
                .predicateNotNull(batchType)
                .query(" and batchType = :batchType")
                .param("batchType", batchType)
                .predicate(Boolean.TRUE)
                .query(" and status = :status")
                .param("status",BatchTask.STATUS_INIT)
                .build();
        return find(query);
    }

    @Override
    public Page<BatchTask> pagingBy(String batchNo, Integer runTimes, Integer status, Date createStartDate, Date createEndDate, GridPage page) {
        StringQuery query = StringQuery.newQuery ()
                .query("from BatchTask where 1 = 1 ")

                .predicateHasText (batchNo)
                .query (" and batchNo like :batchNo")
                .likeParam ("batchNo", batchNo)

                .predicateNotNull (runTimes)
                .query (" and runTimes = :runTimes")
                .param ("runTimes", runTimes)

                .predicateNotNull (status)
                .query (" and status = :status")
                .param ("status", status)

                .predicateNotNull (createStartDate)
                .query(" and  createdDate  >=  :createStartDate")
                .param ("createStartDate", createStartDate)

                .predicateNotNull (createEndDate)
                .query(" and  createdDate  <=  :createEndDate")
                .param ("createEndDate", DateUtils.formatEndDate(createEndDate))

                .predicate (Boolean.TRUE)
                .query (" order by createdDate desc").build ();
        return find (query, PageRequest.newPage (page.getPageNumber (), page.getPageSize ()));
    }
}
