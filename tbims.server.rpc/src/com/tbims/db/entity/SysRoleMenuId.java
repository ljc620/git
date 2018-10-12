package com.tbims.db.entity;
// Generated 2017-6-17 15:51:21 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Entity: 角色权限明细表
 */
@Embeddable
public class SysRoleMenuId implements java.io.Serializable {

	/** 
	 * 角色编号.
	 */
	private String roleId;

	/** 
	 * 菜单编号.
	 */
	private String menuId;

	public SysRoleMenuId() {
	}

	public SysRoleMenuId(String roleId, String menuId) {
		this.roleId = roleId;
		this.menuId = menuId;
	}

	/** 
	 * 角色编号.
	 */
	@Column(name = "ROLE_ID", nullable = false, length = 50)
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/** 
	 * 菜单编号.
	 */
	@Column(name = "MENU_ID", nullable = false, length = 60)
	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SysRoleMenuId))
			return false;
		SysRoleMenuId castOther = (SysRoleMenuId) other;

		return ((this.getRoleId() == castOther.getRoleId()) || (this.getRoleId() != null && castOther.getRoleId() != null && this.getRoleId().equals(castOther.getRoleId())))
				&& ((this.getMenuId() == castOther.getMenuId()) || (this.getMenuId() != null && castOther.getMenuId() != null && this.getMenuId().equals(castOther.getMenuId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getRoleId() == null ? 0 : this.getRoleId().hashCode());
		result = 37 * result + (getMenuId() == null ? 0 : this.getMenuId().hashCode());
		return result;
	}

}
