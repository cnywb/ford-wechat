package com.ford.wechat.respository.user;

import com.ford.wechat.entity.user.JbUser;
import io.dabing.core.repository.JpaRepository;

/**
 * Created by Neel on 2017/5/19.
 */
public interface JbUserRepository extends JpaRepository<JbUser, Long> {

    void deleteByUserId(Long userId);
}
