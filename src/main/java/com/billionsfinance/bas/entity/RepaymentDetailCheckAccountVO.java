package com.billionsfinance.bas.entity;

import java.io.Serializable;

/**
 * 
 * @ClassName: RepaymentDetailCheckAccountVO.java
 * @Description: 还款明细对账表JavaBean
 * @author Feima.zhou
 * 
 *         Copyright: Copyright (c) 2017年5月12日下午4:06:20 Company:佰仟金融
 */

public class RepaymentDetailCheckAccountVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String startcheckdate;
	
	private String endcheckdate;
	
	private String datasource;

	public String getStartcheckdate() {
		return startcheckdate;
	}

	public void setStartcheckdate(String startcheckdate) {
		this.startcheckdate = startcheckdate;
	}

	public String getEndcheckdate() {
		return endcheckdate;
	}

	public void setEndcheckdate(String endcheckdate) {
		this.endcheckdate = endcheckdate;
	}

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	@Override
	public String toString() {
		return "RepaymentDetailCheckAccountVO [startcheckdate=" + startcheckdate + ", endcheckdate=" + endcheckdate
				+ ", datasource=" + datasource + "]";
	}
	
}
