package com.tbims.entity;
// Generated 2017-6-17 15:51:21 by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity: 生成序列表
 */
@Entity
@Table(name = "SYS_GEN_ID", schema = "TBIMS")
public class SysGenId implements java.io.Serializable {

	/** 
	 * 规则名称.
	 */
	private String seqRule;

	/** 
	 * 规则值.
	 */
	private BigDecimal seqVal;

	/** 
	 * 备注.
	 */
	private String remark;

	/** 
	 * 前补位数.
	 */
	private Long seqSetting;

	public SysGenId() {
	}

	public SysGenId(String seqRule) {
		this.seqRule = seqRule;
	}
	public SysGenId(String seqRule, BigDecimal seqVal, String remark, Long seqSetting) {
		this.seqRule = seqRule;
		this.seqVal = seqVal;
		this.remark = remark;
		this.seqSetting = seqSetting;
	}

	/** 
	 * 规则名称.
	 */
	@Id
	@Column(name = "SEQ_RULE", unique = true, nullable = false, length = 100)
	public String getSeqRule() {
		return this.seqRule;
	}

	public void setSeqRule(String seqRule) {
		this.seqRule = seqRule;
	}

	/** 
	 * 规则值.
	 */
	@Column(name = "SEQ_VAL", precision = 20, scale = 0)
	public BigDecimal getSeqVal() {
		return this.seqVal;
	}

	public void setSeqVal(BigDecimal seqVal) {
		this.seqVal = seqVal;
	}

	/** 
	 * 备注.
	 */
	@Column(name = "REMARK", length = 512)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/** 
	 * 前补位数.
	 */
	@Column(name = "SEQ_SETTING", precision = 3, scale = 0)
	public Long getSeqSetting() {
		return this.seqSetting;
	}

	public void setSeqSetting(Long seqSetting) {
		this.seqSetting = seqSetting;
	}

}
