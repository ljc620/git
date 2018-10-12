package com.tbims.entity;
// Generated 2017-7-11 9:37:01 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Entity: 每日检票信息统计
 */
@Embeddable
public class RptCheckticketinfoDId implements java.io.Serializable {

	/** 
	 * 统计日期
	 */
	private Date rptDate;

	/** 
	 * 终端编号
	 */
	private Long clientId;

	public RptCheckticketinfoDId() {
	}

	public RptCheckticketinfoDId(Date rptDate, Long clientId) {
		this.rptDate = rptDate;
		this.clientId = clientId;
	}

	/** 
	 * 统计日期.
	 */
	@Column(name = "RPT_DATE", nullable = false, length = 7)
	public Date getRptDate() {
		return this.rptDate;
	}
	/** 
	 * 统计日期.
	 */
	public void setRptDate(Date rptDate) {
		this.rptDate = rptDate;
	}

	/** 
	 * 终端编号.
	 */
	@Column(name = "CLIENT_ID", nullable = false, precision = 10, scale = 0)
	public Long getClientId() {
		return this.clientId;
	}
	/** 
	 * 终端编号.
	 */
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RptCheckticketinfoDId))
			return false;
		RptCheckticketinfoDId castOther = (RptCheckticketinfoDId) other;

		return ((this.getRptDate() == castOther.getRptDate()) || (this.getRptDate() != null && castOther.getRptDate() != null && this.getRptDate().equals(castOther.getRptDate())))
				&& ((this.getClientId() == castOther.getClientId()) || (this.getClientId() != null && castOther.getClientId() != null && this.getClientId().equals(castOther.getClientId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getRptDate() == null ? 0 : this.getRptDate().hashCode());
		result = 37 * result + (getClientId() == null ? 0 : this.getClientId().hashCode());
		return result;
	}

}
