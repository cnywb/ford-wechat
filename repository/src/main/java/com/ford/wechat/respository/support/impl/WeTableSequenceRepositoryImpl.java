package com.ford.wechat.respository.support.impl;

import com.ford.wechat.entity.support.WeTableSequence;
import com.ford.wechat.respository.support.WeTableSequenceRepository;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by zhaoliang on 2017/6/1.
 */
@Repository
public class WeTableSequenceRepositoryImpl extends DefaultJpaRepository<WeTableSequence, Long> implements WeTableSequenceRepository{
    @Override
    public WeTableSequence getByTableName(Date date, String tableName) {
        StringQuery query = StringQuery.newQuery()
                .query("from WeTableSequence a where 1=1 ")
                .query(" and a.tableName = :tableName")
                .param("tableName", tableName)
                .query(" and a.currentDate = :currentDate")
                .param("currentDate",date)
                .build();
        List<WeTableSequence> retval = find(query);
        if(retval!=null&&!retval.isEmpty()){
            return retval.get(0);
        }
        return null;
    }

    @Override
    public WeTableSequence getByTableName(String tableName) {
        StringQuery query = StringQuery.newQuery()
                .query("from WeTableSequence a where 1 = 1")
                .query(" and a.tableName = :tableName")
                .param("tableName", tableName).build();
        List<WeTableSequence> retval = find(query);
        if(retval!=null&&!retval.isEmpty()){
            return retval.get(0);
        }
        return null;
    }
}
