package com.zhming.basic.service;

import java.util.List;
import java.util.Map;

import com.tbims.entity.SysTicketType;
import com.zhming.support.exception.DBException;

public interface ICommonService {
	/**
	 * Title: 查询指定字段的所有项目的列表 <br/>
	 * Description:
	 * @param navigation
	 * @param key
	 * @return
	 * @throws BFException
	 */
	public List<Map<String, Object>> listItemsByKey(String key) throws DBException;

	public List<SysTicketType> ticketTypeList(String flag);

}
