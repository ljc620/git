package com.webservice.entity;

/**
 * Title: 售检数据实体 <br/>
 * Description:
 * 
 * @ClassName: SaleCheckEntity
 * @author ydc
 * @date 2017年7月25日 下午12:54:38
 * 
 */
public class SaleCheckEntity {
	/**
	 * 销售数量
	 */
	private int saleCount;
	/**
	 * 入园数量
	 */
	private int checkinCount;

	public int getSaleCount() {
		return saleCount;
	}

	public void setSaleCount(int saleCount) {
		this.saleCount = saleCount;
	}

	public int getCheckinCount() {
		return checkinCount;
	}

	public void setCheckinCount(int checkinCount) {
		this.checkinCount = checkinCount;
	}
}
