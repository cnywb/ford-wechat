package com.ford.wechat.entity.coupon;

import com.ford.wechat.entity.AuditEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 代金券活动配置
 * Created by Neel on 16/11/1.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="WE_EVENT")
public class Event extends AuditEntity{

    public static final String COUPON_RULE_UNIT = "天";

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "SEQ_WE_EVENT")
    @SequenceGenerator(name = "SEQ_WE_EVENT",allocationSize=1,sequenceName = "SEQ_WE_EVENT")
    private Long id;

    /**活动代码*/
    @Column(name="PROJECT_CODE")
    private String projectCode;

    /**活动名称*/
    @Column(name="PROJECT_NAME")
    private String projectName;


    /**活动开始时间*/
    @Column(name="START_TIME")
    private String startTime;

    /**活动结束时间*/
    @Column(name="END_TIME")
    private String endTime;

    /**祝福语*/
    @Column(name="WISHING")
    private String wishing;

    /**活动地址*/
    @Column(name="EVENT_URL")
    private String eventUrl;

    /**备注*/
    @Column(name="REMARK")
    private String remark;

}
