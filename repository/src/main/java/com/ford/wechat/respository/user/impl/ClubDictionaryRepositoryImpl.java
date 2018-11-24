/*
 * Copyright (c)  2016, newtouch.com
 * All rights reserved.
 * CarInfoRepositoryImpl.java 2016-11-02 下午2:19
 */
package com.ford.wechat.respository.user.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ford.wechat.entity.user.ClubDictionary;
import com.ford.wechat.entity.user.FordCar;
import com.ford.wechat.respository.user.ClubDictionaryRepository;

import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.StringQuery;

/**
 * 描述：TODO
 *
 * @author huangwen create on 2017-06-26
 * @since 1.0
 */
@Repository
public class ClubDictionaryRepositoryImpl extends DefaultJpaRepository<ClubDictionary, String> implements ClubDictionaryRepository {


    @Override 
    public  ClubDictionary getClubDictoryNameByCode(String code) {
        StringQuery query = StringQuery.newQuery()
                .query("from ClubDictionary a where a.cdcode =:code ")
                .param("code", code).build();
        List<ClubDictionary> list = find(query);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
}
