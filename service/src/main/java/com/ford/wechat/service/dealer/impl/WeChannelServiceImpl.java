package com.ford.wechat.service.dealer.impl;

import com.ford.wechat.entity.dealer.WeChannel;
import com.ford.wechat.respository.dealer.WeChannelRepository;
import com.ford.wechat.service.dealer.WeChannelService;
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
 * All rights reserved. 2017-03-31 20:33
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Service
public class WeChannelServiceImpl extends AbstractService implements WeChannelService {
    @Autowired
    private WeChannelRepository repository;

    @Autowired
    private RedisClient redisClient;

    /**渠道商*/
    public static final String REDIS_CHANNEL_CODE="point:dealer:code:{0}";


    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<WeChannel> pagingBy(GridPage page) {
        return repository.pagingBy(page);
    }

    /**
     * 保存
     *
     * @param object
     */
    public void save(WeChannel object) {
        repository.save(object);
    }

    /**
     * 批量删除
     *
     * @param objectList
     */
    public void delete(List<WeChannel> objectList) {
        repository.delete(objectList);
    }

    /**
     * 删除
     *
     * @param object
     */
    public void delete(WeChannel object) {
        object.setDeleted(Boolean.TRUE);
        this.update(object);
    }

    /**
     * 根据主键删除
     *
     * @param id
     */
    public void delete(Long id) {
        WeChannel entity = this.repository.get(id);

        String key= RedisKeyUtils.build (REDIS_CHANNEL_CODE,entity.getDealerCode());
        redisClient.del(key);
        //entity.setDeleted(Boolean.TRUE);
        //this.update(entity);
        this.repository.delete(entity);
    }

    /**
     * 更新
     *
     * @param object
     */
    public void update(WeChannel object) {
        repository.update(object);
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    public WeChannel get(Long id) {
        return repository.get(id);
    }

    /**
     * 根据渠道代码查询
     *
     * @param code
     * @return
     */
    @Override
    public WeChannel getByCode(String code) {
        return repository.getByCode(code);
    }

    /**
     * 根据经销商查询
     *
     * @param dealerCode@return
     */
    @Override
    public WeChannel getByDealerCode(String dealerCode) {
        return repository.getByDealerCode(dealerCode);
    }

    @Override
    public Page<WeChannel> pagingBy(String dealerCode, String code, String name, Integer type, GridPage page) {
        return repository.pagingBy(dealerCode, code, name, type, page);
    }
}
