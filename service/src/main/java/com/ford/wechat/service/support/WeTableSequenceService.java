package com.ford.wechat.service.support;

import com.ford.wechat.entity.support.WeTableSequence;

import java.util.Date;

/**
 * Created by zhaoliang on 2017/6/1.
 */
public interface WeTableSequenceService {
    /**
     * 根据日期获取指定表名的最新seq值,没个新的日期会从0开始累加
     * @param date 日期
     * @param tableName 表名
     * @return
     */
    WeTableSequence getByTableName(Date date, String tableName);

    /**
     * 根据日期获取指定表名的最新seq值,没个新的日期会从0开始累加
     * @param date 日期
     * @param tableName 表名
     * @return
     */
    Long doIncrementSeqValue(Date date, String tableName);
}
