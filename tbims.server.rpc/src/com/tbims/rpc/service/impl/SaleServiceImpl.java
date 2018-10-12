package com.tbims.rpc.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.TException;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.sun.jmx.snmp.Timestamp;
import com.tbims.annontion.ControlAspect;
import com.tbims.bean.CheckOnlineBean;
import com.tbims.bean.IdentiyCheckCacheBean;
import com.tbims.bean.RptSaleDataOutlet;
import com.tbims.bean.RptSaleDataUser;
import com.tbims.bean.RptStrData;
import com.tbims.bean.SlOrderTicketTypeDetailBean;
import com.tbims.bean.TicketTypeBeforePriceBean;
import com.tbims.bean.TicketTypeSaleInfoBean;
import com.tbims.bean.UselessTicketDetailBean;
import com.tbims.cache.ClientAuthCache;
import com.tbims.cache.IdentiyCheckCache;
import com.tbims.cache.MsgUtil;
import com.tbims.common.OperType;
import com.tbims.db.entity.SlChangeUser;
import com.tbims.db.entity.SlCheck;
import com.tbims.db.entity.SlLimitAmt;
import com.tbims.db.entity.SlNetagentOrder;
import com.tbims.db.entity.SlOrder;
import com.tbims.db.entity.SlOrderDetail;
import com.tbims.db.entity.SlOrderTickettypeDetail;
import com.tbims.db.entity.SlOrderTickettypeDetailId;
import com.tbims.db.entity.SlOrderVenueDetail;
import com.tbims.db.entity.SlOrderVenueDetailId;
import com.tbims.db.entity.SlOrg;
import com.tbims.db.entity.SlOrgSaleinfo;
import com.tbims.db.entity.SlOrgSaleinfoId;
import com.tbims.db.entity.SlPayType;
import com.tbims.db.entity.SlSupply;
import com.tbims.db.entity.SlTeamOrder;
import com.tbims.db.entity.SlTeamOrderDetail;
import com.tbims.db.entity.SlUselessTicketInfo;
import com.tbims.db.entity.StrDeliveryApply;
import com.tbims.db.entity.StrDeliveryApplyDetail;
import com.tbims.db.entity.StrDeliveryDetail;
import com.tbims.db.entity.StrOutletInfo;
import com.tbims.db.entity.StrOutletInfoId;
import com.tbims.db.entity.StrTicketInfo;
import com.tbims.db.entity.SysBlackList;
import com.tbims.db.entity.SysDictionary;
import com.tbims.db.entity.SysOutlet;
import com.tbims.db.entity.SysParemeter;
import com.tbims.db.entity.SysTicketType;
import com.tbims.db.entity.SysTicketTypePrice;
import com.tbims.db.entity.SysTicketTypeRule;
import com.tbims.db.entity.SysTicketTypeVenue;
import com.tbims.db.entity.SysUser;
import com.tbims.db.entity.SysVenue;
import com.tbims.db.util.DBUtil;
import com.tbims.pay.PayUtil;
import com.tbims.pay.bean.CancelOrderRequest;
import com.tbims.pay.bean.OrderPayRequest;
import com.tbims.pay.bean.QueryOrderRequest;
import com.tbims.pay.bfbank.config.SwiftpassConfig;
import com.tbims.rpc.entity.AUTHORIZATION;
import com.tbims.rpc.entity.QRY_IDENTTY_CHECK_INFO;
import com.tbims.rpc.entity.QRY_IDENTTY_SALE_INFO;
import com.tbims.rpc.entity.RPCException;
import com.tbims.rpc.entity.RPT_SALE_DATA_OUTLET;
import com.tbims.rpc.entity.RPT_SALE_DATA_USER;
import com.tbims.rpc.entity.RPT_STR_DATA;
import com.tbims.rpc.entity.SALE_DETAIL_ZY;
import com.tbims.rpc.entity.SL_CHECK;
import com.tbims.rpc.entity.SL_NETAGENT_ORDER;
import com.tbims.rpc.entity.SL_ORDER;
import com.tbims.rpc.entity.SL_ORDER_DETAIL;
import com.tbims.rpc.entity.SL_ORDER_TICKETTYPE_DETAIL;
import com.tbims.rpc.entity.SL_ORG;
import com.tbims.rpc.entity.SL_PAY_TYPE;
import com.tbims.rpc.entity.SL_SUPPLY;
import com.tbims.rpc.entity.SL_TEAM_ORDER;
import com.tbims.rpc.entity.SL_TEAM_ORDER_DETAIL;
import com.tbims.rpc.entity.SL_USELESS_TICKET_INFO;
import com.tbims.rpc.entity.STR_DELIVERY_APPLY;
import com.tbims.rpc.entity.STR_DELIVERY_APPLY_DETAIL;
import com.tbims.rpc.entity.STR_DELIVERY_DETAIL;
import com.tbims.rpc.entity.STR_TICKET_INFO;
import com.tbims.rpc.entity.SYS_CLIENT_OUTLET;
import com.tbims.rpc.entity.USELESS_TICKET_DETAIL;
import com.tbims.util.BeanUtils;
import com.tbims.util.CommonUtil;
import com.tbims.util.DateUtil;
import com.tbims.util.StringUtil;
import com.tbims.util.UUIDGenerator;

@Component
public class SaleServiceImpl implements com.tbims.rpc.service.SaleService.Iface {
	private static final Log logger = LogFactory.getLog(SaleServiceImpl.class);

	@Autowired
	DBUtil dbUtil;

	/**
	 * 支付接口 payUtilByBfBank:浦发银行接口实现
	 * 
	 */
	@Autowired
	@Qualifier("payUtilByBfBank")
	PayUtil payUtil;

	@Override
	public SYS_CLIENT_OUTLET getInfoByClientId(AUTHORIZATION auth, long clientId) throws RPCException, TException {
		return null;
	}

	@Override
	@ControlAspect(funtionName = "查询代理商信息")
	public List<SL_ORG> querySlOrg(AUTHORIZATION auth, String orgType, long outletId) throws RPCException, TException {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		sql.append("SELECT * FROM SL_ORG WHERE ORG_STAT='Y' ");

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
			sl_org.setCreditAmt(999999999);
			sl_orgs.add(sl_org);
		}

		return sl_orgs;
	}

	@Override
	@ControlAspect(funtionName = "门票配送申请查询")
	public List<STR_DELIVERY_APPLY> ticketApplyQuery(com.tbims.rpc.entity.AUTHORIZATION auth, java.lang.String applyId, long app_begin_tm, long app_end_tm, String exam_stat, long outlet_id) throws RPCException, TException {
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

			StrDeliveryApply strDeliveryApply = dbUtil.queryFirstListToBeanLock("", session, "FROM StrDeliveryApply U where applyId=?", "U", StrDeliveryApply.class, applyId);
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
			//dbUtil.executeSql("更新门票库存表状态", session, "UPDATE STR_TICKET_INFO T SET STAT=?,STORE_ID=?,SIGN_TIME=? " + "WHERE STORE_ID IS NULL AND EXISTS (SELECT 1 FROM STR_DELIVERY_DETAIL A  WHERE APPLY_ID =? AND " + "T.TICKET_ID BETWEEN A.BEGIN_NO AND A.END_NO )", ticketState, ticketStoreId, new Date(), applyId);
			List<StrDeliveryDetail> strDeliveryDetailListUpdate= dbUtil.queryListToBean("查询配送明细", "SELECT * FROM STR_DELIVERY_DETAIL WHERE APPLY_ID=?", StrDeliveryDetail.class, applyId);
			for(StrDeliveryDetail strDeliveryDetail:strDeliveryDetailListUpdate){
				dbUtil.executeSql("更新门票库存表状态", session, "UPDATE STR_TICKET_INFO T SET STAT=?,STORE_ID=?,SIGN_TIME=? WHERE STORE_ID IS NULL AND TICKET_ID between ? AND ?", ticketState, ticketStoreId, new Date(), strDeliveryDetail.getBeginNo(),strDeliveryDetail.getEndNo());
			}

			// 批量保存更新网点库存
			if ("3".equals(examStat)) {
				List<StrOutletInfo> strOutletInfoList = new ArrayList<StrOutletInfo>();
				List<Map<String, Object>> strDeliveryDetailList = dbUtil.queryListToMap("汇总配送明细数量", "SELECT TT.TICKET_TYPE_ID,SUM(TT.END_NO-TT.BEGIN_NO+1) TICKET_NUM" + " FROM STR_DELIVERY_DETAIL TT WHERE APPLY_ID = ?  GROUP BY TT.TICKET_TYPE_ID ", applyId);

				for (Map<String, Object> strDeliveryDetail : strDeliveryDetailList) {
					String ticketTypeId = StringUtil.convertString(strDeliveryDetail.get("ticketTypeId"));
					Long ticketNum = CommonUtil.covertLong((strDeliveryDetail.get("ticketNum")));
					addOutletInfos(session, outletId, ticketTypeId, ticketNum);
				}

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
		STR_TICKET_INFO str_ticket_info = new STR_TICKET_INFO();

		StrTicketInfo strTicketInfo = dbUtil.findById("票据信息查询", StrTicketInfo.class, ticket_id);
		if (strTicketInfo == null) {
			throw new RPCException(4, "票号不存在");
		}

		BeanUtils.copyRPCProperties(strTicketInfo, str_ticket_info);

		// 查询售票信息
		String sql = "SELECT B.TICKET_ID,B.OUTLET_ID,C.OUTLET_NAME,B.EJECT_TICKET_TIME" + " FROM SL_ORDER A, SL_ORDER_DETAIL B ,SYS_OUTLET C  " + " WHERE A.ORDER_ID=B.ORDER_ID AND B.OUTLET_ID=C.OUTLET_ID " + "AND B.TICKET_ID=? ";
		Map<String, Object> slOrderDetail = dbUtil.queryFirstForMap("查询售票信息", sql, ticket_id);
		if (slOrderDetail != null) {
			Date ejectTicketTime = (Date) slOrderDetail.get("ejectTicketTime");
			str_ticket_info.setSaleTime(DateUtil.formatDateToString(ejectTicketTime, "yyyy-MM-dd HH:mm:ss"));
			str_ticket_info.setOutletId(StringUtil.convertString(slOrderDetail.get("outletId")));
			str_ticket_info.setOutletName(StringUtil.convertString(slOrderDetail.get("outletName")));
		}

		// 是否在黑名单
		List<SysBlackList> blacks = dbUtil.queryListToBean("查询黑名单", "SELECT * FROM SYS_BLACK_LIST WHERE TICKET_ID=?", SysBlackList.class, ticket_id);
		if (blacks != null && blacks.size() > 0) {
			str_ticket_info.setBacklistFlag("是");
		} else {
			str_ticket_info.setBacklistFlag("否");
		}

		return str_ticket_info;
	}

	@Override
	@ControlAspect(funtionName = "检票信息查询")
	public List<SL_CHECK> checkInfoQuery(AUTHORIZATION auth, long ticket_id) throws RPCException, TException {

		List<SL_CHECK> sl_checks = new ArrayList<SL_CHECK>();
		List<SlCheck> slChecks = dbUtil.queryListToBean("检票信息查询", "SELECT * FROM SL_CHECK  WHERE TICKET_ID=?", SlCheck.class, ticket_id);
		for (SlCheck slCheck : slChecks) {
			SL_CHECK sl_check = new SL_CHECK();
			BeanUtils.copyRPCProperties(slCheck, sl_check);

			sl_checks.add(sl_check);
		}

		return sl_checks;
	}

	@Override
	@ControlAspect(funtionName = "团队订单查询")
	public SL_TEAM_ORDER teamOrderQuery(AUTHORIZATION auth, String apply_id) throws RPCException, TException {

		String userId = ClientAuthCache.getAuthBean(auth.getClientId()).getSysUser().getUserId();
		Long outletId = ClientAuthCache.getAuthBean(auth.getClientId()).getSysOutlet().getOutletId();

		SL_TEAM_ORDER sl_team_order = new SL_TEAM_ORDER();
		SlTeamOrder slTeamOrder = dbUtil.findById("团队订单查询", SlTeamOrder.class, apply_id);

		// 无此单号
		if (slTeamOrder == null) {
			throw new RPCException(60001, MsgUtil.getMsg(60001));
		}

		// 04-已换票 状态
		if (slTeamOrder.getStat().equals("04")) {
			throw new RPCException(6000101, "已换票");
		}

		// 非 03-已审核 状态
		if (!slTeamOrder.getStat().equals("03")) {
			throw new RPCException(6000101, MsgUtil.getMsg(6000101));
		}

		if (StringUtil.isNotNull(slTeamOrder.getChangeOpeUser()) && !userId.equals(slTeamOrder.getChangeOpeUser())) {
			throw new RPCException(4, "此团队订单号正在其它换票员处换票,请勿重复换票");
		}

		// 检验当天是否可以入园
		String inDt = DateUtil.formatDateToString(slTeamOrder.getInDt(), "yyyy-MM-dd");
		if (!inDt.equals(DateUtil.getNowDate("yyyy-MM-dd"))) {
			throw new RPCException(4, String.format("入园日期为[%s],不允许入园", inDt));
		}

		SlChangeUser changeUser = dbUtil.findById("查询换票人信息", SlChangeUser.class, slTeamOrder.getChangeUserId());
		if (changeUser == null) {
			throw new RPCException(6000102, MsgUtil.getMsg(6000102));
		}

		BeanUtils.copyRPCProperties(slTeamOrder, sl_team_order);
		sl_team_order.setCardType(changeUser.getCardType());
		sl_team_order.setCardId(changeUser.getCardId());
		sl_team_order.setTel(changeUser.getTel());

		// 设置证件类型名称
		Map<String, SysDictionary> sysDictionaryMap = dbUtil.queryMapForBean("查询字典", "SELECT * FROM SYS_DICTIONARY WHERE KEY_CD='ID_CARD_TYPE'", SysDictionary.class, "itemCd");
		SysDictionary sysDictionary = sysDictionaryMap.get(changeUser.getCardType());
		if (sysDictionary != null) {
			sl_team_order.setCardTypeName(sysDictionary.getItemName());
		} else {
			sl_team_order.setCardTypeName("");
		}

		// 设置审核人名称
		if (StringUtil.isNotNull(slTeamOrder.getExamUserId())) {
			SysUser examSysUser = dbUtil.findById("查询用户", SysUser.class, slTeamOrder.getExamUserId());
			if (examSysUser != null) {
				sl_team_order.setExamUserName(examSysUser.getUserName());
			} else {
				sl_team_order.setExamUserName(slTeamOrder.getExamUserId());
			}
		}

		List<SlTeamOrderDetail> slTeamOrderDetails = slTeamOrder.getSlTeamOrderDetails();
		List<SL_TEAM_ORDER_DETAIL> sl_team_order_details = new ArrayList<SL_TEAM_ORDER_DETAIL>();
		for (SlTeamOrderDetail slTeamOrderDetail : slTeamOrderDetails) {
			SL_TEAM_ORDER_DETAIL sl_team_order_detail = new SL_TEAM_ORDER_DETAIL();
			BeanUtils.copyRPCProperties(slTeamOrderDetail, sl_team_order_detail);
			sl_team_order_detail.setApplyId(slTeamOrderDetail.getSlTeamOrder().getApplyId());
			sl_team_order_details.add(sl_team_order_detail);
		}
		sl_team_order.setTeamOrderDetail(sl_team_order_details);

		slTeamOrder.setChangeOpeUser(userId);
		slTeamOrder.setChangeOutletId(outletId);

		dbUtil.updateEntity("更新换票信息", slTeamOrder);

		return sl_team_order;
	}

	@Override
	@ControlAspect(funtionName = "自营售票验票号接口")
	public boolean checkSaleTicketBYzy(AUTHORIZATION auth, long begin_ticket_id, long ticket_num, long end_ticket_id) throws RPCException, TException {

		Long outletId = ClientAuthCache.getAuthBean(auth.getClientId()).getSysOutlet().getOutletId();

		// 验证门票数量
		// 状态(000-未核实 001-已核实 003-已销售,004-已作废,005-配送中,006-已接收(网点已接收))
		int ticketNum = dbUtil.count("自营售票验票号", "SELECT 1 FROM STR_TICKET_INFO B " + "WHERE B.STAT in ('006') AND B.TICKET_ID >=?" + " AND B.TICKET_ID<=? AND B.STORE_ID=?", begin_ticket_id, end_ticket_id, outletId);
		if (ticketNum != ticket_num) {
			throw new RPCException(5000101, MsgUtil.getMsg(5000101));
		}

		return true;
	}

	@Override
	@ControlAspect(funtionName = "同步自营售票信息", operType = OperType.ADD)
	public boolean saleTicketByZY(AUTHORIZATION auth, List<SALE_DETAIL_ZY> saleDetailZYList, String payType, long sumAmt, long sumNum) throws RPCException, TException {
		Session session = null;
		// 售票信息上传 (ZY-自营售票)
		try {
			String userId = ClientAuthCache.getAuthBean(auth.getClientId()).getSysUser().getUserId();
			Long outletId = ClientAuthCache.getAuthBean(auth.getClientId()).getSysOutlet().getOutletId();

			checkOutletInfos(outletId);

			String orderId = String.format("%dXS%s", auth.getClientId(), DateUtil.getNowDate("yyyyMMddHHmmssSSSS"));

			// 开启事务
			session = dbUtil.getSessionByTransaction();

			// 订单和票据信息列表
			List<SlOrderDetail> slOrderDetailList = new ArrayList<SlOrderDetail>();
			List<SlOrderTickettypeDetail> slOrderTickettypeDetailList = new ArrayList<SlOrderTickettypeDetail>();
			List<SlOrderVenueDetail> slOrderVenueDetailList = new ArrayList<SlOrderVenueDetail>();

			SlOrder slOrder = new SlOrder();
			slOrder.setOrderId(orderId);
			slOrder.setOrderStat("0");
			slOrder.setOrderType("ZY");
			slOrder.setPayStat("2");
			slOrder.setSaleUserId(userId);
			slOrder.setTicketCount(sumNum);
			slOrder.setDueSum(sumAmt);
			slOrder.setRealSum(sumAmt);
			slOrder.setVersionNo(new Date());
			slOrder.setSaleTime(new Date());

			// 保存销售单-支付明细
			SlPayType slPayType = new SlPayType();
			String payTypeId = String.format("%dXSMX%s", auth.getClientId(), DateUtil.getNowDate("yyyyMMddHHmmssSSSS"));
			slPayType.setPayTypeId(payTypeId);
			slPayType.setOrderId(orderId);
			slPayType.setPayType(payType);
			slPayType.setAmt(slOrder.getRealSum());
			slPayType.setVersionNo(new Date());

			long detailSumAmt = 0;
			long detailSumNum = 0;
			Map<String, SlOrderTickettypeDetail> slOrderTickettypeDetailMap = new HashMap<String, SlOrderTickettypeDetail>();// 缓存每个票种的售票明细
			// 保存销售单
			for (SALE_DETAIL_ZY sale_detail_zy : saleDetailZYList) {
				SysTicketType sysTicketType = dbUtil.findById("查询票种", SysTicketType.class, sale_detail_zy.getTicketTypeId());

				int updateCount = dbUtil.executeSql("更新门票库存表状态", session, "UPDATE STR_TICKET_INFO T SET STAT=?,SALE_USER_ID=?,SALE_OPE_TIME=? WHERE SALE_USER_ID is null and T.TICKET_ID BETWEEN ? AND ? ", "003", userId, new Date(), sale_detail_zy.getBeginTicketId(), sale_detail_zy.getEndTicketId());
				if (updateCount != sale_detail_zy.getTicketNum()) {
					throw new RPCException(60002, MsgUtil.getMsg(60002));// 门票已销售,请勿重复提交
				}

				// 保存门票场馆明细表
				List<SysTicketTypeVenue> sysTicketTypeVenueList = dbUtil.queryListToBean("查询票种场馆表", "SELECT * FROM SYS_TICKET_TYPE_VENUE WHERE TICKET_TYPE_ID=?", SysTicketTypeVenue.class, sale_detail_zy.getTicketTypeId());

				int i = 0;
				for (long ticketId = sale_detail_zy.getBeginTicketId(); ticketId <= sale_detail_zy.getEndTicketId(); ticketId++) {
					SlOrderDetail slOrderDetail = new SlOrderDetail();
					String orderDetailId = String.format("%dXSMX%s%d", auth.getClientId(), DateUtil.getNowDate("yyyyMMddHHmmssSSSS"), i);
					slOrderDetail.setOrderDetailId(orderDetailId);
					slOrderDetail.setOrderId(orderId);
					slOrderDetail.setTicketClass("1");
					slOrderDetail.setTicketId(ticketId);
					slOrderDetail.setTicketTypeId(sale_detail_zy.getTicketTypeId());
					slOrderDetail.setValidateTimes(sysTicketType.getValidateTimes());
					slOrderDetail.setOriginalPrice(sale_detail_zy.getSalePrice());
					slOrderDetail.setSalePrice(sale_detail_zy.getSalePrice());
					slOrderDetail.setCheckFlag("N");
					slOrderDetail.setUselessFlag("N");
					slOrderDetail.setVersionNo(new Date());
					slOrderDetail.setOutletId(outletId);
					slOrderDetail.setClientId(auth.getClientId());
					slOrderDetail.setEjectTicketStat("2");
					slOrderDetail.setEjectTicketTime(new Date());
					slOrderDetail.setEjectUserId(userId);

					slOrderDetailList.add(slOrderDetail);

					for (SysTicketTypeVenue sysTicketTypeVenue : sysTicketTypeVenueList) {
						SlOrderVenueDetail slOrderVenueDetail = new SlOrderVenueDetail();
						slOrderVenueDetail.setId(new SlOrderVenueDetailId(slOrderDetail.getOrderDetailId(), sysTicketTypeVenue.getId().getVenueId()));
						slOrderVenueDetail.setRemainTimes(slOrderDetail.getValidateTimes());
						slOrderVenueDetailList.add(slOrderVenueDetail);
					}

					i++;
					detailSumAmt = detailSumAmt + sale_detail_zy.getSalePrice();
					detailSumNum++;
				}

				if (i != sale_detail_zy.getTicketNum()) {
					throw new RPCException(4, String.format("[%s]票种总数量与门票明细数量不符", sale_detail_zy.getTicketTypeId()));
				}

				// 缓存每个票种的售票汇总信息
				if (slOrderTickettypeDetailMap.containsKey(sale_detail_zy.getTicketTypeId())) {
					// 每个票种的售票明细
					SlOrderTickettypeDetail slOrderTickettypeDetail = slOrderTickettypeDetailMap.get(sale_detail_zy.getTicketTypeId());
					slOrderTickettypeDetail.setTicketCount(slOrderTickettypeDetail.getTicketCount() + sale_detail_zy.getTicketNum());
					slOrderTickettypeDetail.setEjectTicketCount(slOrderTickettypeDetail.getEjectTicketCount() + sale_detail_zy.getTicketNum());
					slOrderTickettypeDetailMap.put(sale_detail_zy.getTicketTypeId(), slOrderTickettypeDetail);
				} else {
					// 每个票种的售票明细
					SlOrderTickettypeDetail slOrderTickettypeDetail = new SlOrderTickettypeDetail();
					slOrderTickettypeDetail.setId(new SlOrderTickettypeDetailId(slOrder.getOrderId(), sale_detail_zy.getTicketTypeId()));
					slOrderTickettypeDetail.setTicketCount(sale_detail_zy.getTicketNum());
					slOrderTickettypeDetail.setEjectTicketCount(sale_detail_zy.getTicketNum());
					slOrderTickettypeDetailMap.put(sale_detail_zy.getTicketTypeId(), slOrderTickettypeDetail);
				}

			}

			slOrderTickettypeDetailList.addAll(slOrderTickettypeDetailMap.values());

			if (detailSumAmt != sumAmt) {
				throw new RPCException(4, "总金额与门票明细总金额不符");
			}

			if (detailSumNum != sumNum) {
				throw new RPCException(4, "总数量与门票明细总数量不符");
			}

			// 核减网点库存数量
			for (SlOrderTickettypeDetail slOrderTickettypeDetail : slOrderTickettypeDetailList) {
				String ticketTypeId = slOrderTickettypeDetail.getId().getTicketTypeId();
				Long ticketNum = CommonUtil.covertLong((slOrderTickettypeDetail.getEjectTicketCount()));
				reduceOutletInfos(session, outletId, ticketTypeId, ticketNum);
			}

			dbUtil.saveEntity("保存订单", session, slOrder);
			saveOrderDetailJdbcBatch(session, slOrderDetailList);
			dbUtil.saveEntity("保存支付明细", session, slPayType);
			dbUtil.saveEntityBatch("批量保存销售单票种明细表", session, slOrderTickettypeDetailList);
			saveOrderDetailVenueJdbcBatch(session, slOrderVenueDetailList);

			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}

		return true;
	}

	/**
	 * 批量保存订单明细表
	 * @param session
	 * @param slOrderDetailList
	 */
	private void saveOrderDetailJdbcBatch(Session session, final List<SlOrderDetail> slOrderDetailList) {
		session.doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				PreparedStatement ps = null;
				try {
					StringBuffer sb = new StringBuffer();
					sb.append(" INSERT INTO SL_ORDER_DETAIL ");
					sb.append(" (ORDER_DETAIL_ID, ORDER_ID, TICKET_CLASS, TICKET_ID, TICKET_UID, IDENTTY_ID, TICKET_TYPE_ID, VALIDATE_TIMES, ORIGINAL_PRICE, SALE_PRICE, CHECK_FLAG, USELESS_FLAG, VERSION_NO, OUTLET_ID, CLIENT_ID, EJECT_USER_ID, EJECT_TICKET_STAT, EJECT_TICKET_TIME, USELESS_TIME, VALID_START_DATE, VALID_END_DATE) ");
					sb.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

					conn.setAutoCommit(false);
					ps = conn.prepareStatement(sb.toString());
					for (int i = 0; i < slOrderDetailList.size(); i++) {
						SlOrderDetail SlOrderDetail = slOrderDetailList.get(i);
						ps.setString(1, SlOrderDetail.getOrderDetailId());
						ps.setString(2, SlOrderDetail.getOrderId());
						ps.setString(3, SlOrderDetail.getTicketClass());
						ps.setLong(4, SlOrderDetail.getTicketId());
						ps.setString(5, SlOrderDetail.getTicketUid());
						ps.setString(6, SlOrderDetail.getIdenttyId());
						ps.setString(7, SlOrderDetail.getTicketTypeId());
						ps.setLong(8, SlOrderDetail.getValidateTimes());
						ps.setLong(9, SlOrderDetail.getOriginalPrice());
						ps.setLong(10, SlOrderDetail.getSalePrice());
						ps.setString(11, SlOrderDetail.getCheckFlag());
						ps.setString(12, SlOrderDetail.getUselessFlag());
						ps.setTimestamp(13, DateUtil.covertSqlTimeStamp(SlOrderDetail.getVersionNo()));
						ps.setLong(14, SlOrderDetail.getOutletId());
						ps.setLong(15, SlOrderDetail.getClientId());
						ps.setString(16, SlOrderDetail.getEjectUserId());
						ps.setString(17, SlOrderDetail.getEjectTicketStat());
						ps.setTimestamp(18, DateUtil.covertSqlTimeStamp(SlOrderDetail.getEjectTicketTime()));
						ps.setTimestamp(19, DateUtil.covertSqlTimeStamp(SlOrderDetail.getUselessTime()));
						ps.setDate(20, DateUtil.covertSqlDate(SlOrderDetail.getValidStartDate()));
						ps.setDate(21, DateUtil.covertSqlDate(SlOrderDetail.getValidEndDate()));
						ps.addBatch();
						if ((i + 1) % 2000 == 0) {
							ps.executeBatch();
							ps.clearBatch();
						}
					}
					if (slOrderDetailList.size() % 2000 != 0) {
						ps.executeBatch();
						ps.clearBatch();
					}
				} finally {
					if(ps != null){
			            try {
			            	ps.close();
			            	ps=null;
			            } catch (Exception ex) {
			            	ps=null;
			            	logger.error("错误",ex);
			            }
			        }
				}

			}
		});
	}

	/**
	 * 批量保存订单明细场馆表
	 * @param session
	 * @param slOrderVenueDetailList
	 */
	private void saveOrderDetailVenueJdbcBatch(Session session, final List<SlOrderVenueDetail> slOrderVenueDetailList) {
		session.doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				PreparedStatement ps = null;
				try {
					StringBuffer sb = new StringBuffer();
					sb.append(" INSERT INTO SL_ORDER_VENUE_DETAIL ");
					sb.append(" (ORDER_DETAIL_ID, VENUE_ID, REMAIN_TIMES) ");
					sb.append(" VALUES(?,?,?) ");

					conn.setAutoCommit(false);
					ps = conn.prepareStatement(sb.toString());
					for (int i = 0; i < slOrderVenueDetailList.size(); i++) {
						SlOrderVenueDetail slOrderVenueDetail = slOrderVenueDetailList.get(i);
						ps.setString(1, slOrderVenueDetail.getId().getOrderDetailId());
						ps.setLong(2, slOrderVenueDetail.getId().getVenueId());
						ps.setLong(3, slOrderVenueDetail.getRemainTimes());

						ps.addBatch();
						if ((i + 1) % 2000 == 0) {
							ps.executeBatch();
							ps.clearBatch();
						}
					}
					if (slOrderVenueDetailList.size() % 2000 != 0) {
						ps.executeBatch();
						ps.clearBatch();
					}
				} finally {
					if(ps != null){
			            try {
			            	ps.close();
			            	ps=null;
			            } catch (Exception ex) {
			            	ps=null;
			            	logger.error("错误",ex);
			            }
			        }
				}
			}
		});
	}

	@Override
	@ControlAspect(funtionName = "同步售票信息", operType = OperType.ADD)
	public boolean saleTicket(AUTHORIZATION auth, List<SL_ORDER> sl_orders) throws RPCException, TException {
		Session session = null;
		// 售票信息上传 (XC-现场售票、ZY-自营售票、TD-团队换票)
		try {
			String userId = ClientAuthCache.getAuthBean(auth.getClientId()).getSysUser().getUserId();
			Long outletId = ClientAuthCache.getAuthBean(auth.getClientId()).getSysOutlet().getOutletId();

			checkOutletInfos(outletId);
			checkOrgSaleNum();

			// 开启事务
			session = dbUtil.getSessionByTransaction();

			// 订单和票据信息列表
			List<SlOrder> slOrderList = new ArrayList<SlOrder>();
			List<SlOrderDetail> slOrderDetailList = new ArrayList<SlOrderDetail>();
			List<SlPayType> slPayTypeList = new ArrayList<SlPayType>();
			List<StrTicketInfo> strTicketInfoList = new ArrayList<StrTicketInfo>();
			List<SlOrderTickettypeDetail> slOrderTickettypeDetailList = new ArrayList<SlOrderTickettypeDetail>();
			List<SlOrderVenueDetail> slOrderVenueDetailList = new ArrayList<SlOrderVenueDetail>();

			Set<String> redoOrderSet = new HashSet<String>();
			// 保存销售单
			for (SL_ORDER sl_order : sl_orders) {
				if ("ZY".equals(sl_order.getOrderType())) {
					throw new RPCException(4, "此接口不支持自营售票数量上传,请更新客户端程序");
				}

				SlOrder slOrderByCheck = dbUtil.findById("查询订单", SlOrder.class, sl_order.getOrderId());
				if (slOrderByCheck != null) {
					redoOrderSet.add(slOrderByCheck.getOrderId());
					continue;// 重复上传不再处理
				}

				Long minusAdvanceAmt = 0L; // 扣减预付款，团队换票用
				Map<String, TicketTypeSaleInfoBean> minusLimitMap = new HashMap<String, TicketTypeSaleInfoBean>();// 缓存每个票种的售票汇总信息
				Map<String, SlOrderTickettypeDetail> slOrderTickettypeDetailMap = new HashMap<String, SlOrderTickettypeDetail>();// 缓存每个票种的售票明细

				SlOrder slOrder = new SlOrder();
				BeanUtils.copyRPCProperties(sl_order, slOrder);

				// 保存销售单-票据明细
				if (sl_order.getSlOrderDetaillist() == null && sl_order.getSlOrderDetaillist().size() == 0) {
					throw new RPCException(4, "销售单-票据明细列表为空");
				}
				for (SL_ORDER_DETAIL sl_order_detail : sl_order.getSlOrderDetaillist()) {
					SlOrderDetail slOrderDetail = new SlOrderDetail();
					BeanUtils.copyRPCProperties(sl_order_detail, slOrderDetail);
					slOrderDetail.setCheckFlag("N"); // CHECK_FLAG 是否检票(Y是N否)
					slOrderDetail.setUselessFlag("N"); // USELESS_FLAG
														// 是否作废(Y是N否)
					StrTicketInfo ticketInfo = dbUtil.findById("查询门票库存", StrTicketInfo.class, slOrderDetail.getTicketId());
					if (ticketInfo != null) {
						ticketInfo.setStat("003");// 状态(000-未核实 001-已核实
													// 003-已销售,004-已作废)
						ticketInfo.setSaleUserId(userId);
						ticketInfo.setSaleOpeTime(new Date());
						strTicketInfoList.add(ticketInfo);

						slOrderDetail.setTicketUid(ticketInfo.getTicketUid());
					}

					slOrderDetailList.add(slOrderDetail);

					// 缓存每个票种的售票汇总信息
					if (minusLimitMap.containsKey(sl_order_detail.getTicketTypeId())) {
						// 缓存每个票种的售票汇总信息
						TicketTypeSaleInfoBean ticketTypeSaleInfoBean = minusLimitMap.get(sl_order_detail.getTicketTypeId());
						ticketTypeSaleInfoBean.setTicketCount(ticketTypeSaleInfoBean.getTicketCount() + 1);
						ticketTypeSaleInfoBean.setRealSumAmt(ticketTypeSaleInfoBean.getRealSumAmt() + sl_order_detail.getSalePrice());
						ticketTypeSaleInfoBean.setSalePrice(sl_order_detail.getSalePrice());
						minusLimitMap.put(sl_order_detail.getTicketTypeId(), ticketTypeSaleInfoBean);
						// 每个票种的售票明细
						SlOrderTickettypeDetail slOrderTickettypeDetail = slOrderTickettypeDetailMap.get(sl_order_detail.getTicketTypeId());
						slOrderTickettypeDetail.setTicketCount(slOrderTickettypeDetail.getTicketCount() + 1);
						slOrderTickettypeDetail.setEjectTicketCount(slOrderTickettypeDetail.getEjectTicketCount() + 1);
						slOrderTickettypeDetailMap.put(sl_order_detail.getTicketTypeId(), slOrderTickettypeDetail);
					} else {
						// 缓存每个票种的售票汇总信息
						TicketTypeSaleInfoBean ticketTypeSaleInfoBean = new TicketTypeSaleInfoBean();
						ticketTypeSaleInfoBean.setTicketTypeId(sl_order_detail.getTicketTypeId());
						ticketTypeSaleInfoBean.setTicketCount(1L);
						ticketTypeSaleInfoBean.setRealSumAmt(sl_order_detail.getSalePrice());
						ticketTypeSaleInfoBean.setSalePrice(sl_order_detail.getSalePrice());
						minusLimitMap.put(sl_order_detail.getTicketTypeId(), ticketTypeSaleInfoBean);
						// 每个票种的售票明细
						SlOrderTickettypeDetail slOrderTickettypeDetail = new SlOrderTickettypeDetail();
						slOrderTickettypeDetail.setId(new SlOrderTickettypeDetailId(sl_order.getOrderId(), sl_order_detail.getTicketTypeId()));
						slOrderTickettypeDetail.setTicketCount(1L);
						slOrderTickettypeDetail.setEjectTicketCount(1L);
						slOrderTickettypeDetailMap.put(sl_order_detail.getTicketTypeId(), slOrderTickettypeDetail);
					}

					// 保存门票场馆明细表
					List<SysTicketTypeVenue> sysTicketTypeVenueList = dbUtil.queryListToBean("查询票种场馆表", "SELECT * FROM SYS_TICKET_TYPE_VENUE WHERE TICKET_TYPE_ID=?", SysTicketTypeVenue.class, slOrderDetail.getTicketTypeId());
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

				// 团队换票特殊处理
				if ("TD".equals(sl_order.getOrderType())) {
					// 团队换票用
					List<SlLimitAmt> slLimitAmtList = new ArrayList<SlLimitAmt>();
					List<SlTeamOrderDetail> slTeamOrderDetailList = new ArrayList<SlTeamOrderDetail>();

					SlTeamOrder slTeamOrder = dbUtil.findById("查询团队预定信息", SlTeamOrder.class, sl_order.getOrderId());
					if (slTeamOrder == null) {
						throw new RPCException(4, "团队订单号不存在");
					}
					if ("04".equals(slTeamOrder.getStat())) {
						throw new RPCException(4, "此团队订单号已换票,不允许再换票");
					}
					if (slTeamOrder.getSlTeamOrderDetails() == null || slTeamOrder.getSlTeamOrderDetails().size() == 0) {
						throw new RPCException(4, "团队订单明细为空");
					}

					// SlOrg slOrg = dbUtil.findById("查询机构信息", SlOrg.class,
					// slTeamOrder.getOrgId());
					SlOrg slOrg = dbUtil.queryFirstListToBeanLock("查询机构信息", session, "FROM SlOrg U where orgId=?", "U", SlOrg.class, slTeamOrder.getOrgId());
					if (slOrg == null) {
						throw new RPCException(4, "机构信息不存在");
					}

					slTeamOrder.setStat("04"); // 04-已换票
					slTeamOrder.setChangeOpeUser(userId);
					slTeamOrder.setChangeTime(new Date());
					slTeamOrder.setChangeOutletId(outletId);

					// 解冻预付款
					// slOrg.setAdvanceAmt(CommonUtil.covertLong(slOrg.getAdvanceAmt())
					// +
					// CommonUtil.covertLong(slTeamOrder.getExamFrozenAdvanceAmt()));//
					// 解冻增加预付款余额
					// slOrg.setAdvanceFrozeAmt(CommonUtil.covertLong(slOrg.getAdvanceFrozeAmt())
					// -
					// CommonUtil.covertLong(slTeamOrder.getExamFrozenAdvanceAmt()));//
					// 解冻减少冻结预付款金额
					addOrgAdvanceAmt(session, slOrg.getOrgId(), CommonUtil.covertLong(slTeamOrder.getExamFrozenAdvanceAmt()));
					minusfrozeOrgAdvanceAmt(session, slOrg.getOrgId(), CommonUtil.covertLong(slTeamOrder.getExamFrozenAdvanceAmt()));

					Map<String, SlLimitAmt> slLimitAmtMap = dbUtil.queryMapForBean("查询机构余额信息", "SELECT * FROM SL_LIMIT_AMT WHERE ORG_ID=?", SlLimitAmt.class, "id.ticketTypeId", slTeamOrder.getOrgId());

					// 按票种缓存计算后队梯票价
					Map<String, TicketTypeBeforePriceBean> ticketTypeBeforePriceBeanMap = new HashMap<String, TicketTypeBeforePriceBean>();
					// 解冻每个票种的额度
					for (SlTeamOrderDetail slTeamOrderDetail : slTeamOrder.getSlTeamOrderDetails()) {
						TicketTypeSaleInfoBean ticketTypeSaleInfoBean = minusLimitMap.get(slTeamOrderDetail.getTicketTypeId());
						if (ticketTypeSaleInfoBean == null) {
							throw new RPCException(4, String.format("销售单-票据明细中票种在额度余额表中[%s]不存在", slTeamOrderDetail.getTicketTypeId()));
						}

						slTeamOrderDetailList.add(slTeamOrderDetail);
						slTeamOrderDetail.setChangeNum(ticketTypeSaleInfoBean.getTicketCount());

						// 增加销售张数
						SlOrgSaleinfo slOrgSaleinfo = dbUtil.findById("查询销售张数", SlOrgSaleinfo.class, new SlOrgSaleinfoId(slOrg.getOrgId(), slTeamOrderDetail.getTicketTypeId()));
						long saleTotalNumOld = slOrgSaleinfo.getSaleTotalNum();
						addOrgSaleNum(session, slOrg.getOrgId(), slTeamOrderDetail.getTicketTypeId(), ticketTypeSaleInfoBean.getTicketCount());

						slTeamOrderDetail.setRemark(String.format("原销售张数[%d]", saleTotalNumOld));

						SlLimitAmt slLimitAmt = slLimitAmtMap.get(slTeamOrderDetail.getTicketTypeId());
						if (slLimitAmt == null) {// 机构下无此票种的额度
							TicketTypeBeforePriceBean ticketTypeBeforePriceBean = this.calcTicketTypeByOrg(slTeamOrderDetail.getTicketTypeId(), saleTotalNumOld, ticketTypeSaleInfoBean.getSalePrice(), ticketTypeSaleInfoBean.getTicketCount());
							minusAdvanceAmt = minusAdvanceAmt + ticketTypeBeforePriceBean.getMinusAmt();// 累计扣减预付款
							ticketTypeBeforePriceBeanMap.put(ticketTypeBeforePriceBean.getTicketTypeId(), ticketTypeBeforePriceBean);
							continue;
						}

						// 解冻每个票种的额度
						slLimitAmt.setLimitAmt(CommonUtil.covertLong(slLimitAmt.getLimitAmt()) + CommonUtil.covertLong(slTeamOrderDetail.getExamFrozenLimit()));
						slLimitAmt.setFrozeLimit(CommonUtil.covertLong(slLimitAmt.getFrozeLimit()) - CommonUtil.covertLong(slTeamOrderDetail.getExamFrozenLimit()));

						// 扣减额度
						// --未超出额度
						if (CommonUtil.covertLong(ticketTypeSaleInfoBean.getTicketCount()) <= CommonUtil.covertLong(slLimitAmt.getLimitAmt())) {
							slLimitAmt.setLimitAmt(CommonUtil.covertLong(slLimitAmt.getLimitAmt()) - ticketTypeSaleInfoBean.getTicketCount());
							slTeamOrderDetail.setMinusLimit(ticketTypeSaleInfoBean.getTicketCount());
						} else {// --已超出额度
							Long i = ticketTypeSaleInfoBean.getTicketCount() - CommonUtil.covertLong(slLimitAmt.getLimitAmt());
							// minusAdvanceAmt = minusAdvanceAmt +
							// ticketTypeSaleInfoBean.getSalePrice() * i;//
							// 累计扣减预付款
							TicketTypeBeforePriceBean ticketTypeBeforePriceBean = this.calcTicketTypeByOrg(slTeamOrderDetail.getTicketTypeId(), saleTotalNumOld + slLimitAmt.getLimitAmt(), ticketTypeSaleInfoBean.getSalePrice(), i);
							minusAdvanceAmt = minusAdvanceAmt + ticketTypeBeforePriceBean.getMinusAmt(); // 累计扣减预付款

							slTeamOrderDetail.setMinusLimit(CommonUtil.covertLong(slLimitAmt.getLimitAmt()));
							slLimitAmt.setLimitAmt(0L);

							ticketTypeBeforePriceBeanMap.put(ticketTypeBeforePriceBean.getTicketTypeId(), ticketTypeBeforePriceBean);
						}

						slLimitAmtList.add(slLimitAmt);

					}

					// 计算出需要扣除的预付款

					// 检查预付款余额  // slOrg里的预付款还未更新，应该加上审核冻结的再与需要扣减的比对  zhuxy 20180415
					if (CommonUtil.covertLong(slOrg.getAdvanceAmt()) + slTeamOrder.getExamFrozenAdvanceAmt() < minusAdvanceAmt) {
						throw new RPCException(4, "额度数量或预付款余额不足,不允许换票");
					}

					// 扣减预付款
					// slOrg.setAdvanceAmt(CommonUtil.covertLong(slOrg.getAdvanceAmt())
					// - minusAdvanceAmt);
					minusOrgAdvanceAmt(session, slOrg.getOrgId(), minusAdvanceAmt);
					slTeamOrder.setMinusAdvanceAmt(minusAdvanceAmt);

					// 保存信息
					dbUtil.updateEntityBatch("批量更新机构额度余额表", session, slLimitAmtList);
					// dbUtil.updateEntity("更新机构表", session, slOrg);
					dbUtil.updateEntity("更新团队预定单表", session, slTeamOrder);
					dbUtil.updateEntityBatch("批量更新团队预定单明细表", session, slTeamOrderDetailList);

					// 修改订单明细中票价为阶梯票价
					slOrder.setRealSum(minusAdvanceAmt);
					slOrder.setDueSum(minusAdvanceAmt);

					Map<String, List<SlOrderDetail>> ticketTypeMap = new HashMap<String, List<SlOrderDetail>>();
					for (SlOrderDetail slOrderDetail : slOrderDetailList) {
						List<SlOrderDetail> ticketTypeList = ticketTypeMap.get(slOrderDetail.getTicketTypeId());
						if (ticketTypeList == null) {
							ticketTypeList = new ArrayList<SlOrderDetail>();
							ticketTypeMap.put(slOrderDetail.getTicketTypeId(), ticketTypeList);
						}
						ticketTypeList.add(slOrderDetail);
					}
					for (TicketTypeBeforePriceBean ticketTypeSaleInfoBean : ticketTypeBeforePriceBeanMap.values()) {
						List<SlOrderDetail> ticketTypeList = ticketTypeMap.get(ticketTypeSaleInfoBean.getTicketTypeId());
						if (ticketTypeSaleInfoBean.getLv1Num() != 0) {
							for (int i = 0; i < ticketTypeSaleInfoBean.getLv1Num(); i++) {
								ticketTypeList.get(i).setSalePrice(ticketTypeSaleInfoBean.getLv1Price());
							}
						}
						if (ticketTypeSaleInfoBean.getLv2Num() != 0) {
							for (int i = 0; i < ticketTypeSaleInfoBean.getLv2Num(); i++) {
								ticketTypeList.get(CommonUtil.covertInt(ticketTypeSaleInfoBean.getLv1Num()) + i).setSalePrice(ticketTypeSaleInfoBean.getLv2Price());
							}
						}
					}

					// 团队票增加默认的支付明细为05-代理；支付方式(01-现金,02-POS付款,03-微信，04-支付宝，05-代理)
					SlPayType slPayType = new SlPayType();
					slPayType.setPayTypeId(UUIDGenerator.getPK());
					slPayType.setOrderId(slOrder.getOrderId());
					slPayType.setPayType("05");
					slPayType.setAmt(slOrder.getRealSum());
					slPayType.setPayId("");
					slPayType.setVersionNo(new Date());
					slPayTypeList.add(slPayType);
				}

				slOrderList.add(slOrder);
				slOrderTickettypeDetailList.addAll(slOrderTickettypeDetailMap.values());
			}

			// 核减网点库存数量
			for (SlOrderTickettypeDetail slOrderTickettypeDetail : slOrderTickettypeDetailList) {
				String ticketTypeId = slOrderTickettypeDetail.getId().getTicketTypeId();
				if (redoOrderSet.contains(slOrderTickettypeDetail.getId().getOrderId())) {// 重复发送的订单，不再核减库存
					continue;
				}
				Long ticketNum = CommonUtil.covertLong((slOrderTickettypeDetail.getEjectTicketCount()));
				reduceOutletInfos(session, outletId, ticketTypeId, ticketNum);
			}

			dbUtil.saveOrUpdateEntityBatch("批量保存订单", session, slOrderList);
			dbUtil.saveOrUpdateEntityBatch("批量保存订单明细", session, slOrderDetailList);
			dbUtil.saveOrUpdateEntityBatch("批量保存支付明细", session, slPayTypeList);
			dbUtil.saveOrUpdateEntityBatch("批量保存销售单票种明细表", session, slOrderTickettypeDetailList);
			dbUtil.saveOrUpdateEntityBatch("批量保存销售单门票场馆明细表", session, slOrderVenueDetailList);
			dbUtil.updateEntityBatch("批量更新门票库存信息", session, strTicketInfoList);

			dbUtil.commit(session);

		} finally {
			dbUtil.close(session);
		}

		return true;
	}

	/**
	 * Title: 增加机构销售张数<br/>
	 * Description:
	 * 
	 */
	private void addOrgSaleNum(Session session, String orgId, String ticketTypeId, Long saleNum) {
		dbUtil.executeSql("更新机构销售数量", session, "UPDATE SL_ORG_SALEINFO SET SALE_TOTAL_NUM=SALE_TOTAL_NUM+?,OPE_TIME=sysdate WHERE ORG_ID=? AND TICKET_TYPE_ID=?", saleNum, orgId, ticketTypeId);
	}

	/**
	 * Title:扣减机构预付款 <br/>
	 * Description:
	 * 
	 */
	private void minusOrgAdvanceAmt(Session session, String orgId, Long saleAmt) {
		dbUtil.executeSql("扣减机构预付款", session, "UPDATE SL_ORG SET ADVANCE_AMT=ADVANCE_AMT-? WHERE ORG_ID=? ", saleAmt, orgId);
	}

	/**
	 * Title:解冻预付款-增加预付款<br/>
	 * Description:
	 * 
	 */
	private void addOrgAdvanceAmt(Session session, String orgId, Long forzeAmt) {
		dbUtil.executeSql("增加机构预付款", session, "UPDATE SL_ORG SET ADVANCE_AMT=ADVANCE_AMT+? WHERE ORG_ID=? ", forzeAmt, orgId);
	}

	/**
	 * Title:解冻预付款 -冻结预付款减少<br/>
	 * Description:
	 * 
	 */
	private void minusfrozeOrgAdvanceAmt(Session session, String orgId, Long forzeAmt) {
		dbUtil.executeSql("减少冻结预付款", session, "UPDATE SL_ORG SET ADVANCE_FROZE_AMT=ADVANCE_FROZE_AMT-? WHERE ORG_ID=? ", forzeAmt, orgId);
	}

	/**
	 * Title: 检查机构销售记录<br/>
	 * Description:
	 */
	private void checkOrgSaleNum() {
		List<Map<String, Object>> maps = dbUtil.queryListToMap("", "SELECT DISTINCT ORG_ID,TICKET_TYPE_ID FROM SL_ORG , SYS_TICKET_TYPE ");
		List<SlOrgSaleinfo> slOrgSaleinfoList = new ArrayList<SlOrgSaleinfo>();
		for (Map<String, Object> map : maps) {
			String orgId = StringUtil.convertString(map.get("orgId"));
			String ticketTypeId = StringUtil.convertString(map.get("ticketTypeId"));
			SlOrgSaleinfo slOrgSaleinfo = dbUtil.findById("", SlOrgSaleinfo.class, new SlOrgSaleinfoId(orgId, ticketTypeId));
			if (slOrgSaleinfo == null) {
				slOrgSaleinfo = new SlOrgSaleinfo();
				slOrgSaleinfo.setId(new SlOrgSaleinfoId(orgId, ticketTypeId));
				slOrgSaleinfo.setOpeTime(new Date());
				slOrgSaleinfo.setSaleTotalNum(0L);
				slOrgSaleinfoList.add(slOrgSaleinfo);
			}
		}
		if (slOrgSaleinfoList.size() > 0) {
			dbUtil.saveEntityBatch("初始化机构销售信息表", slOrgSaleinfoList);
		}
	}

	/**
	 * Title:计算签约社、代理商需要扣除的预付款 <br/>
	 * Description:
	 * 
	 * @param ticketTypeId
	 * @param saleTotalNumOld 原机构销售张数
	 * @param ticketTypePrice 基准票价
	 * @param minusNum 当前销售张数
	 * @return
	 */
	private TicketTypeBeforePriceBean calcTicketTypeByOrg(String ticketTypeId, long saleTotalNumOld, long ticketTypePrice, long minusNum) {
		TicketTypeBeforePriceBean ticketTypeBeforePriceBean = new TicketTypeBeforePriceBean();

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
			ticketTypeBeforePriceBean.setPrice(ticketTypePrice);
			ticketTypeBeforePriceBean.setTicketTypeId(ticketTypeId);
			ticketTypeBeforePriceBean.setMinusAmt(ticketTypePrice * minusNum);
			return ticketTypeBeforePriceBean;
		}

		// 原销售张数和新销售张数在同一阶梯票价中
		if (sysTicketTypePriceOld != null && sysTicketTypePriceNew != null) {
			if (sysTicketTypePriceOld.getPriceId().equals(sysTicketTypePriceNew.getPriceId())) {
				ticketTypeBeforePriceBean.setPrice(ticketTypePrice);
				ticketTypeBeforePriceBean.setTicketTypeId(ticketTypeId);
				ticketTypeBeforePriceBean.setMinusAmt(CommonUtil.covertLong(sysTicketTypePriceNew.getPrice()) * minusNum);
				ticketTypeBeforePriceBean.setLv1Price(sysTicketTypePriceNew.getPrice());
				ticketTypeBeforePriceBean.setLv1Num(minusNum);
				return ticketTypeBeforePriceBean;
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

		ticketTypeBeforePriceBean.setPrice(ticketTypePrice);
		ticketTypeBeforePriceBean.setTicketTypeId(ticketTypeId);
		ticketTypeBeforePriceBean.setMinusAmt(minusDllOld * priceOld + minusDllNew * priceNew);
		ticketTypeBeforePriceBean.setLv1Price(priceOld);
		ticketTypeBeforePriceBean.setLv1Num(minusDllOld);
		ticketTypeBeforePriceBean.setLv2Price(priceNew);
		ticketTypeBeforePriceBean.setLv2Num(minusDllNew);
		return ticketTypeBeforePriceBean;
	}

	@Override
	@ControlAspect(funtionName = "售票信息上传 (WL-网络代理换票)", operType = OperType.ADD)
	public boolean changeTicket(AUTHORIZATION auth, SL_NETAGENT_ORDER sl_netagent_order) throws RPCException, TException {
		Session session = null;

		try {
			String userId = ClientAuthCache.getAuthBean(auth.getClientId()).getSysUser().getUserId();
			Long outletId = ClientAuthCache.getAuthBean(auth.getClientId()).getSysOutlet().getOutletId();

			checkOutletInfos(outletId);
			checkOrgSaleNum();

			// 开启事务
			session = dbUtil.getSessionByTransaction();

			// 转换rpc entity为db entity
			// 保存网络代理换票表
			SlNetagentOrder slNetagentOrder = new SlNetagentOrder();
			BeanUtils.copyRPCProperties(sl_netagent_order, slNetagentOrder);
			slNetagentOrder.setOpeTime(new Date());

			// 保存售票单表
			SL_ORDER sl_order = sl_netagent_order.getSlOrder();
			SlOrder slOrder = new SlOrder();

			BeanUtils.copyRPCProperties(sl_order, slOrder);

			SlOrder slOrderByCheck = dbUtil.findById("查询订单", SlOrder.class, sl_order.getOrderId());
			if (slOrderByCheck != null) {
				throw new RPCException(4, "已换票,不允许重复上传");
			}

			// 保存售票单明细表
			// 保存票据明细
			List<SlOrderDetail> slOrderDetailList = new ArrayList<SlOrderDetail>();
			List<SlOrderTickettypeDetail> slOrderTickettypeDetailList = new ArrayList<SlOrderTickettypeDetail>();
			List<StrTicketInfo> strTicketInfoList = new ArrayList<StrTicketInfo>();
			List<SlOrderVenueDetail> slOrderVenueDetailList = new ArrayList<SlOrderVenueDetail>();

			if (sl_order.getSlOrderDetaillist() == null && sl_order.getSlOrderDetaillist().size() == 0) {
				throw new RPCException(4, "销售单-票据明细列表为空");
			}

			// 缓存每个票种的售票汇总信息
			Map<String, TicketTypeSaleInfoBean> minusLimitMap = new HashMap<String, TicketTypeSaleInfoBean>();

			Map<String, SlOrderTickettypeDetail> slOrderTickettypeDetailMap = new HashMap<String, SlOrderTickettypeDetail>();// 缓存每个票种的售票明细
			for (SL_ORDER_DETAIL sl_order_detail : sl_order.getSlOrderDetaillist()) {
				SlOrderDetail slOrderDetail = new SlOrderDetail();
				BeanUtils.copyRPCProperties(sl_order_detail, slOrderDetail);
				slOrderDetail.setCheckFlag("N"); // CHECK_FLAG 是否检票(Y是N否)
				slOrderDetail.setUselessFlag("N"); // USELESS_FLAG 是否作废(Y是N否)

				StrTicketInfo ticketInfo = dbUtil.findById("查询门票库存", StrTicketInfo.class, slOrderDetail.getTicketId());
				if (ticketInfo != null) {
					ticketInfo.setStat("003");// 状态(000-未核实 001-已核实
												// 003-已销售,004-已作废)
					ticketInfo.setSaleUserId(userId);
					ticketInfo.setSaleOpeTime(new Date());
					strTicketInfoList.add(ticketInfo);

					slOrderDetail.setTicketUid(ticketInfo.getTicketUid());
				}

				slOrderDetailList.add(slOrderDetail);

				// 缓存每个票种的售票汇总信息
				if (slOrderTickettypeDetailMap.containsKey(sl_order_detail.getTicketTypeId())) {
					// 缓存每个票种的售票汇总信息
					TicketTypeSaleInfoBean ticketTypeSaleInfoBean = minusLimitMap.get(sl_order_detail.getTicketTypeId());
					ticketTypeSaleInfoBean.setTicketCount(ticketTypeSaleInfoBean.getTicketCount() + 1);
					ticketTypeSaleInfoBean.setRealSumAmt(ticketTypeSaleInfoBean.getRealSumAmt() + sl_order_detail.getSalePrice());
					ticketTypeSaleInfoBean.setSalePrice(sl_order_detail.getSalePrice());
					minusLimitMap.put(sl_order_detail.getTicketTypeId(), ticketTypeSaleInfoBean);
					// 每个票种的售票明细
					SlOrderTickettypeDetail slOrderTickettypeDetail = slOrderTickettypeDetailMap.get(sl_order_detail.getTicketTypeId());
					slOrderTickettypeDetail.setTicketCount(slOrderTickettypeDetail.getTicketCount() + 1);
					slOrderTickettypeDetail.setEjectTicketCount(slOrderTickettypeDetail.getEjectTicketCount() + 1);
					slOrderTickettypeDetailMap.put(sl_order_detail.getTicketTypeId(), slOrderTickettypeDetail);
				} else {
					// 缓存每个票种的售票汇总信息
					TicketTypeSaleInfoBean ticketTypeSaleInfoBean = new TicketTypeSaleInfoBean();
					ticketTypeSaleInfoBean.setTicketTypeId(sl_order_detail.getTicketTypeId());
					ticketTypeSaleInfoBean.setTicketCount(1L);
					ticketTypeSaleInfoBean.setRealSumAmt(sl_order_detail.getSalePrice());
					ticketTypeSaleInfoBean.setSalePrice(sl_order_detail.getSalePrice());
					minusLimitMap.put(sl_order_detail.getTicketTypeId(), ticketTypeSaleInfoBean);
					// 每个票种的售票明细
					SlOrderTickettypeDetail slOrderTickettypeDetail = new SlOrderTickettypeDetail();
					slOrderTickettypeDetail.setId(new SlOrderTickettypeDetailId(sl_order.getOrderId(), sl_order_detail.getTicketTypeId()));
					slOrderTickettypeDetail.setTicketCount(1L);
					slOrderTickettypeDetail.setEjectTicketCount(1L);
					slOrderTickettypeDetailMap.put(sl_order_detail.getTicketTypeId(), slOrderTickettypeDetail);
				}

				// 保存门票场馆明细表
				List<SysTicketTypeVenue> sysTicketTypeVenueList = dbUtil.queryListToBean("查询票种场馆表", "SELECT * FROM SYS_TICKET_TYPE_VENUE WHERE TICKET_TYPE_ID=?", SysTicketTypeVenue.class, slOrderDetail.getTicketTypeId());
				for (SysTicketTypeVenue sysTicketTypeVenue : sysTicketTypeVenueList) {
					SlOrderVenueDetail slOrderVenueDetail = new SlOrderVenueDetail();
					slOrderVenueDetail.setId(new SlOrderVenueDetailId(slOrderDetail.getOrderDetailId(), sysTicketTypeVenue.getId().getVenueId()));
					slOrderVenueDetail.setRemainTimes(slOrderDetail.getValidateTimes());
					slOrderVenueDetailList.add(slOrderVenueDetail);
				}
			}

			// SlOrg slOrg = dbUtil.findById("查询机构信息", SlOrg.class,
			// sl_netagent_order.getOrgId());
			SlOrg slOrg = dbUtil.queryFirstListToBeanLock("查询机构信息", session, "FROM SlOrg U where orgId=?", "U", SlOrg.class, sl_netagent_order.getOrgId());
			if (slOrg == null) {
				throw new RPCException(4, "机构信息不存在");
			}

			Long minusAdvanceAmt = 0L; // 扣减预付款，
			// 按票种缓存计算后队梯票价
			Map<String, TicketTypeBeforePriceBean> ticketTypeBeforePriceBeanMap = new HashMap<String, TicketTypeBeforePriceBean>();

			for (TicketTypeSaleInfoBean ticketTypeSaleInfoBean : minusLimitMap.values()) {
				// 增加销售张数
				SlOrgSaleinfo slOrgSaleinfo = dbUtil.findById("查询销售张数", SlOrgSaleinfo.class, new SlOrgSaleinfoId(slOrg.getOrgId(), ticketTypeSaleInfoBean.getTicketTypeId()));
				long saleTotalNumOld = slOrgSaleinfo.getSaleTotalNum();
				addOrgSaleNum(session, slOrg.getOrgId(), ticketTypeSaleInfoBean.getTicketTypeId(), ticketTypeSaleInfoBean.getTicketCount());

				TicketTypeBeforePriceBean ticketTypeBeforePriceBean = this.calcTicketTypeByOrg(ticketTypeSaleInfoBean.getTicketTypeId(), saleTotalNumOld, ticketTypeSaleInfoBean.getSalePrice(), ticketTypeSaleInfoBean.getTicketCount());
				minusAdvanceAmt = minusAdvanceAmt + ticketTypeBeforePriceBean.getMinusAmt();// 累计扣减预付款

				ticketTypeBeforePriceBeanMap.put(ticketTypeBeforePriceBean.getTicketTypeId(), ticketTypeBeforePriceBean);

			}

			// 扣减预付款
			// slOrg.setAdvanceAmt(CommonUtil.covertLong(slOrg.getAdvanceAmt())
			// - minusAdvanceAmt);
			minusOrgAdvanceAmt(session, slOrg.getOrgId(), minusAdvanceAmt);

			// 修改订单明细中票价为阶梯票价
			slOrder.setDueSum(minusAdvanceAmt);
			slOrder.setRealSum(minusAdvanceAmt);

			Map<String, List<SlOrderDetail>> ticketTypeMap = new HashMap<String, List<SlOrderDetail>>();// 缓存每个票种售票明细
			for (SlOrderDetail slOrderDetail : slOrderDetailList) {
				List<SlOrderDetail> ticketTypeList = ticketTypeMap.get(slOrderDetail.getTicketTypeId());
				if (ticketTypeList == null) {
					ticketTypeList = new ArrayList<SlOrderDetail>();
					ticketTypeMap.put(slOrderDetail.getTicketTypeId(), ticketTypeList);
				}
				ticketTypeList.add(slOrderDetail);
			}
			for (TicketTypeBeforePriceBean ticketTypeSaleInfoBean : ticketTypeBeforePriceBeanMap.values()) {
				List<SlOrderDetail> ticketTypeList = ticketTypeMap.get(ticketTypeSaleInfoBean.getTicketTypeId());
				if (ticketTypeSaleInfoBean.getLv1Num() != 0) {
					for (int i = 0; i < ticketTypeSaleInfoBean.getLv1Num(); i++) {
						ticketTypeList.get(i).setSalePrice(ticketTypeSaleInfoBean.getLv1Price());
						// 实体票的阶梯票价的原价设置成与销售价一致，否则在网络代理售票统计中统计的销售金额错误
						// （网络代理售票统计中销售金额、退票金额是用原价统计，检票金额是用销售价统计，已解决系统直连跨阶梯后退票问题，并记录原始冻结金额）
						ticketTypeList.get(i).setOriginalPrice(ticketTypeSaleInfoBean.getLv1Price());
					}
				}
				if (ticketTypeSaleInfoBean.getLv2Num() != 0) {
					for (int i = 0; i < ticketTypeSaleInfoBean.getLv2Num(); i++) {
						ticketTypeList.get(CommonUtil.covertInt(ticketTypeSaleInfoBean.getLv1Num()) + i).setSalePrice(ticketTypeSaleInfoBean.getLv2Price());
						// 实体票的阶梯票价的原价设置成与销售价一致，否则在网络代理售票统计中统计的销售金额错误
						// （网络代理售票统计中销售金额、退票金额是用原价统计，检票金额是用销售价统计，已解决系统直连跨阶梯后退票问题，并记录原始冻结金额）
						ticketTypeList.get(CommonUtil.covertInt(ticketTypeSaleInfoBean.getLv1Num()) + i).setOriginalPrice(ticketTypeSaleInfoBean.getLv2Price());
					}
				}
			}

			slOrderTickettypeDetailList.addAll(slOrderTickettypeDetailMap.values());

			// 核减网点库存数量
			for (SlOrderTickettypeDetail slOrderTickettypeDetail : slOrderTickettypeDetailList) {
				String ticketTypeId = slOrderTickettypeDetail.getId().getTicketTypeId();
				Long ticketNum = CommonUtil.covertLong((slOrderTickettypeDetail.getEjectTicketCount()));
				reduceOutletInfos(session, outletId, ticketTypeId, ticketNum);
			}

			// 增加默认的支付明细为05-代理；支付方式(01-现金,02-POS付款,03-微信，04-支付宝，05-代理)
			SlPayType slPayType = new SlPayType();
			slPayType.setPayTypeId(UUIDGenerator.getPK());
			slPayType.setOrderId(slOrder.getOrderId());
			slPayType.setPayType("05");
			slPayType.setAmt(slOrder.getRealSum());
			slPayType.setPayId("");
			slPayType.setVersionNo(new Date());

			dbUtil.saveOrUpdateEntity("保存网络代理换票表", session, slNetagentOrder);
			dbUtil.saveOrUpdateEntity("保存销售单信息", session, slOrder);
			dbUtil.saveOrUpdateEntityBatch("批量保存订单明细", session, slOrderDetailList);
			dbUtil.saveOrUpdateEntity("保存支付明细", session, slPayType);
			dbUtil.saveOrUpdateEntityBatch("批量保存销售单票种明细表", session, slOrderTickettypeDetailList);
			dbUtil.saveOrUpdateEntityBatch("批量保存销售单门票场馆明细表", session, slOrderVenueDetailList);
			dbUtil.updateEntityBatch("批量更新门票库存信息", session, strTicketInfoList);
			// dbUtil.updateEntity("扣减预付款", session, slOrg);

			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}

		return true;
	}

	@Override
	@ControlAspect(funtionName = "补票信息上传 (PB-补票)", operType = OperType.ADD)
	public boolean supplyTicket(AUTHORIZATION auth, SL_SUPPLY sl_supply) throws RPCException, TException {
		Session session = null;
		try {
			String userId = ClientAuthCache.getAuthBean(auth.getClientId()).getSysUser().getUserId();
			SysOutlet sysOutlet = ClientAuthCache.getAuthBean(auth.getClientId()).getSysOutlet();

			checkOutletInfos(sysOutlet.getOutletId());

			// 开启事务
			session = dbUtil.getSessionByTransaction();

			// 保存补票信息
			SlSupply slSupply = new SlSupply();
			BeanUtils.copyRPCProperties(sl_supply, slSupply);
			dbUtil.saveOrUpdateEntity("保存补票信息", session, slSupply);

			sl_supply.getSlUselessTicketInfo();
			// 保存废票信息
			SlUselessTicketInfo slUselessTicketInfo = new SlUselessTicketInfo();
			BeanUtils.copyRPCProperties(sl_supply.getSlUselessTicketInfo(), slUselessTicketInfo);
			slUselessTicketInfo.setOutletId(sysOutlet.getOutletId());

			dbUtil.saveOrUpdateEntity("保存废票信息", session, slUselessTicketInfo);

			// 更新门票库存 新票为已销售 旧票为已作废
			StrTicketInfo ticketInfoOld = dbUtil.findById("", StrTicketInfo.class, slSupply.getOldTicketId());
			if (ticketInfoOld == null) {
				throw new RPCException(4, "旧票号不存在");
			}
			if ("004".equals(ticketInfoOld.getStat())) {
				throw new RPCException(4, "旧票号已作废");
			}
			ticketInfoOld.setStat("004");// 状态(000-未核实 001-已核实 003-已销售,004-已作废)

			StrTicketInfo ticketInfoNew = dbUtil.findById("", StrTicketInfo.class, slSupply.getNewTicketId());
			if (ticketInfoNew == null) {
				throw new RPCException(4, "新票号不存在");
			}
			if ("003".equals(ticketInfoNew.getStat())) {
				throw new RPCException(4, "新票号已销售");
			}
			ticketInfoNew.setStat("003");// 状态(000-未核实 001-已核实 003-已销售,004-已作废)
			ticketInfoNew.setSaleUserId(userId);
			ticketInfoNew.setSaleOpeTime(new Date());

			// 更新旧票的售票信息为已作废 如果没有售票信息则不更新
			SlOrderDetail orderDetailOld = dbUtil.queryFirstForBean("", "SELECT * FROM SL_ORDER_DETAIL WHERE TICKET_ID=?", SlOrderDetail.class, ticketInfoOld.getTicketId());
			// 如果没有售票信息则不更新
			if (orderDetailOld != null) {
				orderDetailOld.setUselessFlag("Y");// 是否作废(Y是N否)
				orderDetailOld.setUselessTime(new Date());
			}

			// 保存销售单
			SlOrder slOrder = new SlOrder();
			SL_ORDER sl_order = sl_supply.getSlOrder();
			if (sl_order == null) {
				throw new RPCException(4, "销售单为空");
			}
			BeanUtils.copyRPCProperties(sl_order, slOrder);

			// 保存销售单-明细子表
			List<SlOrderDetail> slOrderDetailList = new ArrayList<SlOrderDetail>();
			List<SlOrderTickettypeDetail> slOrderTickettypeDetailList = new ArrayList<SlOrderTickettypeDetail>();
			List<SlPayType> slPayTypeList = new ArrayList<SlPayType>();
			List<SlOrderVenueDetail> slOrderVenueDetailList = new ArrayList<SlOrderVenueDetail>();

			// 保存销售单-票据明细
			if (sl_order.getSlOrderDetaillist() == null && sl_order.getSlOrderDetaillist().size() == 0) {
				throw new RPCException(4, "销售单-票据明细列表为空");
			}

			Map<String, SlOrderTickettypeDetail> slOrderTickettypeDetailMap = new HashMap<String, SlOrderTickettypeDetail>();// 缓存每个票种的售票明细
			for (SL_ORDER_DETAIL sl_order_detail : sl_order.getSlOrderDetaillist()) {
				SlOrderDetail slOrderDetail = new SlOrderDetail();
				BeanUtils.copyRPCProperties(sl_order_detail, slOrderDetail);
				slOrderDetail.setCheckFlag("N"); // CHECK_FLAG 是否检票(Y是N否)
				slOrderDetail.setUselessFlag("N"); // USELESS_FLAG 是否作废(Y是N否)

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
				List<SysTicketTypeVenue> sysTicketTypeVenueList = dbUtil.queryListToBean("查询票种场馆表", "SELECT * FROM SYS_TICKET_TYPE_VENUE WHERE TICKET_TYPE_ID=?", SysTicketTypeVenue.class, slOrderDetail.getTicketTypeId());
				for (SysTicketTypeVenue sysTicketTypeVenue : sysTicketTypeVenueList) {
					SlOrderVenueDetail slOrderVenueDetail = new SlOrderVenueDetail();
					slOrderVenueDetail.setId(new SlOrderVenueDetailId(slOrderDetail.getOrderDetailId(), sysTicketTypeVenue.getId().getVenueId()));
					slOrderVenueDetail.setRemainTimes(slOrderDetail.getValidateTimes());
					slOrderVenueDetailList.add(slOrderVenueDetail);
				}
			}

			slOrderTickettypeDetailList.addAll(slOrderTickettypeDetailMap.values());

			// 保存销售单-支付明细
			if (sl_order.getSlPayTypelist() != null) {
				for (SL_PAY_TYPE sl_pay_type : sl_order.getSlPayTypelist()) {
					SlPayType slPayType = new SlPayType();
					BeanUtils.copyRPCProperties(sl_pay_type, slPayType);

					slPayTypeList.add(slPayType);
				}
			}

			int payStat = 1;// 默认待支付

			// 支付方式(01-现金,02-POS付款,03-微信，04-支付宝，05-代理)
			String payType = slPayTypeList.get(0).getPayType();

			dbUtil.saveOrUpdateEntity("保存销售单", session, slOrder);
			dbUtil.saveOrUpdateEntityBatch("保存销售单-销售明细表", session, slOrderDetailList);
			dbUtil.saveOrUpdateEntityBatch("批量保存销售单票种明细表", session, slOrderTickettypeDetailList);
			dbUtil.saveOrUpdateEntityBatch("保存销售单-支付明细表", session, slPayTypeList);
			dbUtil.saveOrUpdateEntityBatch("批量保存销售单门票场馆明细表", session, slOrderVenueDetailList);

			if (payType.equals("03") || payType.equals("04")) {
				// 保存成功，保存销售信息再支付
				dbUtil.commit(session);

				// 开启事务
				session = dbUtil.getSessionByTransaction();

				dbUtil.queryFirstListToBeanLock("查询订单", session, "FROM SlOrder U where orderId=?", "U", SlOrder.class, slOrder.getOrderId());

				// 调用第三支付接口
				OrderPayRequest orderPayRequest = new OrderPayRequest();
				orderPayRequest.setMch_id(SwiftpassConfig.mch_id);
				orderPayRequest.setDevice_info(StringUtil.convertString(auth.getClientId()));
				orderPayRequest.setMch_create_ip(CommonUtil.getHostIP());
				orderPayRequest.setOut_trade_no(slOrder.getOrderId());
				orderPayRequest.setBody("园博园门票");
				orderPayRequest.setAuth_code(slPayTypeList.get(0).getPaymentCode());
				orderPayRequest.setTotal_fee(slPayTypeList.get(0).getAmt().intValue());
				payStat = payUtil.orderPay(session, orderPayRequest);

			} else {
				payStat = 2;// 现金默认支付成功
			}

			slOrder.setPayStat(StringUtil.convertString(payStat));

			if (payStat == 2) {
				// 核减网点库存数量
				for (SlOrderTickettypeDetail slOrderTickettypeDetail : slOrderTickettypeDetailList) {
					String ticketTypeId = slOrderTickettypeDetail.getId().getTicketTypeId();
					Long ticketNum = CommonUtil.covertLong((slOrderTickettypeDetail.getEjectTicketCount()));
					reduceOutletInfos(session, sysOutlet.getOutletId(), ticketTypeId, ticketNum);
				}

				dbUtil.updateEntity("更新旧门票库存", session, ticketInfoOld);
				dbUtil.updateEntity("更新新门票库存", session, ticketInfoNew);
				// 如果没有售票信息则不更新
				if (orderDetailOld != null) {
					dbUtil.updateEntity("更新旧售票信息", session, orderDetailOld);
				}
			}

			dbUtil.saveOrUpdateEntity("保存销售单", session, slOrder);
			dbUtil.commit(session);

			if (payStat != 2) {// 支付失败需要调用查询接口
				throw new RPCException(3, "支付失败");
			}

		} finally {
			dbUtil.close(session);
		}

		return true;
	}

	@Override
	@ControlAspect(funtionName = "坏票信息上传", operType = OperType.ADD)
	public boolean uselessTicket(AUTHORIZATION auth, List<SL_USELESS_TICKET_INFO> useless_ticket_infos) throws RPCException, TException {
		Session session = null;
		List<SlUselessTicketInfo> slUselessTicketInfoList = new ArrayList<SlUselessTicketInfo>();
		List<SlOrderDetail> slOrderDetailList = new ArrayList<SlOrderDetail>();
		List<StrTicketInfo> strTicketInfoList = new ArrayList<StrTicketInfo>();

		try {
			// 开启事务
			session = dbUtil.getSessionByTransaction();
			SysOutlet sysOutlet = ClientAuthCache.getAuthBean(auth.getClientId()).getSysOutlet();

			checkOutletInfos(sysOutlet.getOutletId());

			Map<String, Long> slUselessCount = new HashMap<String, Long>();

			for (SL_USELESS_TICKET_INFO useless_ticket_info : useless_ticket_infos) {
				// 保存废票信息
				SlUselessTicketInfo slUselessTicketInfo = new SlUselessTicketInfo();
				BeanUtils.copyRPCProperties(useless_ticket_info, slUselessTicketInfo);
				slUselessTicketInfo.setOutletId(ClientAuthCache.getAuthBean(auth.getClientId()).getSysOutlet().getOutletId());

				slUselessTicketInfoList.add(slUselessTicketInfo);

				// 更新门票库存 为已作废
				StrTicketInfo ticketInfo = dbUtil.findById("", StrTicketInfo.class, slUselessTicketInfo.getTicketId());
				if (ticketInfo == null) {
					throw new RPCException(4, "票号不存在");
				}
				// 确定该票号属于本网点未销售的票，再进行作废
				if (!sysOutlet.getOutletId().toString().equals(ticketInfo.getStoreId()) || "003".equals(ticketInfo.getStat())) {
					throw new RPCException(4, "只能作废本网点未销售的票");
				}
				if ("004".equals(ticketInfo.getStat())) {
					throw new RPCException(4, "【" + slUselessTicketInfo.getTicketId() + "】已经作废，请勿重复提交");
				}
				ticketInfo.setStat("004");// 状态(000-未核实 001-已核实 003-已销售,004-已作废)

				strTicketInfoList.add(ticketInfo);

				// 更新票的售票信息为已作废 如果没有售票信息则不更新
				List<SlOrderDetail> orderDetails = dbUtil.queryListToBean("", "SELECT * FROM SL_ORDER_DETAIL WHERE TICKET_ID=?", SlOrderDetail.class, slUselessTicketInfo.getTicketId());
				if (orderDetails != null && orderDetails.size() != 0) {
					SlOrderDetail orderDetail = orderDetails.get(0);
					orderDetail.setUselessFlag("Y");// 是否作废(Y是N否)
					orderDetail.setUselessTime(new Date());
					slOrderDetailList.add(orderDetail);
				}

				// 缓存每个票种的废票张数
				if (slUselessCount.containsKey(useless_ticket_info.getTicketTypeId())) {
					slUselessCount.put(useless_ticket_info.getTicketTypeId(), slUselessCount.get(useless_ticket_info.getTicketTypeId() + 1));
				} else {
					slUselessCount.put(useless_ticket_info.getTicketTypeId(), 1L);
				}
			}

			// 核减网点库存数量
			for (String ticketTypeId : slUselessCount.keySet()) {
				long ticketNum = slUselessCount.get(ticketTypeId);
				reduceOutletInfos(session, sysOutlet.getOutletId(), ticketTypeId, ticketNum);
			}

			dbUtil.saveOrUpdateEntityBatch("批量保存废票信息", session, slUselessTicketInfoList);
			dbUtil.updateEntityBatch("批量更新门票库存", session, strTicketInfoList);
			dbUtil.updateEntityBatch("批量更新售票明细信息", session, slOrderDetailList);

			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}

		return true;
	}

	@Override
	@ControlAspect(funtionName = "取消废票", operType = OperType.UPDATE)
	public boolean cancelUselessTicket(AUTHORIZATION auth, List<USELESS_TICKET_DETAIL> useless_ticket_infos) throws RPCException, TException {
		Session session = null;
		List<SlOrderDetail> slOrderDetailList = new ArrayList<SlOrderDetail>();
		List<StrTicketInfo> strTicketInfoList = new ArrayList<StrTicketInfo>();
		try {
			session = dbUtil.getSessionByTransaction();
			SysOutlet sysOutlet = ClientAuthCache.getAuthBean(auth.getClientId()).getSysOutlet();

			Map<String, Long> slUselessCount = new HashMap<String, Long>();

			for (USELESS_TICKET_DETAIL useless_ticket_info : useless_ticket_infos) {
				StrTicketInfo ticketInfo = dbUtil.findById("查询票表信息", StrTicketInfo.class, useless_ticket_info.getTicketId());
				if (ticketInfo == null) {
					throw new RPCException(4, useless_ticket_info.getTicketId() + "废票信息不存在");
				}
				if (!"004".equals(ticketInfo.getStat())) {
					throw new RPCException(4, useless_ticket_info.getTicketId() + "票号非作废状态");
				}
				// 更新门票库存状态
				ticketInfo.setStat("006");// 状态(000-未核实 001-已核实
											// 003-已销售,004-已作废,005-配送中,006-已接收)
				strTicketInfoList.add(ticketInfo);
				// 删除废票信息
				SlUselessTicketInfo uselessInfo = dbUtil.findById("查询该废票信息是否存在", SlUselessTicketInfo.class, useless_ticket_info.getTicketId());
				if (uselessInfo == null) {
					throw new RPCException(4, useless_ticket_info.getTicketId() + "废票信息不存在");
				}
				dbUtil.deleteEntity("删除废票信息", session, SlUselessTicketInfo.class, useless_ticket_info.getTicketId());
				// 取消票的作废状态， 如果没有售票信息则不更新
				List<SlOrderDetail> orderDetails = dbUtil.queryListToBean("", "SELECT * FROM SL_ORDER_DETAIL WHERE TICKET_ID=?", SlOrderDetail.class, useless_ticket_info.getTicketId());
				if (orderDetails != null && orderDetails.size() != 0) {
					SlOrderDetail orderDetail = orderDetails.get(0);
					orderDetail.setUselessFlag("N");// 是否作废(Y是N否)
					orderDetail.setUselessTime(null);
					slOrderDetailList.add(orderDetail);
				}
				// 缓存每个票种的废票张数
				if (slUselessCount.containsKey(useless_ticket_info.getTicketTypeId())) {
					slUselessCount.put(useless_ticket_info.getTicketTypeId(), slUselessCount.get(useless_ticket_info.getTicketTypeId() + 1));
				} else {
					slUselessCount.put(useless_ticket_info.getTicketTypeId(), 1L);
				}
			}
			// 取消芯片作废数量，增加网点库存数量
			List<StrOutletInfo> strOutletInfoList = new ArrayList<StrOutletInfo>();
			Map<String, StrOutletInfo> strOutletInfos = dbUtil.queryMapForBean("查询网点库存", "SELECT * FROM STR_OUTLET_INFO WHERE OUTLET_ID=? ", StrOutletInfo.class, "id.ticketTypeId", sysOutlet.getOutletId());
			for (String ticketTypeId : slUselessCount.keySet()) {
				if (strOutletInfos.containsKey(ticketTypeId)) {
					StrOutletInfo strOutletInfo = strOutletInfos.get(ticketTypeId);
					strOutletInfo.setStrTicketNum(CommonUtil.covertLong(strOutletInfo.getStrTicketNum()) + slUselessCount.get(ticketTypeId)); // 网点库存数量增加
					strOutletInfo.setLastUpdTime(new Date());
					strOutletInfos.put(ticketTypeId, strOutletInfo);
				} else {
					StrOutletInfo strOutletInfo = new StrOutletInfo();
					strOutletInfo.setId(new StrOutletInfoId(CommonUtil.covertLong(sysOutlet.getOutletId()), ticketTypeId));
					strOutletInfo.setStrTicketNum(slUselessCount.get(ticketTypeId));// 增加网点库存数量
					strOutletInfo.setLastUpdTime(new Date());
					strOutletInfos.put(ticketTypeId, strOutletInfo);
				}
			}
			strOutletInfoList.addAll(strOutletInfos.values());

			dbUtil.updateEntityBatch("批量更新门票库存", session, strTicketInfoList);
			dbUtil.updateEntityBatch("批量更新售票明细信息", session, slOrderDetailList);
			dbUtil.saveOrUpdateEntityBatch("批量保存更新网点库存", session, strOutletInfoList);

			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}

		return true;
	}

	@Override
	@ControlAspect(funtionName = "同步检票信息", operType = OperType.ADD)
	public boolean checkInfo(AUTHORIZATION auth, List<SL_CHECK> sl_checks) throws RPCException, TException {
		Session session = null;

		try {
			// 开启事务
			session = dbUtil.getSessionByTransaction();

			// 转换rpc entity为db entity
			List<SlCheck> slCheckList = new ArrayList<SlCheck>();
			List<SlOrderDetail> slOrderDetailList = new ArrayList<SlOrderDetail>();
			List<SlOrderVenueDetail> slOrderVenueDetailList = new ArrayList<SlOrderVenueDetail>();
			// 保存检票信息
			for (SL_CHECK sl_check : sl_checks) {
				SlCheck slCheck = new SlCheck();
				BeanUtils.copyRPCProperties(sl_check, slCheck);

				SlCheck slCheck2 = dbUtil.findById("是否重传", SlCheck.class, sl_check.getCheckId());

				// 更新售票信息为已检票
				SlOrderDetail orderDetail = null;
				orderDetail = dbUtil.queryFirstForBean("查询销售明细", "SELECT ODE.* FROM SL_ORDER O,SL_ORDER_DETAIL ODE WHERE O.ORDER_ID=ODE.ORDER_ID AND O.PAY_STAT='2' AND TICKET_CLASS=? AND TICKET_ID=?", SlOrderDetail.class, sl_check.getTicketClass(), sl_check.getTicketId());
				// 如果没有售票信息则不更新
				if (orderDetail != null && "Y".equals(slCheck.getPassFlag())) {
					SlOrderVenueDetail slOrderVenueDetail = dbUtil.queryFirstForBean("", "SELECT * FROM SL_ORDER_VENUE_DETAIL WHERE ORDER_DETAIL_ID=? AND VENUE_ID=?", SlOrderVenueDetail.class, orderDetail.getOrderDetailId(), sl_check.getVenueId());
					if (slOrderVenueDetail != null && slCheck2 == null) {
						if (sl_check.getRemainTimes() + 1 > slOrderVenueDetail.getRemainTimes()) {
							StrTicketInfo strTicketInfo = dbUtil.findById("", StrTicketInfo.class, sl_check.getTicketId());
							// 加入黑名单
							SysBlackList sysBlackList = new SysBlackList();
							sysBlackList.setBlackListId(UUIDGenerator.getPK());
							sysBlackList.setCardType("2");
							sysBlackList.setLossDt(new Date());
							sysBlackList.setTicketId(StringUtil.convertString(sl_check.getTicketId()));
							// 更新芯片ID
							if (strTicketInfo != null) {
								sysBlackList.setChipId(strTicketInfo.getChipId());
							}
							sysBlackList.setStat("Y");
							sysBlackList.setLossReason(String.format("上传检票数据时发现剩余次数为[%s],自动加入黑名单", slOrderVenueDetail.getRemainTimes()));
							sysBlackList.setOpeUserId(StringUtil.convertString(sl_check.getClientId()));
							sysBlackList.setOpeTime(new Date());
							sysBlackList.setVersionNo(new Date());
							dbUtil.saveEntity("保存黑名单", session, sysBlackList);
						}

						slOrderVenueDetail.setRemainTimes(sl_check.getRemainTimes());// 更新对应场馆的剩余次数
						slOrderVenueDetailList.add(slOrderVenueDetail);
					}

					orderDetail.setCheckFlag("Y");// 是否检票(Y是N否)
					orderDetail.setCheckTime(new Date(sl_check.getOpeTime()));
					slOrderDetailList.add(orderDetail);
					// 更新票据唯一号
					slCheck.setTicketUid(orderDetail.getTicketUid());
					slCheck.setOrderDetailId(orderDetail.getOrderDetailId());
				} else {// 没售票或未通过就进行相关处理
					StrTicketInfo strTicketInfo = dbUtil.findById("", StrTicketInfo.class, sl_check.getTicketId());
					// 更新票据唯一号
					if (strTicketInfo != null) {
						slCheck.setTicketUid(strTicketInfo.getTicketUid());
					}
				}

				slCheckList.add(slCheck);
			}

			dbUtil.saveOrUpdateEntityBatch("批量保存检票表", session, slCheckList);
			dbUtil.updateEntityBatch("批量更新售票单明细表", session, slOrderDetailList);
			dbUtil.updateEntityBatch("批量更新售票单门票场馆明细表表", session, slOrderVenueDetailList);

			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}

		return true;
	}

	@Override
	@ControlAspect(funtionName = "网点员工销售统计表")
	public java.util.List<com.tbims.rpc.entity.RPT_SALE_DATA_USER> rptSaleDataUser(com.tbims.rpc.entity.AUTHORIZATION auth, long rpt_date, long outlet_id) throws com.tbims.rpc.entity.RPCException, org.apache.thrift.TException {

		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();

		SysUser sysUser = ClientAuthCache.getAuthBean(auth.getClientId()).getSysUser();

		// ORDER_TYPE:销售类型(XC-现场售票、ZY-自营售票、TD-团队换票、WL-网络人工换票、ST-实体代理、BP-补票、ZG-自助购票、ZQ-自助取票)
		sql.append("select * from (                                                                                                                                                                                                                               ");
		sql.append("SELECT TRUNC(A.SALE_TIME, 'dd') RTP_DATE,E.OUTLET_ID,E.OUTLET_NAME,                                                                                                                                                                           ");
		sql.append("           B.EJECT_USER_ID USER_ID,                                                                                                                                                                                                           ");
		sql.append("           CASE WHEN A.ORDER_TYPE IN ('BP') THEN C.USER_NAME||'-补票' ELSE C.USER_NAME END  USER_NAME ,                                                                                                                                        ");
		sql.append("           SPT.PAY_TYPE,                                                                                                                                                                                                                        ");
		sql.append("           F.ITEM_NAME PAY_NAME,                                                                                                                                                                                                              ");
		sql.append("           B.TICKET_TYPE_ID,                                                                                                                                                                                                                  ");
		sql.append("           D.TICKET_TYPE_NAME,                                                                                                                                                                                                                ");
		sql.append("           SUM(case when A.Order_Stat='0' and A.Order_Type in ('XC', 'ZY', 'ST', 'BP') then B.SALE_PRICE else 0 end) SALE_SUM_AMT,                                                                                                            ");
		sql.append("           COUNT(1) SALE_NUM                                                                                                                                                                                                                  ");
		sql.append("     FROM SL_ORDER A LEFT JOIN SL_ORDER_DETAIL B on  A.ORDER_ID = B.ORDER_ID                                                                                                                                                                  ");
		sql.append("          INNER JOIN SYS_USER C ON B.EJECT_USER_ID = C.USER_ID                                                                                                                                                                                ");
		sql.append("          LEFT JOIN SYS_TICKET_TYPE D ON B.TICKET_TYPE_ID = D.TICKET_TYPE_ID                                                                                                                                                                  ");
		sql.append("          LEFT JOIN SL_PAY_TYPE SPT ON A.ORDER_ID=SPT.ORDER_ID                                                                                                                                                                                    ");
		sql.append("          LEFT JOIN SYS_DICTIONARY F ON SPT.PAY_TYPE=F.ITEM_CD AND F.KEY_CD='PAY_TYPE'                                                                                                                                                          ");
		sql.append("          LEFT JOIN SYS_OUTLET E   ON  B.OUTLET_ID=E.OUTLET_ID                                                                                                                                                                                ");
		sql.append("     WHERE A.PAY_STAT='2' and a.order_stat='0' AND A.Order_Type in ('XC','ZY','ST','BP','TD','WL')  AND B.OUTLET_ID=:OUTLET_ID and TRUNC(A.SALE_TIME, 'dd')=:RPT_DATE                                                                         ");
		if ("03".equals(sysUser.getPositionCode())) {
			sql.append(" AND B.EJECT_USER_ID=:USER_ID ");
			params.put("USER_ID", sysUser.getUserId());
		}
		sql.append("      GROUP BY E.OUTLET_ID,E.OUTLET_NAME,TRUNC(A.SALE_TIME, 'dd'),B.EJECT_USER_ID,C.USER_NAME,SPT.PAY_TYPE,F.ITEM_NAME,B.TICKET_TYPE_ID,D.TICKET_TYPE_NAME,A.ORDER_TYPE                                                                         ");
		sql.append("  union all                                                                                                                                                                                                                                   ");
		sql.append("      SELECT TRUNC(A.SALE_TIME, 'dd') RTP_DATE,E.OUTLET_ID,E.OUTLET_NAME,                                                                                                                                                                     ");
		sql.append("           B.EJECT_USER_ID USER_ID,                                                                                                                                                                                                           ");
		sql.append("           C.USER_NAME||'-售票机补票',                                                                                                                                                                                                         ");
		sql.append("           SPT.PAY_TYPE,                                                                                                                                                                                                                        ");
		sql.append("           F.ITEM_NAME PAY_NAME,                                                                                                                                                                                                              ");
		sql.append("           B.TICKET_TYPE_ID,                                                                                                                                                                                                                  ");
		sql.append("           D.TICKET_TYPE_NAME,                                                                                                                                                                                                                ");
		sql.append("           0 SALE_SUM_AMT,                                                                                                                                                                                                                    ");
		sql.append("           COUNT(1) SALE_NUM                                                                                                                                                                                                                  ");
		sql.append("     FROM SL_ORDER A LEFT JOIN SL_ORDER_DETAIL B on  A.ORDER_ID = B.ORDER_ID                                                                                                                                                                  ");
		sql.append("          INNER JOIN SYS_USER C ON B.EJECT_USER_ID = C.USER_ID                                                                                                                                                                                ");
		sql.append("          LEFT JOIN SYS_TICKET_TYPE D ON B.TICKET_TYPE_ID = D.TICKET_TYPE_ID                                                                                                                                                                  ");
		sql.append("          LEFT JOIN SL_PAY_TYPE SPT ON A.ORDER_ID=SPT.ORDER_ID                                                                                                                                                                                    ");
		sql.append("          LEFT JOIN SYS_DICTIONARY F ON SPT.PAY_TYPE=F.ITEM_CD AND F.KEY_CD='PAY_TYPE'                                                                                                                                                          ");
		sql.append("          LEFT JOIN SYS_OUTLET E   ON  B.OUTLET_ID=E.OUTLET_ID                                                                                                                                                                                ");
		sql.append("     WHERE A.PAY_STAT='2' and a.order_stat='0' AND A.Order_Type in ('ZG')  AND B.OUTLET_ID=:OUTLET_ID and TRUNC(A.SALE_TIME, 'dd')=:RPT_DATE                                                                                                         ");
		if ("03".equals(sysUser.getPositionCode())) {
			sql.append(" AND B.EJECT_USER_ID=:USER_ID ");
			params.put("USER_ID", sysUser.getUserId());
		}
		sql.append("      GROUP BY E.OUTLET_ID,E.OUTLET_NAME,TRUNC(A.SALE_TIME, 'dd'),B.EJECT_USER_ID,C.USER_NAME,SPT.PAY_TYPE,F.ITEM_NAME,B.TICKET_TYPE_ID,D.TICKET_TYPE_NAME                                                                                      ");
		if (!"03".equals(sysUser.getPositionCode())) {
			sql.append("   UNION ALL   ");
			sql.append("   SELECT TRUNC(A.SALE_TIME, 'dd') RTP_DATE,E.OUTLET_ID,E.OUTLET_NAME,");
			sql.append("            'N' USER_ID,");
			sql.append("            sct.client_name USER_NAME,");
			sql.append("            'N' PAY_TYPE,");
			sql.append("            'N' PAY_NAME,");
			sql.append("            STT.ticket_type_id TICKET_TYPE_ID,");
			sql.append("            TTYPE.TICKET_TYPE_NAME,");
			sql.append("            SUM(A.REAL_SUM) SALE_SUM_AMT,");
			sql.append("            sum(nvl(STT.EJECT_TICKET_COUNT,0)) SALE_NUM");
			sql.append("      FROM SL_ORDER A  left join (select a.order_id,c.ticket_type_id,sum(case when b.order_detail_id is null then 0 else   1 end)  EJECT_TICKET_COUNT");
			sql.append("              from sl_order a inner join sl_order_tickettype_detail c on a.order_id=c.order_id");
			sql.append("                    left join sl_order_detail b  on a.order_id=b.order_id and b.eject_user_id is null");
			sql.append("              where order_stat='0' and A.PAY_STAT='2'   AND A.Order_Type in ('ZG') and TRUNC(A.SALE_TIME, 'dd')=:RPT_DATE GROUP BY A.ORDER_ID ,C.TICKET_TYPE_ID ");
			sql.append("      ) STT on A.Order_Id=STT.order_id");
			sql.append("           left join sys_client sct on A.Sale_User_Id=sct.client_id");
			sql.append("           LEFT JOIN SYS_OUTLET E   ON  sct.OUTLET_ID=E.OUTLET_ID");
			sql.append("           LEFT JOIN SYS_TICKET_TYPE TTYPE ON  STT.TICKET_TYPE_ID = TTYPE.TICKET_TYPE_ID   ");
			sql.append("      WHERE A.PAY_STAT='2' and a.order_stat='0' AND A.Order_Type in ('ZG')  AND E.OUTLET_ID=:OUTLET_ID and TRUNC(A.SALE_TIME, 'dd')=:RPT_DATE ");
			sql.append("       GROUP BY sct.client_id,sct.client_name,E.OUTLET_ID,E.OUTLET_NAME,TRUNC(A.SALE_TIME, 'dd') ,stt.ticket_type_id,TTYPE.TICKET_TYPE_NAME ");
		}

		sql.append("  ) a order by A.USER_ID ASC ,A.USER_NAME,A.PAY_TYPE,A.TICKET_TYPE_ID                                                                                ");

		params.put("OUTLET_ID", outlet_id);
		params.put("RPT_DATE", new Date(rpt_date));

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
		sql.append("SELECT TRUNC(A.SALE_TIME, 'dd') RTP_DATE,                                                                    ");
		sql.append("       E.OUTLET_ID,                                                                                          ");
		sql.append("       E.OUTLET_NAME,                                                                                        ");
		sql.append("       D.PAY_TYPE,                                                                                           ");
		sql.append("       F.ITEM_NAME PAY_NAME,                                                                                 ");
		sql.append("       B.TICKET_TYPE_ID,                                                                                     ");
		sql.append("       D.TICKET_TYPE_NAME,                                                                                   ");
		sql.append("       SUM(CASE                                                                                              ");
		sql.append("             WHEN A.ORDER_TYPE IN ('XC', 'ZY', 'ST', 'BP','ZG') THEN                                         ");
		sql.append("              B.SALE_PRICE                                                                                   ");
		sql.append("             ELSE                                                                                            ");
		sql.append("              0                                                                                              ");
		sql.append("           END) SALE_SUM_AMT,                                                                                ");
		sql.append("       COUNT(1) SALE_NUM                                                                                     ");
		sql.append("  FROM SL_ORDER A                                                                                            ");
		sql.append("  inner JOIN SL_ORDER_DETAIL B                                                                               ");
		sql.append("    on A.ORDER_ID = B.ORDER_ID                                                                               ");
		sql.append("  LEFT JOIN SYS_TICKET_TYPE D                                                                                ");
		sql.append("    ON B.TICKET_TYPE_ID = D.TICKET_TYPE_ID                                                                   ");
		sql.append("  LEFT JOIN SL_PAY_TYPE D                                                                                    ");
		sql.append("    ON A.ORDER_ID = D.ORDER_ID                                                                               ");
		sql.append("  LEFT JOIN SYS_DICTIONARY F                                                                                 ");
		sql.append("    ON D.PAY_TYPE = F.ITEM_CD                                                                                ");
		sql.append("   AND F.KEY_CD = 'PAY_TYPE'                                                                                 ");
		sql.append("  LEFT JOIN SYS_OUTLET E                                                                                     ");
		sql.append("    ON B.OUTLET_ID = E.OUTLET_ID                                                                             ");
		sql.append(" WHERE A.PAY_STAT = '2'  and a.order_stat='0'                                                                                     ");
		sql.append("   and B.OUTLET_ID = :OUTLET_ID                                                                                     ");
		sql.append("   AND TRUNC(A.SALE_TIME, 'dd') = :RPT_DATE                                             ");
		sql.append("   and A.ORDER_TYPE in ('XC','ZY','ST','BP','TD','WL','ZG')                                                  ");
		sql.append(" GROUP BY E.OUTLET_ID,                                                                                       ");
		sql.append("          E.OUTLET_NAME,                                                                                     ");
		sql.append("          TRUNC(A.SALE_TIME, 'dd'),                                                                          ");
		sql.append("          D.PAY_TYPE,                                                                                        ");
		sql.append("          F.ITEM_NAME,                                                                                       ");
		sql.append("          B.TICKET_TYPE_ID,                                                                                  ");
		sql.append("          D.TICKET_TYPE_NAME                                                                                 ");
		sql.append("union                                                                                                        ");
		sql.append("SELECT                                                                                                       ");
		sql.append("TRUNC(A.SALE_TIME, 'dd') RTP_DATE,                                                                           ");
		sql.append("               E.OUTLET_ID,                                                                                  ");
		sql.append("               E.OUTLET_NAME||'-售票机未取票金额',                                                                                ");
		sql.append("               'N' PAY_TYPE,                                                                                 ");
		sql.append("               'N' PAY_NAME,                                                                                 ");
		sql.append("               'N' TICKET_TYPE_ID,                                                                           ");
		sql.append("               'N' TICKET_TYPE_NAME,                                                                         ");
		sql.append("               SUM(A.REAL_SUM-nvl(stt.eject_price,0)) SALE_SUM_AMT,                                          ");
		sql.append("               sum(A.Ticket_Count-nvl(STT.EJECT_TICKET_COUNT,0)) SALE_NUM                                    ");
		sql.append("          FROM SL_ORDER A                                                                                    ");
		sql.append("         left join (select a.order_id, count(1) EJECT_TICKET_COUNT,sum(b.sale_price) eject_price            ");
		sql.append("                      from sl_order a, sl_order_detail b                                                     ");
		sql.append("                     where a.order_id = b.order_id                                                           ");
		sql.append("                       and A.order_stat = '0'                                                                  ");
		sql.append("                       and A.PAY_STAT = '2'                                                                  ");
		sql.append("                       AND A.Order_Type in ('ZG')                                                            ");
		sql.append("                       and TRUNC(A.SALE_TIME, 'dd') = :RPT_DATE                         ");
		sql.append("                     group by a.order_id) STT                                                                ");
		sql.append("            on A.Order_Id = STT.order_id                        ");
		sql.append("          left join sys_client sct                                                                           ");
		sql.append("            on A.Sale_User_Id = sct.client_id                                                                ");
		sql.append("          LEFT JOIN SYS_OUTLET E                                                                             ");
		sql.append("            ON sct.OUTLET_ID = E.OUTLET_ID                                                                   ");
		sql.append("         WHERE A.PAY_STAT = '2' AND A.Ticket_Count-nvl(STT.EJECT_TICKET_COUNT,0)<>0                                                                            ");
		sql.append("           AND A.Order_Type in ('ZG')  and A.order_stat = '0'                                                                         ");
		sql.append("           AND E.OUTLET_ID = :OUTLET_ID                                                                             ");
		sql.append("           and TRUNC(A.SALE_TIME, 'dd') = :RPT_DATE                                     ");
		sql.append("         GROUP BY E.OUTLET_ID,                                                                               ");
		sql.append("                  E.OUTLET_NAME,                                                                             ");
		sql.append("                  TRUNC(A.SALE_TIME, 'dd')                                                                   ");

		params.put("OUTLET_ID", outlet_id);
		params.put("RPT_DATE", new Date(rpt_date));

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
		sql.append("       SUPPLY_TICKET_NUM SUPPLY_NUM,                                    ");
		sql.append("       STR_TICKET_NUM                                                     ");
		sql.append(" FROM RPT_STRINFO_OUTLET_D RPT ");
		sql.append("   LEFT JOIN  SYS_TICKET_TYPE T ON RPT.TICKET_TYPE_ID=T.TICKET_TYPE_ID ");
		sql.append("   LEFT JOIN  SYS_OUTLET O ON    RPT.OUTLET_ID=O.OUTLET_ID      ");
		sql.append("  WHERE RPT.OUTLET_ID=:OUTLET_ID ");
		sql.append(" AND ( 	STR_LAST_TICKET_NUM<>0 or IN_TICKET_NUM<>0 or SALE_TICKET_NUM<>0 or USELESS_TICKET_NUM<>0 or SUPPLY_TICKET_NUM<>0 or STR_TICKET_NUM<>0    ) ");
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
		sb.append(" select * from ( ");
		sb.append(" select mysum.RPT_DATE,     ");
		sb.append("        mysum.outlet_id,    ");
		sb.append("        mysum.ticket_type_id, ");
		sb.append("        outlet.outlet_name,  ");
		sb.append("        ty.ticket_type_name,  ");
		sb.append("        sum(str_last_ticket_num) str_last_ticket_num,  ");
		sb.append("        sum(in_ticket_num) delivery_Num, ");
		sb.append("        sum(sale_ticket_num) sale_ticket_num,   ");
		sb.append("        sum(useless_ticket_num) useless_num, ");
		sb.append("		   sum(supply_ticket_num) supply_num,");
		sb.append("        sum(str_ticket_num)    str_ticket_num   ");
		sb.append("   from (select trunc(sysdate, 'dd') rpt_date,      ");
		sb.append("                ti.outlet_id,         ");
		sb.append("                ti.ticket_type_id,                   ");
		sb.append("                0 str_last_ticket_num,              ");
		sb.append("                0 in_ticket_num,                    ");
		sb.append("                0 sale_ticket_num,                  ");
		sb.append("                0 useless_ticket_num,               ");
		sb.append("   			   0 supply_ticket_num, 				");
		sb.append("                ti.str_ticket_num             ");
		sb.append("           from  str_outlet_info ti where 1=1            ");
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
		sb.append("  			   0 supply_ticket_num, 			   ");
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
		sb.append("   			   0 supply_ticket_num,    ");
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
		sb.append("   			   0 supply_ticket_num,		  ");
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
		sb.append("         select trunc(sysdate, 'dd') rpt_date,                  ");
		sb.append("                useless.outlet_id,                                           ");
		sb.append("                useless.ticket_type_id,                                      ");
		sb.append("                0 str_last_ticket_num,                                       ");
		sb.append("                0 in_ticket_num,                                             ");
		sb.append("                0 sale_ticket_num,                                           ");
		sb.append("                sum(case when useless.useless_reason='芯片作废' then 1 else 0 end) useless_ticket_num, ");
		sb.append("                sum(case when useless.useless_reason='补票作废' then 1 else 0 end) supply_ticket_num,  ");
		sb.append("                0 str_ticket_num                                             ");
		sb.append("           from sl_useless_ticket_info useless                               ");
		sb.append("          where  useless.confirm_time is null  ");
		if (outlet_id != 0) {
			sb.append("               and 	useless.outlet_id=:OUTLET_ID ");
		}
		sb.append("          group by                         ");
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
		sb.append(" ) where (str_last_ticket_num<>0 or delivery_Num<>0 or sale_ticket_num<>0 or useless_num<>0 or supply_num<>0 or str_ticket_num<>0  )  ");
		sb.append("  order by  rpt_date, outlet_id ,ticket_type_id ");

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
	@ControlAspect(funtionName = "身份证入园检票")
	public int checkTicketOnline(AUTHORIZATION auth, String checkId, String ticketClass, String ticketUid) throws RPCException, TException {
		Session session = null;

		// 初始化信息
		String clientId = StringUtil.convertString(auth.getClientId());
		Long venueId = CommonUtil.covertLong(clientId.substring(1, 3));

		String passFlag = "N";
		// ErrorCode错误码(1-黑名单门票、2-已过有时间、3-次数已用完、4-场馆不适用、5-当前不可入场 40-无可用的销售记录)
		String errorCode = "40";// 默认检票失败
		String nopassReason = "当前场馆无有效门票";
		Long remainTimes = -1L;

		SlCheck slCheck = new SlCheck();
		slCheck.setCheckId(checkId);
		slCheck.setTicketClass(ticketClass);
		slCheck.setTicketUid(ticketUid);
		slCheck.setVenueId(venueId);
		slCheck.setClientId(auth.getClientId());

		try {
			// 开启事务
			session = dbUtil.getSessionByTransaction();

			SlCheck slCheck1 = dbUtil.findById("检验是否重复", SlCheck.class, checkId);
			if (slCheck1 != null) {
				if ("Y".equals(slCheck1.getPassFlag())) {
					return 1;// 返回 检票通过
				} else {
					return CommonUtil.covertInt(slCheck1.getErrorCode());
				}
			}
			if(!clientId.startsWith("2")){	// 非闸机客户端，根据客户端设置的区域id查找对应场馆编号
				SysVenue sysVenue = dbUtil.queryFirstForBean("查询当前场馆", "SELECT sv.* FROM Sys_Venue sv,Sys_Region sr,Sys_Client sc WHERE sc.Client_id=? AND sc.STAT='Y' AND sc.Region_Id=sr.Region_id AND sr.Venue_id=sv.Venue_id", SysVenue.class, clientId);
				if(sysVenue == null) {
					return 44;	//  未获取到有效场馆信息
				}
				venueId = sysVenue.getVenueId();
				slCheck.setVenueId(venueId);	
			}
			
			// 检查在几秒内重复验票
			SysParemeter sysParemeter = dbUtil.findById("", SysParemeter.class, "check_valid_time");
			if (sysParemeter != null) {
				long checkValidTime = CommonUtil.covertLong(sysParemeter.getParemeterVal());
				SlCheck slCheckTimeOut = dbUtil.queryFirstForBean("校验重复验票", "SELECT * FROM SL_CHECK WHERE TICKET_UID=? AND VENUE_ID=? AND PASS_FLAG='Y' AND OPE_TIME >= SYSDATE - (?/(24*60*60))", SlCheck.class, ticketUid, venueId, checkValidTime);
				if (slCheckTimeOut != null) {
					return 2;//// 重复验票返回 检票通过
				}
			}

			SlOrderDetail slOrderDetail = null;
			// 身份证入园检票-联机(没检票、没取实体票、没有作废的记录就通过，通过后再入检票表)
			if ("2".equals(ticketClass)) {
				// 查询订单明细中身份证号是否有未入园记录
				// 查询{未作废的记录、票剩余次数大于0、未取票的记录、支付成功的记录、未出票的记录}
				StringBuffer sql = new StringBuffer();
				// PAY_STAT 支付状态(1-待支付 2-已支付 3-支付失败)
				// 、EJECT_TICKET_STAT出票状态(1-待出票2-已出票)
				sql.append("SELECT DISTINCT A.ORDER_DETAIL_ID,A.TICKET_TYPE_ID,A.VALID_START_DATE,A.VALID_END_DATE,B.VENUE_ID,A.SALE_PRICE ");
				sql.append("FROM SL_ORDER_DETAIL A  ");
				sql.append("INNER JOIN SL_ORDER_VENUE_DETAIL B ON A.ORDER_DETAIL_ID=B.ORDER_DETAIL_ID ");
				sql.append("INNER JOIN SL_ORDER C ON A.ORDER_ID=C.ORDER_ID ");
				sql.append("WHERE A.TICKET_CLASS='2' AND C.PAY_STAT='2' AND A.USELESS_FLAG='N' ");
				sql.append(" AND B.REMAIN_TIMES>0 AND A.EJECT_TICKET_STAT='1' ");
				sql.append(" AND A.IDENTTY_ID=? AND B.VENUE_ID=? ");
				sql.append(" ORDER BY A.SALE_PRICE ASC ");

				List<CheckOnlineBean> checkOnlineBeanList = dbUtil.queryListToBean("按身份证号查询可用售票记录", sql.toString(), CheckOnlineBean.class, ticketUid, venueId);
				if (checkOnlineBeanList == null || checkOnlineBeanList.size() == 0) {
					passFlag = "N";
					errorCode = "40";
					nopassReason = "当前场馆无有效门票";
				} else {
					boolean checkFlag = false;
					for (CheckOnlineBean checkOnlineBean : checkOnlineBeanList) {
						Date nowDateTime = new Date();// 当前日期时间
						long nowTime = DateUtil.timeToNumber(DateUtil.getNowDate("HH:mm:ss"));// 当前时间

						// 检验销售记录中的有效开始日期和结束日期
						if ((checkOnlineBean.getValidStartDate() != null && checkOnlineBean.getValidEndDate() != null &&
								!(DateUtils.truncatedCompareTo(nowDateTime, checkOnlineBean.getValidStartDate(), Calendar.DATE) >= 0 && DateUtils.truncatedCompareTo(nowDateTime, checkOnlineBean.getValidEndDate(), Calendar.DATE) <= 0))
								|| (checkOnlineBean.getValidStartDate() == null && checkOnlineBean.getValidEndDate() != null && DateUtils.truncatedCompareTo(nowDateTime, checkOnlineBean.getValidEndDate(), Calendar.DATE) > 0)
								|| (checkOnlineBean.getValidStartDate() != null && checkOnlineBean.getValidEndDate() == null && DateUtils.truncatedCompareTo(nowDateTime, checkOnlineBean.getValidStartDate(), Calendar.DATE) < 0)){
							passFlag = "N";
							errorCode = "41";
							nopassReason = String.format("当前门票已过有效期！开始日期[%s]结束日期[%s]", checkOnlineBean.getValidEndDate(), checkOnlineBean.getValidEndDate());
							logger.equals(String.format("当前门票已过有效期！开始日期[%s]结束日期[%s]", checkOnlineBean.getValidEndDate(), checkOnlineBean.getValidEndDate()));
							continue;
						}

						// 检验检票规则 指定日期、时间
						String sqlrule = "SELECT * FROM SYS_TICKET_TYPE_RULE WHERE TICKET_TYPE_ID=?  ";
						sqlrule += "AND TRUNC(SYSDATE,'DD')>=BEGIN_DT AND TRUNC(SYSDATE,'DD')<=END_DT ";
						sqlrule += " AND ?>=BEGIN_TIME AND ?<=END_TIME ORDER BY OPE_TIME DESC ";

						SysTicketTypeRule sysTicketTypeRule = dbUtil.queryFirstForBean("校验时间", sqlrule, SysTicketTypeRule.class, checkOnlineBean.getTicketTypeId(), nowTime, nowTime);
						if (sysTicketTypeRule == null) {
							passFlag = "N";
							errorCode = "42";
							nopassReason = String.format("当前时间不允许此门票入场！票种[%s]", checkOnlineBean.getTicketTypeId());
							logger.equals(String.format("当前时间不允许此门票入场！票种[%s]", checkOnlineBean.getTicketTypeId()));
							continue;
						}

						// 检验检票规则 指定星期
						String validWeek = sysTicketTypeRule.getValidWeek();
						if (StringUtil.isNotNull(validWeek)) {
							Date date = new Date();
							Calendar cal = Calendar.getInstance();
							cal.setTime(date);
							int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
							if (validWeek.charAt(week) == '0') {
								passFlag = "N";
								errorCode = "42";
								nopassReason = String.format("当前时间不允许此门票入场！票种[%s]", checkOnlineBean.getTicketTypeId());
								logger.equals(String.format("当前时间不允许此门票入场！票种[%s]", checkOnlineBean.getTicketTypeId()));
								continue;
							}
						}

						// 符合检票规则,更新销售明细表、销售明细场馆表
						SlOrderVenueDetail slOrderVenueDetail = dbUtil.findById("查询门票场馆明细", SlOrderVenueDetail.class, new SlOrderVenueDetailId(checkOnlineBean.getOrderDetailId(), checkOnlineBean.getVenueId()));
						slOrderVenueDetail.setRemainTimes(slOrderVenueDetail.getRemainTimes() - 1);// 更新剩余次数.

						slOrderDetail = dbUtil.findById("查询销售明细", SlOrderDetail.class, checkOnlineBean.getOrderDetailId());
						if(slOrderDetail.getCheckFlag().equals("N")) {
							//  次门票第一次检票才修改订单明细表相关信息     20180308 zhuxy
							slOrderDetail.setCheckFlag("Y");// 是否检票(Y是N否)
							slOrderDetail.setOrgCallbackStat("N");//构回调状态(Y:已回调 N:未回调 W:待网络核销回调 S:跳过)
							slOrderDetail.setCheckTime(new Timestamp().getDate());

							dbUtil.updateEntity("更新销售明细表", session, slOrderDetail);
						}

						dbUtil.updateEntity("更新销售明细场馆表", session, slOrderVenueDetail);

						// 添加检票信息
						slCheck.setTicketTypeId(checkOnlineBean.getTicketTypeId());
						slCheck.setOrderDetailId(checkOnlineBean.getOrderDetailId());
						remainTimes = slOrderVenueDetail.getRemainTimes();

						checkFlag = true;
						break;
					}

					// 检票通过，有售票记录符合检票规则
					if (checkFlag) {
						passFlag = "Y";// 是否通过(Y是N否)
						errorCode = "";// 检票通过
						nopassReason = "";
					}
				}
			}

			// 保存检票信息
			slCheck.setPassFlag(passFlag);
			slCheck.setErrorCode(errorCode);
			slCheck.setNopassReason(nopassReason);
			slCheck.setRemainTimes(remainTimes);
			slCheck.setOpeTime(new Timestamp().getDate());
			slCheck.setVersionNo(new Timestamp().getDate());

			dbUtil.saveEntity("保存检票信息", session, slCheck);
			
			dbUtil.commit(session);
			
			if ("Y".equals(passFlag)) {
				IdentiyCheckCacheBean identiyCheckCacheBean=new IdentiyCheckCacheBean();
				identiyCheckCacheBean.setIdentiy(slCheck.getTicketUid());
				identiyCheckCacheBean.setOrderId(slOrderDetail.getOrderId());
				identiyCheckCacheBean.setOrderDetailId(slOrderDetail.getOrderDetailId());
				identiyCheckCacheBean.setCheckTime(slCheck.getOpeTime());
				IdentiyCheckCache.getIdentiyCheckCacheDeque().put(identiyCheckCacheBean);
				return 1;// 检票通过
			} else {
				return CommonUtil.covertInt(errorCode);
			}
		} catch (Exception e) {
			passFlag = "N";// 是否通过(Y是N否)
			errorCode = "43";
			nopassReason = "系统错误";
			slCheck.setPassFlag(passFlag);
			slCheck.setErrorCode(errorCode);
			slCheck.setNopassReason(nopassReason);
			slCheck.setOpeTime(new Timestamp().getDate());
			slCheck.setVersionNo(new Timestamp().getDate());
			try {
				dbUtil.saveEntity("保存出错的检票信息", slCheck);
			} catch (Exception e1) {
				logger.error("检票错误", e);
			}

			logger.error("检票错误", e);
		} finally {
			dbUtil.close(session);
		}

		return CommonUtil.covertInt(errorCode);
	}

	@Override
	@ControlAspect(funtionName = "售票信息上传(扫码支付)", operType = OperType.ADD)
	public boolean saleTicketByZG(AUTHORIZATION auth, SL_ORDER sl_order, com.tbims.rpc.entity.SL_ORDER_TICKETTYPE_DETAIL sl_order_tickettype_detail) throws RPCException, TException {
		Session session = null;
		// 售票信息上传(ZG-自助购票或XXC-现场扫码支付 按RFID卡或XC-现场扫码支付)
		try {
			Long outletId = ClientAuthCache.getAuthBean(auth.getClientId()).getSysOutlet().getOutletId();
			String outletType = ClientAuthCache.getAuthBean(auth.getClientId()).getSysClient().getClientType();

			checkOutletInfos(outletId);

			// 开启事务
			session = dbUtil.getSessionByTransaction();

			SlOrder slOrderByCheck = dbUtil.findById("查询订单", SlOrder.class, sl_order.getOrderId());
			if (slOrderByCheck != null) {
				throw new RPCException(4, "订单已存在,请调用查询接口,查看订单状态");
			}

			// 订单和票据信息列表
			SlOrder slOrder = new SlOrder();
			BeanUtils.copyRPCProperties(sl_order, slOrder);
			slOrder.setPayStat("1");// 支付状态(1-待支付 2-已支付 3-支付失败).

			List<SlOrderDetail> slOrderDetailList = new ArrayList<SlOrderDetail>();
			List<StrTicketInfo> strTicketInfoList = new ArrayList<StrTicketInfo>();
			List<SlOrderVenueDetail> slOrderVenueDetailList = new ArrayList<SlOrderVenueDetail>();
			List<SlOrderTickettypeDetail> slOrderTickettypeDetailList = new ArrayList<SlOrderTickettypeDetail>();

			if (sl_order.getSlOrderDetaillist() != null) {
				String userId = ClientAuthCache.getAuthBean(auth.getClientId()).getSysUser().getUserId();
				Map<String, SlOrderTickettypeDetail> slOrderTickettypeDetailMap = new HashMap<String, SlOrderTickettypeDetail>();// 缓存每个票种的售票明细

				for (SL_ORDER_DETAIL sl_order_detail : sl_order.getSlOrderDetaillist()) {
					SlOrderDetail slOrderDetail = new SlOrderDetail();
					BeanUtils.copyRPCProperties(sl_order_detail, slOrderDetail);
					slOrderDetail.setCheckFlag("N"); // CHECK_FLAG 是否检票(Y是N否)
					slOrderDetail.setUselessFlag("N"); // USELESS_FLAG
														// 是否作废(Y是N否)

					StrTicketInfo ticketInfo = dbUtil.findById("查询门票库存", StrTicketInfo.class, slOrderDetail.getTicketId());
					if (ticketInfo != null) {
						ticketInfo.setStat("003");// 状态(000-未核实 001-已核实
													// 003-已销售,004-已作废)
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
					List<SysTicketTypeVenue> sysTicketTypeVenueList = dbUtil.queryListToBean("查询票种场馆表", "SELECT * FROM SYS_TICKET_TYPE_VENUE WHERE TICKET_TYPE_ID=?", SysTicketTypeVenue.class, slOrderDetail.getTicketTypeId());
					for (SysTicketTypeVenue sysTicketTypeVenue : sysTicketTypeVenueList) {
						SlOrderVenueDetail slOrderVenueDetail = new SlOrderVenueDetail();
						slOrderVenueDetail.setId(new SlOrderVenueDetailId(slOrderDetail.getOrderDetailId(), sysTicketTypeVenue.getId().getVenueId()));
						slOrderVenueDetail.setRemainTimes(slOrderDetail.getValidateTimes());
						slOrderVenueDetailList.add(slOrderVenueDetail);
					}

				}

				slOrderTickettypeDetailList.addAll(slOrderTickettypeDetailMap.values());

			}

			// 保存销售单-票种明细表
			if (sl_order_tickettype_detail != null && StringUtil.isNotNull(sl_order_tickettype_detail.getTicketTypeId())) {
				SlOrderTickettypeDetail slOrderTickettypeDetail = new SlOrderTickettypeDetail();
				BeanUtils.copyRPCProperties(sl_order_tickettype_detail, slOrderTickettypeDetail);
				slOrderTickettypeDetail.setId(new SlOrderTickettypeDetailId(sl_order_tickettype_detail.getOrderId(), sl_order_tickettype_detail.getTicketTypeId()));
				slOrderTickettypeDetailList.add(slOrderTickettypeDetail);
			}

			// 保存销售单-支付明细
			List<SlPayType> slPayTypeList = new ArrayList<SlPayType>();
			if (sl_order.getSlPayTypelist() != null && sl_order.getSlPayTypelist().size() != 0) {
				for (SL_PAY_TYPE sl_pay_type : sl_order.getSlPayTypelist()) {
					SlPayType slPayType = new SlPayType();
					BeanUtils.copyRPCProperties(sl_pay_type, slPayType);

					slPayTypeList.add(slPayType);
				}
			} else {
				throw new RPCException(4, "支付方式错误");
			}

			dbUtil.saveOrUpdateEntity("批量保存订单", session, slOrder);
			dbUtil.saveOrUpdateEntityBatch("批量保存销售单票种明细表", session, slOrderTickettypeDetailList);
			dbUtil.saveOrUpdateEntityBatch("批量保存支付明细", session, slPayTypeList);
			dbUtil.saveOrUpdateEntityBatch("批量保存订单明细", session, slOrderDetailList);
			dbUtil.saveOrUpdateEntityBatch("批量保存销售单门票场馆明细表", session, slOrderVenueDetailList);

			// 保存成功，保存销售信息,再支付
			dbUtil.commit(session);

			// 开启事务
			session = dbUtil.getSessionByTransaction();

			dbUtil.queryFirstListToBeanLock("查询订单", session, "FROM SlOrder U where orderId=?", "U", SlOrder.class, slOrder.getOrderId());

			// 调用第三方支付接口
			OrderPayRequest orderPayRequest = new OrderPayRequest();
			orderPayRequest.setMch_id(SwiftpassConfig.mch_id);
			orderPayRequest.setDevice_info(StringUtil.convertString(auth.getClientId()));
			orderPayRequest.setMch_create_ip(CommonUtil.getHostIP());
			orderPayRequest.setOut_trade_no(slOrder.getOrderId());
			orderPayRequest.setBody("园博园门票");
			orderPayRequest.setAuth_code(slPayTypeList.get(0).getPaymentCode());
			orderPayRequest.setTotal_fee(slPayTypeList.get(0).getAmt().intValue());
			int payStat = payUtil.orderPay(session, orderPayRequest);

			slOrder.setPayStat(StringUtil.convertString(payStat));

			if (payStat == 2 && "1".equals(outletType)) {// 支付成功

				// 核减网点库存数量
				for (SlOrderTickettypeDetail slOrderTickettypeDetail : slOrderTickettypeDetailList) {
					String ticketTypeId = slOrderTickettypeDetail.getId().getTicketTypeId();
					Long ticketNum = CommonUtil.covertLong((slOrderTickettypeDetail.getEjectTicketCount()));
					reduceOutletInfos(session, outletId, ticketTypeId, ticketNum);
				}

				dbUtil.updateEntityBatch("批量更新门票库存信息", session, strTicketInfoList);
			}

			dbUtil.saveOrUpdateEntity("保存订单", session, slOrder);

			// 保存成功
			dbUtil.commit(session);

			if (payStat != 2) {
				throw new RPCException(3, "支付失败");
			}

			return true;
		} finally {
			dbUtil.close(session);
		}
	}

	/**
	 * Title: 减少网点库存<br/>
	 * Description:
	 * 
	 * @param session
	 * @param outletId
	 * @param ticketTypeId
	 * @param ticketNum
	 */
	private void reduceOutletInfos(Session session, Long outletId, String ticketTypeId, Long ticketNum) {
		dbUtil.executeSql("更新网点库存", session, "UPDATE STR_OUTLET_INFO SET STR_TICKET_NUM=STR_TICKET_NUM-?,last_upd_time=sysdate WHERE OUTLET_ID=? AND TICKET_TYPE_ID=?", ticketNum, outletId, ticketTypeId);
	}

	/**
	 * Title:增加网点库存 <br/>
	 * Description:
	 * 
	 * @param session
	 * @param outletId
	 * @param ticketTypeId
	 * @param ticketNum
	 */
	private void addOutletInfos(Session session, Long outletId, String ticketTypeId, Long ticketNum) {
		dbUtil.executeSql("更新网点库存", session, "UPDATE STR_OUTLET_INFO SET STR_TICKET_NUM=STR_TICKET_NUM+?,last_upd_time=sysdate WHERE OUTLET_ID=? AND TICKET_TYPE_ID=?", ticketNum, outletId, ticketTypeId);
	}

	private void checkOutletInfos(Long outletId) {
		if (outletId == null || outletId == 0) {
			return;
		}

		List<SysTicketType> sysTicketTypeList = dbUtil.queryListToBean("", "SELECT * FROM SYS_TICKET_TYPE", SysTicketType.class);
		for (SysTicketType sysTicketType : sysTicketTypeList) {
			StrOutletInfo strOutletInfo = dbUtil.findById("", StrOutletInfo.class, new StrOutletInfoId(outletId, sysTicketType.getTicketTypeId()));
			if (strOutletInfo == null) {
				StrOutletInfo strOutletInfoNew = new StrOutletInfo();
				strOutletInfoNew.setId(new StrOutletInfoId(outletId, sysTicketType.getTicketTypeId()));
				strOutletInfoNew.setStrTicketNum(0L); // 核减网点库存数量增加
				strOutletInfoNew.setLastUpdTime(new Date());
				dbUtil.saveEntity("添加网点库存", strOutletInfoNew);
			}
		}
	}

	@Override
	@ControlAspect(funtionName = "售票信息上传(扫码支付按身份证)", operType = OperType.ADD)
	public boolean saleTicketByIdenttyZG(AUTHORIZATION auth, SL_ORDER sl_order) throws RPCException, TException {
		Session session = null;
		try {
			// 开启事务
			session = dbUtil.getSessionByTransaction();

			SlOrder slOrderByCheck = dbUtil.findById("查询订单", SlOrder.class, sl_order.getOrderId());
			if (slOrderByCheck != null) {
				throw new RPCException(4, "订单已存在,请调用查询接口,查看订单状态");
			}

			// 订单和票据信息列表
			List<SlOrderDetail> slOrderDetailList = new ArrayList<SlOrderDetail>();
			List<SlPayType> slPayTypeList = new ArrayList<SlPayType>();
			List<SlOrderTickettypeDetail> slOrderTickettypeDetailList = new ArrayList<SlOrderTickettypeDetail>();
			List<SlOrderVenueDetail> slOrderVenueDetailList = new ArrayList<SlOrderVenueDetail>();
			// 缓存每个票种的售票明细
			Map<String, SlOrderTickettypeDetail> slOrderTickettypeDetailMap = new HashMap<String, SlOrderTickettypeDetail>();

			// 保存销售单
			SlOrder slOrder = new SlOrder();
			BeanUtils.copyRPCProperties(sl_order, slOrder);
			slOrder.setPayStat("1");// 支付状态(1-待支付 2-已支付 3-支付失败).

			// 保存销售单-票据明细
			if (sl_order.getSlOrderDetaillist() == null && sl_order.getSlOrderDetaillist().size() == 0) {
				throw new RPCException(4, "销售单-票据明细列表为空");
			}
			for (SL_ORDER_DETAIL sl_order_detail : sl_order.getSlOrderDetaillist()) {
				SlOrderDetail slOrderDetail = new SlOrderDetail();
				BeanUtils.copyRPCProperties(sl_order_detail, slOrderDetail);
				slOrderDetail.setCheckFlag("N"); // CHECK_FLAG 是否检票(Y是N否)
				slOrderDetail.setUselessFlag("N"); // USELESS_FLAG 是否作废(Y是N否)
				slOrderDetail.setValidStartDate(new Date());
				slOrderDetail.setValidEndDate(DateUtil.formatStringToDate("9999-12-31", "yyyy-MM-dd"));

				slOrderDetailList.add(slOrderDetail);

				// 缓存每个票种的售票汇总信息
				if (slOrderTickettypeDetailMap.containsKey(sl_order_detail.getTicketTypeId())) {
					// 每个票种的售票明细
					SlOrderTickettypeDetail slOrderTickettypeDetail = slOrderTickettypeDetailMap.get(sl_order_detail.getTicketTypeId());
					slOrderTickettypeDetail.setTicketCount(slOrderTickettypeDetail.getTicketCount() + 1);
					slOrderTickettypeDetail.setEjectTicketCount(0L);
					slOrderTickettypeDetailMap.put(sl_order_detail.getTicketTypeId(), slOrderTickettypeDetail);
				} else {
					// 每个票种的售票明细
					SlOrderTickettypeDetail slOrderTickettypeDetail = new SlOrderTickettypeDetail();
					slOrderTickettypeDetail.setId(new SlOrderTickettypeDetailId(sl_order.getOrderId(), sl_order_detail.getTicketTypeId()));
					slOrderTickettypeDetail.setTicketCount(1L);
					slOrderTickettypeDetail.setEjectTicketCount(0L);
					slOrderTickettypeDetailMap.put(sl_order_detail.getTicketTypeId(), slOrderTickettypeDetail);
				}

				// 保存门票场馆明细表
				List<SysTicketTypeVenue> sysTicketTypeVenueList = dbUtil.queryListToBean("查询票种场馆表", "SELECT * FROM SYS_TICKET_TYPE_VENUE WHERE TICKET_TYPE_ID=?", SysTicketTypeVenue.class, slOrderDetail.getTicketTypeId());
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
			} else {
				throw new RPCException(4, "支付方式错误");
			}

			slOrderTickettypeDetailList.addAll(slOrderTickettypeDetailMap.values());

			dbUtil.saveOrUpdateEntity("批量保存订单", session, slOrder);
			dbUtil.saveOrUpdateEntityBatch("批量保存订单明细", session, slOrderDetailList);
			dbUtil.saveOrUpdateEntityBatch("批量保存支付明细", session, slPayTypeList);
			dbUtil.saveOrUpdateEntityBatch("批量保存销售单票种明细表", session, slOrderTickettypeDetailList);
			dbUtil.saveOrUpdateEntityBatch("批量保存销售单门票场馆明细表", session, slOrderVenueDetailList);

			dbUtil.commit(session);

			// 开启事务
			session = dbUtil.getSessionByTransaction();

			dbUtil.queryFirstListToBeanLock("查询订单", session, "FROM SlOrder U where orderId=?", "U", SlOrder.class, slOrder.getOrderId());

			// 调用第三支付接口
			OrderPayRequest orderPayRequest = new OrderPayRequest();
			orderPayRequest.setMch_id(SwiftpassConfig.mch_id);
			orderPayRequest.setDevice_info(StringUtil.convertString(auth.getClientId()));
			orderPayRequest.setMch_create_ip(CommonUtil.getHostIP());
			orderPayRequest.setOut_trade_no(slOrder.getOrderId());
			orderPayRequest.setBody("园博园门票");
			orderPayRequest.setAuth_code(slPayTypeList.get(0).getPaymentCode());
			orderPayRequest.setTotal_fee(slPayTypeList.get(0).getAmt().intValue());
			int payStat = payUtil.orderPay(session, orderPayRequest);

			slOrder.setPayStat(StringUtil.convertString(payStat));

			dbUtil.saveOrUpdateEntity("保存订单", session, slOrder);

			// 保存成功
			dbUtil.commit(session);

			if (payStat != 2) {
				throw new RPCException(3, "支付失败");
			}

			return true;

		} catch (ParseException e) {
			logger.error("错误", e);
			throw new RPCException(4, "日期格式化错误");
		} finally {
			dbUtil.close(session);
		}
	}

	@Override
	@ControlAspect(funtionName = "身份证换票查询 (ZQ-自助取票)", operType = OperType.QUERY)
	public List<SL_ORDER_DETAIL> queryTicketByIdenttyId(AUTHORIZATION auth, String identty_id) throws RPCException, TException {
		List<SL_ORDER_DETAIL> sl_order_detail_list = new ArrayList<SL_ORDER_DETAIL>();
		// 订单和票据信息列表
		StringBuffer sql = new StringBuffer();
		// EJECT_TICKET_STAT 出票状态(1-待出票 2-已出票) TICKET_CLASS
		// 门票类型(1-FRID、2-身份证、3-二维码)
		sql.append(" SELECT A.* FROM SL_ORDER_DETAIL A,SL_ORDER B WHERE A.ORDER_ID=B.ORDER_ID AND B.PAY_STAT='2' AND A.TICKET_CLASS='2' AND CHECK_FLAG='N' AND USELESS_FLAG='N' AND EJECT_TICKET_STAT='1' and IDENTTY_ID=? ");
		List<SlOrderDetail> slOrderDetailList = dbUtil.queryListToBean("", sql.toString(), SlOrderDetail.class, identty_id);
		if (slOrderDetailList == null) {
			return sl_order_detail_list;
		}

		for (SlOrderDetail slOrderDetail : slOrderDetailList) {
			SL_ORDER_DETAIL sl_order_detail = new SL_ORDER_DETAIL();
			BeanUtils.copyRPCProperties(slOrderDetail, sl_order_detail);
			sl_order_detail_list.add(sl_order_detail);
		}

		return sl_order_detail_list;
	}

	@Override
	@ControlAspect(funtionName = "查询售票单的支付状态", operType = OperType.QUERY)
	public int querTicketPayStatus(AUTHORIZATION auth, String order_id) throws RPCException, TException {
		Session session = null;

		try {
			Long outletId = ClientAuthCache.getAuthBean(auth.getClientId()).getSysOutlet().getOutletId();
			String outletType = ClientAuthCache.getAuthBean(auth.getClientId()).getSysClient().getClientType();

			checkOutletInfos(outletId);

			// 开启事务
			session = dbUtil.getSessionByTransaction();

			SlOrder slOrder = dbUtil.queryFirstListToBeanLock("查询订单", session, "FROM SlOrder U where orderId=?", "U", SlOrder.class, order_id);
			if (slOrder == null) {
				// 无此订单
				return 3; // (1-待支付 2-已支付 3-支付失败),
			}

			String orderType = slOrder.getOrderType();

			if (!"1".equals(slOrder.getPayStat())) {
				return CommonUtil.covertInt(slOrder.getPayStat());
			}

			// 调用 查询接口
			QueryOrderRequest queryOrderRequest = new QueryOrderRequest();
			queryOrderRequest.setMch_id(SwiftpassConfig.mch_id);
			queryOrderRequest.setOut_trade_no(order_id);
			int payStat = payUtil.queryOrderPay(session, queryOrderRequest);

			slOrder.setPayStat(StringUtil.convertString(payStat));

			if (payStat == 2 && "1".equals(outletType)) {// 支付成功
				// 更新门票状态
				List<StrTicketInfo> strTicketInfoList = new ArrayList<StrTicketInfo>();
				List<SlOrderDetail> slOrderDetailList = dbUtil.queryListToBean("", "SELECT * FROM SL_ORDER_DETAIL WHERE ORDER_ID=?", SlOrderDetail.class, slOrder.getOrderId());
				for (SlOrderDetail slOrderDetail : slOrderDetailList) {
					StrTicketInfo ticketInfo = dbUtil.findById("查询门票库存", StrTicketInfo.class, slOrderDetail.getTicketId());
					if (ticketInfo != null) {
						ticketInfo.setStat("003");// 状态(000-未核实 001-已核实
													// 003-已销售,004-已作废)
						ticketInfo.setSaleOpeTime(new Date());
						strTicketInfoList.add(ticketInfo);
					}
				}

				// 核减网点库存数量
				List<SlOrderTickettypeDetail> slOrderTickettypeDetailList = dbUtil.queryListToBean("", "SELECT * FROM SL_ORDER_TICKETTYPE_DETAIL WHERE ORDER_ID=?", SlOrderTickettypeDetail.class, slOrder.getOrderId());
				for (SlOrderTickettypeDetail slOrderTickettypeDetail : slOrderTickettypeDetailList) {
					String ticketTypeId = slOrderTickettypeDetail.getId().getTicketTypeId();
					Long ticketNum = CommonUtil.covertLong((slOrderTickettypeDetail.getEjectTicketCount()));
					reduceOutletInfos(session, outletId, ticketTypeId, ticketNum);
				}

				dbUtil.updateEntityBatch("批量更新门票库存信息", session, strTicketInfoList);

				if ("BP".equals(orderType)) {
					SlSupply slSupply = dbUtil.findById("", SlSupply.class, slOrder.getOrderId());
					// 更新门票库存 新票为已销售 旧票为已作废
					StrTicketInfo ticketInfoOld = dbUtil.findById("", StrTicketInfo.class, slSupply.getOldTicketId());
					ticketInfoOld.setStat("004");// 状态(000-未核实 001-已核实
													// 003-已销售,004-已作废)
					dbUtil.updateEntity("更新旧门票库存", session, ticketInfoOld);

					// 更新旧票的售票信息为已作废 如果没有售票信息则不更新
					SlOrderDetail orderDetailOld = dbUtil.queryFirstForBean("", "SELECT * FROM SL_ORDER_DETAIL WHERE TICKET_ID=?", SlOrderDetail.class, ticketInfoOld.getTicketId());
					// 如果没有售票信息则不更新
					if (orderDetailOld != null) {
						orderDetailOld.setUselessFlag("Y");// 是否作废(Y是N否)
						orderDetailOld.setUselessTime(new Date());
						dbUtil.updateEntity("更新旧售票信息", session, orderDetailOld);
					}
				}
			}

			dbUtil.updateEntity("更新支付状态", session, slOrder);
			// 保存成功
			dbUtil.commit(session);

			return payStat;
		} catch (Exception e) {
			logger.debug("错误", e);
			return 1;// (1-待支付 2-已支付 3-支付失败),
		} finally {
			dbUtil.close(session);
		}
	}

	@Override
	@ControlAspect(funtionName = "出票信息上传", operType = OperType.ADD)
	public boolean ejectTicket(AUTHORIZATION auth, List<SL_ORDER_DETAIL> sl_order_detail_list) throws RPCException, TException {
		Session session = null;
		try {
			Long outletId = ClientAuthCache.getAuthBean(auth.getClientId()).getSysOutlet().getOutletId();
			String outletType = ClientAuthCache.getAuthBean(auth.getClientId()).getSysClient().getClientType();
			checkOutletInfos(outletId);

			String saleUserId = "";
			if ("1".equals(outletType)) {// 售票机补票更新出票人
				saleUserId = ClientAuthCache.getAuthBean(auth.getClientId()).getSysUser().getUserId();
			}

			// 开启事务
			session = dbUtil.getSessionByTransaction();

			// 订单和票据信息列表
			List<SlOrderDetail> slOrderDetailList = new ArrayList<SlOrderDetail>();
			List<SlOrderVenueDetail> slOrderVenueDetailList = new ArrayList<SlOrderVenueDetail>();
			List<SlOrderTickettypeDetail> slOrderTickettypeDetailList = new ArrayList<SlOrderTickettypeDetail>();
			List<StrTicketInfo> strTicketInfoList = new ArrayList<StrTicketInfo>();

			Map<String, SlOrderTickettypeDetail> slOrderTickettypeDetailMap = new HashMap<String, SlOrderTickettypeDetail>();// 缓存每个票种的售票明细

			if (sl_order_detail_list == null || sl_order_detail_list.size() == 0) {
				throw new RPCException(4, "出票明细记录为空");
			}

			for (SL_ORDER_DETAIL sl_order_detail : sl_order_detail_list) {
				// 校验销售单是否存在
				SlOrder slOrder = dbUtil.findById("查询订单", SlOrder.class, sl_order_detail.getOrderId());
				if (slOrder == null) {
					throw new RPCException(4, "销售单不存在");
				}

				SlOrderDetail slOrderDetail = null;
				slOrderDetail = dbUtil.findById("查询订单明细", SlOrderDetail.class, sl_order_detail.getOrderDetailId());

				SlOrderDetail slOrderDetailCheck = dbUtil.queryFirstForBean("判断是否重复上传", "SELECT * FROM SL_ORDER_DETAIL WHERE TICKET_ID=?", SlOrderDetail.class, sl_order_detail.getTicketId());
				if (slOrderDetailCheck != null) {
					continue;
				}

				if (slOrderDetail == null) {// 购票
					if ("1".equals(sl_order_detail.getTicketClass())) {
						slOrderDetail = new SlOrderDetail();
						BeanUtils.copyRPCProperties(sl_order_detail, slOrderDetail);
						slOrderDetail.setEjectUserId(saleUserId);

						StrTicketInfo ticketInfo = dbUtil.findById("查询门票库存", StrTicketInfo.class, slOrderDetail.getTicketId());
						if (ticketInfo != null) {
							ticketInfo.setStat("003");// 状态(000-未核实 001-已核实
														// 003-已销售,004-已作废)
							ticketInfo.setSaleUserId(saleUserId);
							ticketInfo.setSaleOpeTime(new Date());
							strTicketInfoList.add(ticketInfo);

							slOrderDetail.setTicketUid(ticketInfo.getTicketUid());
						}

					} else {
						throw new RPCException(4, "购票时门票类型错误");
					}

					// 保存门票场馆明细表
					List<SysTicketTypeVenue> sysTicketTypeVenueList = dbUtil.queryListToBean("查询票种场馆表", "SELECT * FROM SYS_TICKET_TYPE_VENUE WHERE TICKET_TYPE_ID=?", SysTicketTypeVenue.class, slOrderDetail.getTicketTypeId());
					for (SysTicketTypeVenue sysTicketTypeVenue : sysTicketTypeVenueList) {
						SlOrderVenueDetail slOrderVenueDetail = new SlOrderVenueDetail();
						slOrderVenueDetail.setId(new SlOrderVenueDetailId(slOrderDetail.getOrderDetailId(), sysTicketTypeVenue.getId().getVenueId()));
						slOrderVenueDetail.setRemainTimes(slOrderDetail.getValidateTimes());
						slOrderVenueDetailList.add(slOrderVenueDetail);
					}
				} else {// 取票
					// 门票类型(1-FRID、2-身份证、3-二维码)
					// 只允许 2-身份证、3-二维码 取票
					if ("2".equals(sl_order_detail.getTicketClass()) || "3".equals(sl_order_detail.getTicketClass())) {
						slOrderDetail.setTicketId(sl_order_detail.getTicketId());
						slOrderDetail.setOutletId(outletId);
						slOrderDetail.setClientId(sl_order_detail.getClientId());
						slOrderDetail.setEjectUserId(saleUserId);
						slOrderDetail.setEjectTicketStat(sl_order_detail.getEjectTicketStat());
						slOrderDetail.setEjectTicketTime(new Date(sl_order_detail.getEjectTicketTime()));
					} else {
						throw new RPCException(4, "取票时门票类型错误");
					}
				}

				slOrderDetail.setCheckFlag("N"); // CHECK_FLAG 是否检票(Y是N否)
				slOrderDetail.setUselessFlag("N"); // USELESS_FLAG 是否作废(Y是N否)

				slOrderDetailList.add(slOrderDetail);

				// 缓存每个订单每个票种的出票汇总信息
				String ticketTypeDetailKey = sl_order_detail.getOrderId() + "-" + sl_order_detail.getTicketTypeId();
				if (slOrderTickettypeDetailMap.containsKey(ticketTypeDetailKey)) {
					// 每个票种的出票明细
					SlOrderTickettypeDetail slOrderTickettypeDetail = slOrderTickettypeDetailMap.get(ticketTypeDetailKey);
					slOrderTickettypeDetail.setEjectTicketCount(slOrderTickettypeDetail.getEjectTicketCount() + 1);
					slOrderTickettypeDetailMap.put(ticketTypeDetailKey, slOrderTickettypeDetail);
				} else {
					// 每个票种的出票明细
					SlOrderTickettypeDetail slOrderTickettypeDetail = new SlOrderTickettypeDetail();
					slOrderTickettypeDetail.setId(new SlOrderTickettypeDetailId(sl_order_detail.getOrderId(), sl_order_detail.getTicketTypeId()));
					slOrderTickettypeDetail.setEjectTicketCount(1L);
					slOrderTickettypeDetailMap.put(ticketTypeDetailKey, slOrderTickettypeDetail);
				}

			}

			// 更新出票数量
			for (SlOrderTickettypeDetail slOrderTickettypeDetail : slOrderTickettypeDetailMap.values()) {
				SlOrderTickettypeDetail slOrderTickettypeDetailDB = dbUtil.findById("查询销售单票种明细表", SlOrderTickettypeDetail.class, slOrderTickettypeDetail.getId());
				slOrderTickettypeDetailDB.setEjectTicketCount(CommonUtil.covertLong(slOrderTickettypeDetailDB.getEjectTicketCount()) + slOrderTickettypeDetail.getEjectTicketCount());
				slOrderTickettypeDetailList.add(slOrderTickettypeDetailDB);

				// 核减网点库存数量
				String ticketTypeId = slOrderTickettypeDetail.getId().getTicketTypeId();
				Long ticketNum = CommonUtil.covertLong((slOrderTickettypeDetail.getEjectTicketCount()));
				reduceOutletInfos(session, outletId, ticketTypeId, ticketNum);
			}

			dbUtil.saveOrUpdateEntityBatch("批量保存订单明细", session, slOrderDetailList);
			dbUtil.saveOrUpdateEntityBatch("批量保存销售单门票场馆明细表", session, slOrderVenueDetailList);
			dbUtil.updateEntityBatch("更新销售单票种明细表", session, slOrderTickettypeDetailList);
			dbUtil.updateEntityBatch("批量更新门票库存信息", session, strTicketInfoList);

			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}

		return true;
	}

	@Override
	@ControlAspect(funtionName = "订单撤消交易", operType = OperType.ADD)
	public boolean cancelTicketPay(AUTHORIZATION auth, String order_id) throws TException {
		Session session = null;
		try {
			Long outletId = ClientAuthCache.getAuthBean(auth.getClientId()).getSysOutlet().getOutletId();
			String outletType = ClientAuthCache.getAuthBean(auth.getClientId()).getSysClient().getClientType();

			// 开启事务
			session = dbUtil.getSessionByTransaction();

			// 订单撤消交易,支付超时或未知状态,时调用
			SlOrder slOrder = dbUtil.queryFirstListToBeanLock("查询订单", session, "FROM SlOrder U where orderId=?", "U", SlOrder.class, order_id);
			if (slOrder == null) {
				throw new RPCException(3, "销售单不存在");
			}

			String orderType = slOrder.getOrderType();
			String payStat = slOrder.getPayStat();// 订单状态

			if ("3".equals(slOrder.getPayStat())) {
				return true;
			}

			// 撤消成功，更新支付为3-支付失败
			slOrder.setPayStat("3");
			dbUtil.updateEntity("更新支付状态", session, slOrder);

			if ("2".equals(payStat) && "1".equals(outletType)) {// 支付成功后撤消成功
				// 还原更新门票状态
				List<StrTicketInfo> strTicketInfoList = new ArrayList<StrTicketInfo>();
				List<SlOrderDetail> slOrderDetailList = dbUtil.queryListToBean("", "SELECT * FROM SL_ORDER_DETAIL WHERE ORDER_ID=?", SlOrderDetail.class, slOrder.getOrderId());
				for (SlOrderDetail slOrderDetail : slOrderDetailList) {
					StrTicketInfo ticketInfo = dbUtil.findById("查询门票库存", StrTicketInfo.class, slOrderDetail.getTicketId());
					if (ticketInfo != null) {
						ticketInfo.setStat("006");// 状态(000-未核实 001-已核实
													// 003-已销售,004-已作废)
						ticketInfo.setSaleOpeTime(new Date());
						strTicketInfoList.add(ticketInfo);
					}

				}

				// 核减网点库存数量
				List<SlOrderTickettypeDetail> slOrderTickettypeDetailList = dbUtil.queryListToBean("", "SELECT * FROM SL_ORDER_TICKETTYPE_DETAIL WHERE ORDER_ID=?", SlOrderTickettypeDetail.class, slOrder.getOrderId());
				for (SlOrderTickettypeDetail slOrderTickettypeDetail : slOrderTickettypeDetailList) {
					String ticketTypeId = slOrderTickettypeDetail.getId().getTicketTypeId();
					Long ticketNum = CommonUtil.covertLong((slOrderTickettypeDetail.getEjectTicketCount()));
					addOutletInfos(session, outletId, ticketTypeId, ticketNum);
				}

				dbUtil.updateEntityBatch("批量更新门票库存信息", session, strTicketInfoList);

				if ("BP".equals(orderType)) {
					SlSupply slSupply = dbUtil.findById("", SlSupply.class, slOrder.getOrderId());
					// 更新门票库存 新票为旧票为已销售
					StrTicketInfo ticketInfoOld = dbUtil.findById("", StrTicketInfo.class, slSupply.getOldTicketId());
					ticketInfoOld.setStat("003");// 状态(000-未核实 001-已核实
													// 003-已销售,004-已作废)
					dbUtil.updateEntity("更新旧门票库存", session, ticketInfoOld);

					// 更新旧票的售票信息为已作废 如果没有售票信息则不更新
					SlOrderDetail orderDetailOld = dbUtil.queryFirstForBean("", "SELECT * FROM SL_ORDER_DETAIL WHERE TICKET_ID=?", SlOrderDetail.class, ticketInfoOld.getTicketId());
					// 如果没有售票信息则不更新
					if (orderDetailOld != null) {
						orderDetailOld.setUselessFlag("N");// 是否作废(Y是N否)
						orderDetailOld.setUselessTime(new Date());
						dbUtil.updateEntity("更新旧售票信息", session, orderDetailOld);
					}
				}

				// 还原补票表、废票表
				if ("BP".equals(orderType)) {
					SlSupply slSupply = dbUtil.findById("", SlSupply.class, slOrder.getOrderId());
					if (slSupply != null) {
						dbUtil.deleteEntity("", session, SlSupply.class, slSupply.getSupplyId());
					}

					SlUselessTicketInfo slUselessTicketInfo = dbUtil.findById("", SlUselessTicketInfo.class, slOrder.getOrderId());
					if (slUselessTicketInfo != null) {
						dbUtil.deleteEntity("", session, SlUselessTicketInfo.class, slUselessTicketInfo.getUselessUserId());
					}
				}
			}

			// 保存成功
			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}

		CancelOrderRequest cancelOrderRequest = new CancelOrderRequest();
		cancelOrderRequest.setMch_id(SwiftpassConfig.mch_id);
		cancelOrderRequest.setOut_trade_no(order_id);

		// 撤消失败时
		for (int i = 0; i < 3; i++) {
			try {
				payUtil.cancelOrderPay(cancelOrderRequest);
				break;
			} catch (Exception e) {
				logger.error("撤消失败", e);
				logger.error(String.format("再次撤消[%i]次", i));
			}
		}

		return true;
	}

	@Override
	@ControlAspect(funtionName = "团队换票明细查询", operType = OperType.QUERY)
	public List<SL_TEAM_ORDER> queryTeamOrderDetail(AUTHORIZATION auth, long change_time, String apply_id) throws RPCException, TException {
		List<SL_TEAM_ORDER> teamOrderList = new ArrayList<SL_TEAM_ORDER>();
		StringBuffer sql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		sql.append("SELECT * FROM SL_TEAM_ORDER  WHERE 1=1");
		if (change_time != 0) {
			sql.append(" AND TRUNC(CHANGE_TIME,'DD') =:CHANGE_TIME ");
			params.put("CHANGE_TIME", new Date(change_time));
		}
		if (StringUtil.isNotNull(apply_id)) {
			sql.append(" AND APPLY_ID =:APPLY_ID ");
			params.put("APPLY_ID", apply_id);
		}
		List<SlTeamOrder> teamOrders = dbUtil.queryListToBean("", sql.toString(), SlTeamOrder.class, params);
		for (SlTeamOrder teamOrder : teamOrders) {
			SL_TEAM_ORDER team_order = new SL_TEAM_ORDER();
			BeanUtils.copyRPCProperties(teamOrder, team_order);
			SlChangeUser changeUser = dbUtil.findById("查询换票人信息", SlChangeUser.class, teamOrder.getChangeUserId());
			if (changeUser == null) {
				throw new RPCException(6000102, MsgUtil.getMsg(6000102));
			}
			team_order.setCardType(changeUser.getCardType());
			team_order.setCardId(changeUser.getCardId());
			team_order.setTel(changeUser.getTel());
			// 设置证件类型名称
			Map<String, SysDictionary> sysDictionaryMap = dbUtil.queryMapForBean("查询字典", "SELECT * FROM SYS_DICTIONARY WHERE KEY_CD='ID_CARD_TYPE'", SysDictionary.class, "itemCd");
			SysDictionary sysDictionary = sysDictionaryMap.get(changeUser.getCardType());
			if (sysDictionary != null) {
				team_order.setCardTypeName(sysDictionary.getItemName());
			} else {
				team_order.setCardTypeName("");
			}

			// 设置审核人名称
			if (StringUtil.isNotNull(teamOrder.getExamUserId())) {
				SysUser examSysUser = dbUtil.findById("查询用户", SysUser.class, teamOrder.getExamUserId());
				if (examSysUser != null) {
					team_order.setExamUserName(examSysUser.getUserName());
				} else {
					team_order.setExamUserName(teamOrder.getExamUserId());
				}
			}
			List<SL_TEAM_ORDER_DETAIL> team_order_details = new ArrayList<SL_TEAM_ORDER_DETAIL>();
			for (SlTeamOrderDetail teamOrderDetail : teamOrder.getSlTeamOrderDetails()) {
				SL_TEAM_ORDER_DETAIL team_order_detail = new SL_TEAM_ORDER_DETAIL();
				BeanUtils.copyRPCProperties(teamOrderDetail, team_order_detail);
				team_order_detail.setApplyId(teamOrderDetail.getSlTeamOrder().getApplyId());
				team_order_details.add(team_order_detail);
			}
			team_order.setTeamOrderDetail(team_order_details);
			teamOrderList.add(team_order);
		}
		return teamOrderList;
	}

	@Override
	@ControlAspect(funtionName = "废票明细查询", operType = OperType.QUERY)
	public List<USELESS_TICKET_DETAIL> queryUselessTicket(AUTHORIZATION auth, long outlet_id) throws RPCException, TException {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("OUTLET_ID", outlet_id);
		sql.append(" SELECT SL.TICKET_ID, ");
		sql.append(" SL.USELESS_TIME,SL.USELESS_REASON, ");
		sql.append(" SL.TICKET_TYPE_ID,TY.TICKET_TYPE_NAME,");
		sql.append(" SL.OUTLET_ID,O.OUTLET_NAME, ");
		sql.append(" SL.USELESS_USER_ID,U.USER_NAME USELESS_USER_NAME ");
		sql.append(" FROM SL_USELESS_TICKET_INFO SL ");
		sql.append(" LEFT JOIN SYS_OUTLET O ");
		sql.append(" ON O.OUTLET_ID=SL.OUTLET_ID ");
		sql.append(" LEFT JOIN SYS_TICKET_TYPE TY ");
		sql.append(" ON TY.TICKET_TYPE_ID=SL.TICKET_TYPE_ID ");
		sql.append(" LEFT JOIN SYS_USER U ");
		sql.append(" ON U.USER_ID=SL.USELESS_USER_ID ");
		sql.append(" WHERE SL.OUTLET_ID=:OUTLET_ID ");
		sql.append(" AND SL.USELESS_TIME IS NOT NULL ");
		List<UselessTicketDetailBean> ticketDetail = dbUtil.queryListToBean("查询废票明细", sql.toString(), UselessTicketDetailBean.class, params);
		List<USELESS_TICKET_DETAIL> uselessTicketList = new ArrayList<USELESS_TICKET_DETAIL>();
		for (UselessTicketDetailBean bean : ticketDetail) {
			USELESS_TICKET_DETAIL useless_ticket_detail = new USELESS_TICKET_DETAIL();
			BeanUtils.copyRPCProperties(bean, useless_ticket_detail);
			uselessTicketList.add(useless_ticket_detail);
		}
		return uselessTicketList;
	}

	@Override
	@ControlAspect(funtionName = "售票机补票查询（购票）", operType = OperType.QUERY)
	public List<SL_ORDER_TICKETTYPE_DETAIL> querySaleSupplyTicketG(AUTHORIZATION auth, String pay_id) throws RPCException, TException {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		params.put("BANK_ID", pay_id);// 银行单号
		sql.append("	SELECT T.ORDER_ID,                          ");
		sql.append("           T.TICKET_TYPE_ID,                    ");
		sql.append("           T.TICKET_COUNT,                      ");
		sql.append("           T.EJECT_TICKET_COUNT                 ");
		sql.append("   	FROM SL_PAY_TYPE P                          ");
		sql.append("    INNER JOIN SL_ORDER O                       ");
		sql.append("    ON O.ORDER_ID = P.ORDER_ID                  ");
		sql.append("    INNER JOIN SL_ORDER_TICKETTYPE_DETAIL  T    ");
		sql.append("    ON T.ORDER_ID = P.ORDER_ID                  ");
		sql.append("    WHERE O.PAY_STAT = '2' AND O.ORDER_STAT = '0'                     ");
		sql.append("    AND P.BANK_ID=:BANK_ID						");

		List<SlOrderTicketTypeDetailBean> saleSupplyList = dbUtil.queryListToBean("售票机补票查询（购票）", sql.toString(), SlOrderTicketTypeDetailBean.class, params);
		List<SL_ORDER_TICKETTYPE_DETAIL> saleSupplyTicket = new ArrayList<SL_ORDER_TICKETTYPE_DETAIL>();
		for (SlOrderTicketTypeDetailBean saleSupply : saleSupplyList) {
			SL_ORDER_TICKETTYPE_DETAIL sale_supply_ticket = new SL_ORDER_TICKETTYPE_DETAIL();
			BeanUtils.copyRPCProperties(saleSupply, sale_supply_ticket);
			saleSupplyTicket.add(sale_supply_ticket);
		}
		return saleSupplyTicket;
	}

	@Override
	@ControlAspect(funtionName = "售票机补票查询（取票）", operType = OperType.QUERY)
	public List<SL_ORDER_DETAIL> querySaleSupplyTicketQ(AUTHORIZATION auth, String pay_id) throws RPCException, TException {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		params.put("BANK_ID", pay_id);// 银行单号
		sql.append("	SELECT D.ORDER_DETAIL_ID,            ");
		sql.append("		   D.ORDER_ID,                   ");
		sql.append("	       D.TICKET_CLASS,               ");
		sql.append("	       D.TICKET_ID,                  ");
		sql.append("	       D.TICKET_UID,                 ");
		sql.append("	       D.IDENTTY_ID,                 ");
		sql.append("	       D.TICKET_TYPE_ID,             ");
		sql.append("	       D.VALIDATE_TIMES,             ");
		sql.append("	       D.ORIGINAL_PRICE,             ");
		sql.append("	       D.SALE_PRICE,                 ");
		sql.append("	       D.CHECK_FLAG,                 ");
		sql.append("	       D.USELESS_FLAG,               ");
		sql.append("	       D.VERSION_NO,                 ");
		sql.append("	       D.OUTLET_ID,                  ");
		sql.append("	       D.CLIENT_ID,                  ");
		sql.append("	       D.EJECT_USER_ID,              ");
		sql.append("	       D.EJECT_TICKET_STAT,          ");
		sql.append("	       D.EJECT_TICKET_TIME,          ");
		sql.append("	       D.USELESS_TIME,               ");
		sql.append("	       D.VALID_START_DATE,           ");
		sql.append("	       D.VALID_END_DATE              ");
		sql.append("	  FROM SL_PAY_TYPE P                 ");
		sql.append("	 INNER JOIN SL_ORDER_DETAIL D        ");
		sql.append("	    ON P.ORDER_ID = D.ORDER_ID       ");
		sql.append("    WHERE D.EJECT_TICKET_STAT = '1'      ");// 出票状态：待出票
		sql.append(" 	 AND P.BANK_ID=:BANK_ID			 ");

		List<SlOrderDetail> detailList = dbUtil.queryListToBean("查询售票机取票明细", sql.toString(), SlOrderDetail.class, params);
		List<SL_ORDER_DETAIL> supplyDetailList = new ArrayList<SL_ORDER_DETAIL>();
		for (SlOrderDetail detailBean : detailList) {
			SL_ORDER_DETAIL sale_supply_ticket_detail = new SL_ORDER_DETAIL();
			BeanUtils.copyRPCProperties(detailBean, sale_supply_ticket_detail);
			supplyDetailList.add(sale_supply_ticket_detail);
		}
		return supplyDetailList;
	}

	@Override
	@ControlAspect(funtionName = "身份证检票记录列表", operType = OperType.QUERY)
	public List<QRY_IDENTTY_CHECK_INFO> queryCheckInfoByIdentty(AUTHORIZATION auth, String identty_id) throws RPCException, TException {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT SC.CHECK_ID,                           ");
		sql.append("        SC.TICKET_UID,                         ");
		sql.append("        to_char(SC.VENUE_ID) VENUE_ID,                           ");
		sql.append("        SV.VENUE_NAME,                         ");
		sql.append("        to_char(SC.CLIENT_ID) CLIENT_ID,                          ");
		sql.append("        SCT.CLIENT_NAME,                       ");
		sql.append("     CASE WHEN SC.PASS_FLAG='Y' THEN '通过' ELSE '未通过' END PASS_FLAG, ");
		sql.append("        SC.NOPASS_REASON,                      ");
		sql.append("        to_char(SC.REMAIN_TIMES) REMAIN_TIMES,                       ");
		sql.append("        SC.TICKET_TYPE_ID,                     ");
		sql.append("        STT.TICKET_TYPE_NAME,                  ");
		sql.append("      to_char(SC.OPE_TIME, 'YYYY-MM-DD HH24:MI:SS') OPE_TIME  ");
		sql.append("   FROM SL_CHECK SC                            ");
		sql.append("   LEFT JOIN SYS_VENUE SV                      ");
		sql.append("     ON SC.VENUE_ID = SV.VENUE_ID              ");
		sql.append("   LEFT JOIN SYS_CLIENT SCT                    ");
		sql.append("     ON SC.CLIENT_ID = SCT.CLIENT_ID           ");
		sql.append("   LEFT JOIN SYS_TICKET_TYPE STT               ");
		sql.append("     ON SC.TICKET_TYPE_ID = STT.TICKET_TYPE_ID ");
		sql.append("    WHERE SC.TICKET_UID=?");
		sql.append("     ORDER BY SC.OPE_TIME DESC                 ");

		return dbUtil.queryListToBean("", sql.toString(), QRY_IDENTTY_CHECK_INFO.class, identty_id);
	}

	@Override
	@ControlAspect(funtionName = "身份证售票记录列表", operType = OperType.QUERY)
	public List<QRY_IDENTTY_SALE_INFO> querySaleInfoByIdentty(AUTHORIZATION auth, String identty_id) throws RPCException, TException {
		StringBuilder sql = new StringBuilder();
		sql.append(" select sod.order_detail_id,                                    ");
		sql.append("        sod.identty_id,                                         ");
		sql.append("        sod.ticket_type_id,                                     ");
		sql.append("        stt.ticket_type_name,                                   ");
		sql.append("        to_char(so.sale_time,'YYYY-MM-DD HH24:MI:SS') sale_time, ");
		sql.append("        case                                                    ");
		sql.append("          when sod.check_flag = 'Y' then                        ");
		sql.append("           '已检票'                                             ");
		sql.append("          else                                                  ");
		sql.append("           '未检票'                                             ");
		sql.append("        end check_flag,                                         ");
		sql.append("        nvl(to_char(sc.ope_time,'YYYY-MM-DD HH24:MI:SS'),'') check_Ticket_Time,                                         ");
		sql.append("        case                                                    ");
		sql.append("          when spt.pay_type = '01' then                         ");
		sql.append("           '现金'                                               ");
		sql.append("          when spt.pay_type = '02' then                         ");
		sql.append("           'POS付款'                                            ");
		sql.append("          when spt.pay_type = '03' then                         ");
		sql.append("           '微信'                                               ");
		sql.append("          when spt.pay_type = '04' then                         ");
		sql.append("           '支付宝'                                             ");
		sql.append("          when spt.pay_type = '05' then                         ");
		sql.append("           '代理'                                               ");
		sql.append("        end pay_type,                                           ");
		sql.append("        to_char(sod.valid_start_date,'YYYY-MM-DD') valid_start_date, ");
		sql.append("        to_char(sod.valid_end_date,'YYYY-MM-DD') valid_end_date, ");
		sql.append("        case                                                    ");
		sql.append("          when sod.eject_ticket_stat = '2' then                 ");
		sql.append("           '已取票'                                             ");
		sql.append("          else                                                  ");
		sql.append("           '未取票'                                             ");
		sql.append("        end eject_ticket_stat,                                  ");
		sql.append("        to_char(sod.eject_ticket_time, 'YYYY-MM-DD HH24:MI:SS') eject_ticket_time");
		sql.append("   from sl_order_detail sod                                     ");
		sql.append("   left join sys_ticket_type stt                                ");
		sql.append("     on sod.ticket_type_id = stt.ticket_type_id                 ");
		sql.append("   left join sl_order so                                        ");
		sql.append("     on sod.order_id = so.order_id                              ");
		sql.append("   left join sl_pay_type spt                                    ");
		sql.append("     on so.order_id = spt.order_id                              ");
		sql.append("   left join (                                   ");
		sql.append("         select a.order_detail_id,max(b.ope_time) ope_time from sl_order_detail a,sl_check b where ");
		sql.append("          a.order_detail_id=b.order_detail_id and a.identty_id=? group by a.order_detail_id ");
		sql.append("   ) sc    				                                        ");
		sql.append("     on sc.order_detail_id = sod.order_detail_id                ");
		sql.append("    WHERE sod.identty_id=? and sod.USELESS_FLAG='N' ");
		sql.append("  order by so.sale_time desc                                    ");

		return dbUtil.queryListToBean("", sql.toString(), QRY_IDENTTY_SALE_INFO.class, identty_id,identty_id);
	}

}
