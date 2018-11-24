package com.ford.wechat.quartz.timer;/**
 * Created by jovi on 29/05/2017.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ford.wechat.batch.constants.Parameter;
import com.ford.wechat.entity.batch.BatchTask;
import com.ford.wechat.entity.batch.BatchType;
import com.ford.wechat.entity.dealer.WeChannel;
import com.ford.wechat.quartz.run.JobRunUtil;
import com.ford.wechat.service.batch.BatchTaskService;
import io.dabing.common.date.DateUtil;
import org.springframework.batch.admin.service.JobService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
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
 * All rights reserved. 2017-05-29 17:47
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Service("dayAuthReportJobLauncher")
public class DayAuthReportTimer {

    @Autowired
    private BatchTaskService batchTaskService;

    @Resource(name="dayAuthReportJob")
    private Job job;

    @Resource(name = "taskJobLauncher")
    private JobLauncher jobLauncher;

    @Resource(name = "jobService")
    private JobService jobService;
    /**
     * 导出excel地址
     */
    @Value("#{settings['day.auth.excel.file.path']}")
    private String outputFilePath;


    @Autowired
    private JobRepository jobRepository;

    /***
     * 调用batch job 执行具体的任务
     */
    public void execute() {

        List<BatchTask> list =this.batchTaskService.getByType(BatchType.DAY_AUTH_REPORT);

        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        for(BatchTask batchTask:list){
            String batchNo = batchTask.getBatchNo();
            //参数构建
            String taskParams = batchTask.getParams();
            JSONObject jsonObject = JSON.parseObject(taskParams);
            String dateNo = jsonObject.getString(Parameter.DATE_NO);

            JobParametersBuilder builder = new JobParametersBuilder();
            builder.addLong(Parameter.TASK_ID, batchTask.getId());
            builder.addString(Parameter.BATCH_NO,batchNo);
            builder.addString(Parameter.DATE_NO,dateNo);
            builder.addString(Parameter.OUTPUT_FILE_PATH, outputFilePath);
            builder.addString(Parameter.DATE, DateUtil.formatDate(new Date(),DateUtil.FORMAT_DATETIME_YYYYMMDDHHMMSSSSS));
            JobParameters params = builder.toJobParameters();
            JobRunUtil.runJob(job,params,jobRepository,jobService,jobLauncher);
        }

    }
}
