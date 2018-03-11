package com.cmazxiaoma.framework.common.page;


import com.cmazxiaoma.framework.base.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询结果
 * @param <T>
 */
public class PagingResult<T extends BaseEntity> implements Serializable {

	private static final long serialVersionUID = -2454369717915031521L;
	
	/**
	 * 当前页码
	 */
	private int page;
	
	/**
	 * 每页记录数
	 */
	private int pageSize;
	
	/**
	 * 总记录数
	 */
	private long total;
	
	/**
	 * 每页数据记录
	 */
	private List<T> rows;
	
	public PagingResult(long total, List<T> rows, int page, int pageSize) {
		this.total = total;
		this.rows = rows;
		this.page = page;
		this.pageSize = pageSize;
	}
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the total
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(long total) {
		this.total = total;
	}

	/**
	 * @return the rows
	 */
	public List<T> getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
