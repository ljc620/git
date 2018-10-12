package com.tbims.entity;
// Generated 2017-6-20 9:28:31 by Hibernate Tools 4.0.0

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

	public SysParemeter() {
	}

	public SysParemeter(String paremeterId) {
		this.paremeterId = paremeterId;
	}
	public SysParemeter(String paremeterId, String paremeterName, String paremeterVal, String opeUserId, Date opeTime, String paremeterType) {
		this.paremeterId = paremeterId;
		this.paremeterName = paremeterName;
		this.paremeterVal = paremeterVal;
		this.opeUserId = opeUserId;
		this.opeTime = opeTime;
		this.paremeterType = paremeterType;
	}

	/** 
	 * 销售参数ID.
	 */
	@Id
	@Column(name = "PAREMETER_ID", unique = true, nullable = false, length = 60)
	public String getParemeterId() {
		return this.paremeterId;
	}

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
	 * 参数类型(0销售参数1非销售参数).
	 */
	@Column(name = "PAREMETER_TYPE", length = 1)
	public String getParemeterType() {
		return this.paremeterType;
	}

	public void setParemeterType(String paremeterType) {
		this.paremeterType = paremeterType;
	}

}
