package com.zhming.support.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.zhming.support.bean.FieldBean;

/**
* Title: bean工具类  <br/>
* Description: 
* @ClassName: BeanUtil
* @author ydc
* @date 2016年12月24日 下午5:46:55
* 
*/
public class BeanUtil {
	/**
	 * Title: 获取类所有字段名 <br/>
	 * Description:
	 * @param cla
	 * @return
	 */
	public static <T> List<FieldBean> getBeanField(Class<T> cla) {
		List<FieldBean> fieldList = new ArrayList<FieldBean>();

		Field[] fields = cla.getDeclaredFields();
		for (Field field : fields) {
			FieldBean fieldBean = new FieldBean();
			fieldBean.setFieldName(field.getName().toLowerCase());
			fieldBean.setFieldType(field.getType().getName());
			fieldList.add(fieldBean);
		}

		return fieldList;
	}

}
