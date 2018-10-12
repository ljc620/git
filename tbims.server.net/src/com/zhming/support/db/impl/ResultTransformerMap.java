package com.zhming.support.db.impl;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

/**
* Title:  重写hibernate转换map类,将大写转换为小写  <br/>
* Description: 
* @ClassName: ResultTransformerMap
* @author ydc
* @date 2016年12月10日 下午7:59:33
* 
*/
public class ResultTransformerMap extends AliasedTupleSubsetResultTransformer {

	private static final long serialVersionUID = 1L;
	
	public static final ResultTransformerMap INSTANCE = new ResultTransformerMap();

	/**
	 * Disallow instantiation of AliasToEntityMapResultTransformer.
	 */
	private ResultTransformerMap() {
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Map result = new HashMap(tuple.length);
		for (int i = 0; i < tuple.length; i++) {
			String alias = aliases[i];
			if (alias != null) {
				// 将列表转换成小写 add by ydc 20161210
				alias=alias.toLowerCase();
				result.put(alias, tuple[i]);
			}
		}
		return result;
	}

	@Override
	public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
		return false;
	}

	/**
	 * Serialization hook for ensuring singleton uniqueing.
	 *
	 * @return The singleton instance : {@link #INSTANCE}
	 */
	private Object readResolve() {
		return INSTANCE;
	}

}
