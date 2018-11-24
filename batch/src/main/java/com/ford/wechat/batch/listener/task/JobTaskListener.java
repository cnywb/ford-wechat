package com.ford.wechat.batch.listener.task;


import com.ford.wechat.batch.constants.Parameter;
import com.ford.wechat.entity.batch.BatchTask;
import com.ford.wechat.service.batch.BatchTaskService;
import io.dabing.exception.BusinessException;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by wanglijun on 16/11/18.
 *
 * @modify yangkui 整个任务监听处理，只更改任务状态，和记录日志。默认执行失败后，状态不会设置为失败，只有达到一定次数才会失败。
 */
@Data
public class JobTaskListener extends JobExecutionListenerSupport {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(JobTaskListener.class);

    @Autowired
    private BatchTaskService batchTaskService;

    private String jobName;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("####{}任务开始执行,params:{}", jobName, jobExecution.getJobParameters().toString());
        //设置task为处理中
        Long taskId = jobExecution.getJobParameters().getLong(Parameter.TASK_ID);
        BatchTask task = batchTaskService.get(taskId);
        if (task == null) {
            logger.error("JobName: {} taskId:{} 不存在!", jobName, taskId);
            throw new BusinessException("JobName: " + jobName + "taskId:" + taskId + " 不存在!");
        }
        if (task.getRunTimes() == null) {
            task.setRunTimes(0);
        }
        task.setRunTimes(task.getRunTimes() + 1);
        task.setStatus(BatchTask.STATUS_HANDLING);
        task.setStartDate(new Date());
        task.setBatchResult("");
        this.batchTaskService.update(task);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        //重置TASK状态为空完成;
        Long taskId = jobExecution.getJobParameters().getLong(Parameter.TASK_ID);
        BatchTask task = batchTaskService.get(taskId);

        List<Throwable> errors = jobExecution.getAllFailureExceptions();
        if (task == null) {
            logger.error("JobName: {} taskId:{} 不存在!", jobName, taskId);
            throw new BusinessException("JobName: " + jobName + "taskId:" + taskId + " 不存在!");

        }
        task.setExecuteStatus(jobExecution.getStatus());
        if (errors != null && !errors.isEmpty()) {
            StringBuffer sb = new StringBuffer();
            for (Throwable e : errors) {
                sb.append(e.getMessage());
            }
            String errorsStr = sb.toString();
            int length = errorsStr.length();
            if (length > 4000) {
                length = 3999;
            }
            errorsStr = errorsStr.substring(0, length);
            logger.error("任务:{}执行异常,taskId:{},errors:{}", jobName, taskId, errorsStr);
            task.setBatchResult(errorsStr);
            task.setStatus(BatchTask.STATUS_FAIL);
        } else {
            task.setStatus(BatchTask.STATUS_FINISHED);
            task.setBatchResult("处理成功");
        }
        task.setEndDate(new Date());

        this.batchTaskService.update(task);
        logger.info("####{},taskId:{}任务执行结束", jobName, taskId);
    }

}
