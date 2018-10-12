package com.tbims.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.tbims.entity.SysClient;
import com.tbims.entity.SysRegion;
import com.tbims.service.IClientMngService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.DBException;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.StringUtil;
import com.zhming.support.util.UUIDGenerator;

@Service
public class ClientMngService extends BaseService implements IClientMngService {

	@Override
	public PageBean<Map<String, Object>> listClient(UserBean loginUserBean, SysClient sysClient) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT C.CLIENT_ID,C.CLIENT_NAME,");
		sql.append(" C.CLIENT_TYPE,C.REGION_ID,R.REGION_NAME,  ");
		sql.append(" O.OUTLET_ID,O.OUTLET_NAME,C.IP_ADDR,C.PORT, ");
		sql.append(" C.STAT,C.TOKEN ,C.REPORT_TIME,D.ITEM_NAME ");
		sql.append(" FROM SYS_CLIENT C ");
		sql.append(" LEFT JOIN SYS_REGION R ON R.REGION_ID=C.REGION_ID ");
		sql.append(" LEFT JOIN SYS_OUTLET O ON O.OUTLET_ID=C.OUTLET_ID ");
		sql.append(" LEFT JOIN SYS_DICTIONARY D ON D.ITEM_CD=C.CLIENT_TYPE ");
		sql.append(" WHERE 1=1 AND D.KEY_CD = 'CLIENT_TYPE' ");
		// 终端名称
		if (StringUtil.isNotNull(sysClient.getClientName())) {
			sql.append(" AND C.CLIENT_NAME LIKE :CLIENT_NAME");
			params.put("CLIENT_NAME", "%" + sysClient.getClientName() + "%");
		}
		// 终端类型
		if (StringUtil.isNotNull(sysClient.getClientType())) {
			sql.append(" AND C.CLIENT_TYPE=:CLIENT_TYPE");
			params.put("CLIENT_TYPE", sysClient.getClientType());
		}
		// 终端状态
		if (StringUtil.isNotNull(sysClient.getStat())) {
			sql.append(" AND C.STAT=:STAT");
			params.put("STAT", sysClient.getStat());
		}
		//所属网点
		if (sysClient.getOutletId()!= null) {
			sql.append("  AND C.OUTLET_ID=:OUTLET_ID ");
			params.put("OUTLET_ID", sysClient.getOutletId());
		}
		//区域
		if (sysClient.getRegionId()!= null) {
			sql.append("  AND C.REGION_ID=:REGION_ID ");
			params.put("REGION_ID", sysClient.getRegionId());
		}
		sql.append(" ORDER BY C.CLIENT_ID");
		PageBean<Map<String, Object>> pageBean = dbUtil.queryPageToMap("查询终端列表", sql.toString(), loginUserBean.getPageNum(), loginUserBean.getPageSize(), params);
		return pageBean;
	}

	@Override
	public SysClient getClientById(Long clientId) throws DBException {
		return dbUtil.findById("查询修改之前的终端类型", SysClient.class, clientId);
	}

	@Override
	public void updateClient(UserBean loginUserBean, SysClient sysClient) throws BaseException {
		SysClient tempClient = getClientById(sysClient.getClientId());
		tempClient.setClientName(sysClient.getClientName());
		tempClient.setIpAddr(sysClient.getIpAddr());
		tempClient.setPort(sysClient.getPort());
		tempClient.setStat(sysClient.getStat());
		tempClient.setOpeUserId(loginUserBean.getUserId());
		tempClient.setOpeTime(new Date());
		
		tempClient.setRegionId(sysClient.getRegionId());
		
		//判断闸机ip地址不能重复
		if("2".equals(sysClient.getClientType())){
			boolean flag=isExistUpdate(sysClient.getClientId(),sysClient.getIpAddr());//修改时不能重复
			if(!flag){
				throw new ServiceException(MSG.BF_ERROR, "该闸机的IP地址已使用，请重新设置");
			}
		}
		dbUtil.updateEntity("修改终端", tempClient);
	}
	private boolean isExistUpdate(Long clientId, String ipAddr) {
		List<SysClient> clist=dbUtil.queryListToBean("查询闸机ip地址", "SELECT * FROM SYS_CLIENT T WHERE T.CLIENT_TYPE='2' AND T.IP_ADDR=?",SysClient.class, ipAddr);
		boolean flag=true;
		if(!clist.isEmpty()||clist.size()!=0){
			for(SysClient client:clist){
				if(client.getClientId().longValue()!=clientId.longValue()){
					flag=false;
				}
			}
		}
		return flag;
	}

	@Override
	public void delClient(Long clientId) throws DBException {
		dbUtil.deleteEntity("删除终端", SysClient.class, clientId);
	}
	@Override
	public void addClient(UserBean loginUserBean, SysClient sysClient) throws BaseException {
		sysClient = getClient(sysClient);
		sysClient.setOpeUserId(loginUserBean.getUserId());
		sysClient.setOpeTime(new Date());
		//判断闸机ip地址不能重复
		if("2".equals(sysClient.getClientType())){
			boolean flag=isExistAdd(sysClient.getIpAddr());//添加时不能重复
			if(!flag){
				throw new ServiceException(MSG.BF_ERROR, "该闸机的IP地址已使用，请重新设置");
			}
		}
		dbUtil.saveEntity("保存终端信息", sysClient);
	}
	private boolean isExistAdd(String ipAddr) {
		int count=dbUtil.count("查询闸机ip地址", "SELECT * FROM SYS_CLIENT T WHERE T.CLIENT_TYPE='2' AND T.IP_ADDR=?", ipAddr);
		boolean flag=true;
		if(count!=0){
			flag=false;
		}
		return flag;
	}

	private SysClient getClient(SysClient sysClient) {
		String clientId = "";// 根据规则生成的终端编号
		String seqRule = "";// 生成规则
		// 终端类型-网点
		if ("1".equals(sysClient.getClientType())) {
			seqRule = sysClient.getClientType() + StringUtil.stringFillOrCut(String.valueOf(sysClient.getOutletId()), "0", 3, 1);
			clientId = getSeq(seqRule, 3);
		}
		// 终端类型-闸机
		if ("2".equals(sysClient.getClientType())) {
			SysRegion sysRegion = dbUtil.findById("获取区域对应的场馆编号", SysRegion.class, sysClient.getRegionId());
			String regionId = StringUtil.stringFillOrCut(String.valueOf(sysClient.getRegionId()), "0", 2, 1);
			String venueId = StringUtil.stringFillOrCut(String.valueOf(sysRegion.getVenueId()), "0", 2, 1);
			seqRule = sysClient.getClientType() + venueId + regionId;
			clientId = getSeq(seqRule, 2);
			sysClient.setToken(UUIDGenerator.getRandomString(32));// 授权码
		}
		// 终端类型-缓存服务器
		if ("3".equals(sysClient.getClientType())) {
			seqRule = sysClient.getClientType();
			clientId = getSeq(seqRule, 6);
			sysClient.setToken(UUIDGenerator.getRandomString(32));// 授权码
		}
		// 终端类型-控制终端
		if ("4".equals(sysClient.getClientType())) {
			seqRule = sysClient.getClientType();
			clientId = getSeq(seqRule, 6);
		}
		// 终端类型-自助售票机
		if ("5".equals(sysClient.getClientType())) {
			seqRule = sysClient.getClientType() + StringUtil.stringFillOrCut(String.valueOf(sysClient.getOutletId()), "0", 3, 1);
			clientId = getSeq(seqRule, 3);
			sysClient.setToken(UUIDGenerator.getRandomString(32));// 授权码
		}
		sysClient.setClientId(Long.parseLong(clientId));
		return sysClient;
	}

	@Override
	public void updateStat(UserBean loginUserBean, String clientIds, String stat) throws DBException {
		dbUtil.executeSql("", "UPDATE SYS_CLIENT SET STAT=? WHERE CLIENT_ID IN (" + clientIds + ")", stat);
	}

	@Override
	public void updateToken(UserBean loginUserBean, String clientIds) throws DBException {
		Session session = dbUtil.getSessionByTransaction();
		try{
			String[] clientStr = clientIds.split(",");
			int a = clientStr.length;
			String[] tokenIds = new String[a];
			for (int i = 0; i < tokenIds.length; i++) {
				tokenIds[i] = UUIDGenerator.getRandomString(32);
			}
			for (int i = 0; i < clientStr.length; i++) {
				dbUtil.executeSql("", session, "UPDATE SYS_CLIENT SET TOKEN=? WHERE CLIENT_ID=" + clientStr[i], tokenIds[i]);
			}
			dbUtil.commit(session);
		}
		finally{
			dbUtil.close(session);
		}
	}
}
