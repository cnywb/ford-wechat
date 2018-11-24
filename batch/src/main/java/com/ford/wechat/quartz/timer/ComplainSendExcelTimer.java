/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * BrowseToDBTime.java
 */

package com.ford.wechat.quartz.timer;

import com.ford.wechat.batch.constants.Parameter;
import com.ford.wechat.entity.pc.complain.ComplainEntity;
import com.ford.wechat.quartz.run.JobRunUtil;
import com.ford.wechat.service.pc.complain.ComplainEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.admin.service.JobService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 描述: 发送投诉建议Excel至CIC
 *
 * @author ziv
 * @since 1.0
 */
@Slf4j
@Service("complainSendExcelTimer")
public class ComplainSendExcelTimer {

    @Resource(name = "complainMailBatchJob")
    private Job job;

    @Resource(name = "taskJobLauncher")
    private JobLauncher jobLauncher;

    @Resource(name = "jobService")
    private JobService jobService;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ComplainEntityService complainEntityService;

    /***
     * 调用batch job 执行具体的任务
     */
    public void execute() {
        complainEntityService.updateSendMailBySendMail(ComplainEntity.SEND_MAIL_SENDING, ComplainEntity.SEND_MAIL_WAIT);
        JobParametersBuilder builder = new JobParametersBuilder();
        builder.addDate(Parameter.DATE, new Date());
        JobParameters params = builder.toJobParameters();
        JobRunUtil.runJob(job, params, jobRepository, jobService, jobLauncher);
    }
}