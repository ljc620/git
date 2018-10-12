package com.tbims.entity;
// Generated 2017-6-17 15:51:21 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity: 功能菜单表
 */
@Entity
@Table(name = "SYS_MENU", schema = "TBIMS")
public class SysMenu implements java.io.Serializable {

	/** 
	 * 菜单编号.
	 */
	private String menuId;

	/** 
	 * 菜单名称.
	 */
	private String menuName;

	/** 
	 * 父菜单编号.
	 */
	private String menuPid;

	/** 
	 * 菜单状态(Y正常N禁用).
	 */
	private String menuStat;

	/** 
	 * 菜单类型(0菜单1功能2面板).
	 */
	private String menuType;

	/** 
	 * 菜单url.
	 */
	private String menuUrl;

	/** 
	 * 图片图标.
	 */
	private String menuIcon;

	/** 
	 * 顺序号.
	 */
	private Long orderNum;

	/** 
	 * 面板编号.
	 */
	private String menuPanal;
	
	/** 
	 * 菜单内外部类型(0内部，1外部).
	 */
	private String menuClass;
	
	

	public SysMenu() {
	}

	public SysMenu(String menuId) {
		this.menuId = menuId;
	}
	public SysMenu(String menuId, String menuName, String menuPid, String menuStat, String menuType, String menuUrl, String menuIcon, Long orderNum, String menuPanal) {
		this.menuId = menuId;
		this.menuName = menuName;
		this.menuPid = menuPid;
		this.menuStat = menuStat;
		this.menuType = menuType;
		this.menuUrl = menuUrl;
		this.menuIcon = menuIcon;
		this.orderNum = orderNum;
		this.menuPanal = menuPanal;
	}

	/** 
	 * 菜单编号.
	 */
	@Id
	@Column(name = "MENU_ID", unique = true, nullable = false, length = 60)
	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/** 
	 * 菜单名称.
	 */
	@Column(name = "MENU_NAME", length = 100)
	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/** 
	 * 父菜单编号.
	 */
	@Column(name = "MENU_PID", length = 60)
	public String getMenuPid() {
		return this.menuPid;
	}

	public void setMenuPid(String menuPid) {
		this.menuPid = menuPid;
	}

	/** 
	 * 菜单状态(Y正常N禁用).
	 */
	@Column(name = "MENU_STAT", length = 1)
	public String getMenuStat() {
		return this.menuStat;
	}

	public void setMenuStat(String menuStat) {
		this.menuStat = menuStat;
	}

	/** 
	 * 菜单类型(0菜单1功能2面板).
	 */
	@Column(name = "MENU_TYPE", length = 1)
	public String getMenuType() {
		return this.menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	/** 
	 * 菜单url.
	 */
	@Column(name = "MENU_URL", length = 100)
	public String getMenuUrl() {
		return this.menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	/** 
	 * 图片图标.
	 */
	@Column(name = "MENU_ICON", length = 100)
	public String getMenuIcon() {
		return this.menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	/** 
	 * 顺序号.
	 */
	@Column(name = "ORDER_NUM", precision = 5, scale = 0)
	public Long getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	/** 
	 * 面板编号.
	 */
	@Column(name = "MENU_PANAL", length = 60)
	public String getMenuPanal() {
		return this.menuPanal;
	}

	public void setMenuPanal(String menuPanal) {
		this.menuPanal = menuPanal;
	}
	/** 
	 * 菜单内外部类型(0内部，1外部).
	 */
	@Column(name = "MENU_CLASS", length = 1)
	public String getMenuClass() {
		return menuClass;
	}

	public void setMenuClass(String menuClass) {
		this.menuClass = menuClass;
	}
}
