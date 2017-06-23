package com.billionsfinance.bas.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 
 * @author fmZhou
 * @describe 代偿清单-资金&核算明细
 */

public class CompensatoryDetailVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String keepaccountsDate;
	
	private String serialNumber;
	
	private String loanNo;
	
	private String contractNo;
	
	private String clientName ;
	
	private String city ;
	
	private String capitalSide;
	
	private String productType;
	
	private String productSubType;
	
	private String registerDate;
	
	private String principalBlance;
	
	private String dcoverDuedayBefore;
	
	private String dcoverDueprinCipalBefore;
	
	private String dcoverDueinterestBefore;
	
	private String dcDate;
	
	private String payDate;
	
	private String assetBelong ;
	
	private String guaranteeParty;
	
	private String principalRemainingSum;
	
	private String overDuePrincipal;
	
	private String overDueInterest;
	
	private String dcPrincipal;
	
	private String dcInterest;
	
	private String dcAmount;
	
	private String approveStatus;
	
	private String approveTime;
	
	private String approveBy;
	
	private String approveRemark;
	
	private String keepaccountsStatus;
	
	private String keepaccountsBy;
	
	private String keepaccountsRemark;
	
	private String startKeepaccountsDate;
	
	private String endKeepaccountsDate;
	
	private String startDcDate;
	
	private String endDcDate;
	
	private String updateApproveStatus;
	
	private String updateDate;
	
	private String startRegisterDate;
	
	private String endRegisterDate;
	
	private String seqId;
	
	private String[] seqIdArray;
	
	private String[] contractNoArray;
	
	public String[] getSeqIdArray() {
		return seqIdArray;
	}

	public void setSeqIdArray(String[] seqIdArray) {
		this.seqIdArray = seqIdArray;
	}

	public String[] getContractNoArray() {
		return contractNoArray;
	}

	public void setContractNoArray(String[] contractNoArray) {
		this.contractNoArray = contractNoArray;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getStartRegisterDate() {
		return startRegisterDate;
	}

	public void setStartRegisterDate(String startRegisterDate) {
		this.startRegisterDate = startRegisterDate;
	}

	public String getEndRegisterDate() {
		return endRegisterDate;
	}

	public void setEndRegisterDate(String endRegisterDate) {
		this.endRegisterDate = endRegisterDate;
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

	public String getStartDcDate() {
		return startDcDate;
	}

	public void setStartDcDate(String startDcDate) {
		this.startDcDate = startDcDate;
	}

	public String getEndDcDate() {
		return endDcDate;
	}

	public void setEndDcDate(String endDcDate) {
		this.endDcDate = endDcDate;
	}

	public String getKeepaccountsDate() {
		return keepaccountsDate;
	}

	public void setKeepaccountsDate(String keepaccountsDate) {
		this.keepaccountsDate = keepaccountsDate;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getLoanNo() {
		return loanNo;
	}

	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
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

	public String getCapitalSide() {
		return capitalSide;
	}

	public void setCapitalSide(String capitalSide) {
		this.capitalSide = capitalSide;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductSubType() {
		return productSubType;
	}

	public void setProductSubType(String productSubType) {
		this.productSubType = productSubType;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getPrincipalBlance() {
		return principalBlance;
	}

	public void setPrincipalBlance(String principalBlance) {
		this.principalBlance = principalBlance;
	}

	public String getDcoverDuedayBefore() {
		return dcoverDuedayBefore;
	}

	public void setDcoverDuedayBefore(String dcoverDuedayBefore) {
		this.dcoverDuedayBefore = dcoverDuedayBefore;
	}

	public String getDcoverDueprinCipalBefore() {
		return dcoverDueprinCipalBefore;
	}

	public void setDcoverDueprinCipalBefore(String dcoverDueprinCipalBefore) {
		this.dcoverDueprinCipalBefore = dcoverDueprinCipalBefore;
	}

	public String getDcoverDueinterestBefore() {
		return dcoverDueinterestBefore;
	}

	public void setDcoverDueinterestBefore(String dcoverDueinterestBefore) {
		this.dcoverDueinterestBefore = dcoverDueinterestBefore;
	}

	public String getDcDate() {
		return dcDate;
	}

	public void setDcDate(String dcDate) {
		this.dcDate = dcDate;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
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

	public String getPrincipalRemainingSum() {
		return principalRemainingSum;
	}

	public void setPrincipalRemainingSum(String principalRemainingSum) {
		this.principalRemainingSum = principalRemainingSum;
	}

	public String getOverDuePrincipal() {
		return overDuePrincipal;
	}

	public void setOverDuePrincipal(String overDuePrincipal) {
		this.overDuePrincipal = overDuePrincipal;
	}

	public String getOverDueInterest() {
		return overDueInterest;
	}

	public void setOverDueInterest(String overDueInterest) {
		this.overDueInterest = overDueInterest;
	}

	public String getDcPrincipal() {
		return dcPrincipal;
	}

	public void setDcPrincipal(String dcPrincipal) {
		this.dcPrincipal = dcPrincipal;
	}

	public String getDcInterest() {
		return dcInterest;
	}

	public void setDcInterest(String dcInterest) {
		this.dcInterest = dcInterest;
	}

	public String getDcAmount() {
		return dcAmount;
	}

	public void setDcAmount(String dcAmount) {
		this.dcAmount = dcAmount;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
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

	public String getKeepaccountsStatus() {
		return keepaccountsStatus;
	}

	public void setKeepaccountsStatus(String keepaccountsStatus) {
		this.keepaccountsStatus = keepaccountsStatus;
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

	@Override
	public String toString() {
		return "CompensatoryDetailVO [keepaccountsDate=" + keepaccountsDate + ", serialNumber=" + serialNumber
				+ ", loanNo=" + loanNo + ", contractNo=" + contractNo + ", clientName=" + clientName + ", city=" + city
				+ ", capitalSide=" + capitalSide + ", productType=" + productType + ", productSubType=" + productSubType
				+ ", registerDate=" + registerDate + ", principalBlance=" + principalBlance + ", dcoverDuedayBefore="
				+ dcoverDuedayBefore + ", dcoverDueprinCipalBefore=" + dcoverDueprinCipalBefore
				+ ", dcoverDueinterestBefore=" + dcoverDueinterestBefore + ", dcDate=" + dcDate + ", payDate=" + payDate
				+ ", assetBelong=" + assetBelong + ", guaranteeParty=" + guaranteeParty + ", principalRemainingSum="
				+ principalRemainingSum + ", overDuePrincipal=" + overDuePrincipal + ", overDueInterest="
				+ overDueInterest + ", dcPrincipal=" + dcPrincipal + ", dcInterest=" + dcInterest + ", dcAmount="
				+ dcAmount + ", approveStatus=" + approveStatus + ", approveTime=" + approveTime + ", approveBy="
				+ approveBy + ", approveRemark=" + approveRemark + ", keepaccountsStatus=" + keepaccountsStatus
				+ ", keepaccountsBy=" + keepaccountsBy + ", keepaccountsRemark=" + keepaccountsRemark
				+ ", startKeepaccountsDate=" + startKeepaccountsDate + ", endKeepaccountsDate=" + endKeepaccountsDate
				+ ", startDcDate=" + startDcDate + ", endDcDate=" + endDcDate + ", updateApproveStatus="
				+ updateApproveStatus + ", updateDate=" + updateDate + ", startRegisterDate=" + startRegisterDate
				+ ", endRegisterDate=" + endRegisterDate + ", seqId=" + seqId + ", seqIdArray="
				+ Arrays.toString(seqIdArray) + ", contractNoArray=" + Arrays.toString(contractNoArray) + "]";
	}
}
