package com.tbims.face.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 二次入园比对记录实体类
 * @author develop3
 *
 */
public class SecondinRecord implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//采集表唯一编号
	private String recordId;
	
	//登记编号
	private String regId;
	
	//设备编号
	private Integer clientId;
	
	//比对方式 （0：人脸 1：指纹：2：门票 3：身份证）
	private Integer matchType;
	
	//比对时间
	private Date macthTime;
	
	//比对得分
	private Integer macthScort;

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Integer getMatchType() {
		return matchType;
	}

	public void setMatchType(Integer matchType) {
		this.matchType = matchType;
	}

	public Date getMacthTime() {
		return macthTime;
	}

	public void setMacthTime(Date macthTime) {
		this.macthTime = macthTime;
	}

	public Integer getMacthScort() {
		return macthScort;
	}

	public void setMacthScort(Integer macthScort) {
		this.macthScort = macthScort;
	}

	@Override
	public String toString() {
		return "SecondinRecord [recordId=" + recordId + ", regId=" + regId + ", clientId=" + clientId + ", matchType="
				+ matchType + ", macthTime=" + macthTime + ", macthScort=" + macthScort + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((recordId == null) ? 0 : recordId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SecondinRecord other = (SecondinRecord) obj;
		if (recordId == null) {
			if (other.recordId != null)
				return false;
		} else if (!recordId.equals(other.recordId))
			return false;
		return true;
	}
	
	
	
	

}
