package com.webservice.infoQuery;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;

import com.webservice.CXFException;
import com.webservice.WSUtil;
import com.webservice.entity.SaleCheckEntity;
import com.zhming.support.cache.OrgAuthCache;
import com.zhming.support.common.MSG;
import com.zhming.support.db.DBUtil;
import com.zhming.support.db.impl.DBUtilImpl;
import com.zhming.support.init.SpringContextUtil;
import com.zhming.support.util.UUIDGenerator;

@WebService(endpointInterface = "com.webservice.infoQuery.IInfoQuery")
public class InfoQueryImpl implements IInfoQuery {
	private final Log logger = LogFactory.getLog(getClass());
	private static final String SERVICE_NAME = "售检信息查询服务";

	@Override
	public SaleCheckEntity getSaleCheckinByDate(String token, Date selDate, String transq) throws CXFException {
		DBUtil dbUtil = SpringContextUtil.getBean(DBUtilImpl.class);
		String logId = UUIDGenerator.getPK();
		String menuId = "日售票检票汇总查询接口";
		int logType = 4;
		String navigation = String.format("[%s]-%s-%s-%s", logId, SERVICE_NAME, menuId, transq);
		try {
			MDC.put("navigation", navigation);
			OrgAuthCache.checkQueryToken(token);

			if (selDate == null) {
				throw new CXFException(MSG.BF_ERROR, "参数错误");
			}

			StringBuffer sql = new StringBuffer();
			Map<String, Object> params = new HashMap<String, Object>();
			sql.append("SELECT * FROM (                                                       ");
			sql.append("SELECT COUNT(1) SALE_COUNT                                            ");
			sql.append("  FROM SL_ORDER A, SL_ORDER_DETAIL B                                  ");
			sql.append(" WHERE A.ORDER_ID = B.ORDER_ID                                        ");
			sql.append("   AND TRUNC(A.SALE_TIME, 'DD') = TRUNC(:SELDATE, 'DD')                ");
			sql.append("   AND (USELESS_FLAG='N' OR (USELESS_FLAG='Y' AND CHECK_FLAG='Y'))    ");
			sql.append(") A,(                                                                 ");
			sql.append("SELECT COUNT(1) CHECK_INCOUNT                                         ");
			sql.append("  FROM SL_CHECK C                                                     ");
			sql.append(" WHERE TICKET_CLASS IN ('1', '2', '3')                                ");
			sql.append("   AND PASS_FLAG = 'Y'                                                ");
			sql.append("   AND TRUNC(C.OPE_TIME, 'DD') = TRUNC(:SELDATE, 'DD')                 ");
			sql.append(") B                                                                   ");

			params.put("SELDATE", selDate);

			SaleCheckEntity saleCheckEntity = dbUtil.queryFirstForBean("", sql.toString(), SaleCheckEntity.class, params);

			return saleCheckEntity;
		} catch (CXFException e) {
			logger.error(e);
			WSUtil.saveLog(logId, "", logType, menuId, e.getErrcode(), e.getErrinfo(), transq);
			throw e;
		} catch (Exception e) {
			logger.error("错误", e);
			WSUtil.saveLog(logId, "", logType, menuId, MSG.ERROR, null, transq);
			throw new CXFException(MSG.ERROR);
		} finally {
			// 清空log4j的mdc信息
			@SuppressWarnings("rawtypes")
			Map map = MDC.getContext();
			if (map != null) {
				map.clear();
			}
		}
	}

	@Override
	public SaleCheckEntity getCurrentSaleCheckin(String token, String transq) throws CXFException {
		DBUtil dbUtil = SpringContextUtil.getBean(DBUtilImpl.class);
		String logId = UUIDGenerator.getPK();
		String menuId = "当日售票检票查询接口";
		int logType = 4;
		String navigation = String.format("[%s]-%s-%s-%s", logId, SERVICE_NAME, menuId, transq);
		try {
			MDC.put("navigation", navigation);
			OrgAuthCache.checkQueryToken(token);

			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM (                                                       ");
			sql.append("SELECT COUNT(1) SALE_COUNT                                            ");
			sql.append("  FROM SL_ORDER A, SL_ORDER_DETAIL B                                  ");
			sql.append(" WHERE A.ORDER_ID = B.ORDER_ID                                        ");
			sql.append("   AND TRUNC(A.SALE_TIME, 'DD') = TRUNC(SYSDATE, 'DD')                ");
			sql.append("   AND (USELESS_FLAG='N' OR (USELESS_FLAG='Y' AND CHECK_FLAG='Y'))    ");
			sql.append(") A,(                                                                 ");
			sql.append("SELECT COUNT(1) CHECK_INCOUNT                                         ");
			sql.append("  FROM SL_CHECK C                                                     ");
			sql.append(" WHERE TICKET_CLASS IN ('1', '2', '3')                                ");
			sql.append("   AND PASS_FLAG = 'Y'                                                ");
			sql.append("   AND TRUNC(C.OPE_TIME, 'DD') = TRUNC(SYSDATE, 'DD')                 ");
			sql.append(") B                                                                   ");

			SaleCheckEntity saleCheckEntity = dbUtil.queryFirstForBean("", sql.toString(), SaleCheckEntity.class);

			return saleCheckEntity;
		} catch (CXFException e) {
			logger.error(e);
			WSUtil.saveLog(logId, "", logType, menuId, e.getErrcode(), e.getErrinfo(), transq);
			throw e;
		} catch (Exception e) {
			logger.error("错误", e);
			WSUtil.saveLog(logId, "", logType, menuId, MSG.ERROR, null, transq);
			throw new CXFException(MSG.ERROR);
		} finally {
			// 清空log4j的mdc信息
			@SuppressWarnings("rawtypes")
			Map map = MDC.getContext();
			if (map != null) {
				map.clear();
			}
		}
	}

}
