package com.ford.wechat.respository.user.impl;

import com.ford.wechat.entity.user.FcDmsToData;
import com.ford.wechat.respository.user.FcDmsToDataRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

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
 * All rights reserved. 2017-07-17 16:46
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Repository
public class FcDmsToDataRepositoryImpl extends DefaultJpaRepository<FcDmsToData, Long> implements FcDmsToDataRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<FcDmsToData> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery()
                .query("from FcDmsToData where 1 = 1 ")
                .predicateNotNull(page.getKeyWord())
                .query(" and name like :name")
                .likeParam("name", page.getKeyWord())
                .predicate(Boolean.TRUE)
                .query(" order by firstInsert desc").build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }

    /**
     * 根据vin码查询
     *
     * @param vin
     * @return
     */
    @Override
    public List<FcDmsToData> findByVin(String vin) {
        StringQuery query = StringQuery.newQuery()
                .query("from FcDmsToData where 1 = 1 ")
                .predicateNotNull(vin)
                .query(" and vin = :vin")
                .param("vin", vin)
                .build();
        return this.find(query);
    }
}
