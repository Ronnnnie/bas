package com.billionsfinance.bas.entity.voucher.vo;

import java.math.BigDecimal;

/**
 * @ClassName: VoucherResult.java
 * @Description: 凭证结果VO
 * @author lin.tang
 * @date 2017年5月31日 下午2:48:09
 * Copyright: Copyright (c) 2017-2050 Company:BQJR
 */
public class VoucherResultVo{
	/**统计总费用**/
	private BigDecimal totalfee;
	private String city;
	/**记账日期**/
	private String dueDate;
	
	
	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public BigDecimal getTotalfee() {
		return totalfee;
	}

	public void setTotalfee(BigDecimal totalfee) {
		this.totalfee = totalfee;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	
}


