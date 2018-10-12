package com.tbims.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tbims.bean.StorageTicketBean;
import com.tbims.entity.StrTicketInfo;
import com.tbims.entity.SysDictionary;
import com.tbims.service.IStorageTicketService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.StringUtil;

@Service
public class StorageTicketService extends BaseService implements IStorageTicketService{

	@Override
	public PageBean<StorageTicketBean> listStorageTicket(UserBean userBean, StrTicketInfo strTicketInfo) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sb=" SELECT A.TICKET_TYPE_ID,A.TICKET_TYPE_NAME,  A.CHEST_ID, MIN(A.TICKET_ID) BEGIN_NO, ";
				sb+=" MAX(A.TICKET_ID) END_NO,COUNT(1) TICKET_NUM ";
			  sb+=" FROM (SELECT T.TICKET_TYPE_ID, ";
		      sb+=" TY.TICKET_TYPE_NAME, T.CHEST_ID, T.TICKET_ID, ";
		      sb+=" (T.TICKET_ID - ROW_NUMBER() OVER(ORDER BY T.TICKET_ID)) CNT ";
		      sb+=" FROM STR_TICKET_INFO T ";
		      sb+=" INNER JOIN SYS_TICKET_TYPE TY ";
			  sb+="  ON T.TICKET_TYPE_ID = TY.TICKET_TYPE_ID ";
			  sb+="  WHERE T.STORE_ID IS NULL) A WHERE 1 = 1";
		if (StringUtil.isNotNull(strTicketInfo.getChestId())) {
			params.put("CHEST_ID",strTicketInfo.getChestId());
			sb+=" AND A.CHEST_ID =:CHEST_ID ";
			
		}
		if (StringUtil.isNotNull(strTicketInfo.getTicketTypeId())) {
			params.put("TICKET_TYPE_ID",strTicketInfo.getTicketTypeId());
			sb+=" AND A.TICKET_TYPE_ID =:TICKET_TYPE_ID ";
			
		}
		sb+="  GROUP BY A.TICKET_TYPE_NAME, A.TICKET_TYPE_ID, A.CHEST_ID, A.CNT ";
		sb+=" ORDER BY A.TICKET_TYPE_NAME, A.TICKET_TYPE_ID, A.CHEST_ID, A.CNT ";			
		PageBean<StorageTicketBean> ret=dbUtil.queryPageToBean("查询", sb, StorageTicketBean.class, userBean.getPageNum(), userBean.getPageSize(),params);
		
		return ret;
	}

	

	@Override
	public String getStat(String chestId) throws ServiceException {
		String ret="";
		Map<String, Object> map = new HashMap<>();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT V.*");
		sb.append("   FROM STR_CHEST TV  ");
		sb.append("  INNER JOIN SYS_DICTIONARY V ");
		sb.append("     ON TV.STAT = V.ITEM_CD  ");
		sb.append("  WHERE TV.CHEST_ID = :CHEST_ID  ");
		map.put("CHEST_ID", chestId);
		String sql=sb.toString();
		List<SysDictionary> statList=dbUtil.queryListToBean("", sql, SysDictionary.class, map);
		for(SysDictionary sysDictionary:statList){
			ret+=sysDictionary.getItemName()+",";
		}
		if(ret.indexOf(",")!=-1){
			ret=ret.substring(0, ret.length()-1);
		}
		return ret;
	}
}
