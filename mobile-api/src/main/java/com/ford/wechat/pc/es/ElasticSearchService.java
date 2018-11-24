/*
 * Copyright (c)  2017
 * All rights reserved.
 * ElasticSearchService.java 2017-05-18 下午3:22
 */

package com.ford.wechat.pc.es;

import io.dabing.core.repository.domain.Page;
import org.elasticsearch.search.SearchHit;

/**
 * 描述:ES索引搜索服务
 *
 * @author yangkui create on 2017-05-18 下午3:22.
 * @since 1.0
 */
public interface ElasticSearchService {


    String indexInfo(com.ford.wechat.entity.pc.info.InfoEntity infoEntity);

    String indexQAInfo(com.ford.wechat.entity.pc.info.QAInfoEntity infoEntity);



    /**
     * 按关键字 分页查询资讯信息
     * @param page 分页大小，页码从0开始
     * @return
     */
    Page<SearchHit> searchInfoPage(ESSearchPage page);

    /**
     * 按关键字 分页查询QA资讯信息
     * @param page 分页大小，页码从0开始
     * @return
     */
    Page<SearchHit> searchQAInfoPage(ESSearchPage page);

    /**
     * 根据索引ID删除资讯索引记录
     *
     * @param indexId
     */
    void deleteInfoIndex(String indexId);

    /**
     * 根据索引ID删除QA资讯索引记录
     *
     * @param indexId
     */
    void deleteQAInfoIndex(String indexId);



}