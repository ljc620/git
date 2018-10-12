package com.tbims.rpc.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tbims.annontion.ControlAspect;
import com.tbims.cache.ConfigUtil;
import com.tbims.common.OperType;
import com.tbims.db.entity.SlPeriod;
import com.tbims.db.entity.SysBlackList;
import com.tbims.db.entity.SysDictionary;
import com.tbims.db.entity.SysParemeter;
import com.tbims.db.entity.SysTicketType;
import com.tbims.db.entity.SysTicketTypeRule;
import com.tbims.db.entity.SysTicketTypeVenue;
import com.tbims.db.util.DBUtil;
import com.tbims.init.ServerMain;
import com.tbims.rpc.entity.AUTHORIZATION;
import com.tbims.rpc.entity.RPCException;
import com.tbims.rpc.entity.SL_ORDER_DETAIL;
import com.tbims.rpc.entity.SL_ORDER_TICKETTYPE_DETAIL;
import com.tbims.rpc.entity.SL_PERIOD;
import com.tbims.rpc.entity.SYS_BLACK_LIST;
import com.tbims.rpc.entity.SYS_CLIENT;
import com.tbims.rpc.entity.SYS_DICTIONARY;
import com.tbims.rpc.entity.SYS_EMP_REGISTER;
import com.tbims.rpc.entity.SYS_PAREMETER;
import com.tbims.rpc.entity.SYS_TICKET_TYPE;
import com.tbims.rpc.entity.SYS_TICKET_TYPE_RULE;
import com.tbims.rpc.entity.SYS_TICKET_TYPE_VENUE;
import com.tbims.rpc.entity.SYS_VENUE;
import com.tbims.rpc.entity.USELESS_TICKET_DETAIL;
import com.tbims.util.BeanUtils;
import com.tbims.util.FileUtil;
import com.tbims.util.StringUtil;

@Component
public class DataSyncServiceImpl implements com.tbims.rpc.service.DataSyncService.Iface {
	private static final Log logger = LogFactory.getLog(ServerMain.class);
	/**
	 * 客户端同步日志保存路径
	 */
	private static String CLIENT_LOG_PATH = ConfigUtil.configs.get("client.log.path");

	@Autowired
	DBUtil dbUtil;

	@Override
	@ControlAspect(funtionName = "同步终端状态", operType = OperType.SCHEDULER)
	public boolean gateStateSnyc(AUTHORIZATION auth, SYS_CLIENT sys_client) throws RPCException, TException {
		return false;
	}

	@Override
	@ControlAspect(funtionName = "员工卡表下载", operType = OperType.SCHEDULER)
	public List<SYS_EMP_REGISTER> empRegisterSnyc(AUTHORIZATION auth, long version_no) throws RPCException, TException {
		return new ArrayList<SYS_EMP_REGISTER>();
	}

	@Override
	@ControlAspect(funtionName = "票种下载", operType = OperType.SCHEDULER)
	public List<SYS_TICKET_TYPE> ticketTypeSnyc(AUTHORIZATION auth, long version_no) throws RPCException, TException {

		List<SYS_TICKET_TYPE> sys_ticket_types = new ArrayList<SYS_TICKET_TYPE>();
		List<SysTicketType> sysTicketTypeList = dbUtil.queryListToBean("票种查询", "SELECT * FROM SYS_TICKET_TYPE WHERE TICKET_TYPE_STAT='Y'  AND TEAM_FLAG!='Y' ", SysTicketType.class);
		for (SysTicketType ticketType : sysTicketTypeList) {
			SYS_TICKET_TYPE sys_ticket_type = new SYS_TICKET_TYPE();
			BeanUtils.copyRPCProperties(ticketType, sys_ticket_type);

			List<SYS_TICKET_TYPE_RULE> sys_ticket_type_rules = new ArrayList<SYS_TICKET_TYPE_RULE>();
			List<SysTicketTypeRule> sysTicketTypeRuleList = dbUtil.queryListToBean("票种规则查询", "SELECT * FROM SYS_TICKET_TYPE_RULE WHERE TICKET_TYPE_ID=?", SysTicketTypeRule.class,
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
		List<SysBlackList> sysBlackLists = dbUtil.queryListToBean("黑名单查询", "SELECT * FROM SYS_BLACK_LIST WHERE  STAT='Y' AND  VERSION_NO>=?", SysBlackList.class, new Date(version_no));
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
	@ControlAspect(funtionName = "查询员工卡登记表", operType = OperType.SCHEDULER)
	public List<SYS_EMP_REGISTER> queryEmpRegister(AUTHORIZATION auth, SYS_EMP_REGISTER sys_emp_register) throws RPCException, TException {
		return new ArrayList<SYS_EMP_REGISTER>();
	}

	@Override
	@ControlAspect(funtionName = "保存员工卡登记表", operType = OperType.ADD)
	public boolean saveOrUpdateEmpRegister(AUTHORIZATION auth, SYS_EMP_REGISTER sys_emp_register) throws RPCException, TException {
		return false;
	}

	@Override
	@ControlAspect(funtionName = "获取系统时间", operType = OperType.SCHEDULER)
	public long getSystemDateTime(AUTHORIZATION auth) throws TException {
		return new Date().getTime();
	}

	@Override
	public List<SYS_CLIENT> queryClient(AUTHORIZATION auth, String clientId, String clientName, String clientType, String regionIds) throws RPCException, TException {
		return null;
	}

	@Override
	public List<SYS_VENUE> sysVenueSnyc(AUTHORIZATION auth) throws RPCException, TException {
		return null;
	}

	@Override
	public boolean ejectTicketStatSync(AUTHORIZATION auth, int state, String ticket_num) throws RPCException, TException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean empRegisterStat(AUTHORIZATION auth, long empId, String stat) throws RPCException, TException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean empRegisterChipId(AUTHORIZATION auth, long empId, String chip_id) throws RPCException, TException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<USELESS_TICKET_DETAIL> queryUselessTicket(AUTHORIZATION auth, long outlet_id) throws RPCException, TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SL_ORDER_TICKETTYPE_DETAIL> querySaleSupplyTicketG(AUTHORIZATION auth, String pay_id) throws RPCException, TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SL_ORDER_DETAIL> querySaleSupplyTicketQ(AUTHORIZATION auth, String pay_id) throws RPCException, TException {
		// TODO Auto-generated method stub
		return null;
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
			logger.debug("客户端日志上传错误", e1);
		} catch (IOException e) {
			logger.debug("客户端日志上传错误", e);
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (Exception e) {
					logger.debug("客户端日志上传错误", e);
				}
			}
		}
		return true;
	}
}
