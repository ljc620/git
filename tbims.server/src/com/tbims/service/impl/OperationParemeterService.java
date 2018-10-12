package com.tbims.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tbims.entity.SysParemeter;
import com.tbims.service.IOperationParemeterService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.ServiceException;


@Service
public class OperationParemeterService extends BaseService implements IOperationParemeterService{

	@Override
	public PageBean<Map<String, Object>> listOperationParemeter(UserBean userBean, SysParemeter sysParemeter) {
		String sql = "SELECT T.* FROM SYS_PAREMETER T WHERE T.PAREMETER_TYPE='1' ORDER BY PAREMETER_ID DESC";
		PageBean<Map<String, Object>> ret=dbUtil.queryPageToMap("查询运营参数", sql, userBean.getPageNum(), userBean.getPageSize());
		return ret;
	}

	@Override
	public void addOperationParemeter(UserBean userBean, SysParemeter sysParemeter) throws ServiceException {
		SysParemeter paremeter=dbUtil.findById("", SysParemeter.class, sysParemeter.getParemeterId());
		if(paremeter!=null&&"1".equals(paremeter.getParemeterType())){
			throw new  ServiceException(MSG.ERROR,"运营参数ID已存在");
		}
		sysParemeter.setOpeTime(new Date());
		sysParemeter.setOpeUserId(userBean.getUserId());
		sysParemeter.setParemeterType("1");
		dbUtil.saveEntity("保存运营参数", sysParemeter);
	}
	@Override
	public SysParemeter getById(String paremeterId) {
		SysParemeter sysParemeter=dbUtil.findById("", SysParemeter.class, paremeterId);
		return sysParemeter;
	}
	
	@Override
	public void updateOperationParemeter(UserBean userBean, SysParemeter sysParemeter) {
		sysParemeter.setOpeTime(new Date());
		sysParemeter.setOpeUserId(userBean.getUserId());
		sysParemeter.setOpeTime(new Date());
		sysParemeter.setParemeterType("1");
		dbUtil.updateEntity("", sysParemeter);
	}
	@Override
	public void delChangeUser(UserBean userBean, String paremeterId) {
		dbUtil.deleteEntity("删除运营参数", SysParemeter.class, paremeterId);
	}

	

}
