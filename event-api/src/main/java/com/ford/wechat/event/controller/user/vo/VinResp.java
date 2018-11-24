package com.ford.wechat.event.controller.user.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neel on 2017/5/30.
 */
@Data
@NoArgsConstructor
public class VinResp implements Serializable {

    private static final long serialVersionUID = 8630642907435482115L;

    private Long userId;

    /**会员编号*/
    private String memberNo;

    /**活动ID*/
    private Long eventId;

    /**项目编号*/
    private String projectCode;

    /**手机号*/
    private String mobile;

    /**是否已中奖*/
    private boolean involved = false;

    /**用户VIN*/
    private List<String> vinList = new ArrayList<>();
}
