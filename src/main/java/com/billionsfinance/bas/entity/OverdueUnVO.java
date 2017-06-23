package com.billionsfinance.bas.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 
 * @author feima.zhou
 * @describe 逾期明细JavaBean
 */

public class OverdueUnVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String moneyCount;
	
	private String contractCount;
	
	private String inAccountDate;//记账日期
	
	private String inAccountBy;
	
	private String inAccountRemark;
	
	private String startInAccountDate;
	
	private String endInAccountDate;
	
	private String updateDate;
	
	private String subProductType;
	
	private String assetBelong;
	
	private String startRegistrationDate;
	
	private String endRegistrationDate;
	
	private String serialNo;
	
	private String seqId;
	
	private String startShouldAlsoDate;
	
	private String endShouldAlsoDate;
	
	private String businessModel;
	
	private String creditperson;
	
	private String city;
	
	private String classFy;
	
	public String getClassFy() {
		return classFy;
	}

	public void setClassFy(String classFy) {
		this.classFy = classFy;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	private String guaranteeParty;
	
	private String[] seqIdArray;
	
	private String[] serialNoArray;
	
	private String[] assetBelongArray;
	
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

	public String[] getAssetBelongArray() {
		return assetBelongArray;
	}

	public void setAssetBelongArray(String[] assetBelongArray) {
		this.assetBelongArray = assetBelongArray;
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

	public String getInAccountRemark() {
		return inAccountRemark;
	}

	public void setInAccountRemark(String inAccountRemark) {
		this.inAccountRemark = inAccountRemark;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public String getStartShouldAlsoDate() {
		return startShouldAlsoDate;
	}

	public void setStartShouldAlsoDate(String startShouldAlsoDate) {
		this.startShouldAlsoDate = startShouldAlsoDate;
	}

	public String getEndShouldAlsoDate() {
		return endShouldAlsoDate;
	}

	public void setEndShouldAlsoDate(String endShouldAlsoDate) {
		this.endShouldAlsoDate = endShouldAlsoDate;
	}

	public String getBusinessModel() {
		return businessModel;
	}

	public void setBusinessModel(String businessModel) {
		this.businessModel = businessModel;
	}

	public String getCreditperson() {
		return creditperson;
	}

	public void setCreditperson(String creditperson) {
		this.creditperson = creditperson;
	}

	public String getGuaranteeParty() {
		return guaranteeParty;
	}

	public void setGuaranteeParty(String guaranteeParty) {
		this.guaranteeParty = guaranteeParty;
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

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getInAccountBy() {
		return inAccountBy;
	}

	public void setInAccountBy(String inAccountBy) {
		this.inAccountBy = inAccountBy;
	}


	public String getInAccountDate() {
		return inAccountDate;
	}

	public void setInAccountDate(String inAccountDate) {
		this.inAccountDate = inAccountDate;
	}

	public String getStartInAccountDate() {
		return startInAccountDate;
	}

	public void setStartInAccountDate(String startInAccountDate) {
		this.startInAccountDate = startInAccountDate;
	}

	public String getEndInAccountDate() {
		return endInAccountDate;
	}

	public void setEndInAccountDate(String endInAccountDate) {
		this.endInAccountDate = endInAccountDate;
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

	@Override
	public String toString() {
		return "OverdueUnVO [moneyCount=" + moneyCount + ", contractCount=" + contractCount + ", inAccountDate="
				+ inAccountDate + ", inAccountBy=" + inAccountBy + ", inAccountRemark=" + inAccountRemark
				+ ", startInAccountDate=" + startInAccountDate + ", endInAccountDate=" + endInAccountDate
				+ ", updateDate=" + updateDate + ", subProductType=" + subProductType + ", assetBelong=" + assetBelong
				+ ", startRegistrationDate=" + startRegistrationDate + ", endRegistrationDate=" + endRegistrationDate
				+ ", serialNo=" + serialNo + ", seqId=" + seqId + ", startShouldAlsoDate=" + startShouldAlsoDate
				+ ", endShouldAlsoDate=" + endShouldAlsoDate + ", businessModel=" + businessModel + ", creditperson="
				+ creditperson + ", city=" + city + ", classFy=" + classFy + ", guaranteeParty=" + guaranteeParty
				+ ", seqIdArray=" + Arrays.toString(seqIdArray) + ", serialNoArray=" + Arrays.toString(serialNoArray)
				+ ", assetBelongArray=" + Arrays.toString(assetBelongArray) + "]";
	}
}
