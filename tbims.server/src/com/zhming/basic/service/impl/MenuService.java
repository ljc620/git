package com.zhming.basic.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tbims.entity.SysMenu;
import com.zhming.basic.service.IMenuService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.UserBean;
import com.zhming.support.bean.ZTreeJson;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.BaseException;
import com.zhming.support.util.StringUtil;

@Service
public class MenuService extends BaseService implements IMenuService {

	@Override
	public List<ZTreeJson> listMenuTree(String roleId, String roleType) {
		Map<String, Object> params = new HashMap<>();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT A.MENU_ID, A.MENU_NAME, NVL(A.MENU_PID,-1) MENU_PID,");
		sb.append(" CASE WHEN B.MENU_ID IS NULL THEN 0 ELSE 1 END CHECKED   ");
		sb.append(" FROM SYS_MENU A   LEFT JOIN (SELECT * FROM SYS_ROLE_MENU B ");
			params.put("ROLE_ID", roleId);
			sb.append(" WHERE B.ROLE_ID=:ROLE_ID ");
		sb.append(") B ");
		sb.append(" ON A.MENU_ID = B.MENU_ID");
		if (StringUtil.isNotNull(roleType)) {
			params.put("MENU_CLASS", roleType);
			sb.append(" WHERE A.MENU_CLASS=:MENU_CLASS ");
		}
		sb.append("ORDER BY A.MENU_TYPE DESC,A.ORDER_NUM ");
        String sql=sb.toString();
		List<ZTreeJson> retTreeList = new ArrayList<ZTreeJson>();
		List<Map<String, Object>> retList = dbUtil.queryListToMap("查询菜单树", sql,params);

		ZTreeJson pfunc = new ZTreeJson();
		pfunc.setId("-1");
		pfunc.setpId("-2");
		pfunc.setName("功能列表");
		pfunc.setOpen(true);
		retTreeList.add(pfunc);

		for (Map<String, Object> menu : retList) {
			ZTreeJson json = new ZTreeJson();
			json.setId(StringUtil.convertString(menu.get("menuId")));
			json.setName(StringUtil.convertString(menu.get("menuName")));
			json.setpId(StringUtil.convertString(menu.get("menuPid")));
			if (StringUtil.convertString(menu.get("checked")).equals("1")) {
				json.setChecked(true);// 选中
			} else {
				json.setChecked(false);
			}
			retTreeList.add(json);
		}

		return retTreeList;
	}

	@Override
	public Map<String, SysMenu> listMenuForMap() throws BaseException {
		return dbUtil.queryMapForBean("", "SELECT * FROM SYS_MENU",
				SysMenu.class, "menuId");
	}

	@Override
	public Map<String, String> getNavigationMaps() {
		// TODO 不用
		return null;
	}

	@Override
	public List<SysMenu> listMenu(String menuId) {
		if (StringUtil.isNull(menuId)) {
			menuId = "-1";
		}
		return dbUtil.queryListToBean("",
				"SELECT * FROM SYS_MENU WHERE MENU_PID=? ORDER BY ORDER_NUM ",
				SysMenu.class, menuId);
	}

	@Override
	public List<ZTreeJson> listMenuTree() {
		String sql = "SELECT * FROM SYS_MENU ORDER BY MENU_TYPE DESC,ORDER_NUM ";

		List<ZTreeJson> retTreeList = new ArrayList<ZTreeJson>();
		List<Map<String, Object>> retList = dbUtil.queryListToMap("查询菜单树", sql);

		ZTreeJson pfunc = new ZTreeJson();
		pfunc.setId("-1");
		pfunc.setpId("-2");
		pfunc.setName("功能列表");
		pfunc.setOpen(true);
		retTreeList.add(pfunc);

		for (Map<String, Object> menu : retList) {
			ZTreeJson json = new ZTreeJson();
			json.setId(StringUtil.convertString(menu.get("menuId")));
			json.setName(StringUtil.convertString(menu.get("menuName")));
			json.setpId(StringUtil.convertString(menu.get("menuPid")));
			if (StringUtil.isNull(json.getpId())) {
				json.setpId("-1");
			}
			retTreeList.add(json);
		}

		return retTreeList;
	}

	@Override
	public void addMenu(UserBean userBean, SysMenu sysMenu)
			throws BaseException {
		if (sysMenu == null) {
			throw new BaseException(MSG.BF_ERROR, "菜单为空");
		}
		SysMenu sysMenuS = dbUtil.findById("", SysMenu.class,
				sysMenu.getMenuId());
		if (sysMenuS != null) {
			throw new BaseException(MSG.BF_ERROR, "菜单编号已存在");
		}
		dbUtil.saveEntity("增加菜单", sysMenu);
	}

	@Override
	public void deleteMenu(UserBean userBean, String menuIds) {
		dbUtil.executeSql("", "DELETE SYS_MENU WHERE MENU_ID IN (" + menuIds
				+ ")");
	}

	@Override
	public void updateMenu(UserBean userBean, SysMenu sysMenu) {
		dbUtil.updateEntity("增加菜单", sysMenu);
	}

	@Override
	public SysMenu findMenuById(UserBean userBean, String menuId) {
		return dbUtil.findById("", SysMenu.class, menuId);
	}

	@Override
	public void updateMenuStat(UserBean userBean, String menuIds,
			String menuStat) {
		dbUtil.executeSql("",
				"UPDATE SYS_MENU SET MENU_STAT=? WHERE MENU_ID IN (" + menuIds
						+ ")", menuStat);

	}

}
