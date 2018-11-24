package com.ford.wechat.service.coupon;

import com.ford.wechat.entity.coupon.Event;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * Created by zhaoliang on 2017/8/27.
 */
public interface EventService {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<Event> pagingBy(GridPage page);

    Event findBy(String projectCode);

    /**
     * 查询所有数据
     *
     * @return
     */
    List<Event> findAll();

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    Event get(Long id);

    Event findByDate(String date);

    /**
     * 判断活动日期是否在活动时间内
     *
     * @param projectCode
     * @param dateNo
     * @return
     */
    void compareDateNo(String projectCode,String dateNo);
}
