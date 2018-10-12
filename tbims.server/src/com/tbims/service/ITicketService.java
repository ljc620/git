package com.tbims.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentException;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.ServiceException;

/**
* Title: 门票入库  <br/>
* Description: 
* @ClassName: ITicketService
* @author ydc
* @date 2017年7月8日 下午3:25:34
* 
*/
public interface ITicketService {

	/**
	 * 
	* Title: 导入文件<br/>
	* Description: 
	* @param xmlFilePath
	* @throws IOException
	* @throws DocumentException
	* @throws ParserConfigurationException
	* @throws SAXException
	* @throws Exception
	 */
	public String loadDataFromXML(UserBean userBean,MultipartFile xmlFilePath) throws Exception;
	
	/**
	 * 
	* Title: 查询某一批次的箱信息<br/>
	* Description: 
	* @param batchId
	* @return
	 */
	public List<Map<String, Object>> listChest(UserBean userBean);
	
	/**
	 * 
	* Title: 核对箱信息<br/>
	* Description: 
	* @param chestIds
	* @param batchId
	 */
	public void checkChest(String chestIds,String batchId) throws ServiceException;
	
	/**
	 * 
	* Title: 获取某一批次未核对数量<br/>
	* Description: 
	* @param batchId
	* @return
	 */
	public int  getChestTmpNum(String batchId,String flag);
	
	/**
	 * 
	* Title: 保存信息至正式表<br/>
	* Description: 
	* @param batchId
	* @throws Exception
	 */
	public void  saveInfo(UserBean userBean,String batchId) throws Exception;
	
	/**
	 * 清空临时表
	 */
	public void deleteChestTmp(UserBean userBean);
}
