package com.tbims.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tbims.entity.SlOrg;
import com.tbims.entity.SysOutlet;
import com.tbims.service.IOutletMngService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.StringUtil;

@Service
public class OutletMngService extends BaseService implements IOutletMngService {

	@Override
	public void addOutlet(UserBean userBean, SysOutlet sysOutlet) throws ServiceException {
		SysOutlet outletOld = dbUtil.findById("", SysOutlet.class, sysOutlet.getOutletId());
		if(outletOld!=null){
			throw new ServiceException(MSG.ERROR, "网点编号已存在");
		}
		sysOutlet.setStat("Y");
		sysOutlet.setOpeUserId(userBean.getUserId());
		sysOutlet.setOpeTime(new Date());
		dbUtil.saveEntity("保存网点信息", sysOutlet);
	}
	
	@Override
	public PageBean<Map<String, Object>> listOutlet(UserBean userBean, SysOutlet sysOutlet) {
		Map<String, Object> params = new HashMap<>();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT O.OUTLET_ID, ");
		sb.append(" O.OUTLET_NAME,     ");
		sb.append(" O.LOCATION,        ");
		sb.append(" O.LEADER,          ");
		sb.append(" O.TEL,             ");
		sb.append(" O.OUTLET_TYPE,     ");
		sb.append(" O.OPE_USER_ID,     ");
		sb.append(" O.OPE_TIME,        ");
		sb.append(" O.ORG_ID,          ");
		sb.append(" O.STAT,          ");
		sb.append(" R.ORG_NAME,          ");
		sb.append(" D.ITEM_NAME OUTLET_TYPE_NAME    ");
		sb.append("  FROM SYS_OUTLET O              ");
		sb.append("  LEFT JOIN  SYS_DICTIONARY D     ");
		sb.append("    ON O.OUTLET_TYPE = D.ITEM_CD ");
		sb.append(" AND D.KEY_CD = 'OUTLET_TYPE'  ");
		sb.append(" LEFT JOIN SL_ORG R     ");
		sb.append("    ON O.ORG_ID = R.ORG_ID ");
		sb.append(" WHERE 1=1 ");
		if (sysOutlet != null && StringUtil.isNotNull(sysOutlet.getOutletName())) {
			sb.append(" AND O.OUTLET_NAME LIKE :OUTLET_NAME");
			params.put("OUTLET_NAME", "%" + sysOutlet.getOutletName() + "%");
		}
		sb.append(" ORDER BY O.OUTLET_ID");
		String sql = sb.toString();
		PageBean<Map<String, Object>> ret = dbUtil.queryPageToMap("", sql, userBean.getPageNum(), userBean.getPageSize(), params);
		return ret;
	}

	@Override
	public void delOutlet(Long outletId) {
		dbUtil.deleteEntity("删除网点根据编号", SysOutlet.class, outletId);
	}

	@Override
	public List<SlOrg> orgList(String orgType) {
		if (StringUtil.isNull(orgType)) {
			return new ArrayList<SlOrg>();
		}
		String sql = "SELECT * FROM  SL_ORG ORG WHERE ORG.ORG_STAT='Y' AND ORG.ORG_TYPE in (" + orgType + ") ";
		List<SlOrg> ret = dbUtil.queryListToBean("查询机构列表", sql, SlOrg.class);
		return ret;
	}

	@Override
	public SysOutlet getOutletById(Long outletId) {
		SysOutlet sysOutlet = dbUtil.findById("", SysOutlet.class, outletId);
		return sysOutlet;
	}

	@Override
	public void updateOutlet(UserBean userBean, SysOutlet sysOutlet) {
		sysOutlet.setStat("Y");
		sysOutlet.setOpeTime(new Date());
		sysOutlet.setOpeUserId(userBean.getUserId());
		dbUtil.updateEntity("", sysOutlet);
	}

	@Override
	public void updateStat(UserBean userBean, String outletId, String stat) throws BaseException {
		dbUtil.executeSql("", "UPDATE SYS_OUTLET SET STAT=? WHERE OUTLET_ID IN (" + outletId + ")", stat);
	}
}
