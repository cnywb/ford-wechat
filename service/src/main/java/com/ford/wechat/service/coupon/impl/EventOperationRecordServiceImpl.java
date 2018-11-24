package com.ford.wechat.service.coupon.impl;

import com.ford.wechat.entity.coupon.EventOperationRecord;
import com.ford.wechat.respository.coupon.EventOperationRecordRepository;
import com.ford.wechat.service.coupon.EventOperationRecordService;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by zhaoliang on 2017/8/27.
 */
@Service
public class EventOperationRecordServiceImpl extends AbstractService implements EventOperationRecordService {

    @Autowired
    private EventOperationRecordRepository repository;


    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    @Override
    public Page<EventOperationRecord> pagingBy(GridPage page) {
        return repository.pagingBy(page);
    }

    @Override
    public int countBy(String dateNo, String projectCode, Integer operationType) {
        return this.repository.countBy(dateNo, projectCode, operationType);
    }

    @Override
    public Page<EventOperationRecord> pagingBy(String projectCode, String openId, Integer operationType, String createStartDate, String createEndDate, GridPage page) {
        return repository.pagingBy(projectCode, openId, operationType, createStartDate, createEndDate, page);
    }
}
