package com.ford.wechat.service.user;

import com.ford.wechat.entity.user.JoUser;

import java.util.Date;

/**
 * Created by wanglijun on 16/11/20.
 */
public interface JoUserService {
    JoUser createForWeiXin(String username, String password, String ip, String mobile);

    void update(JoUser jouser);

    int getCountByWechatId(String openid);

    JoUser getJoUserByWechatId(String openid);

    JoUser getByMobile(String mobile);

    /**
     * 根据主键查找车主
     * @param userId
     * @return
     */
    JoUser getById(Long userId);

    String createMemberNo(Date authTime);
}
