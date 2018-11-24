package com.ford.wechat.respository.dealer.impl;

import com.ford.wechat.entity.dealer.ClubDealer;
import com.ford.wechat.respository.dealer.ClubDealerRepository;
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
 * All rights reserved. 2017-04-01 14:30
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Repository
public class ClubDealerRepositoryImpl extends DefaultJpaRepository<ClubDealer, Long> implements ClubDealerRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<ClubDealer> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery()
                .query("from ClubDealer where 1 = 1 ")
                .predicateNotNull(page.getKeyWord())
                .query(" and name like :name")
                .likeParam("name", page.getKeyWord())
                .predicate(Boolean.TRUE)
                .query(" order by firstInsert desc").build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }

    /**
     * 根据销售经销商查询
     *
     * @param dlsaleCode 销售经销商代码
     * @return
     */
    @Override
    public ClubDealer findByDSaleCode(String dlsaleCode) {
        Assert.hasText(dlsaleCode);
        StringQuery query = StringQuery.newQuery()
                .query("from ClubDealer where 1 = 1 ")
                .predicateNotNull(dlsaleCode)
                .query(" and dlsaleCode = :dlsaleCode")
                .param("dlsaleCode", dlsaleCode)
                .build();
        List<ClubDealer> list = find(query);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据经销商服务代码查询
     *
     * @param serviceCode 经销商服务代码
     * @return
     */
    @Override
    public ClubDealer findByServiceCode(String serviceCode) {
        Assert.hasText(serviceCode);
        StringQuery query = StringQuery.newQuery()
                .query("from ClubDealer where 1 = 1 ")
                .predicateNotNull(serviceCode)
                .query(" and sstCode = :sstCode")
                .param("sstCode", serviceCode)
                .build();
        List<ClubDealer> list = find(query);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }
}
