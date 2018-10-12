package com.tbims.db.util.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONArray;
import com.tbims.bean.FieldBean;
import com.tbims.db.util.DBUtil;
import com.tbims.rpc.entity.RPCException;
import com.tbims.util.AnnotationUtil;
import com.tbims.util.BeanUtil;
import com.tbims.util.StringUtil;

@Repository
public class DBUtilImpl implements DBUtil {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private SessionFactory sessionFactory;

	/*---session方法处理---*/

	@Override
	public Session getSession(){
		Session session = sessionFactory.openSession();
		return session;
	}
	
	@Override
	public Session getCurrentSession(){
		Session session = sessionFactory.getCurrentSession();
		return session;
	}
	
	@Override
	public Session getSessionByTransaction() {
		Session session = sessionFactory.getCurrentSession();
		if (!session.getTransaction().isActive()) {
			session.beginTransaction();
		}
		return session;
	}
	
	@Override
	public Session getSessionByTransactionStand() {
		Session session = sessionFactory.openSession();
		if (!session.getTransaction().isActive()) {
			session.beginTransaction();
		}
		return session;
	}

	@Override
	public void commit(Session session) {
		if (session != null && session.isOpen() && session.getTransaction().isActive()) {
			session.getTransaction().commit();
		}
	}

	@Override
	public void close(Session session) {
		if (session != null && session.isOpen()) {
			session.close();
		}
	}

	@Override
	public void rollback(Session session) {
		if (session != null && session.isOpen() && session.getTransaction().isActive()) {
			session.getTransaction().rollback();
		}
	}

	@Override
	public void flush(Session session) {
		if (session != null && session.isOpen() && session.getTransaction().isActive()) {
			session.flush();
			;
		}
	}

	/*--实体保存-----*/

	@Override
	public <T> String saveEntity(String prefix, T obj) {
		MDC.put("PREFIX", "保存对象:" + prefix);
		Serializable pkKey;
		Session session = getSessionByTransaction();
		try {
			pkKey = saveEntity(prefix, session, obj);
			this.commit(session);
		} finally {
			this.close(session);
		}
		MDC.remove("PREFIX");
		return pkKey.toString();
	}

	@Override
	public <T> String saveEntity(String prefix, Session session, T obj) {
		MDC.put("PREFIX", "保存对象:" + prefix);
		this.printLog(obj);
		Serializable pkKey = session.save(obj);
		MDC.remove("PREFIX");
		return pkKey.toString();
	}

	@Override
	public <T> String saveOrUpdateEntity(String prefix, T obj) {
		MDC.put("PREFIX", "保存或更新对象:" + prefix);
		Serializable pkKey;
		Session session = getSessionByTransaction();
		try {
			pkKey = saveOrUpdateEntity(prefix, session, obj);
			this.commit(session);
		} finally {
			this.close(session);
		}
		MDC.remove("PREFIX");
		return pkKey.toString();
	}

	@Override
	public <T> String saveOrUpdateEntity(String prefix, Session session, T obj) {
		MDC.put("PREFIX", "保存或更新对象:" + prefix);
		this.printLog(obj);
		Serializable pkKey = (Serializable) session.merge(obj);
		MDC.remove("PREFIX");
		return pkKey.toString();
	}

	@Override
	public <T> Map<String, T> saveEntityBatch(String prefix, List<T> objList) {
		MDC.put("PREFIX", "批量保存对象:保存对象:" + prefix);
		Session session = getSessionByTransaction();
		Map<String, T> pkMap = new HashMap<String, T>();
		try {
			pkMap = this.saveEntityBatch(prefix, session, objList);
			this.commit(session);
		} finally {
			this.close(session);
		}
		MDC.remove("PREFIX");
		return pkMap;
	}

	@Override
	public <T> Map<String, T> saveEntityBatch(String prefix, Session session, List<T> objList) {
		MDC.put("PREFIX", "批量保存对象:" + prefix);

		Map<String, T> pkMap = new HashMap<String, T>();
		int i = 1;
		for (T obj : objList) {
			Serializable pkKey = session.save(obj);
			pkMap.put(pkKey.toString(), obj);
			printLog(obj);
			if (i % 50 == 0) {
				session.flush();
				session.clear();
			}
			i++;
		}
		MDC.remove("PREFIX");
		return pkMap;
	}

	@Override
	public <T> Map<String, T> saveOrUpdateEntityBatch(String prefix, Session session, List<T> objList) {
		MDC.put("PREFIX", "批量保存对象:" + prefix);

		Map<String, T> pkMap = new HashMap<String, T>();
		int i = 1;
		for (T obj : objList) {
			Object pkKey = session.merge(obj);
			pkMap.put(pkKey.toString(), obj);
			printLog(obj);
			if (i % 50 == 0) {
				session.flush();
				session.clear();
			}
			i++;
		}
		MDC.remove("PREFIX");
		return pkMap;
	}

	/*--实体更新-----*/

	@Override
	public <T> void updateEntity(String prefix, Object obj) {
		MDC.put("PREFIX", "更新对象:" + prefix);
		Session session = getSessionByTransaction();
		try {
			updateEntity(prefix, session, obj);
			commit(session);
		} finally {
			this.close(session);
			MDC.remove("PREFIX");
		}
	}

	@Override
	public <T> void updateEntity(String prefix, Session session, T obj) {
		MDC.put("PREFIX", "更新对象:" + prefix);
		this.printLog(obj);
		session.update(obj);
	}

	@Override
	public <T> void updateEntityBatch(String prefix, List<T> objList) {
		MDC.put("PREFIX", "批量更新对象:" + prefix);
		Session session = getSessionByTransaction();
		try {
			updateEntity(prefix, session, objList);
			this.commit(session);
		} finally {
			this.close(session);
		}
	}

	@Override
	public <T> void updateEntityBatch(String prefix, Session session, List<T> objList) {
		MDC.put("PREFIX", "批量更新对象:" + prefix);
		int i = 1;
		for (T obj : objList) {
			this.printLog(obj);
			session.update(obj);
			if (i % 50 == 0) {
				session.flush();
				session.clear();
			}
			i++;
		}
	}

	/*--实体删除-----*/

	@Override
	public <T> void deleteEntity(String prefix, Class<T> entityClass, Serializable id) {
		MDC.put("PREFIX", "删除对象:" + prefix);
		Session session = getSessionByTransaction();
		try {
			deleteEntity(prefix, session, entityClass, id);
			this.commit(session);
		} finally {
			this.close(session);
		}
	}

	@Override
	public <T> void deleteEntity(String prefix, Session session, Class<T> entityClass, Serializable id) {
		MDC.put("PREFIX", "删除对象:" + prefix);
		Object obj = session.get(entityClass, id);
		this.printLog(obj);
		session.delete(obj);
	}

	/*--原生sql相关操作------------begin---------------*/

	/*--数据查询--单条---*/

	@SuppressWarnings("unchecked")
	@Override
	public <T> T findById(String prefix, Class<T> entityClass, Serializable id) {
		MDC.put("PREFIX", "查找对象:" + prefix);
		Session session = getSession();
		T obj;
		try {
			this.printLog(id);
			obj = (T) session.get(entityClass, id);
		} finally {
			this.close(session);
		}
		MDC.remove("PREFIX");
		return obj;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public <T> List<T> queryListToBeanLock(String prefix, Session session, String hqlStr, String lockTable, Class<T> beanClass, Object... params) {
		MDC.put("PREFIX", "查询列表:" + prefix);
		List<T> result;
		Query query = session.createQuery(hqlStr);
		query.setLockMode(lockTable, LockMode.PESSIMISTIC_READ);

		// 根据实体类与普通bean 做不同处理
		// 实体类型直接查询，普通bean根据字段名设置标量值
		this.setQueryParameters(prefix, query, params);
		result = (List<T>) query.list();
		
		MDC.remove("PREFIX");
		return result;
	}

	public <T> T queryFirstListToBeanLock(String prefix, Session session, String hqlStr, String lockTable, Class<T> beanClass, Object... params) {
		List<T> reList = queryListToBeanLock(prefix, session, hqlStr, lockTable, beanClass, params);
		if (reList == null || reList.size() == 0) {
			return null;
		}
		return reList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryFirstForMap(String prefix, String sqlStr, Object... params) {
		MDC.put("PREFIX", "查询第一行:" + prefix);
		Session session = getSession();
		try {
			SQLQuery query = session.createSQLQuery(sqlStr);
			query.setResultTransformer(MyMapResultTransformer.INSTANCE);

			this.setQueryParameters(prefix, query, params);

			// 从结果集中获取1条记录
			query.setFetchSize(1);

			List<Map<String, Object>> result = (List<Map<String, Object>>) query.list();
			if (result != null && result.size() != 0) {
				return result.get(0);
			}

		} finally {
			this.close(session);
			MDC.remove("PREFIX");
		}

		return null;
	}

	@Override
	public List<Map<String, Object>> queryFirstForAttr(String prefix, String sqlStr, Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryFirstForBean(String prefix, String sqlStr, Class<T> beanClass, Object... params) {
		List<T> reList = queryListToBean(prefix, sqlStr, beanClass, params);
		if (reList == null || reList.size() == 0) {
			return null;
		}
		return reList.get(0);
	}

	/*--数据查询--List列表--根据sql语句--*/

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> queryListToBean(String prefix, String sqlStr, Class<T> beanClass, Object... params) {
		MDC.put("PREFIX", "查询列表:" + prefix);
		Session session = getSession();
		List<T> result;
		try {
			SQLQuery query = session.createSQLQuery(sqlStr);

			// 根据实体类与普通bean 做不同处理
			// 实体类型直接查询，普通bean根据字段名设置标量值
			if (AnnotationUtil.isEntity(beanClass)) {
				query.addEntity(beanClass);
			} else {
				// setScalar(query, beanClass);
				query.setResultTransformer(new MyBeanResultTransformer(beanClass));
			}

			this.setQueryParameters(prefix, query, params);

			result = (List<T>) query.list();

		} finally {
			this.close(session);
			MDC.remove("PREFIX");
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryListToMap(String prefix, String sqlStr, Object... params) {
		MDC.put("PREFIX", "查询列表:" + prefix);
		Session session = getSession();

		List<Map<String, Object>> result = null;
		try {
			SQLQuery query = session.createSQLQuery(sqlStr);

			query.setResultTransformer(MyMapResultTransformer.INSTANCE);

			this.setQueryParameters(prefix, query, params);

			result = (List<Map<String, Object>>) query.list();

		} finally {
			this.close(session);
			MDC.put("PREFIX", "分页查询列表:" + prefix);
		}

		return result;
	}

	/*--数据查询--map列表--根据sql语句--*/

	@Override
	public Map<String, Map<String, Object>> queryMapForKey(String prefix, String sqlStr, String fieldName, Object... params) {
		return null;
	}

	@Override
	public <T> Map<String, T> queryMapForBean(String prefix, String sqlStr, Class<T> beanClass, String fieldName, Object... params) throws RPCException {
		List<T> retList = queryListToBean(prefix, sqlStr, beanClass, params);
		Map<String, T> retMap = new HashMap<String, T>();
		String pkIdB = "";
		String fieldNameB = "";
		if (fieldName.contains(".")) {// 联合获取,适用于联合主键
			pkIdB = fieldName.split("\\.")[0];
			fieldNameB = fieldName.split("\\.")[1];
		}

		for (T obj : retList) {
			String key = null;
			try {
				if (fieldName.contains(".")) {// 联合获取,适用于联合主键
					// 获取主键对象
					Object pkId = BeanUtils.getPropertyDescriptor(beanClass, pkIdB).getReadMethod().invoke(obj);
					// 获取值
					key = StringUtil.convertString(BeanUtils.getPropertyDescriptor(pkId.getClass(), fieldNameB).getReadMethod().invoke(pkId));
				} else {
					// 直接获取属性
					key = StringUtil.convertString(BeanUtils.getPropertyDescriptor(beanClass, fieldName).getReadMethod().invoke(obj));
				}

			} catch (Exception e) {
				logger.error("获取bean值出错", e);
				throw new RPCException(3, "获取bean值出错");
			}
			retMap.put(key, obj);
		}
		return retMap;
	}

	/*--执行sql语句--*/

	@Override
	public int executeSql(String prefix, String sqlStr, Object... params) {
		MDC.put("PREFIX", "执行语句:" + prefix);
		Session session = getSessionByTransaction();
		int status;
		try {
			status = this.executeSql(prefix, session, sqlStr, params);
			this.commit(session);
		} finally {
			this.close(session);
			MDC.put("PREFIX", "分页查询列表:" + prefix);
		}

		return status;
	}

	@Override
	public int executeSql(String prefix, Session session, String sqlStr, Object... params) {
		MDC.put("PREFIX", "执行语句:" + prefix);
		SQLQuery query = session.createSQLQuery(sqlStr);
		this.setQueryParameters(prefix, query, params);

		int status = query.executeUpdate();

		return status;
	}

	@Override
	public int count(String prefix, String sqlStr, Object... params) {
		MDC.put("PREFIX", "查询总记录:" + prefix);
		Session session = getSession();
		int num;
		try {
			String countSql = convertCountSql(sqlStr);
			SQLQuery query = session.createSQLQuery(countSql);
			this.setQueryParameters(prefix, query, params);
			@SuppressWarnings("rawtypes")
			List resultList = query.list();
			if (resultList != null && resultList.size() == 0) {
				return 0;
			}
			Object totalNum = resultList.get(0);
			if (totalNum instanceof BigDecimal) {
				num = ((BigDecimal) totalNum).intValue();
			} else if (totalNum instanceof Long) {
				num = ((Long) totalNum).intValue();
			} else {
				num = (Integer) totalNum;
			}
		} finally {
			this.close(session);
			MDC.put("PREFIX", "分页查询列表:" + prefix);
		}
		return num;
	}

	/*--原生sql相关操作------------end---------------*/

	/*--公用方法--*/

	protected <T> void setScalar(SQLQuery query, Class<T> beanClass) {
		List<FieldBean> fields = BeanUtil.getBeanField(beanClass);
		for (FieldBean field : fields) {
			if (field.getFieldType().equals("java.lang.String")) {
				query.addScalar(field.getFieldName(), StringType.INSTANCE);
			} else {
				query.addScalar(field.getFieldName());
			}
		}
	}

	protected String convertCountSql(String sql) {
		String convertSql = "SELECT COUNT(1) FROM (" + sql + ")";
		return convertSql;
	}

	@SuppressWarnings("unchecked")
	protected void setQueryParameters(String prefix, SQLQuery query, Object... params) {
		StringBuilder msgParam = new StringBuilder();
		if (params != null && params.length != 0) {
			for (int i = 0; i < params.length; i++) {
				if (params[i] instanceof Map) {// 参数为Map,则名称参数
					Map<String, Object> paramMap = (Map<String, Object>) params[i];
					for (String paramName : paramMap.keySet()) {
						query.setParameter(paramName.toUpperCase(), paramMap.get(paramName));
						msgParam.append("{" + paramName.toUpperCase() + ":[" + paramMap.get(paramName) + "]},");
					}
				} else {// 非Map，则用
					int j = i;
					query.setParameter(j, params[i]);
					msgParam.append("{" + j + ":[" + params[i] + "]},");
				}
			}
		}
		if (logger.isDebugEnabled()) {
			if (StringUtil.isNull(msgParam.toString())) {
				msgParam.append("无");
			}
			logger.debug("-执行参数:" + msgParam.toString());
		}
	}

	@SuppressWarnings("unchecked")
	protected void setQueryParameters(String prefix, Query query, Object... params) {
		StringBuilder msgParam = new StringBuilder();
		if (params != null && params.length != 0) {
			for (int i = 0; i < params.length; i++) {
				if (params[i] instanceof Map) {// 参数为Map,则名称参数
					Map<String, Object> paramMap = (Map<String, Object>) params[i];
					for (String paramName : paramMap.keySet()) {
						query.setParameter(paramName.toUpperCase(), paramMap.get(paramName));
						msgParam.append("{" + paramName.toUpperCase() + ":[" + paramMap.get(paramName) + "]},");
					}
				} else {// 非Map，则用
					int j = i;
					query.setParameter(j, params[i]);
					msgParam.append("{" + j + ":[" + params[i] + "]},");
				}
			}
		}
		if (logger.isDebugEnabled()) {
			if (StringUtil.isNull(msgParam.toString())) {
				msgParam.append("无");
			}
			logger.debug("-执行参数:" + msgParam.toString());
		}
	}

	protected void printLog(Object obj) {
		if (logger.isDebugEnabled()) {
			logger.debug("-执行参数:" + JSONArray.toJSONString(obj));
		}
	}

}
