/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * ComplainController.java
 */
package com.ford.wechat.controller.pc.complain;

import com.ford.wechat.controller.pc.complain.vo.ComplainPageReq;
import com.ford.wechat.controller.pc.complain.vo.ComplainPageResp;
import com.ford.wechat.entity.excel.ExcelList;
import com.ford.wechat.entity.pc.complain.ComplainEntity;
import com.ford.wechat.service.excel.ExcelService;
import com.ford.wechat.service.pc.complain.ComplainEntityService;
import io.dabing.common.util.DateUtils;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 描述:ComplainController CRUD 逻辑控制层
 *
 * @author ziv
 * @since 1.0
 */
@Controller
public class ComplainController {

    @Autowired
    private ComplainEntityService service;

    @Autowired
    private ExcelService excelService;


    /**
     * 按关键字分页查询对象
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "complainPage")
    public Page<ComplainPageResp> complainPage(ComplainPageReq req) {
        Page<ComplainEntity> pages = service.pagingBy(req.getPage(), req.getStartDate(), req.getEndDate());


        Page<ComplainPageResp> retval = pages.map (new Converter<ComplainEntity, ComplainPageResp>() {
            public ComplainPageResp convert(ComplainEntity source) {
                ComplainPageResp resp = new ComplainPageResp();
                if (source != null) {
                    BeanUtils.copyProperties(source, resp);
                    resp.setFirstInsert(DateUtils.format(source.getCreatedDate(), DateUtils.FORMAT_DATE_TIME_DEFAULT));
                }
                return resp;
            }
        });

        return retval;
    }

    /**
     * 菜单点击统计记录
     */
    @RequestMapping(value = "/complainExport.ctl")
    public void menuRecordExport(HttpServletRequest request, HttpServletResponse response) {
        excelService.exportData(ExcelList.B_CODE_COMPLAIN, request, response);
    }

}