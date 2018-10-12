package com.zhming.basic.service;

import java.util.Map;

import com.tbims.entity.SysDictionary;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.DBException;
import com.zhming.support.exception.ServiceException;

/**
 * Title: 字典管理 <br/>
 * Description:
 * @ClassName: IDictionaryService
 * @author ydc
 * @date 2016-1-7 下午6:02:18
 * 
 */
public interface IDictionaryService {

	/**
	 * Title: 查询指定字段的指定项目的值 <br/>
	 * Description:
	 * @param userBean
	 * @param Dictionary
	 * @return
	 * @throws BFException
	 */
	public String getItemValueByKey( String key, String itemCd);
	


	/**
	* Title: 添加字典<br/>
	* Description: 
	* @param userBean
	* @param dictionary
	 */
	public void addDictionary( UserBean userBean, SysDictionary dictionary) throws DBException,ServiceException;
	
	/**
	* Title: 修改字典<br/>
	* Description: 
	* @param userBean
	* @param dictionary
	 */
	public void updateDictionary( UserBean userBean, SysDictionary dictionary) throws DBException;

	/**
	* Title: 删除字典<br/>
	* Description: 
	* @param navigation
	* @param userBean
	* @param keyIds
	 */
	public void deleteBydictionaryCds( UserBean userBean, String keyIds) throws DBException;

	/**
	 * 	
	* Title: 查询字典信息<br/>
	* Description: 
	* @param userBean
	* @param dictionary
	* @param page
	* @param rows
	* @return
	 */
	public PageBean<Map<String, Object>> listDictionary(UserBean userBean,SysDictionary dictionary,int page,int rows);

	/**
	* Title: 根据主键查询字典实体<br/>
	* Description: 
	* @param userBean
	* @param dictionary_id
	* @return
	 */
	public SysDictionary find( UserBean userBean, String dictionary_id) throws DBException;
}
