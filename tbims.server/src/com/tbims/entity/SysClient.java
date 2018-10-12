package com.tbims.entity;
// Generated 2017-8-10 18:00:42 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 终端表
 */
@Entity
@Table(name = "SYS_CLIENT", schema = "TBIMS")
public class SysClient implements java.io.Serializable {

	/** 
	 * 终端编号
	 */
	private Long clientId;

	/** 
	 * 终端名称
	 */
	private String clientName;

	/** 
	 * 终端类型(3-缓存服务器1-网点普通终端,2-闸机,4-控制终端,5-自助售票机)
	 */
	private String clientType;

	/** 
	 * 区域编号(场馆,闸机客户端使用)
	 */
	private Long regionId;

	/** 
	 * 网点编号(网点客户端使用)
	 */
	private Long outletId;

	/** 
	 * IP地址(闸机客户端使用)
	 */
	private String ipAddr;

	/** 
	 * 端口(闸机客户端使用)
	 */
	private String port;

	/** 
	 * 状态(Y启用N停用)
	 */
	private String stat;

	/** 
	 * 运行模式0-正常模式,1-紧急模式,2-落杆模式,3-关闭模式,4-A向关闭,B向不控制,5-A向不控制,B向关闭
	 */
	private Long gateMode;

	/** 
	 * 运行状态(1-正常,2-停用,3-压印设备故障,4-闸机异常,5-员工卡读卡器异常,6-票据读卡器异常,7-IO输出异常8-网络异常)
	 */
	private Long runStat;

	/** 
	 * 报告时间
	 */
	private Date reportTime;

	/** 
	 * 授权码(闸机客户端、缓存服务器使用)
	 */
	private String token;

	/** 
	 * 操作人
	 */
	private String opeUserId;

	/** 
	 * 操作时间
	 */
	private Date opeTime;

	/** 
	 * 余票数量(自助售票机用)
	 */
	private String ticketNum;

	public SysClient() {
	}

	public SysClient(Long clientId) {
		this.clientId = clientId;
	}

	public SysClient(Long clientId, String clientName, String clientType, Long regionId, Long outletId, String ipAddr, String port, String stat, Long gateMode, Long runStat, Date reportTime,
			String token, String opeUserId, Date opeTime, String ticketNum) {
		this.clientId = clientId;
		this.clientName = clientName;
		this.clientType = clientType;
		this.regionId = regionId;
		this.outletId = outletId;
		this.ipAddr = ipAddr;
		this.port = port;
		this.stat = stat;
		this.gateMode = gateMode;
		this.runStat = runStat;
		this.reportTime = reportTime;
		this.token = token;
		this.opeUserId = opeUserId;
		this.opeTime = opeTime;
		this.ticketNum = ticketNum;
	}

	/** 
	 * 终端编号.
	 */
	@Id
	@Column(name = "CLIENT_ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getClientId() {
		return this.clientId;
	}

	/** 
	 * 终端编号.
	 */
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	/** 
	 * 终端名称.
	 */
	@Column(name = "CLIENT_NAME", length = 200)
	public String getClientName() {
		return this.clientName;
	}

	/** 
	 * 终端名称.
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	/** 
	 * 终端类型(3-缓存服务器1-网点普通终端,2-闸机,4-控制终端,5-自助售票机).
	 */
	@Column(name = "CLIENT_TYPE", length = 1)
	public String getClientType() {
		return this.clientType;
	}

	/** 
	 * 终端类型(3-缓存服务器1-网点普通终端,2-闸机,4-控制终端,5-自助售票机).
	 */
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	/** 
	 * 区域编号(场馆,闸机客户端使用).
	 */
	@Column(name = "REGION_ID", precision = 6, scale = 0)
	public Long getRegionId() {
		return this.regionId;
	}

	/** 
	 * 区域编号(场馆,闸机客户端使用).
	 */
	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	/** 
	 * 网点编号(网点客户端使用).
	 */
	@Column(name = "OUTLET_ID", precision = 6, scale = 0)
	public Long getOutletId() {
		return this.outletId;
	}

	/** 
	 * 网点编号(网点客户端使用).
	 */
	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}

	/** 
	 * IP地址(闸机客户端使用).
	 */
	@Column(name = "IP_ADDR", length = 20)
	public String getIpAddr() {
		return this.ipAddr;
	}

	/** 
	 * IP地址(闸机客户端使用).
	 */
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	/** 
	 * 端口(闸机客户端使用).
	 */
	@Column(name = "PORT", length = 20)
	public String getPort() {
		return this.port;
	}

	/** 
	 * 端口(闸机客户端使用).
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/** 
	 * 状态(Y启用N停用).
	 */
	@Column(name = "STAT", length = 1)
	public String getStat() {
		return this.stat;
	}

	/** 
	 * 状态(Y启用N停用).
	 */
	public void setStat(String stat) {
		this.stat = stat;
	}

	/** 
	 * 运行模式0-正常模式,1-紧急模式,2-落杆模式,3-关闭模式,4-A向关闭,B向不控制,5-A向不控制,B向关闭.
	 */
	@Column(name = "GATE_MODE", precision = 2, scale = 0)
	public Long getGateMode() {
		return this.gateMode;
	}

	/** 
	 * 运行模式0-正常模式,1-紧急模式,2-落杆模式,3-关闭模式,4-A向关闭,B向不控制,5-A向不控制,B向关闭.
	 */
	public void setGateMode(Long gateMode) {
		this.gateMode = gateMode;
	}

	/** 
	 * 运行状态(1-正常,2-停用,3-压印设备故障,4-闸机异常,5-员工卡读卡器异常,6-票据读卡器异常,7-IO输出异常8-网络异常).
	 */
	@Column(name = "RUN_STAT", precision = 2, scale = 0)
	public Long getRunStat() {
		return this.runStat;
	}

	/** 
	 * 运行状态(1-正常,2-停用,3-压印设备故障,4-闸机异常,5-员工卡读卡器异常,6-票据读卡器异常,7-IO输出异常8-网络异常).
	 */
	public void setRunStat(Long runStat) {
		this.runStat = runStat;
	}

	/** 
	 * 报告时间.
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "REPORT_TIME", length = 7)
	public Date getReportTime() {
		return this.reportTime;
	}

	/** 
	 * 报告时间.
	 */
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	/** 
	 * 授权码(闸机客户端、缓存服务器使用).
	 */
	@Column(name = "TOKEN", length = 100)
	public String getToken() {
		return this.token;
	}

	/** 
	 * 授权码(闸机客户端、缓存服务器使用).
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/** 
	 * 操作人.
	 */
	@Column(name = "OPE_USER_ID", length = 60)
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
	 * 余票数量(自助售票机用) A:数量,B:数量
	 */
	@Column(name = "TICKET_NUM")
	public String getTicketNum() {
		return this.ticketNum;
	}

	/** 
	 * 余票数量(自助售票机用).
	 */
	public void setTicketNum(String ticketNum) {
		this.ticketNum = ticketNum;
	}

}
