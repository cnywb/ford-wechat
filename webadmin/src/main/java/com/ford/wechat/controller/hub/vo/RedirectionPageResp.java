package com.ford.wechat.controller.hub.vo;

import com.ford.wechat.controller.vo.PageResp;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by wanglijun on 16/11/2.
 */
@Data
@NoArgsConstructor
public class RedirectionPageResp extends PageResp {
    /**
     * 主键
     */
    private Long id;

    /**微信入口状态参数*/
    private String state;

    private String url;

    /**备注说明*/
    private String remark;

}
