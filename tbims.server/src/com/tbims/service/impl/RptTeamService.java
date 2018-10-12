package com.tbims.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tbims.bean.CustFlowBean;
import com.tbims.bean.CustFlowDateTimeBean;
import com.tbims.bean.CustFlowDayBean;
import com.tbims.bean.CustFlowTimeBean;
import com.tbims.bean.RefundTicketBean;
import com.tbims.bean.RptTeamOrderDetailBean;
import com.tbims.bean.RptTeamTdBean;
import com.tbims.bean.SysTimeSplitBean;
import com.tbims.bean.TicketTeamOrderBean;
import com.tbims.bean.WlAndStTicketChangeBean;
import com.tbims.bean.WlOrgChangeBean;
import com.tbims.entity.SlTeamOrderDetail;
import com.tbims.entity.SysTicketType;
import com.tbims.service.IRptTeamService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.BaseException;
import com.zhming.support.util.StringUtil;

@Service
public class RptTeamService extends BaseService implements IRptTeamService {

	@Override
	public PageBean<WlOrgChangeBean> listRptTeamWl(UserBean loginUserBean, WlOrgChangeBean wlBean) throws BaseException {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT O2.ORG_ID,   ");
		sql.append("       O2.ORG_NAME,   ");
		sql.append("	   O2.OUTLET_ID,   ");
		sql.append("       O2.OUTLET_NAME,   ");
		sql.append("       O2.TICKET_TYPE_ID,   ");
		sql.append("       O2.TICKET_TYPE_NAME,   ");
		sql.append("       O2.PAY_TYPE,   ");
		sql.append("       O2.ITEM_NAME,   ");
		sql.append("       SUM(O2.SALE_NUM) SALE_NUM,   ");
		sql.append("       SUM(O2.SALE_AMT) SALE_AMT,   ");
		sql.append("       SUM(O2.EJECT_NUM) EJECT_NUM,   ");
		sql.append("       SUM(O2.TRADE_NUM) TRADE_NUM,   ");
		sql.append("       SUM(O2.TRADE_AMT) TRADE_AMT ,   ");
		sql.append("       TRUNC(O2.SALE_TIME, 'DD') OPE_TIME   ");
		sql.append("  FROM (SELECT SO.ORDER_ID,   ");
		sql.append("                SNO.ORG_ID,   ");
		sql.append("                O.ORG_NAME,   ");
		sql.append("                OT.OUTLET_ID,   ");
		sql.append("                OT.OUTLET_NAME,   ");
		sql.append("                OD.TICKET_TYPE_ID,   ");
		sql.append("                TY.TICKET_TYPE_NAME,   ");
		sql.append("                SO.TICKET_COUNT,   ");
		sql.append("                PT.PAY_TYPE,   ");
		sql.append("                SD.ITEM_NAME,   ");
		sql.append("                SO.SALE_TIME,   ");
		sql.append("                SO.ORDER_TYPE,   ");
		sql.append("                SO.PAY_STAT,   ");
		sql.append("                1 SALE_NUM,   ");
		sql.append("                OD.SALE_PRICE SALE_AMT,   ");
		sql.append("                CASE WHEN OD.EJECT_TICKET_TIME IS NOT NULL THEN 1 ELSE 0 END EJECT_NUM,   ");
		sql.append("                CASE WHEN OD.USELESS_FLAG='T'  THEN 1 ELSE 0 END TRADE_NUM,   ");
		sql.append("                CASE WHEN OD.USELESS_FLAG='T'  THEN OD.SALE_PRICE ELSE 0 END TRADE_AMT   ");
		sql.append("           FROM SL_ORDER SO   ");
		sql.append("          INNER JOIN SL_PAY_TYPE PT   ");
		sql.append("             ON SO.ORDER_ID = PT.ORDER_ID   ");
		sql.append("          INNER JOIN SL_NETAGENT_ORDER SNO   ");
		sql.append("             ON SNO.ORDER_ID = SO.ORDER_ID   ");
		sql.append("          INNER JOIN SL_ORDER_DETAIL OD   ");
		sql.append("             ON OD.ORDER_ID = SO.ORDER_ID   ");
		sql.append("          LEFT JOIN SL_ORG O ON SNO.ORG_ID = O.ORG_ID     ");
		sql.append("          LEFT JOIN SYS_TICKET_TYPE TY ON TY.TICKET_TYPE_ID = OD.TICKET_TYPE_ID    ");
		sql.append("          LEFT JOIN SYS_DICTIONARY SD ON PT.PAY_TYPE=SD.ITEM_CD AND SD.KEY_CD = 'PAY_TYPE'     ");
		sql.append("          LEFT JOIN SYS_OUTLET OT ON OT.OUTLET_ID=OD.OUTLET_ID     ");

		sql.append(" WHERE SO.ORDER_TYPE IN ('WL', 'ZQ')   ");
		sql.append("   AND SO.PAY_STAT = '2'   ");

		// 销售日期
		if (wlBean.getOpeTime() != null) {
			sql.append(" AND TRUNC(SO.SALE_TIME, 'DD') BETWEEN :SALE_TIME1 ");
			sql.append(" AND :SALE_TIME2 ");
			params.put("SALE_TIME1", wlBean.getOpeTime());
			params.put("SALE_TIME2", wlBean.getOpeTime2());
		}
		// 网点名称
		if (wlBean.getOutletId() != null) {
			sql.append(" AND OD.OUTLET_ID=:OUTLET_ID ");
			params.put("OUTLET_ID", wlBean.getOutletId());
		}
		// 票种
		if (StringUtil.isNotNull(wlBean.getTicketTypeId())) {
			sql.append(" AND OD.TICKET_TYPE_ID=:TICKET_TYPE_ID ");
			params.put("TICKET_TYPE_ID", wlBean.getTicketTypeId());
		}

		// 机构
		if (StringUtil.isNotNull(wlBean.getOrgId())) {
			sql.append(" AND SNO.ORG_ID=:ORG_ID ");
			params.put("ORG_ID", wlBean.getOrgId());
		}

		sql.append(" ) O2   ");
		sql.append(" WHERE O2.EJECT_NUM!=0");
		sql.append(" GROUP BY TRUNC(O2.SALE_TIME, 'DD'),   ");
		sql.append("          O2.ORG_ID,   ");
		sql.append("          O2.ORG_NAME,   ");
		sql.append("          O2.OUTLET_ID,   ");
		sql.append("          O2.OUTLET_NAME,   ");
		sql.append("          O2.TICKET_TYPE_ID,   ");
		sql.append("          O2.TICKET_TYPE_NAME,   ");
		sql.append("          O2.PAY_TYPE,   ");
		sql.append("          O2.ITEM_NAME   ");
		sql.append(" ORDER BY TRUNC(O2.SALE_TIME, 'DD') DESC,O2.ORG_ID, O2.OUTLET_ID, O2.TICKET_TYPE_ID ");

		PageBean<WlOrgChangeBean> rptTeamList = dbUtil.queryPageToBean("网络代理商换票统计", sql.toString(), WlOrgChangeBean.class, loginUserBean.getPageNum(), loginUserBean.getPageSize(), params);

		return rptTeamList;
	}

	@Override
	public PageBean<WlOrgChangeBean> listRptTeamWlSale(UserBean loginUserBean, WlOrgChangeBean wlBean) throws BaseException {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.*, O.ORG_NAME, TY.TICKET_TYPE_NAME, SD.ITEM_NAME                                                                     ");
		sql.append("  FROM (select ORG_ID,                                                                                                        ");
		sql.append("               SALE_TIME OPE_TIME,                                                                                            ");
		sql.append("               TICKET_TYPE_ID,                                                                                                ");
		sql.append("               case when ticket_class='1' then '实体票' when ticket_class='2' then '身份证' when ticket_class='3' then '二维码' end ticket_class, ");
		sql.append("               PAY_TYPE,                                                                                                      ");
		sql.append("               sum(SALE_NUM) SALE_NUM,                                                                                        ");
		sql.append("               sum(SALE_AMT) SALE_AMT,                                                                                        ");
		sql.append("               sum(EJECT_NUM) EJECT_NUM,                                                                                      ");
		sql.append("               sum(TRADE_NUM) TRADE_NUM,                                                                                      ");
		sql.append("               sum(TRADE_AMT) TRADE_AMT,                                                                                      ");
		sql.append("               sum(check_num) check_num,                                                                                      ");
		sql.append("               sum(check_amt) check_amt                                                                                       ");
		sql.append("          FROM (SELECT SNO.ORG_ID,                                                                                            ");
		sql.append("                       TRUNC(SO.SALE_TIME, 'DD') SALE_TIME,                                                                   ");
		sql.append("                       OD.TICKET_TYPE_ID,                                                                                     ");
		sql.append("                       od.ticket_class,                                                                                       ");
		sql.append("                       PT.PAY_TYPE,                                                                                           ");
		sql.append("                       1 SALE_NUM,                                                                                            ");
		sql.append("                       OD.ORIGINAL_PRICE SALE_AMT,                                                                                ");
		sql.append("                       CASE                                                                                                   ");
		sql.append("                         WHEN OD.EJECT_TICKET_STAT = '2' THEN                                                                 ");
		sql.append("                          1                                                                                                   ");
		sql.append("                         ELSE                                                                                                 ");
		sql.append("                          0                                                                                                   ");
		sql.append("                       end EJECT_NUM,                                                                                         ");
		sql.append("                       0 TRADE_NUM,                                                                                           ");
		sql.append("                       0 TRADE_AMT,                                                                                           ");
		sql.append("                       0 check_num,                                                                                           ");
		sql.append("                       0 check_amt                                                                                            ");
		sql.append("                  FROM SL_ORDER SO                                                                                            ");
		sql.append("                 INNER JOIN SL_PAY_TYPE PT                                                                                    ");
		sql.append("                    ON SO.ORDER_ID = PT.ORDER_ID                                                                              ");
		sql.append("                 INNER JOIN SL_NETAGENT_ORDER SNO                                                                             ");
		sql.append("                    ON SNO.ORDER_ID = SO.ORDER_ID                                                                             ");
		sql.append("                 INNER JOIN SL_ORDER_DETAIL OD                                                                                ");
		sql.append("                    ON OD.ORDER_ID = SO.ORDER_ID                                                                              ");
		sql.append("                 WHERE SO.ORDER_TYPE IN ('WL', 'ZQ')                                                                          ");
		sql.append("                   AND SO.PAY_STAT = '2'                                                                                      ");
		// 销售日期
		if (wlBean.getOpeTime() != null && wlBean.getOpeTime2() != null) {
			sql.append(" AND (TRUNC(SO.SALE_TIME, 'DD') BETWEEN :SALE_TIME1 AND :SALE_TIME2) ");
			params.put("SALE_TIME1", wlBean.getOpeTime());
			params.put("SALE_TIME2", wlBean.getOpeTime2());
		}

		// 票种
		if (StringUtil.isNotNull(wlBean.getTicketTypeId())) {
			sql.append(" AND OD.TICKET_TYPE_ID=:TICKET_TYPE_ID ");
			params.put("TICKET_TYPE_ID", wlBean.getTicketTypeId());
		}

		// 机构
		if (StringUtil.isNotNull(wlBean.getOrgId())) {
			sql.append(" AND SNO.ORG_ID=:ORG_ID ");
			params.put("ORG_ID", wlBean.getOrgId());
		}

		sql.append("                union all                                                                                                     ");
		sql.append("                SELECT SNO.ORG_ID,                                                                                            ");
		sql.append("                       TRUNC(OD.USELESS_TIME, 'DD') SALE_TIME,                                                                ");
		sql.append("                       OD.TICKET_TYPE_ID,                                                                                     ");
		sql.append("                       od.ticket_class,                                                                                       ");
		sql.append("                       PT.PAY_TYPE,                                                                                           ");
		sql.append("                       0 SALE_NUM,                                                                                            ");
		sql.append("                       0 SALE_AMT,                                                                                            ");
		sql.append("                       0 EJECT_NUM,                                                                                           ");
		sql.append("                       1 TRADE_NUM,                                                                                           ");
		sql.append("                       OD.SALE_PRICE TRADE_AMT,                                                                               ");
		sql.append("                       0 check_num,                                                                                           ");
		sql.append("                       0 check_amt                                                                                            ");
		sql.append("                  FROM SL_ORDER SO                                                                                            ");
		sql.append("                 INNER JOIN SL_PAY_TYPE PT                                                                                    ");
		sql.append("                    ON SO.ORDER_ID = PT.ORDER_ID                                                                              ");
		sql.append("                 INNER JOIN SL_NETAGENT_ORDER SNO                                                                             ");
		sql.append("                    ON SNO.ORDER_ID = SO.ORDER_ID                                                                             ");
		sql.append("                 INNER JOIN SL_ORDER_DETAIL OD                                                                                ");
		sql.append("                    ON OD.ORDER_ID = SO.ORDER_ID                                                                              ");
		sql.append("                 WHERE SO.ORDER_TYPE IN ('WL', 'ZQ')                                                                          ");
		sql.append("                   AND SO.PAY_STAT = '2'                                                                                      ");

		if (wlBean.getOpeTime() != null && wlBean.getOpeTime2() != null) {
			sql.append(" AND (TRUNC(OD.USELESS_TIME, 'DD') BETWEEN :SALE_TIME1 AND :SALE_TIME2) ");
			params.put("SALE_TIME1", wlBean.getOpeTime());
			params.put("SALE_TIME2", wlBean.getOpeTime2());
		}

		// 票种
		if (StringUtil.isNotNull(wlBean.getTicketTypeId())) {
			sql.append(" AND OD.TICKET_TYPE_ID=:TICKET_TYPE_ID ");
			params.put("TICKET_TYPE_ID", wlBean.getTicketTypeId());
		}

		// 机构
		if (StringUtil.isNotNull(wlBean.getOrgId())) {
			sql.append(" AND SNO.ORG_ID=:ORG_ID ");
			params.put("ORG_ID", wlBean.getOrgId());
		}
		
		sql.append("                   union all                                                                                                  ");
		sql.append("                   SELECT SNO.ORG_ID,                                                                                         ");
		sql.append("                       TRUNC(OD.CHECK_TIME, 'DD') SALE_TIME,                                                                ");
		sql.append("                       OD.TICKET_TYPE_ID,                                                                                     ");
		sql.append("                       od.ticket_class,                                                                                       ");
		sql.append("                       PT.PAY_TYPE,                                                                                           ");
		sql.append("                       0 SALE_NUM,                                                                                            ");
		sql.append("                       0 SALE_AMT,                                                                                            ");
		sql.append("                       0 EJECT_NUM,                                                                                           ");
		sql.append("                       0 TRADE_NUM,                                                                                           ");
		sql.append("                       0 TRADE_AMT,                                                                                           ");
		sql.append("                       1 check_num,                                                                                           ");
		sql.append("                       OD.SALE_PRICE check_amt                                                                                ");
		sql.append("                  FROM SL_ORDER SO                                                                                            ");
		sql.append("                 INNER JOIN SL_PAY_TYPE PT                                                                                    ");
		sql.append("                    ON SO.ORDER_ID = PT.ORDER_ID                                                                              ");
		sql.append("                 INNER JOIN SL_NETAGENT_ORDER SNO                                                                             ");
		sql.append("                    ON SNO.ORDER_ID = SO.ORDER_ID                                                                             ");
		sql.append("                 INNER JOIN SL_ORDER_DETAIL OD                                                                                ");
		sql.append("                    ON OD.ORDER_ID = SO.ORDER_ID                                                                              ");
		sql.append("                 WHERE SO.ORDER_TYPE IN ('WL', 'ZQ')                                                                          ");
		sql.append("                   AND SO.PAY_STAT = '2'                                                                                      ");
		
		if (wlBean.getOpeTime() != null && wlBean.getOpeTime2() != null) {
			sql.append(" AND OD.CHECK_FLAG='Y' AND (TRUNC(OD.CHECK_TIME, 'DD') BETWEEN :SALE_TIME1 AND :SALE_TIME2) ");
			params.put("SALE_TIME1", wlBean.getOpeTime());
			params.put("SALE_TIME2", wlBean.getOpeTime2());
		}

		// 票种
		if (StringUtil.isNotNull(wlBean.getTicketTypeId())) {
			sql.append(" AND OD.TICKET_TYPE_ID=:TICKET_TYPE_ID ");
			params.put("TICKET_TYPE_ID", wlBean.getTicketTypeId());
		}

		// 机构
		if (StringUtil.isNotNull(wlBean.getOrgId())) {
			sql.append(" AND SNO.ORG_ID=:ORG_ID ");
			params.put("ORG_ID", wlBean.getOrgId());
		}
		
		sql.append("                   )                                                                                                          ");
		sql.append("         GROUP BY ORG_ID, SALE_TIME, TICKET_TYPE_ID, PAY_TYPE,TICKET_CLASS) t                                                 ");
		sql.append("  LEFT JOIN SL_ORG O                                                                                                          ");
		sql.append("    ON t.ORG_ID = O.ORG_ID                                                                                                    ");
		sql.append("  LEFT JOIN SYS_TICKET_TYPE TY                                                                                                ");
		sql.append("    ON TY.TICKET_TYPE_ID = t.TICKET_TYPE_ID                                                                                   ");
		sql.append("  LEFT JOIN SYS_DICTIONARY SD                                                                                                 ");
		sql.append("    ON t.PAY_TYPE = SD.ITEM_CD                                                                                                ");
		sql.append("   AND SD.KEY_CD = 'PAY_TYPE'                                                                                                 ");
		sql.append(" ORDER BY t.ORG_ID, t.OPE_TIME, t.TICKET_TYPE_ID, t.PAY_TYPE,t.ticket_class                                                   ");


		PageBean<WlOrgChangeBean> rptTeamList = dbUtil.queryPageToBean("网络代理商售票统计", sql.toString(), WlOrgChangeBean.class, loginUserBean.getPageNum(), loginUserBean.getPageSize(), params);

		return rptTeamList;
	}

	@Override
	public PageBean<WlAndStTicketChangeBean> listRptTeamSt(UserBean loginUserBean, WlAndStTicketChangeBean sTBean) throws BaseException {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT W.ORG_ID,                                             ");
		sql.append("       W.ORG_NAME,                                           ");
		sql.append("       W.TICKET_TYPE_ID,                                     ");
		sql.append("       W.TICKET_TYPE_NAME,                                   ");
		sql.append("       W.TICKET_NUM,                                         ");
		sql.append("       W.TOTAL_PRICE,                                        ");
		sql.append("       W.OPE_TIME                                            ");
		sql.append("  FROM (SELECT U1.ORG_ID,                                    ");
		sql.append("               U1.ORG_NAME,                                  ");
		sql.append("               U1.TICKET_TYPE_ID,                            ");
		sql.append("               U1.TICKET_TYPE_NAME,                          ");
		sql.append("               SUM(1) TICKET_NUM,                            ");
		sql.append("               SUM(U1.SALE_PRICE) TOTAL_PRICE,               ");
		sql.append("               TRUNC(U1.SALE_TIME, 'DD') OPE_TIME            ");
		sql.append("          FROM (SELECT SO.ORDER_ID,                          ");
		sql.append("                       ORG.ORG_ID,                           ");
		sql.append("                       ORG.ORG_NAME,                         ");
		sql.append("                       OD.OUTLET_ID,                         ");
		sql.append("                       SO.TICKET_COUNT,                      ");
		sql.append("                       OD.TICKET_TYPE_ID,                    ");
		sql.append("                       TY.TICKET_TYPE_NAME,                  ");
		sql.append("                       SO.ORDER_TYPE,                        ");
		sql.append("                       SO.PAY_STAT,                          ");
		sql.append("                       OD.SALE_PRICE,                        ");
		sql.append("                       SO.SALE_TIME                          ");
		sql.append("                  FROM SL_ORDER SO                           ");
		sql.append("                 INNER JOIN SL_ORDER_DETAIL OD               ");
		sql.append("                    ON OD.ORDER_ID = SO.ORDER_ID             ");
		sql.append("                 INNER JOIN SYS_TICKET_TYPE TY               ");
		sql.append("                    ON TY.TICKET_TYPE_ID = OD.TICKET_TYPE_ID ");
		sql.append("                 INNER JOIN SYS_OUTLET OT                    ");
		sql.append("                    ON OT.OUTLET_ID = OD.OUTLET_ID           ");
		sql.append("                 INNER JOIN SL_ORG ORG                       ");
		sql.append("                    ON ORG.ORG_ID = OT.ORG_ID) U1            ");
		sql.append("         WHERE U1.ORDER_TYPE = 'ST'                          ");
		sql.append("           AND U1.PAY_STAT = '2'                             ");

		// 销售日期
		sql.append("  AND TRUNC(U1.SALE_TIME, 'DD') between :SALE_START_TIME and :SALE_END_TIME ");
		if (sTBean.getSaleEndTime() != null && sTBean.getSaleEndTime() != null) {
			params.put("SALE_START_TIME", sTBean.getSaleStartTime());
			params.put("SALE_END_TIME", sTBean.getSaleEndTime());
		} else {
			params.put("SALE_START_TIME", new Date());
			params.put("SALE_END_TIME", new Date());
		}

		// 票种
		if (StringUtil.isNotNull(sTBean.getTicketTypeId())) {
			sql.append(" AND U1.TICKET_TYPE_ID =:TICKET_TYPE_ID ");
			params.put("TICKET_TYPE_ID", sTBean.getTicketTypeId());
		}

		// 机构
		if (StringUtil.isNotNull(sTBean.getOrgId())) {
			sql.append(" AND U1.ORG_ID =:ORG_ID ");
			params.put("ORG_ID", sTBean.getOrgId());
		}

		sql.append("         GROUP BY TRUNC(U1.SALE_TIME, 'DD'),                 ");
		sql.append("                  U1.ORG_ID,                                 ");
		sql.append("                  U1.ORG_NAME,                               ");
		sql.append("                  U1.TICKET_TYPE_ID,                         ");
		sql.append("                  U1.TICKET_TYPE_NAME                        ");
		sql.append("        UNION                                                ");
		sql.append("        SELECT R.ORG_ID,                                     ");
		sql.append("               ORG.ORG_NAME,                                 ");
		sql.append("               R.TICKET_TYPE_ID,                             ");
		sql.append("               TY.TICKET_TYPE_NAME,                          ");
		sql.append("               SUM(R.SALE_NUM) TICKET_NUM,                   ");
		sql.append("               SUM(R.SALE_AMT) TOTAL_PRICE,                  ");
		sql.append("               TRUNC(R.SALE_DATE, 'DD') OPE_TIME             ");
		sql.append("          FROM SL_SALE_REG R                                 ");
		sql.append("         INNER JOIN SYS_TICKET_TYPE TY                       ");
		sql.append("            ON R.TICKET_TYPE_ID = TY.TICKET_TYPE_ID          ");
		sql.append("         INNER JOIN SYS_OUTLET O                             ");
		sql.append("            ON R.OUTLET_ID = O.OUTLET_ID                     ");
		sql.append("         INNER JOIN SL_ORG ORG                               ");
		sql.append("            ON R.ORG_ID = ORG.ORG_ID                         ");
		sql.append("         WHERE 1=1                                           ");

		// 销售日期
		sql.append("  AND TRUNC(R.SALE_DATE, 'DD') BETWEEN :SALE_START_TIME AND :SALE_END_TIME ");
		if (sTBean.getSaleEndTime() != null && sTBean.getSaleEndTime() != null) {
			params.put("SALE_START_TIME", sTBean.getSaleStartTime());
			params.put("SALE_END_TIME", sTBean.getSaleEndTime());
		} else {
			params.put("SALE_START_TIME", new Date());
			params.put("SALE_END_TIME", new Date());
		}

		// 票种
		if (StringUtil.isNotNull(sTBean.getTicketTypeId())) {
			sql.append(" AND R.TICKET_TYPE_ID =:TICKET_TYPE_ID ");
			params.put("TICKET_TYPE_ID", sTBean.getTicketTypeId());
		}

		// 机构
		if (StringUtil.isNotNull(sTBean.getOrgId())) {
			sql.append(" AND ORG.ORG_ID =:ORG_ID ");
			params.put("ORG_ID", sTBean.getOrgId());
		}

		sql.append("         GROUP BY R.ORG_ID,                                  ");
		sql.append("                  ORG.ORG_NAME,                              ");
		sql.append("                  R.TICKET_TYPE_ID,                          ");
		sql.append("                  TY.TICKET_TYPE_NAME,                       ");
		sql.append("                  TRUNC(R.SALE_DATE, 'DD')                   ");
		sql.append("        ) W                                                  ");
		sql.append(" ORDER BY TRUNC(W.OPE_TIME, 'DD') DESC,                           ");
		sql.append("       W.ORG_NAME,                                           ");
		sql.append("       W.TICKET_TYPE_NAME                                    ");

		PageBean<WlAndStTicketChangeBean> rptTeamList = dbUtil.queryPageToBean("实体代理商销售统计报表", sql.toString(), WlAndStTicketChangeBean.class, loginUserBean.getPageNum(), loginUserBean.getPageSize(), params);
		return rptTeamList;
	}

	@Override
	public List<RptTeamOrderDetailBean> listRptTeamTd(UserBean loginUserBean, String ticketTypeId, Date changeTime) throws BaseException {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		sql.append(" SELECT O1.ORG_ID,");
		sql.append("        O1.ORG_NAME,  ");
		sql.append("        O2.TICKET_TYPE_ID, ");
		sql.append("        O2.TICKET_TYPE_NAME, ");
		sql.append("        TRUNC(O1.CHANGE_TIME, 'DD') CHANGE_TIME, ");
		sql.append("        NVL(SUM(O2.CHANGE_NUM),0) CHANGE_NUM, ");
		sql.append("        SUM(O2.MINUS_LIMIT) MINUS_LIMIT, ");
		sql.append("        SUM(O1.MINUS_ADVANCE_AMT) MINUS_ADVANCE_AMT ");
		sql.append("   FROM SL_TEAM_ORDER O1 ");
		sql.append("  INNER JOIN SL_TEAM_ORDER_DETAIL O2 ");
		sql.append("     ON O2.APPLY_ID = O1.APPLY_ID  ");
		sql.append("  WHERE 1 = 1 ");
		// 销售日期
		if (changeTime != null) {
			sql.append("   AND TRUNC(O1.CHANGE_TIME, 'DD') = :CHANGE_TIME ");
			params.put("CHANGE_TIME", changeTime);
		}
		// 票种
		if (StringUtil.isNotNull(ticketTypeId)) {
			sql.append("   AND O2.TICKET_TYPE_ID = :TICKET_TYPE_ID ");
			params.put("TICKET_TYPE_ID", ticketTypeId);
		}
		sql.append(" GROUP BY  ");
		sql.append(" 		  TRUNC(O1.CHANGE_TIME, 'DD'),  ");
		sql.append("          O1.ORG_ID,  ");
		sql.append(" 		  O1.ORG_NAME,   ");
		sql.append(" 		  O2.TICKET_TYPE_ID, ");
		sql.append(" 		  O2.TICKET_TYPE_NAME ");
		sql.append(" 		  ORDER BY TRUNC(O1.CHANGE_TIME,'DD') ,O1.ORG_ID, O2.TICKET_TYPE_ID  ");

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT O1.ORG_ID,");
		sb.append("        TRUNC(O1.CHANGE_TIME, 'DD') CHANGE_TIME, ");
		sb.append("        SUM(O1.MINUS_ADVANCE_AMT) MINUS_ADVANCE_AMT ");
		sb.append("   FROM SL_TEAM_ORDER O1 ");
		sb.append("  INNER JOIN SL_TEAM_ORDER_DETAIL O2 ");
		sb.append("     ON O2.APPLY_ID = O1.APPLY_ID  ");
		sb.append("  WHERE 1 = 1 ");
		// 销售日期
		if (changeTime != null) {
			sb.append("   AND TRUNC(O1.CHANGE_TIME, 'DD') = :CHANGE_TIME ");
		}
		// 票种
		if (StringUtil.isNotNull(ticketTypeId)) {
			sb.append("   AND O2.TICKET_TYPE_ID = :TICKET_TYPE_ID ");
		}
		sb.append(" GROUP BY  ");
		sb.append(" 		  TRUNC(O1.CHANGE_TIME, 'DD'),  ");
		sb.append("           O1.ORG_ID  ");
		sb.append(" 		  ORDER BY TRUNC(O1.CHANGE_TIME,'DD'),O1.ORG_ID  ");
		List<TicketTeamOrderBean> list = dbUtil.queryListToBean("团队换票统计查询", sql.toString(), TicketTeamOrderBean.class, params);
		List<TicketTeamOrderBean> minusList = dbUtil.queryListToBean("扣减预付款查询", sb.toString(), TicketTeamOrderBean.class, params);

		List<TicketTeamOrderBean> tempList = new ArrayList<TicketTeamOrderBean>();
		tempList.addAll(list);
		List<RptTeamOrderDetailBean> tDList = new ArrayList<RptTeamOrderDetailBean>();
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = list.size() - 1; j > i; j--) {
				if (list.get(j).getChangeTime() != null && list.get(i).getChangeTime() != null) {
					if ((list.get(j).getChangeTime().compareTo(list.get(i).getChangeTime()) == 0) && (list.get(j).getOrgId().equals(list.get(i).getOrgId()))) {
						list.remove(j);
					}
				}
			}
		}
		for (TicketTeamOrderBean order : list) {
			RptTeamTdBean rBean = new RptTeamTdBean();
			RptTeamOrderDetailBean bean = new RptTeamOrderDetailBean();
			rBean.setOrgId(order.getOrgId());
			rBean.setOrgName(order.getOrgName());
			rBean.setChangeTime(order.getChangeTime());
			bean.setTeamBean(rBean);
			tDList.add(bean);
		}

		// 扣减预付款汇总
		for (RptTeamOrderDetailBean bean : tDList) {
			for (TicketTeamOrderBean minus : minusList) {
				if (bean.getTeamBean().getChangeTime() != null && minus.getChangeTime() != null) {
					if (bean.getTeamBean().getOrgId().equals(minus.getOrgId()) && bean.getTeamBean().getChangeTime().compareTo(minus.getChangeTime()) == 0) {
						RptTeamTdBean rBean = bean.getTeamBean();
						rBean.setMinusAdvanceAmt(minus.getMinusAdvanceAmt());
					}
				}
			}
		}
		for (RptTeamOrderDetailBean order : tDList) {
			List<SlTeamOrderDetail> detailList = new ArrayList<SlTeamOrderDetail>();
			RptTeamTdBean teamBean = order.getTeamBean();
			for (TicketTeamOrderBean o : tempList) {
				SlTeamOrderDetail detail = new SlTeamOrderDetail();
				if (o.getChangeTime() != null && teamBean.getChangeTime() != null) {
					if ((o.getChangeTime().compareTo(teamBean.getChangeTime()) == 0) && (o.getOrgId().compareTo(teamBean.getOrgId()) == 0)) {
						detail.setTicketTypeId(o.getTicketTypeId());
						detail.setTicketTypeName(o.getTicketTypeName());
						detail.setChangeNum(o.getChangeNum());
						detail.setMinusLimit(o.getMinusLimit());
						detailList.add(detail);
					}
				}
			}
			order.setOrderInfo(detailList);
		}
		return tDList;
	}

	@Override
	public List<CustFlowDayBean> listCustFlowDay(UserBean loginUserBean, Date startDate, Date endDate, String venueId, String regionId, String ticketTypeId) throws BaseException {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		sql.append(" select U.RPT_DATE, ");
		sql.append("       U.VENUE_ID,");
		sql.append("       U.VENUE_NAME,");
		sql.append("       U.REGION_ID,");
		sql.append("       U.REGION_NAME,");
		sql.append("       U.TICKET_TYPE_ID,");
		sql.append("       U.TICKET_TYPE_NAME,");
		sql.append("       U.CHECK_TICKET_NUM");
		sql.append(" FROM (SELECT TRUNC(T.RPT_DATE, 'DD') RPT_DATE,          ");
		sql.append("               V.VENUE_ID,                               ");
		sql.append("               NVL(V.VENUE_NAME, '无') VENUE_NAME,       ");
		sql.append("               R.REGION_ID,                              ");
		sql.append("               NVL(R.REGION_NAME, '无') REGION_NAME,     ");
		sql.append("               T.TICKET_TYPE_ID,                         ");
		sql.append("               TY.TICKET_TYPE_NAME,                      ");
		sql.append("               SUM(T.CHECK_TICKET_NUM) CHECK_TICKET_NUM, ");
		sql.append("               1 ORDER_NUM                               ");
		sql.append("          FROM RPT_CHECKTICKETINFO_D T                   ");
		sql.append("          LEFT JOIN SYS_REGION R                         ");
		sql.append("            ON R.REGION_ID = T.REGION_ID                 ");
		sql.append("          LEFT JOIN SYS_VENUE V                          ");
		sql.append("            ON V.VENUE_ID = R.VENUE_ID                   ");
		sql.append("          LEFT JOIN SYS_TICKET_TYPE TY                   ");
		sql.append("            ON TY.TICKET_TYPE_ID = T.TICKET_TYPE_ID      ");
		sql.append("         WHERE 1 = 1                                     ");
		// 统计日期(查询条件为空时默认查询三天的客流量)
		if (startDate != null && endDate != null) {
			sql.append("   AND TRUNC(T.RPT_DATE, 'DD') BETWEEN :STARTDATE ");
			sql.append("   AND :ENDDATE ");
			params.put("STARTDATE", startDate);
			params.put("ENDDATE", endDate);
		}
		// 场馆编号
		if (StringUtil.isNotNull(venueId)) {
			if (venueId.contains("-1")) {
				venueId = venueId.substring(venueId.indexOf("-1,") + 1);
			}
			if (venueId.contains(",")) {
				sql.append("   AND V.VENUE_ID in (" + venueId + ")");
			} else {
				sql.append("   AND V.VENUE_ID = :VENUE_ID ");
				params.put("VENUE_ID", venueId);
			}
		}
		// 区域编号
		if (StringUtil.isNotNull(regionId)) {
			if (regionId.contains("-1")) {
				regionId = regionId.substring(regionId.indexOf("-1,") + 1);
			}
			if (regionId.contains(",")) {
				sql.append("   AND R.REGION_ID in (" + regionId + ")");
			} else {
				sql.append("   AND R.REGION_ID = :REGION_ID");
				params.put("REGION_ID", regionId);
			}
		}
		// 票种
		if (StringUtil.isNotNull(ticketTypeId)) {
			if (ticketTypeId.contains("-1")) {
				ticketTypeId = ticketTypeId.substring(ticketTypeId.indexOf("-1,") + 1);
			}
			if (ticketTypeId.contains(",")) {
				sql.append("   AND T.TICKET_TYPE_ID in (" + ticketTypeId + ")");
			} else {
				sql.append("   AND T.TICKET_TYPE_ID = :TICKET_TYPE_ID ");
				params.put("TICKET_TYPE_ID", ticketTypeId);
			}
		}
		sql.append("         GROUP BY TRUNC(T.RPT_DATE, 'DD'),");
		sql.append("                  V.VENUE_ID,");
		sql.append("                  V.VENUE_NAME,");
		sql.append("                  R.REGION_ID,");
		sql.append("                  R.REGION_NAME,");
		sql.append("                  T.TICKET_TYPE_ID,");
		sql.append("                  TY.TICKET_TYPE_NAME");
		sql.append("        UNION");
		sql.append(" SELECT TRUNC(T.RPT_DATE, 'DD') RPT_DATE,                   ");
		sql.append("                 V.VENUE_ID,                                ");
		sql.append("                 NVL(V.VENUE_NAME, '无') VENUE_NAME,         ");
		sql.append("                 NULL REGION_ID,                            ");
		sql.append("                 '小计' REGION_NAME,                         ");
		sql.append("                 '' AS TICKET_TYPE_ID,                      ");
		sql.append("                 '' TICKET_TYPE_NAME,                       ");
		sql.append("                 SUM(T.CHECK_TICKET_NUM) CHECK_TICKET_NUM,  ");
		sql.append("                 2 ORDER_NUM                                ");
		sql.append("            FROM RPT_CHECKTICKETINFO_D T                    ");
		sql.append("            LEFT JOIN SYS_REGION R                          ");
		sql.append("            ON R.REGION_ID = T.REGION_ID                    ");
		sql.append("            LEFT JOIN SYS_VENUE V                           ");
		sql.append("            ON V.VENUE_ID = R.VENUE_ID                      ");
		sql.append("            LEFT JOIN SYS_TICKET_TYPE TY                    ");
		sql.append("              ON TY.TICKET_TYPE_ID = T.TICKET_TYPE_ID       ");
		sql.append("           WHERE 1 = 1                                      ");
		// 统计日期
		if (startDate != null && endDate != null) {
			sql.append("   AND TRUNC(T.RPT_DATE, 'DD') BETWEEN :STARTDATE ");
			sql.append("   AND :ENDDATE ");
		}
		// 场馆编号
		if (StringUtil.isNotNull(venueId)) {
			if (venueId.contains("-1")) {
				venueId = venueId.substring(venueId.indexOf("-1,") + 1);
			}
			if (venueId.contains(",")) {
				sql.append("   AND V.VENUE_ID in (" + venueId + ")");
			} else {
				sql.append("   AND V.VENUE_ID = :VENUE_ID ");
				params.put("VENUE_ID", venueId);
			}
		}
		// 区域编号
		if (StringUtil.isNotNull(regionId)) {
			if (regionId.contains("-1")) {
				regionId = regionId.substring(regionId.indexOf("-1,") + 1);
			}
			if (regionId.contains(",")) {
				sql.append("   AND R.REGION_ID in (" + regionId + ")");
			} else {
				sql.append("   AND R.REGION_ID = :REGION_ID");
				params.put("REGION_ID", regionId);
			}
		}
		// 票种
		if (StringUtil.isNotNull(ticketTypeId)) {
			if (ticketTypeId.contains("-1")) {
				ticketTypeId = ticketTypeId.substring(ticketTypeId.indexOf("-1,") + 1);
			}
			if (ticketTypeId.contains(",")) {
				sql.append("   AND T.TICKET_TYPE_ID in (" + ticketTypeId + ")");
			} else {
				sql.append("   AND T.TICKET_TYPE_ID = :TICKET_TYPE_ID ");
				params.put("TICKET_TYPE_ID", ticketTypeId);
			}
		}
		sql.append("        GROUP BY TRUNC(T.RPT_DATE, 'DD'), V.VENUE_ID, V.VENUE_NAME");
		sql.append("        UNION");
		sql.append(" SELECT TRUNC(T.RPT_DATE, 'DD') RPT_DATE,                    ");
		sql.append("                 NULL VENUE_ID,                              ");
		sql.append("                 '' VENUE_NAME,                              ");
		sql.append("                 NULL REGION_ID,                             ");
		sql.append("                 '合计' REGION_NAME,                         ");
		sql.append("                 '' AS TICKET_TYPE_ID,                       ");
		sql.append("                 '' TICKET_TYPE_NAME,                        ");
		sql.append("                 SUM(T.CHECK_TICKET_NUM) CHECK_TICKET_NUM,   ");
		sql.append("                 3 ORDER_NUM                                 ");
		sql.append("            FROM RPT_CHECKTICKETINFO_D T                     ");
		sql.append("           LEFT JOIN SYS_REGION R                            ");
		sql.append("            ON R.REGION_ID = T.REGION_ID                     ");
		sql.append("            LEFT JOIN SYS_VENUE V                            ");
		sql.append("            ON V.VENUE_ID = R.VENUE_ID                       ");
		sql.append("            LEFT JOIN SYS_TICKET_TYPE TY                     ");
		sql.append("              ON TY.TICKET_TYPE_ID = T.TICKET_TYPE_ID        ");
		sql.append("           WHERE 1 = 1                                       ");
		// 统计日期
		if (startDate != null && endDate != null) {
			sql.append("   AND TRUNC(T.RPT_DATE, 'DD') BETWEEN :STARTDATE ");
			sql.append("   AND :ENDDATE ");
		}
		// 场馆编号
		if (StringUtil.isNotNull(venueId)) {
			if (venueId.contains("-1")) {
				venueId = venueId.substring(venueId.indexOf("-1,") + 1);
			}
			if (venueId.contains(",")) {
				sql.append("   AND V.VENUE_ID in (" + venueId + ")");
			} else {
				sql.append("   AND V.VENUE_ID = :VENUE_ID ");
				params.put("VENUE_ID", venueId);
			}
		}
		// 区域编号
		if (StringUtil.isNotNull(regionId)) {
			if (regionId.contains("-1")) {
				regionId = regionId.substring(regionId.indexOf("-1,") + 1);
			}
			if (regionId.contains(",")) {
				sql.append("   AND R.REGION_ID in (" + regionId + ")");
			} else {
				sql.append("   AND R.REGION_ID = :REGION_ID");
				params.put("REGION_ID", regionId);
			}
		}
		// 票种
		if (StringUtil.isNotNull(ticketTypeId)) {
			if (ticketTypeId.contains("-1")) {
				ticketTypeId = ticketTypeId.substring(ticketTypeId.indexOf("-1,") + 1);
			}
			if (ticketTypeId.contains(",")) {
				sql.append("   AND T.TICKET_TYPE_ID in (" + ticketTypeId + ")");
			} else {
				sql.append("   AND T.TICKET_TYPE_ID = :TICKET_TYPE_ID ");
				params.put("TICKET_TYPE_ID", ticketTypeId);
			}
		}
		sql.append("         GROUP BY TRUNC(T.RPT_DATE, 'DD')) U");

		sql.append(" ORDER BY U.RPT_DATE,U.VENUE_ID,U.REGION_ID,U.TICKET_TYPE_ID, U.ORDER_NUM ");
		List<CustFlowDayBean> list = dbUtil.queryListToBean("客流日统计", sql.toString(), CustFlowDayBean.class, params);
		CustFlowDayBean totalBean = new CustFlowDayBean();
		totalBean.setVenueName("总计");
		totalBean.setCheckTicketNum(new Long(0));
		for (CustFlowDayBean bean : list) {
			if (!"小计".equals(bean.getRegionName()) && !"合计".equals(bean.getRegionName())) {
				totalBean.setCheckTicketNum(totalBean.getCheckTicketNum() + bean.getCheckTicketNum());
			} else {
				continue;
			}
		}
		list.add(totalBean);
		return list;
	}

	@Override
	public List<CustFlowDateTimeBean> listCustFlowInterTime(UserBean loginUserBean, Date date, String startTime, String endTime, String intervalTime, String venueId, String regionId, String ticketTypeId) throws BaseException {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("STARTTIME", startTime);// 时间段--开始时间
		params.put("ENDTIME", endTime);// 时间段--结束时间
		params.put("DATE", date);// 统计日期
		if (StringUtil.isNotNull(intervalTime)) {
			params.put("SPLITTIME", Long.parseLong(intervalTime));
		}
		sql.append(" SELECT   W2.SJD RPT_DATE,                                                               ");
		sql.append("         W1.VENUE_ID,                                                                    ");
		sql.append("         W1.VENUE_NAME,                                                                  ");
		sql.append("         W1.REGION_ID,                                                                   ");
		sql.append("         W1.REGION_NAME,                                                                 ");
		sql.append("         W1.TICKET_TYPE_ID,                                                              ");
		sql.append("         W1.TICKET_TYPE_NAME,                                                            ");
		sql.append("         SUM(CHECK_TICKET_NUM) CHECK_TICKET_NUM                                          ");
		sql.append("    FROM (SELECT TO_CHAR(S.OPE_TIME, 'HH24:mi') RPT_DATE,                                ");
		sql.append("                         V.VENUE_ID,                                                     ");
		sql.append("                         V.VENUE_NAME,                                                   ");
		sql.append("                         R.REGION_ID,                                                    ");
		sql.append("                         R.REGION_NAME,                                                  ");
		sql.append("                         S.TICKET_TYPE_ID,                                               ");
		sql.append("                         TY.TICKET_TYPE_NAME,                                            ");
		sql.append("                         SUM(1) CHECK_TICKET_NUM,                                        ");
		sql.append("                         1 ORDER_NUM                                                     ");
		sql.append("                    FROM SL_CHECK S                                                      ");
		sql.append("                    LEFT JOIN SYS_TICKET_TYPE TY                                         ");
		sql.append("                      ON TY.TICKET_TYPE_ID = S.TICKET_TYPE_ID                            ");
		sql.append("                    LEFT JOIN SYS_CLIENT C                                               ");
		sql.append("                      ON C.CLIENT_ID = S.CLIENT_ID                                       ");
		sql.append("                    LEFT JOIN SYS_REGION R                                               ");
		sql.append("                        ON R.REGION_ID = C.REGION_ID                                     ");
		sql.append("                    LEFT JOIN SYS_VENUE V                                                ");
		sql.append("                   ON V.VENUE_ID = R.VENUE_ID                                            ");
		sql.append("                 WHERE TRUNC(S.OPE_TIME, 'DD') = :DATE ");
		sql.append("                   AND TO_CHAR(S.OPE_TIME, 'HH24:mi') >= :STARTTIME  ");
		sql.append("                   AND TO_CHAR(S.OPE_TIME, 'HH24:mi') <= :ENDTIME ");
		sql.append("				   AND S.PASS_FLAG='Y' ");// 已通过
		sql.append("				   AND S.TICKET_CLASS<> '4' ");// 排除员工卡
		// 场馆编号
		if (StringUtil.isNotNull(venueId)) {
			if (venueId.contains("-1")) {
				venueId = venueId.substring(venueId.indexOf("-1,") + 1);
			}
			if (venueId.contains(",")) {
				sql.append("   AND V.VENUE_ID in (" + venueId + ")");
			} else {
				sql.append("   AND V.VENUE_ID = :VENUE_ID ");
				params.put("VENUE_ID", venueId);
			}
		}
		// 区域编号
		if (StringUtil.isNotNull(regionId)) {
			if (regionId.contains("-1")) {
				regionId = regionId.substring(regionId.indexOf("-1,") + 1);
			}
			if (regionId.contains(",")) {
				sql.append("   AND R.REGION_ID in (" + regionId + ")");
			} else {
				sql.append("   AND R.REGION_ID = :REGION_ID");
				params.put("REGION_ID", regionId);
			}
		}
		// 票种
		if (StringUtil.isNotNull(ticketTypeId)) {
			if (ticketTypeId.contains("-1")) {
				ticketTypeId = ticketTypeId.substring(ticketTypeId.indexOf("-1,") + 1);
			}
			if (ticketTypeId.contains(",")) {
				sql.append("   AND S.TICKET_TYPE_ID in (" + ticketTypeId + ")");
			} else {
				sql.append("   AND S.TICKET_TYPE_ID = :TICKET_TYPE_ID");
				params.put("TICKET_TYPE_ID", ticketTypeId);
			}
		}
		sql.append("                   GROUP BY TO_CHAR(S.OPE_TIME, 'HH24:mi'),                              ");
		sql.append("                            V.VENUE_ID,                                                  ");
		sql.append("                            V.VENUE_NAME,                                                ");
		sql.append("                            R.REGION_ID,                                                 ");
		sql.append("                            R.REGION_NAME,                                               ");
		sql.append("                            S.TICKET_TYPE_ID,                                            ");
		sql.append("                            TY.TICKET_TYPE_NAME                                          ");
		sql.append("                  UNION                                                                  ");
		sql.append("                  SELECT TO_CHAR(S.OPE_TIME, 'HH24:mi') RPT_DATE,                        ");
		sql.append("                         V.VENUE_ID,                                                     ");
		sql.append("                         V.VENUE_NAME,                                                   ");
		sql.append("                         NULL REGION_ID,                                                 ");
		sql.append("                         '小计' REGION_NAME,                                             ");
		sql.append("                         '' AS TICKET_TYPE_ID,                                           ");
		sql.append("                         '' TICKET_TYPE_NAME,                                            ");
		sql.append("                         SUM(1) CHECK_TICKET_NUM,                                        ");
		sql.append("                         2 ORDER_NUM                                                     ");
		sql.append("                    FROM SL_CHECK S                                                      ");
		sql.append("                    LEFT JOIN SYS_TICKET_TYPE TY                                         ");
		sql.append("                      ON TY.TICKET_TYPE_ID = S.TICKET_TYPE_ID                            ");
		sql.append("                     LEFT JOIN SYS_CLIENT C                                              ");
		sql.append("                      ON C.CLIENT_ID = S.CLIENT_ID                                       ");
		sql.append("                    LEFT JOIN SYS_REGION R                                               ");
		sql.append("                        ON R.REGION_ID = C.REGION_ID                                     ");
		sql.append("                    LEFT JOIN SYS_VENUE V                                                ");
		sql.append("                   ON V.VENUE_ID = R.VENUE_ID                                            ");
		sql.append("                 WHERE TRUNC(S.OPE_TIME, 'DD') = :DATE ");
		sql.append("                   AND TO_CHAR(S.OPE_TIME, 'HH24:mi') >= :STARTTIME  ");
		sql.append("                   AND TO_CHAR(S.OPE_TIME, 'HH24:mi') <= :ENDTIME ");
		sql.append("				   AND S.PASS_FLAG='Y'");
		sql.append("				   AND S.TICKET_CLASS<> '4' ");// 排除员工卡
		// 场馆编号
		if (StringUtil.isNotNull(venueId)) {
			if (venueId.contains("-1")) {
				venueId = venueId.substring(venueId.indexOf("-1,") + 1);
			}
			if (venueId.contains(",")) {
				sql.append("   AND V.VENUE_ID in (" + venueId + ")");
			} else {
				sql.append("   AND V.VENUE_ID = :VENUE_ID ");
				params.put("VENUE_ID", venueId);
			}
		}
		// 区域编号
		if (StringUtil.isNotNull(regionId)) {
			if (regionId.contains("-1")) {
				regionId = regionId.substring(regionId.indexOf("-1,") + 1);
			}
			if (regionId.contains(",")) {
				sql.append("   AND R.REGION_ID in (" + regionId + ")");
			} else {
				sql.append("   AND R.REGION_ID = :REGION_ID");
				params.put("REGION_ID", regionId);
			}
		}
		// 票种
		if (StringUtil.isNotNull(ticketTypeId)) {
			if (ticketTypeId.contains("-1")) {
				ticketTypeId = ticketTypeId.substring(ticketTypeId.indexOf("-1,") + 1);
			}
			if (ticketTypeId.contains(",")) {
				sql.append("   AND S.TICKET_TYPE_ID in (" + ticketTypeId + ")");
			} else {
				sql.append("   AND S.TICKET_TYPE_ID = :TICKET_TYPE_ID");
				params.put("TICKET_TYPE_ID", ticketTypeId);
			}
		}
		sql.append("                   GROUP BY TO_CHAR(S.OPE_TIME, 'HH24:mi'),                              ");
		sql.append("                            V.VENUE_ID,                                                  ");
		sql.append("                            V.VENUE_NAME                                                 ");
		sql.append("                  UNION                                                                  ");
		sql.append("                  SELECT TO_CHAR(S.OPE_TIME, 'HH24:mi') RPT_DATE,                        ");
		sql.append("                         NULL VENUE_ID,                                                  ");
		sql.append("                         '' VENUE_NAME,                                                  ");
		sql.append("                         NULL REGION_ID,                                                 ");
		sql.append("                         '合计' REGION_NAME,                                             ");
		sql.append("                         '' AS TICKET_TYPE_ID,                                           ");
		sql.append("                         '' TICKET_TYPE_NAME,                                            ");
		sql.append("                         SUM(1) CHECK_TICKET_NUM,                                        ");
		sql.append("                         3 ORDER_NUM                                                     ");
		sql.append("                    FROM SL_CHECK S                                                      ");
		sql.append("                    LEFT JOIN SYS_TICKET_TYPE TY                                         ");
		sql.append("                      ON TY.TICKET_TYPE_ID = S.TICKET_TYPE_ID                            ");
		sql.append("                  LEFT JOIN SYS_CLIENT C                                                 ");
		sql.append("                      ON C.CLIENT_ID = S.CLIENT_ID                                       ");
		sql.append("                    LEFT JOIN SYS_REGION R                                               ");
		sql.append("                        ON R.REGION_ID = C.REGION_ID                                     ");
		sql.append("                    LEFT JOIN SYS_VENUE V                                                ");
		sql.append("                   ON V.VENUE_ID = R.VENUE_ID                                            ");
		sql.append("                 WHERE TRUNC(S.OPE_TIME, 'DD') = :DATE ");
		sql.append("                   AND TO_CHAR(S.OPE_TIME, 'HH24:mi') >= :STARTTIME  ");
		sql.append("                   AND TO_CHAR(S.OPE_TIME, 'HH24:mi') <= :ENDTIME ");
		sql.append("				   AND S.PASS_FLAG='Y'");
		sql.append("				   AND S.TICKET_CLASS<> '4' ");// 排除员工卡
		// 场馆编号
		if (StringUtil.isNotNull(venueId)) {
			if (venueId.contains("-1")) {
				venueId = venueId.substring(venueId.indexOf("-1,") + 1);
			}
			if (venueId.contains(",")) {
				sql.append("   AND V.VENUE_ID in (" + venueId + ")");
			} else {
				sql.append("   AND V.VENUE_ID = :VENUE_ID ");
				params.put("VENUE_ID", venueId);
			}
		}
		// 区域编号
		if (StringUtil.isNotNull(regionId)) {
			if (regionId.contains("-1")) {
				regionId = regionId.substring(regionId.indexOf("-1,") + 1);
			}
			if (regionId.contains(",")) {
				sql.append("   AND R.REGION_ID in (" + regionId + ")");
			} else {
				sql.append("   AND R.REGION_ID = :REGION_ID");
				params.put("REGION_ID", regionId);
			}
		}
		// 票种
		if (StringUtil.isNotNull(ticketTypeId)) {
			if (ticketTypeId.contains("-1")) {
				ticketTypeId = ticketTypeId.substring(ticketTypeId.indexOf("-1,") + 1);
			}
			if (ticketTypeId.contains(",")) {
				sql.append("   AND S.TICKET_TYPE_ID in (" + ticketTypeId + ")");
			} else {
				sql.append("   AND S.TICKET_TYPE_ID = :TICKET_TYPE_ID");
				params.put("TICKET_TYPE_ID", ticketTypeId);
			}
		}
		sql.append("                   GROUP BY TO_CHAR(S.OPE_TIME, 'HH24:mi')) W1                           ");
		// 关联上dual
		sql.append("   INNER JOIN (");
		sql.append(" SELECT SPEC SJD, START_TIME A, END_TIME B FROM SYS_TIME_SPLIT WHERE START_TIME>=:STARTTIME AND END_TIME<=:ENDTIME ");
		// 时间间隔默认15分钟
		if (StringUtil.isNull(intervalTime)) {
			params.put("SPLITTIME", 15);
		}
		sql.append(" AND SPLIT_TIME=:SPLITTIME  ) W2         ");// 间隔时间
		sql.append("      ON W1.RPT_DATE >= W2.A                                                             ");
		sql.append("     AND W1.RPT_DATE < W2.B                                                              ");
		sql.append("   GROUP BY W2.SJD,                                                                      ");
		sql.append("            W1.VENUE_ID,                                                                 ");
		sql.append("            W1.VENUE_NAME,                                                               ");
		sql.append("            W1.REGION_ID,                                                                ");
		sql.append("            W1.REGION_NAME,                                                              ");
		sql.append("            W1.TICKET_TYPE_ID,                                                           ");
		sql.append("            W1.TICKET_TYPE_NAME,                                                         ");
		sql.append("            W1.ORDER_NUM                                                                 ");
		sql.append("   ORDER BY W2.SJD,                                                                      ");
		sql.append("            W1.VENUE_ID,                                                                 ");
		sql.append("            W1.REGION_ID,                                                                ");
		sql.append("            W1.TICKET_TYPE_ID,                                                           ");
		sql.append("            W1.ORDER_NUM                                                                 ");

		List<CustFlowDateTimeBean> list = dbUtil.queryListToBean("客流时间段统计", sql.toString(), CustFlowDateTimeBean.class, params);
		CustFlowDateTimeBean totalBean = new CustFlowDateTimeBean();
		totalBean.setVenueName("总计");
		totalBean.setCheckTicketNum(new Long(0));
		for (CustFlowDateTimeBean bean : list) {
			if (!"小计".equals(bean.getRegionName()) && !"合计".equals(bean.getRegionName())) {
				totalBean.setCheckTicketNum(totalBean.getCheckTicketNum() + bean.getCheckTicketNum());
			} else {
				continue;
			}
		}
		list.add(totalBean);
		return list;
	}

	@Override
	public List<Map<String, Object>> listStartTime(String splitTime) throws BaseException {
		List<Map<String, Object>> items = dbUtil.queryListToMap("查询时间间隔对应的开始时间", "SELECT START_TIME ID,START_TIME TEXT FROM SYS_TIME_SPLIT  WHERE SPLIT_TIME = ? ORDER BY START_TIME", splitTime);
		return items;
	}

	@Override
	public List<Map<String, Object>> listEndTime(String splitTime) throws BaseException {
		List<Map<String, Object>> items = dbUtil.queryListToMap("查询时间间隔对应的结束时间", "SELECT END_TIME ID,END_TIME TEXT FROM SYS_TIME_SPLIT  WHERE SPLIT_TIME = ? ORDER BY END_TIME", splitTime);
		return items;
	}

	@Override
	public PageBean<TicketTeamOrderBean> listRptTeamTdX(UserBean userBean, TicketTeamOrderBean tDBean) throws BaseException {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		sql.append(" SELECT O1.ORG_ID,");
		sql.append("        O1.ORG_NAME,  ");
		sql.append("        O2.TICKET_TYPE_ID, ");
		sql.append("        O2.TICKET_TYPE_NAME, ");
		sql.append("        TRUNC(O1.CHANGE_TIME, 'DD') CHANGE_TIME, ");
		sql.append("        NVL(SUM(O2.CHANGE_NUM),0) CHANGE_NUM, ");
		sql.append("        SUM(O2.MINUS_LIMIT) MINUS_LIMIT, ");
		sql.append("        SUM(O1.MINUS_ADVANCE_AMT) MINUS_ADVANCE_AMT ");
		sql.append("   FROM SL_TEAM_ORDER O1 ");
		sql.append("  INNER JOIN SL_TEAM_ORDER_DETAIL O2 ");
		sql.append("     ON O2.APPLY_ID = O1.APPLY_ID  ");
		sql.append("  WHERE 1 = 1 ");
		// 销售日期
		if (tDBean.getChangeTimeEnd() != null && tDBean.getChangeTimeStart() != null) {
			sql.append("   AND TRUNC(O1.CHANGE_TIME, 'DD') between :CHANGE_TIME_START AND :CHANGE_TIME_END");
			params.put("CHANGE_TIME_START", tDBean.getChangeTimeStart());
			params.put("CHANGE_TIME_END", tDBean.getChangeTimeEnd());
		}
		// 票种
		if (StringUtil.isNotNull(tDBean.getTicketTypeId())) {
			sql.append("   AND O2.TICKET_TYPE_ID = :TICKET_TYPE_ID ");
			params.put("TICKET_TYPE_ID", tDBean.getTicketTypeId());
		}
		// 票种
		if (StringUtil.isNotNull(tDBean.getOrgId())) {
			sql.append("   AND O1.ORG_ID = :ORG_ID ");
			params.put("ORG_ID", tDBean.getOrgId());
		}
		sql.append(" GROUP BY  ");
		sql.append(" 		  TRUNC(O1.CHANGE_TIME, 'DD'),  ");
		sql.append("          O1.ORG_ID,  ");
		sql.append(" 		  O1.ORG_NAME,   ");
		sql.append(" 		  O2.TICKET_TYPE_ID, ");
		sql.append(" 		  O2.TICKET_TYPE_NAME ");
		sql.append(" ORDER BY TRUNC(O1.CHANGE_TIME,'DD') DESC ,O1.ORG_ID, O2.TICKET_TYPE_ID  ");
		PageBean<TicketTeamOrderBean> page = dbUtil.queryPageToBean("团队换票统计查询", sql.toString(), TicketTeamOrderBean.class, userBean.getPageNum(), userBean.getPageSize(), params);
		return page;
	}

	@Override
	public List<CustFlowBean> listCustFlowDayN(UserBean loginUserBean, Date startDate, Date endDate, String regionId) throws BaseException {
		List<String> columnNm = new ArrayList<String>();// 列名称（票种名称）
		List<String> columnId = new ArrayList<String>();// 列编号（票种编号）

		List<CustFlowBean> custFlowList = new ArrayList<CustFlowBean>();// 返回页面的list
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer(); // 查询票种类型sql
		StringBuffer sb = new StringBuffer(); // 统计sql
		sql.append(" SELECT * FROM SYS_TICKET_TYPE ORDER BY TICKET_TYPE_ID ");
		List<SysTicketType> type = dbUtil.queryListToBean("查询票种类型", sql.toString(), SysTicketType.class, params);
		for (int j = 0; j < type.size(); j++) {
			columnNm.add(type.get(j).getTicketTypeName()); // 获得所有票种列名称
			columnId.add(type.get(j).getTicketTypeId());// 票种编号
		}
		sb.append(" SELECT REGION_NAME,COUNT(1) TOTAL_NUM  ");
		for (int i = 0; i < type.size(); i++) {
			sb.append(",SUM(DECODE(TICKET_TYPE_ID,'" + columnId.get(i) + "',1,0)) as \"" + columnId.get(i) + "\"");
		}
		sb.append(" FROM V_CUST_FLOW ");
		params.put("STARTDATE", startDate);
		params.put("ENDDATE", endDate);
		sb.append(" WHERE TRUNC(OPE_TIME,'DD')>=:STARTDATE AND TRUNC(OPE_TIME,'DD')<=:ENDDATE");
		if (StringUtil.isNotNull(regionId)) {
			if (regionId.contains("-1")) {
				regionId = regionId.substring(regionId.indexOf("-1,") + 1);
			}
			sb.append(" AND REGION_ID in (" + regionId + ")");
		}
		// if (StringUtil.isNotNull(ticketTypeId)) {
		// if (ticketTypeId.contains(",")) {
		// sb.append(" AND TICKET_TYPE_ID in (" + ticketTypeId + ")");
		// } else {
		// params.put("TICKET_TYPE_ID", ticketTypeId);
		// sb.append(" AND TICKET_TYPE_ID = :TICKET_TYPE_ID");
		// }
		// }
		sb.append(" GROUP BY REGION_ID,REGION_NAME ORDER BY REGION_ID ");

		List<Map<String, Object>> custFlowMap = dbUtil.queryListToMap("根据区域、票种统计客流量", sb.toString(), params);
		
		// 去除数据都为0的票种列
		int delCnt = 0;
		for (int j = 0; j < type.size(); j++) {
			//columnNm.add(type.get(j).getTicketTypeName()); // 获得所有票种列名称
			//columnId.add(type.get(j).getTicketTypeId());// 票种编号
			boolean allZero = true;
			for(Map<String, Object> row : custFlowMap) {
				if((Long)row.get(type.get(j).getTicketTypeId())>0) {
					allZero = false;
					break;
				}
			}
			if(allZero) {
				columnNm.remove(j - delCnt);
				columnId.remove(j - delCnt);
				delCnt++;
			}
		}
		// 添加合计行
		CustFlowBean totalBean = new CustFlowBean();
		totalBean.setRegionName("总计");
		totalBean.setTotalNum(new Long(0));
		List<Long> allColumnVal = new ArrayList<Long>();

		// 计算单个区域下每个票种的数量
		for (Map<String, Object> map : custFlowMap) {
			CustFlowBean custFlowBean = new CustFlowBean();
			custFlowBean.setRegionName((String) map.get("regionName"));
			custFlowBean.setTicTypeColName(columnNm);// 设置所有票种列名
			List<Long> columnVal = new ArrayList<Long>();
			for (int i = 0; i < columnId.size(); i++) {
				columnVal.add((Long) map.get(columnId.get(i)));// 设置票种对应数量
			}
			custFlowBean.setTicTypeNum(columnVal);
			custFlowBean.setTotalNum((Long) map.get("totalNum"));
			totalBean.setTotalNum(totalBean.getTotalNum() + custFlowBean.getTotalNum());
			custFlowList.add(custFlowBean);
		}
		// 计算所有区域对应票种的数量
		for (int z = 0; z < columnNm.size(); z++) {
			Long tempVal = new Long(0);
			for (int i = 0; i < custFlowMap.size(); i++) {
				tempVal = tempVal + (Long) custFlowMap.get(i).get(columnId.get(z));
			}
			allColumnVal.add(tempVal);
			totalBean.setTicTypeColName(columnNm);
			totalBean.setTicTypeNum(allColumnVal);
		}
		custFlowList.add(totalBean);
		return custFlowList;
	}

	@Override
	public List<CustFlowTimeBean> listCustFlowInterTimeN(UserBean loginUserBean, Date date, String startTime, String endTime, String intervalTime) throws BaseException {
		StringBuffer sql = new StringBuffer();
		StringBuffer sb = new StringBuffer();
		List<CustFlowTimeBean> custFlowList = new ArrayList<CustFlowTimeBean>();
		Map<String, Object> params = new HashMap<String, Object>();
		List<String> columnNm = new ArrayList<String>();
		params.put("START_TIME", startTime);// 时间段--开始时间
		params.put("END_TIME", endTime);// 时间段--结束时间
		// 时间间隔默认60分钟
		if (StringUtil.isNull(intervalTime)) {
			params.put("SPLIT_TIME", 60);
		}
		if (StringUtil.isNotNull(intervalTime)) {
			params.put("SPLIT_TIME", Long.parseLong(intervalTime));
		}
		sql.append(" SELECT SPEC, START_TIME, END_TIME FROM SYS_TIME_SPLIT WHERE START_TIME>=:START_TIME AND END_TIME<=:END_TIME AND SPLIT_TIME=:SPLIT_TIME AND START_TIME<=END_TIME");
		List<SysTimeSplitBean> split = dbUtil.queryListToBean("查询时间段", sql.toString(), SysTimeSplitBean.class, params);
		for (int j = 0; j < split.size(); j++) {
			columnNm.add(split.get(j).getSpec());
		}
		params.put("DATE", date);// 统计日期
		sb.append(" SELECT REGION_ID,REGION_NAME,TICKET_TYPE_ID,TICKET_TYPE_NAME,COUNT(1) TOTAL_NUM ,1 ORDERNUM ");
		for (int i = 0; i < split.size(); i++) {
			sb.append(",SUM(DECODE(SPEC,'" + columnNm.get(i) + "',1,0)) AS C" + i);
		}
		sb.append(" FROM V_CUST_FLOW_N ");
		sb.append(" WHERE TRUNC(OPE_TIME, 'DD') = :DATE AND START_TIME>=:START_TIME AND END_TIME<=:END_TIME AND SPLIT_TIME=:SPLIT_TIME ");
		sb.append(" GROUP BY REGION_ID,REGION_NAME,TICKET_TYPE_ID,TICKET_TYPE_NAME ");
		sb.append(" UNION ");
		sb.append(" SELECT REGION_ID,REGION_NAME,'' TICKET_TYPE_ID,'小计' TICKET_TYPE_NAME,COUNT(1) TOTAL_NUM ,2 ORDERNUM ");
		for (int i = 0; i < split.size(); i++) {
			sb.append(",SUM(DECODE(SPEC,'" + columnNm.get(i) + "',1,0)) AS C" + i);
		}
		sb.append(" FROM V_CUST_FLOW_N ");
		sb.append(" WHERE TRUNC(OPE_TIME, 'DD') = :DATE AND START_TIME>=:START_TIME AND END_TIME<=:END_TIME AND SPLIT_TIME=:SPLIT_TIME AND START_TIME<=END_TIME");
		sb.append(" GROUP BY REGION_ID,REGION_NAME ");
		sb.append(" ORDER BY REGION_ID,REGION_NAME,TICKET_TYPE_ID,TICKET_TYPE_NAME,ORDERNUM ");
		List<Map<String, Object>> custFlowMap = dbUtil.queryListToMap("根据区域、时间段统计客流量", sb.toString(), params);
		CustFlowTimeBean totalBean = new CustFlowTimeBean();
		totalBean.setRegionName("总计");
		totalBean.setTotalNum(new Long(0));
		List<Long> allColumnVal = new ArrayList<Long>();

		// 计算单个区域下每个票种的数量
		for (Map<String, Object> map : custFlowMap) {
			CustFlowTimeBean custFlowBean = new CustFlowTimeBean();
			custFlowBean.setRegionName((String) map.get("regionName"));
			custFlowBean.setTicketTypeName((String) map.get("ticketTypeName"));
			custFlowBean.setSplitName(columnNm);// 设置所有时间段列名
			List<Long> columnVal = new ArrayList<Long>();
			for (int i = 0; i < split.size(); i++) {
				columnVal.add((Long) map.get("c" + i));// 设置票种对应数量
			}
			custFlowBean.setTicTypeNum(columnVal);
			custFlowBean.setTotalNum((Long) map.get("totalNum"));
			if (!"小计".equals(map.get("ticketTypeName"))) {
				totalBean.setTotalNum(totalBean.getTotalNum() + custFlowBean.getTotalNum());
			}
			custFlowList.add(custFlowBean);
		}
		// 计算所有区域对应票种的数量
		for (int z = 0; z < columnNm.size(); z++) {
			Long tempVal = new Long(0);
			for (int i = 0; i < custFlowMap.size(); i++) {
				if ("小计".equals(custFlowMap.get(i).get("ticketTypeName"))) {
					continue;
				}
				tempVal = tempVal + (Long) custFlowMap.get(i).get("c" + z);
			}
			allColumnVal.add(tempVal);
			totalBean.setSplitName(columnNm);
			totalBean.setTicTypeNum(allColumnVal);
		}
		custFlowList.add(totalBean);
		return custFlowList;
	}

	@Override
	public PageBean<RefundTicketBean> outletRefundTicket(UserBean loginUserBean, Date refundTimeBegin, Date refundTimeEnd) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT to_char(RT.REFUND_TIME,'YYYY-MM-DD HH24:MI:SS') REFUND_TIME, ");
		sql.append("       to_char(O.Sale_Time,'YYYY-MM-DD HH24:MI:SS') Sale_Time, ");
		sql.append("       D.ITEM_NAME       ORDER_TYPE,            ");
		sql.append("       DI.ITEM_NAME      PAY_TYPE,              ");
		sql.append("       O.Ticket_Count,                          ");
		sql.append("       O.Real_Sum,                              ");
		sql.append("       RT.TICKET_COUNT   TRADE_NUM,             ");
		sql.append("       RT.REFUND_AMT_SUM,                       ");
		sql.append("       CASE WHEN O.ORDER_STAT='0' THEN '正常' WHEN O.ORDER_STAT='1' THEN '全额退款' WHEN O.ORDER_STAT='2' THEN '部分退款' END ORDER_STAT ");
		sql.append("  FROM SL_REFUND_TICKET RT                      ");
		sql.append(" INNER JOIN SL_PAY_TYPE PT                      ");
		sql.append("    ON RT.ORDER_ID = PT.ORDER_ID                ");
		sql.append(" INNER JOIN SL_ORDER O                          ");
		sql.append("    ON RT.ORDER_ID = O.ORDER_ID                 ");
		sql.append(" INNER JOIN SYS_DICTIONARY D                    ");
		sql.append("    ON RT.ORDER_TYPE = D.ITEM_CD                ");
		sql.append("   AND D.KEY_CD = 'ORDER_TYPE'                  ");
		sql.append(" INNER JOIN SYS_DICTIONARY DI                   ");
		sql.append("    ON PT.PAY_TYPE = DI.ITEM_CD                 ");
		sql.append("   AND DI.KEY_CD = 'PAY_TYPE'                   ");
		sql.append(" WHERE O.ORDER_STAT IN ('1','2') ");
		sql.append(" AND TRUNC(RT.REFUND_TIME,'dd') between :REFUNDTIMEBEGIN AND :REFUNDTIMEEND ");

		if (refundTimeBegin != null && refundTimeEnd != null) {
			params.put("REFUNDTIMEBEGIN", refundTimeBegin);
			params.put("REFUNDTIMEEND", refundTimeEnd);
		} else {
			params.put("REFUNDTIMEBEGIN", new Date());
			params.put("REFUNDTIMEEND", new Date());
		}

		sql.append(" ORDER BY RT.Refund_Time desc                   ");

		PageBean<RefundTicketBean> refundPage = dbUtil.queryPageToBean("", sql.toString(), RefundTicketBean.class, loginUserBean.getPageNum(), loginUserBean.getPageSize(), params);
		return refundPage;
	}

	@Override
	public String queryVenueName(String venueId) throws BaseException {
		List<Map<String, Object>> items = dbUtil.queryListToMap("查询场馆名称", "SELECT VENUE_NAME FROM SYS_VENUE  WHERE VENUE_ID IN (" + venueId + ")  ORDER BY VENUE_ID");
		String venueName = "";
		for (Map<String, Object> venue : items) {
			venueName = venueName + "," + venue.get("venueName");
		}
		if (venueName.contains(",")) {
			venueName = venueName.substring(venueName.indexOf(",") + 1);
		}
		return venueName;
	}

	@Override
	public String queryRegionName(String regionId) throws BaseException {
		List<Map<String, Object>> items = dbUtil.queryListToMap("查询区域名称", "SELECT REGION_NAME FROM SYS_REGION  WHERE REGION_ID IN (" + regionId + ") ORDER BY REGION_ID");
		String regionName = "";
		for (Map<String, Object> region : items) {
			regionName = regionName + "," + region.get("regionName");
		}
		if (regionName.contains(",")) {
			regionName = regionName.substring(regionName.indexOf(",") + 1);
		}
		return regionName;
	}

	@Override
	public String queryTicketTypeName(String ticketTypeId) throws BaseException {
		List<Map<String, Object>> items = dbUtil.queryListToMap("查询票种名称", "SELECT TICKET_TYPE_NAME FROM SYS_TICKET_TYPE  WHERE TICKET_TYPE_ID IN (" + ticketTypeId + ") ORDER BY TICKET_TYPE_ID");
		String ticketTypeName = "";
		for (Map<String, Object> ticketType : items) {
			ticketTypeName = ticketTypeName + "," + ticketType.get("ticketTypeName");
		}
		if (ticketTypeName.contains(",")) {
			ticketTypeName = ticketTypeName.substring(ticketTypeName.indexOf(",") + 1);
		}
		return ticketTypeName;
	}

	@Override
	public List<TicketTeamOrderBean> listRptTeamTdExp(UserBean loginUserBean, String ticketTypeId, Date changeTime) {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		sql.append(" SELECT O1.ORG_ID,");
		sql.append("        O1.ORG_NAME,  ");
		sql.append("        O2.TICKET_TYPE_ID, ");
		sql.append("        O2.TICKET_TYPE_NAME, ");
		sql.append("        TRUNC(O1.CHANGE_TIME, 'DD') CHANGE_TIME, ");
		sql.append("        NVL(SUM(O2.CHANGE_NUM),0) CHANGE_NUM, ");
		sql.append("        SUM(O2.MINUS_LIMIT) MINUS_LIMIT, ");
		sql.append("        SUM(O1.MINUS_ADVANCE_AMT) MINUS_ADVANCE_AMT ");
		sql.append("   FROM SL_TEAM_ORDER O1 ");
		sql.append("  INNER JOIN SL_TEAM_ORDER_DETAIL O2 ");
		sql.append("     ON O2.APPLY_ID = O1.APPLY_ID  ");
		sql.append("  WHERE 1 = 1 ");
		// 销售日期
		if (changeTime != null) {
			sql.append("   AND TRUNC(O1.CHANGE_TIME, 'DD') = :CHANGE_TIME ");
			params.put("CHANGE_TIME", changeTime);
		}
		// 票种
		if (StringUtil.isNotNull(ticketTypeId)) {
			sql.append("   AND O2.TICKET_TYPE_ID = :TICKET_TYPE_ID ");
			params.put("TICKET_TYPE_ID", ticketTypeId);
		}
		sql.append(" GROUP BY  ");
		sql.append(" 		  TRUNC(O1.CHANGE_TIME, 'DD'),  ");
		sql.append("          O1.ORG_ID,  ");
		sql.append(" 		  O1.ORG_NAME,   ");
		sql.append(" 		  O2.TICKET_TYPE_ID, ");
		sql.append(" 		  O2.TICKET_TYPE_NAME ");
		sql.append(" 		  ORDER BY TRUNC(O1.CHANGE_TIME,'DD') ,O1.ORG_ID, O2.TICKET_TYPE_ID ");

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT O1.ORG_ID,");
		sb.append("        TRUNC(O1.CHANGE_TIME, 'DD') CHANGE_TIME, ");
		sb.append("        SUM(O1.MINUS_ADVANCE_AMT) MINUS_ADVANCE_AMT ");
		sb.append("   FROM SL_TEAM_ORDER O1 ");
		sb.append("  INNER JOIN SL_TEAM_ORDER_DETAIL O2 ");
		sb.append("     ON O2.APPLY_ID = O1.APPLY_ID  ");
		sb.append("  WHERE 1 = 1 ");
		// 销售日期
		if (changeTime != null) {
			sb.append("   AND TRUNC(O1.CHANGE_TIME, 'DD') = :CHANGE_TIME ");
		}
		// 票种
		if (StringUtil.isNotNull(ticketTypeId)) {
			sb.append("   AND O2.TICKET_TYPE_ID = :TICKET_TYPE_ID ");
		}
		sb.append(" GROUP BY  ");
		sb.append(" 		  TRUNC(O1.CHANGE_TIME, 'DD'),  ");
		sb.append("           O1.ORG_ID  ");
		sb.append(" 		  ORDER BY TRUNC(O1.CHANGE_TIME,'DD'),O1.ORG_ID  ");
		List<TicketTeamOrderBean> list = dbUtil.queryListToBean("团队换票统计查询", sql.toString(), TicketTeamOrderBean.class, params);
		List<TicketTeamOrderBean> minusList = dbUtil.queryListToBean("扣减预付款查询", sb.toString(), TicketTeamOrderBean.class, params);

		for (TicketTeamOrderBean order : list) {
			for (TicketTeamOrderBean minus : minusList) {
				if (order.getChangeTime() != null && minus.getChangeTime() != null) {
					if (order.getOrgId().equals(minus.getOrgId()) && order.getChangeTime().compareTo(minus.getChangeTime()) == 0) {
						order.setMinusAdvanceAmt(minus.getMinusAdvanceAmt());
					}
				}
			}
		}
		return list;
	}

	@Override
	public List<Integer[]> getExcelMerge(List<TicketTeamOrderBean> teamList, String ticketTypeId, Date changeTime) {
		StringBuffer sb = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();

		sb.append(" SELECT O1.ORG_ID,");
		sb.append("        TRUNC(O1.CHANGE_TIME, 'DD') CHANGE_TIME, ");
		sb.append("        SUM(O1.MINUS_ADVANCE_AMT) MINUS_ADVANCE_AMT ");
		sb.append("   FROM SL_TEAM_ORDER O1 ");
		sb.append("  INNER JOIN SL_TEAM_ORDER_DETAIL O2 ");
		sb.append("     ON O2.APPLY_ID = O1.APPLY_ID  ");
		sb.append("  WHERE 1 = 1 ");
		// 销售日期
		if (changeTime != null) {
			sb.append("   AND TRUNC(O1.CHANGE_TIME, 'DD') = :CHANGE_TIME ");
			params.put("CHANGE_TIME", changeTime);
		}
		// 票种
		if (StringUtil.isNotNull(ticketTypeId)) {
			sb.append("   AND O2.TICKET_TYPE_ID = :TICKET_TYPE_ID ");
			params.put("TICKET_TYPE_ID", ticketTypeId);
		}
		sb.append(" GROUP BY  ");
		sb.append(" 		  TRUNC(O1.CHANGE_TIME, 'DD'),  ");
		sb.append("           O1.ORG_ID  ");
		sb.append(" 		  ORDER BY TRUNC(O1.CHANGE_TIME,'DD'),O1.ORG_ID  ");

		List<TicketTeamOrderBean> minusList = dbUtil.queryListToBean("扣减预付款查询", sb.toString(), TicketTeamOrderBean.class, params);
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (TicketTeamOrderBean minus : minusList) {
			Integer count = 1;
			for (TicketTeamOrderBean order : teamList) {
				if (order.getChangeTime() != null && minus.getChangeTime() != null) {
					if (order.getOrgId().equals(minus.getOrgId()) && order.getChangeTime().compareTo(minus.getChangeTime()) == 0) {
						map.put(order.getChangeTime() + order.getOrgId(), count++);
					}
				}
			}
		}
		List<Integer[]> list = new ArrayList<Integer[]>();
		Integer index = 0;
		for (int j = 0; j < minusList.size(); j++) {
			index = index + map.get(minusList.get(j).getChangeTime() + minusList.get(j).getOrgId());
			for (int i = 0; i < 3; i++) {
				Integer[] r = new Integer[4];
				if (j == 0) {
					r[0] = 2;
					r[1] = r[0] + map.get(minusList.get(j).getChangeTime() + minusList.get(j).getOrgId()) - 1;
					index = r[1];
					r[2] = i;
					r[3] = i;
					if (r[0] == r[1]) {
						break;
					}
					list.add(r);
				} else {
					r[1] = index;
					r[0] = r[1] - map.get(minusList.get(j).getChangeTime() + minusList.get(j).getOrgId()) + 1;
					r[2] = i;
					r[3] = i;
					if (r[0] == r[1]) {
						break;
					}
					list.add(r);
				}
			}
		}
		return list;
	}
}
