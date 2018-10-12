package com.tbims.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.tbims.Constant;
import com.tbims.bean.ChangeUserBean;
import com.tbims.bean.TeamOrderBean;
import com.tbims.entity.SlChangeUser;
import com.tbims.entity.SlLimitAmt;
import com.tbims.entity.SlLimitAmtId;
import com.tbims.entity.SlOrg;
import com.tbims.entity.SlOrgSaleinfo;
import com.tbims.entity.SlTeamOrder;
import com.tbims.entity.SlTeamOrderDetail;
import com.tbims.entity.SysTicketType;
import com.tbims.entity.SysTicketTypePrice;
import com.tbims.service.ITeamOrderService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.DBException;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.CommonUtil;
import com.zhming.support.util.DateUtil;
import com.zhming.support.util.StringUtil;
import com.zhming.support.util.UUIDGenerator;

@Service
public class TeamOrderService extends BaseService implements ITeamOrderService {
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
	private void doCommit(UserBean userBean, Session session, SlOrg slOrg, SlTeamOrder slTeamOrder, List<SlTeamOrderDetail> detailList) throws ServiceException, DBException {
		// 把审核冻结预付款、扣减预付款、流单扣罚预付款初始为0
		slTeamOrder.setExamFrozenAdvanceAmt(0l);
		slTeamOrder.setMinusAdvanceAmt(0l);
		slTeamOrder.setFlowAdvanceAmt(0l);
		slTeamOrder.setStat(Constant.TEAM_EXAM_STAT_03);
		List<SlTeamOrderDetail> newDetailList = new ArrayList<SlTeamOrderDetail>();

		Map<String, SlOrgSaleinfo> slOrgSaleinfoMap = dbUtil.queryMapForBean("查询机构售票信息表", "SELECT * FROM SL_ORG_SALEINFO WHERE ORG_ID=?", SlOrgSaleinfo.class, "id.ticketTypeId", slOrg.getOrgId());

		// 遍历订单明细
		Long totalNum = 0l;
		for (SlTeamOrderDetail slTeamOrderDetail : detailList) {
			// 获取销售信息
			long saleTotalNumOld = 0;
			SlOrgSaleinfo slOrgSaleinfo = slOrgSaleinfoMap.get(slTeamOrderDetail.getTicketTypeId());
			if (slOrgSaleinfo != null) {
				saleTotalNumOld = slOrgSaleinfo.getSaleTotalNum();// 原销售张数
			}
			slTeamOrderDetail.setRemark(String.format("当前销售张数[%d]", saleTotalNumOld));

			// 先把扣减额度、审核冻结额度、申请冻结额度、额度流单罚金初始为0
			slTeamOrderDetail.setMinusLimit(0l);
			slTeamOrderDetail.setExamFrozenLimit(0l);
			slTeamOrderDetail.setApplyFrozenLimit(0l);
			slTeamOrderDetail.setFlowLimit(0l);
			// 把审核数量初始为申请数量
			slTeamOrderDetail.setExamNum(slTeamOrderDetail.getApplyNum());
			slTeamOrderDetail.setApplyFrozenLimit(0l);
			totalNum = totalNum + slTeamOrderDetail.getApplyNum();
			slTeamOrderDetail.setExamNum(slTeamOrderDetail.getApplyNum());
			if (slTeamOrderDetail.getDetailId() == null) {
				slTeamOrderDetail.setDetailId(UUIDGenerator.getPK());
			}
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
					Long frozenAdvanceAmt = calcTicketTypeByOrg(ticketTypeId, saleTotalNumOld, price, slTeamOrderDetail.getApplyNum());
					// Long frozenAdvanceAmt = slTeamOrderDetail.getApplyNum() *
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
						slTeamOrder.setApplyFrozenAdvanceAmt(slTeamOrder.getApplyFrozenAdvanceAmt() + frozenAdvanceAmt);
					}
				}
			} else {
				// 如果额度不为空
				Long limitAmt = slLimitAmt.getLimitAmt();
				Long frozeLimit = slLimitAmt.getFrozeLimit();
				// 判断额度是否够冻结
				if (limitAmt >= slTeamOrderDetail.getApplyNum()) {
					// 如果够冻结，直接冻结
					Long newLimitAmt = limitAmt - slTeamOrderDetail.getApplyNum();
					if (newLimitAmt < 0) {
						throw new ServiceException(MSG.BF_ERROR_AMT);
					}
					slLimitAmt.setLimitAmt(newLimitAmt);
					Long newFrozeLimit = frozeLimit + slTeamOrderDetail.getApplyNum();
					slLimitAmt.setFrozeLimit(newFrozeLimit);
					slTeamOrderDetail.setApplyFrozenLimit(slTeamOrderDetail.getApplyNum());
					slTeamOrder.setApplyFrozenAdvanceAmt(0l);
				} else {
					// 如果额度不够冻结
					Long remainLimit = slTeamOrderDetail.getApplyNum() - limitAmt;
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
						slTeamOrderDetail.setApplyFrozenLimit(limitAmt);
						slTeamOrder.setApplyFrozenAdvanceAmt(remainAavance);
					} else {
						throw new ServiceException(MSG.BF_ERROR_AMT);
					}
				}
				dbUtil.updateEntity("", session, slLimitAmt);
			}
			slTeamOrderDetail.setExamFrozenLimit(slTeamOrderDetail.getApplyFrozenLimit());
			slTeamOrderDetail.setSlTeamOrder(slTeamOrder);

			newDetailList.add(slTeamOrderDetail);
		}
		/*
		 * if(totalNum<Long.valueOf(getParemeterVal("team_low_num"))) { throw
		 * new ServiceException(MSG.BF_TEAM_LOW_NUM); }
		 */
		slTeamOrder.setExamUserId(userBean.getUserId());
		slTeamOrder.setExamTime(new Date());
		slTeamOrder.setExamFrozenAdvanceAmt(slTeamOrder.getApplyFrozenAdvanceAmt());
		slTeamOrder.setSlTeamOrderDetails(newDetailList);

		dbUtil.updateEntity("更新机构信息", session, slOrg);
		dbUtil.saveOrUpdateEntity("更新团队订单", session, slTeamOrder);
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

	@Override
	public PageBean<TeamOrderBean> listApply(UserBean userBean, TeamOrderBean teamOrderBean) throws ServiceException {
		StringBuffer sb = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ORG_ID", teamOrderBean.getOrgId());
		sb.append("SELECT * FROM SL_TEAM_ORDER T WHERE ORG_ID=:ORG_ID ");
		if (teamOrderBean != null) {
			if (StringUtil.isNotNull(teamOrderBean.getStat())) {
				sb.append(" AND T.STAT=:STAT ");
				params.put("STAT", teamOrderBean.getStat());
			} else {
				sb.append(" AND T.STAT IN('01','02','03') ");
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
		}
		sb.append(" ORDER BY T.APPLY_TIME DESC");
		String sql = sb.toString();
		PageBean<TeamOrderBean> ret = dbUtil.queryPageToBean("查询团队申请列表", sql, TeamOrderBean.class, userBean.getPageNum(), userBean.getPageSize(), params);
		return ret;
	}

	@Override
	public PageBean<TeamOrderBean> listApplyHis(UserBean userBean, TeamOrderBean teamOrderBean) throws Exception {
		StringBuffer sb = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ORG_ID", teamOrderBean.getOrgId());
		// 查询已审核的数据
		sb.append("SELECT T.* FROM SL_TEAM_ORDER T WHERE ORG_ID=:ORG_ID ");
		if (teamOrderBean != null) {
			if (StringUtil.isNotNull(teamOrderBean.getStat())) {
				sb.append(" AND T.STAT=:STAT ");
				params.put("STAT", teamOrderBean.getStat());
			} else {
				// 历史查询状态(03-已审核,04-已换票05-已拒绝06-已流单)
				sb.append(" AND T.STAT in('04','05','06') ");
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
		}
		sb.append("ORDER BY T.APPLY_TIME DESC　");
		String sql = sb.toString();
		PageBean<TeamOrderBean> ret = dbUtil.queryPageToBean("查询团队申请列表", sql, TeamOrderBean.class, userBean.getPageNum(), userBean.getPageSize(), params);
		return ret;
	}

	@Override
	public List<SysTicketType> listTicketType() {
		String sql = "SELECT * FROM SYS_TICKET_TYPE T WHERE T.TEAM_FLAG='Y' ";
		return dbUtil.queryListToBean("查询票种信息", sql, SysTicketType.class);
	}

	@Override
	public void addOrder(UserBean userBean, SlTeamOrder slTeamOrder, List<SlTeamOrderDetail> detailList) throws Exception {
		// SlOrg slOrg = dbUtil.findById("", SlOrg.class,
		// slTeamOrder.getOrgId());
		Session session = null;
		try {
			String applyId = getSeqByTeam("T", Integer.valueOf(getParemeterVal("team_appid_len")));
			
			session = dbUtil.getSessionByTransaction();
			
			SlOrg slOrg = dbUtil.queryFirstListToBeanLock("查询机构信息", session, "FROM SlOrg U where orgId=?", "U", SlOrg.class, slTeamOrder.getOrgId());
			Date inDt = slTeamOrder.getInDt();
			String currDateStr = DateUtil.formatDateToString(new Date(), "yyyy-MM-dd");
			if (inDt.getTime() < (DateUtil.formatStringToDate(currDateStr, "yyyy-MM-dd")).getTime()) {
				throw new ServiceException(MSG.ERROR, "入园日期应大于等于当前日期！");
			}
			slTeamOrder.setApplyId(applyId);
			// slTeamOrder.setOrgId(userBean.getOrgId());
			slTeamOrder.setApplyUserId(userBean.getUserId());
			slTeamOrder.setApplyTime(new Date());
			slTeamOrder.setOrgName(slOrg.getOrgName());
			String cUserId = slTeamOrder.getChangeUserId();
			SlChangeUser slChangeUser = dbUtil.findById("", SlChangeUser.class, cUserId);
			slTeamOrder.setChangeUserName(slChangeUser.getChangeUserName());
			slTeamOrder.setApplyFrozenAdvanceAmt(0l);
			
			slTeamOrder.setExamType("03");

			for (SlTeamOrderDetail slTeamOrderDetail : detailList) {
				SysTicketTypePrice sysTicketTypePriceNew = dbUtil.queryFirstForBean("查询是否超过三个阶梯票价段", "SELECT * FROM SYS_TICKET_TYPE_PRICE T WHERE T.TICKET_TYPE_ID=? AND ?>END_NO-START_NO+1", SysTicketTypePrice.class, slTeamOrderDetail.getTicketTypeId(), slTeamOrderDetail.getApplyNum());
				if (sysTicketTypePriceNew != null) {
					throw new ServiceException(MSG.BF_ERROR, "您[" + slTeamOrderDetail.getTicketTypeName() + "]申请张数" + slTeamOrderDetail.getApplyNum() + "超过两个票价阶梯段，请重新添加订单");
				}
			}
			// 临时保存
			if (Constant.TEAM_EXAM_STAT_01.equals(slTeamOrder.getStat())) {
				List<SlTeamOrderDetail> newDetailList = new ArrayList<SlTeamOrderDetail>();
				Long totalNum = 0l;
				for (SlTeamOrderDetail slTeamOrderDetail : detailList) {
					totalNum = totalNum + slTeamOrderDetail.getApplyNum();
					slTeamOrderDetail.setDetailId(UUIDGenerator.getPK());
					slTeamOrderDetail.setSlTeamOrder(slTeamOrder);
					newDetailList.add(slTeamOrderDetail);
				}
				/*
				 * //判断总人数是否达到成团最低人数
				 * if(totalNum<Long.valueOf(getParemeterVal("team_low_num"))) {
				 * throw new ServiceException(MSG.BF_TEAM_LOW_NUM); }
				 */
				slTeamOrder.setSlTeamOrderDetails(newDetailList);
				dbUtil.saveEntity("", session, slTeamOrder);
			}
			// 正式保存，需冻结额度及预付款
			else {
				doCommit(userBean, session, slOrg, slTeamOrder, detailList);
			}
			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}
	}

	@Override
	public List<SlChangeUser> listChangeUser(String orgId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ORG_ID", orgId);
		String sql = "SELECT * FROM SL_CHANGE_USER WHERE ORG_ID=:ORG_ID";
		return dbUtil.queryListToBean("", sql, SlChangeUser.class, params);
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
	public void delOrder(String applyId) {
		dbUtil.deleteEntity("", SlTeamOrder.class, applyId);
	}

	@Override
	public void updChangeUser(TeamOrderBean teamOrderBean) throws ServiceException {
		SlTeamOrder slTeamOrder = dbUtil.findById("", SlTeamOrder.class, teamOrderBean.getApplyId());
		slTeamOrder.setChangeUserId(teamOrderBean.getChangeUserId());
		dbUtil.updateEntity("更新换票人", slTeamOrder);
	}

}
