package com.tbims.db.entity;
// Generated 2017-10-11 15:25:36 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity: 客户端登录缓存
 */
@Entity
@Table(name = "SYS_LOGIN_CACHE", schema = "TBIMS")
public class SysLoginCache implements java.io.Serializable {

	/** 
	 * 客户端编号
	 */
	private Long clientId;

	/** 
	 * 用户账号
	 */
	private String userId;

	/** 
	 * 网点编号
	 */
	private Long outletId;

	/** 
	 * 授权码
	 */
	private String tokey;

	public SysLoginCache() {
	}

	public SysLoginCache(Long clientId) {
		this.clientId = clientId;
	}

	public SysLoginCache(Long clientId, String userId, Long outletId, String tokey) {
		this.clientId = clientId;
		this.userId = userId;
		this.outletId = outletId;
		this.tokey = tokey;
	}

	/** 
	 * 客户端编号.
	 */
	@Id
	@Column(name = "CLIENT_ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getClientId() {
		return this.clientId;
	}

	/** 
	 * 客户端编号.
	 */
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	/** 
	 * 用户账号.
	 */
	@Column(name = "USER_ID", length = 60)
	public String getUserId() {
		return this.userId;
	}

	/** 
	 * 用户账号.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/** 
	 * 网点编号.
	 */
	@Column(name = "OUTLET_ID", precision = 6, scale = 0)
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
	 * 授权码.
	 */
	@Column(name = "TOKEY", length = 200)
	public String getTokey() {
		return this.tokey;
	}

	/** 
	 * 授权码.
	 */
	public void setTokey(String tokey) {
		this.tokey = tokey;
	}

}
