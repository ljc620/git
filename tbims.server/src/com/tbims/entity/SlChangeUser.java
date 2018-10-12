package com.tbims.entity;
// Generated 2017-6-20 9:28:31 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity: 换票人表
 */
@Entity
@Table(name = "SL_CHANGE_USER", schema = "TBIMS")
public class SlChangeUser implements java.io.Serializable {

	/** 
	 * 换票人编号
	 */
	private String changeUserId;

	/** 
	 * 换票人名称
	 */
	private String changeUserName;

	/** 
	 * 换票人证件类型
	 */
	private String cardType;

	/** 
	 * 换票人证件号码
	 */
	private String cardId;

	/** 
	 * 联系电话
	 */
	private String tel;

	/** 
	 * 电子邮箱
	 */
	private String mail;

	public SlChangeUser() {
	}

	public SlChangeUser(String changeUserId) {
		this.changeUserId = changeUserId;
	}
	public SlChangeUser(String changeUserId, String changeUserName, String cardType, String cardId, String tel, String mail) {
		this.changeUserId = changeUserId;
		this.changeUserName = changeUserName;
		this.cardType = cardType;
		this.cardId = cardId;
		this.tel = tel;
		this.mail = mail;
	}

	/** 
	 * 换票人编号.
	 */
	@Id
	@Column(name = "CHANGE_USER_ID", unique = true, nullable = false, length = 60)
	public String getChangeUserId() {
		return this.changeUserId;
	}

	public void setChangeUserId(String changeUserId) {
		this.changeUserId = changeUserId;
	}

	/** 
	 * 换票人名称.
	 */
	@Column(name = "CHANGE_USER_NAME", length = 100)
	public String getChangeUserName() {
		return this.changeUserName;
	}

	public void setChangeUserName(String changeUserName) {
		this.changeUserName = changeUserName;
	}

	/** 
	 * 换票人证件类型.
	 */
	@Column(name = "CARD_TYPE", length = 2)
	public String getCardType() {
		return this.cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	/** 
	 * 换票人证件号码.
	 */
	@Column(name = "CARD_ID", length = 100)
	public String getCardId() {
		return this.cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	/** 
	 * 联系电话.
	 */
	@Column(name = "TEL", length = 20)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	/** 
	 * 电子邮箱.
	 */
	@Column(name = "MAIL", length = 30)
	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

}
