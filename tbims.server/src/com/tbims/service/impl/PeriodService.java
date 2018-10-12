package com.tbims.service.impl;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tbims.bean.PeriodBean;
import com.tbims.entity.SlPeriod;
import com.tbims.entity.SysTicketType;
import com.tbims.service.IPeriodService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.ServiceException;


@Service
public class PeriodService extends BaseService implements IPeriodService{

	@Override
	public PageBean<PeriodBean> listPeriod(UserBean userBean, SlPeriod slPeriod) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT T.SALE_PERIOD_ID, ");
		sb.append("  T.TICKET_TYPE_ID,       ");
		sb.append("  T.SALE_PERIOD_NAME,     ");
		sb.append("  T.BEGIN_DT,             ");
		sb.append("  T.END_DT,               ");
		sb.append("  T.DISCOUNT_PRICE,       ");
		sb.append("  T.VERSION_NO,           ");
		sb.append("  TY.TICKET_TYPE_NAME     ");
		sb.append("   FROM SL_PERIOD T       ");
		sb.append("  INNER JOIN SYS_TICKET_TYPE TY ");
		sb.append("     ON T.TICKET_TYPE_ID = TY.TICKET_TYPE_ID ");
		sb.append("  ORDER BY BEGIN_DT DESC ");
		 String sql=sb.toString();
			PageBean<PeriodBean> ret=dbUtil.queryPageToBean("查询预售期", sql, PeriodBean.class, userBean.getPageNum(), userBean.getPageSize());
		return ret;
	}

	@Override
	public void addPeriod(UserBean userBean, SlPeriod slPeriod) throws ServiceException {
		Map<String, Object> params = new HashMap<>();
		String sql	= "SELECT T.* FROM SL_PERIOD T WHERE T.TICKET_TYPE_ID=:TICKET_TYPE_ID AND  T.END_DT >:BEGIN_DT";
		params.put("BEGIN_DT", slPeriod.getBeginDt());
		params.put("TICKET_TYPE_ID", slPeriod.getTicketTypeId());
		int count =dbUtil.count("", sql, params);
			if(count==0&& slPeriod.getBeginDt().before(slPeriod.getEndDt())){
				dbUtil.saveEntity("保存预售期", slPeriod);
			}else{
				 throw new ServiceException(MSG.ERROR,"预售期开始日期或预售期结束日期有重叠！");
			}
			
	}

	@Override
	public SlPeriod getPeriodById(String salePeriodId) {
		SlPeriod slPeriod=dbUtil.findById("",SlPeriod.class , salePeriodId);
		return slPeriod;
	}

	@Override
	public void updatePeriod(UserBean userBean, SlPeriod slPeriod) throws ServiceException {
		if(slPeriod.getBeginDt().before(slPeriod.getEndDt())){
			dbUtil.updateEntity("", slPeriod);
		}else{
			 throw new ServiceException(MSG.ERROR,"预售期开始日期和预售期结束日期错误！");
		}
			
	}

	@Override
	public void delPeriod(UserBean userBean, String salePeriodId) {
		dbUtil.deleteEntity("删除预售期", SlPeriod.class, salePeriodId);
	}

	@Override
	public SysTicketType getTicketType(String ticketTypeId) {
		return dbUtil.findById("查询票种", SysTicketType.class, ticketTypeId);
	}

	@Override
	public String getTicketTypeName(String salePeriodId) throws ServiceException {
		String ret="";
		Map<String, Object> map = new HashMap<>();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT V.*");
		sb.append("   FROM SL_PERIOD TV ");
		sb.append("  INNER JOIN SYS_TICKET_TYPE V ");
		sb.append("     ON TV.TICKET_TYPE_ID = V.TICKET_TYPE_ID ");
		sb.append("  WHERE TV.SALE_PERIOD_ID = :SALE_PERIOD_ID ");
		map.put("SALE_PERIOD_ID", salePeriodId);
		String sql=sb.toString();
		List<SysTicketType> ticketList=dbUtil.queryListToBean("", sql, SysTicketType.class, map);
		for(SysTicketType sysTicketType:ticketList){
			ret+=sysTicketType.getTicketTypeName()+",";
		}
		if(ret.indexOf(",")!=-1){
			ret=ret.substring(0, ret.length()-1);
		}
		return ret;
	}

}
