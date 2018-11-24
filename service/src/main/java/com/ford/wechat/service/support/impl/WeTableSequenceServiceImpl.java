package com.ford.wechat.service.support.impl;

import com.ford.wechat.entity.support.WeTableSequence;
import com.ford.wechat.respository.support.WeTableSequenceRepository;
import com.ford.wechat.service.support.WeTableSequenceService;
import io.dabing.common.date.DateUtil;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by zhaoliang on 2017/6/1.
 */
@Service
public class WeTableSequenceServiceImpl extends AbstractService implements WeTableSequenceService {
    @Autowired
    private WeTableSequenceRepository repository;
    @Override
    public WeTableSequence getByTableName(Date date, String tableName) {
        return repository.getByTableName(date, tableName);
    }


    /**
     * 根据日期获取指定表名的最新seq值,每个新的日期会从0开始累加
     *
     * @param date      日期
     * @param tableName 表名
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Long doIncrementSeqValue(Date date, String tableName) {
        //日期只精确到天
        String ds = DateUtil.formatDefaultDate(date);
        Date newDate = DateUtil.parser(ds, "yyyy-MM-dd");
        WeTableSequence sequence = repository.getByTableName(newDate, tableName);
        if (sequence == null) {
            sequence = repository.getByTableName(tableName);
            if (sequence != null) {
                //从1重新开始
                Long seqValue = 1L;
                sequence.setSeqValue(seqValue);
                sequence.setCurrentDate(newDate);
                repository.update(sequence);
                return seqValue;
            } else {
                sequence = new WeTableSequence();
                sequence.setSeqValue(1L);
                sequence.setTableName(tableName);
                sequence.setCurrentDate(newDate);
                repository.save(sequence);
                return 1L;
            }
        } else {
            Long seqValue = sequence.getSeqValue();
            seqValue += 1;
            sequence.setSeqValue(seqValue);
            repository.update(sequence);
            return seqValue;
        }
    }
}
