package com.zhming.basic.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tbims.entity.SysTicketType;
import com.zhming.basic.service.ICommonService;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.BaseException;

@RestController
@RequestMapping("/comm/")
public class CommonController {
	@Autowired
	private ICommonService commonService;
	/**
	 * 
	 * Title: 查询指定字段字典值<br/>
	 * Description:
	 * @param key 字段名称
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listItemsByKey")
	@ControlAspect(funtionCd = "查询指定字段字典值")
	@ControllerException
	public List<Map<String, Object>> listItemsByKey(String key) throws BaseException {
		List<Map<String, Object>> items = commonService.listItemsByKey(key);
		return items;
	}
//	/**
//	 * 
//	 * Title: 查询票种列表<br/>
//	 * Description:
//	 * @return
//	 */
//	@RequestMapping(value = "ticketTypeList")
//	@ControlAspect(funtionCd = "查询票种列表下拉框", operType = OperType.QUERY)
//	@ControllerException
//	public List<SysTicketType> ticketTypeList(String flag) {
//		List<SysTicketType> ticketTypeList = commonService.ticketTypeList(flag);
//		return ticketTypeList;
//	}
}
