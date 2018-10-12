package com.tbims.entity;
// Generated 2017-11-6 14:28:08 by Hibernate Tools 4.0.0

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
	 * 组织机构代码
	 */
	private String orgId;

	/** 
	 * 机构名称
	 */
	private String orgName;

	/** 
	 * 机构类型(0签约社1网络代理商2实体代理商)
	 */
	private String orgType;

	/** 
	 * 地址
	 */
	private String location;

	/** 
	 * 法人代表
	 */
	private String legal;

	/** 
	 * 联系人
	 */
	private String contact;

	/** 
	 * 联系电话
	 */
	private String tel;

	/** 
	 * 信用额度
	 */
	private Long creditAmt;

	/** 
	 * 机构状态(Y正常N停用)
	 */
	private String orgStat;

	/** 
	 * 保证金
	 */
	private Long depositAmt;

	/** 
	 * 预付款余额
	 */
	private Long advanceAmt;

	/** 
	 * 预付款冻结金额
	 */
	private Long advanceFrozeAmt;

	/** 
	 * 操作人
	 */
	private String opeUserId;

	/** 
	 * 操作时间
	 */
	private Date opeTime;

	/** 
	 * 版本号
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

	public SlOrg() {
	}

	public SlOrg(String orgId) {
		this.orgId = orgId;
	}

	public SlOrg(String orgId, String orgName, String orgType, String location, String legal, String contact,
			String tel, Long creditAmt, String orgStat, Long depositAmt, Long advanceAmt, Long advanceFrozeAmt,
			String opeUserId, Date opeTime, Date versionNo, String token, String callbackUrl, String callbackData) {
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
	}

	/** 
	 * 组织机构代码.
	 */
	@Id
	@Column(name = "ORG_ID", unique = true, nullable = false, length = 20)
	public String getOrgId() {
		return this.orgId;
	}

	/** 
	 * 组织机构代码.
	 */
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

	/** 
	 * 机构名称.
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/** 
	 * 机构类型(0签约社1网络代理商2实体代理商).
	 */
	@Column(name = "ORG_TYPE", length = 1)
	public String getOrgType() {
		return this.orgType;
	}

	/** 
	 * 机构类型(0签约社1网络代理商2实体代理商).
	 */
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

	/** 
	 * 地址.
	 */
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

	/** 
	 * 法人代表.
	 */
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

	/** 
	 * 联系人.
	 */
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

	/** 
	 * 联系电话.
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/** 
	 * 信用额度.
	 */
	@Column(name = "CREDIT_AMT", precision = 10)
	public Long getCreditAmt() {
		return this.creditAmt;
	}

	/** 
	 * 信用额度.
	 */
	public void setCreditAmt(Long creditAmt) {
		this.creditAmt = creditAmt;
	}

	/** 
	 * 机构状态(Y正常N停用).
	 */
	@Column(name = "ORG_STAT", length = 1)
	public String getOrgStat() {
		return this.orgStat;
	}

	/** 
	 * 机构状态(Y正常N停用).
	 */
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

	/** 
	 * 保证金.
	 */
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

	/** 
	 * 预付款余额.
	 */
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

	/** 
	 * 预付款冻结金额.
	 */
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

	/** 
	 * 授权码.
	 */
	@Column(name = "TOKEN", length = 100)
	public String getToken() {
		return this.token;
	}

	/** 
	 * 授权码.
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
	@Column(name = "CALLBACK_DATA", length = 100)
	public String getCallbackData() {
		return this.callbackData;
	}

	/** 
	 * 检票回调数据格式.
	 */
	public void setCallbackData(String callbackData) {
		this.callbackData = callbackData;
	}

}
