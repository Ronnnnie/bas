package com.billionsfinance.bas.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class SystemUserVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3844215782131679058L;

	/**系统用户ID*/
	private String sysUserId;
	/**系统编码*/
	private String sysCode;
	/**用户编码*/
	private String userCode;
	private String updatedUser;
	/**可用状态*/
	private String validInd;
	private Timestamp createdDate;
	private String createdUser;
	private Timestamp updatedDate;

	
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
	public String getSysUserId() {
		return sysUserId;
	}
	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}
	
}
