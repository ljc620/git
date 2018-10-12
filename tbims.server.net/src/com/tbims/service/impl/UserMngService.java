package com.tbims.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tbims.service.IUserMngService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.MD5;
import com.zhming.support.util.StringUtil;
import com.zhming.support.util.UUIDGenerator;

@Service
public class UserMngService extends BaseService implements IUserMngService {
//
//	@Override
//	public void updatePassword(UserBean userBean, String oldpass, String newpass) throws BaseException {
//
//		if (!userBean.getSysUser().getUserPassword().equals(MD5.MD5Encode(oldpass))) {
//			throw new ServiceException(MSG.BF_ERROR, "新旧密码不一致");
//		}
//		Map<String, Object> params = new HashMap<String, Object>();
//		String sql = "UPDATE SYS_USER SET USER_PASSWORD =:USER_PASSWORD WHERE USER_ID =:USER_ID ";
//		params.put("USER_PASSWORD", MD5.MD5Encode(newpass));
//		params.put("USER_ID", userBean.getSysUser().getUserId());
//
//		dbUtil.executeSql("修改密码", sql, params);
//	}


	@Override
	public void updatePassword(UserBean userBean, String oldpass, String newpass) throws BaseException {

		if ((StringUtil.isNull(userBean.getSysUser().getSalt()) && !userBean.getSysUser().getUserPassword().equals(MD5.MD5Encode(oldpass))) ||
				(!StringUtil.isNull(userBean.getSysUser().getSalt()) && !userBean.getSysUser().getUserPassword().equals(MD5.MD5Encode(MD5.MD5Encode(oldpass)+userBean.getSysUser().getSalt())))){
			throw new ServiceException(MSG.BF_ERROR, "原始密码输入错误");
		}
		String salt = getSalt();
		userBean.getSysUser().setSalt(salt);
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "UPDATE SYS_USER SET USER_PASSWORD =:USER_PASSWORD, SALT=:SALT WHERE USER_ID =:USER_ID ";
		//params.put("USER_PASSWORD", MD5.MD5Encode(newpass));
		params.put("USER_PASSWORD", MD5.MD5Encode(MD5.MD5Encode(newpass)+salt));
		params.put("SALT", salt);
		params.put("USER_ID", userBean.getSysUser().getUserId());
		userBean.getSysUser().setUserPassword((String)params.get("USER_PASSWORD"));

		dbUtil.executeSql("修改密码", sql, params);
	}
	
	private String getSalt(){
//		SecureRandom random = new SecureRandom();
//		byte[] bytes = new byte[16];
//		random.nextBytes(bytes);
//		String str="";
//		try {
//			str = new String(bytes, "ASCII");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return str;
		return UUIDGenerator.getRandomString(16, true);
	}
}
