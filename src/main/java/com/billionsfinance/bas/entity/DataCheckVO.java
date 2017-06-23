package com.billionsfinance.bas.entity;

import java.io.Serializable;

/**
 * 
 * @author fmZhoua
 * @describe 回款表VO
 */

public class DataCheckVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String checkType;

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	@Override
	public String toString() {
		return "DataCheckVO [checkType=" + checkType + "]";
	}
}
