package com.tbims.service;

import java.util.List;
import java.util.Map;

import com.tbims.bean.ApplyDetailBean;
import com.tbims.bean.DeliveryApplyBean;
import com.tbims.bean.DeliveryApplyDetailBean;
import com.tbims.entity.StrDeliveryApply;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.BaseException;


public interface IDeliveryService {

	/**
	 * 
	* Title: 查询配送申请列表<br/>
	* Description: 
	* @param loginUserBean
	* @param strDeliveryApply
	* @return
	 */
	public PageBean<DeliveryApplyBean> listDeliveryApply(UserBean loginUserBean, StrDeliveryApply strDeliveryApply) throws BaseException;

	/**
	 * 
	* Title: 配送申请审核情况<br/>
	* Description: 
	* @param applyId
	* @return
	* @throws BaseException
	 */
	public List<DeliveryApplyDetailBean> applyExamDetail(String applyId) throws BaseException;

	/**
	 * 
	* Title:配送详情 <br/>
	* Description: 
	* @param applyId
	* @return
	* @throws BaseException
	 */
	public List<Map<String, Object>> deliveryDetail(String applyId) throws BaseException;

	/**
	 * 
	* Title: 门票配送-确认接收<br/>
	* Description: 
	* @param applyId
	* @param examStat
	* @throws BaseException
	 */
	public void confrimReceive(UserBean userBean,String applyId,String examStat) throws BaseException;

	/**
	 * 
	* Title: 门票申请<br/>
	* Description: 
	* @param loginUserBean
	* @param applyDeliveryTime
	* @throws BaseException
	 */
	public void saveApply(UserBean loginUserBean,String applyDeliveryTime,List<ApplyDetailBean> applyDetail) throws Exception;

	/**
	 * 
	* Title:配送申请历史查询 <br/>
	* Description: 
	* @param loginUserBean
	* @param applyId
	* @param examStat
	* @param beginApplyTime
	* @param endApplyTime
	* @return
	 */
	public PageBean<DeliveryApplyBean> listDeliveryApplyH(UserBean loginUserBean, String applyId, String examStat, String beginApplyTime, String endApplyTime) throws Exception;

	/**
	 * 
	* Title: 校验申请票种数量<br/>
	* Description: 
	* @param loginUserBean
	* @param applyNum
	* @throws BaseException
	 */
	public void checkApplyNum(UserBean loginUserBean, Long applyNum) throws BaseException;
}
