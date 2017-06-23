package com.billionsfinance.bas.entity;

import java.sql.Timestamp;

public class SysAdminVO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3419493350593101699L;
	
	private String updatedUser;
	private String validInd;
	private Timestamp createdDate;
	private String userCode;
	private String createdUser;
	private String adminLevel;
	private String sysCode;
	private Timestamp updatedDate;
	private String pkUuid;
	private String userCname;
	
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
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	public String getAdminLevel() {
		return adminLevel;
	}
	public void setAdminLevel(String adminLevel) {
		this.adminLevel = adminLevel;
	}
	public String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getPkUuid() {
		return pkUuid;
	}
	public void setPkUuid(String pkUuid) {
		this.pkUuid = pkUuid;
	}
	public String getUserCname() {
		return userCname;
	}
	public void setUserCname(String userCname) {
		this.userCname = userCname;
	}
	
}