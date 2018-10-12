package com.tbims.service;

import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.BaseException;

/**
 * 
 * Title: 用户管理 Description:
 * @ClassName: IUserService
 * @author syq
 * @date 2016年12月12日 下午4:45:57
 *
 */
public interface IUserMngService {

	/** 
	* Title:修改密码 <br/>
	* Description: 
	* @param userBean
	* @param oldpass
	* @param newpass
	* @throws BaseException
	*/ 
	public void updatePassword(UserBean userBean, String oldpass, String newpass) throws BaseException;


}
