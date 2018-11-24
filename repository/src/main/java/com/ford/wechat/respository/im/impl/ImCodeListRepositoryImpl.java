/*
 * Copyright (c)  2015, dabing.io
 * All rights reserved. 
 * ImCodeList.java 15/10/15 上午11:47
 */
package com.ford.wechat.respository.im.impl;

import com.ford.wechat.entity.im.ImCodeList;
import com.ford.wechat.respository.im.ImCodeListRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by yangkui on 15/10/15.
 * 数据字典小类
 *
 * @since 1.0
 */
@Repository
public class ImCodeListRepositoryImpl extends DefaultJpaRepository<ImCodeList, Long> implements ImCodeListRepository {


    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param typeCode 字典大类代码 允许为空
     * @param page     分页对象，里面有关键字进行模糊匹配
     * @return
     */
    public Page<ImCodeList> findByGridPage(String typeCode, GridPage page) {
        StringQuery query = StringQuery.newQuery()
                    .query("select a from ImCodeList a where deleted = false  ")
                .predicateHasText(typeCode)
                    .query(" and a.typeCode =:typeCode")
                    .param("typeCode", typeCode)
                .predicateHasText(page.getKeyWord())
                    .query(" and (a.code like:code or a.nameCn like :nameCn or a.nameEn like :nameEn)")
                    .likeParam("code", page.getKeyWord())
                    .likeParam("nameCn", page.getKeyWord())
                    .likeParam("nameEn", page.getKeyWord())
                .predicate(Boolean.TRUE)
                    .query(" order by a.sortNo,a.createdDate asc").build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }

    public List<ImCodeList> findByTypeCode(String typeCode) {
        StringQuery query = StringQuery.newQuery()
                    .query("from ImCodeList a where deleted = false  ")
                .predicateHasText(typeCode)
                    .query(" and a.typeCode = :typeCode")
                    .param("typeCode", typeCode)
                .query(" order by a.sortNo asc").build();
        return find(query);
    }

    /**
     * 根据大类代码和字典值查找字典对象
     *
     * @param typeCode
     * @param code
     * @return
     */
    @Override
    public ImCodeList getByTypeCodeAndCode(String typeCode, String code) {
        StringQuery query = StringQuery.newQuery()
                .query("from ImCodeList a where deleted = false  ")
                .predicateHasText(typeCode)
                .query(" and a.typeCode = :typeCode")
                .param("typeCode", typeCode)
                .predicateHasText(code)
                .query(" and a.code = :code")
                .param("code",code).build();
        List<ImCodeList> retval = find(query);
        if(retval!=null&&retval.size()>0){
            return retval.get(0);
        }
        return null;
    }
}
