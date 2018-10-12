package com.tbims.bean;

/**
 * Title: 字段信息bean <br/>
 * Description:
 * @ClassName: FieldBean
 * @author ydc
 * @date 2016年12月24日 下午5:33:05
 * 
 */
public class FieldBean {
	/**
	 * 字段名称
	 */
	String fieldName;
	/**
	 * 字段类型
	 */
	String FieldType;

	public FieldBean() {
		super();
	}
	public FieldBean(String fieldName, String fieldType) {
		super();
		this.fieldName = fieldName;
		FieldType = fieldType;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldType() {
		return FieldType;
	}
	public void setFieldType(String fieldType) {
		FieldType = fieldType;
	}
}
