package com.ford.wechat.entity.member;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * FordMemberForm entity.
 * 认证数据来源表
 * 
 * @author Neel
 */
@Data
@Entity
@Table(name = "FORD_MEMBER_FORM")
public class FordMemberForm {

	// Fields
	@Id
	@Column(name = "VFORM_ID", unique = true, nullable = false, length = 60)
	private String vformId;

	@Column(name = "VFORM_NAME", length = 160)
	private String vformName;

	@Column(name = "VFORM_TEL", length = 40)
	private String vformTel;

	@Column(name = "VVIN", length = 60)
	private String vvin;

	@Column(name = "VCUSTOMER_ID", length = 20)
	private String vcustomerId;

	@Column(name = "VCAR_ID", length = 40)
	private String vcarId;

	@Column(name = "VCONTACTP_ID", length = 20)
	private String vcontactpId;

	@Column(name = "VCREATED", length = 100)
	private String vcreated;

	@Temporal(TemporalType.DATE)
	@Column(name = "DCRT_DATE", length = 7)
	private Date dcrtDate;

	@Column(name = "VUPDATED", length = 100)
	private String vupdated;

	@Temporal(TemporalType.DATE)
	@Column(name = "DUP_DATE", length = 7)
	private Date dupDate;

	@Column(name = "VNOTES", length = 600)
	private String vnotes;

	@Column(name = "VDMS_OWNER_ID", length = 40)
	private String vdmsOwnerId;

}