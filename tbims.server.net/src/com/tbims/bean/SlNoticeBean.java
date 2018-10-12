package com.tbims.bean;


public class SlNoticeBean {
	/** 
	 * 公告编号
	 */
	private String noticeId;

	/** 
	 * 操作人
	 */
	private String opeUserId;
	/**
	 * 用户名称
	 */
	private String opeUserName;

	/** 
	 * 操作时间
	 */
	private String opeTime;

	/** 
	 * 标题
	 */
	private String title;

	/** 
	 * 内容
	 */
	private String content;

	/** 
	 * 优先级
	 */
	private Long lev;

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public String getOpeUserId() {
		return opeUserId;
	}

	public void setOpeUserId(String opeUserId) {
		this.opeUserId = opeUserId;
	}

	

	public String getOpeUserName() {
		return opeUserName;
	}

	public void setOpeUserName(String opeUserName) {
		this.opeUserName = opeUserName;
	}
	

	public String getOpeTime() {
		return opeTime;
	}

	public void setOpeTime(String opeTime) {
		this.opeTime = opeTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getLev() {
		return lev;
	}

	public void setLev(Long lev) {
		this.lev = lev;
	}
	
}
