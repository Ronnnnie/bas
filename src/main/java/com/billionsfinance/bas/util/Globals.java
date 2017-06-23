package com.billionsfinance.bas.util;

import java.util.HashMap;
import java.util.Map;

/**  
* 类名称：Globals   
* 类描述：  全局变量定义
*
*/
public final class Globals {
	/**
	 * 保存用户到SESSION
	 */
	public static final String USER_SESSION="USER_SESSION";
	public static final Short USER_NORMAL=1; //正常
	public static final Short USER_FORBIDDEN=0; //禁用
	public static final Short USER_ADMIN=-1; //超级管理员
	
	/**
	 * 系统超级权限
	 */
	public static final String SUPERAUTHORITY = "bas_super_admin";
	
	public static final String DEPART_CACHE = "DEPART_CACHE";
	
	public static final String CON_DEPT = "CON_DEPT";
	
	/** 会计撤销角色 **/
	public static final Map<String, String> REVOKE_ROLE = new HashMap<String, String>();;
	static {
		REVOKE_ROLE.put("swcs_system_admin", "swcs系统管理员");
		REVOKE_ROLE.put("swcs_super_admin", "swcs超级管理员");
	}
}
