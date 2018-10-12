package com.tbims.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.tbims.entity.SlAdvance;
import com.tbims.entity.SlLimit;
import com.tbims.entity.SlLimitAmt;
import com.tbims.entity.SlLimitAmtId;
import com.tbims.entity.SlOrg;
import com.tbims.entity.SysTicketType;
import com.tbims.service.IOrgMngService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.DBException;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.StringUtil;
import com.zhming.support.util.UUIDGenerator;

@Service
public class OrgMngService extends BaseService implements IOrgMngService {

	@Override
	public PageBean<Map<String, Object>> listOrg(UserBean userBean, SlOrg slOrg, String orgBtype) {
		Map<String, Object> map = new HashMap<>();
		String sql = "SELECT * FROM SL_ORG T WHERE T.ORG_STAT='Y' AND T.ORG_TYPE='0' ";
		if (slOrg != null) {
			sql += " AND T.ORG_ID=:ORG_ID ";
			map.put("ORG_ID", userBean.getOrgId());
		}
		PageBean<Map<String, Object>> ret = dbUtil.queryPageToMap("查询签约社列表", sql, userBean.getPageNum(), userBean.getPageSize(), map);
		return ret;
	}

	@Override
	public Map<String, SlOrg> listOrg(String orgBtype) throws DBException {
		Map<String, Object> map = new HashMap<>();
		String sql = "SELECT * FROM SL_ORG T WHERE T.ORG_STAT='Y' ";
		if (StringUtil.isNotNull(orgBtype)) {
			sql += " AND ORG_TYPE=:ORG_TYPE ";
			map.put("ORG_TYPE", orgBtype);
		}
		Map<String, SlOrg> ret = dbUtil.queryMapForBean("查询机构列表", sql, SlOrg.class, "orgId", map);
		return ret;
	}

	@Override
	public SlOrg getOrgById(String orgId) {
		SlOrg slOrg = dbUtil.findById("查询签约社", SlOrg.class, orgId);
		return slOrg;
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
	public PageBean<SlLimit> listLimit(UserBean userBean, String orgId, String ticketTypeId) {
		Map<String, Object> map = new HashMap<>();
		String sql = "SELECT * FROM SL_LIMIT WHERE ORG_ID=:ORG_ID AND TICKET_TYPE_ID=:TICKET_TYPE_ID ORDER BY OPE_TIME DESC";
		map.put("ORG_ID", orgId);
		map.put("TICKET_TYPE_ID", ticketTypeId);
		PageBean<SlLimit> ret = dbUtil.queryPageToBean("查询额度明细", sql, SlLimit.class, userBean.getPageNum(), userBean.getPageSize(), map);
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
				SlLimitAmt slLimitAmt = new SlLimitAmt();
				slLimitAmt.setFrozeLimit(0l);
				slLimitAmt.setLimitAmt(slLimit.getLimit());
				slLimitAmt.setId(new SlLimitAmtId(slLimit.getOrgId(), slLimit.getTicketTypeId()));
				slLimitAmt.setOpeTime(new Date());
				slLimitAmt.setOptUserId(userBean.getUserId());
				dbUtil.saveEntity("添加额度余额信息", session, slLimitAmt);
			} else {
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
	public PageBean<SlAdvance> listAdvance(UserBean userBean, String orgId) {
		Map<String, Object> map = new HashMap<>();
		String sql = "SELECT * FROM SL_ADVANCE WHERE ORG_ID=:ORG_ID ORDER BY OPE_TIME DESC";
		map.put("ORG_ID", orgId);
		PageBean<SlAdvance> ret = dbUtil.queryPageToBean("查询预付款列表", sql, SlAdvance.class, userBean.getPageNum(), userBean.getPageSize(), map);
		return ret;
	}
}
