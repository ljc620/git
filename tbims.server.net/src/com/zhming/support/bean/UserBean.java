package com.zhming.support.bean;

import java.util.List;
import java.util.Set;

import com.tbims.entity.SysOutlet;
import com.tbims.entity.SysUser;
import com.zhming.support.BaseBean;

/**
 * Title: 当前登录用户的bean <br/>
 * Description:
 * 
 * @ClassName: UserBean
 * @author ydc
 * @date 2016-1-7 下午5:04:57
 * 
 */
public class UserBean extends BaseBean {
	/**
	 * 用户号
	 */
	private String userId;
	/**
	 * 当前用户
	 */
	private SysUser sysUser;

	/**
	 * 所属网点编号
	 */
	private Long outletId;

	/**
	 * 所属网点
	 */
	private SysOutlet sysOutlet;

	/**
	 * 所属机构
	 */
	private String orgId;

	/**
	 * 所属机构名称
	 */
	private String orgName;
	/**
	 * 权限集(用于权限控制)
	 */
	private Set<String> functionSet;

	/**
	 * 权限集
	 */
	private String functionListStr;

	/**
	 * 系统面板
	 */
	private List<ZTreeJson> panals;
	/**
	 * 菜单用，仓储管理
	 */
	private String deliveryList;
	/**
	 * 菜单用，销售管理
	 */
	private String saleList;
	/**
	 * 菜单用，系统管理
	 */
	private String sysList;
	/**
	 * 菜单用，查询统计
	 */
	private String rptList;
	/**
	 * 当前页号
	 */
	private Integer pageNum;
	/**
	 * 页数
	 */
	private Integer pageSize;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Set<String> getFunctionSet() {
		return functionSet;
	}

	public void setFunctionSet(Set<String> functionSet) {
		this.functionSet = functionSet;
	}

	public List<ZTreeJson> getPanals() {
		return panals;
	}

	public void setPanals(List<ZTreeJson> panals) {
		this.panals = panals;
	}

	public String getDeliveryList() {
		return deliveryList;
	}

	public void setDeliveryList(String deliveryList) {
		this.deliveryList = deliveryList;
	}

	public String getSaleList() {
		return saleList;
	}

	public void setSaleList(String saleList) {
		this.saleList = saleList;
	}

	public String getSysList() {
		return sysList;
	}

	public void setSysList(String sysList) {
		this.sysList = sysList;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public String getFunctionListStr() {
		return functionListStr;
	}

	public void setFunctionListStr(String functionListStr) {
		this.functionListStr = functionListStr;
	}

	public Long getOutletId() {
		return outletId;
	}

	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getRptList() {
		return rptList;
	}

	public void setRptList(String rptList) {
		this.rptList = rptList;
	}

	public SysOutlet getSysOutlet() {
		return sysOutlet;
	}

	public void setSysOutlet(SysOutlet sysOutlet) {
		this.sysOutlet = sysOutlet;
	}

}
