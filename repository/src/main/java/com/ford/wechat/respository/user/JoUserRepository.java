package com.ford.wechat.respository.user;

import com.ford.wechat.entity.user.JoUser;
import io.dabing.core.repository.JpaRepository;

import java.util.List;

/**
 * Created by wanglijun on 16/11/20.
 */
public interface JoUserRepository extends JpaRepository<JoUser, Long> {
    /**
     * 根据openid查询是否已经有认证的vin码
     *
     * @param openId
     * @return
     */
    int getCountByWechatId(String openId);

    /**
     * 根据openid查询刚刚通过扫描经销商认证渠道的用户
     *
     * @param openId
     * @return
     */
    JoUser getJoUserByWechatId(String openId);

    JoUser getByMobile(String mobile);

    List<JoUser> getList(String openId);

    List<JoUser> getList(String openId, String vin);

    void updateResetByUserId(Long userId, String openId);

    JoUser getByUserIdNotOpenId(Long userId, String openId);

    String getMemberNoSeq();
}
