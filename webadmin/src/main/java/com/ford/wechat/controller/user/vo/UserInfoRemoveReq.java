package com.ford.wechat.controller.user.vo;

import java.util.List;

/**
 * Created by wanglijun on 16/11/2.
 */
public class UserInfoRemoveReq {

    private List<UserInfoRemoveVo> dataVo;

    public List<UserInfoRemoveVo> getDataVo() {
        return dataVo;
    }

    public void setDataVo(List<UserInfoRemoveVo> dataVo) {
        this.dataVo = dataVo;
    }
}
