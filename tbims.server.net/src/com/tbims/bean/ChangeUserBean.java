package com.tbims.bean;

public class ChangeUserBean {

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
	 * 换票人证件名称
	 */
	private String cardTypeName;

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

	public String getChangeUserId() {
		return changeUserId;
	}

	public void setChangeUserId(String changeUserId) {
		this.changeUserId = changeUserId;
	}

	public String getChangeUserName() {
		return changeUserName;
	}

	public void setChangeUserName(String changeUserName) {
		this.changeUserName = changeUserName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}
	
}
