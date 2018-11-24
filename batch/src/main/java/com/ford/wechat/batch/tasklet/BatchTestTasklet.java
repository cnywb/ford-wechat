package com.ford.wechat.batch.tasklet;/**
 * Created by jovi on 20/03/2017.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import javax.annotation.Resource;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-03-20 19:47
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public class BatchTestTasklet implements Tasklet {
    /***
     * 日志
     */
    private static final Logger logger= LoggerFactory.getLogger (BatchTestTasklet.class);



    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        logger.info("========================");
        logger.info("batch测试");
        logger.info("========================");
        return RepeatStatus.FINISHED;
    }
}
