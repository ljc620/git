package com.tbims.face.dao;

import org.apache.ibatis.annotations.Param;

import com.tbims.face.entity.Client;

public interface ClientDao {
	
	public Client findByClient(Client c);
	
}
