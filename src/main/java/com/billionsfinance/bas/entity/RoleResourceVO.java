package com.billionsfinance.bas.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class RoleResourceVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5352404918518161689L;
	
	private String updatedUser;
	private String validInd;
	private Timestamp createdDate;
	private String createdUser;
	private String roleCode;
	private Timestamp updatedDate;
	private String resourceId;
	private String pkUuid;
	public String getUpdatedUser() {
		return updatedUser;
	}
	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}
	public String getValidInd() {
		return validInd;
	}
	public void setValidInd(String validInd) {
		this.validInd = validInd;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getPkUuid() {
		return pkUuid;
	}
	public void setPkUuid(String pkUuid) {
		this.pkUuid = pkUuid;
	}
}