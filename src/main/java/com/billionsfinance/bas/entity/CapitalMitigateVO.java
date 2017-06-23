package com.billionsfinance.bas.entity;

import java.io.Serializable;

/**
 * 
 * @author feima.zhou
 * @describe 逾期明细JavaBean
 */

public class CapitalMitigateVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String moneyCount;
	
	private String contractCount;
	
	private String contractNo;//合同号
	
	private String startPayDate;
	
	private String endPayDate;

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

	@Override
	public String toString() {
		return "CapitalMitigateVO [moneyCount=" + moneyCount + ", contractCount=" + contractCount + ", contractNo="
				+ contractNo + ", startPayDate=" + startPayDate + ", endPayDate=" + endPayDate + "]";
	}
}
