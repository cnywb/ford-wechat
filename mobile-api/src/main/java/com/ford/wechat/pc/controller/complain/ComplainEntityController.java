/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * ComplainEntityController.java
 */
package com.ford.wechat.pc.controller.complain;

import com.ford.wechat.entity.pc.complain.ComplainEntity;
import com.ford.wechat.pc.common.response.Response;
import com.ford.wechat.pc.controller.complain.vo.ComplainReq;
import com.ford.wechat.service.pc.complain.ComplainEntityService;
import io.dabing.common.Assert;
import io.dabing.common.util.DateUtils;
import io.dabing.redis.client.BinaryRedisClient;
import io.dabing.redis.client.RedisClient;
import io.dabing.redis.util.RedisKeyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;


/**
 * 描述:ComplainEntityController CRUD 逻辑控制层
 *
 * @author ziv
 * @since 1.0
 */
@Controller
@ResponseBody
@RequestMapping("/api/public/complain")
public class ComplainEntityController {

    private static final String COMPLAIN_APPLY_KEY = "PC:COMPLAIN:APPLY:{0}:{1}";

    @Autowired
    private BinaryRedisClient binaryRedisClient;
    @Autowired
    private ComplainEntityService service;
    @Autowired
    private RedisClient redisClient;

    @RequestMapping("/apply")
    public Response<Void> apply(@RequestBody ComplainReq req) {
        Assert.notNull(req.getOpenId(), "请使用微信");
        String applyKey = RedisKeyUtils.build(COMPLAIN_APPLY_KEY, req.getOpenId(), DateUtils.format(new Date(), DateUtils.FORMAT_DATE_YYYYMMDD));
        Long times = binaryRedisClient.get(applyKey, Long.class);
        if (times == null || times < 10) {
            synchronized (redisClient) {
                redisClient.incr(applyKey);
            }
        } else if (times > 9) {
            Assert.isTrue(false, "当天投诉已超限");
        }
        ComplainEntity entity = new ComplainEntity();
        BeanUtils.copyProperties(req, entity, new String[]{"id"});
        entity.setSendMail(ComplainEntity.SEND_MAIL_WAIT);
        service.save(entity);
        return new Response<>();
    }
}