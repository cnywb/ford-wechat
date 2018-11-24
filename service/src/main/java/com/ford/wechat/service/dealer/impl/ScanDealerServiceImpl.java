package com.ford.wechat.service.dealer.impl;

import com.ford.wechat.entity.dealer.ScanDealer;
import com.ford.wechat.respository.dealer.ScanDealerRepository;
import com.ford.wechat.service.dealer.ScanDealerService;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import io.dabing.redis.client.RedisClient;
import io.dabing.redis.util.RedisKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-02-17 11:12
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Service
public class ScanDealerServiceImpl extends AbstractService implements ScanDealerService {
    @Autowired
    private ScanDealerRepository repository;
    @Autowired
    private RedisClient redisClient;

    /**经销商*/
    public static final String REDIS_DEALER_CODE="point:dealer:code:{0}";

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<ScanDealer> pagingBy(GridPage page) {
        return repository.pagingBy(page);
    }

    /**
     * 保存
     *
     * @param object
     */
    public void save(ScanDealer object) {
        repository.save(object);
    }

    /**
     * 批量删除
     *
     * @param objectList
     */
    public void delete(List<ScanDealer> objectList) {
        for(ScanDealer dealer:objectList){
            String key= RedisKeyUtils.build (REDIS_DEALER_CODE,dealer.getSstCode());
            redisClient.del(key);
        }
        repository.delete(objectList);
    }

    /**
     * 删除
     *
     * @param object
     */
    public void delete(ScanDealer object) {
        String key= RedisKeyUtils.build (REDIS_DEALER_CODE,object.getSstCode());
        redisClient.del(key);
       this.repository.delete(object);
    }

    /**
     * 根据主键删除
     *
     * @param id
     */
    public void delete(Long id) {
        ScanDealer entity = this.repository.get(id);
        String key= RedisKeyUtils.build (REDIS_DEALER_CODE,entity.getSstCode());
        redisClient.del(key);
        this.repository.delete(entity);
    }

    /**
     * 更新
     *
     * @param object
     */
    public void update(ScanDealer object) {
        String key= RedisKeyUtils.build (REDIS_DEALER_CODE,object.getSstCode());
        redisClient.del(key);
        repository.update(object);
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    public ScanDealer get(Long id) {
        return repository.get(id);
    }
}
