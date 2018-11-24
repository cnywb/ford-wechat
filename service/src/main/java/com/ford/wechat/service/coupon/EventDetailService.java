package com.ford.wechat.service.coupon;

import com.ford.wechat.entity.coupon.EventDetail;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;

import java.util.List;

/**
 * Created by zhaoliang on 2017/8/27.
 */
public interface EventDetailService {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<EventDetail> pagingBy(GridPage page);

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<EventDetail> pagingBy(String projectCode, String createStartDate, String createEndDate, GridPage page);


    /**
     * 保存
     *
     * @param object
     */
    void save(EventDetail object);

    /**
     * 批量删除
     *
     * @param objectList
     */
    void delete(List<EventDetail> objectList);

    /**
     * 删除
     *
     * @param object
     */
    void delete(EventDetail object);

    /**
     * 根据主键删除
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 更新
     *
     * @param object
     */
    void update(EventDetail object);

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    EventDetail get(Long id);

    /**
     * 根据时间批次查询
     *
     * @param dateNo
     * @return
     */
    EventDetail getByDateNo(String dateNo);

    EventDetail getByDateNoAndProjectCode(String dateNo, String projectCode);

    List<EventDetail> findBy(String dateNo, String projectCode);
}
