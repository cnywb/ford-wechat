/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * SearchController.java
 */

package com.ford.wechat.controller.pc;

import com.alibaba.druid.support.json.JSONUtils;
import com.ford.wechat.controller.es.ESSearchPage;
import com.ford.wechat.controller.es.ElasticSearchService;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * 描述: ES 搜索
 *
 * @author ziv
 * @since 1.0
 */
@Controller
@Slf4j
public class SearchController {

    @Autowired
    private ElasticSearchService elasticSearchService;

    //按关键字分页查询对象
    @ApiService(transCode = "esSearchInfoPage")
    public Page<EsSearchInfoPageResp> esSearchInfoPage(EsSearchInfoPageReq req) {
        ESSearchPage page = new ESSearchPage();
        page.setKeyWord(req.getKeyWord());
        page.setPageNumber(req.getPage().getPageNumber());
        page.setPageSize(10);
        page.setType(req.getInfoType());
        Page<SearchHit> pages = null;
        if ("info".equalsIgnoreCase(req.getType())) {
            pages = elasticSearchService.searchInfoPage(page);
        } else {
            pages = elasticSearchService.searchQAInfoPage(page);
        }
        Page<EsSearchInfoPageResp> resp = pages.map(new Converter<SearchHit, EsSearchInfoPageResp>() {
            public EsSearchInfoPageResp convert(SearchHit source) {
                EsSearchInfoPageResp a = new EsSearchInfoPageResp();
                a.setContent(source.getSourceAsString());
                a.setEsId(source.getId());
                a.setType(source.getIndex());
                Map<String, HighlightField> fields = source.getHighlightFields();
                for (Map.Entry<String, HighlightField> entry : fields.entrySet()) {
                    String key = entry.getKey();
                    Text[] text = entry.getValue().getFragments();
                    if ("title".equals(key)) {
                        StringBuffer sb = new StringBuffer();
                        for (Text t : text) {
                            sb.append(t);
                        }
                        a.setHighLightTitle(sb.toString());
                    }

                    if ("digest".equals(key)) {
                        StringBuffer sb = new StringBuffer();
                        for (Text t : text) {
                            sb.append(t);
                        }
                        a.setHighLightDigest(sb.toString());
                    }
                }
                return a;
            }
        });
        return resp;
    }

    //删除指定索引ID的元数据
    @ApiService(transCode = "esSearchInfoDelete")
    public void esSearchInfoDelete(EsSearchInfoDeleteReq req) {
        if ("infoentity".equalsIgnoreCase(req.getType())) {
            elasticSearchService.deleteInfoIndex(req.getEsId());
        } else {
            elasticSearchService.deleteQAInfoIndex(req.getEsId());
        }
    }
}