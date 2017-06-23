package com.billionsfinance.bas.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 
 * @ClassName: FinanceDaily.java
 * @Description: 放款表JavaBean
 * @author Feima.zhou
 * 
 *         Copyright: Copyright (c) 2017年5月16日上午11:15:36 Company:佰仟金融
 */
public class FinanceDaily implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date duedate;

	private String contractno;

	private String loanno;

	private String contractstatus;

	private String businesssum;

	private String registrationdate;

	private String province;

	private String citycode;

	private String city;

	private String businesstype;

	private String subproducttype;

	private String creditperson;

	private Date createtime;

	private String orgcode;

	private String customername;

	private String suretype;

	private String businessmodel;

	private String maturitydate;

	public Date getDuedate() {
		return duedate;
	}

	public void setDuedate(String duedate) {
		try {
			Date date = new SimpleDateFormat("yyyy/MM/dd").parse(duedate);
			this.duedate = date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getContractno() {
		return contractno;
	}

	public void setContractno(String contractno) {
		this.contractno = contractno;
	}

	public String getLoanno() {
		return loanno;
	}

	public void setLoanno(String loanno) {
		this.loanno = loanno;
	}

	public String getContractstatus() {
		return contractstatus;
	}

	public void setContractstatus(String contractstatus) {
		this.contractstatus = contractstatus;
	}

	public String getBusinesssum() {
		return businesssum;
	}

	public void setBusinesssum(String businesssum) {
		this.businesssum = businesssum;
	}

	public String getRegistrationdate() {
		return registrationdate;
	}

	public void setRegistrationdate(String registrationdate) {
		this.registrationdate = registrationdate;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBusinesstype() {
		return businesstype;
	}

	public void setBusinesstype(String businesstype) {
		this.businesstype = businesstype;
	}

	public String getSubproducttype() {
		return subproducttype;
	}

	public void setSubproducttype(String subproducttype) {
		this.subproducttype = subproducttype;
	}

	public String getCreditperson() {
		return creditperson;
	}

	public void setCreditperson(String creditperson) {
		this.creditperson = creditperson;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		try {
			Date date = new SimpleDateFormat("yyyy/MM/dd").parse(createtime);
			this.createtime = date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getOrgcode() {
		return orgcode;
	}

	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getSuretype() {
		return suretype;
	}

	public void setSuretype(String suretype) {
		this.suretype = suretype;
	}

	public String getBusinessmodel() {
		return businessmodel;
	}

	public void setBusinessmodel(String businessmodel) {
		this.businessmodel = businessmodel;
	}

	public String getMaturitydate() {
		return maturitydate;
	}

	public void setMaturitydate(String maturitydate) {
		this.maturitydate = maturitydate;
	}

	@Override
	public String toString() {
		return "LoanTableVO [duedate=" + duedate + ", contractno=" + contractno + ", loanno=" + loanno
				+ ", contractstatus=" + contractstatus + ", businesssum=" + businesssum + ", registrationdate="
				+ registrationdate + ", province=" + province + ", citycode=" + citycode + ", city=" + city
				+ ", businesstype=" + businesstype + ", subproducttype=" + subproducttype + ", creditperson="
				+ creditperson + ", createtime=" + createtime + ", orgcode=" + orgcode + ", customername="
				+ customername + ", suretype=" + suretype + ", businessmodel=" + businessmodel + ", maturitydate="
				+ maturitydate + "]";
	}

}
