package com.tbims.db.util.impl;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

import com.tbims.util.RowMapUtil;
import com.tbims.util.StringUtil;

public class MyMapResultTransformer extends AliasedTupleSubsetResultTransformer {
	private static final long serialVersionUID = 8896839512802622479L;
	public static final MyMapResultTransformer INSTANCE = new MyMapResultTransformer();
	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Map<String, Object> result = new HashMap<String, Object>(tuple.length);
		for (int i = 0; i < tuple.length; i++) {
			String alias = aliases[i];
			if (alias != null) {
				alias = StringUtil.underlineToCamel(alias);
				if (tuple[i] != null) {
					result.put(alias, RowMapUtil.transValue(tuple[i]));
				} else {
					result.put(alias, tuple[i]);
				}
			}
		}

		return result;
	}

	@Override
	public boolean isTransformedValueATupleElement(String[] arg0, int arg1) {
		return false;
	}
}
