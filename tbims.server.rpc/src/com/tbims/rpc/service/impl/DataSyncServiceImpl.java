package com.tbims.rpc.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.TException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tbims.annontion.ControlAspect;
import com.tbims.cache.ConfigUtil;
import com.tbims.common.OperType;
import com.tbims.db.entity.SlPeriod;
import com.tbims.db.entity.SysBlackList;
import com.tbims.db.entity.SysClient;
import com.tbims.db.entity.SysDictionary;
import com.tbims.db.entity.SysEmpRegister;
import com.tbims.db.entity.SysEmpVenue;
import com.tbims.db.entity.SysOutlet;
import com.tbims.db.entity.SysParemeter;
import com.tbims.db.entity.SysRegion;
import com.tbims.db.entity.SysTicketType;
import com.tbims.db.entity.SysTicketTypeRule;
import com.tbims.db.entity.SysTicketTypeVenue;
import com.tbims.db.entity.SysVenue;
import com.tbims.db.util.DBUtil;
import com.tbims.rpc.entity.AUTHORIZATION;
import com.tbims.rpc.entity.RPCException;
import com.tbims.rpc.entity.SL_PERIOD;
import com.tbims.rpc.entity.SYS_BLACK_LIST;
import com.tbims.rpc.entity.SYS_CLIENT;
import com.tbims.rpc.entity.SYS_DICTIONARY;
import com.tbims.rpc.entity.SYS_EMP_REGISTER;
import com.tbims.rpc.entity.SYS_EMP_VENUE;
import com.tbims.rpc.entity.SYS_PAREMETER;
import com.tbims.rpc.entity.SYS_TICKET_TYPE;
import com.tbims.rpc.entity.SYS_TICKET_TYPE_RULE;
import com.tbims.rpc.entity.SYS_TICKET_TYPE_VENUE;
import com.tbims.rpc.entity.SYS_VENUE;
import com.tbims.util.BeanUtils;
import com.tbims.util.CommonUtil;
import com.tbims.util.FileUtil;
import com.tbims.util.StringUtil;
import com.tbims.util.UUIDGenerator;

@Component
public class DataSyncServiceImpl implements com.tbims.rpc.service.DataSyncService.Iface {
	private static final Log logger = LogFactory.getLog(DataSyncServiceImpl.class);
	/**
	 * 客户端同步日志保存路径
	 */
	private static String CLIENT_LOG_PATH = ConfigUtil.configs.get("client.log.path");

	@Autowired
	DBUtil dbUtil;

	@Override
	@ControlAspect(funtionName = "同步终端状态", operType = OperType.SCHEDULER)
	public boolean gateStateSnyc(AUTHORIZATION auth, SYS_CLIENT sys_client) throws RPCException, TException {
		SysClient sysClient = dbUtil.findById("查询终端", SysClient.class, sys_client.getClientId());
		if (sysClient == null) {
			throw new RPCException(3, "终端编号不存在");
		}
		sysClient.setRunStat(CommonUtil.covertInt(sys_client.getRunStat()));
		sysClient.setGateMode(CommonUtil.covertInt(sys_client.getGateMode()));
		sysClient.setReportTime(new Date());

		dbUtil.updateEntity("更新终端状态", sysClient);
		return true;
	}

	@Override
	@ControlAspect(funtionName = "自助售票机状态和票数同步", operType = OperType.SCHEDULER)
	public boolean ejectTicketStatSync(AUTHORIZATION auth, int state, String ticket_num) throws RPCException, TException {
		SysClient sysClient = dbUtil.findById("", SysClient.class, auth.getClientId());
		sysClient.setRunStat(state);
		sysClient.setReportTime(new Date());
		sysClient.setTicketNum(ticket_num);

		dbUtil.updateEntity("更新自助售票机状态", sysClient);

		return true;
	}

	@Override
	@ControlAspect(funtionName = "员工卡表下载", operType = OperType.SCHEDULER)
	public List<SYS_EMP_REGISTER> empRegisterSnyc(AUTHORIZATION auth, long version_no) throws RPCException, TException {

		List<SYS_EMP_REGISTER> sys_emp_registers = new ArrayList<SYS_EMP_REGISTER>();
		List<SysEmpRegister> sysEmpRegisters = dbUtil.queryListToBean("员工卡表查询", "SELECT * FROM SYS_EMP_REGISTER  WHERE VERSION_NO>=? ", SysEmpRegister.class, new Date(version_no));
		for (SysEmpRegister sysEmpRegister : sysEmpRegisters) {
			SYS_EMP_REGISTER sys_emp_register = new SYS_EMP_REGISTER();
			BeanUtils.copyRPCProperties(sysEmpRegister, sys_emp_register);
			List<SYS_EMP_VENUE> sys_emp_venues = new ArrayList<SYS_EMP_VENUE>();
			for (SysEmpVenue sysEmpVenue : sysEmpRegister.getSysEmpVenues()) {
				SYS_EMP_VENUE sys_emp_venue = new SYS_EMP_VENUE();
				BeanUtils.copyRPCProperties(sysEmpVenue, sys_emp_venue);
				sys_emp_venue.setEmpId(sysEmpVenue.getSysEmpRegister().getEmpId());
				sys_emp_venues.add(sys_emp_venue);
			}
			sys_emp_register.setSysEmpVenuelist(sys_emp_venues);
			sys_emp_registers.add(sys_emp_register);
		}

		return sys_emp_registers;
	}

	@Override
	@ControlAspect(funtionName = "票种下载", operType = OperType.SCHEDULER)
	public List<SYS_TICKET_TYPE> ticketTypeSnyc(AUTHORIZATION auth, long version_no) throws RPCException, TException {
		LogFactory.releaseAll();
		List<SYS_TICKET_TYPE> sys_ticket_types = new ArrayList<SYS_TICKET_TYPE>();
		List<SysTicketType> sysTicketTypeList = dbUtil.queryListToBean("票种查询", "SELECT * FROM SYS_TICKET_TYPE WHERE TICKET_TYPE_STAT='Y' ", SysTicketType.class);
		for (SysTicketType ticketType : sysTicketTypeList) {
			SYS_TICKET_TYPE sys_ticket_type = new SYS_TICKET_TYPE();
			BeanUtils.copyRPCProperties(ticketType, sys_ticket_type);

			List<SYS_TICKET_TYPE_RULE> sys_ticket_type_rules = new ArrayList<SYS_TICKET_TYPE_RULE>();
			List<SysTicketTypeRule> sysTicketTypeRuleList = dbUtil.queryListToBean("票种规则查询", "SELECT * FROM SYS_TICKET_TYPE_RULE WHERE TICKET_TYPE_ID=? ", SysTicketTypeRule.class,
					ticketType.getTicketTypeId());
			for (SysTicketTypeRule ticketTypeRule : sysTicketTypeRuleList) {
				SYS_TICKET_TYPE_RULE sys_ticket_type_rule = new SYS_TICKET_TYPE_RULE();
				BeanUtils.copyRPCProperties(ticketTypeRule, sys_ticket_type_rule);

				sys_ticket_type_rules.add(sys_ticket_type_rule);
			}

			List<SYS_TICKET_TYPE_VENUE> sys_ticket_type_venues = new ArrayList<SYS_TICKET_TYPE_VENUE>();
			List<SysTicketTypeVenue> sysTicketTypeVenueList = dbUtil.queryListToBean("票种规则查询", "SELECT * FROM SYS_TICKET_TYPE_VENUE WHERE TICKET_TYPE_ID=?", SysTicketTypeVenue.class,
					ticketType.getTicketTypeId());
			for (SysTicketTypeVenue ticketTypeVenue : sysTicketTypeVenueList) {
				SYS_TICKET_TYPE_VENUE sys_ticket_type_venue = new SYS_TICKET_TYPE_VENUE();
				BeanUtils.copyRPCProperties(ticketTypeVenue, sys_ticket_type_venue);
				sys_ticket_type_venue.setTicketTypeId(ticketTypeVenue.getId().getTicketTypeId());
				sys_ticket_type_venue.setVenueId(ticketTypeVenue.getId().getVenueId());

				sys_ticket_type_venues.add(sys_ticket_type_venue);
			}

			sys_ticket_type.setSysTicketTypeRulelist(sys_ticket_type_rules);
			sys_ticket_type.setSysTicketTypeVenuelist(sys_ticket_type_venues);

			sys_ticket_types.add(sys_ticket_type);
		}

		return sys_ticket_types;
	}

	@Override
	@ControlAspect(funtionName = "黑名单下载", operType = OperType.SCHEDULER)
	public List<SYS_BLACK_LIST> blacklistSnyc(AUTHORIZATION auth, long version_no) throws RPCException, TException {

		List<SYS_BLACK_LIST> sys_black_lists = new ArrayList<SYS_BLACK_LIST>();
		List<SysBlackList> sysBlackLists = dbUtil.queryListToBean("黑名单查询", "SELECT * FROM SYS_BLACK_LIST WHERE STAT='Y' AND VERSION_NO>=?", SysBlackList.class, new Date(version_no));
		for (SysBlackList sysBlackList : sysBlackLists) {
			SYS_BLACK_LIST sys_black_list = new SYS_BLACK_LIST();
			BeanUtils.copyRPCProperties(sysBlackList, sys_black_list);

			sys_black_lists.add(sys_black_list);
		}

		return sys_black_lists;
	}

	@Override
	@ControlAspect(funtionName = "字典下载", operType = OperType.SCHEDULER)
	public List<SYS_DICTIONARY> sysDictionarySnyc(AUTHORIZATION auth, long version_no) throws RPCException, TException {

		List<SYS_DICTIONARY> sys_dictionarys = new ArrayList<SYS_DICTIONARY>();
		List<SysDictionary> sysDictionarys = dbUtil.queryListToBean("字典查询", "SELECT * FROM SYS_DICTIONARY WHERE STAT='Y'", SysDictionary.class);
		for (SysDictionary sysDictionary : sysDictionarys) {
			SYS_DICTIONARY sys_dictionary = new SYS_DICTIONARY();
			BeanUtils.copyRPCProperties(sysDictionary, sys_dictionary);

			sys_dictionarys.add(sys_dictionary);
		}

		return sys_dictionarys;
	}

	@Override
	@ControlAspect(funtionName = "参数下载", operType = OperType.SCHEDULER)
	public List<SYS_PAREMETER> sysParemeterSnyc(AUTHORIZATION auth, long version_no) throws RPCException, TException {

		List<SYS_PAREMETER> sys_paremeters = new ArrayList<SYS_PAREMETER>();
		List<SysParemeter> sysParemeters = dbUtil.queryListToBean("参数查询", "SELECT * FROM SYS_PAREMETER ", SysParemeter.class);
		for (SysParemeter sysParemeter : sysParemeters) {
			SYS_PAREMETER sys_paremeter = new SYS_PAREMETER();
			BeanUtils.copyRPCProperties(sysParemeter, sys_paremeter);

			sys_paremeters.add(sys_paremeter);
		}

		return sys_paremeters;
	}

	@Override
	@ControlAspect(funtionName = "预售期查询", operType = OperType.SCHEDULER)
	public List<SL_PERIOD> salePeriodQuery(AUTHORIZATION auth, long version_no) throws RPCException, TException {
		List<SL_PERIOD> sl_periods = new ArrayList<SL_PERIOD>();
		List<SlPeriod> slPeriods = dbUtil.queryListToBean("预售期查询", "SELECT * FROM SL_PERIOD ", SlPeriod.class);
		for (SlPeriod slPeriod : slPeriods) {
			SL_PERIOD sl_period = new SL_PERIOD();
			BeanUtils.copyRPCProperties(slPeriod, sl_period);

			sl_periods.add(sl_period);
		}

		return sl_periods;
	}

	@Override
	@ControlAspect(funtionName = "查询员工卡登记表", operType = OperType.QUERY)
	public List<SYS_EMP_REGISTER> queryEmpRegister(AUTHORIZATION auth, SYS_EMP_REGISTER sys_emp_register) throws RPCException, TException {
		List<SYS_EMP_REGISTER> sys_emp_registers = new ArrayList<SYS_EMP_REGISTER>();
		StringBuffer sql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		sql.append("SELECT * FROM SYS_EMP_REGISTER  WHERE 1=1");
		if (sys_emp_register.getEmpId() != 0) {
			sql.append(" AND EMP_ID =:EMP_ID ");
			params.put("EMP_ID", sys_emp_register.getEmpId());
		}
		if (StringUtil.isNotNull(sys_emp_register.getEmpName())) {
			sql.append(" AND EMP_NAME LIKE :EMP_NAME ");
			params.put("EMP_NAME", StringUtil.queryParam(sys_emp_register.getEmpName()));
		}

		List<SysEmpRegister> sysEmpRegisters = dbUtil.queryListToBean("员工卡表查询", sql.toString(), SysEmpRegister.class, params);
		for (SysEmpRegister sysEmpRegister : sysEmpRegisters) {
			SYS_EMP_REGISTER sys_emp_register1 = new SYS_EMP_REGISTER();
			BeanUtils.copyRPCProperties(sysEmpRegister, sys_emp_register1);
			List<SYS_EMP_VENUE> sys_emp_venues = new ArrayList<SYS_EMP_VENUE>();
			for (SysEmpVenue sysEmpVenue : sysEmpRegister.getSysEmpVenues()) {
				SYS_EMP_VENUE sys_emp_venue = new SYS_EMP_VENUE();
				BeanUtils.copyRPCProperties(sysEmpVenue, sys_emp_venue);
				sys_emp_venues.add(sys_emp_venue);
			}
			sys_emp_register1.setSysEmpVenuelist(sys_emp_venues);
			sys_emp_registers.add(sys_emp_register1);
		}
		return sys_emp_registers;
	}

	@Override
	@ControlAspect(funtionName = "保存员工卡登记表", operType = OperType.ADD)
	public boolean saveOrUpdateEmpRegister(AUTHORIZATION auth, SYS_EMP_REGISTER sys_emp_register) throws RPCException, TException {
		Session session = null;

		try {
			// 开启事务
			session = dbUtil.getSessionByTransaction();

			SysEmpRegister sysEmpRegister = new SysEmpRegister();
			List<SysEmpVenue> sysEmpVenueList = new ArrayList<SysEmpVenue>();

			BeanUtils.copyRPCProperties(sys_emp_register, sysEmpRegister);
			List<SYS_EMP_VENUE> sys_emp_venues = sys_emp_register.getSysEmpVenuelist();
			if (sys_emp_venues != null) {
				for (SYS_EMP_VENUE sys_emp_venue : sys_emp_venues) {
					SysEmpVenue sysEmpVenue = new SysEmpVenue();
					String[] ignoreProperties = { "sysEmpRegister" };
					BeanUtils.copyRPCProperties(sys_emp_venue, sysEmpVenue, ignoreProperties);
					sysEmpVenue.setSysEmpRegister(sysEmpRegister);
					sysEmpVenueList.add(sysEmpVenue);
				}
			}

			dbUtil.executeSql("删除员工通行场馆信息", session, "DELETE FROM SYS_EMP_VENUE WHERE EMP_ID=?", sys_emp_register.getEmpId());
			dbUtil.saveOrUpdateEntity("保存员工卡信息", session, sysEmpRegister);
			dbUtil.saveOrUpdateEntityBatch("批量保存员工通行场馆表", session, sysEmpVenueList);

			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}

		return true;
	}

	@Override
	@ControlAspect(funtionName = "员工卡状态启用或禁用", operType = OperType.UPDATE)
	public boolean empRegisterStat(AUTHORIZATION auth, long empId, String stat) throws RPCException, TException {
		Session session = null;
		try {
			// 开启事务
			session = dbUtil.getSessionByTransaction();
			// 操作黑名单表
			SysEmpRegister emp = dbUtil.findById("查询员工信息", SysEmpRegister.class, empId);
			if ("N".equals(stat)) {
				SysBlackList sysBlackList = new SysBlackList();
				sysBlackList.setChipId(emp.getChipId());
				sysBlackList.setBlackListId(UUIDGenerator.getPK());
				sysBlackList.setCardType("1");
				sysBlackList.setLossDt(new Date());
				sysBlackList.setTicketId(String.valueOf(emp.getEmpId()));
				sysBlackList.setStat("Y");
				sysBlackList.setLossReason("员工卡禁用");
				sysBlackList.setOpeUserId(String.valueOf(auth.getClientId()));
				sysBlackList.setOpeTime(new Date());
				sysBlackList.setVersionNo(new Date());
				dbUtil.saveEntity("加入黑名单", session, sysBlackList);
			}
			if ("Y".equals(stat)) {
				dbUtil.executeSql("删除黑名单", session, "DELETE FROM SYS_BLACK_LIST T WHERE T.CHIP_ID=?", emp.getChipId());
			}
			// 更新员工登记表状态
			dbUtil.executeSql("更新员工卡状态", session, "UPDATE SYS_EMP_REGISTER SET STAT=? WHERE EMP_ID=?", stat, empId);
			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}
		return true;
	}

	@Override
	@ControlAspect(funtionName = "员工卡补卡更新芯片ID", operType = OperType.UPDATE)
	public boolean empRegisterChipId(AUTHORIZATION auth, long empId, String chip_id) throws RPCException, TException {
		Session session = null;
		try {
			session = dbUtil.getSessionByTransaction();
			SysEmpRegister emp = dbUtil.findById("查询员工登记表旧芯片", SysEmpRegister.class, empId);
			SysBlackList sysBlackList = new SysBlackList();
			sysBlackList.setBlackListId(UUIDGenerator.getPK());
			sysBlackList.setCardType("1");
			sysBlackList.setLossDt(new Date());
			sysBlackList.setTicketId(String.valueOf(emp.getEmpId()));
			sysBlackList.setChipId(emp.getChipId());
			sysBlackList.setStat("Y");
			sysBlackList.setLossReason("补卡");
			sysBlackList.setOpeTime(new Date());
			sysBlackList.setOpeUserId(String.valueOf(auth.getClientId()));
			sysBlackList.setVersionNo(new Date());
			dbUtil.saveEntity("旧卡加入黑名单", session, sysBlackList);
			dbUtil.executeSql("更新员工卡芯片ID", session, "UPDATE SYS_EMP_REGISTER SET CHIP_ID=? WHERE EMP_ID=?", chip_id, empId);
			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}
		return true;
	}

	@Override
	@ControlAspect(funtionName = "获取系统时间", operType = OperType.SCHEDULER)
	public long getSystemDateTime(AUTHORIZATION auth) throws RPCException, TException {
		return new Date().getTime();
	}

	@Override
	@ControlAspect(funtionName = "查询、同步终端信息", operType = OperType.SCHEDULER)
	public List<SYS_CLIENT> queryClient(AUTHORIZATION auth, String clientId, String clientName, String clientType, String regionIds) throws RPCException, TException {
		List<SYS_CLIENT> sys_clients = new ArrayList<SYS_CLIENT>();
		StringBuffer sql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();

		sql.append("SELECT * FROM SYS_CLIENT WHERE 1=1 ");
		// 2-（闸机）区域编号 5-（自助售票机）网点编号
		if (StringUtil.isNotNull(clientType)) {
			// regionIds不为null，且不为0时查询所有闸机
			if (StringUtil.isNotNull(regionIds) && !"0".equals(regionIds)) {
				if ("2".equals(clientType)) {
					sql.append(" AND REGION_ID IN (" + regionIds + ")");
				} else {
					sql.append(" AND OUTLET_ID IN (" + regionIds + ")");
				}
			}
			sql.append(" AND CLIENT_TYPE=:CLIENT_TYPE");
			params.put("CLIENT_TYPE", clientType);
		}

		if (StringUtil.isNotNull(clientName)) {
			sql.append(" AND CLIENT_NAME LIKE :CLIENT_NAME");
			params.put("CLIENT_NAME", StringUtil.queryParam(clientName));
		}

		if (StringUtil.isNotNull(clientId)) {
			sql.append(" AND CLIENT_ID LIKE :CLIENT_ID");
			params.put("CLIENT_ID", StringUtil.queryParam(clientId));
		}

		List<SysClient> sysClientList = dbUtil.queryListToBean("终端信息查询", sql.toString(), SysClient.class, params);
		for (SysClient sysClient : sysClientList) {
			SYS_CLIENT sys_client = new SYS_CLIENT();
			String[] ignoreProperties = { "reportTime" };
			BeanUtils.copyRPCProperties(sysClient, sys_client, ignoreProperties);
			if (sysClient.getReportTime() == null) {
				sys_client.setReportTime(0);
			} else {
				sys_client.setReportTime(sysClient.getReportTime().getTime());
			}
			if (sysClient.getRunStat() == null) {
				sys_client.setRunStat(8);
			}
			if (sysClient.getGateMode() == null) {
				sys_client.setGateMode(0);
			}

			if ("2".equals(sysClient.getClientType())) {
				// 设置区域名称
				SysRegion sysRegion = dbUtil.findById("查询区域", SysRegion.class, sysClient.getRegionId());
				if (sysRegion != null) {
					sys_client.setRegionName(sysRegion.getRegionName());
				} else {
					sys_client.setRegionName("");
				}
				sys_clients.add(sys_client);
			}
			if ("5".equals(sysClient.getClientType())) {
				// 设置网点名称
				SysOutlet sysOutlet = dbUtil.findById("查询网点", SysOutlet.class, sysClient.getOutletId());
				if (sysOutlet != null) {
					sys_client.setOutletName(sysOutlet.getOutletName());
				} else {
					sys_client.setOutletName("");
				}
				sys_clients.add(sys_client);
			}
		}

		return sys_clients;
	}

	@Override
	@ControlAspect(funtionName = "场馆表下载", operType = OperType.SCHEDULER)
	public List<SYS_VENUE> sysVenueSnyc(AUTHORIZATION auth) throws RPCException, TException {
		List<SYS_VENUE> sys_venues = new ArrayList<SYS_VENUE>();
		List<SysVenue> sysVenues = dbUtil.queryListToBean("场馆表查询", "SELECT * FROM SYS_VENUE WHERE STAT='Y'", SysVenue.class);
		for (SysVenue sysVenue : sysVenues) {
			SYS_VENUE sys_venue = new SYS_VENUE();
			BeanUtils.copyRPCProperties(sysVenue, sys_venue);

			sys_venues.add(sys_venue);
		}

		return sys_venues;
	}

	@Override
	@ControlAspect(funtionName = "客户端日志上传", operType = OperType.SCHEDULER)
	public boolean uploadClientLog(AUTHORIZATION auth, String fileName, ByteBuffer file) throws RPCException, TException {
		Long clientId = auth.getClientId();
		if (!CLIENT_LOG_PATH.endsWith("\\") && !CLIENT_LOG_PATH.endsWith("/")) {
			CLIENT_LOG_PATH = CLIENT_LOG_PATH + "/";
		}
		String fileByDir = CLIENT_LOG_PATH + StringUtil.convertString(clientId) + "/" + fileName;

		FileOutputStream fw = null;
		try {
			// 创建目录
			FileUtil.createPathByFileName(fileByDir);
			// 创建文件
			File fileLocal = null;
			if (!FileUtil.isExistsFile(fileByDir)) {
				fileLocal = new File(fileByDir);
				fileLocal.createNewFile();
			} else {
				fileLocal = new File(fileByDir);
			}

			fw = new FileOutputStream(fileLocal, true);
			fw.write(file.array());
		} catch (InterruptedException e1) {
			logger.error("客户端日志上传错误", e1);
		} catch (IOException e) {
			logger.error("客户端日志上传错误", e);
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (Exception e) {
					logger.error("客户端日志上传错误", e);
				}
			}
		}
		return true;
	}
}
