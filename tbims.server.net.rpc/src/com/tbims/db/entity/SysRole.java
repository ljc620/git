package com.tbims.db.entity;
// Generated 2017-6-17 15:51:21 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity: 角色表
 */
@Entity
@Table(name = "SYS_ROLE", schema = "TBIMS")
public class SysRole implements java.io.Serializable {

	/** 
	 * 角色编号.
	 */
	private String roleId;

	/** 
	 * 角色名称.
	 */
	private String roleName;

	/** 
	 * 角色类型(0内部角色1外部角色).
	 */
	private String roleType;

	public SysRole() {
	}

	public SysRole(String roleId) {
		this.roleId = roleId;
	}
	public SysRole(String roleId, String roleName, String roleType) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleType = roleType;
	}

	/** 
	 * 角色编号.
	 */
	@Id
	@Column(name = "ROLE_ID", unique = true, nullable = false, length = 50)
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/** 
	 * 角色名称.
	 */
	@Column(name = "ROLE_NAME", length = 100)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/** 
	 * 角色类型(0内部角色1外部角色).
	 */
	@Column(name = "ROLE_TYPE", length = 2)
	public String getRoleType() {
		return this.roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

}
