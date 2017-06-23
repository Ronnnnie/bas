package com.billionsfinance.bas.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 
 * @author ZFM
 * @describe 回款明细
 */

public class RepaymentDetailVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer payStatus;
	
	private String duedate;
	
	private String startDueDate;
	
	private String endDueDate;
	
	private String seqId;
	
	private String serialNo;
	
	private String clientName;
	
	private String city;
	
	private String productId;
	
	private String subProductType;
	
	private String assetBelong;
	
	private String guaranteeParty;
	
	private String payType;
	
	private String payDate;
	
	private String payPrincipalamt;
	
	private String payInteAmt;
	
	private Double stampDuty;
	
	private Double payAmt;
	
	private String approveTime;
	
	private String approveStatus;
	
	private Double moneyCount;
	
	private Long contractCount;
	
	private String startPayDate;
	
	private String endPayDate;
	
	private String keepaccountsdate;
	
	private String keepaccountsBy;
	
	private String keepaccountsRemark;
	
	private String approveBy;
	
	private String approveRemark;
	
	private String updateApproveStatus;
	
	private String registrationDate;
	
	private String startRegistrationDate;
	
	private String endRegistrationDate;
	
	private String[] serialNoArray;
	
	private String[] seqIdArray;
	
	private String[] assetBelongArray;
	
	private String[] payPrincipalamtArray;
	
	private String[] payInteAmtArray;
	
	public String[] getSerialNoArray() {
		return serialNoArray;
	}

	public void setSerialNoArray(String[] serialNoArray) {
		this.serialNoArray = serialNoArray;
	}

	public String[] getSeqIdArray() {
		return seqIdArray;
	}

	public void setSeqIdArray(String[] seqIdArray) {
		this.seqIdArray = seqIdArray;
	}

	public String[] getAssetBelongArray() {
		return assetBelongArray;
	}

	public void setAssetBelongArray(String[] assetBelongArray) {
		this.assetBelongArray = assetBelongArray;
	}

	public String[] getPayPrincipalamtArray() {
		return payPrincipalamtArray;
	}

	public void setPayPrincipalamtArray(String[] payPrincipalamtArray) {
		this.payPrincipalamtArray = payPrincipalamtArray;
	}

	public String[] getPayInteAmtArray() {
		return payInteAmtArray;
	}

	public void setPayInteAmtArray(String[] payInteAmtArray) {
		this.payInteAmtArray = payInteAmtArray;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
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

	public String getUpdateApproveStatus() {
		return updateApproveStatus;
	}

	public void setUpdateApproveStatus(String updateApproveStatus) {
		this.updateApproveStatus = updateApproveStatus;
	}

	public String getApproveBy() {
		return approveBy;
	}

	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}

	public String getApproveRemark() {
		return approveRemark;
	}

	public void setApproveRemark(String approveRemark) {
		this.approveRemark = approveRemark;
	}


	public String getKeepaccountsBy() {
		return keepaccountsBy;
	}

	public void setKeepaccountsBy(String keepaccountsBy) {
		this.keepaccountsBy = keepaccountsBy;
	}

	public String getKeepaccountsRemark() {
		return keepaccountsRemark;
	}

	public void setKeepaccountsRemark(String keepaccountsRemark) {
		this.keepaccountsRemark = keepaccountsRemark;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public String getAssetBelong() {
		return assetBelong;
	}

	public void setAssetBelong(String assetBelong) {
		this.assetBelong = assetBelong;
	}

	public String getGuaranteeParty() {
		return guaranteeParty;
	}

	public void setGuaranteeParty(String guaranteeParty) {
		this.guaranteeParty = guaranteeParty;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getPayPrincipalamt() {
		return payPrincipalamt;
	}

	public void setPayPrincipalamt(String payPrincipalamt) {
		this.payPrincipalamt = payPrincipalamt;
	}

	public String getPayInteAmt() {
		return payInteAmt;
	}

	public void setPayInteAmt(String payInteAmt) {
		this.payInteAmt = payInteAmt;
	}

	public Double getStampDuty() {
		return stampDuty;
	}

	public void setStampDuty(Double stampDuty) {
		this.stampDuty = stampDuty;
	}

	public Double getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(Double payAmt) {
		this.payAmt = payAmt;
	}

	public String getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(String approveTime) {
		this.approveTime = approveTime;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public Double getMoneyCount() {
		return moneyCount;
	}

	public void setMoneyCount(Double moneyCount) {
		this.moneyCount = moneyCount;
	}

	public Long getContractCount() {
		return contractCount;
	}

	public void setContractCount(Long contractCount) {
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

	public String getDuedate() {
		return duedate;
	}

	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}

	public String getStartDueDate() {
		return startDueDate;
	}

	public void setStartDueDate(String startDueDate) {
		this.startDueDate = startDueDate;
	}

	public String getEndDueDate() {
		return endDueDate;
	}

	public void setEndDueDate(String endDueDate) {
		this.endDueDate = endDueDate;
	}

	public String getKeepaccountsdate() {
		return keepaccountsdate;
	}

	public void setKeepaccountsdate(String keepaccountsdate) {
		this.keepaccountsdate = keepaccountsdate;
	}

	@Override
	public String toString() {
		return "RepaymentDetailVO [payStatus=" + payStatus + ", duedate=" + duedate + ", startDueDate=" + startDueDate
				+ ", endDueDate=" + endDueDate + ", seqId=" + seqId + ", serialNo=" + serialNo + ", clientName="
				+ clientName + ", city=" + city + ", productId=" + productId + ", subProductType=" + subProductType
				+ ", assetBelong=" + assetBelong + ", guaranteeParty=" + guaranteeParty + ", payType=" + payType
				+ ", payDate=" + payDate + ", payPrincipalamt=" + payPrincipalamt + ", payInteAmt=" + payInteAmt
				+ ", stampDuty=" + stampDuty + ", payAmt=" + payAmt + ", approveTime=" + approveTime
				+ ", approveStatus=" + approveStatus + ", moneyCount=" + moneyCount + ", contractCount=" + contractCount
				+ ", startPayDate=" + startPayDate + ", endPayDate=" + endPayDate + ", keepaccountsdate="
				+ keepaccountsdate + ", keepaccountsBy=" + keepaccountsBy + ", keepaccountsRemark=" + keepaccountsRemark
				+ ", approveBy=" + approveBy + ", approveRemark=" + approveRemark + ", updateApproveStatus="
				+ updateApproveStatus + ", registrationDate=" + registrationDate + ", startRegistrationDate="
				+ startRegistrationDate + ", endRegistrationDate=" + endRegistrationDate + ", serialNoArray="
				+ Arrays.toString(serialNoArray) + ", seqIdArray=" + Arrays.toString(seqIdArray) + ", assetBelongArray="
				+ Arrays.toString(assetBelongArray) + ", payPrincipalamtArray=" + Arrays.toString(payPrincipalamtArray)
				+ ", payInteAmtArray=" + Arrays.toString(payInteAmtArray) + "]";
	}

}
