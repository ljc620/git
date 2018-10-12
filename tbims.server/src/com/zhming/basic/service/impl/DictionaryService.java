package com.zhming.basic.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tbims.entity.SysDictionary;
import com.zhming.basic.service.IDictionaryService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.DBException;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.StringUtil;
import com.zhming.support.util.UUIDGenerator;

@Service
public class DictionaryService extends BaseService implements IDictionaryService {

	@Override
	public String getItemValueByKey(String key, String itemCd) {
		Map<String, Object> item = dbUtil.queryFirstForMap("", "SELECT * FROM SYS_DICTIONARY WHERE KEY_CD=? AND ITEM_CD=?", key, itemCd);
		return StringUtil.convertString(item.get("item_val"));
	}


	@Override
	public void addDictionary(UserBean userBean, SysDictionary dictionary) throws DBException, ServiceException {
		int count = dbUtil.count("判断字典字段名和项目代码是否重复", "SELECT * FROM SYS_DICTIONARY WHERE KEY_CD=? AND ITEM_CD=?", dictionary.getKeyCd(), dictionary.getItemCd());
		if (count != 0) {
			throw new ServiceException(MSG.BF_ERROR, "字段名和项目代码不能重复添加");
		}
		dictionary.setDictionaryId(UUIDGenerator.getPK());
		dictionary.setStat("Y");
		dbUtil.saveEntity("", dictionary);
	}

	@Override
	public void updateDictionary(UserBean userBean, SysDictionary dictionary) throws DBException {
		dbUtil.updateEntity("", dictionary);
	}

	@Override
	public void deleteBydictionaryCds(UserBean userBean, String keyIds) throws DBException {
		dbUtil.executeSql("", "DELETE FROM SYS_DICTIONARY WHERE DICTIONARY_ID IN (" + keyIds + ")");
	}

	@Override
	public PageBean<Map<String, Object>> listDictionary(UserBean userBean,SysDictionary dictionary,int page,int rows) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A.* FROM SYS_DICTIONARY A WHERE 1=1");

		// 字段名
		if (StringUtil.isNotNull(dictionary.getKeyCd())) {
			sql.append(" AND A.KEY_CD=:KEY_CD");
			params.put("KEY_CD", dictionary.getKeyCd());
		}
		// 字段中文名
		if (StringUtil.isNotNull(dictionary.getKeyName())) {
			sql.append(" AND A.KEY_NAME LIKE :KEY_NAME");
			params.put("KEY_NAME", StringUtil.queryParam(dictionary.getKeyName()));
		}
		// 项目代码
		if(StringUtil.isNotNull(dictionary.getItemCd())){
			sql.append(" AND A.ITEM_CD=:ITEM_CD");
			params.put("ITEM_CD", dictionary.getItemCd());
		}
		// 项目名称
		if (StringUtil.isNotNull(dictionary.getItemName())) {
			sql.append(" AND A.ITEM_NAME LIKE :ITEM_NAME");
			params.put("ITEM_NAME", StringUtil.queryParam(dictionary.getItemName()));
		}
		// 状态
		if (StringUtil.isNotNull(dictionary.getStat())) {
			sql.append(" AND A.STAT=:STAT");
			params.put("STAT", dictionary.getStat());
		}
		sql.append(" ORDER BY A.KEY_CD ,A.ORDER_NUM,A.ITEM_CD");
		
		PageBean<Map<String, Object>> dictionarys = dbUtil.queryPageToMap("", sql.toString(),page,rows, params);
		return dictionarys;
	}
	@Override
	public SysDictionary find(UserBean userBean, String dictionary_id) throws DBException {
		SysDictionary dictionary = dbUtil.findById("", SysDictionary.class, dictionary_id);
		return dictionary;
	}

}
