package com.billionsfinance.bas.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @ClassName: AccountTotalCheckingAccountVO.java
 * @Description: 总账对账表JavaBean
 * @author Feima.zhou
 * 
 *         Copyright: Copyright (c) 2017年5月12日下午4:05:55 Company:佰仟金融
 */

public class AccountTotalCheckingAccountVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String startcheckdate;
	
	private String endcheckdate;
	
	private String datasource;
	
	private Integer checknumber;
	
	private String checkdate;
	
	private BigDecimal normal_withhold_back;
	
	private BigDecimal pre_withhold_back;
	
	private BigDecimal temp_withhold_back;
	
	private BigDecimal temp_withhold_back_hang;
	
	private BigDecimal active_deposit;
	
	private BigDecimal active_deposit_hang;
	
	private BigDecimal push_total;
	
	private BigDecimal today_deposit_balance;
	
	private BigDecimal tm1_deposit_balance;

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	public Integer getChecknumber() {
		return checknumber;
	}

	public void setChecknumber(Integer checknumber) {
		this.checknumber = checknumber;
	}

	public String getCheckdate() {
		return checkdate;
	}

	public void setCheckdate(String checkdate) {
		this.checkdate = checkdate;
	}

	public BigDecimal getNormal_withhold_back() {
		return normal_withhold_back;
	}

	public void setNormal_withhold_back(BigDecimal normal_withhold_back) {
		this.normal_withhold_back = normal_withhold_back;
	}

	public BigDecimal getPre_withhold_back() {
		return pre_withhold_back;
	}

	public void setPre_withhold_back(BigDecimal pre_withhold_back) {
		this.pre_withhold_back = pre_withhold_back;
	}

	public BigDecimal getTemp_withhold_back() {
		return temp_withhold_back;
	}

	public void setTemp_withhold_back(BigDecimal temp_withhold_back) {
		this.temp_withhold_back = temp_withhold_back;
	}

	public BigDecimal getTemp_withhold_back_hang() {
		return temp_withhold_back_hang;
	}

	public void setTemp_withhold_back_hang(BigDecimal temp_withhold_back_hang) {
		this.temp_withhold_back_hang = temp_withhold_back_hang;
	}

	public BigDecimal getActive_deposit() {
		return active_deposit;
	}

	public void setActive_deposit(BigDecimal active_deposit) {
		this.active_deposit = active_deposit;
	}

	public BigDecimal getActive_deposit_hang() {
		return active_deposit_hang;
	}

	public void setActive_deposit_hang(BigDecimal active_deposit_hang) {
		this.active_deposit_hang = active_deposit_hang;
	}

	public BigDecimal getPush_total() {
		return push_total;
	}

	public void setPush_total(BigDecimal push_total) {
		this.push_total = push_total;
	}

	public BigDecimal getToday_deposit_balance() {
		return today_deposit_balance;
	}

	public void setToday_deposit_balance(BigDecimal today_deposit_balance) {
		this.today_deposit_balance = today_deposit_balance;
	}

	public BigDecimal getTm1_deposit_balance() {
		return tm1_deposit_balance;
	}

	public void setTm1_deposit_balance(BigDecimal tm1_deposit_balance) {
		this.tm1_deposit_balance = tm1_deposit_balance;
	}
	
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

	@Override
	public String toString() {
		return "AccountTotalCheckingAccountVO [startcheckdate=" + startcheckdate + ", endcheckdate=" + endcheckdate
				+ ", datasource=" + datasource + ", checknumber=" + checknumber + ", checkdate=" + checkdate
				+ ", normal_withhold_back=" + normal_withhold_back + ", pre_withhold_back=" + pre_withhold_back
				+ ", temp_withhold_back=" + temp_withhold_back + ", temp_withhold_back_hang=" + temp_withhold_back_hang
				+ ", active_deposit=" + active_deposit + ", active_deposit_hang=" + active_deposit_hang
				+ ", push_total=" + push_total + ", today_deposit_balance=" + today_deposit_balance
				+ ", tm1_deposit_balance=" + tm1_deposit_balance + "]";
	}
	
}
