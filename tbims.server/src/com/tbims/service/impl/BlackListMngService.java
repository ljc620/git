package com.tbims.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.tbims.Constant;
import com.tbims.bean.SysBlackListBean;
import com.tbims.entity.StrTicketInfo;
import com.tbims.entity.SysBlackList;
import com.tbims.service.IBlackListMngService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.StringUtil;
import com.zhming.support.util.UUIDGenerator;

@Service
public class BlackListMngService extends BaseService implements IBlackListMngService {

	@Override
	public PageBean<SysBlackList> listBlackList(UserBean userBean, SysBlackList sysBlackList) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "SELECT R.* FROM SYS_BLACK_LIST R  WHERE 1=1";
		if (StringUtil.isNotNull(sysBlackList.getTicketId())) {
			sql += " AND R.TICKET_ID =:TICKET_ID ";
			params.put("TICKET_ID", sysBlackList.getTicketId());
		}
				sql+= " ORDER BY LOSS_DT DESC ";
		PageBean<SysBlackList> ret = dbUtil.queryPageToBean("查询黑名单", sql, SysBlackList.class, userBean.getPageNum(), userBean.getPageSize(),params);
		return ret;
	}

	@Override
	public void addBlackList(UserBean userBean, SysBlackListBean sysBlackListBean) throws ServiceException {
		Map<String, Object> params = new HashMap<>();
		Session session = dbUtil.getSessionByTransaction();
		String lossReason = sysBlackListBean.getLossReason();
		String beginNo = sysBlackListBean.getBeginNo();
		String endNo = sysBlackListBean.getEndNo();
		params.put("BEGIN_NO", beginNo);
		params.put("END_NO", endNo);
		try {

			String sqlTicket = "SELECT T.* FROM STR_TICKET_INFO T WHERE T.TICKET_ID >=:BEGIN_NO AND T.TICKET_ID<=:END_NO";
			List<StrTicketInfo> ticketlist = dbUtil.queryListToBean("", sqlTicket, StrTicketInfo.class, params);
			for (StrTicketInfo map : ticketlist) {
				SysBlackList sysBlackList = new SysBlackList();
				sysBlackList.setBlackListId(UUIDGenerator.getPK());
				sysBlackList.setCardType("2");
				sysBlackList.setChipId(map.getChipId());
				sysBlackList.setLossDt(new Date());
				sysBlackList.setLossReason(lossReason);
				sysBlackList.setOpeTime(new Date());
				sysBlackList.setVersionNo(new Date());
				sysBlackList.setOpeUserId(userBean.getUserId());
				sysBlackList.setStat("Y");
				sysBlackList.setTicketId(String.valueOf(map.getTicketId()));
				Map<String, Object> hasTicketSqlPar = new HashMap<>();
				hasTicketSqlPar.put("TICKET_ID", String.valueOf(map.getTicketId()));
				String hasTicketSql = "SELECT * FROM SYS_BLACK_LIST T WHERE T.TICKET_ID=:TICKET_ID";
				int count = dbUtil.count("判断是否已经存在", hasTicketSql, hasTicketSqlPar);
				if (count == 0) {
					// 不存在保存
					dbUtil.saveEntity("保存信息", session, sysBlackList);
				} else {
					// 存在更新状态
					SysBlackList sysBlackListNew = dbUtil.queryFirstForBean("查询", hasTicketSql, SysBlackList.class, hasTicketSqlPar);
					sysBlackListNew.setStat("Y");
					dbUtil.updateEntity("更新实体", session, sysBlackListNew);
				}
				StrTicketInfo strTicketInfo = dbUtil.findById("", StrTicketInfo.class, Long.valueOf(map.getTicketId()));
				strTicketInfo.setStat(Constant.TICKET_STAT_004);
				dbUtil.updateEntity("更新票号表", session, strTicketInfo);

			}
			dbUtil.commit(session);

		} finally {
			dbUtil.close(session);
		}
	}

	@Override
	public void updateStat(UserBean userBean, String blackListId, String stat) throws BaseException {
		dbUtil.executeSql("", "UPDATE SYS_BLACK_LIST SET STAT=? WHERE BLACK_LIST_ID IN (" + blackListId + ")", stat);
		
	}

}
