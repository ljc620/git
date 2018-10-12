package com.zhming.basic.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.tbims.entity.SysMenu;
import com.tbims.entity.SysUser;
import com.zhming.basic.service.ILoginService;
import com.zhming.support.bean.UserBean;
import com.zhming.support.bean.ZTreeJson;
import com.zhming.support.common.Constant;
import com.zhming.support.common.MSG;
import com.zhming.support.db.DBUtil;
import com.zhming.support.exception.BaseException;
import com.zhming.support.util.MD5;
import com.zhming.support.util.StringUtil;

@Service
public class LoginService implements ILoginService {

	@Autowired
	private DBUtil dbUtil;

	@Override
	public UserBean login(UserBean userBean, String userId, String password) throws BaseException {
		userId=StringUtil.trim(userId);
		SysUser sysUser = dbUtil.findById("", SysUser.class, userId);// 查询管理员用户名和密码

		// 验证用户是否存在
		if (sysUser == null) {
			throw new BaseException(MSG.BF_ERROR_USERCODE);
		}

		// 验证用户状态 0:启用 1:停用
		if ("N".equals(sysUser.getUserStat())) {
			throw new BaseException(MSG.BF_ERROR_USER_CANCEL);
		}
		
		if ((StringUtil.isNull(sysUser.getSalt()) && !MD5.MD5Encode(password).equals(sysUser.getUserPassword())) ||
				(!StringUtil.isNull(sysUser.getSalt()) && !MD5.MD5Encode(MD5.MD5Encode(password)+sysUser.getSalt()).equals(sysUser.getUserPassword()))) {
			throw new BaseException(MSG.BF_ERROR_PASS);
		}
			
		

		// 3.获取所有用户对应的功能代码，构造成树(0菜单1 按钮2面板)
		Map<String, List<ZTreeJson>> menuJson = new HashMap<String, List<ZTreeJson>>();

		Set<String> functionSet = new HashSet<String>();
		Set<String> urlSet = new HashSet<String>();
		List<SysMenu> sysMenuList=new ArrayList<SysMenu>();
		List<SysMenu> panalList = new ArrayList<SysMenu>();
		List<ZTreeJson> panals = new ArrayList<ZTreeJson>();
		panalList = queryFunList(userId, "2", null);

		// // 缓存当前用户菜单和权限集
		for (SysMenu sysMenu : panalList) {
			List<ZTreeJson> zTreeFuncList = new ArrayList<ZTreeJson>();
			functionSet.add(sysMenu.getMenuId());
			if(StringUtil.isNotNull(sysMenu.getMenuUrl())){
				urlSet.add(sysMenu.getMenuUrl());
			}
			ZTreeJson pzTree = new ZTreeJson();
			pzTree.setId(sysMenu.getMenuId());
			pzTree.setName(sysMenu.getMenuName());
			pzTree.setIconSkin(sysMenu.getMenuIcon());
			panals.add(pzTree);
			List<SysMenu> menuList = queryFunList(userId, Constant.MENU_TYPE_M, sysMenu.getMenuId());
			for (SysMenu tbSysResMenu : menuList) {
				functionSet.add(tbSysResMenu.getMenuId());
				if(StringUtil.isNotNull(tbSysResMenu.getMenuUrl())){
					urlSet.add(tbSysResMenu.getMenuUrl());
				}
				ZTreeJson ztreeFunc = new ZTreeJson();
				ztreeFunc.setId(tbSysResMenu.getMenuId());
				ztreeFunc.setpId(tbSysResMenu.getMenuPid());
				ztreeFunc.setName(tbSysResMenu.getMenuName());
				ztreeFunc.setIconSkin(tbSysResMenu.getMenuIcon());
				ztreeFunc.setMenuUrl(tbSysResMenu.getMenuUrl());
				zTreeFuncList.add(ztreeFunc);
			}
			menuJson.put(sysMenu.getMenuId(), zTreeFuncList);
		}
		List<SysMenu> funList = queryFunList(userId, Constant.MENU_TYPE_B, null);
		for (SysMenu tbSysResFun : funList) {
			functionSet.add(tbSysResFun.getMenuId());
			if(StringUtil.isNotNull(tbSysResFun.getMenuUrl())){
				urlSet.add(tbSysResFun.getMenuUrl());
			}
			sysMenuList.add(tbSysResFun);
		}
		// 5.将用户所有登录相关信息存入UserBean
		UserBean userSession = new UserBean();
		userSession.setUserId(sysUser.getUserId());
		userSession.setSysUser(sysUser);
		userSession.setFunctionSet(functionSet);
		userSession.setUrlSet(urlSet);
		userSession.setFunctionListStr(JSONArray.toJSONString(sysMenuList));
		userSession.setPanals(panals);
		userSession.setDeliveryList(JSONArray.toJSONString(menuJson.get("i1_delivery")));
		userSession.setSaleList(JSONArray.toJSONString(menuJson.get("i1_sale")));
		userSession.setSysList(JSONArray.toJSONString(menuJson.get("i1_sys")));
		userSession.setRptList(JSONArray.toJSONString(menuJson.get("i1_rpt_mng")));
		userSession.setAnalyList(JSONArray.toJSONString(menuJson.get("i1_analy")));		
		return userSession;
	}

	private List<SysMenu> queryFunList(String userId, String menuType, String menuPanal) {
		List<SysMenu> funList = new ArrayList<SysMenu>();
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder();
		//状态为Y-启用,MENU_ID NOT LIKE 'c%' 过滤CS客户端权限
		sb.append(" SELECT DISTINCT MENU.*  ");
		sb.append("   FROM SYS_USER_ROLE R ");
		sb.append("  INNER JOIN SYS_ROLE_MENU ROLE_MENU ");
		sb.append("     ON R.ROLE_ID = ROLE_MENU.ROLE_ID ");
		sb.append("  INNER JOIN SYS_MENU MENU ");
		sb.append("     ON ROLE_MENU.MENU_ID = MENU.MENU_ID ");
		sb.append("  WHERE MENU.MENU_STAT = 'Y' AND MENU.MENU_ID NOT LIKE 'c%' AND R.USER_ID=:USER_ID ");
		params.put("USER_ID", userId);
		if (menuPanal != null) {
			sb.append(" and MENU.MENU_PANAL=:MENU_PANAL");
			params.put("MENU_PANAL", menuPanal);
		}
		if (menuType != null) {
			sb.append(" AND MENU.MENU_TYPE=:MENU_TYPE");
			params.put("MENU_TYPE", menuType);
		}
		sb.append(" ORDER BY MENU.MENU_STAT,MENU.ORDER_NUM ");
		funList = dbUtil.queryListToBean("", sb.toString(), SysMenu.class, params);
		return funList;
	}

}
