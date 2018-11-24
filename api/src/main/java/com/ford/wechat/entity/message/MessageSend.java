package com.ford.wechat.entity.message;

import com.ford.wechat.entity.AuditEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 短信发送记录
 * Created by Neel on 16/11/1.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "WE_MESSAGE_SEND")
public class MessageSend extends AuditEntity {

    public static final String SEND_RESULT_OK = "成功";
    public static final String SEND_RESULT_FAILED = "失败";


    /**
     * 认证激励代金券下发
     */
    public static final String CHANNEL_AUTH_COUPON ="SMS_AUTH_COUPON";


    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WE_MESSAGE_SEND")
    @SequenceGenerator(name = "SEQ_WE_MESSAGE_SEND", allocationSize = 1, sequenceName = "SEQ_WE_MESSAGE_SEND")
    private Long id;


    @Column(name="DATE_NO")
    private String dateNo ;

    /**
     * 项目代码
     */
    @Column(name = "PROJECT_CODE")
    private String projectCode;

    /**
     * 项目名称
     */
    @Column(name = "PROJECT_NAME")
    private String projectName;


    /**
     * 触发方式   如：跑批  个人中心  车主认证
     */
    @Column(name = "SEND_CHANNEL")
    private String sendChannel;

    /**
     * 绑定VIN
     */
    @Column(name = "VIN")
    private String vin;


    /**
     * 绑定OPENID
     */
    @Column(name = "OPEN_ID")
    private String openId;

    /**
     * 会员编号
     */
    @Column(name = "MEMBER_NO")
    private String memberNo;

    /**
     * 会员手机号
     */
    @Column(name = "MOBILE")
    private String mobile;


    /**
     * 发送状态 发送中 已发送
     */
    @Column(name = "STATUS_CODE")
    private String statusCode;

    /**
     * 发送次数
     */
    @Column(name = "SEND_COUNT")
    private int sendCount = 0;


    /**
     * 发送结果  成功  失败
     */
    @Column(name = "SEND_RESULT")
    private String sendResult;

    /**
     * 发送结果提示
     */
    @Column(name = "ERROR_MESSAGE")
    private String errorMessage;


    /**
     * 发送时间
     */
    @Column(name = "SEND_TIME")
    private Date sendTime;

    /**
     * 短信内容
     */
    @Column(name = "CONTENT")
    private String content;


}
