package com.ford.wechat.respository.member.impl;

import com.ford.wechat.entity.member.WeAssessLog;
import com.ford.wechat.respository.member.WeAssessLogRepository;
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
 * All rights reserved. 2017-06-02 14:58
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Repository
public class WeAssessLogRepositoryImpl extends DefaultJpaRepository<WeAssessLog, Long> implements WeAssessLogRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<WeAssessLog> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery()
                .query("from WeAssessLog where 1 = 1 ")
                .predicateNotNull(page.getKeyWord())
                .query(" and name like :name")
                .likeParam("name", page.getKeyWord())
                .predicate(Boolean.TRUE)
                .query(" order by firstInsert desc").build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }
}
