/*
 * Copyright (c)  2016
 * All rights reserved.
 * CarOwnerService.java 2016-11-06 下午8:47
 */

package com.ford.wechat.service.members;


import com.ford.wechat.entity.elec.ElecListResponseBody;
import com.ford.wechat.entity.repair.HistoryDetailResponseBody;
import com.ford.wechat.entity.repair.HistoryResponseBody;

public interface CarOwnerService {

    /**
     * 车主认证和绑定，返回状态值
     *
     * @param name
     * @param mobile
     * @param vincode
     * @param openid
     * @param rzbd_state
     * @return
     */
    String carOwnerAuth(String name, String mobile, String vincode, String openid, String rzbd_state);

    HistoryResponseBody getRepairList(String openid);

    HistoryDetailResponseBody getRepairDetail(String openid, String billid);

    ElecListResponseBody getElec(String openid);

}
