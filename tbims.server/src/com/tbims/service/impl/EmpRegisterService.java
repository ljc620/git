package com.tbims.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tbims.bean.SysEmpRegisterBean;
import com.tbims.entity.SysBlackList;
import com.tbims.entity.SysEmpRegister;
import com.tbims.service.IEmpRegisterService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.StringUtil;
import com.zhming.support.util.UUIDGenerator;

@Service
public class EmpRegisterService extends BaseService implements IEmpRegisterService {

	@Override
	public PageBean<SysEmpRegisterBean> listEmpRegister(UserBean userBean, SysEmpRegister sysEmpRegister) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sb = " SELECT T.EMP_ID,T.EMP_NAME,T.DEPARTMENT,T.CHIP_ID,T.STAT, ";
		sb += " T.CHIP_STAT,T.CARD_TYPE,T.CARD_ID,T.MAIL,T.GENDER, ";
		sb += " T.TEL,T.VERSION_NO,T.OPE_USER_ID,T.OPE_TIME,U.USER_NAME AS OPE_USER_NAME  ";
		sb += " FROM SYS_EMP_REGISTER T ";
		sb += " INNER JOIN SYS_USER U ";
		sb += " ON U.USER_ID = T.OPE_USER_ID ";
		if (StringUtil.isNotNull(sysEmpRegister.getDepartment())) {
			sb+= " AND T.DEPARTMENT LIKE :DEPARTMENT ";
			params.put("DEPARTMENT", '%'+sysEmpRegister.getDepartment()+'%');
		}
		if (sysEmpRegister.getEmpId()!=null) {
			sb+= " AND T.EMP_ID = :EMP_ID ";
			params.put("EMP_ID", sysEmpRegister.getEmpId());
		}
		if (StringUtil.isNotNull(sysEmpRegister.getEmpName())) {
			sb+= " AND T.EMP_NAME LIKE :EMP_NAME ";
			params.put("EMP_NAME", '%'+sysEmpRegister.getEmpName()+'%');
		}
		if (StringUtil.isNotNull(sysEmpRegister.getStat())) {
			sb+= " AND T.STAT LIKE :STAT ";
			params.put("STAT", '%'+sysEmpRegister.getStat()+'%');
		}
		sb += " ORDER BY T.EMP_ID ";
		PageBean<SysEmpRegisterBean> ret = dbUtil.queryPageToBean("查询", sb, SysEmpRegisterBean.class, userBean.getPageNum(), userBean.getPageSize(), params);
		return ret;
	}

	
	@Override
	public void updateStat(UserBean userBean, String empId, String stat) throws BaseException {
			String[] a=empId.split(",");
		
			for (int i = 0; i < a.length; i++) {
				String sql ="SELECT T.* FROM SYS_EMP_REGISTER T WHERE T.EMP_ID="+a[i]+"";
				List<SysEmpRegister> sysEmpRegisters = dbUtil.queryListToBean("", sql, SysEmpRegister.class);
				for (SysEmpRegister map : sysEmpRegisters) {
					SysBlackList sysBlackList = new SysBlackList();
					sysBlackList.setChipId(map.getChipId());
				if("N".equals(stat)){
					sysBlackList.setBlackListId(UUIDGenerator.getPK());
					sysBlackList.setCardType("1");
					sysBlackList.setLossDt(new Date());
					sysBlackList.setLossReason("员工卡禁用自动添加黑名单");
					sysBlackList.setOpeTime(new Date());
					sysBlackList.setVersionNo(new Date());
					sysBlackList.setOpeUserId(userBean.getUserId());
					sysBlackList.setStat("Y");
					sysBlackList.setTicketId(String.valueOf(map.getEmpId()));
					String sb = "SELECT * FROM SYS_BLACK_LIST T WHERE T.TICKET_ID="+String.valueOf(map.getEmpId())+" AND T.CHIP_ID="+map.getChipId()+" ";
					int sum =dbUtil.count("", sb);
					if (sum !=0) {
						throw new ServiceException(MSG.ERROR, "黑名单票号已存在");
					}
					dbUtil.saveEntity("", sysBlackList);
					dbUtil.executeSql("", "UPDATE SYS_EMP_REGISTER SET STAT=? WHERE EMP_ID IN (" + empId + ")", stat);
				}	
				if("Y".equals(stat)){
					if(sysBlackList.getChipId()!=null){
					dbUtil.executeSql("", "DELETE  FROM SYS_BLACK_LIST T WHERE T.CHIP_ID IN (" + sysBlackList.getChipId() + ")");
					dbUtil.executeSql("", "UPDATE SYS_EMP_REGISTER SET STAT=? WHERE EMP_ID IN (" + empId + ")", stat);
					}else{
						throw new ServiceException(MSG.ERROR, "黑名单芯片号不存在");
					}
				}		
				}
				
			}

	}


	@Override
	public SysEmpRegister photo( Long empId) {
		SysEmpRegister empRegister= dbUtil.findById("", SysEmpRegister.class,empId );
		return empRegister;
		
	}
	

}
