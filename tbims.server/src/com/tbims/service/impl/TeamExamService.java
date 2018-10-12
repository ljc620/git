package com.tbims.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.tbims.Constant;
import com.tbims.bean.ChangeUserBean;
import com.tbims.bean.TeamOrderBean;
import com.tbims.bean.TeamOrderDetailBean;
import com.tbims.entity.SlLimitAmt;
import com.tbims.entity.SlLimitAmtId;
import com.tbims.entity.SlOrg;
import com.tbims.entity.SlOrgSaleinfo;
import com.tbims.entity.SlTeamOrder;
import com.tbims.entity.SlTeamOrderDetail;
import com.tbims.entity.SysTicketType;
import com.tbims.entity.SysTicketTypePrice;
import com.tbims.service.ITeamExamService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.DBException;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.CommonUtil;
import com.zhming.support.util.StringUtil;

@Service
public class TeamExamService extends BaseService implements ITeamExamService {
	private static final Logger logger = LoggerFactory.getLogger(TeamExamService.class);

	@Override
	public PageBean<TeamOrderBean> listApply(UserBean userBean, TeamOrderBean teamOrderBean, String flag) throws Exception {
		StringBuffer sb = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		// 查询待审核的数据
		sb.append(" SELECT T.APPLY_ID,  ");
		sb.append(" T.APPLY_TIME,       ");
		sb.append(" T.APPLY_USER_ID,    ");
		sb.append(" SU.USER_NAME APPLY_USER_NAME,    ");
		sb.append(" T.ORG_ID,           ");
		sb.append(" T.ORG_NAME,         ");
		sb.append(" T.IN_DT,            ");
		sb.append(" T.CHANGE_USER_ID,   ");
		sb.append(" T.CHANGE_USER_NAME, ");
		sb.append(" T.CHANGE_OPE_USER,  ");
		sb.append(" T.CHANGE_TIME,      ");
		sb.append(" T.CHANGE_OUTLET_ID, ");
		sb.append(" T.EXAM_USER_ID,     ");
		sb.append(" T.EXAM_TIME,        ");
		sb.append(" T.STAT,             ");
		sb.append(" T.EXAM_FROZEN_ADVANCE_AMT,  ");
		sb.append(" T.APPLY_FROZEN_ADVANCE_AMT, ");
		sb.append(" T.EXAM_TYPE,                ");
		sb.append(" T.MINUS_ADVANCE_AMT,        ");
		sb.append(" T.REMARK,                   ");
		sb.append(" T.FLOW_ADVANCE_AMT ,SUM(DE.EXAM_NUM) TOTAL_EXAM_NUM        ");
		sb.append(" FROM SL_TEAM_ORDER T INNER JOIN  SL_TEAM_ORDER_DETAIL DE ON  DE.APPLY_ID=T.APPLY_ID LEFT JOIN SYS_USER SU ON T.APPLY_USER_ID=SU.USER_ID WHERE  1=1 ");
		if (teamOrderBean != null) {
			if (StringUtil.isNotNull(teamOrderBean.getStat())) {
				sb.append(" AND T.STAT in (" + teamOrderBean.getStat() + ") ");
			}
			if (StringUtil.isNotNull(teamOrderBean.getApplyId())) {
				sb.append(" AND T.APPLY_ID=:APPLY_ID ");
				params.put("APPLY_ID", teamOrderBean.getApplyId());
			}
			if (teamOrderBean.getApplyBTime() != null && teamOrderBean.getApplyETime() != null) {
				sb.append(" AND trunc(T.APPLY_TIME,'dd')>=:APPLY_BTIME ");
				sb.append(" AND trunc(T.APPLY_TIME,'dd')<=:APPLY_ETIME ");
				params.put("APPLY_BTIME", teamOrderBean.getApplyBTime());
				params.put("APPLY_ETIME", teamOrderBean.getApplyETime());
			}

			if (teamOrderBean.getInDt() != null) {
				sb.append(" AND TRUNC(T.IN_DT,'dd')=:IN_DT ");
				params.put("IN_DT", teamOrderBean.getInDt());
			}
			
			if (StringUtil.isNotNull(teamOrderBean.getOrgId())) {
				sb.append(" AND T.ORG_ID=:ORG_ID ");
				params.put("ORG_ID", teamOrderBean.getOrgId());
			}
		}

		sb.append(" GROUP BY T.APPLY_ID, ");
		sb.append(" T.APPLY_TIME,       ");
		sb.append(" T.APPLY_USER_ID,    ");
		sb.append(" SU.USER_NAME ,    ");
		sb.append(" T.ORG_ID,           ");
		sb.append(" T.ORG_NAME,         ");
		sb.append(" T.IN_DT,            ");
		sb.append(" T.CHANGE_USER_ID,   ");
		sb.append(" T.CHANGE_USER_NAME, ");
		sb.append(" T.CHANGE_OPE_USER,  ");
		sb.append(" T.CHANGE_TIME,      ");
		sb.append(" T.CHANGE_OUTLET_ID, ");
		sb.append(" T.EXAM_USER_ID,     ");
		sb.append(" T.EXAM_TIME,        ");
		sb.append(" T.STAT,             ");
		sb.append(" T.EXAM_FROZEN_ADVANCE_AMT,  ");
		sb.append(" T.APPLY_FROZEN_ADVANCE_AMT, ");
		sb.append(" T.EXAM_TYPE,                ");
		sb.append(" T.MINUS_ADVANCE_AMT,        ");
		sb.append(" T.REMARK ,                 ");
		sb.append(" T.FLOW_ADVANCE_AMT  ");
		sb.append("ORDER BY T.APPLY_TIME DESC　");
		String sql = sb.toString();
		PageBean<TeamOrderBean> ret = dbUtil.queryPageToBean("查询团队申请列表", sql, TeamOrderBean.class, userBean.getPageNum(), userBean.getPageSize(), params);
		return ret;
	}

	public TeamOrderBean getTotalNum(TeamOrderBean teamOrderBean, String flag) throws Exception {
		StringBuffer sb = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		// 查询待审核的数据
		sb.append(" SELECT ");
		sb.append(" SUM(DE.EXAM_NUM) TOTAL_EXAM_NUM        ");
		sb.append(" FROM SL_TEAM_ORDER T INNER JOIN  SL_TEAM_ORDER_DETAIL DE ON  DE.APPLY_ID=T.APPLY_ID LEFT JOIN SYS_USER SU ON T.APPLY_USER_ID=SU.USER_ID WHERE  1=1 ");
		if (teamOrderBean != null) {
			if (StringUtil.isNotNull(teamOrderBean.getStat())) {
				sb.append(" AND T.STAT in (" + teamOrderBean.getStat() + ") ");
			}

			if (StringUtil.isNotNull(teamOrderBean.getApplyId())) {
				sb.append(" AND T.APPLY_ID=:APPLY_ID ");
				params.put("APPLY_ID", teamOrderBean.getApplyId());
			}
			if (teamOrderBean.getApplyBTime() != null && teamOrderBean.getApplyETime() != null) {
				sb.append(" AND trunc(T.APPLY_TIME,'dd')>=:APPLY_BTIME ");
				sb.append(" AND trunc(T.APPLY_TIME,'dd')<=:APPLY_ETIME ");
				params.put("APPLY_BTIME", teamOrderBean.getApplyBTime());
				params.put("APPLY_ETIME", teamOrderBean.getApplyETime());
			}

			if (teamOrderBean.getInDt() != null) {
				sb.append(" AND TRUNC(T.IN_DT,'dd')=:IN_DT ");
				params.put("IN_DT", teamOrderBean.getInDt());
			}
			
			if (StringUtil.isNotNull(teamOrderBean.getOrgId())) {
				sb.append(" AND T.ORG_ID=:ORG_ID ");
				params.put("ORG_ID", teamOrderBean.getOrgId());
			}
		}

		String sql = sb.toString();
		TeamOrderBean ret = dbUtil.queryFirstForBean("查询团队申请列表", sql, TeamOrderBean.class, params);
		return ret;
	}

	@Override
	public List<SysTicketType> listTicketType() {
		String sql = "SELECT * FROM SYS_TICKET_TYPE ";
		return dbUtil.queryListToBean("", sql, SysTicketType.class);
	}

	@Override
	public SlTeamOrder getSlTeamOrder(String applyId) {
		SlTeamOrder slTeamOrder = dbUtil.findById("", SlTeamOrder.class, applyId);
		return slTeamOrder;
	}

	@Override
	public ChangeUserBean getChangeUser(String changeUserId) {
		ChangeUserBean retBean = null;
		StringBuffer sb = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		sb.append(" SELECT CUSER.CHANGE_USER_ID, CUSER.CHANGE_USER_NAME, CUSER.CARD_TYPE, CUSER.CARD_ID, CUSER.TEL, CUSER.MAIL,DI.ITEM_NAME AS CARD_TYPE_NAME");
		sb.append("   FROM SL_CHANGE_USER CUSER ");
		sb.append("  INNER JOIN SYS_DICTIONARY DI ");
		sb.append("     ON CUSER.CARD_TYPE = DI.ITEM_VAL ");
		sb.append(" WHERE DI.KEY_CD='ID_CARD_TYPE' ");
		sb.append(" AND CUSER.CHANGE_USER_ID=:CHANGE_USER_ID   ");
		String sql = sb.toString();
		params.put("CHANGE_USER_ID", changeUserId);
		List<ChangeUserBean> ret = dbUtil.queryListToBean("", sql, ChangeUserBean.class, params);
		if (ret != null && ret.size() != 0) {
			retBean = ret.get(0);
		}
		return retBean;
	}

	@Override
	public List<TeamOrderDetailBean> applyDetail(String applyId) {
		StringBuffer sb = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		sb.append(" SELECT T.DETAIL_ID,T.TICKET_TYPE_ID, T.TICKET_TYPE_NAME,T.APPLY_NUM, T.EXAM_NUM ,T.CHANGE_NUM,T.MINUS_LIMIT,T.FLOW_LIMIT,T.EXAM_FROZEN_LIMIT ,T.APPLY_FROZEN_LIMIT");
		sb.append("   FROM SL_TEAM_ORDER_DETAIL T  ");
		sb.append("  WHERE T.APPLY_ID =:APPLY_ID");
		params.put("APPLY_ID", applyId);
		String sql = sb.toString();
		List<TeamOrderDetailBean> ret = dbUtil.queryListToBean("申请明细查询", sql, TeamOrderDetailBean.class, params);
		return ret;
	}

	@Override
	public void examOrder(UserBean userBean, SlTeamOrder slTeamOrder, List<TeamOrderDetailBean> applyDetailList) throws Exception {
		SlTeamOrder slTeamOrderNew = dbUtil.findById("", SlTeamOrder.class, slTeamOrder.getApplyId());
		if (!Constant.TEAM_EXAM_STAT_02.equals(slTeamOrderNew.getStat())) {
			throw new ServiceException(MSG.BF_ERROR, "订单已处理！");
		}
		slTeamOrderNew.setExamUserId(userBean.getUserId());
		slTeamOrderNew.setExamTime(new Date());
		slTeamOrderNew.setExamType("02");
		slTeamOrderNew.setStat(slTeamOrder.getStat());

		Session session = null;
		try {
			session = dbUtil.getSessionByTransaction();
			//SlOrg slOrg = dbUtil.findById("", SlOrg.class, slTeamOrderNew.getOrgId());
			SlOrg slOrg=dbUtil.queryFirstListToBeanLock("查询机构信息", session, "FROM SlOrg U where orgId=?", "U", SlOrg.class, slTeamOrderNew.getOrgId());

			List<SlTeamOrderDetail> newSlTeamOrderDetails = slTeamOrderNew.getSlTeamOrderDetails();
			for (TeamOrderDetailBean teamOrderDetailBean : applyDetailList) {
				SysTicketTypePrice sysTicketTypePriceNew = dbUtil.queryFirstForBean("查询是否超过三个阶梯票价段", "SELECT * FROM SYS_TICKET_TYPE_PRICE T WHERE T.TICKET_TYPE_ID=? AND ?>END_NO-START_NO+1", SysTicketTypePrice.class, teamOrderDetailBean.getTicketTypeId(), teamOrderDetailBean.getExamNum());
				if (sysTicketTypePriceNew != null) {
					throw new ServiceException(MSG.BF_ERROR, "您[" + teamOrderDetailBean.getTicketTypeName() + "]审核张数" + teamOrderDetailBean.getExamNum() + "超过两个票价阶梯段，请重新填写审核数量或添加订单");
				}
			}
			if (Constant.TEAM_EXAM_STAT_05.equals(slTeamOrder.getStat())) {
				// 遍历申请的明细信息
				for (SlTeamOrderDetail slTeamOrderDetail : newSlTeamOrderDetails) {
					// 解冻额度
					Long applyFrozenLimit = slTeamOrderDetail.getApplyFrozenLimit();
					if (applyFrozenLimit != 0) {
						SlLimitAmtId slLimitAmtId = new SlLimitAmtId(slOrg.getOrgId(), slTeamOrderDetail.getTicketTypeId());
						SlLimitAmt slLimitAmt = dbUtil.findById("", SlLimitAmt.class, slLimitAmtId);
						slLimitAmt.setFrozeLimit(slLimitAmt.getFrozeLimit() - applyFrozenLimit);
						slLimitAmt.setLimitAmt(slLimitAmt.getLimitAmt() + applyFrozenLimit);
						dbUtil.updateEntity("更新额度", session, slLimitAmt);
					}

				}
				// 解冻预付款
				Long frozenAdvanceAmt = slTeamOrderNew.getApplyFrozenAdvanceAmt();
				if (frozenAdvanceAmt != 0) {
					slOrg.setAdvanceAmt(slOrg.getAdvanceAmt() + frozenAdvanceAmt);
					slOrg.setAdvanceFrozeAmt(slOrg.getAdvanceFrozeAmt() - frozenAdvanceAmt);
					dbUtil.updateEntity("更新机构预付款", session, slOrg);
				}
				dbUtil.updateEntity("更新审核状态", session, slTeamOrderNew);
			}
			if (Constant.TEAM_EXAM_STAT_03.equals(slTeamOrder.getStat())) {
				List<SlTeamOrderDetail> detailList = new ArrayList<SlTeamOrderDetail>();
				for (TeamOrderDetailBean teamOrderDetailBean : applyDetailList) {
					SlTeamOrderDetail slTeamOrderDetailNew = dbUtil.findById("", SlTeamOrderDetail.class, teamOrderDetailBean.getDetailId());
					slTeamOrderDetailNew.setExamNum(teamOrderDetailBean.getExamNum());

					detailList.add(slTeamOrderDetailNew);
				}
				this.doCommit(session, slOrg, slTeamOrderNew, detailList);
			}
			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}
	}

	/**
	 * Title: 提交订单<br/>
	 * Description:
	 * 
	 * @param session
	 * @param slOrg
	 * @param slTeamOrder
	 * @param detailList
	 * @throws ServiceException
	 * @throws DBException
	 */
	private void doCommit(Session session, SlOrg slOrg, SlTeamOrder slTeamOrder, List<SlTeamOrderDetail> detailList) throws ServiceException, DBException {
		List<SlTeamOrderDetail> newDetailList = new ArrayList<SlTeamOrderDetail>();
		// 解冻预付款
		if (slTeamOrder.getApplyFrozenAdvanceAmt() != 0) {
			slOrg.setAdvanceAmt(slOrg.getAdvanceAmt() + slTeamOrder.getApplyFrozenAdvanceAmt());
			slOrg.setAdvanceFrozeAmt(slOrg.getAdvanceFrozeAmt() - slTeamOrder.getApplyFrozenAdvanceAmt());
		}

		Map<String, SlOrgSaleinfo> slOrgSaleinfoMap = dbUtil.queryMapForBean("查询机构售票信息表", "SELECT * FROM SL_ORG_SALEINFO WHERE ORG_ID=?", SlOrgSaleinfo.class, "id.ticketTypeId", slOrg.getOrgId());

		// 遍历订单明细
		for (SlTeamOrderDetail slTeamOrderDetail : detailList) {
			// 获取销售信息
			long saleTotalNumOld = 0;
			SlOrgSaleinfo slOrgSaleinfo = slOrgSaleinfoMap.get(slTeamOrderDetail.getTicketTypeId());
			if (slOrgSaleinfo != null) {
				saleTotalNumOld = slOrgSaleinfo.getSaleTotalNum();// 原销售张数
			}
			slTeamOrderDetail.setRemark(String.format("当前销售张数[%d]", saleTotalNumOld));

			Long applyFrozenLimit = slTeamOrderDetail.getApplyFrozenLimit();
			// 根据票种机构号，获取额度
			String ticketTypeId = slTeamOrderDetail.getTicketTypeId();
			// 获取票价
			Long price = getPrice(ticketTypeId);
			SlLimitAmtId slLimitAmtId = new SlLimitAmtId(slOrg.getOrgId(), ticketTypeId);
			SlLimitAmt slLimitAmt = dbUtil.findById("获取额度", SlLimitAmt.class, slLimitAmtId);

			if (slLimitAmt == null) {
				// 如果额度为空，判断预付款
				if (slOrg.getAdvanceAmt() == 0) {
					// 如果预付款为0
					throw new ServiceException(MSG.BF_ERROR_AMT);
				} else {
					// 计算需冻结预付款金额
					Long frozenAdvanceAmt = calcTicketTypeByOrg(ticketTypeId, saleTotalNumOld, price, slTeamOrderDetail.getExamNum());
					// Long frozenAdvanceAmt = slTeamOrderDetail.getExamNum() *
					// price;

					// 判断预付款余额是否充足
					if (slOrg.getAdvanceAmt() < frozenAdvanceAmt) {
						// 如果预付款余额不足
						throw new ServiceException(MSG.BF_ERROR_AMT);
					} else {
						// 预付款余额充足，冻结预付款
						Long newAdvanceAmt = slOrg.getAdvanceAmt() - frozenAdvanceAmt;
						slOrg.setAdvanceAmt(newAdvanceAmt);
						Long newAdvanceFrozeAmt = slOrg.getAdvanceFrozeAmt() + frozenAdvanceAmt;
						slOrg.setAdvanceFrozeAmt(newAdvanceFrozeAmt);
						slTeamOrder.setExamFrozenAdvanceAmt(frozenAdvanceAmt);
					}
				}
			} else {
				// 如果额度不为空
				// 解冻
				if (slLimitAmt.getFrozeLimit() - applyFrozenLimit < 0) {
					throw new ServiceException(MSG.BF_ERROR_AMT);
				}
				slLimitAmt.setFrozeLimit(slLimitAmt.getFrozeLimit() - applyFrozenLimit);
				slLimitAmt.setLimitAmt(slLimitAmt.getLimitAmt() + applyFrozenLimit);
				Long limitAmt = slLimitAmt.getLimitAmt();
				Long frozeLimit = slLimitAmt.getFrozeLimit();
				// 判断额度是否够冻结
				if (limitAmt >= slTeamOrderDetail.getExamNum()) {
					// 如果够冻结，直接冻结
					Long newLimitAmt = limitAmt - slTeamOrderDetail.getExamNum();
					slLimitAmt.setLimitAmt(newLimitAmt);
					Long newFrozeLimit = frozeLimit + slTeamOrderDetail.getExamNum();
					slLimitAmt.setFrozeLimit(newFrozeLimit);
					slTeamOrderDetail.setExamFrozenLimit(slTeamOrderDetail.getExamNum());
					slTeamOrder.setExamFrozenAdvanceAmt(0l);
				} else {
					// 如果额度不够冻结
					Long remainLimit = slTeamOrderDetail.getExamNum() - limitAmt;
					// 不够部分换算为预付款
					Long remainAavance = calcTicketTypeByOrg(ticketTypeId, saleTotalNumOld + limitAmt, price, remainLimit);
					// Long remainAavance = remainLimit * price;
					// 判断预付款是否充足
					if (slOrg.getAdvanceAmt() >= remainAavance) {
						// 如果充足，扣减额度及预付款
						slLimitAmt.setLimitAmt(0l);
						slLimitAmt.setFrozeLimit(slLimitAmt.getFrozeLimit() + limitAmt);
						slOrg.setAdvanceAmt(slOrg.getAdvanceAmt() - remainAavance);
						slOrg.setAdvanceFrozeAmt(slOrg.getAdvanceFrozeAmt() + remainAavance);
						slTeamOrderDetail.setExamFrozenLimit(limitAmt);
						slTeamOrder.setExamFrozenAdvanceAmt(remainAavance);
					} else {
						throw new ServiceException(MSG.BF_ERROR_AMT);
					}
				}
				dbUtil.updateEntity("", session, slLimitAmt);
			}
			slTeamOrderDetail.setSlTeamOrder(slTeamOrder);

			newDetailList.add(slTeamOrderDetail);
		}

		slTeamOrder.setSlTeamOrderDetails(newDetailList);

		dbUtil.updateEntity("更新机构信息", session, slOrg);
		dbUtil.updateEntity("更新团队订单", session, slTeamOrder);
	}

	/**
	 * Title:计算签约社需要扣除的预付款 <br/>
	 * Description:
	 * 
	 * @param ticketTypeId
	 * @param saleTotalNumOld
	 * @param ticketTypePrice
	 * @param minusNum 去除额度后的销售张数
	 * @return
	 */
	private long calcTicketTypeByOrg(String ticketTypeId, long saleTotalNumOld, long ticketTypePrice, long minusNum) {
		long saleTotalNumNew = saleTotalNumOld + minusNum;// 新销售总张数

		long priceOld = 0;// 原票价
		long minusDllOld = 0;// 原待扣除张数
		SysTicketTypePrice sysTicketTypePriceOld = dbUtil.queryFirstForBean("查询阶梯票价1", "SELECT * FROM SYS_TICKET_TYPE_PRICE WHERE TICKET_TYPE_ID=? AND (? BETWEEN START_NO AND END_NO)", SysTicketTypePrice.class, ticketTypeId, saleTotalNumOld);
		if (sysTicketTypePriceOld != null) {
			minusDllOld = CommonUtil.covertLong(sysTicketTypePriceOld.getEndNo()) - saleTotalNumOld;
			priceOld = CommonUtil.covertLong(sysTicketTypePriceOld.getPrice());
		}

		long priceNew = 0;// 新票价
		long minusDllNew = 0;// 新待扣除张数
		SysTicketTypePrice sysTicketTypePriceNew = dbUtil.queryFirstForBean("查询阶梯票价2", "SELECT * FROM SYS_TICKET_TYPE_PRICE WHERE TICKET_TYPE_ID=? AND (? BETWEEN START_NO AND END_NO)", SysTicketTypePrice.class, ticketTypeId, saleTotalNumNew);
		if (sysTicketTypePriceNew != null) {
			minusDllNew = saleTotalNumNew - CommonUtil.covertLong(sysTicketTypePriceNew.getStartNo()) + 1;
			priceNew = CommonUtil.covertLong(sysTicketTypePriceNew.getPrice());
		}

		// 无阶梯票价，按票种基准价格
		if (sysTicketTypePriceOld == null && sysTicketTypePriceNew == null) {
			return ticketTypePrice * minusNum;
		}

		// 原销售张数和新销售张数在同一阶梯票价中
		if (sysTicketTypePriceOld != null && sysTicketTypePriceNew != null) {
			if (sysTicketTypePriceOld.getPriceId().equals(sysTicketTypePriceNew.getPriceId())) {
				return CommonUtil.covertLong(sysTicketTypePriceNew.getPrice()) * minusNum;
			}
		}

		// 原销售张数没有阶梯票价和新销售张数有阶梯票，处理
		if (sysTicketTypePriceOld == null && sysTicketTypePriceNew != null) {
			minusDllOld = minusNum - minusDllNew;
			priceOld = ticketTypePrice;
		}

		// 原销售张数有阶梯票价和新销售张数没有阶梯票，处理
		if (sysTicketTypePriceOld != null && sysTicketTypePriceNew == null) {
			minusDllNew = minusNum - minusDllOld;
			priceNew = ticketTypePrice;
		}

		return minusDllOld * priceOld + minusDllNew * priceNew;
	}

	/**
	 * Title: 获取单价<br/>
	 * Description:
	 * 
	 * @param ticketTypeId
	 * @return
	 */
	private Long getPrice(String ticketTypeId) {
		SysTicketType sysTicketType = dbUtil.findById("", SysTicketType.class, ticketTypeId);
		// 获取票价
		Long price = sysTicketType.getPrice();
		return price;
	}

	/**
	 * 自动审核
	 */
	@Scheduled(cron = "0 0 0/1 * * ?")
	public void execTask() {
		logger.info("正在进行自动审核：" + new Date());
		String autoTime = getParemeterVal("auto_exam_time");
		String sql = "UPDATE SL_TEAM_ORDER T SET T.EXAM_TYPE = '01', T.STAT='03', T.exam_time=sysdate WHERE T.STAT='02' AND SYSDATE>=APPLY_TIME+numtodsinterval(" + autoTime + ",'hour') ";
		dbUtil.executeSql("自动审核", sql);
	}

	/**
	 * 流单管理
	 */
	@Scheduled(cron = "0 30 23 * * ?")
	public void execFlowTask() {
		String flow_minus_flag = getParemeterVal("flow_minus_flag");
		if ("Y".equals(flow_minus_flag)) {
			// 获取流单扣罚比例
			Long flowMinusPersent = Long.valueOf(getParemeterVal("flow_minus_persent"));
			Map<String, Object> params = new HashMap<String, Object>();
			String sql = "SELECT T.* FROM SL_TEAM_ORDER T WHERE T.STAT = '03'   AND T.IN_DT <:MY_DATE";
			params.put("MY_DATE", new Date());
			List<Map<String, Object>> list = dbUtil.queryListToMap("", sql, params);
			for (Map<String, Object> map : list) {
				String applyId = map.get("applyId").toString();
				Session session = null;
				try {
					logger.info("正在处理的订单编号：" + applyId);
				    session = dbUtil.getSessionByTransaction();
					SlTeamOrder slTeamOrder = dbUtil.findById("", SlTeamOrder.class, applyId);
					String orgId = slTeamOrder.getOrgId();
					//SlOrg slOrg = dbUtil.findById("", SlOrg.class, orgId);
					SlOrg slOrg=dbUtil.queryFirstListToBeanLock("查询机构信息", session, "FROM SlOrg U where orgId=?", "U", SlOrg.class, orgId);

					Long advanceAmt = slOrg.getAdvanceAmt();
					Long advanceFrozeAmt = slOrg.getAdvanceFrozeAmt();
					List<SlTeamOrderDetail> slTeamOrderDetails = slTeamOrder.getSlTeamOrderDetails();
					List<SlTeamOrderDetail> slTeamOrderDetailsNew = new ArrayList<SlTeamOrderDetail>();
					for (SlTeamOrderDetail slTeamOrderDetail : slTeamOrderDetails) {
						Long examNum = slTeamOrderDetail.getExamFrozenLimit();
						if (examNum != 0) {
							// 流单扣罚额度
							Long flowLimit = (long) Math.round(examNum * flowMinusPersent / 100.00);
							// 解冻额度
							SlLimitAmt slLimitAmt = dbUtil.findById("查询额度", SlLimitAmt.class, new SlLimitAmtId(orgId, slTeamOrderDetail.getTicketTypeId()));
							slLimitAmt.setFrozeLimit(slLimitAmt.getFrozeLimit() - examNum);
							slLimitAmt.setLimitAmt(slLimitAmt.getLimitAmt() + flowLimit);
							dbUtil.updateEntity("更新额度", session, slLimitAmt);
							slTeamOrderDetail.setFlowLimit(flowLimit);

						} else {
							slTeamOrderDetail.setFlowLimit(0l);
						}
						slTeamOrderDetailsNew.add(slTeamOrderDetail);
					}
					Long examFrozenAdvanceAmt = slTeamOrder.getExamFrozenAdvanceAmt();
					if (examFrozenAdvanceAmt == 0) {
						slTeamOrder.setFlowAdvanceAmt(0l);
					} else {
						// 流单扣罚预付款
						Long flowAdvanceAmt = (long) Math.round(examFrozenAdvanceAmt * flowMinusPersent / 100);
						// 解冻预付款
						slOrg.setAdvanceAmt(advanceAmt + flowAdvanceAmt);
						slOrg.setAdvanceFrozeAmt(advanceFrozeAmt - examFrozenAdvanceAmt);
						slTeamOrder.setFlowAdvanceAmt(flowAdvanceAmt);
						dbUtil.updateEntity("更新预付款", session, slOrg);
					}
					// 订单状态改为流单
					slTeamOrder.setStat("06");
					slTeamOrder.setSlTeamOrderDetails(slTeamOrderDetailsNew);
					dbUtil.updateEntity("更新订单", session, slTeamOrder);
					dbUtil.commit(session);
				} finally {
					dbUtil.close(session);
				}
			}

		}
	}

	@Override
	public void cancleOrder(UserBean userBean, SlTeamOrder slTeamOrder) throws Exception {
		SlTeamOrder slTeamOrderNew = dbUtil.findById("", SlTeamOrder.class, slTeamOrder.getApplyId());
		if (!Constant.TEAM_EXAM_STAT_03.equals(slTeamOrderNew.getStat())) {
			throw new ServiceException(MSG.BF_ERROR, "订单状态不正确，不可取消！");
		}
		slTeamOrderNew.setExamUserId(userBean.getUserId());
		slTeamOrderNew.setExamTime(new Date());
		slTeamOrderNew.setStat(Constant.TEAM_EXAM_STAT_07);
		Session session=null;
		try {
			session = dbUtil.getSessionByTransaction();
			//SlOrg slOrg = dbUtil.findById("", SlOrg.class, slTeamOrderNew.getOrgId());
			SlOrg slOrg=dbUtil.queryFirstListToBeanLock("查询机构信息", session, "FROM SlOrg U where orgId=?", "U", SlOrg.class, slTeamOrderNew.getOrgId());

			List<SlTeamOrderDetail> newSlTeamOrderDetails = slTeamOrderNew.getSlTeamOrderDetails();
			// 遍历申请的明细信息
			for (SlTeamOrderDetail slTeamOrderDetail : newSlTeamOrderDetails) {
				// 解冻额度
				Long examFrozenLimit = slTeamOrderDetail.getExamFrozenLimit();
				if (examFrozenLimit != 0) {
					SlLimitAmtId slLimitAmtId = new SlLimitAmtId(slOrg.getOrgId(), slTeamOrderDetail.getTicketTypeId());
					SlLimitAmt slLimitAmt = dbUtil.findById("", SlLimitAmt.class, slLimitAmtId);
					if (slLimitAmt.getFrozeLimit() - examFrozenLimit < 0) {
						throw new ServiceException(MSG.BF_ERROR);
					}
					slLimitAmt.setFrozeLimit(slLimitAmt.getFrozeLimit() - examFrozenLimit);
					slLimitAmt.setLimitAmt(slLimitAmt.getLimitAmt() + examFrozenLimit);
					dbUtil.updateEntity("更新额度", session, slLimitAmt);
				}

			}
			// 解冻预付款
			Long frozenAdvanceAmt = slTeamOrderNew.getExamFrozenAdvanceAmt();
			if (frozenAdvanceAmt != 0) {
				slOrg.setAdvanceAmt(slOrg.getAdvanceAmt() + frozenAdvanceAmt);
				slOrg.setAdvanceFrozeAmt(slOrg.getAdvanceFrozeAmt() - frozenAdvanceAmt);
				dbUtil.updateEntity("更新机构预付款", session, slOrg);
			}
			dbUtil.updateEntity("更新审核状态", session, slTeamOrderNew);
			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}
	}
}