package com.tbims.org.impl;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tbims.bean.IdentiyCheckCacheBean;
import com.tbims.db.entity.SlOrg;
import com.tbims.db.util.DBUtil;
import com.tbims.org.OrgInterfaceUtil;
import com.tbims.rpc.entity.RPCException;
import com.tbims.util.DateUtil;
import com.tbims.util.SignUtil;
import com.tbims.util.StringUtil;

@Component
public class OrgInterfaceUtilImpl implements OrgInterfaceUtil {
	private static final Log logger = LogFactory.getLog(OrgInterfaceUtilImpl.class);

	@Autowired
	DBUtil dbUtil;

	@Override
	public void checkTicketCallback(SlOrg slOrg, IdentiyCheckCacheBean identiyCheckCacheBean) throws RPCException {
		MDC.put("PREFIX", "机构核销回调：");
		String reqUrl = slOrg.getCallbackUrl();
		String reqData = slOrg.getCallbackData();
		if (reqData == null || StringUtil.isNull(reqData)) {
			reqData = "{\"orgId\":\"#orgid#\",\"orderId\":\"#orderid#\",\"identityCode\":\"#identity#\",\"checkTime\":\"#checktime#\",\"sign\":\"#sign#\"}";
		}
		reqData = reqData.replaceAll("\\#orgid\\#", slOrg.getOrgId());
		reqData = reqData.replaceAll("\\#orderid\\#", identiyCheckCacheBean.getOrderId());
		reqData = reqData.replaceAll("\\#identity\\#", identiyCheckCacheBean.getIdentiy());
		reqData = reqData.replaceAll("\\#checktime\\#", DateUtil.formatDateToString(identiyCheckCacheBean.getCheckTime(), "yyyy-MM-dd HH:mm:ss"));
		
		StringBuilder content = new StringBuilder();	// 计算回调数据签名
		content.append(slOrg.getOrgId())
			.append(identiyCheckCacheBean.getOrderId())
			.append(identiyCheckCacheBean.getIdentiy())
			.append(DateUtil.formatDateToString(identiyCheckCacheBean.getCheckTime(), "yyyy-MM-dd HH:mm:ss"));
		String sign = SignUtil.sign(content.toString(), slOrg.getToken());
		reqData = reqData.replaceAll("\\#sign\\#", sign);

		logger.debug("机构检票核销回调地址" + reqUrl);
		logger.debug("机构检票核销回调数据" + reqData);

		CloseableHttpResponse response = null;
		CloseableHttpClient client = null;
		try {
			String responseParams = null;

			HttpPost httpPost = new HttpPost(reqUrl);
			StringEntity entityParams = new StringEntity(reqData, "utf-8");
			httpPost.setEntity(entityParams);
			httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
			//httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			// setConnectTimeout:1000*30=30秒
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1000 * 30).setSocketTimeout(1000 * 30).build();
			httpPost.setConfig(requestConfig);

			client = HttpClients.createDefault();
			response = client.execute(httpPost);

			if (response != null && response.getEntity() != null) {
				responseParams = new String(EntityUtils.toByteArray(response.getEntity()), "utf-8");
				logger.debug("机构检票核销回调返回参数：" + responseParams);
				if (!responseParams.toLowerCase().contains("success")) {
					throw new RPCException(3, "机构检票核销回调接口,调用接口返回错误");
				}
			} else {
				throw new RPCException(3, "机构检票核销回调接口,调用接口错误");
			}

		} catch (RPCException e) {
			throw e;
		} catch (Exception e) {
			logger.error("机构检票核销回调接口,系统异常", e);
			throw new RPCException(3, "机构检票核销回调接口,系统异常");
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (client != null) {
					client.close();
				}
			} catch (IOException e) {
				logger.error("机构检票核销回调接口,系统异常", e);
			}
			MDC.clear();
		}
	}

}
