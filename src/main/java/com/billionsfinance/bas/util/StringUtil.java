package com.billionsfinance.bas.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
  * @ClassName: StringUtil
  * @Description: 
  *  2016年7月19日 下午4:29:55
  * Copyright: Copyright (c) 2016 
  * Company:佰仟金融
  */
public class StringUtil {
	
	/** 日志记录 */
	private static final Log LOGGER = LogFactory.getLog(StringUtil.class);

	/**
	 * 字符串转list
	 * @param text  "[{ROLECODE=sims_Sales_Manager, ROLENAME=销售经理},{ROLECODE=sims_Sales_Manager, ROLENAME=销售经理}]"
	 * @return LIST
	 */
	public static List<Map<String, Object>> stringToList(String text){
		if(text == null || "".equals(text)){
			return null;
		}
		
		text = text.trim();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(!"[]".equals(text)){
			//去除字符串空格
			text = text.replace(" ", "");
			//替找字符串map标识
			text = text.replace("}, {", ";");
			text = text.replace("},{", ";");
			text = text.substring(2, text.length()-2);
			//获取map数组
			String[] strarray = text.split(";");
			
			Map<String, Object> map = null;
			//遍历map数组字符串,并转换成map对象
			for(String strs:strarray){
				String[] stMap = strs.split(",");
				map = new HashMap<String, Object>();
				for(String strMap:stMap){
					String[] sMap = strMap.split("=");
					map.put(sMap[0], sMap[1]);
				}
				list.add(map);
		   }
		   return list;
		}
		return list;
	}
	
	/**
	 * 根据指定key和companyue获取list
	 * @param listData  list数据
	 * @param key    指key
	 * @param companyue   指定值
	 * @return  返回满足key和companyue 数据
	 */
	public static List<Map<String, Object>> getListByParam(List<Map<String, Object>> listData, String key, String companyue){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		//遍历list
		if(listData != null){
			//根据指定key和companyue获取list
			for(Map<String, Object> map : listData){
				if(map.get(key).equals(companyue)){
					list.add(map);
				}
			}
		}
		return list;
	}
	
	public static String isNullOrEmpty(Object obj){
		if( ("").equals(obj) || obj == null){
			return "";
		}else{
			return obj.toString();
		}
	}
	
	public static boolean checkIsNull(Object obj){
		if( ("").equals(obj) || obj == null){
			return true;
		}else{
			return false;
		}
	}
	public static boolean checkIsNotNull(Object obj){
		if( ("").equals(obj) || obj == null){
			return false;
		}else{
			return true;
		}
	}
	public static String checkMoneyIsNull(Object obj){
		if( ("").equals(obj) || obj == null){
			return "0.00";
		}else{
			return obj.toString();
		}
	}
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String stringFormatter(String str){
		String temp = "";
		try {
			temp = new String(str.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("字符串转码异常!", e);
		}
		return temp;
	}
	
	public static String companyFormatter(String company){
		if ("BQJR".equals(company)){		
			return "佰仟金融";
		}else if("GZBC".equals(company)){		
			return "贵州佰诚";
		}else if("RMBX".equals(company)){		
			return "中国人民财产保险";
		}else if("BR".equals(company)){		
			return "佰融";
		}else if("HBYH".equals(company)){		
			return "哈尔滨银行";
		}else if("JSZB".equals(company)){		
			return "嘉实资本";
		}else if("HKYH".equals(company)){		
			return "海口农商行";
		}else if("ZXXT".equals(company)){		
			return "中信信托";
		}else if("ZTXT".equals(company)){		
			return "中泰信托";
		}else if("ZHXT".equals(company)){		
			return "中航信托";
		}else if("JYPH".equals(company)){
			return "嘉银普惠";
		}else if("RXCH".equals(company)){		
			return "宝安融兴村行";
		}else if("SZKK".equals(company)){		
			return "深圳快快";
		}else if("XZHR".equals(company)){		
			return "西藏惠融";
		}else if("BDGY".equals(company)){		
			return "百度国元";
		}else{
			return company;
		}
	}

	
//	public static void main(String[] args) {
//		String str="[{ROLECODE=sims_Sales_Manager, ROLENAME=销售经理},{ROLECODE=sims_Sales_Manager, ROLENAME=销售}]";
//		List list = stringToList(str);
//		System.out.println(list);
//		List lis = getListByParam(list,"ROLENAME","销售");
//		System.out.println(lis);
//		
//
//	}
}

