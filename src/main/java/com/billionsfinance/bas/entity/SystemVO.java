package com.billionsfinance.bas.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class SystemVO implements Serializable
{


	/**
	 * 
	 */
	private static final long serialVersionUID = 7102835505065968392L;
	
	private String updatedUser;
	private String validInd;
	private Timestamp createdDate;
	private String sysUrl;
	private String sysDesc;
	private String createdUser;
	private String sysCode;
	private Timestamp updatedDate;
	private String sysEname;
	private String sysTypeCode;
	private String sysTypeCname;
	private String sysCname;
	private String isMappinguser;//是否需要映射用户，''1''需要映射，''2''不需要映射
	private String checkUrl;//校验用户请求url
	
	private String sysLogo;
	
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
	public String getSysUrl() {
		return sysUrl;
	}
	public void setSysUrl(String sysUrl) {
		this.sysUrl = sysUrl;
	}
	public String getSysDesc() {
		return sysDesc;
	}
	public void setSysDesc(String sysDesc) {
		this.sysDesc = sysDesc;
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
	public String getSysEname() {
		return sysEname;
	}
	public void setSysEname(String sysEname) {
		this.sysEname = sysEname;
	}
	public String getSysTypeCode() {
		return sysTypeCode;
	}
	public void setSysTypeCode(String sysTypeCode) {
		this.sysTypeCode = sysTypeCode;
	}
	public String getSysCname() {
		return sysCname;
	}
	public void setSysCname(String sysCname) {
		this.sysCname = sysCname;
	}
	public String getSysTypeCname() {
		return sysTypeCname;
	}
	public void setSysTypeCname(String sysTypeCname) {
		this.sysTypeCname = sysTypeCname;
	}
	public String getSysLogo() {
		return sysLogo;
	}
	public void setSysLogo(String sysLogo) {
		this.sysLogo = sysLogo;
	}
	public String getIsMappinguser() {
		return isMappinguser;
	}
	public void setIsMappinguser(String isMappinguser) {
		this.isMappinguser = isMappinguser;
	}
	public String getCheckUrl() {
		return checkUrl;
	}
	public void setCheckUrl(String checkUrl) {
		this.checkUrl = checkUrl;
	}
}