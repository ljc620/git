package com.tbims.bean;

import java.util.List;

/**
 * 
* Title: 客流统计（按区域、票种统计）  <br/>
* Description: 
* @ClassName: CustFlowBean
* @author syq
* @date 2017年7月27日 下午1:52:07
*
 */
public class CustFlowBean {
	private String regionName;//区域名称
	private List<String> ticTypeColName;//票种列名称
	private List<Long> ticTypeNum;//每列对应的票种数量
	private Long totalNum;//总计

	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public List<String> getTicTypeColName() {
		return ticTypeColName;
	}
	public void setTicTypeColName(List<String> ticTypeColName) {
		this.ticTypeColName = ticTypeColName;
	}
	public List<Long> getTicTypeNum() {
		return ticTypeNum;
	}
	public void setTicTypeNum(List<Long> ticTypeNum) {
		this.ticTypeNum = ticTypeNum;
	}
	public Long getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}
	
}
