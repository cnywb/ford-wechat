package com.ford.wechat.service.user.impl;

import com.ford.wechat.entity.user.JcGroup;
import com.ford.wechat.entity.user.JcUser;
import com.ford.wechat.entity.user.JcUserExt;
import com.ford.wechat.respository.user.JcGroupRepository;
import com.ford.wechat.respository.user.JcUserRepository;
import com.ford.wechat.service.user.JcUserService;
import io.dabing.core.service.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Neel on 2017/5/19.
 */
@Service
@Slf4j
public class JcUserServiceImpl extends AbstractService implements JcUserService {

    @Autowired
    JcUserRepository jcUserRepository;

    @Autowired
    JcGroupRepository jcGroupRepository;


    @Override
    public void save(Long userId, String username, String email, String ip, Long groupId, Long grain) {
        JcUser user = new JcUser();
        JcUserExt userExt = new JcUserExt();
        JcGroup cmsGroup = null;
        if (groupId != null) {
            cmsGroup = jcGroupRepository.get(groupId);
        } else {
            cmsGroup = jcGroupRepository.findByRegDef();//得到默认会员组
        }
        if (cmsGroup == null) {
            throw new RuntimeException("register default member cmsGroup not found!");
        }
        user.setGroupId(cmsGroup.getId());
        user.setUserId(userId);
        user.setUserName(username);
        user.setEmail(email);
        user.setRegisterIp(ip);
        user.setLastLoginIp(ip);
        user.setGrain(grain);
        user.setIsAdmin(0);
        user.setRank(0l);
        user.setIsViewonlyAdmin(0);
        user.setIsSelfAdmin(0);
        user.setIsDisabled(0);
        user.setRegisterTime(new Date());
        user.setLastLoginTime(new Date());
        user.setLoginCount(0l);
        user.setUploadTotal(0l);
        user.setUploadSize(0l);
        user.setFileTotal(0l);


        userExt.setUserId(userId);

        jcUserRepository.save(user);
    }

}
