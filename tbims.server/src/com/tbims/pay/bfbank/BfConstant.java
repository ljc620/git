package com.tbims.pay.bfbank;

import java.util.HashMap;
import java.util.Map;

/**
 * Title: 浦发银行接口，常量 <br/>
 * Description:
 * 
 * @ClassName: BfConstant
 * @author ydc
 * @date 2017年8月9日 下午4:37:21
 * 
 */
public class BfConstant {
	/**
	 * 提交刷卡支付
	 */
	public static final String MICROPAY = "unified.trade.micropay";

	/**
	 * 查询订单
	 */
	public static final String QUERY = "unified.trade.query";

	/**
	 * 撤销订单
	 */
	public static final String REVERSE = "unified.micropay.reverse";

	/**
	 * 申请退款
	 */
	public static final String REFUND = "unified.trade.refund";

	/**
	 * 查询退款
	 */
	public static final String REFUNDQUERY = "unified.trade.refundquery";

	/**
	 * 程序标识
	 */
	public static final String BILL_FLAG = "tbims.server.rpc";

	/**
	 * 对账类型
	 */
	public static final String BILL_TYPE = "ALL";

	/**
	 * 退款状态
	 */
	public static final Map<String, String> REFUND_STATUS = new HashMap<String, String>();
	static {
		REFUND_STATUS.put("SUCCESS", "退款成功");
		REFUND_STATUS.put("FAIL", "退款失败");
		REFUND_STATUS.put("PROCESSING", "退款处理中");
		REFUND_STATUS.put("NOTSURE", "未确定， 需要商户原退款单号重新发起");
		REFUND_STATUS.put("CHANGE", "转入代发，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，资金回流到商户的现金帐号，需要商户人工干预，通过线下或者平台转账的方式进行退款");
	}
}
