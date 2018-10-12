package com.tbims.db.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.tbims.rpc.entity.RPCException;

/**
 * Title: 数据操作工具 <br/>
 * Description:
 * @ClassName: DBUtil
 * @author ydc
 * @date 2016-1-7 下午1:41:48
 * 
 */
public interface DBUtil {

	/*---hibernate session 相关操作处理---*/

	/**
	* Title:获取session带事务(当前线程),需要手动提交 <br/>
	 * Description:
	 * @return
	 */
	public Session getSessionByTransaction();
	
	/**
	* Title:获取session带事务(独立的事务，与线程无关),需要手动提交 <br/>
	 * Description:
	 * @return
	 */
	public Session getSessionByTransactionStand();
	
	/**
	* Title:获取session <br/>
	 * Description:
	 * @return
	 */
	public Session getSession();
	
	/**
	* Title:获取当前session <br/>
	 * Description:
	 * @return
	 */
	public Session getCurrentSession();
	
	/**
	 * Title:提交事务 <br/>
	 * Description: hibernate提交事务时session会自动关闭
	 * @param session 当前会话
	 */
	public void commit(Session session);

	/**
	 * Title: 回滚事务<br/>
	 * Description: hibernate回滚事务时session会自动关闭
	 * @param session 当前会话
	 */
	public void rollback(Session session);

	/**
	 * Title: 刷新事务<br/>
	 * Description: 将session中的操作持久化
	 * @param session 当前会话
	 */
	public void flush(Session session);

	/**
	 * Title: 关闭事务 <br/>
	 * Description: 关闭事务时将没提交的事务自动回滚
	 * @param session
	 */
	public void close(Session session);

	/*--实体保存-----*/

	/**
	 * Title:保存实体 <br/>
	 * Description:
	 * @param obj 实体对象
	 * @return 返回实体主键
	 */
	public <T> String saveEntity(String prefix, T obj);

	/**
	 * Title: 带事务保存实体 <br/>
	 * Description:
	 * @param session 当前会话，session由被调用的方法进行事务处理(提交、关闭) 当前会话
	 * @param obj 实体对象
	 * @return 返回实体主键
	 */
	public <T> String saveEntity(String prefix, Session session, T obj);

	/**
	 * Title:保存或更新实体 <br/>
	 * Description:
	 * @param obj 实体对象
	 * @return 返回实体主键
	 */
	public <T> String saveOrUpdateEntity(String prefix, T obj);

	/**
	 * Title: 带事务保存或更新实体 <br/>
	 * Description:
	 * @param session 当前会话，session由被调用的方法进行事务处理(提交、关闭) 当前会话
	 * @param obj 实体对象
	 * @return 返回实体主键
	 */
	public <T> String saveOrUpdateEntity(String prefix, Session session, T obj);

	/**
	 * Title: 批量保存实体<br/>
	 * Description:
	 * @param objList 实体或bean集合list
	 * @return 返回实体主键map
	 */
	public <T> Map<String, T> saveEntityBatch(String prefix, List<T> objList);

	/**
	 * Title: 带事务批量保存实体 <br/>
	 * Description:
	 * @param session 当前会话，session由被调用的方法进行事务处理(提交、关闭)
	 * @param objList 实体或bean集合list
	 * @return 返回实体主键map
	 */
	public <T> Map<String, T> saveEntityBatch(String prefix, Session session, List<T> objList);

	/**
	 * Title: 带事务批量保存或更新实体 <br/>
	 * Description:
	 * @param session 当前会话，session由被调用的方法进行事务处理(提交、关闭)
	 * @param objList 实体或bean集合list
	 * @return 返回实体主键map
	 */
	public <T> Map<String, T> saveOrUpdateEntityBatch(String prefix, Session session, List<T> objList);

	/*--实体更新-----*/

	/**
	 * Title: 更新实体<br/>
	 * Description:
	 * @param obj 实体对象
	 */
	public <T> void updateEntity(String prefix, Object obj);

	/**
	 * Title: 带事务更新实体 <br/>
	 * Description:
	 * @param session 当前会话，session由被调用的方法进行事务处理(提交、关闭)
	 * @param obj 实体对象
	 */
	public <T> void updateEntity(String prefix, Session session, T obj);

	/**
	 * Title: 批量更新实体 <br/>
	 * Description:
	 * @param objList 实体或bean集合list
	 */
	public <T> void updateEntityBatch(String prefix, List<T> objList);

	/**
	 * Title: 带事务批量更新实体 <br/>
	 * Description:
	 * @param session 当前会话，session由被调用的方法进行事务处理(提交、关闭)
	 * @param objList 实体或bean集合list
	 */
	public <T> void updateEntityBatch(String prefix, Session session, List<T> objList);

	/*--实体删除-----*/

	/**
	 * Title: 删除实体根据主键<br/>
	 * Description:
	 * @param entityClass 实体类型
	 * @param id 主键
	 */
	public <T> void deleteEntity(String prefix, Class<T> entityClass, Serializable id);

	/**
	 * Title: 带事务删除实体根据主键<br/>
	 * Description:
	 * @param session 当前会话，session由被调用的方法进行事务处理(提交、关闭)
	 * @param entityClass 实体类型
	 * @param id 主键
	 */
	public <T> void deleteEntity(String prefix, Session session, Class<T> entityClass, Serializable id);

	/*--原生sql相关操作------------begin---------------*/

	/*--数据查询--单条---*/

	/**
	 * Title: 根据主键查询实体<br/>
	 * Description:
	 * @param entityClass 实体类型
	 * @param id 主键
	 * @return 返回实体
	 */
	public <T> T findById(String prefix, Class<T> entityClass, Serializable id);

	/**
	 * 查询列表并对数据进行加锁，调用时记得关闭事务
	 * @param prefix
	 * @param session
	 * @param hqlStr HQL语句
	 * @param lockTable hqlStr中需要加锁的表的别名
	 * @param beanClass
	 * @param params
	 * @return
	 */
	public <T> List<T> queryListToBeanLock(String prefix,Session session, String hqlStr,String lockTable, Class<T> beanClass, Object... params);
	
	/**
	 * 查询列表并对数据进行加锁，调用时记得关闭事务
	 * @param prefix
	 * @param session
	 * @param hqlStr HQL语句
	 * @param lockTable hqlStr中需要加锁的表的别名
	 * @param beanClass
	 * @param params
	 * @return
	 */
	public <T> T queryFirstListToBeanLock(String prefix,Session session, String hqlStr,String lockTable, Class<T> beanClass, Object... params);
	
	/**
	 * Title: 查询数据集的第一条记录，返回Map <br/>
	 * Description:
	 * @param sqlStr 原生sql语句
	 * @param params 执行参数（object数组按顺序对应？或Map数据按key对应）
	 * @return
	 */
	public Map<String, Object> queryFirstForMap(String prefix, String sqlStr, Object... params);

	/**
	 * Title: 查询数据集的第一条记录，并转换成属性列表 <br/>
	 * Description:
	 * @param sqlStr 原生sql语句
	 * @param params 执行参数（object数组按顺序对应？或Map数据按key对应）
	 * @return
	 */
	public List<Map<String, Object>> queryFirstForAttr(String prefix, String sqlStr, Object... params);

	/**
	 * Title: 查询数据集的第一条记录，并转换成存储bean的集合list <br/>
	 * Description:
	 * @param sqlStr 原生sql语句
	 * @param beanClass 普通bean或实体的类型
	 * @param params 执行参数（object数组按顺序对应？或Map数据按key对应）
	 * @return
	 */
	public <T> T queryFirstForBean(String prefix, String sqlStr, Class<T> beanClass, Object... params);
	/*--数据查询--List列表--根据sql语句--*/

	/**
	 * Title: 查询数据集，并转换成存储bean的集合list <br/>
	 * Description:
	 * @param sqlStr 原生sql语句
	 * @param beanClass 普通bean或实体的类型
	 * @param params 执行参数（object数组按顺序对应？或Map数据按key对应）
	 * @return
	 */
	public <T> List<T> queryListToBean(String prefix, String sqlStr, Class<T> beanClass, Object... params);

	/**
	 * Title: 查询数据集，并转换成存储map的集合list <br/>
	 * Description:
	 * @param sqlStr 原生sql语句
	 * @param params 执行参数（object数组按顺序对应？或Map数据按key对应）
	 * @return
	 */
	public List<Map<String, Object>> queryListToMap(String prefix, String sqlStr, Object... params);

	/*--数据查询--map列表--根据sql语句--*/

	/**
	 * Title: 查询数据集，并转换成存储bean的集合Map <br/>
	 * Description:
	 * @param sqlStr 原生sql语句
	 * @param beanClass 普通bean或实体的类型
	 * @param fieldName 普通bean或实体的字段名作为key返回map的key
	 * @param params 执行参数（object数组按顺序对应？或Map数据按key对应）
	 * @return
	 * @throws Exception
	 */
	public <T> Map<String, T> queryMapForBean(String prefix, String sqlStr, Class<T> beanClass, String fieldName, Object... params) throws RPCException;

	/**
	 * Title: 查询数据集，并转换成存储map的集合Map <br/>
	 * Description:
	 * @param sqlStr 原生sql语句
	 * @param fieldName 普通bean或实体的字段名作为返回map的key
	 * @param params 执行参数（object数组按顺序对应？或Map数据按key对应）
	 * @return
	 */
	public Map<String, Map<String, Object>> queryMapForKey(String prefix, String sqlStr, String fieldName, Object... params);

	/*--执行sql语句--*/

	/**
	 * Title:执行sql语句 <br/>
	 * Description:
	 * @param sqlStr 原生sql语句
	 * @param params 执行参数（object数组按顺序对应？或Map数据按key对应）
	 * @return
	 */
	public int executeSql(String prefix, String sqlStr, Object... params);

	/**
	 * Title: 带事务执行sql语句<br/>
	 * Description:
	 * @param session 当前会话，session由被调用的方法进行事务处理(提交、关闭)
	 * @param sqlStr 原生sql语句
	 * @param params 执行参数（object数组按顺序对应？或Map数据按key对应）
	 * @return
	 */
	public int executeSql(String prefix, Session session, String sqlStr, Object... params);

	/*--总记录数--*/

	/**
	 * Title: 查询总记录<br/>
	 * Description:
	 * @param sqlStr 原生sql语句
	 * @param params 执行参数（object数组按顺序对应？或Map数据按key对应）
	 * @return
	 */
	public int count(String prefix, String sqlStr, Object... params);

	/*--原生sql相关操作------------end---------------*/
}
