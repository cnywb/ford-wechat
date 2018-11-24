package com.ford.wechat.respository.dealer.impl;

import com.ford.wechat.entity.dealer.ScanDealer;
import com.ford.wechat.respository.dealer.ScanDealerRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-02-17 11:11
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Repository
public class ScanDealerRepositoryImpl extends DefaultJpaRepository<ScanDealer, Long> implements ScanDealerRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<ScanDealer> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery()
                .query("from ScanDealer where 1 = 1 ")

                .predicateNotNull(page.getKeyWord())
                .query(" and name like :name")
                .likeParam("name", page.getKeyWord())

                .predicate(Boolean.TRUE)
                .query(" order by id desc").build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }
}
