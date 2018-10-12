package com.tbims.rpc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.TException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tbims.annontion.ControlAspect;
import com.tbims.bean.RptSaleDataOutlet;
import com.tbims.bean.RptSaleDataUser;
import com.tbims.bean.RptStrData;
import com.tbims.cache.ClientAuthCache;
import com.tbims.common.OperType;
import com.tbims.db.entity.SlOrder;
import com.tbims.db.entity.SlOrderDetail;
import com.tbims.db.entity.SlOrderTickettypeDetail;
import com.tbims.db.entity.SlOrderTickettypeDetailId;
import com.tbims.db.entity.SlOrderVenueDetail;
import com.tbims.db.entity.SlOrderVenueDetailId;
import com.tbims.db.entity.SlOrg;
import com.tbims.db.entity.SlPayType;
import com.tbims.db.entity.StrDeliveryApply;
import com.tbims.db.entity.StrDeliveryApplyDetail;
import com.tbims.db.entity.StrDeliveryDetail;
import com.tbims.db.entity.StrOutletInfo;
import com.tbims.db.entity.StrOutletInfoId;
import com.tbims.db.entity.StrTicketInfo;
import com.tbims.db.entity.SysTicketTypeVenue;
import com.tbims.db.entity.SysUser;
import com.tbims.db.util.DBUtil;
import com.tbims.init.ServerMain;
import com.tbims.rpc.entity.AUTHORIZATION;
import com.tbims.rpc.entity.RPCException;
import com.tbims.rpc.entity.RPT_SALE_DATA_OUTLET;
import com.tbims.rpc.entity.RPT_SALE_DATA_USER;
import com.tbims.rpc.entity.RPT_STR_DATA;
import com.tbims.rpc.entity.SL_CHECK;
import com.tbims.rpc.entity.SL_NETAGENT_ORDER;
import com.tbims.rpc.entity.SL_ORDER;
import com.tbims.rpc.entity.SL_ORDER_DETAIL;
import com.tbims.rpc.entity.SL_ORDER_TICKETTYPE_DETAIL;
import com.tbims.rpc.entity.SL_ORG;
import com.tbims.rpc.entity.SL_PAY_TYPE;
import com.tbims.rpc.entity.SL_SUPPLY;
import com.tbims.rpc.entity.SL_TEAM_ORDER;
import com.tbims.rpc.entity.SL_USELESS_TICKET_INFO;
import com.tbims.rpc.entity.STR_DELIVERY_APPLY;
import com.tbims.rpc.entity.STR_DELIVERY_APPLY_DETAIL;
import com.tbims.rpc.entity.STR_DELIVERY_DETAIL;
import com.tbims.rpc.entity.STR_TICKET_INFO;
import com.tbims.rpc.entity.SYS_CLIENT_OUTLET;
import com.tbims.rpc.entity.USELESS_TICKET_DETAIL;
import com.tbims.util.BeanUtils;
import com.tbims.util.CommonUtil;
import com.tbims.util.StringUtil;

@Component
public class SaleServiceImpl implements com.tbims.rpc.service.SaleService.Iface {
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ServerMain.class);

	@Autowired
	DBUtil dbUtil;

	@Override
	public SYS_CLIENT_OUTLET getInfoByClientId(AUTHORIZATION auth, long clientId) throws RPCException, TException {
		return null;
	}

	@Override
	@ControlAspect(funtionName = "查询代理商信息")
	public List<SL_ORG> querySlOrg(AUTHORIZATION auth, String orgType, long outletId) throws RPCException, TException {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		sql.append("SELECT * FROM SL_ORG WHERE 1=1 ");

		if (StringUtil.isNotNull(orgType)) {
			sql.append(" AND ORG_TYPE=:ORG_TYPE");
			params.put("ORG_TYPE", orgType);
		}

		if (outletId != 0) {
			sql.append(" AND ORG_ID IN (SELECT ORG_ID FROM SYS_OUTLET WHERE OUTLET_ID=:OUTLET_ID)");
			params.put("OUTLET_ID", outletId);
		}

		List<SlOrg> slOrgs = dbUtil.queryListToBean("查询代理商信息", sql.toString(), SlOrg.class, params);
		List<SL_ORG> sl_orgs = new ArrayList<SL_ORG>();
		for (SlOrg slOrg : slOrgs) {
			SL_ORG sl_org = new SL_ORG();
			BeanUtils.copyRPCProperties(slOrg, sl_org);

			sl_orgs.add(sl_org);
		}

		return sl_orgs;
	}

	@Override
	@ControlAspect(funtionName = "门票配送申请查询")
	public List<STR_DELIVERY_APPLY> ticketApplyQuery(com.tbims.rpc.entity.AUTHORIZATION auth, java.lang.String applyId, long app_begin_tm, long app_end_tm, String exam_stat, long outlet_id)
			throws RPCException, TException {

		List<STR_DELIVERY_APPLY> retList = new ArrayList<STR_DELIVERY_APPLY>();

		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM STR_DELIVERY_APPLY WHERE OUTLET_ID=:OUTLET_ID ");

		params.put("OUTLET_ID", outlet_id);

		if (StringUtil.isNotNull(applyId)) {
			sql.append(" AND APPLY_ID=:APPLY_ID");
			params.put("APPLY_ID", applyId);
		}

		if (app_begin_tm != 0 && app_end_tm != 0) {
			sql.append(" AND APPLY_TIME BETWEEN :APP_BEGIN_TM AND :APP_END_TM");
			params.put("APP_BEGIN_TM", new Date(app_begin_tm));
			params.put("APP_END_TM", new Date(app_end_tm));
		}

		if (StringUtil.isNotNull(exam_stat)) {
			sql.append(" AND EXAM_STAT in (" + exam_stat + ")");
		}

		List<StrDeliveryApply> rets = dbUtil.queryListToBean("门票配送申请查询", sql.toString(), StrDeliveryApply.class, params);
		for (StrDeliveryApply strDeliveryApply_DB : rets) {
			STR_DELIVERY_APPLY delivery_apply_RPC = new STR_DELIVERY_APPLY(); // 申请单信息
			List<STR_DELIVERY_APPLY_DETAIL> delivery_apply_detailList_RPC = new ArrayList<STR_DELIVERY_APPLY_DETAIL>();// 配送申请明细
			List<STR_DELIVERY_DETAIL> delivery_detailList_RPC = new ArrayList<STR_DELIVERY_DETAIL>();// 配送明细

			// 转换申请单信息
			BeanUtils.copyRPCProperties(strDeliveryApply_DB, delivery_apply_RPC);

			// 设置申请人名称
			SysUser appSysUser = dbUtil.findById("查询用户", SysUser.class, strDeliveryApply_DB.getApplyUserId());
			if (appSysUser != null) {
				delivery_apply_RPC.setApplyUserName(appSysUser.getUserName());
			} else {
				delivery_apply_RPC.setApplyUserName("");
			}

			// 转换配送申请明细表
			for (StrDeliveryApplyDetail strDeliveryApplyDetail : strDeliveryApply_DB.getStrDeliveryApplyDetails()) {
				STR_DELIVERY_APPLY_DETAIL delivery_apply_detail_RPC = new STR_DELIVERY_APPLY_DETAIL();
				BeanUtils.copyRPCProperties(strDeliveryApplyDetail, delivery_apply_detail_RPC);
				delivery_apply_detail_RPC.setApplyId(strDeliveryApplyDetail.getStrDeliveryApply().getApplyId());

				delivery_apply_detailList_RPC.add(delivery_apply_detail_RPC);
			}
			// 配送明细表
			for (StrDeliveryDetail strDeliveryDetail : strDeliveryApply_DB.getStrDeliveryDetails()) {
				STR_DELIVERY_DETAIL delivery_detail_RPC = new STR_DELIVERY_DETAIL();
				String[] noField1 = {};
				BeanUtils.copyRPCProperties(strDeliveryDetail, delivery_detail_RPC, noField1);
				delivery_detail_RPC.setApplyId(strDeliveryDetail.getStrDeliveryApply().getApplyId());
				delivery_detailList_RPC.add(delivery_detail_RPC);
			}

			delivery_apply_RPC.setStrDeliveryApplyDetaillist(delivery_apply_detailList_RPC);
			delivery_apply_RPC.setStrDeliveryDetailList(delivery_detailList_RPC);

			retList.add(delivery_apply_RPC);
		}

		return retList;
	}

	@Override
	@ControlAspect(funtionName = "门票配送申请", operType = OperType.ADD)
	public boolean ticketApply(AUTHORIZATION auth, STR_DELIVERY_APPLY delivery_apply) throws RPCException, TException {
		Session session = null;

		try {
			// 开启事务
			session = dbUtil.getSessionByTransaction();

			// 校验订单状态,没审核之前可以修改
			StrDeliveryApply strDeliveryApply = dbUtil.findById("", StrDeliveryApply.class, delivery_apply.applyId);
			if (strDeliveryApply != null) {
				if (!"0".equals(strDeliveryApply.getExamStat())) {
					throw new RPCException(3, "订单不是待审核状态,不能修改");
				}
			} else {
				strDeliveryApply = new StrDeliveryApply();
			}

			// 配送申请
			BeanUtils.copyRPCProperties(delivery_apply, strDeliveryApply);

			// 配送申请明细
			strDeliveryApply.setStrDeliveryApplyDetails(new ArrayList<StrDeliveryApplyDetail>());
			for (STR_DELIVERY_APPLY_DETAIL str_delivery_apply_detail : delivery_apply.strDeliveryApplyDetaillist) {
				StrDeliveryApplyDetail strDeliveryApplyDetail = new StrDeliveryApplyDetail();
				// 默认审核数量为申请数量
				str_delivery_apply_detail.setExamNum(str_delivery_apply_detail.getApplyNum());
				BeanUtils.copyRPCProperties(str_delivery_apply_detail, strDeliveryApplyDetail);
				strDeliveryApplyDetail.setStrDeliveryApply(strDeliveryApply);
				strDeliveryApply.getStrDeliveryApplyDetails().add(strDeliveryApplyDetail);
			}

			dbUtil.saveOrUpdateEntity("批量保存配送申请", session, strDeliveryApply);

			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}

		return true;
	}

	@Override
	@ControlAspect(funtionName = "门票配送确认", operType = OperType.UPDATE)
	public boolean ticketConfirm(AUTHORIZATION auth, String applyId, String examStat) throws RPCException, TException {
		Session session = null;

		try {
			// 开启事务
			session = dbUtil.getSessionByTransaction();
			// 查询订单
			StrDeliveryApply strDeliveryApply = dbUtil.findById("", StrDeliveryApply.class, applyId);
			if (strDeliveryApply != null) {
				if (!"2".equals(strDeliveryApply.getExamStat())) {
					throw new RPCException(3, "订单不是已配送状态,不能修改");
				}
			} else {
				throw new RPCException(3, "订单号不存在");
			}

			// 修改状态为3-已接收4-已拒收
			strDeliveryApply.setExamStat(examStat);
			strDeliveryApply.setSignUserId(ClientAuthCache.getAuthBean(auth.getClientId()).getSysUser().getUserId());
			strDeliveryApply.setSignTime(new Date());
			dbUtil.updateEntity("修改订单状态", session, strDeliveryApply);

			Long outletId = ClientAuthCache.getAuthBean(auth.getClientId()).getSysOutlet().getOutletId();

			// 门票库存表 状态(000-未核实 001-已核实 003-已销售,004-已作废,005-配送中,006-已接收)
			String ticketState = "";
			String ticketStoreId = "";
			if ("3".equals(examStat)) {// 状态为3-已接收4-已拒收
				ticketState = "006";
				ticketStoreId = StringUtil.convertString(outletId);
			} else if ("4".equals(examStat)) {
				ticketState = "001";
				ticketStoreId = "";
			}

			// 更新门票库存表，网点编号和箱库存状态,网点签收时间
			dbUtil.executeSql("更新库存箱表状态", session, "UPDATE STR_TICKET_INFO T SET STAT=?,STORE_ID=?,SIGN_TIME=? " + "WHERE EXISTS (SELECT 1 FROM STR_DELIVERY_DETAIL A  WHERE APPLY_ID =? AND "
					+ "T.TICKET_ID BETWEEN A.BEGIN_NO AND A.END_NO )", ticketState, ticketStoreId, new Date(), applyId);

			// 批量保存更新网点库存
			if ("3".equals(examStat)) {
				List<StrOutletInfo> strOutletInfoList = new ArrayList<StrOutletInfo>();
				List<Map<String, Object>> strDeliveryDetailList = dbUtil.queryListToMap("汇总配送明细数量",
						"SELECT TT.TICKET_TYPE_ID,SUM(TT.END_NO-TT.BEGIN_NO+1) TICKET_NUM" + " FROM STR_DELIVERY_DETAIL TT WHERE APPLY_ID = ?  GROUP BY TT.TICKET_TYPE_ID ", applyId);
				Map<String, StrOutletInfo> strOutletInfos = dbUtil.queryMapForBean("查询网点库存", "SELECT * FROM STR_OUTLET_INFO WHERE OUTLET_ID=? ", StrOutletInfo.class, "id.ticketTypeId", ticketStoreId);

				for (Map<String, Object> strDeliveryDetail : strDeliveryDetailList) {
					String ticketTypeId = StringUtil.convertString(strDeliveryDetail.get("ticketTypeId"));
					Long ticketNum = CommonUtil.covertLong((strDeliveryDetail.get("ticketNum")));
					if (strOutletInfos.containsKey(ticketTypeId)) {
						StrOutletInfo strOutletInfo = strOutletInfos.get(ticketTypeId);
						strOutletInfo.setStrTicketNum(CommonUtil.covertLong(strOutletInfo.getStrTicketNum()) + ticketNum); // 网点库存数量增加
						strOutletInfo.setLastUpdTime(new Date());
						strOutletInfos.put(ticketTypeId, strOutletInfo);
					} else {
						StrOutletInfo strOutletInfo = new StrOutletInfo();
						strOutletInfo.setId(new StrOutletInfoId(CommonUtil.covertLong(ticketStoreId), ticketTypeId));
						strOutletInfo.setStrTicketNum(ticketNum);// 网点库存数量
						strOutletInfo.setLastUpdTime(new Date());
						strOutletInfos.put(ticketTypeId, strOutletInfo);
					}
				}

				strOutletInfoList.addAll(strOutletInfos.values());
				dbUtil.saveOrUpdateEntityBatch("批量保存更新网点库存", session, strOutletInfoList);
			}

			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}

		return true;
	}

	@Override
	@ControlAspect(funtionName = "票据信息查询")
	public STR_TICKET_INFO ticketInfoQuery(AUTHORIZATION auth, long ticket_id, long outlet_id) throws RPCException, TException {
		return null;
	}

	@Override
	@ControlAspect(funtionName = "检票信息查询")
	public List<SL_CHECK> checkInfoQuery(AUTHORIZATION auth, long ticket_id) throws RPCException, TException {
		return null;
	}

	@Override
	@ControlAspect(funtionName = "团队订单查询")
	public SL_TEAM_ORDER teamOrderQuery(AUTHORIZATION auth, String apply_id) throws RPCException, TException {
		return null;
	}

	@Override
	@ControlAspect(funtionName = "自营售票验票号接口")
	public boolean checkSaleTicketBYzy(AUTHORIZATION auth, long begin_ticket_id, long ticket_num, long end_ticket_id) throws RPCException, TException {
		return false;
	}

	@Override
	@ControlAspect(funtionName = "同步售票信息", operType = OperType.ADD)
	public boolean saleTicket(AUTHORIZATION auth, List<SL_ORDER> sl_orders) throws RPCException, TException {
		Session session = null;

		try {
			String userId = ClientAuthCache.getAuthBean(auth.getClientId()).getSysUser().getUserId();
			Long outletId = ClientAuthCache.getAuthBean(auth.getClientId()).getSysOutlet().getOutletId();

			// 开启事务
			session = dbUtil.getSessionByTransaction();

			// 订单和票据信息列表
			List<SlOrder> slOrderList = new ArrayList<SlOrder>();
			List<SlOrderDetail> slOrderDetailList = new ArrayList<SlOrderDetail>();
			List<SlPayType> slPayTypeList = new ArrayList<SlPayType>();
			List<StrTicketInfo> strTicketInfoList = new ArrayList<StrTicketInfo>();
			List<SlOrderTickettypeDetail> slOrderTickettypeDetailList = new ArrayList<SlOrderTickettypeDetail>();
			List<SlOrderVenueDetail> slOrderVenueDetailList = new ArrayList<SlOrderVenueDetail>();
			List<StrOutletInfo> strOutletInfoList = new ArrayList<StrOutletInfo>();

			// 保存销售单
			for (SL_ORDER sl_order : sl_orders) {
				SlOrder slOrder = new SlOrder();
				Map<String, SlOrderTickettypeDetail> slOrderTickettypeDetailMap = new HashMap<String, SlOrderTickettypeDetail>();// 缓存每个票种的售票明细

				BeanUtils.copyRPCProperties(sl_order, slOrder);

				// 保存销售单-票据明细
				if (sl_order.getSlOrderDetaillist() == null && sl_order.getSlOrderDetaillist().size() == 0) {
					throw new RPCException(4, "销售单-票据明细列表为空");
				}
				for (SL_ORDER_DETAIL sl_order_detail : sl_order.getSlOrderDetaillist()) {
					SlOrderDetail slOrderDetail = new SlOrderDetail();
					BeanUtils.copyRPCProperties(sl_order_detail, slOrderDetail);
					slOrderDetail.setCheckFlag("N"); // CHECK_FLAG 是否检票(Y是N否)
					slOrderDetail.setUselessFlag("N"); // USELESS_FLAG 是否作废(Y是N否)

					StrTicketInfo ticketInfo = dbUtil.findById("查询门票库存", StrTicketInfo.class, slOrderDetail.getTicketId());
					if (ticketInfo != null) {
						ticketInfo.setStat("003");// 状态(000-未核实 001-已核实 003-已销售,004-已作废)
						ticketInfo.setSaleUserId(userId);
						ticketInfo.setSaleOpeTime(new Date());
						strTicketInfoList.add(ticketInfo);

						slOrderDetail.setTicketUid(ticketInfo.getTicketUid());
					}

					slOrderDetailList.add(slOrderDetail);

					// 缓存每个票种的售票汇总信息
					if (slOrderTickettypeDetailMap.containsKey(sl_order_detail.getTicketTypeId())) {
						// 每个票种的售票明细
						SlOrderTickettypeDetail slOrderTickettypeDetail = slOrderTickettypeDetailMap.get(sl_order_detail.getTicketTypeId());
						slOrderTickettypeDetail.setTicketCount(slOrderTickettypeDetail.getTicketCount() + 1);
						slOrderTickettypeDetail.setEjectTicketCount(slOrderTickettypeDetail.getEjectTicketCount() + 1);
						slOrderTickettypeDetailMap.put(sl_order_detail.getTicketTypeId(), slOrderTickettypeDetail);
					} else {
						// 每个票种的售票明细
						SlOrderTickettypeDetail slOrderTickettypeDetail = new SlOrderTickettypeDetail();
						slOrderTickettypeDetail.setId(new SlOrderTickettypeDetailId(sl_order.getOrderId(), sl_order_detail.getTicketTypeId()));
						slOrderTickettypeDetail.setTicketCount(1L);
						slOrderTickettypeDetail.setEjectTicketCount(1L);
						slOrderTickettypeDetailMap.put(sl_order_detail.getTicketTypeId(), slOrderTickettypeDetail);
					}

					// 保存门票场馆明细表
					List<SysTicketTypeVenue> sysTicketTypeVenueList = dbUtil.queryListToBean("查询票种场馆表", "SELECT * FROM SYS_TICKET_TYPE_VENUE WHERE TICKET_TYPE_ID=?", SysTicketTypeVenue.class,
							slOrderDetail.getTicketTypeId());
					for (SysTicketTypeVenue sysTicketTypeVenue : sysTicketTypeVenueList) {
						SlOrderVenueDetail slOrderVenueDetail = new SlOrderVenueDetail();
						slOrderVenueDetail.setId(new SlOrderVenueDetailId(slOrderDetail.getOrderDetailId(), sysTicketTypeVenue.getId().getVenueId()));
						slOrderVenueDetail.setRemainTimes(slOrderDetail.getValidateTimes());
						slOrderVenueDetailList.add(slOrderVenueDetail);
					}
				}

				// 保存销售单-支付明细
				if (sl_order.getSlPayTypelist() != null) {
					for (SL_PAY_TYPE sl_pay_type : sl_order.getSlPayTypelist()) {
						SlPayType slPayType = new SlPayType();
						BeanUtils.copyRPCProperties(sl_pay_type, slPayType);

						slPayTypeList.add(slPayType);
					}
				}

				slOrderList.add(slOrder);
				slOrderTickettypeDetailList.addAll(slOrderTickettypeDetailMap.values());
			}

			// 核减网点库存数量
			Map<String, StrOutletInfo> strOutletInfos = dbUtil.queryMapForBean("查询网点库存", "SELECT * FROM STR_OUTLET_INFO WHERE OUTLET_ID=? ", StrOutletInfo.class, "id.ticketTypeId", outletId);
			for (SlOrderTickettypeDetail slOrderTickettypeDetail : slOrderTickettypeDetailList) {
				String ticketTypeId = slOrderTickettypeDetail.getId().getTicketTypeId();
				Long ticketNum = CommonUtil.covertLong((slOrderTickettypeDetail.getTicketCount()));
				if (strOutletInfos.containsKey(ticketTypeId)) {
					StrOutletInfo strOutletInfo = strOutletInfos.get(ticketTypeId);
					strOutletInfo.setStrTicketNum(CommonUtil.covertLong(strOutletInfo.getStrTicketNum()) - ticketNum); // 核减网点库存数量增加
					strOutletInfo.setLastUpdTime(new Date());
					strOutletInfos.put(ticketTypeId, strOutletInfo);
				} else {
					StrOutletInfo strOutletInfo = new StrOutletInfo();
					strOutletInfo.setId(new StrOutletInfoId(CommonUtil.covertLong(outletId), ticketTypeId));
					strOutletInfo.setStrTicketNum(-ticketNum);// 核减网点库存数量
					strOutletInfo.setLastUpdTime(new Date());
					strOutletInfos.put(ticketTypeId, strOutletInfo);
				}
			}

			strOutletInfoList.addAll(strOutletInfos.values());

			dbUtil.saveOrUpdateEntityBatch("批量保存订单", session, slOrderList);
			dbUtil.saveOrUpdateEntityBatch("批量保存订单明细", session, slOrderDetailList);
			dbUtil.saveOrUpdateEntityBatch("批量保存支付明细", session, slPayTypeList);
			dbUtil.saveOrUpdateEntityBatch("批量保存销售单票种明细表", session, slOrderTickettypeDetailList);
			dbUtil.saveOrUpdateEntityBatch("批量保存销售单门票场馆明细表", session, slOrderVenueDetailList);
			dbUtil.updateEntityBatch("批量更新门票库存信息", session, strTicketInfoList);
			dbUtil.saveOrUpdateEntityBatch("批量保存更新网点库存", session, strOutletInfoList);

			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}

		return true;
	}

	@Override
	@ControlAspect(funtionName = "售票信息上传 (WL-网络代理换票)", operType = OperType.ADD)
	public boolean changeTicket(AUTHORIZATION auth, SL_NETAGENT_ORDER sl_netagent_order) throws RPCException, TException {
		return false;
	}

	@Override
	@ControlAspect(funtionName = "补票信息上传 (PB-补票)", operType = OperType.ADD)
	public boolean supplyTicket(AUTHORIZATION auth, SL_SUPPLY sl_supply) throws RPCException, TException {
		return false;
	}

	@Override
	@ControlAspect(funtionName = "坏票信息上传", operType = OperType.ADD)
	public boolean uselessTicket(AUTHORIZATION auth, List<SL_USELESS_TICKET_INFO> useless_ticket_infos) throws RPCException, TException {
		return false;
	}

	@Override
	@ControlAspect(funtionName = "同步检票信息", operType = OperType.ADD)
	public boolean checkInfo(AUTHORIZATION auth, List<SL_CHECK> sl_checks) throws RPCException, TException {
		return false;
	}

	@Override
	@ControlAspect(funtionName = "网点员工销售统计表")
	public java.util.List<com.tbims.rpc.entity.RPT_SALE_DATA_USER> rptSaleDataUser(com.tbims.rpc.entity.AUTHORIZATION auth, long rpt_date, long outlet_id)
			throws com.tbims.rpc.entity.RPCException, org.apache.thrift.TException {

		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();

		// ORDER_TYPE:销售类型(XC-现场售票、ZY-自营售票、TD-团队换票、WL-网络人工换票、ST-实体代理、BP-补票、ZG-自助购票、ZQ-自助取票)
		sql.append("SELECT TRUNC(A.SALE_TIME, 'dd') RTP_DATE,E.OUTLET_ID,E.OUTLET_NAME,                                                      ");
		sql.append("       B.EJECT_USER_ID USER_ID,                                                                                                           ");
		sql.append("       C.USER_NAME,                                                                                                             ");
		sql.append("       D.PAY_TYPE,                                                                                                              ");
		sql.append("       F.ITEM_NAME PAY_NAME,                                                                                                    ");
		sql.append("       B.TICKET_TYPE_ID,                                                                                                        ");
		sql.append("       D.TICKET_TYPE_NAME,                                                                                                      ");
		sql.append("       SUM(CASE WHEN A.ORDER_TYPE IN('XC','ZY','ST','BP','ZG')  THEN B.SALE_PRICE ELSE 0 END) SALE_SUM_AMT,                      ");
		sql.append("       COUNT(1) SALE_NUM                                                                                                        ");
		sql.append(" FROM SL_ORDER A LEFT JOIN SL_ORDER_DETAIL B on  A.ORDER_ID = B.ORDER_ID     ");
		sql.append("      INNER JOIN SYS_USER C ON B.EJECT_USER_ID = C.USER_ID           ");
		sql.append("      LEFT JOIN SYS_TICKET_TYPE D ON B.TICKET_TYPE_ID = D.TICKET_TYPE_ID           ");
		sql.append("      LEFT JOIN SL_PAY_TYPE D ON A.ORDER_ID=D.ORDER_ID              ");
		sql.append("      LEFT JOIN SYS_DICTIONARY F ON D.PAY_TYPE=F.ITEM_CD AND F.KEY_CD='PAY_TYPE'     ");
		sql.append("      LEFT JOIN SYS_OUTLET E   ON  B.OUTLET_ID=E.OUTLET_ID   ");
		sql.append(" WHERE A.PAY_STAT='2' AND B.OUTLET_ID=:OUTLET_ID             ");

		params.put("OUTLET_ID", outlet_id);

		SysUser sysUser = ClientAuthCache.getAuthBean(auth.getClientId()).getSysUser();
		if ("03".equals(sysUser.getPositionCode())) {
			sql.append(" AND B.EJECT_USER_ID=:USER_ID ");
			params.put("USER_ID", sysUser.getUserId());
		}

		if (rpt_date != 0) {
			sql.append(" AND TRUNC(A.SALE_TIME, 'dd') = :RPT_DATE ");
			params.put("RPT_DATE", new Date(rpt_date));
		}

		sql.append("  GROUP BY E.OUTLET_ID,E.OUTLET_NAME,TRUNC(A.SALE_TIME, 'dd'),B.EJECT_USER_ID,C.USER_NAME,D.PAY_TYPE,F.ITEM_NAME,B.TICKET_TYPE_ID,D.TICKET_TYPE_NAME ");
		sql.append("  ORDER BY TRUNC(A.SALE_TIME, 'dd') DESC ");

		List<RPT_SALE_DATA_USER> rptSaleDataList = new ArrayList<RPT_SALE_DATA_USER>();
		List<RptSaleDataUser> retMaps = dbUtil.queryListToBean("", sql.toString(), RptSaleDataUser.class, params);
		for (RptSaleDataUser obj : retMaps) {
			RPT_SALE_DATA_USER rpt_sale_data_user = new RPT_SALE_DATA_USER();
			BeanUtils.copyRPCProperties(obj, rpt_sale_data_user);

			rptSaleDataList.add(rpt_sale_data_user);
		}

		return rptSaleDataList;
	}

	@Override
	@ControlAspect(funtionName = "网点销售统计表")
	public List<RPT_SALE_DATA_OUTLET> rptSaleDataOutlet(AUTHORIZATION auth, long rpt_date, long outlet_id) throws RPCException, TException {

		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();

		// ORDER_TYPE:销售类型(XC-现场售票、ZY-自营售票、TD-团队换票、WL-网络人工换票、ST-实体代理、BP-补票、ZG-自助购票、ZQ-自助取票)
		sql.append("SELECT TRUNC(A.SALE_TIME, 'dd') RTP_DATE,E.OUTLET_ID,E.OUTLET_NAME,                                                     ");
		sql.append("       D.PAY_TYPE,                                                                                                              ");
		sql.append("       F.ITEM_NAME PAY_NAME,                                                                                                    ");
		sql.append("       B.TICKET_TYPE_ID,                                                                                                        ");
		sql.append("       D.TICKET_TYPE_NAME,                                                                                                      ");
		sql.append("       SUM(CASE WHEN A.ORDER_TYPE IN('XC','ZY','ST','BP','ZG')  THEN B.SALE_PRICE ELSE 0 END) SALE_SUM_AMT,                     ");
		sql.append("       COUNT(1) SALE_NUM                                                                                                        ");
		sql.append(" FROM SL_ORDER A LEFT JOIN SL_ORDER_DETAIL B on  A.ORDER_ID = B.ORDER_ID     ");
		sql.append("      LEFT JOIN SYS_USER C ON B.EJECT_USER_ID = C.USER_ID           ");
		sql.append("      LEFT JOIN SYS_TICKET_TYPE D ON B.TICKET_TYPE_ID = D.TICKET_TYPE_ID           ");
		sql.append("      LEFT JOIN SL_PAY_TYPE D ON A.ORDER_ID=D.ORDER_ID              ");
		sql.append("      LEFT JOIN SYS_DICTIONARY F ON D.PAY_TYPE=F.ITEM_CD AND F.KEY_CD='PAY_TYPE'     ");
		sql.append("      LEFT JOIN SYS_OUTLET E   ON  B.OUTLET_ID=E.OUTLET_ID   ");
		sql.append(" WHERE A.PAY_STAT='2' AND B.OUTLET_ID=:OUTLET_ID             ");

		params.put("OUTLET_ID", outlet_id);

		SysUser sysUser = ClientAuthCache.getAuthBean(auth.getClientId()).getSysUser();
		if ("03".equals(sysUser.getPositionCode())) {
			sql.append(" AND B.EJECT_USER_ID=:USER_ID ");
			params.put("USER_ID", sysUser.getUserId());
		}

		if (rpt_date != 0) {
			sql.append(" AND TRUNC(A.SALE_TIME,'dd') = :RPT_DATE ");
			params.put("RPT_DATE", new Date(rpt_date));
		}

		sql.append("  GROUP BY E.OUTLET_ID,E.OUTLET_NAME,TRUNC(A.SALE_TIME,'dd'),D.PAY_TYPE,F.ITEM_NAME,B.TICKET_TYPE_ID,D.TICKET_TYPE_NAME ");
		sql.append("  ORDER BY TRUNC(A.SALE_TIME, 'dd') DESC ");

		List<RPT_SALE_DATA_OUTLET> rptSaleDataList = new ArrayList<RPT_SALE_DATA_OUTLET>();
		List<RptSaleDataOutlet> retMaps = dbUtil.queryListToBean("", sql.toString(), RptSaleDataOutlet.class, params);
		for (RptSaleDataOutlet obj : retMaps) {
			RPT_SALE_DATA_OUTLET rpt_sale_data_outlet = new RPT_SALE_DATA_OUTLET();
			BeanUtils.copyRPCProperties(obj, rpt_sale_data_outlet);

			rptSaleDataList.add(rpt_sale_data_outlet);
		}

		return rptSaleDataList;
	}

	@Override
	@ControlAspect(funtionName = "网点库存统计表")
	public List<RPT_STR_DATA> rptStrData(AUTHORIZATION auth, long rpt_date, long outlet_id) throws RPCException, TException {

		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT RPT_DATE,                                                         ");
		sql.append("       RPT.OUTLET_ID,                                                     ");
		sql.append("       O.OUTLET_NAME,                                                     ");
		sql.append("       RPT.TICKET_TYPE_ID,                                                ");
		sql.append("       T.TICKET_TYPE_NAME,                                                ");
		sql.append("       STR_LAST_TICKET_NUM,                                               ");
		sql.append("       IN_TICKET_NUM DELIVERY_NUM,                                        ");
		sql.append("       SALE_TICKET_NUM,                                                   ");
		sql.append("       USELESS_TICKET_NUM USELESS_NUM,                                    ");
		sql.append("       STR_TICKET_NUM                                                     ");
		sql.append(" FROM RPT_STRINFO_OUTLET_D RPT ");
		sql.append("   LEFT JOIN  SYS_TICKET_TYPE T ON RPT.TICKET_TYPE_ID=T.TICKET_TYPE_ID ");
		sql.append("   LEFT JOIN  SYS_OUTLET O ON    RPT.OUTLET_ID=O.OUTLET_ID      ");
		sql.append("  WHERE RPT.OUTLET_ID=:OUTLET_ID ");
		params.put("OUTLET_ID", outlet_id);

		if (rpt_date != 0) {
			sql.append(" AND RPT.RPT_DATE= :RPT_DATE ");
			params.put("RPT_DATE", new Date(rpt_date));
		} else {
			throw new RPCException(4, "日期参数错误");
		}

		List<RPT_STR_DATA> rptStrDataList = new ArrayList<RPT_STR_DATA>();
		List<RptStrData> retMaps = dbUtil.queryListToBean("", sql.toString(), RptStrData.class, params);
		for (RptStrData obj : retMaps) {
			RPT_STR_DATA rpt_str_data = new RPT_STR_DATA();
			BeanUtils.copyRPCProperties(obj, rpt_str_data);

			rptStrDataList.add(rpt_str_data);
		}

		return rptStrDataList;
	}

	@Override
	@ControlAspect(funtionName = "当日网点库存统计表")
	public List<RPT_STR_DATA> rptStrDataNow(AUTHORIZATION auth, long outlet_id) throws RPCException, TException {
		if (outlet_id == 0) {
			throw new RPCException(4, "参数错误:网点编号错误");

		}

		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select mysum.RPT_DATE,     ");
		sb.append("        mysum.outlet_id,    ");
		sb.append("        mysum.ticket_type_id, ");
		sb.append("        outlet.outlet_name,  ");
		sb.append("        ty.ticket_type_name,  ");
		sb.append("        sum(str_last_ticket_num) str_last_ticket_num,  ");
		sb.append("        sum(in_ticket_num) delivery_Num, ");
		sb.append("        sum(sale_ticket_num) sale_ticket_num,   ");
		sb.append("        sum(useless_ticket_num) useless_num, ");
		sb.append("        sum(str_ticket_num)    str_ticket_num   ");
		sb.append("   from (select trunc(sysdate, 'dd') rpt_date,      ");
		sb.append("                ti.outlet_id,         ");
		sb.append("                ti.ticket_type_id,                   ");
		sb.append("                0 str_last_ticket_num,              ");
		sb.append("                0 in_ticket_num,                    ");
		sb.append("                0 sale_ticket_num,                  ");
		sb.append("                0 useless_ticket_num,               ");
		sb.append("                ti.str_ticket_num             ");
		sb.append("           from  str_outlet_info ti where 1=1         ");
		if (outlet_id != 0) {
			sb.append("         and  ti.outlet_id=:OUTLET_ID ");
		}
		sb.append("         union all                                  ");
		sb.append("         select trunc(c.sign_time, 'dd') rpt_date,  ");
		sb.append("                to_number(c.store_id) outlet_id,    ");
		sb.append("                c.ticket_type_id,                   ");
		sb.append("                0 str_last_ticket_num,              ");
		sb.append("                count(1) in_ticket_num,    ");
		sb.append("                0 sale_ticket_num,                  ");
		sb.append("                0 useless_ticket_num,               ");
		sb.append("                0 str_ticket_num                    ");
		sb.append("           from str_ticket_info c                   ");
		sb.append("          where c.store_id is not null              ");
		sb.append("            and trunc(c.sign_time, 'dd') = trunc(sysdate, 'dd') ");
		if (outlet_id != 0) {
			sb.append("               and 	c.store_id=:OUTLET_ID ");
		}
		sb.append("          group by trunc(c.sign_time, 'dd'), c.store_id, c.ticket_type_id ");
		sb.append("         union all ");
		sb.append("         select trunc(sysdate, 'dd') rpt_date, ");
		sb.append("                c.outlet_id,            ");
		sb.append("                c.ticket_type_id,       ");
		sb.append("                c.str_ticket_num,       ");
		sb.append("                0 in_ticket_num,        ");
		sb.append("                0 sale_ticket_num,      ");
		sb.append("                0 useless_ticket_num,   ");
		sb.append("                0 str_ticket_num        ");
		sb.append("           from rpt_strinfo_outlet_d c  ");
		sb.append("          where trunc(c.rpt_date, 'dd') = trunc(sysdate - 1, 'dd') ");
		if (outlet_id != 0) {
			sb.append("               and 	c.outlet_id=:OUTLET_ID ");
		}
		sb.append("         union all ");
		sb.append("         select trunc(od.eject_ticket_time, 'dd') rpt_date, ");
		sb.append("                od.outlet_id, ");
		sb.append("                od.ticket_type_id, ");
		sb.append("                0 str_last_ticket_num, ");
		sb.append("                0 in_ticket_num,     ");
		sb.append("                sum(case ");
		sb.append("                      when od.eject_ticket_stat = '2' then  ");
		sb.append("                       1    ");
		sb.append("                      else  ");
		sb.append("                       0    ");
		sb.append("                    end) sale_ticket_num,  ");
		sb.append("                0 useless_ticket_num,      ");
		sb.append("                0 str_ticket_num           ");
		sb.append("           from sl_order_detail od         ");
		sb.append("          inner join sl_order o            ");
		sb.append("             on od.order_id = o.order_id   ");
		sb.append("          where o.pay_stat='2' and trunc(od.eject_ticket_time, 'dd') = trunc(sysdate, 'dd')     ");
		if (outlet_id != 0) {
			sb.append("               and 	od.outlet_id=:OUTLET_ID ");
		}
		sb.append("          group by trunc(od.eject_ticket_time, 'dd'),                        ");
		sb.append("                   od.outlet_id,                                             ");
		sb.append("                   od.ticket_type_id                                         ");
		sb.append("         union all                                                           ");
		sb.append("         select trunc(useless.useless_time, 'dd') rpt_date,                  ");
		sb.append("                useless.outlet_id,                                           ");
		sb.append("                useless.ticket_type_id,                                      ");
		sb.append("                0 str_last_ticket_num,                                       ");
		sb.append("                0 in_ticket_num,                                             ");
		sb.append("                0 sale_ticket_num,                                           ");
		sb.append("                sum(1) useless_ticket_num,                                   ");
		sb.append("                0 str_ticket_num                                             ");
		sb.append("           from sl_useless_ticket_info useless                               ");
		sb.append("          where trunc(useless.useless_time, 'dd') = trunc(sysdate, 'dd')  ");
		if (outlet_id != 0) {
			sb.append("               and 	useless.outlet_id=:OUTLET_ID ");
		}
		sb.append("          group by trunc(useless.useless_time, 'dd'),                        ");
		sb.append("                   useless.outlet_id,                                        ");
		sb.append("                   useless.ticket_type_id) mysum                             ");
		sb.append("  inner join sys_outlet outlet                                               ");
		sb.append("     on mysum.outlet_id = outlet.outlet_id                                   ");
		sb.append("  inner join sys_ticket_type ty                                              ");
		sb.append("     on mysum.ticket_type_id = ty.ticket_type_id                             ");
		sb.append("  group by mysum.rpt_date,                                                   ");
		sb.append("           mysum.outlet_id,                                                  ");
		sb.append("           mysum.ticket_type_id,                                             ");
		sb.append("           outlet.outlet_name,                                               ");
		sb.append("           ty.ticket_type_name                                               ");
		params.put("OUTLET_ID", outlet_id);

		if (outlet_id != 0) {
			params.put("OUTLET_ID", outlet_id);
		}

		List<RPT_STR_DATA> rptStrDataList = new ArrayList<RPT_STR_DATA>();
		List<RptStrData> retMaps = dbUtil.queryListToBean("", sb.toString(), RptStrData.class, params);
		for (RptStrData obj : retMaps) {
			RPT_STR_DATA rpt_str_data = new RPT_STR_DATA();
			BeanUtils.copyRPCProperties(obj, rpt_str_data);

			rptStrDataList.add(rpt_str_data);
		}

		return rptStrDataList;
	}


	@Override
	public boolean saleTicketByZG(AUTHORIZATION auth, SL_ORDER sl_order, SL_ORDER_TICKETTYPE_DETAIL sl_order_tickettype_detail) throws RPCException, TException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saleTicketByIdenttyZG(AUTHORIZATION auth, SL_ORDER sl_order) throws RPCException, TException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<SL_ORDER_DETAIL> queryTicketByIdenttyId(AUTHORIZATION auth, String identty_id) throws RPCException, TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int querTicketPayStatus(AUTHORIZATION auth, String order_id) throws RPCException, TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean cancelTicketPay(AUTHORIZATION auth, String order_id) throws TException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ejectTicket(AUTHORIZATION auth, List<SL_ORDER_DETAIL> sl_order_detail_list) throws RPCException, TException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int checkTicketOnline(AUTHORIZATION auth, String checkId, String ticketClass, String ticketUid) throws RPCException, TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean cancelUselessTicket(AUTHORIZATION auth, List<USELESS_TICKET_DETAIL> useless_ticket_infos) throws RPCException, TException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<SL_TEAM_ORDER> queryTeamOrderDetail(AUTHORIZATION auth, long change_time, String apply_id) throws RPCException, TException {
		// TODO Auto-generated method stub
		return null;
	}

 

}
