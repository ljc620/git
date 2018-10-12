package com.tbims.entity;
// Generated 2017-6-17 15:51:21 by Hibernate Tools 4.0.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity: 角色权限明细表
 */
@Entity
@Table(name = "SYS_ROLE_MENU", schema = "TBIMS")
public class SysRoleMenu implements java.io.Serializable {

	/** 
	 * 角色编号.
	 */
	/** 
	* 菜单编号.
	*/
	private SysRoleMenuId id;

	public SysRoleMenu() {
	}

	public SysRoleMenu(SysRoleMenuId id) {
		this.id = id;
	}

	/** 
	 * 角色编号.
	 */
	/** 
	* 菜单编号.
	*/
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "roleId", column = @Column(name = "ROLE_ID", nullable = false, length = 50)),
			@AttributeOverride(name = "menuId", column = @Column(name = "MENU_ID", nullable = false, length = 60))})
	public SysRoleMenuId getId() {
		return this.id;
	}

	public void setId(SysRoleMenuId id) {
		this.id = id;
	}

}
