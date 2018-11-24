package com.ford.wechat.quartz.timer;

import com.ford.wechat.quartz.run.JobRunUtil;
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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by xuwenfeng on 16-8-6.
 *
 * @modify yangkui
 * 定时调度job，将dms提供的车主xml文件，解析入库到本地数据库，并进行清洗操作。
 */
@Component("jobTestJobLauncher")
public class JobTestTimer {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(JobTestTimer.class);

    @Resource(name="jobTestJob")
    private Job job;

    @Resource(name = "taskJobLauncher")
    private JobLauncher jobLauncher;

    @Resource(name = "jobService")
    private JobService jobService;

    @Autowired
    private JobRepository jobRepository;

    /***
     * 调用batch job 执行具体的任务
     */
    public void execute() {
        System.out.println("============================");
        logger.info("job test");
        System.out.println("============================");

        JobParametersBuilder builder = new JobParametersBuilder();
        builder.addString("batchNo","batchNo11111111");
        builder.addString("date", DateUtil.formatDate(new Date(),DateUtil.FORMAT_DATETIME_YYYYMMDDHHMMSSSSS));
        JobParameters params = builder.toJobParameters();
        JobRunUtil.runJob(job,params,jobRepository,jobService,jobLauncher);
    }

}
