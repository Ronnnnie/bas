package com.billionsfinance.bas.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 
 * @author fmZhoua
 * @describe 资金明细VO
 */

public class ContractVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String array[];
	
	private Double moneyCount;
	
	private Long contractCount;

	private String id;//主键
	
	private String serialNo; //合同号
	
	private String businessDate;// 注册日期 贷款日期
	
	private String keepAccountsDate; //KEEPACCOUNTS_DATE 记账日期 
	
	private String makeCollectionsDate;//收款日期
	
	
	private String startBusinessDate;// 注册日期 贷款日期 起
	
	private String endBusinessDate;// 注册日期 贷款日期 至
	
	
	
	private String startKeepAccountsDate; //KEEPACCOUNTS_DATE 记账日期  起
	
	private String endKeepAccountsDate; //KEEPACCOUNTS_DATE 记账日期  至
	
	
	private String startMakeCollectionsDate;//收款日期 起
	
	private String endMakeCollectionsDate;//收款日期 至
	
	
	
	private String productSubType;//产品子类型
	
	private Integer businessSum;//BUSINESSSUM  放款本金
	
	private String capitalSide;//Capitalside 资金方
	
	private String clientName;//客户姓名
	
	private String city;//城市
	
	private String updateDate;//操作日期
	
	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	private String[] idArray;
	
	public String[] getIdArray() {
		return idArray;
	}

	public void setIdArray(String[] idArray) {
		this.idArray = idArray;
	}
	
	public String[] getArray() {
		return array;
	}

	public void setArray(String[] array) {
		this.array = array;
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

	public Integer getBusinessSum() {
		return businessSum;
	}

	public void setBusinessSum(Integer businessSum) {
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

	public String getMakeCollectionsDate() {
		return makeCollectionsDate;
	}

	public void setMakeCollectionsDate(String makeCollectionsDate) {
		this.makeCollectionsDate = makeCollectionsDate;
	}

	public String getProductSubType() {
		return productSubType;
	}

	public void setProductSubType(String productSubType) {
		this.productSubType = productSubType;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "ContractVO [array=" + Arrays.toString(array) + ", moneyCount=" + moneyCount + ", contractCount="
				+ contractCount + ", id=" + id + ", serialNo=" + serialNo + ", businessDate=" + businessDate
				+ ", keepAccountsDate=" + keepAccountsDate + ", makeCollectionsDate=" + makeCollectionsDate
				+ ", startBusinessDate=" + startBusinessDate + ", endBusinessDate=" + endBusinessDate
				+ ", startKeepAccountsDate=" + startKeepAccountsDate + ", endKeepAccountsDate=" + endKeepAccountsDate
				+ ", startMakeCollectionsDate=" + startMakeCollectionsDate + ", endMakeCollectionsDate="
				+ endMakeCollectionsDate + ", productSubType=" + productSubType + ", businessSum=" + businessSum
				+ ", capitalSide=" + capitalSide + ", clientName=" + clientName + ", city=" + city + ", updateDate="
				+ updateDate + ", idArray=" + Arrays.toString(idArray) + "]";
	}
	
}
