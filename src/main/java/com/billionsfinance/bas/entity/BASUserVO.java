package com.billionsfinance.bas.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class BASUserVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8381316511531147072L;
	
	private String userCode;
	private String userCname;
	private String updatedUser;
	private String pwd;
	private Timestamp pwdUpdateTime;
	private Timestamp createdDate;
	private String createdUser;
	private Timestamp updatedDate;
	private String status;
	private String userEmail;
	private String userPhone; 
	private String orgid;
	private String deptNo;
	
	public String getUpdatedUser() {
		return updatedUser;
	}
	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Timestamp getPwdUpdateTime() {
		return pwdUpdateTime;
	}
	public void setPwdUpdateTime(Timestamp pwdUpdateTime) {
		this.pwdUpdateTime = pwdUpdateTime;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getUserCname() {
		return userCname;
	}
	public void setUserCname(String userCname) {
		this.userCname = userCname;
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
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}
	
}
