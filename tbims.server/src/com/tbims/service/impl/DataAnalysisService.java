package com.tbims.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.tbims.service.IDataAnalysisService;
import com.zhming.support.BaseService;
import com.zhming.support.exception.DBException;

@Service
public class DataAnalysisService extends BaseService implements IDataAnalysisService {

//	private final Log logger = LogFactory.getLog(getClass());


	/**
	* Title: 查询销售类型列表<br/>
	* Description: 
	* @param startDate
	* @param endDate
	* @return
	*/
	@Override
	public List<Map<String, Object>> listSaleType(Date startDate, Date endDate) throws DBException {
		StringBuffer sql = new StringBuffer();
//		sql.append(" select dic.item_name, u.order_type from									    ");
//		sql.append(" (  select distinct so.order_type											    ");
//		sql.append("        from sl_order so 												    ");
//		sql.append("        where so.pay_stat='2'											    ");
//		sql.append("        and trunc(so.sale_time,'dd') between :BGGIN_DT and :END_DT	    ");
//		sql.append(" union all														    ");
//		sql.append("  select 'ST' order_type from dual having (select count(*)								    ");
//		sql.append("         from sl_sale_reg sr											    ");
//		sql.append("         where trunc(sr.sale_date,'dd') between :BGGIN_DT and :END_DT)>0    ");
//		sql.append(" ) u,SYS_DICTIONARY dic where dic.key_cd='ORDER_TYPE' and u.order_type=dic.item_val					    ");
//		sql.append("  group by u.order_type,dic.item_name										    ");
		
		sql.append(" select dic.item_name name,dic.item_val id from SYS_DICTIONARY dic where dic.key_cd='ORDER_TYPE' order by dic.Order_num ");
		Map<String, Object> params = new HashMap<>();
//		params.put("BGGIN_DT", startDate);
//		params.put("END_DT", endDate);
		List<Map<String, Object>> queryList = dbUtil.queryListToMap("查询销售类型列表", sql.toString(), params);
		
		return queryList;
	}

	/**
	* Title: 查询销售类型金额占比<br/>
	* Description: 
	* @param startDate
	* @param endDate
	* @return
	*/
	@Override
	public List<Map<String, Object>> listSaleData(Date startDate, Date endDate, String[] saleTypes) throws DBException {
		Map<String, Object> params = new HashMap<>();
		params.put("BGGIN_DT", startDate);
		params.put("END_DT", endDate);
		
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(amt) value, dic.item_name name, u.order_type from                        ");
		sql.append("(  select so.order_type,so.real_sum amt                                             ");
		sql.append("       from sl_order so                                                             ");
		sql.append("       where so.pay_stat='2'                                                        ");
		sql.append("       and trunc(so.sale_time,'dd') between :BGGIN_DT and :END_DT                   ");
		sql.append("union all                                                                           ");
		sql.append(" select 'ST' order_type,nvl(sum(sr.sale_amt),0) amt                                 ");
		sql.append("        from sl_sale_reg sr                                                         ");
		sql.append("        where trunc(sr.sale_date,'dd') between :BGGIN_DT and :END_DT                ");
		sql.append(") u,SYS_DICTIONARY dic                                                              ");
		sql.append("  where dic.key_cd='ORDER_TYPE' and u.amt>0 and u.order_type=dic.item_val           ");
		if(saleTypes != null && saleTypes.length>0) {
			sql.append(" and (");
			for(int i=0;i< saleTypes.length;i++) {
				if(i==0)
					sql.append(" u.order_type=:ORDERTYPE" + String.valueOf(i));
				else
					sql.append(" or u.order_type=:ORDERTYPE" + String.valueOf(i));
				params.put("ORDERTYPE" + String.valueOf(i), saleTypes[i]);
			}
			sql.append(" )");
		}	
		sql.append(" group by u.order_type,dic.item_name,dic.order_num order by dic.order_num           ");
		

		List<Map<String, Object>> queryList = dbUtil.queryListToMap("查询销售类型金额占比", sql.toString(), params);
		
		return queryList;
	}

	/**
	* Title: 查询支付方式金额占比<br/>
	* Description: 
	* @param startDate
	* @param endDate
	* @return
	*/
	@Override
	public List<Map<String, Object>> listPayData(Date startDate, Date endDate, String[] saleTypes) throws DBException {
		Map<String, Object> params = new HashMap<>();
		params.put("BGGIN_DT", startDate);
		params.put("END_DT", endDate);
		StringBuffer sql = new StringBuffer();
		sql.append(" select sum(amt) value, dic.item_name name, u.pay_type from           ");
		sql.append(" (  select sp.pay_type,so.real_sum amt,so.order_type                  ");
		sql.append("        from sl_order so, sl_pay_type sp                              ");
		sql.append("        where so.pay_stat='2' and so.order_id=sp.order_id             ");
		sql.append("        and trunc(so.sale_time,'dd') between :BGGIN_DT and :END_DT    ");
		sql.append(" union all                                                            ");
		sql.append("    select '05' pay_type,nvl(sum(sr.sale_amt),0) amt,'ST' order_type  ");
		sql.append("         from sl_sale_reg sr                                          ");
		sql.append("         where trunc(sr.sale_date,'dd') between :BGGIN_DT and :END_DT ");
		sql.append(" ) u,SYS_DICTIONARY dic                                               ");
		sql.append("  where dic.key_cd='PAY_TYPE' and u.pay_type=dic.item_val and u.amt>0 ");
		if(saleTypes != null && saleTypes.length > 0) {
			sql.append(" and (");
			for(int i=0;i< saleTypes.length;i++) {
				if(i==0)
					sql.append(" u.order_type=:ORDERTYPE" + String.valueOf(i));
				else
					sql.append(" or u.order_type=:ORDERTYPE" + String.valueOf(i));
				params.put("ORDERTYPE" + String.valueOf(i), saleTypes[i]);
			}
			sql.append(" )");
		}
		sql.append("  group by u.pay_type,dic.item_name,dic.order_num order by dic.order_num ");
		

		List<Map<String, Object>> queryList = dbUtil.queryListToMap("查询支付方式金额占比", sql.toString(), params);
		
		return queryList;
	}

	/**
	* Title: 游客入园通道分析--查询各门区入园游客数量占比<br/>
	* Description: 
	* @param startDate
	* @param endDate
	* @return
	*/
	@Override
	public List<Map<String, Object>> listRegionAdmissionData(Date startDate, Date endDate) throws DBException {
		Map<String, Object> params = new HashMap<>();
		params.put("BGGIN_DT", startDate);
		params.put("END_DT", endDate);
		StringBuffer sql = new StringBuffer();
		sql.append(" select cli.region_id,srg.region_name name,count(*) value                                     ");
		sql.append("        from sl_check sc,sys_client cli,sys_region srg                                        ");
		sql.append("        where sc.pass_flag='Y' and sc.client_id=cli.client_id and cli.region_id=srg.region_id ");
		sql.append("        and trunc(sc.ope_time,'dd') between :BGGIN_DT and :END_DT                             ");
		sql.append("        group by cli.region_id,srg.region_name order by cli.region_id                         ");

		List<Map<String, Object>> queryList = dbUtil.queryListToMap("查询各门区入园游客数量占比", sql.toString(), params);
		
		return queryList;
	}

	/**
	* Title: 游客入园通道分析--查询各闸机入园游客数量占比<br/>
	* Description: 
	* @param startDate
	* @param endDate
	* @param regions
	* @return
	*/
	@Override
	public List<Map<String, Object>> listGataAdmissionData(Date startDate, Date endDate, int[] regions) throws DBException {
		Map<String, Object> params = new HashMap<>();
		params.put("BGGIN_DT", startDate);
		params.put("END_DT", endDate);
		StringBuffer sql = new StringBuffer();
		sql.append(" select cli.client_id,cli.client_name name,count(*) value           ");
		sql.append("        from sl_check sc,sys_client cli                             ");
		sql.append("        where sc.pass_flag='Y' and sc.client_id=cli.client_id       ");
		sql.append("        and trunc(sc.ope_time,'dd') between :BGGIN_DT and :END_DT   ");
		if(regions != null && regions.length > 0) {
			sql.append(" and (");
			for(int i=0;i< regions.length;i++) {
				if(i==0)
					sql.append(" cli.region_id=:REGIONID" + String.valueOf(i));
				else
					sql.append(" or cli.region_id=:REGIONID" + String.valueOf(i));
				params.put("REGIONID" + String.valueOf(i), regions[i]);
			}
			sql.append(" )");
		}
		sql.append("        group by cli.client_id,cli.client_name order by cli.client_id ");//value desc

		List<Map<String, Object>> queryList = dbUtil.queryListToMap("查询各闸机入园游客数量占比", sql.toString(), params);
		
		return queryList;
	}

	/**
	* Title: 网点门票销售分析--查询各网点销售数量占比<br/>
	* Description: 
	* @param startDate
	* @param endDate
	* @return
	*/
	@Override
	public List<Map<String, Object>> listOutletSaleData(Date startDate, Date endDate) throws DBException {
		Map<String, Object> params = new HashMap<>();
		params.put("BGGIN_DT", startDate);
		params.put("END_DT", endDate);
		StringBuffer sql = new StringBuffer();
		sql.append(" select slt.outlet_name name,count(*) value, sod.outlet_id                                 ");
		sql.append("        from sl_order so, sl_order_detail sod, sys_outlet slt                              ");
		sql.append("        where so.pay_stat='2' and so.order_id=sod.order_id and sod.outlet_id=slt.outlet_id ");
		sql.append("        and trunc(so.sale_time,'dd') between :BGGIN_DT and :END_DT                         ");
		sql.append("        and (slt.outlet_type='01' or slt.outlet_type='03')                                 ");
		sql.append("        group by sod.outlet_id,slt.outlet_name order by sod.outlet_id                      ");

		List<Map<String, Object>> queryList = dbUtil.queryListToMap("查询各网点销售数量占比", sql.toString(), params);
		
		return queryList;
	}

	/**
	* Title: 网点门票销售分析--查询各窗口/终端销售数量占比<br/>
	* Description: 
	* @param startDate
	* @param endDate
	* @param outlets
	* @param groupType
	* @return
	*/
	@Override
	public List<Map<String, Object>> listDetailSaleData(Date startDate, Date endDate, int[] outlets, String groupType) throws DBException {
		Map<String, Object> params = new HashMap<>();
		params.put("BGGIN_DT", startDate);
		params.put("END_DT", endDate);
		StringBuffer sql = new StringBuffer();
		if(groupType.equals("byClients")) {
			sql.append(" select cli.client_name name,count(*) value, cli.client_id                                 ");
			sql.append("        from sl_order so, sl_order_detail sod, sys_client cli                              ");
			sql.append("        where so.pay_stat='2' and so.order_id=sod.order_id and sod.client_id=cli.client_id ");
			sql.append("        and trunc(so.sale_time,'dd') between :BGGIN_DT and :END_DT                         ");
			sql.append("        and sod.outlet_id in (select outlet_id from sys_outlet where outlet_type='01' or outlet_type='03') ");
			if(outlets != null && outlets.length > 0) {
				sql.append(" and (");
				for(int i=0;i< outlets.length;i++) {
					if(i==0)
						sql.append(" sod.outlet_id=:OUTLETID" + String.valueOf(i));
					else
						sql.append(" or sod.outlet_id=:OUTLETID" + String.valueOf(i));
					params.put("OUTLETID" + String.valueOf(i), outlets[i]);
				}
				sql.append(" )");
			}
			sql.append("        group by cli.client_id,cli.client_name order by value desc                         ");
		}
		else if(groupType.equals("byUsers")) {
			sql.append(" select su.user_name name,count(*) value, sod.eject_user_id                                 ");
			sql.append("        from sl_order so, sl_order_detail sod, sys_user su                                  ");
			sql.append("        where so.pay_stat='2' and so.order_id=sod.order_id and sod.eject_user_id=su.user_id ");
			sql.append("        and trunc(so.sale_time,'dd') between :BGGIN_DT and :END_DT                          ");
			sql.append("        and sod.outlet_id in (select outlet_id from sys_outlet where outlet_type='01' or outlet_type='03') ");
			if(outlets != null && outlets.length > 0) {
				sql.append(" and (");
				for(int i=0;i< outlets.length;i++) {
					if(i==0)
						sql.append(" sod.outlet_id=:OUTLETID" + String.valueOf(i));
					else
						sql.append(" or sod.outlet_id=:OUTLETID" + String.valueOf(i));
					params.put("OUTLETID" + String.valueOf(i), outlets[i]);
				}
				sql.append(" )");
			}
			sql.append("        group by sod.eject_user_id,su.user_name order by value desc                         ");
		}
		
		if(sql.toString().equals(""))
			return null;
		
		List<Map<String, Object>> queryList = dbUtil.queryListToMap("查询各窗口/终端销售数量占比", sql.toString(), params);
		
		return queryList;
	}

	/**
	* Title: 游客热力分布图--查询区间内每日入园游客数量<br/>
	* Description: 
	* @param startDate
	* @param endDate
	* @return
	*/
	@Override
	public List<Map<String, Object>> listAdmissionData(Date startDate, Date endDate) throws DBException {
		Map<String, Object> params = new HashMap<>();
		params.put("BGGIN_DT", startDate);
		params.put("END_DT", endDate);
		StringBuffer sql = new StringBuffer();
		sql.append(" select trunc(sc.ope_time,'dd') rpt_date, count(*) count_num from sl_check sc where sc.pass_flag='Y' ");
		sql.append("        and trunc(sc.ope_time,'dd') between :BGGIN_DT and :END_DT                                    ");
		sql.append("        group by trunc(sc.ope_time,'dd') order by rpt_date                                           ");

		List<Map<String, Object>> queryList = dbUtil.queryListToMap("查询区间内每日入园游客数量(热力分析)", sql.toString(), params);
		
		return queryList;
	}
	
	

}
