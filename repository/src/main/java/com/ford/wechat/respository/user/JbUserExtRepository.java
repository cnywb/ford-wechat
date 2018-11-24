package com.ford.wechat.respository.user;

import com.ford.wechat.entity.user.JbUserExt;
import io.dabing.core.repository.JpaRepository;

/**
 * Created by Neel on 2017/5/22.
 */
public interface JbUserExtRepository extends JpaRepository<JbUserExt, Long> {
    void deleteByUserId(Long userId);
}
