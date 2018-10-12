package com.zhming.basic.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.tbims.entity.SysRole;
import com.tbims.entity.SysRoleMenu;
import com.tbims.entity.SysRoleMenuId;
import com.zhming.basic.service.IRoleMngService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.DBException;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.StringUtil;

@Service
public class RoleMngService extends BaseService implements IRoleMngService {

	@Override
	public SysRole findByRoleId(UserBean userBean, String role_id) throws DBException {
		SysRole role = dbUtil.findById("根据角色代码查询角色", SysRole.class, role_id);
		return role;
	}

	@Override
	public void addRole(UserBean userBean, SysRole role, String funcKeys) throws ServiceException {
		SysRole temRole = dbUtil.findById("根据角色代码查询角色是否存在", SysRole.class, role.getRoleId());
		if (temRole != null) {
			throw new ServiceException(MSG.BF_ERROR, "角色代码已存在");
		}
		if (StringUtil.isChineseCode(role.getRoleId())) {
			throw new ServiceException(MSG.BF_ERROR, "角色代码不能包含汉字，请重新输入");
		}
		Session session = dbUtil.getSessionByTransaction();
		try {
			List<SysRoleMenu> sysRoles = new ArrayList<SysRoleMenu>();
			// 角色和权限的关联
			String[] funcArray = funcKeys.split(",");
			for (String fun : funcArray) {
				if (fun.equals("-1")) {
					continue;
				}
				if (StringUtil.isNull(fun)) {
					break;
				}

				SysRoleMenu roleMenu = new SysRoleMenu();
				roleMenu.setId(new SysRoleMenuId(role.getRoleId(), fun));
				sysRoles.add(roleMenu);
			}

			// 保存角色
			dbUtil.saveEntity("保存角色", session, role);
			dbUtil.saveEntityBatch("保存角色对应权限", session, sysRoles);

			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}
	}
	@Override
	public void updateRole(UserBean userBean, SysRole role, String funcKeys) throws DBException {
		Session session = dbUtil.getSessionByTransaction();
		try {
			List<SysRoleMenu> sysRoles = new ArrayList<SysRoleMenu>();
			// 修改角色
			dbUtil.updateEntity("更新角色", session, role);
			// 删除角色权限关联
			dbUtil.executeSql("删除角色权限关联", session, "DELETE FROM SYS_ROLE_MENU WHERE ROLE_ID =?", role.getRoleId());

			// 保存角色权限关联
			String[] funcArray = funcKeys.replace("'", "").split(",");
			for (String fun : funcArray) {
				if (fun.equals("-1")) {
					continue;
				}
				if (StringUtil.isNull(fun)) {
					break;
				}
				SysRoleMenu roleMenu = new SysRoleMenu();
				roleMenu.setId(new SysRoleMenuId(role.getRoleId(), fun));
				sysRoles.add(roleMenu);
			}

			dbUtil.saveEntityBatch("保存角色权限关联", session, sysRoles);

			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}
	}

	@Override
	public void deleteByRoleIds(UserBean userBean, String role_id) throws DBException {
		Session session = dbUtil.getSessionByTransaction();
		try {
			// 删除角色权限关联
			dbUtil.executeSql("删除角色权限关联", session, "DELETE FROM SYS_ROLE_MENU WHERE ROLE_ID=?", role_id);
			// 删除角色
			dbUtil.executeSql("删除角色", session, "DELETE FROM SYS_ROLE WHERE ROLE_ID=?", role_id);
			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}
	}

	@Override
	public PageBean<Map<String, Object>> listRole(UserBean userBean, SysRole role) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("select * from SYS_ROLE WHERE 1=1");

		// 角色代码
		if (StringUtil.isNotNull(role.getRoleId())) {
			sql.append(" and  ROLE_ID LIKE :ROLE_ID ");
			params.put("ROLE_ID", StringUtil.queryParam(role.getRoleId()));
		}
		// 角色名称
		if (StringUtil.isNotNull(role.getRoleName())) {
			sql.append(" and ROLE_NAME LIKE :ROLE_NAME ");
			params.put("ROLE_NAME", StringUtil.queryParam(role.getRoleName()));
		}
		sql.append(" ORDER BY ROLE_TYPE,ROLE_ID ");
		PageBean<Map<String, Object>> roles = dbUtil.queryPageToMap("查询角色列表", sql.toString(), userBean.getPageNum(), userBean.getPageSize(), params);
		return roles;
	}

	@Override
	public Set<String> findRoleFunc(UserBean userBean, String role_id) {
		List<Map<String, Object>> roleFuncs = dbUtil.queryListToMap("查询角色对应权限", "SELECT * FROM SYS_ROLE_MENU WHERE ROLE_ID=?", role_id);
		Set<String> funcCds = new HashSet<String>();
		for (Map<String, Object> o : roleFuncs) {
			funcCds.add(StringUtil.convertString(o.get("MENU_ID")));
		}
		return funcCds;
	}

}