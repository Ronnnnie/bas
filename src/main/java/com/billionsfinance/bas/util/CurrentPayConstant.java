package com.billionsfinance.bas.util;

import java.util.HashMap;
import java.util.Map;

/**  
* 类名称：Globals   
* 类描述：  全局变量定义
*
*/
public final class CurrentPayConstant {
	
	/** 会计撤销角色 **/
	public static final Map<String, String> CURRENT_PAY_ASSETBELONG = new HashMap<String, String>();;
	static {
		CURRENT_PAY_ASSETBELONG.put("JYPH", "嘉银普惠");
		CURRENT_PAY_ASSETBELONG.put("ZTXT", "中泰信托");
		CURRENT_PAY_ASSETBELONG.put("ZXXT", "中信信托");
		CURRENT_PAY_ASSETBELONG.put("RXCH", "宝安融兴村行");
		CURRENT_PAY_ASSETBELONG.put("HBYH", "哈尔滨银行");
	}
}
