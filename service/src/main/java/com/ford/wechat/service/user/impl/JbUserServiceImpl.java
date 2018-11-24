package com.ford.wechat.service.user.impl;

import com.ford.wechat.entity.user.JbUser;
import com.ford.wechat.entity.user.JbUserExt;
import com.ford.wechat.entity.user.JbUserGroup;
import com.ford.wechat.entity.user.JbUserOnline;
import com.ford.wechat.respository.bbs.BbsConfigRepository;
import com.ford.wechat.respository.user.JbGroupRepository;
import com.ford.wechat.respository.user.JbUserRepository;
import com.ford.wechat.service.user.JbUserService;
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
public class JbUserServiceImpl extends AbstractService implements JbUserService {


    @Autowired
    private JbUserRepository repository;

    @Autowired
    private BbsConfigRepository bbsConfigRepository;

    @Autowired
    private JbGroupRepository jbGroupRepository;


    @Override
    public void save(Long userId, String username, String email, String ip) {

        Long registerGroupId = bbsConfigRepository.get(2l).getRegisterGroupId();
        JbUserGroup group = jbGroupRepository.get(registerGroupId);
        if (group == null) {
            group = jbGroupRepository.findByRegDef();
        }
        if (group == null) {
            throw new RuntimeException("register default member group not found!");
        }

        JbUser u = new JbUser();
        u.setId(userId);
        u.setUsername(username);
        u.setEmail(email);
        u.setGroupId(group.getId());
        u.setDisabled(false);
        u.setLastLoginIp(ip);
        u.setAdmin(false);
        u.setRegisterIp(ip);
        u.setRegisterTime(new Date());
        u.setLastLoginTime(new Date());
        u.setLoginCount(0);
        u.setUploadTotal(0l);
        u.setUploadSize(0);

        JbUserOnline userOnline = new JbUserOnline();
        userOnline.setId(userId);


        JbUserExt uExt = new JbUserExt();
        uExt.setUserId(userId);

        repository.save(u);
    }

}
