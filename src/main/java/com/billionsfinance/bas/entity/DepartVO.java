package com.billionsfinance.bas.entity;

import java.io.Serializable;

public class DepartVO implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7473955723452832855L;

	
	private String id;
	private String departName;
	private String parentDepartId;
	private String description;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getParentDepartId() {
		return parentDepartId;
	}
	public void setParentDepartId(String parentDepartId) {
		this.parentDepartId = parentDepartId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
