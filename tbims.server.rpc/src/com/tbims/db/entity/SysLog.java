package com.tbims.db.entity;
// Generated 2017-6-24 17:26:01 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity: 系统日志表
 */
@Entity
@Table(name = "SYS_LOG", schema = "TBIMS")
public class SysLog implements java.io.Serializable {

	/** 
	 * 日志代码
	 */
	private String logId;

	/** 
	 * 终端编号
	 */
	private Long clientId;

	/** 
	 * 用户代码
	 */
	private String userId;

	/** 
	 * 用户名称
	 */
	private String userName;

	/** 
	 * 操作类型 操作类型 (1-增 2-删 3-改 4-查 5-定时触发)
	 */
	private Long logType;

	/** 
	 * 功能代码
	 */
	private String menuId;

	/** 
	 * 模块名称
	 */
	private String menuName;

	/** 
	 * 操作内容
	 */
	private String logCnt;

	/** 
	 * 操作时间
	 */
	private String logTime;

	/** 
	 * 执行状态 0-成功 1-失败 msg.properties
	 */
	private String logStat;

	/** 
	 * 状态信息 成功信息或失败信息
	 */
	private String vldDesc;

	public SysLog() {
	}

	public SysLog(String logId, Long clientId) {
		this.logId = logId;
		this.clientId = clientId;
	}
	public SysLog(String logId, Long clientId, String userId, String userName, Long logType, String menuId, String menuName, String logCnt, String logTime, String logStat, String vldDesc) {
		this.logId = logId;
		this.clientId = clientId;
		this.userId = userId;
		this.userName = userName;
		this.logType = logType;
		this.menuId = menuId;
		this.menuName = menuName;
		this.logCnt = logCnt;
		this.logTime = logTime;
		this.logStat = logStat;
		this.vldDesc = vldDesc;
	}

	/** 
	 * 日志代码.
	 */
	@Id
	@Column(name = "LOG_ID", unique = true, nullable = false, length = 60)
	public String getLogId() {
		return this.logId;
	}
	/** 
	 * 日志代码.
	 */
	public void setLogId(String logId) {
		this.logId = logId;
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

	/** 
	 * 用户代码.
	 */
	@Column(name = "USER_ID", length = 100)
	public String getUserId() {
		return this.userId;
	}
	/** 
	 * 用户代码.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/** 
	 * 用户名称.
	 */
	@Column(name = "USER_NAME", length = 100)
	public String getUserName() {
		return this.userName;
	}
	/** 
	 * 用户名称.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/** 
	 * 操作类型 操作类型 (1-增 2-删 3-改 4-查 5-定时触发).
	 */
	@Column(name = "LOG_TYPE", precision = 1, scale = 0)
	public Long getLogType() {
		return this.logType;
	}
	/** 
	 * 操作类型 操作类型 (1-增 2-删 3-改 4-查 5-定时触发).
	 */
	public void setLogType(Long logType) {
		this.logType = logType;
	}

	/** 
	 * 功能代码.
	 */
	@Column(name = "MENU_ID", length = 100)
	public String getMenuId() {
		return this.menuId;
	}
	/** 
	 * 功能代码.
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/** 
	 * 模块名称.
	 */
	@Column(name = "MENU_NAME", length = 100)
	public String getMenuName() {
		return this.menuName;
	}
	/** 
	 * 模块名称.
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/** 
	 * 操作内容.
	 */
	@Column(name = "LOG_CNT", length = 3000)
	public String getLogCnt() {
		return this.logCnt;
	}
	/** 
	 * 操作内容.
	 */
	public void setLogCnt(String logCnt) {
		this.logCnt = logCnt;
	}

	/** 
	 * 操作时间.
	 */
	@Column(name = "LOG_TIME", length = 25)
	public String getLogTime() {
		return this.logTime;
	}
	/** 
	 * 操作时间.
	 */
	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}

	/** 
	 * 执行状态 0-成功 1-失败 msg.properties.
	 */
	@Column(name = "LOG_STAT", length = 30)
	public String getLogStat() {
		return this.logStat;
	}
	/** 
	 * 执行状态 0-成功 1-失败 msg.properties.
	 */
	public void setLogStat(String logStat) {
		this.logStat = logStat;
	}

	/** 
	 * 状态信息 成功信息或失败信息.
	 */
	@Column(name = "VLD_DESC", length = 1024)
	public String getVldDesc() {
		return this.vldDesc;
	}
	/** 
	 * 状态信息 成功信息或失败信息.
	 */
	public void setVldDesc(String vldDesc) {
		this.vldDesc = vldDesc;
	}

}
