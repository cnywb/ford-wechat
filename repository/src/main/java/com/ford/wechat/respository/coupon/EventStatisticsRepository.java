package com.ford.wechat.respository.coupon;

import com.ford.wechat.entity.coupon.EventStatistics;
import com.ford.wechat.entity.message.MessageSend;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;

import java.util.Date;

/**
 * Created by zhaoliang on 2017/8/27.
 */
public interface EventStatisticsRepository extends JpaRepository<EventStatistics, Long> {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<EventStatistics> pagingBy(GridPage page);

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<EventStatistics> pagingBy(String projectCode, String createStartDate, String createEndDate, GridPage page);

}
