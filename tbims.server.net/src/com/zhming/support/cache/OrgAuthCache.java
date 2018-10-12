package com.zhming.support.cache;

import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tbims.entity.SlOrg;
import com.tbims.service.IOrgMngService;
import com.tbims.service.impl.OrgMngService;
import com.webservice.CXFException;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.DBException;
import com.zhming.support.init.SpringContextUtil;
import com.zhming.support.util.FrameProp;
import com.zhming.support.util.StringUtil;

/**
 * Title: 缓存网络代理商信息 <br/>
 * Description:
 * 
 * @ClassName: OrgAuthCache
 * @author ydc
 * @date 2017年7月27日 上午11:28:53
 * 
 */
public class OrgAuthCache {
	private final static Log logger = LogFactory.getLog(OrgAuthCache.class);

	static Map<String, SlOrg> orgMaps = new Hashtable<String, SlOrg>();

	/**
	 * Title:缓存代理商 <br/>
	 * Description:
	 */
	public static void initOrgMaps() {
		IOrgMngService orgMngService = SpringContextUtil.getBean(OrgMngService.class);
		try {
			orgMaps = orgMngService.listOrg("1");
		} catch (DBException e) {
			logger.error("缓存机构信息出错", e);
		}
	}

	/**
	 * Title:获取授权码 <br/>
	 * Description:
	 * 
	 * @param orgID
	 * @return
	 * @throws CXFException
	 */
	public static String getOrgToken(String orgID) throws CXFException {
		SlOrg slOrg = orgMaps.get(orgID);
		if (slOrg == null || StringUtil.isNull(slOrg.getToken())) {
			throw new CXFException(MSG.BF_ERROR, "获取缓存授权码出错");
		} else {
			return slOrg.getToken();
		}
	}

	/**
	 * Title:验证客户端与服务端缓存中的授权码是否一致 <br/>
	 * Description:
	 * 
	 * @param orgID
	 * @param token
	 * @throws CXFException
	 */
	public static boolean checkOrgToken(String orgID, String token) throws CXFException {
		String cacheToken = getOrgToken(orgID);
		if (!cacheToken.equals(token)) {
			throw new CXFException(MSG.BF_ERROR_TOKEN);
		}
		return true;
	}

	/**
	 * Title:验证客户端与服务端缓存中的授权码是否一致 <br/>
	 * Description:
	 * 
	 * @param token 固定授权码
	 * @throws CXFException
	 */
	public static boolean checkQueryToken(String token) throws CXFException {
		String cacheToken = FrameProp.getWebservice_query_token();
		if (!cacheToken.equals(token)) {
			throw new CXFException(MSG.BF_ERROR_TOKEN);
		}
		return true;
	}

}
