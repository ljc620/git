package com.zhming.basic.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tbims.bean.RoleBean;
import com.tbims.entity.SlOrg;
import com.tbims.entity.SysClient;
import com.tbims.entity.SysOutlet;
import com.tbims.entity.SysRegion;
import com.zhming.basic.service.ICommonService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.UserBean;
import com.zhming.support.bean.ZTreeJson;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.DBException;
import com.zhming.support.util.StringUtil;

@Service
public class CommonService extends BaseService implements ICommonService {

	@Override
	public List<Map<String, Object>> listItemsByKey(String key) throws DBException {
		List<Map<String, Object>> items = dbUtil.queryListToMap("", "SELECT ITEM_CD ID,  ITEM_NAME TEXT, " + "ITEM_NAME FROM SYS_DICTIONARY  WHERE KEY_CD = ?  AND STAT = 'Y' ORDER BY ORDER_NUM", key);
		return items;
	}

	@Override
	public List<Map<String, Object>> listItemsByKeyOutletMng(String key) throws DBException {
		List<Map<String, Object>> items = dbUtil.queryListToMap("", "SELECT ITEM_CD ID,  ITEM_NAME TEXT, " + "ITEM_NAME FROM SYS_DICTIONARY  WHERE KEY_CD = ?  AND ITEM_VAL NOT IN('05','06') AND STAT = 'Y' ORDER BY ORDER_NUM", key);
		return items;
	}

	@Override
	public List<RoleBean> listRole(String userId) throws DBException {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT DISTINCT R.ROLE_ID,                      ");
		sb.append("        R.ROLE_NAME,                    ");
		sb.append("        R.ROLE_TYPE                    ");
		if (StringUtil.isNotNull(userId)) {
			params.put("USER_ID", userId);
			sb.append("        ,CASE                            ");
			sb.append("          WHEN UR.USER_ID IS NULL THEN  ");
			sb.append("           'false'                      ");
			sb.append("          ELSE                          ");
			sb.append("           'true'                       ");
			sb.append("        END CHECKED                     ");
		}
		sb.append("   FROM SYS_ROLE R                      ");
		sb.append("   LEFT JOIN SYS_USER_ROLE UR           ");
		sb.append("     ON R.ROLE_ID = UR.ROLE_ID          ");
		if (StringUtil.isNotNull(userId)) {
			sb.append("    AND UR.USER_ID =:USER_ID            ");
		}
		sb.append("  ORDER BY ROLE_ID                      ");
		String sql = sb.toString();
		List<RoleBean> ret = dbUtil.queryListToBean("查询角色列表", sql, RoleBean.class, params);
		return ret;
	}

	@Override
	public List<SysOutlet> listOutlet() throws DBException {
		return dbUtil.queryListToBean("查询网点列表", "SELECT * FROM SYS_OUTLET ORDER BY OUTLET_TYPE DESC", SysOutlet.class);
	}

	@Override
	public List<SysRegion> listRegion() throws DBException {
		return dbUtil.queryListToBean("查询区域列表", "SELECT * FROM SYS_REGION ORDER BY  VENUE_ID DESC", SysRegion.class);
	}

	@Override
	public List<SysOutlet> listOutletByType(String outletType) throws DBException {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> param = new HashMap<String, Object>();
		sql.append(" SELECT * FROM SYS_OUTLET WHERE 1=1 ");
		if (StringUtil.isNotNull(outletType)) {
			sql.append(" AND OUTLET_TYPE=:OUTLET_TYPE");
			param.put("OUTLET_TYPE", outletType);
		}
		sql.append(" ORDER BY OUTLET_TYPE DESC ");
		List<SysOutlet> sysOut = dbUtil.queryListToBean("查询网点列表", sql.toString(), SysOutlet.class, param);
		return sysOut;
	}

	@Override
	public List<SysClient> listClientByType(String clientType) throws DBException {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> param = new HashMap<String, Object>();
		sql.append(" SELECT * FROM SYS_CLIENT WHERE 1=1 ");
		if (StringUtil.isNotNull(clientType)) {
			sql.append(" AND CLIENT_TYPE=:CLIENT_TYPE");
			param.put("CLIENT_TYPE", clientType);
		}
		sql.append(" ORDER BY CLIENT_ID ");
		List<SysClient> sysClients = dbUtil.queryListToBean("查询终端列表", sql.toString(), SysClient.class, param);
		return sysClients;
	}

	@Override
	public List<SysOutlet> listSaleOutlet() throws DBException {
		return dbUtil.queryListToBean("查询网点列表", "SELECT T.* FROM SYS_OUTLET T WHERE T.OUTLET_TYPE !='06' ORDER BY OUTLET_TYPE DESC", SysOutlet.class);
	}

	@Override
	public List<Map<String, Object>> listOutletTree() throws DBException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select to_char(t.outlet_id) outlet_id ,t.outlet_name,t.outlet_type from SYS_OUTLET t ");
		sb.append(" union                                                       ");
		sb.append(" select distinct t.outlet_type, ");
		sb.append("  decode(t.outlet_type,         ");
		sb.append("  '01', ");
		sb.append("  '现场网点',  ");
		sb.append("  '02',        ");
		sb.append("  '自营网点',  ");
		sb.append("  '03',        ");
		sb.append("  '换票网点',  ");
		sb.append("  '04',        ");
		sb.append("  '票务处理',  ");
		sb.append("  '05',        ");
		sb.append("  '实体代理',  ");
		sb.append("  '签约社'),   ");
		sb.append("    null  ");
		sb.append("   from SYS_OUTLET t  ");
		sb.append(" order by outlet_id                                          ");
		String sql = sb.toString();
		return dbUtil.queryListToMap("查询网点树", sql);
	}

	@Override
	public List<Map<String, Object>> listSaleOutletByUser(UserBean userBean) throws DBException {
		StringBuffer sb = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		sb.append(" select to_char(t.outlet_id) outlet_id ,t.outlet_name,t.outlet_type from SYS_OUTLET t WHERE outlet_type not in ('06','05')  ");
		if (!userBean.getSysUser().getPositionCode().equals("01")) {
			sb.append(" and t.OUTLET_ID=:OUTLET_ID");
			params.put("OUTLET_ID", userBean.getSysUser().getOutletId());
		}
		sb.append(" order by outlet_type,outlet_id                                          ");
		String sql = sb.toString();
		return dbUtil.queryListToMap("查询网点", sql, params);
	}

	@Override
	public List<ZTreeJson> initVenueJson(String venueId) throws DBException {
		String sql = "SELECT VENUE_ID ID,VENUE_NAME NAME";
		if (StringUtil.isNotNull(venueId)) {
			sql += ",CASE WHEN VENUE_ID IN (" + venueId + ") THEN 1 ELSE 0 END CHECKED ";
		}
		sql += " FROM SYS_VENUE V ORDER BY VENUE_ID";

		List<Map<String, Object>> venuList = dbUtil.queryListToMap("查询带下拉框的场馆", sql);
		List<ZTreeJson> zTreeList = new ArrayList<ZTreeJson>();
		ZTreeJson pfunc = new ZTreeJson();
		pfunc.setId("-1");
		pfunc.setpId("-2");
		pfunc.setName("全部");
		pfunc.setOpen(true);
		zTreeList.add(pfunc);

		for (Map<String, Object> menu : venuList) {
			ZTreeJson zTree = new ZTreeJson();
			zTree.setId(StringUtil.convertString(menu.get("id")));
			zTree.setpId(String.valueOf(-1));
			zTree.setName(StringUtil.convertString(menu.get("name")));
			if (StringUtil.convertString(menu.get("checked")).equals("1")) {
				zTree.setChecked(true);// 选中
			} else {
				zTree.setChecked(false);
			}
			zTreeList.add(zTree);
		}
		return zTreeList;
	}

	@Override
	public List<ZTreeJson> initRegionJson(String regionId) throws DBException {
		String sql = "SELECT REGION_ID ID,REGION_NAME NAME";
		if (StringUtil.isNotNull(regionId)) {
			sql += ",CASE WHEN REGION_ID IN (" + regionId + ") THEN 1 ELSE 0 END CHECKED ";
		}
		sql += " FROM SYS_REGION V ORDER BY REGION_ID";

		List<Map<String, Object>> venuList = dbUtil.queryListToMap("查询带下拉框的区域", sql);
		List<ZTreeJson> zTreeList = new ArrayList<ZTreeJson>();
		ZTreeJson pfunc = new ZTreeJson();
		pfunc.setId("-1");
		pfunc.setpId("-2");
		pfunc.setName("全部");
		pfunc.setOpen(true);
		zTreeList.add(pfunc);

		for (Map<String, Object> menu : venuList) {
			ZTreeJson zTree = new ZTreeJson();
			zTree.setId(StringUtil.convertString(menu.get("id")));
			zTree.setpId(String.valueOf(-1));
			zTree.setName(StringUtil.convertString(menu.get("name")));
			if (StringUtil.convertString(menu.get("checked")).equals("1")) {
				zTree.setChecked(true);// 选中
			} else {
				zTree.setChecked(false);
			}
			zTreeList.add(zTree);
		}
		return zTreeList;
	}

	@Override
	public List<ZTreeJson> initTicketTypeJson(String ticketTypeId) throws DBException {
		String sql = "SELECT TICKET_TYPE_ID ID,TICKET_TYPE_NAME NAME";
		if (StringUtil.isNotNull(ticketTypeId)) {
			sql += ",CASE WHEN TICKET_TYPE_ID IN (" + ticketTypeId + ") THEN 1 ELSE 0 END CHECKED ";
		}
		sql += " FROM SYS_TICKET_TYPE V ORDER BY TICKET_TYPE_ID ";

		List<Map<String, Object>> venuList = dbUtil.queryListToMap("查询带下拉框的票种", sql);
		List<ZTreeJson> zTreeList = new ArrayList<ZTreeJson>();
		ZTreeJson pfunc = new ZTreeJson();
		pfunc.setId("-1");
		pfunc.setpId("-2");
		pfunc.setName("全部");
		pfunc.setOpen(true);
		zTreeList.add(pfunc);

		for (Map<String, Object> menu : venuList) {
			ZTreeJson zTree = new ZTreeJson();
			zTree.setId(StringUtil.convertString(menu.get("id")));
			zTree.setpId(String.valueOf(-1));
			zTree.setName(StringUtil.convertString(menu.get("name")));
			if (StringUtil.convertString(menu.get("checked")).equals("1")) {
				zTree.setChecked(true);// 选中
			} else {
				zTree.setChecked(false);
			}
			zTreeList.add(zTree);
		}
		return zTreeList;
	}

	@Override
	public List<ZTreeJson> empList(String empId) throws BaseException {
		String sql = "SELECT EMP_ID ID,EMP_NAME NAME ";
		if (StringUtil.isNotNull(empId)) {
			sql += ",CASE WHEN EMP_ID IN (" + empId + ") THEN 1 ELSE 0 END CHECKED ";
		}
		sql += "FROM SYS_EMP_REGISTER R WHERE R.STAT='Y' ORDER BY EMP_ID ";
		List<Map<String, Object>> empList = dbUtil.queryListToMap("查询员工列表", sql);
		List<ZTreeJson> zTreeList = new ArrayList<ZTreeJson>();
		ZTreeJson pfunc = new ZTreeJson();
		pfunc.setId("-1");
		pfunc.setpId("-2");
		pfunc.setName("全部");
		pfunc.setOpen(true);
		zTreeList.add(pfunc);

		for (Map<String, Object> menu : empList) {
			ZTreeJson zTree = new ZTreeJson();
			zTree.setId(StringUtil.convertString(menu.get("id")));
			zTree.setpId(String.valueOf(-1));
			zTree.setName(StringUtil.convertString(menu.get("name")));
			if (StringUtil.convertString(menu.get("checked")).equals("1")) {
				zTree.setChecked(true);// 选中
			} else {
				zTree.setChecked(false);
			}
			zTreeList.add(zTree);
		}
		return zTreeList;
	}

	@Override
	public List<SlOrg> listOrg(String orgType) throws DBException {
		List<SlOrg> retOrgList = dbUtil.queryListToBean("查询机构", "SELECT * FROM SL_ORG WHERE ORG_ID<>'YBYAPP' AND ORG_TYPE IN (" + orgType + ")", SlOrg.class);
		return retOrgList;
	}

	@Override
	public List<SlOrg> listOrgAll(String orgType) throws DBException {
		List<SlOrg> retOrgList = dbUtil.queryListToBean("查询机构", "SELECT * FROM SL_ORG WHERE ORG_TYPE IN (" + orgType + ")", SlOrg.class);
		return retOrgList;
	}
}
