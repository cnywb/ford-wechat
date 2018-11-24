/*
 * Copyright (c)  2016, newtouch.com
 * All rights reserved.
 * CarInfoRepositoryImpl.java 2016-11-02 下午2:19
 */
package com.ford.wechat.respository.infocollection.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ford.wechat.entity.infocollection.InfoCollectionChannel;
import com.ford.wechat.respository.infocollection.InfoCollectionChannelRepository;

import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.StringQuery;

/**
 * 描述：TODO
 *
 * @author huangwen create on 2017-06-27
 * @since 1.0
 */
@Repository
public class InfoCollectionChannelRepositoryImpl extends DefaultJpaRepository<InfoCollectionChannel, String> implements InfoCollectionChannelRepository {

	@Override
	public List<InfoCollectionChannel> findByChannelCode(String channelCode) {
      StringQuery query = StringQuery.newQuery()
      .query("from InfoCollectionChannel where code = :channelCode")
      .param("channelCode", channelCode).build();
       return find(query);
	}

 
	
}
