package com.tbims.entity;
// Generated 2017-6-28 13:30:25 by Hibernate Tools 4.0.0

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 员工卡登记表
 */
@Entity
@Table(name = "SYS_EMP_REGISTER", schema = "TBIMS")
public class SysEmpRegister implements java.io.Serializable {

	/** 
	 * 员工编号
	 */
	private Long empId;

	/** 
	 * 员工名称
	 */
	private String empName;

	/** 
	 * 部门
	 */
	private String department;

	/** 
	 * 照片信息
	 */
	private byte[] photo;

	/** 
	 * 芯片ID
	 */
	private String chipId;

	/** 
	 * 员工状态(Y启用N禁用)
	 */
	private String stat;

	/** 
	 * 卡片状态(Y可用N作废)
	 */
	private String chipStat;

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
	 * 性别
	 */
	private String gender;

	/** 
	 * 联系电话
	 */
	private String tel;

	/** 
	 * 版本号
	 */
	private Date versionNo;

	/** 
	 * 操作人
	 */
	private String opeUserId;

	/** 
	 * 操作时间
	 */
	private Date opeTime;

	private List<SysEmpVenue> sysEmpVenues = new ArrayList<SysEmpVenue>(0);

	public SysEmpRegister() {
	}

	public SysEmpRegister(Long empId) {
		this.empId = empId;
	}
	public SysEmpRegister(Long empId, String empName, String department, byte[] photo, String chipId, String stat, String chipStat, String cardType, String cardId, String mail, String gender, String tel, Date versionNo, String opeUserId, Date opeTime, List<SysEmpVenue> sysEmpVenues) {
		this.empId = empId;
		this.empName = empName;
		this.department = department;
		this.photo = photo;
		this.chipId = chipId;
		this.stat = stat;
		this.chipStat = chipStat;
		this.cardType = cardType;
		this.cardId = cardId;
		this.mail = mail;
		this.gender = gender;
		this.tel = tel;
		this.versionNo = versionNo;
		this.opeUserId = opeUserId;
		this.opeTime = opeTime;
		this.sysEmpVenues = sysEmpVenues;
	}

	/** 
	 * 员工编号.
	 */
	@Id
	@Column(name = "EMP_ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getEmpId() {
		return this.empId;
	}
	/** 
	 * 员工编号.
	 */
	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	/** 
	 * 员工名称.
	 */
	@Column(name = "EMP_NAME", length = 100)
	public String getEmpName() {
		return this.empName;
	}
	/** 
	 * 员工名称.
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	/** 
	 * 部门.
	 */
	@Column(name = "DEPARTMENT", length = 100)
	public String getDepartment() {
		return this.department;
	}
	/** 
	 * 部门.
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/** 
	 * 照片信息.
	 */
	@Column(name = "PHOTO")
	public byte[] getPhoto() {
		return this.photo;
	}
	/** 
	 * 照片信息.
	 */
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	/** 
	 * 芯片ID.
	 */
	@Column(name = "CHIP_ID", length = 20)
	public String getChipId() {
		return this.chipId;
	}
	/** 
	 * 芯片ID.
	 */
	public void setChipId(String chipId) {
		this.chipId = chipId;
	}

	/** 
	 * 员工状态(Y启用N禁用).
	 */
	@Column(name = "STAT", length = 1)
	public String getStat() {
		return this.stat;
	}
	/** 
	 * 员工状态(Y启用N禁用).
	 */
	public void setStat(String stat) {
		this.stat = stat;
	}

	/** 
	 * 卡片状态(Y可用N作废).
	 */
	@Column(name = "CHIP_STAT", length = 1)
	public String getChipStat() {
		return this.chipStat;
	}
	/** 
	 * 卡片状态(Y可用N作废).
	 */
	public void setChipStat(String chipStat) {
		this.chipStat = chipStat;
	}

	/** 
	 * 证件类型.
	 */
	@Column(name = "CARD_TYPE", length = 2)
	public String getCardType() {
		return this.cardType;
	}
	/** 
	 * 证件类型.
	 */
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
	/** 
	 * 证件号码.
	 */
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
	/** 
	 * 邮箱地址.
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/** 
	 * 性别.
	 */
	@Column(name = "GENDER", length = 1)
	public String getGender() {
		return this.gender;
	}
	/** 
	 * 性别.
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/** 
	 * 联系电话.
	 */
	@Column(name = "TEL", length = 15)
	public String getTel() {
		return this.tel;
	}
	/** 
	 * 联系电话.
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/** 
	 * 版本号.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "VERSION_NO", length = 7)
	public Date getVersionNo() {
		return this.versionNo;
	}
	/** 
	 * 版本号.
	 */
	public void setVersionNo(Date versionNo) {
		this.versionNo = versionNo;
	}

	/** 
	 * 操作人.
	 */
	@Column(name = "OPE_USER_ID", length = 60)
	public String getOpeUserId() {
		return this.opeUserId;
	}
	/** 
	 * 操作人.
	 */
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
	/** 
	 * 操作时间.
	 */
	public void setOpeTime(Date opeTime) {
		this.opeTime = opeTime;
	}

	@OneToMany(fetch = FetchType.EAGER,cascade={CascadeType.ALL},  mappedBy = "sysEmpRegister")
	public List<SysEmpVenue> getSysEmpVenues() {
		return this.sysEmpVenues;
	}

	public void setSysEmpVenues(List<SysEmpVenue> sysEmpVenues) {
		this.sysEmpVenues = sysEmpVenues;
	}

}
