package com.billionsfinance.bas.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanghs
 *
 */
public class CommonUtils {
	/**
	  * <p>Title: buildQueryData</p>
	  * <p>Description: buildQueryData</p>
	  * @param list 
	  * @return string
	  */
	public static String buildQueryData(List<String> list) {
		StringBuffer buffer = new StringBuffer();
		String temp = null;
		for (int i = 0; i < list.size(); i++) {
			temp = list.get(i);
			if (null == temp || 0 == temp.trim().length()) {
				temp = "\'\'";
			}
			buffer.append(temp).append(",");
		}
		String result = buffer.toString().substring(0, buffer.toString().length() - 1);
		return result;
	}

	/**
	  * <p>Title: buildQueryStringData</p>
	  * <p>Description: buildQueryStringData</p>
	  * @param list 
	  * @return string
	  */
	
	
	public static String buildQueryStringData(List<String> list) {
		StringBuffer buffer = new StringBuffer();
		String temp = null;
		for (int i = 0; i < list.size(); i++) {
			temp = list.get(i);
			if (null == temp || 0 == temp.trim().length()) {
				temp = "\'\'";
			} else {
				temp = "'" + temp + "'";
			}
			buffer.append(temp).append(",");
		}
		String result = buffer.toString().substring(0, buffer.toString().length() - 1);
		return result;
	}

	/**
	  * <p>Title: buildQueryDatas</p>
	  * <p>Description: buildQueryDatas</p>
	  * @param list 
	  * @return  list
	  */
	public static List<String> buildQueryDatas(List<List<String>> list) {
		List<String> result = new ArrayList<String>();
		for (int i = 1; i < list.size(); i++) {
			result.add(buildQueryStringData(list.get(i)));
		}
		return result;
	}
}
