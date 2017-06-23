package com.billionsfinance.bas.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class SysRoleVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6680262787090009478L;
	
	private String updatedUser;
	private String roleDesc;
	private String validInd;
	private Timestamp createdDate;
	private String createdUser;
	private String sysCode;
	private String roleCname;
	private String roleCode;
	private Timestamp updatedDate;
	
	public String getUpdatedUser() {
		return updatedUser;
	}
	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
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
	public String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	public String getRoleCname() {
		return roleCname;
	}
	public void setRoleCname(String roleCname) {
		this.roleCname = roleCname;
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
}