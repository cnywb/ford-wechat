/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * ComplainExcelServiceImpl.java
 */

package com.ford.wechat.service.pc.complain.impl;

import com.ford.wechat.repository.pc.complain.ComplainEntityRepository;
import com.ford.wechat.service.pc.complain.ComplainExcelService;
import io.dabing.common.date.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 描述:TODO
 *
 * @author ziv
 * @since 1.0
 */
@Service(value = "complainExcelService")
public class ComplainExcelServiceImpl implements ComplainExcelService {

    /**
     * 查询数据
     *
     * @param queryFields 提供的查询字段,要求返回的结果数组必须与该字段下标匹配
     * @param from        查询那个类
     * @param where       where条件
     * @param orderBy     排序条件
     * @param whereValue  页面上传递的参数值.
     * @return
     */
    @Autowired
    private ComplainEntityRepository complainEntityRepository;

    @Override
    public List<Object[]> getExportDataList(String queryFields, String from, String where, String orderBy, Map<String, String> whereValue) {
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
        return complainEntityRepository.findForExcel(startDate, endDate);
    }
}