package com.ford.wechat.service.coupon;

import com.ford.wechat.entity.coupon.EventOperationRecord;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;

import java.util.Date;

/**
 * Created by zhaoliang on 2017/8/27.
 */
public interface EventOperationRecordService {
    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<EventOperationRecord> pagingBy(GridPage page);

    int countBy(String dateNo, String projectCode, Integer operationType);

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<EventOperationRecord> pagingBy(String projectCode, String openId, Integer operationType, String createStartDate, String createEndDate, GridPage page);

}
