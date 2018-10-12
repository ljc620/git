package com.tbims.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tbims.bean.CustFlowDayBean;
import com.tbims.bean.RptCheckBean;
import com.tbims.bean.RptDeliveryBean;
import com.tbims.bean.RptOutletSale1Bean;
import com.tbims.bean.RptStrinfoBean;
import com.tbims.bean.TicketCheckByIdenttyBean;
import com.tbims.bean.TicketNoCheckBean;
import com.tbims.bean.TicketSaleByIdenttyBean;
import com.tbims.entity.SysBlackList;
import com.tbims.service.IRptSaleService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.CommonUtil;
import com.zhming.support.util.DateUtil;
import com.zhming.support.util.StringUtil;

@Service
public class RptSaleService extends BaseService implements IRptSaleService {
	private static final int INTERVALMIN = 15;

	@Override
	public PageBean<RptStrinfoBean> getOutletStorePage(UserBean userBean, String ticketTypeId, Long outletId) {
		Map<String, Object> params = new HashMap<>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from (");
		sb.append(" select mysum.rpt_date,     ");
		sb.append("        mysum.outlet_id,    ");
		sb.append("        mysum.ticket_type_id, ");
		sb.append("        outlet.outlet_name,  ");
		sb.append("        ty.ticket_type_name,  ");
		sb.append("        sum(str_last_ticket_num) str_last_ticket_num,  ");
		sb.append("        sum(in_ticket_num) in_ticket_num, ");
		sb.append("        sum(sale_ticket_num) sale_ticket_num,   ");
		sb.append("        sum(useless_ticket_num) useless_ticket_num, ");
		sb.append("		   sum(supply_ticket_num) supply_ticket_num,");
		sb.append("        sum(str_ticket_num)    str_ticket_num   ");
		sb.append("   from (     ");
		sb.append(" select trunc(sysdate, 'dd') rpt_date, ");
		sb.append("   ti.outlet_id,            ");
		sb.append("   ti.ticket_type_id,       ");
		sb.append("   0 str_last_ticket_num,   ");
		sb.append("   0 in_ticket_num,         ");
		sb.append("   0 sale_ticket_num,       ");
		sb.append("   0 useless_ticket_num,    ");
		sb.append("   0 supply_ticket_num, ");
		sb.append("   str_ticket_num          ");
		sb.append("   from str_outlet_info ti where 1=1 ");
		if (StringUtil.isNotNull(ticketTypeId)) {
			sb.append("               and 	ti.TICKET_TYPE_ID=:TICKET_TYPE_ID ");
			params.put("TICKET_TYPE_ID", ticketTypeId);
		}
		if (outletId != null) {
			sb.append("               and 	ti.outlet_id=:OUTLET_ID ");
			params.put("OUTLET_ID", outletId);
		}
		sb.append("         union all                                  ");
		sb.append("         select trunc(c.sign_time, 'dd') rpt_date,  ");
		sb.append("                to_number(c.store_id) outlet_id,    ");
		sb.append("                c.ticket_type_id,                   ");
		sb.append("                0 str_last_ticket_num,              ");
		sb.append("                count(1) in_ticket_num,    ");
		sb.append("                0 sale_ticket_num,                  ");
		sb.append("                0 useless_ticket_num,               ");
		sb.append("  			   0 supply_ticket_num, 			   ");
		sb.append("                0 str_ticket_num                    ");
		sb.append("           from str_ticket_info c                   ");
		sb.append("          where c.store_id is not null              ");
		sb.append("            and trunc(c.sign_time, 'dd') = trunc(sysdate, 'dd') ");
		if (StringUtil.isNotNull(ticketTypeId)) {
			sb.append("               and 	c.TICKET_TYPE_ID=:TICKET_TYPE_ID ");
			params.put("TICKET_TYPE_ID", ticketTypeId);
		}
		if (outletId != null) {
			sb.append("               and 	c.store_id=:OUTLET_ID ");
			params.put("OUTLET_ID", outletId);
		}
		sb.append("          group by trunc(c.sign_time, 'dd'), c.store_id, c.ticket_type_id ");
		sb.append("         union all ");
		sb.append("         select trunc(sysdate, 'dd') rpt_date, ");
		sb.append("                c.outlet_id,            ");
		sb.append("                c.ticket_type_id,       ");
		sb.append("                c.str_ticket_num,       ");
		sb.append("                0 in_ticket_num,        ");
		sb.append("                0 sale_ticket_num,      ");
		sb.append("                0 useless_ticket_num,   ");
		sb.append("   			   0 supply_ticket_num,    ");
		sb.append("                0 str_ticket_num        ");
		sb.append("           from rpt_strinfo_outlet_d c  ");
		sb.append("          where trunc(c.rpt_date, 'dd') = trunc(sysdate - 1, 'dd') ");
		if (StringUtil.isNotNull(ticketTypeId)) {
			sb.append("               and 	c.TICKET_TYPE_ID=:TICKET_TYPE_ID ");
			params.put("TICKET_TYPE_ID", ticketTypeId);
		}
		if (outletId != null) {
			sb.append("               and 	c.outlet_id=:OUTLET_ID ");
			params.put("OUTLET_ID", outletId);
		}
		sb.append("         union all ");
		sb.append("         select trunc(od.eject_ticket_time, 'dd') rpt_date, ");
		sb.append("                od.outlet_id, ");
		sb.append("                od.ticket_type_id, ");
		sb.append("                0 str_last_ticket_num, ");
		sb.append("                0 in_ticket_num,     ");
		sb.append("                sum(case ");
		sb.append("                      when od.eject_ticket_stat = '2' then  ");
		sb.append("                       1    ");
		sb.append("                      else  ");
		sb.append("                       0    ");
		sb.append("                    end) sale_ticket_num,  ");
		sb.append("                0 useless_ticket_num,      ");
		sb.append("   			   0 supply_ticket_num,		  ");
		sb.append("                0 str_ticket_num           ");
		sb.append("           from sl_order_detail od         ");
		sb.append("          inner join sl_order o            ");
		sb.append("             on od.order_id = o.order_id   ");
		sb.append("          where trunc(od.eject_ticket_time, 'dd') = trunc(sysdate, 'dd')  and o.pay_stat='2'   ");
		if (StringUtil.isNotNull(ticketTypeId)) {
			sb.append("               and 	od.TICKET_TYPE_ID=:TICKET_TYPE_ID ");
			params.put("TICKET_TYPE_ID", ticketTypeId);
		}
		if (outletId != null) {
			sb.append("               and 	od.outlet_id=:OUTLET_ID ");
			params.put("OUTLET_ID", outletId);
		}
		sb.append("          group by trunc(od.eject_ticket_time, 'dd'),                        ");
		sb.append("                   od.outlet_id,                                             ");
		sb.append("                   od.ticket_type_id                                         ");

		// 追加实体代理销售记录登记
		sb.append("         union all                                                           ");
		sb.append("select trunc(srg.SALE_DATE,'dd') rpt_date, ");
		sb.append("                 srg.outlet_id, ");
		sb.append("                 srg.ticket_type_id, ");
		sb.append("                 0 str_last_ticket_num,  ");
		sb.append("                 0 in_ticket_num,");
		sb.append("                 sum(srg.sale_num) sale_ticket_num, ");
		sb.append("                 0 useless_ticket_num,   ");
		sb.append("   			    0 supply_ticket_num,    ");
		sb.append("                 0 str_ticket_num        ");
		sb.append("          from SL_SALE_REG srg");
		sb.append("          where trunc(srg.SALE_DATE,'dd')= trunc(sysdate, 'dd') ");
		if (StringUtil.isNotNull(ticketTypeId)) {
			sb.append("               and 	srg.TICKET_TYPE_ID=:TICKET_TYPE_ID ");
			params.put("TICKET_TYPE_ID", ticketTypeId);
		}
		if (outletId != null) {
			sb.append("               and 	srg.outlet_id=:OUTLET_ID ");
			params.put("OUTLET_ID", outletId);
		}
		sb.append("          group by trunc(srg.SALE_DATE,'dd'),srg.outlet_id,srg.ticket_type_id ");

		sb.append("         union all                                                           ");
		sb.append("         select trunc(sysdate, 'dd') rpt_date,                  ");
		sb.append("                useless.outlet_id,                                           ");
		sb.append("                useless.ticket_type_id,                                      ");
		sb.append("                0 str_last_ticket_num,                                       ");
		sb.append("                0 in_ticket_num,                                             ");
		sb.append("                0 sale_ticket_num,                                           ");
		sb.append("                sum(case when useless.useless_reason='芯片作废' then 1 else 0 end) useless_ticket_num, ");
		sb.append("                sum(case when useless.useless_reason='补票作废' then 1 else 0 end) supply_ticket_num,  ");
		sb.append("                0 str_ticket_num                                             ");
		sb.append("           from sl_useless_ticket_info useless                               ");
		sb.append("          where  useless.confirm_time is null ");
		if (StringUtil.isNotNull(ticketTypeId)) {
			sb.append("               and 	useless.TICKET_TYPE_ID=:TICKET_TYPE_ID ");
			params.put("TICKET_TYPE_ID", ticketTypeId);
		}
		if (outletId != null) {
			sb.append("               and 	useless.outlet_id=:OUTLET_ID  ");
			params.put("OUTLET_ID", outletId);
		}
		sb.append("          group by                   ");
		sb.append("                   useless.outlet_id,                                        ");
		sb.append("                   useless.ticket_type_id  ) mysum                           ");
		sb.append("  inner join sys_outlet outlet                                               ");
		sb.append("     on mysum.outlet_id = outlet.outlet_id                                   ");
		sb.append("  inner join sys_ticket_type ty                                              ");
		sb.append("     on mysum.ticket_type_id = ty.ticket_type_id                             ");
		sb.append("  group by mysum.rpt_date,                                                   ");
		sb.append("           mysum.outlet_id,                                                  ");
		sb.append("           mysum.ticket_type_id,                                             ");
		sb.append("           outlet.outlet_name,                                               ");
		sb.append("           ty.ticket_type_name     ");
		sb.append(" ) where (str_last_ticket_num<>0 or in_ticket_num<>0 or sale_ticket_num<>0 or useless_ticket_num<>0 or supply_ticket_num<>0 or str_ticket_num<>0)  ");
		sb.append("  order by  rpt_date, outlet_id ,ticket_type_id ");
		String sql = sb.toString();
		return dbUtil.queryPageToBean("网点当日库存统计", sql, RptStrinfoBean.class, userBean.getPageNum(), userBean.getPageSize(), params);
	}

	@Override
	public PageBean<RptStrinfoBean> getCenterStorePage(UserBean userBean, String ticketTypeId) {
		Map<String, Object> params = new HashMap<>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select mysum.rpt_Date,            ");
		sb.append("        mysum.ticket_type_id,      ");
		sb.append("        ty.ticket_type_name,       ");
		sb.append("        sum(str_Last_Chest_Num) str_Last_Chest_Num,   ");
		sb.append("        sum(str_Last_Ticket_Num) str_Last_Ticket_Num,  ");
		sb.append("        sum(in_Chest_Num) in_Chest_Num,         ");
		sb.append("        sum(in_Ticket_Num) in_Ticket_Num,        ");
		sb.append("        sum(out_Chest_Num) out_Chest_Num,        ");
		sb.append("        sum(out_Ticket_Num)out_Ticket_Num,       ");
		sb.append("        sum(str_Chest_Num) str_Chest_Num,        ");
		sb.append("        sum(str_Ticket_Num)str_Ticket_Num        ");
		sb.append("   from (select trunc(c.ope_time, 'dd') rpt_Date, ");
		sb.append("                c.ticket_type_id ticket_type_id,  ");
		sb.append("                0 str_Last_Chest_Num,             ");
		sb.append("                0 str_Last_Ticket_Num,            ");
		sb.append("                sum(1) in_Chest_Num,              ");
		sb.append("                sum(c.ticket_num) in_Ticket_Num,  ");
		sb.append("                0 out_Chest_Num,                  ");
		sb.append("                0 out_Ticket_Num,                 ");
		sb.append("                0 str_Chest_Num,                  ");
		sb.append("                0 str_Ticket_Num                  ");
		sb.append("           from str_chest c                       ");
		sb.append("          where trunc(c.ope_time, 'dd') = trunc(sysdate, 'dd') ");
		if (StringUtil.isNotNull(ticketTypeId)) {
			params.put("TICKET_TYPE_ID", ticketTypeId);
			sb.append(" AND C.TICKET_TYPE_ID=:TICKET_TYPE_ID ");
		}
		sb.append("          group by trunc(c.ope_time, 'dd'), c.ticket_type_id   ");
		sb.append("         union  ");
		sb.append("         select trunc(sysdate, 'dd') rpt_Date,   ");
		sb.append("                c.ticket_type_id ticket_type_id, ");
		sb.append("                0 str_Last_Chest_Num,            ");
		sb.append("                0 str_Last_Ticket_Num,           ");
		sb.append("                0 in_Chest_Num,                  ");
		sb.append("                0 in_Ticket_Num,                 ");
		sb.append("                0 out_Chest_Num,                 ");
		sb.append("                0 out_Ticket_Num,                ");
		sb.append("                trunc(count(1)/max(n.chestNum),2) STR_CHEST_NUM,            ");
		sb.append("                count(1) STR_TICKET_NUM  ");
		sb.append("           from str_ticket_info c,v_calc_chestNum n        ");
		sb.append("          where c.store_id is null  ");
		if (StringUtil.isNotNull(ticketTypeId)) {

			sb.append(" AND C.TICKET_TYPE_ID=:TICKET_TYPE_ID ");
		}
		sb.append("         group by c.ticket_type_id ");
		sb.append("         union                                                   ");
		sb.append("         select trunc(c.SIGN_TIME, 'dd') rpt_Date,               ");
		sb.append("                c.ticket_type_id ticket_type_id,                 ");
		sb.append("                0 str_Last_Chest_Num,                            ");
		sb.append("                0 str_Last_Ticket_Num,                           ");
		sb.append("                0 in_Chest_Num,                                  ");
		sb.append("                0 in_Ticket_Num,                                 ");
		sb.append("                trunc(count(1)/max(n.chestNum),2) STR_CHEST_NUM,      ");
		sb.append("                count(1) out_Ticket_Num,                         ");
		sb.append("                0 str_Chest_Num,                                 ");
		sb.append("                0 str_Ticket_Num                                 ");
		sb.append("           from str_ticket_info c,v_calc_chestNum n              ");
		// store_id is not null表示已出库 SIGN_TIME网点签收时间代表出库
		sb.append("          where trunc(c.SIGN_TIME, 'dd') = trunc(sysdate, 'dd') and c.store_id is not null ");
		if (StringUtil.isNotNull(ticketTypeId)) {

			sb.append(" AND C.TICKET_TYPE_ID=:TICKET_TYPE_ID ");
		}
		sb.append("          group by trunc(c.SIGN_TIME, 'dd'), c.ticket_type_id    ");
		sb.append("         union                                                   ");
		sb.append("         select trunc(sysdate, 'dd'),                            ");
		sb.append("                c.ticket_type_id,                                ");
		sb.append("                c.str_chest_num str_Last_Chest_Num,              ");
		sb.append("                c.str_ticket_num str_Last_Ticket_Num,            ");
		sb.append("                0 in_Chest_Num,                                  ");
		sb.append("                0 in_Ticket_Num,                                 ");
		sb.append("                0 out_Chest_Num,                                 ");
		sb.append("                0 out_Ticket_Num,                                ");
		sb.append("                0 str_Chest_Num,                                 ");
		sb.append("                0 str_Ticket_Num                                 ");
		sb.append("           from rpt_strinfo_d c                                  ");
		sb.append("          where trunc(c.rpt_date, 'dd') = trunc(sysdate - 1, 'dd')");
		if (StringUtil.isNotNull(ticketTypeId)) {

			sb.append(" AND C.TICKET_TYPE_ID=:TICKET_TYPE_ID ");
		}
		sb.append(" ) mysum ");
		sb.append("  inner join sys_ticket_type ty                                           ");
		sb.append("     on mysum.ticket_type_id = ty.ticket_type_id                          ");
		sb.append("  group by mysum.rpt_Date, mysum.ticket_type_id, ty.ticket_type_name      ");
		sb.append("   ORDER BY mysum.TICKET_TYPE_ID");
		String sql = sb.toString();
		PageBean<RptStrinfoBean> ret = dbUtil.queryPageToBean("查询票务", sql, RptStrinfoBean.class, userBean.getPageNum(), userBean.getPageSize(), params);
		return ret;
	}

	// @Override
	// public List<RptOutletSale1Bean> getRptOutletSaleBeanList(Date rptDt,
	// String flag) {
	// Map<String, Object> params = new HashMap<>();
	// StringBuffer sql = new StringBuffer();
	//
	// if (rptDt == null) {
	// rptDt = new Date();
	// }
	//
	// flag = StringUtil.queryParamByString(flag);
	//
	// sql.append("SELECT RPT_DT, ");
	// sql.append(" OUTLET.OUTLET_ID, ");
	// sql.append(" OUTLET.OUTLET_NAME, ");
	// sql.append(" ORDER_TYPE_DESC, ");
	// sql.append(" PAY_TYPE_DESC, ");
	// sql.append(" TICKET_TYPE_NAME, ");
	// sql.append(" SALE_SUM, ");
	// sql.append(" SALE_AMT ");
	// sql.append(" FROM SYS_OUTLET OUTLET ");
	// sql.append(" INNER JOIN ( ");
	// sql.append(" select TRUNC(RPT_DT," + flag + ") RPT_DT, OUTLET_ID,
	// ORDER_TYPE_DESC, PAY_TYPE_DESC, TICKET_TYPE_NAME, ORDER_NUM, SALE_SUM,
	// SALE_AMT from v_outsale_dd where TRUNC(rpt_dt," + flag + ")=:RPT_DT ");
	// sql.append(" union ");
	// sql.append(" select TRUNC(RPT_DT," + flag + ")
	// RPT_DT,outlet_id,outlet_name order_type_desc,'N' pay_type_desc,'N'
	// ticket_type_name,1,sale_num sale_num,sale_sum_amt sale_amt from
	// V_OUTSALE_DD_ZG_NOTIC where TRUNC(rpt_dt," + flag + ")=:RPT_DT ");
	// sql.append(") T ");
	// sql.append(" ON OUTLET.OUTLET_ID = T.OUTLET_ID ");
	// sql.append(" ORDER BY OUTLET_ID, ");
	// sql.append(" order_num, ");
	// sql.append(" ORDER_TYPE_DESC, ");
	// sql.append(" PAY_TYPE_DESC, ");
	// sql.append(" TICKET_TYPE_NAME ");
	//
	// params.put("RPT_DT", rptDt);
	//
	// List<RptOutletSale1Bean> outletSaleList =
	// dbUtil.queryListToBean("网点销售统计表", sql.toString(),
	// RptOutletSale1Bean.class, params);
	// return outletSaleList;
	// }

	@Override
	public List<RptOutletSale1Bean> getRptOutletSaleBeanListPage(Date rptDt, String flag, boolean isExcel) {
		Map<String, Object> params = new HashMap<>();
		StringBuffer sql = new StringBuffer();

		if (rptDt == null) {
			rptDt = new Date();
		}

		flag = StringUtil.queryParamByString(flag);

		sql.append("SELECT RPT_DT,                                                                                                                                                                                 ");
		sql.append("       OUTLET.OUTLET_ID,                                                                                                                                                                       ");
		sql.append("       OUTLET.OUTLET_NAME,                                                                                                                                                                     ");
		sql.append("       ORDER_TYPE_DESC,                                                                                                                                                                        ");
		sql.append("       PAY_TYPE_DESC,                                                                                                                                                                          ");
		sql.append("       TICKET_TYPE_NAME,                                                                                                                                                                       ");
		sql.append("       SALE_SUM,                                                                                                                                                                               ");
		sql.append("       SALE_AMT                                                                                                                                                                                ");
		sql.append("  FROM SYS_OUTLET OUTLET                                                                                                                                                                       ");
		sql.append(" INNER JOIN (                                                                                                                                                                                  ");
		sql.append("    select TRUNC(rpt_dt," + flag + ") rpt_dt, OUTLET_ID, ORDER_TYPE_DESC, PAY_TYPE_DESC, TICKET_TYPE_NAME, ORDER_NUM, sum(SALE_SUM) SALE_SUM, sum(SALE_AMT) SALE_AMT from v_outsale_dd where  order_type<>'ZY' and TRUNC(rpt_dt," + flag + ")=:RPT_DT  ");
		sql.append("   group by TRUNC(rpt_dt," + flag + "), OUTLET_ID, ORDER_TYPE_DESC, PAY_TYPE_DESC, TICKET_TYPE_NAME, ORDER_NUM ");
		sql.append("      union                                                                                                                                                                                    ");
		sql.append(getZGNOQusql(flag));
		if (!isExcel) {
			sql.append("    UNION                                                                                                                                                                                      ");
			sql.append("    select  vodd.RPT_DT,                                                                                                                                                                       ");
			sql.append("         OUTLET_ID,                                                                                                                                                                            ");
			sql.append("          '<span style=\"font-weight:900;color:#000;\" >小计</span>' AS ORDER_TYPE_DESC,                                                                                                         ");
			sql.append("          '' AS PAY_TYPE_DESC,                                                                                                                                                                 ");
			sql.append("          '' TICKET_TYPE_NAME,                                                                                                                                                                 ");
			sql.append("          2 ORDER_NUM,                                                                                                                                                                         ");
			sql.append("          SUM(vodd.SALE_SUM) AS SALE_SUM,                                                                                                                                                      ");
			sql.append("          SUM(vodd.SALE_AMT) AS SALE_AMT                                                                                                                                                       ");
			sql.append("          from (                                                                                                                                                                               ");
			sql.append("    select TRUNC(rpt_dt," + flag + ") rpt_dt, OUTLET_ID, ORDER_TYPE_DESC, PAY_TYPE_DESC, TICKET_TYPE_NAME, ORDER_NUM, sum(SALE_SUM) SALE_SUM, sum(SALE_AMT) SALE_AMT from v_outsale_dd where order_type<>'ZY' and TRUNC(rpt_dt," + flag + ")=:RPT_DT  ");
			sql.append("   group by TRUNC(rpt_dt," + flag + "), OUTLET_ID, ORDER_TYPE_DESC, PAY_TYPE_DESC, TICKET_TYPE_NAME, ORDER_NUM ");
			sql.append("    union                                                                                                                                                                           ");
			sql.append(getZGNOQusql(flag));
			sql.append("         ) vodd                                                                                                                                                                                ");
			sql.append("     GROUP BY vodd.RPT_DT,OUTLET_ID                                                                                                                                                            ");
		}
		sql.append(") T                                                                                                                                                                                            ");
		sql.append("    ON OUTLET.OUTLET_ID = T.OUTLET_ID                                                                                                                                                          ");
		sql.append(" ORDER BY OUTLET_ID,                                                                                                                                                                           ");
		sql.append("          order_num,                                                                                                                                                                           ");
		sql.append("          ORDER_TYPE_DESC,                                                                                                                                                                     ");
		sql.append("          PAY_TYPE_DESC,                                                                                                                                                                       ");
		sql.append("          TICKET_TYPE_NAME                                                                                                                                                                     ");

		params.put("RPT_DT", rptDt);

		List<RptOutletSale1Bean> outletSaleList = dbUtil.queryListToBean("网点销售统计表", sql.toString(), RptOutletSale1Bean.class, params);
		return outletSaleList;
	}

	/**
	 * 售票机未取票金额和笔数的sql
	 * 
	 * @param flag
	 * @return
	 */
	private String getZGNOQusql(String flag) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TRUNC(A.SALE_TIME, " + flag + ") rpt_dt,                                                         ");
		sql.append("                    E.OUTLET_ID,                                                                ");
		sql.append("                    E.OUTLET_NAME || '-售票机未取票金额' ORDER_TYPE_DESC,                       ");
		sql.append("                    'N' PAY_TYPE_DESC,                                                          ");
		sql.append("                    'N' TICKET_TYPE_NAME,                                                       ");
		sql.append("                    1 ORDER_NUM,                                                                ");
		sql.append("                    sum(A.Ticket_Count - nvl(STT.EJECT_TICKET_COUNT, 0)) SALE_SUM,               ");
		sql.append("                    sum(A.REAL_SUM - nvl(stt.eject_price, 0)) SALE_SUM_AMT                     ");
		sql.append("               FROM SL_ORDER A                                                                  ");
		sql.append("               left join (select a.order_id,                                                    ");
		sql.append("                                count(1) EJECT_TICKET_COUNT,                                    ");
		sql.append("                                sum(b.sale_price) eject_price                                   ");
		sql.append("                           from sl_order a, sl_order_detail b                                   ");
		sql.append("                          where a.order_id = b.order_id                                         ");
		sql.append("                            and order_stat = '0'                                                ");
		sql.append("                            and A.PAY_STAT = '2'                                                ");
		sql.append("                            AND A.Order_Type in ('ZG')                                          ");
		sql.append("                            and TRUNC(A.SALE_TIME, " + flag + ") = :RPT_DT                      ");
		sql.append("                          group by a.order_id) STT                                              ");
		sql.append("                 on A.Order_Id = STT.order_id                                                   ");
		sql.append("               left join sys_client sct                                                         ");
		sql.append("                 on A.Sale_User_Id = sct.client_id                                              ");
		sql.append("               LEFT JOIN SYS_OUTLET E                                                           ");
		sql.append("                 ON sct.OUTLET_ID = E.OUTLET_ID                                                 ");
		sql.append("              WHERE A.PAY_STAT = '2'  and a.order_stat='0' AND A.Ticket_Count-nvl(STT.EJECT_TICKET_COUNT,0)<>0                                                          ");
		sql.append("                AND A.Order_Type in ('ZG')                                                      ");
		sql.append("                and TRUNC(A.SALE_TIME, " + flag + ") = :RPT_DT                                  ");
		sql.append("              GROUP BY E.OUTLET_ID, E.OUTLET_NAME, TRUNC(A.SALE_TIME, " + flag + ")             ");
		return sql.toString();
	}

	@Override
	public PageBean<RptDeliveryBean> getRptDeliveryBeanListPage(UserBean userBean, Date beginDt, Date endDt, Long outletId, String ticketTypeId) {
		Map<String, Object> params = new HashMap<>();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT O.OUTLET_NAME,  ");
		sb.append(" S.OUTLET_ID,           ");
		sb.append(" S.DELIVERY_DT,         ");
		sb.append(" S.SIGN_TIME,         ");
		sb.append(" S.TICKET_TYPE_NAME,    ");
		sb.append(" S.TICKET_TYPE_ID,    ");
		sb.append(" S.delivery_User_Name,    ");
		sb.append(" S.sign_User_Name,        ");
		sb.append(" S.TICKET_NUM           ");
		sb.append(" FROM SYS_OUTLET O      ");
		sb.append(" INNER JOIN  ");
		sb.append(" (SELECT A.OUTLET_ID, ");
		sb.append(" A.DELIVERY_TIME DELIVERY_DT, ");
		sb.append(" A.SIGN_TIME, ");
		sb.append(" TY.TICKET_TYPE_NAME,D.TICKET_TYPE_ID,  ");
		sb.append(" A.DELIVERY_USER_ID,   ");
		sb.append(" A.SIGN_USER_ID,       ");
		sb.append(" UD.USER_NAME  delivery_User_Name,       ");
		sb.append(" US.USER_NAME  sign_User_Name,       ");
		sb.append(" D.EXAM_NUM TICKET_NUM ");
		sb.append("  FROM STR_DELIVERY_APPLY A        ");
		sb.append(" INNER JOIN STR_DELIVERY_APPLY_DETAIL D  ");
		sb.append("    ON A.APPLY_ID = D.APPLY_ID     ");
		sb.append(" INNER JOIN SYS_TICKET_TYPE TY     ");
		sb.append("    ON D.TICKET_TYPE_ID = TY.TICKET_TYPE_ID ");
		sb.append(" INNER JOIN SYS_USER UD ");
		sb.append("    ON UD.USER_ID = A.DELIVERY_USER_ID ");
		sb.append(" INNER JOIN SYS_USER US ");
		sb.append("    ON US.USER_ID = A.SIGN_USER_ID ");
		sb.append(" WHERE 1=1 ");
		if (beginDt != null && endDt != null) {
			params.put("BGGIN_DT", beginDt);
			params.put("END_DT", endDt);
			sb.append(" AND TRUNC(A.DELIVERY_TIME, 'dd') >=:BGGIN_DT");
			sb.append(" AND TRUNC(A.DELIVERY_TIME, 'dd') <=:END_DT");
		}
		if (outletId != null) {
			params.put("OUTLET_ID", outletId);
			sb.append(" AND A.OUTLET_ID=:OUTLET_ID ");
		}
		if (StringUtil.isNotNull(ticketTypeId)) {
			params.put("TICKET_TYPE_ID", ticketTypeId);
			sb.append(" AND D.TICKET_TYPE_ID=:TICKET_TYPE_ID ");
		}
		// sb.append(" GROUP BY A.OUTLET_ID, ");
		// sb.append(" TRUNC(A.DELIVERY_TIME, 'dd'), ");
		// sb.append(" TY.TICKET_TYPE_NAME, D.TICKET_TYPE_ID,");
		// sb.append(" A.DELIVERY_USER_ID, ");
		// sb.append(" A.SIGN_USER_ID, UD.USER_NAME,US.USER_NAME) S ");
		sb.append("  ) S    ");
		sb.append("  ON O.OUTLET_ID = S.OUTLET_ID  ORDER BY S.DELIVERY_DT,S.OUTLET_ID,S.TICKET_TYPE_ID ");
		String sql = sb.toString();
		PageBean<RptDeliveryBean> retList = dbUtil.queryPageToBean("出库配送查询", sql, RptDeliveryBean.class, userBean.getPageNum(), userBean.getPageSize(), params);
		return retList;
	}

	@Override
	public Map<String, Object> saleDay(String sbeginTime, String sendTime) throws ServiceException {
		Map<String, Object> retmap = new HashMap<String, Object>();
		String beginTime = "00:00";
		String endTime = "00:00";
		if (StringUtil.isNotNull(sbeginTime) && StringUtil.isNotNull(sendTime)) {
			beginTime = DateUtil.getNearMinute(sbeginTime, INTERVALMIN, "b");
			endTime = DateUtil.getNearMinute(sendTime, INTERVALMIN, "e");
		} else {
			beginTime = DateUtil.getNearMinute(DateUtil.beforeOrAfterHour("HH:mm", 3), INTERVALMIN, "b");
			endTime = DateUtil.getNearMinute(DateUtil.getNowDate("HH:mm"), INTERVALMIN, "e");
		}
		int timeLen = (timeToNumber(endTime) - timeToNumber(beginTime)) / INTERVALMIN + 1;
		String[] times = new String[timeLen];

		Long[] nums = new Long[timeLen - 1];
		times[0] = beginTime;
		for (int i = 0; i < timeLen; i++) {
			if (i + 1 < timeLen) {
				times[i + 1] = DateUtil.addDayMinute(times[i], "HH:mm", INTERVALMIN);
			}
		}
		String[] xArrys = Arrays.copyOfRange(times, 1, timeLen);
		StringBuffer sb = new StringBuffer();
		sb.append(" select ");
		for (int j = 0; j < timeLen - 2; j++) {
			sb.append("  sum(case when  ");
			sb.append("  to_char(O.sale_time,'HH24:mi') >= ");
			sb.append("  '" + times[j] + "' and to_char(O.sale_time,'HH24:mi')<    ");
			sb.append("  '" + times[j + 1] + "' then 1 else 0 end ) as c" + j + ",  ");
		}
		sb.append("  sum(case when  ");
		sb.append("  to_char(O.sale_time,'HH24:mi') >=  ");
		sb.append("  '" + times[timeLen - 2] + "' and to_char(O.sale_time,'HH24:mi')< ");
		sb.append("  '" + times[timeLen - 1] + "' then 1 else 0 end ) as c" + (timeLen - 2) + " ");
		sb.append("  from sl_order_detail d  inner join sl_order o on  d.order_id=o.order_id  ");
		sb.append("  where trunc(O.sale_time, 'dd') = trunc(sysdate)  and o.pay_stat='2'    ");
		String sql = sb.toString();
		Map<String, Object> sqlret = dbUtil.queryFirstForMap("查询首页售票日汇总", sql);
		for (int k = 0; k < timeLen - 1; k++) {
			Object v = sqlret.get("c" + k);
			if (v != null) {
				nums[k] = Long.valueOf(sqlret.get("c" + k).toString());
			} else {
				nums[k] = 0l;
			}
		}
		retmap.put("xValues", xArrys);
		retmap.put("yValues", nums);
		return retmap;
	}

	@Override
	public Map<String, Object> checkDay(String cbeginTime, String cendTime) throws ServiceException {
		Map<String, Object> retmap = new HashMap<String, Object>();
		String beginTime = "00:00";
		String endTime = "00:00";
		if (StringUtil.isNotNull(cbeginTime) && StringUtil.isNotNull(cendTime)) {
			beginTime = DateUtil.getNearMinute(cbeginTime, INTERVALMIN, "b");
			endTime = DateUtil.getNearMinute(cendTime, INTERVALMIN, "e");
		} else {
			beginTime = DateUtil.getNearMinute(DateUtil.beforeOrAfterHour("HH:mm", 3), INTERVALMIN, "b");
			endTime = DateUtil.getNearMinute(DateUtil.getNowDate("HH:mm"), INTERVALMIN, "e");
		}
		int timeLen = (timeToNumber(endTime) - timeToNumber(beginTime)) / 15 + 1;
		String[] times = new String[timeLen];

		Long[] nums = new Long[timeLen - 1];
		times[0] = beginTime;
		for (int i = 0; i < timeLen; i++) {
			if (i + 1 < timeLen) {
				times[i + 1] = DateUtil.addDayMinute(times[i], "HH:mm", INTERVALMIN);
			}
		}
		String[] xArrys = Arrays.copyOfRange(times, 1, timeLen);
		StringBuffer sb = new StringBuffer();
		sb.append(" select ");
		for (int j = 0; j < timeLen - 2; j++) {
			sb.append("  sum(case when  ");
			sb.append("  to_char(d.ope_time,'HH24:mi') >= ");
			sb.append("  '" + times[j] + "' and to_char(d.ope_time,'HH24:mi')<    ");
			sb.append("  '" + times[j + 1] + "' then 1 else 0 end ) as c" + j + ",  ");
		}
		sb.append("  sum(case when  ");
		sb.append("  to_char(d.ope_time,'HH24:mi') >=  ");
		sb.append("  '" + times[timeLen - 2] + "' and to_char(d.ope_time,'HH24:mi')< ");
		sb.append("  '" + times[timeLen - 1] + "' then 1 else 0 end ) as c" + (timeLen - 2) + " ");
		sb.append("  from sl_check d                                      ");
		sb.append("  where trunc(d.ope_time, 'dd') = trunc(sysdate) and  d.ticket_class<>'4' and d.pass_flag='Y'   ");
		String sql = sb.toString();
		Map<String, Object> sqlret = dbUtil.queryFirstForMap("查询首页检票日汇总", sql);
		for (int k = 0; k < timeLen - 1; k++) {
			Object v = sqlret.get("c" + k);
			if (v != null) {
				nums[k] = Long.valueOf(sqlret.get("c" + k).toString());
			} else {
				nums[k] = 0l;
			}
		}
		retmap.put("xValues", xArrys);
		retmap.put("yValues", nums);
		return retmap;
	}

	private int timeToNumber(String time) throws ServiceException {
		if (StringUtil.isNull(time)) {
			return 0;
		}
		int ret = 0;
		String[] timeArray = time.split(":");
		if (timeArray == null) {
			return 0;
		} else {
			if (timeArray.length != 2) {
				throw new ServiceException(MSG.ERROR, "时间格式不正确");
			}
			ret = Integer.valueOf(timeArray[0]) * 60 + Integer.valueOf(timeArray[1]);
			return ret;
		}
	}

	@Override
	public Map<String, Object> saleMonth() {
		Map<String, Object> retmap = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select to_char(t.rpt_date,'yyyy-MM-dd') as mydate,     ");
		sb.append(" sum(t.sale_ticket_num) as mynum  ");
		sb.append(" from RPT_STRINFO_OUTLET_D t      ");
		sb.append(" where t.rpt_date >= sysdate - 30 ");
		sb.append(" and t.rpt_date < sysdate         ");
		sb.append("  group by to_char(t.rpt_date,'yyyy-MM-dd')  Order by    to_char(t.rpt_date,'yyyy-MM-dd')        ");
		String sql = sb.toString();
		List<Map<String, Object>> ret = dbUtil.queryListToMap("销售月统计", sql);
		int lenth = ret.size();
		String[] days = new String[lenth];
		Long[] nums = new Long[lenth];
		for (int i = 0; i < lenth; i++) {
			if (ret.get(i).get("mydate") != null) {
				days[i] = ret.get(i).get("mydate").toString();
			}
			if (ret.get(i).get("mynum") != null) {
				nums[i] = Long.valueOf(ret.get(i).get("mynum").toString());
			}

		}
		retmap.put("xValues", days);
		retmap.put("yValues", nums);
		return retmap;
	}

	@Override
	public Map<String, Object> checkMonth() {
		Map<String, Object> retmap = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select to_char(t.RPT_DATE,'yyyy-MM-dd') as mydate,     ");
		sb.append(" sum(CHECK_TICKET_NUM) as mynum  ");
		sb.append(" from RPT_CHECKTICKETINFO_D t      ");
		sb.append(" where t.RPT_DATE >= sysdate - 30 ");
		sb.append(" and t.RPT_DATE < sysdate        ");
		sb.append("  group by to_char(t.RPT_DATE,'yyyy-MM-dd')  Order by    to_char(t.RPT_DATE,'yyyy-MM-dd')         ");
		String sql = sb.toString();
		List<Map<String, Object>> ret = dbUtil.queryListToMap("检票月统计", sql);
		int lenth = ret.size();
		String[] days = new String[lenth];
		Long[] nums = new Long[lenth];
		for (int i = 0; i < lenth; i++) {
			if (ret.get(i).get("mydate") != null) {
				days[i] = ret.get(i).get("mydate").toString();
			}
			if (ret.get(i).get("mynum") != null) {
				nums[i] = Long.valueOf(ret.get(i).get("mynum").toString());
			}

		}
		retmap.put("xValues", days);
		retmap.put("yValues", nums);
		return retmap;

	}

	@Override
	public Map<String, Object> ticketBaseInfo(Long ticketId) throws ServiceException {
		if (ticketId == null) {
			throw new ServiceException(MSG.DB_ERROR, "请输入票号！");
		}
		Map<String, Object> slOrderDetail = new HashMap<String, Object>();
		// 是否在黑名单
		List<SysBlackList> blacks = dbUtil.queryListToBean("查询黑名单", "SELECT * FROM SYS_BLACK_LIST WHERE TICKET_ID=?", SysBlackList.class, ticketId);
		if (blacks != null && blacks.size() > 0) {
			slOrderDetail.put("backlistFlag", "是");
		} else {
			slOrderDetail.put("backlistFlag", "否");
		}
		// 查询基本信息及售票信息
		String sql = " SELECT D.TICKET_ID,  " + "        D.Ticket_Uid,        " + "        D.CHIP_ID,           " + "    E.ticket_type_Name " + "   FROM  str_ticket_info D    " + "  INNER JOIN sys_ticket_type E    " + "     ON D.ticket_type_id = E.ticket_type_id " + "WHERE  D.TICKET_ID =?";
		Map<String, Object> slOrderDetailRet = dbUtil.queryFirstForMap("查询售票信息", sql, ticketId);
		if (slOrderDetailRet != null && !slOrderDetailRet.isEmpty()) {
			slOrderDetail.putAll(slOrderDetailRet);
		}

		return slOrderDetail;
	}

	@Override
	public Map<String, Object> ticketDelInfo(Long ticketId) throws ServiceException {
		if (ticketId == null) {
			throw new ServiceException(MSG.DB_ERROR, "请输入票号！");
		}
		Map<String, Object> slOrderDetail = new HashMap<String, Object>();
		// 查询配送信息
		StringBuffer sb = new StringBuffer();
		sb.append(" select t.apply_id, ");
		sb.append("        u1.user_name    delivery_user_name,  ");
		sb.append("        to_char(a.delivery_time,'yyyy-MM-dd HH24:mi:ss') delivery_time,  ");
		sb.append("        o.outlet_name,                         ");
		sb.append("        u2.user_name    sign_user_name,      ");
		sb.append("        to_char(a.sign_time,'yyyy-MM-dd HH24:mi:ss') sign_time     ");
		sb.append("   from STR_DELIVERY_DETAIL t                ");
		sb.append("  inner join str_delivery_apply a            ");
		sb.append("     on t.apply_id = a.apply_id              ");
		sb.append("  inner join sys_user u1                     ");
		sb.append("     on a.delivery_user_id = u1.user_id      ");
		sb.append("  inner join sys_user u2                     ");
		sb.append("     on a.sign_user_id = u2.user_id          ");
		sb.append("  inner join sys_outlet o                    ");
		sb.append("     on a.outlet_id = o.outlet_id            ");
		sb.append(" where ? between t.begin_no and t.end_no ");
		String sql = sb.toString();
		slOrderDetail = dbUtil.queryFirstForMap("查询售票信息", sql, ticketId);
		return slOrderDetail;
	}

	@Override
	public Map<String, Object> ticketSaleInfo(Long ticketId) throws ServiceException {
		if (ticketId == null) {
			throw new ServiceException(MSG.DB_ERROR, "请输入票号！");
		}
		Map<String, Object> slOrderDetail = new HashMap<String, Object>();
		// 查询基本信息及售票信息
		String sql = " SELECT B.TICKET_ID,  " + "        B.OUTLET_ID,  " + "        C.OUTLET_NAME," + "        to_char(B.EJECT_TICKET_TIME,'yyyy-MM-dd HH24:mi:ss') EJECT_TICKET_TIME, " + "        CASE                 " + "          WHEN a.ORDER_TYPE IN ('XC', 'ZY', 'ST', 'BP', 'ZG') THEN " + "           b.sale_price " + "          ELSE          " + "           0            " + "        END AS SALE_AMT ," + "    su.user_name,   " + "    A.ORDER_ID,   "
				+ "  CASE WHEN B.USELESS_FLAG='Y' THEN '是' ELSE '否' END USELESS_FLAG,  " + "  TO_CHAR(B.USELESS_TIME,'YYYY-MM-DD HH24:MI:SS') USELESS_TIME " + "   FROM SL_ORDER A      " + "  INNER JOIN SL_ORDER_DETAIL B    " + "     ON A.ORDER_ID = B.ORDER_ID   " + "  INNER JOIN SYS_OUTLET C         " + "     ON B.OUTLET_ID = C.OUTLET_ID " + "  INNER JOIN sys_user su " + "     On  B.EJECT_USER_ID=su.USER_ID " + "   LEFT JOIN sl_pay_type SP       " + "     ON A.ORDER_ID = SP.ORDER_ID  "
				+ "WHERE A.pay_stat='2' AND B.TICKET_ID =?";
		slOrderDetail = dbUtil.queryFirstForMap("查询售票信息", sql, ticketId);
		return slOrderDetail;
	}

	@Override
	public List<RptCheckBean> checkInfo(Long ticketId) throws ServiceException {
		String sql = "SELECT A.CHECK_ID, " + " A.TICKET_CLASS,   " + " A.TICKET_ID,      " + " A.TICKET_UID,     " + " A.VENUE_ID,       " + " A.CLIENT_ID,      " + " A.PASS_FLAG,      " + " A.ERROR_CODE,     " + " A.NOPASS_REASON,  " + " A.REMAIN_TIMES,   " + " A.VERSION_NO,     " + "  to_char(A.OPE_TIME,'YYYY-MM-DD HH24:MI:SS') OPE_TIME,       " + " B.CLIENT_NAME,    " + " R.REGION_NAME,    " + " R.REGION_ID,    " + " S.Venue_Name      " + "  FROM SL_CHECK A  " + " INNER JOIN SYS_CLIENT B  "
				+ "    ON A.CLIENT_ID = B.CLIENT_ID " + " INNER JOIN SYS_REGION R         " + "    ON B.REGION_ID = R.REGION_ID " + " INNER JOIN sys_venue S " + "  on r.venue_id=s.venue_id" + " WHERE A.ticket_class<>'4' AND　A.TICKET_ID=?  ORDER BY  A.OPE_TIME";

		List<RptCheckBean> slChecks = dbUtil.queryListToBean("检票信息查询", sql, RptCheckBean.class, ticketId);
		return slChecks;
	}

	public PageBean<Map<String, Object>> saleDetail(UserBean userBean, Date rptDt, String beginTime, String endTime, String ticketTypeId, Long outletId, String ejectUserId) {
		Map<String, Object> params = new HashMap<>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select  ");
		sb.append(" o.order_type,         ");
		sb.append(" o.ticket_count,       ");
		sb.append(" o.due_sum,            ");
		sb.append(" o.real_sum,           ");
		sb.append(" o.remark,             ");
		sb.append(" o.pay_stat,           ");
		sb.append(" pt.pay_type,          ");
		sb.append(" dit.item_name AS ORDER_TYPE_DESC,  ");
		sb.append(" ditpay.item_name AS PAY_TYPE_DESC,  ");
		sb.append(" od.order_detail_id,   ");
		sb.append(" od.order_id,          ");
		sb.append(" od.ticket_class,      ");
		sb.append(" od.ticket_id,         ");
		sb.append(" od.ticket_uid,        ");
		sb.append(" od.identty_id,        ");
		sb.append(" od.ticket_type_id,    ");
		sb.append(" stt.ticket_type_Name,    ");
		sb.append(" od.validate_times,    ");
		sb.append(" od.original_price,    ");
		sb.append(" od.sale_price,        ");
		sb.append(" od.check_flag,        ");
		sb.append(" od.useless_flag,      ");
		sb.append(" od.outlet_id,         ");
		sb.append(" so.OUTLET_NAME,         ");
		sb.append(" od.client_id,         ");
		sb.append(" od.eject_user_id,     ");
		sb.append(" su.USER_NAME,     ");
		sb.append(" od.eject_ticket_stat, ");
		sb.append(" to_char(od.eject_ticket_time,'yyyy-MM-dd HH24:mi:ss') eject_ticket_time, ");
		sb.append(" od.useless_time  ");
		sb.append("  from sl_order o               ");
		sb.append(" inner join sl_order_detail od  ");
		sb.append("    on o.order_id = od.order_id ");
		sb.append(" inner join sl_pay_type pt      ");
		sb.append("    on o.order_id = pt.order_id ");
		sb.append("  INNER JOIN sys_dictionary dit ");
		sb.append("  on o.order_type=dit.item_val and dit.key_cd='ORDER_TYPE' ");
		sb.append("  INNER JOIN sys_dictionary ditpay  ");
		sb.append("  on PT.PAY_TYPE=ditpay.item_val and ditpay.key_cd='PAY_TYPE' ");
		sb.append("  LEFT JOIN SYS_USER su  ");
		sb.append("  on od.EJECT_USER_ID=su.USER_ID  ");
		if (StringUtil.isNotNull(ejectUserId)) {
			params.put("EJECT_USER_ID", ejectUserId);
			sb.append(" AND od.eject_user_id =:EJECT_USER_ID ");
		}
		sb.append("  INNER JOIN SYS_OUTLET so  ");
		sb.append("  on od.OUTLET_ID=so.OUTLET_ID  ");
		sb.append("  INNER JOIN SYS_TICKET_TYPE stt  ");
		sb.append("  on od.TICKET_TYPE_ID=stt.TICKET_TYPE_ID  ");
		sb.append("   WHERE  o.pay_stat='2'");
		if (rptDt != null) {
			params.put("RPT_DT", rptDt);
			sb.append(" AND trunc(od.eject_ticket_time, 'dd') =:RPT_DT ");
		}
		if (beginTime != null && endTime != null) {
			params.put("BEGIN_TIME", beginTime);
			sb.append(" and to_char(od.eject_ticket_time,'HH24:mi') >=:BEGIN_TIME ");
			params.put("END_TIME", endTime);
			sb.append(" and to_char(od.eject_ticket_time,'HH24:mi') <=:END_TIME ");
		}
		if (StringUtil.isNotNull(ticketTypeId)) {
			params.put("TICKET_TYPE_ID", ticketTypeId);
			sb.append(" AND od.TICKET_TYPE_ID =:TICKET_TYPE_ID ");
		}
		if (outletId != null) {
			params.put("OUTLET_ID", outletId);
			sb.append(" AND od.outlet_id =:OUTLET_ID ");
		}

		sb.append(" order by eject_ticket_time");
		String sql = sb.toString();
		PageBean<Map<String, Object>> retList = dbUtil.queryPageToMap("售票明细", sql, userBean.getPageNum(), userBean.getPageSize(), params);
		return retList;
	}

	public PageBean<Map<String, Object>> checkDetail(UserBean userBean, Date rptDt, String beginTime, String endTime, String ticketTypeId, Long venueId, Long regionId, Long clientId) {
		Map<String, Object> params = new HashMap<>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select c.check_id, ");
		sb.append("  decode(c.ticket_class,'1','RFID','2','身份证','3','二维码','4','员工卡','其他') ticket_class,   ");
		sb.append("  c.ticket_id,      ");
		sb.append("  c.ticket_uid,     ");
		sb.append("  stt.TICKET_TYPE_NAME,     ");
		sb.append("  c.venue_id,       ");
		sb.append("  c.client_id,      ");
		sb.append("  decode(c.pass_flag,'Y','通过','N','未通过') pass_flag, ");
		sb.append("  c.error_code,     ");
		sb.append("  c.nopass_reason,  ");
		sb.append("  decode(c.remain_times,-1,null,c.remain_times) remain_times,   ");
		sb.append("  c.version_no,     ");
		sb.append("  to_char(c.ope_time,'yyyy-MM-dd HH24:mi:ss') ope_time, ");
		sb.append("  v.venue_name,     ");
		sb.append("  sc.REGION_ID,   ");
		sb.append("  sc.client_name,   ");
		sb.append("  sr.REGION_NAME    ");
		sb.append("   from sl_check c  ");
		sb.append("  inner join SYS_TICKET_TYPE stt ");
		sb.append("     on c.TICKET_TYPE_ID = stt.TICKET_TYPE_ID    ");
		sb.append("  inner join sys_venue v ");
		sb.append("     on c.venue_id = v.venue_id    ");
		sb.append("  inner join sys_client sc         ");
		sb.append("     on c.client_id = sc.client_id ");
		sb.append("  inner join SYS_REGION sr         ");
		sb.append("     on sr.REGION_ID = sc.REGION_ID ");
		sb.append("   WHERE  1=1 ");
		if (rptDt != null) {
			params.put("RPT_DT", rptDt);
			sb.append(" AND trunc(c.ope_time, 'dd') =:RPT_DT ");
		}
		if (beginTime != null && endTime != null) {
			params.put("BEGIN_TIME", beginTime);
			sb.append(" and to_char(c.ope_time,'HH24:mi') >=:BEGIN_TIME ");
			params.put("END_TIME", endTime);
			sb.append(" and to_char(c.ope_time,'HH24:mi') <=:END_TIME ");
		}
		if (StringUtil.isNotNull(ticketTypeId)) {
			params.put("TICKET_TYPE_ID", ticketTypeId);
			sb.append(" AND c.TICKET_TYPE_ID =:TICKET_TYPE_ID ");
		}
		if (venueId != null) {
			params.put("VENUE_ID", venueId);
			sb.append(" AND c.venue_id =:VENUE_ID ");
		}
		if (regionId != null) {
			params.put("REGION_ID", regionId);
			sb.append(" AND sc.REGION_ID =:REGION_ID ");
		}
		if (clientId != null) {
			params.put("CLIENT_ID", clientId);
			sb.append(" AND c.CLIENT_ID =:CLIENT_ID ");
		}
		sb.append(" order by ope_time,c.venue_id,sc.REGION_ID");
		String sql = sb.toString();
		PageBean<Map<String, Object>> retList = dbUtil.queryPageToMap("检票明细", sql, userBean.getPageNum(), userBean.getPageSize(), params);
		return retList;
	}

	@Override
	public List<CustFlowDayBean> listEmpInDay(UserBean loginUserBean, Date startDate, Date endDate, String venueId, String regionId, String empId) throws BaseException {
		StringBuffer sb = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		sb.append(" SELECT RPT_DATE,                                    ");
		sb.append("        VENUE_NAME,                                  ");
		sb.append("        REGION_NAME,                                 ");
		sb.append("        EMP_ID,                                      ");
		sb.append("        EMP_NAME,                                    ");
		sb.append("        CHECK_TICKET_NUM                             ");
		sb.append("   FROM (SELECT TRUNC(C.OPE_TIME, 'dd') RPT_DATE,    ");
		sb.append("          V.VENUE_NAME,                              ");
		sb.append("          R.REGION_NAME,                             ");
		sb.append("          EMP.EMP_ID,                                ");
		sb.append("          EMP.EMP_NAME,                              ");
		sb.append("          1 ORDER_NUM,                               ");
		sb.append("          SUM(1) CHECK_TICKET_NUM                    ");
		sb.append("     FROM SL_CHECK C                                 ");
		sb.append("    INNER JOIN SYS_CLIENT CLIENT                     ");
		sb.append("       ON C.CLIENT_ID = CLIENT.CLIENT_ID             ");
		sb.append("    INNER JOIN SYS_VENUE V                           ");
		sb.append("       ON C.VENUE_ID = V.VENUE_ID                    ");
		sb.append("    INNER JOIN SYS_REGION R                          ");
		sb.append("       ON CLIENT.REGION_ID = R.REGION_ID             ");
		sb.append("     INNER JOIN SYS_EMP_REGISTER EMP                  ");
		sb.append("       ON C.TICKET_ID = EMP.EMP_ID                   ");
		sb.append("    WHERE C.PASS_FLAG = 'Y'                          ");
		sb.append("      AND C.TICKET_CLASS = '4'                      ");
		// 统计日期
		if (startDate != null && endDate != null) {
			sb.append("   AND TRUNC(C.OPE_TIME, 'dd') BETWEEN :STARTDATE ");
			sb.append("   AND :ENDDATE ");
			params.put("STARTDATE", startDate);
			params.put("ENDDATE", endDate);
		}
		// 场馆编号
		if (StringUtil.isNotNull(venueId)) {
			if (venueId.contains("-1")) {
				venueId = venueId.substring(venueId.indexOf("-1,") + 1);
			}
			if (venueId.contains(",")) {
				sb.append("   AND C.VENUE_ID in (" + venueId + ")");
			} else {
				sb.append("   AND C.VENUE_ID = :VENUE_ID ");
				params.put("VENUE_ID", venueId);
			}
		}
		// 区域编号
		if (StringUtil.isNotNull(regionId)) {
			if (regionId.contains("-1")) {
				regionId = regionId.substring(regionId.indexOf("-1,") + 1);
			}
			if (regionId.contains(",")) {
				sb.append("   AND R.REGION_ID in (" + regionId + ")");
			} else {
				sb.append("   AND R.REGION_ID = :REGION_ID");
				params.put("REGION_ID", regionId);
			}
		}
		// 员工编号
		if (StringUtil.isNotNull(empId)) {
			if (empId.contains("-1")) {
				empId = empId.substring(empId.indexOf("-1,") + 1);
			}
			if (empId.contains(",")) {
				sb.append("   AND EMP.EMP_ID in (" + empId + ")");
			} else {
				sb.append("   AND EMP.EMP_ID = :EMP_ID");
				params.put("EMP_ID", empId);
			}
		}
		sb.append("    GROUP BY TRUNC(C.OPE_TIME, 'dd'),                ");
		sb.append("             V.VENUE_NAME,                           ");
		sb.append("             R.REGION_NAME,                          ");
		sb.append("             EMP.EMP_ID,                             ");
		sb.append("             EMP.EMP_NAME                            ");
		sb.append("   UNION                                             ");
		sb.append("   SELECT RPT_DATE,                                  ");
		sb.append("          VENUE_NAME,                                ");
		sb.append("          '小计',                                    ");
		sb.append("          NULL,                                      ");
		sb.append("          NULL,                                      ");
		sb.append("          2 ORDER_NUM,                               ");
		sb.append("          CHECK_TICKET_NUM                           ");
		sb.append("     FROM (SELECT TRUNC(C.OPE_TIME, 'dd') RPT_DATE,  ");
		sb.append("          V.VENUE_NAME,                              ");
		sb.append("          SUM(1) CHECK_TICKET_NUM                    ");
		sb.append("     FROM SL_CHECK C                                 ");
		sb.append("    INNER JOIN SYS_CLIENT CLIENT                     ");
		sb.append("       ON C.CLIENT_ID = CLIENT.CLIENT_ID             ");
		sb.append("    INNER JOIN SYS_VENUE V                           ");
		sb.append("       ON C.VENUE_ID = V.VENUE_ID                    ");
		sb.append("    INNER JOIN SYS_CLIENT CLIENT                     ");
		sb.append("       ON C.CLIENT_ID = CLIENT.CLIENT_ID             ");
		sb.append("    INNER JOIN SYS_VENUE V                           ");
		sb.append("       ON C.VENUE_ID = V.VENUE_ID                    ");
		sb.append("    INNER JOIN SYS_REGION R                          ");
		sb.append("       ON CLIENT.REGION_ID = R.REGION_ID             ");
		sb.append("     LEFT JOIN SYS_EMP_REGISTER EMP                  ");
		sb.append("       ON C.TICKET_ID = EMP.EMP_ID                   ");
		sb.append("    WHERE C.PASS_FLAG = 'Y'                          ");
		sb.append("      AND C.TICKET_CLASS = '4'                      ");
		// 统计日期
		if (startDate != null && endDate != null) {
			sb.append("   AND TRUNC(C.OPE_TIME, 'dd') BETWEEN :STARTDATE ");
			sb.append("   AND :ENDDATE ");
			params.put("STARTDATE", startDate);
			params.put("ENDDATE", endDate);
		}
		// 场馆编号
		if (StringUtil.isNotNull(venueId)) {
			if (venueId.contains("-1")) {
				venueId = venueId.substring(venueId.indexOf("-1,") + 1);
			}
			if (venueId.contains(",")) {
				sb.append("   AND C.VENUE_ID in (" + venueId + ")");
			} else {
				sb.append("   AND C.VENUE_ID = :VENUE_ID ");
				params.put("VENUE_ID", venueId);
			}
		}
		// 区域编号
		if (StringUtil.isNotNull(regionId)) {
			if (regionId.contains("-1")) {
				regionId = regionId.substring(regionId.indexOf("-1,") + 1);
			}
			if (regionId.contains(",")) {
				sb.append("   AND R.REGION_ID in (" + regionId + ")");
			} else {
				sb.append("   AND R.REGION_ID = :REGION_ID");
				params.put("REGION_ID", regionId);
			}
		}
		// 员工编号
		if (StringUtil.isNotNull(empId)) {
			if (empId.contains("-1")) {
				empId = empId.substring(empId.indexOf("-1,") + 1);
			}
			if (empId.contains(",")) {
				sb.append("   AND EMP.EMP_ID in (" + empId + ")");
			} else {
				sb.append("   AND EMP.EMP_ID = :EMP_ID");
				params.put("EMP_ID", empId);
			}
		}
		sb.append("    GROUP BY TRUNC(C.OPE_TIME, 'dd'), V.VENUE_NAME)  ");
		sb.append("   UNION                                             ");
		sb.append("   SELECT RPT_DATE,                                  ");
		sb.append("          '合计',                                    ");
		sb.append("          NULL,                                      ");
		sb.append("          NULL,                                      ");
		sb.append("          NULL,                                      ");
		sb.append("          3 ORDER_NUM,                               ");
		sb.append("          CHECK_TICKET_NUM                           ");
		sb.append("     FROM (SELECT TRUNC(C.OPE_TIME, 'dd') RPT_DATE,  ");
		sb.append("     SUM(1) CHECK_TICKET_NUM                         ");
		sb.append("    FROM SL_CHECK C                                  ");
		sb.append("   INNER JOIN SYS_CLIENT CLIENT                      ");
		sb.append("      ON C.CLIENT_ID = CLIENT.CLIENT_ID              ");
		sb.append("   INNER JOIN SYS_VENUE V                            ");
		sb.append("      ON C.VENUE_ID = V.VENUE_ID                     ");
		sb.append("   INNER JOIN SYS_REGION R                           ");
		sb.append("      ON CLIENT.REGION_ID = R.REGION_ID              ");
		sb.append("    LEFT JOIN SYS_EMP_REGISTER EMP                   ");
		sb.append("      ON C.TICKET_ID = EMP.EMP_ID                    ");
		sb.append("   WHERE C.PASS_FLAG = 'Y'                           ");
		sb.append("     AND C.TICKET_CLASS = '4'                       ");
		// 统计日期
		if (startDate != null && endDate != null) {
			sb.append("   AND TRUNC(C.OPE_TIME, 'dd') BETWEEN :STARTDATE ");
			sb.append("   AND :ENDDATE ");
			params.put("STARTDATE", startDate);
			params.put("ENDDATE", endDate);
		}
		// 场馆编号
		if (StringUtil.isNotNull(venueId)) {
			if (venueId.contains("-1")) {
				venueId = venueId.substring(venueId.indexOf("-1,") + 1);
			}
			if (venueId.contains(",")) {
				sb.append("   AND C.VENUE_ID in (" + venueId + ")");
			} else {
				sb.append("   AND C.VENUE_ID = :VENUE_ID ");
				params.put("VENUE_ID", venueId);
			}
		}
		// 区域编号
		if (StringUtil.isNotNull(regionId)) {
			if (regionId.contains("-1")) {
				regionId = regionId.substring(regionId.indexOf("-1,") + 1);
			}
			if (regionId.contains(",")) {
				sb.append("   AND R.REGION_ID in (" + regionId + ")");
			} else {
				sb.append("   AND R.REGION_ID = :REGION_ID");
				params.put("REGION_ID", regionId);
			}
		}
		// 员工编号
		if (StringUtil.isNotNull(empId)) {
			if (empId.contains("-1")) {
				empId = empId.substring(empId.indexOf("-1,") + 1);
			}
			if (empId.contains(",")) {
				sb.append("   AND EMP.EMP_ID in (" + empId + ")");
			} else {
				sb.append("   AND EMP.EMP_ID = :EMP_ID");
				params.put("EMP_ID", empId);
			}
		}
		sb.append("   GROUP BY TRUNC(C.OPE_TIME, 'dd'))) U              ");
		sb.append("  ORDER BY U.RPT_DATE, U.ORDER_NUM                   ");

		List<CustFlowDayBean> list = dbUtil.queryListToBean("员工入园日统计", sb.toString(), CustFlowDayBean.class, params);
		CustFlowDayBean totalBean = new CustFlowDayBean();
		totalBean.setVenueName("总计");
		totalBean.setCheckTicketNum(new Long(0));
		for (CustFlowDayBean bean : list) {
			if (!"小计".equals(bean.getRegionName()) && !"合计".equals(bean.getVenueName())) {
				totalBean.setCheckTicketNum(totalBean.getCheckTicketNum() + bean.getCheckTicketNum());
			} else {
				continue;
			}
		}
		list.add(totalBean);
		return list;
	}

	@Override
	public List<Map<String, Object>> listSaleChange(Date startDate, Date endDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("STARTDATE", startDate);
		params.put("ENDDATE", endDate);
		StringBuffer sb = new StringBuffer();
		sb.append(" select sale_time, outlet_name,c9,c10,c11,c12,c13,c14,c15,c16,c17,c18 from (    ");
		sb.append(" select to_char(o.sale_time, 'yyyy-MM-dd') sale_time,                           ");
		sb.append("        ol.outlet_name, d.outlet_id,1 ordernum,                                 ");
		sb.append("        sum(case                                                                ");
		sb.append("              when to_char(o.sale_time, 'HH24:mi') < '09:00' then               ");
		sb.append("               1                                                                ");
		sb.append("              else                                                              ");
		sb.append("               0                                                                ");
		sb.append("            end) as c9,                                                         ");
		sb.append("        sum(case                                                                ");
		sb.append("              when to_char(o.sale_time, 'HH24:mi') >= '09:00' and               ");
		sb.append("                   to_char(o.sale_time, 'HH24:mi') < '10:00' then               ");
		sb.append("               1                                                                ");
		sb.append("              else                                                              ");
		sb.append("               0                                                                ");
		sb.append("            end) as c10,                                                        ");
		sb.append("        sum(case                                                                ");
		sb.append("              when to_char(o.sale_time, 'HH24:mi') >= '10:00' and               ");
		sb.append("                   to_char(o.sale_time, 'HH24:mi') < '11:00' then               ");
		sb.append("               1                                                                ");
		sb.append("              else                                                              ");
		sb.append("               0                                                                ");
		sb.append("            end) as c11,                                                        ");
		sb.append("        sum(case                                                                ");
		sb.append("              when to_char(o.sale_time, 'HH24:mi') >= '11:00' and               ");
		sb.append("                   to_char(o.sale_time, 'HH24:mi') < '12:00' then               ");
		sb.append("               1                                                                ");
		sb.append("              else                                                              ");
		sb.append("               0                                                                ");
		sb.append("            end) as c12,                                                        ");
		sb.append("        sum(case                                                                ");
		sb.append("              when to_char(o.sale_time, 'HH24:mi') >= '12:00' and               ");
		sb.append("                   to_char(o.sale_time, 'HH24:mi') < '13:00' then               ");
		sb.append("               1                                                                ");
		sb.append("              else                                                              ");
		sb.append("               0                                                                ");
		sb.append("            end) as c13,                                                        ");
		sb.append("        sum(case                                                                ");
		sb.append("              when to_char(o.sale_time, 'HH24:mi') >= '13:00' and               ");
		sb.append("                   to_char(o.sale_time, 'HH24:mi') < '14:00' then               ");
		sb.append("               1                                                                ");
		sb.append("              else                                                              ");
		sb.append("               0                                                                ");
		sb.append("            end) as c14,                                                        ");
		sb.append("        sum(case                                                                ");
		sb.append("              when to_char(o.sale_time, 'HH24:mi') >= '14:00' and               ");
		sb.append("                   to_char(o.sale_time, 'HH24:mi') < '15:00' then               ");
		sb.append("               1                                                                ");
		sb.append("              else                                                              ");
		sb.append("               0                                                                ");
		sb.append("            end) as c15,                                                        ");
		sb.append("        sum(case                                                                ");
		sb.append("              when to_char(o.sale_time, 'HH24:mi') >= '15:00' and               ");
		sb.append("                   to_char(o.sale_time, 'HH24:mi') < '16:00' then               ");
		sb.append("               1                                                                ");
		sb.append("              else                                                              ");
		sb.append("               0                                                                ");
		sb.append("            end) as c16,                                                        ");
		sb.append("        sum(case                                                                ");
		sb.append("              when to_char(o.sale_time, 'HH24:mi') >= '16:00' and               ");
		sb.append("                   to_char(o.sale_time, 'HH24:mi') < '17:00' then               ");
		sb.append("               1                                                                ");
		sb.append("              else                                                              ");
		sb.append("               0                                                                ");
		sb.append("            end) as c17,                                                        ");
		sb.append("        sum(case                                                                ");
		sb.append("              when to_char(o.sale_time, 'HH24:mi') >= '17:00' then              ");
		sb.append("               1                                                                ");
		sb.append("              else                                                              ");
		sb.append("               0                                                                ");
		sb.append("            end) as c18                                                         ");
		sb.append("   from sl_order o                                                              ");
		sb.append("  inner join sl_order_detail d                                                  ");
		sb.append("     on o.order_id = d.order_id                                                 ");
		sb.append("  inner join sys_outlet ol                                                      ");
		sb.append("     on d.outlet_id = ol.outlet_id                                              ");
		sb.append("  where trunc(o.sale_time, 'dd') between :STARTDATE AND :ENDDATE  ");
		sb.append("  group by to_char(o.sale_time, 'yyyy-MM-dd'), d.outlet_id, ol.outlet_name      ");
		sb.append(" union                                                                          ");
		sb.append(" select sale_time, '总计' outlet_name,null outlet_id,2 ordernum,c9,c10,c11,c12,c13,c14,c15,c16,c17,c18   ");
		sb.append("   from (select to_char(o.sale_time, 'yyyy-MM-dd') sale_time,                            ");
		sb.append("                sum(case                                                                 ");
		sb.append("                      when to_char(o.sale_time, 'HH24:mi') < '09:00' then                ");
		sb.append("                       1                                                                 ");
		sb.append("                      else                                                               ");
		sb.append("                       0                                                                 ");
		sb.append("                    end) as c9,                                                          ");
		sb.append("                sum(case                                                                 ");
		sb.append("                      when to_char(o.sale_time, 'HH24:mi') >= '09:00' and                ");
		sb.append("                           to_char(o.sale_time, 'HH24:mi') < '10:00' then                ");
		sb.append("                       1                                                                 ");
		sb.append("                      else                                                               ");
		sb.append("                       0                                                                 ");
		sb.append("                    end) as c10,                                                         ");
		sb.append("                sum(case                                                                 ");
		sb.append("                      when to_char(o.sale_time, 'HH24:mi') >= '10:00' and                ");
		sb.append("                           to_char(o.sale_time, 'HH24:mi') < '11:00' then                ");
		sb.append("                       1                                                                 ");
		sb.append("                      else                                                               ");
		sb.append("                       0                                                                 ");
		sb.append("                    end) as c11,                                                         ");
		sb.append("                sum(case                                                                 ");
		sb.append("                      when to_char(o.sale_time, 'HH24:mi') >= '11:00' and                ");
		sb.append("                           to_char(o.sale_time, 'HH24:mi') < '12:00' then                ");
		sb.append("                       1                                                                 ");
		sb.append("                      else                                                               ");
		sb.append("                       0                                                                 ");
		sb.append("                    end) as c12,                                                         ");
		sb.append("                sum(case                                                                 ");
		sb.append("                      when to_char(o.sale_time, 'HH24:mi') >= '12:00' and                ");
		sb.append("                           to_char(o.sale_time, 'HH24:mi') < '13:00' then                ");
		sb.append("                       1                                                                 ");
		sb.append("                      else                                                               ");
		sb.append("                       0                                                                 ");
		sb.append("                    end) as c13,                                                         ");
		sb.append("                sum(case                                                                 ");
		sb.append("                      when to_char(o.sale_time, 'HH24:mi') >= '13:00' and                ");
		sb.append("                           to_char(o.sale_time, 'HH24:mi') < '14:00' then                ");
		sb.append("                       1                                                                 ");
		sb.append("                      else                                                               ");
		sb.append("                       0                                                                 ");
		sb.append("                    end) as c14,                                                         ");
		sb.append("                sum(case                                                                 ");
		sb.append("                      when to_char(o.sale_time, 'HH24:mi') >= '14:00' and                ");
		sb.append("                           to_char(o.sale_time, 'HH24:mi') < '15:00' then                ");
		sb.append("                       1                                                                 ");
		sb.append("                      else                                                               ");
		sb.append("                       0                                                                 ");
		sb.append("                    end) as c15,                                                         ");
		sb.append("                sum(case                                                                 ");
		sb.append("                      when to_char(o.sale_time, 'HH24:mi') >= '15:00' and                ");
		sb.append("                           to_char(o.sale_time, 'HH24:mi') < '16:00' then                ");
		sb.append("                       1                                                                 ");
		sb.append("                      else                                                               ");
		sb.append("                       0                                                                 ");
		sb.append("                    end) as c16,                                                         ");
		sb.append("                sum(case                                                                 ");
		sb.append("                      when to_char(o.sale_time, 'HH24:mi') >= '16:00' and                ");
		sb.append("                           to_char(o.sale_time, 'HH24:mi') < '17:00' then                ");
		sb.append("                       1                                                                 ");
		sb.append("                      else                                                               ");
		sb.append("                       0                                                                 ");
		sb.append("                    end) as c17,                                                         ");
		sb.append("                sum(case                                                                 ");
		sb.append("                      when to_char(o.sale_time, 'HH24:mi') >= '17:00' then               ");
		sb.append("                       1                                                                 ");
		sb.append("                      else                                                               ");
		sb.append("                       0                                                                 ");
		sb.append("                    end) as c18                                                          ");
		sb.append("           from sl_order o                                                               ");
		sb.append("          inner join sl_order_detail d                                                   ");
		sb.append("             on o.order_id = d.order_id                                                  ");
		sb.append("          inner join sys_outlet ol                                                       ");
		sb.append("             on d.outlet_id = ol.outlet_id                                               ");
		sb.append("          where trunc(o.sale_time, 'dd') between :STARTDATE AND :ENDDATE   ");
		sb.append("          group by to_char(o.sale_time, 'yyyy-MM-dd')                                    ");
		sb.append(" ) sumt)                                                                                 ");
		sb.append(" order by sale_time,ordernum,outlet_id                                                   ");
		sb.append("                                                                                         ");
		String sql = sb.toString();
		List<Map<String, Object>> ret = dbUtil.queryListToMap("查询售换票信息", sql, params);
		return ret;
	}

	@Override
	public List<TicketCheckByIdenttyBean> listTicketCheckByIdentty(String identty_id) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT SC.CHECK_ID, sod.order_id,             ");
		sql.append("        SC.TICKET_UID,                         ");
		sql.append("        to_char(SC.VENUE_ID) VENUE_ID,         ");
		sql.append("        SV.VENUE_NAME,                         ");
		sql.append("        to_char(SC.CLIENT_ID) CLIENT_ID,       ");
		sql.append("        SCT.CLIENT_NAME,                       ");
		sql.append("     CASE WHEN SC.PASS_FLAG='Y' THEN '通过' ELSE '未通过' END PASS_FLAG, ");
		sql.append("        SC.NOPASS_REASON,                      ");
		sql.append("        to_char(SC.REMAIN_TIMES) REMAIN_TIMES,                       ");
		sql.append("        SC.TICKET_TYPE_ID,                     ");
		sql.append("        STT.TICKET_TYPE_NAME,                  ");
		sql.append("      to_char(SC.OPE_TIME, 'YYYY-MM-DD HH24:MI:SS') OPE_TIME  ");
		sql.append("   FROM SL_CHECK SC                            ");
		sql.append("   LEFT JOIN SYS_VENUE SV                      ");
		sql.append("     ON SC.VENUE_ID = SV.VENUE_ID              ");
		sql.append("   LEFT JOIN SYS_CLIENT SCT                    ");
		sql.append("     ON SC.CLIENT_ID = SCT.CLIENT_ID           ");
		sql.append("   LEFT JOIN SYS_TICKET_TYPE STT               ");
		sql.append("     ON SC.TICKET_TYPE_ID = STT.TICKET_TYPE_ID ");
		
		sql.append("   LEFT JOIN sl_order_detail sod               ");
		sql.append("     on SC.order_detail_id=sod.order_detail_id ");
		
		sql.append("    WHERE SC.TICKET_UID=?");
		sql.append("     ORDER BY SC.OPE_TIME DESC                 ");

		return dbUtil.queryListToBean("", sql.toString(), TicketCheckByIdenttyBean.class, identty_id);
	}

	@Override
	public List<TicketSaleByIdenttyBean> listTicketSaleByIdentty(String identty_id) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select sod.order_detail_id, sod.order_id,                      ");
		sql.append("        sod.identty_id,                                         ");
		sql.append("        sod.ticket_type_id,                                     ");
		sql.append("        stt.ticket_type_name,                                   ");
		sql.append("        to_char(so.sale_time,'YYYY-MM-DD HH24:MI:SS') sale_time, ");
		sql.append("        case                                                    ");
		sql.append("          when sod.check_flag = 'Y' then                        ");
		sql.append("           '已检票'                                             ");
		sql.append("          else                                                  ");
		sql.append("           '未检票'                                             ");
		sql.append("        end check_flag,                                         ");
		sql.append("        nvl(to_char(sc.ope_time,'YYYY-MM-DD HH24:MI:SS'),'') check_Ticket_Time,                                         ");
		sql.append("        case                                                    ");
		sql.append("          when spt.pay_type = '01' then                         ");
		sql.append("           '现金'                                               ");
		sql.append("          when spt.pay_type = '02' then                         ");
		sql.append("           'POS付款'                                            ");
		sql.append("          when spt.pay_type = '03' then                         ");
		sql.append("           '微信'                                               ");
		sql.append("          when spt.pay_type = '04' then                         ");
		sql.append("           '支付宝'                                             ");
		sql.append("          when spt.pay_type = '05' then                         ");
		sql.append("           '代理'                                               ");
		sql.append("        end pay_type,                                           ");
		sql.append("        to_char(sod.valid_start_date,'YYYY-MM-DD') valid_start_date, ");
		sql.append("        to_char(sod.valid_end_date,'YYYY-MM-DD') valid_end_date, ");
		sql.append("        case                                                    ");
		sql.append("          when sod.eject_ticket_stat = '2' then                 ");
		sql.append("           '已取票'                                             ");
		sql.append("          else                                                  ");
		sql.append("           '未取票'                                             ");
		sql.append("        end eject_ticket_stat,                                  ");
		sql.append("        to_char(sod.eject_ticket_time, 'YYYY-MM-DD HH24:MI:SS') eject_ticket_time,");
		sql.append("        CASE WHEN SOD.USELESS_FLAG='T' THEN '是' ELSE '否' END USELESS_FLAG,  ");
		sql.append("        TO_CHAR(USELESS_TIME,'YYYY-MM-DD HH24:MI:SS') USELESS_TIME  ");
		sql.append("   from sl_order_detail sod                                     ");
		sql.append("   left join sys_ticket_type stt                                ");
		sql.append("     on sod.ticket_type_id = stt.ticket_type_id                 ");
		sql.append("   left join sl_order so                                        ");
		sql.append("     on sod.order_id = so.order_id                              ");
		sql.append("   left join sl_pay_type spt                                    ");
		sql.append("     on so.order_id = spt.order_id                              ");
		sql.append("   left join (                                   ");
		sql.append("         select a.order_detail_id,max(b.ope_time) ope_time from sl_order_detail a,sl_check b where ");
		sql.append("          a.order_detail_id=b.order_detail_id and a.identty_id=? group by a.order_detail_id ");
		sql.append("  ) sc ");
		sql.append("     on sc.order_detail_id = sod.order_detail_id                ");
		sql.append("    WHERE sod.identty_id=?");
		sql.append("  order by so.sale_time desc                                    ");

		return dbUtil.queryListToBean("", sql.toString(), TicketSaleByIdenttyBean.class, identty_id, identty_id);
	}

	@Override
	public List<RptOutletSale1Bean> getRptSelfSaleBeanListPage(Date startDate, Date endDate, String ticketTypeId) {
		Map<String, Object> params = new HashMap<>();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT 	RPT_DT,TICKET_TYPE_NAME,TICKET_CLASS_NAME, SALE_SUM,SALE_AMT from (   ");
		sb.append(" SELECT 	     ");
		sb.append("          TRUNC(O.sale_time,'dd') RPT_DT,     ");
		sb.append("          DE.TICKET_TYPE_ID,TY.TICKET_TYPE_NAME,1 ORDER_NUM ,    ");
		sb.append("          case DE.ticket_class when '1' then '实体票' when '2' then '身份证' when '3' then '二维码' end TICKET_CLASS_NAME,    ");
	
		sb.append("          SUM(1) AS SALE_SUM,                ");
		sb.append("          SUM( DE.sale_price ) AS SALE_AMT            ");
		sb.append("     FROM SL_ORDER O                         ");
		sb.append("     INNER JOIN sys_dictionary dit            ");
		sb.append("     on o.order_type=dit.item_val and dit.key_cd='ORDER_TYPE' ");
		sb.append("    INNER JOIN SL_ORDER_DETAIL DE  ");
		sb.append("    ON O.ORDER_ID=DE.ORDER_ID      ");
		sb.append("    INNER JOIN SYS_TICKET_TYPE TY  ");
		sb.append("    ON DE.TICKET_TYPE_ID=TY.TICKET_TYPE_ID where pay_stat='2' and o.order_type='ZY' ");
		if (startDate != null && endDate != null) {
			params.put("STARTDATE", startDate);
			params.put("ENDDATE", endDate);
			sb.append(" and   trunc(o.sale_time, 'dd') between :STARTDATE AND :ENDDATE");
		}
		if (ticketTypeId != null) {
			params.put("TICKET_TYPE_ID", ticketTypeId);
			sb.append(" and   de.TICKET_TYPE_ID=:TICKET_TYPE_ID ");
		}
		sb.append("    GROUP BY ");
		sb.append("   TRUNC(O.sale_time,'dd'), DE.TICKET_TYPE_ID,");
		sb.append("   TY.TICKET_TYPE_NAME,DE.ticket_class    ");
		sb.append(" UNION                                        ");
		sb.append("  SELECT TRUNC(O.sale_time, 'dd') RPT_DT, null TICKET_TYPE_ID,     ");
		sb.append("        '小计' AS TICKET_TYPE_NAME,         ");
		sb.append("        2 ORDER_NUM, null TICKET_CLASS_NAME ,  ");
		sb.append("        SUM(1) AS SALE_SUM,                   ");
		sb.append("        SUM( DE.sale_price) AS SALE_AMT               ");
		sb.append("   FROM SL_ORDER O                            ");
		sb.append("  INNER JOIN SL_ORDER_DETAIL DE               ");
		sb.append("     ON O.ORDER_ID = DE.ORDER_ID              ");
		sb.append("  INNER JOIN SYS_TICKET_TYPE TY               ");
		sb.append("     ON DE.TICKET_TYPE_ID = TY.TICKET_TYPE_ID  where pay_stat='2'  and o.order_type='ZY' ");
		if (startDate != null && endDate != null) {
			params.put("STARTDATE", startDate);
			params.put("ENDDATE", endDate);
			sb.append(" and   trunc(o.sale_time, 'dd') between :STARTDATE AND :ENDDATE");
		}
		if (ticketTypeId != null) {
			params.put("TICKET_TYPE_ID", ticketTypeId);
			sb.append(" and   de.TICKET_TYPE_ID=:TICKET_TYPE_ID ");
		}
		sb.append("  GROUP BY TRUNC(O.sale_time, 'dd')         ");
		sb.append("           )T                  ");

		sb.append("  ORDER BY RPT_DT , order_num,TICKET_TYPE_ID,TICKET_CLASS_NAME");
		String sql = sb.toString();
		List<RptOutletSale1Bean> outletSaleList = dbUtil.queryListToBean("网点销售统计表", sql, RptOutletSale1Bean.class, params);
		return outletSaleList;
	}

	@Override
	public List<TicketNoCheckBean> ticketNoCheck(UserBean loginUserBean) throws BaseException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT W.OUTLET_ID, W.OUTLET_NAME, W.ITEM_CD, W.ITEM_NAME, W.TICKET_TYPE_NAME, W.TICKET_NUM ");
		sql.append("		  FROM (SELECT OD.OUTLET_ID,                                                ");
		sql.append("		               OT.OUTLET_NAME,                                              ");
		sql.append("		               D.ITEM_CD,                                                   ");
		sql.append("		               D.ITEM_NAME,                                                 ");
		sql.append("		               TT.TICKET_TYPE_NAME,                                         ");
		sql.append("		               SUM(1) TICKET_NUM                                            ");
		sql.append("		          FROM SL_ORDER O                                                   ");
		sql.append("		         INNER JOIN SL_ORDER_DETAIL OD                                      ");
		sql.append("		            ON O.ORDER_ID = OD.ORDER_ID                                     ");
		sql.append("		         INNER JOIN SYS_TICKET_TYPE TT                                      ");
		sql.append("		            ON OD.TICKET_TYPE_ID = TT.TICKET_TYPE_ID                        ");
		sql.append("		         INNER JOIN SYS_OUTLET OT                                           ");
		sql.append("		            ON OD.OUTLET_ID = OT.OUTLET_ID                                  ");
		sql.append("		         INNER JOIN SYS_DICTIONARY D                                        ");
		sql.append("		            ON O.ORDER_TYPE = D.ITEM_CD                                     ");
		sql.append("		           AND D.KEY_CD = 'ORDER_TYPE'                                      ");
		sql.append("		         WHERE OD.CHECK_FLAG = 'N' AND OD.USELESS_FLAG='N'                         ");
		sql.append("		           AND O.ORDER_TYPE <> 'ZQ' and (OD.VALID_END_DATE is null or OD.VALID_END_DATE>sysdate)     ");
		sql.append("		         GROUP BY OD.OUTLET_ID, OT.OUTLET_NAME, D.ITEM_CD, D.ITEM_NAME,TT.TICKET_TYPE_NAME      ");
		sql.append("		        UNION                                                               ");
		sql.append("		        SELECT OD.OUTLET_ID,                                                ");
		sql.append("		               '网络购票' OUTLET_NAME,                                         ");
		sql.append("		               D.ITEM_CD,                                                   ");
		sql.append("		               D.ITEM_NAME,                                                 ");
		sql.append("		               TT.TICKET_TYPE_NAME,                                         ");
		sql.append("		               SUM(1) TICKET_NUM                                            ");
		sql.append("		          FROM SL_ORDER O                                                   ");
		sql.append("		         INNER JOIN SL_ORDER_DETAIL OD                                      ");
		sql.append("		            ON O.ORDER_ID = OD.ORDER_ID                                     ");
		sql.append("		         INNER JOIN SYS_TICKET_TYPE TT                                      ");
		sql.append("		            ON OD.TICKET_TYPE_ID = TT.TICKET_TYPE_ID                        ");
		sql.append("		         INNER JOIN SYS_DICTIONARY D                                        ");
		sql.append("		            ON O.ORDER_TYPE = D.ITEM_CD                                     ");
		sql.append("		           AND D.KEY_CD = 'ORDER_TYPE'                                      ");
		sql.append("		         WHERE OD.CHECK_FLAG = 'N'  AND OD.USELESS_FLAG='N'                           ");
		sql.append("		           AND O.ORDER_TYPE = 'ZQ' and (OD.VALID_END_DATE is null or OD.VALID_END_DATE>sysdate)  ");
		sql.append("		         GROUP BY OD.OUTLET_ID, D.ITEM_CD, D.ITEM_NAME,TT.TICKET_TYPE_NAME) W   ");
		sql.append("		 ORDER BY W.OUTLET_ID, W.OUTLET_NAME, W.ITEM_CD, W.TICKET_TYPE_NAME, W.ITEM_NAME   ");

		List<TicketNoCheckBean> list = dbUtil.queryListToBean("查询门票未入园记录", sql.toString(), TicketNoCheckBean.class);
		return list;
	}

	@Override
	public PageBean<Map<String, Object>> wlSaleDetail(UserBean loginUserBean, Date rptDt, String ticketTypeId, String orgId, String orderId) throws BaseException {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select o.order_type,                                                              ");
		sql.append("       o.ticket_count,                                                            ");
		sql.append("       o.due_sum,                                                                 ");
		sql.append("       o.real_sum,                                                                ");
		sql.append("       o.remark,                                                                  ");
		sql.append("       o.pay_stat,                                                                ");
		sql.append("       sno.org_id,                                                                ");
		sql.append("       org.org_name,                                                              ");
		sql.append("       pt.pay_type,                                                               ");
		sql.append("       dit.item_name as order_type_desc,                                          ");
		sql.append("       ditpay.item_name as pay_type_desc,                                         ");
		sql.append("       od.order_detail_id,                                                        ");
		sql.append("       od.order_id,                                                               ");
		sql.append("       od.ticket_class,                                                           ");
		sql.append("       od.ticket_id,                                                              ");
		sql.append("       od.ticket_uid,                                                             ");
		sql.append("       od.identty_id,                                                             ");
		sql.append("       od.ticket_type_id,                                                         ");
		sql.append("       stt.ticket_type_Name,                                                      ");
		sql.append("       od.validate_times,                                                         ");
		sql.append("       od.original_price,                                                         ");
		sql.append("       od.sale_price,                                                             ");
		//sql.append("       od.check_flag,                                                             ");
		sql.append("       case when od.useless_flag='N' then '否' else '是' end useless_flag,         ");
		sql.append("       case when od.check_flag='N' then '否' else '是' end check_flag,             ");
		sql.append("       od.client_id,                                                              ");
		sql.append("       case when od.eject_ticket_stat='1' then '否' else '是' end eject_ticket_stat, ");
		sql.append("       to_char(o.sale_time, 'yyyy-MM-dd HH24:mi:ss') sale_time,                   ");
		sql.append("       to_char(od.check_time,'yyyy-MM-dd HH24:mi:ss') check_time,                 ");
		sql.append("       to_char(od.useless_time,'yyyy-MM-dd HH24:mi:ss') useless_time              ");
		sql.append("  from sl_order o                                                                 ");
		sql.append(" inner join sl_order_detail od                                                    ");
		sql.append("    on o.order_id = od.order_id                                                   ");
		sql.append(" inner join sl_pay_type pt                                                        ");
		sql.append("    on o.order_id = pt.order_id                                                   ");
		sql.append(" inner join sl_netagent_order sno                                                 ");
		sql.append("    on sno.order_id = o.order_id                                                  ");
		sql.append(" inner join sl_org org                                                            ");
		sql.append("    on sno.org_id = org.org_id                                                    ");
		sql.append(" inner join sys_dictionary dit                                                    ");
		sql.append("    on o.order_type = dit.item_val                                                ");
		sql.append("   and dit.key_cd = 'ORDER_TYPE'                                                  ");
		sql.append(" inner join sys_dictionary ditpay                                                 ");
		sql.append("    on PT.PAY_TYPE = ditpay.item_val                                              ");
		sql.append("   and ditpay.key_cd = 'PAY_TYPE'                                                 ");
		sql.append(" inner join sys_ticket_type stt                                                   ");
		sql.append("    on od.ticket_type_id = stt.ticket_type_id                                     ");
		sql.append(" where o.pay_stat = '2'                                                           ");
		sql.append("   and o.order_type in ('ZQ')                                                     ");
		if (rptDt != null) {
			params.put("RPT_DT", rptDt);
			sql.append(" and trunc(o.sale_time, 'dd') =:RPT_DT ");
		}
		if(orgId != null && !orgId.equals("")) {
			params.put("ORG_ID", orgId);
			sql.append(" and sno.org_id =:ORG_ID ");
		}
		if(orderId != null && !orderId.equals("")) {
			params.put("ORDER_ID", orderId);
			sql.append(" and o.order_id =:ORDER_ID ");
		}
//		if (beginTime != null && endTime != null) {
//			params.put("BEGIN_TIME", beginTime);
//			sql.append(" and to_char(o.sale_time, 'HH24:mi') >=:BEGIN_TIME ");
//			params.put("END_TIME", endTime);
//			sql.append(" and to_char(o.sale_time, 'HH24:mi') <=:END_TIME ");
//		}
		if (StringUtil.isNotNull(ticketTypeId)) {
			params.put("TICKET_TYPE_ID", ticketTypeId);
			sql.append("  and od.ticket_type_id =:TICKET_TYPE_ID ");
		}
		sql.append(" order by o.sale_time desc                                                     ");
		PageBean<Map<String, Object>> pageList = dbUtil.queryPageToMap("", sql.toString(), loginUserBean.getPageNum(), loginUserBean.getPageSize(), params);
		return pageList;
	}

	@Override
	public PageBean<Map<String, Object>> wlSaleDetailByHB(UserBean loginUserBean, Date rptDt, String beginTime, String endTime, String ticketTypeId, String orgId, String outletId) throws BaseException {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select                                                                  ");
		sql.append("   to_char(o.sale_time,'YYYY-MM-DD HH24:MI:SS') sale_time,              ");
		sql.append("   sno.org_id,org.org_name,                                             ");
		sql.append("   od.outlet_id,so.outlet_name,od.ticket_type_id,                       ");
		sql.append("   stt.ticket_type_name,                                                ");
		sql.append("   od.eject_user_id,u.user_name,                                        ");
		sql.append("   sno.third_order_num,                                                 ");
		sql.append("   count(1) sale_count,                                                 ");
		sql.append("   sum(od.sale_price) sale_price                                        ");
		sql.append("  from sl_order o                                                       ");
		sql.append(" inner join sl_order_detail od                                          ");
		sql.append("    on o.order_id = od.order_id                                         ");
		sql.append(" inner join sl_netagent_order sno                                       ");
		sql.append("    on sno.order_id = o.order_id                                        ");
		sql.append(" inner join sl_org org                                                  ");
		sql.append("    on sno.org_id = org.org_id                                          ");
		sql.append(" inner join sys_ticket_type stt                                         ");
		sql.append("    on od.ticket_type_id = stt.ticket_type_id                           ");
		sql.append(" inner join sys_user u                                                  ");
		sql.append("    on od.eject_user_id=u.user_id                                       ");
		sql.append(" inner join sys_outlet so                                              ");
		sql.append("    on od.outlet_id=so.outlet_id                                        ");
		sql.append(" where o.pay_stat = '2' and o.order_type in ('WL')                      ");
		if (rptDt != null) {
			params.put("RPT_DT", rptDt);
			sql.append(" and trunc(o.sale_time, 'dd') =:RPT_DT ");
		}
		if (beginTime != null && endTime != null) {
			params.put("BEGIN_TIME", beginTime);
			sql.append(" and to_char(o.sale_time, 'HH24:mi') >=:BEGIN_TIME ");
			params.put("END_TIME", endTime);
			sql.append(" and to_char(o.sale_time, 'HH24:mi') <=:END_TIME ");
		}
		if (StringUtil.isNotNull(ticketTypeId)) {
			params.put("TICKET_TYPE_ID", ticketTypeId);
			sql.append("  and od.ticket_type_id =:TICKET_TYPE_ID ");
		}
		if (StringUtil.isNotNull(outletId)) {
			params.put("OUTLET_ID", CommonUtil.covertLong(outletId));
			sql.append("  and od.OUTLET_ID =:OUTLET_ID ");
		}
		if (StringUtil.isNotNull(orgId)) {
			params.put("ORG_ID", orgId);
			sql.append("  and sno.ORG_ID =:ORG_ID ");
		}

		sql.append("   group by to_char(o.sale_time,'YYYY-MM-DD HH24:MI:SS'),sno.org_id,org.org_name,  ");
		sql.append("   od.outlet_id,so.outlet_name,od.ticket_type_id,                       ");
		sql.append("   stt.ticket_type_name,od.eject_user_id,u.user_name,                   ");
		sql.append("   sno.third_order_num                                                  ");
		sql.append(" order by  to_char(o.sale_time, 'YYYY-MM-DD HH24:MI:SS') ");
		PageBean<Map<String, Object>> pageList = dbUtil.queryPageToMap("", sql.toString(), loginUserBean.getPageNum(), loginUserBean.getPageSize(), params);
		return pageList;
	}

	@Override
	public List<Map<String, Object>> regionCheckTicketByChannel(UserBean loginUserBean, Date beginDate, Date endDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select to_char(t.RPT_DT,'YYYY-MM-DD') RPT_DT,                                                                       ");
		sql.append("        t.REGION_ID,                                                                    ");
		sql.append("        case when t.ORDER_TYPE in ('XC','BP') then '现场售票'                           ");
		sql.append("             when t.ORDER_TYPE='ZY' then '自营售票'                                     ");
		sql.append("             when t.ORDER_TYPE='TD' then '团队换票'                                     ");
		sql.append("             when t.ORDER_TYPE='WL' then '网络人工换票'                                 ");
		sql.append("             when t.ORDER_TYPE='ST' then '实体代理'                                     ");
		sql.append("             when t.ORDER_TYPE='ZG' then '自助购票'                                     ");
		sql.append("             when t.ORDER_TYPE='ZQ' then '电商售票-直连'  ELSE t.ORDER_TYPE end ORDER_TYPE_dsec,           ");
		sql.append("        t.EJECT_TICKET_NUM,                                                             ");
		sql.append("        t.ORDER_NUM,                                                                    ");
		sql.append("        sr.REGION_NAME                                                                  ");
		sql.append("   from (                                                                               ");
		sql.append("select trunc(sc.ope_time, 'dd') rpt_dt,                                                 ");
		sql.append("                sct.region_id,                                                          ");
		sql.append("                so.order_type,                                                          ");
		sql.append("                count(1) EJECT_TICKET_num,                                              ");
		sql.append("                1 order_num                                                             ");
		sql.append("           from sl_order so, sl_order_detail sod, sl_check sc, sys_client sct           ");
		sql.append("          where so.order_id = sod.order_id                                              ");
		sql.append("            and sod.order_detail_id = sc.order_detail_id                                ");
		sql.append("            and sc.client_id = sct.client_id                                            ");
		if (beginDate != null && endDate != null) {
			sql.append(" AND trunc(sc.ope_time, 'dd') between :BEGIN_DATE and :END_DATE");
			params.put("BEGIN_DATE", beginDate);
			params.put("END_DATE", endDate);
		}
		sql.append("          group by trunc(sc.ope_time, 'dd'), sct.region_id, so.order_type               ");
		sql.append("         union all                                                                      ");
		sql.append("         select trunc(sc.ope_time, 'dd'),                                               ");
		sql.append("                sct.region_id,                                                          ");
		sql.append("                '小计' AS order_type,                                                   ");
		sql.append("                count(1) EJECT_TICKET_num,                                              ");
		sql.append("                2 order_num                                                             ");
		sql.append("           from sl_order so, sl_order_detail sod, sl_check sc, sys_client sct           ");
		sql.append("          where so.order_id = sod.order_id                                              ");
		sql.append("            and sod.order_detail_id = sc.order_detail_id                                ");
		sql.append("            and sc.client_id = sct.client_id                                            ");
		if (beginDate != null && endDate != null) {
			sql.append(" AND trunc(sc.ope_time, 'dd') between :BEGIN_DATE and :END_DATE");
			params.put("BEGIN_DATE", beginDate);
			params.put("END_DATE", endDate);
		}
		sql.append("          group by trunc(sc.ope_time, 'dd'), sct.region_id       ");
		sql.append(" union all                                                                               ");
		sql.append("         select trunc(sc.ope_time, 'dd'),                                                ");
		sql.append("                null region_id,                                                          ");
		sql.append("                '合计' AS order_type,                                                    ");
		sql.append("                count(1) EJECT_TICKET_num,                                               ");
		sql.append("                3 order_num                                                              ");
		sql.append("           from sl_order so, sl_order_detail sod, sl_check sc, sys_client sct            ");
		sql.append("          where so.order_id = sod.order_id                                               ");
		sql.append("            and sod.order_detail_id = sc.order_detail_id                                 ");
		sql.append("            and sc.client_id = sct.client_id                                             ");
		if (beginDate != null && endDate != null) {
			sql.append(" AND trunc(sc.ope_time, 'dd') between :BEGIN_DATE and :END_DATE");
			params.put("BEGIN_DATE", beginDate);
			params.put("END_DATE", endDate);
		}
		sql.append("          group by trunc(sc.ope_time, 'dd')                                              ");
		sql.append(" ) t                     ");
		sql.append("   left join sys_region sr                                                              ");
		sql.append("     on t.region_id = sr.region_id                                                      ");
		sql.append("  order by t.rpt_dt, t.region_id, order_num, order_type                                 ");

		List<Map<String, Object>> retList = dbUtil.queryListToMap("", sql.toString(), params);
		return retList;
	}

	@Override
	public PageBean<Map<String, Object>> wlRefundDetail(UserBean loginUserBean, Date startDate, Date endDate,
			String orgId) throws BaseException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("START_DATE", startDate);
		params.put("END_DATE", endDate);
		StringBuffer sql = new StringBuffer();
		sql.append("select SO.ORG_NAME,SR.ORDER_ID,to_char(SR.REFUND_TIME,'YYYY-MM-DD HH24:MI:SS') REFUND_TIME,SRD.IDENTTY_ID,SRD.REFUND_AMT,  ");
		sql.append("       SOD.SALE_PRICE,STT.TICKET_TYPE_NAME,to_char(SLO.SALE_TIME,'YYYY-MM-DD HH24:MI:SS') SALE_TIME                        ");
		sql.append("       from SL_REFUND_TICKET SR,SL_REFUND_TICKET_DETAIL SRD,SL_ORG SO,                 ");
		sql.append("            SL_ORDER_DETAIL SOD,SYS_TICKET_TYPE STT,SL_ORDER SLO                       ");
		sql.append("       WHERE SR.REFUND_ID=SRD.REFUND_ID AND SR.ORG_ID=SO.ORG_ID AND SR.ORDER_TYPE='ZQ' ");
		sql.append("             AND SR.ORDER_ID=SOD.ORDER_ID AND SRD.IDENTTY_ID=SOD.IDENTTY_ID            ");
		sql.append("             AND SOD.TICKET_TYPE_ID=STT.TICKET_TYPE_ID                                 ");
		sql.append("             AND SR.ORDER_ID=SLO.ORDER_ID                                              ");
		sql.append("             AND TRUNC(SR.REFUND_TIME,'DD') BETWEEN :START_DATE AND :END_DATE      ");
		if(orgId != null && !orgId.equals("")) {
			params.put("ORG_ID", orgId);
			sql.append("    AND SR.ORG_ID=:ORG_ID ");
		}
		sql.append("             order by sr.refund_time asc                                               ");

		PageBean<Map<String, Object>> pageList = dbUtil.queryPageToMap("", sql.toString(), loginUserBean.getPageNum(), loginUserBean.getPageSize(), params);
		return pageList;
	}
	
	@Override
	public PageBean<Map<String, Object>> wlCheckdDetail(UserBean loginUserBean, Date startDate, Date endDate,
			String orgId) throws BaseException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("START_DATE", startDate);
		params.put("END_DATE", endDate);
		StringBuffer sql = new StringBuffer();
		sql.append(" select SO.ORG_NAME, STT.TICKET_TYPE_NAME, SNO.ORDER_ID, SOD.IDENTTY_ID,	");
		sql.append("        to_char(SLO.SALE_TIME, 'YYYY-MM-DD HH24:MI:SS') SALE_TIME,		    ");
		sql.append("        to_char(SOD.CHECK_TIME, 'YYYY-MM-DD HH24:MI:SS') CHECK_TIME,	    ");
		sql.append("        SOD.SALE_PRICE							                            ");
		sql.append("   from SL_NETAGENT_ORDER SNO, SL_ORG SO, SL_ORDER_DETAIL SOD,		        ");
		sql.append("        SYS_TICKET_TYPE STT, SL_ORDER SLO					                ");
		sql.append("  WHERE SNO.ORDER_ID = SLO.ORDER_ID AND SNO.ORG_ID = SO.ORG_ID		        ");
		sql.append("    AND SLO.ORDER_ID = SOD.ORDER_ID AND SOD.TICKET_TYPE_ID = STT.TICKET_TYPE_ID  ");
		sql.append("    AND SOD.CHECK_FLAG='Y' AND SLO.ORDER_TYPE = 'ZQ'			            ");
		sql.append("    AND TRUNC(SOD.CHECK_TIME, 'DD') BETWEEN :START_DATE AND :END_DATE	    ");
		if(orgId != null && !orgId.equals("")) {
			params.put("ORG_ID", orgId);
			sql.append("    AND SNO.ORG_ID=:ORG_ID ");
		}
		sql.append("             order by SOD.CHECK_TIME asc                                    ");

		PageBean<Map<String, Object>> pageList = dbUtil.queryPageToMap("", sql.toString(), loginUserBean.getPageNum(), loginUserBean.getPageSize(), params);
		return pageList;
	}
}