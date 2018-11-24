package com.ford.wechat.service.user.impl;

import com.ford.wechat.entity.user.JoUser;
import com.ford.wechat.respository.user.JoUserRepository;
import com.ford.wechat.service.user.JbUserService;
import com.ford.wechat.service.user.JcUserService;
import com.ford.wechat.service.user.JoUserService;
import io.dabing.common.util.DateUtils;
import io.dabing.common.util.Encrypt;
import io.dabing.core.service.AbstractService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by wanglijun on 16/11/20.
 */
@Service
public class JoUserServiceImpl extends AbstractService implements JoUserService {

    @Autowired
    private JoUserRepository repository;


    @Autowired
    private JbUserService jbUserService;

    @Autowired
    private JcUserService jcUserService;


    @Override
    public JoUser createForWeiXin(String username, String password, String ip, String mobile){

        JoUser u = this.save(username,"", password, ip, mobile);//保存unifiedUser

        jcUserService.save(u.getId(), username, "", ip,null, 0l);//保存cmsUser

        jbUserService.save(u.getId(), username, "", ip);//保存bbsUser

        return u;
    }

    public JoUser save(String username, String email, String password, String ip, String mobile){
        String memberNo = this.createMemberNo(null);
        JoUser uu = new JoUser();
        uu.setActivation(true);
        uu.setWechatUserName(username);
        uu.setEmail(email);
        uu.setUsername(username);
        uu.setPassword(password);
        uu.setRegisterIp(ip);
        uu.setLastLoginIp(ip);
        uu.setMobilePhone(mobile);
        uu.setMobilePhoneAuth(0);
        uu.setMemberNo(memberNo);
        uu.setLastLoginTime(new Date());
        uu.setRegisterTime(new Date());
        uu.setLoginCount(0);
        try {
            uu.setPassword(Encrypt.encryptString(Encrypt.ENCRY_STYLE_MD5, uu.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        repository.save(uu);
        return uu;
    }


    @Override
    public void update(JoUser jouser) {
        if (StringUtils.isEmpty(jouser.getMemberNo())) {
            jouser.setMemberNo(this.createMemberNo(jouser.getRegisterTime()));
        }
        repository.update(jouser);
    }

    @Override
    public int getCountByWechatId(String openid) {
        return repository.getCountByWechatId(openid);
    }

    @Override
    public JoUser getJoUserByWechatId(String openid) {
        return repository.getJoUserByWechatId(openid);
    }
    @Override
    public JoUser getByMobile(String mobile) {
        return repository.getByMobile(mobile);
    }

    /**
     * 根据主键查找车主
     *
     * @param userId
     * @return
     */
    @Override
    public JoUser getById(Long userId) {
        return repository.get(userId);
    }


    @Override
    public synchronized String createMemberNo(Date authTime) {
        String seq = this.repository.getMemberNoSeq();
        log.info("生成会员号SEQ:[{}]", seq);
        if (seq == null) return null;

        Date date = authTime == null ? new Date() : authTime;
        String prefix = DateUtils.format(date, DateUtils.FORMAT_DATE_YYMMDD);
        log.info("生成会员号PREFIX:[{}]", prefix);

        return prefix + seq;
    }
}
