package com.ford.wechat.respository.coupon.impl;

import com.ford.wechat.entity.coupon.EventDetail;
import com.ford.wechat.respository.coupon.EventDetailRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by zhaoliang on 2017/8/27.
 */
@Repository
public class EventDetailRepositoryImpl extends DefaultJpaRepository<EventDetail, Long> implements EventDetailRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    @Override
    public Page<EventDetail> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery()
                .query("from EventDetail where 1 = 1 ")
                .predicateNotNull(page.getKeyWord())
                .query(" and projectName like :projectName")
                .likeParam("projectName", page.getKeyWord())
                .predicate(Boolean.TRUE)
                .query(" order by dateNo desc").build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }

    @Override
    public Page<EventDetail> pagingBy(String projectCode, String createStartDate, String createEndDate, GridPage page) {
        StringQuery query = StringQuery.newQuery ()
                .query("from EventDetail where 1 = 1 ")

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
                .query (" order by dateNo desc").build ();
        return find (query, PageRequest.newPage (page.getPageNumber (), page.getPageSize ()));
    }

    @Override
    public EventDetail getByDateNo(String dateNo) {
        Assert.hasText(dateNo);
        StringQuery query = StringQuery.newQuery()
                .query("from EventDetail where 1 = 1 ")
                .predicateNotNull(dateNo)
                .query(" and dateNo = :dateNo")
                .param("dateNo", dateNo)
                .build();
        List<EventDetail> list = find(query);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }



    @Override
    public EventDetail getByDateNoAndProjectCode(String dateNo, String projectCode) {
        Assert.hasText(dateNo);
        StringQuery query = StringQuery.newQuery()
                .query("from EventDetail where 1 = 1 ")
                .predicateNotNull(dateNo)
                .query(" and dateNo = :dateNo")
                .param("dateNo", dateNo)
                .predicateNotNull(projectCode)
                .query(" and projectCode = :projectCode")
                .param("projectCode", projectCode)
                .build();
        List<EventDetail> list = find(query);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }



    @Override
    public List<EventDetail> findBy(String dateNo, String projectCode) {
        Assert.hasText(dateNo);
        StringQuery query = StringQuery.newQuery()
                .query("from EventDetail where 1 = 1 ")
                .predicateNotNull(dateNo)
                .query(" and dateNo = :dateNo")
                .param("dateNo", dateNo)
                .predicateNotNull(projectCode)
                .query(" and projectCode = :projectCode")
                .param("projectCode", projectCode)
                .build();
        return find(query);
    }
}
