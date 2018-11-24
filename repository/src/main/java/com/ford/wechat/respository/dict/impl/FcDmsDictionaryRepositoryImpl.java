package com.ford.wechat.respository.dict.impl;

import com.ford.wechat.entity.dealer.ClubDealer;
import com.ford.wechat.entity.dict.FcDmsDictionary;
import com.ford.wechat.respository.dict.FcDmsDictionaryRepository;
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
 * All rights reserved. 2017-08-14 21:44
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Repository
public class FcDmsDictionaryRepositoryImpl extends DefaultJpaRepository<FcDmsDictionary, Long> implements FcDmsDictionaryRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<FcDmsDictionary> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery()
                .query("from FcDmsDictionary where 1 = 1 ")
                .predicateNotNull(page.getKeyWord())
                .query(" and name like :name")
                .likeParam("name", page.getKeyWord())
                .predicate(Boolean.TRUE)
                .query(" order by firstInsert desc").build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }

    /**
     * 根据代码查询
     *
     * @param code 代码
     * @return
     */
    @Override
    public FcDmsDictionary findByCode(String code) {
        Assert.hasText(code);
        StringQuery query = StringQuery.newQuery()
                .query("from FcDmsDictionary where 1 = 1 ")
                .predicateNotNull(code)
                .query(" and code = :code")
                .param("code", code)
                .predicate(Boolean.TRUE)
                .query(" and type = :type")
                .param("type", "9994")
                .build();
        List<FcDmsDictionary> list = find(query);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }
}
