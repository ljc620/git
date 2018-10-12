package com.tbims.entity;
// Generated 2017-7-4 17:18:05 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 网点表
 */
@Entity
@Table(name = "SYS_OUTLET", schema = "TBIMS")
public class SysOutlet implements java.io.Serializable {

	/**
	 * 网点编号
	 */
	private Long outletId;

	/**
	 * 网点名称
	 */
	private String outletName;

	/**
	 * 地点
	 */
	private String location;

	/**
	 * 负责人
	 */
	private String leader;

	/**
	 * 联系电话
	 */
	private String tel;

	/**
	 * 网点类型(01-现场网点（包括网络代理）、02-自营网点、03-团队换票、04-票务处理、05-实体代理、06-签约社)
	 */
	private String outletType;

	/**
	 * 操作人
	 */
	private String opeUserId;

	/**
	 * 操作时间
	 */
	private Date opeTime;

	/**
	 * 机构编号
	 */
	private String orgId;

	/**
	 * 状态(Y正常N停用)
	 */
	private String stat;

	public SysOutlet() {
	}

	public SysOutlet(Long outletId) {
		this.outletId = outletId;
	}
	public SysOutlet(Long outletId, String outletName, String location, String leader, String tel, String outletType, String opeUserId, Date opeTime, String orgId, String stat) {
		this.outletId = outletId;
		this.outletName = outletName;
		this.location = location;
		this.leader = leader;
		this.tel = tel;
		this.outletType = outletType;
		this.opeUserId = opeUserId;
		this.opeTime = opeTime;
		this.orgId = orgId;
		this.stat = stat;
	}

	/**
	 * 网点编号.
	 */
	@Id
	@Column(name = "OUTLET_ID", unique = true, nullable = false, precision = 6, scale = 0)
	public Long getOutletId() {
		return this.outletId;
	}
	/**
	 * 网点编号.
	 */
	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}

	/**
	 * 网点名称.
	 */
	@Column(name = "OUTLET_NAME", length = 200)
	public String getOutletName() {
		return this.outletName;
	}
	/**
	 * 网点名称.
	 */
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	/**
	 * 地点.
	 */
	@Column(name = "LOCATION", length = 200)
	public String getLocation() {
		return this.location;
	}
	/**
	 * 地点.
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * 负责人.
	 */
	@Column(name = "LEADER", length = 100)
	public String getLeader() {
		return this.leader;
	}
	/**
	 * 负责人.
	 */
	public void setLeader(String leader) {
		this.leader = leader;
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
	 * 网点类型(01-现场网点（包括网络代理）、02-自营网点、03-团队换票、04-票务处理、05-实体代理、06-签约社)
	 */
	@Column(name = "OUTLET_TYPE", length = 2)
	public String getOutletType() {
		return this.outletType;
	}
	/**
	 * 网点类型(01-现场网点（包括网络代理）、02-自营网点、03-团队换票、04-票务处理、05-实体代理、06-签约社)
	 */
	public void setOutletType(String outletType) {
		this.outletType = outletType;
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

	/**
	 * 机构编号.
	 */
	@Column(name = "ORG_ID", length = 50)
	public String getOrgId() {
		return this.orgId;
	}
	/**
	 * 机构编号.
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/**
	 * 状态(Y正常N停用)
	 */
	public String getStat() {
		return stat;
	}
	/**
	 * 状态(Y正常N停用)
	 */
	public void setStat(String stat) {
		this.stat = stat;
	}

}
