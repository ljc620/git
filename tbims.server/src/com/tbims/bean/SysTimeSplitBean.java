package com.tbims.bean;

/**
 * Title:  时间段<br/>
 * Description: 
 * @ClassName: SysTimeSplit
 * @author syq
 * @date 2017年7月29日 下午6:15:53
 * 
 */
public class SysTimeSplitBean {
	private Long splitTime;//时间间隔
	private String spec;//时间段描述
	private String startTime;//开始时间
	private String endTime;//结束时间
	public Long getSplitTime() {
		return splitTime;
	}
	public void setSplitTime(Long splitTime) {
		this.splitTime = splitTime;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
