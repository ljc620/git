package com.tbims.face.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;
import com.tbims.face.common.Global;
import com.tbims.face.common.Util;
import com.tbims.face.dao.ClientDao;
import com.tbims.face.dao.SecondinRecordDao;
import com.tbims.face.dao.SecondinRegInfoDao;
import com.tbims.face.entity.Client;
import com.tbims.face.entity.FingerTemp;
import com.tbims.face.entity.SecondinRecord;
import com.tbims.face.entity.SecondinRegInfo;


@Service("tbimsService")
public class TbimsServiceImpl implements TbimsService {

	@Resource
	ClientDao clientDao;
	
	@Resource
	SecondinRegInfoDao sriDao;
	
	@Resource
	SecondinRecordDao srDao;
	
	@Transactional(readOnly=true)
	public boolean ping(Client OldClient) {
		//client 传入空直接false
		if(OldClient == null) {
			return false;
		}
		
		//查询celient
		Client newClient = clientDao.findByClient(OldClient);
		
		//返回结果
		if(newClient == null) {
			return false;
		}
		return true;
	}

	//游客登记
	@Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED)
	public boolean secondinRegInfo(SecondinRegInfo sri) {
		
		if(Util.isStart() == false){
			throw new StartTiemException("未到达登记时间");
		}
		
		
		switch(Global.ISEPEATREGISTER){
		case 0:
			return addRegInfo(sri);
		case 1:
			System.out.println(sri.toString());
			//如果指纹不为空，则指纹匹配
			if(Util.isNotEmpty(sri.getFingerId1()) && Util.isNotEmpty(sri.getFingerId2())){
				SecondinRegInfo resultSecondReg = null;
				resultSecondReg = fingerMatch(sri,resultSecondReg);
				if(resultSecondReg != null){
					return false;
				}else{
					addRegInfo(sri);
					return true;
				}
			}else{
				SecondinRegInfo seachSri = new SecondinRegInfo();
				if(sri.getTickedId() != null &&  sri.getTickedId() != 0 ){
					seachSri.setTickedId(sri.getTickedId());
				}
				seachSri.setIdcard(sri.getIdcard());
				List<SecondinRegInfo> sris = sriDao.find(seachSri,Global.EXPIRATION_TIME);
				if(sris.size() > 0){
					return false;
				}else{
					addRegInfo(sri);
					return true;
				}
			}
		}
		return true;
		
	}

	private boolean addRegInfo(SecondinRegInfo sri) {
		FingerprintTemplate probe = null;
		FingerprintTemplate candidate = null;
		
		//如果携带指纹
		if(Util.isNotEmpty(sri.getFingerId1()) && Util.isNotEmpty(sri.getFingerId2())) {
			
			//转成指纹
			probe = Util.base64ForFinger(sri.getFingerId1());
			candidate = Util.base64ForFinger(sri.getFingerId2());
			
			///System.out.println("提交指纹评分：" + Util.fingerMatch(probe, candidate));
			//匹配不成功
			/*if(Util.fingerMatch(probe, candidate) < Global.FINGER_MATCH_SOCRE) {
				System.out.println("两次指纹匹配不成功");
				return false;
			}*/
			
			//转成json
			sri.setFingerId1(probe.json());
			sri.setFingerId2(candidate.json());
		}

		//设置id插入时间
		//sri.setRegId(UUID.randomUUID().toString());
		sri.setInsertTime(new Date());
		//添加
		sriDao.save(sri);
		
		if(probe != null && candidate != null) {
			//用户指纹模板集合添加
			Global.FINGER_TEMPS.add(new FingerTemp(sri.getRegId(),probe,candidate));
		}
		return true;
	}

	/**
	 * 验证
	 * @param matchType 
	 * @param second 
	 * @throws InterruptedException 
	 */
	@Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED)
	public Map<String,Object> verInfo(SecondinRegInfo second, String matchType){
		
		if(Util.isStart() == false){
			throw new StartTiemException("未到达验证时间");
		}
		
		Integer matchtTypeInt = Integer.parseInt(matchType);
		
		Map<String,Object> resuleMap = new HashMap<String, Object>();
		
		//接收查找到的记录
		SecondinRegInfo resultSecondReg = null;
		
		//查找参数
		SecondinRegInfo parSri = new SecondinRegInfo();

		//新的匹配记录
		final SecondinRecord newSr = new SecondinRecord();
		
		List<SecondinRegInfo> sris = new ArrayList<SecondinRegInfo>();
		switch(matchtTypeInt) {
			case 0:
				parSri.setFaceId(second.getFaceId());
				List<SecondinRegInfo> reginfos = sriDao.find(parSri,Global.EXPIRATION_TIME);
				if(reginfos.size() > 0){
					resultSecondReg = reginfos.get(0);
				}
				break;
			case 1:
				resultSecondReg = fingerMatch(second, resultSecondReg, newSr);
				break;
			case 2:
				parSri.setTickedId(second.getTickedId());
				sris = sriDao.find(parSri,Global.EXPIRATION_TIME);
				if(sris.size() != 0) {
					resultSecondReg = sris.get(0);
					resultSecondReg.setFingerId1(null);
					resultSecondReg.setFingerId2(null);
				}
				
				break;
			case 3:
				
				parSri.setIdcard(second.getIdcard());;
				sris = sriDao.find(parSri,Global.EXPIRATION_TIME);
				if(sris.size() != 0) {
					resultSecondReg = sris.get(0);
					resultSecondReg.setFingerId1(null);
					resultSecondReg.setFingerId2(null);
				}
				break;
		}
		
		if(resultSecondReg != null) {
			resuleMap.put("state", 0);
			resuleMap.put("data", resultSecondReg);
			if(Global.ISEPEATVERIFICATION == 1){
				List<SecondinRecord> srs = srDao.findByRegid(resultSecondReg.getRegId());
				if(srs.size() >= 1){
					resuleMap.put("state", 3);
					//throw new EpeatVerificationException("不可重复验证");
				}
			}
			newSr.setRecordId(UUID.randomUUID().toString());
			newSr.setRegId(resultSecondReg.getRegId());
			newSr.setClientId(resultSecondReg.getClientId());
			newSr.setMatchType(matchtTypeInt);
			newSr.setMacthTime(new Date());
			srDao.add(newSr);
		}else{
			resuleMap.put("state", 2);
		}
		
		return resuleMap;
	}

	private SecondinRegInfo fingerMatch(SecondinRegInfo second,
			SecondinRegInfo resultSecondReg, final SecondinRecord newSr) {
		// 获取模板库数量
		final int size = Global.FINGER_TEMPS.size();

		// 需查找指纹
		FingerprintTemplate probe = Util.base64ForFinger(second.getFingerId1());

		// 探测模板
		final FingerprintMatcher matcher = new FingerprintMatcher(probe);

		// 如果数据库数量小于制定最低则不适用线程
		if (size < Global.FINGER_MATCH_MINNUM) {
			// double scoreMatch = Util.fingerMatch(matcher, ft.getFingerid1());
			// matcher = new FingerprintMatcher(probe);
			Double scoreMatch;
			for (FingerTemp ft : Global.FINGER_TEMPS) {
				scoreMatch = Util.fingerMatch(matcher, ft.getFingerid1());
				if (scoreMatch > Global.FINGER_MATCH_SOCRE) {
					DecimalFormat decimalFormat = new DecimalFormat("######0");
					String scoreStr = decimalFormat.format(scoreMatch);
					newSr.setMacthScort(Integer.parseInt(scoreStr));
					resultSecondReg = sriDao.find(
							new SecondinRegInfo(ft.getRegId()),
							Global.EXPIRATION_TIME).get(0);
					resultSecondReg.setFingerId1(null);
					resultSecondReg.setFingerId2(null);
					break;
				}
			}
		} else {

			// 创建线程池
			final ExecutorService pool = Executors
					.newFixedThreadPool(Global.FINGER_MATCH_CONCURRENCY);

			// 获取平均值
			final double avgNum = size / Global.FINGER_MATCH_CONCURRENCY;

			// 平均值转int
			DecimalFormat decimalFormat = new DecimalFormat("######0");
			String avgNumStr = decimalFormat.format(avgNum);
			final Integer angNumInt = Integer.parseInt(avgNumStr);

			// 接收线程结果
			final FingerTemp ft = new FingerTemp();

			// 创建线程
			for (int i = 0; i < Global.FINGER_MATCH_CONCURRENCY; i++) {

				Runnable runn = null;
				// 如果是最后一条线程，将剩下的模板分配给该线程
				if (i == Global.FINGER_MATCH_CONCURRENCY - 1) {
					final int n = i;
					runn = new Runnable() {
						public void run() {
							// Thread t = Thread.currentThread();
							while (true) {
								for (int i = (int) (n * avgNum); i < size; i++) {
									Double scoreMatch = Util.fingerMatch(
											matcher, Global.FINGER_TEMPS.get(i)
													.getFingerid1());
									if (scoreMatch > Global.FINGER_MATCH_SOCRE) {
										DecimalFormat decimalFormat = new DecimalFormat(
												"######0");
										String scoreStr = decimalFormat
												.format(scoreMatch);
										newSr.setMacthScort(Integer
												.parseInt(scoreStr));
										ft.setRegId(Global.FINGER_TEMPS.get(i)
												.getRegId());
										pool.shutdownNow();
									}
								}
								break;
							}

						}
					};

				} else {

					// 分配模板
					final List<FingerTemp> fts = new ArrayList<FingerTemp>();
					for (int j = i * angNumInt; j < angNumInt * (i + 1); j++) {
						fts.add(Global.FINGER_TEMPS.get(j));
					}
					runn = new Runnable() {
						public void run() {
							while (true) {
								for (FingerTemp fingerTemp : fts) {
									Double scordMatch = Util.fingerMatch(
											matcher, fingerTemp.getFingerid1());
									if (scordMatch > Global.FINGER_MATCH_SOCRE) {
										DecimalFormat decimalFormat = new DecimalFormat(
												"######0");
										String scoreStr = decimalFormat
												.format(scordMatch);
										newSr.setMacthScort(Integer
												.parseInt(scoreStr));
										ft.setRegId(fingerTemp.getRegId());
										pool.shutdownNow();
										// Global.CACHEN_THREAD_POOL.shutdownNow();
									}
								}
								break;
							}

						}
					};
				}

				pool.execute(runn);
			}
			pool.shutdown();
			while (true) {
				if (pool.isTerminated()) {
					if (Util.isNotEmpty(ft.getRegId())) {
						resultSecondReg = sriDao.find(
								new SecondinRegInfo(ft.getRegId()),
								Global.EXPIRATION_TIME).get(0);
						resultSecondReg.setFingerId1(null);
						resultSecondReg.setFingerId2(null);
					}
					break;
				}
			}
		}
		return resultSecondReg;
	}
	
	/**
	 * 
	 * @param second 指纹模板
	 * @param resultSecondReg 查询结果
	 * @return
	 */
	private SecondinRegInfo fingerMatch(SecondinRegInfo second,SecondinRegInfo resultSecondReg) {
		// 获取模板库数量
		final int size = Global.FINGER_TEMPS.size();
		
		// 需查找指纹
		FingerprintTemplate probe = Util.base64ForFinger(second.getFingerId1());
		
		// 探测模板
		final FingerprintMatcher matcher = new FingerprintMatcher(probe);
		
		// 如果数据库数量小于制定最低则不适用线程
		if (size < Global.FINGER_MATCH_MINNUM) {
			// double scoreMatch = Util.fingerMatch(matcher, ft.getFingerid1());
			// matcher = new FingerprintMatcher(probe);
			Double scoreMatch;
			for (FingerTemp ft : Global.FINGER_TEMPS) {
				scoreMatch = Util.fingerMatch(matcher, ft.getFingerid1());
				if (scoreMatch > Global.FINGER_MATCH_SOCRE) {
					DecimalFormat decimalFormat = new DecimalFormat("######0");
					String scoreStr = decimalFormat.format(scoreMatch);
					resultSecondReg = sriDao.find(
							new SecondinRegInfo(ft.getRegId()),
							Global.EXPIRATION_TIME).get(0);
					resultSecondReg.setFingerId1(null);
					resultSecondReg.setFingerId2(null);
					break;
				}
			}
		} else {
			
			// 创建线程池
			final ExecutorService pool = Executors
					.newFixedThreadPool(Global.FINGER_MATCH_CONCURRENCY);
			
			// 获取平均值
			final double avgNum = size / Global.FINGER_MATCH_CONCURRENCY;
			
			// 平均值转int
			DecimalFormat decimalFormat = new DecimalFormat("######0");
			String avgNumStr = decimalFormat.format(avgNum);
			final Integer angNumInt = Integer.parseInt(avgNumStr);
			
			// 接收线程结果
			final FingerTemp ft = new FingerTemp();
			
			// 创建线程
			for (int i = 0; i < Global.FINGER_MATCH_CONCURRENCY; i++) {
				
				Runnable runn = null;
				// 如果是最后一条线程，将剩下的模板分配给该线程
				if (i == Global.FINGER_MATCH_CONCURRENCY - 1) {
					final int n = i;
					runn = new Runnable() {
						public void run() {
							// Thread t = Thread.currentThread();
							while (true) {
								for (int i = (int) (n * avgNum); i < size; i++) {
									Double scoreMatch = Util.fingerMatch(
											matcher, Global.FINGER_TEMPS.get(i)
											.getFingerid1());
									if (scoreMatch > Global.FINGER_MATCH_SOCRE) {
										DecimalFormat decimalFormat = new DecimalFormat(
												"######0");
										String scoreStr = decimalFormat
												.format(scoreMatch);
										ft.setRegId(Global.FINGER_TEMPS.get(i)
												.getRegId());
										pool.shutdownNow();
									}
								}
								break;
							}
							
						}
					};
					
				} else {
					
					// 分配模板
					final List<FingerTemp> fts = new ArrayList<FingerTemp>();
					for (int j = i * angNumInt; j < angNumInt * (i + 1); j++) {
						fts.add(Global.FINGER_TEMPS.get(j));
					}
					runn = new Runnable() {
						public void run() {
							while (true) {
								for (FingerTemp fingerTemp : fts) {
									Double scordMatch = Util.fingerMatch(
											matcher, fingerTemp.getFingerid1());
									if (scordMatch > Global.FINGER_MATCH_SOCRE) {
										DecimalFormat decimalFormat = new DecimalFormat(
												"######0");
										String scoreStr = decimalFormat
												.format(scordMatch);
										ft.setRegId(fingerTemp.getRegId());
										pool.shutdownNow();
										// Global.CACHEN_THREAD_POOL.shutdownNow();
									}
								}
								break;
							}
							
						}
					};
				}
				
				pool.execute(runn);
			}
			pool.shutdown();
			while (true) {
				if (pool.isTerminated()) {
					if (Util.isNotEmpty(ft.getRegId())) {
						resultSecondReg = sriDao.find(
								new SecondinRegInfo(ft.getRegId()),
								Global.EXPIRATION_TIME).get(0);
						resultSecondReg.setFingerId1(null);
						resultSecondReg.setFingerId2(null);
					}
					break;
				}
			}
		}
		return resultSecondReg;
	}

	
	//根据faceid查询二次入园登记
	public SecondinRegInfo findRebByFaceid(SecondinRegInfo reg) {
		if(Util.isNotEmpty(reg.getFaceId()) == false){
			return null;
		}
		SecondinRegInfo paramReg = new SecondinRegInfo();
		
		paramReg.setFaceId(reg.getFaceId());
		
		List<SecondinRegInfo> regs = sriDao.find(paramReg,Global.EXPIRATION_TIME);
		if(regs.size() > 0){
			return regs.get(0);
		}
		
		return null;
	}
	
	
	
}
