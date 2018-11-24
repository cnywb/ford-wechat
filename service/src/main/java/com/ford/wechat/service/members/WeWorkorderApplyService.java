package com.ford.wechat.service.members;

import com.ford.wechat.entity.license.WeLicenseImage;
import com.ford.wechat.entity.member.WeWorkorderApply;
import com.ford.wechat.entity.user.FordClubMember;
import com.ford.wechat.entity.user.JoUser;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;

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
 * All rights reserved. 2017-05-23 18:40
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public interface WeWorkorderApplyService {

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    Page<WeWorkorderApply> pagingBy(GridPage page);

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<WeWorkorderApply> pagingBy(String vin, String openId, String mobile, Integer assessStatus, GridPage page);


    /**
     * 保存
     *
     * @param object
     */
    void save(WeWorkorderApply object);

    /**
     * 批量删除
     *
     * @param objectList
     */
    void delete(List<WeWorkorderApply> objectList);

    /**
     * 删除
     *
     * @param object
     */
    void delete(WeWorkorderApply object);

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
    void update(WeWorkorderApply object);

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    WeWorkorderApply get(Long id);

    void doUnAuthen(String openId, String vin);

    void doChangeMobile(String openId, String vin, String mobile);

    List<WeWorkorderApply> findListBy(String openId, String vin);

    List<WeWorkorderApply> findUnbindList(String openId, String vin);

    List<WeWorkorderApply> findUnAuthList(String openId);

    int noPass(Long id,Integer appType, String reason);

    int doUnAuthenOrChangeMobile(Long id,Integer appType,String openId,String vin,String mobile);

    void saveUnauthApplyForSuccess(FordClubMember oldClubMember, JoUser oldJoUser, String channelCode, String openId, String mobile, String vin, WeLicenseImage licenseImage);

    void saveUnauthApplyForSuccessByManual(FordClubMember oldClubMember, JoUser oldJoUser, String channelCode, String openId, String mobile, String vin, WeLicenseImage licenseImage);

    void saveManualUnauthApply(String openId, String mobile, String vin, Long userId, WeLicenseImage licenseImage);

    void saveChangeMobileApply(String openId, String vin, String mobile, String question, String option, String dealer, String desc, Long licenseImgId, String licenseImgUrl);

    void saveUnbindVinApply(String openId, String vin, String question, String option, String dealer, String desc, Long licenseImgId, String licenseImgUrl);

    void saveWorkorderApply(String openId, String vin, String mobile, String title, String reason, String dealer, String memo, Long licenseImgId, String licenseImgUrl);
}
