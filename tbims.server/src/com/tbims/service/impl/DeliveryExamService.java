package com.tbims.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.tbims.Constant;
import com.tbims.bean.ChestBean;
import com.tbims.bean.DeliveryApplyBean;
import com.tbims.bean.DeliveryApplyDetailBean;
import com.tbims.entity.StrDeliveryApply;
import com.tbims.entity.StrDeliveryApplyDetail;
import com.tbims.entity.StrDeliveryDetail;
import com.tbims.service.IDeliveryExamService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.StringUtil;
import com.zhming.support.util.UUIDGenerator;

@Service
public class DeliveryExamService extends BaseService implements IDeliveryExamService {
	@Override
	public PageBean<DeliveryApplyBean> listDeliveryApply(UserBean userBean, StrDeliveryApply strDeliveryApply) {
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
		if (strDeliveryApply != null) {
			if (StringUtil.isNotNull(strDeliveryApply.getExamStat())) {
				sb.append(" AND DAPPLY.EXAM_STAT=:EXAM_STAT ");
				params.put("EXAM_STAT", strDeliveryApply.getExamStat());
			}
			if (StringUtil.isNotNull(strDeliveryApply.getApplyId())) {
				sb.append(" AND DAPPLY.APPLY_ID=:APPLY_ID ");
				params.put("APPLY_ID", strDeliveryApply.getApplyId());
			}
		}
		sb.append(" ORDER BY  DAPPLY.APPLY_TIME DESC");
		String sql = sb.toString();
		PageBean<DeliveryApplyBean> ret = dbUtil.queryPageToBean("查询配送申请列表", sql, DeliveryApplyBean.class, userBean.getPageNum(), userBean.getPageSize(), params);
		return ret;
	}

	@Override
	public DeliveryApplyBean getDeliveryApply(String applyId) {
		StringBuffer sb = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		sb.append(" SELECT DAPPLY.APPLY_ID,      ");
		sb.append(" DAPPLY.APPLY_USER_ID,        ");
		sb.append(" SUSERAPP.USER_NAME AS APPLY_USER_NAME , ");
		sb.append(" DAPPLY.APPLY_TIME,           ");
		sb.append(" DAPPLY.OUTLET_ID,     ");
		sb.append(" OUTLET.OUTLET_NAME,   ");
		// sb.append(" OUTLET.OUTLET_TYPE, ");
		sb.append(" DIC.ITEM_NAME OUTLET_TYPE,   ");
		sb.append(" ORG.ORG_NAME,   ");
		sb.append(" DAPPLY.EXAM_USER_ID,         ");
		sb.append(" SUSEREXAM.USER_NAME AS EXAM_USER_NAME , ");
		sb.append(" DAPPLY.EXAM_TIME,            ");
		sb.append(" DAPPLY.EXAM_STAT,            ");
		sb.append(" DAPPLY.DELIVERY_USER_ID,     ");
		sb.append(" DAPPLY.DELIVERY_TIME         ");
		sb.append("  FROM STR_DELIVERY_APPLY DAPPLY ");
		sb.append(" INNER JOIN SYS_OUTLET OUTLET ");
		sb.append("    ON DAPPLY.OUTLET_ID = OUTLET.OUTLET_ID ");
		sb.append(" INNER JOIN SYS_USER SUSERAPP ");
		sb.append("    ON DAPPLY.APPLY_USER_ID = SUSERAPP.USER_ID ");
		sb.append(" LEFT JOIN SYS_USER SUSEREXAM ");
		sb.append("    ON DAPPLY.EXAM_USER_ID = SUSEREXAM.USER_ID ");
		sb.append(" LEFT JOIN SYS_DICTIONARY DIC ");
		sb.append("    ON OUTLET.OUTLET_TYPE = DIC.ITEM_VAL AND DIC.KEY_CD='OUTLET_TYPE' ");
		sb.append("  LEFT JOIN SL_ORG ORG ");
		sb.append("    ON OUTLET.ORG_ID = ORG.ORG_ID WHERE  DAPPLY.APPLY_ID=:APPLY_ID");
		params.put("APPLY_ID", applyId);
		String sql = sb.toString();
		DeliveryApplyBean deliveryApplyBean = dbUtil.queryFirstForBean("根据申请编号查询申请", sql, DeliveryApplyBean.class, params);
		return deliveryApplyBean;
	}

	@Override
	public List<DeliveryApplyDetailBean> applyDetail(String applyId) {
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
	public void examDelivery(UserBean userBean, StrDeliveryApply strDeliveryApply, List<DeliveryApplyDetailBean> applyDetailList) throws Exception {
		StrDeliveryApply strDeliveryApplyOld = dbUtil.findById("查询申请表", StrDeliveryApply.class, strDeliveryApply.getApplyId());
		strDeliveryApplyOld.setExamTime(new Date());
		strDeliveryApplyOld.setExamUserId(userBean.getUserId());
		strDeliveryApplyOld.setExamStat(strDeliveryApply.getExamStat());
		Session session = dbUtil.getSessionByTransaction();
		try {
			// 审核数量修改
			if (Constant.DELIVERY_EXAM_STAT_1.equals(strDeliveryApply.getExamStat())) {
				List<StrDeliveryApplyDetail> strDeliveryApplyDetails = new ArrayList<StrDeliveryApplyDetail>();
				for (DeliveryApplyDetailBean deliveryApplyDetailBean : applyDetailList) {
					// String
					// ticketTypeId=deliveryApplyDetailBean.getTicketTypeId();
					Long examNum = deliveryApplyDetailBean.getExamNum();
					// Long
					// perChestNum=Long.valueOf(getParemeterVal("boxnum_chest"))*Long.valueOf(getParemeterVal("ticketnum_box"));
					// //获取非整箱数量
					// Long remain=examNum%perChestNum;
					// if(remain!=0){
					// Map<String, Object> params = new HashMap<String,
					// Object>();
					// String sql="SELECT * FROM STR_CHEST T WHERE
					// T.TICKET_TYPE_ID=:TICKET_TYPE_ID AND
					// T.TICKET_NUM=:TICKET_NUM AND
					// T.STAT='"+Constant.CHEST_STAT_001+"'";
					// params.put("TICKET_TYPE_ID", ticketTypeId);
					// params.put("TICKET_NUM", remain);
					// int countNum = dbUtil.count("获取数量为非整箱数量的箱数", sql,
					// params);
					// if(countNum==0){
					// throw new ServiceException(MSG.ERROR,"库房没有满足票数的箱！");
					// }
					// }
					StrDeliveryApplyDetail strDeliveryApplyDetail = dbUtil.findById("", StrDeliveryApplyDetail.class, deliveryApplyDetailBean.getApplyExamId());
					strDeliveryApplyDetail.setExamNum(examNum);
					strDeliveryApplyDetails.add(strDeliveryApplyDetail);
				}
				strDeliveryApplyOld.setStrDeliveryApplyDetails(strDeliveryApplyDetails);
			}
			dbUtil.updateEntity("更新申请表", session, strDeliveryApplyOld);
			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}

	}

	@Override
	public List<Map<String, Object>> detailList(String applyId) {
		StringBuffer sb = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		sb.append(" SELECT DETAIL.DELIVERY_DETAIL_ID, ");
		sb.append(" DETAIL.CHEST_ID, ");
		sb.append(" DETAIL.APPLY_ID, ");
		sb.append(" CHEST.TICKET_TYPE_ID, ");
		sb.append(" TY.TICKET_TYPE_NAME, ");
		sb.append(" CHEST.TICKET_NUM ");
		sb.append("  FROM STR_DELIVERY_DETAIL DETAIL ");
		sb.append(" INNER JOIN STR_CHEST CHEST ");
		sb.append("    ON DETAIL.CHEST_ID = CHEST.CHEST_ID ");
		sb.append(" INNER JOIN SYS_TICKET_TYPE TY ");
		sb.append("    ON DETAIL.TICKET_TYPE_ID = TY.TICKET_TYPE_ID ");
		sb.append(" WHERE APPLY_ID =:APPLY_ID ");
		String sql = sb.toString();
		List<Map<String, Object>> ret = dbUtil.queryListToMap("查询配送明细", sql, params);
		return ret;
	}

	@Override
	public List<Map<String, Object>> getTicketNo(String chestId, String boxId) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT MAX(T.TICKET_ID) END_NUM, ");
		sb.append(" MIN(T.TICKET_ID) BEGIN_NUM ");
		sb.append(" FROM STR_TICKET_INFO T ");
		sb.append(" INNER JOIN SYS_TICKET_TYPE TY ");
		sb.append(" ON T.TICKET_TYPE_ID = TY.TICKET_TYPE_ID ");
		sb.append(" WHERE 1=1 ");

		// 箱号
		if (StringUtil.isNotNull(chestId)) {
			params.put("CHEST_ID", chestId);
			sb.append(" AND T.CHEST_ID=:CHEST_ID ");
		}
		// 盒号
		if (StringUtil.isNotNull(boxId)) {
			params.put("BOX_ID", boxId);
			sb.append(" AND T.BOX_ID=:BOX_ID ");
		}
		List<Map<String, Object>> ret = dbUtil.queryListToMap("查询配送明细", sb.toString(), params);
		return ret;
	}

	@Override
	public List<Map<String, Object>> listChest(String chestId, String boxId, Long beginNo, Long endNo) throws BaseException {
		if (chestId == null && boxId == null && beginNo == null && endNo == null) {
			return new ArrayList<Map<String, Object>>();
		}

		StringBuffer chestIdQuery=new StringBuffer();
		String[] chestIdArray=chestId.split("[,，\\s]");
		for(String cid:chestIdArray){
			if(chestIdQuery.length()!=0){
				chestIdQuery.append(",");
			}
			chestIdQuery.append(StringUtil.queryParamByString(cid));
		}
		
		StringBuffer boxIdQuery=new StringBuffer();
		String[] boxIdArray=boxId.split("[,，\\s]");
		for(String bid:boxIdArray){
			if(boxIdQuery.length()!=0){
				boxIdQuery.append(",");
			}
			boxIdQuery.append(StringUtil.queryParamByString(bid));
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT U.CHEST_ID, ");
		sql.append(" U.BOX_ID, ");
		sql.append(" U.TICKET_TYPE_ID, ");
		sql.append(" U.TICKET_TYPE_NAME, ");
		sql.append(" MAX(U.TICKET_ID) END_NO, ");
		sql.append(" MIN(U.TICKET_ID) BEGIN_NO, ");
		sql.append(" SUM(1) TICKET_NUM   ");
		sql.append(" FROM (SELECT T.TICKET_TYPE_ID, ");
		sql.append(" TY.TICKET_TYPE_NAME,T.BOX_ID, ");
		sql.append(" T.CHEST_ID, ");
		sql.append(" T.TICKET_ID, ");
		sql.append(" T.STAT, ");
		sql.append(" (T.TICKET_ID - ROW_NUMBER() OVER(ORDER BY T.TICKET_ID)) CNT ");
		sql.append(" FROM STR_TICKET_INFO T ");
		sql.append(" INNER JOIN SYS_TICKET_TYPE TY ");
		sql.append(" ON T.TICKET_TYPE_ID = TY.TICKET_TYPE_ID ");
		sql.append(" WHERE T.STORE_ID IS NULL AND T.STAT IN ('001','003')");
		// 箱号
		if (StringUtil.isNotNull(chestId)) {
			//params.put("CHEST_ID", chestId);

			sql.append(" AND T.CHEST_ID in (" + chestIdQuery.toString() + ") ");
		}
		// 盒号
		if (StringUtil.isNotNull(boxId)) {
			//params.put("BOX_ID", boxId);
			sql.append(" AND T.BOX_Id in (" + boxIdQuery.toString() + ") ");
		}
		// 票号起号--票号止号
		if (beginNo != null && endNo != null) {
			params.put("BEGIN_NO", beginNo);
			params.put("END_NO", endNo);
			sql.append(" AND T.TICKET_ID>=:BEGIN_NO AND T.TICKET_ID<=:END_NO ");
		}
		sql.append(" ) U ");
		sql.append(" WHERE 1=1 ");

		sql.append(" GROUP BY U.CHEST_ID,U.BOX_ID,U.TICKET_TYPE_ID,U.TICKET_TYPE_NAME,U.CNT ");
		sql.append(" ORDER BY U.CHEST_ID,U.BOX_ID,U.TICKET_TYPE_ID,U.TICKET_TYPE_NAME,U.CNT ");
		List<Map<String, Object>> ret = dbUtil.queryListToMap("查询配送明细", sql.toString(), params);
		return ret;
	}

	@Override
	public void confirmDelivery(UserBean userBean, String applyId, List<ChestBean> chestList) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		// 扫描每个票种的张数MAP
		Map<String, Long> chestNumMap = new HashMap<String, Long>();
		// 申请明细审核的张数
		Map<String, Long> examNumMap = new HashMap<String, Long>();
		Session session = dbUtil.getSessionByTransaction();
		try {
			List<StrDeliveryDetail> strDeliveryDetails = new ArrayList<StrDeliveryDetail>();
			// List<StrChest> strChestList=new ArrayList<StrChest>();
			// List<StrTicketInfo> StrTicketInfoList = new
			// ArrayList<StrTicketInfo>();
			StrDeliveryApply strDeliveryApply = dbUtil.findById("获取申请信息", StrDeliveryApply.class, applyId);
			strDeliveryApply.setDeliveryUserId(userBean.getUserId());
			strDeliveryApply.setDeliveryTime(new Date());
			strDeliveryApply.setExamStat(Constant.DELIVERY_EXAM_STAT_2);
			for (int i = 0; i < chestList.size(); i++) {
				String ticketTypeId = chestList.get(i).getTicketTypeId();
				String chestId = chestList.get(i).getChestId();
				Long ticketNum = chestList.get(i).getTicketNum();
				Long beginNo = chestList.get(i).getBeginNo();
				Long endNo = chestList.get(i).getEndNo();
				/*
				 * 注释原因，不按箱配送，将一些字段加到门票库存表中，更新库存表 StrChest
				 * strChest=dbUtil.findById("查询箱信息", StrChest.class, chestId);
				 * if(!Constant.CHEST_STAT_001.equals(strChest.getStat())){
				 * throw new ServiceException(MSG.ERROR,"箱号为："+chestId+"状态不匹配");
				 * } strChest.setOpeUserId(userBean.getUserId());;
				 * //strChest.setDeliveryTime(new Date());
				 * strChest.setStat(Constant.CHEST_STAT_002);
				 * strChestList.add(strChest);
				 */
				// 判断票号是否连续
				isContinuous(beginNo, endNo);// 增加2017/09/26
				// 修改门票库存表的门票状态
				dbUtil.executeSql("更新门票库存表状态", session, "UPDATE STR_TICKET_INFO T SET STAT=?,DELIVERY_TIME=? WHERE T.TICKET_ID BETWEEN ? AND ? ", Constant.CHEST_STAT_005, new Date(), beginNo, endNo);
				/*
				 * for (Long j = beginNo; j < endNo + 1; j++) { StrTicketInfo
				 * strTicket = dbUtil.findById("查询门票信息", StrTicketInfo.class,
				 * j); if (strTicket != null) { if
				 * (Constant.CHEST_STAT_001.equals(strTicket.getStat())) {
				 * strTicket.setDeliveryTime(new Date());
				 * strTicket.setStat(Constant.CHEST_STAT_005);
				 * StrTicketInfoList.add(strTicket); } } }
				 */
				if (chestNumMap.containsKey(ticketTypeId)) {
					chestNumMap.put(ticketTypeId, chestNumMap.get(ticketTypeId) + ticketNum);
				} else {
					chestNumMap.put(ticketTypeId, ticketNum);
				}
				StrDeliveryDetail strDeliveryDetail = new StrDeliveryDetail();
				strDeliveryDetail.setChestId(chestId);
				strDeliveryDetail.setTicketTypeId(ticketTypeId);
				strDeliveryDetail.setDeliveryDetailId(UUIDGenerator.getPK());
				strDeliveryDetail.setStrDeliveryApply(strDeliveryApply);
				strDeliveryDetail.setBeginNo(beginNo);// 票号起号
				strDeliveryDetail.setEndNo(endNo);// 票号止号
				strDeliveryDetails.add(strDeliveryDetail);
			}
			strDeliveryApply.setStrDeliveryDetails(strDeliveryDetails);
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT T.TICKET_TYPE_ID, SUM(T.EXAM_NUM) AS TICKET_NUM ");
			sb.append("   FROM STR_DELIVERY_APPLY_DETAIL T ");
			sb.append("  WHERE T.APPLY_ID =:APPLY_ID ");
			sb.append("  GROUP BY T.TICKET_TYPE_ID ");
			String sql = sb.toString();
			params.put("APPLY_ID", applyId);
			List<Map<String, Object>> mapList = dbUtil.queryListToMap("查询每个票种的票数量", sql, params);
			for (int i = 0; i < mapList.size(); i++) {
				Map<String, Object> tnum = mapList.get(i);
				examNumMap.put(tnum.get("ticketTypeId").toString(), Long.valueOf(tnum.get("ticketNum").toString()));
			}
			if (chestNumMap.size() != examNumMap.size()) {
				throw new ServiceException(MSG.ERROR, "票种数量不一致");
			} else {
				/*
				 * for(Map.Entry<String, Long> entry:chestNumMap.entrySet()){
				 * String chestKey=entry.getKey(); Long
				 * chestValue=entry.getValue(); Long
				 * examValue=examNumMap.get(chestKey);
				 * if(chestValue.longValue()!=examValue.longValue()){ throw new
				 * ServiceException(MSG.ERROR,"票种内票数量不一致"); } }
				 */
				Iterator<String> it = chestNumMap.keySet().iterator();
				while (it.hasNext()) {
					String chestKey = (String) it.next();
					if (examNumMap.containsKey(chestKey)) {
						Long chestValue = chestNumMap.get(chestKey);
						Long examValue = examNumMap.get(chestKey);
						if (chestValue.longValue() != examValue.longValue()) {
							throw new ServiceException(MSG.ERROR, "票种内票数量不一致");
						}
					} else {
						throw new ServiceException(MSG.ERROR, "票种数量不一致");
					}
				}

			}
			// dbUtil.updateEntityBatch("批量更新箱表状态", session, StrTicketInfoList);
			dbUtil.updateEntity("更新配送信息", session, strDeliveryApply);

			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}
	}

	private void isContinuous(Long beginNo, Long endNo) throws BaseException {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		StringBuffer sql = new StringBuffer();

		sb.append(" SELECT COUNT(1) TICKET_NUM ");
		sb.append(" FROM STR_TICKET_INFO T ");
		sb.append("	WHERE 1=1 ");

		sql.append(" SELECT COUNT(1) TICKET_NUM ");
		sql.append(" FROM STR_TICKET_INFO T ");
		sql.append(" WHERE T.STORE_ID IS NULL AND T.STAT IN ('001','003') ");// 状态已核实、已配送

		// 票号起号--票号止号
		if (beginNo != null && endNo != null) {
			params.put("BEGIN_NO", beginNo);
			params.put("END_NO", endNo);
			sb.append(" AND T.TICKET_ID>=:BEGIN_NO AND T.TICKET_ID<=:END_NO ");
			sql.append(" AND T.TICKET_ID>=:BEGIN_NO AND T.TICKET_ID<=:END_NO ");
		}

		List<Map<String, Object>> num = dbUtil.queryListToMap("查询实际票数", sb.toString(), params);
		List<Map<String, Object>> totalNum = dbUtil.queryListToMap("根据票号起止号查询总数", sql.toString(), params);
		if (!num.isEmpty() && !totalNum.isEmpty()) {
			Map<String, Object> ticketNum = num.get(0);
			Map<String, Object> ticketNum2 = totalNum.get(0);

			Long num1 = (Long) ticketNum.get("ticketNum");
			Long num2 = (Long) ticketNum2.get("ticketNum");
			if (num2.intValue() != num1.intValue()) {
				throw new ServiceException(MSG.ERROR, "票号不连续，请核实");
			}
		}
	}

	@Override
	public List<Map<String, Object>> listDeliveryDetail(String applyId) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		/*
		 * 不再查询箱表库存表 sb.append(" SELECT DETAIL.CHEST_ID, "); sb.append(
		 * "  DETAIL.TICKET_TYPE_ID, "); sb.append(
		 * "  TY.TICKET_TYPE_NAME,      "); sb.append(
		 * "  CHEST.TICKET_NUM,      "); sb.append("  CHEST.BEGIN_NO,        ");
		 * sb.append("  CHEST.END_NO           "); sb.append(
		 * "   FROM STR_DELIVERY_DETAIL DETAIL "); sb.append(
		 * "  INNER JOIN STR_CHEST CHEST "); sb.append(
		 * "     ON DETAIL.CHEST_ID = CHEST.CHEST_ID "); sb.append(
		 * "  INNER JOIN SYS_TICKET_TYPE TY "); sb.append(
		 * "     ON CHEST.TICKET_TYPE_ID = TY.TICKET_TYPE_ID ");
		 */

		sb.append(" SELECT DETAIL.CHEST_ID, ");
		sb.append("     DETAIL.TICKET_TYPE_ID,");
		sb.append("     TY.TICKET_TYPE_NAME,");
		sb.append("     DETAIL.END_NO,");
		sb.append("     DETAIL.BEGIN_NO,");
		sb.append("     (DETAIL.END_NO - DETAIL.BEGIN_NO) + 1 TICKET_NUM");
		sb.append(" FROM STR_DELIVERY_DETAIL DETAIL");
		sb.append(" INNER JOIN SYS_TICKET_TYPE TY");
		sb.append("  ON DETAIL.TICKET_TYPE_ID = TY.TICKET_TYPE_ID");
		sb.append(" WHERE DETAIL.APPLY_ID=:APPLY_ID ");
		params.put("APPLY_ID", applyId);
		String sql = sb.toString();
		List<Map<String, Object>> ret = dbUtil.queryListToMap("查询打印明细列表", sql, params);
		return ret;
	}
}
