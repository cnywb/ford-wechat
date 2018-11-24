package com.ford.wechat.entity.user;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Neel
 */
@Data
@Entity
@Table(name = "BBS_USER_GROUP")
public class JbUserGroup {

	
	@Id
	@GeneratedValue(generator="SEQUENCE",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName="s_BBS_USER_GROUP", name ="SEQUENCE")
	@Column(name = "GROUP_ID", unique = true, nullable = false)
	private Long id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="GROUP_TYPE")
	private String type;
	
	@Column(name="GROUP_IMG")
	private String imgPath;
	
	@Column(name="GROUP_POINT")
	private Long point;
	
	@Column(name="IS_DEFAULT")
	private Boolean isDefault;
	
	@Column(name="GRADE_NUM")
	private Integer gradeNum;
	
	@Column(name="MAGIC_PACKET_SIZE")
	private Integer magicPacketSize;

}
