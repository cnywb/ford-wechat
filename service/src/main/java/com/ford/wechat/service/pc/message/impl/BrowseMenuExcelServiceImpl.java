/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * BrowseMenuExcelServiceImpl.java
 */

package com.ford.wechat.service.pc.message.impl;

import com.ford.wechat.repository.pc.message.MenuReadRecordEntityRepository;
import com.ford.wechat.service.excel.ExcelDataSupportService;
import com.ford.wechat.service.pc.message.BrowseMenuExcelService;
import io.dabing.common.date.DateUtil;
import io.dabing.common.exception.BusinessException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 描述: 预览导出功能
 *
 * @author Yvan
 * @since 1.0
 */
@Service(value = "browseMenuExcelService")
public class BrowseMenuExcelServiceImpl implements BrowseMenuExcelService {

    @Autowired
    private MenuReadRecordEntityRepository menuReadRecordEntityRepository;

    @Override
    public List<Object[]> getExportDataList(String queryFields, String from, String where, String orderBy, Map<String, String> whereValue) {
        String type = whereValue.get("type");
        if (StringUtils.isEmpty(type)) {
            throw new BusinessException("数据提交异常！");
        }
        String startDateStr = whereValue.get("startDate");
        String endDateStr = whereValue.get("endDate");
        Date startDate = null;
        if (StringUtils.isNotEmpty(startDateStr)) {
            startDate = DateUtil.parser(startDateStr, DateUtil.FORMAT_DATE_DEFAULT);
        }
        Date endDate = null;
        if (StringUtils.isNotEmpty(endDateStr)) {
            endDate = DateUtil.parser(endDateStr, DateUtil.FORMAT_DATE_DEFAULT);
        }
        return menuReadRecordEntityRepository.findForExcel(getPattern(type), startDate, endDate);
    }


    /**
     * 日期格式化 pattern
     */
    public String getPattern(String type) {
        if ("时".equals(type)) {
            return "yyyy-mm-dd HH24";
        } else {
            return "yyyy-mm-dd";
        }
    }
}