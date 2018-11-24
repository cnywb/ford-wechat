package com.ford.wechat.respository.user;

import com.ford.wechat.entity.user.JbUserGroup;
import io.dabing.core.repository.JpaRepository;

/**
 * Created by Neel on 2017/5/19.
 */
public interface JbGroupRepository extends JpaRepository<JbUserGroup, Long> {

    JbUserGroup findByRegDef();
}
