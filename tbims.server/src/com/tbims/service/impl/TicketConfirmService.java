package com.tbims.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.tbims.bean.TicketConfirmBean;
import com.tbims.bean.TicketInfoBean;
import com.tbims.service.ITicketConfirmService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.BaseException;
import com.zhming.support.util.DateUtil;
import com.zhming.support.util.StringUtil;

@Service
public class TicketConfirmService extends BaseService implements ITicketConfirmService {

	@Override
	public PageBean<TicketConfirmBean> listTicketConfirm(UserBean loginUserBean,Date startDate,Date endDate,Long outletId,String stat) throws BaseException {
		Map<String, Object> params = new HashMap<>();
		StringBuffer sb=new StringBuffer();
		sb.append(" SELECT V.OUTLET_ID,V.TICKET_TYPE_ID,V.OUTLET_NAME,");
		sb.append(" V.TICKET_TYPE_NAME,V.USELESS_TIME" );
		sb.append( ",V.TICKET_NUM,V.CONFIRM_TIME ");
		sb.append(" FROM V_SL_USELESS_TICKET_INFO V "); 
		sb.append(" WHERE 1=1 ");
		if(startDate!=null&&endDate!=null){
			sb.append(" AND V.USELESS_TIME BETWEEN :STARTDATE AND :ENDDATE");
			params.put("STARTDATE", startDate);
			params.put("ENDDATE", endDate);
		}
		if (outletId!=null) {
			sb.append(" AND V.OUTLET_ID = :OUTLET_ID");
			params.put("OUTLET_ID", outletId);
		}
		//是否确认 1-未确认 2-已确认
		if(StringUtil.isNotNull(stat)){
			if("1".equals(stat)){
				sb.append(" AND V.CONFIRM_TIME IS NULL ");
			}else{
				sb.append(" AND V.CONFIRM_TIME IS NOT NULL ");
			}
		}
		sb.append(" ORDER BY V.USELESS_TIME,V.OUTLET_ID,V.TICKET_TYPE_ID,V.TICKET_NUM DESC ");
		PageBean<TicketConfirmBean> ret=dbUtil.queryPageToBean("查询废票交回确认列表", sb.toString(), TicketConfirmBean.class, loginUserBean.getPageNum(), loginUserBean.getPageSize(), params);
		return ret;
	}
	@Override
	public Long  getToNum(Date startDate,Date endDate,Long outletId,String stat){
		Long retLval=0l;
		Map<String, Object> params = new HashMap<>();
		StringBuffer sb=new StringBuffer();
		sb.append(" SELECT SUM(V.TICKET_NUM) TONUM");
		
		sb.append(" FROM V_SL_USELESS_TICKET_INFO V "); 
		sb.append(" WHERE 1=1 ");
		if(startDate!=null&&endDate!=null){
			sb.append(" AND V.USELESS_TIME BETWEEN :STARTDATE AND :ENDDATE");
			params.put("STARTDATE", startDate);
			params.put("ENDDATE", endDate);
		}
		if (outletId!=null) {
			sb.append(" AND V.OUTLET_ID = :OUTLET_ID");
			params.put("OUTLET_ID", outletId);
		}
		//是否确认 1-未确认 2-已确认
		if(StringUtil.isNotNull(stat)){
			if("1".equals(stat)){
				sb.append(" AND V.CONFIRM_TIME IS NULL ");
			}else{
				sb.append(" AND V.CONFIRM_TIME IS NOT NULL ");
			}
		}
		Map<String,Object> ret=dbUtil.queryFirstForMap("查询总数", sb.toString(), params);
		if(ret!=null&&!ret.isEmpty()){
			retLval=(Long)ret.get("tonum");
		}
		if(retLval==null){
			retLval=0l;
		}
		return retLval;
	}
	@Override
	public void confirm(UserBean loginUserBean,String myJSONText) throws BaseException{
		Map<String, Object> params = new HashMap<>();
		params.put("CONFIRM_TIME", new Date());
		params.put("CONFIRM_USER_ID", loginUserBean.getUserId());
		List<TicketConfirmBean> ticketBean = JSON.parseArray(myJSONText, TicketConfirmBean.class);
		for (TicketConfirmBean bean:ticketBean) {
			StringBuffer sb=new StringBuffer();
			sb.append(" UPDATE SL_USELESS_TICKET_INFO T1 SET T1.CONFIRM_TIME=:CONFIRM_TIME ,T1.CONFIRM_USER_ID=:CONFIRM_USER_ID WHERE 1=1 ");
			if(bean.getUselessTime()!=null){
				sb.append(" AND T1.USELESS_TIME>=:USELESS_TIME ");	
				sb.append(" AND T1.USELESS_TIME<=:USELESS_TIME1 ");	
				params.put("USELESS_TIME", bean.getUselessTime());
				Date at=bean.getUselessTime();
				String dayAfter=DateUtil.addDay(DateUtil.formatDateToString(at, "yyyy-MM-dd"), 1);		
				try {
					params.put("USELESS_TIME1", DateUtil.formatStringToDate(dayAfter, "yyyy-MM-dd"));
				} catch (Exception e) {
					throw new BaseException(MSG.BF_ERROR);
				}
			}
			if (bean.getOutletId()!=null) {
				sb.append(" AND T1.OUTLET_ID = :OUTLET_ID");
				params.put("OUTLET_ID", bean.getOutletId());
			}
			if(StringUtil.isNotNull(bean.getTicketTypeId())){
				sb.append(" AND T1.TICKET_TYPE_ID = :TICKET_TYPE_ID");
				params.put("TICKET_TYPE_ID", bean.getTicketTypeId());
			}
			dbUtil.executeSql("确认票", sb.toString(), params);
			params.remove("USELESS_TIME");
			params.remove("USELESS_TIME1");
			params.remove("OUTLET_ID");
			params.remove("TICKET_TYPE_ID");
		}
	}

	@Override
	public PageBean<TicketInfoBean> showTicketInfo(UserBean loginUserBean,Date startDate,Date endDate,Date uselessTime,Long outletId,String ticketTypeId,String stat) throws BaseException {
		StringBuffer sql=new StringBuffer();
		Map<String,Object> params=new HashMap<String, Object>();
		sql.append(" SELECT SL.TICKET_ID, ");
		sql.append(" SL.USELESS_TIME,SL.USELESS_REASON, ");
		sql.append(" SL.TICKET_TYPE_ID,TY.TICKET_TYPE_NAME,");
		sql.append(" SL.OUTLET_ID,O.OUTLET_NAME, ");
		sql.append(" SL.USELESS_USER_ID,U.USER_NAME ");
		sql.append(" FROM SL_USELESS_TICKET_INFO SL ");
		sql.append(" LEFT JOIN SYS_OUTLET O ");
		sql.append(" ON O.OUTLET_ID=SL.OUTLET_ID ");
 		sql.append(" LEFT JOIN SYS_TICKET_TYPE TY ");
		sql.append(" ON TY.TICKET_TYPE_ID=SL.TICKET_TYPE_ID ");
		sql.append(" LEFT JOIN SYS_USER U ");
		sql.append(" ON U.USER_ID=SL.USELESS_USER_ID "); 
		sql.append(" WHERE 1=1 ");
		if(uselessTime!=null){
			sql.append(" AND SL.USELESS_TIME>=:USELESS_TIME ");	
			sql.append(" AND SL.USELESS_TIME<=:USELESS_TIME1 ");	
			params.put("USELESS_TIME", uselessTime);
			String dayAfter=DateUtil.addDay(DateUtil.formatDateToString(uselessTime, "yyyy-MM-dd"), 1);		
			try {
				params.put("USELESS_TIME1", DateUtil.formatStringToDate(dayAfter, "yyyy-MM-dd"));
			} catch (Exception e) {
				throw new BaseException(MSG.BF_ERROR);
			}
		}
		if (outletId!=null) {
			sql.append(" AND SL.OUTLET_ID = :OUTLET_ID");
			params.put("OUTLET_ID", outletId);
		}
		if(StringUtil.isNotNull(ticketTypeId)){
			sql.append(" AND SL.TICKET_TYPE_ID = :TICKET_TYPE_ID");
			params.put("TICKET_TYPE_ID", ticketTypeId);
		}
		//合计查看详情使用条件
		//1、是否确认 1-未确认 2-已确认
		if(StringUtil.isNotNull(stat)){
			if("1".equals(stat)){
				sql.append(" AND SL.CONFIRM_TIME IS NULL ");
			}else{
				sql.append(" AND SL.CONFIRM_TIME IS NOT NULL ");
			}
		}
		//2、作废日期（开始日期--结束日期）
		if(startDate!=null&&endDate!=null){
			sql.append(" AND TRUNC(SL.USELESS_TIME,'DD') BETWEEN :STARTDATE AND :ENDDATE");
			params.put("STARTDATE", startDate);
			params.put("ENDDATE", endDate);
		}
		sql.append(" ORDER BY SL.TICKET_ID ");
		PageBean<TicketInfoBean> page=dbUtil.queryPageToBean("查询票详细信息", sql.toString(), TicketInfoBean.class, loginUserBean.getPageNum(),loginUserBean.getPageSize(),params);
		return page;
	}
}