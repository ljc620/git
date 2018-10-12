package com.tbims.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tbims.entity.SlChangeUser;
import com.tbims.entity.SysDictionary;
import com.tbims.service.IChangeUserService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.ServiceException;

@Service
public class ChangeUserService extends BaseService implements IChangeUserService {

	@Override
	public PageBean<Map<String, Object>> listChangeUser(UserBean userBean, SlChangeUser slChangeUser) {
		Map<String, Object> map = new HashMap<>();
		map.put("ORG_ID", userBean.getOrgId());
		String sql = "SELECT * FROM SL_CHANGE_USER T WHERE ORG_ID=:ORG_ID ";
		PageBean<Map<String, Object>> ret=dbUtil.queryPageToMap("查询换票人", sql, userBean.getPageNum(), userBean.getPageSize(), map);
		return ret;
	}

	@Override
	public void addChangeUser(UserBean userBean, SlChangeUser slChangeUser) throws ServiceException {
		slChangeUser.setOrgId(userBean.getOrgId());
		dbUtil.saveEntity("保存换票人", slChangeUser);
	}

	@Override
	public void updateChangeUser(UserBean userBean, SlChangeUser slChangeUser) {
		dbUtil.updateEntity("更新换票人", slChangeUser);
	}

	@Override
	public void delChangeUser(UserBean userBean, String changeUserId) {
		dbUtil.deleteEntity("删除换票人", SlChangeUser.class, changeUserId);
	}

	@Override
	public String getCareType(String changeUserId) throws ServiceException {
		String ret="";
		Map<String, Object> map = new HashMap<>();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT V.*");
		sb.append("   FROM SL_CHANGE_USER TV ");
		sb.append("  INNER JOIN SYS_DICTIONARY V ");
		sb.append("     ON TV.CARD_TYPE = V.ITEM_CD ");
		sb.append("  WHERE TV.CHANGE_USER_ID = :CHANGE_USER_ID ");
		map.put("CHANGE_USER_ID", changeUserId);
		String sql=sb.toString();
		List<SysDictionary> venueList=dbUtil.queryListToBean("", sql, SysDictionary.class, map);
		for(SysDictionary sysDictionary:venueList){
			ret+=sysDictionary.getItemName()+",";
		}
		if(ret.indexOf(",")!=-1){
			ret=ret.substring(0, ret.length()-1);
		}
		return ret;
	}

	@Override
	public SlChangeUser getById(String changeUserId) {
		SlChangeUser slChangeUser=dbUtil.findById("", SlChangeUser.class, changeUserId);
		return slChangeUser;
	}

}
