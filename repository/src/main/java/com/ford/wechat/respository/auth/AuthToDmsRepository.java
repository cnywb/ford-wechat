package com.ford.wechat.respository.auth;


import com.ford.wechat.entity.auth.AuthToDms;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;

import java.util.Date;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-05-19 15:59
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public interface AuthToDmsRepository extends JpaRepository<AuthToDms, Long> {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<AuthToDms> pagingBy(GridPage page);

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<AuthToDms> pagingBy(String batchNo, String vin, String openId, Integer channelType, Integer sendDmsStatus, Integer verify, Date createStartDate, Date createEndDate, GridPage page);


}
