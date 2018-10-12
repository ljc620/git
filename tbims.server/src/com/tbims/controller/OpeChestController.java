package com.tbims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tbims.bean.OpeChestBean;
import com.tbims.entity.StrChest;
import com.tbims.service.IOpeChestService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.common.OperType;

@RestController
@RequestMapping("/opechest/")
public class OpeChestController extends BaseController{
	@Autowired
	private IOpeChestService opeChestService;
	/**
	 * 
	* Title: 入库明细查询
	* Description: 
	* @param strChest
	* @return
	 */
	@RequestMapping(value = "listOpeChest")
	@ControlAspect(funtionCd = "i2_ope_chest", operType = OperType.QUERY, havPrivs = true)
	@ControllerException
	public PageBean<OpeChestBean> listOpeChest(StrChest strChest) {
		PageBean<OpeChestBean> listOpeChest = opeChestService.listStorageTicket(getLoginUserBean(), strChest);
		return listOpeChest;
	}
}
