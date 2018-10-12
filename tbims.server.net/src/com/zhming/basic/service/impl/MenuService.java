package com.zhming.basic.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbims.entity.SysMenu;
import com.zhming.basic.service.IMenuService;
import com.zhming.support.db.DBUtil;

@Service
public class MenuService implements IMenuService {

	@Autowired
	private DBUtil dbUtil;

	@Override
	public Map<String, SysMenu> listMenu() {
		Map<String, SysMenu> menuMaps = new HashMap<String, SysMenu>();
		List<SysMenu> listMenu = dbUtil.queryListToBean("缓存权限信息", "select * from SYS_MENU", SysMenu.class);
		for (SysMenu menu : listMenu) {
			menuMaps.put(menu.getMenuId(), menu);
		}
		return menuMaps;
	}

	@Override
	public Map<String, String> getNavigationMaps() {
		return null;
	}

}
