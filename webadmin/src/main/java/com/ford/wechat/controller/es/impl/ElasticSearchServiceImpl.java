/*
 * Copyright (c)  2017
 * All rights reserved.
 * ElasticSearchServiceImpl.java 2017-05-18 下午3:36
 */

package com.ford.wechat.controller.es.impl;

import com.ford.wechat.controller.es.ESSearchPage;
import com.ford.wechat.controller.es.ElasticSearchService;
import io.dabing.common.util.JSONUtil;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * 描述:ES搜索服务实现
 *
 * @author yangkui create on 2017-05-18 下午3:36.
 * @since 1.0
 */
@Service
@Slf4j
public class ElasticSearchServiceImpl implements ElasticSearchService, InitializingBean {

    private static final String QAInfoEntityIndex = "qainfoentity";
    private static final String QAInfoType = "QAInfo";

    private static final String InfoEntityIndex = "infoentity";
    private static final String InfoType = "info";

    @Value("${es.server.host}")
    private String esServerHost;
    @Value("${es.server.port}")
    private int esServerPort;

    /**
     * 创建一个索引的mapping，相当于创建一个表结构，定义每个字段的类型
     */
    public void createInfoMapping() {
        Client client = null;
        try {
            client = getClient();
            //查询是否已经存在
            GetMappingsRequest getMappingsRequest = new GetMappingsRequest();
            getMappingsRequest.indices(InfoEntityIndex).types(InfoType);
            GetMappingsResponse response = client.admin().indices().getMappings(getMappingsRequest).actionGet();

            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .startObject("properties")
                    .startObject("title").field("type", "string").endObject()
                    .startObject("indexDate").field("type", "date").field("index", "not_analyzed").endObject()
                    .startObject("infoType").field("type", "string").field("index", "not_analyzed").endObject()
                    .startObject("digest").field("type", "string").endObject()
                    .startObject("id").field("type", "integer").field("index", "not_analyzed").endObject()
                    .startObject("iconUrl").field("type", "string").field("index", "not_analyzed").endObject()
                    .startObject("sourceHref").field("type", "string").field("index", "not_analyzed").endObject()
                    .endObject()
                    .endObject();
            PutMappingRequest mapping = Requests.putMappingRequest(InfoEntityIndex).type(InfoType).source(builder);
            client.admin().indices().putMapping(mapping).actionGet();

        } catch (IOException e) {
            log.error("创建资讯mapping失败,errorMsg:{}", e.getMessage(), e);
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }


    @Override
    public String indexInfo(com.ford.wechat.entity.pc.info.InfoEntity infoEntity) {
        return indexObject(infoEntity.getEsId(), JSONUtil.toJson(infoEntity),InfoEntityIndex,InfoType);
    }

    private String indexObject(String indexId, String objectJson,String index,String type) {
        Client client = null;
        try {
            client = getClient();

            if (StringUtils.isNoneBlank(indexId)) {
                UpdateResponse response = client.prepareUpdate(index, type, indexId).setDoc(objectJson, XContentType.JSON).get();
                return response.getId();
            }
            IndexResponse response = client.prepareIndex(index, type).setSource(objectJson, XContentType.JSON).get();
            return response.getId();
        } catch (IOException e) {
            log.error("资讯索引失败,errorMsg:{}", e.getMessage(), e);
        } finally {
            if (client != null) {
                client.close();
            }
        }
        return null;
    }

    @Override
    public String indexQAInfo(com.ford.wechat.entity.pc.info.QAInfoEntity infoEntity) {
        return indexObject(infoEntity.getEsId(), JSONUtil.toJson(infoEntity),QAInfoEntityIndex,QAInfoType);
    }


    /**
     * 分页查询 资讯信息
     *
     * @param page
     * @return
     */
    @Override
    public Page<SearchHit> searchInfoPage(ESSearchPage page) {
        Client client = null;
        try {
            client = getClient();
            //PageNumber从1开始
            int from = (page.getPageNumber() - 1) * page.getPageSize();
            PageRequest request = new PageRequest(page.getPageNumber(), page.getPageSize());
            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
            if (StringUtils.isNotBlank(page.getKeyWord())) {
                queryBuilder.must(QueryBuilders.multiMatchQuery(page.getKeyWord(), "tags", "title", "digest"));
            }

            HighlightBuilder highlightBuilder = new HighlightBuilder().field("*").requireFieldMatch(false);
            highlightBuilder.preTags("<span style=\"color:red\">");
            highlightBuilder.postTags("</span>");

            SearchResponse response = client.prepareSearch(InfoEntityIndex)
                    .setTypes(InfoType)
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(queryBuilder).highlighter(highlightBuilder)
                    //.setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter
                    .setFrom(from).setSize(request.getPageSize()).setExplain(true)
                    .get();
            SearchHit[] hits = response.getHits().getHits();

            Page<SearchHit> result = new Page<>(Arrays.asList(hits));
            return result;
        } catch (IOException e) {
            log.error("查询资讯信息失败,errorMsg:{}", e.getMessage(), e);
        } finally {
            if (client != null) {
                client.close();
            }
        }
        return null;
    }

    /**
     * 分页查询QA资讯信息
     *
     * @param page
     * @return
     */
    @Override
    public Page<SearchHit> searchQAInfoPage(ESSearchPage page) {
        Client client = null;
        try {
            client = getClient();
            //PageNumber从1开始
            int from = (page.getPageNumber() - 1) * page.getPageSize();
            PageRequest request = new PageRequest(page.getPageNumber(), page.getPageSize());
            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
            if (StringUtils.isNotBlank(page.getKeyWord())) {
                queryBuilder.must(QueryBuilders.multiMatchQuery(page.getKeyWord(), "tags", "question", "answer"));
            }

            HighlightBuilder highlightBuilder = new HighlightBuilder().field("*").requireFieldMatch(false);
            highlightBuilder.preTags("<span style=\"color:red\">");
            highlightBuilder.postTags("</span>");

            SearchResponse response = client.prepareSearch(QAInfoEntityIndex)
                    .setTypes(QAInfoType)
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(queryBuilder).highlighter(highlightBuilder)                 // Query
                    //.setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter
                    .setFrom(from).setSize(request.getPageSize()).setExplain(true)
                    .get();
            SearchHit[] hits = response.getHits().getHits();
            Page<SearchHit> result = new Page<>(Arrays.asList(hits));
            return result;
        } catch (IOException e) {
            log.error("查询资讯信息失败,errorMsg:{}", e.getMessage(), e);
        } finally {
            if (client != null) {
                client.close();
            }
        }
        return null;
    }

    /**
     * 根据索引ID删除资讯索引记录
     *
     * @param indexId
     */
    @Override
    public void deleteInfoIndex(String indexId) {
        Client client = null;
        try {
            client = getClient();
            DeleteRequest request = new DeleteRequest();
            request.id(indexId);
            request.type(InfoType);
            request.index(InfoEntityIndex);
            DeleteResponse response = client.delete(request).actionGet();
        } catch (IOException e) {
            log.error("删除资讯信息失败,errorMsg:{}", e.getMessage(), e);
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }

    /**
     * 根据索引ID删除QA资讯索引记录
     *
     * @param indexId
     */
    @Override
    public void deleteQAInfoIndex(String indexId) {
        Client client = null;
        try {
            client = getClient();
            DeleteRequest request = new DeleteRequest();
            request.type(QAInfoType);
            request.id(indexId);
            request.index(QAInfoEntityIndex);
            client.delete(request).actionGet();
        } catch (IOException e) {
            log.error("删除QA资讯信息失败,errorMsg:{}", e.getMessage(), e);
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }

    private Client getClient() throws UnknownHostException {
        Client client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esServerHost), esServerPort));
        return client;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //this.createInfoMapping();
    }
}