package com.ford.wechat.service.coupon.impl;

import com.ford.wechat.entity.coupon.Event;
import com.ford.wechat.respository.coupon.EventRepository;
import com.ford.wechat.service.coupon.EventService;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * Created by zhaoliang on 2017/8/27.
 */
@Service
public class EventServiceImpl extends AbstractService implements EventService {

    @Autowired
    private EventRepository repository;


    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    @Override
    public Page<Event> pagingBy(GridPage page) {
        return repository.pagingBy(page);
    }

    @Override
    @Cacheable(key = "'Event:findBy:'+#projectCode", value = "Event")
    public Event findBy(String projectCode) {
        return this.repository.findBy(projectCode);
    }

    @Override
    public List<Event> findAll() {
        return repository.getAll();
    }

    @Override
    @Cacheable(key = "'Event:get:'+#id", value = "Event")
    public Event get(Long id) {
        return repository.get(id);
    }

    @Override
    @Cacheable(key = "'Event:findByDate:'+#date", cacheNames = "Event")
    public Event findByDate(String date) {
        return this.repository.findByDate(date);
    }

    /**
     * 判断活动日期是否在活动时间内
     *
     * @param projectCode
     * @param dateNo
     * @return
     */
    @Override
    public void compareDateNo(String projectCode, String dateNo) {
        if(projectCode != null){
            Event event = repository.findBy(projectCode);
            String createStartDate = null;
            String createEndDate = null;
            if(event.getEndTime() != null && event.getStartTime() != null){
                createStartDate = event.getStartTime().replaceAll("-","");
                createEndDate = event.getEndTime().replaceAll("-","");
            }
            if(dateNo.compareTo(createStartDate)< 0){
                Assert.isNull(event, "活动时间应大于："+ event.getStartTime());
            }
            if(dateNo.compareTo(createEndDate)> 0){
                Assert.isNull(event, "活动时间应小于："+ event.getEndTime());
            }
        }
    }
}
