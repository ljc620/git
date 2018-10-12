package com.tbims.bean;

import com.tbims.db.entity.SysClient;
import com.tbims.db.entity.SysOutlet;
import com.tbims.db.entity.SysUser;
import com.tbims.rpc.entity.AUTHORIZATION;

/**
 * Title:缓存授权信息bean <br/>
 * Description:
 * @ClassName: AuthBean
 * @author ydc
 * @date 2017年6月24日 下午3:38:15
 * 
 */
public class AuthBean {
	/**
	 * 授权信息（网点客户端、缓存服务器、闸机客户端）
	 */
	private AUTHORIZATION auth;
	/**
	 * 客户端信息（网点客户端、缓存服务器、闸机客户端）
	 */
	private SysClient sysClient;
	
	/**
	 * 当前客户端登录用户信息（网点客户端）
	 */
	private SysUser sysUser;
	
	/**
	* 网点信息（网点客户端）
	*/
	private SysOutlet sysOutlet;

	public SysOutlet getSysOutlet() {
		return sysOutlet;
	}
	public void setSysOutlet(SysOutlet sysOutlet) {
		this.sysOutlet = sysOutlet;
	}
	public SysClient getSysClient() {
		return sysClient;
	}
	public void setSysClient(SysClient sysClient) {
		this.sysClient = sysClient;
	}
	public AUTHORIZATION getAuth() {
		return auth;
	}
	public void setAuth(AUTHORIZATION auth) {
		this.auth = auth;
	}
	public SysUser getSysUser() {
		return sysUser;
	}
	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}
}
