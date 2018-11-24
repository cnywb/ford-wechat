/*
 * Copyright (c)  2016, newtouch.com
 * All rights reserved.
 * CarInfoRepositoryImpl.java 2016-11-02 下午2:19
 */
package com.ford.wechat.respository.infocollection.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ford.wechat.entity.infocollection.InfoCollectionCampaign;
import com.ford.wechat.respository.infocollection.InfoCollectionCampaignRepository;

import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.StringQuery;

/**
 * 描述：TODO
 *
 * @author huangwen create on 2017-06-27
 * @since 1.0
 */
@Repository
public class InfoCollectionCampaignRepositoryImpl extends DefaultJpaRepository<InfoCollectionCampaign, String> implements InfoCollectionCampaignRepository {

	@Override
	public List<InfoCollectionCampaign> findByCampaignCode(String campaignCode) {
      StringQuery query = StringQuery.newQuery()
      .query("from InfoCollectionCampaign where code  = :campaignCode")
      .param("campaignCode", campaignCode).build();
       return find(query);
	}

 
	
}
