package com.tbims.entity;
// Generated 2017-7-3 16:35:08 by Hibernate Tools 4.0.0

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhming.support.common.DateSerializer;

/**
 * Entity: 公告表
 */
@Entity
@Table(name = "SL_NOTICE", schema = "TBIMS")
public class SlNotice implements java.io.Serializable {

	/** 
	 * 公告编号
	 */
	private String noticeId;

	/** 
	 * 操作人
	 */
	private String opeUserId;

	/** 
	 * 操作时间
	 */
	private Date opeTime;

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

	public SlNotice() {
	}

	public SlNotice(String noticeId) {
		this.noticeId = noticeId;
	}
	public SlNotice(String noticeId, String opeUserId, Date opeTime, String title, String content, Long lev) {
		this.noticeId = noticeId;
		this.opeUserId = opeUserId;
		this.opeTime = opeTime;
		this.title = title;
		this.content = content;
		this.lev = lev;
	}

	/** 
	 * 公告编号.
	 */
	@Id
	@Column(name = "NOTICE_ID", unique = true, nullable = false, length = 60)
	public String getNoticeId() {
		return this.noticeId;
	}
	/** 
	 * 公告编号.
	 */
	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	/** 
	 * 操作人.
	 */
	@Column(name = "OPE_USER_ID", length = 50)
	public String getOpeUserId() {
		return this.opeUserId;
	}
	/** 
	 * 操作人.
	 */
	public void setOpeUserId(String opeUserId) {
		this.opeUserId = opeUserId;
	}

	/** 
	 * 操作时间.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = DateSerializer.class)
	@Column(name = "OPE_TIME", length = 7)
	public Date getOpeTime() {
		return this.opeTime;
	}
	/** 
	 * 操作时间.
	 */
	public void setOpeTime(Date opeTime) {
		this.opeTime = opeTime;
	}

	/** 
	 * 标题.
	 */
	@Column(name = "TITLE", length = 100)
	public String getTitle() {
		return this.title;
	}
	/** 
	 * 标题.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/** 
	 * 内容.
	 */
	@Column(name = "CONTENT", length = 1000)
	public String getContent() {
		return this.content;
	}
	/** 
	 * 内容.
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/** 
	 * 优先级.
	 */
	@Column(name = "LEV", precision = 5, scale = 0)
	public Long getLev() {
		return this.lev;
	}
	/** 
	 * 优先级.
	 */
	public void setLev(Long lev) {
		this.lev = lev;
	}

}
