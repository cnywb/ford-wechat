package com.ford.wechat.service.coupon.impl;

import com.ford.wechat.entity.coupon.EventStatistics;
import com.ford.wechat.respository.coupon.EventStatisticsRepository;
import com.ford.wechat.service.coupon.EventStatisticsService;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhaoliang on 2017/8/27.
 */
@Service
public class EventStatisticsServiceImpl extends AbstractService implements EventStatisticsService {

    @Autowired
    private EventStatisticsRepository repository;


    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    @Override
    public Page<EventStatistics> pagingBy(GridPage page) {
        return repository.pagingBy(page);
    }

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    @Override
    public Page<EventStatistics> pagingBy(String projectCode, String createStartDate, String createEndDate, GridPage page) {
        return repository.pagingBy(projectCode, createStartDate, createEndDate, page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void save(EventStatistics object) {
        this.repository.save(object);
    }
}
