package com.ford.wechat.entity.user;

import lombok.Data;

import javax.persistence.*;


/**
 * @author Neel
 */
@Data
@Entity
@Table(name = "JC_GROUP")
public class JcGroup {

	@Id
	@GeneratedValue(generator="SEQUENCE",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName="S_JC_GROUP", allocationSize = 1, name ="SEQUENCE")
	@Column(name = "GROUP_ID", unique = true, nullable = false)
	private Long id;
	
	@Column(name="GROUP_NAME")
	private String name;
	
	@Column(name="PRIORITY")
	private Integer priority;
	
	@Column(name="ALLOW_PER_DAY")
	private Integer allowPerDay;
	
	@Column(name="ALLOW_MAX_FILE")
	private Integer allowMaxFile;
	
	@Column(name="ALLOW_SUFFIX")
	private String allowSuffix;
	
	@Column(name="ALLOW_FILE_SIZE")
	private Integer allowFileSize;
	
	@Column(name="ALLOW_FILE_TOTAL")
	private Integer allowFileTotal;
	
	@Column(name="NEED_CAPTCHA")
	private Boolean needCaptcha;
	
	@Column(name="NEED_CHECK")
	private Boolean needCheck;
	
	@Column(name="IS_REG_DEF")
	private Boolean regDef;

	

}
