package com.tbims.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tbims.entity.SysRegion;
import com.tbims.entity.SysVenue;
import com.tbims.service.IRegionService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.StringUtil;

@Service
public class RegionService extends BaseService implements IRegionService {

	@Override
	public PageBean<Map<String, Object>> listRegion(UserBean userBean, SysRegion region) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT R.REGION_ID, ");
		sb.append("  R.VENUE_ID,        ");
		sb.append("  V.VENUE_NAME,      ");
		sb.append("  R.REGION_NAME,     ");
		sb.append("  R.LOCATION,        ");
		sb.append("  R.TEL,             ");
		sb.append("  R.LEADER,          ");
		sb.append("  R.OPE_USER_ID,     ");
		sb.append("  R.OPE_TIME   ,      ");
		sb.append("  R.STAT         ");
		sb.append("   FROM SYS_REGION R  ");
		sb.append("  INNER JOIN SYS_VENUE V ");
		sb.append("   ON R.VENUE_ID = V.VENUE_ID  WHERE 1=1 ");
		if (region.getVenueId()!=null) {
			sb .append(" AND R.VENUE_ID =:VENUE_ID ");
			params.put("VENUE_ID", region.getVenueId());
		}
		if (StringUtil.isNotNull(region.getRegionName())) {
			sb .append(" AND R.REGION_NAME LIKE :REGION_NAME ");
			params.put("REGION_NAME",'%'+region.getRegionName()+'%');
		}
		sb.append("   ORDER BY  R.REGION_ID");
		String sql = sb.toString();
		PageBean<Map<String, Object>> ret = dbUtil.queryPageToMap("查询检票区域", sql, userBean.getPageNum(), userBean.getPageSize(),params);
		return ret;
	}

	@Override
	public void addRegion(UserBean userBean, SysRegion region)throws ServiceException {
		SysRegion sysRegion = dbUtil.findById("", SysRegion.class, region.getRegionId());
		if(sysRegion!=null){
			throw new ServiceException(MSG.ERROR,"检票区域编码已存在");
		}
		region.setStat("Y");
		region.setOpeTime(new Date());
		region.setOpeUserId(userBean.getUserId());
		dbUtil.saveEntity("保存检票区域", region);
	}

	@Override
	public void updateRegion(UserBean userBean, SysRegion region) {
		region.setStat("Y");
		region.setOpeUserId(userBean.getUserId());
		region.setOpeTime(new Date());
		dbUtil.updateEntity("更新检票区域", region);
	}

	@Override
	public List<SysVenue> venueList() {
		String sql = "SELECT * FROM SYS_VENUE WHERE STAT='Y'";
		List<SysVenue> venueList = dbUtil.queryListToBean("查询场馆列表", sql, SysVenue.class);
		return venueList;
	}

	@Override
	public void delRegion(UserBean userBean, Long regionId) {
		dbUtil.deleteEntity("删除签约社", SysRegion.class, regionId);
	}

	@Override
	public SysRegion getById(Long regionId) {
		SysRegion sysRegion = dbUtil.findById("", SysRegion.class, regionId);
		return sysRegion;
	}

	@Override
	public String getVenueStr(Long regionId) throws ServiceException {
		String ret = "";
		Map<String, Object> map = new HashMap<>();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT V.*");
		sb.append("   FROM SYS_REGION TV ");
		sb.append("  INNER JOIN SYS_VENUE V ");
		sb.append("     ON TV.VENUE_ID = V.VENUE_ID ");
		sb.append("  WHERE TV.REGION_ID = :REGION_ID ");
		map.put("REGION_ID", regionId);
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
	public void updateStat(UserBean userBean, String regionId, String stat) throws BaseException {
		dbUtil.executeSql("", "UPDATE SYS_REGION SET STAT=? WHERE REGION_ID IN (" + regionId + ")", stat);
		
	}

}
