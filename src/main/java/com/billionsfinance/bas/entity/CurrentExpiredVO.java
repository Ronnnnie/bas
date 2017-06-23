package com.billionsfinance.bas.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class CurrentExpiredVO implements Serializable {
	/**
	 * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	 */

	private static final long serialVersionUID = 1L;

	private String loansno;

	private String serialno;

	private String productid;

	private String subproducttype;

	private String paybelong;

	private String assetbelong;

	private String paydate;

	private BigDecimal seqid;

	private String paytype;

	private BigDecimal payprincipalamt;

	private BigDecimal payinteamt;

	private BigDecimal payamt;

	private String approvestatus;

	private String approvetime;

	private String approveby;

	private String paystatus;

	private String paytime;

	private String payby;

	private String createtime;

	private String payremark;

	private String keepaccountsdate;

	private String city;

	private String clientname;

	private String guaranteeparty;

	private String shouldalsodate;

	private BigDecimal stampduty;

	private String approveremark;

	private String keepaccountsremark;

	private String keepaccountsby;

	private String keepaccountsstatus;

	private String province;

	private String citycode;

	private String isjc;

	private String registrationdate;

	private String creditperson;

	private String accorddate;

	private String sno;

	private String rno;

	private String saId;

	private String suretype;

	private String businessmodel;

	private String productcategory;

	private String cdate;

	private String deliverydate;

	private String dcdate;

	private String shdate;

	private String tbdate;

	private String lpdate;

	private String debours;

	private BigDecimal a2;

	private BigDecimal a7;

	private BigDecimal a9;

	private BigDecimal a10;

	private BigDecimal a12;

	private BigDecimal a17;

	private BigDecimal a18;

	private BigDecimal a19;

	private BigDecimal a22;

	private String inaccountby;

	private String inaccountdate;

	private String inaccountremark;

	private String inaccountstatus;

	private String startPayDate;

	private String endPayDate;

	private String startRegistrationDate;

	private String endRegistrationDate;

	private BigDecimal amount;

	private BigDecimal waiveintamt;

	private String contractlife;

	private String maturitydate;

	private String canceltype;

	private String[] seqidArray;

	private String[] serialnoArray;

	private String[] assetbelongArray;

	private String[] payprincipalamtArray;

	private String[] payinteamtArray;

	private String payprincipalamtStr;

	private String payinteamtStr;

	private String seqidStr;

	private String startInaccountdate;

	private String endInaccountdate;

	private String datasource;

	private String startActualpaydate;

	private String endActualpaydate;

	public String getLoansno() {
		return loansno;
	}

	public void setLoansno(String loansno) {
		this.loansno = loansno == null ? null : loansno.trim();
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno == null ? null : serialno.trim();
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid == null ? null : productid.trim();
	}

	public String getSubproducttype() {
		return subproducttype;
	}

	public void setSubproducttype(String subproducttype) {
		this.subproducttype = subproducttype == null ? null : subproducttype.trim();
	}

	public String getPaybelong() {
		return paybelong;
	}

	public void setPaybelong(String paybelong) {
		this.paybelong = paybelong == null ? null : paybelong.trim();
	}

	public String getAssetbelong() {
		return assetbelong;
	}

	public void setAssetbelong(String assetbelong) {
		this.assetbelong = assetbelong == null ? null : assetbelong.trim();
	}

	public String getPaydate() {
		return paydate;
	}

	public void setPaydate(String paydate) {
		this.paydate = paydate == null ? null : paydate.trim();
	}

	public BigDecimal getSeqid() {
		return seqid;
	}

	public void setSeqid(BigDecimal seqid) {
		this.seqid = seqid;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype == null ? null : paytype.trim();
	}

	public BigDecimal getPayprincipalamt() {
		return payprincipalamt;
	}

	public void setPayprincipalamt(BigDecimal payprincipalamt) {
		this.payprincipalamt = payprincipalamt;
	}

	public BigDecimal getPayinteamt() {
		return payinteamt;
	}

	public void setPayinteamt(BigDecimal payinteamt) {
		this.payinteamt = payinteamt;
	}

	public BigDecimal getPayamt() {
		return payamt;
	}

	public void setPayamt(BigDecimal payamt) {
		this.payamt = payamt;
	}

	public String getApprovestatus() {
		return approvestatus;
	}

	public void setApprovestatus(String approvestatus) {
		this.approvestatus = approvestatus == null ? null : approvestatus.trim();
	}

	public String getApprovetime() {
		return approvetime;
	}

	public void setApprovetime(String approvetime) {
		this.approvetime = approvetime == null ? null : approvetime.trim();
	}

	public String getApproveby() {
		return approveby;
	}

	public void setApproveby(String approveby) {
		this.approveby = approveby == null ? null : approveby.trim();
	}

	public String getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(String paystatus) {
		this.paystatus = paystatus == null ? null : paystatus.trim();
	}

	public String getPaytime() {
		return paytime;
	}

	public void setPaytime(String paytime) {
		this.paytime = paytime == null ? null : paytime.trim();
	}

	public String getPayby() {
		return payby;
	}

	public void setPayby(String payby) {
		this.payby = payby == null ? null : payby.trim();
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime == null ? null : createtime.trim();
	}

	public String getPayremark() {
		return payremark;
	}

	public void setPayremark(String payremark) {
		this.payremark = payremark == null ? null : payremark.trim();
	}

	public String getKeepaccountsdate() {
		return keepaccountsdate;
	}

	public void setKeepaccountsdate(String keepaccountsdate) {
		this.keepaccountsdate = keepaccountsdate == null ? null : keepaccountsdate.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public String getClientname() {
		return clientname;
	}

	public void setClientname(String clientname) {
		this.clientname = clientname == null ? null : clientname.trim();
	}

	public String getGuaranteeparty() {
		return guaranteeparty;
	}

	public void setGuaranteeparty(String guaranteeparty) {
		this.guaranteeparty = guaranteeparty == null ? null : guaranteeparty.trim();
	}

	public String getShouldalsodate() {
		return shouldalsodate;
	}

	public void setShouldalsodate(String shouldalsodate) {
		this.shouldalsodate = shouldalsodate == null ? null : shouldalsodate.trim();
	}

	public BigDecimal getStampduty() {
		return stampduty;
	}

	public void setStampduty(BigDecimal stampduty) {
		this.stampduty = stampduty;
	}

	public String getApproveremark() {
		return approveremark;
	}

	public void setApproveremark(String approveremark) {
		this.approveremark = approveremark == null ? null : approveremark.trim();
	}

	public String getKeepaccountsremark() {
		return keepaccountsremark;
	}

	public void setKeepaccountsremark(String keepaccountsremark) {
		this.keepaccountsremark = keepaccountsremark == null ? null : keepaccountsremark.trim();
	}

	public String getKeepaccountsby() {
		return keepaccountsby;
	}

	public void setKeepaccountsby(String keepaccountsby) {
		this.keepaccountsby = keepaccountsby == null ? null : keepaccountsby.trim();
	}

	public String getKeepaccountsstatus() {
		return keepaccountsstatus;
	}

	public void setKeepaccountsstatus(String keepaccountsstatus) {
		this.keepaccountsstatus = keepaccountsstatus == null ? null : keepaccountsstatus.trim();
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode == null ? null : citycode.trim();
	}

	public String getIsjc() {
		return isjc;
	}

	public void setIsjc(String isjc) {
		this.isjc = isjc == null ? null : isjc.trim();
	}

	public String getRegistrationdate() {
		return registrationdate;
	}

	public void setRegistrationdate(String registrationdate) {
		this.registrationdate = registrationdate == null ? null : registrationdate.trim();
	}

	public String getCreditperson() {
		return creditperson;
	}

	public void setCreditperson(String creditperson) {
		this.creditperson = creditperson == null ? null : creditperson.trim();
	}

	public String getAccorddate() {
		return accorddate;
	}

	public void setAccorddate(String accorddate) {
		this.accorddate = accorddate == null ? null : accorddate.trim();
	}

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno == null ? null : sno.trim();
	}

	public String getRno() {
		return rno;
	}

	public void setRno(String rno) {
		this.rno = rno == null ? null : rno.trim();
	}

	public String getSaId() {
		return saId;
	}

	public void setSaId(String saId) {
		this.saId = saId == null ? null : saId.trim();
	}

	public String getSuretype() {
		return suretype;
	}

	public void setSuretype(String suretype) {
		this.suretype = suretype == null ? null : suretype.trim();
	}

	public String getBusinessmodel() {
		return businessmodel;
	}

	public void setBusinessmodel(String businessmodel) {
		this.businessmodel = businessmodel == null ? null : businessmodel.trim();
	}

	public String getProductcategory() {
		return productcategory;
	}

	public void setProductcategory(String productcategory) {
		this.productcategory = productcategory == null ? null : productcategory.trim();
	}

	public String getCdate() {
		return cdate;
	}

	public void setCdate(String cdate) {
		this.cdate = cdate == null ? null : cdate.trim();
	}

	public String getDeliverydate() {
		return deliverydate;
	}

	public void setDeliverydate(String deliverydate) {
		this.deliverydate = deliverydate == null ? null : deliverydate.trim();
	}

	public String getDcdate() {
		return dcdate;
	}

	public void setDcdate(String dcdate) {
		this.dcdate = dcdate == null ? null : dcdate.trim();
	}

	public String getShdate() {
		return shdate;
	}

	public void setShdate(String shdate) {
		this.shdate = shdate == null ? null : shdate.trim();
	}

	public String getTbdate() {
		return tbdate;
	}

	public void setTbdate(String tbdate) {
		this.tbdate = tbdate == null ? null : tbdate.trim();
	}

	public String getLpdate() {
		return lpdate;
	}

	public void setLpdate(String lpdate) {
		this.lpdate = lpdate == null ? null : lpdate.trim();
	}

	public String getDebours() {
		return debours;
	}

	public void setDebours(String debours) {
		this.debours = debours == null ? null : debours.trim();
	}

	public BigDecimal getA2() {
		return a2;
	}

	public void setA2(BigDecimal a2) {
		this.a2 = a2;
	}

	public BigDecimal getA7() {
		return a7;
	}

	public void setA7(BigDecimal a7) {
		this.a7 = a7;
	}

	public BigDecimal getA9() {
		return a9;
	}

	public void setA9(BigDecimal a9) {
		this.a9 = a9;
	}

	public BigDecimal getA10() {
		return a10;
	}

	public void setA10(BigDecimal a10) {
		this.a10 = a10;
	}

	public BigDecimal getA12() {
		return a12;
	}

	public void setA12(BigDecimal a12) {
		this.a12 = a12;
	}

	public BigDecimal getA17() {
		return a17;
	}

	public void setA17(BigDecimal a17) {
		this.a17 = a17;
	}

	public BigDecimal getA18() {
		return a18;
	}

	public void setA18(BigDecimal a18) {
		this.a18 = a18;
	}

	public BigDecimal getA19() {
		return a19;
	}

	public void setA19(BigDecimal a19) {
		this.a19 = a19;
	}

	public BigDecimal getA22() {
		return a22;
	}

	public void setA22(BigDecimal a22) {
		this.a22 = a22;
	}

	public String getInaccountby() {
		return inaccountby;
	}

	public void setInaccountby(String inaccountby) {
		this.inaccountby = inaccountby == null ? null : inaccountby.trim();
	}

	public String getInaccountdate() {
		return inaccountdate;
	}

	public void setInaccountdate(String inaccountdate) {
		this.inaccountdate = inaccountdate == null ? null : inaccountdate.trim();
	}

	public String getInaccountremark() {
		return inaccountremark;
	}

	public void setInaccountremark(String inaccountremark) {
		this.inaccountremark = inaccountremark == null ? null : inaccountremark.trim();
	}

	public String getInaccountstatus() {
		return inaccountstatus;
	}

	public void setInaccountstatus(String inaccountstatus) {
		this.inaccountstatus = inaccountstatus == null ? null : inaccountstatus.trim();
	}

	public String getStartPayDate() {
		return startPayDate;
	}

	public void setStartPayDate(String startPayDate) {
		this.startPayDate = startPayDate;
	}

	public String getEndPayDate() {
		return endPayDate;
	}

	public void setEndPayDate(String endPayDate) {
		this.endPayDate = endPayDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getStartRegistrationDate() {
		return startRegistrationDate;
	}

	public void setStartRegistrationDate(String startRegistrationDate) {
		this.startRegistrationDate = startRegistrationDate;
	}

	public String getEndRegistrationDate() {
		return endRegistrationDate;
	}

	public void setEndRegistrationDate(String endRegistrationDate) {
		this.endRegistrationDate = endRegistrationDate;
	}

	public BigDecimal getWaiveintamt() {
		return waiveintamt;
	}

	public void setWaiveintamt(BigDecimal waiveintamt) {
		this.waiveintamt = waiveintamt;
	}

	public String getContractlife() {
		return contractlife;
	}

	public void setContractlife(String contractlife) {
		this.contractlife = contractlife;
	}

	public String getMaturitydate() {
		return maturitydate;
	}

	public void setMaturitydate(String maturitydate) {
		this.maturitydate = maturitydate;
	}

	public String getCanceltype() {
		return canceltype;
	}

	public void setCanceltype(String canceltype) {
		this.canceltype = canceltype;
	}

	public String[] getSeqidArray() {
		return seqidArray;
	}

	public void setSeqidArray(String[] seqidArray) {
		this.seqidArray = seqidArray;
	}

	public String[] getSerialnoArray() {
		return serialnoArray;
	}

	public void setSerialnoArray(String[] serialnoArray) {
		this.serialnoArray = serialnoArray;
	}

	public String[] getAssetbelongArray() {
		return assetbelongArray;
	}

	public void setAssetbelongArray(String[] assetbelongArray) {
		this.assetbelongArray = assetbelongArray;
	}

	public String[] getPayprincipalamtArray() {
		return payprincipalamtArray;
	}

	public void setPayprincipalamtArray(String[] payprincipalamtArray) {
		this.payprincipalamtArray = payprincipalamtArray;
	}

	public String[] getPayinteamtArray() {
		return payinteamtArray;
	}

	public void setPayinteamtArray(String[] payinteamtArray) {
		this.payinteamtArray = payinteamtArray;
	}

	public String getPayprincipalamtStr() {
		return payprincipalamtStr;
	}

	public void setPayprincipalamtStr(String payprincipalamtStr) {
		this.payprincipalamtStr = payprincipalamtStr;
	}

	public String getPayinteamtStr() {
		return payinteamtStr;
	}

	public void setPayinteamtStr(String payinteamtStr) {
		this.payinteamtStr = payinteamtStr;
	}

	public String getSeqidStr() {
		return seqidStr;
	}

	public void setSeqidStr(String seqidStr) {
		this.seqidStr = seqidStr;
	}

	public String getStartInaccountdate() {
		return startInaccountdate;
	}

	public void setStartInaccountdate(String startInaccountdate) {
		this.startInaccountdate = startInaccountdate;
	}

	public String getEndInaccountdate() {
		return endInaccountdate;
	}

	public void setEndInaccountdate(String endInaccountdate) {
		this.endInaccountdate = endInaccountdate;
	}

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	public String getStartActualpaydate() {
		return startActualpaydate;
	}

	public void setStartActualpaydate(String startActualpaydate) {
		this.startActualpaydate = startActualpaydate;
	}

	public String getEndActualpaydate() {
		return endActualpaydate;
	}

	public void setEndActualpaydate(String endActualpaydate) {
		this.endActualpaydate = endActualpaydate;
	}

}