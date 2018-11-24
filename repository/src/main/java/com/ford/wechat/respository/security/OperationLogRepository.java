/*
 * Copyright (c)  2016
 * All rights reserved.
 * OperationLogRepository.java 2016-12-03 上午12:04
 */
package com.ford.wechat.respository.security;


import com.ford.wechat.entity.security.OperationLog;
import com.ford.wechat.entity.security.OperationType;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;

import java.util.Date;


/**
 * 描述：TODO
 *
 * @author wanglijun create on 2016-12-03
 * @since 1.0
 */
public interface OperationLogRepository extends JpaRepository<OperationLog, Long> {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<OperationLog> pagingBy(GridPage page);

    /***
     *
     * @param page
     * @param userName
     * @param operationType
     * @param startDate
     * @param endDateCover
     * @return
     */
    Page<OperationLog> pagingBy(GridPage page, String userName, OperationType operationType, Date startDate, Date endDateCover);
}
