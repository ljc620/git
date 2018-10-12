package com.tbims.bean;

import java.util.List;

/**
 * 
* Title: 客流量统计（按区域、时间段统计）  <br/>
* Description: 
* @ClassName: CustFlowTimeBean
* @author syq
* @date 2017年7月27日 下午1:52:07
*
 */
public class CustFlowTimeBean {
	private String regionName;//区域名称
	private String ticketTypeName;//票种名称
	private List<String> splitName;//时间段列名称
	private List<Long> ticTypeNum;//每列对应的数量
	private Long totalNum;//总计

	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public List<String> getSplitName() {
		return splitName;
	}
	public void setSplitName(List<String> splitName) {
		this.splitName = splitName;
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
	public String getTicketTypeName() {
		return ticketTypeName;
	}
	public void setTicketTypeName(String ticketTypeName) {
		this.ticketTypeName = ticketTypeName;
	}
}
