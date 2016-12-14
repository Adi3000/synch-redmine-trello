package com.adi3000.code.synch.redminetrello.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="USER_MEMBER")
@SequenceGenerator(name = "user_member_seq", sequenceName = "user_member_seq", allocationSize=1)
public class UserMember {

	private Integer id;
	private Integer userId;
	private String memberId;
	/**
	 * @return the id
	 */
	@Id
	@Column(name="UM_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_member_seq")
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the version
	 */
	@Column(name="USER_ID")
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param version the version to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * @return the labelId
	 */
	@Column(name="MEMBER_ID")
	public String getMemberId() {
		return memberId;
	}
	/**
	 * @param labelId the labelId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}


}
