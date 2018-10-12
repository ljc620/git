package com.tbims.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tbims.bean.OpeChestBean;
import com.tbims.entity.StrChest;
import com.tbims.service.IOpeChestService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.util.StringUtil;

@Service
public class OpeChestService extends BaseService implements IOpeChestService {

	@Override
	public PageBean<OpeChestBean> listStorageTicket(UserBean userBean, StrChest strChest) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sb = " SELECT T.*, S.TICKET_TYPE_NAME, U.USER_NAME ";
		sb += " FROM STR_CHEST T ";
		sb += "	 INNER JOIN SYS_TICKET_TYPE S ";
		sb += "	ON T.TICKET_TYPE_ID = S.TICKET_TYPE_ID ";
		sb += " INNER JOIN SYS_USER U";
		sb += " ON U.USER_ID = T.OPE_USER_ID  WHERE 1=1";
		if (StringUtil.isNotNull(strChest.getChestId())) {
			sb += " AND T.CHEST_ID =:CHEST_ID ";
			params.put("CHEST_ID", strChest.getChestId());
		}
		if (StringUtil.isNotNull(strChest.getBatchId())) {
			sb += " AND T.BATCH_ID =:BATCH_ID ";
			params.put("BATCH_ID", strChest.getBatchId());
		}
		if (StringUtil.isNotNull(strChest.getTicketTypeId())) {
			sb += " AND T.TICKET_TYPE_ID =:TICKET_TYPE_ID ";
			params.put("TICKET_TYPE_ID", strChest.getTicketTypeId());
		}
		if (strChest.getOpeTime()!=null) {
			sb += " AND T.OPE_TIME =:OPE_TIME ";
			params.put("OPE_TIME", strChest.getOpeTime());
		}
		sb += " ORDER BY T.OPE_TIME, T.TICKET_TYPE_ID";
		PageBean<OpeChestBean> ret = dbUtil.queryPageToBean("查询", sb, OpeChestBean.class, userBean.getPageNum(), userBean.getPageSize(), params);
		return ret;
	}

}
