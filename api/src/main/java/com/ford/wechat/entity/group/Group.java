package com.ford.wechat.entity.group;

import com.ford.wechat.entity.VersionEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 微信用户组（增删改查）
 * Created by wanglijun on 16/11/2.
 */
@Entity
@Table(name="WE_GROUP")
public class Group  extends VersionEntity {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "SEQ_WE_GROUP_ID")
    @SequenceGenerator(name = "SEQ_WE_GROUP_ID",allocationSize=1,sequenceName = "SEQ_WE_GROUP_ID")
    private Long id;

    /**微信openId*/
    @Column(name="OPEN_ID")
    private String openId;
    /**用户姓名*/
    @Column(name="USER_NAME")
    private String name;
    /**用户手机*/
    @Column(name="USER_MOBILE")
    private String mobile;
    /**意向车型编号也即粉丝分组编号*/
    @Column(name="FAVOUR_CAR_CODE")
    private String favourCarCode;

    /**意向车型名称也即粉丝分组名称*/
    @Column(name="FAVOUR_CAR_NAME")
    private String favourCarName;
    /***
     * 购车时间
     */
    @Column(name="BUY_TIME")
    private Date buyDate;
    /**省*/
    @Column(name="PROVINCE")
    private String province;
    /**经销商*/
    @Column(name="CITY")
    private String city;

    /**经销商*/
    @Column(name="DEALER")
    private String dealer;

    /**经销商编号*/
    @Column(name="DEALER_NO")
    private String dealerNo;

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getDealerNo() {
        return dealerNo;
    }

    public void setDealerNo(String dealerNo) {
        this.dealerNo = dealerNo;
    }

    public String getFavourCarCode() {
        return favourCarCode;
    }

    public void setFavourCarCode(String favourCarCode) {
        this.favourCarCode = favourCarCode;
    }

    public String getFavourCarName() {
        return favourCarName;
    }

    public void setFavourCarName(String favourCarName) {
        this.favourCarName = favourCarName;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }


    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
