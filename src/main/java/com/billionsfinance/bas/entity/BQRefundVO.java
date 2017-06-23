package com.billionsfinance.bas.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 
 * @author fmZhoua
 * @describe 取消合同佰仟退款VO
 */

public class BQRefundVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double moneyCount;
	
	private long contractCount;
	
	private String id;//主键
	
	private String serialNo; //合同号
	
	private String businessDate;// 注册日期 贷款日期
	
	private String keepAccountsDate; //KEEPACCOUNTS_DATE 记账日期 
	
	private String makeCollectionsDate;//收款日期
	
	private String payDate;//支付日期
	
	private String updateDate;//修改日期
	
	private String startBusinessDate;// 注册日期 贷款日期 起
	
	private String endBusinessDate;// 注册日期 贷款日期 至
	
	private String startKeepAccountsDate; //KEEPACCOUNTS_DATE 记账日期  起
	
	private String endKeepAccountsDate; //KEEPACCOUNTS_DATE 记账日期  至
	
	private String startMakeCollectionsDate;//收款日期 起
	
	private String endMakeCollectionsDate;//收款日期 至
	
	private String startPayDate;//付款日期 起
	
	private String endPayDate;//付款日期 至
	
	private String productSubType;//产品子类型
	
	private double businessSum;//BUSINESSSUM  贷款本金
	
	private String capitalSide;//Capitalside 资金方
	
	private String city;//城市
	
	private Integer periods; //期数
	
	private Double returnSum;//退还金额
	
	private String contractStatus;//合同状态
	
	private double deductionKHServiceFee;//扣减客户服务费
	
	private double deductionSHServiceFee;//扣减商户服务费
	
	private String khName;//客户姓名
	
	private String shName;//商户姓名
	
	private String shId;//商户Id
	
	private String[] array;
	
	private String[] idArray;
	
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

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getPeriods() {
		return periods;
	}

	public void setPeriods(Integer periods) {
		this.periods = periods;
	}

	public Double getReturnSum() {
		return returnSum;
	}

	public void setReturnSum(Double returnSum) {
		this.returnSum = returnSum;
	}

	public String getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

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
	
	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	@Override
	public String toString() {
		return "ClientRefundVO [moneyCount=" + moneyCount + ", contractCount=" + contractCount + ", id=" + id
				+ ", serialNo=" + serialNo + ", businessDate=" + businessDate + ", keepAccountsDate=" + keepAccountsDate
				+ ", makeCollectionsDate=" + makeCollectionsDate + ", payDate=" + payDate + ", updateDate=" + updateDate
				+ ", startBusinessDate=" + startBusinessDate + ", endBusinessDate=" + endBusinessDate
				+ ", startKeepAccountsDate=" + startKeepAccountsDate + ", endKeepAccountsDate=" + endKeepAccountsDate
				+ ", startMakeCollectionsDate=" + startMakeCollectionsDate + ", endMakeCollectionsDate="
				+ endMakeCollectionsDate + ", productSubType=" + productSubType + ", businessSum=" + businessSum
				+ ", capitalSide=" + capitalSide + ", city=" + city + ", periods=" + periods + ", returnSum="
				+ returnSum + ", contractStatus=" + contractStatus + ", deductionKHServiceFee=" + deductionKHServiceFee
				+ ", deductionSHServiceFee=" + deductionSHServiceFee + ", khName=" + khName + ", shName=" + shName
				+ ", shId=" + shId + ", array=" + Arrays.toString(array) + ", idArray=" + Arrays.toString(idArray)
				+ "]";
	}
	
}
