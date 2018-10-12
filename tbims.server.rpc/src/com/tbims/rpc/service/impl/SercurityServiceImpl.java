package com.tbims.rpc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tbims.annontion.ControlAspect;
import com.tbims.bean.AuthBean;
import com.tbims.cache.ClientAuthCache;
import com.tbims.cache.MsgUtil;
import com.tbims.common.NameConvert;
import com.tbims.common.OperType;
import com.tbims.db.entity.SysClient;
import com.tbims.db.entity.SysLoginCache;
import com.tbims.db.entity.SysMenu;
import com.tbims.db.entity.SysOutlet;
import com.tbims.db.entity.SysUser;
import com.tbims.db.util.DBUtil;
import com.tbims.rpc.entity.AUTHORIZATION;
import com.tbims.rpc.entity.LOGIN_USER_INFO;
import com.tbims.rpc.entity.RPCException;
import com.tbims.util.CommonUtil;
import com.tbims.util.StringUtil;
import com.tbims.util.UUIDGenerator;
import com.tbims.util.MD5;

@Component
public class SercurityServiceImpl implements com.tbims.rpc.service.SercurityService.Iface {
	private static final Log logger = LogFactory.getLog(SercurityServiceImpl.class);

	@Autowired
	DBUtil dbUtil;

	@Override
	@ControlAspect(funtionName = "客户端登录")
	public LOGIN_USER_INFO login(long client_id, String userId, String password) throws RPCException, TException {
		LOGIN_USER_INFO login_user_info = new LOGIN_USER_INFO();
		AUTHORIZATION auth = null;
		// 参数校验
		if (StringUtil.isNull(userId)) {
			throw new RPCException(1000101, MsgUtil.getMsg(1000101));
		}

		if (StringUtil.isNull(password)) {
			throw new RPCException(1000201, MsgUtil.getMsg(1000201));
		}

		SysClient sysClient = dbUtil.findById("校验终端码", SysClient.class, client_id);
		if (sysClient == null) {
			throw new RPCException(30001, MsgUtil.getMsg(30001));
		}
		if ("N".equals(sysClient.getStat())) {
			throw new RPCException(30001, "终端已停用");
		}

		// 查询用户信息
		SysUser sysUser = dbUtil.findById("查询用户", SysUser.class, userId);
		if (sysUser == null) {
			throw new RPCException(10001, MsgUtil.getMsg(10001));
		}
		if ((StringUtil.isNull(sysUser.getSalt()) && !sysUser.getUserPassword().equals(password)) ||
				(!StringUtil.isNull(sysUser.getSalt()) && !MD5.MD5Encode(password+sysUser.getSalt()).equals(sysUser.getUserPassword()))) {
			throw new RPCException(10002, MsgUtil.getMsg(10002));
		}
		if ("N".equals(sysUser.getUserStat())) {
			throw new RPCException(10004, MsgUtil.getMsg(10004));
		}

		SysOutlet sysOutlet = null;
		// 终端类型为网点时校验，终端类型(3-缓存服务器1-网点,2-闸机,4-控制终端)
		if ("1".equals(sysClient.getClientType())) {
			// 校验用户岗位是否为网点，岗位(01-票务中心02-网点管理岗03-网点操作岗)
			if (StringUtil.isNull(sysUser.getPositionCode())) {
				throw new RPCException(10004, "用户岗位未设置");
			}
			Set<String> positions = new HashSet<String>();
			positions.add("02");
			positions.add("03");
			if (!positions.contains(sysUser.getPositionCode())) {
				throw new RPCException(5, "非网点岗位不允许登录");
			}

			// 校验用户所属网点和终端编号网点是否一致
			if (CommonUtil.covertLong(sysClient.getOutletId()) != CommonUtil.covertLong(sysUser.getOutletId())) {
				throw new RPCException(3000103, MsgUtil.getMsg(3000103));
			}

			sysOutlet = dbUtil.findById("查询网点信息", SysOutlet.class, sysClient.getOutletId());
			if (sysOutlet == null) {
				throw new RPCException(30002, MsgUtil.getMsg(30002));
			}
		}
		
		// 查询用户权限信息
		StringBuffer menus = new StringBuffer();
		List<SysMenu> funList = new ArrayList<SysMenu>();
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT DISTINCT MENU.*  ");
		sb.append("   FROM SYS_USER_ROLE R ");
		sb.append("  INNER JOIN SYS_ROLE_MENU ROLE_MENU ");
		sb.append("     ON R.ROLE_ID = ROLE_MENU.ROLE_ID ");
		sb.append("  INNER JOIN SYS_MENU MENU ");
		sb.append("     ON ROLE_MENU.MENU_ID = MENU.MENU_ID ");
		sb.append("  WHERE MENU.MENU_STAT = 'Y' AND R.USER_ID=:USER_ID ");
		params.put("USER_ID", userId);
		funList = dbUtil.queryListToBean("查询权限集", sb.toString(), SysMenu.class, params);

		for (SysMenu menu : funList) {
			if (menus.length() != 0) {
				menus.append(",");
			}
			menus.append(menu.getMenuId());
		}

		// 缓存网点授权码
		auth = new AUTHORIZATION(client_id, UUIDGenerator.getRandomString(32));
		AuthBean authBean = new AuthBean();
		authBean.setAuth(auth);
		authBean.setSysUser(sysUser);
		authBean.setSysClient(sysClient);
		authBean.setSysOutlet(sysOutlet);
		ClientAuthCache.setAuthorizationMap(authBean);

		// 如果盐为空，设置盐
		if(StringUtil.isNull(sysUser.getSalt())){
			changePass(auth, sysUser.getUserId(), password, password);
			sysUser = dbUtil.findById("查询用户", SysUser.class, userId);
			authBean.setSysUser(sysUser);
		}
		// 设置返回实体
		// string user_id , /** 用户编号 */
		// string user_nm , /** 用户名称 */
		// string user_password , /** 用户密码 */
		// string user_stat , /** 用户状态(N停用Y启用) */
		// string outlet_id , /** 所属网点 */
		// string department , /** 所属部门 */
		// string token , /** 授权码 */
		// string menus , /** 权限集,以逗分隔 */
		if (StringUtil.isNull(sysUser.getPositionCode())) {
			throw new RPCException(10004, "用户岗位未设置");
		}

		login_user_info.setUserId(sysUser.getUserId());
		login_user_info.setUserName(sysUser.getUserName());
		login_user_info.setUserStat(StringUtil.convertString(sysUser.getUserStat()));
		login_user_info.setOutletId(CommonUtil.covertLong(sysUser.getOutletId()));
		if (sysOutlet != null) {
			login_user_info.setOutletName(sysOutlet.getOutletName());
			login_user_info.setOutletType(sysOutlet.getOutletType());
		} else {
			login_user_info.setOutletType("");
			login_user_info.setOutletName("");
		}
		login_user_info.setDepartment(sysUser.getDepartment());
		login_user_info.setToken(auth.getToken());
		login_user_info.setPositionCode(sysUser.getPositionCode());
		login_user_info.setPositionName(NameConvert.getPositionName(sysUser.getPositionCode()));
		login_user_info.setMenus(menus.toString());

		SysLoginCache sysLoginCache = new SysLoginCache();
		sysLoginCache.setClientId(sysClient.getClientId());
		sysLoginCache.setUserId(userId);
		sysLoginCache.setOutletId(CommonUtil.covertLong(sysUser.getOutletId()));
		sysLoginCache.setTokey(auth.getToken());
		dbUtil.saveOrUpdateEntity("缓存登录信息", sysLoginCache);
		return login_user_info;
	}

	@Override
	@ControlAspect(funtionName = "客户端密码修改", operType = OperType.UPDATE)
	public boolean changePass(AUTHORIZATION auth, String userId, String oldPassword, String newPassword) throws RPCException, TException {

		// 参数校验
		if (StringUtil.isNull(userId)) {
			throw new RPCException(1000101, MsgUtil.getMsg(1000101));
		}

		if (StringUtil.isNull(oldPassword)) {
			throw new RPCException(1000201, MsgUtil.getMsg(1000201));
		}

		if (StringUtil.isNull(newPassword)) {
			throw new RPCException(1000201, MsgUtil.getMsg(1000201));
		}

		// 查询用户信息，验证旧密码是否相同
		SysUser user = dbUtil.findById("", SysUser.class, userId);
		if (user == null) {
			throw new RPCException(10001, MsgUtil.getMsg(10001));
		}
//		if (!user.getUserPassword().equals(oldPassword)) {
//			throw new RPCException(1000202, MsgUtil.getMsg(1000202));
//		}
		if ((StringUtil.isNull(user.getSalt()) && !user.getUserPassword().equals(oldPassword)) ||
				(!StringUtil.isNull(user.getSalt()) && !MD5.MD5Encode(oldPassword+user.getSalt()).equals(user.getUserPassword()))) {
			throw new RPCException(1000202, MsgUtil.getMsg(1000202));
		}

		// 更新盐和新密码
		String salt = getSalt();
		user.setSalt(salt);
		user.setUserPassword(MD5.MD5Encode(newPassword+salt));
		dbUtil.updateEntity("更新密码", user);

		return true;
	}

	@Override
	public String getRandom(long client_id) throws RPCException, TException {
		return null;
	}

	@Override
	public LOGIN_USER_INFO loginByNet(long client_id, String userId, String password, String encDataClient) throws RPCException, TException {
		return null;
	}

	@Override
	@ControlAspect(funtionName = "发送心跳包", operType = OperType.SCHEDULER)
	public boolean ping(AUTHORIZATION auth) throws RPCException, TException {
		logger.trace(auth.getClientId() + "发送心跳包");
		return true;
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
