package com.zhming.basic.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tbims.entity.SysTicketType;
import com.zhming.basic.service.ICommonService;
import com.zhming.support.BaseService;
import com.zhming.support.exception.DBException;

@Service
public class CommonService extends BaseService implements ICommonService {

	@Override
	public List<Map<String, Object>> listItemsByKey(String key) throws DBException {
			List<Map<String, Object>> items = dbUtil.queryListToMap("","SELECT ITEM_CD ID,  ITEM_NAME TEXT, ITEM_NAME FROM SYS_DICTIONARY  WHERE KEY_CD = ?  AND STAT = 'Y' ORDER BY ORDER_NUM",key);
			return items;
		}
	
	@Override
	public List<SysTicketType> ticketTypeList(String flag) {
		String sql = "SELECT * FROM SYS_TICKET_TYPE";
		if ("t".equals(flag)) {
			sql += " WHERE TEAM_FLAG='Y'";
		}
		if("nt".equals(flag)){
			sql += " WHERE TEAM_FLAG='N'";
		}
		sql += " ORDER BY TICKET_TYPE_ID";
		List<SysTicketType> ticketTypeList = dbUtil.queryListToBean("查询票种列表", sql, SysTicketType.class);
		return ticketTypeList;
	}
}
