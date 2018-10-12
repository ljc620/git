package com.tbims.db.entity;
// Generated 2017-6-17 15:51:21 by Hibernate Tools 4.0.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity: 用户角色明细表
 */
@Entity
@Table(name = "SYS_USER_ROLE", schema = "TBIMS")
public class SysUserRole implements java.io.Serializable {

	/** 
	 * 用户编号.
	 */
	/** 
	* 角色编号.
	*/
	private SysUserRoleId id;

	public SysUserRole() {
	}

	public SysUserRole(SysUserRoleId id) {
		this.id = id;
	}

	/** 
	 * 用户编号.
	 */
	/** 
	* 角色编号.
	*/
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "userId", column = @Column(name = "USER_ID", nullable = false, length = 60)),
			@AttributeOverride(name = "roleId", column = @Column(name = "ROLE_ID", nullable = false, length = 60))})
	public SysUserRoleId getId() {
		return this.id;
	}

	public void setId(SysUserRoleId id) {
		this.id = id;
	}

}
