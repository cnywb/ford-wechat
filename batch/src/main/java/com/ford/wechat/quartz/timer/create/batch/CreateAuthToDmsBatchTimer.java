package com.ford.wechat.quartz.timer.create.batch;/**
 * Created by jovi on 02/06/2017.
 */

import com.ford.wechat.date.DateUtils;
import com.ford.wechat.entity.batch.BatchTask;
import com.ford.wechat.entity.batch.BatchType;
import com.ford.wechat.service.batch.BatchTaskService;
import com.ford.wechat.service.support.WeTableSequenceService;
import io.dabing.common.date.DateUtil;
import io.dabing.common.json.JSONParser;
import io.dabing.common.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-06-02 10:25
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Service("createAuthToDmsBatchTimer")
public class CreateAuthToDmsBatchTimer {

    @Autowired
    private WeTableSequenceService tableSequenceService;
    @Autowired
    private BatchTaskService batchTaskService;

    /***
     * 调用batch job 执行具体的任务
     */
    public void execute() {

        BatchTask batchTask = new BatchTask();
        //批次号
        Long num = this.tableSequenceService.doIncrementSeqValue(new Date(),BatchTask.class.getName());
        String batchNo = NumberUtil.getBatchNo(num);
        batchTask.setBatchNo(batchNo);
        //批次执行状态
        batchTask.setStatus(BatchTask.STATUS_INIT);
        //批次执行参数
        Map<String,String> map = new HashMap<>();
        String dateNo = DateUtils.getDateNo();
        map.put("dateNo", dateNo);
        String params = JSONParser.toJSONString(map);
        batchTask.setParams(params);
        //批次备注
        String remark = DateUtil.formatDate(new Date(),DateUtil.FORMAT_DATETIME_DEFAULT)+"-"+BatchType.AUTH_TO_DMS.getName();
        batchTask.setRemark(remark);
        //批次类型
        batchTask.setBatchType(BatchType.AUTH_TO_DMS);
        this.batchTaskService.save(batchTask);


    }



}
