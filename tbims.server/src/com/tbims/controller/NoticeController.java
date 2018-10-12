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
import com.zhming.support.exception.ServiceException;
/**
 * 
 * Title: 信息公告管理 Description:
 * @ClassName: NoticeController
 * @author ly
 * @date 2017年7月3日 上午11:24:19
 *
 */
@RestController
@RequestMapping("/noticeMng/")
public class NoticeController extends BaseController {
	@Autowired
	private INoticeService iNoticeService;
	/**
	 * 
	 * Title: 查询公告 Description:
	 * @param slNotice
	 * @return
	 */
	@RequestMapping(value = "listNotice")
	@ControlAspect(funtionCd = "i2_sale_notice", operType = OperType.QUERY, havPrivs = true)
	@ControllerException
	public PageBean<SlNoticeBean> listNotice(SlNotice slNotice) {
		return iNoticeService.listNotice(getLoginUserBean(), slNotice);
	}
	/**
	 * 
	 * Title: 添加公告 Description:
	 * @param slNotice
	 * @throws ServiceException
	 */
	@RequestMapping(value = "addNotice")
	@ControlAspect(funtionCd = "i2_sale_notice", operType = OperType.ADD, havPrivs = true)
	@ControllerException
	public void addNotice(SlNotice slNotice) throws ServiceException {
		iNoticeService.addNotice(getLoginUserBean(), slNotice);
	}
	/**
	 * 
	 * Title: 删除公告 Description:
	 * @param noticeId
	 */
	@RequestMapping(value = "delNotice")
	@ControlAspect(funtionCd = "i2_sale_notice", operType = OperType.UPDATE, havPrivs = true)
	@ControllerException
	public void delNotice(String noticeId) {
		iNoticeService.delNotice(getLoginUserBean(), noticeId);
	}
	/**
	 * 
	 * Title: 查询公告详情<br/>
	 * Description:
	 * @param noticeId
	 * @return
	 */
	@RequestMapping(value = "showNotice")
	@ControlAspect(funtionCd = "查看公告", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView showNotice(String noticeId) {
		ModelAndView mv = new ModelAndView("pages/sys/noticeMng/content");
		SlNotice slNotice = iNoticeService.showNotice(noticeId);
		mv.addObject("slNotice", slNotice);
		return mv;
	}
}
