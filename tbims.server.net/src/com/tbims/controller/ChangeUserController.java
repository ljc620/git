package com.tbims.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tbims.entity.SlChangeUser;
import com.tbims.service.IChangeUserService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.UUIDGenerator;

/**
 * 
 * Title: 换票人管理 Description:
 * 
 * @ClassName: ChangeUserController
 * @author ly
 * @date 2017年7月3日 上午11:23:30
 *
 */
@RestController
@RequestMapping("/changeUser/")
public class ChangeUserController extends BaseController {
	@Autowired
	private IChangeUserService iChangeUserService;

	/**
	 * 
	 * Title: 查询换票人 Description:
	 * 
	 * @param slChangeUser
	 * @return
	 */
	@RequestMapping(value = "listChangeUser")
	@ControlAspect(funtionCd = "i2_sale_change_user", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public PageBean<Map<String, Object>> listChangeUser(SlChangeUser slChangeUser) {
		PageBean<Map<String, Object>> listChangeUser = iChangeUserService.listChangeUser(getLoginUserBean(), slChangeUser);
		return listChangeUser;
	}

	/**
	 * 
	 * Title: 添加换票人 Description:
	 * 
	 * @param slChangeUser
	 * @throws ServiceException
	 */
	@RequestMapping(value = "addChangeUser")
	@ControlAspect(funtionCd = "i2_sale_change_user", operType = OperType.ADD, havPrivs=true)
	@ControllerException
	public void addChangeUser(SlChangeUser slChangeUser) throws ServiceException {
		slChangeUser.setChangeUserId(UUIDGenerator.getPK());
		iChangeUserService.addChangeUser(getLoginUserBean(), slChangeUser);
	}

	/**
	 * 修改换票人 Title: <br/>
	 * Description:
	 * 
	 * @param slChangeUser
	 */
	@RequestMapping(value = "updateChangeUser")
	@ControlAspect(funtionCd = "i2_sale_change_user", operType = OperType.UPDATE, havPrivs=true)
	@ControllerException
	public void updateRegion(SlChangeUser slChangeUser) {
		iChangeUserService.updateChangeUser(getLoginUserBean(), slChangeUser);
	}

	/**
	 * 
	 * Title: 删除换票人 Description:
	 * 
	 * @param changeUserId
	 */
	@RequestMapping(value = "delChangeUser")
	@ControlAspect(funtionCd = "i2_sale_change_user", operType = OperType.DELETE, havPrivs=true)
	@ControllerException
	public void delRegion(String changeUserId) {
		iChangeUserService.delChangeUser(getLoginUserBean(), changeUserId);
	}

	/**
	 * 
	 * Title: 修改前查询换票人信息 Description:
	 * 
	 * @param changeUserId
	 * @return
	 */
	@RequestMapping(value = "beforeUpdteChangeUser")
	@ControlAspect(funtionCd = "修改前查询换票人信息", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView beforeUpdteChangeUser(String changeUserId) {
		ModelAndView mv = new ModelAndView("pages/changeUser/updateChangeUser");
		SlChangeUser slChangeUser = iChangeUserService.getById(changeUserId);
		mv.addObject("slChangeUser", slChangeUser);
		return mv;
	}
}
