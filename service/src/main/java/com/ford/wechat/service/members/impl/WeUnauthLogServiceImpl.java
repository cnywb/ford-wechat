package com.ford.wechat.service.members.impl;

import com.ford.wechat.entity.member.WeUnauthLog;
import com.ford.wechat.respository.member.WeUnauthLogRepository;
import com.ford.wechat.service.members.WeUnauthLogService;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by zhaoliang on 2017/5/27.
 */
@Service
public class WeUnauthLogServiceImpl extends AbstractService implements WeUnauthLogService {
    @Autowired
    private WeUnauthLogRepository repository;

    @Override
    public void save(WeUnauthLog object) {
        repository.save(object);
    }

    @Override
    public void delete(List<WeUnauthLog> objectList) {
        repository.delete(objectList);
    }

    @Override
    public void delete(WeUnauthLog object) {
        repository.delete(object);
    }

    @Override
    public void delete(Long id) {
       repository.delete(id);
    }

    @Override
    public void update(WeUnauthLog object) {
       repository.update(object);
    }

    @Override
    public WeUnauthLog get(Long id) {
        return repository.get(id);
    }
}
