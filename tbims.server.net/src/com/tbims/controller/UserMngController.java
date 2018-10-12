package com.tbims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tbims.service.IUserMngService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.BaseException;

/**
 * Title: 用户管理 <br/>
 * Description:
 * @ClassName: UserMngController
 * @author ydc
 * @date 2017年6月29日 下午3:29:30
 * 
 */
@RestController
@RequestMapping("/usermng/")
public class UserMngController extends BaseController {

	@Autowired
	private IUserMngService userMngService;


	/**
	 * Title: 删除用户<br/>
	 * Description:
	 * @return
	 */
	@RequestMapping(value = "updatePassword")
	@ControlAspect(funtionCd = "修改密码", operType = OperType.UPDATE)
	@ControllerException
	public void updatePassword(String oldpass, String repassword) throws BaseException {
		userMngService.updatePassword(getLoginUserBean(), oldpass, repassword);
	}
}
