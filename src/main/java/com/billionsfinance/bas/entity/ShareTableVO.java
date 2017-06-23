package com.billionsfinance.bas.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 
 * @ClassName: ActualPayTableVO.java
 * @Description: 通用表JavaBean 囊括到期表、实还表、逾期表、逾期检查表、未到期表、预提表、减免表
 * @author Feima.zhou
 * 
 *         Copyright: Copyright (c) 2017年5月16日上午11:15:05 Company:佰仟金融
 */
public class ShareTableVO implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5167902283824800133L;

	private Date duedate;
	
	private Date waivedate;
	
	private String loansno;

	private String paydate;

	private String actualpaydate;

	private String paytype;

	private String payprincipalamt;

	private String payinteamt;

	private String a2;

	private String a7;

	private String a9;

	private String a10;

	private String a11;

	private String a12;

	private String a17;

	private String a18;

	private String a19;

	private String a22;

	private String payamt;

	private Date createtime;

	private String seqid;

	private String belong;

	private String guaranteeparty;

	private String compensatory;

	private String deliveryday;

	private String dcdate;

	private String dealdata;

	private String claimdate;

	private String canceltype;

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

	public String getLoansno() {
		return loansno;
	}

	public void setLoansno(String loansno) {
		this.loansno = loansno;
	}

	public String getPaydate() {
		return paydate;
	}

	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}

	public String getActualpaydate() {
		return actualpaydate;
	}

	public void setActualpaydate(String actualpaydate) {
		this.actualpaydate = actualpaydate;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
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

	public String getA2() {
		return a2;
	}

	public void setA2(String a2) {
		this.a2 = a2;
	}

	public String getA7() {
		return a7;
	}

	public void setA7(String a7) {
		this.a7 = a7;
	}

	public String getA9() {
		return a9;
	}

	public void setA9(String a9) {
		this.a9 = a9;
	}

	public String getA10() {
		return a10;
	}

	public void setA10(String a10) {
		this.a10 = a10;
	}

	public String getA11() {
		return a11;
	}

	public void setA11(String a11) {
		this.a11 = a11;
	}

	public String getA12() {
		return a12;
	}

	public void setA12(String a12) {
		this.a12 = a12;
	}

	public String getA17() {
		return a17;
	}

	public void setA17(String a17) {
		this.a17 = a17;
	}

	public String getA18() {
		return a18;
	}

	public void setA18(String a18) {
		this.a18 = a18;
	}

	public String getA19() {
		return a19;
	}

	public void setA19(String a19) {
		this.a19 = a19;
	}

	public String getA22() {
		return a22;
	}

	public void setA22(String a22) {
		this.a22 = a22;
	}

	public String getPayamt() {
		return payamt;
	}

	public void setPayamt(String payamt) {
		this.payamt = payamt;
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

	public String getSeqid() {
		return seqid;
	}

	public void setSeqid(String seqid) {
		this.seqid = seqid;
	}

	public String getBelong() {
		return belong;
	}

	public void setBelong(String belong) {
		this.belong = belong;
	}

	public String getGuaranteeparty() {
		return guaranteeparty;
	}

	public void setGuaranteeparty(String guaranteeparty) {
		this.guaranteeparty = guaranteeparty;
	}

	public String getCompensatory() {
		return compensatory;
	}

	public void setCompensatory(String compensatory) {
		this.compensatory = compensatory;
	}

	public String getDeliveryday() {
		return deliveryday;
	}

	public void setDeliveryday(String deliveryday) {
		this.deliveryday = deliveryday;
	}

	public String getDcdate() {
		return dcdate;
	}

	public void setDcdate(String dcdate) {
		this.dcdate = dcdate;
	}

	public String getDealdata() {
		return dealdata;
	}

	public void setDealdata(String dealdata) {
		this.dealdata = dealdata;
	}

	public String getClaimdate() {
		return claimdate;
	}

	public void setClaimdate(String claimdate) {
		this.claimdate = claimdate;
	}

	public String getCanceltype() {
		return canceltype;
	}

	public void setCanceltype(String canceltype) {
		this.canceltype = canceltype;
	}

	@Override
	public String toString() {
		return "ShareTableVO [duedate=" + duedate + ", waivedate=" + waivedate + ", loansno=" + loansno + ", paydate="
				+ paydate + ", actualpaydate=" + actualpaydate + ", paytype=" + paytype + ", payprincipalamt="
				+ payprincipalamt + ", payinteamt=" + payinteamt + ", a2=" + a2 + ", a7=" + a7 + ", a9=" + a9 + ", a10="
				+ a10 + ", a11=" + a11 + ", a12=" + a12 + ", a17=" + a17 + ", a18=" + a18 + ", a19=" + a19 + ", a22="
				+ a22 + ", payamt=" + payamt + ", createtime=" + createtime + ", seqid=" + seqid + ", belong=" + belong
				+ ", guaranteeparty=" + guaranteeparty + ", compensatory=" + compensatory + ", deliveryday="
				+ deliveryday + ", dcdate=" + dcdate + ", dealdata=" + dealdata + ", claimdate=" + claimdate
				+ ", canceltype=" + canceltype + "]";
	}

	public Date getWaivedate() {
		return waivedate;
	}

	public void setWaivedate(String waivedate) {
		try {
			Date date = new SimpleDateFormat("yyyy/MM/dd").parse(waivedate);
			this.waivedate = date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
