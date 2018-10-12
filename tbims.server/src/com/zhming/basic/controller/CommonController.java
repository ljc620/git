package com.zhming.basic.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tbims.bean.RoleBean;
import com.tbims.entity.SlOrg;
import com.tbims.entity.SysClient;
import com.tbims.entity.SysOutlet;
import com.tbims.entity.SysRegion;
import com.zhming.basic.service.ICommonService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.ZTreeJson;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.DBException;

@RestController
@RequestMapping("/comm/")
public class CommonController extends BaseController {
	@Autowired
	private ICommonService commonService;

	/**
	 * 
	 * Title: 查询指定字段字典值<br/>
	 * Description:
	 * 
	 * @param key 字段名称
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listItemsByKey")
	@ControlAspect(funtionCd = "查询指定字段字典值")
	@ControllerException
	public List<Map<String, Object>> listItemsByKey(String key) throws BaseException {
		List<Map<String, Object>> items = commonService.listItemsByKey(key);
		return items;
	}

	/**
	 * 
	 * Title: 查询指定字段字典值<br/>
	 * Description:
	 * 
	 * @param key 字段名称
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listItemsByKeyOutletMng")
	@ControlAspect(funtionCd = "查询指定字段字典值")
	@ControllerException
	public List<Map<String, Object>> listItemsByKeyOutletMng(String key) throws BaseException {
		List<Map<String, Object>> items = commonService.listItemsByKeyOutletMng(key);
		return items;
	}

	/**
	 * 
	 * Title: 查询角色列表<br/>
	 * Description:
	 * 
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listRole")
	@ControlAspect(funtionCd = "查询角色")
	@ControllerException
	public List<RoleBean> listRole(String userId) throws BaseException {
		List<RoleBean> items = commonService.listRole(userId);
		return items;
	}

	/**
	 * 
	 * Title: 查询网点列表<br/>
	 * Description:
	 * 
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listOutlet")
	@ControlAspect(funtionCd = "查询网点")
	@ControllerException
	public List<SysOutlet> listOutlet() throws BaseException {
		List<SysOutlet> items = commonService.listOutlet();
		return items;
	}

	/**
	 * 
	 * Title: 查询网点列表<br/>
	 * Description:
	 * 
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listOutletTree")
	@ControlAspect(funtionCd = "查询网点")
	@ControllerException
	public List<Map<String, Object>> listOutletTree() throws BaseException {
		List<Map<String, Object>> items = commonService.listOutletTree();
		return items;
	}

	/**
	 * 
	 * Title: 查询网点列表<br/>
	 * Description:
	 * 
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listSaleOutletByUser")
	@ControlAspect(funtionCd = "查询网点")
	@ControllerException
	public List<Map<String, Object>> listSaleOutletByUser() throws BaseException {
		List<Map<String, Object>> items = commonService.listSaleOutletByUser(getLoginUserBean());
		return items;
	}

	/**
	 * 
	 * Title: 查询网点列表<br/>
	 * Description:
	 * 
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listSaleOutlet")
	@ControlAspect(funtionCd = "查询网点")
	@ControllerException
	public List<SysOutlet> listSaleOutlet() throws BaseException {
		List<SysOutlet> items = commonService.listSaleOutlet();
		return items;
	}

	/**
	 * 
	 * Title: 查询区域列表<br/>
	 * Description:
	 * 
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listRegion")
	@ControlAspect(funtionCd = "查询区域")
	@ControllerException
	public List<SysRegion> listRegion() throws BaseException {
		List<SysRegion> regions = commonService.listRegion();
		return regions;
	}

	/**
	 * 
	 * Title: 控制终端对应查询区域列表<br/>
	 * Description:
	 * 
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listRegionN")
	@ControlAspect(funtionCd = "控制终端对应的区域列表")
	@ControllerException
	public List<SysRegion> listRegionN() throws BaseException {
		List<SysRegion> regionList = new ArrayList<SysRegion>();
		List<SysRegion> regions = commonService.listRegion();
		SysRegion region = new SysRegion();
		region.setRegionId(new Long(0));
		region.setRegionName("票务中心");
		regionList.add(region);
		regionList.addAll(regions);
		return regionList;
	}

	/**
	 * 
	 * Title:根据网点类型查询网点列表 <br/>
	 * Description:
	 * 
	 * @return
	 * @param outletType
	 * @throws BaseException
	 */
	@RequestMapping(value = "listOutletByType")
	@ControlAspect(funtionCd = "根据网点类型查询网点")
	@ControllerException
	public List<SysOutlet> listOutletByType(String outletType) throws BaseException {
		List<SysOutlet> items = commonService.listOutletByType(outletType);
		return items;
	}

	/**
	 * 
	 * Title:根据终端类型查询终端列表 <br/>
	 * Description:
	 * 
	 * @return
	 * @param outletType
	 * @throws BaseException
	 */
	@RequestMapping(value = "listClientByType")
	@ControlAspect(funtionCd = "根据网点类型查询终端")
	@ControllerException
	public List<SysClient> listClientByType(String clientType) throws BaseException {
		List<SysClient> items = commonService.listClientByType(clientType);
		return items;
	}

	/**
	 * 
	 * Title: 客流统计使用多选下拉框：场馆下拉多选框<br/>
	 * Description:
	 * 
	 * @return
	 */
	@RequestMapping(value = "initVenueJson")
	@ControlAspect(funtionCd = "场馆下拉多选框")
	@ControllerException
	public List<ZTreeJson> initVenueJson(String venueId) throws BaseException {
		List<ZTreeJson> zTreeList = commonService.initVenueJson(venueId);
		return zTreeList;
	}

	/**
	 * 
	 * Title: 客流统计使用多选下拉框：区域下拉多选框<br/>
	 * Description:
	 * 
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "initRegionJson")
	@ControlAspect(funtionCd = "区域下拉多选框")
	@ControllerException
	public List<ZTreeJson> initRegionJson(String regionId) throws BaseException {
		List<ZTreeJson> zTreeList = commonService.initRegionJson(regionId);
		return zTreeList;
	}

	/**
	 * 
	 * Title: 客流统计使用多选下拉框：票种下拉多选框 <br/>
	 * Description:
	 * 
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "initTicketTypeJson")
	@ControlAspect(funtionCd = "票种下拉多选框 ")
	@ControllerException
	public List<ZTreeJson> initTicketTypeJson(String ticketTypeId) throws BaseException {
		List<ZTreeJson> zTreeList = commonService.initTicketTypeJson(ticketTypeId);
		return zTreeList;
	}

	/**
	 * 
	 * Title: 查询员工列表<br/>
	 * Description:
	 * 
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "empList")
	@ControlAspect(funtionCd = "员工下拉多选框 ")
	@ControllerException
	public List<ZTreeJson> empList(String empId) throws BaseException {
		List<ZTreeJson> ret = commonService.empList(empId);
		return ret;
	}

	/**
	 * 查询机构
	 * 
	 * @param orgType
	 * @return
	 * @throws DBException
	 */
	@RequestMapping(value = "listOrg")
	@ControlAspect(funtionCd = "查询机构")
	@ControllerException
	public List<SlOrg> listOrg(String orgType) throws DBException {
		return commonService.listOrg(orgType);
	}

	/**
	 * 查询机构
	 * 
	 * @param orgType
	 * @return
	 * @throws DBException
	 */
	@RequestMapping(value = "listOrgAll")
	@ControlAspect(funtionCd = "查询机构")
	@ControllerException
	public List<SlOrg> listOrgAll(String orgType) throws DBException {
		return commonService.listOrgAll(orgType);
	}
}
