/*
 * Copyright (c)  2016
 * All rights reserved.
 * CarInfoRepository.java 2016-11-02 下午2:19
 */
package com.ford.wechat.respository.user;

import com.ford.wechat.entity.user.ClubDictionary;

import io.dabing.core.repository.JpaRepository;


/**
 * 描述：TODO
 *
 * @author huangwen create on 2017-06-26
 * @since 1.0
 */
public interface ClubDictionaryRepository extends JpaRepository<ClubDictionary, String> {

	
	
	
	
    /**
     * 根据车型code找车型名称
     * @param code
     * @return
     */
    public ClubDictionary getClubDictoryNameByCode(String code);

}
