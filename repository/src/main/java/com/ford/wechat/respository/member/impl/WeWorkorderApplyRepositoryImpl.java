package com.ford.wechat.respository.member.impl;

import com.ford.wechat.entity.member.WeWorkorderApply;
import com.ford.wechat.respository.member.WeWorkorderApplyRepository;
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
 * All rights reserved. 2017-05-23 18:39
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Repository
public class WeWorkorderApplyRepositoryImpl extends DefaultJpaRepository<WeWorkorderApply, Long> implements WeWorkorderApplyRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<WeWorkorderApply> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery()
                .query("from WeWorkorderApply where 1 = 1 ")
                .predicateNotNull(page.getKeyWord())
                .query(" and name like :name")
                .likeParam("name", page.getKeyWord())
                .predicate(Boolean.TRUE);
              /*  .query(" order by firstInsert desc").build();*/
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }


    @Override
    public List<WeWorkorderApply> findList(String openId, String vin) {
        StringQuery query = StringQuery.newQuery()
                .query("from WeWorkorderApply where 1 = 1 ")
                .query(" and deleted = :deleted")
                .param("deleted", false)
                .query(" and openId = :openId")
                .param("openId", openId)
                .query(" and vin = :vin")
                .param("vin", vin)
                .query(" order by id desc").build();
        return find(query);
    }


    /**
     * 根据VIN和OPENID查询该用户解绑次数
     * @param openId
     * @param vin
     * @return
     */
    @Override
    public List<WeWorkorderApply> findUnbindList(String openId, String vin) {
        StringQuery query = StringQuery.newQuery()
                .query("from WeWorkorderApply where 1 = 1 ")
                .query(" and deleted = :deleted")
                .param("deleted", false)
                .query(" and openId = :openId")
                .param("openId", openId)
                .query(" and vin = :vin")
                .param("vin", vin)
                .query(" and applyType = :applyType")
                .param("applyType", WeWorkorderApply.APPLY_TYPE_UNBIND)//解绑
                .query(" and assessResult = :assessResult")
                .param("assessResult", WeWorkorderApply.ASSESS_RESULT_PASS)//审核通过
                .query(" order by id desc").build();
        return find(query);
    }

    /**
     * 查询未完成解绑申请列表
     * @param openId
     * @return
     */
    @Override
    public List<WeWorkorderApply> findUnAuthList(String openId) {
        StringQuery query = StringQuery.newQuery()
                .query("from WeWorkorderApply where 1 = 1 ")
                .query(" and deleted = :deleted")
                .param("deleted", false)
                .query(" and openId = :openId")
                .param("openId", openId)
                .query(" and assessStatus <> 2")//审核未完成
                .query(" and assessResult <> 3")//已经关闭
                .query(" and applyType <> 1")
                .query(" order by id desc").build();
        return find(query);
    }

    @Override
    public Page<WeWorkorderApply> pagingBy(String vin, String openId, String mobile, Integer assessStatus, GridPage page) {
        StringQuery query = StringQuery.newQuery ()
                .query("from WeWorkorderApply where 1 = 1 ")
                .query(" and deleted = :deleted")
                .param("deleted", false)

                .predicateHasText (vin)
                .query (" and vin like :vin")
                .likeParam ("vin", vin)

                .predicateHasText (openId)
                .query (" and openId like :openId")
                .likeParam ("openId", openId)

                .predicateHasText (mobile)
                .query (" and mobile like :mobile")
                .likeParam ("mobile", mobile)

                .predicateNotNull (assessStatus)
                .query (" and assessStatus = :assessStatus")
                .param ("assessStatus", assessStatus)

                .predicate (Boolean.TRUE)
                .query (" order by createdDate desc").build ();
        return find (query, PageRequest.newPage (page.getPageNumber (), page.getPageSize ()));
    }
}
