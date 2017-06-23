package com.billionsfinance.bas.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: JsonData
 * @Description: 2016年7月19日 下午4:21:40 Copyright: Copyright (c) 2016 Company:佰仟金融
 */
public class JsonData {

	/**
	 * <p>
	 * Title: stringToBean
	 * </p>
	 * <p>
	 * Description: stringToBean
	 * </p>
	 * 
	 * @param str 
	 * @param clazz 
	 * @return object 
	 * @throws Exception 
	 */
	public static Object stringToBean(String str, Class<?> clazz) throws Exception {
		if (str == null) {
			return null;
		}
		try {
			Object jsonObject = JSONObject.parseObject(str, clazz);
			return jsonObject;
		} catch (Exception e) {
			throw new Exception(e.getMessage() + ":" + str, e);
		}
	}

	/**
	 * * 将JSONObjec对象转换成Map-List集合 *
	 * 
	 * @see JSONHelper#reflect(JSONArray) * @param json * @return
	 */
	/**
	 * <p>
	 * Title: 将JSONObjec对象转换成Map-List集合
	 * </p>
	 * <p>
	 * Description: 将JSONObjec对象转换成Map-List集合
	 * </p>
	 * 
	 * @see JSONHelper#reflect(JSONArray)
	 * @param json 
	 * @return map 
	 */

	public static HashMap<String, Object> reflect(JSONObject json) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Set<?> keys = json.keySet();
		for (Object key : keys) {
			Object o = json.get(key);
			if (o instanceof JSONArray) {
				map.put((String) key, reflect((JSONArray) o));
			} else if (o instanceof JSONObject) {
				map.put((String) key, reflect((JSONObject) o));
			} else {
				map.put((String) key, o);
			}
		}
		return map;
	}

	/**
	 * * 将JSONArray对象转换成Map-List集合 *
	 * 
	 * @see JSONHelper#reflect(JSONObject)
	 * @param json 
	 * @return object
	 */
	public static Object reflect(JSONArray json) {
		List<Object> list = new ArrayList<Object>();
		for (Object o : json) {
			if (o instanceof JSONArray) {
				list.add(reflect((JSONArray) o));
			} else if (o instanceof JSONObject) {
				list.add(reflect((JSONObject) o));
			} else {
				list.add(o);
			}
		}
		return list;
	}

	/**
	  * <p>Title: strToMap</p>
	  * <p>Description: strToMap</p>
	  * @param str 
	  * @return map 
	  */
	public static Map<String, Object> strToMap(String str) {
		if (str == null || "".equals(str)) {
			return null;
		}
		Map<String, Object> mapJson = (Map<String, Object>) JSONObject.parseObject(str);
		return mapJson;
	}

	/**
	  * <p>Title: mapToArray</p>
	  * <p>Description: mapToArray</p>
	  * @param mapJson 
	  * @param paramKey 
	  * @return list
	  */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> mapToArray(Map<String, Object> mapJson, String paramKey) {
		if (mapJson == null) {
			return null;
		}
		if (paramKey == null || "".equals(paramKey)) {
			return null;
		}
		JSONArray array = (JSONArray) mapJson.get(paramKey);
		if (array != null && array.size() > 0) {
			List<Map<String, Object>> mapList = (List<Map<String, Object>>) JsonData.reflect(array);
			return mapList;
		}
		return null;
	}

	// public static void main(String []args)throws Exception{
	// String str =
	// "{\"Employee\":\"员工号\",\"FullName\":\"姓名\",\"Sex\":1,\"OrgID\":[\"组织1id\",\"组织2id\",\"组织Nid\"],"
	// +
	// "\"Title\":[\"岗位1id\",\"岗位2id\"],\"Email\":\"邮箱\",\"IdCard\":\"身份证后6位\",\"IsAvailable\":0}";
	// //UserHr userObj = (UserHr)stringToBean(str, UserHr.class);
	// //System.out.println(userObj);
	//
	// String org =
	// "{\"ID\":\"组织id\",\"OrgName\":\"组织名\",\"ParentID\":\"父级组织id\",\"OrgLevel\":\"所属层级\",\"IsAvailable\":\"0\"}";
	//
	// //OrgHr orgObj = (OrgHr)stringToBean(str, OrgHr.class);
	// //System.out.println(orgObj);
	// }

}
