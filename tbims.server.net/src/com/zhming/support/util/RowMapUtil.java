package com.zhming.support.util;

import java.math.BigDecimal;

public class RowMapUtil {
	public static Object transValue(Object obj, String retrunTypeName) {
		if (obj == null) {
			return null;
		}
		try {
			String fieldtypename = obj.getClass().getName();
			if (fieldtypename.equals("java.math.BigDecimal")) {
				if (retrunTypeName.equals("java.math.BigDecimal")) {// 包含浮点
					obj = (BigDecimal) obj;
				} else if (retrunTypeName.equals("java.lang.Long") || retrunTypeName.equals("long")) {
					obj = new Long(obj.toString());
				} else if (retrunTypeName.equals("java.lang.Double") || retrunTypeName.equals("double")) {
					obj = new Double(obj.toString());
				} else if (retrunTypeName.equals("java.lang.Integer") || retrunTypeName.equals("int")) {
					obj = new Integer(obj.toString());
				}
			} else if (fieldtypename.equals("java.sql.Date")) {
				obj = new java.util.Date(((java.sql.Date) obj).getTime());
			} else if (fieldtypename.equals("java.sql.Timestamp")) {
				obj = new java.util.Date(((java.sql.Timestamp) obj).getTime());
			} else if (fieldtypename.equals("java.util.Date")) {
				obj = (java.util.Date) obj;
			} else if (fieldtypename.equals("java.lang.Character")) {
				obj = obj.toString();
			} else {
				fieldtypename.equals("Object");
				obj = (Object) obj;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return obj;
	}

	public static Object transValue(Object obj) {
		if (obj == null) {
			return null;
		}
		try {
			String fieldtypename = obj.getClass().getName();
			if (fieldtypename.equals("java.math.BigDecimal")) {
				if (obj.toString().indexOf(".") > 0) {// 包含浮点
					obj = (BigDecimal) obj;
				} else {
					obj = new Long(obj.toString());
				}
			} else if (fieldtypename.equals("java.sql.Date")) {
				obj = new java.util.Date(((java.sql.Date) obj).getTime());
			} else if (fieldtypename.equals("java.sql.Timestamp")) {
				obj = new java.util.Date(((java.sql.Timestamp) obj).getTime());
			} else if (fieldtypename.equals("java.util.Date")) {
				obj = (java.util.Date) obj;
			} else if (fieldtypename.equals("java.lang.Character")) {
				obj = obj.toString();
			}

			else {
				fieldtypename.equals("Object");
				obj = (Object) obj;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return obj;
	}
}
