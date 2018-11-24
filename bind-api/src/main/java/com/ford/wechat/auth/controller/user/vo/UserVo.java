package com.ford.wechat.auth.controller.user.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Neel on 2017/5/30.
 */
@Data
@NoArgsConstructor
public class UserVo implements Serializable {

    private static final long serialVersionUID = 8630642907435482115L;

    private Long userId;

    private int cars;

    private boolean binding;

    private boolean unbinding;

    private String channelCode;

    private String state = "31";

    private String channelUrl;

    private Integer channelType;

    private String dealer;
}
