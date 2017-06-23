package com.billionsfinance.bas.entity;

import java.io.Serializable;

/**
 * 
 * @author fmZhoua
 * @describe 回款表VO
 */

public class HHReceivedPaymentsVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String serialNo;
	
	private String allotDate;
	
	private String payDate;
	
	private String payType;
	
	private Double payPrincipalamt;
	
	private Double payInteamt;
	
	private Double payAmt;
	
	private String startPayDate;
	
	private String endPayDate;
	
	private String startAllotDate;
	
	private String endAllotDate;
	
	private String updateDate;
	
	private Double moneyCount;
	
	private Integer contractCount;
	
	public Double getMoneyCount() {
		return moneyCount;
	}

	public void setMoneyCount(Double moneyCount) {
		this.moneyCount = moneyCount;
	}

	public Integer getContractCount() {
		return contractCount;
	}

	public void setContractCount(Integer contractCount) {
		this.contractCount = contractCount;
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

	public String getStartAllotDate() {
		return startAllotDate;
	}

	public void setStartAllotDate(String startAllotDate) {
		this.startAllotDate = startAllotDate;
	}

	public String getEndAllotDate() {
		return endAllotDate;
	}

	public void setEndAllotDate(String endAllotDate) {
		this.endAllotDate = endAllotDate;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public Double getPayPrincipalamt() {
		return payPrincipalamt;
	}

	public void setPayPrincipalamt(Double payPrincipalamt) {
		this.payPrincipalamt = payPrincipalamt;
	}

	public Double getPayInteamt() {
		return payInteamt;
	}

	public void setPayInteamt(Double payInteamt) {
		this.payInteamt = payInteamt;
	}

	public Double getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(Double payAmt) {
		this.payAmt = payAmt;
	}
	
	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getAllotDate() {
		return allotDate;
	}

	public void setAllotDate(String allotDate) {
		this.allotDate = allotDate;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "ReceivedPaymentsVO [serialNo=" + serialNo + ", allotDate=" + allotDate + ", payDate=" + payDate
				+ ", payType=" + payType + ", payPrincipalamt=" + payPrincipalamt + ", payInteamt=" + payInteamt
				+ ", payAmt=" + payAmt + ", startPayDate=" + startPayDate + ", endPayDate=" + endPayDate
				+ ", startAllotDate=" + startAllotDate + ", endAllotDate=" + endAllotDate + ", updateDate=" + updateDate
				+ ", moneyCount=" + moneyCount + ", contractCount=" + contractCount + "]";
	}
}
