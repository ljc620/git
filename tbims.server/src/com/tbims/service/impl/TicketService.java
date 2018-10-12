package com.tbims.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tbims.entity.StrChestTmp;
import com.tbims.entity.StrTicketInfoTmp;
import com.tbims.service.ITicketService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.ServiceException;

@Service
public class TicketService extends BaseService implements ITicketService {
	private static final Log logger = LogFactory.getLog(TicketService.class);

	@Override
	public String loadDataFromXML(UserBean userBean, MultipartFile xmlFilePath) throws Exception {
		String fileName = xmlFilePath.getOriginalFilename();
		if (fileName == null || fileName.length() < 8) {
			throw new ServiceException(MSG.ERROR, "文件名格式不正确");
		}
		if (!(xmlFilePath.getContentType().equals("text/xml") || xmlFilePath.getContentType().equals("application/xml"))) {
			throw new ServiceException(MSG.ERROR, "文件名格式不正确");
		}
		String batchId = fileName.substring(7).replaceAll(".xml", "");
		InputStream is = xmlFilePath.getInputStream();
		Session session = dbUtil.getSessionByTransaction();
		try {
			List<Map<String, Object>> chestListCheck = dbUtil.queryListToMap("查询箱信息", "SELECT * FROM STR_CHEST WHERE BATCH_ID=? ", batchId);
			if (chestListCheck != null && chestListCheck.size() != 0) {
				throw new ServiceException(MSG.ERROR, String.format("[%s]该批次已提交入库,不允许再入库", batchId));
			}

			String sqlDelTicket = "TRUNCATE TABLE STR_TICKET_INFO_TMP ";
			dbUtil.executeSql("删除临时表票信息", session, sqlDelTicket);
			String sqlDelChest = "TRUNCATE TABLE STR_CHEST_TMP ";
			dbUtil.executeSql("删除临时表箱信息", session, sqlDelChest);
			/*
			 * XmlParseHandler handler = new XmlParseHandler(); // 1. 得到SAX解析工厂
			 * SAXParserFactory saxParserFactory =
			 * SAXParserFactory.newInstance(); // 2. 让工厂生产一个sax解析器 SAXParser
			 * newSAXParser = saxParserFactory.newSAXParser(); // 3.
			 * 传入输入流和handler，解析 newSAXParser.parse(is, handler); is.close();
			 * List<StrChestTmp> StrChestTmps = handler.getStrChestTmps();
			 */
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "GBK"));
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(reader);
			Element root = document.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> listElement = (List<Element>) root.elements();
			List<StrChestTmp> chestList = new ArrayList<StrChestTmp>();
			for (Element chestE : listElement) {// 遍历所有一级子节点
				StrChestTmp strChestTmp = new StrChestTmp();
				String chestId = chestE.attribute("CHEST_ID").getValue();
				String ticketTypeId = chestE.attribute("TICKET_TYPE_ID").getValue();
				strChestTmp.setChestId(chestId);
				strChestTmp.setBoxNum(chestE.attribute("BOX_NUM").getValue());
				strChestTmp.setTicketNum(Long.valueOf(chestE.attribute("TICKET_NUM").getValue()));
				strChestTmp.setTicketTypeId(ticketTypeId);
				strChestTmp.setStat("N");
				strChestTmp.setBeginNo(Long.valueOf(chestE.attribute("BEGIN_NO").getValue()));
				strChestTmp.setEndNo(Long.valueOf(chestE.attribute("END_NO").getValue()));
				strChestTmp.setBatchId(batchId);
				strChestTmp.setOpeTime(new Date());
				strChestTmp.setOpeUserId(userBean.getUserId());
				chestList.add(strChestTmp);
				@SuppressWarnings("unchecked")
				List<Element> listElementTicket = chestE.elements();
				List<StrTicketInfoTmp> ticketList = new ArrayList<StrTicketInfoTmp>();
				for (Element ticketE : listElementTicket) {
					StrTicketInfoTmp strTicketInfoTmp = new StrTicketInfoTmp();
					strTicketInfoTmp.setBatchId(batchId);
					strTicketInfoTmp.setBoxId(ticketE.attribute("BOX_ID").getValue());
					strTicketInfoTmp.setChestId(chestId);
					strTicketInfoTmp.setTicketId(Long.valueOf(ticketE.attribute("TICKET_ID").getValue()));
					strTicketInfoTmp.setTicketUid(ticketE.attribute("TICKET_UID").getValue());
					strTicketInfoTmp.setTicketTypeId(ticketTypeId);
					strTicketInfoTmp.setChipId(ticketE.attribute("CHIP_ID").getValue());
					strTicketInfoTmp.setOpeTime(new Date());
					strTicketInfoTmp.setOpeUserId(userBean.getUserId());
					ticketList.add(strTicketInfoTmp);
				}
				// 每箱保存票信息
				// dbUtil.saveEntityBatch("临时保存票信息", session, ticketList);
				// 采用jdbc batch的方式批量保存数据
				saveEntityJdbcBatch("临时保存票信息", session, ticketList);
			}
			dbUtil.saveEntityBatch("临时保存箱信息", session, chestList);
			dbUtil.commit(session);
			return batchId;
		} finally {
			dbUtil.close(session);
		}

	}

	private void saveEntityJdbcBatch(String string, Session session, final List<StrTicketInfoTmp> ticketList) {
		session.doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				PreparedStatement ps = null;
				try {
					String sql = "INSERT INTO STR_TICKET_INFO_TMP " + " (TICKET_ID,TICKET_UID, CHIP_ID,CHEST_ID, " + " BOX_ID, BATCH_ID, TICKET_TYPE_ID,OPE_TIME,OPE_USER_ID)" + "VALUES(?,?,?,?,?,?,?,?,?)";
					ps = conn.prepareStatement(sql);
					for (int i = 0; i < ticketList.size(); i++) {
						ps.setLong(1, ticketList.get(i).getTicketId());
						ps.setString(2, ticketList.get(i).getTicketUid());
						ps.setString(3, ticketList.get(i).getChipId());
						ps.setString(4, ticketList.get(i).getChestId());
						ps.setString(5, ticketList.get(i).getBoxId());
						ps.setString(6, ticketList.get(i).getBatchId());
						ps.setString(7, ticketList.get(i).getTicketTypeId());
						ps.setDate(8, new java.sql.Date(ticketList.get(i).getOpeTime().getTime()));
						ps.setString(9, ticketList.get(i).getOpeUserId());
						ps.addBatch();
						if ((i + 1) % 2000 == 0) {
							ps.executeBatch();
							ps.clearBatch();
						}
					}
					if (ticketList.size() % 2000 != 0) {
						ps.executeBatch();
						ps.clearBatch();
					}
				} finally {
					if (ps != null) {
						try {
							ps.close();
							ps = null;
						} catch (Exception ex) {
							ps = null;
							logger.error("错误", ex);
						}
					}
				}
			}
		});
	}

	@Override
	public List<Map<String, Object>> listChest(UserBean userBean) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT CHEST.CHEST_ID, ");
		sb.append(" CHEST.BATCH_ID,        ");
		sb.append(" CHEST.TICKET_TYPE_ID,  ");
		sb.append(" TY.TICKET_TYPE_NAME,   ");
		sb.append(" CHEST.BOX_NUM,         ");
		sb.append(" CHEST.TICKET_NUM,      ");
		sb.append(" CHEST.STAT,           ");
		sb.append(" CHEST.OPE_USER_ID,     ");
		sb.append(" CHEST.OPE_TIME         ");
		sb.append(" FROM STR_CHEST_TMP CHEST   ");
		sb.append(" INNER JOIN SYS_TICKET_TYPE TY  ");
		sb.append(" ON CHEST.TICKET_TYPE_ID=TY.TICKET_TYPE_ID ");
		sb.append(" ORDER BY CHEST.TICKET_TYPE_ID,CHEST.CHEST_ID");
		String sql = sb.toString();
		List<Map<String, Object>> ret = dbUtil.queryListToMap("查询箱列表", sql);
		return ret;
	}

	@Override
	public void checkChest(String chestIds, String batchId) throws ServiceException {
		// String tem = chestIds.replace(",", "");
		// for (int i = 0; i < tem.length(); i++) {
		// if (!StringUtil.isContainLetter(String.valueOf(tem.charAt(i)))) {
		// throw new ServiceException(MSG.BF_ERROR, "箱号输入格式不正确, 请确保箱号只包含【数字】【
		// 逗号分隔符】处在英文状态下");
		// }
		// }
		String[] chestIdArr = chestIds.split("[,，\\s]");
		Session session = dbUtil.getSessionByTransaction();
		try {
			for (int i = 0; i < chestIdArr.length; i++) {
				String chestId = null;
				String chestIdStr = chestIdArr[i];
				if (chestIdStr != null) {
					chestId = chestIdStr;
				}
				StrChestTmp strChestTmp = dbUtil.findById("查询箱信息", StrChestTmp.class, chestId);
				if (strChestTmp == null) {
					throw new ServiceException(MSG.BF_ERROR, "【" + chestId + "】不存在，请核实后再进行核对");
				}
				if (!strChestTmp.getBatchId().equals(batchId)) {
					continue;
				} else {
					strChestTmp.setStat("Y");
				}
				dbUtil.updateEntity("更新状态为已核实", session, strChestTmp);
			}
			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}

	}

	@Override
	public int getChestTmpNum(String batchId, String flag) {
		String sql = "";
		if ("ALL".equals(flag)) {
			sql = "SELECT *  FROM STR_CHEST T WHERE   T.BATCH_ID=? ";
		} else {
			sql = "SELECT *  FROM STR_CHEST_TMP T WHERE   T.BATCH_ID=? AND T.STAT='N' ";
		}
		return dbUtil.count("获取临时表数量", sql, batchId);
	}

	@Override
	public void saveInfo(UserBean userBean, String batchId) throws Exception {
		Session session = dbUtil.getSessionByTransaction();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("BATCH_ID", batchId);
		List<Map<String, Object>> chestList = dbUtil.queryListToMap("查询箱信息", "SELECT * FROM STR_CHEST WHERE BATCH_ID=:BATCH_ID ", params);
		if (chestList != null && chestList.size() != 0) {
			throw new ServiceException(MSG.ERROR, String.format("[%s]该批次已入库,不允许再入库", batchId));
		}
		try {

			String sqlChest = "INSERT INTO STR_CHEST " + " (CHEST_ID, BATCH_ID, TICKET_TYPE_ID, BOX_NUM, TICKET_NUM,BEGIN_NO,END_NO, STAT, OPE_USER_ID, OPE_TIME)" + " SELECT CHEST_ID, BATCH_ID,TICKET_TYPE_ID, BOX_NUM, TICKET_NUM,BEGIN_NO,END_NO, '001', OPE_USER_ID, OPE_TIME" + " FROM STR_CHEST_TMP WHERE BATCH_ID =:BATCH_ID ";
			dbUtil.executeSql("保存箱信息", session, sqlChest, params);
			String sqlTicket = "INSERT INTO STR_TICKET_INFO" + " (TICKET_ID, TICKET_UID, CHIP_ID, CHEST_ID, BOX_ID, BATCH_ID, TICKET_TYPE_ID, STAT, OPE_USER_ID, OPE_TIME) " + " SELECT TICKET_ID, TICKET_UID, CHIP_ID, CHEST_ID, BOX_ID, BATCH_ID, TICKET_TYPE_ID," + " '001', OPE_USER_ID, OPE_TIME FROM STR_TICKET_INFO_TMP WHERE " + "  BATCH_ID=:BATCH_ID";
			dbUtil.executeSql("保存票信息", session, sqlTicket, params);

			String sqlDelChest = "TRUNCATE TABLE STR_TICKET_INFO_TMP";
			dbUtil.executeSql("删除箱临时表删除信息", sqlDelChest);

			String sqlDelTicket = "TRUNCATE TABLE STR_CHEST_TMP";
			dbUtil.executeSql("删除箱临时表删除信息", sqlDelTicket);

			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}
	}

	@Override
	public void deleteChestTmp(UserBean userBean) {
		Session session = null;
		try {
			session = dbUtil.getSessionByTransaction();

			String sqlDelTicket = "TRUNCATE TABLE STR_TICKET_INFO_TMP ";
			dbUtil.executeSql("删除临时表票信息", session, sqlDelTicket);
			String sqlDelChest = "TRUNCATE TABLE STR_CHEST_TMP ";
			dbUtil.executeSql("删除临时表箱信息", session, sqlDelChest);

			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}

	}
}