package com.ford.wechat.entity.bbs;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Neel
 */
@Data
@Entity
@Table(name = "BBS_CONFIG")
public class BbsConfig {


	@Id
	@Column(name = "CONFIG_ID", unique = true, nullable = false)
	private Long id;
	
	
	@Column(name="DEF_AVATAR")
	private String defAvatar;
	
	@Column(name="AVATAR_WIDTH")
	private Integer avatarWidth;
	
	@Column(name="AVATAR_HEIGHT")
	private Integer avatarHeight;
	
	@Column(name="TOPIC_COUNT_PER_PAGE")
	private Integer topicCountPerPage;
	
	@Column(name="POST_COUNT_PER_PAGE")
	private Integer postCountPerPage;
	
	@Column(name="KEYWORDS")
	private String keywords;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="REGISTER_STATUS")
	private Short registerStatus;
	
	@Column(name="REGISTER_RULE")
	private String registerRule;
	
	@Column(name="TOPIC_HOT_COUNT")
	private Integer topicHotCount;
	
	@Column(name="CACHE_POST_TODAY")
	private Integer postToday;
	
	@Column(name="CACHE_POST_YESTERDAY")
	private Integer postYesterday;
	
	@Column(name="CACHE_POST_MAX")
	private Integer postMax;
	
	@Column(name="CACHE_POST_MAX_DATE")
	private Date postMaxDate;
	
	@Column(name="CACHE_TOPIC_TOTAL")
	private Integer topicTotal;
	
	@Column(name="CACHE_POST_TOTAL")
	private Integer postTotal;
	
	@Column(name="CACHE_USER_TOTAL")
	private Integer userTotal;
	
	@Column(name="AUTO_REGISTER")
	private Boolean autoRegister;
	
	@Column(name="EMAIL_VALIDATE")
	private Boolean emailValidate;
	
	@Column(name="LAST_USER_ID")
	private Long lastUserId;
	
	@Column(name="REGISTER_GROUP_ID")
	private Long registerGroupId;
	
	@Column(name="DEFAULT_GROUP_ID")
	private Long defaultGroupId;

}
