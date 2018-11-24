package com.ford.wechat.entity.token;

import com.ford.wechat.entity.BaseEntity;

/**
 * Created by wanglijun on 16/10/22.
 */
public class AccessToken extends BaseEntity {

    /**ID*/
    private  Long id;

    private String accessToken;

    private Integer expiesIn;

    /***
     * 主键方法
     *
     * @return
     */
    @Override
    public Long getId() {
        return null;
    }
}
