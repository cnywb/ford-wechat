package com.ford.wechat.respository.repair.impl;

import com.ford.wechat.entity.repair.FordRepairWeb;
import com.ford.wechat.respository.repair.FordRepairWebRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

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
 * All rights reserved. 2017-04-01 14:24
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Repository
public class FordRepairWebRepositoryImpl extends DefaultJpaRepository<FordRepairWeb, Long> implements FordRepairWebRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<FordRepairWeb> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery()
                .query("from FordRepairWeb where 1 = 1 ")
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
     * @param vvin vin码
     * @return
     */
    @Override
    public List<FordRepairWeb> findByVin(String vvin) {
        Assert.hasText(vvin);
        StringQuery query = StringQuery.newQuery()
                .query("from FordRepairWeb where 1 = 1 ")
                .predicateNotNull(vvin)
                .query(" and vvin = :vvin")
                .param("vvin", vvin)
                .predicate(true)
                .query(" order by createDate desc")
                .build();

        return find(query);
    }
}
