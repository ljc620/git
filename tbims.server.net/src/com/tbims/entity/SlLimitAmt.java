package com.tbims.entity;
// Generated 2017-6-17 15:51:21 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 额度余额表
 */
@Entity
@Table(name = "SL_LIMIT_AMT", schema = "TBIMS")
public class SlLimitAmt implements java.io.Serializable {

	/** 
	 * 机构编号.
	 */
	/** 
	* 票种编号.
	*/
	private SlLimitAmtId id;

	/** 
	 * 额度余额.
	 */
	private Long limitAmt;

	/** 
	 * 冻结额度.
	 */
	private Long frozeLimit;

	/** 
	 * 操作人.
	 */
	private String optUserId;

	/** 
	 * 操作时间.
	 */
	private Date opeTime;


	public SlLimitAmt() {
	}

	public SlLimitAmt(SlLimitAmtId id) {
		this.id = id;
	}
	public SlLimitAmt(SlLimitAmtId id, Long limitAmt, Long frozeLimit, String optUserId, Date opeTime) {
		this.id = id;
		this.limitAmt = limitAmt;
		this.frozeLimit = frozeLimit;
		this.optUserId = optUserId;
		this.opeTime = opeTime;
	}

	/** 
	 * 机构编号.
	 */
	/** 
	* 票种编号.
	*/
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "orgId", column = @Column(name = "ORG_ID", nullable = false, length = 50)),
			@AttributeOverride(name = "ticketTypeId", column = @Column(name = "TICKET_TYPE_ID", nullable = false, length = 3))})
	public SlLimitAmtId getId() {
		return this.id;
	}

	public void setId(SlLimitAmtId id) {
		this.id = id;
	}

	/** 
	 * 额度余额.
	 */
	@Column(name = "LIMIT_AMT", precision = 12, scale = 0)
	public Long getLimitAmt() {
		return this.limitAmt;
	}

	public void setLimitAmt(Long limitAmt) {
		this.limitAmt = limitAmt;
	}

	/** 
	 * 冻结额度.
	 */
	@Column(name = "FROZE_LIMIT", precision = 12, scale = 0)
	public Long getFrozeLimit() {
		return this.frozeLimit;
	}

	public void setFrozeLimit(Long frozeLimit) {
		this.frozeLimit = frozeLimit;
	}

	/** 
	 * 操作人.
	 */
	@Column(name = "OPT_USER_ID", length = 50)
	public String getOptUserId() {
		return this.optUserId;
	}

	public void setOptUserId(String optUserId) {
		this.optUserId = optUserId;
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



}
