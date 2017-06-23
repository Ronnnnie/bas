package com.billionsfinance.bas.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @ClassName: BaseContract.java
 * @Description: 合同表JavaBean
 * @author Feima.zhou
 * 
 *         Copyright: Copyright (c) 2017年5月16日上午10:43:18 Company:佰仟金融
 */
public class BaseContract implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date duedate;
	
	private String contractno;
	
	private String loanno;
	
	private String isp2p;
	
	private Integer overduedays;
	
	private String contractstatus;
	
	private String businesssum;
	
	private String registrationdate;
	
	private String province;
	
	private String citycode;
	
	private String city;
	
	private String businesstype;
	
	private String subproducttype;
	
	private String creditperson;
	
	private String describe;
	
	private String classfy;
	
	private String cdate;
	
	private Date createtime;
	
	private String orgcode;
	
	private String customername;
	
	private String sno;
	
	private String rno;
	
	private String sa_id;
	
	private String suretype;
	
	private String productcategory;
	
	private String businessmodel;
	
	private String tbdate;
	
	private String bccanceldate;
	
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

	public String getIsp2p() {
		return isp2p;
	}

	public void setIsp2p(String isp2p) {
		this.isp2p = isp2p;
	}

	public Integer getOverduedays() {
		return overduedays;
	}

	public void setOverduedays(Integer overduedays) {
		this.overduedays = overduedays;
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

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getClassfy() {
		return classfy;
	}

	public void setClassfy(String classfy) {
		this.classfy = classfy;
	}

	public String getCdate() {
		return cdate;
	}

	public void setCdate(String cdate) {
		this.cdate = cdate;
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

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	public String getRno() {
		return rno;
	}

	public void setRno(String rno) {
		this.rno = rno;
	}

	public String getSa_id() {
		return sa_id;
	}

	public void setSa_id(String sa_id) {
		this.sa_id = sa_id;
	}

	public String getSuretype() {
		return suretype;
	}

	public void setSuretype(String suretype) {
		this.suretype = suretype;
	}

	public String getProductcategory() {
		return productcategory;
	}

	public void setProductcategory(String productcategory) {
		this.productcategory = productcategory;
	}

	public String getBusinessmodel() {
		return businessmodel;
	}

	public void setBusinessmodel(String businessmodel) {
		this.businessmodel = businessmodel;
	}

	public String getTbdate() {
		return tbdate;
	}

	public void setTbdate(String tbdate) {
		this.tbdate = tbdate;
	}

	public String getBccanceldate() {
		return bccanceldate;
	}

	public void setBccanceldate(String bccanceldate) {
		this.bccanceldate = bccanceldate;
	}

	public String getMaturitydate() {
		return maturitydate;
	}

	public void setMaturitydate(String maturitydate) {
		this.maturitydate = maturitydate;
	}

	@Override
	public String toString() {
		return "ContractTableVO [duedate=" + duedate + ", contractno=" + contractno + ", loanno=" + loanno + ", isp2p="
				+ isp2p + ", overduedays=" + overduedays + ", contractstatus=" + contractstatus + ", businesssum="
				+ businesssum + ", registrationdate=" + registrationdate + ", province=" + province + ", citycode="
				+ citycode + ", city=" + city + ", businesstype=" + businesstype + ", subproducttype=" + subproducttype
				+ ", creditperson=" + creditperson + ", describe=" + describe + ", classfy=" + classfy + ", cdate="
				+ cdate + ", createtime=" + createtime + ", orgcode=" + orgcode + ", customername=" + customername
				+ ", sno=" + sno + ", rno=" + rno + ", sa_id=" + sa_id + ", suretype=" + suretype + ", productcategory="
				+ productcategory + ", businessmodel=" + businessmodel + ", tbdate=" + tbdate + ", bccanceldate="
				+ bccanceldate + ", maturitydate=" + maturitydate + "]";
	}
	
}
