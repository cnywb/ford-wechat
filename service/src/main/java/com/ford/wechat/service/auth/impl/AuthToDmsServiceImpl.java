package com.ford.wechat.service.auth.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ford.wechat.entity.auth.AuthToDms;
import com.ford.wechat.entity.dealer.ClubDealer;
import com.ford.wechat.entity.dealer.WeChannel;
import com.ford.wechat.entity.dict.FcDmsDictionary;
import com.ford.wechat.entity.repair.FordRepairWeb;
import com.ford.wechat.entity.user.*;
import com.ford.wechat.respository.auth.AuthToDmsRepository;
import com.ford.wechat.respository.dealer.ClubDealerRepository;
import com.ford.wechat.respository.dealer.WeChannelRepository;
import com.ford.wechat.respository.dict.FcDmsDictionaryRepository;
import com.ford.wechat.respository.repair.FordRepairWebRepository;
import com.ford.wechat.respository.user.DmsRepairOrderRepository;
import com.ford.wechat.respository.user.FcDmsToDataNewOwnersRepository;
import com.ford.wechat.respository.user.FcDmsToDataRepository;
import com.ford.wechat.respository.user.FordCarRepository;
import com.ford.wechat.service.auth.AuthToDmsService;
import com.ford.wechat.service.weixin.ParllayService;
import com.google.common.collect.Maps;
import io.dabing.common.grid.GridPage;
import io.dabing.common.util.HttpClientUtil;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import io.dabing.exception.BusinessException;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-05-19 16:06
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Service
public class AuthToDmsServiceImpl extends AbstractService implements AuthToDmsService {

    private static final String dmsUrl = "https://dms01.changanford.cn/DataReciveService";

    private static String WX_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={0}&openid={1}&lang=zh_CN";

    private static final Logger logger = LoggerFactory.getLogger(AuthToDmsServiceImpl.class);

    @Autowired
    private AuthToDmsRepository repository;
    @Autowired
    private FordCarRepository fordCarRepository;
    @Autowired
    private FordRepairWebRepository fordRepairWebRepository;
    @Autowired
    private ClubDealerRepository clubDealerRepository;
    @Autowired
    private DmsRepairOrderRepository dmsRepairOrderRepository;
    @Autowired
    private FcDmsToDataRepository fcDmsToDataRepository;
    @Autowired
    private FcDmsToDataNewOwnersRepository fcDmsToDataNewOwnersRepository;
    @Autowired
    private FcDmsDictionaryRepository fcDmsDictionaryRepository;
    @Autowired
    private WeChannelRepository weChannelRepository;
    @Autowired
    private ParllayService service;

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<AuthToDms> pagingBy(GridPage page) {
        return repository.pagingBy(page);
    }

    @Override
    public Page<AuthToDms> pagingBy(String batchNo, String vin, String openId, Integer channelType, Integer sendDmsStatus, Integer verify, Date createStartDate, Date createEndDate, GridPage page) {
        return repository.pagingBy(batchNo, vin, openId, channelType,sendDmsStatus,verify,createStartDate,createEndDate, page);
    }

    /**
     * 保存
     *
     * @param object
     */
    public void save(AuthToDms object) {
        repository.save(object);
    }

    /**
     * 批量删除
     *
     * @param objectList
     */
    public void delete(List<AuthToDms> objectList) {
        repository.delete(objectList);
    }

    /**
     * 删除
     *
     * @param object
     */
    public void delete(AuthToDms object) {
        repository.delete(object);
    }

    /**
     * 根据主键删除
     *
     * @param id
     */
    public void delete(Long id) {
        AuthToDms entity = this.repository.get(id);
        repository.delete(entity);
    }

    /**
     * 更新
     *
     * @param object
     */
    public void update(AuthToDms object) {
        repository.update(object);
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    public AuthToDms get(Long id) {
        return repository.get(id);
    }

    public String sendAuthToDms(List<Map<String, String>> contentList) {

        Map<String, Object> map = Maps.newHashMap();
        map.put("user_name", "wumderman");
        map.put("password", "123456");
        map.put("business_type", "WD02");
        map.put("zip", "1");
        map.put("content", contentList);
        String requestJson = JSON.toJSONString(map, SerializerFeature.WriteMapNullValue);
        logger.info("发送DMS数据发送请求：{}", requestJson);
        String responseMessage;
        try {
            responseMessage = HttpClientUtil.sendHTTP(dmsUrl, requestJson, null, "UTF-8", true);
            logger.info("发送DMS数据响应：{}", responseMessage);
        } catch (Exception e) {
            logger.error("调用DMS服务异常！AUTH不做任何处理！{}", e);
            throw new BusinessException("DMS回传接口服务异常！AUTH不做任何处理！");
        }
        return responseMessage;
    }

    /**
     * 获取经销商服务代码
     * @param vin
     * @return
     */
    public String getAuthToDms(String vin){
        //查询工单
        List<FordRepairWeb> fordRepairWebs = fordRepairWebRepository.findByVin(vin);
        for(FordRepairWeb fordRepairWeb: fordRepairWebs){
            if(!StringUtils.hasText(fordRepairWeb.getVsstId())){
                continue;
            }
            if(!StringUtils.hasLength(findServiceCode(fordRepairWeb.getVsstId()))){
                continue;
            }
            return fordRepairWeb.getVsstId();

        }
        //查询工单全量表
        List<DmsRepairOrder> dmsRepairOrders = dmsRepairOrderRepository.findByVin(vin);
        for(DmsRepairOrder dmsRepairOrder: dmsRepairOrders){
            if(!StringUtils.hasText(dmsRepairOrder.getDealerServiceCode())){
                continue;
            }
            if(!StringUtils.hasLength(findServiceCode(dmsRepairOrder.getDealerServiceCode()))){
                continue;
            }
            return dmsRepairOrder.getDealerServiceCode();

        }
        //查询全量车主
        List<FcDmsToData> fcDmsToDatas = fcDmsToDataRepository.findByVin(vin);
        for(FcDmsToData fcDmsToData: fcDmsToDatas){
            if(!StringUtils.hasText(fcDmsToData.getDealerServiceCode())){
                continue;
            }
            if(!StringUtils.hasLength(findServiceCode(fcDmsToData.getDealerServiceCode()))){
                continue;
            }
            return fcDmsToData.getDealerServiceCode();

        }
        //查询全量车主
        List<FcDmsToDataNewOwners> fcDmsToDataNewOwnerses = fcDmsToDataNewOwnersRepository.findByVin(vin);
        for(FcDmsToDataNewOwners fcDmsToDataNewOwners: fcDmsToDataNewOwnerses){
            if(!StringUtils.hasText(fcDmsToDataNewOwners.getDealerServiceCode())){
                continue;
            }
            if(!StringUtils.hasLength(findServiceCode(fcDmsToDataNewOwners.getDealerServiceCode()))){
                continue;
            }
            return fcDmsToDataNewOwners.getDealerServiceCode();

        }
        //查询经销商服务代码
        FordCar fordCar = fordCarRepository.findByVin(vin, 5);
        if(fordCar!=null){
            if(StringUtils.hasText(fordCar.getVdealerId())){
                if(StringUtils.hasLength(findServiceCode(fordCar.getVdealerId()))){
                    return fordCar.getVdealerId();
                }
            }
        }
        //查询经销商代码
        FordCar fordCar6 = fordCarRepository.findByVin(vin, 6);
        if(fordCar6!=null){
            if(StringUtils.hasText(fordCar6.getVdealerId())) {
                ClubDealer clubDealer = this.clubDealerRepository.findByDSaleCode(fordCar6.getVdealerId());
                if (clubDealer != null) {

                    return clubDealer.getSstCode();
                }
            }

        }
        return StringUtil.EMPTY_STRING;
    }

    public AuthToDms getAuthToDms(AuthToDms authToDms){
        //查询工单
        List<FordRepairWeb> fordRepairWebs = fordRepairWebRepository.findByVin(authToDms.getVin());
        for(FordRepairWeb fordRepairWeb: fordRepairWebs){
            if(!StringUtils.hasText(fordRepairWeb.getVsstId())){
                continue;
            }
            if(!findServiceCode(authToDms,fordRepairWeb.getVsstId())){
                continue;
            }
            return authToDms;

        }
        //查询工单全量表
        List<DmsRepairOrder> dmsRepairOrders = dmsRepairOrderRepository.findByVin(authToDms.getVin());
        for(DmsRepairOrder dmsRepairOrder: dmsRepairOrders){
            if(!StringUtils.hasText(dmsRepairOrder.getDealerServiceCode())){
                continue;
            }
            if(!findServiceCode(authToDms,dmsRepairOrder.getDealerServiceCode())){
                continue;
            }
            return authToDms;

        }
        //查询全量车主
        List<FcDmsToData> fcDmsToDatas = fcDmsToDataRepository.findByVin(authToDms.getVin());
        for(FcDmsToData fcDmsToData: fcDmsToDatas){
            if(!StringUtils.hasText(fcDmsToData.getDealerServiceCode())){
                continue;
            }
            if(!findServiceCode(authToDms,fcDmsToData.getDealerServiceCode())){
                continue;
            }
            return authToDms;

        }
        //查询全量车主
        List<FcDmsToDataNewOwners> fcDmsToDataNewOwnerses = fcDmsToDataNewOwnersRepository.findByVin(authToDms.getVin());
        for(FcDmsToDataNewOwners fcDmsToDataNewOwners: fcDmsToDataNewOwnerses){
            if(!StringUtils.hasText(fcDmsToDataNewOwners.getDealerServiceCode())){
                continue;
            }
            if(!findServiceCode(authToDms,fcDmsToDataNewOwners.getDealerServiceCode())){
                continue;
            }
            return authToDms;

        }
        //查询经销商服务代码
        FordCar fordCar = fordCarRepository.findByVin(authToDms.getVin(), 5);
        if(fordCar!=null){
            if(StringUtils.hasText(fordCar.getVdealerId())){
                if(findServiceCode(authToDms,fordCar.getVdealerId())){
                    return authToDms;
                }
            }
        }
        //查询经销商代码
        FordCar fordCar6 = fordCarRepository.findByVin(authToDms.getVin(), 6);
        if(fordCar6!=null){
            if(StringUtils.hasText(fordCar6.getVdealerId())) {
                ClubDealer clubDealer = this.clubDealerRepository.findByDSaleCode(fordCar6.getVdealerId());
                if (clubDealer != null) {
                    authToDms.setDealerServiceCode(clubDealer.getSstCode());
                    authToDms.setDealerName(clubDealer.getSstName());
                    authToDms.setBigArea(clubDealer.getDlaName());
                    return authToDms;
                }
            }

        }
        return authToDms;
    }

    /**
     * @param openId
     * @return
     */
    @Override
    public int subscribe(String openId) {
        String accessToken = service.getAccessToken();

        String url = MessageFormat.format(WX_URL,accessToken,openId);
        System.out.println(url);
        Subscribe subscribe=null;
        try {
            String result = HttpClientUtil.get(url, 30000, 30000, "UTF-8");
            System.out.println(result);
            subscribe = JSON.parseObject (result, Subscribe.class);
        }catch (IOException e){

        }

        if(subscribe!=null&&"1".equals (subscribe.getFlag ())){

            return AuthToDms.FOLLOW_FOLLOW;
        }
        return AuthToDms.FOLLOW_NOT_FOLLOW;
    }

    /**
     * 查找经销商代码
     * @param code
     * @return
     */
    private String findServiceCode(String code){
        ClubDealer clubDealer = this.clubDealerRepository.findByServiceCode(code);
        if(clubDealer==null){
            FcDmsDictionary dmsDictionary = fcDmsDictionaryRepository.findByCode(code);
            if(dmsDictionary==null){
                WeChannel channel = weChannelRepository.getByDealerServiceCode(code);
                if(channel==null){
                    return StringUtil.EMPTY_STRING;
                }

                return channel.getDealerServiceCode();
            }

            return code;
        }
        return clubDealer.getSstCode();
    }

    /**
     * 寻找
     * @param authToDms
     * @param code
     * @return
     */
    private Boolean findServiceCode(AuthToDms authToDms,String code){
        ClubDealer clubDealer = this.clubDealerRepository.findByServiceCode(code);
        if(clubDealer==null){
            FcDmsDictionary dmsDictionary = fcDmsDictionaryRepository.findByCode(code);
            if(dmsDictionary==null){
                WeChannel channel = weChannelRepository.getByDealerServiceCode(code);
                if(channel==null){
                    return Boolean.FALSE;
                }
                authToDms.setDealerServiceCode(channel.getDealerServiceCode());
                authToDms.setDealerName(channel.getName());
                authToDms.setBigArea(channel.getBigArea());
                return Boolean.TRUE;
            }
            authToDms.setDealerServiceCode(code);
            authToDms.setDealerName(dmsDictionary.getCodeName());
            return Boolean.TRUE;
        }
        authToDms.setDealerServiceCode(clubDealer.getSstCode());
        authToDms.setDealerName(clubDealer.getSstName());
        authToDms.setBigArea(clubDealer.getDlaName());
        return Boolean.TRUE;
    }
}
