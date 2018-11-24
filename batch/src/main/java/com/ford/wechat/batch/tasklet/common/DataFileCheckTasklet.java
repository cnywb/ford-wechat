package com.ford.wechat.batch.tasklet.common;

import com.ford.wechat.batch.constants.Parameter;
import io.dabing.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.File;

/**
 * Created by xuwenfeng on 16-7-30.
 *
 * @modify yangkui 检查文件是否存在。
 */
public class DataFileCheckTasklet implements Tasklet {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(DataFileCheckTasklet.class);

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext context) throws Exception {
        String dateNo = (String) context.getStepContext().getJobParameters().get(Parameter.DATE_NO);
        String batchNo = (String) context.getStepContext().getJobParameters().get(Parameter.BATCH_NO);
        String inputPath = (String) context.getStepContext().getJobParameters().get(Parameter.INPUT_FILE_PATH);
        String dataType = (String) context.getStepContext().getJobParameters().get(Parameter.DATA_TYPE);
        String inputFilePath = inputPath + dateNo + "/xml/";

        logger.info("检查文件是否存在:inputFilePath:{},batchNo:{}", inputFilePath, batchNo);

        File file = new File(inputFilePath);

        if (!file.exists() && !file.isDirectory()) {
            logger.error(dataType + "文件不存在,fileName:" + inputFilePath);
            throw new BusinessException(inputFilePath + "文件不存在!");
        }

        return RepeatStatus.FINISHED;
    }
}
