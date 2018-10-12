package com.zhming.support.bean;

import java.util.List;
import java.util.Set;

import com.tbims.entity.SysUser;
import com.zhming.support.BaseBean;

/**
 * Title: 当前登录用户的bean <br/>
 * Description:
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
	 * 岗位名称
	 */
	private String positionName;

	/**
	 * 权限集(用于权限控制)
	 */
	private Set<String> functionSet;
	
	/**
	 * 页面集(用于权限控制)
	 */
	private Set<String> urlSet;

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
	*菜单用，查询统计
	*/
	private String rptList;
	
	/**
	*菜单用，查询统计
	*/
	private String analyList;
	
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

	public String getPositionName() {
		// 岗位(01-票务中心02-网点管理岗03-网点操作岗)
		if ("01".equals(sysUser.getPositionCode())) {
			return "票务中心";
		} else if ("02".equals(sysUser.getPositionCode())) {
			return "网点管理岗";
		} else if ("03".equals(sysUser.getPositionCode())) {
			return "网点操作岗";
		}
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getRptList() {
		return rptList;
	}

	public void setRptList(String rptList) {
		this.rptList = rptList;
	}
	
	public String getAnalyList() {
		return analyList;
	}
	
	public void setAnalyList(String analyList) {
		this.analyList = analyList;
	}

	public Set<String> getUrlSet() {
		return urlSet;
	}

	public void setUrlSet(Set<String> urlSet) {
		this.urlSet = urlSet;
	}

}
