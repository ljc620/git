package com.tbims.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhming.support.common.DateSerializer;


public class SysEmpRegisterBean {
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
	 * 用户名称
	 */
	private String opeUserName;

	/** 
	 * 操作时间
	 */
	private Date opeTime;

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getChipId() {
		return chipId;
	}

	public void setChipId(String chipId) {
		this.chipId = chipId;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getChipStat() {
		return chipStat;
	}

	public void setChipStat(String chipStat) {
		this.chipStat = chipStat;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Date getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Date versionNo) {
		this.versionNo = versionNo;
	}

	public String getOpeUserId() {
		return opeUserId;
	}

	public void setOpeUserId(String opeUserId) {
		this.opeUserId = opeUserId;
	}

	public String getOpeUserName() {
		return opeUserName;
	}

	public void setOpeUserName(String opeUserName) {
		this.opeUserName = opeUserName;
	}
	@JsonSerialize(using = DateSerializer.class)
	@Temporal(TemporalType.DATE)
	@Column(name = "RPT_DATE", length = 7)
	public Date getOpeTime() {
		return opeTime;
	}

	public void setOpeTime(Date opeTime) {
		this.opeTime = opeTime;
	}
	
	
}
