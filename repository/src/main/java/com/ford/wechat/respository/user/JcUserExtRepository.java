package com.ford.wechat.respository.user;

import com.ford.wechat.entity.user.JcUserExt;
import io.dabing.core.repository.JpaRepository;

/**
 * Created by Neel on 2017/5/22.
 */
public interface JcUserExtRepository extends JpaRepository<JcUserExt, Long> {
    void deleteByUserId(Long userId);
}
