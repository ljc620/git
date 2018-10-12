package com.tbims.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.tbims.bean.ApplyDetailBean;
import com.tbims.bean.DeliveryApplyBean;
import com.tbims.bean.DeliveryApplyDetailBean;
import com.tbims.entity.StrDeliveryApply;
import com.tbims.entity.StrDeliveryApplyDetail;
import com.tbims.entity.StrDeliveryDetail;
import com.tbims.entity.StrOutletInfo;
import com.tbims.entity.StrOutletInfoId;
import com.tbims.entity.SysTicketType;
import com.tbims.service.IDeliveryService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.BaseException;
import com.zhming.support.util.CommonUtil;
import com.zhming.support.util.DateUtil;
import com.zhming.support.util.StringUtil;

@Service
public class DeliveryService extends BaseService implements IDeliveryService {

	/**
	 * Title: 减少网点库存<br/>
	 * Description:
	 * 
	 * @param session
	 * @param outletId
	 * @param ticketTypeId
	 * @param ticketNum
	 */
	@SuppressWarnings("unused")
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
	
	private void checkOutletInfos(Long outletId, String ticketTypeId) {
		if (outletId == null || outletId == 0) {
			return;
		}

		List<SysTicketType> sysTicketTypeList = dbUtil.queryListToBean("", "SELECT * FROM SYS_TICKET_TYPE WHERE TICKET_TYPE_ID=?", SysTicketType.class, ticketTypeId);
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
	public PageBean<DeliveryApplyBean> listDeliveryApply(UserBean loginUserBean, StrDeliveryApply strDeliveryApply) throws BaseException {
		StringBuffer sb = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		sb.append(" SELECT DAPPLY.APPLY_ID,      ");
		sb.append(" DAPPLY.APPLY_USER_ID,        ");
		sb.append(" SUSERAPP.USER_NAME AS APPLY_USER_NAME , ");
		sb.append(" DAPPLY.APPLY_TIME,           ");
		sb.append(" DAPPLY.OUTLET_ID,     ");
		sb.append(" OUTLET.OUTLET_NAME,   ");
		sb.append(" SYS_DICTIONARY.ITEM_NAME OUTLET_TYPE,   ");
		sb.append(" ORG.ORG_NAME,   ");
		sb.append(" DAPPLY.EXAM_USER_ID,         ");
		sb.append(" SUSEREXAM.USER_NAME AS EXAM_USER_NAME , ");
		sb.append(" DAPPLY.EXAM_TIME,            ");
		sb.append(" DAPPLY.EXAM_STAT,            ");
		sb.append(" DAPPLY.APPLY_DELIVERY_TIME,            ");
		sb.append(" SUSERDELIVERY.USER_NAME AS DELIVERY_USER_NAME , ");
		sb.append(" DAPPLY.DELIVERY_USER_ID,     ");
		sb.append(" DAPPLY.DELIVERY_TIME         ");
		sb.append("  FROM STR_DELIVERY_APPLY DAPPLY ");
		sb.append(" INNER JOIN SYS_OUTLET OUTLET ");
		sb.append("    ON DAPPLY.OUTLET_ID = OUTLET.OUTLET_ID ");
		sb.append(" LEFT JOIN SYS_DICTIONARY  ");
		sb.append("    ON OUTLET.OUTLET_TYPE = SYS_DICTIONARY.ITEM_CD AND SYS_DICTIONARY.kEY_CD='OUTLET_TYPE'");
		sb.append(" INNER JOIN SYS_USER SUSERAPP ");
		sb.append("    ON DAPPLY.APPLY_USER_ID = SUSERAPP.USER_ID ");
		sb.append(" LEFT JOIN SYS_USER SUSEREXAM ");
		sb.append("    ON DAPPLY.EXAM_USER_ID = SUSEREXAM.USER_ID ");
		sb.append(" LEFT JOIN SYS_USER SUSERDELIVERY ");
		sb.append("    ON DAPPLY.DELIVERY_USER_ID = SUSERDELIVERY.USER_ID ");
		sb.append("  LEFT JOIN SL_ORG ORG ");
		sb.append("    ON OUTLET.ORG_ID = ORG.ORG_ID WHERE 1=1");
		sb.append("	AND DAPPLY.OUTLET_ID=:OUTLET_ID");
		params.put("OUTLET_ID", loginUserBean.getOutletId());
		if (strDeliveryApply != null) {
			if (StringUtil.isNotNull(strDeliveryApply.getExamStat())) {
				sb.append(" AND DAPPLY.EXAM_STAT = :EXAM_STAT ");
				params.put("EXAM_STAT", strDeliveryApply.getExamStat());
			}
			if(StringUtil.isNull(strDeliveryApply.getExamStat())){
				sb.append("	AND DAPPLY.EXAM_STAT IN ('0','1','2')");//0待审核1已审核2已配送
			}
			if (StringUtil.isNotNull(strDeliveryApply.getApplyId())) {
				sb.append(" AND DAPPLY.APPLY_ID=:APPLY_ID ");
				params.put("APPLY_ID", strDeliveryApply.getApplyId());
			}
		}
		sb.append(" ORDER BY  DAPPLY.APPLY_TIME DESC");
		String sql = sb.toString();
		PageBean<DeliveryApplyBean> ret = dbUtil.queryPageToBean("查询配送申请列表", sql, DeliveryApplyBean.class, loginUserBean.getPageNum(), loginUserBean.getPageSize(), params);
		return ret;
	}

	@Override
	public List<DeliveryApplyDetailBean> applyExamDetail(String applyId) throws BaseException {
		StringBuffer sb = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		sb.append(" SELECT T.APPLY_ID,          ");
		sb.append("        T.APPLY_EXAM_ID,     ");
		sb.append("        T.TICKET_TYPE_ID,    ");
		sb.append("        TP.TICKET_TYPE_NAME, ");
		sb.append("        T.APPLY_NUM,         ");
		sb.append("        T.EXAM_NUM           ");
		sb.append("   FROM STR_DELIVERY_APPLY_DETAIL T  ");
		sb.append("  INNER JOIN SYS_TICKET_TYPE TP ");
		sb.append("     ON T.TICKET_TYPE_ID = TP.TICKET_TYPE_ID ");
		sb.append("  WHERE T.APPLY_ID = :APPLY_ID");
		params.put("APPLY_ID", applyId);
		String sql = sb.toString();
		List<DeliveryApplyDetailBean> ret = dbUtil.queryListToBean("申请明细查询", sql, DeliveryApplyDetailBean.class, params);
		return ret;
	}

	@Override
	public List<Map<String, Object>> deliveryDetail(String applyId) throws BaseException {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT DETAIL.APPLY_ID,DETAIL.CHEST_ID, ");
		sb.append("     DETAIL.TICKET_TYPE_ID,");
		sb.append("     TY.TICKET_TYPE_NAME,");
		sb.append("     DETAIL.END_NO,");
		sb.append("     DETAIL.BEGIN_NO ");
		sb.append(" FROM STR_DELIVERY_DETAIL DETAIL");
		sb.append(" INNER JOIN SYS_TICKET_TYPE TY");
		sb.append("  ON DETAIL.TICKET_TYPE_ID = TY.TICKET_TYPE_ID");
		sb.append(" WHERE DETAIL.APPLY_ID=:APPLY_ID ");
		params.put("APPLY_ID", applyId);
		String sql = sb.toString();
		List<Map<String, Object>> ret = dbUtil.queryListToMap("查询配送明细列表", sql, params);
		return ret;
	}

	@Override
	public void confrimReceive(UserBean userBean, String applyId, String examStat) throws BaseException {
		Session session = null;

		try {
			// 开启事务
			session = dbUtil.getSessionByTransaction();
			// 查询订单
			//StrDeliveryApply strDeliveryApply = dbUtil.findById("", StrDeliveryApply.class, applyId);
			StrDeliveryApply strDeliveryApply = dbUtil.queryFirstListToBeanLock("", session, "FROM StrDeliveryApply U where applyId=?", "U", StrDeliveryApply.class, applyId);
			if (strDeliveryApply != null) {
				if (!"2".equals(strDeliveryApply.getExamStat())) {
					throw new BaseException(MSG.BF_ERROR, "订单不是已配送状态,不能接收");
				}
			} else {
				throw new BaseException(MSG.BF_ERROR, "订单号不存在");
			}

			// 修改状态为3-已接收4-已拒收
			strDeliveryApply.setExamStat(examStat);// 申请单为已配送，此时设为已接收
			strDeliveryApply.setSignUserId(userBean.getUserId());
			strDeliveryApply.setSignTime(new Date());
			dbUtil.updateEntity("修改订单状态", session, strDeliveryApply);

			Long outletId = userBean.getOutletId();

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
			//dbUtil.executeSql("更新门票库存表状态", session, "UPDATE STR_TICKET_INFO T SET STAT=?,STORE_ID=?,SIGN_TIME=? " + "WHERE STORE_ID IS NULL AND EXISTS (SELECT 1 FROM STR_DELIVERY_DETAIL A  WHERE APPLY_ID =? AND "+ "T.TICKET_ID BETWEEN A.BEGIN_NO AND A.END_NO )", ticketState, ticketStoreId, new Date(), applyId);
			List<StrDeliveryDetail> strDeliveryDetailListUpdate= dbUtil.queryListToBean("查询配送明细", "SELECT * FROM STR_DELIVERY_DETAIL WHERE APPLY_ID=?", StrDeliveryDetail.class, applyId);
			for(StrDeliveryDetail strDeliveryDetail:strDeliveryDetailListUpdate){
				dbUtil.executeSql("更新门票库存表状态", session, "UPDATE STR_TICKET_INFO T SET STAT=?,STORE_ID=?,SIGN_TIME=? WHERE STORE_ID IS NULL AND TICKET_ID between ? AND ?", ticketState, ticketStoreId, new Date(), strDeliveryDetail.getBeginNo(),strDeliveryDetail.getEndNo());
			}
			
			// 批量保存更新网点库存
			if ("3".equals(examStat)) {
				List<Map<String, Object>> strDeliveryDetailList = dbUtil.queryListToMap("汇总配送明细数量",
						"SELECT TT.TICKET_TYPE_ID,SUM(TT.END_NO-TT.BEGIN_NO+1) TICKET_NUM" + " FROM STR_DELIVERY_DETAIL TT WHERE APPLY_ID = ?  GROUP BY TT.TICKET_TYPE_ID ", applyId);

				for (Map<String, Object> strDeliveryDetail : strDeliveryDetailList) {
					String ticketTypeId = StringUtil.convertString(strDeliveryDetail.get("ticketTypeId"));
					Long ticketNum = CommonUtil.covertLong((strDeliveryDetail.get("ticketNum")));
					checkOutletInfos(outletId, ticketTypeId);
					addOutletInfos(session, outletId, ticketTypeId, ticketNum);
				}

			}

			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}
	}

	@Override
	public void saveApply(UserBean userBean, String applyDeliveryTime, List<ApplyDetailBean> applyDetail) throws Exception {
		Session session = null;

		try {
			// 开启事务
			session = dbUtil.getSessionByTransaction();

			// 配送申请 审核状态(0待审核1已审核2已配送3已接收4已拒收5已退回)
			StrDeliveryApply deliveryApply = new StrDeliveryApply();
			deliveryApply.setApplyId(userBean.getOutletId() + "SQ" + DateUtil.getNowDate("yyyyMMddHHmmss") + "1");// 申请编号
			deliveryApply.setApplyUserId(userBean.getUserId());
			deliveryApply.setApplyTime(new Date());
			deliveryApply.setOutletId(userBean.getOutletId());
			deliveryApply.setApplyDeliveryTime(DateUtil.formatStringToDate(applyDeliveryTime, "yyyy-MM-dd HH:mm:ss"));
			deliveryApply.setExamStat("0");// 0待审核

			// 配送申请明细
			List<StrDeliveryApplyDetail> strDeliveryApplyDetails = new ArrayList<StrDeliveryApplyDetail>();

			int i = 1;
			for (ApplyDetailBean applyDetailBean : applyDetail) {
				StrDeliveryApplyDetail strDeliveryApplyDetail = new StrDeliveryApplyDetail();
				// 默认审核数量为申请数量
				strDeliveryApplyDetail.setApplyExamId(userBean.getOutletId() + "SQMX" + DateUtil.getNowDate("yyyyMMddHHmmss") + i);// 申请明细编号
				strDeliveryApplyDetail.setStrDeliveryApply(deliveryApply);
				strDeliveryApplyDetail.setTicketTypeId(applyDetailBean.getTicketTypeId());
				strDeliveryApplyDetail.setApplyNum(applyDetailBean.getApplyNum());
				strDeliveryApplyDetail.setExamNum(applyDetailBean.getApplyNum());
				strDeliveryApplyDetails.add(strDeliveryApplyDetail);
				i++;
			}
			deliveryApply.setStrDeliveryApplyDetails(strDeliveryApplyDetails);
			dbUtil.saveOrUpdateEntity("批量保存配送申请", session, deliveryApply);

			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}
	}

	@Override
	public PageBean<DeliveryApplyBean> listDeliveryApplyH(UserBean loginUserBean, String applyId, String examStat, String beginApplyTime, String endApplyTime) throws Exception {
		StringBuffer sb = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		sb.append(" SELECT DAPPLY.APPLY_ID,      ");
		sb.append(" DAPPLY.APPLY_USER_ID,        ");
		sb.append(" SUSERAPP.USER_NAME AS APPLY_USER_NAME , ");
		sb.append(" DAPPLY.APPLY_TIME,           ");
		sb.append(" DAPPLY.OUTLET_ID,     ");
		sb.append(" OUTLET.OUTLET_NAME,   ");
		sb.append(" SYS_DICTIONARY.ITEM_NAME OUTLET_TYPE,   ");
		sb.append(" ORG.ORG_NAME,   ");
		sb.append(" DAPPLY.EXAM_USER_ID,         ");
		sb.append(" SUSEREXAM.USER_NAME AS EXAM_USER_NAME , ");
		sb.append(" DAPPLY.EXAM_TIME,            ");
		sb.append(" DAPPLY.EXAM_STAT,            ");
		sb.append(" DAPPLY.APPLY_DELIVERY_TIME,            ");
		sb.append(" SUSERDELIVERY.USER_NAME AS DELIVERY_USER_NAME , ");
		sb.append(" DAPPLY.DELIVERY_USER_ID,     ");
		sb.append(" DAPPLY.DELIVERY_TIME         ");
		sb.append("  FROM STR_DELIVERY_APPLY DAPPLY ");
		sb.append(" INNER JOIN SYS_OUTLET OUTLET ");
		sb.append("    ON DAPPLY.OUTLET_ID = OUTLET.OUTLET_ID ");
		sb.append(" LEFT JOIN SYS_DICTIONARY  ");
		sb.append("    ON OUTLET.OUTLET_TYPE = SYS_DICTIONARY.ITEM_CD AND SYS_DICTIONARY.kEY_CD='OUTLET_TYPE'");
		sb.append(" INNER JOIN SYS_USER SUSERAPP ");
		sb.append("    ON DAPPLY.APPLY_USER_ID = SUSERAPP.USER_ID ");
		sb.append(" LEFT JOIN SYS_USER SUSEREXAM ");
		sb.append("    ON DAPPLY.EXAM_USER_ID = SUSEREXAM.USER_ID ");
		sb.append(" LEFT JOIN SYS_USER SUSERDELIVERY ");
		sb.append("    ON DAPPLY.DELIVERY_USER_ID = SUSERDELIVERY.USER_ID ");
		sb.append("  LEFT JOIN SL_ORG ORG ");
		sb.append("    ON OUTLET.ORG_ID = ORG.ORG_ID WHERE 1=1");
		sb.append("	AND DAPPLY.OUTLET_ID=:OUTLET_ID");
		params.put("OUTLET_ID", loginUserBean.getOutletId());
		if (StringUtil.isNotNull(examStat)) {
			sb.append(" AND DAPPLY.EXAM_STAT =:EXAM_STAT ");
			params.put("EXAM_STAT", examStat);
		}
		if (StringUtil.isNotNull(applyId)) {
			sb.append(" AND DAPPLY.APPLY_ID=:APPLY_ID ");
			params.put("APPLY_ID", applyId);
		}
		if (StringUtil.isNotNull(beginApplyTime) && StringUtil.isNotNull(endApplyTime)) {
			sb.append(" AND DAPPLY.APPLY_TIME BETWEEN :BEGINAPPLYTIME AND :ENDAPPLYTIME ");
			params.put("BEGINAPPLYTIME", DateUtil.formatStringToDate(beginApplyTime, "yyyy-MM-dd HH:mm:ss"));
			params.put("ENDAPPLYTIME", DateUtil.formatStringToDate(endApplyTime, "yyyy-MM-dd HH:mm:ss"));
		}
		sb.append(" ORDER BY  DAPPLY.APPLY_TIME DESC");
		String sql = sb.toString();
		PageBean<DeliveryApplyBean> ret = dbUtil.queryPageToBean("查询配送申请列表", sql, DeliveryApplyBean.class, loginUserBean.getPageNum(), loginUserBean.getPageSize(), params);
		return ret;
	}

	@Override
	public void checkApplyNum(UserBean loginUserBean, Long applyNum) throws BaseException {
		Long standardNum=Long.valueOf(getParemeterVal("delivery_apply_num"));
		if(applyNum%standardNum!=0){
			throw new BaseException(MSG.BF_ERROR, "申请门票数量必须为【"+standardNum+"张】的整数倍，请重新输入 ！");
		}
	}
}
