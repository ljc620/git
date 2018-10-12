package com.tbims.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.tbims.bean.SysTicketTypePriceBean;
import com.tbims.bean.TicketTypeRuleBean;
import com.tbims.entity.SysTicketType;
import com.tbims.entity.SysTicketTypePrice;
import com.tbims.entity.SysTicketTypePriceH;
import com.tbims.entity.SysTicketTypeRule;
import com.tbims.entity.SysTicketTypeVenue;
import com.tbims.entity.SysTicketTypeVenueId;
import com.tbims.entity.SysVenue;
import com.tbims.service.ITicketTypeService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.BeanUtils;
import com.zhming.support.util.StringUtil;
import com.zhming.support.util.UUIDGenerator;

@Service
public class TicketTypeService extends BaseService implements ITicketTypeService {

	@Override
	public PageBean<Map<String, Object>> listTicketType(UserBean userBean) {
		String sql = "SELECT * FROM SYS_TICKET_TYPE T ORDER BY TICKET_TYPE_ID ";
		PageBean<Map<String, Object>> ret = dbUtil.queryPageToMap("查询票种信息", sql, userBean.getPageNum(), userBean.getPageSize());
		return ret;
	}

	@Override
	public void addTicketType(UserBean userBean, SysTicketType sysTicketType, List<TicketTypeRuleBean> ruleList, String venueIds) throws ServiceException {
		SysTicketType sysTicketTypeOld = dbUtil.findById("", SysTicketType.class, sysTicketType.getTicketTypeId());
		if (sysTicketTypeOld != null) {
			throw new ServiceException(MSG.ERROR, "票种编号已存在");
		}
		Session session = dbUtil.getSessionByTransaction();
		try {
			// 保存
			sysTicketType.setTicketTypeStat("Y");
			sysTicketType.setOpeTime(new Date());
			sysTicketType.setOpeUserId(userBean.getUserId());
			sysTicketType.setVersionNo(new Date());
			dbUtil.saveEntity("保存票种", session, sysTicketType);
			// 保存角色和权限的关联
			String[] venueArray = venueIds.split(",");
			for (String venueId : venueArray) {
				if (StringUtil.isNull(venueId)) {
					break;
				}
				SysTicketTypeVenue sysTicketTypeVenue = new SysTicketTypeVenue();
				sysTicketTypeVenue.setId(new SysTicketTypeVenueId(sysTicketType.getTicketTypeId(), Integer.valueOf(venueId)));
				sysTicketTypeVenue.setVersionNo(new Date());
				sysTicketTypeVenue.setOpeUserId(userBean.getUserId());
				sysTicketTypeVenue.setOpeTime(new Date());
				dbUtil.saveEntity("保存票种场馆", session, sysTicketTypeVenue);
			}
			for (TicketTypeRuleBean ticketTypeRuleBean : ruleList) {
				SysTicketTypeRule sysTicketTypeRule = new SysTicketTypeRule();
				sysTicketTypeRule.setRuleId(UUIDGenerator.getPK());
				sysTicketTypeRule.setTicketTypeId(sysTicketType.getTicketTypeId());
				sysTicketTypeRule.setBeginDt(ticketTypeRuleBean.getBeginDt());
				sysTicketTypeRule.setEndDt(ticketTypeRuleBean.getEndDt());
				sysTicketTypeRule.setBeginTime(timeToNumber(ticketTypeRuleBean.getBeginTime()));
				sysTicketTypeRule.setEndTime(timeToNumber(ticketTypeRuleBean.getEndTime()));
				sysTicketTypeRule.setOpeTime(new Date());
				sysTicketTypeRule.setOpeUserId(userBean.getUserId());
				sysTicketTypeRule.setVersionNo(new Date());
				sysTicketTypeRule.setValidWeek(ticketTypeRuleBean.getW0()
						                       +ticketTypeRuleBean.getW1()
						                       +ticketTypeRuleBean.getW2()
						                       +ticketTypeRuleBean.getW3()
						                       +ticketTypeRuleBean.getW4()
						                       +ticketTypeRuleBean.getW5()
						                       +ticketTypeRuleBean.getW6()
						                       );

				dbUtil.saveEntity("保存票种规则", session, sysTicketTypeRule);
			}
			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}
	}
	@Override
	public void saveUpdTicketType(UserBean userBean, SysTicketType sysTicketType, String[] venueIds) throws ServiceException {
		Session session = dbUtil.getSessionByTransaction();
		try {
			// 保存
			sysTicketType.setTicketTypeStat("Y");
			sysTicketType.setOpeTime(new Date());
			sysTicketType.setOpeUserId(userBean.getUserId());
			sysTicketType.setVersionNo(new Date());
			dbUtil.updateEntity("更新票种", session, sysTicketType);
			Map<String, Object> map = new HashMap<>();
			String sql = "DELETE FROM SYS_TICKET_TYPE_VENUE T WHERE T.TICKET_TYPE_ID=:TICKET_TYPE_ID";
			map.put("TICKET_TYPE_ID", sysTicketType.getTicketTypeId());
			dbUtil.executeSql("根据票种删除票种场馆", session, sql, map);
			// 保存角色和权限的关联
			for (String venueId : venueIds) {
				if (StringUtil.isNull(venueId)) {
					break;
				}
				SysTicketTypeVenue sysTicketTypeVenue = new SysTicketTypeVenue();
				sysTicketTypeVenue.setId(new SysTicketTypeVenueId(sysTicketType.getTicketTypeId(), Integer.valueOf(venueId)));
				dbUtil.saveEntity("修改票种场馆", session, sysTicketTypeVenue);
			}
			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}
	}

	/**
	 * Title:时间转换成数字 <br/>
	 * Description:
	 * @param time
	 * @return
	 * @throws ServiceException
	 */
	private Long timeToNumber(String time) throws ServiceException {
		if (StringUtil.isNull(time)) {
			return 0l;
		}
		Long ret = 0l;
		String[] timeArray = time.split(":");
		if (timeArray == null) {
			return 0l;
		} else {
			if (timeArray.length != 3) {
				throw new ServiceException(MSG.ERROR, "时间格式不正确");
			}
			ret = Long.valueOf(timeArray[0]) * 3600 + Long.valueOf(timeArray[1]) * 60 + Long.valueOf(timeArray[2]);
			return ret;
		}
	}

	/**
	 * Title:数字转换成时间 <br/>
	 * Description:
	 * @param time
	 * @return
	 */
	private String numberToTime(Long time) {
		String timeStr = null;
		Long hour = 0l;
		Long minute = 0l;
		Long second = 0l;
		if (time <= 0)
			return "00:00:00";
		else {
			minute = time / 60;
			if (minute < 60) {
				second = time % 60;
				timeStr = unitFormat(minute) + ":" + unitFormat(second);
			} else {
				hour = minute / 60;
				if (hour > 99)
					return "99:59:59";
				minute = minute % 60;
				second = time - hour * 3600 - minute * 60;
				timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
			}
		}
		return timeStr;
	}

	/**
	 * Title:数字转换成时间 <br/>
	 * Description:
	 * @param i
	 * @return
	 */
	private String unitFormat(Long i) {
		String retStr = null;
		if (i >= 0 && i < 10)
			retStr = "0" + Long.toString(i);
		else
			retStr = "" + i;
		return retStr;
	}

	@Override
	public List<SysVenue> venueList() {
		String sql = "SELECT * FROM SYS_VENUE WHERE STAT='Y'";
		List<SysVenue> venueList = dbUtil.queryListToBean("查询场馆列表", sql, SysVenue.class);
		return venueList;
	}

	@Override
	public List<SysTicketTypeVenue> typeVenueList(String ticketTypeId) {
		Map<String, Object> map = new HashMap<>();
		String sql = "SELECT * FROM  SYS_TICKET_TYPE_VENUE TV WHERE TV.TICKET_TYPE_ID=:TICKET_TYPE_ID";
		map.put("TICKET_TYPE_ID", ticketTypeId);
		List<SysTicketTypeVenue> typeVenueList = dbUtil.queryListToBean("查询场馆列表", sql, SysTicketTypeVenue.class, map);
		return typeVenueList;
	}

	@Override
	public PageBean<TicketTypeRuleBean> ruleList(UserBean userBean, String ticketTypeId) {
		Map<String, Object> map = new HashMap<>();
		String sql = "SELECT * FROM SYS_TICKET_TYPE_RULE T WHERE T.TICKET_TYPE_ID=:TICKET_TYPE_ID ORDER BY BEGIN_DT";
		map.put("TICKET_TYPE_ID", ticketTypeId);
		PageBean<SysTicketTypeRule> retInt = dbUtil.queryPageToBean("查询票种规则信息", sql, SysTicketTypeRule.class, userBean.getPageNum(), userBean.getPageSize(), map);
		List<SysTicketTypeRule> ruleList = retInt.getRows();
		List<TicketTypeRuleBean> ruleBeanList = new ArrayList<TicketTypeRuleBean>();
		for (SysTicketTypeRule sysTicketTypeRule : ruleList) {
			TicketTypeRuleBean ticketTypeRuleBean = new TicketTypeRuleBean();
			ticketTypeRuleBean.setRuleId(sysTicketTypeRule.getRuleId());
			ticketTypeRuleBean.setTicketTypeId(sysTicketTypeRule.getTicketTypeId());
			ticketTypeRuleBean.setBeginDt(sysTicketTypeRule.getBeginDt());
			ticketTypeRuleBean.setEndDt(sysTicketTypeRule.getEndDt());
			ticketTypeRuleBean.setBeginTime(numberToTime(sysTicketTypeRule.getBeginTime()));
			ticketTypeRuleBean.setEndTime(numberToTime(sysTicketTypeRule.getEndTime()));
			ticketTypeRuleBean.setW0(sysTicketTypeRule.getValidWeek().substring(0,1));
			ticketTypeRuleBean.setW1(sysTicketTypeRule.getValidWeek().substring(1,2));
			ticketTypeRuleBean.setW2(sysTicketTypeRule.getValidWeek().substring(2,3));
			ticketTypeRuleBean.setW3(sysTicketTypeRule.getValidWeek().substring(3,4));
			ticketTypeRuleBean.setW4(sysTicketTypeRule.getValidWeek().substring(4,5));
			ticketTypeRuleBean.setW5(sysTicketTypeRule.getValidWeek().substring(5,6));
			ticketTypeRuleBean.setW6(sysTicketTypeRule.getValidWeek().substring(6,7));
			ruleBeanList.add(ticketTypeRuleBean);
		}
		PageBean<TicketTypeRuleBean> ret = new PageBean<TicketTypeRuleBean>();
		ret.setNowPage(retInt.getNowPage());
		ret.setPageSize(retInt.getPageSize());
		ret.setTotal(retInt.getTotal());
		ret.setTotalPage(retInt.getTotalPage());
		ret.setRows(ruleBeanList);
		return ret;
	}

	@Override
	public SysTicketType getTicketType(String ticketTypeId) {
		return dbUtil.findById("查询票种", SysTicketType.class, ticketTypeId);
	}

	@Override
	public void addTicketTypeRule(UserBean userBean, TicketTypeRuleBean ticketTypeRuleBean) throws ServiceException {
		Map<String, Object> params = new HashMap<>();
		String sql="select * from SYS_TICKET_TYPE_RULE where TICKET_TYPE_ID=:TICKET_TYPE_ID and END_DT>=:BEGIN_DT";
		params.put("TICKET_TYPE_ID", ticketTypeRuleBean.getTicketTypeId());
		params.put("BEGIN_DT",ticketTypeRuleBean.getBeginDt());
		int cou=dbUtil.count("是否存在输入开始日期小于等于结束日期的数据", sql, params);
		if(cou>0){
			throw new ServiceException(MSG.ERROR, "日期不能有重叠，且只能顺序添加");
		}
		SysTicketTypeRule sysTicketTypeRule = new SysTicketTypeRule();
		sysTicketTypeRule.setRuleId(UUIDGenerator.getPK());
		sysTicketTypeRule.setTicketTypeId(ticketTypeRuleBean.getTicketTypeId());
		sysTicketTypeRule.setBeginDt(ticketTypeRuleBean.getBeginDt());
		sysTicketTypeRule.setEndDt(ticketTypeRuleBean.getEndDt());
		sysTicketTypeRule.setBeginTime(timeToNumber(ticketTypeRuleBean.getBeginTime()));
		sysTicketTypeRule.setEndTime(timeToNumber(ticketTypeRuleBean.getEndTime()));
		sysTicketTypeRule.setOpeTime(new Date());
		sysTicketTypeRule.setOpeUserId(userBean.getUserId());
		sysTicketTypeRule.setVersionNo(new Date());
		sysTicketTypeRule.setValidWeek(ticketTypeRuleBean.getW0()
                +ticketTypeRuleBean.getW1()
                +ticketTypeRuleBean.getW2()
                +ticketTypeRuleBean.getW3()
                +ticketTypeRuleBean.getW4()
                +ticketTypeRuleBean.getW5()
                +ticketTypeRuleBean.getW6()
                );
		dbUtil.saveEntity("添加票种规则信息", sysTicketTypeRule);
	}

	@Override
	public void delTicketType(UserBean userBean, String ticketTypeId) throws ServiceException {
		Session session = dbUtil.getSessionByTransaction();
		try {
			Map<String, Object> params = new HashMap<>();
			String delRule = "DELETE FROM SYS_TICKET_TYPE_RULE WHERE TICKET_TYPE_ID=:TICKET_TYPE_ID";
			String delVenue = "DELETE FROM SYS_TICKET_TYPE_VENUE WHERE TICKET_TYPE_ID=:TICKET_TYPE_ID";
			params.put("TICKET_TYPE_ID", ticketTypeId);
			dbUtil.executeSql("删除规则", session, delRule, params);
			dbUtil.executeSql("删除场馆", session, delVenue, params);
			dbUtil.deleteEntity("删除票种信息", session, SysTicketType.class, ticketTypeId);
			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}
	}

	@Override
	public void delTicketTypeRule(UserBean userBean, String ruleId) throws ServiceException {
		dbUtil.deleteEntity("删除票种规则", SysTicketTypeRule.class, ruleId);
	}

	@Override
	public String getVenueStr(String ticketTypeId) throws ServiceException {
		String ret = "";
		Map<String, Object> map = new HashMap<>();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT V.*");
		sb.append("   FROM SYS_TICKET_TYPE_VENUE TV ");
		sb.append("  INNER JOIN SYS_VENUE V ");
		sb.append("     ON TV.VENUE_ID = V.VENUE_ID ");
		sb.append("  WHERE TV.TICKET_TYPE_ID = :TICKET_TYPE_ID ");
		map.put("TICKET_TYPE_ID", ticketTypeId);
		String sql = sb.toString();
		List<SysVenue> venueList = dbUtil.queryListToBean("", sql, SysVenue.class, map);
		for (SysVenue sysVenue : venueList) {
			ret += sysVenue.getVenueName() + ",";
		}
		if (ret.indexOf(",") != -1) {
			ret = ret.substring(0, ret.length() - 1);
		}
		return ret;
	}

	@Override
	public void updateTicketStat(UserBean userBean, String ticketTypeId, String ticketTypeStat) throws BaseException {
		dbUtil.executeSql("", "UPDATE SYS_TICKET_TYPE SET TICKET_TYPE_STAT=? WHERE TICKET_TYPE_ID IN (" + ticketTypeId + ")", ticketTypeStat);
	}

	@Override
	public Long getEndNo(String ticketTypeId) throws BaseException {
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("TICKET_TYPE_ID", ticketTypeId);
		boolean result=checkContinue(null,ticketTypeId);
		String sql="";
		if(result){
			sql=" SELECT MAX(END_NO) END_NO FROM SYS_TICKET_TYPE_PRICE WHERE TICKET_TYPE_ID=:TICKET_TYPE_ID ";
		}else{
			sql=" SELECT MIN(END_NO) END_NO FROM SYS_TICKET_TYPE_PRICE WHERE TICKET_TYPE_ID=:TICKET_TYPE_ID ";
		}
		List<Map<String,Object>> list=dbUtil.queryListToMap("查询该票种中结束张数最小的值", sql, param);
		Long endNo=null;
		if(list.get(0).get("endNo")!=null&&list.size()!=0){
			for(Map<String,Object> map:list){
				endNo=Long.valueOf(StringUtil.convertString(map.get("endNo")))+1;
			}
		}else{
			endNo=0l;
		}
		return endNo;
	}

	@Override
	public PageBean<SysTicketTypePriceBean> ticPriceList(UserBean loginUserBean, String ticketTypeId) throws BaseException {
		Map<String, Object> params = new HashMap<>();
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT T.PRICE_ID,T.START_NO,T.END_NO,T.PRICE,T.CREATE_TIME,T.CREATE_USER_ID,U.USER_NAME CREATE_USER_NAME ");
		sql.append("    FROM SYS_TICKET_TYPE_PRICE T ");
		sql.append("	INNER JOIN SYS_USER U ");
		sql.append("	ON U.USER_ID=T.CREATE_USER_ID ");
		sql.append("	WHERE T.TICKET_TYPE_ID=:TICKET_TYPE_ID ORDER BY START_NO ");
		params.put("TICKET_TYPE_ID", ticketTypeId);
		PageBean<SysTicketTypePriceBean> ticPriceList = dbUtil.queryPageToBean("查询票种票价信息", sql.toString(), SysTicketTypePriceBean.class, loginUserBean.getPageNum(), loginUserBean.getPageSize(), params);
		return ticPriceList;
	}

	@Override
	public void addTicketTypePrice(UserBean loginUserBean, SysTicketTypePrice ticTypePrice) throws BaseException {
		Map<String, Object> params = new HashMap<>();
		//判断区间是否交叉
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT * FROM SYS_TICKET_TYPE_PRICE WHERE TICKET_TYPE_ID=:TICKET_TYPE_ID  ");
		sql.append("	AND ( ");
		sql.append(" 	(START_NO<=:START_NO AND END_NO>=:END_NO)   ");
		sql.append("	OR (START_NO>=:START_NO AND :END_NO BETWEEN START_NO AND END_NO )");
		sql.append("	OR (END_NO<=:END_NO AND :START_NO BETWEEN START_NO AND END_NO )");
		sql.append("	OR (START_NO>=:START_NO AND END_NO<=:END_NO)");
		sql.append("	) ");

		params.put("TICKET_TYPE_ID", ticTypePrice.getTicketTypeId());
		params.put("START_NO",ticTypePrice.getStartNo());
		params.put("END_NO", ticTypePrice.getEndNo());
		int cou=dbUtil.count("是否存在输入开始张数小于等于结束张数的数据", sql.toString(), params);
		if(cou>0){
			throw new ServiceException(MSG.ERROR, "开始张数和结束张数不能有交叉");
		}
		SysTicketTypePrice ticTypePriceN = new SysTicketTypePrice();
		ticTypePriceN.setPriceId(UUIDGenerator.getPK());
		ticTypePriceN.setTicketTypeId(ticTypePrice.getTicketTypeId());
		ticTypePriceN.setStartNo(ticTypePrice.getStartNo());
		ticTypePriceN.setEndNo(ticTypePrice.getEndNo());
		ticTypePriceN.setPrice(ticTypePrice.getPrice());
		ticTypePriceN.setCreateUserId(loginUserBean.getUserId());
		ticTypePriceN.setCreateTime(new Date());
		dbUtil.saveEntity("添加阶梯票价信息", ticTypePriceN);
		
		SysTicketTypePriceH ticTypePriceH = new SysTicketTypePriceH();
		BeanUtils.copyRPCProperties(ticTypePriceN, ticTypePriceH);
		ticTypePriceH.setUpdateUserId(loginUserBean.getUserId());
		ticTypePriceH.setEndTime(new Date());
		dbUtil.saveEntity("添加阶梯票价历史表信息", ticTypePriceH);
	}

	@Override
	public void delTicketTypePrice(UserBean loginUserBean, String priceId) throws BaseException {
		Session session =dbUtil.getSessionByTransaction();
		try {
			dbUtil.executeSql("更新阶梯票价历史表字段信息", session, "UPDATE SYS_TICKET_TYPE_PRICE_H SET END_TIME=?,UPDATE_USER_ID=? WHERE PRICE_ID='"+priceId+"'", new Date(),loginUserBean.getUserId());
			dbUtil.deleteEntity("删除阶梯票价表信息",session, SysTicketTypePrice.class, priceId);
			dbUtil.commit(session);
		} catch (Exception e) {
			throw new BaseException(MSG.ERROR);
		} finally{
			dbUtil.close(session);
		}
	}

	@Override
	public PageBean<SysTicketTypePriceBean> ticPriceHList(UserBean loginUserBean, String ticketTypeId) throws BaseException {
		Map<String, Object> params = new HashMap<>();
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT T.PRICE_ID,T.START_NO,T.END_NO,T.PRICE,T.CREATE_TIME,T.CREATE_USER_ID,U1.USER_NAME CREATE_USER_NAME, ");
		sql.append("	T.END_TIME,T.UPDATE_USER_ID,U2.USER_NAME UPDATE_USER_NAME ");
		sql.append("    FROM SYS_TICKET_TYPE_PRICE_H T ");
		sql.append("	INNER JOIN SYS_USER U1 ");
		sql.append("	ON U1.USER_ID=T.CREATE_USER_ID ");
		sql.append("	INNER JOIN SYS_USER U2");
		sql.append("	ON U2.USER_ID=T.UPDATE_USER_ID ");
		sql.append("	WHERE T.TICKET_TYPE_ID=:TICKET_TYPE_ID ORDER BY START_NO ");
		params.put("TICKET_TYPE_ID", ticketTypeId);
		PageBean<SysTicketTypePriceBean> ticPriceList = dbUtil.queryPageToBean("查询票种票价信息", sql.toString(), SysTicketTypePriceBean.class, loginUserBean.getPageNum(), loginUserBean.getPageSize(), params);
		return ticPriceList;
	}

	@Override
	public boolean checkContinue(UserBean loginUserBean, String ticketTypeId) throws BaseException {
		Map<String, Object> params = new HashMap<>();
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT T.PRICE_ID,T.START_NO,T.END_NO ");
		sql.append("    FROM SYS_TICKET_TYPE_PRICE T ");
		sql.append("	WHERE T.TICKET_TYPE_ID=:TICKET_TYPE_ID ORDER BY START_NO ");
		params.put("TICKET_TYPE_ID", ticketTypeId);
		List<Map<String, Object>> ticPriceList = dbUtil.queryListToMap("", sql.toString(), params);
		List<Long> ticNo=new ArrayList<Long>();
		for(int i=0;i<ticPriceList.size();i++){
			if(i==0){
				ticNo.add(Long.valueOf(StringUtil.convertString(ticPriceList.get(0).get("endNo"))));
				continue;
			}
			if(i==ticPriceList.size()-1){
				ticNo.add(Long.valueOf(StringUtil.convertString(ticPriceList.get(i).get("startNo"))));
				continue;
			}else{
				ticNo.add(Long.valueOf(StringUtil.convertString(ticPriceList.get(i).get("startNo"))));
				ticNo.add(Long.valueOf(StringUtil.convertString(ticPriceList.get(i).get("endNo"))));
			}
		}
		boolean result=true;//连续
		for(int i=0;i<ticNo.size()-1;i++){
			if(i%2==0){
				if((ticNo.get(i+1).longValue()-ticNo.get(i).longValue())!=1){
					System.out.println(ticNo.get(i+1).longValue()+"-"+ticNo.get(i).longValue());
					result=false;//不连续
				}
			}else{
				continue;
			}
		}
		return result;
	}
}
