/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * BrowseController.java
 */

package com.ford.wechat.controller.pc.browse;

import com.ford.wechat.controller.pc.browse.vo.BrowseRecordPageReq;
import com.ford.wechat.controller.pc.browse.vo.BrowseRecordPageResp;
import com.ford.wechat.entity.excel.ExcelList;
import com.ford.wechat.service.excel.ExcelService;
import com.ford.wechat.service.pc.message.MenuReadRecordEntityService;
import com.ford.wechat.service.pc.message.MessageReadRecordEntityService;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述: 浏览地址统计
 *
 * @author ziv
 * @since 1.0
 */
@Controller
public class BrowseController {

    @Autowired
    private MenuReadRecordEntityService menuReadRecordEntityService;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private MessageReadRecordEntityService messageReadRecordEntityService;

    //按关键字分页查询对象
    @ApiService(transCode = "menuRecordPage")
    public Page<BrowseRecordPageResp> menuRecordPage(BrowseRecordPageReq req) {
        Page<Object[]> pageDatas = menuReadRecordEntityService.pagingBy(req.getPage(), req.getPattern(), req.getStartDate(), req.getEndDate());
        Page<BrowseRecordPageResp> resp = pageDatas.map(new Converter<Object[], BrowseRecordPageResp>() {
            public BrowseRecordPageResp convert(Object[] source) {
                BrowseRecordPageResp pageResp = new BrowseRecordPageResp();
                pageResp.setId(source[0]);
                pageResp.setName(source[1]);
                pageResp.setSort(source[2]);
                pageResp.setReadTime(source[3]);
                pageResp.setTimes(source[4]);
                return pageResp;
            }
        });
        return resp;
    }

    //按关键字分页查询对象
    @ApiService(transCode = "msgRecordPage")
    public Page<BrowseRecordPageResp> msgRecordPage(BrowseRecordPageReq req) {
        Page<Object[]> pageDatas = messageReadRecordEntityService.pagingBy(req.getPage(), req.getPattern(), req.getStartDate(), req.getEndDate());
        Page<BrowseRecordPageResp> resp = pageDatas.map(new Converter<Object[], BrowseRecordPageResp>() {
            public BrowseRecordPageResp convert(Object[] source) {
                BrowseRecordPageResp pageResp = new BrowseRecordPageResp();
                pageResp.setId(source[0]);
                pageResp.setName(source[1]);
                pageResp.setReadTime(source[2]);
                pageResp.setTimes(source[3]);
                return pageResp;
            }
        });
        return resp;
    }

    /**
     * 菜单点击统计记录
     */
    @RequestMapping(value = "/menuRecordExport.ctl")
    public void menuRecordExport(HttpServletRequest request, HttpServletResponse response) {
        excelService.exportData(ExcelList.B_CODE_MENU, request, response);
    }


    /**
     * 操作日志信息导出
     */
    @RequestMapping(value = "/msgRecordExport.ctl")
    public void msgRecordExport(HttpServletRequest request, HttpServletResponse response) {
        excelService.exportData(ExcelList.B_CODE_MSG, request, response);
    }
}