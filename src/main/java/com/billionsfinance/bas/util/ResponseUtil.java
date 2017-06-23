package com.billionsfinance.bas.util;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: ResponseUtil
 * @Description: 2016年7月19日 下午4:29:02 Copyright: Copyright (c) 2016 Company:佰仟金融
 */
public abstract class ResponseUtil {

	/**
	  * <p>Title: sendJSONList</p>
	  * <p>Description: sendJSONList</p>
	  * @param response 
	  * @param list  
	  */
	@SuppressWarnings("rawtypes")
	public static void sendJSONList(HttpServletResponse response, Collection list) {
		if (list != null) {
			try {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().print(JSONArray.toJSONString(list));
			} catch (IOException e) {
				LogUtil.ERROR.error("将list转换为JSON数组失败", e);
			}
		} else {
			throw new RuntimeException("SendUtil.sendJSONList方法给定的参数list为null");
		}
	}

	/**
	  * <p>Title: sendJSON</p>
	  * <p>Description: sendJSON</p>
	  * @param response 
	  * @param obj  
	  */
	public static void sendJSON(HttpServletResponse response, Object obj) {
		if (obj != null) {
			try {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().print(JSONObject.toJSONString(obj));
				LogUtil.APP.debug("返回到页面的json数据:" + JSONObject.toJSONString(obj));
			} catch (IOException e) {
				LogUtil.ERROR.error("将对象转换为JSON对象失败", e);
			}
		} else {
			throw new RuntimeException("SendUtil.sendJSON方法给定的参数list为null");
		}
	}

	/**
	  * <p>Title: sendString</p>
	  * <p>Description: sendString</p>
	  * @param response 
	  * @param str  
	  */
	public static void sendString(HttpServletResponse response, String str) {
		if (str != null) {
			try {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().print(str);
			} catch (IOException e) {
				LogUtil.ERROR.error("输出字符串失败", e);
			}
		} else {
			throw new RuntimeException("SendUtil.sendString方法给定的参数str为null");
		}
	}

	/**
	  * <p>Title: sendMessage</p>
	  * <p>Description: sendMessage</p>
	  * @param response 
	  * @param success 
	  * @param message  
	  */
	public static void sendMessage(HttpServletResponse response, boolean success, String message) {
		Map<String, Object> responseMsg = new HashMap<String, Object>();
		if (message != null) {
			// 返回成功或失败信息
			responseMsg.put("success", success);
			responseMsg.put("message", message);
			try {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().print(JSONObject.toJSONString(responseMsg));
			} catch (IOException e) {
				LogUtil.ERROR.error("返回提示信息失败", e);
			}
		} else {
			throw new RuntimeException("SendUtil.sendString方法给定的参数str为null");
		}
	}
}
