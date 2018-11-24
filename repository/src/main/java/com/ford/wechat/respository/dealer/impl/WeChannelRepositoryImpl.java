package com.ford.wechat.respository.dealer.impl;

import com.ford.wechat.entity.dealer.WeChannel;
import com.ford.wechat.respository.dealer.WeChannelRepository;
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
 * All rights reserved. 2017-03-31 20:31
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Repository
public class WeChannelRepositoryImpl extends DefaultJpaRepository<WeChannel, Long> implements WeChannelRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<WeChannel> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery()
                .query("from WeChannel where 1 = 1 ")
                .predicateNotNull(page.getKeyWord())
                .query(" and name like :name")
                .likeParam("name", page.getKeyWord())
                .predicate(Boolean.TRUE)
                .query(" and deleted = false").build()
                .query(" order by id desc").build();

        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }

    /**
     * 根据渠道代码查询
     *
     * @param code
     * @return
     */
    @Override
    public WeChannel getByCode(String code) {
        Assert.hasText(code);
        StringQuery query = StringQuery.newQuery()
                .query("from WeChannel where 1 = 1 ")
                .predicateHasText(code)
                .query(" and code = :code")
                .param("code",code)
                .build();
        List<WeChannel> list = this.find(query);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据经销商代码查询
     *
     * @param dealerCode
     * @return
     */
    @Override
    public WeChannel getByDealerCode(String dealerCode) {
        StringQuery query = StringQuery.newQuery()
                .query("from WeChannel where 1 = 1 ")
                .predicateHasText(dealerCode)
                .query(" and dealerCode = :dealerCode")
                .param("dealerCode",dealerCode)
                .build();
        List<WeChannel> list = this.find(query);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据经销商服务代码查询
     *
     * @param dealerServiceCode
     * @return
     */
    @Override
    public WeChannel getByDealerServiceCode(String dealerServiceCode) {
        StringQuery query = StringQuery.newQuery()
                .query("from WeChannel where 1 = 1 ")
                .predicateHasText(dealerServiceCode)
                .query(" and dealerServiceCode = :dealerServiceCode")
                .param("dealerServiceCode",dealerServiceCode)
                .build();
        List<WeChannel> list = this.find(query);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    @Override
    public Page<WeChannel> pagingBy(String dealerCode, String code, String name, Integer type, GridPage page) {
        StringQuery query = StringQuery.newQuery ()
                .query("from WeChannel where 1 = 1 ")

                .predicateHasText (dealerCode)
                .query (" and dealerCode like :dealerCode")
                .likeParam ("dealerCode", dealerCode)

                .predicateHasText (code)
                .query (" and code like :code")
                .likeParam ("code", code)

                .predicateHasText (name)
                .query (" and name like :name")
                .likeParam ("name", name)

                .predicateNotNull (type)
                .query (" and type = :type")
                .param ("type", type)

                .predicate (Boolean.TRUE)
                .query (" order by createdDate desc").build ();
        return find (query, PageRequest.newPage (page.getPageNumber (), page.getPageSize ()));
    }
}
