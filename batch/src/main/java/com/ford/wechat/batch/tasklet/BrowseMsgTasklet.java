package com.ford.wechat.batch.tasklet;

import com.ford.wechat.batch.constants.Parameter;
import com.ford.wechat.entity.pc.PCRedisKeys;
import com.ford.wechat.entity.pc.message.MessageReadRecordEntity;
import com.ford.wechat.service.pc.message.MessageReadRecordEntityService;
import com.google.common.collect.Lists;
import io.dabing.common.util.JSONUtil;
import io.dabing.redis.util.RedisKeyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Set;

public class BrowseMsgTasklet implements Tasklet {
    /***
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(BrowseMsgTasklet.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MessageReadRecordEntityService messageReadRecordEntityService;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        String yesterday = (String) chunkContext.getStepContext().getJobParameters().get(Parameter.DATE);
        String msgReadKey = RedisKeyUtils.build(PCRedisKeys.MSG_READ_RECORD, yesterday);

        List<MessageReadRecordEntity> messageReadRecordEntityList = Lists.newArrayList();
        Set<String> values = redisTemplate.opsForZSet().rangeByScore(msgReadKey, 1, 2);
        if (values == null || values.size() == 0) {
            logger.info("本次处理{}公告阅读量记录为0条,不作处理", yesterday);
            return RepeatStatus.FINISHED;
        }
        logger.info("本次处理{}公告阅读量记录为{}条", yesterday, values.size());
        for (String s : values) {
            MessageReadRecordEntity messageReadRecordEntity = JSONUtil.toObject(s, MessageReadRecordEntity.class);
            messageReadRecordEntityList.add(messageReadRecordEntity);
        }
        messageReadRecordEntityService.save(messageReadRecordEntityList);
        return RepeatStatus.FINISHED;
    }
}
