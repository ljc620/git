package com.tbims.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tbims.entity.SysParemeter;
import com.tbims.service.ISaleParemeterService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.ServiceException;

@Service
public class SaleParemeterService extends BaseService implements ISaleParemeterService{

	@Override
	public PageBean<Map<String, Object>> listSaleParemeter(UserBean userBean, SysParemeter sysParemeter) {
		String sql = "SELECT * FROM SYS_PAREMETER T WHERE PAREMETER_TYPE='0' ORDER BY PAREMETER_ID DESC ";
		PageBean<Map<String, Object>> ret=dbUtil.queryPageToMap("查询销售参数", sql, userBean.getPageNum(), userBean.getPageSize());
		return ret;
	}

	@Override
	public void addSaleParemeter(UserBean userBean, SysParemeter sysParemeter) throws ServiceException {
		SysParemeter paremeter=dbUtil.findById("", SysParemeter.class, sysParemeter.getParemeterId());
		if(paremeter!=null&&"0".equals(paremeter.getParemeterType())){
			throw new  ServiceException(MSG.ERROR,"销售参数ID已存在");
		}
		sysParemeter.setOpeTime(new Date());
		sysParemeter.setOpeUserId(userBean.getUserId());
		sysParemeter.setParemeterType("0");
		dbUtil.saveEntity("保存销售参数", sysParemeter);
	}

	@Override
	public void updateSaleParemeter(UserBean userBean, SysParemeter sysParemeter) {
		sysParemeter.setOpeTime(new Date());
		sysParemeter.setOpeUserId(userBean.getUserId());
		sysParemeter.setOpeTime(new Date());
		sysParemeter.setParemeterType("0");
		dbUtil.updateEntity("更新销售参数", sysParemeter);
	}

	@Override
	public void delSaleParemeter (UserBean userBean, String paremeterId) {
		dbUtil.deleteEntity("删除销售参数", SysParemeter.class, paremeterId);
	}

	@Override
	public SysParemeter getById(String paremeterId) {
		SysParemeter sysParemeter=dbUtil.findById("", SysParemeter.class, paremeterId);
		return sysParemeter;
	}

}
