package com.ford.wechat.batch.item.processor.verify.dealer;/**
 * Created by jovi on 26/03/2017.
 */

import com.ford.wechat.entity.auth.AuthToDms;
import io.dabing.redis.client.BinaryRedisClient;
import io.dabing.redis.util.RedisKeyUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-03-26 15:14
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Getter
@Setter
public class SendDmsClearProcessor implements ItemProcessor<AuthToDms,AuthToDms> {

    @Autowired
    private BinaryRedisClient redisClient;

    private static final String SEND_AUTH_DMS_CLEAR_BATCHNO_VIN = "WE:SEND:AUTH:DMS:CLEAR:{0}:{1}";

    @Override
    public AuthToDms process(AuthToDms authToDms) throws Exception {
        String key= RedisKeyUtils.build (SEND_AUTH_DMS_CLEAR_BATCHNO_VIN,authToDms.getBatchNo(),authToDms.getVin());
        AuthToDms authToDmsRedis = redisClient.get(key,AuthToDms.class);
        if(authToDmsRedis==null){
            redisClient.set(key,authToDms);
            return authToDms;
        }
        authToDms.setSendDmsStatus(AuthToDms.SEND_DMS_NOT);
        return authToDms;
    }

}
