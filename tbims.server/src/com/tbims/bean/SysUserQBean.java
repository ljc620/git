package com.tbims.bean;

/**
* Title:  查询表单bean <br/>
* Description: 
* @ClassName: SysUserQBean
* @author ydc
* @date 2017年6月29日 下午6:08:18
* 
*/
public class SysUserQBean {
	private String userId;
	private String userName;
	private String roleId;
	private String userStat;
	private Long outletId;
	private String positionCode;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getUserStat() {
		return userStat;
	}
	public void setUserStat(String userStat) {
		this.userStat = userStat;
	}
	public Long getOutletId() {
		return outletId;
	}
	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}
	public String getPositionCode() {
		return positionCode;
	}
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
}
