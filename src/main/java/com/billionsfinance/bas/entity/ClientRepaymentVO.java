package com.billionsfinance.bas.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 
 * @author fmZhou
 * @describe 客户回款明细
 */

public class ClientRepaymentVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String keepaccountsDate;
	
	private String seqId;
	
	private String serialNo;
	
	private String clientName;
	
	private String city;
	
	private String productType;
	
	private String fundProviders;
	
	private String subProductType;
	
	private String assetBelong;
	
	private String guaranteeParty;
	
	private String transferDate;
	
	private String payDate;
	
	private String actualPayDate;
	
	private String payType;
	
	private String actualPayPrincipalAmt;
	
	private String actualPayinteAmt;
	
	private String payAmt;
	
	private String approveTime;
	
	private String approveStatus;
	
	private String contractCount;
	
	private String moneyCount;
	
	private String startKeepaccountsDate;
	
	private String endKeepaccountsDate;
	
	private String startActualPayDate;
	
	private String endActualPayDate;
	
	private String startApproveTime;
	
	private String endApproveTime;
	
	private String payStatus;
	
	private String approveRemark;
	
	private String approveBy;
	
	private String keepaccountsStatus;
	
	private String keepaccountsRemark;
	
	private String keepaccountsBy;
	
	private String updateApproveStatus;
	
	private String updateDate;
	
	private String registrationDate;
	
	private String startRegistrationDate;
	
	private String endRegistrationDate;
	
	private String[] seqIdArray;
	
	private String[] serialNoArray;
	
	private String[] actualPayPrincipalAmtArray;
	
	private String[] actualPayinteAmtArray;
	
	public String[] getSeqIdArray() {
		return seqIdArray;
	}

	public void setSeqIdArray(String[] seqIdArray) {
		this.seqIdArray = seqIdArray;
	}

	public String[] getSerialNoArray() {
		return serialNoArray;
	}

	public void setSerialNoArray(String[] serialNoArray) {
		this.serialNoArray = serialNoArray;
	}

	public String[] getActualPayPrincipalAmtArray() {
		return actualPayPrincipalAmtArray;
	}

	public void setActualPayPrincipalAmtArray(String[] actualPayPrincipalAmtArray) {
		this.actualPayPrincipalAmtArray = actualPayPrincipalAmtArray;
	}

	public String[] getActualPayinteAmtArray() {
		return actualPayinteAmtArray;
	}

	public void setActualPayinteAmtArray(String[] actualPayinteAmtArray) {
		this.actualPayinteAmtArray = actualPayinteAmtArray;
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

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateApproveStatus() {
		return updateApproveStatus;
	}

	public void setUpdateApproveStatus(String updateApproveStatus) {
		this.updateApproveStatus = updateApproveStatus;
	}

	public String getApproveRemark() {
		return approveRemark;
	}

	public void setApproveRemark(String approveRemark) {
		this.approveRemark = approveRemark;
	}

	public String getApproveBy() {
		return approveBy;
	}

	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}

	public String getKeepaccountsStatus() {
		return keepaccountsStatus;
	}

	public void setKeepaccountsStatus(String keepaccountsStatus) {
		this.keepaccountsStatus = keepaccountsStatus;
	}

	public String getKeepaccountsRemark() {
		return keepaccountsRemark;
	}

	public void setKeepaccountsRemark(String keepaccountsRemark) {
		this.keepaccountsRemark = keepaccountsRemark;
	}

	public String getKeepaccountsBy() {
		return keepaccountsBy;
	}

	public void setKeepaccountsBy(String keepaccountsBy) {
		this.keepaccountsBy = keepaccountsBy;
	}

	public String getKeepaccountsDate() {
		return keepaccountsDate;
	}

	public void setKeepaccountsDate(String keepaccountsDate) {
		this.keepaccountsDate = keepaccountsDate;
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

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getFundProviders() {
		return fundProviders;
	}

	public void setFundProviders(String fundProviders) {
		this.fundProviders = fundProviders;
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

	public String getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getActualPayDate() {
		return actualPayDate;
	}

	public void setActualPayDate(String actualPayDate) {
		this.actualPayDate = actualPayDate;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getActualPayPrincipalAmt() {
		return actualPayPrincipalAmt;
	}

	public void setActualPayPrincipalAmt(String actualPayPrincipalAmt) {
		this.actualPayPrincipalAmt = actualPayPrincipalAmt;
	}

	public String getActualPayinteAmt() {
		return actualPayinteAmt;
	}

	public void setActualPayinteAmt(String actualPayinteAmt) {
		this.actualPayinteAmt = actualPayinteAmt;
	}

	public String getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(String payAmt) {
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

	public String getContractCount() {
		return contractCount;
	}

	public void setContractCount(String contractCount) {
		this.contractCount = contractCount;
	}

	public String getMoneyCount() {
		return moneyCount;
	}

	public void setMoneyCount(String moneyCount) {
		this.moneyCount = moneyCount;
	}

	public String getStartKeepaccountsDate() {
		return startKeepaccountsDate;
	}

	public void setStartKeepaccountsDate(String startKeepaccountsDate) {
		this.startKeepaccountsDate = startKeepaccountsDate;
	}

	public String getEndKeepaccountsDate() {
		return endKeepaccountsDate;
	}

	public void setEndKeepaccountsDate(String endKeepaccountsDate) {
		this.endKeepaccountsDate = endKeepaccountsDate;
	}

	public String getStartActualPayDate() {
		return startActualPayDate;
	}

	public void setStartActualPayDate(String startActualPayDate) {
		this.startActualPayDate = startActualPayDate;
	}

	public String getEndActualPayDate() {
		return endActualPayDate;
	}

	public void setEndActualPayDate(String endActualPayDate) {
		this.endActualPayDate = endActualPayDate;
	}

	public String getStartApproveTime() {
		return startApproveTime;
	}

	public void setStartApproveTime(String startApproveTime) {
		this.startApproveTime = startApproveTime;
	}

	public String getEndApproveTime() {
		return endApproveTime;
	}

	public void setEndApproveTime(String endApproveTime) {
		this.endApproveTime = endApproveTime;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	@Override
	public String toString() {
		return "ClientRepaymentDetailVO [keepaccountsDate=" + keepaccountsDate + ", seqId=" + seqId + ", serialNo="
				+ serialNo + ", clientName=" + clientName + ", city=" + city + ", productType=" + productType
				+ ", fundProviders=" + fundProviders + ", subProductType=" + subProductType + ", assetBelong="
				+ assetBelong + ", guaranteeParty=" + guaranteeParty + ", transferDate=" + transferDate + ", payDate="
				+ payDate + ", actualPayDate=" + actualPayDate + ", payType=" + payType + ", actualPayPrincipalAmt="
				+ actualPayPrincipalAmt + ", actualPayinteAmt=" + actualPayinteAmt + ", payAmt=" + payAmt
				+ ", approveTime=" + approveTime + ", approveStatus=" + approveStatus + ", contractCount="
				+ contractCount + ", moneyCount=" + moneyCount + ", startKeepaccountsDate=" + startKeepaccountsDate
				+ ", endKeepaccountsDate=" + endKeepaccountsDate + ", startActualPayDate=" + startActualPayDate
				+ ", endActualPayDate=" + endActualPayDate + ", startApproveTime=" + startApproveTime
				+ ", endApproveTime=" + endApproveTime + ", payStatus=" + payStatus + ", approveRemark=" + approveRemark
				+ ", approveBy=" + approveBy + ", keepaccountsStatus=" + keepaccountsStatus + ", keepaccountsRemark="
				+ keepaccountsRemark + ", keepaccountsBy=" + keepaccountsBy + ", updateApproveStatus="
				+ updateApproveStatus + ", updateDate=" + updateDate + ", registrationDate=" + registrationDate
				+ ", startRegistrationDate=" + startRegistrationDate + ", endRegistrationDate=" + endRegistrationDate
				+ ", seqIdArray=" + Arrays.toString(seqIdArray) + ", serialNoArray=" + Arrays.toString(serialNoArray)
				+ ", actualPayPrincipalAmtArray=" + Arrays.toString(actualPayPrincipalAmtArray)
				+ ", actualPayinteAmtArray=" + Arrays.toString(actualPayinteAmtArray) + "]";
	}
	
}
