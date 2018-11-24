/*
 * Copyright (c)  2016, newtouch.com
 * All rights reserved.
 * CarInfoRepositoryImpl.java 2016-11-02 下午2:19
 */
package com.ford.wechat.respository.user.impl;

import com.ford.wechat.date.DateUtils;
import com.ford.wechat.entity.user.CarInfo;
import com.ford.wechat.respository.user.CarInfoRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 描述：TODO
 *
 * @author wanglijun create on 2016-11-02
 * @since 1.0
 */
@Repository
public class CarInfoRepositoryImpl extends DefaultJpaRepository<CarInfo, String> implements CarInfoRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<CarInfo> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery()
                .query("from CarInfo where 1 = 1 ")
                .predicateHasText(page.getKeyWord())
                .query(" and name like :name")
                .likeParam("name", page.getKeyWord())
                .predicate(Boolean.TRUE)
                .query(" order by createdDate desc").build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }

    /**
     * 根据GridPage对象按分页查找服务
     * uyE
     *
     * @param openId
     * @param buyDealerName
     * @param repairDealerName
     * @param model
     * @param style
     * @param color
     * @param buyStartDate
     * @param buyEndDate
     * @param page             分页对象，里有关键字keyWord,供模糊匹配  @return 分页结果数据对象集合
     */
    @Override
    public Page<CarInfo> pagingBy(String openId, String buyDealerName, String repairDealerName, String model, String style, String color, Date buyStartDate, Date buyEndDate, GridPage page) {
        StringQuery query = StringQuery.newQuery()
                .query("from CarInfo where 1 = 1 ")

                .predicateHasText(openId)
                .query(" and openId like :openId")
                .likeParam("openId", openId)

                .predicateHasText(buyDealerName)
                .query(" and buyDealerName like :buyDealerName")
                .likeParam("buyDealerName", buyDealerName)

                .predicateHasText(repairDealerName)
                .query(" and repairDealerName like :repairDealerName")
                .likeParam("repairDealerName", repairDealerName)

                .predicateHasText(model)
                .query(" and model like :model")
                .likeParam("model", model)

                .predicateHasText(style)
                .query(" and style like :style")
                .likeParam("style", style)

                .predicateHasText(color)
                .query(" and color like :color")
                .likeParam("color", color)

                .predicateNotNull(buyStartDate)
                .query(" and  buyDate  >  :buyStartDate")
                .param("buyStartDate", buyStartDate)

                .predicateNotNull(buyEndDate)
                .query(" and  buyDate  <  :buyEndDate")
                .param("buyEndDate", DateUtils.formatEndDate(buyEndDate))


                .predicate(Boolean.TRUE)
                .query(" order by id desc").build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }

    @Override
    public List<CarInfo> findByOpenId(String openId) {
        StringQuery query = StringQuery.newQuery()
                .query("from CarInfo where openId = :openId")
                .param("openId", openId).build();
        return find(query);
    }
}
