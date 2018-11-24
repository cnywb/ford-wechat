package com.ford.wechat.service.members.impl;

import com.ford.wechat.entity.license.WeLicenseImage;
import com.ford.wechat.entity.member.BackTempOwner;
import com.ford.wechat.entity.member.FordMemberForm;
import com.ford.wechat.entity.member.WeWorkorderApply;
import com.ford.wechat.entity.user.FordClubMember;
import com.ford.wechat.entity.user.JoUser;
import com.ford.wechat.entity.user.JobStatus;
import com.ford.wechat.respository.member.BackTempOwnerRepository;
import com.ford.wechat.respository.member.FordMemberFormRepository;
import com.ford.wechat.respository.member.WeWorkorderApplyRepository;
import com.ford.wechat.respository.user.*;
import com.ford.wechat.service.members.WeWorkorderApplyService;

import com.ford.wechat.service.weixin.AuthMessageService;
import io.dabing.common.exception.BusinessException;
import io.dabing.common.grid.GridPage;
import io.dabing.common.util.DateUtils;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-05-23 18:41
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class WeWorkorderApplyServiceImpl extends AbstractService implements WeWorkorderApplyService {

    @Autowired
    private WeWorkorderApplyRepository repository;

    @Autowired
    private FordClubMemberRepository fordClubMemberRepository;

    @Autowired
    private JoUserRepository joUserRepository;

    @Autowired
    private JcUserExtRepository jcUserExtRepository;
    @Autowired
    private JbUserExtRepository jbUserExtRepository;
    @Autowired
    private JcUserRepository jcUserRepository;
    @Autowired
    private JbUserRepository jbUserRepository;

    @Autowired
    private BackTempOwnerRepository backTempOwnerRepository;

    @Autowired
    private FordMemberFormRepository fordMemberFormRepository;

    @Autowired
    private AuthMessageService authMessageService;

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<WeWorkorderApply> pagingBy(GridPage page) {
        return repository.pagingBy(page);
    }

    @Override
    public Page<WeWorkorderApply> pagingBy(String vin, String openId, String mobile, Integer assessStatus, GridPage page) {
        return repository.pagingBy(vin, openId, mobile, assessStatus, page);
    }

    /**
     * 保存
     *
     * @param object
     */
    public void save(WeWorkorderApply object) {
        object.setJobStatus(JobStatus.NOT_TO_JOB);
        repository.save(object);
    }

    /**
     * 批量删除
     *
     * @param objectList
     */
    public void delete(List<WeWorkorderApply> objectList) {
        repository.delete(objectList);
    }

    /**
     * 删除
     *
     * @param object
     */
    public void delete(WeWorkorderApply object) {
        object.setDeleted(Boolean.TRUE);
        this.update(object);
    }

    /**
     * 根据主键删除
     *
     * @param id
     */
    public void delete(Long id) {
        WeWorkorderApply entity = this.repository.get(id);
        entity.setDeleted(Boolean.TRUE);
        this.update(entity);
    }

    /**
     * 更新
     *
     * @param object
     */
    public void update(WeWorkorderApply object) {
        repository.update(object);
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    public WeWorkorderApply get(Long id) {
        return repository.get(id);
    }



    /**
     * 解绑逻辑
     * @param openId 需要解绑的openId
     * @param vin 需要解绑的VIN
     */
    @Override
    public void doUnAuthen(String openId, String vin) {
        log.info("doUnAuthen");
        log.info("解绑逻辑");

        List<FordClubMember> fordClubMemberList = this.fordClubMemberRepository.findListByVin(vin);
        log.info("根据vin: {} 获取FordClubMember列表 size: {}", vin, fordClubMemberList.size());

        log.info("清除该VIN:{}下所有的非当前openId:{}的相关记录", vin, openId);
        for (FordClubMember item : fordClubMemberList) {
            JoUser joUser = this.joUserRepository.getByUserIdNotOpenId(item.getUserId(), openId);
            if (joUser != null) {
                this.joUserRepository.updateResetByUserId(item.getUserId(), openId);
                log.info("更新JoUser 的相关属性为空成功");
                this.jcUserRepository.updateResetByUserId(item.getUserId());
                log.info("更新JcUser 的 groupId = 1 成功");
            }
        }
        this.fordClubMemberRepository.delete(fordClubMemberList);
        log.info("删除该VIN下所有的绑定FordClubMember");


        log.info("还原ford_member_form记录，以便下次认证");
        //还原ford_member_form记录
        BackTempOwner backTempOwner = this.backTempOwnerRepository.findByVin(vin);
        if (backTempOwner != null) {
            log.info("BackTempOwner 查询成功 vin: {}", vin);
            FordMemberForm t = new FordMemberForm();
            t.setVformId(this.fordMemberFormRepository.getSeq());//TODO
            t.setVvin(backTempOwner.getVin());
            t.setVformName(backTempOwner.getOwnerName());
            t.setVformTel(backTempOwner.getMobile());
            t.setVcreated("00-001");
            t.setDcrtDate(new Date());
            log.info("插入（还原）FordMemberForm数据");
            this.fordMemberFormRepository.save(t);
        }
        log.info("解绑逻辑完成");

        List<FordClubMember> fordClubMembers = this.fordClubMemberRepository.findListByOpenId(openId);
        if (!fordClubMembers.isEmpty()) {
            log.info("该openId下还有车辆绑定");
            return;
        }

        List<JoUser> joUserList = this.joUserRepository.getList(openId);
        for(JoUser item : joUserList) {
            this.jcUserExtRepository.deleteByUserId(item.getId());
            log.info("清除JcUserExt成功");
            //删除jb_user_ext
            this.jbUserExtRepository.deleteByUserId(item.getId());
            log.info("清除JbUserExt成功");
            this.jcUserRepository.deleteByUserId(item.getId());
            log.info("清除JcUser成功");
            this.jbUserRepository.deleteByUserId(item.getId());
            log.info("清除JbUser成功");
        }
        this.joUserRepository.delete(joUserList);
        log.info("清除JoUser List成功");

    }




    /*public void doUnAuthen2(String openId, String vin) {
        log.info("doUnAuthen");
        log.info("解绑逻辑");

        List<JoUser> joUserList = this.joUserRepository.getList(openId, vin);
        log.info("根据openId: {}，VIN: {} 获取JoUser列表 size: {}", openId, vin, joUserList.size());
        for (JoUser item : joUserList) {
            //删除jc_user_ext
            this.jcUserExtRepository.deleteByUserId(item.getId());
            log.info("清除JcUserExt成功");
            //删除jb_user_ext
            this.jbUserExtRepository.deleteByUserId(item.getId());
            log.info("清除JbUserExt成功");
            this.jcUserRepository.deleteByUserId(item.getId());
            log.info("清除JcUser成功");
            this.jbUserRepository.deleteByUserId(item.getId());
            log.info("清除JbUser成功");
        }
        this.joUserRepository.delete(joUserList);
        log.info("清除JoUser List成功");

        List<FordClubMember> fordClubMemberList = this.fordClubMemberRepository.findListByVin(vin);
        log.info("根据vin: {} 获取FordClubMember列表 size: {}", vin, fordClubMemberList.size());

        for (FordClubMember item : fordClubMemberList) {
            this.joUserRepository.updateResetByUserId(item.getUserId(), openId);
            log.info("更新JoUser 的相关属性为空成功");
            this.jcUserRepository.updateResetByUserId(item.getUserId());
            log.info("更新JcUser 的 groupId = 1 成功");
        }
        this.fordClubMemberRepository.delete(fordClubMemberList);
        log.info("清除FordClubMember List成功");

        log.info("还原ford_member_form记录");
        //还原ford_member_form记录
        BackTempOwner backTempOwner = this.backTempOwnerRepository.findByVin(vin);
        if (backTempOwner != null) {
            log.info("BackTempOwner 查询成功 vin: {}", vin);
            FordMemberForm t = new FordMemberForm();
            t.setVformId(this.fordMemberFormRepository.getSeq());//TODO
            t.setVvin(backTempOwner.getVin());
            t.setVformName(backTempOwner.getOwnerName());
            t.setVformTel(backTempOwner.getMobile());
            t.setVcreated("00-001");
            t.setDcrtDate(new Date());
            log.info("插入（还原）FordMemberForm数据");
            this.fordMemberFormRepository.save(t);
        }
        log.info("解绑逻辑完成");
    }*/


    @Override
    public void doChangeMobile(String openId, String vin, String mobile) {
        List<FordClubMember> list = this.fordClubMemberRepository.findListByVin(vin);
        if (list.isEmpty()) {
            return;
        }
        for (FordClubMember t : list) {
            t.setMobile(mobile);
        }
        this.fordClubMemberRepository.save(list);
    }

    @Override
    public List<WeWorkorderApply> findListBy(String openId, String vin) {
        return this.repository.findList(openId, vin);
    }

    /**
     * 根据VIN和OPENID查询该用户解绑次数
     * @param openId
     * @param vin
     * @return
     */
    @Override
    public List<WeWorkorderApply> findUnbindList(String openId, String vin) {
        return this.repository.findUnbindList(openId, vin);
    }

    @Override
    public List<WeWorkorderApply> findUnAuthList(String openId) {
        return this.repository.findUnAuthList(openId);
    }

    //不通过解绑或修改手机号
    @Override
    public int noPass(Long id, Integer appType, String reason) {
        int logValue;
        WeWorkorderApply entity = repository.get(id);
        if (appType == 0) {
            log.info("解绑");
            entity.setCompletedDate(new Date());
            entity.setAssessResult(WeWorkorderApply.ASSESS_RESULT_FAIL);
            entity.setAssessStatus(WeWorkorderApply.ASSESS_STATUS_AUDITED);
            logValue = 0;//解绑失败
            this.authMessageService.sendUnbindFailedMessage(entity.getOpenId(), entity.getVin(), DateUtils.format(new Date(), DateUtils.FORMAT_DATE_DEFAULT), reason);
        } else {
            log.info("修改手机");
            entity.setCompletedDate(new Date());
            entity.setAssessResult(WeWorkorderApply.ASSESS_RESULT_FAIL);
            entity.setAssessStatus(WeWorkorderApply.ASSESS_STATUS_AUDITED);
            logValue = 3;//修改手机失败
//            this.authMessageService.sendUpdateMobileMessage(entity.getOpenId(), entity.getOldMobile(), entity.getMobile(), DateUtils.format(new Date(), DateUtils.FORMAT_DATE_DEFAULT));
        }
        repository.update(entity);
        return logValue;
    }

    //修改手机或解绑的方法
    @Override
    public int doUnAuthenOrChangeMobile(Long id,Integer appType,String openId,String vin,String mobile) {
        int logValue;
        WeWorkorderApply entity = repository.get(id);
        if(appType==0){
            log.info("解绑");
            try{
                FordClubMember clubMember = this.fordClubMemberRepository.findByVin(vin);
                if (clubMember == null) {
                    throw new BusinessException("该VIN没被认证");
                }
                doUnAuthen(openId, vin);
                entity.setCompletedDate(new Date());
                entity.setAssessResult(WeWorkorderApply.ASSESS_RESULT_PASS);
                entity.setAssessStatus(WeWorkorderApply.ASSESS_STATUS_AUDITED);
                entity.setApplyType(0);
                entity.setDcrtDate(clubMember.getDcrtDate());
                this.authMessageService.sendUnbindSuccessMessage(openId, vin, DateUtils.format(new Date(), DateUtils.FORMAT_DATE_DEFAULT));
                logValue=1;//解绑成功
            }catch (Exception e){
                entity.setCompletedDate(new Date());
                entity.setAssessResult(WeWorkorderApply.ASSESS_RESULT_FAIL);
                entity.setAssessStatus(WeWorkorderApply.ASSESS_STATUS_AUDITED);
                logValue=0;//解绑失败
                e.printStackTrace();
            }
            repository.update (entity);
            return logValue;
        }else {
            log.info("修改手机");
            try{
                doChangeMobile(openId, vin, mobile);
                entity.setCompletedDate(new Date());
                entity.setAssessResult(WeWorkorderApply.ASSESS_RESULT_PASS);
                entity.setAssessStatus(WeWorkorderApply.ASSESS_STATUS_AUDITED);
                entity.setApplyType(1);
                this.authMessageService.sendUpdateMobileMessage(openId, entity.getOldMobile(), mobile, DateUtils.format(new Date(), DateUtils.FORMAT_DATE_DEFAULT));
                logValue=2;//修改手机成功
            }catch (Exception e){
                entity.setCompletedDate(new Date());
                entity.setAssessResult(WeWorkorderApply.ASSESS_RESULT_FAIL);
                entity.setAssessStatus(WeWorkorderApply.ASSESS_STATUS_AUDITED);
                logValue=3;//修改手机失败
                e.printStackTrace();
            }
            repository.update (entity);
            return logValue;
        }

    }


    @Override
    public void saveUnauthApplyForSuccess(FordClubMember oldClubMember, JoUser oldJoUser, String channelCode, String openId, String mobile, String vin, WeLicenseImage licenseImage) {
        log.info("saveUnauthApplyForSuccess");
        log.info("添加自动解绑申请");
        WeWorkorderApply t = new WeWorkorderApply();
        t.setApplyType(WeWorkorderApply.APPLY_TYPE_UNBIND);
        t.setAddress(null);
        t.setApplyDate(new Date());
        t.setAssessResult(WeWorkorderApply.ASSESS_RESULT_PASS);//审核成功
        t.setAssessStatus(WeWorkorderApply.ASSESS_STATUS_AUDITED);//审核完成
        t.setVin(vin);
        t.setJobStatus(0);
        t.setMobile(mobile);
        t.setLicenseImgId(licenseImage == null ? null : licenseImage.getId());
        t.setOpenId(openId);
        t.setCompletedDate(new Date());
        t.setDealer(null);//TODO
        t.setLicenseUrl(licenseImage == null ? null : licenseImage.getResult());//TODO
        t.setName(null);
        t.setReason("");
        t.setSource("");
        t.setDcrtDate(oldClubMember.getDcrtDate());
        t.setOldOpenId(oldJoUser.getWechatUserName());
        t.setOldChannelCode(oldClubMember.getChannelCode());
        t.setOldMobile(oldClubMember.getMobile());
        t.setOldName(oldClubMember.getName());
        this.repository.save(t);
        log.info("保存自动解绑申请成功  vin: {}  mobile: {}  dealer: {}  openId: {}  AssessResult: {}  AssessStatus: {}  JobStatus: {}  licenseImgId: {}",
                t.getVin(), t.getMobile(), t.getDealer(), t.getOpenId(), t.getAssessResult(), t.getAssessStatus(), t.getJobStatus(), t.getLicenseImgId());
    }


    @Override
    public void saveUnauthApplyForSuccessByManual(FordClubMember oldClubMember, JoUser oldJoUser, String channelCode, String openId, String mobile, String vin, WeLicenseImage licenseImage) {
        log.info("saveUnauthApplyForSuccessByManual");
        log.info("添加自动解绑申请");
        WeWorkorderApply t = new WeWorkorderApply();
        t.setApplyType(WeWorkorderApply.APPLY_TYPE_UNBIND);
        t.setAddress(null);
        t.setApplyDate(new Date());
        t.setAssessResult(WeWorkorderApply.ASSESS_RESULT_PASS);//审核成功
        t.setAssessStatus(WeWorkorderApply.ASSESS_STATUS_AUDITED);//审核完成
        t.setVin(vin);
        t.setJobStatus(0);
        t.setMobile(mobile);
        t.setLicenseImgId(licenseImage == null ? null : licenseImage.getId());
        t.setOpenId(openId);
        t.setCompletedDate(new Date());
        t.setDealer(null);//TODO
        t.setLicenseUrl(licenseImage == null ? null : licenseImage.getResult());//TODO
        t.setName(null);
        t.setReason("");
        t.setSource("");
        t.setDcrtDate(oldClubMember.getDcrtDate());
        t.setOldOpenId(oldJoUser.getWechatUserName());
        t.setOldChannelCode(oldClubMember.getChannelCode());
        t.setOldMobile(oldClubMember.getMobile());
        t.setOldName(oldClubMember.getName());
        this.repository.save(t);
        log.info("保存自动解绑申请成功  vin: {}  mobile: {}  dealer: {}  openId: {}  AssessResult: {}  AssessStatus: {}  JobStatus: {}  licenseImgId: {}",
                t.getVin(), t.getMobile(), t.getDealer(), t.getOpenId(), t.getAssessResult(), t.getAssessStatus(), t.getJobStatus(), t.getLicenseImgId());
    }


    @Override
    public void saveManualUnauthApply(String openId, String mobile, String vin, Long userId, WeLicenseImage licenseImage) {
        log.info("saveAutoUnauthApply");
        log.info("添加人工解绑申请");
        WeWorkorderApply t = new WeWorkorderApply();
        t.setAddress(null);
        t.setApplyDate(new Date());
        t.setAssessResult(0);//未成功
        t.setAssessStatus(0);//待审核
        t.setVin(vin);
        t.setJobStatus(0);
        t.setMobile(mobile);
        t.setLicenseImgId(licenseImage == null ? null : licenseImage.getId());
        t.setOpenId(openId);
        t.setCompletedDate(null);
        t.setDealer(null);//TODO
        t.setLicenseUrl(licenseImage == null ? null : licenseImage.getResult());//TODO
        t.setName(null);
        t.setReason("");
        t.setSource("");
        this.repository.save(t);
        log.info("保存人工解绑申请成功  vin: {}  mobile: {}  dealer: {}  openId: {}  AssessResult: {}  AssessStatus: {}  JobStatus: {}  licenseImgId: {}",
                t.getVin(), t.getMobile(), t.getDealer(), t.getOpenId(), t.getAssessResult(), t.getAssessStatus(), t.getJobStatus(), t.getLicenseImgId());
    }


    /**
     * 保存解绑手机号工单申请
     * @param openId
     * @param mobile
     * @param question
     * @param option
     * @param dealer
     * @param desc
     * @param licenseImgId
     * @param licenseImgUrl
     */
    @Override
    public void saveChangeMobileApply(String openId, String vin, String mobile, String question, String option, String dealer, String desc, Long licenseImgId, String licenseImgUrl) {

        JoUser joUser = this.joUserRepository.getJoUserByWechatId(openId);

        WeWorkorderApply t = new WeWorkorderApply();
        t.setAddress(null);
        t.setApplyDate(new Date());
        t.setAssessResult(0);//未成功
        t.setAssessStatus(0);//待审核
        t.setVin(vin);
        t.setJobStatus(0);
        t.setMobile(mobile);
        t.setLicenseImgId(licenseImgId);
        t.setLicenseUrl(licenseImgUrl);//TODO
        t.setOpenId(openId);
        t.setCompletedDate(null);
        t.setDealer(dealer);//TODO
        t.setName(null);
        t.setTitle(question);
        t.setReason(option);
        t.setSource("微信工单");
//        t.setApplyType(0);
        t.setMemo(desc);

        t.setOldOpenId(joUser == null ? null :joUser.getWechatUserName());
        t.setOldMobile(joUser == null ? null :joUser.getMobilePhone());
        this.repository.save(t);
    }


    /**
     * 保存解绑VIN工单申请
     * @param openId
     * @param vin
     * @param question
     * @param option
     * @param dealer
     * @param desc
     * @param licenseImgId
     * @param licenseImgUrl
     */
    @Override
    public void saveUnbindVinApply(String openId, String vin, String question, String option, String dealer, String desc, Long licenseImgId, String licenseImgUrl) {
        FordClubMember fordClubMember = this.fordClubMemberRepository.findByVin(vin);
        if (fordClubMember == null) {
            throw new BusinessException("您要解绑的VIN没被绑定");
        }
        JoUser joUser = this.joUserRepository.get(fordClubMember.getUserId());
        if (joUser == null) {
            throw new BusinessException("您要解绑的VIN没被绑定");
        }

        log.info("校验该微信号解绑该VIN次数是否达到2次");
        int unbinds = this.findListBy(openId, vin).size();
        if (unbinds >= 2) {
            log.info("该微信号解绑该VIN次数达到2次，认证失败，返回-1");
            throw new BusinessException("一个VIN最多只能解绑2次，避免重复解绑哟");
        }

        WeWorkorderApply t = new WeWorkorderApply();
        t.setAddress(null);
        t.setApplyDate(new Date());
        t.setAssessResult(0);//未成功
        t.setAssessStatus(0);//待审核
        t.setVin(vin);
        t.setJobStatus(0);
        t.setMobile(null);
        t.setLicenseImgId(licenseImgId);
        t.setOpenId(openId);
        t.setCompletedDate(null);
        t.setDealer(dealer);//TODO
        t.setLicenseUrl(licenseImgUrl);//TODO
        t.setName(null);
        t.setTitle(question);
        t.setReason(option);
        t.setSource("微信工单");
//        t.setApplyType(1);
        t.setMemo(desc);
        t.setDcrtDate(fordClubMember.getDcrtDate());

        t.setOldOpenId(joUser.getWechatUserName());
        t.setOldChannelCode(fordClubMember.getChannelCode());
        t.setOldMobile(fordClubMember.getMobile());
        t.setOldName(fordClubMember.getName());
        this.repository.save(t);
    }


    /**
     * 保存工单申请
     * @param openId
     * @param vin
     * @param mobile
     * @param title
     * @param reason
     * @param dealer
     * @param memo
     * @param licenseImgId
     * @param licenseImgUrl
     */
    @Override
    public void saveWorkorderApply(String openId, String vin, String mobile, String title, String reason, String dealer, String memo, Long licenseImgId, String licenseImgUrl) {
        WeWorkorderApply t = new WeWorkorderApply();

        if ("解除VIN码绑定".equals(title)) {
            FordClubMember fordClubMember = this.fordClubMemberRepository.findByVin(vin);
            if (fordClubMember == null) {
                throw new BusinessException("您要解绑的VIN没被绑定");
            }
            JoUser joUser = this.joUserRepository.get(fordClubMember.getUserId());
            if (joUser == null) {
                throw new BusinessException("您要解绑的VIN没被绑定");
            }

            log.info("校验该微信号解绑该VIN次数是否达到2次");
            int unbinds = this.findUnbindList(openId, vin).size();
            if (unbinds >= 2) {
                log.info("该微信号解绑该VIN次数达到2次，认证失败，返回-1");
                throw new BusinessException("一个VIN最多只能解绑2次，避免重复解绑哟");
            }
            t.setOldOpenId(joUser.getWechatUserName());
            t.setOldChannelCode(fordClubMember.getChannelCode());
            t.setOldMobile(fordClubMember.getMobile());
            t.setOldName(fordClubMember.getName());
            t.setDcrtDate(fordClubMember.getDcrtDate());
        }


        t.setAddress(null);
        t.setApplyDate(new Date());
        t.setAssessResult(0);//未成功
        t.setAssessStatus(0);//待审核
        t.setVin(vin);
        t.setMobile(mobile);
        t.setJobStatus(0);
        t.setLicenseImgId(licenseImgId);
        t.setOpenId(openId);
        t.setCompletedDate(null);
        t.setDealer(dealer);//TODO
        t.setLicenseUrl(licenseImgUrl);//TODO
        t.setName(null);
        t.setTitle(title);
        t.setReason(reason);
        t.setSource("微信工单");
//        t.setApplyType(1);
        t.setMemo(memo);

        this.repository.save(t);
    }


}
