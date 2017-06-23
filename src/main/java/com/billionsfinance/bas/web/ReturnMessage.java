package com.billionsfinance.bas.web;

import java.util.Map;

/**
 * 页面返回信息
 * 
 * @author zhanghs
 *
 */
public class ReturnMessage {
	String returnCode = null;
	String returnMsg = null;
	Map<String, Object> returnMap = null;

	public Map<String, Object> getReturnMap() {
		return returnMap;
	}

	public void setReturnMap(Map<String, Object> returnMap) {
		this.returnMap = returnMap;
	}

	/**
	
	  * 创建一个新的实例 ReturnMessage. 
	  * <p>Title: </p>
	  * <p>Description: </p>
	  */
	public ReturnMessage() {

	}

	/**
	
	  * 创建一个新的实例 ReturnMessage. 
	  * <p>Title: </p>
	  * <p>Description: </p>
	  * @param returnCode 
	  * @param returnMsg 
	  */
	public ReturnMessage(String returnCode, String returnMsg) {
		this.returnMsg = returnMsg;
		this.returnCode = returnCode;
	}

	/**
	
	  * 创建一个新的实例 ReturnMessage. 
	  * <p>Title: </p>
	  * <p>Description: </p>
	  * @param returnCode 
	  * @param returnMsg 
	  * @param returnObj 
	  */
	public ReturnMessage(String returnCode, String returnMsg, Map<String, Object> returnObj) {
		this.returnCode = returnCode;
		this.returnMsg = returnMsg;
		this.returnMap = returnObj;

	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() { 
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}


}
