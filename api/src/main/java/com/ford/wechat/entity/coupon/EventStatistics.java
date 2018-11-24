package com.ford.wechat.entity.coupon;

import com.ford.wechat.entity.AuditEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 认证激励统计
 * Created by Neel on 16/11/1.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "WE_EVENT_STATISTICS")
public class EventStatistics extends AuditEntity {


    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WE_EVENT_STATISTICS")
    @SequenceGenerator(name = "SEQ_WE_EVENT_STATISTICS", allocationSize = 1, sequenceName = "SEQ_WE_EVENT_STATISTICS")
    private Long id;

    /**
     * 批次号
     */
    @Column(name="BATCH_NO")
    private String batchNo ;

    /**
     * 时间批次
     */
    @Column(name="DATE_NO")
    private String dateNo ;

    /**
     * 活动代码
     */
    @Column(name = "PROJECT_CODE")
    private String projectCode;

    /**
     * 活动名称
     */
    @Column(name = "PROJECT_NAME")
    private String projectName;

    /**
     * 每日访问人数
     */
    @Column(name = "VISIT_COUNT")
    private Integer visitCount = 0;

    /**
     * 每次人抽奖人数
     */
    @Column(name = "DRAW_COUNT")
    private Integer drawCount = 0;

    /**
     * 短信发送数
     */
    @Column(name = "SMS_SEND_COUNT")
    private Integer smsSendCount = 0;

    /**
     * 短信发送失败数量
     */
    @Column(name = "SMS_FAILED_COUNT")
    private Integer smsFailedCount = 0;


    /**
     * 每日认证人数
     */
    @Column(name = "AUTHEN_COUNT")
    private Integer authenCount = 0;


    /**
     * 因活动认证人数
     */
    @Column(name = "AUTHEN_COUNT_WITH_EVENT")
    private Integer authenCountWithEvent = 0;

    /**
     * 抽中人数
     */
    @Column(name = "WINNER_COUNT")
    private Integer winnerCount = 0;

    /**
     * 老认证用户获券人数
     */
    @Column(name = "ORIGINAL_WINNER_COUNT")
    private Integer originalWinnerCount = 0;


}
