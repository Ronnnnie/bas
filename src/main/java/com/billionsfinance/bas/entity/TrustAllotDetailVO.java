package com.billionsfinance.bas.entity;

import java.io.Serializable;

/**
 * 
 * @author fmZhou
 * @describe 信托划拨明细
 */

public class TrustAllotDetailVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String serialNo;
	
	private String assetBelong;
	
	private String productId;
	
	private String payDate;
	
	private Double payPrincipalamt;
	
	private Double payInteAmt;
	
	private Double payAmt;
	
	private String keepaccountsRemark;
	
	private String updateDate;
	
	private String startPayDate;
	
	private String endPayDate;
	
	private String startPayTime;
	
	private String endPayTime;
	
	private String payTime;
	
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

	public Double getPayPrincipalamt() {
		return payPrincipalamt;
	}

	public Double getPayInteAmt() {
		return payInteAmt;
	}

	public Double getPayAmt() {
		return payAmt;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public void setPayPrincipalamt(Double payPrincipalamt) {
		this.payPrincipalamt = payPrincipalamt;
	}

	public void setPayInteAmt(Double payInteAmt) {
		this.payInteAmt = payInteAmt;
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

	public String getAssetBelong() {
		return assetBelong;
	}

	public void setAssetBelong(String assetBelong) {
		this.assetBelong = assetBelong;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getKeepaccountsRemark() {
		return keepaccountsRemark;
	}

	public void setKeepaccountsRemark(String keepaccountsRemark) {
		this.keepaccountsRemark = keepaccountsRemark;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
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

	public String getStartPayTime() {
		return startPayTime;
	}

	public void setStartPayTime(String startPayTime) {
		this.startPayTime = startPayTime;
	}

	public String getEndPayTime() {
		return endPayTime;
	}

	public void setEndPayTime(String endPayTime) {
		this.endPayTime = endPayTime;
	}

	@Override
	public String toString() {
		return "TrustAllotDetailVO [serialNo=" + serialNo + ", assetBelong=" + assetBelong + ", productId=" + productId
				+ ", payDate=" + payDate + ", payPrincipalamt=" + payPrincipalamt + ", payInteAmt=" + payInteAmt
				+ ", payAmt=" + payAmt + ", keepaccountsRemark=" + keepaccountsRemark + ", updateDate=" + updateDate
				+ ", startPayDate=" + startPayDate + ", endPayDate=" + endPayDate + ", startPayTime=" + startPayTime
				+ ", endPayTime=" + endPayTime + ", payTime=" + payTime + ", moneyCount=" + moneyCount
				+ ", contractCount=" + contractCount + "]";
	}
	
}
