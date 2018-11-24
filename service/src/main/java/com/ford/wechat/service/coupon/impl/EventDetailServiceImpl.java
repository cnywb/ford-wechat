package com.ford.wechat.service.coupon.impl;

import com.ford.wechat.entity.coupon.EventDetail;
import com.ford.wechat.respository.coupon.EventDetailRepository;
import com.ford.wechat.service.coupon.EventDetailService;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by zhaoliang on 2017/8/27.
 */

@Service
public class EventDetailServiceImpl extends AbstractService implements EventDetailService {


    @Autowired
    private EventDetailRepository repository;



    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    @Override
    public Page<EventDetail> pagingBy(GridPage page) {
        return repository.pagingBy(page);
    }


    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    @Override
    public Page<EventDetail> pagingBy(String projectCode, String createStartDate, String createEndDate, GridPage page) {
        return repository.pagingBy(projectCode, createStartDate, createEndDate, page);
    }

    /**
     * 保存
     *
     * @param object
     */
    @Override
    @CacheEvict(value = "EventDetail", allEntries = true, key = "'EventDetail:*'")
    public void save(EventDetail object) {
        repository.save(object);
    }

    /**
     * 批量删除
     *
     * @param objectList
     */
    @Override
    @CacheEvict(value = "EventDetail", allEntries = true, key = "'EventDetail:*'")
    public void delete(List<EventDetail> objectList) {
       repository.delete(objectList);
    }

    /**
     * 删除
     *
     * @param object
     */
    @Override
    @CacheEvict(value = "EventDetail", allEntries = true, key = "'EventDetail:*'")
    public void delete(EventDetail object) {
      repository.delete(object);
    }

    /**
     * 根据主键删除
     *
     * @param id
     */
    @Override
    @CacheEvict(value = "EventDetail", allEntries = true, key = "'EventDetail:*'")
    public void delete(Long id) {
      repository.delete(id);
    }

    /**
     * 更新
     *
     * @param object
     */
    @Override
    @CacheEvict(value = "EventDetail", allEntries = true, key = "'EventDetail:*'")
    public void update(EventDetail object) {
        repository.update(object);
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    @Override
    @Cacheable(key = "'EventDetail:get:'+#id", value = "EventDetail")
    public EventDetail get(Long id) {
        return repository.get(id);
    }

    @Override
    @Cacheable(key = "'EventDetail:getByDateNo:'+#dateNo", value = "EventDetail")
    public EventDetail getByDateNo(String dateNo) {
        return repository.getByDateNo(dateNo);
    }

    @Override
    @Cacheable(key = "'EventDetail:getByDateNoAndProjectCode:dateNo:'+#dateNo+':projectCode:'+#projectCode", value = "EventDetail")
    public EventDetail getByDateNoAndProjectCode(String dateNo, String projectCode) {
        return repository.getByDateNoAndProjectCode(dateNo, projectCode);
    }

    @Override
    @Cacheable(key = "'EventDetail:findBy:dateNo:'+#dateNo+':projectCode:'+#projectCode", value = "EventDetail")
    public List<EventDetail> findBy(String dateNo, String projectCode) {
        return repository.findBy(dateNo, projectCode);
    }

}
