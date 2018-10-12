package com.tbims.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tbims.bean.PayTypeBean;
import com.tbims.bean.RptStrinfoDBean;
import com.tbims.bean.RptStrinfoOutletDBean;
import com.tbims.bean.SyntheticalBean;
import com.tbims.entity.SysUser;
import com.tbims.service.IRptSumService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.BaseException;
import com.zhming.support.util.StringUtil;

@Service
public class RptSumService extends BaseService implements IRptSumService {
	@Override
	public PageBean<RptStrinfoDBean> listRptStrinfoD(UserBean userBean, Date startDate,Date endDate, String ticketTypeId) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sb = " SELECT R.*, TY.TICKET_TYPE_NAME ";
		sb += "  FROM RPT_STRINFO_D R       ";
		sb += "  INNER JOIN SYS_TICKET_TYPE TY     ";
		sb += "  ON R.TICKET_TYPE_ID = TY.TICKET_TYPE_ID WHERE 1=1 ";
		if (StringUtil.isNotNull(ticketTypeId)) {
			sb += " AND R.TICKET_TYPE_ID =:TICKET_TYPE_ID ";
			params.put("TICKET_TYPE_ID", ticketTypeId);
		}
		if (startDate != null&&endDate != null) {
			sb += " AND R.RPT_DATE >=:START_DATE ";
			sb += " AND R.RPT_DATE <=:END_DATE ";
			params.put("START_DATE", startDate);
			params.put("END_DATE", endDate);
		}
		sb += " ORDER BY R.RPT_DATE , R.TICKET_TYPE_ID  ";
		PageBean<RptStrinfoDBean> ret = dbUtil.queryPageToBean("库存查询", sb, RptStrinfoDBean.class, userBean.getPageNum(), userBean.getPageSize(), params);
		return ret;

	}

	@Override
	public PageBean<RptStrinfoOutletDBean> listRptSumOutlet(UserBean userBean, Date startDate,Date endDate, Long outletId, String ticketTypeId) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sb = " SELECT T.*,TY.TICKET_TYPE_NAME,Y.OUTLET_NAME ";
		sb += "   FROM RPT_STRINFO_OUTLET_D T ";
		sb += "  INNER JOIN SYS_OUTLET Y ";
		sb += "  ON T.OUTLET_ID = Y.OUTLET_ID  ";
		sb += "  INNER JOIN SYS_TICKET_TYPE TY ";
		sb += "  ON T.TICKET_TYPE_ID = TY.TICKET_TYPE_ID  WHERE 1=1";
		sb+= " AND ( STR_LAST_TICKET_NUM<>0 or IN_TICKET_NUM<>0 or SALE_TICKET_NUM<>0 or USELESS_TICKET_NUM<>0 or STR_TICKET_NUM<>0 or SUPPLY_TICKET_NUM<>0 ) ";
		if (StringUtil.isNotNull(ticketTypeId)) {
			sb += " AND T.TICKET_TYPE_ID =:TICKET_TYPE_ID ";
			params.put("TICKET_TYPE_ID", ticketTypeId);
		}
		if (outletId != null) {
			sb += " AND T.OUTLET_ID =:OUTLET_ID ";
			params.put("OUTLET_ID", outletId);
		}
		if (startDate != null&& endDate != null) {
			sb += " AND T.RPT_DATE >=:START_DATE ";
			sb += " AND T.RPT_DATE <=:END_DATE ";
			params.put("START_DATE", startDate);
			params.put("END_DATE", endDate);
		}
		sb += " ORDER BY T.RPT_DATE ,T.OUTLET_ID, T.TICKET_TYPE_ID  ";
		PageBean<RptStrinfoOutletDBean> ret = dbUtil.queryPageToBean("网点库存查询", sb, RptStrinfoOutletDBean.class, userBean.getPageNum(), userBean.getPageSize(), params);
		return ret;
	}

	@Override
	public List<PayTypeBean> listPayType( Date startDate, Date endDate, String outletId, String userId) throws BaseException {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		sql.append(" SELECT RPT_DATE,TICKET_TYPE_ID,TICKET_TYPE_NAME, ");
		sql.append("    OUTLET.OUTLET_ID,OUTLET.OUTLET_NAME, ");
		sql.append("    USER_ID,USER_NAME, ");
		sql.append("    SUM(MONEY1)AS MONEY,SUM(POS_MACHINE1)AS POS_MACHINE, SUM(WECHAT1)AS WECHAT, ");
		sql.append(" SUM(ALIPAY1)AS ALIPAY, ");
		sql.append(" SUM(AGENTS1)AS AGENTS, ");
		sql.append("   (SUM(MONEY1)+SUM(POS_MACHINE1)+SUM(WECHAT1)+SUM(ALIPAY1)+SUM(AGENTS1)) AS TOTAL ");
		sql.append("    FROM SYS_OUTLET OUTLET ");
		sql.append("    INNER JOIN ( ");
		sql.append("       SELECT TRUNC(O.SALE_TIME, 'dd') RPT_DATE, ");
		sql.append(" 	DE.OUTLET_ID AS OUTLET_ID,DITPAY.ITEM_CD AS ITEM_CD, ");
		sql.append(" 	DITPAY.ITEM_NAME AS PAY_TYPE_DESC,DE.TICKET_TYPE_ID AS TICKET_TYPE_ID, ");
		sql.append("    TY.TICKET_TYPE_NAME AS TICKET_TYPE_NAME,O.SALE_USER_ID AS USER_ID, ");
		sql.append("    US.USER_NAME AS USER_NAME, ");
		sql.append("	CASE DITPAY.ITEM_CD WHEN '01' THEN");
		sql.append("	   (SUM(CASE WHEN O.ORDER_TYPE IN ('XC','ZY','TD','WL','ST','BP','ZG','ZQ') THEN ");
		sql.append("			DE.SALE_PRICE ELSE 0 END)) ELSE 0 END MONEY1, ");
		sql.append("    CASE DITPAY.ITEM_CD WHEN '02' THEN ");
		sql.append("	   (SUM(CASE WHEN O.ORDER_TYPE IN ('XC','ZY','TD','WL','ST','BP','ZG','ZQ') THEN ");
		sql.append("	 		DE.SALE_PRICE ELSE 0 END)) ELSE 0 END POS_MACHINE1, ");
		sql.append("     CASE DITPAY.ITEM_CD WHEN '03' THEN ");
		sql.append(" 	   (SUM(CASE WHEN O.ORDER_TYPE IN ('XC','ZY','TD','WL','ST','BP','ZG','ZQ') THEN ");
		sql.append(" 	 		DE.SALE_PRICE  ELSE 0 END)) ELSE 0 END WECHAT1, ");
		sql.append(" 	 CASE DITPAY.ITEM_CD WHEN '04' THEN ");
		sql.append("	   (SUM(CASE WHEN O.ORDER_TYPE IN ('XC','ZY','TD','WL','ST','BP','ZG','ZQ') THEN ");
		sql.append("	  		DE.SALE_PRICE ELSE 0 END)) ELSE 0 END ALIPAY1, ");
		sql.append("	 CASE DITPAY.ITEM_CD WHEN '05' THEN ");
		sql.append("	   (SUM(CASE WHEN O.ORDER_TYPE IN ('XC','ZY','TD','WL','ST','BP','ZG','ZQ') THEN ");
		sql.append("			DE.SALE_PRICE ELSE 0 END)) ELSE 0 END AGENTS1 ");
		sql.append(" FROM SL_ORDER O ");
		sql.append("  INNER JOIN SYS_DICTIONARY DIT ");
		sql.append("  	ON O.ORDER_TYPE = DIT.ITEM_VAL ");
		sql.append("  	AND DIT.KEY_CD = 'ORDER_TYPE' ");
		sql.append("  INNER JOIN SL_PAY_TYPE PT ");
		sql.append(" 	 ON O.ORDER_ID = PT.ORDER_ID ");
		sql.append("  INNER JOIN SYS_DICTIONARY DITPAY ");
		sql.append("  	ON PT.PAY_TYPE = DITPAY.ITEM_VAL ");
		sql.append("  	AND DITPAY.KEY_CD = 'PAY_TYPE' ");
		sql.append("  INNER JOIN SL_ORDER_DETAIL DE ");
		sql.append("  	ON O.ORDER_ID = DE.ORDER_ID ");
		sql.append("  INNER JOIN SYS_TICKET_TYPE TY ");
		sql.append("  	ON DE.TICKET_TYPE_ID = TY.TICKET_TYPE_ID ");
		sql.append("  LEFT JOIN SYS_USER US ");
		sql.append("  	ON US.USER_ID = O.SALE_USER_ID  WHERE O.PAY_STAT='2' AND O.ORDER_TYPE<>'ZY' ");
		sql.append(" GROUP BY TRUNC(O.SALE_TIME, 'dd'), ");
		sql.append(" DE.OUTLET_ID,DITPAY.ITEM_CD, ");
		sql.append(" O.SALE_USER_ID,US.USER_NAME, ");
		sql.append(" DE.TICKET_TYPE_ID,DITPAY.ITEM_NAME,TY.TICKET_TYPE_NAME ) T ");
		sql.append(" ON OUTLET.OUTLET_ID = T.OUTLET_ID WHERE 1=1 ");
		if (startDate != null && endDate != null) {
			sql.append("   AND RPT_DATE>=:START_DATE ");
			sql.append("   AND RPT_DATE<=:END_DATE ");
			params.put("START_DATE", startDate);
			params.put("END_DATE", endDate);
		}
		if (StringUtil.isNotNull(outletId)) {
			sql.append(" AND  OUTLET.OUTLET_ID=:OUTLET_ID ");
			params.put("OUTLET_ID", outletId);
		}
		if(StringUtil.isNotNull(userId)){
			sql.append(" AND USER_ID=:USER_ID ");
			params.put("USER_ID", userId);
		}
		sql.append(" GROUP BY RPT_DATE,TICKET_TYPE_ID,TICKET_TYPE_NAME,OUTLET.OUTLET_ID,OUTLET.OUTLET_NAME,USER_ID,USER_NAME  ");
		sql.append(" ORDER BY RPT_DATE,OUTLET.OUTLET_ID,OUTLET.OUTLET_NAME,TICKET_TYPE_ID,TICKET_TYPE_NAME,USER_ID,USER_NAME  DESC ");
		String  sb =sql.toString();
		List<PayTypeBean> ret = dbUtil.queryListToBean("支付方式统计", sb, PayTypeBean.class, params);
		return ret;
	}

	@Override
	public List<SysUser> userList(Long outletId) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sb =" SELECT * FROM SYS_USER  WHERE OUTLET_ID=:OUTLET_ID";
		params.put("OUTLET_ID", outletId);
		List<SysUser> userList = dbUtil.queryListToBean("",sb ,SysUser.class,params);
		return userList;
	}

	@Override
	public List<SyntheticalBean> listSynthetical(Date startDate, Date endDate, String ticketTypeId) throws BaseException {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sql.append(" SELECT W.RTP_DATE,                                                       "); 
		sql.append("        W.TICKET_TYPE_ID,                                                 ");
		sql.append("        W.TICKET_TYPE_NAME,                                               ");
		sql.append("        W.TICKET_NUM,                                                     ");
		sql.append("        W.SALE_NUM,                                                       ");
		sql.append("        W.CHECK_NUM                                                       ");
		sql.append(" FROM (                                                                   ");
		sql.append(" SELECT A.RTP_DATE,                                                       ");
		sql.append("        A.TICKET_TYPE_ID,                                                 ");
		sql.append("        SUM(A.TICKET_NUM) AS TICKET_NUM,                                  ");
		sql.append("        SUM(A.CHECK_NUM) AS CHECK_NUM,                                    ");
		sql.append("        SUM(A.SALE_NUM) AS SALE_NUM,                                      ");
		sql.append("        S.TICKET_TYPE_NAME,                                               ");
		sql.append("        1 ORDERNUM                                                        ");
		sql.append("   FROM (SELECT R.RPT_DATE       AS RTP_DATE,                             ");
		sql.append("                R.TICKET_TYPE_ID AS TICKET_TYPE_ID,                       ");
		sql.append("                R.STR_TICKET_NUM AS TICKET_NUM,                           ");
		sql.append("                0                CHECK_NUM,                               ");
		sql.append("                0                SALE_NUM                                 ");
		sql.append("           FROM RPT_STRINFO_D R                                           ");
		sql.append("         UNION ALL                                                        ");
		sql.append("         SELECT TRUNC(C.OPE_TIME, 'dd') AS RTP_DATE,                      ");
		sql.append("                C.TICKET_TYPE_ID AS TICKET_TYPE_ID,                       ");
		sql.append("                0 TICKET_NUM,                                             ");
		sql.append("                SUM(1) AS CHECK_NUM,                                      ");
		sql.append("                0 SALE_NUM                                                ");
		sql.append("           FROM SL_CHECK C                                                ");
		sql.append("          WHERE C.PASS_FLAG = 'Y'                                         ");
		sql.append("            AND C.TICKET_CLASS IN ('1', '2', '3')                         ");
		sql.append("          GROUP BY TRUNC(C.OPE_TIME, 'dd'), C.TICKET_TYPE_ID              ");
		sql.append("         UNION ALL                                                        ");
		sql.append(" 		SELECT TRUNC(O.SALE_TIME, 'DD') AS RTP_DATE,					  ");
		sql.append(" 	       D.TICKET_TYPE_ID AS TICKET_TYPE_ID, 							  ");
		sql.append(" 	       0 TICKET_NUM,                       							  ");
		sql.append(" 	       0 CHECK_NUM,                        							  ");
		sql.append(" 	       COUNT(O.TICKET_COUNT) AS SALE_NUM                  			  ");
		sql.append(" 	  FROM SL_ORDER O                          							  ");
		sql.append(" 	  JOIN SL_ORDER_DETAIL D                   							  ");
		sql.append(" 	    ON O.ORDER_ID = D.ORDER_ID             							  ");
		sql.append(" 	  WHERE O.PAY_STAT='2'   											  ");		
		sql.append("      GROUP BY TRUNC(O.SALE_TIME, 'DD'), D.TICKET_TYPE_ID                 ");
		sql.append("         UNION ALL                                                        ");
		sql.append("      SELECT TRUNC(E.SALE_DATE, 'DD') AS RTP_DATE,                        ");   
		sql.append("          E.TICKET_TYPE_ID AS TICKET_TYPE_ID,                             ");    
		sql.append("          0 TICKET_NUM,                                                   ");    
		sql.append("          0 CHECK_NUM,                                                    ");   
		sql.append("          E.SALE_NUM                                                      ");
		sql.append("      FROM SL_SALE_REG E                                                  ");
		sql.append("    )  A                                                                  ");
		sql.append("  INNER JOIN SYS_TICKET_TYPE S                                            ");
		sql.append("     ON S.TICKET_TYPE_ID = A. TICKET_TYPE_ID                              ");
		sql.append("  WHERE 1 = 1                                                             ");
		if (startDate != null && endDate != null) {
			sql.append("   AND A.RTP_DATE>=:START_DATE ");
			sql.append("   AND A.RTP_DATE<=:END_DATE ");
			params.put("START_DATE", startDate);
			params.put("END_DATE", endDate);
		}
		if (StringUtil.isNotNull(ticketTypeId)) {
			sql.append(" AND  A.TICKET_TYPE_ID=:TICKET_TYPE_ID ");
			params.put("TICKET_TYPE_ID", ticketTypeId);
		}
		sql.append("  GROUP BY A.RTP_DATE, A.TICKET_TYPE_ID, S.TICKET_TYPE_NAME               ");
		sql.append("                                                                          ");
		sql.append("  UNION                                                                   ");
		sql.append("  SELECT A.RTP_DATE,                                                      ");
		sql.append("        NULL TICKET_TYPE_ID,                                              ");
		sql.append("        SUM(A.TICKET_NUM) AS TICKET_NUM,                                  ");
		sql.append("        SUM(A.CHECK_NUM) AS CHECK_NUM,                                    ");
		sql.append("        SUM(A.SALE_NUM) AS SALE_NUM,                                      ");
		sql.append("        '合计' TICKET_TYPE_NAME,              ");
		sql.append("        2 ORDERNUM                                                        ");
		sql.append("   FROM (SELECT R.RPT_DATE       AS RTP_DATE,                             ");
		sql.append("                R.TICKET_TYPE_ID AS TICKET_TYPE_ID,                       ");
		sql.append("                R.STR_TICKET_NUM AS TICKET_NUM,                           ");
		sql.append("                0                CHECK_NUM,                               ");
		sql.append("                0                SALE_NUM                                 ");
		sql.append("           FROM RPT_STRINFO_D R                                           ");
		sql.append("         UNION ALL                                                        ");
		sql.append("         SELECT TRUNC(C.OPE_TIME, 'dd') AS RTP_DATE,                      ");
		sql.append("                C.TICKET_TYPE_ID AS TICKET_TYPE_ID,                       ");
		sql.append("                0 TICKET_NUM,                                             ");
		sql.append("                SUM(1) AS CHECK_NUM,                                      ");
		sql.append("                0 SALE_NUM                                                ");
		sql.append("           FROM SL_CHECK C                                                ");
		sql.append("          WHERE C.PASS_FLAG = 'Y'                                         ");
		sql.append("            AND C.TICKET_CLASS IN ('1', '2', '3')                         ");
		sql.append("          GROUP BY TRUNC(C.OPE_TIME, 'dd'), C.TICKET_TYPE_ID              ");
		sql.append("         UNION ALL                                                        ");
		sql.append(" 		SELECT TRUNC(O.SALE_TIME, 'DD') AS RTP_DATE,					  ");
		sql.append(" 	       D.TICKET_TYPE_ID AS TICKET_TYPE_ID, 							  ");
		sql.append(" 	       0 TICKET_NUM,                       							  ");
		sql.append(" 	       0 CHECK_NUM,                        							  ");
		sql.append(" 	       COUNT(O.TICKET_COUNT) AS SALE_NUM                  			  ");
		sql.append(" 	  FROM SL_ORDER O                          							  ");
		sql.append(" 	  JOIN SL_ORDER_DETAIL D                   							  ");
		sql.append(" 	    ON O.ORDER_ID = D.ORDER_ID             							  ");
		sql.append(" 	  WHERE O.PAY_STAT='2'   											  ");
		sql.append("      GROUP BY TRUNC(O.SALE_TIME, 'DD'), D.TICKET_TYPE_ID                 ");
		sql.append("         UNION ALL                                                        ");
		sql.append("      SELECT TRUNC(E.SALE_DATE, 'DD') AS RTP_DATE,                        ");   
		sql.append("          E.TICKET_TYPE_ID AS TICKET_TYPE_ID,                             ");    
		sql.append("          0 TICKET_NUM,                                                   ");    
		sql.append("          0 CHECK_NUM,                                                    ");   
		sql.append("          E.SALE_NUM                                                      ");
		sql.append("      FROM SL_SALE_REG E                                                  ");
		sql.append("    )  A                                                                  ");
		sql.append("  INNER JOIN SYS_TICKET_TYPE S                                            ");
		sql.append("     ON S.TICKET_TYPE_ID = A. TICKET_TYPE_ID                              ");
		sql.append("   WHERE 1 = 1                                                            ");
		if (startDate != null && endDate != null) {
			sql.append("   AND A.RTP_DATE>=:START_DATE ");
			sql.append("   AND A.RTP_DATE<=:END_DATE ");
			params.put("START_DATE", startDate);
			params.put("END_DATE", endDate);
		}
		if (StringUtil.isNotNull(ticketTypeId)) {
			sql.append(" AND  A.TICKET_TYPE_ID=:TICKET_TYPE_ID ");
			params.put("TICKET_TYPE_ID", ticketTypeId);
		}
		sql.append("  GROUP BY A.RTP_DATE                                                     ");
		sql.append("  ) W                                                                     ");
		sql.append("  ORDER BY W.RTP_DATE ,W.TICKET_TYPE_ID ,W.ORDERNUM                       ");
		 
		String  sb =sql.toString();
		List<SyntheticalBean> ret = dbUtil.queryListToBean("综合汇总查询", sb, SyntheticalBean.class, params);
		return ret;
	}
	
}