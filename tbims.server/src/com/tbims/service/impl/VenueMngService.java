package com.tbims.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tbims.entity.SysVenue;
import com.tbims.service.IVenueMngService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.DBException;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.StringUtil;

@Service
public class VenueMngService extends BaseService implements IVenueMngService {

	@Override
	public void addVenue(UserBean userBean, SysVenue sysVenue) throws ServiceException {
		SysVenue venueId = dbUtil.findById("根据场馆号查询场馆", SysVenue.class, sysVenue.getVenueId());
		if(venueId!=null){
			throw new ServiceException(MSG.ERROR, "场馆编号已存在");
		}
		sysVenue.setOpeUserId(userBean.getUserId());
		sysVenue.setOpeTime(new Date());
		dbUtil.saveEntity("保存场馆信息", sysVenue);
	}
	
	@Override
	public PageBean<SysVenue> listVenue(UserBean userBean, SysVenue sysVenue)  throws DBException{
		Map<String, Object> params = new HashMap<>();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT V.VENUE_ID, ");
		sb.append(" V.VENUE_NAME,      ");
		sb.append(" V.STAT,            ");
		sb.append(" V.OPE_USER_ID,     ");
		sb.append(" V.OPE_TIME         "); 
		sb.append(" FROM SYS_VENUE V   "); 
		sb.append(" WHERE 1=1 ");
		//场馆名称
		if (StringUtil.isNotNull(sysVenue.getVenueName())) {
			sb.append(" AND V.VENUE_NAME LIKE :VENUE_NAME");
			params.put("VENUE_NAME", "%" + sysVenue.getVenueName() + "%");
		}
		sb.append(" ORDER BY V.VENUE_ID");
		PageBean<SysVenue> ret = dbUtil.queryPageToBean("查询场馆信息", sb.toString(),SysVenue.class, userBean.getPageNum(), userBean.getPageSize(), params);
		return ret;
	}

	@Override
	public void delVenue(Long venueId)  throws DBException{
		dbUtil.deleteEntity("删除场馆根据编号", SysVenue.class, venueId);
	}

	@Override
	public SysVenue getVenueById(Long venueId)  throws DBException{
		SysVenue sysVenue = dbUtil.findById("根据场馆号查询场馆", SysVenue.class, venueId);
		return sysVenue;
	}

	@Override
	public void updateVenue(UserBean userBean, SysVenue sysVenue)  throws DBException{
		sysVenue.setOpeTime(new Date());
		sysVenue.setOpeUserId(userBean.getUserId());
		dbUtil.updateEntity("更新场馆", sysVenue);
	}

	@Override
	public void updateStat(UserBean loginUserBean, String venueIds, String stat) throws BaseException {
		dbUtil.executeSql("", "UPDATE SYS_VENUE SET STAT=? WHERE VENUE_ID IN (" + venueIds + ")", stat);
	}
}
