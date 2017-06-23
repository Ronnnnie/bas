package com.billionsfinance.bas.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long total = 0L;

	private Integer pageSize = 10;

	private Integer pageNo = 1;

	private Integer endpoint;
	
	private Integer startpoint;
	
	private Map<String, Object> whereMap = new HashMap<String, Object>(0);

	private List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>(
			0);

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<Map<String, Object>> getRows() {
		return rows;
	}

	public void setRows(List<Map<String, Object>> rows) {
		this.rows = rows;
	}

	public Map<String, Object> getWhereMap() {
		return whereMap;
	}

	public void setWhereMap(Map<String, Object> whereMap) {
		this.whereMap = whereMap;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(Integer endpoint) {
		this.endpoint = endpoint;
	}

	public Integer getStartpoint() {
		return startpoint;
	}

	public void setStartpoint(Integer startpoint) {
		this.startpoint = startpoint;
	}

}
