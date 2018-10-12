package com.tbims.cache;

import java.util.HashMap;
import java.util.Map;

import com.tbims.bean.AuthBean;
import com.tbims.rpc.entity.AUTHORIZATION;
import com.tbims.rpc.entity.RPCException;
import com.tbims.util.StringUtil;

/**
 * Title: 缓存终端授权码等信息 <br/>
 * Description:
 * 
 * @ClassName: ClientAuthCache
 * @author ydc
 * @date 2017年6月16日 下午4:51:06
 * 
 */
public class ClientAuthCache {
	private static Map<Long, AuthBean> authorizationMap = new HashMap<Long, AuthBean>();

	/**
	 * Title: 缓存授权信息bean <br/>
	 * Description:
	 * 
	 * @param client 终端编码
	 * @param token 授权码
	 */
	public static void setAuthorizationMap(AuthBean auth) {
		authorizationMap.put(auth.getAuth().getClientId(), auth);
	}

	/**
	 * Title: 缓存授权信息bean <br/>
	 * Description:
	 * 
	 * @param client 终端编码
	 * @param token 授权码
	 */
	public static void setAuthorizationMap(long clientId, AuthBean auth) {
		authorizationMap.put(clientId, auth);
	}

	/**
	 * Title: 获取缓存集 <br/>
	 * Description:
	 * 
	 * @return
	 * @throws RPCException
	 */
	public static Map<Long, AuthBean> getAuthorizationMap() throws RPCException {
		if (authorizationMap == null) {
			throw new RPCException(3, "缓存信息出错");
		}
		return authorizationMap;
	}

	/**
	 * Title: 获取授权码<br/>
	 * Description:
	 * 
	 * @param client 终端编码
	 * @return
	 */
	public static String getToken(Long client) {
		AuthBean auth = authorizationMap.get(client);
		if (auth != null && auth.getAuth() != null) {
			return auth.getAuth().getToken();
		}
		return null;
	}

	/** 
	 * Title: 校验授权码是否正确 <br/>
	* Description: 
	* @param auth 客户端授权码
	* @param authBean 服务器端缓存信息
	* @throws RPCException
	*/ 
	public static void checkToken(AUTHORIZATION auth,AuthBean authBean) throws RPCException {
		if (auth == null) {
			throw new RPCException(3000101, MsgUtil.getMsg(3000101));
		}

		//客户端传入的token
		String token = auth.getToken();
		if (StringUtil.isNull(token)) {
			throw new RPCException(3000101, MsgUtil.getMsg(3000101));
		}
		
		//客户端传入的token与服务器缓存中的token进行比较
		if (!token.equals(authBean.getAuth().getToken())) {
			throw new RPCException(3000102, MsgUtil.getMsg(3000102));
		}
	}

	/**
	 * Title:获取指定终端号的信息 <br/>
	 * Description:
	 * 
	 * @param clientId
	 * @return
	 * @throws RPCException
	 */
	public static AuthBean getAuthBean(Long clientId) throws RPCException {
		if (authorizationMap == null) {
			throw new RPCException(3, "缓存信息出错");
		}

		AuthBean authBean = authorizationMap.get(clientId);
		if (authBean == null) {
			throw new RPCException(3000101, String.format("终端编号[%s]", clientId) + MsgUtil.getMsg(3000101));
		}

		return authBean;
	}
}
