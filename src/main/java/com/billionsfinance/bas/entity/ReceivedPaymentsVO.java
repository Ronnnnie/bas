package com.billionsfinance.bas.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 
 * @author fmZhoua
 * @describe 回款表VO
 */

public class ReceivedPaymentsVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String cancelType;
	
	private String id;//主键
	
	private String seqId;
	
	private String serialNo; //合同号
	
	private String registrationDate;// 注册日期 贷款日期
	
	private String keepAccountsDate; //KEEPACCOUNTS_DATE 记账日期 
	
	private String subProductType;//产品子类型
	
	private String city;//城市
	
	private String assetBelong;
	
	private String startRegistrationDate;// 注册日期 贷款日期 起
	
	private String endRegistrationDate;// 注册日期 贷款日期 至
	
	private String startKeepAccountsDate; //KEEPACCOUNTS_DATE 记账日期  起
	
	private String endKeepAccountsDate; //KEEPACCOUNTS_DATE 记账日期  至
	
	private String updateDate;//修改日期
	
	private Double moneyCount;
	
	private Long contractCount;
	
	private String actualPayPrincipalAmt;
	
	private String actualPayinteAmt;
	
//-----------------------------
//-----------------------------
	private String[] seqIdArray;
	
	private String[] serialNoArray;
	
	private String[] assetBelongArray;
	
	private String[] actualPayPrincipalAmtArray;
	
	private String[] actualPayinteAmtArray;
	
	private String startShouldAlsoDate;
	
	private String endShouldAlsoDate;
	
	private String businessModel;
	
	private String creditperson;
	
	private String guaranteeParty;
	
	public String getCancelType() {
		return cancelType;
	}

	public void setCancelType(String cancelType) {
		this.cancelType = cancelType;
	}

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getKeepAccountsDate() {
		return keepAccountsDate;
	}

	public void setKeepAccountsDate(String keepAccountsDate) {
		this.keepAccountsDate = keepAccountsDate;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAssetBelong() {
		return assetBelong;
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

	public void setAssetBelong(String assetBelong) {
		this.assetBelong = assetBelong;
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

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	@Override
	public String toString() {
		return "ReceivedPaymentsVO [id=" + id + ", seqId=" + seqId + ", serialNo=" + serialNo + ", registrationDate="
				+ registrationDate + ", keepAccountsDate=" + keepAccountsDate + ", subProductType=" + subProductType
				+ ", city=" + city + ", assetBelong=" + assetBelong + ", startRegistrationDate=" + startRegistrationDate
				+ ", endRegistrationDate=" + endRegistrationDate + ", startKeepAccountsDate=" + startKeepAccountsDate
				+ ", endKeepAccountsDate=" + endKeepAccountsDate + ", updateDate=" + updateDate + ", moneyCount="
				+ moneyCount + ", contractCount=" + contractCount + ", actualPayPrincipalAmt=" + actualPayPrincipalAmt
				+ ", actualPayinteAmt=" + actualPayinteAmt + ", seqIdArray=" + Arrays.toString(seqIdArray)
				+ ", serialNoArray=" + Arrays.toString(serialNoArray) + ", assetBelongArray="
				+ Arrays.toString(assetBelongArray) + ", actualPayPrincipalAmtArray="
				+ Arrays.toString(actualPayPrincipalAmtArray) + ", actualPayinteAmtArray="
				+ Arrays.toString(actualPayinteAmtArray) + ", startShouldAlsoDate=" + startShouldAlsoDate
				+ ", endShouldAlsoDate=" + endShouldAlsoDate + ", businessModel=" + businessModel + ", creditperson="
				+ creditperson + ", guaranteeParty=" + guaranteeParty + "]";
	}
}
