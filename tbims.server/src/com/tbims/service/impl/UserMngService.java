package com.tbims.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tbims.bean.SysUserQBean;
import com.tbims.entity.SysUser;
import com.tbims.entity.SysUserRole;
import com.tbims.entity.SysUserRoleId;
import com.tbims.service.IUserMngService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.FrameProp;
import com.zhming.support.util.MD5;
import com.zhming.support.util.StringUtil;
import com.zhming.support.util.UUIDGenerator;

@Service
public class UserMngService extends BaseService implements IUserMngService {

	@Override
	public PageBean<Map<String, Object>> listUser(UserBean userBean, SysUserQBean sysUserQBean) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A.USER_ID,A.USER_NAME ,A.USER_STAT,A.OUTLET_ID,B.OUTLET_NAME,A.DEPARTMENT, A.TEL,A.POSITION_CODE FROM SYS_USER A LEFT JOIN SYS_OUTLET B ON A.OUTLET_ID=B.OUTLET_ID WHERE USER_ID<>'admin' ");

		// 用户账号
		if (StringUtil.isNotNull(sysUserQBean.getUserId())) {
			sql.append(" and a.USER_ID like :USER_ID");
			params.put("USER_ID", StringUtil.queryParam(sysUserQBean.getUserId()));
		}
		// 用户姓名
		if (StringUtil.isNotNull(sysUserQBean.getUserName())) {
			sql.append(" and a.USER_NAME like :USER_NAME");
			params.put("USER_NAME", StringUtil.queryParam(sysUserQBean.getUserName()));
		}
		// 用户角色
		if (StringUtil.isNotNull(sysUserQBean.getRoleId())) {
			sql.append(" AND (EXISTS (SELECT 1 FROM SYS_USER_ROLE WHERE A.USER_ID=USER_ID AND ROLE_ID=:ROLE_ID))");
			params.put("ROLE_ID", sysUserQBean.getRoleId());
		}
		// 员工状态
		if (StringUtil.isNotNull(sysUserQBean.getUserStat())) {
			sql.append(" AND a.USER_STAT=:USER_STAT");
			params.put("USER_STAT", sysUserQBean.getUserStat());
		}
		// 网点名称
		if (sysUserQBean.getOutletId()!= null) {
			sql.append("  AND A.OUTLET_ID=:OUTLET_ID ");
			params.put("OUTLET_ID", sysUserQBean.getOutletId());
		}
		//岗位
		if (StringUtil.isNotNull(sysUserQBean.getPositionCode())) {
			sql.append(" AND A.POSITION_CODE=:POSITION_CODE");
			params.put("POSITION_CODE", sysUserQBean.getPositionCode());
		}
		sql.append(" ORDER BY UPD_TIME DESC ");

		PageBean<Map<String, Object>> users = dbUtil.queryPageToMap("查询用户", sql.toString(), userBean.getPageNum(), userBean.getPageSize(), params);

		// 查询角色字符串以，分隔
		List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> user : users.getRows()) {
			String roles = "";
			List<Map<String, Object>> userRoleList = dbUtil.queryListToMap(
					"查询角色",
					"SELECT A.ROLE_ID,NVL(B.ROLE_NAME,A.ROLE_ID) ROLE_NAME "
							+ "FROM SYS_USER_ROLE A LEFT JOIN SYS_ROLE B ON A.ROLE_ID=B.ROLE_ID WHERE USER_ID=?",
					user.get("userId"));

			for (Map<String, Object> userRole : userRoleList) {
				if (roles.length() == 0) {
					roles = StringUtil.convertString(userRole.get("roleName"));
				} else {
					roles = roles + "," + userRole.get("roleName");
				}
			}
			user.put("roleNames", roles);
			userList.add(user);
		}

		users.setRows(userList);

		return users;
	}

	@Override
	public void setRoleToUser(UserBean userBean, String userCds, String roleCds) {
		Session session = dbUtil.getSessionByTransaction();
		try {
			List<SysUserRole> tbSysUserRoleList = new ArrayList<SysUserRole>();
			String[] users = userCds.split(",");
			String[] roles = roleCds.split(",");

			for (String user : users) {
				// 删除用户所属角色
				dbUtil.executeSql("", session,
						"DELETE FROM SYS_USER_ROLE WHERE USER_ID=?", user);

				for (String role : roles) {
					SysUserRole tbSysUserRole = new SysUserRole();
					SysUserRoleId tbSysUserRoleId = new SysUserRoleId();
					tbSysUserRoleId.setUserId(user);
					tbSysUserRoleId.setRoleId(role);
					tbSysUserRole.setId(tbSysUserRoleId);
					tbSysUserRoleList.add(tbSysUserRole);
				}
			}

			// 批量增加所属角色
			dbUtil.saveEntityBatch("", session, tbSysUserRoleList);
			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}

	}

	@Override
	public void updateUserStat(UserBean userBean, String userIds, String userStat) throws BaseException {
		dbUtil.executeSql("", "UPDATE SYS_USER SET USER_STAT=? WHERE USER_ID IN (" + userIds + ")", userStat);
	}

	@Override
	public SysUser findByUserCd(UserBean userBean, String userId) throws BaseException {
		return dbUtil.findById("", SysUser.class, userId);
	}

	@Override
	public void passReSet(UserBean userBean, String[] userIds) throws BaseException {
		List<SysUser> users = new ArrayList<SysUser>();
		for(String userId : userIds) {
			SysUser user = dbUtil.findById("查询用户", SysUser.class, userId);
			String salt = getSalt();
			user.setSalt(salt);
			user.setUserPassword(MD5.MD5Encode(MD5.MD5Encode(FrameProp.getInitPassword())+salt));
			user.setUpdUserId(userBean.getSysUser().getUserId());
			user.setUpdTime(new Date());
			users.add(user);
		}
		dbUtil.updateEntityBatch("批量保存修改后的用户", users);
	}

	@Override
	public void addUser(UserBean userBean, SysUser sysUser) throws BaseException {

		SysUser user = dbUtil.findById("验证用户", SysUser.class, sysUser.getUserId());
		if (user != null) {
			throw new ServiceException(MSG.BF_ERROR, "用户账号已存在");
		}
		if("01".equals(sysUser.getPositionCode())&&sysUser.getOutletId()!=null){
			throw new ServiceException(MSG.BF_ERROR, "票务中心用户不用添加“所属网点”");
		}
		sysUser.setUserStat("Y");
		String salt = getSalt();
		sysUser.setSalt(salt);
		sysUser.setUserPassword(MD5.MD5Encode(MD5.MD5Encode(FrameProp.getInitPassword())+salt));
		sysUser.setOpeUserId(userBean.getUserId());
		sysUser.setOpeTime(new Date());
		sysUser.setUpdUserId(userBean.getUserId());
		sysUser.setUpdTime(new Date());
		dbUtil.saveEntity("保存用户", sysUser);
	}

	@Override
	public void updateUser(UserBean userBean, SysUser sysUser) throws BaseException {
		if("01".equals(sysUser.getPositionCode())&&sysUser.getOutletId()!=null){
			throw new ServiceException(MSG.BF_ERROR, "票务中心用户不用添加“所属网点”");
		}
		SysUser oldSysUser = dbUtil.findById("查询用户", SysUser.class, sysUser.getUserId());
		String[] ignoreProperties = {"userPassword", "userStat", "ukeyId", "ukeyCa", "opeUserId", "opeTime", "updUserId", "updTime", "salt"};
		BeanUtils.copyProperties(sysUser, oldSysUser, ignoreProperties);
		oldSysUser.setUpdUserId(userBean.getUserId());
		oldSysUser.setUpdTime(new Date());
		dbUtil.updateEntity("更新用户", oldSysUser);
	}

	@Override
	public void deleteUser(UserBean userBean, String userIds) throws BaseException {
		dbUtil.executeSql("", "DELETE SYS_USER WHERE USER_ID IN (" + userIds + ")");
	}

	@Override
	public void updatePassword(UserBean userBean, String oldpass, String newpass) throws BaseException {

		if ((StringUtil.isNull(userBean.getSysUser().getSalt()) && !userBean.getSysUser().getUserPassword().equals(MD5.MD5Encode(oldpass))) ||
				(!StringUtil.isNull(userBean.getSysUser().getSalt()) && !userBean.getSysUser().getUserPassword().equals(MD5.MD5Encode(MD5.MD5Encode(oldpass)+userBean.getSysUser().getSalt())))){
			throw new ServiceException(MSG.BF_ERROR, "原始密码输入错误");
		}
		String salt = getSalt();
		userBean.getSysUser().setSalt(salt);
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "UPDATE SYS_USER SET USER_PASSWORD =:USER_PASSWORD, SALT=:SALT WHERE USER_ID =:USER_ID ";
		//params.put("USER_PASSWORD", MD5.MD5Encode(newpass));
		params.put("USER_PASSWORD", MD5.MD5Encode(MD5.MD5Encode(newpass)+salt));
		params.put("SALT", salt);
		params.put("USER_ID", userBean.getSysUser().getUserId());
		userBean.getSysUser().setUserPassword((String)params.get("USER_PASSWORD"));

		dbUtil.executeSql("修改密码", sql, params);
	}

	private String getSalt(){
//		SecureRandom random = new SecureRandom();
//		byte[] bytes = new byte[16];
//		random.nextBytes(bytes);
//		String str="";
//		try {
//			str = new String(bytes, "ASCII");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return str;
		return UUIDGenerator.getRandomString(16, true);
	}
}
