package com.billionsfinance.bas.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 
 * @author feima.zhou
 * @describe 逾期明细JavaBean
 */

public class VillageReceivedPaymentsVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String moneyCount;
	
	private String contractCount;
	
	private String contractNo;
	
	private String seqId;
	
	private String payDate;
	
	private String keepAccountsDate;
	
	private String startPayDate;
	
	private String endPayDate;
	
	private String startActualPayDate;
	
	private String endActualPayDate;
	
	private String startKeepAccountsDate;
	
	private String endKeepAccountsDate;
	
	private String updateDate;
	
	private String keepAccountsBy;
	
	private String keepAccountsRemark;
	
	private String approveTime;
	
	private String approveBy;
	
	private String approveRemark;
	
	private String registrationDate;
	
	private String startRegistrationDate;
	
	private String endRegistrationDate;
	
	private String payType;
	
	private String[] contractNoArray;
	
	private String[] seqIdArray;
	
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String[] getContractNoArray() {
		return contractNoArray;
	}

	public void setContractNoArray(String[] contractNoArray) {
		this.contractNoArray = contractNoArray;
	}

	public String[] getSeqIdArray() {
		return seqIdArray;
	}

	public void setSeqIdArray(String[] seqIdArray) {
		this.seqIdArray = seqIdArray;
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

	public String getKeepAccountsBy() {
		return keepAccountsBy;
	}

	public void setKeepAccountsBy(String keepAccountsBy) {
		this.keepAccountsBy = keepAccountsBy;
	}

	public String getKeepAccountsRemark() {
		return keepAccountsRemark;
	}

	public void setKeepAccountsRemark(String keepAccountsRemark) {
		this.keepAccountsRemark = keepAccountsRemark;
	}

	public String getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(String approveTime) {
		this.approveTime = approveTime;
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

	public String getMoneyCount() {
		return moneyCount;
	}

	public void setMoneyCount(String moneyCount) {
		this.moneyCount = moneyCount;
	}

	public String getContractCount() {
		return contractCount;
	}

	public void setContractCount(String contractCount) {
		this.contractCount = contractCount;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getKeepAccountsDate() {
		return keepAccountsDate;
	}

	public void setKeepAccountsDate(String keepAccountsDate) {
		this.keepAccountsDate = keepAccountsDate;
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

	public String getStartKeepAccountsDate() {
		return startKeepAccountsDate;
	}

	public void setStartKeepAccountsDate(String startKeepAccountsDate) {
		this.startKeepAccountsDate = startKeepAccountsDate;
	}

	public String getEndKeepAccountsDate() {
		return endKeepAccountsDate;
	}

	public void setEndKeepAccountsDate(String endKeepAccountsDate) {
		this.endKeepAccountsDate = endKeepAccountsDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	@Override
	public String toString() {
		return "VillageReceivedPaymentsVO [moneyCount=" + moneyCount + ", contractCount=" + contractCount
				+ ", contractNo=" + contractNo + ", seqId=" + seqId + ", payDate=" + payDate + ", keepAccountsDate="
				+ keepAccountsDate + ", startPayDate=" + startPayDate + ", endPayDate=" + endPayDate
				+ ", startActualPayDate=" + startActualPayDate + ", endActualPayDate=" + endActualPayDate
				+ ", startKeepAccountsDate=" + startKeepAccountsDate + ", endKeepAccountsDate=" + endKeepAccountsDate
				+ ", updateDate=" + updateDate + ", keepAccountsBy=" + keepAccountsBy + ", keepAccountsRemark="
				+ keepAccountsRemark + ", approveTime=" + approveTime + ", approveBy=" + approveBy + ", approveRemark="
				+ approveRemark + ", registrationDate=" + registrationDate + ", startRegistrationDate="
				+ startRegistrationDate + ", endRegistrationDate=" + endRegistrationDate + ", payType=" + payType
				+ ", contractNoArray=" + Arrays.toString(contractNoArray) + ", seqIdArray="
				+ Arrays.toString(seqIdArray) + "]";
	}
}
