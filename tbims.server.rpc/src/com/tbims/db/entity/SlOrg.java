package com.tbims.db.entity;
// Generated 2017-6-17 15:51:21 by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 机构表
 */
@Entity
@Table(name = "SL_ORG", schema = "TBIMS")
public class SlOrg implements java.io.Serializable {

	/** 
	 * 组织机构代码.
	 */
	private String orgId;

	/** 
	 * 机构名称.
	 */
	private String orgName;

	/** 
	 * 机构类型(0签约社1代理商).
	 */
	private String orgType;

	/** 
	 * 地址.
	 */
	private String location;

	/** 
	 * 法人代表.
	 */
	private String legal;

	/** 
	 * 联系人.
	 */
	private String contact;

	/** 
	 * 联系电话.
	 */
	private String tel;

	/** 
	 * 信用额度.
	 */
	private BigDecimal creditAmt;

	/** 
	 * 机构状态(Y正常N停用)
	 */
	private String orgStat;

	/** 
	 * 保证金.
	 */
	private Long depositAmt;

	/** 
	 * 预付款余额.
	 */
	private Long advanceAmt;

	/** 
	 * 预付款冻结金额.
	 */
	private Long advanceFrozeAmt;

	/** 
	 * 操作人.
	 */
	private String opeUserId;

	/** 
	 * 操作时间.
	 */
	private Date opeTime;

	/** 
	 * 版本号.
	 */
	private Date versionNo;
	
	/** 
	 * 授权码
	 */
	private String token;
	

	/** 
	 * 检票回调地址
	 */
	private String callbackUrl;

	/** 
	 * 检票回调数据格式
	 */
	private String callbackData;

	/**
	*回调失败计数
	*/
	private Long callbackErrorCount;
	
	public SlOrg() {
	}

	public SlOrg(String orgId) {
		this.orgId = orgId;
	}
	public SlOrg(String orgId, String orgName, String orgType, String location, String legal, String contact, String tel, BigDecimal creditAmt, String orgStat, Long depositAmt, Long advanceAmt, Long advanceFrozeAmt, String opeUserId, Date opeTime, Date versionNo,String token, String callbackUrl, String callbackData,Long callbackErrorCount) {
		this.orgId = orgId;
		this.orgName = orgName;
		this.orgType = orgType;
		this.location = location;
		this.legal = legal;
		this.contact = contact;
		this.tel = tel;
		this.creditAmt = creditAmt;
		this.orgStat = orgStat;
		this.depositAmt = depositAmt;
		this.advanceAmt = advanceAmt;
		this.advanceFrozeAmt = advanceFrozeAmt;
		this.opeUserId = opeUserId;
		this.opeTime = opeTime;
		this.versionNo = versionNo;
		this.token = token;
		this.callbackUrl = callbackUrl;
		this.callbackData = callbackData;
		this.callbackErrorCount=callbackErrorCount;
	}

	/** 
	 * 组织机构代码.
	 */
	@Id
	@Column(name = "ORG_ID", unique = true, nullable = false, length = 20)
	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/** 
	 * 机构名称.
	 */
	@Column(name = "ORG_NAME")
	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/** 
	 * 机构类型(0签约社1代理商).
	 */
	@Column(name = "ORG_TYPE", length = 1)
	public String getOrgType() {
		return this.orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	/** 
	 * 地址.
	 */
	@Column(name = "LOCATION")
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	/** 
	 * 法人代表.
	 */
	@Column(name = "LEGAL", length = 50)
	public String getLegal() {
		return this.legal;
	}

	public void setLegal(String legal) {
		this.legal = legal;
	}

	/** 
	 * 联系人.
	 */
	@Column(name = "CONTACT", length = 50)
	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	/** 
	 * 联系电话.
	 */
	@Column(name = "TEL", length = 20)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	/** 
	 * 信用额度.
	 */
	@Column(name = "CREDIT_AMT", precision = 10)
	public BigDecimal getCreditAmt() {
		return this.creditAmt;
	}

	public void setCreditAmt(BigDecimal creditAmt) {
		this.creditAmt = creditAmt;
	}

	/** 
	 * 机构状态(0正常1停用).
	 */
	@Column(name = "ORG_STAT", length = 1)
	public String getOrgStat() {
		return this.orgStat;
	}

	public void setOrgStat(String orgStat) {
		this.orgStat = orgStat;
	}

	/** 
	 * 保证金.
	 */
	@Column(name = "DEPOSIT_AMT", precision = 10, scale = 0)
	public Long getDepositAmt() {
		return this.depositAmt;
	}

	public void setDepositAmt(Long depositAmt) {
		this.depositAmt = depositAmt;
	}

	/** 
	 * 预付款余额.
	 */
	@Column(name = "ADVANCE_AMT", precision = 10, scale = 0)
	public Long getAdvanceAmt() {
		return this.advanceAmt;
	}

	public void setAdvanceAmt(Long advanceAmt) {
		this.advanceAmt = advanceAmt;
	}

	/** 
	 * 预付款冻结金额.
	 */
	@Column(name = "ADVANCE_FROZE_AMT", precision = 10, scale = 0)
	public Long getAdvanceFrozeAmt() {
		return this.advanceFrozeAmt;
	}

	public void setAdvanceFrozeAmt(Long advanceFrozeAmt) {
		this.advanceFrozeAmt = advanceFrozeAmt;
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
	 * 版本号.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "VERSION_NO", length = 7)
	public Date getVersionNo() {
		return this.versionNo;
	}

	public void setVersionNo(Date versionNo) {
		this.versionNo = versionNo;
	}

	/** 
	 * 授权码
	 */
	@Column(name = "TOKEN", length = 100)
	public String getToken() {
		return token;
	}
	/** 
	 * 授权码
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
	/** 
	 * 检票回调地址.
	 */
	@Column(name = "CALLBACK_URL", length = 200)
	public String getCallbackUrl() {
		return this.callbackUrl;
	}

	/** 
	 * 检票回调地址.
	 */
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	/** 
	 * 检票回调数据格式.
	 */
	@Column(name = "CALLBACK_DATA", length = 500)
	public String getCallbackData() {
		return this.callbackData;
	}

	/** 
	 * 检票回调数据格式.
	 */
	public void setCallbackData(String callbackData) {
		this.callbackData = callbackData;
	}
	
	/** 
	* 回调失败计数
	*/ 
	@Column(name = "CALLBACK_ERROR_COUNT")
	public Long getCallbackErrorCount() {
		return callbackErrorCount;
	}

	/** 
	* 回调失败计数
	*/ 
	public void setCallbackErrorCount(Long callbackErrorCount) {
		this.callbackErrorCount = callbackErrorCount;
	}

}
