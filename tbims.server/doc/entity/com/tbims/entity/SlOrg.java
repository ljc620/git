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
 * Entity: ������
 */
@Entity
@Table(name = "SL_ORG", schema = "TBIMS")
public class SlOrg implements java.io.Serializable {

	/** 
	 * ��֯��������
	 */
	private String orgId;

	/** 
	 * ��������
	 */
	private String orgName;

	/** 
	 * ��������(0ǩԼ��1���������2ʵ�������)
	 */
	private String orgType;

	/** 
	 * ��ַ
	 */
	private String location;

	/** 
	 * ���˴���
	 */
	private String legal;

	/** 
	 * ��ϵ��
	 */
	private String contact;

	/** 
	 * ��ϵ�绰
	 */
	private String tel;

	/** 
	 * ���ö��
	 */
	private Long creditAmt;

	/** 
	 * ����״̬(Y����Nͣ��)
	 */
	private String orgStat;

	/** 
	 * ��֤��
	 */
	private Long depositAmt;

	/** 
	 * Ԥ�������
	 */
	private Long advanceAmt;

	/** 
	 * Ԥ�������
	 */
	private Long advanceFrozeAmt;

	/** 
	 * ������
	 */
	private String opeUserId;

	/** 
	 * ����ʱ��
	 */
	private Date opeTime;

	/** 
	 * �汾��
	 */
	private Date versionNo;

	/** 
	 * ��Ȩ��
	 */
	private String token;

	/** 
	 * ��Ʊ�ص���ַ
	 */
	private String callbackUrl;

	/** 
	 * ��Ʊ�ص����ݸ�ʽ
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
	 * ��֯��������.
	 */
	@Id
	@Column(name = "ORG_ID", unique = true, nullable = false, length = 20)
	public String getOrgId() {
		return this.orgId;
	}

	/** 
	 * ��֯��������.
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/** 
	 * ��������.
	 */
	@Column(name = "ORG_NAME")
	public String getOrgName() {
		return this.orgName;
	}

	/** 
	 * ��������.
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/** 
	 * ��������(0ǩԼ��1���������2ʵ�������).
	 */
	@Column(name = "ORG_TYPE", length = 1)
	public String getOrgType() {
		return this.orgType;
	}

	/** 
	 * ��������(0ǩԼ��1���������2ʵ�������).
	 */
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	/** 
	 * ��ַ.
	 */
	@Column(name = "LOCATION")
	public String getLocation() {
		return this.location;
	}

	/** 
	 * ��ַ.
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/** 
	 * ���˴���.
	 */
	@Column(name = "LEGAL", length = 50)
	public String getLegal() {
		return this.legal;
	}

	/** 
	 * ���˴���.
	 */
	public void setLegal(String legal) {
		this.legal = legal;
	}

	/** 
	 * ��ϵ��.
	 */
	@Column(name = "CONTACT", length = 50)
	public String getContact() {
		return this.contact;
	}

	/** 
	 * ��ϵ��.
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	/** 
	 * ��ϵ�绰.
	 */
	@Column(name = "TEL", length = 20)
	public String getTel() {
		return this.tel;
	}

	/** 
	 * ��ϵ�绰.
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/** 
	 * ���ö��.
	 */
	@Column(name = "CREDIT_AMT", precision = 10)
	public Long getCreditAmt() {
		return this.creditAmt;
	}

	/** 
	 * ���ö��.
	 */
	public void setCreditAmt(Long creditAmt) {
		this.creditAmt = creditAmt;
	}

	/** 
	 * ����״̬(Y����Nͣ��).
	 */
	@Column(name = "ORG_STAT", length = 1)
	public String getOrgStat() {
		return this.orgStat;
	}

	/** 
	 * ����״̬(Y����Nͣ��).
	 */
	public void setOrgStat(String orgStat) {
		this.orgStat = orgStat;
	}

	/** 
	 * ��֤��.
	 */
	@Column(name = "DEPOSIT_AMT", precision = 10, scale = 0)
	public Long getDepositAmt() {
		return this.depositAmt;
	}

	/** 
	 * ��֤��.
	 */
	public void setDepositAmt(Long depositAmt) {
		this.depositAmt = depositAmt;
	}

	/** 
	 * Ԥ�������.
	 */
	@Column(name = "ADVANCE_AMT", precision = 10, scale = 0)
	public Long getAdvanceAmt() {
		return this.advanceAmt;
	}

	/** 
	 * Ԥ�������.
	 */
	public void setAdvanceAmt(Long advanceAmt) {
		this.advanceAmt = advanceAmt;
	}

	/** 
	 * Ԥ�������.
	 */
	@Column(name = "ADVANCE_FROZE_AMT", precision = 10, scale = 0)
	public Long getAdvanceFrozeAmt() {
		return this.advanceFrozeAmt;
	}

	/** 
	 * Ԥ�������.
	 */
	public void setAdvanceFrozeAmt(Long advanceFrozeAmt) {
		this.advanceFrozeAmt = advanceFrozeAmt;
	}

	/** 
	 * ������.
	 */
	@Column(name = "OPE_USER_ID", length = 50)
	public String getOpeUserId() {
		return this.opeUserId;
	}

	/** 
	 * ������.
	 */
	public void setOpeUserId(String opeUserId) {
		this.opeUserId = opeUserId;
	}

	/** 
	 * ����ʱ��.
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "OPE_TIME", length = 7)
	public Date getOpeTime() {
		return this.opeTime;
	}

	/** 
	 * ����ʱ��.
	 */
	public void setOpeTime(Date opeTime) {
		this.opeTime = opeTime;
	}

	/** 
	 * �汾��.
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "VERSION_NO", length = 7)
	public Date getVersionNo() {
		return this.versionNo;
	}

	/** 
	 * �汾��.
	 */
	public void setVersionNo(Date versionNo) {
		this.versionNo = versionNo;
	}

	/** 
	 * ��Ȩ��.
	 */
	@Column(name = "TOKEN", length = 100)
	public String getToken() {
		return this.token;
	}

	/** 
	 * ��Ȩ��.
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/** 
	 * ��Ʊ�ص���ַ.
	 */
	@Column(name = "CALLBACK_URL", length = 200)
	public String getCallbackUrl() {
		return this.callbackUrl;
	}

	/** 
	 * ��Ʊ�ص���ַ.
	 */
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	/** 
	 * ��Ʊ�ص����ݸ�ʽ.
	 */
	@Column(name = "CALLBACK_DATA", length = 100)
	public String getCallbackData() {
		return this.callbackData;
	}

	/** 
	 * ��Ʊ�ص����ݸ�ʽ.
	 */
	public void setCallbackData(String callbackData) {
		this.callbackData = callbackData;
	}

}
