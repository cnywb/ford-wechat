package com.ford.wechat.service.members;

import com.ford.wechat.entity.member.WeUnauthLog;

import java.util.List;

/**
 * Created by zhaoliang on 2017/5/27.
 */
public interface WeUnauthLogService {

    /**
     * 保存
     *
     * @param object
     */
    void save(WeUnauthLog object);

    /**
     * 批量删除
     *
     * @param objectList
     */
    void delete(List<WeUnauthLog> objectList);

    /**
     * 删除
     *
     * @param object
     */
    void delete(WeUnauthLog object);

    /**
     * 根据主键删除
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 更新
     *
     * @param object
     */
    void update(WeUnauthLog object);

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    WeUnauthLog get(Long id);

}
