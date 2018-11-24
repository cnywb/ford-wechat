package com.ford.wechat.entity.wireframe;

import com.ford.wechat.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by lc on 2017/9/18.
 */
@Data
@Entity
@Table(name = "WIRE_FRAME_APPOINTMENT")
public class Appointment {

    /*主键ID*/
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "ORDER_NO")
    private String orderNo;

    /*VIN码*/
    @Column(name = "VIN")
    private String vin;

    /*车型*/
    @Column(name = "CAR_TYPE")
    private String carType;

    /*行驶里程数*/
    @Column(name = "MILES")
    private String miles;

    /*经销商名称*/
    @Column(name = "DEALER_NAME")
    private String dealerName;

    @Column(name = "DEALER_SERVICE_CODE")
    private String dealerServiceCode;

    /*预约日期*/
    @Column(name = "APPOINT_TIME")
    private String appointDate;

    /*预约时间段,小时时间段*/
    @Column(name = "APPOINT_HOUR")
    private String appointHour;

    /*服务顾问*/
    @Column(name = "SERVICE_ADVISER")
    private String serviceAdviser;

    /*服务类型*/
    @Column(name = "SERVICE_TYPE")
    private String serviceType;

    /*备注*/
    @Column(name = "COMMENT")
    private String comment;

    /*联系人*/
    @Column(name = "CONTACT_NAME")
    private String contactName;

    /*联系电话*/
    @Column(name ="CONTACT_PHONE")
    private String contactPhone;

    /*预约状态*/
    @Column(name = "APPOINT_STATUS")
    private String appointStatus;

    /*首次时间*/
    @Column(name = "FIRST_INSERT")
    private Date firstInsertDate=new Date();

    /*修改时间*/
    @Column(name = "MODIFY_DATE")
    private  Date modifyDate;

    /*是否删除 0 否  1 是*/
    @Column(name = "DELETED")
    private Integer deleted=0;

    @Column(name = "DELETE_TIME")
    private String deleteTime;


}
