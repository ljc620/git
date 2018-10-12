package com.webservice.saleTicket;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;
//获取客户端IP引入以下4个包
//import javax.servlet.http.HttpServletRequest;
//import javax.xml.ws.WebServiceContext;
//import javax.xml.ws.handler.MessageContext;
//import org.apache.cxf.jaxws.context.WebServiceContextImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;
import org.hibernate.Session;

import com.alibaba.fastjson.JSONArray;
import com.tbims.entity.SlNetagentOrder;
import com.tbims.entity.SlOrder;
import com.tbims.entity.SlOrderDetail;
import com.tbims.entity.SlOrderTickettypeDetail;
import com.tbims.entity.SlOrderTickettypeDetailId;
import com.tbims.entity.SlOrderVenueDetail;
import com.tbims.entity.SlOrderVenueDetailId;
import com.tbims.entity.SlOrg;
import com.tbims.entity.SlOrgSaleinfo;
import com.tbims.entity.SlOrgTickettype;
import com.tbims.entity.SlPayType;
import com.tbims.entity.SlRefundTicket;
import com.tbims.entity.SlRefundTicketDetail;
import com.tbims.entity.SysTicketType;
import com.tbims.entity.SysTicketTypePrice;
import com.tbims.entity.SysTicketTypeVenue;
import com.webservice.CXFException;
import com.webservice.WSUtil;
import com.webservice.entity.OrderDetail;
import com.webservice.entity.RefundTicket;
import com.webservice.entity.TicketStatus;
import com.webservice.entity.TicketType;
import com.zhming.support.cache.OrgAuthCache;
import com.zhming.support.common.MSG;
import com.zhming.support.common.OperType;
import com.zhming.support.db.DBUtil;
import com.zhming.support.db.impl.DBUtilImpl;
import com.zhming.support.init.SpringContextUtil;
import com.zhming.support.util.CommonUtil;
import com.zhming.support.util.MsgUtil;
import com.zhming.support.util.StringUtil;
import com.zhming.support.util.UUIDGenerator;

/**
 * Title: 售票相关接口 <br/>
 * Description:
 * 
 * @ClassName: ISaleTicket
 * @author ydc
 * @date 2017年7月25日 上午10:48:39
 * 
 */
@WebService(endpointInterface = "com.webservice.saleTicket.ISaleTicket")
public class SaleTicketImpl implements ISaleTicket {
	private final Log logger = LogFactory.getLog(getClass());
	private static final String SERVICE_NAME = "售票服务";
	

	@Override
	public TicketType[] getTicketType(String orgID, String token, String transq) throws CXFException {
		DBUtil dbUtil = SpringContextUtil.getBean(DBUtilImpl.class);
		String logId = UUIDGenerator.getPK();
		String menuId = "票种查询接口";
		int logType = OperType.QUERY;
		String navigation = String.format("[%s][%s]-%s-%s-%s", logId, orgID, SERVICE_NAME, menuId, transq);
		TicketType[] retArray = null;
		try {
			MDC.put("navigation", navigation);
			logger.debug(String.format("输入参数:token=[%s]", token));

			OrgAuthCache.checkOrgToken(orgID, token);

			// 查询票种 票种状态=Y(Y正常N停用) 是否团体=N(Y是N否)
			List<TicketType> ticketTypeList = dbUtil.queryListToBean("查询票种",
					"SELECT t.* FROM SYS_TICKET_TYPE t,SL_ORG_TICKETTYPE s WHERE s.Org_Id=? AND T.TICKET_TYPE_ID=s.TICKET_TYPE_ID AND T.TICKET_TYPE_STAT='Y'", TicketType.class, orgID);
			if (ticketTypeList == null || ticketTypeList.size() == 0) {
				return new TicketType[0];
			}
			TicketType[] ticketTypeArray = new TicketType[ticketTypeList.size()];
			retArray = ticketTypeList.toArray(ticketTypeArray);

		} catch (CXFException e) {
			logger.error(e);
			WSUtil.saveLog(logId, orgID, logType, menuId, e.getErrcode(), e.getErrinfo(), transq);
			throw e;
		} catch (Exception e) {
			logger.error("错误", e);
			WSUtil.saveLog(logId, orgID, logType, menuId, MSG.ERROR, null, transq);
			throw new CXFException(MSG.ERROR);
		} finally {
			// 清空log4j的mdc信息
			@SuppressWarnings("rawtypes")
			Map map = MDC.getContext();
			if (map != null) {
				map.clear();
			}
		}
		return retArray;
	}

	@Override
	public int getTicketCountByID(String orgID, String token, String identityCode, String transq) throws CXFException {
		DBUtil dbUtil = SpringContextUtil.getBean(DBUtilImpl.class);
		String logId = UUIDGenerator.getPK();
		String menuId = "售票查询接口";
		int logType = OperType.QUERY;
		String navigation = String.format("[%s][%s]-%s-%s-%s", logId, orgID, SERVICE_NAME, menuId, transq);
		try {
			MDC.put("navigation", navigation);
			identityCode = StringUtil.trim(identityCode).toUpperCase();

			logger.debug(String.format("输入参数:token=[%s],identityCode=[%s]", token, identityCode));

			OrgAuthCache.checkOrgToken(orgID, token);

			// 查询{未作废的记录、票剩余次数大于0、未取票的记录、支付成功的记录、未出票的记录}
			StringBuffer sql = new StringBuffer();
			// PAY_STAT 支付状态(1-待支付 2-已支付 3-支付失败) 、EJECT_TICKET_STAT出票状态(1-待出票 2-已出票)
			// 未退票、未作废、未过有效期、剩余次数>0 则认为是有效票
			sql.append("SELECT distinct A.* FROM SL_ORDER_DETAIL A  ");
			sql.append("INNER JOIN SL_ORDER_VENUE_DETAIL B ON A.ORDER_DETAIL_ID=B.ORDER_DETAIL_ID ");
			sql.append("INNER JOIN SL_ORDER C ON A.ORDER_ID=C.ORDER_ID ");
			sql.append("WHERE C.PAY_STAT='2' AND A.USELESS_FLAG='N' ");
			sql.append(" AND ((A.valid_end_date is null and A.Valid_Start_Date is null) ");		//   2018-2-22 增加有效期日期检查 zhuxy
			sql.append("	 or (A.Valid_Start_Date is null and A.Valid_End_Date is not null and trunc(sysdate,'DD')<=A.Valid_End_Date) ");
			sql.append("	 or (A.Valid_Start_Date is not null and A.Valid_End_Date is null and trunc(sysdate,'DD')>=A.Valid_Start_Date) ");
			sql.append("	 or (trunc(sysdate,'DD') between A.valid_start_date and A.valid_end_date))  ");
			sql.append(" AND B.REMAIN_TIMES>0 AND A.EJECT_TICKET_STAT='1' ");
			sql.append(" AND A.IDENTTY_ID=? ");

			List<SlOrderDetail> slOrderDetailList = dbUtil.queryListToBean("按身份证号查询可用售票记录", sql.toString(), SlOrderDetail.class, identityCode);
			if (slOrderDetailList == null) {
				return 0;
			}

			return slOrderDetailList.size();
		} catch (CXFException e) {
			logger.error(e);
			WSUtil.saveLog(logId, orgID, logType, menuId, e.getErrcode(), e.getErrinfo(), transq);
			throw e;
		} catch (Exception e) {
			logger.error("错误", e);
			WSUtil.saveLog(logId, orgID, logType, menuId, MSG.ERROR, null, transq);
			throw new CXFException(MSG.ERROR);
		} finally {
			// 清空log4j的mdc信息
			@SuppressWarnings("rawtypes")
			Map map = MDC.getContext();
			if (map != null) {
				map.clear();
			}
		}
	}

	@Override
	public int saleDateSync(String orgID, String token, String orderID, int ticketCount, long realSum, Date saleTime, OrderDetail[] saleOrderDetail, String payType, String payID, String transq)
			throws CXFException {
		Session session = null;
		DBUtil dbUtil = SpringContextUtil.getBean(DBUtilImpl.class);
		String logId = UUIDGenerator.getPK();
		String menuId = "售票接口";
		int logType = OperType.ADD;
		String navigation = String.format("[%s][%s]-%s-%s-%s", logId, orgID, SERVICE_NAME, menuId, transq);
		try {
			MDC.put("navigation", navigation);

			logger.debug(String.format("输入参数:token=[%s],orderID=[%s],ticketCount=[%d],realSum=[%d],saleTime=[%tc],saleOrderDetail=[%s],payType=[%s],payID=[%s]", token, orderID, ticketCount, realSum,
					saleTime, JSONArray.toJSONString(saleOrderDetail), payType, payID));

			OrgAuthCache.checkOrgToken(orgID, token);

			// 查询订单是否已处理成功
			SlOrder slOrder1 = dbUtil.queryFirstForBean("", "SELECT * FROM SL_ORDER WHERE ORDER_ID=? ", SlOrder.class, orderID);
			if (slOrder1 != null) {
				return 2;// 返回值：1 成功，-1 失败 2-该订单已完成
			}

			// 校验订单票据张数与明细不符
			if (saleOrderDetail == null || saleOrderDetail.length != ticketCount) {
				throw new CXFException(MSG.BF_ERROR, "订单票据张数与明细不符");
			}

			// 开启事务
			session = dbUtil.getSessionByTransaction();

			// 查询所有票种
			Map<String, SysTicketType> sysTicketTypeList = dbUtil.queryMapForBean("", "SELECT * FROM SYS_TICKET_TYPE", SysTicketType.class, "ticketTypeId");
			// 查询机构可销售票种表
			Map<String, SlOrgTickettype> slOrgTickettypeMap = dbUtil.queryMapForBean("查询机构可销售票种", "SELECT * FROM SL_ORG_TICKETTYPE WHERE ORG_ID=?", SlOrgTickettype.class, "id.ticketTypeId", orgID);

			// 生成销售单明细列表
			List<SlOrderDetail> slOrderDetailList = new ArrayList<SlOrderDetail>();
			List<SlOrderVenueDetail> slOrderVenueDetailList = new ArrayList<SlOrderVenueDetail>();
			Map<String, SlOrderTickettypeDetail> slOrderTickettypeDetailMap = new HashMap<String, SlOrderTickettypeDetail>();// 缓存每个票种的售票明细
			List<SlOrderTickettypeDetail> slOrderTickettypeDetailList = new ArrayList<SlOrderTickettypeDetail>();

			// 生成销售单
			SlOrder slOrder = new SlOrder();
			slOrder.setOrderId(orderID);
			slOrder.setOrderType("ZQ");// 销售类型(XC-现场售票、ZY-自营售票、TD-团队换票、WL-网络人工换票、ST-实体代理、BP-补票、ZG-自助购票、ZQ-自助取票)
			slOrder.setTicketCount(CommonUtil.covertLong(ticketCount));
			// slOrder.setDueSum(realSum);
			// slOrder.setRealSum(realSum);
			slOrder.setPayStat("2");// 支付状态(1-待支付 2-已支付 3-支付失败)
			slOrder.setSaleTime(saleTime);
			slOrder.setVersionNo(new Date());

			// 网络代理订单表
			SlNetagentOrder slNetagentOrder = new SlNetagentOrder();
			slNetagentOrder.setOrderId(orderID);
			slNetagentOrder.setThirdOrderNum(orderID);
			slNetagentOrder.setOrgId(orgID);
			slNetagentOrder.setOpeTime(new Date());

			// 生成销售单明细
			long detailAmt = 0;
			Map<String, OrderDetail> identityCodes = new HashMap<String, OrderDetail>();// 缓存订单明细中的身份证号
			for (OrderDetail orderDetail : saleOrderDetail) {
				if (!slOrgTickettypeMap.containsKey(orderDetail.getTicketTypeId())) {
					throw new CXFException(MSG.BF_ERROR, "此机构不允许购买此票种");
				}

				// 校验订单明细中身份证号重复
				if (!identityCodes.containsKey(orderDetail.getIdentityCode())) {
					identityCodes.put(orderDetail.getIdentityCode(), orderDetail);
				} else {
					throw new CXFException(MSG.BF_ERROR, "订单明细中身份证号重复");
				}

				// 查询票种
				SysTicketType sysTicketType = sysTicketTypeList.get(orderDetail.getTicketTypeId());
				if (sysTicketType == null) {
					throw new CXFException(MSG.BF_ERROR, "票种编号不存在");
				}

				SlOrderDetail slOrderDetail = new SlOrderDetail();
				slOrderDetail.setOrderDetailId(UUIDGenerator.getPK());
				slOrderDetail.setOrderId(orderID);
				slOrderDetail.setTicketClass("2");// 门票类型(1-FRID、2-身份证、3-二维码)
				slOrderDetail.setIdenttyId(StringUtil.trim(orderDetail.getIdentityCode()).toUpperCase());
				slOrderDetail.setTicketTypeId(orderDetail.getTicketTypeId());
				slOrderDetail.setValidateTimes(sysTicketType.getValidateTimes());
				// slOrderDetail.setOriginalPrice(sysTicketType.getPrice());
				// slOrderDetail.setSalePrice(orderDetail.getSalePrice());
				slOrderDetail.setCheckFlag("N");// 是否检票(Y是N否)
				slOrderDetail.setUselessFlag("N");// 是否作废(Y是N否)
				slOrderDetail.setEjectTicketStat("1");// 出票状态(1-待出票 2-已出票)
				slOrderDetail.setVersionNo(new Date());
				slOrderDetail.setValidStartDate(orderDetail.getStartDate());
				slOrderDetail.setValidEndDate(orderDetail.getEndDate());

				slOrderDetailList.add(slOrderDetail);

				// 汇总明细金额
				detailAmt += orderDetail.getSalePrice();

				// 保存门票场馆明细表
				List<SysTicketTypeVenue> sysTicketTypeVenueList = dbUtil.queryListToBean("查询票种场馆表", "SELECT * FROM SYS_TICKET_TYPE_VENUE WHERE TICKET_TYPE_ID=?", SysTicketTypeVenue.class,
						slOrderDetail.getTicketTypeId());
				for (SysTicketTypeVenue sysTicketTypeVenue : sysTicketTypeVenueList) {
					SlOrderVenueDetail slOrderVenueDetail = new SlOrderVenueDetail();
					slOrderVenueDetail.setId(new SlOrderVenueDetailId(slOrderDetail.getOrderDetailId(), sysTicketTypeVenue.getId().getVenueId()));
					slOrderVenueDetail.setRemainTimes(slOrderDetail.getValidateTimes());
					slOrderVenueDetailList.add(slOrderVenueDetail);
				}

				// 缓存每个票种的售票汇总信息
				if (slOrderTickettypeDetailMap.containsKey(slOrderDetail.getTicketTypeId())) {
					// 每个票种的售票明细
					SlOrderTickettypeDetail slOrderTickettypeDetail = slOrderTickettypeDetailMap.get(slOrderDetail.getTicketTypeId());
					slOrderTickettypeDetail.setTicketCount(slOrderTickettypeDetail.getTicketCount() + 1);
					slOrderTickettypeDetail.setEjectTicketCount(0L);
					slOrderTickettypeDetailMap.put(slOrderDetail.getTicketTypeId(), slOrderTickettypeDetail);
				} else {
					// 每个票种的售票明细
					SlOrderTickettypeDetail slOrderTickettypeDetail = new SlOrderTickettypeDetail();
					slOrderTickettypeDetail.setId(new SlOrderTickettypeDetailId(orderID, slOrderDetail.getTicketTypeId()));
					slOrderTickettypeDetail.setTicketCount(1L);
					slOrderTickettypeDetail.setEjectTicketCount(0L);
					slOrderTickettypeDetailMap.put(slOrderDetail.getTicketTypeId(), slOrderTickettypeDetail);
				}
			}

			slOrderTickettypeDetailList.addAll(slOrderTickettypeDetailMap.values());

			// 校验订单票据总金额与明细金额
			if (detailAmt != realSum) {
				throw new CXFException(MSG.BF_ERROR, "校验订单票据总金额与明细金额不符");
			}

			// 生成销售单支付明细
			SlPayType slPayType = new SlPayType();
			slPayType.setPayTypeId(UUIDGenerator.getPK());
			slPayType.setOrderId(orderID);
			slPayType.setPayType(payType);
			slPayType.setBankId(payID);// 银行平台单号
			slPayType.setVersionNo(new Date());

			// 冻结预付款
			long frozeAmtTotal = 0;
			SlOrg slOrg = dbUtil.queryFirstForBean("查询机构信息", "SELECT * FROM SL_ORG U WHERE ORG_ID=?", SlOrg.class, orgID);
			if (slOrg == null) {
				throw new CXFException(4, "机构信息不存在");
			}

			Map<String, SlOrgSaleinfo> slOrgSaleinfoMap = dbUtil.queryMapForBean("查询机构售票信息表", "SELECT * FROM SL_ORG_SALEINFO WHERE ORG_ID=?", SlOrgSaleinfo.class, "id.ticketTypeId", slOrg.getOrgId());

			// 缓存每个票种的计算后的阶梯票价
			Map<String, Long> ticketTypePriceMap = new HashMap<String, Long>();
			for (SlOrderTickettypeDetail slOrderTickettypeDetail : slOrderTickettypeDetailMap.values()) {
				String ticketTypeId = slOrderTickettypeDetail.getId().getTicketTypeId();
				long price = calcTicketTypeByOrgFroze(ticketTypeId, slOrgSaleinfoMap.get(ticketTypeId).getSaleTotalNum());
				ticketTypePriceMap.put(ticketTypeId, price);
			}

			for (SlOrderDetail slOrderDetail : slOrderDetailList) {
				String ticketTypeId = slOrderDetail.getTicketTypeId();
				Long price = ticketTypePriceMap.get(ticketTypeId);
				slOrderDetail.setOriginalPrice(price);
				slOrderDetail.setSalePrice(price);
				frozeAmtTotal = frozeAmtTotal + price;
			}

			slPayType.setAmt(frozeAmtTotal);

			slOrder.setDueSum(frozeAmtTotal);
			slOrder.setRealSum(frozeAmtTotal);
			frozeOrgAdvanceAmt(session, slOrg.getOrgId(), frozeAmtTotal);

			dbUtil.saveEntity("保存销售单", session, slOrder);
			dbUtil.saveEntity("保存网络代理订单", session, slNetagentOrder);
			dbUtil.saveEntity("保存销售单支付明细", session, slPayType);
			dbUtil.saveEntityBatch("批量保存销售单销售单明细", session, slOrderDetailList);
			dbUtil.saveEntityBatch("批量保存销售单票种明细", session, slOrderTickettypeDetailList);
			dbUtil.saveEntityBatch("批量保存销售单门票场馆明细表", session, slOrderVenueDetailList);

			dbUtil.commit(session);
			WSUtil.saveLog(logId, orgID, logType, menuId, MSG.OK, MsgUtil.getMsg(MSG.OK), transq);
			return 1;// 返回值：1 成功，-1 失败 2-该订单已完成
		} catch (CXFException e) {
			logger.error(e);
			WSUtil.saveLog(logId, orgID, logType, menuId, e.getErrcode(), e.getErrinfo(), transq);
			throw e;
		} catch (Exception e) {
			logger.error("错误", e);
			WSUtil.saveLog(logId, orgID, logType, menuId, MSG.ERROR, null, transq);
			throw new CXFException(MSG.ERROR);
		} finally {
			// 清空log4j的mdc信息
			@SuppressWarnings("rawtypes")
			Map map = MDC.getContext();
			if (map != null) {
				map.clear();
			}
			dbUtil.close(session);
		}
	}

	/**
	 * Title:计算机构（美团等）需要扣除的预付款的票价 <br/>
	 * Description:
	 * 
	 * @param ticketTypeId
	 * @param saleTotalNumOld
	 * @return
	 */
	private long calcTicketTypeByOrgFroze(String ticketTypeId, long saleTotalNumOld) {
		DBUtil dbUtil = SpringContextUtil.getBean(DBUtilImpl.class);
		SysTicketTypePrice sysTicketTypePrice = dbUtil.queryFirstForBean("查询阶梯票价", "SELECT * FROM SYS_TICKET_TYPE_PRICE WHERE TICKET_TYPE_ID=? AND (? BETWEEN START_NO AND END_NO)",
				SysTicketTypePrice.class, ticketTypeId, saleTotalNumOld);
		if (sysTicketTypePrice != null) {
			return sysTicketTypePrice.getPrice();
		} else {
			return getPrice(ticketTypeId);
		}
	}

	/**
	 * Title: 获取单价<br/>
	 * Description:
	 * 
	 * @param ticketTypeId
	 * @return
	 */
	private Long getPrice(String ticketTypeId) {
		DBUtil dbUtil = SpringContextUtil.getBean(DBUtilImpl.class);
		SysTicketType sysTicketType = dbUtil.findById("", SysTicketType.class, ticketTypeId);
		// 获取票价
		Long price = sysTicketType.getPrice();
		return price;
	}

	/**
	 * Title:冻结预付款 -冻结预付款增加，预付款余额减少<br/>
	 * Description:
	 * 
	 */
	private void frozeOrgAdvanceAmt(Session session, String orgId, Long forzeAmt) {
		DBUtil dbUtil = SpringContextUtil.getBean(DBUtilImpl.class);
		dbUtil.executeSql("增加冻结预付款", session, "UPDATE SL_ORG SET ADVANCE_FROZE_AMT=ADVANCE_FROZE_AMT+?,ADVANCE_AMT=ADVANCE_AMT-? WHERE ORG_ID=? ", forzeAmt, forzeAmt, orgId);
	}

	/**
	 * Title:退款预付款解冻 -冻结预付款减少，预付款余额增加<br/>
	 * Description:
	 * 
	 */
	private void refundOrgAdvanceAmt(Session session, String orgId, Long forzeAmt) {
		DBUtil dbUtil = SpringContextUtil.getBean(DBUtilImpl.class);
		dbUtil.executeSql("退款预付款解冻", session, "UPDATE SL_ORG SET ADVANCE_FROZE_AMT=ADVANCE_FROZE_AMT-?,ADVANCE_AMT=ADVANCE_AMT+? WHERE ORG_ID=? ", forzeAmt, forzeAmt, orgId);
	}

	@Override
	public TicketStatus[] GetTicketStatusByOrderID(String orgID, String token, String orderID, String transq) throws CXFException {
		DBUtil dbUtil = SpringContextUtil.getBean(DBUtilImpl.class);
		String logId = UUIDGenerator.getPK();
		String menuId = "门票状态查询接口";
		int logType = OperType.QUERY;
		String navigation = String.format("[%s][%s]-%s-%s-%s", logId, orgID, SERVICE_NAME, menuId, transq);
		try {
			MDC.put("navigation", navigation);

			logger.debug(String.format("输入参数:token=[%s],orderID=[%s]", token, orderID));

			OrgAuthCache.checkOrgToken(orgID, token);

			StringBuffer sql = new StringBuffer();
			sql.append("SELECT A.* FROM SL_ORDER_DETAIL A  WHERE A.ORDER_ID=? ");

			List<SlOrderDetail> slOrderDetailList = dbUtil.queryListToBean("按订单号查询门票状态", sql.toString(), SlOrderDetail.class, orderID);
			if (slOrderDetailList == null || slOrderDetailList.size() == 0) {
				return new TicketStatus[0];
			}
			List<TicketStatus> ticketStatusList = new ArrayList<TicketStatus>();
			for (SlOrderDetail slOrderDetail : slOrderDetailList) {
				TicketStatus ticketStatus = new TicketStatus();
				ticketStatusList.add(ticketStatus);

				ticketStatus.setIdentityCode(slOrderDetail.getIdenttyId());
				if (!"N".equals(slOrderDetail.getCheckFlag())) {
					ticketStatus.setStatus(2);// 2-已检票
					continue;
				}

				if (!"N".equals(slOrderDetail.getUselessFlag())) {
					ticketStatus.setStatus(3);// 3-已作废（退票）
					continue;
				}

				if ("2".equals(slOrderDetail.getEjectTicketStat())) {
					ticketStatus.setStatus(1);// 1-已出票 (已换取实体票,不能再退票)
					continue;
				}

				ticketStatus.setStatus(0);// 0-未使用
			}

			TicketStatus[] ticketStatusArray = new TicketStatus[ticketStatusList.size()];
			return ticketStatusList.toArray(ticketStatusArray);
		} catch (CXFException e) {
			logger.error(e);
			WSUtil.saveLog(logId, orgID, logType, menuId, e.getErrcode(), e.getErrinfo(), transq);
			throw e;
		} catch (Exception e) {
			logger.error("错误", e);
			WSUtil.saveLog(logId, orgID, logType, menuId, MSG.ERROR, null, transq);
			throw new CXFException(MSG.ERROR);
		} finally {
			// 清空log4j的mdc信息
			@SuppressWarnings("rawtypes")
			Map map = MDC.getContext();
			if (map != null) {
				map.clear();
			}
		}
	}

	@Override
	public int tradeRefund(String orgID, String token, String orderID, String refundID, int ticketCount, long refundAMTSum, Date refundTime, RefundTicket[] refundTickets, String transq)
			throws CXFException {
		Session session = null;
		DBUtil dbUtil = SpringContextUtil.getBean(DBUtilImpl.class);
		String logId = UUIDGenerator.getPK();
		String menuId = "退票接口";
		int logType = OperType.ADD;
		String navigation = String.format("[%s][%s]-%s-%s-%s", logId, orgID, SERVICE_NAME, menuId, transq);
		try {
			MDC.put("navigation", navigation);

			logger.debug(String.format("输入参数:token=[%s],orderID=[%s],refundID=[%s],ticketCount=[%d],refundAMTSum=[%d],refundTime=[%tc],refundTickets=[%s],transq=[%s]", token, orderID, refundID,
					ticketCount, refundAMTSum, refundTime, JSONArray.toJSONString(refundTickets), transq));

			OrgAuthCache.checkOrgToken(orgID, token);
			// 开启事务
			session = dbUtil.getSessionByTransaction();

			SlRefundTicket slRefundTicket1 = dbUtil.queryFirstForBean("", "SELECT * FROM SL_REFUND_TICKET WHERE REFUND_ID=? ", SlRefundTicket.class, refundID);
			if (slRefundTicket1 != null) {
				return 2;// 返回值：2：此退款单已做过退款处理
			}

			// 检验原销售单号是否存在
			SlOrder slOrder = dbUtil.queryFirstForBean("", "SELECT * FROM SL_ORDER WHERE ORDER_ID=? ", SlOrder.class, orderID);
			if (slOrder == null) {
				throw new CXFException(MSG.BF_ERROR, String.format("退款失败,原订单号[%s]不存在", orderID));
			}

			// 校验退票单张数与明细不符
			if ((refundTickets != null && refundTickets.length > 0 && ticketCount != refundTickets.length)
					|| ((refundTickets == null || refundTickets.length == 0) && ticketCount != slOrder.getTicketCount())) {
				throw new CXFException(MSG.BF_ERROR, "退票单张数与明细不符");
			}

			// 退票信息登记
			SlRefundTicket slRefundTicket = new SlRefundTicket();
			slRefundTicket.setRefundId(refundID);
			slRefundTicket.setOrderType("ZQ");// 销售类型(XC-现场售票、ST-实体代理、ZG-自助购票、ZQ-自助取票)
			slRefundTicket.setOrgId(orgID);
			slRefundTicket.setOrderId(orderID);
			slRefundTicket.setTicketCount(CommonUtil.covertLong(ticketCount));
			slRefundTicket.setRefundTime(refundTime);
			slRefundTicket.setTransq(transq);

			List<SlOrderDetail> slOrderDetailListByUpdate = new ArrayList<SlOrderDetail>();
			// 查询原订单明细信息
			Map<String, SlOrderDetail> slOrderDetails = dbUtil.queryMapForBean("", "SELECT * FROM SL_ORDER_DETAIL WHERE ORDER_ID=? ", SlOrderDetail.class, "identtyId", orderID);
			if (slOrderDetails == null || slOrderDetails.size() == 0) {
				throw new CXFException(MSG.BF_ERROR, String.format("退款失败,原订单明细不存在"));
			}

			// 退款明细数组为空，整单退款，构造退款明细数组
			if (refundTickets == null || refundTickets.length == 0) {
				refundTickets = new RefundTicket[ticketCount];
				int i = 0;
				for (SlOrderDetail slOrderDetail : slOrderDetails.values()) {
					RefundTicket refundTicket = new RefundTicket();
					refundTicket.setIdentityCode(slOrderDetail.getIdenttyId());
					refundTicket.setRefundAmt(slOrderDetail.getSalePrice());
					refundTickets[i] = refundTicket;
					i++;
				}
			}

			// 退票明细
			List<SlRefundTicketDetail> sRefundTicketDetailListBySave = new ArrayList<SlRefundTicketDetail>();
			long detailRefundAmt = 0;
			for (RefundTicket refundTicket : refundTickets) {
				refundTicket.setIdentityCode(StringUtil.trim(refundTicket.getIdentityCode()).toUpperCase());
				if (!slOrderDetails.containsKey(refundTicket.getIdentityCode())) {
					throw new CXFException(MSG.BF_ERROR, String.format("退款失败,原订单明细中身份证号[%s]不存在", refundTicket.getIdentityCode()));
				}
				SlOrderDetail slOrderDetail = slOrderDetails.get(refundTicket.getIdentityCode());
				// 是否检票(Y是N否)
				if ("Y".equals(slOrderDetail.getCheckFlag())) {
					throw new CXFException(MSG.BF_ERROR, String.format("退款失败,原订单明细中身份证号[%s]已入园,不允许再退票", refundTicket.getIdentityCode()));
				}
				// 是否作废(N否 Y补票作废 T退票作废 )
				if (!"N".equals(slOrderDetail.getUselessFlag())) {
					throw new CXFException(MSG.BF_ERROR, String.format("退款失败,原订单明细中身份证号[%s]已退票,不允许再退票", refundTicket.getIdentityCode()));
				}
				// 出票状态(1-待出票 2-已出票)
				if ("2".equals(slOrderDetail.getEjectTicketStat())) {
					throw new CXFException(MSG.BF_ERROR, String.format("退款失败,原订单明细中身份证号[%s]已取票,不允许再退票", refundTicket.getIdentityCode()));
				}
				// 校验原金额与退票金额不符
//				if (slOrderDetail.getSalePrice() != refundTicket.getRefundAmt()) {
//					throw new CXFException(MSG.BF_ERROR, String.format("退款失败,身份证号[%s],原金额[%d]与退票金额[%d]不符,", slOrderDetail.getSalePrice(), refundTicket.getRefundAmt()));
//				}

				// 汇总明细总金额
				detailRefundAmt += slOrderDetail.getSalePrice();

				// 更新原销售单明细
				slOrderDetail.setUselessFlag("T");// 是否作废(N否 Y补票作废 T退票作废 )
				slOrderDetail.setUselessTime(new Date());
				slOrderDetailListByUpdate.add(slOrderDetail);
				// 增加退票登记明細信息
				SlRefundTicketDetail sRefundTicketDetail = new SlRefundTicketDetail();
				sRefundTicketDetail.setRefundDetailId(UUIDGenerator.getPK());
				sRefundTicketDetail.setRefundId(refundID);
				sRefundTicketDetail.setTicketClass("2");// 门票类型(1-FRID、2-身份证、3-二维码)
				sRefundTicketDetail.setIdenttyId(refundTicket.getIdentityCode());
				sRefundTicketDetail.setRefundAmt(slOrderDetail.getSalePrice());
				sRefundTicketDetailListBySave.add(sRefundTicketDetail);
			}

			// 退票单总金额与明细总金额不符
//			if (detailRefundAmt != refundAMTSum) {
//				throw new CXFException(MSG.BF_ERROR, String.format("退款失败,退票单总金额[%d]与明细总金额不符[%d]", detailRefundAmt, refundAMTSum));
//			}
			
			slRefundTicket.setRefundAmtSum(detailRefundAmt);

			// 退款预付款解冻 -冻结预付款减少，预付款余额增加
			refundOrgAdvanceAmt(session, orgID, detailRefundAmt);

			dbUtil.saveEntity("保存退票单信息", session, slRefundTicket);
			dbUtil.saveEntityBatch("批量保存退票单明细信息", session, sRefundTicketDetailListBySave);
			dbUtil.updateEntityBatch("批量更新原订单明细信息", session, slOrderDetailListByUpdate);

			dbUtil.commit(session);
			WSUtil.saveLog(logId, orgID, logType, menuId, MSG.OK, MsgUtil.getMsg(MSG.OK), transq);
			return 1;// 返回值：1 成功，-1 失败
		} catch (CXFException e) {
			logger.error(e);
			WSUtil.saveLog(logId, orgID, logType, menuId, e.getErrcode(), e.getErrinfo(), transq);
			throw e;
		} catch (Exception e) {
			logger.error("错误", e);
			WSUtil.saveLog(logId, orgID, logType, menuId, MSG.ERROR, null, transq);
			throw new CXFException(MSG.ERROR);
		} finally {
			// 清空log4j的mdc信息
			@SuppressWarnings("rawtypes")
			Map map = MDC.getContext();
			if (map != null) {
				map.clear();
			}
			dbUtil.close(session);
		}
	}
	
	
	//获取调用方的IP地址
//	private WebServiceContext wsContext= new WebServiceContextImpl();
//	
//	private String getClientIp() {
//		String clientIP = null;
//		try {
//			MessageContext mc = wsContext.getMessageContext();
//			HttpServletRequest request = (HttpServletRequest) (mc
//					.get(MessageContext.SERVLET_REQUEST));
//			clientIP = request.getRemoteAddr();
//			System.out.println("client IP : " + clientIP);
//		} catch (Exception e) {
//			//e.printStackTrace();
//			logger.error("获取客户端IP地址错误", e);
//		}
//		return clientIP;
//	}
	
//	private String getClientIp() {
//		try {
//			MessageContext mc = wsContext.getMessageContext();
// 
//			HttpExchange exchange = (HttpExchange) mc
//					.get(JAXWSProperties.HTTP_EXCHANGE);
//			InetSocketAddress isa = exchange.getRemoteAddress();
//			System.out.println("InetSocketAddress : " + isa);
//			System.out.println("Hostname : "
//					+ isa.getAddress().getHostName() + " address: "
//					+ isa.getAddress().getHostAddress());
//			return isa.getAddress().getHostAddress();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
//	public String getClientIpCxf() {
//		MessageContext ctx = wsContext.getMessageContext();
//		HttpServletRequest request = (HttpServletRequest)
//		ctx.get(AbstractHTTPDestination.HTTP_REQUEST);
//		String ip=request.getRemoteAddr();
//		return ip; 
//	}
	
//	/** 
//     * 获取客户端IP地址，如调用方的IP，以便检查权限。 
//     * 适用于axis发布的webservice 
//     * @return 
//     */ 
//    public String getClientIpAxis() { 
//        MessageContext mc = null; 
//        HttpServletRequest request = null; 
//        try { 
//            mc = MessageContext.getCurrentMessageContext(); 
//            if (mc == null) 
//                throw new Exception("无法获取到MessageContext"); 
//            request = (HttpServletRequest) mc 
//                    .getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST); 
//            System.out.println("remote  ip:  " + request.getRemoteAddr()); 
//        } catch (Exception e) { 
//            System.out.println(e.getMessage()); 
//            e.printStackTrace(); 
//        } 
//        return request.getRemoteAddr(); 
//    } 
 
//    /** 
//     * 获取客户端IP地址 
//     * 适用于xfire发布的webservice 
//     * @return 
//     */ 
//    public String getClientIpXfire() { 
//        String ip = ""; 
//        try { 
//            HttpServletRequest request = XFireServletController.getRequest(); 
//            ip = request.getRemoteAddr(); 
//        } catch (Exception e) { 
//            System.out.println("无法获取HttpServletRequest."); 
//            e.printStackTrace(); 
//        } 
//        return ip; 
//    }
}
