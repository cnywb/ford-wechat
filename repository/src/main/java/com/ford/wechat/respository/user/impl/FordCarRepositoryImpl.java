package com.ford.wechat.respository.user.impl;

import com.ford.wechat.entity.user.FordCar;
import com.ford.wechat.respository.user.FordCarRepository;
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
 * All rights reserved. 2017-04-01 14:18
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Repository
public class FordCarRepositoryImpl extends DefaultJpaRepository<FordCar, Long> implements FordCarRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<FordCar> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery()
                .query("from FordCar where 1 = 1 ")
                .predicateNotNull(page.getKeyWord())
                .query(" and name like :name")
                .likeParam("name", page.getKeyWord())
                .predicate(Boolean.TRUE)
                .query(" order by firstInsert desc").build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
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
        StringQuery query = StringQuery.newQuery()
                .query("from FordCar a where a.vvin =:vvin ")
                .param("vvin", vin)
                .query(" and length(a.vdealerId) = :dealerIdLength ")
                .param("dealerIdLength", i)
                .build();
        List<FordCar> list = find(query);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public FordCar findFordCarByVin(String vin) {
        StringQuery query = StringQuery.newQuery()
                .query("from FordCar a where a.vvin =:vvin ")
                .param("vvin", vin).build();
        List<FordCar> list = find(query);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
}
