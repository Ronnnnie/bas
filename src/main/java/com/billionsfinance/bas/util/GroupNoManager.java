package com.billionsfinance.bas.util;

import javax.servlet.http.HttpServletRequest;

import com.billionsfinance.common.framework.GlobalParam;

/**
* @Title: GroupManager.java
* @Description:服务组号管理 
* @author HuGuobo
* 2015年10月13日 上午10:00:19
* @version V1.0
 */
public class GroupNoManager {

	/**
	 * 获取服务组号
	 * @param request 
	 * @return string
	 */
	public static String getGroupNo(HttpServletRequest request){
		//服务组号默认为项目代码
		String groupNo = GlobalParam.getParam("app_code");
		return groupNo;
	}
}
