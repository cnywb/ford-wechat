package com.ford.wechat.respository.user;

import com.ford.wechat.entity.user.JcUser;
import io.dabing.core.repository.JpaRepository;

/**
 * Created by Neel on 2017/5/19.
 */
public interface JcUserRepository extends JpaRepository<JcUser, Long> {

    void deleteByUserId(Long userId);

    void updateResetByUserId(Long userId);
}
