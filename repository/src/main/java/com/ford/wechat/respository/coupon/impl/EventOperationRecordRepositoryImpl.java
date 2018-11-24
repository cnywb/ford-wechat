package com.ford.wechat.respository.coupon.impl;

import com.ford.wechat.entity.coupon.EventOperationRecord;
import com.ford.wechat.respository.coupon.EventOperationRecordRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * Created by zhaoliang on 2017/8/27.
 */
@Repository
public class EventOperationRecordRepositoryImpl extends DefaultJpaRepository<EventOperationRecord, Long> implements EventOperationRecordRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    @Override
    public Page<EventOperationRecord> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery()
                .query("from EventOperationRecord where 1 = 1 ")
                .predicateNotNull(page.getKeyWord())
                .query(" and projectName like :projectName")
                .likeParam("projectName", page.getKeyWord())
                .predicate(Boolean.TRUE)
                .query(" order by projectName desc").build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }


    @Override
    public int countBy(String dateNo, String projectCode, Integer operationType) {
        StringQuery query = StringQuery.newQuery()
                .query("select count(*) as count from EventOperationRecord a where 1 = 1 ")
                .predicateNotNull(dateNo)
                .query(" and a.dateNo = :dateNo")
                .param("dateNo", dateNo)
                .predicateNotNull(projectCode)
                .query(" and a.projectCode = :projectCode")
                .param("projectCode", projectCode)
                .predicateNotNull(operationType)
                .query(" and a.operationType = :operationType")
                .param("operationType", operationType)
                .build();
        List<Object> list = findObject(query);
        if (list.isEmpty()) return 0;

        return Integer.parseInt(list.get(0).toString());
    }

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    @Override
    public Page<EventOperationRecord> pagingBy(String projectCode, String openId, Integer operationType, String createStartDate, String createEndDate, GridPage page) {
        StringQuery query = StringQuery.newQuery ()
                .query("from EventOperationRecord where 1 = 1 ")

                .predicateHasText (projectCode)
                .query (" and projectCode like :projectCode")
                .likeParam ("projectCode", projectCode)

                .predicateHasText (openId)
                .query (" and openId like :openId")
                .likeParam ("openId", openId)

                .predicateNotNull (operationType)
                .query (" and operationType = :operationType")
                .param ("operationType", operationType)

                .predicateHasText (createStartDate)
                .query(" and  dateNo  >=  :createStartDate")
                .param ("createStartDate", createStartDate)

                .predicateHasText (createEndDate)
                .query(" and  dateNo  <=  :createEndDate")
                .param ("createEndDate", createEndDate)

                .predicate (Boolean.TRUE)
                .query (" order by openId desc").build ();
        return find (query, PageRequest.newPage (page.getPageNumber (), page.getPageSize ()));
    }
}
