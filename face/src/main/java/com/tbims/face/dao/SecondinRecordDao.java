package com.tbims.face.dao;

import java.util.List;

import com.tbims.face.entity.SecondinRecord;

public interface SecondinRecordDao {

	public void add(SecondinRecord sr);
	
	public List<SecondinRecord> findByRegid(String regid);
}
