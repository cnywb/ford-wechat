package com.ford.wechat.entity.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;

/**
 * Created by huangwen on 17/11/20.
 */
@JsonAutoDetect
@Entity
@Table(name = "JO_OWNER_QRCODE_URL")
public class OwnerQrCodeUrl implements Serializable {
	private static final long serialVersionUID = 1L;


	/**
     * 物理主键
     */
    @javax.persistence.Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_JO_OWNER_QRCODE_URL_ID")
    @SequenceGenerator(name = "SEQ_JO_OWNER_QRCODE_URL_ID", allocationSize = 1, sequenceName = "SEQ_JO_OWNER_QRCODE_URL_ID")
    private Long id;

    
    @Column(name = "CAMPAIGN_CODE")
    private String campaignCode;

    @Column(name = "CHANNEL_CODE")
    private String channelCode;
    @Id
    @Column(name="REFERRER_VIN")
    private String referrerVin ;


    @Column(name="QRCODE_URL")
    private String qrcodeUrl;

    @Column(name="CREATE_TIME")
    private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCampaignCode() {
		return campaignCode;
	}

	public void setCampaignCode(String campaignCode) {
		this.campaignCode = campaignCode;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getReferrerVin() {
		return referrerVin;
	}

	public void setReferrerVin(String referrerVin) {
		this.referrerVin = referrerVin;
	}

	public String getQrcodeUrl() {
		return qrcodeUrl;
	}

	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
    
}
