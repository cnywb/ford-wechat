/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * BrowseToDBTime.java
 */

package com.ford.wechat.quartz.timer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ford.wechat.batch.constants.Parameter;
import com.ford.wechat.entity.batch.BatchTask;
import com.ford.wechat.entity.batch.BatchType;
import com.ford.wechat.quartz.run.JobRunUtil;
import com.ford.wechat.service.batch.BatchTaskService;
import io.dabing.common.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service("couponEventStatisticsJobLauncher")
public class CouponEventStatisticsTimer {


    @Resource(name = "couponEventStatisticsJob")
    private Job job;

    @Resource(name = "taskJobLauncher")
    private JobLauncher jobLauncher;

    @Resource(name = "jobService")
    private JobService jobService;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private BatchTaskService batchTaskService;


    /***
     * 调用batch job 执行具体的任务
     */
    public void execute() {

        List<BatchTask> list = this.batchTaskService.getByType(BatchType.COUPON_EVENT_STATISTICS);

        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        for (BatchTask batchTask : list) {
            String batchNo = batchTask.getBatchNo();
            //参数构建
            String taskParams = batchTask.getParams();
            JSONObject jsonObject = JSON.parseObject(taskParams);
            String dateNo = jsonObject.getString(Parameter.DATE_NO);
            String date = jsonObject.getString(Parameter.DATE);

            JobParametersBuilder builder = new JobParametersBuilder();
            builder.addLong(Parameter.TASK_ID, batchTask.getId());
            builder.addString(Parameter.BATCH_NO, batchNo);
            builder.addString(Parameter.DATE_NO, dateNo);
            builder.addString(Parameter.DATE, date);
            JobParameters params = builder.toJobParameters();
            JobRunUtil.runJob(job, params, jobRepository, jobService, jobLauncher);
        }

    }
}