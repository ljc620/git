package com.tbims.entity;
// Generated 2017-6-17 15:51:21 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Entity: 用户角色明细表
 */
@Embeddable
public class SysUserRoleId implements java.io.Serializable {

	/** 
	 * 用户编号.
	 */
	private String userId;

	/** 
	 * 角色编号.
	 */
	private String roleId;

	public SysUserRoleId() {
	}

	public SysUserRoleId(String userId, String roleId) {
		this.userId = userId;
		this.roleId = roleId;
	}

	/** 
	 * 用户编号.
	 */
	@Column(name = "USER_ID", nullable = false, length = 60)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/** 
	 * 角色编号.
	 */
	@Column(name = "ROLE_ID", nullable = false, length = 60)
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SysUserRoleId))
			return false;
		SysUserRoleId castOther = (SysUserRoleId) other;

		return ((this.getUserId() == castOther.getUserId()) || (this.getUserId() != null && castOther.getUserId() != null && this.getUserId().equals(castOther.getUserId())))
				&& ((this.getRoleId() == castOther.getRoleId()) || (this.getRoleId() != null && castOther.getRoleId() != null && this.getRoleId().equals(castOther.getRoleId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result + (getRoleId() == null ? 0 : this.getRoleId().hashCode());
		return result;
	}

}
