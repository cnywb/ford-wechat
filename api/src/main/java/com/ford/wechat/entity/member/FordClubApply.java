package com.ford.wechat.entity.member;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * FordClubApply entity.
 * 
 * @author Neel
 */
@Data
@Entity
@Table(name = "FORD_CLUB_APPLY")
public class FordClubApply {

	//申请编号
	@Id
	@Column(name = "VAPPLY_ID", unique = true, nullable = false, length = 30)
	private String id;
	//客户名称
	@Column(name = "VNAME", length = 100)
	private String vname;
	//性别
	@Column(name = "VGENDER", length = 30)
	private String vgender;
	//出生日期
	@Column(name = "VBIRTHDAY", length = 30)
	private String vbirthday;
	//证件号码
	@Column(name = "VIDNMB", length = 80)
	private String vidnmb;
	//省
	@Column(name = "VPROVINCE", length = 100)
	private String vprovince;
	//市
	@Column(name = "VCITY", length = 100)
	private String vcity;
	//区
	@Column(name = "VDIST", length = 180)
	private String vdist;
	//地址
	@Column(name = "VADDRESS", length = 200)
	private String vaddress;
	//邮编
	@Column(name = "VZIPCODE", length = 30)
	private String vzipcode;
	//手机
	@Column(name = "VMOBILE", length = 40)
	private String vmobile;
	//座机
	@Column(name = "VTEL", length = 40)
	private String vtel;
	//Email地址
	@Column(name = "VEMAIL", length = 160)
	private String vemail;
	//Vin码
	@Column(name = "VVIN", length = 40)
	private String vvin;
	//牌照号码
	@Column(name = "VLICENSE_NMB", length = 60)
	private String vlicenseNmb;
	//申请次数
	@Column(name = "NAPPLY_TIMES", precision = 10, scale = 0)
	private Long napplyTimes;
	//申请日期(20090101)
	@Column(name = "VAPPLY_DATE", length = 20)
	private String vapplyDate;
	//申请状态(00申请中/01申请成功/02申请失败)
	@Column(name = "VAPPLY_STATUS", length = 4)
	private String vapplyStatus;
	//处理日期(20090101)
	@Column(name = "VCONFIRM_DATE", length = 20)
	private String vconfirmDate;
	//处理结果(00无效车主申请失败/01号码错误申请失败/02无人接听申请失败/03未联系到此人申请失败/04拒绝沟通申请失败/05拒绝加入申请失败/06成功加入)
	@Column(name = "VCONFIRM_RESULT", length = 4)
	private String vconfirmResult;
	//申请来源(01 经销商/02 福域网站/03呼叫中心/04 活动现场/05 其他)
	@Column(name = "VAPPLY_FROM", length = 4)
	private String vapplyFrom;
	//申请来源细分)
	@Column(name = "VAPPLY_SOURCE", length = 40)
	private String vapplySource;
	//备注
	@Column(name = "VNOTES", length = 600)
	private String vnotes;
	//创建人
	@Column(name = "VCREATED", length = 100)
	private String vcreated;
	//创建日期
	@Temporal(TemporalType.DATE)
	@Column(name = "DCRT_DATE", length = 7)
	private Date dcrtDate;
	//更新人
	@Column(name = "VUPDATE", length = 100)
	private String vupdate;
	//更新日期
	@Temporal(TemporalType.DATE)
	@Column(name = "DUP_DATE", length = 7)
	private Date dupDate;
	//公司电话
	@Column(name = "VCOMTEL", length = 40)
	private String vcomtel;
	//01：个人，02：公司
	@Column(name = "VBUY_REASON", length = 4)
	private String vbuyReason;
	//证件类型
	@Column(name = "VIDTYPE", length = 100)
	private String vidtype;
	//网站用户编号
	@Column(name = "VUSERID", length = 20)
	private String vuserid;
	//省编号
	@Column(name = "VPVID", precision = 22, scale = 0)
	private Long vpvid;
	//市编号
	@Column(name = "VCIID", precision = 22, scale = 0)
	private Long vciid;
	@Column(name = "USER_ID")
	private Long userId;
}