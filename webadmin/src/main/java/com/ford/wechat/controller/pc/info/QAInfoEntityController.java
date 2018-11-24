/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * QAInfoEntity.java 2017-05-16 19:35:16
 */
package com.ford.wechat.controller.pc.info;

import com.ford.wechat.controller.es.ElasticSearchService;
import io.dabing.common.date.DateUtil;
import io.dabing.common.exception.BusinessException;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import com.ford.wechat.controller.pc.info.vo.*;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;
import com.ford.wechat.entity.pc.info.QAInfoEntity;
import com.ford.wechat.service.pc.info.QAInfoEntityService;
import org.springframework.util.StringUtils;

/**
 * Created by ziv.hung on 2017-05-16 19:35:16.
 *
 * @since 1.0
 */
@Controller
public class QAInfoEntityController {
    @Autowired
    private QAInfoEntityService service;

    @Autowired
    private ElasticSearchService elasticSearchService;

    //按关键字分页查询对象
    @ApiService(transCode = "qaInfoEntityPage")
    public Page<QAInfoEntityPageResp> qaInfoEntityPage(QAInfoEntityPageReq req) {
        Page<QAInfoEntity> pages = service.findByGridPage(req.getPage());
        Page<QAInfoEntityPageResp> resp = pages.map(new Converter<QAInfoEntity, QAInfoEntityPageResp>() {
            public QAInfoEntityPageResp convert(QAInfoEntity source) {
                QAInfoEntityPageResp a = new QAInfoEntityPageResp();
                BeanUtils.copyProperties(source, a);
                return a;
            }
        });
        return resp;
    }

    //单一创建对象
    @ApiService(transCode = "qaInfoEntityHandle")
    public String qaInfoEntityHandle(QAInfoEntityHandleReq req) {
        QAInfoEntity object = new QAInfoEntity();
        if (req.getId() != null) {
            object = service.get(req.getId());
        }


        BeanUtils.copyProperties(req, object);
        //查看问题回答是否为空
        if(!StringUtils.hasText(object.getAnswer())){
            return "500";
        }
        //清除空格
        String answer = StringUtils.trimAllWhitespace(object.getAnswer());
        answer.replaceAll("&nbsp;","");


        //过滤script关键字
        if(answer.indexOf("script")>0 || answer.indexOf("iframe")>0 || answer.indexOf("alert")>0){
            return "500";
        }
        String esId = elasticSearchService.indexQAInfo(object);
        object.setEsId(esId);
        object.setIndexed(true);
        if (req.getId() != null) {
            this.service.update(object);
        } else {
            this.service.save(object);
        }
        return "200";

    }

    //重建索引
    @ApiService(transCode = "qaInfoEntityEs")
    public void qaInfoEntityEs(QAInfoEntityHandleReq req) {
        QAInfoEntity object = service.get(req.getId());
        String esId = elasticSearchService.indexQAInfo(object);
        object.setEsId(esId);
        object.setIndexed(true);
        service.update(object);
    }


    //获取明细
    @ApiService(transCode = "qaInfoEntityGet")
    public QAInfoEntityHandleReq qaInfoEntityGet(QAInfoEntityHandleReq req) {
        QAInfoEntity object = service.get(req.getId());
        BeanUtils.copyProperties(object, req);
        return req;
    }

    //批量删除对象
    @ApiService(transCode = "qaInfoEntityDelete")
    public void qaInfoEntityDelete(QAInfoEntityDeleteReq req) {
        for (Long id : req.getIds()) {
            String esId = service.get(id).getEsId();
            try {
                elasticSearchService.deleteQAInfoIndex(esId);
            }catch (Exception e){
                throw new BusinessException("索引删除失败");
            }
            service.delete(id);
        }
    }


    public static void main(String[] args){
        String a = "  Core Java jsp servlets             jdbc struts hibernate spring  ";
        String b = new String(a);
        System.out.println("==========================================");
        System.out.println(b);
        b = StringUtils.trimAllWhitespace(a);

        System.out.println(a);
        System.out.println(b);
        System.out.println("==========================================");
    }

}