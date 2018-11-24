package com.ford.wechat.entity.coupon;

import com.ford.wechat.entity.AuditEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Date;

/**
 * 代金券活动配置
 * Created by Neel on 16/11/1.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="WE_EVENT_DETAIL")
public class EventDetail extends AuditEntity{

    public static final String COUPON_RULE_UNIT = "天";

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "SEQ_WE_EVENT_DETAIL")
    @SequenceGenerator(name = "SEQ_WE_EVENT_DETAIL",allocationSize=1,sequenceName = "SEQ_WE_EVENT_DETAIL")
    private Long id;

    /**时间批次  yyyyMMdd*/
    @Column(name="DATE_NO")
    private String dateNo ;

    /**活动代码*/
    @Column(name="PROJECT_CODE")
    private String projectCode;

    /**活动名称*/
    @Column(name="PROJECT_NAME")
    private String projectName;

    /**最大额度*/
    @Column(name="MAX")
    private Integer max = 0;

    /**最小额度*/
    @Column(name="MIN")
    private Integer min = 0;

    /**总金额*/
    @Column(name="TOTAL_AMOUNT")
    private Integer totalAmount = 0;

    /**份数*/
    @Column(name="COUNT")
    private Integer count = 0;

    /**门槛金额 界面暂时不需要配置*/
    @Column(name="LOWEST_AMOUNT")
    private Integer lowestAmount = 0;

    /**每日开始时间 HH-mm-ss*/
    @Column(name="START_TIME")
    private Date startTime;

    /**每日结束时间 HH-mm-ss*/
    @Column(name="END_TIME")
    private Date endTime;

    /**代金券有效天数*/
    @Column(name="VALID_TIMES")
    private int validTimes = 31;

    /**祝福语*/
    @Column(name="WISHING")
    private String wishing;

    /**备注*/
    @Column(name="REMARK")
    private String remark;

}
