package com.ford.wechat.respository.user;

import com.ford.wechat.entity.user.JcGroup;
import io.dabing.core.repository.JpaRepository;

/**
 * Created by Neel on 2017/5/19.
 */
public interface JcGroupRepository extends JpaRepository<JcGroup, Long> {

    JcGroup findByRegDef();
}
