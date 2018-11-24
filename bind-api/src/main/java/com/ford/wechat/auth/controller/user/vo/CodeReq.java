package com.ford.wechat.auth.controller.user.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Neel on 2017/5/9.
 */

@Data
@NoArgsConstructor
public class CodeReq {

    private String mobile;

    private String code;

}
