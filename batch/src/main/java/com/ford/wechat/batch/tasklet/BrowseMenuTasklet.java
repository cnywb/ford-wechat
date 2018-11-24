package com.ford.wechat.batch.tasklet;

import com.ford.wechat.batch.constants.Parameter;
import com.ford.wechat.entity.pc.PCRedisKeys;
import com.ford.wechat.entity.pc.menu.MenuReadRecordEntity;
import com.ford.wechat.service.pc.message.MenuReadRecordEntityService;
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
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Set;

public class BrowseMenuTasklet implements Tasklet {
    /***
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(BrowseMenuTasklet.class);

    @Autowired
    private RedisTemplate redisTemplate;


    @Autowired
    private MenuReadRecordEntityService menuReadRecordEntityService;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        String yesterday = (String) chunkContext.getStepContext().getJobParameters().get(Parameter.DATE);
        String msgReadKey = RedisKeyUtils.build(PCRedisKeys.MENU_READ_RECORD, yesterday);

        List<MenuReadRecordEntity> menuReadRecordEntityList = Lists.newArrayList();
        Set<String> values = redisTemplate.opsForZSet().rangeByScore(msgReadKey, 1, 2);
        if (values == null || values.size() == 0) {
            logger.info("本次处理{}菜单点击量记录为0条,不作处理", yesterday);
            return RepeatStatus.FINISHED;
        }
        logger.info("本次处理{}菜单点击量记录为{}条", yesterday, values.size());
        for (String s : values) {
            MenuReadRecordEntity menuReadRecordEntity = JSONUtil.toObject(s, MenuReadRecordEntity.class);
            menuReadRecordEntityList.add(menuReadRecordEntity);
        }
        menuReadRecordEntityService.save(menuReadRecordEntityList);
        return RepeatStatus.FINISHED;
    }
}
