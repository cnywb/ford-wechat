package com.ford.wechat.service.message;

import com.ford.wechat.entity.message.MessageSend;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;

import java.util.Date;

/**
 * Created by zhaoliang on 2017/8/27.
 */
public interface MessageSendService {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<MessageSend> pagingBy(GridPage page);

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<MessageSend> pagingBy(String sendResult, String mobile, String projectCode, String openId, String vin, Date createStartDate, Date createEndDate, GridPage page);

    int countBy(String dateNo, String projectCode, String sendResult);
}
