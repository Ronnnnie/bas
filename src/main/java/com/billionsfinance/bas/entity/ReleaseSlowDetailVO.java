package com.billionsfinance.bas.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 
 * @author fmZhoua
 * @describe 回款表VO
 */

public class ReleaseSlowDetailVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String serialNo;
	
	private String[] serialNoArray;
	
	private String startKeepAccountsDate;
	
	private String endKeepAccountsDate;
	
	private String sffk;
	
	private String updateDate;
	
	private String keepAccountsBy;
	
	private String keepAccountsRemark;
	
	public String getKeepAccountsRemark() {
		return keepAccountsRemark;
	}

	public void setKeepAccountsRemark(String keepAccountsRemark) {
		this.keepAccountsRemark = keepAccountsRemark;
	}

	public String[] getSerialNoArray() {
		return serialNoArray;
	}

	public void setSerialNoArray(String[] serialNoArray) {
		this.serialNoArray = serialNoArray;
	}

	public String getKeepAccountsBy() {
		return keepAccountsBy;
	}

	public void setKeepAccountsBy(String keepAccountsBy) {
		this.keepAccountsBy = keepAccountsBy;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
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

	public String getSffk() {
		return sffk;
	}

	public void setSffk(String sffk) {
		this.sffk = sffk;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "ReleaseSlowDetailVO [serialNo=" + serialNo + ", serialNoArray=" + Arrays.toString(serialNoArray)
				+ ", startKeepAccountsDate=" + startKeepAccountsDate + ", endKeepAccountsDate=" + endKeepAccountsDate
				+ ", sffk=" + sffk + ", updateDate=" + updateDate + ", keepAccountsBy=" + keepAccountsBy
				+ ", keepAccountsRemark=" + keepAccountsRemark + "]";
	}
	
	
}
