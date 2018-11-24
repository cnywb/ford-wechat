/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * SearchController.java
 */

package com.ford.wechat.pc.controller.info.search;

import com.ford.wechat.entity.pc.info.InfoEntity;
import com.ford.wechat.entity.pc.info.QAInfoEntity;
import com.ford.wechat.pc.common.response.Response;
import com.ford.wechat.pc.controller.info.search.vo.EsSearchInfoPageReq;
import com.ford.wechat.pc.controller.info.search.vo.EsSearchInfoPageResp;
import com.ford.wechat.pc.controller.info.search.vo.EsSearchQAInfoPageResp;
import com.ford.wechat.pc.es.ESSearchPage;
import com.ford.wechat.pc.es.ElasticSearchService;
import com.ford.wechat.service.pc.info.InfoEntityService;
import com.ford.wechat.service.pc.info.QAInfoEntityService;
import io.dabing.common.date.DateUtil;
import io.dabing.common.util.JSONUtil;
import io.dabing.core.repository.domain.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 资讯与互动问答查询与关键字搜索
 */
@Controller
@RequestMapping("/api/public")
@Slf4j
public class SearchController {

    @Autowired
    private ElasticSearchService elasticSearchService;

    @Autowired
    private InfoEntityService infoEntityService;

    @Autowired
    private QAInfoEntityService qaInfoEntityService;

    @Autowired
    private RedisTemplate redisTemplate;

    //按关键字分页查询对象
    @RequestMapping("/info/search")
    @ResponseBody
    public Response<Page<EsSearchInfoPageResp>> infoSearch(@RequestBody EsSearchInfoPageReq req) {

        Page<EsSearchInfoPageResp> resp = null;
        //有关键字直接搜索ES，没有关键字查询数据库
        if (StringUtils.isNotBlank(req.getPage().getKeyWord())) {

            final ESSearchPage page = new ESSearchPage();
            page.setPageNumber(req.getPage().getPageNumber());
            page.setPageSize(req.getPage().getPageSize());
            page.setPageNumber(req.getPage().getPageNumber());
            page.setType(req.getInfoType());
            page.setKeyWord(req.getPage().getKeyWord());
            Page<SearchHit> pages = elasticSearchService.searchInfoPage(page);
            resp = pages.map(new Converter<SearchHit, EsSearchInfoPageResp>() {
                public EsSearchInfoPageResp convert(SearchHit source) {
                    float score = source.getScore();
                    log.info("关键字:{},title:{},score:{}", page.getKeyWord(), source.getField("title"), score);
                    EsSearchInfoPageResp a = new EsSearchInfoPageResp();
                    InfoEntity entity = JSONUtil.toObject(source.getSourceAsString(), InfoEntity.class);
                    BeanUtils.copyProperties(entity, a, "indexDate", "indexed", "esId");
                    a.setCreateDate(DateUtil.formatDefaultDate(entity.getCreatedDate()));
                    //页面用到的高亮关键字。页面上只显示标题和摘要即可。
                    Map<String, HighlightField> fields = source.getHighlightFields();
                    for (Map.Entry<String, HighlightField> entry : fields.entrySet()) {
                        String key = entry.getKey();
                        Text[] text = entry.getValue().getFragments();
                        if ("title".equals(key)) {
                            a.setHighLightTitle(convertStr(text));
                        }
                        if ("digest".equals(key)) {
                            a.setHighLightDigest(convertStr(text));
                        }
                    }
                    return a;
                }
            });
            //搜索到有结果的关键字才增加热门值
            if (!resp.getContent().isEmpty()) {
                //关键字保存到redis当中，用于统计每日热门关键字
                redisTemplate.opsForZSet().incrementScore("infoHotKeyWord", req.getPage().getKeyWord(), 1d);
            }
        } else {
            Page<InfoEntity> pages = infoEntityService.findByGridPage(req.getPage());
            resp = pages.map(new Converter<InfoEntity, EsSearchInfoPageResp>() {
                @Override
                public EsSearchInfoPageResp convert(InfoEntity infoEntity) {
                    EsSearchInfoPageResp a = new EsSearchInfoPageResp();
                    BeanUtils.copyProperties(infoEntity, a);
                    a.setCreateDate(DateUtil.formatDefaultDate(infoEntity.getCreatedDate()));

                    return a;
                }
            });
        }

        return new Response<>(resp);
    }


    //按关键字分页查询对象
    @RequestMapping("/qainfo/search")
    @ResponseBody
    public Response<Page<EsSearchQAInfoPageResp>> qaInfoSearch(@RequestBody EsSearchInfoPageReq req) {

        Page<EsSearchQAInfoPageResp> resp = null;
        //有关键字直接搜索ES，没有关键字查询数据库
        if (StringUtils.isNotBlank(req.getPage().getKeyWord())) {

            ESSearchPage page = new ESSearchPage();
            page.setPageNumber(req.getPage().getPageNumber());
            page.setPageSize(req.getPage().getPageSize());
            page.setPageNumber(req.getPage().getPageNumber());
            page.setKeyWord(req.getPage().getKeyWord());
            Page<SearchHit> pages = elasticSearchService.searchQAInfoPage(page);
            resp = pages.map(new Converter<SearchHit, EsSearchQAInfoPageResp>() {
                public EsSearchQAInfoPageResp convert(SearchHit source) {
                    EsSearchQAInfoPageResp a = new EsSearchQAInfoPageResp();
                    QAInfoEntity entity = JSONUtil.toObject(source.getSourceAsString(), QAInfoEntity.class);
                    BeanUtils.copyProperties(entity, a, "tags", "indexed", "esId");
                    a.setCreateDate(DateUtil.formatDefaultDate(entity.getCreatedDate()));
                    //页面用到的高亮关键字。页面上只显示标题和摘要即可。
                    Map<String, HighlightField> fields = source.getHighlightFields();
                    for (Map.Entry<String, HighlightField> entry : fields.entrySet()) {
                        String key = entry.getKey();
                        Text[] text = entry.getValue().getFragments();
                        if ("question".equals(key)) {
                            a.setHighLightQuestion(convertStr(text));
                        }
                        if ("answer".equals(key)) {
                            a.setHighLightAnswer(convertStr(text));
                        }
                    }
                    return a;
                }
            });
            //搜索到有结果的关键字才增加热门值
            if (!resp.getContent().isEmpty()) {
                //关键字保存到redis当中，用于统计每日热门关键字
                redisTemplate.opsForZSet().incrementScore("QAInfoHotKeyWord", req.getPage().getKeyWord(), 1d);
            }

        } else {
            Page<QAInfoEntity> pages = qaInfoEntityService.findByGridPage(req.getPage());
            resp = pages.map(new Converter<QAInfoEntity, EsSearchQAInfoPageResp>() {
                @Override
                public EsSearchQAInfoPageResp convert(QAInfoEntity infoEntity) {
                    EsSearchQAInfoPageResp a = new EsSearchQAInfoPageResp();
                    BeanUtils.copyProperties(infoEntity, a);
                    a.setCreateDate(DateUtil.formatDefaultDate(infoEntity.getCreatedDate()));
                    return a;
                }
            });
        }

        return new Response<>(resp);
    }

    //获取资讯热门搜索关键字
    @RequestMapping("/info/hot")
    @ResponseBody
    public Response<List<String>> listInfoHotKeyWord() {
        List<String> result = new ArrayList<>();

        Set<ZSetOperations.TypedTuple<String>> values = redisTemplate.opsForZSet().reverseRangeWithScores("infoHotKeyWord", 0, 4);
        for (ZSetOperations.TypedTuple s : values) {
            result.add((String) s.getValue());
        }
        return new Response<>(result);
    }


    //获取问答热门搜索关键字
    @RequestMapping("/qainfo/hot")
    @ResponseBody
    public Response<List<String>> listQAInfoHotKeyWord() {
        List<String> result = new ArrayList<>();

        Set<ZSetOperations.TypedTuple<String>> values = redisTemplate.opsForZSet().reverseRangeWithScores("QAInfoHotKeyWord", 0, 4);
        for (ZSetOperations.TypedTuple s : values) {
            result.add((String) s.getValue());
        }
        return new Response<>(result);
    }

    private String convertStr(Text[] texts) {
        StringBuilder sb = new StringBuilder();
        for (Text t : texts) {
            sb.append(t);
        }
        return sb.toString();
    }
}