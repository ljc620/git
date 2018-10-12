package com.tbims.pay.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hibernate.Session;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tbims.entity.SlBill;
import com.tbims.entity.SlBillDetail;
import com.tbims.entity.SlOrder;
import com.tbims.entity.SlRefundTicket;
import com.tbims.pay.PayUtil;
import com.tbims.pay.bean.AttachParamsBean;
import com.tbims.pay.bean.BillOrderDetialEM;
import com.tbims.pay.bean.BillOrderRequest;
import com.tbims.pay.bean.CancelOrderRequest;
import com.tbims.pay.bean.OrderPayRequest;
import com.tbims.pay.bean.OrderPayResponse;
import com.tbims.pay.bean.QueryOrderRequest;
import com.tbims.pay.bean.QueryOrderResponse;
import com.tbims.pay.bean.QueryRefundOrderRequest;
import com.tbims.pay.bean.QueryRefundOrderResponse;
import com.tbims.pay.bean.RefundOrderRequest;
import com.tbims.pay.bean.RefundOrderResponse;
import com.tbims.pay.bean.SendDataBean;
import com.tbims.pay.bfbank.BfConstant;
import com.tbims.pay.bfbank.config.SwiftpassConfig;
import com.tbims.pay.bfbank.util.MD5;
import com.tbims.pay.bfbank.util.SignUtils;
import com.tbims.pay.bfbank.util.XmlUtils;
import com.zhming.support.db.DBUtil;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.BeanUtils;
import com.zhming.support.util.CommonUtil;
import com.zhming.support.util.StringUtil;
import com.zhming.support.util.UUIDGenerator;

@Component
public class PayUtilByBfBank implements PayUtil {
	private static final Log logger = LogFactory.getLog(PayUtilByBfBank.class);

	String reqUrl = SwiftpassConfig.req_url;
	String key = SwiftpassConfig.key;

	@Autowired
	DBUtil dbUtil;

	/**
	 * Title:调用银行接口 <br/>
	 * Description:
	 * 
	 * @param requestParamsMap
	 * @return
	 * @throws ServiceException
	 */
	private SendDataBean sendData(Map<String, String> requestParamsMap) throws ServiceException {
		SendDataBean sendDataBean = new SendDataBean();
		StringBuilder buf = new StringBuilder();

		String logId = MDC.get("logid");
		if (StringUtil.isNull(logId)) {
			logId = UUIDGenerator.getPK();
		}

		requestParamsMap.put("nonce_str", logId);// 随机字符串

		SignUtils.buildPayParams(buf, requestParamsMap, false);
		String preStr = buf.toString();

		logger.debug("签名字符串 preStr:" + preStr);

		String sign = MD5.sign(preStr, "&key=" + key, "utf-8");

		requestParamsMap.put("sign", sign.toUpperCase());// 签名

		String requestParamsXML = XmlUtils.parseXML(requestParamsMap);
		logger.debug("请求地址 reqUrl：" + reqUrl);
		logger.debug("请求xml参数:\n" + requestParamsXML);

		CloseableHttpResponse response = null;
		CloseableHttpClient client = null;
		try {
			String responseParamsXML = null;
			Map<String, String> responseParamsMap = null;

			HttpPost httpPost = new HttpPost(reqUrl);
			StringEntity entityParams = new StringEntity(requestParamsXML, "utf-8");
			httpPost.setEntity(entityParams);
			httpPost.setHeader("Content-Type", "text/xml;charset=ISO-8859-1");
			client = HttpClients.createDefault();
			response = client.execute(httpPost);

			if (response != null && response.getEntity() != null) {
				responseParamsMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
				responseParamsXML = XmlUtils.toXml(responseParamsMap);
				logger.debug("返回xml参数：\n" + responseParamsXML);

				if (responseParamsMap.containsKey("sign")) {
					if (!SignUtils.checkParam(responseParamsMap, key)) {
						throw new ServiceException(3, "银行接口,验证签名不通过");
					}
				}

			} else {
				throw new ServiceException(3, "银行接口,调用接口错误");
			}

			sendDataBean.setRequestParamsMap(requestParamsMap);
			sendDataBean.setRequestParamsXML(requestParamsXML);
			sendDataBean.setResponseParamsMap(responseParamsMap);
			sendDataBean.setResponseParamsXML(responseParamsXML);
			return sendDataBean;
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			logger.error("银行接口,系统异常", e);
			throw new ServiceException(3, "银行接口,系统异常");
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (client != null) {
					client.close();
				}
			} catch (IOException e) {
				logger.error("银行接口,系统异常", e);
			}
		}
	}

	@Override
	public void orderPay(OrderPayRequest orderPayRequest) throws ServiceException {
	}

	/**
	 * Title:订单支付接口 更新订单支付状态<br/>
	 * Description:
	 * 
	 * @param orderPayRequest
	 * @param orderPayResponse
	 * @param request_param
	 *            请求串
	 * @param response_params
	 *            响应串
	 * @param pay_stat
	 *            支付状态(1-待支付 2-已支付 3-支付失败)
	 * @throws ServiceException
	 */
	@SuppressWarnings("unused")
	private void updatePayStat(OrderPayRequest orderPayRequest, OrderPayResponse orderPayResponse, String request_param,
			String response_params, String pay_stat) throws ServiceException {
	}

	@Override
	public int queryOrderPay(QueryOrderRequest orderPayQueryRequest) throws ServiceException {
		return 0;
	}

	/**
	 * Title:订单支付接口 更新订单支付状态<br/>
	 * Description:
	 * 
	 * @param orderPayRequest
	 * @param orderPayResponse
	 * @param request_param
	 *            请求串
	 * @param response_params
	 *            响应串
	 * @param pay_stat
	 *            支付状态(1-待支付 2-已支付 3-支付失败)
	 * @throws ServiceException
	 */
	@SuppressWarnings("unused")
	private void updatePayStatByQuery(QueryOrderRequest queryOrderRequest, QueryOrderResponse queryOrderResponse,
			String request_param, String response_params, String pay_stat) throws ServiceException {
	}

	@Override
	public void cancelOrderPay(CancelOrderRequest cancelOrderRequest) throws ServiceException {
	}

	@Override
	public void refundOrderPay(RefundOrderRequest tradeOrderRequest) throws ServiceException {
		tradeOrderRequest.setTotal_fee(tradeOrderRequest.getTotal_fee() * SwiftpassConfig.unit_type);
		tradeOrderRequest.setRefund_fee(tradeOrderRequest.getRefund_fee() * SwiftpassConfig.unit_type);

		Map<String, String> requestParamMap = BeanUtils.copyBeanToMap(tradeOrderRequest, null);
		requestParamMap.put("service", BfConstant.REFUND);
		SendDataBean sendDataBean = sendData(requestParamMap);
		RefundOrderResponse refundOrderResponse = null;

		try {
			refundOrderResponse = BeanUtils.copyMapToBean(sendDataBean.getResponseParamsMap(),
					RefundOrderResponse.class, null);
			if (!"0".equals(refundOrderResponse.getStatus())) {// 接口通信异常
				throw new ServiceException(3, String.format("银行接口返回,错误,[%s]", refundOrderResponse.getMessage()));
			}

			if (!"0".equals(refundOrderResponse.getResult_code())) {// 申请退款失败
				throw new ServiceException(3, String.format("银行接口返回,错误,[%s]", refundOrderResponse.getErr_msg()));
			}

		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(3, String.format("退款错误,[%s]"));
		}
	}

	@Override
	public void queryRefundOrderPay(QueryRefundOrderRequest queryTradeOrderRequest) throws ServiceException {
		Map<String, String> requestParamMap = BeanUtils.copyBeanToMap(queryTradeOrderRequest, null);
		requestParamMap.put("service", BfConstant.REFUNDQUERY);
		SendDataBean sendDataBean = sendData(requestParamMap);

		QueryRefundOrderResponse queryRefundOrderResponse = null;

		try {
			// 开启事务
			queryRefundOrderResponse = BeanUtils.copyMapToBean(sendDataBean.getResponseParamsMap(),
					QueryRefundOrderResponse.class, null);
			if (!"0".equals(queryRefundOrderResponse.getStatus())) {// 接口通信异常
				throw new ServiceException(3, String.format("银行接口返回,错误,[%s]", queryRefundOrderResponse.getMessage()));
			}

			if (!"0".equals(queryRefundOrderResponse.getResult_code())) {// 申请退款失败
				throw new ServiceException(3, String.format("银行接口返回,错误,[%s]", queryRefundOrderResponse.getErr_msg()));
			}

			SlRefundTicket slRefundTicket = dbUtil.findById("查询退款表", SlRefundTicket.class,
					queryTradeOrderRequest.getOut_refund_no());
			slRefundTicket
					.setRemark("银行返回:" + BfConstant.REFUND_STATUS.get(queryRefundOrderResponse.getRefund_status()));
			dbUtil.updateEntity("更新退款表", slRefundTicket);
			if ("SUCCESS".equals(queryRefundOrderResponse.getRefund_status())) {
				return;
			} else {
				throw new ServiceException(3, String.format("银行接口返回,错误,[%s]",
						BfConstant.REFUND_STATUS.get(queryRefundOrderResponse.getRefund_status())));
			}

		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			logger.error("银行接口,系统异常", e);
			throw new ServiceException(3, "银行接口,系统异常");
		}
	}

	@Override
	public void billOrder(BillOrderRequest billOrderBean) throws ServiceException {
		StringBuilder buf = new StringBuilder();
		Map<String, String> requestParamsMap = BeanUtils.copyBeanToMap(billOrderBean, null);

		String logId = MDC.get("logid");
		if (StringUtil.isNull(logId)) {
			logId = UUIDGenerator.getPK();
		}

		requestParamsMap.put("service", "pay.bill.merchant");
		requestParamsMap.put("nonce_str", logId);// 随机字符串

		SignUtils.buildPayParams(buf, requestParamsMap, false);
		String preStr = buf.toString();

		logger.debug("签名字符串 preStr:" + preStr);

		String sign = MD5.sign(preStr, "&key=" + key, "utf-8");

		requestParamsMap.put("sign", sign.toUpperCase());// 签名

		String requestParamsXML = XmlUtils.parseXML(requestParamsMap);
		logger.debug("请求地址 reqUrl：" + SwiftpassConfig.bill_req_url);
		logger.debug("支付请求xml参数:\n" + requestParamsXML);

		CloseableHttpResponse response = null;
		CloseableHttpClient client = null;
		List<SlBillDetail> slBillDetailList = new ArrayList<SlBillDetail>();
		SlBill slBill = new SlBill();
		try {
			slBill.setBillId(UUIDGenerator.getPK());
			slBill.setBillDate(billOrderBean.getBill_date());

			HttpPost httpPost = new HttpPost(SwiftpassConfig.bill_req_url);
			StringEntity entityParams = new StringEntity(requestParamsXML, "utf-8");
			httpPost.setEntity(entityParams);
			httpPost.setHeader("Content-Type", "text/xml;charset=ISO-8859-1");
			client = HttpClients.createDefault();
			response = client.execute(httpPost);

			if (response != null && response.getEntity() != null) {
				String retInfo = EntityUtils.toString(response.getEntity(), "utf-8");
				retInfo = retInfo + ",`";
				String[] lines = retInfo.split("\r\n");

				int[] igrors = { 0, lines.length - 1, lines.length - 2 }; // 将第一行和倒数第一、二行过滤掉

				if (CommonUtil.isJsonObjStr(retInfo)) {
					JSONObject a = JSONArray.parseObject(retInfo);
					if (a.containsKey("result")) {
						slBill.setStat(StringUtil.convertString(a.get("result")));
					}
					if (a.containsKey("status")) {
						slBill.setStat(StringUtil.convertString(a.get("status")));
					}
					slBill.setStatInfo(StringUtil.convertString(a.get("message")));
					lines = new String[0];
				}

				for (int i = 0; i < lines.length; i++) {
					if (CommonUtil.isContainsByInt(i, igrors)) {// 将第一行和倒数第一、二行过滤掉
						continue;
					}

					String line = lines[i];
					String[] lineSplit = line.split(",");
					for (int j = 0; j < lineSplit.length; j++) {// 去掉拆分后`
						lineSplit[j] = lineSplit[j].substring(1);
					}

					String attach = lineSplit[BillOrderDetialEM.ATTACH.getValue()];
					AttachParamsBean attachParamsBean = null;
					if (CommonUtil.isJsonObjStr(attach)) {
						attachParamsBean = JSONArray.parseObject(attach, AttachParamsBean.class);
					} else {// 非本系统的交易过滤掉
						continue;
					}

					if (!BfConstant.BILL_FLAG.equals(attachParamsBean.getBill_flag())) {// 非本系统的交易过滤掉
						continue;
					}

					SlBillDetail slBillDetail = new SlBillDetail();
					slBillDetail.setBillDetailId(UUIDGenerator.getPK());
					slBillDetail.setBillId(slBill.getBillId());
					slBillDetail.setTranTime(lineSplit[BillOrderDetialEM.TRAN_TIME.getValue()]);
					slBillDetail.setMchId(lineSplit[BillOrderDetialEM.MCH_ID.getValue()]);
					slBillDetail.setTransactionId(lineSplit[BillOrderDetialEM.TRANSACTION_ID.getValue()]);
					slBillDetail.setOutTransactionId(lineSplit[BillOrderDetialEM.TRANSACTION_ID.getValue()]);
					slBillDetail.setOutTradeNo(lineSplit[BillOrderDetialEM.OUT_TRADE_NO.getValue()]);
					slBillDetail.setTranType(lineSplit[BillOrderDetialEM.TRAN_TYPE.getValue()]);
					slBillDetail.setTranStatus(lineSplit[BillOrderDetialEM.TRAN_STATUS.getValue()]);
					slBillDetail
							.setOrderFee(CommonUtil.covertDouble(lineSplit[BillOrderDetialEM.ORDER_FEE.getValue()]));
					slBillDetail.setRefundId(lineSplit[BillOrderDetialEM.REFUND_ID.getValue()]);
					slBillDetail.setOutRefundNo(lineSplit[BillOrderDetialEM.OUT_REFUND_NO.getValue()]);
					slBillDetail
							.setRefundFee(CommonUtil.covertDouble(lineSplit[BillOrderDetialEM.REFUND_FEE.getValue()]));
					slBillDetail.setRefundType(lineSplit[BillOrderDetialEM.REFUND_TYPE.getValue()]);
					slBillDetail.setRefundStatus(lineSplit[BillOrderDetialEM.REFUND_STATUS.getValue()]);
					slBillDetail.setServiceFee(
							CommonUtil.covertDouble(lineSplit[BillOrderDetialEM.SERVICE_FEE.getValue()]));
					slBillDetail.setCollectFee(
							CommonUtil.covertDouble(lineSplit[BillOrderDetialEM.COLLECT_FEE.getValue()]));
					slBillDetail.setResponseInfo(line);
					slBillDetail.setBodyStr(lineSplit[BillOrderDetialEM.BODY.getValue()]);
					slBillDetail.setAttach(lineSplit[BillOrderDetialEM.ATTACH.getValue()]);
					slBillDetail.setBillResult("N");// 对账状态 0-对账成功 1-对账失败 N-未对账
					slBillDetailList.add(slBillDetail);

					if (slBillDetail.getTranStatus().equals("支付成功")) {
						slBill.setPayFeeTotal(CommonUtil.covertDouble(slBill.getPayFeeTotal())
								+ CommonUtil.covertDouble(slBillDetail.getOrderFee()));
						slBill.setPayNumTotal(CommonUtil.covertLong(slBill.getPayNumTotal()) + 1);
					}

					if (slBillDetail.getRefundStatus().equals("退款成功")) {
						slBill.setRefundFeeTotal(CommonUtil.covertDouble(slBill.getRefundFeeTotal())
								+ CommonUtil.covertDouble(slBillDetail.getRefundFee()));
						slBill.setRefundNumTotal(CommonUtil.covertLong(slBill.getRefundNumTotal()) + 1);
					}
				}

			} else {
				throw new ServiceException(3, "银行接口,调用接口错误");
			}

			slBill.setStat("1");
			if (slBillDetailList.size() > 0) {
				slBill.setStatInfo("下载对账单成功");
			} else {
				slBill.setStatInfo("下载对账单成功,无交易数据");
			}
		} catch (ServiceException e) {
			logger.error("下载对账单错误", e);
			slBill.setStat("3");
			slBill.setStatInfo("下载对账单错误");
		} catch (Exception e) {
			logger.error("银行接口,系统异常", e);
			slBill.setStat("3");
			slBill.setStatInfo("下载对账单错误");
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (client != null) {
					client.close();
				}
			} catch (IOException e) {
				logger.error("银行接口,系统异常", e);
			}
		}

		Session session = null;
		try {
			// 开启事务
			session = dbUtil.getSessionByTransaction();

			dbUtil.executeSql("删除对账明细", session,
					"DELETE FROM SL_BILL_DETAIL WHERE BILL_ID IN (SELECT BILL_ID FROM SL_BILL WHERE BILL_DATE=?)",
					billOrderBean.getBill_date());
			dbUtil.executeSql("删除对账记录", session, "DELETE FROM SL_BILL WHERE BILL_DATE=?", billOrderBean.getBill_date());
			dbUtil.saveEntity("保存对账单", session, slBill);
			dbUtil.saveEntityBatch("保存对账单明细", session, slBillDetailList);

			dbUtil.commit(session);
		} catch (Exception e) {
			logger.error("保存对账单错误", e);
		} finally {
			dbUtil.close(session);
		}

		// 0-对账完成 1-下载对账单成功 3-下载对账单错误 0-对账成功 5-对账失败 4-对账错误
		// 下载对账单成功进行对账
		if ("1".equals(slBill.getStat()) && slBillDetailList.size() > 0) {
			beginBill(billOrderBean);
		}
	}

	/**
	 * 开始对账
	 * 
	 * @throws ServiceException
	 */
	public void beginBill(BillOrderRequest billOrderBean) throws ServiceException {
		SlBill slBill = null;
		List<SlOrder> slOrderFailList = new ArrayList<SlOrder>();
		List<SlBillDetail> slBillDetailList = null;
		List<SlRefundTicket> slRefundTicketList = new ArrayList<SlRefundTicket>();
		try {
			slBill = dbUtil.queryFirstForBean("", "SELECT * FROM SL_BILL WHERE BILL_DATE=?", SlBill.class,
					billOrderBean.getBill_date());
			slBillDetailList = dbUtil.queryListToBean("查询对账明细",
					"SELECT B.* FROM SL_BILL A,SL_BILL_DETAIL B WHERE A.BILL_ID=B.BILL_ID and BILL_DATE=?",
					SlBillDetail.class, billOrderBean.getBill_date());

			for (SlBillDetail slBillDetail : slBillDetailList) {
				if ("支付成功".equals(slBillDetail.getTranStatus())) {
					SlOrder slOrder = dbUtil.queryFirstForBean("查询订单表", "SELECT * FROM SL_ORDER WHERE ORDER_ID=?",
							SlOrder.class, slBillDetail.getOutTradeNo());
					if (slOrder != null && slOrder.getPayStat().equals("2")) {// 对账成功
						slBillDetail.setBillResult("0");// 对账状态 0-对账成功 1-对账失败
					} else {// 对账失败
						slBillDetail.setBillResult("1");
						slBill.setPayNumFail(CommonUtil.covertLong(slBill.getPayNumFail()) + 1);
						if (slOrder != null) {
							slOrder.setPayStat("3");
							slOrderFailList.add(slOrder);
						}
					}
					
					SlRefundTicket slRefundTicket=dbUtil.queryFirstForBean("是否已退款", "select * from sl_refund_ticket where ORDER_ID=?", SlRefundTicket.class, slBillDetail.getOutTradeNo());
					if(slRefundTicket!=null){
						slBillDetail.setRemark("已退款");
						slBillDetail.setOutRefundNo(slRefundTicket.getRefundId());
						slBillDetail.setRefundFee(slRefundTicket.getRefundAmtSum());
					}
				}

				// 退款成功，更新退款表
				if ("退款成功".equals(slBillDetail.getRefundStatus())) {
					SlRefundTicket slRefundTicketQuery = dbUtil.queryFirstForBean("查询退款表",
							"SELECT * FROM SL_REFUND_TICKET WHERE REFUND_ID=?", SlRefundTicket.class,
							slBillDetail.getOutRefundNo());
					if (slRefundTicketQuery != null) {
						slBillDetail.setBillResult("0");// 对账状态 0-对账成功 1-对账失败.
						slRefundTicketQuery.setRemark("银行返回: 退款成功");
						slRefundTicketList.add(slRefundTicketQuery);
					} else {
						slBillDetail.setBillResult("1");// 对账状态 0-对账成功 1-对账失败.
						slBill.setRefundNumFail(CommonUtil.covertLong(slBill.getRefundNumFail()) + 1);
					}
				}
			}

			if (slBill.getPayNumFail() == 0 && slBill.getRefundNumFail() == 0) {
				slBill.setStat("0");
				slBill.setStatInfo("对账成功");
			} else {
				slBill.setStat("5");
				slBill.setStatInfo("对账失败");
			}
		} catch (Exception e) {
			logger.error("对账错误", e);
			slBill.setStat("4");
			slBill.setStatInfo("对账错误");
		}

		Session session = null;
		try {
			// 开启事务 session =
			session = dbUtil.getSessionByTransaction();
			dbUtil.updateEntity("更新对账表", session, slBill);
			dbUtil.updateEntityBatch("批量更新对账明细", session, slBillDetailList);
			dbUtil.updateEntityBatch("批量更新订单支付状态", session, slOrderFailList);
			dbUtil.updateEntityBatch("批量更新退款表", session, slRefundTicketList);
			dbUtil.commit(session);
		} catch (Exception e) {
			logger.error("保存对账信息错误", e);
		} finally {
			dbUtil.close(session);
		}
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) throws ServiceException {
		logger.debug("加载spring 配值...start");
		ApplicationContext factory = new ClassPathXmlApplicationContext(
				new String[] { "spring-common.xml", "spring-hibernate.xml" });
		logger.debug("加载spring 配值...end");

		PayUtilByBfBank payUtilByBfBank = factory.getBean(PayUtilByBfBank.class);
		BillOrderRequest billOrderBean = new BillOrderRequest();
		billOrderBean.setBill_date("20170918");
		billOrderBean.setBill_type("ALL");
		billOrderBean.setMch_id(SwiftpassConfig.mch_id);
		payUtilByBfBank.billOrder(billOrderBean);

		// DBUtil dbUtil = factory.getBean(DBUtilImpl.class);
		// List<SlBillDetail> slBillDetailList = dbUtil.queryListToBean("",
		// "SELECT * FROM SL_BILL_DETAIL WHERE BILL_RESULT='1' ",
		// SlBillDetail.class);
		//
		// for (SlBillDetail slBillDetail : slBillDetailList) {
		// try {
		// RefundOrderRequest tradeOrderRequest = new RefundOrderRequest();
		// tradeOrderRequest.setMch_id(SwiftpassConfig.mch_id);
		// tradeOrderRequest.setOut_refund_no(slBillDetail.getOutTradeNo() +
		// "-R01");
		// tradeOrderRequest.setOut_trade_no(slBillDetail.getOutTradeNo());
		// tradeOrderRequest.setRefund_fee(CommonUtil.covertInt((int)
		// (slBillDetail.getOrderFee() * 100)));
		// tradeOrderRequest.setTotal_fee(CommonUtil.covertInt((int)
		// (slBillDetail.getOrderFee() * 100)));
		// payUtilByBfBank.refundOrderPay(tradeOrderRequest);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }

	}

}
