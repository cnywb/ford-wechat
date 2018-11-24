package com.ford.wechat.respository.coupon.impl;

import com.ford.wechat.entity.coupon.EventStatistics;
import com.ford.wechat.respository.coupon.EventStatisticsRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

/**
 * Created by zhaoliang on 2017/8/27.
 */
@Repository
public class EventStatisticsRepositoryImpl extends DefaultJpaRepository<EventStatistics, Long> implements EventStatisticsRepository {

        /**
         * 根据GridPage对象按分页查找服务
         *
         * @param page 分页对象，里有关键字keyWord,供模糊匹配
         * @return 分页结果数据对象集合
         */
        @Override
        public Page<EventStatistics> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery()
                .query("from EventStatistics where 1 = 1 ")
                .predicateNotNull(page.getKeyWord())
                .query(" and projectName like :projectName")
                .likeParam("projectName", page.getKeyWord())
                .predicate(Boolean.TRUE)
                .query(" order by projectName desc").build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }

        /**
         * 根据GridPage对象按分页查找服务
         *
         * @param page 分页对象，里有关键字keyWord,供模糊匹配
         * @return 分页结果数据对象集合
         */
        @Override
        public Page<EventStatistics> pagingBy(String projectCode, String createStartDate, String createEndDate, GridPage page) {
                StringQuery query = StringQuery.newQuery ()
                        .query("from EventStatistics where 1 = 1 ")

                        .predicateHasText (projectCode)
                        .query (" and projectCode like :projectCode")
                        .likeParam ("projectCode", projectCode)

                        .predicateHasText (createStartDate)
                        .query(" and  dateNo  >=  :createStartDate")
                        .param ("createStartDate", createStartDate)

                        .predicateHasText (createEndDate)
                        .query(" and  dateNo  <=  :createEndDate")
                        .param ("createEndDate", createEndDate)

                        .predicate (Boolean.TRUE)
                        .query (" order by projectCode desc").build ();
                return find (query, PageRequest.newPage (page.getPageNumber (), page.getPageSize ()));
        }

}
