package com.billionsfinance.bas.entity.voucher.vo;

/**
 * @ClassName: SelectConditionVo.java
 * @Description:
 * @author lin.tang
 * @date 2017年6月1日 下午7:23:13 Copyright: Copyright (c) 2017-2050 Company:BQJR
 */
public class SelectConditionVo {
	private FactSumVo sum;
	private String city;
	private FactAccountVo factAccountVo;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public FactAccountVo getFactAccountVo() {
		return factAccountVo;
	}

	public void setFactAccountVo(FactAccountVo factAccountVo) {
		this.factAccountVo = factAccountVo;
	}

	public FactSumVo getSum() {
		return sum;
	}

	public void setSum(FactSumVo sum) {
		this.sum = sum;
	}

}
