package com.tbims.db.entity;
// Generated 2017-6-20 9:28:31 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 字典表
 */
@Entity
@Table(name = "SYS_DICTIONARY", schema = "TBIMS")
public class SysDictionary implements java.io.Serializable {

	/** 
	 * 字典编号
	 */
	private String dictionaryId;

	/** 
	 * 字段名
	 */
	private String keyCd;

	/** 
	 * 字段中文名
	 */
	private String keyName;

	/** 
	 * 数据项代码
	 */
	private String itemCd;

	/** 
	 * 数据项名称
	 */
	private String itemName;

	/** 
	 * 数据项值
	 */
	private String itemVal;

	/** 
	 * 状态
	 */
	private String stat;

	/** 
	 * 序号
	 */
	private Long orderNum;

	/** 
	 * 版本号
	 */
	private Date versionNo;

	public SysDictionary() {
	}

	public SysDictionary(String dictionaryId) {
		this.dictionaryId = dictionaryId;
	}
	public SysDictionary(String dictionaryId, String keyCd, String keyName, String itemCd, String itemName, String itemVal, String stat, Long orderNum, Date versionNo) {
		this.dictionaryId = dictionaryId;
		this.keyCd = keyCd;
		this.keyName = keyName;
		this.itemCd = itemCd;
		this.itemName = itemName;
		this.itemVal = itemVal;
		this.stat = stat;
		this.orderNum = orderNum;
		this.versionNo = versionNo;
	}

	/** 
	 * 字典编号.
	 */
	@Id
	@Column(name = "DICTIONARY_ID", unique = true, nullable = false, length = 60)
	public String getDictionaryId() {
		return this.dictionaryId;
	}

	public void setDictionaryId(String dictionaryId) {
		this.dictionaryId = dictionaryId;
	}

	/** 
	 * 字段名.
	 */
	@Column(name = "KEY_CD", length = 100)
	public String getKeyCd() {
		return this.keyCd;
	}

	public void setKeyCd(String keyCd) {
		this.keyCd = keyCd;
	}

	/** 
	 * 字段中文名.
	 */
	@Column(name = "KEY_NAME", length = 100)
	public String getKeyName() {
		return this.keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	/** 
	 * 数据项代码.
	 */
	@Column(name = "ITEM_CD", length = 100)
	public String getItemCd() {
		return this.itemCd;
	}

	public void setItemCd(String itemCd) {
		this.itemCd = itemCd;
	}

	/** 
	 * 数据项名称.
	 */
	@Column(name = "ITEM_NAME", length = 100)
	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/** 
	 * 数据项值.
	 */
	@Column(name = "ITEM_VAL", length = 500)
	public String getItemVal() {
		return this.itemVal;
	}

	public void setItemVal(String itemVal) {
		this.itemVal = itemVal;
	}

	/** 
	 * 状态.
	 */
	@Column(name = "STAT", length = 1)
	public String getStat() {
		return this.stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	/** 
	 * 序号.
	 */
	@Column(name = "ORDER_NUM", precision = 3, scale = 0)
	public Long getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	/** 
	 * 版本号.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "VERSION_NO", length = 7)
	public Date getVersionNo() {
		return this.versionNo;
	}

	public void setVersionNo(Date versionNo) {
		this.versionNo = versionNo;
	}

}
