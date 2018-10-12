package com.tbims.face.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tbims.face.entity.SecondinRegInfo;

public interface SecondinRegInfoDao {

	public List<SecondinRegInfo> find(@Param("sri")SecondinRegInfo sri,@Param("invalidNum")Integer invalidNum);

	public void save(SecondinRegInfo sri);
	
	//批量更新时间
	public void updateInsetTime(Map<String,Object> pars);
	
	//查询所有未失效指纹
	public List<Map<String,Object>> findAllFinger(Integer invalidNum);
	



	
}
