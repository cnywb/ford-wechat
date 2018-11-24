package com.ford.wechat.respository.bbs.impl;

import com.ford.wechat.entity.bbs.BbsConfig;
import com.ford.wechat.entity.user.JbUserGroup;
import com.ford.wechat.respository.bbs.BbsConfigRepository;
import com.ford.wechat.respository.user.JbGroupRepository;
import io.dabing.core.repository.DefaultJpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Neel on 2017/5/21.
 */
@Repository
public class BbsConfigRepositoryImpl extends DefaultJpaRepository<BbsConfig, Long> implements BbsConfigRepository {



}
