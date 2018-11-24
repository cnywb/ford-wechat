package com.ford.wechat.entity.coupon;

import com.ford.wechat.entity.AuditEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 代金券页面访问日志
 * Created by Neel on 16/11/1.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "WE_EVENT_OPERATION_RECORD")
public class EventOperationRecord extends AuditEntity {

    public static final String COUPON_RULE_UNIT = "天";

    public static final int OPERATION_TYPE_VISIT = 0;
    public static final int OPERATION_TYPE_DRAW = 1;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WE_EVENT_OPERATION_RECORD")
    @SequenceGenerator(name = "SEQ_WE_EVENT_OPERATION_RECORD", allocationSize = 1, sequenceName = "SEQ_WE_EVENT_OPERATION_RECORD")
    private Long id;

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
     * 绑定OPENID
     */
    @Column(name = "OPEN_ID")
    private String openId;

    /**
     * 操作类型  0 访问页面  1 点击抽奖
     */
    @Column(name = "OPERATION_TYPE")
    private Integer operationType = 0;


    /**
     * 最后记录时间
     */
    @Column(name = "LAST_OPERATION_TIME")
    private Date lastOperationTime;


}
