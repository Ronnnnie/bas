package com.billionsfinance.bas.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 
 * @author fmZhoua
 * @describe 资金明细VO
 */

public class BusinessLoanVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double moneyCount;
	
	private long contractCount;
	
	public double getMoneyCount() {
		return moneyCount;
	}

	public void setMoneyCount(double moneyCount) {
		this.moneyCount = moneyCount;
	}

	public long getContractCount() {
		return contractCount;
	}

	public void setContractCount(long contractCount) {
		this.contractCount = contractCount;
	}

	private String id;//主键
	
	private String serialNo; //合同号
	
	private String businessDate;// 注册日期 贷款日期
	
	private String keepAccountsDate; //KEEPACCOUNTS_DATE 记账日期 
	
	private String payDate;//付款日期
	
	private String makeCollectionsDate;//收款日期
	
	
	private String startBusinessDate;// 注册日期 贷款日期 起
	
	private String endBusinessDate;// 注册日期 贷款日期 至
	
	
	
	private String startKeepAccountsDate; //KEEPACCOUNTS_DATE 记账日期  起
	
	private String endKeepAccountsDate; //KEEPACCOUNTS_DATE 记账日期  至
	
	
	private String startPayDate;//付款日期 起
	
	private String endPayDate;//付款日期 至
	
	private String startMakeCollectionsDate;//收款日期 起
	
	private String endMakeCollectionsDate;//收款日期 至
	
	
	private String productSubType;//产品子类型
	
	private double businessSum;//BUSINESSSUM  贷款本金
	
	private String capitalSide;//Capitalside 资金方
	
	private String city;//城市
	
	private double deductionKHServiceFee;//扣减客户服务费
	
	private double deductionSHServiceFee;//扣减商户服务费
	
	private double paySum;//支付金额
	
	private String khName;//客户姓名
	
	private String shName;//商户姓名
	
	private String shId;//商户Id
	
	private String[] array;
	
	private String[] idArray;
	
	public String[] getArray() {
		return array;
	}

	public void setArray(String[] array) {
		this.array = array;
	}
	
	public String[] getIdArray() {
		return idArray;
	}
	
	public void setIdArray(String[] idArray) {
		this.idArray = idArray;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getKeepAccountsDate() {
		return keepAccountsDate;
	}

	public void setKeepAccountsDate(String keepAccountsDate) {
		this.keepAccountsDate = keepAccountsDate;
	}

	public double getBusinessSum() {
		return businessSum;
	}

	public void setBusinessSum(double businessSum) {
		this.businessSum = businessSum;
	}

	public String getCapitalSide() {
		return capitalSide;
	}

	public void setCapitalSide(String capitalSide) {
		this.capitalSide = capitalSide;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getProductSubType() {
		return productSubType;
	}

	public void setProductSubType(String productSubType) {
		this.productSubType = productSubType;
	}

	public String getStartBusinessDate() {
		return startBusinessDate;
	}

	public void setStartBusinessDate(String startBusinessDate) {
		this.startBusinessDate = startBusinessDate;
	}

	public String getEndBusinessDate() {
		return endBusinessDate;
	}

	public void setEndBusinessDate(String endBusinessDate) {
		this.endBusinessDate = endBusinessDate;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public double getDeductionKHServiceFee() {
		return deductionKHServiceFee;
	}

	public void setDeductionKHServiceFee(double deductionKHServiceFee) {
		this.deductionKHServiceFee = deductionKHServiceFee;
	}

	public double getDeductionSHServiceFee() {
		return deductionSHServiceFee;
	}

	public void setDeductionSHServiceFee(double deductionSHServiceFee) {
		this.deductionSHServiceFee = deductionSHServiceFee;
	}

	public double getPaySum() {
		return paySum;
	}

	public void setPaySum(double paySum) {
		this.paySum = paySum;
	}

	public String getKhName() {
		return khName;
	}

	public void setKhName(String khName) {
		this.khName = khName;
	}

	public String getShName() {
		return shName;
	}

	public void setShName(String shName) {
		this.shName = shName;
	}

	public String getShId() {
		return shId;
	}

	public void setShId(String shId) {
		this.shId = shId;
	}
	
	public String getMakeCollectionsDate() {
		return makeCollectionsDate;
	}

	public void setMakeCollectionsDate(String makeCollectionsDate) {
		this.makeCollectionsDate = makeCollectionsDate;
	}

	public String getStartMakeCollectionsDate() {
		return startMakeCollectionsDate;
	}

	public void setStartMakeCollectionsDate(String startMakeCollectionsDate) {
		this.startMakeCollectionsDate = startMakeCollectionsDate;
	}

	public String getEndMakeCollectionsDate() {
		return endMakeCollectionsDate;
	}

	public void setEndMakeCollectionsDate(String endMakeCollectionsDate) {
		this.endMakeCollectionsDate = endMakeCollectionsDate;
	}

	@Override
	public String toString() {
		return "BusinessLoanVO [moneyCount=" + moneyCount + ", contractCount=" + contractCount + ", id=" + id
				+ ", serialNo=" + serialNo + ", businessDate=" + businessDate + ", keepAccountsDate=" + keepAccountsDate
				+ ", payDate=" + payDate + ", makeCollectionsDate=" + makeCollectionsDate + ", startBusinessDate="
				+ startBusinessDate + ", endBusinessDate=" + endBusinessDate + ", startKeepAccountsDate="
				+ startKeepAccountsDate + ", endKeepAccountsDate=" + endKeepAccountsDate + ", startPayDate="
				+ startPayDate + ", endPayDate=" + endPayDate + ", startMakeCollectionsDate=" + startMakeCollectionsDate
				+ ", endMakeCollectionsDate=" + endMakeCollectionsDate + ", productSubType=" + productSubType
				+ ", businessSum=" + businessSum + ", capitalSide=" + capitalSide + ", city=" + city
				+ ", deductionKHServiceFee=" + deductionKHServiceFee + ", deductionSHServiceFee="
				+ deductionSHServiceFee + ", paySum=" + paySum + ", khName=" + khName + ", shName=" + shName + ", shId="
				+ shId + ", array=" + Arrays.toString(array) + "]";
	}
}
