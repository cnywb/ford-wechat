package com.ford.wechat.batch.item.processor.auto.auth;/**
 * Created by jovi on 25/05/2017.
 */

import com.ford.wechat.entity.factory.WeFactoryForm;
import com.ford.wechat.entity.member.FordMemberForm;
import com.ford.wechat.entity.member.WeAssessLog;
import com.ford.wechat.entity.user.CarOwnerAuthenStatus;
import com.ford.wechat.entity.user.CarOwnerAuthenStatusResult;
import com.ford.wechat.entity.user.FordClubMember;
import com.ford.wechat.entity.user.JoUser;
import com.ford.wechat.mobile.MobileUtils;
import com.ford.wechat.service.bind.BindService;
import com.ford.wechat.service.factory.WeFactoryFormService;
import com.ford.wechat.service.members.FordMemberFormService;
import com.ford.wechat.service.members.WeAssessLogService;
import com.ford.wechat.service.user.FordClubMemberService;
import com.ford.wechat.service.user.JoUserService;
import com.ford.wechat.vin.VinUtils;
import io.dabing.common.util.ValidateUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-05-25 20:06
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Getter
@Setter
public class AutoAuthProcessor implements ItemProcessor<CarOwnerAuthenStatus,CarOwnerAuthenStatus> {

    public static final String SOURCE = "自动认证";
    /**
     * 批次号
     */
    private String batchNo;
    /**
     * 日期批次
     */
    private String dateNo;

    @Autowired
    private FordClubMemberService fordClubMemberService;
    @Autowired
    private FordMemberFormService fordMemberFormService;
    @Autowired
    private JoUserService joUserService;
    @Autowired
    private WeFactoryFormService weFactoryFormService;
    @Autowired
    private BindService bindService;
    @Autowired
    private WeAssessLogService assessLogService;


    @Override
    public CarOwnerAuthenStatus process(CarOwnerAuthenStatus carOwnerAuthenStatus) throws Exception {

        //添加自动审核次数
        carOwnerAuthenStatus.setTimes(carOwnerAuthenStatus.getTimes()+1);

        try{
            //验证手机号
            String mobile = carOwnerAuthenStatus.getUserMobile();
            if(!(StringUtils.hasText(mobile) && ValidateUtils.isMobileNo(mobile))){
                carOwnerAuthenStatus.setAuthDate(new Date());
                carOwnerAuthenStatus.setAuthState(CarOwnerAuthenStatus.AUTH_STATE_AUDITED);
                carOwnerAuthenStatus.setAuthWay(CarOwnerAuthenStatus.AUTH_WAY_AUTO);
                carOwnerAuthenStatus.setAuthResult(CarOwnerAuthenStatus.AUTH_RESULT_FAIL);
                carOwnerAuthenStatus.setAuthWay(CarOwnerAuthenStatus.AUTH_WAY_AUTO);
                carOwnerAuthenStatus.setRemark(CarOwnerAuthenStatusResult.getReason(CarOwnerAuthenStatusResult.MOBILE_NOT_VALID));

                createAssessLog(carOwnerAuthenStatus);
                return carOwnerAuthenStatus;
            }else{
                //验证手机号是否存在
                JoUser userMobile = joUserService.getByMobile(mobile);
                if(userMobile!=null){
                    carOwnerAuthenStatus.setAuthDate(new Date());
                    carOwnerAuthenStatus.setAuthState(CarOwnerAuthenStatus.AUTH_STATE_AUDITED);
                    carOwnerAuthenStatus.setAuthWay(CarOwnerAuthenStatus.AUTH_WAY_AUTO);
                    carOwnerAuthenStatus.setAuthResult(CarOwnerAuthenStatus.AUTH_RESULT_FAIL);
                    carOwnerAuthenStatus.setAuthWay(CarOwnerAuthenStatus.AUTH_WAY_AUTO);
                    carOwnerAuthenStatus.setRemark(CarOwnerAuthenStatusResult.getReason(CarOwnerAuthenStatusResult.MOBILE_ALREADY_EXIST));

                    createAssessLog(carOwnerAuthenStatus);
                    return carOwnerAuthenStatus;
                }
            }


            //验证vin码
            String vin = carOwnerAuthenStatus.getUserVin();
            if(StringUtils.hasText(vin)){
                vin =  vin.toUpperCase().trim();

            }
            if(!VinUtils.isVin(vin)){
                carOwnerAuthenStatus.setAuthDate(new Date());
                carOwnerAuthenStatus.setAuthState(CarOwnerAuthenStatus.AUTH_STATE_AUDITED);
                carOwnerAuthenStatus.setAuthWay(CarOwnerAuthenStatus.AUTH_WAY_AUTO);
                carOwnerAuthenStatus.setAuthResult(CarOwnerAuthenStatus.AUTH_RESULT_FAIL);
                carOwnerAuthenStatus.setAuthWay(CarOwnerAuthenStatus.AUTH_WAY_AUTO);
                carOwnerAuthenStatus.setRemark(CarOwnerAuthenStatusResult.getReason(CarOwnerAuthenStatusResult.VIN_NOT_VALID));

                createAssessLog(carOwnerAuthenStatus);
                return carOwnerAuthenStatus;
            }


            //验证vin码是否被认证
            FordClubMember member = this.fordClubMemberService.findByVin(vin);
            if(member!=null){
                JoUser user = this.joUserService.getById(member.getUserId());
                if(user!=null){
                    if (user.getWechatUserName() != null) {
                        //如果已认证的vin码对应的openid等于预留的openid 审核成功
                        if(user.getWechatUserName().equals(carOwnerAuthenStatus.getOpenId())){
                            carOwnerAuthenStatus.setAuthDate(new Date());
                            carOwnerAuthenStatus.setAuthState(CarOwnerAuthenStatus.AUTH_STATE_AUDITED);
                            carOwnerAuthenStatus.setAuthWay(CarOwnerAuthenStatus.AUTH_WAY_AUTO);
                            carOwnerAuthenStatus.setAuthResult(CarOwnerAuthenStatus.AUTH_RESULT_PAAS);
                            carOwnerAuthenStatus.setAuthWay(CarOwnerAuthenStatus.AUTH_WAY_AUTO);
                            carOwnerAuthenStatus.setVinSource(CarOwnerAuthenStatus.VIN_SOURCE_AUTH);
                            carOwnerAuthenStatus.setRemark(CarOwnerAuthenStatusResult.getReason(CarOwnerAuthenStatusResult.AUTH_SUCCESS));

                            createAssessLog(carOwnerAuthenStatus);
                            return carOwnerAuthenStatus;
                        }
                    }

                }
                carOwnerAuthenStatus.setAuthDate(new Date());
                carOwnerAuthenStatus.setAuthState(CarOwnerAuthenStatus.AUTH_STATE_AUDITED);
                carOwnerAuthenStatus.setAuthWay(CarOwnerAuthenStatus.AUTH_WAY_AUTO);
                carOwnerAuthenStatus.setAuthResult(CarOwnerAuthenStatus.AUTH_RESULT_FAIL);
                carOwnerAuthenStatus.setAuthWay(CarOwnerAuthenStatus.AUTH_WAY_AUTO);
                carOwnerAuthenStatus.setRemark(CarOwnerAuthenStatusResult.getReason(CarOwnerAuthenStatusResult.VIN_ALREADY_AUTH));

                createAssessLog(carOwnerAuthenStatus);
                return carOwnerAuthenStatus;
            }
            //验证vin码是否存在DMS传输数据中
            FordMemberForm fordMemberForm = this.fordMemberFormService.findByVin(vin);
            //如果vin码已经从dms传输过来 审核成功
            if(fordMemberForm!=null){
                carOwnerAuthenStatus.setAuthDate(new Date());
                carOwnerAuthenStatus.setAuthState(CarOwnerAuthenStatus.AUTH_STATE_AUDITED);
                carOwnerAuthenStatus.setAuthWay(CarOwnerAuthenStatus.AUTH_WAY_AUTO);
                carOwnerAuthenStatus.setAuthResult(CarOwnerAuthenStatus.AUTH_RESULT_PAAS);
                carOwnerAuthenStatus.setAuthWay(CarOwnerAuthenStatus.AUTH_WAY_AUTO);
                carOwnerAuthenStatus.setVinSource(CarOwnerAuthenStatus.VIN_SOURCE_DMS);
                int result = bindService.doOwnerAutoAuthenForWechat(carOwnerAuthenStatus.getChannelCode(), carOwnerAuthenStatus.getChannelType(),  carOwnerAuthenStatus.getOpenId(),mobile, SOURCE, vin, "127.0.0.1");
                if(result==1){
                    carOwnerAuthenStatus.setRemark(CarOwnerAuthenStatusResult.getReason(CarOwnerAuthenStatusResult.AUTH_SUCCESS));
                }else{
                    carOwnerAuthenStatus.setRemark(CarOwnerAuthenStatusResult.getReason(CarOwnerAuthenStatusResult.AUTH_FAIL,Integer.valueOf(result).toString()));
                }

                createAssessLog(carOwnerAuthenStatus);
                return carOwnerAuthenStatus;
            }
            //验证vin码是否存在主机厂数据中
            WeFactoryForm weFactoryForm = this.weFactoryFormService.findByVin(vin);
            //如果vin码已经从dms传输过来 审核成功
            if(weFactoryForm!=null) {
                carOwnerAuthenStatus.setAuthDate(new Date());
                carOwnerAuthenStatus.setAuthState(CarOwnerAuthenStatus.AUTH_STATE_AUDITED);
                carOwnerAuthenStatus.setAuthWay(CarOwnerAuthenStatus.AUTH_WAY_AUTO);
                carOwnerAuthenStatus.setAuthResult(CarOwnerAuthenStatus.AUTH_RESULT_PAAS);
                carOwnerAuthenStatus.setAuthWay(CarOwnerAuthenStatus.AUTH_WAY_AUTO);
                carOwnerAuthenStatus.setVinSource(CarOwnerAuthenStatus.VIN_SOURCE_FACTORY);
                int result = bindService.doOwnerAutoAuthenForWechat(carOwnerAuthenStatus.getChannelCode(), carOwnerAuthenStatus.getChannelType(), carOwnerAuthenStatus.getOpenId(), mobile, SOURCE, vin, "127.0.0.1");
                if (result == 1) {
                    carOwnerAuthenStatus.setRemark(CarOwnerAuthenStatusResult.getReason(CarOwnerAuthenStatusResult.AUTH_SUCCESS));
                } else {
                    carOwnerAuthenStatus.setRemark(CarOwnerAuthenStatusResult.getReason(CarOwnerAuthenStatusResult.AUTH_FAIL, Integer.valueOf(result).toString()));
                }


                return carOwnerAuthenStatus;
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            createAssessLog(carOwnerAuthenStatus);
        }

        return carOwnerAuthenStatus;
    }

    private void  createAssessLog(CarOwnerAuthenStatus status){
        WeAssessLog weAssessLog = new WeAssessLog();
        BeanUtils.copyProperties(status,weAssessLog);
        weAssessLog.setVin(status.getUserVin());
        weAssessLog.setAuthCount(Long.valueOf(status.getTimes()).intValue());
        if(CarOwnerAuthenStatus.AUTH_STATE_NOT_AUDITED.equals(status.getAuthState())){
            weAssessLog.setAuthDate(new Date());
            weAssessLog.setAuthResult(CarOwnerAuthenStatus.AUTH_RESULT_FAIL.intValue());
            weAssessLog.setAuthWay(CarOwnerAuthenStatus.AUTH_WAY_AUTO);
            weAssessLog.setRemark(CarOwnerAuthenStatusResult.getReason(CarOwnerAuthenStatusResult.VIN_NOT_EXIST));
        }
        this.assessLogService.save(weAssessLog);
    }
}
