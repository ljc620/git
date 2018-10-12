package com.tbims.pay.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.config.RequestConfig;
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
import com.tbims.db.entity.SlBill;
import com.tbims.db.entity.SlBillDetail;
import com.tbims.db.entity.SlOrder;
import com.tbims.db.entity.SlPayType;
import com.tbims.db.entity.SlRefundTicket;
import com.tbims.db.util.DBUtil;
import com.tbims.pay.PayUtil;
import com.tbims.pay.bean.AttachParamsBean;
import com.tbims.pay.bean.BillOrderDetialEM;
import com.tbims.pay.bean.BillOrderRequest;
import com.tbims.pay.bean.CancelOrderRequest;
import com.tbims.pay.bean.CancelOrderResponse;
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
import com.tbims.rpc.entity.RPCException;
import com.tbims.util.BeanUtils;
import com.tbims.util.CommonUtil;
import com.tbims.util.StringUtil;
import com.tbims.util.UUIDGenerator;

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
	 * @throws RPCException
	 */
	private SendDataBean sendData(Map<String, String> requestParamsMap) throws RPCException {
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
			// setConnectTimeout:1000*30=30秒
			RequestConfig requestConfig =
			RequestConfig.custom().setConnectTimeout(1000*SwiftpassConfig.timeout).setSocketTimeout(1000*SwiftpassConfig.timeout).build();
			httpPost.setConfig(requestConfig);

			client = HttpClients.createDefault();
			response = client.execute(httpPost);

			if (response != null && response.getEntity() != null) {
				responseParamsMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
				responseParamsXML = XmlUtils.toXml(responseParamsMap);
				logger.debug("返回xml参数：\n" + responseParamsXML);

				if (responseParamsMap.containsKey("sign")) {
					if (!SignUtils.checkParam(responseParamsMap, key)) {
						throw new RPCException(3, "银行接口,验证签名不通过");
					}
				}

			} else {
				throw new RPCException(3, "银行接口,调用接口错误");
			}

			sendDataBean.setRequestParamsMap(requestParamsMap);
			sendDataBean.setRequestParamsXML(requestParamsXML);
			sendDataBean.setResponseParamsMap(responseParamsMap);
			sendDataBean.setResponseParamsXML(responseParamsXML);
			return sendDataBean;
		} catch (RPCException e) {
			throw e;
		} catch (Exception e) {
			logger.error("银行接口,系统异常", e);
			throw new RPCException(3, "银行接口,系统异常");
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
	public int orderPay(Session session, OrderPayRequest orderPayRequest) throws RPCException {
		int totalFee = orderPayRequest.getTotal_fee() * SwiftpassConfig.unit_type;// *100
																					// 以分为单位，上生产修改
		orderPayRequest.setTotal_fee(totalFee);
		Map<String, String> requestParamMap = BeanUtils.copyBeanToMap(orderPayRequest, null);
		requestParamMap.put("service", BfConstant.MICROPAY);

		AttachParamsBean attachParamsBean = new AttachParamsBean();
		attachParamsBean.setBill_flag(BfConstant.BILL_FLAG);
		requestParamMap.put("attach", JSONArray.toJSONString(attachParamsBean));

		SendDataBean sendDataBean = sendData(requestParamMap);

		OrderPayResponse orderPayResponse = null;

		String request_param = sendDataBean.getRequestParamsXML();
		String response_param = sendDataBean.getResponseParamsXML();
		try {
			orderPayResponse = BeanUtils.copyMapToBean(sendDataBean.getResponseParamsMap(), OrderPayResponse.class, null);
			updatePayStat(session, orderPayRequest, orderPayResponse, request_param, response_param, "1");

			if (!"0".equals(orderPayResponse.getStatus())) {// 接口通信异常
				logger.error(String.format("银行接口返回,错误,[%s]", orderPayResponse.getMessage()));
				return 1;// 待支付,需要调用查询接口
			}

			if (!"0".equals(orderPayResponse.getResult_code())) {// 支付失败
				logger.error(String.format("银行接口返回,错误,[%s]", orderPayResponse.getErr_msg()));
				if ("N".equals(orderPayResponse.getNeed_query())) {
					return 3;// 确认银行支付失败，不需要再调用查询接口
				}
				return 1;// 待支付,需要调用查询接口
			}

			return 2;// 支付成功，不需要再调用查询接口
		} catch (RPCException e) {
			throw e;
		} catch (Exception e) {
			logger.error("银行接口,系统异常", e);
			throw new RPCException(3, "银行接口,系统异常");
		}
	}

	/**
	 * Title:订单支付接口 更新订单支付状态<br/>
	 * Description:
	 * 
	 * @param orderPayRequest
	 * @param orderPayResponse
	 * @param request_param 请求串
	 * @param response_params 响应串
	 * @param pay_stat 支付状态(1-待支付 2-已支付 3-支付失败)
	 * @throws RPCException
	 */
	private void updatePayStat(Session session, OrderPayRequest orderPayRequest, OrderPayResponse orderPayResponse, String request_param, String response_params, String pay_stat) throws RPCException {

		SlPayType slPayType = dbUtil.queryFirstForBean("查询订单支付表", "SELECT * FROM SL_PAY_TYPE WHERE ORDER_ID=?", SlPayType.class, orderPayRequest.getOut_trade_no());

		slPayType.setPayId(orderPayResponse.getOut_transaction_id());
		slPayType.setBankId(orderPayResponse.getTransaction_id());
		slPayType.setRequestParam(request_param);
		slPayType.setResponseParams("订单支付接口返回-" + response_params);

		// dbUtil.updateEntity("更新支付状态", session, slOrder);
		dbUtil.updateEntity("更新支付后数据", session, slPayType);
	}

	@Override
	public int queryOrderPay(Session session, QueryOrderRequest orderPayQueryRequest) throws RPCException {
		Map<String, String> requestParamMap = BeanUtils.copyBeanToMap(orderPayQueryRequest, null);
		requestParamMap.put("service", BfConstant.QUERY);
		SendDataBean sendDataBean = sendData(requestParamMap);

		QueryOrderResponse queryOrderResponse = null;

		String request_param = sendDataBean.getRequestParamsXML();
		String response_param = sendDataBean.getResponseParamsXML();
		try {
			queryOrderResponse = BeanUtils.copyMapToBean(sendDataBean.getResponseParamsMap(), QueryOrderResponse.class, null);
			updatePayStatByQuery(session, orderPayQueryRequest, queryOrderResponse, request_param, response_param, "1");

			if (!"0".equals(queryOrderResponse.getStatus())) {// 接口通信异常
				logger.error(String.format("银行接口返回,错误,[%s]", queryOrderResponse.getMessage()));
				return 1;// 待支付,需要调用查询接口
			}

			if (!"0".equals(queryOrderResponse.getResult_code())) {// 支付失败
				logger.error(String.format("银行接口返回,错误,[%s]", queryOrderResponse.getErr_msg()));
				return 1;// 待支付,需要调用查询接口
			}

			// SUCCESS—支付成功 REFUND—转入退款 NOTPAY—未支付 CLOSED—已关闭
			// REVOKED—已撤销 USERPAYING—用户支付中 PAYERROR—支付失败(其他原因，如银行返回失败)
			if ("SUCCESS".equals(queryOrderResponse.getTrade_state())) {
				// 支付成功
				return 2;// 支付成功，不需要再调用查询接口 支付状态(1-待支付 2-已支付 3-支付失败)
			}

			if ("USERPAYING".equals(queryOrderResponse.getTrade_state())) {
				// 用户支付中
				return 1;// 支付成功，不需要再调用查询接口 支付状态(1-待支付 2-已支付 3-支付失败)
			}

			// 调用订单查询接口建议：查询6次每隔5秒查询一次（具体的查询次数和时间也可自定义，建议查询时间不低于30秒）
			// 6次查询完成，接口仍未返回成功标识(即查询接口返回的trade_state不是SUCCESS)则调用撤销接口；
			// 未支付成功则全部状态默认为正在支付，直到撤消订单时修改为3-支付失败
			return 3;// 确认银行支付失败，不需要再调用查询接口 支付状态(1-待支付 2-已支付 3-支付失败)
		} catch (RPCException e) {
			throw e;
		} catch (Exception e) {
			logger.error("银行接口,系统异常", e);
			throw new RPCException(3, "银行接口,系统异常");
		}
	}

	/**
	 * Title:订单支付接口 更新订单支付状态<br/>
	 * Description:
	 * 
	 * @param orderPayRequest
	 * @param orderPayResponse
	 * @param request_param 请求串
	 * @param response_params 响应串
	 * @param pay_stat 支付状态(1-待支付 2-已支付 3-支付失败)
	 * @throws RPCException
	 */
	private void updatePayStatByQuery(Session session, QueryOrderRequest queryOrderRequest, QueryOrderResponse queryOrderResponse, String request_param, String response_params, String pay_stat) throws RPCException {
		// SlOrder slOrder = dbUtil.findById("查询订单表", SlOrder.class,
		// queryOrderRequest.getOut_trade_no());
		SlPayType slPayType = dbUtil.queryFirstForBean("查询订单支付表", "SELECT * FROM SL_PAY_TYPE WHERE ORDER_ID=?", SlPayType.class, queryOrderRequest.getOut_trade_no());

		// slOrder.setPayStat(pay_stat);

		slPayType.setPayId(queryOrderResponse.getOut_transaction_id());
		slPayType.setBankId(queryOrderResponse.getTransaction_id());
		slPayType.setResponseParams("订单查询接口返回-" + response_params);

		// dbUtil.updateEntity("更新支付状态", session, slOrder);
		dbUtil.updateEntity("更新支付后数据", session, slPayType);
	}

	@Override
	public void cancelOrderPay(CancelOrderRequest cancelOrderRequest) throws RPCException {
		Map<String, String> requestParamMap = BeanUtils.copyBeanToMap(cancelOrderRequest, null);
		requestParamMap.put("service", BfConstant.REVERSE);
		SendDataBean sendDataBean = sendData(requestParamMap);

		CancelOrderResponse cancelOrderResponse = null;

		try {
			cancelOrderResponse = BeanUtils.copyMapToBean(sendDataBean.getResponseParamsMap(), CancelOrderResponse.class, null);
			if (!"0".equals(cancelOrderResponse.getStatus())) {// 接口通信异常
				throw new RPCException(3, String.format("银行接口返回,错误,[%s]", cancelOrderResponse.getMessage()));
			}

			if (!"0".equals(cancelOrderResponse.getResult_code())) {// 撤消订单失败
				throw new RPCException(3, String.format("银行接口返回,错误,[%s]", cancelOrderResponse.getErr_msg()));
			}

		} catch (RPCException e) {
			throw e;
		} catch (Exception e) {
			logger.error("银行接口,系统异常", e);
			throw new RPCException(3, "银行接口,系统异常");
		}
	}

	@Override
	public void refundOrderPay(RefundOrderRequest tradeOrderRequest) throws RPCException {
		tradeOrderRequest.setTotal_fee(tradeOrderRequest.getTotal_fee() * SwiftpassConfig.unit_type);
		tradeOrderRequest.setRefund_fee(tradeOrderRequest.getRefund_fee() * SwiftpassConfig.unit_type);

		Map<String, String> requestParamMap = BeanUtils.copyBeanToMap(tradeOrderRequest, null);
		requestParamMap.put("service", BfConstant.REFUND);
		SendDataBean sendDataBean = sendData(requestParamMap);
		RefundOrderResponse refundOrderResponse = null;

		try {
			refundOrderResponse = BeanUtils.copyMapToBean(sendDataBean.getResponseParamsMap(), RefundOrderResponse.class, null);
			if (!"0".equals(refundOrderResponse.getStatus())) {// 接口通信异常
				throw new RPCException(3, String.format("银行接口返回,错误,[%s]", refundOrderResponse.getMessage()));
			}

			if (!"0".equals(refundOrderResponse.getResult_code())) {// 申请退款失败
				throw new RPCException(3, String.format("银行接口返回,错误,[%s]", refundOrderResponse.getErr_msg()));
			}

		} catch (RPCException e) {
			throw e;
		} catch (Exception e) {
			throw new RPCException(3, String.format("退款错误,[%s]"));
		}
	}

	@Override
	public void queryRefundOrderPay(QueryRefundOrderRequest queryTradeOrderRequest) throws RPCException {
		Map<String, String> requestParamMap = BeanUtils.copyBeanToMap(queryTradeOrderRequest, null);
		requestParamMap.put("service", BfConstant.REFUNDQUERY);
		SendDataBean sendDataBean = sendData(requestParamMap);

		QueryRefundOrderResponse queryRefundOrderResponse = null;

		try {
			// 开启事务
			queryRefundOrderResponse = BeanUtils.copyMapToBean(sendDataBean.getResponseParamsMap(), QueryRefundOrderResponse.class, null);
			if (!"0".equals(queryRefundOrderResponse.getStatus())) {// 接口通信异常
				throw new RPCException(3, String.format("银行接口返回,错误,[%s]", queryRefundOrderResponse.getMessage()));
			}

			if (!"0".equals(queryRefundOrderResponse.getResult_code())) {// 申请退款失败
				throw new RPCException(3, String.format("银行接口返回,错误,[%s]", queryRefundOrderResponse.getErr_msg()));
			}

			SlRefundTicket slRefundTicket = dbUtil.findById("查询退款表", SlRefundTicket.class, queryTradeOrderRequest.getOut_refund_no());
			slRefundTicket.setRemark("银行返回:" + BfConstant.REFUND_STATUS.get(queryRefundOrderResponse.getRefund_status()));
			dbUtil.updateEntity("更新退款表", slRefundTicket);
			if ("SUCCESS".equals(queryRefundOrderResponse.getRefund_status())) {
				return;
			} else {
				throw new RPCException(3, String.format("银行接口返回,错误,[%s]", BfConstant.REFUND_STATUS.get(queryRefundOrderResponse.getRefund_status())));
			}

		} catch (RPCException e) {
			throw e;
		} catch (Exception e) {
			logger.error("银行接口,系统异常", e);
			throw new RPCException(3, "银行接口,系统异常");
		}
	}

	@Override
	public void billOrder(BillOrderRequest billOrderBean) throws RPCException {
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
					slBillDetail.setOrderFee(CommonUtil.covertDouble(lineSplit[BillOrderDetialEM.ORDER_FEE.getValue()]));
					slBillDetail.setRefundId(lineSplit[BillOrderDetialEM.REFUND_ID.getValue()]);
					slBillDetail.setOutRefundNo(lineSplit[BillOrderDetialEM.OUT_REFUND_NO.getValue()]);
					slBillDetail.setRefundFee(CommonUtil.covertDouble(lineSplit[BillOrderDetialEM.REFUND_FEE.getValue()]));
					slBillDetail.setRefundType(lineSplit[BillOrderDetialEM.REFUND_TYPE.getValue()]);
					slBillDetail.setRefundStatus(lineSplit[BillOrderDetialEM.REFUND_STATUS.getValue()]);
					slBillDetail.setServiceFee(CommonUtil.covertDouble(lineSplit[BillOrderDetialEM.SERVICE_FEE.getValue()]));
					slBillDetail.setCollectFee(CommonUtil.covertDouble(lineSplit[BillOrderDetialEM.COLLECT_FEE.getValue()]));
					slBillDetail.setResponseInfo(line);
					slBillDetail.setBodyStr(lineSplit[BillOrderDetialEM.BODY.getValue()]);
					slBillDetail.setAttach(lineSplit[BillOrderDetialEM.ATTACH.getValue()]);
					slBillDetail.setBillResult("N");// 对账状态 0-对账成功 1-对账失败 N-未对账
					slBillDetailList.add(slBillDetail);

					if (slBillDetail.getTranStatus().equals("支付成功")) {
						slBill.setPayFeeTotal(CommonUtil.covertDouble(slBill.getPayFeeTotal()) + CommonUtil.covertDouble(slBillDetail.getOrderFee()));
						slBill.setPayNumTotal(CommonUtil.covertLong(slBill.getPayNumTotal()) + 1);
					}

					if (slBillDetail.getRefundStatus().equals("退款成功")) {
						slBill.setRefundFeeTotal(CommonUtil.covertDouble(slBill.getRefundFeeTotal()) + CommonUtil.covertDouble(slBillDetail.getRefundFee()));
						slBill.setRefundNumTotal(CommonUtil.covertLong(slBill.getRefundNumTotal()) + 1);
					}
				}

			} else {
				throw new RPCException(3, "银行接口,调用接口错误");
			}

			slBill.setStat("1");
			if (slBillDetailList.size() > 0) {
				slBill.setStatInfo("下载对账单成功");
			} else {
				slBill.setStatInfo("下载对账单成功,无交易数据");
			}
		} catch (RPCException e) {
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

			dbUtil.executeSql("删除对账明细", session, "DELETE FROM SL_BILL_DETAIL WHERE BILL_ID IN (SELECT BILL_ID FROM SL_BILL WHERE BILL_DATE=?)", billOrderBean.getBill_date());
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
	 * @throws RPCException
	 */
	public void beginBill(BillOrderRequest billOrderBean) throws RPCException {
		SlBill slBill = null;
		List<SlOrder> slOrderFailList = new ArrayList<SlOrder>();
		List<SlBillDetail> slBillDetailList = null;
		List<SlRefundTicket> slRefundTicketList = new ArrayList<SlRefundTicket>();
		try {
			slBill = dbUtil.queryFirstForBean("", "SELECT * FROM SL_BILL WHERE BILL_DATE=?", SlBill.class, billOrderBean.getBill_date());
			slBillDetailList = dbUtil.queryListToBean("查询对账明细", "SELECT B.* FROM SL_BILL A,SL_BILL_DETAIL B WHERE A.BILL_ID=B.BILL_ID and BILL_DATE=?", SlBillDetail.class, billOrderBean.getBill_date());

			for (SlBillDetail slBillDetail : slBillDetailList) {
				if ("支付成功".equals(slBillDetail.getTranStatus())) {
					SlOrder slOrder = dbUtil.queryFirstForBean("查询订单表", "SELECT * FROM SL_ORDER WHERE ORDER_ID=?", SlOrder.class, slBillDetail.getOutTradeNo());
					if (slOrder != null && slOrder.getPayStat().equals("2")) {// 对账成功
						slBillDetail.setBillResult("0");// 对账状态 0-对账成功 1-对账失败
					} else {
						slBillDetail.setBillResult("1");
						slBill.setPayNumFail(CommonUtil.covertLong(slBill.getPayNumFail()) + 1);
						slOrder.setPayStat("3");
						slOrderFailList.add(slOrder);
					}
				}

				// 退款成功，更新退款表
				if ("退款成功".equals(slBillDetail.getRefundStatus())) {
					SlRefundTicket slRefundTicketQuery = dbUtil.queryFirstForBean("查询退款表", "SELECT * FROM SL_REFUND_TICKET WHERE REFUND_ID=?", SlRefundTicket.class, slBillDetail.getOutRefundNo());
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
	public static void main(String[] args) throws RPCException {
		logger.debug("加载spring 配值...start");
		ApplicationContext factory = new ClassPathXmlApplicationContext(new String[] { "spring-common.xml", "spring-hibernate.xml" });
		logger.debug("加载spring 配值...end");

		PayUtilByBfBank payUtilByBfBank = factory.getBean(PayUtilByBfBank.class);
		BillOrderRequest billOrderBean = new BillOrderRequest();
		billOrderBean.setBill_date("20170818");
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
