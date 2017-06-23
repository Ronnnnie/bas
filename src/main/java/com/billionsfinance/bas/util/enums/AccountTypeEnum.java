package com.billionsfinance.bas.util.enums;

/**
 * @ClassName: AccountType.java
 * @Description:费用计算枚举
 * @author lin.tang
 * @date 2017年6月1日 下午5:47:40 Copyright: Copyright (c) 2017-2050 Company:BQJR
 */
public enum AccountTypeEnum {
	PAYPRINCIPALAMT("实还本金", "PAYPRINCIPALAMT"), 
	PAYINTEAMT("实还利息", "PAYINTEAMT"), 
	A2("实还客户服务费", "A2"), 
	A7("实还账户管理费", "A7"), 
	A12("实还增值服务费", "A12"), 
	A18("实还随心还服务费", "A18"), 
	A9("实还提前还款手续费", "A9"), 
	A10("实还滞纳金", "A10"), 
	A17("实还委外催收费", "A17"), 
	A19("实还提前委外催收费", "A19");
	
	private String name;
	private String value;

	AccountTypeEnum(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static String getValue(String name) {
		for (AccountTypeEnum enu : AccountTypeEnum.values()) {
			if (enu.name.equals(name)) {
				return enu.getValue();
			}
		}
		return null;
	}
	public static String getlikeValue(String name) {
		for (AccountTypeEnum enu : AccountTypeEnum.values()) {
			if (enu.name.indexOf(name)>-1) {
				return enu.getValue();
			}
		}
		return null;
	}
	public static String getName(String value) {

		for (AccountTypeEnum enu : AccountTypeEnum.values()) {
			if (enu.value.equals(value)) {
				return enu.getName();
			}
		}
		return null;
	}
}
