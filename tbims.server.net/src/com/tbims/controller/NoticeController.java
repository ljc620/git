package com.tbims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tbims.bean.SlNoticeBean;
import com.tbims.entity.SlNotice;
import com.tbims.service.INoticeService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.common.OperType;
/**
 * 
* Title:   信息公告管理
* Description: 
* @ClassName: NoticeController
* @author ly
* @date 2017年7月3日 上午11:24:19
*
 */
@RestController
@RequestMapping("/noticeMng/")
public class NoticeController extends BaseController {
	@Autowired
	private INoticeService noticeService;
	
	/**
	 * 
	* Title: 查询公告信息<br/>
	* Description: 
	* @param slNotice
	* @return
	 */
	@RequestMapping(value = "listNotice")
	@ControlAspect(funtionCd = "i2_sale_notice", operType = OperType.QUERY)
	@ControllerException
	public PageBean<SlNoticeBean> listNotice(SlNotice slNotice ){
		return noticeService.listNotice(getLoginUserBean(), slNotice);
	}
	/**
	 * 
	* Title: 获取公告详情信息<br/>
	* Description: 
	* @param noticeId
	* @return
	 */
	@RequestMapping(value = "showNotice")
	@ControlAspect(funtionCd = "查看公告", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView showNotice(String noticeId){
		ModelAndView   mv =	new ModelAndView("pages/sys/noticeMng/firstnewcontent");
		SlNotice slNotice = noticeService.showNotice(noticeId);
		mv.addObject("slNotice", slNotice);
		return mv;
	}
}
