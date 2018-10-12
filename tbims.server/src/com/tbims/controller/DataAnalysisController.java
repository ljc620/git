package com.tbims.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tbims.service.IDataAnalysisService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.BaseException;


/**
 * 数据分析
 * @author zhuxy
 *
 */
@RestController
@RequestMapping("/dataAnalysis/")
public class DataAnalysisController extends BaseController {

	@Autowired
	private IDataAnalysisService dataAnalysisService;
	
	/**
	 * Title: 销售分析--查询销售类型列表<br/>
	 * Description:
	 * @param sysClient
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listSaleType")
	@ControlAspect(funtionCd = "i2_analy_sale_pay", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public List<Map<String, Object>> listSaleType(Date startDate, Date endDate) throws BaseException {
		List<Map<String, Object>> saleTypeList = dataAnalysisService.listSaleType(startDate, endDate);
		return saleTypeList;
	}
	
	/**
	 * Title: 销售分析--查询销售类型及支付方式占比<br/>
	 * Description:
	 * @param sysClient
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listSaleAndPayData")
	@ControlAspect(funtionCd = "i2_analy_sale_pay", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public Map<String, Object> listSaleAndPayData(Date startDate, Date endDate, String[] saleTypes) throws BaseException {
		Map<String, Object> saleAndPayList = new HashMap<String, Object>();
				//dataAnalysisService.listSaleAndPayData(getLoginUserBean(), startDate, endDate);
		List<Map<String, Object>> saleDataList = dataAnalysisService.listSaleData(startDate, endDate, saleTypes);
		List<String> legendList = new ArrayList<String>();
		for(Map<String, Object> row : saleDataList){
			legendList.add((String) row.get("name"));
		}
		
		saleAndPayList.put("legend", legendList);
		saleAndPayList.put("saleData", saleDataList);
		saleAndPayList.put("payData", dataAnalysisService.listPayData(startDate, endDate, saleTypes));
		return saleAndPayList;
	}
	
	/**
	 * Title: 销售分析--根据销售类型查询各支付方式支付金额占比<br/>
	 * Description:
	 * @param sysClient
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listPayDataBySaleType")
	@ControlAspect(funtionCd = "i2_analy_sale_pay", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public List<Map<String, Object>> listPayDataBySaleType(Date startDate, Date endDate, String[] saleTypes) throws BaseException {
		List<Map<String, Object>> payDataList = dataAnalysisService.listPayData(startDate, endDate, saleTypes);
		return payDataList;
	}
	
	/**
	* Title: 游客入园通道分析--查询各门区入园游客数量占比<br/>
	* Description: 
	* @param startDate
	* @param endDate
	* @return
	*/
	@RequestMapping(value = "listRegionAdmissionData")
	@ControlAspect(funtionCd = "i2_analy_admission", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public Map<String, Object> listRegionAdmissionData(Date startDate, Date endDate) throws BaseException {
		Map<String, Object> regionAndGataList = new HashMap<String, Object>();
		List<Map<String, Object>> regionDataList = dataAnalysisService.listRegionAdmissionData(startDate, endDate);
		List<String> legendList = new ArrayList<String>();
		for(Map<String, Object> row : regionDataList){
			legendList.add((String) row.get("name"));
		}
		
		regionAndGataList.put("legend", legendList);
		regionAndGataList.put("regionData", regionDataList);
		regionAndGataList.put("gataData", dataAnalysisService.listGataAdmissionData(startDate, endDate, null));
		return regionAndGataList;
	}

	/**
	* Title: 游客入园通道分析--查询各闸机入园游客数量占比<br/>
	* Description: 
	* @param startDate
	* @param endDate
	* @param regions
	* @return
	*/
	@RequestMapping(value = "listGataAdmissionData")
	@ControlAspect(funtionCd = "i2_analy_admission", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public List<Map<String, Object>> listGataAdmissionData(Date startDate, Date endDate, int[] regions) throws BaseException {
		List<Map<String, Object>> gataDataList = dataAnalysisService.listGataAdmissionData(startDate, endDate, regions);
		return gataDataList;
	}
	
	/**
	* Title: 网点门票销售分析--查询各网点销售数量占比<br/>
	* Description: 
	* @param startDate
	* @param endDate
	* @param groupType
	* @return
	*/
	@RequestMapping(value = "listOutletSaleData")
	@ControlAspect(funtionCd = "i2_analy_outlet_sale", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public Map<String, Object> listOutletSaleData(Date startDate, Date endDate, String groupType) throws BaseException {
		Map<String, Object> outletAndClientList = new HashMap<String, Object>();
		List<Map<String, Object>> outletDataList = dataAnalysisService.listOutletSaleData(startDate, endDate);
		List<String> legendList = new ArrayList<String>();
		for(Map<String, Object> row : outletDataList){
			legendList.add((String) row.get("name"));
		}
		
		outletAndClientList.put("legend", legendList);
		outletAndClientList.put("outletData", outletDataList);
		outletAndClientList.put("clientData", dataAnalysisService.listDetailSaleData(startDate, endDate, null, groupType));
		return outletAndClientList;
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
	@RequestMapping(value = "listDetailSaleData")
	@ControlAspect(funtionCd = "i2_analy_outlet_sale", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public List<Map<String, Object>> listDetailSaleData(Date startDate, Date endDate, int[] outlets, String groupType) throws BaseException {
		List<Map<String, Object>> clientDataList = dataAnalysisService.listDetailSaleData(startDate, endDate, outlets, groupType);
		return clientDataList;
	}
	
	/**
	* Title: 游客热力分布图--查询区间内每日入园游客数量<br/>
	* Description: 
	* @param startDate
	* @param endDate
	* @param outlets
	* @return
	*/
	@RequestMapping(value = "listAdmissionData")
	@ControlAspect(funtionCd = "i2_analy_admission_heatmap", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public List<List<Object>> listAdmissionData(Date startDate, Date endDate) throws BaseException {
		List<Map<String, Object>> admissionDataList = dataAnalysisService.listAdmissionData(startDate, endDate);
		
		List<List<Object>> heatmapList = new ArrayList<List<Object>>();
		for(Map<String, Object> row : admissionDataList){
			List<Object> dayList = new ArrayList<Object>();
			dayList.add(row.get("rptDate"));
			dayList.add(row.get("countNum"));
			
			heatmapList.add(dayList);
		}
		return heatmapList;
	}
}
