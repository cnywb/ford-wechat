package com.ford.wechat.service.coupon.impl;

import com.alibaba.fastjson.JSON;
import com.ford.wechat.entity.coupon.CouponDms;
import com.ford.wechat.respository.coupon.CouponDmsRepository;
import com.ford.wechat.service.coupon.CouponDmsService;
import com.google.common.collect.Maps;
import io.dabing.common.grid.GridPage;
import io.dabing.common.util.HttpClientUtil;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import io.dabing.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Neel on 2017/8/27.
 */
@Service
public class CouponDmsServiceImpl extends AbstractService implements CouponDmsService {


    @Value("${dms.url}")
    private String dmsUrl;

    @Value("${dms.username}")
    private String username;

    @Value("${dms.password}")
    private String password;

    @Value("${dms.business_type}")
    private String businessType;

    @Value("${dms.zip}")
    private String zip;

    @Autowired
    private CouponDmsRepository repository;

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page  分页对象，里有关键字keyWord,供模糊匹配
     * @param seqNo
     * @return 分页结果数据对象集合
     */
    public Page<CouponDms> pagingBy(GridPage page, String seqNo) {
        return repository.pagingBy(page, seqNo);
    }

    public CouponDms get(Long id) {
        return repository.get(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(CouponDms object) {
        repository.save(object);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        this.repository.delete(id);
    }

    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void update(CouponDms object) {
        repository.update(object);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRES_NEW)
    public void updateForBatch(CouponDms object) {
        repository.update(object);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void update(List<CouponDms> orderDmsList) {
        repository.update(orderDmsList);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateSendDmsStatus(String sendDmsStatusSource, String sendDmsStatusTarget) {
        repository.updateSendDmsStatus(sendDmsStatusSource, sendDmsStatusTarget);
    }

    public List<CouponDms> findBySendDmsStatus(String sendDmsStatus, Date sendDate) {
        return repository.findBySendDmsStatus(sendDmsStatus, sendDate);
    }

    public List<CouponDms> findBySendDmsStatus(String sendDmsStatus) {
        return repository.findBySendDmsStatus(sendDmsStatus);
    }

    public List<CouponDms> findBySendDmsStatusAndDmsResultAndSendSmsStatusNot(String sendDmsStatus, String dmsResult, String sendSmsStatus) {
        return repository.findBySendDmsStatusAndDmsResultAndSendSmsStatusNot(sendDmsStatus, dmsResult, sendSmsStatus);
    }

    @Override
    public CouponDms findBySeqNo(String orderNo) {
        List<CouponDms> mmOrderDmsList = repository.findBySeqNo(orderNo);
        if (mmOrderDmsList == null || mmOrderDmsList.size() == 0) {
            return null;
        }
        return mmOrderDmsList.get(0);
    }


    @Override
    public String sendDmsCoupon(List<Map<String, String>> contentList) {

        Map<String, Object> map = Maps.newHashMap();
        map.put("user_name", username);
        map.put("password", password);
        map.put("business_type", businessType);
        map.put("zip", zip);
        map.put("content", contentList);
        String requestJson = JSON.toJSONString(map);
        log.info("发送DMS代金券发送请求：{}", requestJson);
        String responseMessage;
        try {
            responseMessage = HttpClientUtil.sendHTTP(dmsUrl, requestJson, null, "UTF-8", true);
            log.info("发送DMS代金券响应：{}", responseMessage);
        } catch (Exception e) {
            log.error("调用DMS服务异常！FC不做任何处理！{}", e);
            throw new BusinessException("DMS回传接口服务异常！FC不做任何处理！");
        }
        return responseMessage;
    }

    @Override
    public Page<CouponDms> pagingBy(String sendDmsStatus,String targetDealer, String batchNo, String vin, String customerMobile, String sendSms, String createStartDate, String createEndDate, GridPage page) {
        return repository.pagingBy(sendDmsStatus,targetDealer, batchNo, vin, customerMobile, sendSms, createStartDate, createEndDate, page);
    }
}
