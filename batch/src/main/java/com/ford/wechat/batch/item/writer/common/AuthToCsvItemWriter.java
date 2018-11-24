package com.ford.wechat.batch.item.writer.common;/**
 * Created by jovi on 23/05/2017.
 */

import com.ford.wechat.date.DateUtils;
import com.ford.wechat.entity.auth.AuthToDms;
import com.ford.wechat.entity.auth.AuthToDmsEntity;
import com.ford.wechat.service.auth.AuthToDmsService;
import com.ford.wechat.service.excel.CsvService;
import com.google.common.collect.Lists;
import io.dabing.common.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;


/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-05-23 18:49
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public class AuthToCsvItemWriter implements ItemWriter<AuthToDms> {
    /**
     * CSV存放目录
     */
    @Value("#{settings['auth.csv.file.path']}")
    private String csvFilePath;

    @Autowired
    private CsvService csvService;
    @Autowired
    private AuthToDmsService authToDmsService;

    private static Logger logger = LoggerFactory.getLogger(AuthToCsvItemWriter.class);

    @Override
    public void write(List<? extends AuthToDms> list) throws Exception {
        List<AuthToDmsEntity> mapContentList = Lists.newLinkedList();
        for (AuthToDms authToDms : list) {
            AuthToDmsEntity entity = new AuthToDmsEntity();
            entity.setVerifyDate(converNullString(DateUtil.formatDate(authToDms.getDcrtDate(), DateUtils.FORMAT_DATE_TIME_DEFAULT)));
            entity.setStatus("1");
            entity.setVerifyChannel(converNullString(authToDms.getChannelCode()));
            entity.setVerifyChannel(converNullString(authToDms.getChannelCode()));
            entity.setVerify(converNullString(authToDms.getVerify().toString()));
            entity.setDealerServiceCode(converNullString(authToDms.getDealerServiceCode()));
            entity.setDateNo(converNullString(authToDms.getDateNo()));
            entity.setRemark2(converNullString(authToDms.getRemark()));
            entity.setSendDate(converNullString(DateUtil.formatDate(new Date(), DateUtils.FORMAT_DATE_TIME_DEFAULT)));
            entity.setDealerScan(converNullString(authToDms.getDealerScan().toString()));
            entity.setBatchNo(converNullString(authToDms.getBatchNo()));
            entity.setVerifyMobile(converNullString(authToDms.getMobile()));
            entity.setVin(converNullString(authToDms.getVin()));
            entity.setFollow(converNullString(authToDms.getFollow().toString()));
            entity.setOpenId(converNullString(authToDms.getOpenId()));
            entity.setUpdateDate(converNullString(DateUtil.formatDate(authToDms.getUnauthDate(), DateUtils.FORMAT_DATE_TIME_DEFAULT)));
            authToDmsService.update(authToDms);
            mapContentList.add(entity);
        }

        this.csvService.exportData(mapContentList,"oldAuthDataExport",csvFilePath+DateUtils.getDateNo()+"/");

    }

    private String converNullString(String str){
        if(str==null){
            return "";
        }
        return str;
    }
}
