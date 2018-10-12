package com.zhming.basic.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tbims.entity.SysDictionary;
import com.zhming.basic.service.IDictionaryService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.BaseException;

/**
 * 字典管理
* Title:   <br/>
* Description: 
* @ClassName: DictionaryController
* @author syq
* @date 2016年12月15日 下午5:21:19
*
 */
@RestController
@RequestMapping("/dictionary/")
public class DictionaryController extends BaseController {

	@Autowired
	private IDictionaryService dictionaryService;

	/**
	 * 
	* Title: 查询字典列表<br/>
	* Description: 
	* @param dictionary 
	* @param page
	* @param rows
	* @return
	* @throws BaseException
	 */
	@RequestMapping(value = "listDictionary")
	@ControlAspect(funtionCd="i2_sys_dictionary",operType=OperType.QUERY,havPrivs=true)
	@ControllerException
	public PageBean<Map<String, Object>> queryDictionary(SysDictionary dictionary,Integer page,Integer rows) throws BaseException{
		PageBean<Map<String, Object>> dictionaryList = dictionaryService.listDictionary(getLoginUserBean(),dictionary,page,rows);
		return dictionaryList;
	}
	/**
	 * 
	* Title: 新增字典<br/>
	* Description: 
	* @param dictionary
	* @return
	* @throws BaseException
	 */
	@RequestMapping(value="addDictionary")
	@ControlAspect(funtionCd="i2_sys_dictionary",operType=OperType.ADD,havPrivs=true)
	@ControllerException
	public void addDictionary(SysDictionary dictionary) throws BaseException{
		dictionaryService.addDictionary(getLoginUserBean(),dictionary);
	}
	/**
	 * 
	* Title: 修改字典<br/>
	* Description: 
	* @param dictionary
	* @throws BaseException
	 */
	@RequestMapping(value="updateDictionary")
	@ControlAspect(funtionCd="i2_sys_dictionary",operType=OperType.UPDATE,havPrivs=true)
	@ControllerException
	public void updateDictionary(SysDictionary dictionary) throws BaseException{
		dictionaryService.updateDictionary( getLoginUserBean(), dictionary); 
	}

	/**
	 * 
	* Title: 修改之前获取字典详情<br/>
	* Description: 
	* @param dictionaryId
	* @return
	* @throws BaseException
	 */
	@RequestMapping(value="initBeforeUpdate")
	@ControlAspect(funtionCd="查询字典信息",operType=OperType.QUERY)
	@ControllerException	
	public ModelAndView initBeforeUpdate(String dictionaryId) throws BaseException{
		UserBean userBean=getLoginUserBean();
		ModelAndView mv = new ModelAndView("pages/sys/dictionary/dictionaryEdit");
		SysDictionary dictionary=dictionaryService.find(userBean, dictionaryId);
		mv.addObject("dictionary", dictionary);
		return mv;
	}
	/**
	 * 
	* Title: 删除字典<br/>
	* Description: 
	* @param dictionaryId
	* @return
	* @throws BaseException
	 */
	@RequestMapping(value="deleteDictionary")
	@ControlAspect(funtionCd="i2_sys_dictionary",operType=OperType.DELETE,havPrivs=true)
	@ControllerException	
	public void deleteDictionary(String dictionaryId) throws BaseException{
		dictionaryService.deleteBydictionaryCds(getLoginUserBean(), dictionaryId);
	}
}