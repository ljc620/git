package com.zhming.support.bean;

import java.util.List;

import com.zhming.support.common.Constant;

/**
 * Title: 分页实体 <br/>
 * Description:
 * 
 * @ClassName: PageBean
 * @author ydc
 * @date 2016-1-7 下午5:03:23
 * 
 * @param <T>
 */
public class PageBean<T> {

	public PageBean(int nowPage, int pageSize, int total, List<T> rows) {
		this.nowPage = nowPage;
		this.pageSize = pageSize;
		this.total = total;
		this.rows = rows;
		if (pageSize != 0) {
			this.totalPage = (this.total + this.pageSize - 1) / this.pageSize;
			if (this.total == 0 || this.nowPage > this.totalPage) {
				this.nowPage = 1;
			}
		} else {//不分页
			this.totalPage = 1;
			this.nowPage = 1;
		}
	}

	public PageBean() {
	}

	/**
	 * 当前页
	 */
	private int nowPage = 1;

	/**
	 * 总页数
	 */
	private int totalPage;
	/**
	 * 每页行数
	 */
	private int pageSize = Constant.PAGESIZE;
	/**
	 * 总行数
	 */
	private int total;

	/**
	 * 本页数据
	 */
	private List<T> rows;

	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
		this.totalPage = (this.total + this.pageSize - 1) / this.pageSize;
		if (this.total == 0 || this.nowPage > this.totalPage) {
			this.nowPage = 1;
		}
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}