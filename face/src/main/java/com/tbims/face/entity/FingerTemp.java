package com.tbims.face.entity;

import java.io.Serializable;

import com.machinezoo.sourceafis.FingerprintTemplate;

/**
 * 指纹模板实体类
 * @author develop3
 *
 */
public class FingerTemp implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//二次入园登记id
	private String regId;
	
	//指纹模板1
	private FingerprintTemplate fingerid1;
	
	//指纹模板2
	private FingerprintTemplate fingerid2;

	public FingerTemp() {
		
	}
	
	public FingerTemp(String regId, FingerprintTemplate fingerprintTemplate1,
			FingerprintTemplate fingerprintTemplate2) {
		this.regId = regId;
		this.fingerid1 = fingerprintTemplate1;
		this.fingerid2 = fingerprintTemplate2;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public FingerprintTemplate getFingerid1() {
		return fingerid1;
	}

	public void setFingerid1(FingerprintTemplate fingerid1) {
		this.fingerid1 = fingerid1;
	}

	public FingerprintTemplate getFingerid2() {
		return fingerid2;
	}

	public void setFingerid2(FingerprintTemplate fingerid2) {
		this.fingerid2 = fingerid2;
	}

	@Override
	public String toString() {
		return "MyFingerprintTemplate [regId=" + regId + ", fingerid1=" + fingerid1 + ", fingerid2=" + fingerid2 + "]";
	}
	
	
	

}
