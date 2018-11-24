/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * InfoEntity.java 2017-05-16 19:31:48
 */
package com.ford.wechat.controller.pc.info;

import com.ford.wechat.controller.es.ElasticSearchService;
import com.ford.wechat.controller.pc.info.vo.InfoEntityDeleteReq;
import com.ford.wechat.controller.pc.info.vo.InfoEntityHandleReq;
import com.ford.wechat.controller.pc.info.vo.InfoEntityPageReq;
import com.ford.wechat.controller.pc.info.vo.InfoEntityPageResp;
import com.ford.wechat.entity.pc.info.InfoEntity;
import com.ford.wechat.service.pc.info.InfoEntityService;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;

/**
 * Created by ziv.hung on 2017-05-16 19:31:48.
 *
 * @since 1.0
 */
@Controller
public class InfoEntityController {
    @Autowired
    private InfoEntityService service;

    @Autowired
    private ElasticSearchService elasticSearchService;

    //按关键字分页查询对象
    @ApiService(transCode = "infoEntityPage")
    public Page<InfoEntityPageResp> findPage(InfoEntityPageReq req) {
        Page<InfoEntity> pages = service.findByGridPage(req.getPage());
        Page<InfoEntityPageResp> resp = pages.map(new Converter<InfoEntity, InfoEntityPageResp>() {
            public InfoEntityPageResp convert(InfoEntity source) {
                InfoEntityPageResp a = new InfoEntityPageResp();
                BeanUtils.copyProperties(source, a);
                return a;
            }
        });
        return resp;
    }

    //单一创建对象
    @ApiService(transCode = "infoEntityHandle")
    public void create(InfoEntityHandleReq req) {
        InfoEntity object = new InfoEntity();
        if (req.getId() != null) {
            object = service.get(req.getId());
        }
        BeanUtils.copyProperties(req, object);
        String esId = elasticSearchService.indexInfo(object);
        object.setEsId(esId);
        object.setIndexed(true);
        if (req.getId() != null) {
            this.service.update(object);
        } else {
            this.service.save(object);
        }
    }
    //重建索引
    @ApiService(transCode = "infoEntityEs")
    public void infoEntityEs(InfoEntityHandleReq req) {
        InfoEntity object = service.get(req.getId());
        String esId = elasticSearchService.indexInfo(object);
        object.setEsId(esId);
        object.setIndexed(true);
        service.update(object);
    }

    //获取明细
    @ApiService(transCode = "infoEntityGet")
    public InfoEntityHandleReq infoEntityGet(InfoEntityHandleReq req) {
        InfoEntity object = service.get(req.getId());
        BeanUtils.copyProperties(object, req);
        return req;
    }

    //批量删除对象
    @ApiService(transCode = "infoEntityDelete")
    public void delete(InfoEntityDeleteReq req) {
        for (Long id : req.getIds()) {
            String esId = service.get(id).getEsId();
            try{
                elasticSearchService.deleteInfoIndex(esId);
            }catch (Exception e){
                e.printStackTrace();
            }
            service.delete(id);
        }
    }
}