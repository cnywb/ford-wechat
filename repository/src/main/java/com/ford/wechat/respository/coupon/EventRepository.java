package com.ford.wechat.respository.coupon;

import com.ford.wechat.entity.coupon.Event;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;

import java.util.Date;
import java.util.List;


/**
 * Created by Neel on 2017/8/27.
 */
public interface EventRepository extends JpaRepository<Event, Long> {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<Event> pagingBy(GridPage page);

    Event findBy(String projectCode);

    Event findByDate(String date);

    /**
     * 查询所有数据
     *
     * @return
     */
    List<Event> getAll();

}
