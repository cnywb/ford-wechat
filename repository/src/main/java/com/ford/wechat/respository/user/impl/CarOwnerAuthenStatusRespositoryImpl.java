package com.ford.wechat.respository.user.impl;

import com.ford.wechat.entity.user.CarOwnerAuthenStatus;
import com.ford.wechat.respository.user.CarOwnerAuthenStatusRespository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by wanglijun on 16/11/25.
 */
@Repository
public class CarOwnerAuthenStatusRespositoryImpl extends DefaultJpaRepository<CarOwnerAuthenStatus, Long> implements CarOwnerAuthenStatusRespository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    @Override
    public Page<CarOwnerAuthenStatus> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery()
                .query("from CarOwnerAuthenStatus")
                .predicateNotNull(page.getKeyWord())
                .query(" and name like :name")
               /* .likeParam("name", page.getKeyWord())
                .predicate(Boolean.TRUE)
                .query(" and deleted = false").build()*/
                .query(" order by id desc").build();

        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }


    @Override
    public List<CarOwnerAuthenStatus> findProcessingBy(String openId) {
        StringQuery query = StringQuery.newQuery()
                .query("from CarOwnerAuthenStatus where ")
                .query(" openId = :openId")
                .param("openId", openId)
                .query(" and authState <> 1 ")
                .query(" order by id desc").build();

        return find(query);
    }

    @Override
    public List<CarOwnerAuthenStatus> findByUserVinAndOpenId(String userVin, String openId) {
        StringQuery query = StringQuery.newQuery ()
                .query("from CarOwnerAuthenStatus where 1 = 1 ")

                .predicateHasText (userVin)
                .query (" and userVin like :userVin")
                .likeParam ("userVin", userVin)

                .predicateHasText (openId)
                .query (" and openId like :openId")
                .likeParam ("openId", openId)

                .predicate (Boolean.TRUE).build();

        return find(query);
    }

    @Override
    public CarOwnerAuthenStatus findByUserVin(String userVin) {
        StringQuery query = StringQuery.newQuery()
                .query("from CarOwnerAuthenStatus where ")
                .query(" userVin = :userVin")
                .param("userVin", userVin)
                .query(" order by createDate desc").build();

        List<CarOwnerAuthenStatus> list = find(query);
        if (list.isEmpty()) return null;
        return list.get(0);
    }


    @Override
    public Page<CarOwnerAuthenStatus> pagingBy(String userVin, String openId, String userMobile, Long authState, GridPage page) {
        StringQuery query = StringQuery.newQuery ()
                .query("from CarOwnerAuthenStatus where 1 = 1 ")

                .predicateHasText (userVin)
                .query (" and userVin like :userVin")
                .likeParam ("userVin", userVin)

                .predicateHasText (openId)
                .query (" and openId like :openId")
                .likeParam ("openId", openId)

                .predicateHasText (userMobile)
                .query (" and userMobile like :userMobile")
                .likeParam ("userMobile", userMobile)

                .predicateNotNull (authState)
                .query (" and authState = :authState")
                .param ("authState", authState)

                .predicate (Boolean.TRUE)
                .query (" order by createDate desc,userVin desc").build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }

}
