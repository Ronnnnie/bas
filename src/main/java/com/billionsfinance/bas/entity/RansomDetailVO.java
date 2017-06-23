package com.billionsfinance.bas.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 
 * @author fmZhou
 * @describe 赎回清单明细VO
 */

public class RansomDetailVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String[] seqIdArray;
	
	private String[] contractNoArray;
	
	private String startRegisterDate;
	
	private String endRegisterDate;
	
	private String startKeepaccountsDate;
	
	private String endKeepaccountsDate;
	
	private String startAtoneForDate;
	
	private String endAtoneForDate;
	
	private String startApproveTime;
	
	private String endApproveTime;

	private String keepaccountsDate;
	
	private String seqId;
	
	private String contractNo;
	
	private String clientName;
	
	private String city;
	
	private String capitalSide;
	
	private String productType;
	
	private String productSubType;
	
	private String registerDate;
	
	private String contractDueDate;
	
	private String transferDate;
	
	private Double transferPrincipal;
	
	private Double totalPremium;
	
	private String atoneForDate;//赎回日期
	
	private String overDueFatalism;
	
	private String payDate;
	
	private String assetBelong;
	
	private String guaranteeParty;
	
	private Double principalRemainingSum;
	
	private Double overDuePrincipal;
	
	private Double overDueInterest;
	
	private Double premium;
	
	private String atoneForAmount;
	
	private String approveTime;
	
	private String approveStatus;
	
	private String approveRemark;
	
	private String approveBy;
	
	private String keepaccountsStatus;
	
	private String keepaccountsRemark;
	
	private String keepaccountsBy;
	
	private String updateApproveStatus;
	
	private String updateDate;
	
	private String payStatus;
	
	private String serialNumber;
	
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
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

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
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

	public String getContractDueDate() {
		return contractDueDate;
	}

	public void setContractDueDate(String contractDueDate) {
		this.contractDueDate = contractDueDate;
	}

	public String getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}

	public Double getTransferPrincipal() {
		return transferPrincipal;
	}

	public void setTransferPrincipal(Double transferPrincipal) {
		this.transferPrincipal = transferPrincipal;
	}

	public Double getTotalPremium() {
		return totalPremium;
	}

	public void setTotalPremium(Double totalPremium) {
		this.totalPremium = totalPremium;
	}

	public String getAtoneForDate() {
		return atoneForDate;
	}

	public void setAtoneForDate(String atoneForDate) {
		this.atoneForDate = atoneForDate;
	}

	public String getOverDueFatalism() {
		return overDueFatalism;
	}

	public void setOverDueFatalism(String overDueFatalism) {
		this.overDueFatalism = overDueFatalism;
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

	public Double getPrincipalRemainingSum() {
		return principalRemainingSum;
	}

	public void setPrincipalRemainingSum(Double principalRemainingSum) {
		this.principalRemainingSum = principalRemainingSum;
	}

	public Double getOverDuePrincipal() {
		return overDuePrincipal;
	}

	public void setOverDuePrincipal(Double overDuePrincipal) {
		this.overDuePrincipal = overDuePrincipal;
	}

	public Double getOverDueInterest() {
		return overDueInterest;
	}

	public void setOverDueInterest(Double overDueInterest) {
		this.overDueInterest = overDueInterest;
	}

	public Double getPremium() {
		return premium;
	}

	public void setPremium(Double premium) {
		this.premium = premium;
	}

	public String getAtoneForAmount() {
		return atoneForAmount;
	}

	public void setAtoneForAmount(String atoneForAmount) {
		this.atoneForAmount = atoneForAmount;
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

	public String getStartAtoneForDate() {
		return startAtoneForDate;
	}

	public void setStartAtoneForDate(String startAtoneForDate) {
		this.startAtoneForDate = startAtoneForDate;
	}

	public String getEndAtoneForDate() {
		return endAtoneForDate;
	}

	public void setEndAtoneForDate(String endAtoneForDate) {
		this.endAtoneForDate = endAtoneForDate;
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

	@Override
	public String toString() {
		return "RansomDetailVO [seqIdArray=" + Arrays.toString(seqIdArray) + ", contractNoArray="
				+ Arrays.toString(contractNoArray) + ", startRegisterDate=" + startRegisterDate + ", endRegisterDate="
				+ endRegisterDate + ", startKeepaccountsDate=" + startKeepaccountsDate + ", endKeepaccountsDate="
				+ endKeepaccountsDate + ", startAtoneForDate=" + startAtoneForDate + ", endAtoneForDate="
				+ endAtoneForDate + ", startApproveTime=" + startApproveTime + ", endApproveTime=" + endApproveTime
				+ ", keepaccountsDate=" + keepaccountsDate + ", seqId=" + seqId + ", contractNo=" + contractNo
				+ ", clientName=" + clientName + ", city=" + city + ", capitalSide=" + capitalSide + ", productType="
				+ productType + ", productSubType=" + productSubType + ", registerDate=" + registerDate
				+ ", contractDueDate=" + contractDueDate + ", transferDate=" + transferDate + ", transferPrincipal="
				+ transferPrincipal + ", totalPremium=" + totalPremium + ", atoneForDate=" + atoneForDate
				+ ", overDueFatalism=" + overDueFatalism + ", payDate=" + payDate + ", assetBelong=" + assetBelong
				+ ", guaranteeParty=" + guaranteeParty + ", principalRemainingSum=" + principalRemainingSum
				+ ", overDuePrincipal=" + overDuePrincipal + ", overDueInterest=" + overDueInterest + ", premium="
				+ premium + ", atoneForAmount=" + atoneForAmount + ", approveTime=" + approveTime + ", approveStatus="
				+ approveStatus + ", approveRemark=" + approveRemark + ", approveBy=" + approveBy
				+ ", keepaccountsStatus=" + keepaccountsStatus + ", keepaccountsRemark=" + keepaccountsRemark
				+ ", keepaccountsBy=" + keepaccountsBy + ", updateApproveStatus=" + updateApproveStatus
				+ ", updateDate=" + updateDate + ", payStatus=" + payStatus + ", serialNumber=" + serialNumber + "]";
	}
}
