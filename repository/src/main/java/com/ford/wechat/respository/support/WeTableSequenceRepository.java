package com.ford.wechat.respository.support;

import com.ford.wechat.entity.support.WeTableSequence;
import io.dabing.core.repository.JpaRepository;

import java.util.Date;

/**
 * Created by zhaoliang on 2017/6/1.
 */
public interface WeTableSequenceRepository extends JpaRepository<WeTableSequence, Long> {

    /**
     * 根据日期获取指定表名的最新seq值,没个新的日期会从0开始累加
     * @param date 日期
     * @param tableName 表名
     * @return
     */
    WeTableSequence getByTableName(Date date, String tableName);

    WeTableSequence getByTableName(String tableName);

}
