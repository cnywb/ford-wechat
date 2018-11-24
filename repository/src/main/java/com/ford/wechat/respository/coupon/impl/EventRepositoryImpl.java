package com.ford.wechat.respository.coupon.impl;

import com.ford.wechat.entity.coupon.Event;
import com.ford.wechat.respository.coupon.EventRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Neel on 2017/8/27.
 */
@Repository
public class EventRepositoryImpl extends DefaultJpaRepository<Event, Long> implements EventRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    @Override
    public Page<Event> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery()
                .query("from Event where 1 = 1 ")
                .predicateNotNull(page.getKeyWord())
                .query(" and projectName like :projectName")
                .likeParam("projectName", page.getKeyWord())
                .predicate(Boolean.TRUE)
                .query(" order by dateNo desc").build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }


    @Override
    public Event findBy(String projectCode) {
        StringQuery query = StringQuery.newQuery()
                .query("from Event where 1 = 1 ")
                .predicateNotNull(projectCode)
                .query(" and projectCode = :projectCode")
                .param("projectCode", projectCode)
                .build();
        List<Event> list = find(query);
        if (list.isEmpty()) return null;
        return list.get(0);
    }

    @Override
    public Event findByDate(String date) {
        StringQuery query = StringQuery.newQuery()
                .query("from Event where 1 = 1 ")
                .predicateNotNull(date)
                .query(" and to_date(startTime, 'yyyy-MM-dd') <= to_date(:startTime, 'yyyy-MM-dd')")
                .param("startTime", date)
                .query(" and to_date(endTime, 'yyyy-MM-dd') >= to_date(:endTime, 'yyyy-MM-dd')")
                .param("endTime", date)
                .build();
        List<Event> list = find(query);
        if (list.isEmpty()) return null;
        return list.get(0);
    }

    @Override
    public List<Event> getAll() {
        StringQuery query = StringQuery.newQuery()
                .query("from Event where 1 = 1 ")
                .build();
        return find(query);
    }

}
