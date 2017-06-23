package com.billionsfinance.bas.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 
 * @author feima.zhou
 * @describe 逾期明细JavaBean
 */

public class JYReceivedPaymentsVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String moneyCount;
	
	private String contractCount;
	
	private String contracts;//合同号
	
	public String getContracts() {
		return contracts;
	}

	public void setContracts(String contracts) {
		this.contracts = contracts;
	}

	private String sequence;
	
	private String repayDate;
	
	private String keepAccountsTime;
	
	private String startKeepAccountsTime;
	
	private String endKeepAccountsTime;
	
	private String startRepayDate;
	
	private String endRepayDate;
	
	private String updateDate;
	
	private String approveBy;
	
	private String approveTime;
	
	private String approveRemark;
	
	private String keepAccountsBy;
	
	private String keepAccountsRemark;
	
	private String registrationDate;
	
	private String startRegistrationDate;
	
	private String endRegistrationDate;
	
	private String[] contractsArray;
	
	private String[] sequenceArray;
	
	public String[] getContractsArray() {
		return contractsArray;
	}

	public void setContractsArray(String[] contractsArray) {
		this.contractsArray = contractsArray;
	}

	public String[] getSequenceArray() {
		return sequenceArray;
	}

	public void setSequenceArray(String[] sequenceArray) {
		this.sequenceArray = sequenceArray;
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

	public String getApproveBy() {
		return approveBy;
	}

	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}

	public String getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(String approveTime) {
		this.approveTime = approveTime;
	}

	public String getApproveRemark() {
		return approveRemark;
	}

	public void setApproveRemark(String approveRemark) {
		this.approveRemark = approveRemark;
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

	public String getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}

	public String getKeepAccountsTime() {
		return keepAccountsTime;
	}

	public void setKeepAccountsTime(String keepAccountsTime) {
		this.keepAccountsTime = keepAccountsTime;
	}

	public String getStartKeepAccountsTime() {
		return startKeepAccountsTime;
	}

	public void setStartKeepAccountsTime(String startKeepAccountsTime) {
		this.startKeepAccountsTime = startKeepAccountsTime;
	}

	public String getEndKeepAccountsTime() {
		return endKeepAccountsTime;
	}

	public void setEndKeepAccountsTime(String endKeepAccountsTime) {
		this.endKeepAccountsTime = endKeepAccountsTime;
	}

	public String getStartRepayDate() {
		return startRepayDate;
	}

	public void setStartRepayDate(String startRepayDate) {
		this.startRepayDate = startRepayDate;
	}

	public String getEndRepayDate() {
		return endRepayDate;
	}

	public void setEndRepayDate(String endRepayDate) {
		this.endRepayDate = endRepayDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	@Override
	public String toString() {
		return "JYReceivedPaymentsVO [moneyCount=" + moneyCount + ", contractCount=" + contractCount + ", contracts="
				+ contracts + ", contracts=" + contracts + ", sequence=" + sequence + ", repayDate=" + repayDate
				+ ", keepAccountsTime=" + keepAccountsTime + ", startKeepAccountsTime=" + startKeepAccountsTime
				+ ", endKeepAccountsTime=" + endKeepAccountsTime + ", startRepayDate=" + startRepayDate
				+ ", endRepayDate=" + endRepayDate + ", updateDate=" + updateDate + ", approveBy=" + approveBy
				+ ", approveTime=" + approveTime + ", approveRemark=" + approveRemark + ", keepAccountsBy="
				+ keepAccountsBy + ", keepAccountsRemark=" + keepAccountsRemark + ", registrationDate="
				+ registrationDate + ", startRegistrationDate=" + startRegistrationDate + ", endRegistrationDate="
				+ endRegistrationDate + ", contractNoArray=" + Arrays.toString(contractsArray) + ", sequenceArray="
				+ Arrays.toString(sequenceArray) + "]";
	}
	
}
