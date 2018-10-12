package com.tbims.service;

import java.util.List;
import java.util.Map;

import com.tbims.bean.ChestBean;
import com.tbims.bean.DeliveryApplyBean;
import com.tbims.bean.DeliveryApplyDetailBean;
import com.tbims.entity.StrDeliveryApply;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.BaseException;

public interface IDeliveryExamService {

	/**
	 * 
	* Title: 查询配送申请列表<br/>
	* Description: 
	* @param strDeliveryApply
	* @return
	 */
	public PageBean<DeliveryApplyBean> listDeliveryApply(UserBean userBean,StrDeliveryApply strDeliveryApply);
	
	/**
	 * 
	* Title: 根据申请编号获取申请信息<br/>
	* Description: 
	* @param applyId
	* @return
	 */
	public DeliveryApplyBean getDeliveryApply(String applyId);
	
	/**
	 * 
	* Title: 获取申请明细<br/>
	* Description: 
	* @param applyId
	* @return
	 */
	public List<DeliveryApplyDetailBean> applyDetail(String applyId);
	
	/**
	 * 
	* Title: 审核<br/>
	* Description: 
	* @param strDeliveryApply
	* @param applyDetailListr
	 * @throws Exception 
	 */
	public void examDelivery(UserBean userBean,StrDeliveryApply strDeliveryApply,List<DeliveryApplyDetailBean> applyDetailList) throws Exception;
	
	/**
	 * 
	* Title: 查询明细<br/>
	* Description: 
	* @param applyId
	* @return
	 */
    public  List<Map<String,Object>> detailList(String applyId);
    
    /**
     * 获取箱列表
    * Title: <br/>
    * Description: 
    * @param chestId
    * @param beginNo
    * @param endNo
    * @param boxId
    * @return
     */
    public List<Map<String,Object>> listChest(String chestId,String boxId,Long beginNo,Long endNo) throws BaseException;
    
    /**
     * 确认配送
    * Title: <br/>
    * Description: 
    * @param applyId
    * @param chestList
     * @throws Exception 
     */
    public void confirmDelivery(UserBean userBean,String applyId,List<ChestBean> chestList) throws Exception;
    
    /**
     * 
    * Title: <br/>
    * Description: 
    * @param applyId
    * @return
     */
    public List<Map<String,Object>> listDeliveryDetail(String applyId);
    
    /**
     * 
    * Title:获取箱号、盒号内票的起止号 <br/>
    * Description: 
    * @param chestId
    * @param boxId
    * @return
     */
	public List<Map<String, Object>> getTicketNo(String chestId, String boxId) throws Exception;
}
