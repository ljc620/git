package com.tbims.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zhming.support.exception.DBException;

/**
 * Title: 数据分析 <br/>
 * Description:
 * 
 * @ClassName: IDataAnalysisService
 * @author zhuxy
 * @date 2017年11月25日 上午9:10:54
 * 
 */
public interface IDataAnalysisService {

	/**
	* Title: 查询销售类型列表<br/>
	* Description: 
	* @param startDate
	* @param endDate
	* @return
	*/
	public List<Map<String, Object>> listSaleType(Date startDate, Date endDate) throws DBException;
	
	/**
	* Title: 查询销售类型金额占比<br/>
	* Description: 
	* @param startDate
	* @param endDate
	* @return
	*/
	public List<Map<String, Object>> listSaleData(Date startDate, Date endDate, String[] saleTypes) throws DBException;
	
	/**
	* Title: 查询支付方式金额占比<br/>
	* Description: 
	* @param startDate
	* @param endDate
	* @return
	*/
	public List<Map<String, Object>> listPayData(Date startDate, Date endDate, String[] saleTypes) throws DBException;
	
	/**
	* Title: 查询各门区入园游客数量占比<br/>
	* Description: 
	* @param startDate
	* @param endDate
	* @return
	*/
	public List<Map<String, Object>> listRegionAdmissionData(Date startDate, Date endDate) throws DBException;
	
	/**
	* Title: 查询各闸机入园游客数量占比<br/>
	* Description: 
	* @param startDate
	* @param endDate
	* @param regions
	* @return
	*/
	public List<Map<String, Object>> listGataAdmissionData(Date startDate, Date endDate, int[] regions) throws DBException;
	
	/**
	* Title: 查询各网点销售数量占比<br/>
	* Description: 
	* @param startDate
	* @param endDate
	* @return
	*/
	public List<Map<String, Object>> listOutletSaleData(Date startDate, Date endDate) throws DBException;
	
	/**
	* Title: 查询各窗口/终端销售数量占比<br/>
	* Description: 
	* @param startDate
	* @param endDate
	* @param outlets
	* @param groupType
	* @return
	*/
	public List<Map<String, Object>> listDetailSaleData(Date startDate, Date endDate, int[] outlets, String groupType) throws DBException;
	
	/**
	* Title: 游客热力分布图--查询区间内每日入园游客数量<br/>
	* Description: 
	* @param startDate
	* @param endDate
	* @return
	*/
	public List<Map<String, Object>> listAdmissionData(Date startDate, Date endDate) throws DBException;
}
