package com.tbims.bean;

public class RoleBean {
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
	
	/**
	* 备注
	*/
	private String remark;
	
	private String checked;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean getChecked() {
		if("true".equals(checked)){
		return true;
		}
		else{
			return false;	
		}
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}



}
