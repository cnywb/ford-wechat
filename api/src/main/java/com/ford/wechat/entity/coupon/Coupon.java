package com.ford.wechat.entity.coupon;

import com.ford.wechat.entity.AuditEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 代金券
 * Created by Neel on 16/11/1.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "WE_COUPON")
public class Coupon extends AuditEntity implements Serializable {

    private static final long serialVersionUID = 8630642907435482333L;

    public static final int BATCH_STATUS_INIT = 0;
    public static final int BATCH_STATUS_PROCESSED = 1;

    public static final int STATUS_INIT = 0;
    public static final int STATUS_RECEIVING = 1;
    public static final int STATUS_RECEIVED = 2;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WE_COUPON")
    @SequenceGenerator(name = "SEQ_WE_COUPON", allocationSize = 1, sequenceName = "SEQ_WE_COUPON")
    private Long id;

    /**时间批次  yyyyMMdd*/
    @Column(name="DATE_NO")
    private String dateNo ;

    /**批处理批次号*/
    @Column(name="BATCH_NO")
    private String batchNo;

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
     * 代金券编号
     */
    @Column(name = "COUPON_NO")
    private String couponNo;

    /**
     * 金额
     */
    @Column(name = "AMOUNT")
    private Integer amount = 0;

    /**
     * 门槛金额
     */
    @Column(name = "LOWEST_AMOUNT")
    private Integer lowestAmount;

    /**
     * 目标（责任）经销商服务代码
     */
    @Column(name = "DEALER_CODE")
    private String dealerCode;

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
     * 中奖Vin 的认证时间
     */
    @Column(name = "AUTH_TIME")
    private Date authTime;

    /**
     * 代金券领用时间
     */
    @Column(name = "RECEIVED_TIME")
    private Date receivedTime;

    /**
     * 状态  0 未领用  1 领用中  2 已领用
     */
    @Column(name = "STATUS")
    private int status = 0;

    /**
     * 批处理状态  0 未处理  1 已处理
     */
    @Column(name = "BATCH_STATUS")
    private int batchStatus = 0;

    /**
     * 代金券开始日期 yyyy-MM-dd
     */
    @Column(name = "START_TIME")
    private String startTime;

    /**
     * 代金券失效日期 yyyy-MM-dd
     */
    @Column(name = "EXPIRED_TIME")
    private String expiredTime;

    /**
     * 祝福语
     */
    @Column(name = "WISHING")
    private String wishing;

}
