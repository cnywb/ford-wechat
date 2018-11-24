/*
 * Copyright (c)  2016
 * All rights reserved.
 * CarInfoRepository.java 2016-11-02 下午2:19
 */
package com.ford.wechat.respository.infocollection;

import java.util.List;

import com.ford.wechat.entity.infocollection.InfoCollectionCampaign;

import io.dabing.core.repository.JpaRepository;


/**
 * 描述：TODO
 *
 * @author huangwen create on 2017-06-27
 * @since 1.0
 */
public interface InfoCollectionCampaignRepository extends JpaRepository<InfoCollectionCampaign, String> {

   
    List<InfoCollectionCampaign> findByCampaignCode(String campaignCode);
}
