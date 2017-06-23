package com.billionsfinance.bas.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 
 * @author fmZhoua
 * @describe 回款表VO
 */

public class XZXDPaymentsVO implements Serializable {

private static final long serialVersionUID = 1L;

	//================split条件
	private String[] seqIdArr;
	
	private String[] serialNoArr;
	
	//================ 查询条件
	
	private String startactualpaydate;
	
	private String endactualpaydate;
	
	private String startpaydate;
	
	private String endpaydate;
	
	private String starttransferpaypaymentday;
	
	private String endtransferpaypaymentday;
	
	private String startregistrationdate;
	
	private String endregistrationdate;
	
	private String subproducttype;
	
	private String paytype;
	
	private String accordmonth;
	
	//================ 查询条件
	
	private String serialno;
	
	private String suretype;
	
	private String businessmodel;
	
	private String productid;
	
	private String province;
	
	private String city;
	
	private String citycode;
	
	private String seqid;
	
	private String registrationmonth;
	
	private String registrationdate;
	
	private String paymonth;
	
	private String paydate;
	
	private String deliverymonth;
	
	private String deliverydate;
	
	private String transferpaypaymentmonth;
	
	private String transferpaypaymentday;
	
	private String canceltype;
	
	private String shmonth;
	
	private String lpmonth;
	
	private String debours;
	
	private String assetBelong;
	
	private String guaranteeparty;
	
	private String sponsor;
	
	private String sponsortype;
	
	private String payprincipalamt;
	
	private String payinteamt;
	
	private String loanprincipaltotal;
	
	private String sywhze;
	
	private String payamt;
	
	private String yhlxfx;
	
	private String yhbjfx;
	
	private String overduedays;
	
	private String actualpaydate;
	
	private String actualpayer;
	
	private String remark;

	public String[] getSeqIdArr() {
		return seqIdArr;
	}

	public void setSeqIdArr(String[] seqIdArr) {
		this.seqIdArr = seqIdArr;
	}

	public String[] getSerialNoArr() {
		return serialNoArr;
	}

	public void setSerialNoArr(String[] serialNoArr) {
		this.serialNoArr = serialNoArr;
	}

	public String getStartactualpaydate() {
		return startactualpaydate;
	}

	public void setStartactualpaydate(String startactualpaydate) {
		this.startactualpaydate = startactualpaydate;
	}

	public String getEndactualpaydate() {
		return endactualpaydate;
	}

	public void setEndactualpaydate(String endactualpaydate) {
		this.endactualpaydate = endactualpaydate;
	}

	public String getStarttransferpaypaymentday() {
		return starttransferpaypaymentday;
	}

	public void setStarttransferpaypaymentday(String starttransferpaypaymentday) {
		this.starttransferpaypaymentday = starttransferpaypaymentday;
	}

	public String getEndtransferpaypaymentday() {
		return endtransferpaypaymentday;
	}

	public void setEndtransferpaypaymentday(String endtransferpaypaymentday) {
		this.endtransferpaypaymentday = endtransferpaypaymentday;
	}

	public String getStartregistrationdate() {
		return startregistrationdate;
	}

	public void setStartregistrationdate(String startregistrationdate) {
		this.startregistrationdate = startregistrationdate;
	}

	public String getEndregistrationdate() {
		return endregistrationdate;
	}

	public void setEndregistrationdate(String endregistrationdate) {
		this.endregistrationdate = endregistrationdate;
	}

	public String getSubproducttype() {
		return subproducttype;
	}

	public void setSubproducttype(String subproducttype) {
		this.subproducttype = subproducttype;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public String getAccordmonth() {
		return accordmonth;
	}

	public void setAccordmonth(String accordmonth) {
		this.accordmonth = accordmonth;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
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

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getSeqid() {
		return seqid;
	}

	public void setSeqid(String seqid) {
		this.seqid = seqid;
	}

	public String getRegistrationmonth() {
		return registrationmonth;
	}

	public void setRegistrationmonth(String registrationmonth) {
		this.registrationmonth = registrationmonth;
	}

	public String getRegistrationdate() {
		return registrationdate;
	}

	public void setRegistrationdate(String registrationdate) {
		this.registrationdate = registrationdate;
	}

	public String getPaymonth() {
		return paymonth;
	}

	public void setPaymonth(String paymonth) {
		this.paymonth = paymonth;
	}

	public String getPaydate() {
		return paydate;
	}

	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}

	public String getDeliverymonth() {
		return deliverymonth;
	}

	public void setDeliverymonth(String deliverymonth) {
		this.deliverymonth = deliverymonth;
	}

	public String getDeliverydate() {
		return deliverydate;
	}

	public void setDeliverydate(String deliverydate) {
		this.deliverydate = deliverydate;
	}

	public String getTransferpaypaymentmonth() {
		return transferpaypaymentmonth;
	}

	public void setTransferpaypaymentmonth(String transferpaypaymentmonth) {
		this.transferpaypaymentmonth = transferpaypaymentmonth;
	}

	public String getTransferpaypaymentday() {
		return transferpaypaymentday;
	}

	public void setTransferpaypaymentday(String transferpaypaymentday) {
		this.transferpaypaymentday = transferpaypaymentday;
	}

	public String getCanceltype() {
		return canceltype;
	}

	public void setCanceltype(String canceltype) {
		this.canceltype = canceltype;
	}

	public String getShmonth() {
		return shmonth;
	}

	public void setShmonth(String shmonth) {
		this.shmonth = shmonth;
	}

	public String getLpmonth() {
		return lpmonth;
	}

	public void setLpmonth(String lpmonth) {
		this.lpmonth = lpmonth;
	}

	public String getDebours() {
		return debours;
	}

	public void setDebours(String debours) {
		this.debours = debours;
	}

	public String getAssetBelong() {
		return assetBelong;
	}

	public void setAssetBelong(String assetBelong) {
		this.assetBelong = assetBelong;
	}

	public String getGuaranteeparty() {
		return guaranteeparty;
	}

	public void setGuaranteeparty(String guaranteeparty) {
		this.guaranteeparty = guaranteeparty;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public String getSponsortype() {
		return sponsortype;
	}

	public void setSponsortype(String sponsortype) {
		this.sponsortype = sponsortype;
	}

	public String getPayprincipalamt() {
		return payprincipalamt;
	}

	public void setPayprincipalamt(String payprincipalamt) {
		this.payprincipalamt = payprincipalamt;
	}

	public String getPayinteamt() {
		return payinteamt;
	}

	public void setPayinteamt(String payinteamt) {
		this.payinteamt = payinteamt;
	}

	public String getLoanprincipaltotal() {
		return loanprincipaltotal;
	}

	public void setLoanprincipaltotal(String loanprincipaltotal) {
		this.loanprincipaltotal = loanprincipaltotal;
	}

	public String getSywhze() {
		return sywhze;
	}

	public void setSywhze(String sywhze) {
		this.sywhze = sywhze;
	}

	public String getPayamt() {
		return payamt;
	}

	public void setPayamt(String payamt) {
		this.payamt = payamt;
	}

	public String getYhlxfx() {
		return yhlxfx;
	}

	public void setYhlxfx(String yhlxfx) {
		this.yhlxfx = yhlxfx;
	}

	public String getYhbjfx() {
		return yhbjfx;
	}

	public void setYhbjfx(String yhbjfx) {
		this.yhbjfx = yhbjfx;
	}

	public String getOverduedays() {
		return overduedays;
	}

	public void setOverduedays(String overduedays) {
		this.overduedays = overduedays;
	}

	public String getActualpaydate() {
		return actualpaydate;
	}

	public void setActualpaydate(String actualpaydate) {
		this.actualpaydate = actualpaydate;
	}

	public String getActualpayer() {
		return actualpayer;
	}

	public void setActualpayer(String actualpayer) {
		this.actualpayer = actualpayer;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStartpaydate() {
		return startpaydate;
	}

	public void setStartpaydate(String startpaydate) {
		this.startpaydate = startpaydate;
	}

	public String getEndpaydate() {
		return endpaydate;
	}

	public void setEndpaydate(String endpaydate) {
		this.endpaydate = endpaydate;
	}

	@Override
	public String toString() {
		return "XZXDPaymentsVO [seqIdArr=" + Arrays.toString(seqIdArr) + ", serialNoArr=" + Arrays.toString(serialNoArr)
				+ ", startactualpaydate=" + startactualpaydate + ", endactualpaydate=" + endactualpaydate
				+ ", startpaydate=" + startpaydate + ", endpaydate=" + endpaydate + ", starttransferpaypaymentday="
				+ starttransferpaypaymentday + ", endtransferpaypaymentday=" + endtransferpaypaymentday
				+ ", startregistrationdate=" + startregistrationdate + ", endregistrationdate=" + endregistrationdate
				+ ", subproducttype=" + subproducttype + ", paytype=" + paytype + ", accordmonth=" + accordmonth
				+ ", serialno=" + serialno + ", suretype=" + suretype + ", businessmodel=" + businessmodel
				+ ", productid=" + productid + ", province=" + province + ", city=" + city + ", citycode=" + citycode
				+ ", seqid=" + seqid + ", registrationmonth=" + registrationmonth + ", registrationdate="
				+ registrationdate + ", paymonth=" + paymonth + ", paydate=" + paydate + ", deliverymonth="
				+ deliverymonth + ", deliverydate=" + deliverydate + ", transferpaypaymentmonth="
				+ transferpaypaymentmonth + ", transferpaypaymentday=" + transferpaypaymentday + ", canceltype="
				+ canceltype + ", shmonth=" + shmonth + ", lpmonth=" + lpmonth + ", debours=" + debours
				+ ", assetBelong=" + assetBelong + ", guaranteeparty=" + guaranteeparty + ", sponsor=" + sponsor
				+ ", sponsortype=" + sponsortype + ", payprincipalamt=" + payprincipalamt + ", payinteamt=" + payinteamt
				+ ", loanprincipaltotal=" + loanprincipaltotal + ", sywhze=" + sywhze + ", payamt=" + payamt
				+ ", yhlxfx=" + yhlxfx + ", yhbjfx=" + yhbjfx + ", overduedays=" + overduedays + ", actualpaydate="
				+ actualpaydate + ", actualpayer=" + actualpayer + ", remark=" + remark + ", getSeqIdArr()="
				+ Arrays.toString(getSeqIdArr()) + ", getSerialNoArr()=" + Arrays.toString(getSerialNoArr())
				+ ", getStartactualpaydate()=" + getStartactualpaydate() + ", getEndactualpaydate()="
				+ getEndactualpaydate() + ", getStarttransferpaypaymentday()=" + getStarttransferpaypaymentday()
				+ ", getEndtransferpaypaymentday()=" + getEndtransferpaypaymentday() + ", getStartregistrationdate()="
				+ getStartregistrationdate() + ", getEndregistrationdate()=" + getEndregistrationdate()
				+ ", getSubproducttype()=" + getSubproducttype() + ", getPaytype()=" + getPaytype()
				+ ", getAccordmonth()=" + getAccordmonth() + ", getSerialno()=" + getSerialno() + ", getSuretype()="
				+ getSuretype() + ", getBusinessmodel()=" + getBusinessmodel() + ", getProductid()=" + getProductid()
				+ ", getProvince()=" + getProvince() + ", getCity()=" + getCity() + ", getCitycode()=" + getCitycode()
				+ ", getSeqid()=" + getSeqid() + ", getRegistrationmonth()=" + getRegistrationmonth()
				+ ", getRegistrationdate()=" + getRegistrationdate() + ", getPaymonth()=" + getPaymonth()
				+ ", getPaydate()=" + getPaydate() + ", getDeliverymonth()=" + getDeliverymonth()
				+ ", getDeliverydate()=" + getDeliverydate() + ", getTransferpaypaymentmonth()="
				+ getTransferpaypaymentmonth() + ", getTransferpaypaymentday()=" + getTransferpaypaymentday()
				+ ", getCanceltype()=" + getCanceltype() + ", getShmonth()=" + getShmonth() + ", getLpmonth()="
				+ getLpmonth() + ", getDebours()=" + getDebours() + ", getAssetBelong()=" + getAssetBelong()
				+ ", getGuaranteeparty()=" + getGuaranteeparty() + ", getSponsor()=" + getSponsor()
				+ ", getSponsortype()=" + getSponsortype() + ", getPayprincipalamt()=" + getPayprincipalamt()
				+ ", getPayinteamt()=" + getPayinteamt() + ", getLoanprincipaltotal()=" + getLoanprincipaltotal()
				+ ", getSywhze()=" + getSywhze() + ", getPayamt()=" + getPayamt() + ", getYhlxfx()=" + getYhlxfx()
				+ ", getYhbjfx()=" + getYhbjfx() + ", getOverduedays()=" + getOverduedays() + ", getActualpaydate()="
				+ getActualpaydate() + ", getActualpayer()=" + getActualpayer() + ", getRemark()=" + getRemark()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
}
