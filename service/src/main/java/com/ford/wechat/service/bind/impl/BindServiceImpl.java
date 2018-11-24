package com.ford.wechat.service.bind.impl;

import com.ford.wechat.entity.factory.WeFactoryForm;
import com.ford.wechat.entity.license.WeLicenseImage;
import com.ford.wechat.entity.member.FordClubApply;
import com.ford.wechat.entity.member.FordMemberForm;
import com.ford.wechat.entity.user.*;
import com.ford.wechat.respository.factory.WeFactoryFormRepository;
import com.ford.wechat.respository.member.FordClubApplyRepository;
import com.ford.wechat.respository.member.FordMemberFormRepository;
import com.ford.wechat.respository.user.JcGroupRepository;
import com.ford.wechat.respository.user.JcUserRepository;
import com.ford.wechat.respository.user.JoUserRepository;
import com.ford.wechat.service.bind.BindService;
import com.ford.wechat.service.license.LicenseImageService;
import com.ford.wechat.service.mdao.SmsService;
import com.ford.wechat.service.members.WeWorkorderApplyService;
import com.ford.wechat.service.user.*;
import com.ford.wechat.service.weixin.AuthMessageService;
import com.ford.wechat.service.weixin.MessageData;
import com.ford.wechat.service.weixin.WeiXinMessage;
import com.ford.wechat.service.weixin.WeiXinMessageService;
import io.dabing.common.exception.BusinessException;
import io.dabing.common.util.DateUtils;
import io.dabing.common.util.NumberUtil;
import io.dabing.redis.util.RedisKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Neel on 2017/5/16.
 */
@Service
@Slf4j
public class BindServiceImpl implements BindService {

    public static final String KEY_BIND_MOBILE_CAPTCHA = "FORD:BIND:CAPTCHA:OPENID:{0}:MOBILE:{1}";
    public static final String KEY_BIND_MOBILE_CAPTCHA_TIME = "FORD:BIND:CAPTCHA:OPENID:{0}:MOBILE:{1}:TIME";

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private CarOwnerAuthenStatusService carOwnerAuthenStatusService;

    @Autowired
    private WeFactoryFormRepository weFactoryFormRepository;

    @Autowired
    private FordClubMemberService fordClubMemberService;

    @Autowired
    private JoUserRepository joUserRepository;
    @Autowired
    private JcUserRepository jcUserRepository;

    @Autowired
    private JoUserService joUserService;
    @Autowired
    private FordMemberFormRepository fordMemberFormRepository;
    @Autowired
    private JcGroupRepository jcGroupRepository;
    @Autowired
    private FordClubApplyRepository fordClubApplyRepository;

    @Autowired
    private WeWorkorderApplyService weWorkorderApplyService;

//    @Autowired
//    private WeiXinMessageService weiXinMessageService;

    @Autowired
    private AuthMessageService authMessageService;

    @Autowired
    private LicenseImageService licenseImageService;

    @Override
    public void sendBindCode(String openId, String mobile) {
        log.info("sendBindCode > openId: {}  mobile: {}", openId, mobile);

        JoUser user = joUserService.getByMobile(mobile);
        if (user != null && !openId.equals(user.getWechatUserName())) {//已被其他微信绑定
            throw new BusinessException("500001", "您的手机号已经和其他微信绑定，您可以换个手机号绑定哦，或者联系客服进行更改～");
        }

        String keyTime = RedisKeyUtils.build(KEY_BIND_MOBILE_CAPTCHA_TIME, openId, mobile);
        String value = redisTemplate.opsForValue().get(keyTime);
        if (StringUtils.isNotEmpty(value)) {
            throw new BusinessException("500002", "一分钟只能获取一条验证码");
        }

        String key = RedisKeyUtils.build(KEY_BIND_MOBILE_CAPTCHA, openId, mobile);
//        String code = "888888";
        String code = NumberUtil.getStringNumber("", 6l);
        String content = "您的手机验证码是：" + code + "，请完成验证，如果不是本人操作，请忽略本短信";
        String result = smsService.send(mobile, content);
        log.info("sendBindCode > key: {}   value: {}", key, code);
        if (StringUtils.isEmpty(result)) {
            redisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);
            redisTemplate.opsForValue().set(keyTime, code, 1, TimeUnit.MINUTES);
            return;
        }

        throw new BusinessException("系统繁忙，请稍后再试");

//        redisTemplate.delete(redisTemplate.keys("*"));

    }

    @Override
    public boolean checkBindCode(String openId, String mobile, String code) {
        log.info("checkBindCode > openId: {}  mobile: {}  code: {}", openId, mobile, code);
        String key = RedisKeyUtils.build(KEY_BIND_MOBILE_CAPTCHA, openId, mobile);
        String keyTime = RedisKeyUtils.build(KEY_BIND_MOBILE_CAPTCHA_TIME, openId, mobile);

        log.info("sendBindCode > key: {}   value: {}", key, code);

        String value = redisTemplate.opsForValue().get(key);

        if (StringUtils.isEmpty(value)) {
            throw new BusinessException("验证码不正确");
        }
        if (!code.equalsIgnoreCase(value)) {
            throw new BusinessException("验证码不正确");
        }
        redisTemplate.delete(key);
        redisTemplate.delete(keyTime);

        return true;
    }


    /**
     * 绑定操作....
     * 或者更新手机号
     * @param openId
     * @param mobile
     * @param clubMember
     * @return
     */
    private int doAuthByFordClubMember(String channelCode, Integer channelType, String openId, String mobile, String source, JoUser joUser, FordClubMember clubMember) {
        log.info("doAuthByFordClubMember");
        //手机号、VIN 都与自己的匹配，认证成功
        if (joUser == null) {
            throw new BusinessException("该用户数据异常");
        }

        //已认证，未绑定微信
        if (StringUtils.isBlank(joUser.getWechatUserName())) {//没有绑定微信号
            log.info("该JoUser未绑定微信号，直接绑定微信号");
            //绑定微信
            joUser.setWechatUserName(openId);
            joUser.setEmail(joUser.getEmail() == null ? "" : joUser.getEmail());
            joUser.setMobilePhone(mobile);
            joUser.setMobilePhoneAuth(1);
            this.joUserRepository.update(joUser);

            //更新手机号
            /*if (mobile != null && !mobile.equals(clubMember.getMobile())) {
                log.info("更新FordClubMember下的手机号");
                clubMember.setMobile(mobile);
                clubMember.setChannelCode(channelCode);
                clubMember.setJobStatus(0);
                clubMember.setOpenId(openId);
                clubMember.setSource("微信认证");
                clubMember.setMemberNo(joUser.getId().toString());

                this.fordClubMemberService.update(clubMember);
            }*/
            log.info("更新FordClubMember信息");
            this.fordClubMemberService.updateForAuthSuccess(clubMember, mobile, channelCode, channelType, openId, source, joUser);
            return 1;//认证成功
        } else if (openId.equals(joUser.getWechatUserName())) {//是本人绑定
            log.info("认证是本人认证");
            //判断手机号是否需要更改
            //更新手机号
            if (mobile != null && !mobile.equals(joUser.getMobilePhone())) {
                log.info("更新JoUser手机号");
                joUser.setMobilePhone(mobile);
                joUser.setMobilePhoneAuth(1);
                this.joUserRepository.update(joUser);
            }

            //更新手机号
            /*if (mobile != null && !mobile.equals(clubMember.getMobile())) {
                log.info("更改FordClubMember 手机号");
                clubMember.setMobile(mobile);
                this.fordClubMemberService.update(clubMember);
            }*/
            log.info("更新FordClubMember信息");
            this.fordClubMemberService.updateForAuthSuccess(clubMember, mobile, channelCode, channelType, openId, source, joUser);
            log.info("认证成功");
            return 1;//认证成功
        }
        log.info("没匹配上本人认证逻辑");
        return 0;
    }


    /**
     * 车主认证预留信息审核 - 后台管理系统
     * @param openId 绑定人的openId
     * @param mobile 绑定人的手机号
     * @param vin   绑定人的vin
     * @param licenseImgId 绑定的行驶证照片
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doOwnerAuthenForConsole(String channelCode, Integer channelType, String openId, /*String name,*/ String mobile, String vin, Long licenseImgId) {
        String source = "后台认证";
        log.info("校验该微信号解绑该VIN次数是否达到2次");
        int unbinds = this.weWorkorderApplyService.findUnbindList(openId, vin).size();
        if (unbinds >= 2) {
            log.info("该微信号解绑该VIN次数达到2次，认证失败，返回-1");
            return -1;//解绑次数超过2次，不运行绑定
        }

        log.info("根据VIN获取FordClubMember信息");
        FordClubMember clubMember = this.fordClubMemberService.findByVin(vin);

        WeLicenseImage licenseImage = null;

        //已认证流程
        if (clubMember != null) {//该vin认证过
            //TODO 处理已认证相关逻辑
            log.info("VIN被认证流程");
            log.info("根据VIN获取FordClubMember存在，该VIN已认证，进行相关逻辑");


            //手机号、VIN 都与自己的匹配，认证成功
            Long userId = clubMember.getUserId();
            if (userId == null) {
                log.error("FordClubMember userId 为空，异常数据，需人工处理");
            }
            //原绑定车主
            log.info("根据FordClubMember中userId获取JoUser userId: {}", userId);
            JoUser joUser = this.joUserRepository.get(userId);

            log.info("准备判断FordClubMember信息");
            int status = this.doAuthByFordClubMember(channelCode, channelType, openId, mobile, "预留信息", joUser, clubMember);
            if (status == 1) {
                this.authMessageService.sendAuthSuccessMessage(openId, vin, DateUtils.format(new Date(), DateUtils.FORMAT_DATE_DEFAULT));
                return 1;
            }

            //非本人验证，并且被人绑定过需要上传行驶证
            if (licenseImgId == null || licenseImgId.longValue() == 0l) {
                log.error("非本人验证、并且被人绑定过，没有上传行驶证，不允许绑定");
                throw new BusinessException("需要上传行驶证");//需要上传行驶证
            }
            licenseImage = this.licenseImageService.get(licenseImgId);

            //非本人绑定
            /**
             * TODO 自动解绑逻辑
             * 解绑次数限制内
             * 自动解绑前一个openId的绑定
             * 自动绑定
             */
            log.info("准备进行解绑逻辑");
            this.weWorkorderApplyService.doUnAuthen(joUser.getWechatUserName(), vin);//解绑

            log.info("添加自动解绑申请记录");
            this.weWorkorderApplyService.saveUnauthApplyForSuccess(clubMember, joUser, channelCode, openId, mobile, vin, licenseImage);//添加解绑记录
            //TODO 认证绑定逻辑
        }
        /**
         * VIN未被认证过流程
         */
        log.info("VIN未被认证流程");
        //新的vin信息
        JoUser joUser = this.joUserRepository.getJoUserByWechatId(openId);
        if (joUser == null) {//不存在创建
            //保存JoUser 保存CmsUser 保存BbsUser
            joUser = this.joUserService.createForWeiXin(openId, "53c9bed922650a17de7907a71591fa00", "127.0.0.1", mobile);
        }
        Long userId = joUser.getId();
        FordMemberForm memberForm = this.fordMemberFormRepository.findByVin(vin);
        if (memberForm != null) {
            String customerId = memberForm.getVcustomerId();
            String carId = memberForm.getVcarId();
            String name = memberForm.getVformName();

            this.saveCarOwnerAuthen(openId,channelCode, carId, customerId, name, mobile, source, vin, userId);
            //记录认证流水
            this.saveClubApply(name, mobile, vin, 1, userId);

            this.authMessageService.sendAuthSuccessMessage(openId, vin, DateUtils.format(new Date(), DateUtils.FORMAT_DATE_DEFAULT));
            return 1;//认证成功
        }
        //从 WE_FACTORY_FROM 查询校验, 校验逻辑同上
        WeFactoryForm factoryForm = weFactoryFormRepository.findByVin(vin);//伪代码
        if (factoryForm != null) {

            this.saveCarOwnerAuthen(openId,channelCode, null, null, "", mobile, source, vin, userId);

            //记录认证流水
            this.saveClubApply(null, mobile, vin, 1, userId);

            this.authMessageService.sendAuthSuccessMessage(openId, vin, DateUtils.format(new Date(), DateUtils.FORMAT_DATE_DEFAULT));
            return 1;//认证成功
        }
        throw new BusinessException("VIN无法匹配，认证失败");
    }


    /**
     * 车主认证-微信
     *
     * @param openId
     * @param mobile
     * @param vin
     * @param ip
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doOwnerAuthenForWechat(String channelCode, Integer channelType, String openId, /*String name,*/ String mobile, String vin, String ip, Long licenseImgId) {
        String source = "微信认证";

        log.info("doOwnerAuthenForWechat");
        log.info("车主认证-微信");
        log.info("channelCode: {}  openId: {}  mobile: {}  vin: {}  ip: {}  licenseImgId: {}", channelCode, openId, mobile, vin, ip, licenseImgId);

        log.info("校验该微信号解绑该VIN次数是否达到2次");
        int unbinds = this.weWorkorderApplyService.findUnbindList(openId, vin).size();
        if (unbinds >= 2) {
            log.info("该微信号解绑该VIN次数达到2次，认证失败，返回-1");
            return -1;//解绑次数超过2次，不运行绑定
        }

        log.info("根据VIN获取FordClubMember信息");
        FordClubMember clubMember = this.fordClubMemberService.findByVin(vin);

        WeLicenseImage licenseImage = null;

        //已认证流程
        if (clubMember != null) {//该vin认证过
            //TODO 处理已认证相关逻辑
            log.info("VIN被认证流程");
            log.info("根据VIN获取FordClubMember存在，该VIN已认证，进行相关逻辑");

            //手机号、VIN 都与自己的匹配，认证成功
            Long userId = clubMember.getUserId();
            if (userId == null) {
                log.error("FordClubMember userId 为空，异常数据，需人工处理");
            }
            //原绑定车主
            log.info("根据FordClubMember中userId获取JoUser userId: {}", userId);
            JoUser joUser = this.joUserRepository.get(userId);

            //已认证，未绑定微信
            log.info("准备判断FordClubMember信息");
            int status = this.doAuthByFordClubMember(channelCode, channelType, openId, mobile, source, joUser, clubMember);
            if (status == 1) {
//                this.sendAuthenSuccessMessage(openId, vin);
                this.authMessageService.sendAuthSuccessMessage(openId, vin, DateUtils.format(new Date(), DateUtils.FORMAT_DATE_DEFAULT));
                return 1;
            }

            //非本人验证，并且被人绑定过需要上传行驶证
            if (licenseImgId == null || licenseImgId.longValue() == 0l) {
                log.error("非本人验证、并且被人绑定过，没有上传行驶证，不允许绑定");
                return 3;//需要上传行驶证
            }

            licenseImage = this.licenseImageService.get(licenseImgId);

            //非本人绑定
            /**
             * TODO 自动解绑逻辑
             * 解绑次数限制内
             * 自动解绑前一个openId的绑定
             * 自动绑定
             */
            log.info("准备进行解绑逻辑");
            this.weWorkorderApplyService.doUnAuthen(joUser.getWechatUserName(), vin);//解绑

            log.info("添加自动解绑申请记录");
            //添加自动解绑申请
            this.weWorkorderApplyService.saveUnauthApplyForSuccess(clubMember, joUser, channelCode, openId, mobile, vin, licenseImage);//添加解绑记录
            //TODO 认证绑定逻辑

        }
        /**
         * VIN未被认证过流程
         */
        log.info("VIN未被认证流程");
        return this.doOwnerAutoAuthenForWechat(channelCode, channelType, openId, mobile, source, vin, ip);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doOwnerAutoAuthenForWechat(String channelCode, Integer channelType, String openId, String mobile, String source, String vin, String ip) {
        log.info("doOwnerAutoAuthenForWechat");
        //新的vin信息
        JoUser joUser = this.joUserRepository.getJoUserByWechatId(openId);
        log.info("根据openId： {} 获取JoUser", openId);
        if (joUser == null) {//不存在创建
            //保存JoUser 保存CmsUser 保存BbsUser
            log.info("JoUser不存在，创建JoUser");
            joUser = this.joUserService.createForWeiXin(openId, "53c9bed922650a17de7907a71591fa00", ip, mobile);
        }

        //进行认证操作
        log.info("进行认证操作");
        int flag = this.doCarOwnerAuthen(channelCode, channelType, openId, mobile, source, vin, joUser);
        if (flag == 1) {
//            this.sendAuthenSuccessMessage(openId, vin);
            this.authMessageService.sendAuthSuccessMessage(openId, vin, DateUtils.format(new Date(), DateUtils.FORMAT_DATE_DEFAULT));
            return 1;//认证成功
        }
        if (flag == 6) return 2;//认证审核中
        return 0;//认证失败
    }


    private int doCarOwnerAuthen(String channelCode, Integer channelType, String openId, String mobile, String source, String vin, JoUser joUser) {
        log.info("doCarOwnerAuthen");
        log.info("进行VIN DMS和主机厂数据匹配认证");
        Long userId = joUser.getId();
        FordMemberForm memberForm = this.fordMemberFormRepository.findByVin(vin);
        if (memberForm != null) {
            log.info("FordMemberForm DMS数据匹配上VIN");
            String customerId = memberForm.getVcustomerId();
            String carId = memberForm.getVcarId();
            String name = memberForm.getVformName();

            this.saveCarOwnerAuthen(openId,channelCode, carId, customerId, name, mobile, source, vin, userId);
            log.info("FordClubMember 认证数据保存成功");
            //记录认证流水
            this.saveClubApply(name, mobile, vin, 1, userId);
            log.info("FordClubApply 认证流水记录保存成功[认证成功]");

            return 1;//认证成功
        }
        //从 WE_FACTORY_FROM 查询校验, 校验逻辑同上
        WeFactoryForm factoryForm = weFactoryFormRepository.findByVin(vin);//伪代码
        if (factoryForm != null) {
            log.info("WeFactoryForm 主机厂数据匹配上VIN");
            this.saveCarOwnerAuthen(openId, channelCode, null, null, "", mobile, source, vin, userId);
            log.info("FordClubMember 认证数据保存成功");

            //记录认证流水
            this.saveClubApply(null, mobile, vin, 1, userId);
            log.info("FordClubApply 认证流水记录保存成功[认证成功]");

            return 1;//认证成功
        }
        log.info("DMS和主机厂数据匹配VIN失败");
        //没有自动认证成功，预留信息处理
        //没找到VIN，做预留信息处理
        this.carOwnerAuthenStatusService.save(channelCode, channelType, openId, mobile, vin);
        log.info("预留信息保存成功");

        //flag=6 表示vin未传送到dms系统或者是vin码不存在
        //记录认证流水
        this.saveClubApply("", mobile, vin, 6, userId);
        log.info("FordClubApply 认证流水记录保存成功[信息预留]");
        return 6;//认证暂时未成功
    }


    /**
     * 满足条件后进行认证处理
     *
     * @param cardId
     * @param customerId
     * @param name
     * @param mobile
     * @param vin
     * @param userId
     */
    private void saveCarOwnerAuthen(String openId,String channelCode, String cardId, String customerId, String name, String mobile, String source, String vin, Long userId) {
        //更新车主FordClubMember表信息生成车主卡号
        FordClubMember mem = new FordClubMember();
        String id = this.fordClubMemberService.getSeq();
        mem.setId(id.trim());
        mem.setVcustomerId(customerId);
        mem.setVcardNo(id.trim().replaceFirst("0", "1"));
        mem.setVcarId(cardId);
        mem.setVvin(vin);
        mem.setName(name);
        mem.setDcrtDate(new Date());
        mem.setMobile(mobile);
        mem.setVcardStatus("01");
        mem.setUserId(Long.parseLong(String.valueOf(userId)));
        mem.setChannelCode(channelCode);
        mem.setOpenId(openId);
        mem.setJobStatus(JobStatus.NOT_TO_JOB);
        mem.setSource(source);
        this.fordClubMemberService.save(mem);

        JcGroup jcGroup = this.jcGroupRepository.get(2l);
        JcUser jcUser = this.jcUserRepository.get(userId);
        jcUser.setGroupId(jcGroup.getId());

        this.jcUserRepository.update(jcUser);
    }


    /**
     * 认证流水记录
     *
     * @param name
     * @param mobile
     * @param vin
     * @param flag
     * @param userId
     * @return
     */
    private FordClubApply saveClubApply(String name, String mobile, String vin, int flag, Long userId) {
        FordClubApply fordClubApply = this.fordClubApplyRepository.findByVin(vin);
        if (fordClubApply == null) {
            fordClubApply = new FordClubApply();
            fordClubApply.setId(this.fordClubApplyRepository.getSeq());
            fordClubApply.setDcrtDate(new Date());
            fordClubApply.setVname(name);
            fordClubApply.setVmobile(mobile);
            fordClubApply.setVvin(vin.trim().toUpperCase());
            fordClubApply.setUserId(userId);
            if (flag == 1) {
                fordClubApply.setVapplyStatus("01");
                fordClubApply.setVconfirmResult("06");
            } else {
                fordClubApply.setVapplyStatus("00");
            }
            this.fordClubApplyRepository.save(fordClubApply);
        } else {
            fordClubApply.setDupDate(new Date());
            fordClubApply.setVname(name);
            fordClubApply.setVmobile(mobile);
            fordClubApply.setVvin(vin.trim().toUpperCase());
            fordClubApply.setUserId(userId);
            if (flag == 1) {
                fordClubApply.setVapplyStatus("01");
                fordClubApply.setVconfirmResult("06");
            } else {
                fordClubApply.setVapplyStatus("00");
            }
            this.fordClubApplyRepository.update(fordClubApply);
        }
        return fordClubApply;
    }


    /**
     *
     * @param openId
     */
    /*private void sendAuthenSuccessMessage(String openId, String vin) {
        Map<String, MessageData> data = new HashMap<>();
        data.put("first", new MessageData("尊敬的车主您好！您已经成功完成微信平台车主认证身份验证，长安福特为您开启全新服务体验，祝您生活愉快！"));
        data.put("keyword1", new MessageData(""));
        data.put("keyword2", new MessageData(""));
        data.put("keyword3", new MessageData(vin));
        data.put("keyword4", new MessageData(""));
        data.put("keyword5", new MessageData(""));
        data.put("remark", new MessageData(""));

        WeiXinMessage message = new WeiXinMessage();
        message.setData(data);
        message.setTemplate_id("8zXosekfQ1gBRvOtzDZX1kMgby8d6qHykyh5DPAvMmA");
        message.setTouser(openId);
        message.setUrl("http://dev.point.xiqing.info/bind/static/init.html?state=31");

        this.weiXinMessageService.doSendMessage(message);
    }*/


}
