package com.ford.wechat.respository.member.impl;

import com.ford.wechat.entity.member.WeUnauthLog;
import com.ford.wechat.respository.member.WeUnauthLogRepository;
import io.dabing.core.repository.DefaultJpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhaoliang on 2017/5/27.
 */
@Repository
public class WeUnauthLogRepositoryImpl extends DefaultJpaRepository<WeUnauthLog, Long> implements WeUnauthLogRepository {
}
