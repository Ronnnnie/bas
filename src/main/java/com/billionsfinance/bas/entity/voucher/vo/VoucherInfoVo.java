package com.billionsfinance.bas.entity.voucher.vo;
/**
 * @ClassName: VoucherInfoVo.java
 * @Description: 凭证详细信息
 * @author lin.tang
 * @date 2017年5月31日 下午3:09:12
 * Copyright: Copyright (c) 2017-2050 Company:BQJR
 */
public class VoucherInfoVo {
	/**公司**/
	private String company="sdfd";
	/**记账日期**/
	private String accountDate;
	/**业务日期**/
	private String businessDate;
	/**会计期间**/
	private String accountingGap;
	/**凭证类型**/
	private String voucherType="业";
	/**凭证号**/
	private String voucherNo;
	/**分录号**/
	private String entryNo;
	/**摘要**/
	private String abstractInfo;
	/**科目**/
	private String subjectInfo;
	/**币种**/
	private String currency="RMB";
	/**汇率**/
	private String exchangeRate="1";
	/**方向**/
	private String direction;
	/**原币金额**/
	private String totalFee;
	/**数量**/
	private String total="0";
	/**单价**/
	private String singlePrice="0";
	/**借方金额**/
	private String debtorMoney;
	/**贷方金额**/
	private String lenderMoney;
	/**制单人**/
	private String originator="B00142297";
	/**过账人**/
	private String postinger="B00142297";
	/**审核人**/
	private String auditor="B00142297";
	/**附件数量**/
	private String enclosureTotal="0";
	/**过账标记**/
	private String postingFlag="TRUE";
	/**机制凭证**/
	private String mechanismCredential;
	/**删除标记**/
	private String deleteFlag="FALSE";
	/**凭证号**/
	private String voucherNum="1494990961478--0";
	/**公司编号**/
	private String companyNum;
	/**参考信息**/
	private String referInfo;
	/**是否有现金流**/
	private String isCashFlow;
	/**现金流标记**/
	private String cashFlowFlag="4";
	/**业务编号**/
	private String businessNum;
	/**结算方式**/
	private String settlementType;
	/**结算号**/
	private String settlementNum;
	/**辅助摘要信息**/
	private String auxiliaryAbstractInfo;
	/**发票编号**/
	private String invoiceNum;
	/**换票证号**/
	private String exchangeInvoiceNum;
	/**客户**/
	private String customer;
	/**费用类型**/
	private String feeType;
	/**收款人**/
	private String payee;
	/**物料**/
	private String materiel;
	/**财务组织**/
	private String financialOrganization;
	/**供应商**/
	private String Supplier;
	/**辅助账务日期**/
	private String auxiliaryAccountingDate;
	/**到期日**/
	private String expireDate;
	
	/**核算项目**/
	private String businessAccounting1;
	/**编码**/
	private String code1;
	/**名称**/
	private String name1;
	
	private String businessAccounting2;
	private String code2;
	private String name2;
	
	private String businessAccounting3;
	private String code3;
	private String name3;
	
	private String businessAccounting4;
	private String code4;
	private String name4;
	
	private String businessAccounting5;
	private String code5;
	private String name5;
	
	private String businessAccounting6;
	private String code6;
	private String name6;
	
	private String businessAccounting7;
	private String code7;
	private String name7;
	
	private String businessAccounting8;
	private String code8;
	private String name8;
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getAccountDate() {
		return accountDate;
	}
	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
	}
	public String getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}
	public String getAccountingGap() {
		return accountingGap;
	}
	public void setAccountingGap(String accountingGap) {
		this.accountingGap = accountingGap;
	}
	public String getVoucherType() {
		return voucherType;
	}
	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}
	public String getVoucherNo() {
		return voucherNo;
	}
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	public String getEntryNo() {
		return entryNo;
	}
	public void setEntryNo(String entryNo) {
		this.entryNo = entryNo;
	}
	public String getAbstractInfo() {
		return abstractInfo;
	}
	public void setAbstractInfo(String abstractInfo) {
		this.abstractInfo = abstractInfo;
	}
	public String getSubjectInfo() {
		return subjectInfo;
	}
	public void setSubjectInfo(String subjectInfo) {
		this.subjectInfo = subjectInfo;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getSinglePrice() {
		return singlePrice;
	}
	public void setSinglePrice(String singlePrice) {
		this.singlePrice = singlePrice;
	}
	public String getDebtorMoney() {
		return debtorMoney;
	}
	public void setDebtorMoney(String debtorMoney) {
		this.debtorMoney = debtorMoney;
	}
	public String getLenderMoney() {
		return lenderMoney;
	}
	public void setLenderMoney(String lenderMoney) {
		this.lenderMoney = lenderMoney;
	}
	public String getOriginator() {
		return originator;
	}
	public void setOriginator(String originator) {
		this.originator = originator;
	}
	public String getPostinger() {
		return postinger;
	}
	public void setPostinger(String postinger) {
		this.postinger = postinger;
	}
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	public String getEnclosureTotal() {
		return enclosureTotal;
	}
	public void setEnclosureTotal(String enclosureTotal) {
		this.enclosureTotal = enclosureTotal;
	}
	public String getPostingFlag() {
		return postingFlag;
	}
	public void setPostingFlag(String postingFlag) {
		this.postingFlag = postingFlag;
	}
	public String getMechanismCredential() {
		return mechanismCredential;
	}
	public void setMechanismCredential(String mechanismCredential) {
		this.mechanismCredential = mechanismCredential;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getVoucherNum() {
		return voucherNum;
	}
	public void setVoucherNum(String voucherNum) {
		this.voucherNum = voucherNum;
	}
	public String getCompanyNum() {
		return companyNum;
	}
	public void setCompanyNum(String companyNum) {
		this.companyNum = companyNum;
	}
	public String getReferInfo() {
		return referInfo;
	}
	public void setReferInfo(String referInfo) {
		this.referInfo = referInfo;
	}
	public String getIsCashFlow() {
		return isCashFlow;
	}
	public void setIsCashFlow(String isCashFlow) {
		this.isCashFlow = isCashFlow;
	}
	public String getCashFlowFlag() {
		return cashFlowFlag;
	}
	public void setCashFlowFlag(String cashFlowFlag) {
		this.cashFlowFlag = cashFlowFlag;
	}
	public String getBusinessNum() {
		return businessNum;
	}
	public void setBusinessNum(String businessNum) {
		this.businessNum = businessNum;
	}
	public String getSettlementType() {
		return settlementType;
	}
	public void setSettlementType(String settlementType) {
		this.settlementType = settlementType;
	}
	public String getSettlementNum() {
		return settlementNum;
	}
	public void setSettlementNum(String settlementNum) {
		this.settlementNum = settlementNum;
	}
	public String getAuxiliaryAbstractInfo() {
		return auxiliaryAbstractInfo;
	}
	public void setAuxiliaryAbstractInfo(String auxiliaryAbstractInfo) {
		this.auxiliaryAbstractInfo = auxiliaryAbstractInfo;
	}
	public String getInvoiceNum() {
		return invoiceNum;
	}
	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}
	public String getExchangeInvoiceNum() {
		return exchangeInvoiceNum;
	}
	public void setExchangeInvoiceNum(String exchangeInvoiceNum) {
		this.exchangeInvoiceNum = exchangeInvoiceNum;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getPayee() {
		return payee;
	}
	public void setPayee(String payee) {
		this.payee = payee;
	}
	public String getMateriel() {
		return materiel;
	}
	public void setMateriel(String materiel) {
		this.materiel = materiel;
	}
	public String getFinancialOrganization() {
		return financialOrganization;
	}
	public void setFinancialOrganization(String financialOrganization) {
		this.financialOrganization = financialOrganization;
	}
	public String getSupplier() {
		return Supplier;
	}
	public void setSupplier(String supplier) {
		Supplier = supplier;
	}
	public String getAuxiliaryAccountingDate() {
		return auxiliaryAccountingDate;
	}
	public void setAuxiliaryAccountingDate(String auxiliaryAccountingDate) {
		this.auxiliaryAccountingDate = auxiliaryAccountingDate;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	public String getBusinessAccounting1() {
		return businessAccounting1;
	}
	public void setBusinessAccounting1(String businessAccounting1) {
		this.businessAccounting1 = businessAccounting1;
	}
	public String getCode1() {
		return code1;
	}
	public void setCode1(String code1) {
		this.code1 = code1;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getBusinessAccounting2() {
		return businessAccounting2;
	}
	public void setBusinessAccounting2(String businessAccounting2) {
		this.businessAccounting2 = businessAccounting2;
	}
	public String getCode2() {
		return code2;
	}
	public void setCode2(String code2) {
		this.code2 = code2;
	}
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	public String getBusinessAccounting3() {
		return businessAccounting3;
	}
	public void setBusinessAccounting3(String businessAccounting3) {
		this.businessAccounting3 = businessAccounting3;
	}
	public String getCode3() {
		return code3;
	}
	public void setCode3(String code3) {
		this.code3 = code3;
	}
	public String getName3() {
		return name3;
	}
	public void setName3(String name3) {
		this.name3 = name3;
	}
	public String getBusinessAccounting4() {
		return businessAccounting4;
	}
	public void setBusinessAccounting4(String businessAccounting4) {
		this.businessAccounting4 = businessAccounting4;
	}
	public String getCode4() {
		return code4;
	}
	public void setCode4(String code4) {
		this.code4 = code4;
	}
	public String getName4() {
		return name4;
	}
	public void setName4(String name4) {
		this.name4 = name4;
	}
	public String getBusinessAccounting5() {
		return businessAccounting5;
	}
	public void setBusinessAccounting5(String businessAccounting5) {
		this.businessAccounting5 = businessAccounting5;
	}
	public String getCode5() {
		return code5;
	}
	public void setCode5(String code5) {
		this.code5 = code5;
	}
	public String getName5() {
		return name5;
	}
	public void setName5(String name5) {
		this.name5 = name5;
	}
	public String getBusinessAccounting6() {
		return businessAccounting6;
	}
	public void setBusinessAccounting6(String businessAccounting6) {
		this.businessAccounting6 = businessAccounting6;
	}
	public String getCode6() {
		return code6;
	}
	public void setCode6(String code6) {
		this.code6 = code6;
	}
	public String getName6() {
		return name6;
	}
	public void setName6(String name6) {
		this.name6 = name6;
	}
	public String getBusinessAccounting7() {
		return businessAccounting7;
	}
	public void setBusinessAccounting7(String businessAccounting7) {
		this.businessAccounting7 = businessAccounting7;
	}
	public String getCode7() {
		return code7;
	}
	public void setCode7(String code7) {
		this.code7 = code7;
	}
	public String getName7() {
		return name7;
	}
	public void setName7(String name7) {
		this.name7 = name7;
	}
	public String getBusinessAccounting8() {
		return businessAccounting8;
	}
	public void setBusinessAccounting8(String businessAccounting8) {
		this.businessAccounting8 = businessAccounting8;
	}
	public String getCode8() {
		return code8;
	}
	public void setCode8(String code8) {
		this.code8 = code8;
	}
	public String getName8() {
		return name8;
	}
	public void setName8(String name8) {
		this.name8 = name8;
	}
	
	
	
}


