package com.tbims.entity;
// Generated 2017-6-21 9:39:29 by Hibernate Tools 4.0.0

import java.sql.Blob;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 用户表
 */
@Entity
@Table(name = "SYS_USER", schema = "TBIMS")
public class SysUser implements java.io.Serializable {

	/**
	 * 用户编号
	 */
	private String userId;

	/**
	 * 用户名称
	 */
	private String userName;

	/**
	 * 用户密码
	 */
	private String userPassword;

	/**
	 * 岗位(01-票务中心02-网点管理岗03-网点操作岗)
	 */
	private String positionCode;

	/**
	 * 用户状态(N停用Y启用)
	 */
	private String userStat;

	/**
	 * 所属网点
	 */
	private Long outletId;

	/**
	 * 所属部门
	 */
	private String department;

	/**
	 * 证件类型
	 */
	private String cardType;

	/**
	 * 证件号码
	 */
	private String cardId;

	/**
	 * 邮箱地址
	 */
	private String mail;

	/**
	 * 联系电话
	 */
	private String tel;

	/**
	 * 性别
	 */
	private String gender;

	/**
	 * UkeyId
	 */
	private String ukeyId;

	/**
	 * 证书
	 */
	private Blob ukeyCa;

	/**
	 * 操作人
	 */
	private String opeUserId;

	/**
	 * 操作时间
	 */
	private Date opeTime;

	/**
	 * 修改人
	 */
	private String updUserId;

	/**
	 * 修改时间
	 */
	private Date updTime;
	
	/**
	 * 盐
	 */
	private String salt;

	public SysUser() {
	}

	public SysUser(String userId) {
		this.userId = userId;
	}
	public SysUser(String userId, String userName, String userPassword, String positionCode, String userStat, Long outletId, String department, String cardType, String cardId, String mail, String tel, String gender, String ukeyId, Blob ukeyCa, String opeUserId, Date opeTime, String updUserId, Date updTime, String salt) {
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.positionCode = positionCode;
		this.userStat = userStat;
		this.outletId = outletId;
		this.department = department;
		this.cardType = cardType;
		this.cardId = cardId;
		this.mail = mail;
		this.tel = tel;
		this.gender = gender;
		this.ukeyId = ukeyId;
		this.ukeyCa = ukeyCa;
		this.opeUserId = opeUserId;
		this.opeTime = opeTime;
		this.updUserId = updUserId;
		this.updTime = updTime;
		this.salt = salt;
	}

	/**
	 * 用户编号.
	 */
	@Id
	@Column(name = "USER_ID", unique = true, nullable = false, length = 60)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 用户名称.
	 */
	@Column(name = "USER_NAME", length = 100)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 用户密码.
	 */
	@Column(name = "USER_PASSWORD", length = 60)
	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	/**
	 * 岗位(01-票务中心02-网点管理岗03-网点操作岗).
	 */
	@Column(name = "POSITION_CODE", length = 2)
	public String getPositionCode() {
		return this.positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	/**
	 * 用户状态(N停用Y启用).
	 */
	@Column(name = "USER_STAT", length = 1)
	public String getUserStat() {
		return this.userStat;
	}

	public void setUserStat(String userStat) {
		this.userStat = userStat;
	}

	/**
	 * 所属网点.
	 */
	@Column(name = "OUTLET_ID", precision = 6, scale = 0)
	public Long getOutletId() {
		return this.outletId;
	}

	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}

	/**
	 * 所属部门.
	 */
	@Column(name = "DEPARTMENT", length = 200)
	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * 证件类型.
	 */
	@Column(name = "CARD_TYPE", length = 2)
	public String getCardType() {
		return this.cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	/**
	 * 证件号码.
	 */
	@Column(name = "CARD_ID", length = 100)
	public String getCardId() {
		return this.cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	/**
	 * 邮箱地址.
	 */
	@Column(name = "MAIL", length = 200)
	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * 联系电话.
	 */
	@Column(name = "TEL", length = 15)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * 性别.
	 */
	@Column(name = "GENDER", length = 1)
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * UkeyId.
	 */
	@Column(name = "UKEY_ID", length = 50)
	public String getUkeyId() {
		return this.ukeyId;
	}

	public void setUkeyId(String ukeyId) {
		this.ukeyId = ukeyId;
	}

	/**
	 * 证书.
	 */
	@Column(name = "UKEY_CA")
	public Blob getUkeyCa() {
		return this.ukeyCa;
	}

	public void setUkeyCa(Blob ukeyCa) {
		this.ukeyCa = ukeyCa;
	}

	/**
	 * 操作人.
	 */
	@Column(name = "OPE_USER_ID", length = 50)
	public String getOpeUserId() {
		return this.opeUserId;
	}

	public void setOpeUserId(String opeUserId) {
		this.opeUserId = opeUserId;
	}

	/**
	 * 操作时间.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "OPE_TIME", length = 7)
	public Date getOpeTime() {
		return this.opeTime;
	}

	public void setOpeTime(Date opeTime) {
		this.opeTime = opeTime;
	}

	/**
	 * 修改人.
	 */
	@Column(name = "UPD_USER_ID", length = 50)
	public String getUpdUserId() {
		return this.updUserId;
	}

	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}

	/**
	 * 修改时间.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPD_TIME", length = 7)
	public Date getUpdTime() {
		return this.updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}
	
	/**
	 * 盐
	 */
	@Column(name = "SALT", length = 50)
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
}
