package com.ford.wechat.respository.message.impl;

import com.ford.wechat.date.DateUtils;
import com.ford.wechat.entity.message.MessageSend;
import com.ford.wechat.respository.message.MessageSendRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoliang on 2017/8/27.
 */
@Repository
public class MessageSendRepositoryImpl extends DefaultJpaRepository<MessageSend, Long> implements MessageSendRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    @Override
    public Page<MessageSend> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery()
                .query("from MessageSend where 1 = 1 ")
                .predicateNotNull(page.getKeyWord())
                .query(" and projectName like :projectName")
                .likeParam("projectName", page.getKeyWord())
                .predicate(Boolean.TRUE)
                .query(" order by projectName desc").build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }

    @Override
    public Page<MessageSend> pagingBy(String sendResult, String mobile, String projectCode, String openId, String vin, Date createStartDate, Date createEndDate, GridPage page) {
        StringQuery query = StringQuery.newQuery ()
                .query("from MessageSend where 1 = 1 ")

                .predicateHasText (sendResult)
                .query (" and sendResult like :sendResult")
                .likeParam ("sendResult", sendResult)

                .predicateHasText (mobile)
                .query (" and mobile like :mobile")
                .likeParam ("mobile", mobile)

                .predicateHasText (projectCode)
                .query (" and projectCode like :projectCode")
                .likeParam ("projectCode", projectCode)

                .predicateHasText (openId)
                .query (" and openId = :openId")
                .param ("openId", openId)

                .predicateHasText (vin)
                .query (" and vin like :vin")
                .likeParam ("vin", vin)

                .predicateNotNull (createStartDate)
                .query(" and  sendTime  >=  :createStartDate")
                .param ("createStartDate", createStartDate)

                .predicateNotNull (createEndDate)
                .query(" and  sendTime  <=  :createEndDate")
                .param ("createEndDate", DateUtils.formatEndDate(createEndDate))

                .predicate (Boolean.TRUE)
                .query (" order by vin desc").build ();
        return find (query, PageRequest.newPage (page.getPageNumber (), page.getPageSize ()));
    }


    @Override
    public int countBy(String dateNo, String projectCode, String sendResult) {
        StringQuery query = StringQuery.newQuery()
                .query("select count(*) as count from MessageSend a where 1 = 1 ")
                .predicateNotNull(dateNo)
                .query(" and a.dateNo = :dateNo")
                .param("dateNo", dateNo)
                .predicateNotNull(projectCode)
                .query(" and a.projectCode = :projectCode")
                .param("projectCode", projectCode)
                .predicateNotNull(sendResult)
                .query(" and a.sendResult = :sendResult")
                .param("sendResult", sendResult)
                .build();
        List<Object> list = findObject(query);
        if (list.isEmpty()) return 0;

        return Integer.parseInt(list.get(0).toString());
    }
}

