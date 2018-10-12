package com.tbims.face.entity;

import java.io.Serializable;

/**
 *设备实体表
 * @author develop3
 *
 */
public class Client implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//id
	private Integer clientId;
	
	//token
	private String token;

	public Client() {
		
	}
	
	public Client(int clientId, String token) {
		this.clientId = clientId;
		this.token = token;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "Client [clientId=" + clientId + ", token=" + token + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
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
		Client other = (Client) obj;
		if (clientId == null) {
			if (other.clientId != null)
				return false;
		} else if (!clientId.equals(other.clientId))
			return false;
		return true;
	}
	
	

}
