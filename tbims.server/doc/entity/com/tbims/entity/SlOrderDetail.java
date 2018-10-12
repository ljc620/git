package com.tbims.entity;
// Generated 2017-11-6 14:28:08 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: ������ϸ��
 */
@Entity
@Table(name = "SL_ORDER_DETAIL", schema = "TBIMS")
public class SlOrderDetail implements java.io.Serializable {

	/** 
	 * ������ϸ��ID
	 */
	private String orderDetailId;

	/** 
	 * ���������͵ȹ���
	 */
	private String orderId;

	/** 
	 * ��Ʊ����(1-FRID��2-���֤��3-��ά��)
	 */
	private String ticketClass;

	/** 
	 * Ʊ��
	 */
	private Long ticketId;

	/** 
	 * Ʊ��Ψһ�Ż��ַ�����Ʊ��
	 */
	private String ticketUid;

	/** 
	 * ���֤����
	 */
	private String identtyId;

	/** 
	 * Ʊ�ֱ��
	 */
	private String ticketTypeId;

	/** 
	 * ���ô���
	 */
	private Long validateTimes;

	/** 
	 * ԭ����
	 */
	private Long originalPrice;

	/** 
	 * ���ۼ�
	 */
	private Long salePrice;

	/** 
	 * �Ƿ��Ʊ(Y��N��)
	 */
	private String checkFlag;

	/** 
	 * �Ƿ�����(Y��N��)
	 */
	private String uselessFlag;

	/** 
	 * �汾��
	 */
	private Date versionNo;

	/** 
	 * ��Ʊ������
	 */
	private Long outletId;

	/** 
	 * ��Ʊ�ն˱��
	 */
	private Long clientId;

	/** 
	 * ��Ʊ��
	 */
	private String ejectUserId;

	/** 
	 * ��Ʊ״̬(1-����Ʊ 2-�ѳ�Ʊ)
	 */
	private String ejectTicketStat;

	/** 
	 * ��Ʊʱ��
	 */
	private Date ejectTicketTime;

	/** 
	 * ����ʱ��
	 */
	private Date uselessTime;

	/** 
	 * ��Ч��ʼ����
	 */
	private Date validStartDate;

	/** 
	 * ��Ч��������
	 */
	private Date validEndDate;

	/** 
	 * �����ص�״̬(Y:�ѻص� N:δ�ص� S:����)
	 */
	private String orgCallbackStat;

	/** 
	 * �����ص�ʱ��
	 */
	private Date orgCallbackTime;

	/** 
	 * ��Ʊʱ��
	 */
	private Date checkTime;

	public SlOrderDetail() {
	}

	public SlOrderDetail(String orderDetailId, String orderId, String orgCallbackStat) {
		this.orderDetailId = orderDetailId;
		this.orderId = orderId;
		this.orgCallbackStat = orgCallbackStat;
	}

	public SlOrderDetail(String orderDetailId, String orderId, String ticketClass, Long ticketId, String ticketUid,
			String identtyId, String ticketTypeId, Long validateTimes, Long originalPrice, Long salePrice,
			String checkFlag, String uselessFlag, Date versionNo, Long outletId, Long clientId, String ejectUserId,
			String ejectTicketStat, Date ejectTicketTime, Date uselessTime, Date validStartDate, Date validEndDate,
			String orgCallbackStat, Date orgCallbackTime, Date checkTime) {
		this.orderDetailId = orderDetailId;
		this.orderId = orderId;
		this.ticketClass = ticketClass;
		this.ticketId = ticketId;
		this.ticketUid = ticketUid;
		this.identtyId = identtyId;
		this.ticketTypeId = ticketTypeId;
		this.validateTimes = validateTimes;
		this.originalPrice = originalPrice;
		this.salePrice = salePrice;
		this.checkFlag = checkFlag;
		this.uselessFlag = uselessFlag;
		this.versionNo = versionNo;
		this.outletId = outletId;
		this.clientId = clientId;
		this.ejectUserId = ejectUserId;
		this.ejectTicketStat = ejectTicketStat;
		this.ejectTicketTime = ejectTicketTime;
		this.uselessTime = uselessTime;
		this.validStartDate = validStartDate;
		this.validEndDate = validEndDate;
		this.orgCallbackStat = orgCallbackStat;
		this.orgCallbackTime = orgCallbackTime;
		this.checkTime = checkTime;
	}

	/** 
	 * ������ϸ��ID.
	 */
	@Id
	@Column(name = "ORDER_DETAIL_ID", unique = true, nullable = false, length = 60)
	public String getOrderDetailId() {
		return this.orderDetailId;
	}

	/** 
	 * ������ϸ��ID.
	 */
	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	/** 
	 * ���������͵ȹ���.
	 */
	@Column(name = "ORDER_ID", nullable = false, length = 50)
	public String getOrderId() {
		return this.orderId;
	}

	/** 
	 * ���������͵ȹ���.
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/** 
	 * ��Ʊ����(1-FRID��2-���֤��3-��ά��).
	 */
	@Column(name = "TICKET_CLASS", length = 1)
	public String getTicketClass() {
		return this.ticketClass;
	}

	/** 
	 * ��Ʊ����(1-FRID��2-���֤��3-��ά��).
	 */
	public void setTicketClass(String ticketClass) {
		this.ticketClass = ticketClass;
	}

	/** 
	 * Ʊ��.
	 */
	@Column(name = "TICKET_ID", precision = 15, scale = 0)
	public Long getTicketId() {
		return this.ticketId;
	}

	/** 
	 * Ʊ��.
	 */
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	/** 
	 * Ʊ��Ψһ�Ż��ַ�����Ʊ��.
	 */
	@Column(name = "TICKET_UID", length = 50)
	public String getTicketUid() {
		return this.ticketUid;
	}

	/** 
	 * Ʊ��Ψһ�Ż��ַ�����Ʊ��.
	 */
	public void setTicketUid(String ticketUid) {
		this.ticketUid = ticketUid;
	}

	/** 
	 * ���֤����.
	 */
	@Column(name = "IDENTTY_ID", length = 18)
	public String getIdenttyId() {
		return this.identtyId;
	}

	/** 
	 * ���֤����.
	 */
	public void setIdenttyId(String identtyId) {
		this.identtyId = identtyId;
	}

	/** 
	 * Ʊ�ֱ��.
	 */
	@Column(name = "TICKET_TYPE_ID", length = 3)
	public String getTicketTypeId() {
		return this.ticketTypeId;
	}

	/** 
	 * Ʊ�ֱ��.
	 */
	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	/** 
	 * ���ô���.
	 */
	@Column(name = "VALIDATE_TIMES", precision = 3, scale = 0)
	public Long getValidateTimes() {
		return this.validateTimes;
	}

	/** 
	 * ���ô���.
	 */
	public void setValidateTimes(Long validateTimes) {
		this.validateTimes = validateTimes;
	}

	/** 
	 * ԭ����.
	 */
	@Column(name = "ORIGINAL_PRICE", precision = 5, scale = 0)
	public Long getOriginalPrice() {
		return this.originalPrice;
	}

	/** 
	 * ԭ����.
	 */
	public void setOriginalPrice(Long originalPrice) {
		this.originalPrice = originalPrice;
	}

	/** 
	 * ���ۼ�.
	 */
	@Column(name = "SALE_PRICE", precision = 5, scale = 0)
	public Long getSalePrice() {
		return this.salePrice;
	}

	/** 
	 * ���ۼ�.
	 */
	public void setSalePrice(Long salePrice) {
		this.salePrice = salePrice;
	}

	/** 
	 * �Ƿ��Ʊ(Y��N��).
	 */
	@Column(name = "CHECK_FLAG", length = 1)
	public String getCheckFlag() {
		return this.checkFlag;
	}

	/** 
	 * �Ƿ��Ʊ(Y��N��).
	 */
	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	/** 
	 * �Ƿ�����(Y��N��).
	 */
	@Column(name = "USELESS_FLAG", length = 1)
	public String getUselessFlag() {
		return this.uselessFlag;
	}

	/** 
	 * �Ƿ�����(Y��N��).
	 */
	public void setUselessFlag(String uselessFlag) {
		this.uselessFlag = uselessFlag;
	}

	/** 
	 * �汾��.
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "VERSION_NO", length = 7)
	public Date getVersionNo() {
		return this.versionNo;
	}

	/** 
	 * �汾��.
	 */
	public void setVersionNo(Date versionNo) {
		this.versionNo = versionNo;
	}

	/** 
	 * ��Ʊ������.
	 */
	@Column(name = "OUTLET_ID", precision = 6, scale = 0)
	public Long getOutletId() {
		return this.outletId;
	}

	/** 
	 * ��Ʊ������.
	 */
	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}

	/** 
	 * ��Ʊ�ն˱��.
	 */
	@Column(name = "CLIENT_ID", precision = 10, scale = 0)
	public Long getClientId() {
		return this.clientId;
	}

	/** 
	 * ��Ʊ�ն˱��.
	 */
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	/** 
	 * ��Ʊ��.
	 */
	@Column(name = "EJECT_USER_ID", length = 50)
	public String getEjectUserId() {
		return this.ejectUserId;
	}

	/** 
	 * ��Ʊ��.
	 */
	public void setEjectUserId(String ejectUserId) {
		this.ejectUserId = ejectUserId;
	}

	/** 
	 * ��Ʊ״̬(1-����Ʊ 2-�ѳ�Ʊ).
	 */
	@Column(name = "EJECT_TICKET_STAT", length = 1)
	public String getEjectTicketStat() {
		return this.ejectTicketStat;
	}

	/** 
	 * ��Ʊ״̬(1-����Ʊ 2-�ѳ�Ʊ).
	 */
	public void setEjectTicketStat(String ejectTicketStat) {
		this.ejectTicketStat = ejectTicketStat;
	}

	/** 
	 * ��Ʊʱ��.
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "EJECT_TICKET_TIME", length = 7)
	public Date getEjectTicketTime() {
		return this.ejectTicketTime;
	}

	/** 
	 * ��Ʊʱ��.
	 */
	public void setEjectTicketTime(Date ejectTicketTime) {
		this.ejectTicketTime = ejectTicketTime;
	}

	/** 
	 * ����ʱ��.
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "USELESS_TIME", length = 7)
	public Date getUselessTime() {
		return this.uselessTime;
	}

	/** 
	 * ����ʱ��.
	 */
	public void setUselessTime(Date uselessTime) {
		this.uselessTime = uselessTime;
	}

	/** 
	 * ��Ч��ʼ����.
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "VALID_START_DATE", length = 7)
	public Date getValidStartDate() {
		return this.validStartDate;
	}

	/** 
	 * ��Ч��ʼ����.
	 */
	public void setValidStartDate(Date validStartDate) {
		this.validStartDate = validStartDate;
	}

	/** 
	 * ��Ч��������.
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "VALID_END_DATE", length = 7)
	public Date getValidEndDate() {
		return this.validEndDate;
	}

	/** 
	 * ��Ч��������.
	 */
	public void setValidEndDate(Date validEndDate) {
		this.validEndDate = validEndDate;
	}

	/** 
	 * �����ص�״̬(Y:�ѻص� N:δ�ص� S:����).
	 */
	@Column(name = "ORG_CALLBACK_STAT", nullable = false, length = 1)
	public String getOrgCallbackStat() {
		return this.orgCallbackStat;
	}

	/** 
	 * �����ص�״̬(Y:�ѻص� N:δ�ص� S:����).
	 */
	public void setOrgCallbackStat(String orgCallbackStat) {
		this.orgCallbackStat = orgCallbackStat;
	}

	/** 
	 * �����ص�ʱ��.
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ORG_CALLBACK_TIME", length = 7)
	public Date getOrgCallbackTime() {
		return this.orgCallbackTime;
	}

	/** 
	 * �����ص�ʱ��.
	 */
	public void setOrgCallbackTime(Date orgCallbackTime) {
		this.orgCallbackTime = orgCallbackTime;
	}

	/** 
	 * ��Ʊʱ��.
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CHECK_TIME", length = 7)
	public Date getCheckTime() {
		return this.checkTime;
	}

	/** 
	 * ��Ʊʱ��.
	 */
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

}
