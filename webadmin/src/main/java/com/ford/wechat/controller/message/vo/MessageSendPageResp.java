package com.ford.wechat.controller.message.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhaoliang on 2017/8/27.
 */
@Data
public class MessageSendPageResp {

    /**主键 */
    private Long id;

    /**时间批次  yyyyMMdd*/
    private String dateNo ;

    /**
     * 项目代码
     */
    private String projectCode;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 触发方式   如：跑批  个人中心  车主认证
     */
    private String sendChannel;

    /**
     * 绑定VIN
     */
    private String vin;

    /**
     * 绑定OPENID
     */
    private String openId;

    /**
     * 会员编号
     */
    private String memberNo;

    /**
     * 会员手机号
     */
    private String mobile;

    /**
     * 发送状态
     */
    private String statusCode;

    /**
     * 发送结果  成功  失败
     */
    private String sendResult;

    /**
     * 发送结果提示
     */
    private String sendMessage;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 短信内容
     */
    private String content;

}
