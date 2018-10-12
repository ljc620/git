package com.zhming.basic.service;

import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.BaseException;

/**
* Title:  登录操作 <br/>
* Description: 
* @ClassName: ILoginService
* @author ydc
* @date 2016年12月23日 下午5:14:55
* 
*/
public interface ILoginService {

	/** 
	* Title: 用户登录 <br/>
	* Description: 
	* @param userBean
	* @param userCd
	* @param password
	* @return
	* @throws BaseException
	*/ 
	public UserBean login(UserBean userBean,String userId, String userPassword) throws BaseException;

}
