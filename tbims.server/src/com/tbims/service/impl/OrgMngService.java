package com.tbims.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.tbims.bean.OrgSaleTicketType;
import com.tbims.bean.SlAdvanceBean;
import com.tbims.bean.SlDepositBean;
import com.tbims.bean.SlLimitBean;
import com.tbims.entity.SlAdvance;
import com.tbims.entity.SlDeposit;
import com.tbims.entity.SlLimit;
import com.tbims.entity.SlLimitAmt;
import com.tbims.entity.SlLimitAmtId;
import com.tbims.entity.SlOrg;
import com.tbims.entity.SlOrgSaleinfo;
import com.tbims.entity.SlOrgSaleinfoId;
import com.tbims.entity.SysOutlet;
import com.tbims.entity.SysTicketType;
import com.tbims.service.IOrgMngService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.StringUtil;
import com.zhming.support.util.UUIDGenerator;
@Service
public class OrgMngService extends BaseService implements IOrgMngService {
	@Override
	public PageBean<Map<String, Object>> listOrg(UserBean userBean, SlOrg slOrg, String orgBtype) {
		Map<String, Object> map = new HashMap<>();
		String sql = "SELECT * FROM SL_ORG T WHERE 1=1";
		if (slOrg != null && StringUtil.isNotNull(slOrg.getOrgName())) {
			sql += " AND ORG_NAME LIKE :ORG_NAME";
			map.put("ORG_NAME", "%" + slOrg.getOrgName() + "%");
		}
		if ("org".equals(orgBtype)) {
			sql += " AND ORG_TYPE = '0' "; //签约社
		}
		else if ("wlagent".equals(orgBtype)) {
			sql += " AND ORG_TYPE = '1' "; //网络代理商
		}else if("stagent".equals(orgBtype)){
			sql += " AND ORG_TYPE = '2' "; //实体代理商
		}
		sql += " ORDER BY T.ORG_TYPE,T.ORG_NAME ";
		PageBean<Map<String, Object>> ret = dbUtil.queryPageToMap("查询签约社列表", sql, userBean.getPageNum(), userBean.getPageSize(), map);
		return ret;
	}
	@Override
	public void addOrg(UserBean userBean, SlOrg slOrg) throws ServiceException {
		if ("1".equals(slOrg.getOrgType())) {
			slOrg.setToken(UUIDGenerator.getRandomString(32));// 设置授权码
		}
		slOrg.setOrgStat("Y");
		slOrg.setOpeUserId(userBean.getUserId());
		slOrg.setVersionNo(new Date());
		slOrg.setAdvanceAmt(0l);
		slOrg.setAdvanceFrozeAmt(0l);
		if (slOrg.getCreditAmt() == null) {
			slOrg.setCreditAmt(new BigDecimal(0));
		}
		slOrg.setDepositAmt(0l);
		slOrg.setOpeTime(new Date());
		slOrg.setCallbackErrorCount(0l);
		Session session = dbUtil.getSessionByTransaction();
		try {
		dbUtil.saveEntity("保存机构",session, slOrg);
		if(!"1".equals(slOrg.getOrgType())){
		String sql="SELECT MAX(T.OUTLET_ID) MAX_OUTLET_ID from SYS_OUTLET t ";
		Long maxOutletId=(Long) dbUtil.queryFirstForMap("查询最大的网点编号", sql).get("maxOutletId");
		Long newOutletId= 0l;
		if(maxOutletId>=500){
		  newOutletId= maxOutletId+1;
		}
		else{
			newOutletId=500l;
		}
		SysOutlet sysOutlet = new SysOutlet();
		sysOutlet.setOutletId(newOutletId);
		sysOutlet.setLeader(slOrg.getLegal());
		sysOutlet.setLocation(slOrg.getLocation());
		sysOutlet.setOutletName(slOrg.getOrgName());
		sysOutlet.setOrgId(slOrg.getOrgId());
		sysOutlet.setTel(slOrg.getTel());
		sysOutlet.setStat("Y");
		if("0".equals(slOrg.getOrgType())){
		sysOutlet.setOutletType("06");
		}
		else if("2".equals(slOrg.getOrgType())){
			sysOutlet.setOutletType("05");	
		}
		dbUtil.saveEntity("保存网点", session, sysOutlet);
		}
		dbUtil.commit(session);
	} finally {
		dbUtil.close(session);
	}

	}

	@Override
	public SlOrg getOrgById(String orgId) {
		SlOrg slOrg = dbUtil.findById("查询签约社", SlOrg.class, orgId);
		return slOrg;
	}

	@Override
	public void updateOrg(UserBean userBean, SlOrg slOrg) {
		SlOrg slOrgNew = dbUtil.findById("", SlOrg.class, slOrg.getOrgId());
		slOrgNew.setContact(slOrg.getContact());
		slOrgNew.setLegal(slOrg.getLegal());
		slOrgNew.setLocation(slOrg.getLocation());
		slOrgNew.setCreditAmt(slOrg.getCreditAmt());
		slOrgNew.setOrgName(slOrg.getOrgName());
		slOrgNew.setTel(slOrg.getTel());
		slOrgNew.setOpeUserId(userBean.getUserId());
		slOrgNew.setOrgType(slOrg.getOrgType());
		slOrgNew.setOpeTime(new Date());
		slOrgNew.setVersionNo(new Date());
		dbUtil.updateEntity("更新签约社", slOrgNew);
	}
	@Override
	public void updateOrgStat(UserBean userBean, String orgId, String stat) {
		SlOrg slOrg = dbUtil.findById("查询签约社", SlOrg.class, orgId);
		slOrg.setOrgStat(stat);
		dbUtil.updateEntity("更新签约社", slOrg);
	}
	@Override
	public void delOrg(UserBean userBean, String orgId) {
		dbUtil.deleteEntity("删除签约社", SlOrg.class, orgId);
	}

	@Override
	public PageBean<Map<String, Object>> listAmtLimit(UserBean userBean, String orgId) {
		Map<String, Object> map = new HashMap<>();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT T.ORG_ID,TICKET_TYPE.TICKET_TYPE_ID,TICKET_TYPE.TICKET_TYPE_NAME,T.LIMIT_AMT, T.FROZE_LIMIT ");
		sb.append(" FROM SL_LIMIT_AMT T ");
		sb.append(" INNER JOIN SYS_TICKET_TYPE TICKET_TYPE ");
		sb.append(" ON T.TICKET_TYPE_ID = TICKET_TYPE.TICKET_TYPE_ID ");
		sb.append(" WHERE T.ORG_ID =:ORG_ID  ORDER BY T.OPE_TIME DESC");
		String sql = sb.toString();
		map.put("ORG_ID", orgId);
		PageBean<Map<String, Object>> ret = dbUtil.queryPageToMap("查询限额列表", sql, userBean.getPageNum(), userBean.getPageSize(), map);
		return ret;
	}
	@Override
	public PageBean<SlLimitBean> listLimit(UserBean userBean, String orgId, String ticketTypeId) {
		Map<String, Object> map = new HashMap<>();
		String sql = " SELECT L.*,U.USER_NAME AS OPE_USER_NAME FROM SL_LIMIT L INNER JOIN SYS_USER U ON U.USER_ID=L.OPE_USER_ID ";
				sql+= " WHERE L.ORG_ID=:ORG_ID AND L.TICKET_TYPE_ID=:TICKET_TYPE_ID AND L.OPE_USER_ID=:OPE_USER_ID ORDER BY L.OPE_TIME DESC ";
		map.put("ORG_ID", orgId);
		map.put("TICKET_TYPE_ID", ticketTypeId);
		map.put("OPE_USER_ID", userBean.getUserId());
		PageBean<SlLimitBean> ret = dbUtil.queryPageToBean("查询额度明细", sql, SlLimitBean.class, userBean.getPageNum(), userBean.getPageSize(), map);
		return ret;
	}
	@Override
	public void addLimit(UserBean userBean, SlLimit slLimit) throws ServiceException {
		Session session = dbUtil.getSessionByTransaction();
		try {
			// 第一步：添加额度信息表
			slLimit.setLimitId(UUIDGenerator.getPK());
			slLimit.setOpeTime(new Date());
			slLimit.setOpeUserId(userBean.getUserId());
			dbUtil.saveEntity("添加额度信息", session, slLimit);
			// 第二步：判断额度余额表，如果余额表为空，初始化余额表，不为空，在余额上增加值
			String sqlCount = "SELECT * FROM SL_LIMIT_AMT T WHERE T.ORG_ID=:ORG_ID AND T.TICKET_TYPE_ID=:TICKET_TYPE_ID ORDER BY OPE_TIME DESC";
			Map<String, Object> paramsMap = new HashMap<>();
			paramsMap.put("ORG_ID", slLimit.getOrgId());
			paramsMap.put("TICKET_TYPE_ID", slLimit.getTicketTypeId());
			int countAmt = dbUtil.count("查询余额表是否有记录", sqlCount, paramsMap);
			if (countAmt == 0) {
				// 如果额度为0并且是扣减，则提示余额不足
				if (slLimit.getLimit() < 0) {
					throw new ServiceException(MSG.BF_ERROR_AMT);
				}
				SlLimitAmt slLimitAmt = new SlLimitAmt();
				slLimitAmt.setFrozeLimit(0l);
				slLimitAmt.setLimitAmt(slLimit.getLimit());
				slLimitAmt.setId(new SlLimitAmtId(slLimit.getOrgId(), slLimit.getTicketTypeId()));
				slLimitAmt.setOpeTime(new Date());
				slLimitAmt.setOptUserId(userBean.getUserId());
				dbUtil.saveEntity("添加额度余额信息", session, slLimitAmt);

			}
			else {
				SlLimitAmt slLimitAmt = dbUtil.findById("查询额度余额表", SlLimitAmt.class, new SlLimitAmtId(slLimit.getOrgId(), slLimit.getTicketTypeId()));
				if (slLimitAmt.getLimitAmt() + slLimit.getLimit() < 0) {
					throw new ServiceException(MSG.BF_ERROR_AMT);
				}
				slLimitAmt.setLimitAmt(slLimitAmt.getLimitAmt() + slLimit.getLimit());
				dbUtil.updateEntity("更新额度余额表", session, slLimitAmt);
			}
			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}

	}

	@Override
	public List<SysTicketType> ticketTypeList(String flag) {
		String sql = "SELECT * FROM SYS_TICKET_TYPE";
		if ("t".equals(flag)) {
			sql += " WHERE TEAM_FLAG='Y'";
		}
		if("nt".equals(flag)){
			sql += " WHERE TEAM_FLAG='N'";
		}
		sql += " ORDER BY TICKET_TYPE_ID";
		List<SysTicketType> ticketTypeList = dbUtil.queryListToBean("查询票种列表", sql, SysTicketType.class);
		return ticketTypeList;
	}

	@Override
	public SlOrg getOrg(String orgId) {
		SlOrg slOrg = dbUtil.findById("查询机构信息", SlOrg.class, orgId);
		return slOrg;
	}

	@Override
	public void addAdvance(UserBean userBean, SlAdvance slAdvance) throws ServiceException {
		Session session = dbUtil.getSessionByTransaction();
		try {
			slAdvance.setAdvanceId(UUIDGenerator.getPK());
			slAdvance.setOpeUserId(userBean.getUserId());
			slAdvance.setOpeTime(new Date());
			dbUtil.saveEntity("添加预付款", session, slAdvance);
			SlOrg slOrg = dbUtil.findById("查询机构表", SlOrg.class, slAdvance.getOrgId());
			if (slOrg.getAdvanceAmt() + slAdvance.getAdvanceAmt() < 0) {
				throw new ServiceException(MSG.BF_ERROR_AMT);
			}
			slOrg.setAdvanceAmt(slOrg.getAdvanceAmt() + slAdvance.getAdvanceAmt());
			dbUtil.updateEntity("更新机构表", session, slOrg);
			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}
	}

	@Override
	public PageBean<SlAdvanceBean> listAdvance(UserBean userBean, String orgId) {
		Map<String, Object> map = new HashMap<>();
		String sql = "SELECT L.*, U.USER_NAME AS OPE_USER_NAME FROM SL_ADVANCE L INNER JOIN SYS_USER U ON U.USER_ID = L.OPE_USER_ID ";
		       sql+=" WHERE L.ORG_ID = :ORG_ID   ORDER BY L.OPE_TIME DESC ";
		       map.put("ORG_ID", orgId);
		       // map.put("OPE_USER_ID", userBean.getUserId());
		PageBean<SlAdvanceBean> ret = dbUtil.queryPageToBean("查询预付款列表", sql, SlAdvanceBean.class, userBean.getPageNum(), userBean.getPageSize(), map);
		return ret;
	}

	@Override
	public PageBean<SlDepositBean> listDeposit(UserBean userBean, String orgId) {
		Map<String, Object> map = new HashMap<>();
		String sql = "SELECT D.*,U.USER_NAME ope_User_Name FROM SL_DEPOSIT D INNER　JOIN  SYS_USER U ON U.USER_ID = D.OPE_USER_ID WHERE D.ORG_ID=:ORG_ID ORDER BY D.OPE_TIME DESC";
		map.put("ORG_ID", orgId);
		PageBean<SlDepositBean> ret = dbUtil.queryPageToBean("查询保证金列表", sql, SlDepositBean.class, userBean.getPageNum(), userBean.getPageSize(), map);
		return ret;
	}

	@Override
	public void addDeposit(UserBean userBean, SlDeposit slDeposit) {
		slDeposit.setDepositId(UUIDGenerator.getPK());
		slDeposit.setOpeTime(new Date());
		slDeposit.setOpeUserId(userBean.getUserId());
		dbUtil.saveEntity("保存保证金", slDeposit);
	}
	@Override
	public Long getDepositSum(String orgId) {
		Long ret = 0l;
		Map<String, Object> params = new HashMap<>();
		params.put("ORG_ID", orgId);
		String sql = "SELECT SUM(DEPOSIT_AMT) SUM_AMT FROM SL_DEPOSIT T WHERE T.ORG_ID=:ORG_ID ";
		Map<String, Object> map = dbUtil.queryFirstForMap("查询保证金总额", sql, params);
		if (map != null && !map.isEmpty()) {
			if (map.get("sumAmt") != null) {
				ret = Long.valueOf(map.get("sumAmt").toString());
			}
		}
		return ret;
	}
	@Override
	public void updateStat(UserBean userBean, String orgId, String orgStat) throws BaseException {
		dbUtil.executeSql("", "UPDATE SL_ORG SET ORG_STAT=? WHERE ORG_ID IN (" + orgId + ")", orgStat);

	}
	@Override
	public void updateToken(String orgIds) throws BaseException {
		Session session = dbUtil.getSessionByTransaction();
		try {
			String[] orgStr = orgIds.split(",");
			int a = orgStr.length;
			String[] tokenIds = new String[a];
			for (int i = 0; i < tokenIds.length; i++) {
				tokenIds[i] = UUIDGenerator.getRandomString(32);
			}
			for (int i = 0; i < orgStr.length; i++) {
				dbUtil.executeSql("", session, "UPDATE SL_ORG SET TOKEN=? WHERE ORG_ID=" + orgStr[i], tokenIds[i]);
			}
			dbUtil.commit(session);
		}
		finally{
			dbUtil.close(session);
		}
	}
	@Override
	public void updateCallBackSetting(String orgId, String callbackUrl, String callbackData)
			throws BaseException {
		Session session = dbUtil.getSessionByTransaction();
		try {
			dbUtil.executeSql("", session, "UPDATE SL_ORG SET CALLBACK_URL=?,CALLBACK_DATA=? WHERE ORG_ID=?" , callbackUrl, callbackData, orgId);
			dbUtil.commit(session);
		}
		finally{
			dbUtil.close(session);
		}
	}
	
	@Override
	public List<OrgSaleTicketType> listOrgSaleTicketTypes(String orgId) throws BaseException {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT DISTINCT STT.TICKET_TYPE_ID,               ");
		sb.append("        STT.Ticket_type_NAME                       ");
		if (StringUtil.isNotNull(orgId)) {
			params.put("ORG_ID", orgId);
			sb.append("        ,CASE                                  ");
			sb.append("          WHEN SOT.ORG_ID IS NULL THEN         ");
			sb.append("           'false'                             ");
			sb.append("          ELSE                                 ");
			sb.append("           'true'                              ");
			sb.append("        END CHECKED                            ");
		}
		sb.append("   FROM SYS_TICKET_TYPE STT                        ");
		sb.append("   LEFT JOIN SL_ORG_TICKETTYPE SOT                 ");
		sb.append("     ON STT.TICKET_TYPE_ID = SOT.TICKET_TYPE_ID    ");
		if (StringUtil.isNotNull(orgId)) {
			sb.append("    AND SOT.ORG_ID =:ORG_ID                    ");
		}
		sb.append("    AND STT.TICKET_TYPE_STAT='Y'                   ");
		sb.append("  ORDER BY TICKET_TYPE_ID                          ");
		String sql = sb.toString();
		List<OrgSaleTicketType> ret = dbUtil.queryListToBean("查询机构可售票种列表", sql, OrgSaleTicketType.class, params);
		return ret;
	}
	@Override
	public void updateOrgSaleTicketTypes(String orgId, String[] saleTickets) throws BaseException {
		Session session = dbUtil.getSessionByTransaction();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ORG_ID", orgId);
		try {
			dbUtil.executeSql("修改机构可售票种", session, "DELETE SL_ORG_TICKETTYPE WHERE ORG_ID=:ORG_ID", params);
			
			for (int i = 0; i < saleTickets.length; i++) {
				params.put("TICKET_TYPE_ID", saleTickets[i]);
				dbUtil.executeSql("插入机构可售票种", session, "INSERT INTO SL_ORG_TICKETTYPE(ORG_ID,TICKET_TYPE_ID) VALUES(:ORG_ID,:TICKET_TYPE_ID)", params);
				
				Map<String, SlOrgSaleinfo> slOrgSaleinfoMap = dbUtil.queryMapForBean("查询机构售票信息表", "SELECT * FROM SL_ORG_SALEINFO WHERE ORG_ID=?", SlOrgSaleinfo.class, "id.ticketTypeId", orgId);
				if(!slOrgSaleinfoMap.containsKey(saleTickets[i])){
					SlOrgSaleinfo slOrgSaleinfo = new SlOrgSaleinfo();
					SlOrgSaleinfoId slOrgSaleinfoId = new SlOrgSaleinfoId();
					slOrgSaleinfoId.setOrgId(orgId);
					slOrgSaleinfoId.setTicketTypeId(saleTickets[i]);
					slOrgSaleinfo.setId(slOrgSaleinfoId);
					slOrgSaleinfo.setSaleTotalNum(0L);
					slOrgSaleinfo.setOpeTime(new Date());
					dbUtil.saveEntity("创建机构门票销售信息记录", session, slOrgSaleinfo);
				}
			}
			dbUtil.commit(session);
		}
		finally{
			dbUtil.close(session);
		}
	}
}
