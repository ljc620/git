package com.tbims.db.entity;
// Generated 2017-6-26 15:59:15 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 参数表
 */
@Entity
@Table(name = "SYS_PAREMETER", schema = "TBIMS")
public class SysParemeter implements java.io.Serializable {

	/** 
	 * 销售参数ID
	 */
	private String paremeterId;

	/** 
	 * 参数名称
	 */
	private String paremeterName;

	/** 
	 * 参数值
	 */
	private String paremeterVal;

	/** 
	 * 操作人
	 */
	private String opeUserId;

	/** 
	 * 操作时间
	 */
	private Date opeTime;

	/** 
	 * 参数类型(0销售参数1非销售参数)
	 */
	private String paremeterType;

	/** 
	 * 版本号
	 */
	private Date versionNo;

	public SysParemeter() {
	}

	public SysParemeter(String paremeterId) {
		this.paremeterId = paremeterId;
	}
	public SysParemeter(String paremeterId, String paremeterName, String paremeterVal, String opeUserId, Date opeTime, String paremeterType, Date versionNo) {
		this.paremeterId = paremeterId;
		this.paremeterName = paremeterName;
		this.paremeterVal = paremeterVal;
		this.opeUserId = opeUserId;
		this.opeTime = opeTime;
		this.paremeterType = paremeterType;
		this.versionNo = versionNo;
	}

	/** 
	 * 销售参数ID.
	 */
	@Id
	@Column(name = "PAREMETER_ID", unique = true, nullable = false, length = 60)
	public String getParemeterId() {
		return this.paremeterId;
	}
	/** 
	 * 销售参数ID.
	 */
	public void setParemeterId(String paremeterId) {
		this.paremeterId = paremeterId;
	}

	/** 
	 * 参数名称.
	 */
	@Column(name = "PAREMETER_NAME", length = 100)
	public String getParemeterName() {
		return this.paremeterName;
	}
	/** 
	 * 参数名称.
	 */
	public void setParemeterName(String paremeterName) {
		this.paremeterName = paremeterName;
	}

	/** 
	 * 参数值.
	 */
	@Column(name = "PAREMETER_VAL", length = 100)
	public String getParemeterVal() {
		return this.paremeterVal;
	}
	/** 
	 * 参数值.
	 */
	public void setParemeterVal(String paremeterVal) {
		this.paremeterVal = paremeterVal;
	}

	/** 
	 * 操作人.
	 */
	@Column(name = "OPE_USER_ID", length = 50)
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
	@Temporal(TemporalType.DATE)
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

	/** 
	 * 参数类型(0销售参数1非销售参数).
	 */
	@Column(name = "PAREMETER_TYPE", length = 1)
	public String getParemeterType() {
		return this.paremeterType;
	}
	/** 
	 * 参数类型(0销售参数1非销售参数).
	 */
	public void setParemeterType(String paremeterType) {
		this.paremeterType = paremeterType;
	}

	/** 
	 * 版本号.
	 */
	@Temporal(TemporalType.DATE)
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

}
