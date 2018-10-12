package com.tbims.face.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * 二次入园登记实体类
 * @author develop3
 *
 */
public class SecondinRegInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//唯一编号
	private String regId;
	
	//设备编号
	private Integer clientId;
	
	//人脸识别编码
	private String faceId;
	
	//身份证
	private String idcard;
	
	//门票票号
	private Integer tickedId;
	
	//指纹特征1
	private String fingerId1;
	
	//指纹特征2
	private String fingerId2;
	

	
	//采集时间
	private Date insertTime;
	
	public SecondinRegInfo() {
		
	}

	public SecondinRegInfo(String regId) {
		this.regId = regId;
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

	public String getFaceId() {
		return faceId;
	}

	public void setFaceId(String faceId) {
		this.faceId = faceId;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public Integer getTickedId() {
		return tickedId;
	}

	public void setTickedId(Integer tickedId) {
		this.tickedId = tickedId;
	}

	public String getFingerId1() {
		return fingerId1;
	}

	public void setFingerId1(String fingerId1) {
		this.fingerId1 = fingerId1;
	}

	public String getFingerId2() {
		return fingerId2;
	}

	public void setFingerId2(String fingerId2) {
		this.fingerId2 = fingerId2;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	

	

	@Override
	public String toString() {
		return "SecondinRegInfo [regId=" + regId + ", clientId=" + clientId + ", faceId=" + faceId + ", idcard="
				+ idcard + ", tickedId=" + tickedId + ", fingerId1=" + fingerId1 + ", fingerId2=" + fingerId2
				+ ", insertTime=" + insertTime + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((regId == null) ? 0 : regId.hashCode());
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
		SecondinRegInfo other = (SecondinRegInfo) obj;
		if (regId == null) {
			if (other.regId != null)
				return false;
		} else if (!regId.equals(other.regId))
			return false;
		return true;
	}
	
	
	
}
