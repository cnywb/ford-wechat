/*
 * Copyright (c)  2016, 
 * All rights reserved.
 * JobRunUtil.java 2016-06-24 下午12:06
 */

package com.ford.wechat.quartz.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.admin.service.JobService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;

/**
 * 描述:batch Job 调用工具类
 *
 * @author yangkui create on 2016-06-24.
 * @since 1.0
 */
public class JobRunUtil {
    /**日志*/
    private static Logger logger = LoggerFactory.getLogger(JobRunUtil.class);

    public static String runJob(Job job, JobParameters params, JobRepository jobRepository, JobService jobService, JobLauncher jobLauncher) {
        String jobName = job.getName();
        try {
            JobExecution jobExecution = jobRepository.getLastJobExecution(jobName, params);
            if (jobExecution != null) {
                String exitCode = jobExecution.getExitStatus().getExitCode();
                // 已完成的job instance，不允许再次run与create，理论上不会出现
                if ("COMPLETED".equals(exitCode)) {
                    logger.error("*******current job is COMPLETED jobName:{},jobParams:{}", new Object[]{jobName, params.toString()});
                    return exitCode;
                } else if ("FAILED".equals(exitCode)) {
                    logger.info("***** 当前任务：{} 状态为:{} params:{},进入重启机制 ********", new Object[]{jobName, exitCode, params.toString()});
                    jobService.restart(jobExecution.getId());
                } else {
                    logger.info("****  jobName:{},jobParams:{},jobExitCode:{} 不执行", new Object[]{jobName, params.toString(), jobExecution.getExitStatus().getExitCode()});
                    return exitCode;
                }
            } else {
                //异步操作，放入队列，等待调度
                jobLauncher.run(job, params);
                logger.info("{} is running params:{}", new Object[]{jobName, params.toString()});
            }
        } catch (JobExecutionAlreadyRunningException e) {
            logger.error(jobName + " error", e);
        } catch (JobRestartException e) {
            logger.error(jobName + " error", e);
        } catch (JobInstanceAlreadyCompleteException e) {
            logger.error(jobName + " error", e);
        } catch (JobParametersInvalidException e) {
            logger.error(jobName + " error", e);
        } catch (NoSuchJobExecutionException e) {
            logger.error(jobName + " error", e);
        } catch (NoSuchJobException e) {
            logger.error(jobName + " error", e);
        } catch (Exception e) {
            logger.error(jobName + " error", e);
        }
        return null;

    }

}