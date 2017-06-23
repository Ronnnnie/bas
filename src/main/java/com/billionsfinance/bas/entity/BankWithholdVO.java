package com.billionsfinance.bas.entity;

import java.io.Serializable;

/**
 * 
 * @ClassName: BankWithholdVO.java
 * @Description: 每日银行代扣对账表JavaBean
 * @author Feima.zhou
 * 
 *         Copyright: Copyright (c) 2017年5月8日下午4:24:40 Company:佰仟金融
 */

public class BankWithholdVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String startInputDate;
	
	private String endInputDate;

	@Override
	public String toString() {
		return "BankWithholdVO [startInputDate=" + startInputDate + ", endInputDate=" + endInputDate + "]";
	}

	public String getStartInputDate() {
		return startInputDate;
	}

	public void setStartInputDate(String startInputDate) {
		this.startInputDate = startInputDate;
	}

	public String getEndInputDate() {
		return endInputDate;
	}

	public void setEndInputDate(String endInputDate) {
		this.endInputDate = endInputDate;
	}
	
}
