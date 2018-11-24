package com.ford.wechat.service.message.impl;

import com.ford.wechat.entity.coupon.CouponDms;
import com.ford.wechat.entity.message.MessageSend;
import com.ford.wechat.respository.message.MessageSendRepository;
import com.ford.wechat.service.message.MessageSendService;
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
public class MessageSendServiceImpl extends AbstractService implements MessageSendService {

    @Autowired
    private MessageSendRepository repository;


    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    @Override
    public Page<MessageSend> pagingBy(GridPage page) {
        return repository.pagingBy(page);
    }

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    @Override
    public Page<MessageSend> pagingBy(String sendResult, String mobile, String projectCode, String openId, String vin, Date createStartDate, Date createEndDate, GridPage page) {
        return repository.pagingBy(sendResult, mobile, projectCode, openId, vin, createStartDate, createEndDate, page);
    }

    @Override
    public int countBy(String dateNo, String projectCode, String sendResult) {
        return this.repository.countBy(dateNo, projectCode, sendResult);
    }


}