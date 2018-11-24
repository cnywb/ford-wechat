/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * BrowseToDBTime.java
 */

package com.ford.wechat.quartz.timer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ford.wechat.batch.constants.Parameter;
import com.ford.wechat.date.DateUtils;
import com.ford.wechat.entity.batch.BatchTask;
import com.ford.wechat.entity.batch.BatchType;
import com.ford.wechat.quartz.run.JobRunUtil;
import com.ford.wechat.service.batch.BatchTaskService;
import io.dabing.common.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.admin.service.JobService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 描述: 点击量从redis中获取T-1日的数据保存DB表
 *
 * @author ziv
 * @since 1.0
 */
@Service("browseMsgToDBLauncher")
public class BrowseMsgToDBTimer {

    private static final Logger logger = LoggerFactory.getLogger(BrowseMsgToDBTimer.class);

    @Resource(name = "browseMsgToDBJob")
    private Job job;

    @Resource(name = "taskJobLauncher")
    private JobLauncher jobLauncher;

    @Resource(name = "jobService")
    private JobService jobService;

    @Autowired
    private JobRepository jobRepository;


    @Autowired
    private BatchTaskService batchTaskService;

    public void createBatch() {
        Date nowDate = new Date();
        Date yesterday = DateUtil.getNextDay(nowDate, -1);
        String yesterdayDate = DateUtil.formatDate(yesterday, DateUtil.FORMAT_DATE_YYYYMMDD);

        BatchTask batchTask = new BatchTask();
        String batchNo = DateUtil.formatDate(nowDate, DateUtil.FORMAT_DATETIME_YYYYMMDDHHMMSS);
        batchTask.setBatchDate(nowDate);
        batchTask.setBatchNo(batchNo);
        batchTask.setBatchType(BatchType.BROWSE_MSG_TO_DB);
        batchTask.setStatus(BatchTask.STATUS_INIT);
        batchTask.setParams(yesterdayDate);
        this.batchTaskService.save(batchTask);
    }


    /***
     * 调用batch job 执行具体的任务
     */
    public void execute() {
        List<BatchTask> list = this.batchTaskService.getByType(BatchType.BROWSE_MSG_TO_DB);

        if (CollectionUtils.isEmpty(list)) {
            logger.info("本次无公告阅读记录任务！");
            return;
        }

        for (BatchTask batchTask : list) {
            String date = batchTask.getParams();
            JobParametersBuilder builder = new JobParametersBuilder();
            builder.addString(Parameter.DATE, date);
            builder.addLong(Parameter.TASK_ID, batchTask.getId());
            builder.addString("a", "aaaa");
            JobParameters params = builder.toJobParameters();
            JobRunUtil.runJob(job, params, jobRepository, jobService, jobLauncher);
        }

    }
}